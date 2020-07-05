package com.abchina.sh.dev2.ss5.model;

/**
 * X’00’ succeeded
 * X’01’ general SOCKS server failure
 * X’02’ connection not allowed by ruleset
 * X’03’ Network unreachable
 * X’04’ Host unreachable
 * X’05’ Connection refused
 * X’06’ TTL expired
 * X’07’ Command not supported
 * X’08’ Address type not supported
 * X’09’ to X’FF’ unassigned
 */
public enum Reply {
    SUCCEED((byte) 0x00),
    GENERAL_FAILURE((byte) 0x01),
    NOT_ALLOWED((byte) 0x02),
    NETWORK_UNREACHABLE((byte) 0x03),
    HOST_UNREACHABLE((byte) 0x04),
    CONNECTION_REFUSED((byte) 0x05),
    TTL_EXPIRED((byte) 0x06),
    COMMAND_NOT_SUPPORTED((byte) 0x07),
    ADDRESS_TYPE_NOT_SUPPORTED((byte) 0x08),
    UNASSIGNED((byte) 0x09),
    UNKNOWN((byte) 0xFF);

    private final byte value;

    Reply(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    public static Reply parseValue(byte value) {
        switch (value) {
            case 0x00: return SUCCEED;
            case 0x01: return GENERAL_FAILURE;
            case 0x02: return NOT_ALLOWED;
            case 0x03: return NETWORK_UNREACHABLE;
            case 0x04: return HOST_UNREACHABLE;
            case 0x05: return CONNECTION_REFUSED;
            case 0x06: return TTL_EXPIRED;
            case 0x07: return COMMAND_NOT_SUPPORTED;
            case 0x08: return ADDRESS_TYPE_NOT_SUPPORTED;
            case 0x09 : return UNASSIGNED;
            default: return UNKNOWN;
        }
    }
}
