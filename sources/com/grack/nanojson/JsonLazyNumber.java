package com.grack.nanojson;

import java.math.BigDecimal;

class JsonLazyNumber extends Number {
    private boolean isDouble;
    private String value;

    public JsonLazyNumber(String str, boolean z) {
        this.value = str;
        this.isDouble = z;
    }

    private Object writeReplace() {
        return new BigDecimal(this.value);
    }

    public double doubleValue() {
        return Double.parseDouble(this.value);
    }

    public float floatValue() {
        return Float.parseFloat(this.value);
    }

    public int intValue() {
        return this.isDouble ? (int) Double.parseDouble(this.value) : Integer.parseInt(this.value);
    }

    public long longValue() {
        return this.isDouble ? (long) Double.parseDouble(this.value) : Long.parseLong(this.value);
    }
}
