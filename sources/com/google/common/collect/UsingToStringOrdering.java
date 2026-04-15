package com.google.common.collect;

import java.io.Serializable;

final class UsingToStringOrdering extends Ordering<Object> implements Serializable {
    public static final UsingToStringOrdering c = new UsingToStringOrdering();
    private static final long serialVersionUID = 0;

    private Object readResolve() {
        return c;
    }

    public int compare(Object obj, Object obj2) {
        return obj.toString().compareTo(obj2.toString());
    }

    public String toString() {
        return "Ordering.usingToString()";
    }
}
