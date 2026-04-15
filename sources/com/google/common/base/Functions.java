package com.google.common.base;

import java.io.Serializable;
import java.util.Map;

public final class Functions {

    public static class ConstantFunction<E> implements Function<Object, E>, Serializable {
        private static final long serialVersionUID = 0;
        public final E c;

        public ConstantFunction(E e) {
            this.c = e;
        }

        public E apply(Object obj) {
            return this.c;
        }

        public boolean equals(Object obj) {
            if (obj instanceof ConstantFunction) {
                return Objects.equal(this.c, ((ConstantFunction) obj).c);
            }
            return false;
        }

        public int hashCode() {
            E e = this.c;
            if (e == null) {
                return 0;
            }
            return e.hashCode();
        }

        public String toString() {
            return "Functions.constant(" + this.c + ")";
        }
    }

    public static class ForMapWithDefault<K, V> implements Function<K, V>, Serializable {
        private static final long serialVersionUID = 0;
        public final Map<K, ? extends V> c;
        public final V f;

        public ForMapWithDefault(Map<K, ? extends V> map, V v) {
            this.c = (Map) Preconditions.checkNotNull(map);
            this.f = v;
        }

        public V apply(K k) {
            Map<K, ? extends V> map = this.c;
            V v = map.get(k);
            if (v != null || map.containsKey(k)) {
                return v;
            }
            return this.f;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof ForMapWithDefault)) {
                return false;
            }
            ForMapWithDefault forMapWithDefault = (ForMapWithDefault) obj;
            if (!this.c.equals(forMapWithDefault.c) || !Objects.equal(this.f, forMapWithDefault.f)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return Objects.hashCode(this.c, this.f);
        }

        public String toString() {
            return "Functions.forMap(" + this.c + ", defaultValue=" + this.f + ")";
        }
    }

    public static class FunctionComposition<A, B, C> implements Function<A, C>, Serializable {
        private static final long serialVersionUID = 0;
        public final Function<B, C> c;
        public final Function<A, ? extends B> f;

        public FunctionComposition(Function<B, C> function, Function<A, ? extends B> function2) {
            this.c = (Function) Preconditions.checkNotNull(function);
            this.f = (Function) Preconditions.checkNotNull(function2);
        }

        public C apply(A a) {
            return this.c.apply(this.f.apply(a));
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof FunctionComposition)) {
                return false;
            }
            FunctionComposition functionComposition = (FunctionComposition) obj;
            if (!this.f.equals(functionComposition.f) || !this.c.equals(functionComposition.c)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return this.f.hashCode() ^ this.c.hashCode();
        }

        public String toString() {
            return this.c + "(" + this.f + ")";
        }
    }

    public static class FunctionForMapNoDefault<K, V> implements Function<K, V>, Serializable {
        private static final long serialVersionUID = 0;
        public final Map<K, V> c;

        public FunctionForMapNoDefault(Map<K, V> map) {
            this.c = (Map) Preconditions.checkNotNull(map);
        }

        public V apply(K k) {
            boolean z;
            Map<K, V> map = this.c;
            V v = map.get(k);
            if (v != null || map.containsKey(k)) {
                z = true;
            } else {
                z = false;
            }
            Preconditions.checkArgument(z, "Key '%s' not present in map", (Object) k);
            return v;
        }

        public boolean equals(Object obj) {
            if (obj instanceof FunctionForMapNoDefault) {
                return this.c.equals(((FunctionForMapNoDefault) obj).c);
            }
            return false;
        }

        public int hashCode() {
            return this.c.hashCode();
        }

        public String toString() {
            return "Functions.forMap(" + this.c + ")";
        }
    }

    public enum IdentityFunction implements Function<Object, Object> {
        ;

        /* access modifiers changed from: public */
        IdentityFunction() {
        }

        public Object apply(Object obj) {
            return obj;
        }

        public String toString() {
            return "Functions.identity()";
        }
    }

    public static class PredicateFunction<T> implements Function<T, Boolean>, Serializable {
        private static final long serialVersionUID = 0;
        public final Predicate<T> c;

        public PredicateFunction() {
            throw null;
        }

        public PredicateFunction(Predicate predicate) {
            this.c = (Predicate) Preconditions.checkNotNull(predicate);
        }

        public boolean equals(Object obj) {
            if (obj instanceof PredicateFunction) {
                return this.c.equals(((PredicateFunction) obj).c);
            }
            return false;
        }

        public int hashCode() {
            return this.c.hashCode();
        }

        public String toString() {
            return "Functions.forPredicate(" + this.c + ")";
        }

        public Boolean apply(T t) {
            return Boolean.valueOf(this.c.apply(t));
        }
    }

    public static class SupplierFunction<T> implements Function<Object, T>, Serializable {
        private static final long serialVersionUID = 0;
        public final Supplier<T> c;

        public SupplierFunction() {
            throw null;
        }

        public SupplierFunction(Supplier supplier) {
            this.c = (Supplier) Preconditions.checkNotNull(supplier);
        }

        public T apply(Object obj) {
            return this.c.get();
        }

        public boolean equals(Object obj) {
            if (obj instanceof SupplierFunction) {
                return this.c.equals(((SupplierFunction) obj).c);
            }
            return false;
        }

        public int hashCode() {
            return this.c.hashCode();
        }

        public String toString() {
            return "Functions.forSupplier(" + this.c + ")";
        }
    }

    public enum ToStringFunction implements Function<Object, String> {
        ;

        /* access modifiers changed from: public */
        ToStringFunction() {
        }

        public String toString() {
            return "Functions.toStringFunction()";
        }

        public String apply(Object obj) {
            Preconditions.checkNotNull(obj);
            return obj.toString();
        }
    }

    public static <A, B, C> Function<A, C> compose(Function<B, C> function, Function<A, ? extends B> function2) {
        return new FunctionComposition(function, function2);
    }

    public static <E> Function<Object, E> constant(E e) {
        return new ConstantFunction(e);
    }

    public static <K, V> Function<K, V> forMap(Map<K, V> map) {
        return new FunctionForMapNoDefault(map);
    }

    public static <T> Function<T, Boolean> forPredicate(Predicate<T> predicate) {
        return new PredicateFunction(predicate);
    }

    public static <T> Function<Object, T> forSupplier(Supplier<T> supplier) {
        return new SupplierFunction(supplier);
    }

    public static <E> Function<E, E> identity() {
        return IdentityFunction.c;
    }

    public static Function<Object, String> toStringFunction() {
        return ToStringFunction.c;
    }

    public static <K, V> Function<K, V> forMap(Map<K, ? extends V> map, V v) {
        return new ForMapWithDefault(map, v);
    }
}
