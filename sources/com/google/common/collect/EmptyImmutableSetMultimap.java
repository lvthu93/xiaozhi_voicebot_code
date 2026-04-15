package com.google.common.collect;

import java.util.Comparator;

class EmptyImmutableSetMultimap extends ImmutableSetMultimap<Object, Object> {
    public static final EmptyImmutableSetMultimap o = new EmptyImmutableSetMultimap();
    private static final long serialVersionUID = 0;

    public EmptyImmutableSetMultimap() {
        super(ImmutableMap.of(), 0, (Comparator) null);
    }

    private Object readResolve() {
        return o;
    }
}
