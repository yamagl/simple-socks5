package com.abchina.sh.dev2.ss5.model;

import io.vertx.core.buffer.Buffer;

import java.util.Arrays;

/**
 * ver          1
 * rep          1
 * rsv          1 `0x00`
 * atyp         1
 * bnd.addr     variable
 * bnd.port     2
 */
public class SocksReply {

    public byte ver;
    public Reply rep;
    public byte rsv;
    public AddressType atyp;
    public byte[] addr;
    public byte[] port;

    @Override
    public String toString() {
        return "SocksReply{" +
                "ver=" + ver +
                ", rep=" + rep +
                ", rsv=" + rsv +
                ", atyp=" + atyp +
                ", addr=" + Arrays.toString(addr) +
                ", port=" + Arrays.toString(port) +
                '}';
    }

    public SocksReply(Reply rep, AddressType atyp, byte[] addr, byte[] port) {
        this.ver = 0x05;
        this.rep = rep;
        this.rsv = 0x00;
        this.atyp = atyp;
        this.addr = addr;
        this.port = port;
    }

    public SocksReply(Reply rep, AddressType atyp, String addr, int port) {
        this.ver = 0x05;
        this.rep = rep;
        this.rsv = 0x00;
        this.atyp = atyp;

        switch (atyp) {
            case IPV4:
                this.addr = new byte[4];
                int i = 0;
                for (String s : addr.split("\\.")) {
                    if (i == 4) {
                        break;
                    }
                    this.addr[i++] = (byte) Integer.parseInt(s);
                }
                break;
            case DOMAIN_NAME:
                this.addr = addr.getBytes();
        }

        this.port = new byte[] {(byte)(port >> 8), (byte) port};
    }

    public Buffer toBuffer() {
        byte[] bytes = new byte[6 + this.addr.length];

        bytes[0] = ver;
        bytes[1] = rep.getValue();
        bytes[2] = rsv;
        bytes[3] = atyp.getValue();
        System.arraycopy(addr, 0, bytes, 4, addr.length);
        System.arraycopy(port, 0, bytes, 4 + addr.length, port.length);

        return Buffer.buffer(bytes);
    }
}
