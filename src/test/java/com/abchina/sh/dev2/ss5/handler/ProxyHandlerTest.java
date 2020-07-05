package com.abchina.sh.dev2.ss5.handler;

import org.junit.jupiter.api.Test;

import java.util.StringJoiner;

import static org.junit.jupiter.api.Assertions.*;

class ProxyHandlerTest {

    @Test
    void getAddress() {
        byte[] bytes = new byte[] {106, 11, 47, 19};
        System.out.println(new StringJoiner(".")
                .add(String.valueOf((bytes[0] & 0x00FF)))
                .add(String.valueOf((bytes[1] & 0x00FF)))
                .add(String.valueOf((bytes[2] & 0x00FF)))
                .add(String.valueOf((bytes[3] & 0x00FF)))
                .toString());
    }
}