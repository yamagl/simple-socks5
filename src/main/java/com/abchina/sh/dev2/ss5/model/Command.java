package com.abchina.sh.dev2.ss5.model;

public enum Command {
    CONNECT,
    BIND,
    UDP,
    UNKNOWN;

    public static Command parseValue(byte value) {
        switch (value) {
            case 0x01: return CONNECT;
            case 0x02: return BIND;
            case 0x03: return UDP;
            default: return UNKNOWN;
        }
    }
}
