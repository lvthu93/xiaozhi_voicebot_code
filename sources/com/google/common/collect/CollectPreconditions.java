package com.google.common.collect;

import com.google.common.base.Preconditions;

final class CollectPreconditions {
    public static void a(Object obj, Object obj2) {
        if (obj == null) {
            throw new NullPointerException("null key in entry: null=" + obj2);
        } else if (obj2 == null) {
            throw new NullPointerException("null value in entry: " + obj + "=null");
        }
    }

    public static void b(int i, String str) {
        if (i < 0) {
            throw new IllegalArgumentException(str + " cannot be negative but was: " + i);
        }
    }

    public static void c(long j) {
        if (j < 0) {
            throw new IllegalArgumentException(y2.g("distance cannot be negative but was: ", j));
        }
    }

    public static void d(int i, String str) {
        if (i <= 0) {
            throw new IllegalArgumentException(str + " must be positive but was: " + i);
        }
    }

    public static void e(boolean z) {
        Preconditions.checkState(z, "no calls to next() since the last call to remove()");
    }
}
