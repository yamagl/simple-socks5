package com.abchina.sh.dev2.ss5.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Socks5HandlerTest {

    @Test
    public void portTest() {
        byte[] port = new byte[]{0b00000001, (byte) 0b10111011};
        assertEquals(443, port[0] << 8 | (port[1] & 0x00FF));
    }

}