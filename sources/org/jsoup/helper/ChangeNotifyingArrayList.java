package org.jsoup.helper;

import java.util.ArrayList;
import java.util.Collection;

public abstract class ChangeNotifyingArrayList<E> extends ArrayList<E> {
    public ChangeNotifyingArrayList(int i) {
        super(i);
    }

    public boolean add(E e) {
        onContentsChanged();
        return super.add(e);
    }

    public boolean addAll(Collection<? extends E> collection) {
        onContentsChanged();
        return super.addAll(collection);
    }

    public void clear() {
        onContentsChanged();
        super.clear();
    }

    public abstract void onContentsChanged();

    public E remove(int i) {
        onContentsChanged();
        return super.remove(i);
    }

    public boolean removeAll(Collection<?> collection) {
        onContentsChanged();
        return super.removeAll(collection);
    }

    public void removeRange(int i, int i2) {
        onContentsChanged();
        super.removeRange(i, i2);
    }

    public boolean retainAll(Collection<?> collection) {
        onContentsChanged();
        return super.retainAll(collection);
    }

    public E set(int i, E e) {
        onContentsChanged();
        return super.set(i, e);
    }

    public void add(int i, E e) {
        onContentsChanged();
        super.add(i, e);
    }

    public boolean addAll(int i, Collection<? extends E> collection) {
        onContentsChanged();
        return super.addAll(i, collection);
    }

    public boolean remove(Object obj) {
        onContentsChanged();
        return super.remove(obj);
    }
}
