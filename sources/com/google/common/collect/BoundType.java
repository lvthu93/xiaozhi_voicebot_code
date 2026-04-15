package com.google.common.collect;

public enum BoundType {
    OPEN,
    CLOSED;

    public static BoundType b(boolean z) {
        return z ? CLOSED : OPEN;
    }
}
