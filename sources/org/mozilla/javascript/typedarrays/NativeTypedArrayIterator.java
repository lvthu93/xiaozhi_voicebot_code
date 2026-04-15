package org.mozilla.javascript.typedarrays;

import j$.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class NativeTypedArrayIterator<T> implements ListIterator<T>, Iterator {
    private int lastPosition = -1;
    private int position;
    private final NativeTypedArrayView<T> view;

    public NativeTypedArrayIterator(NativeTypedArrayView<T> nativeTypedArrayView, int i) {
        this.view = nativeTypedArrayView;
        this.position = i;
    }

    public void add(T t) {
        throw new UnsupportedOperationException();
    }

    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        Iterator.CC.$default$forEachRemaining(this, consumer);
    }

    public boolean hasNext() {
        return this.position < this.view.length;
    }

    public boolean hasPrevious() {
        return this.position > 0;
    }

    public T next() {
        if (hasNext()) {
            T t = this.view.get(this.position);
            int i = this.position;
            this.lastPosition = i;
            this.position = i + 1;
            return t;
        }
        throw new NoSuchElementException();
    }

    public int nextIndex() {
        return this.position;
    }

    public T previous() {
        if (hasPrevious()) {
            int i = this.position - 1;
            this.position = i;
            this.lastPosition = i;
            return this.view.get(i);
        }
        throw new NoSuchElementException();
    }

    public int previousIndex() {
        return this.position - 1;
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }

    public void set(T t) {
        int i = this.lastPosition;
        if (i >= 0) {
            this.view.js_set(i, t);
            return;
        }
        throw new IllegalStateException();
    }
}
