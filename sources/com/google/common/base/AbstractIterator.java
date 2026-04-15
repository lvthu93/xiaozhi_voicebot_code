package com.google.common.base;

import j$.util.Iterator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

abstract class AbstractIterator<T> implements Iterator<T>, j$.util.Iterator {
    public State c = State.NOT_READY;
    public T f;

    /* renamed from: com.google.common.base.AbstractIterator$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] a;

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x000d */
        static {
            /*
                com.google.common.base.AbstractIterator$State[] r0 = com.google.common.base.AbstractIterator.State.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                a = r0
                r1 = 1
                r2 = 0
                r0[r2] = r1     // Catch:{ NoSuchFieldError -> 0x000d }
            L_0x000d:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0012 }
                r1 = 2
                r0[r1] = r1     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.base.AbstractIterator.AnonymousClass1.<clinit>():void");
        }
    }

    public enum State {
        READY,
        NOT_READY,
        DONE,
        FAILED
    }

    public abstract T a();

    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        Iterator.CC.$default$forEachRemaining(this, consumer);
    }

    public final boolean hasNext() {
        boolean z;
        State state = this.c;
        State state2 = State.FAILED;
        if (state != state2) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkState(z);
        int ordinal = this.c.ordinal();
        if (ordinal == 0) {
            return true;
        }
        if (ordinal == 2) {
            return false;
        }
        this.c = state2;
        this.f = a();
        if (this.c == State.DONE) {
            return false;
        }
        this.c = State.READY;
        return true;
    }

    public final T next() {
        if (hasNext()) {
            this.c = State.NOT_READY;
            T t = this.f;
            this.f = null;
            return t;
        }
        throw new NoSuchElementException();
    }

    public final void remove() {
        throw new UnsupportedOperationException();
    }
}
