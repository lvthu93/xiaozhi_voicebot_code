package org.schabi.newpipe.extractor.utils;

import j$.util.Objects;
import java.io.Serializable;

public class Pair<F extends Serializable, S extends Serializable> implements Serializable {
    public F c;
    public S f;

    public Pair(F f2, S s) {
        this.c = f2;
        this.f = s;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Pair pair = (Pair) obj;
        if (!Objects.equals(this.c, pair.c) || !Objects.equals(this.f, pair.f)) {
            return false;
        }
        return true;
    }

    public F getFirst() {
        return this.c;
    }

    public S getSecond() {
        return this.f;
    }

    public int hashCode() {
        return Objects.hash(this.c, this.f);
    }

    public void setFirst(F f2) {
        this.c = f2;
    }

    public void setSecond(S s) {
        this.f = s;
    }

    public String toString() {
        return "{" + this.c + ", " + this.f + "}";
    }
}
