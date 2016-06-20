package com.diev.aplicacion.diev.httpclient;


public enum MethodType {
    GET("GET"),
    POST("POST");

    private final String value;

    MethodType(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
