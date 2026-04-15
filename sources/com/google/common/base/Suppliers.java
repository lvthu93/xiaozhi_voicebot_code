package com.google.common.base;

import com.google.common.base.Platform;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

public final class Suppliers {

    public static class ExpiringMemoizingSupplier<T> implements Supplier<T>, Serializable {
        private static final long serialVersionUID = 0;
        public final Supplier<T> c;
        public final long f;
        public volatile transient T g;
        public volatile transient long h;

        public ExpiringMemoizingSupplier(Supplier<T> supplier, long j, TimeUnit timeUnit) {
            boolean z;
            this.c = (Supplier) Preconditions.checkNotNull(supplier);
            this.f = timeUnit.toNanos(j);
            if (j > 0) {
                z = true;
            } else {
                z = false;
            }
            Preconditions.checkArgument(z, "duration (%s %s) must be > 0", j, (Object) timeUnit);
        }

        public T get() {
            long j = this.h;
            Platform.JdkPatternCompiler jdkPatternCompiler = Platform.a;
            long nanoTime = System.nanoTime();
            if (j == 0 || nanoTime - j >= 0) {
                synchronized (this) {
                    if (j == this.h) {
                        T t = this.c.get();
                        this.g = t;
                        long j2 = nanoTime + this.f;
                        if (j2 == 0) {
                            j2 = 1;
                        }
                        this.h = j2;
                        return t;
                    }
                }
            }
            return this.g;
        }

        public String toString() {
            return "Suppliers.memoizeWithExpiration(" + this.c + ", " + this.f + ", NANOS)";
        }
    }

    public static class MemoizingSupplier<T> implements Supplier<T>, Serializable {
        private static final long serialVersionUID = 0;
        public final Supplier<T> c;
        public volatile transient boolean f;
        public transient T g;

        public MemoizingSupplier(Supplier<T> supplier) {
            this.c = (Supplier) Preconditions.checkNotNull(supplier);
        }

        public T get() {
            if (!this.f) {
                synchronized (this) {
                    if (!this.f) {
                        T t = this.c.get();
                        this.g = t;
                        this.f = true;
                        return t;
                    }
                }
            }
            return this.g;
        }

        public String toString() {
            Object obj;
            StringBuilder sb = new StringBuilder("Suppliers.memoize(");
            if (this.f) {
                obj = "<supplier that returned " + this.g + ">";
            } else {
                obj = this.c;
            }
            sb.append(obj);
            sb.append(")");
            return sb.toString();
        }
    }

    public static class NonSerializableMemoizingSupplier<T> implements Supplier<T> {
        public volatile Supplier<T> c;
        public volatile boolean f;
        public T g;

        public NonSerializableMemoizingSupplier(Supplier<T> supplier) {
            this.c = (Supplier) Preconditions.checkNotNull(supplier);
        }

        public T get() {
            if (!this.f) {
                synchronized (this) {
                    if (!this.f) {
                        T t = this.c.get();
                        this.g = t;
                        this.f = true;
                        this.c = null;
                        return t;
                    }
                }
            }
            return this.g;
        }

        public String toString() {
            Object obj = this.c;
            StringBuilder sb = new StringBuilder("Suppliers.memoize(");
            if (obj == null) {
                obj = "<supplier that returned " + this.g + ">";
            }
            sb.append(obj);
            sb.append(")");
            return sb.toString();
        }
    }

    public static class SupplierComposition<F, T> implements Supplier<T>, Serializable {
        private static final long serialVersionUID = 0;
        public final Function<? super F, T> c;
        public final Supplier<F> f;

        public SupplierComposition(Function<? super F, T> function, Supplier<F> supplier) {
            this.c = (Function) Preconditions.checkNotNull(function);
            this.f = (Supplier) Preconditions.checkNotNull(supplier);
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof SupplierComposition)) {
                return false;
            }
            SupplierComposition supplierComposition = (SupplierComposition) obj;
            if (!this.c.equals(supplierComposition.c) || !this.f.equals(supplierComposition.f)) {
                return false;
            }
            return true;
        }

        public T get() {
            return this.c.apply(this.f.get());
        }

        public int hashCode() {
            return Objects.hashCode(this.c, this.f);
        }

        public String toString() {
            return "Suppliers.compose(" + this.c + ", " + this.f + ")";
        }
    }

    public interface SupplierFunction<T> extends Function<Supplier<T>, T> {
    }

    public enum SupplierFunctionImpl implements SupplierFunction<Object> {
        ;

        /* access modifiers changed from: public */
        SupplierFunctionImpl() {
        }

        public String toString() {
            return "Suppliers.supplierFunction()";
        }

        public Object apply(Supplier<Object> supplier) {
            return supplier.get();
        }
    }

    public static class SupplierOfInstance<T> implements Supplier<T>, Serializable {
        private static final long serialVersionUID = 0;
        public final T c;

        public SupplierOfInstance(T t) {
            this.c = t;
        }

        public boolean equals(Object obj) {
            if (obj instanceof SupplierOfInstance) {
                return Objects.equal(this.c, ((SupplierOfInstance) obj).c);
            }
            return false;
        }

        public T get() {
            return this.c;
        }

        public int hashCode() {
            return Objects.hashCode(this.c);
        }

        public String toString() {
            return "Suppliers.ofInstance(" + this.c + ")";
        }
    }

    public static class ThreadSafeSupplier<T> implements Supplier<T>, Serializable {
        private static final long serialVersionUID = 0;
        public final Supplier<T> c;

        public ThreadSafeSupplier(Supplier<T> supplier) {
            this.c = (Supplier) Preconditions.checkNotNull(supplier);
        }

        public T get() {
            T t;
            synchronized (this.c) {
                t = this.c.get();
            }
            return t;
        }

        public String toString() {
            return "Suppliers.synchronizedSupplier(" + this.c + ")";
        }
    }

    public static <F, T> Supplier<T> compose(Function<? super F, T> function, Supplier<F> supplier) {
        return new SupplierComposition(function, supplier);
    }

    public static <T> Supplier<T> memoize(Supplier<T> supplier) {
        if ((supplier instanceof NonSerializableMemoizingSupplier) || (supplier instanceof MemoizingSupplier)) {
            return supplier;
        }
        if (supplier instanceof Serializable) {
            return new MemoizingSupplier(supplier);
        }
        return new NonSerializableMemoizingSupplier(supplier);
    }

    public static <T> Supplier<T> memoizeWithExpiration(Supplier<T> supplier, long j, TimeUnit timeUnit) {
        return new ExpiringMemoizingSupplier(supplier, j, timeUnit);
    }

    public static <T> Supplier<T> ofInstance(T t) {
        return new SupplierOfInstance(t);
    }

    public static <T> Function<Supplier<T>, T> supplierFunction() {
        return SupplierFunctionImpl.c;
    }

    public static <T> Supplier<T> synchronizedSupplier(Supplier<T> supplier) {
        return new ThreadSafeSupplier(supplier);
    }
}
