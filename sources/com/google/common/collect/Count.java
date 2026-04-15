package com.google.common.collect;

import java.io.Serializable;

final class Count implements Serializable {
    public int c;

    public void add(int i) {
        this.c += i;
    }

    public int addAndGet(int i) {
        int i2 = this.c + i;
        this.c = i2;
        return i2;
    }

    public boolean equals(Object obj) {
        return (obj instanceof Count) && ((Count) obj).c == this.c;
    }

    public int get() {
        return this.c;
    }

    public int getAndSet(int i) {
        int i2 = this.c;
        this.c = i;
        return i2;
    }

    public int hashCode() {
        return this.c;
    }

    public void set(int i) {
        this.c = i;
    }

    public String toString() {
        return Integer.toString(this.c);
    }
}
