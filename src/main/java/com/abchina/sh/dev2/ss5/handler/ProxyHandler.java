package com.abchina.sh.dev2.ss5.handler;

import com.abchina.sh.dev2.ss5.model.AddressType;
import com.abchina.sh.dev2.ss5.model.Reply;
import com.abchina.sh.dev2.ss5.model.SocksReply;
import com.abchina.sh.dev2.ss5.model.SocksRequest;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;
import io.vertx.core.streams.Pump;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ProxyHandler implements Handler<Buffer> {
    private final NetClient netClient;
    private final NetSocket socket;
    private final Logger logger = LoggerFactory.getLogger(ProxyHandler.class);

    public ProxyHandler(NetClient netClient, NetSocket socket) {
        this.netClient = netClient;
        this.socket = socket;
    }

    @Override
    public void handle(Buffer buffer) {
        SocksRequest request = new SocksRequest(buffer);
        logger.info(request.toString());
        switch (request.cmd) {
            case BIND:
                logger.warn("Bind Command not supported");
                replay(Reply.COMMAND_NOT_SUPPORTED);
                break;
            case CONNECT:
                processConnect(request);
                break;
            case UDP:
                logger.warn("UDP Command not supported");
                replay(Reply.COMMAND_NOT_SUPPORTED);
                break;
            case UNKNOWN:
                logger.warn("Unknown Command");
                replay(Reply.COMMAND_NOT_SUPPORTED);
                break;
        }
    }

    private void replay(Reply reply) {
        SocksReply socksReply = new SocksReply(reply, AddressType.IPV4, new byte[] {0, 0, 0, 0}, new byte[] {0, 0});
        socket.write(socksReply.toBuffer(), i -> logger.info(socksReply.toString()));
    }

    void processConnect(SocksRequest request) {
        netClient.connect(request.port, request.addr, result -> {
            if (result.succeeded()) {
                NetSocket clientSocket = result.result();
                logger.info("local host: {} port: {}", clientSocket.localAddress().host(), clientSocket.localAddress().port());
                int localPort = clientSocket.localAddress().port();
                SocksReply socksReply = new SocksReply(Reply.SUCCEED, AddressType.IPV4, clientSocket.localAddress().host(), localPort);
                logger.info(socksReply.toString());
                socket.write(socksReply.toBuffer(), i -> {
                    Pump.pump(clientSocket, socket).start();
                    Pump.pump(socket, clientSocket).start();
                    clientSocket.closeHandler(j -> logger.info("client socket closed"));
                });
            } else {
                replay(Reply.GENERAL_FAILURE);
            }
            socket.closeHandler(j -> logger.info("server socket closed"));
        });
    }

}
