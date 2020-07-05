package com.abchina.sh.dev2.ss5.model;

import io.vertx.core.buffer.Buffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.StringJoiner;

/**
 * ver          1
 * cmd          1
 * rsv          1 `0x00`
 * atyp         1
 * dst.addr     variable
 * dst.port     2
 */
public class SocksRequest {
    private final Logger logger = LoggerFactory.getLogger(SocksRequest.class);
    public final byte ver;
    public final Command cmd;
    public final byte rsv;
    public final AddressType atyp;
    public String addr;
    public int port;

    @Override
    public String toString() {
        return "SocksRequest{" +
                "ver=" + ver +
                ", cmd=" + cmd +
                ", rsv=" + rsv +
                ", atyp=" + atyp +
                ", addr=" + addr +
                ", port=" + port +
                '}';
    }

    public SocksRequest(Buffer buffer) {
        ver = buffer.getByte(0);
        cmd = Command.parseValue(buffer.getByte(1));
        rsv = buffer.getByte(2);
        atyp =  AddressType.parseValue(buffer.getByte(3));
        switch (atyp) {
            case IPV4:
                getAddress(buffer.getBytes(4, 8));
                port = buffer.getByte(8) << 8 | buffer.getByte(9) & 0x00FF;
                break;
            case IPV6:
                getAddress(buffer.getBytes(4, 20));
                port = buffer.getByte(20) << 8 | buffer.getByte(21) & 0x00FF;
                break;
            case DOMAIN_NAME:
                byte length = buffer.getByte(4);
                getAddress(buffer.getBytes(5, 5 + length));
                port = buffer.getByte(5 + length) << 8 | buffer.getByte(6 + length) & 0x00FF;
                break;
            case UNKNOWN:
            default:
                logger.info("UnKonwn address type: {} buffer layout: {}", buffer.getByte(3), Arrays.toString(buffer.getBytes()));
        }
    }

    private void getAddress(byte[] addr) {
        switch (atyp) {
            case IPV4:
                logger.info("IPV4 address {}", addr);
                this.addr =  new StringJoiner(".")
                        .add(String.valueOf((addr[0] & 0x00FF)))
                        .add(String.valueOf((addr[1] & 0x00FF)))
                        .add(String.valueOf((addr[2] & 0x00FF)))
                        .add(String.valueOf((addr[3] & 0x00FF)))
                        .toString();
                break;
            case IPV6:
                logger.info("IPV6 address {}", addr);
                this.addr =  new StringJoiner(".")
                        .add(String.valueOf((addr[0] & 0x00FF)))
                        .add(String.valueOf((addr[1] & 0x00FF)))
                        .add(String.valueOf((addr[2] & 0x00FF)))
                        .add(String.valueOf((addr[3] & 0x00FF)))
                        .toString();
                break;
            case DOMAIN_NAME:
                this.addr = new String(addr);
                logger.info("Domain address {}", this.addr);
                break;
            case UNKNOWN:
            default:
                this.addr = "0.0.0.0";
        }
    }
}
