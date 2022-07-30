package com.kkstater.util;

public enum LogModule {
    DETAIL("detail"),
    SIMPLE("simple");
    private String value;
    LogModule(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }

}
