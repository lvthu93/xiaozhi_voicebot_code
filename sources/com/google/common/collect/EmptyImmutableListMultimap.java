package com.google.common.collect;

class EmptyImmutableListMultimap extends ImmutableListMultimap<Object, Object> {
    public static final EmptyImmutableListMultimap m = new EmptyImmutableListMultimap();
    private static final long serialVersionUID = 0;

    public EmptyImmutableListMultimap() {
        super(0, ImmutableMap.of());
    }

    private Object readResolve() {
        return m;
    }
}
