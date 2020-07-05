package com.abchina.sh.dev2.ss5.model;

import org.junit.jupiter.api.Test;

class Socks5HandlerTest {

    @Test
    public void portTest() {
        byte[] port = new byte[]{0b00000001, (byte) 0b10111011};
        System.out.println(port[0]);
        System.out.println(port[1] & 0x00FF);

        System.out.println(port[0] << 8 | (port[1] & 0x00FF));
    }

}