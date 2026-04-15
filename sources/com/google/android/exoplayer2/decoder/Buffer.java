package com.google.android.exoplayer2.decoder;

public abstract class Buffer {
    public int c;

    public final void addFlag(int i) {
        this.c = i | this.c;
    }

    public void clear() {
        this.c = 0;
    }

    public final void clearFlag(int i) {
        this.c = (~i) & this.c;
    }

    public final boolean hasSupplementalData() {
        return (this.c & 268435456) == 268435456;
    }

    public final boolean isDecodeOnly() {
        return (this.c & Integer.MIN_VALUE) == Integer.MIN_VALUE;
    }

    public final boolean isEndOfStream() {
        return (this.c & 4) == 4;
    }

    public final boolean isKeyFrame() {
        return (this.c & 1) == 1;
    }

    public final void setFlags(int i) {
        this.c = i;
    }
}
