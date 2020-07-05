package com.abchina.sh.dev2.ss5.model;

import io.vertx.core.buffer.Buffer;

import java.util.Arrays;

public class MethodRequest {
    public final byte ver;
    public final byte nMethod;
    public final byte[] methods;

    @Override
    public String toString() {
        return "MethodRequest{" +
                "ver=" + ver +
                ", nMethod=" + nMethod +
                ", method=" + Arrays.toString(methods) +
                '}';
    }

    public MethodRequest(Buffer buffer) {
        ver = buffer.getByte(0);
        nMethod = buffer.getByte(1);
        methods = buffer.getBytes(2, nMethod + 2);
    }
}
