package com.google.common.base;

import java.io.Serializable;

public abstract class Equivalence<T> {

    public static final class Equals extends Equivalence<Object> implements Serializable {
        public static final Equals c = new Equals();
        private static final long serialVersionUID = 1;

        private Object readResolve() {
            return c;
        }

        public final boolean a(Object obj, Object obj2) {
            return obj.equals(obj2);
        }

        public final int b(Object obj) {
            return obj.hashCode();
        }
    }

    public static final class EquivalentToPredicate<T> implements Predicate<T>, Serializable {
        private static final long serialVersionUID = 0;
        public final Equivalence<T> c;
        public final T f;

        public EquivalentToPredicate(Equivalence<T> equivalence, T t) {
            this.c = (Equivalence) Preconditions.checkNotNull(equivalence);
            this.f = t;
        }

        public boolean apply(T t) {
            return this.c.equivalent(t, this.f);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof EquivalentToPredicate)) {
                return false;
            }
            EquivalentToPredicate equivalentToPredicate = (EquivalentToPredicate) obj;
            if (!this.c.equals(equivalentToPredicate.c) || !Objects.equal(this.f, equivalentToPredicate.f)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return Objects.hashCode(this.c, this.f);
        }

        public String toString() {
            return this.c + ".equivalentTo(" + this.f + ")";
        }
    }

    public static final class Identity extends Equivalence<Object> implements Serializable {
        public static final Identity c = new Identity();
        private static final long serialVersionUID = 1;

        private Object readResolve() {
            return c;
        }

        public final boolean a(Object obj, Object obj2) {
            return false;
        }

        public final int b(Object obj) {
            return System.identityHashCode(obj);
        }
    }

    public static final class Wrapper<T> implements Serializable {
        private static final long serialVersionUID = 0;
        public final Equivalence<? super T> c;
        public final T f;

        public Wrapper() {
            throw null;
        }

        public Wrapper(Equivalence equivalence, Object obj) {
            this.c = (Equivalence) Preconditions.checkNotNull(equivalence);
            this.f = obj;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Wrapper)) {
                return false;
            }
            Wrapper wrapper = (Wrapper) obj;
            Equivalence<? super T> equivalence = wrapper.c;
            Equivalence<? super T> equivalence2 = this.c;
            if (equivalence2.equals(equivalence)) {
                return equivalence2.equivalent(this.f, wrapper.f);
            }
            return false;
        }

        public T get() {
            return this.f;
        }

        public int hashCode() {
            return this.c.hash(this.f);
        }

        public String toString() {
            return this.c + ".wrap(" + this.f + ")";
        }
    }

    public static Equivalence<Object> equals() {
        return Equals.c;
    }

    public static Equivalence<Object> identity() {
        return Identity.c;
    }

    public abstract boolean a(T t, T t2);

    public abstract int b(T t);

    public final boolean equivalent(T t, T t2) {
        if (t == t2) {
            return true;
        }
        if (t == null || t2 == null) {
            return false;
        }
        return a(t, t2);
    }

    public final Predicate<T> equivalentTo(T t) {
        return new EquivalentToPredicate(this, t);
    }

    public final int hash(T t) {
        if (t == null) {
            return 0;
        }
        return b(t);
    }

    public final <F> Equivalence<F> onResultOf(Function<F, ? extends T> function) {
        return new FunctionalEquivalence(function, this);
    }

    public final <S extends T> Equivalence<Iterable<S>> pairwise() {
        return new PairwiseEquivalence(this);
    }

    public final <S extends T> Wrapper<S> wrap(S s) {
        return new Wrapper<>(this, s);
    }
}
