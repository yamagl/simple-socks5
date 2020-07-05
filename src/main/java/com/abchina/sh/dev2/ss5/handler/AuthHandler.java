package com.abchina.sh.dev2.ss5.handler;

import com.abchina.sh.dev2.ss5.model.MethodRequest;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AuthHandler implements Handler<Buffer> {

    private final NetSocket socket;
    private final Logger logger = LoggerFactory.getLogger(AuthHandler.class);
    private final Handler<Buffer> succeedHandler;

    /*
     *     X’00’ NO AUTHENTICATION REQUIRED
     *     X’01’ GSSAPI
     *     X’02’ USERNAME/PASSWORD
     *     X’03’ to X’7F’ IANA ASSIGNED
     *     X’80’ to X’FE’ RESERVED FOR PRIVATE METHODS
     *     X’FF’ NO ACCEPTABLE METHODS
     */
    private final static byte[] ACCEPT_RESPONSE = new byte[] {0x05, 0x00};
    private final static byte[] NO_ACCEPTABLE_METHOD_RESPONSE = new byte[] {0x05, (byte) 0xFF};

    public AuthHandler(NetSocket socket, Handler<Buffer> succeedHandler) {
        this.socket = socket;
        this.succeedHandler = succeedHandler;
    }

    @Override
    public void handle(Buffer buffer) {
        MethodRequest methodRequest = new MethodRequest(buffer);
        logger.info(methodRequest.toString());
        if (methodRequest.ver != 0x05) {
            logger.warn("Incorrect version: {}", methodRequest.ver);
            socket.write(Buffer.buffer(NO_ACCEPTABLE_METHOD_RESPONSE));
            return;
        }
        for (byte m : methodRequest.methods) {
            if (m == 0x00) {
                socket.write(Buffer.buffer(ACCEPT_RESPONSE));
                logger.info("NO AUTHENTICATION REQUIRED");
                socket.handler(succeedHandler);
                return;
            }
        }
        logger.info("NO ACCEPTABLE METHODS");
        socket.write(Buffer.buffer(NO_ACCEPTABLE_METHOD_RESPONSE));
    }
}
