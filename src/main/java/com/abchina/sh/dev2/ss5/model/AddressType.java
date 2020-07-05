package com.abchina.sh.dev2.ss5.model;

public enum AddressType {
    IPV4((byte) 0x01),
    DOMAIN_NAME((byte) 0x03),
    IPV6((byte) 0x04),
    UNKNOWN((byte) 0xFF);

    private final byte value;

    AddressType(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    public static AddressType parseValue(byte value) {
        switch (value) {
            case 0x01:
                return IPV4;
            case 0x03:
                return DOMAIN_NAME;
            case 0x04:
                return IPV6;
            default:
                return UNKNOWN;
        }
    }
}
