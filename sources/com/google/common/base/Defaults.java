package com.google.common.base;

public final class Defaults {
    public static final Double a = Double.valueOf(0.0d);
    public static final Float b = Float.valueOf(0.0f);

    public static <T> T defaultValue(Class<T> cls) {
        Preconditions.checkNotNull(cls);
        if (cls == Boolean.TYPE) {
            return Boolean.FALSE;
        }
        if (cls == Character.TYPE) {
            return 0;
        }
        if (cls == Byte.TYPE) {
            return (byte) 0;
        }
        if (cls == Short.TYPE) {
            return (short) 0;
        }
        if (cls == Integer.TYPE) {
            return 0;
        }
        if (cls == Long.TYPE) {
            return 0L;
        }
        if (cls == Float.TYPE) {
            return b;
        }
        if (cls == Double.TYPE) {
            return a;
        }
        return null;
    }
}
