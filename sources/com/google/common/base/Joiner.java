package com.google.common.base;

import java.io.IOException;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

public class Joiner {
    public final String a;

    public static final class MapJoiner {
        public final Joiner a;
        public final String b;

        public MapJoiner(Joiner joiner, String str) {
            this.a = joiner;
            this.b = (String) Preconditions.checkNotNull(str);
        }

        public <A extends Appendable> A appendTo(A a2, Map<?, ?> map) throws IOException {
            return appendTo(a2, (Iterable<? extends Map.Entry<?, ?>>) map.entrySet());
        }

        public String join(Map<?, ?> map) {
            return join((Iterable<? extends Map.Entry<?, ?>>) map.entrySet());
        }

        public MapJoiner useForNull(String str) {
            return new MapJoiner(this.a.useForNull(str), this.b);
        }

        public StringBuilder appendTo(StringBuilder sb, Map<?, ?> map) {
            return appendTo(sb, (Iterable<? extends Map.Entry<?, ?>>) map.entrySet());
        }

        public String join(Iterable<? extends Map.Entry<?, ?>> iterable) {
            return join(iterable.iterator());
        }

        public <A extends Appendable> A appendTo(A a2, Iterable<? extends Map.Entry<?, ?>> iterable) throws IOException {
            return appendTo(a2, iterable.iterator());
        }

        public String join(Iterator<? extends Map.Entry<?, ?>> it) {
            return appendTo(new StringBuilder(), it).toString();
        }

        public <A extends Appendable> A appendTo(A a2, Iterator<? extends Map.Entry<?, ?>> it) throws IOException {
            Preconditions.checkNotNull(a2);
            if (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                Object key = entry.getKey();
                Joiner joiner = this.a;
                a2.append(joiner.a(key));
                String str = this.b;
                a2.append(str);
                a2.append(joiner.a(entry.getValue()));
                while (it.hasNext()) {
                    a2.append(joiner.a);
                    Map.Entry entry2 = (Map.Entry) it.next();
                    a2.append(joiner.a(entry2.getKey()));
                    a2.append(str);
                    a2.append(joiner.a(entry2.getValue()));
                }
            }
            return a2;
        }

        public StringBuilder appendTo(StringBuilder sb, Iterable<? extends Map.Entry<?, ?>> iterable) {
            return appendTo(sb, iterable.iterator());
        }

        public StringBuilder appendTo(StringBuilder sb, Iterator<? extends Map.Entry<?, ?>> it) {
            try {
                appendTo(sb, it);
                return sb;
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        }
    }

    public Joiner(String str) {
        this.a = (String) Preconditions.checkNotNull(str);
    }

    public static Joiner on(String str) {
        return new Joiner(str);
    }

    public CharSequence a(Object obj) {
        Preconditions.checkNotNull(obj);
        if (obj instanceof CharSequence) {
            return (CharSequence) obj;
        }
        return obj.toString();
    }

    public <A extends Appendable> A appendTo(A a2, Iterable<?> iterable) throws IOException {
        return appendTo(a2, iterable.iterator());
    }

    public final String join(Iterable<?> iterable) {
        return join(iterable.iterator());
    }

    public Joiner skipNulls() {
        return new Joiner(this) {
            public <A extends Appendable> A appendTo(A a, Iterator<?> it) throws IOException {
                Joiner joiner;
                Preconditions.checkNotNull(a, "appendable");
                Preconditions.checkNotNull(it, "parts");
                while (true) {
                    boolean hasNext = it.hasNext();
                    joiner = Joiner.this;
                    if (hasNext) {
                        Object next = it.next();
                        if (next != null) {
                            a.append(joiner.a(next));
                            break;
                        }
                    } else {
                        break;
                    }
                }
                while (it.hasNext()) {
                    Object next2 = it.next();
                    if (next2 != null) {
                        a.append(joiner.a);
                        a.append(joiner.a(next2));
                    }
                }
                return a;
            }

            public Joiner useForNull(String str) {
                throw new UnsupportedOperationException("already specified skipNulls");
            }

            public MapJoiner withKeyValueSeparator(String str) {
                throw new UnsupportedOperationException("can't use .skipNulls() with maps");
            }
        };
    }

    public Joiner useForNull(final String str) {
        Preconditions.checkNotNull(str);
        return new Joiner(this) {
            public final CharSequence a(Object obj) {
                return obj == null ? str : Joiner.this.a(obj);
            }

            public Joiner skipNulls() {
                throw new UnsupportedOperationException("already specified useForNull");
            }

            public Joiner useForNull(String str) {
                throw new UnsupportedOperationException("already specified useForNull");
            }
        };
    }

    public MapJoiner withKeyValueSeparator(char c) {
        return withKeyValueSeparator(String.valueOf(c));
    }

    public static Joiner on(char c) {
        return new Joiner(String.valueOf(c));
    }

    public <A extends Appendable> A appendTo(A a2, Iterator<?> it) throws IOException {
        Preconditions.checkNotNull(a2);
        if (it.hasNext()) {
            a2.append(a(it.next()));
            while (it.hasNext()) {
                a2.append(this.a);
                a2.append(a(it.next()));
            }
        }
        return a2;
    }

    public final String join(Iterator<?> it) {
        return appendTo(new StringBuilder(), it).toString();
    }

    public MapJoiner withKeyValueSeparator(String str) {
        return new MapJoiner(this, str);
    }

    public Joiner(Joiner joiner) {
        this.a = joiner.a;
    }

    public final String join(Object[] objArr) {
        return join((Iterable<?>) Arrays.asList(objArr));
    }

    public final String join(final Object obj, final Object obj2, final Object... objArr) {
        Preconditions.checkNotNull(objArr);
        return join((Iterable<?>) new AbstractList<Object>() {
            public Object get(int i) {
                if (i == 0) {
                    return obj;
                }
                if (i == 1) {
                    return obj2;
                }
                return objArr[i - 2];
            }

            public int size() {
                return objArr.length + 2;
            }
        });
    }

    public final <A extends Appendable> A appendTo(A a2, Object[] objArr) throws IOException {
        return appendTo(a2, (Iterable<?>) Arrays.asList(objArr));
    }

    public final StringBuilder appendTo(StringBuilder sb, Iterable<?> iterable) {
        return appendTo(sb, iterable.iterator());
    }

    public final StringBuilder appendTo(StringBuilder sb, Iterator<?> it) {
        try {
            appendTo(sb, it);
            return sb;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    public final StringBuilder appendTo(StringBuilder sb, Object[] objArr) {
        return appendTo(sb, (Iterable<?>) Arrays.asList(objArr));
    }

    public final <A extends Appendable> A appendTo(A a2, final Object obj, final Object obj2, final Object... objArr) throws IOException {
        Preconditions.checkNotNull(objArr);
        return appendTo(a2, (Iterable<?>) new AbstractList<Object>() {
            public Object get(int i) {
                if (i == 0) {
                    return obj;
                }
                if (i == 1) {
                    return obj2;
                }
                return objArr[i - 2];
            }

            public int size() {
                return objArr.length + 2;
            }
        });
    }

    public final StringBuilder appendTo(StringBuilder sb, final Object obj, final Object obj2, final Object... objArr) {
        Preconditions.checkNotNull(objArr);
        return appendTo(sb, (Iterable<?>) new AbstractList<Object>() {
            public Object get(int i) {
                if (i == 0) {
                    return obj;
                }
                if (i == 1) {
                    return obj2;
                }
                return objArr[i - 2];
            }

            public int size() {
                return objArr.length + 2;
            }
        });
    }
}
