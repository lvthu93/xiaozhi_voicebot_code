package com.google.common.base;

import com.google.common.base.CharMatcher;
import java.util.BitSet;

final class SmallCharMatcher extends CharMatcher.NamedFastMatcher {
    public final char[] f;
    public final boolean g;
    public final long h;

    public SmallCharMatcher(char[] cArr, long j, boolean z, String str) {
        super(str);
        this.f = cArr;
        this.h = j;
        this.g = z;
    }

    public final void d(BitSet bitSet) {
        if (this.g) {
            bitSet.set(0);
        }
        for (char c : this.f) {
            if (c != 0) {
                bitSet.set(c);
            }
        }
    }

    public boolean matches(char c) {
        boolean z;
        if (c == 0) {
            return this.g;
        }
        if (1 == ((this.h >> c) & 1)) {
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            return false;
        }
        char[] cArr = this.f;
        int length = cArr.length - 1;
        int rotateLeft = (Integer.rotateLeft(11601 * c, 15) * 461845907) & length;
        int i = rotateLeft;
        do {
            char c2 = cArr[i];
            if (c2 == 0) {
                return false;
            }
            if (c2 == c) {
                return true;
            }
            i = (i + 1) & length;
        } while (i != rotateLeft);
        return false;
    }
}
