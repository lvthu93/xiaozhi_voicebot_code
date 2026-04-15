package com.google.common.base;

public enum StandardSystemProperty {
    ;
    
    public final String c;

    /* access modifiers changed from: public */
    StandardSystemProperty(String str) {
        this.c = str;
    }

    public String key() {
        return this.c;
    }

    public String toString() {
        return key() + "=" + value();
    }

    public String value() {
        return System.getProperty(this.c);
    }
}
