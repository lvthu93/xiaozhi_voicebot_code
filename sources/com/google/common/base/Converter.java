package com.google.common.base;

import j$.util.Iterator;
import java.io.Serializable;
import java.util.Iterator;
import java.util.function.Consumer;

public abstract class Converter<A, B> implements Function<A, B> {
    public final boolean c = true;
    public transient Converter<B, A> f;

    public static final class ConverterComposition<A, B, C> extends Converter<A, C> implements Serializable {
        private static final long serialVersionUID = 0;
        public final Converter<A, B> g;
        public final Converter<B, C> h;

        public ConverterComposition(Converter<A, B> converter, Converter<B, C> converter2) {
            this.g = converter;
            this.h = converter2;
        }

        public final A a(C c) {
            return this.g.a(this.h.a(c));
        }

        public final C b(A a) {
            return this.h.b(this.g.b(a));
        }

        public final A d(C c) {
            throw new AssertionError();
        }

        public final C e(A a) {
            throw new AssertionError();
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof ConverterComposition)) {
                return false;
            }
            ConverterComposition converterComposition = (ConverterComposition) obj;
            if (!this.g.equals(converterComposition.g) || !this.h.equals(converterComposition.h)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return this.h.hashCode() + (this.g.hashCode() * 31);
        }

        public String toString() {
            return this.g + ".andThen(" + this.h + ")";
        }
    }

    public static final class FunctionBasedConverter<A, B> extends Converter<A, B> implements Serializable {
        public final Function<? super A, ? extends B> g;
        public final Function<? super B, ? extends A> h;

        public FunctionBasedConverter() {
            throw null;
        }

        public FunctionBasedConverter(Function function, Function function2) {
            this.g = (Function) Preconditions.checkNotNull(function);
            this.h = (Function) Preconditions.checkNotNull(function2);
        }

        public final A d(B b) {
            return this.h.apply(b);
        }

        public final B e(A a) {
            return this.g.apply(a);
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof FunctionBasedConverter)) {
                return false;
            }
            FunctionBasedConverter functionBasedConverter = (FunctionBasedConverter) obj;
            if (!this.g.equals(functionBasedConverter.g) || !this.h.equals(functionBasedConverter.h)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return this.h.hashCode() + (this.g.hashCode() * 31);
        }

        public String toString() {
            return "Converter.from(" + this.g + ", " + this.h + ")";
        }
    }

    public static final class IdentityConverter<T> extends Converter<T, T> implements Serializable {
        public static final IdentityConverter g = new IdentityConverter();
        private static final long serialVersionUID = 0;

        private Object readResolve() {
            return g;
        }

        public final <S> Converter<T, S> c(Converter<T, S> converter) {
            return (Converter) Preconditions.checkNotNull(converter, "otherConverter");
        }

        public final T d(T t) {
            return t;
        }

        public final T e(T t) {
            return t;
        }

        public IdentityConverter<T> reverse() {
            return this;
        }

        public String toString() {
            return "Converter.identity()";
        }
    }

    public static final class ReverseConverter<A, B> extends Converter<B, A> implements Serializable {
        private static final long serialVersionUID = 0;
        public final Converter<A, B> g;

        public ReverseConverter(Converter<A, B> converter) {
            this.g = converter;
        }

        public final B a(A a) {
            return this.g.b(a);
        }

        public final A b(B b) {
            return this.g.a(b);
        }

        public final B d(A a) {
            throw new AssertionError();
        }

        public final A e(B b) {
            throw new AssertionError();
        }

        public boolean equals(Object obj) {
            if (obj instanceof ReverseConverter) {
                return this.g.equals(((ReverseConverter) obj).g);
            }
            return false;
        }

        public int hashCode() {
            return ~this.g.hashCode();
        }

        public Converter<A, B> reverse() {
            return this.g;
        }

        public String toString() {
            return this.g + ".reverse()";
        }
    }

    public static <A, B> Converter<A, B> from(Function<? super A, ? extends B> function, Function<? super B, ? extends A> function2) {
        return new FunctionBasedConverter(function, function2);
    }

    public static <T> Converter<T, T> identity() {
        return IdentityConverter.g;
    }

    public A a(B b) {
        if (!this.c) {
            return d(b);
        }
        if (b == null) {
            return null;
        }
        return Preconditions.checkNotNull(d(b));
    }

    public final <C> Converter<A, C> andThen(Converter<B, C> converter) {
        return c(converter);
    }

    @Deprecated
    public final B apply(A a) {
        return convert(a);
    }

    public B b(A a) {
        if (!this.c) {
            return e(a);
        }
        if (a == null) {
            return null;
        }
        return Preconditions.checkNotNull(e(a));
    }

    public <C> Converter<A, C> c(Converter<B, C> converter) {
        return new ConverterComposition(this, (Converter) Preconditions.checkNotNull(converter));
    }

    public final B convert(A a) {
        return b(a);
    }

    public Iterable<B> convertAll(final Iterable<? extends A> iterable) {
        Preconditions.checkNotNull(iterable, "fromIterable");
        return new Iterable<B>() {
            public Iterator<B> iterator() {
                return new Object() {
                    public final Iterator<? extends A> c;

                    {
                        this.c = iterable.iterator();
                    }

                    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
                        Iterator.CC.$default$forEachRemaining(this, consumer);
                    }

                    public boolean hasNext() {
                        return this.c.hasNext();
                    }

                    public B next() {
                        return Converter.this.convert(this.c.next());
                    }

                    public void remove() {
                        this.c.remove();
                    }
                };
            }
        };
    }

    public abstract A d(B b);

    public abstract B e(A a);

    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public Converter<B, A> reverse() {
        Converter<B, A> converter = this.f;
        if (converter != null) {
            return converter;
        }
        ReverseConverter reverseConverter = new ReverseConverter(this);
        this.f = reverseConverter;
        return reverseConverter;
    }
}
