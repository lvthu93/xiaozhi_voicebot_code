package com.google.common.collect;

import java.util.Deque;
import java.util.Iterator;

public abstract class ForwardingDeque<E> extends ForwardingQueue<E> implements Deque<E> {
    public void addFirst(E e) {
        g().addFirst(e);
    }

    public void addLast(E e) {
        g().addLast(e);
    }

    public Iterator<E> descendingIterator() {
        return g().descendingIterator();
    }

    public E getFirst() {
        return g().getFirst();
    }

    public E getLast() {
        return g().getLast();
    }

    /* renamed from: j */
    public abstract Deque<E> g();

    public boolean offerFirst(E e) {
        return g().offerFirst(e);
    }

    public boolean offerLast(E e) {
        return g().offerLast(e);
    }

    public E peekFirst() {
        return g().peekFirst();
    }

    public E peekLast() {
        return g().peekLast();
    }

    public E pollFirst() {
        return g().pollFirst();
    }

    public E pollLast() {
        return g().pollLast();
    }

    public E pop() {
        return g().pop();
    }

    public void push(E e) {
        g().push(e);
    }

    public E removeFirst() {
        return g().removeFirst();
    }

    public boolean removeFirstOccurrence(Object obj) {
        return g().removeFirstOccurrence(obj);
    }

    public E removeLast() {
        return g().removeLast();
    }

    public boolean removeLastOccurrence(Object obj) {
        return g().removeLastOccurrence(obj);
    }
}
