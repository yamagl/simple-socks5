package com.abchina.sh.dev2.ss5;


import com.abchina.sh.dev2.ss5.handler.AuthHandler;
import com.abchina.sh.dev2.ss5.handler.ProxyHandler;
import io.vertx.core.Vertx;
import io.vertx.core.net.NetClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private final static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        NetClient netClient = Vertx.vertx().createNetClient();
        Vertx.vertx()
                .createNetServer()
                .connectHandler(socket -> {
                    AuthHandler authHandler = new AuthHandler(socket, new ProxyHandler(netClient, socket));
                    socket.handler(authHandler);
                })
                .listen(9503);
    }

}
