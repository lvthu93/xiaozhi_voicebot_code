package j$.util.concurrent;

import androidx.appcompat.widget.ActivityChooserView;
import j$.util.stream.C0121j0;
import j$.util.stream.C0170t0;
import j$.util.stream.D0;
import j$.util.stream.H;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.security.AccessController;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public final class A extends Random {
    private static final ThreadLocal d = new ThreadLocal();
    private static final AtomicInteger e = new AtomicInteger();
    private static final ThreadLocal f = new v();
    private static final AtomicLong g = new AtomicLong(i(System.currentTimeMillis()) ^ i(System.nanoTime()));
    private static final ObjectStreamField[] serialPersistentFields = {new ObjectStreamField("rnd", Long.TYPE), new ObjectStreamField("initialized", Boolean.TYPE)};
    private static final long serialVersionUID = -5851777807851030925L;
    long a;
    int b;
    boolean c;

    static {
        if (((Boolean) AccessController.doPrivileged(new w())).booleanValue()) {
            byte[] seed = SecureRandom.getSeed(8);
            long j = ((long) seed[0]) & 255;
            for (int i = 1; i < 8; i++) {
                j = (j << 8) | (((long) seed[i]) & 255);
            }
            g.set(j);
        }
    }

    private A() {
        this.c = true;
    }

    /* synthetic */ A(int i) {
        this();
    }

    static final int a(int i) {
        int i2 = i ^ (i << 13);
        int i3 = i2 ^ (i2 >>> 17);
        int i4 = i3 ^ (i3 << 5);
        ((A) f.get()).b = i4;
        return i4;
    }

    public static A b() {
        A a2 = (A) f.get();
        if (a2.b == 0) {
            g();
        }
        return a2;
    }

    static final int c() {
        return ((A) f.get()).b;
    }

    static final void g() {
        int addAndGet = e.addAndGet(-1640531527);
        if (addAndGet == 0) {
            addAndGet = 1;
        }
        long i = i(g.getAndAdd(-4942790177534073029L));
        A a2 = (A) f.get();
        a2.a = i;
        a2.b = addAndGet;
    }

    private static int h(long j) {
        long j2 = (j ^ (j >>> 33)) * -49064778989728563L;
        return (int) (((j2 ^ (j2 >>> 33)) * -4265267296055464877L) >>> 32);
    }

    private static long i(long j) {
        long j2 = (j ^ (j >>> 33)) * -49064778989728563L;
        long j3 = (j2 ^ (j2 >>> 33)) * -4265267296055464877L;
        return j3 ^ (j3 >>> 33);
    }

    private Object readResolve() {
        return b();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) {
        ObjectOutputStream.PutField putFields = objectOutputStream.putFields();
        putFields.put("rnd", this.a);
        putFields.put("initialized", true);
        objectOutputStream.writeFields();
    }

    /* access modifiers changed from: package-private */
    public final double d(double d2, double d3) {
        double nextLong = ((double) (nextLong() >>> 11)) * 1.1102230246251565E-16d;
        if (d2 >= d3) {
            return nextLong;
        }
        double d4 = ((d3 - d2) * nextLong) + d2;
        return d4 >= d3 ? Double.longBitsToDouble(Double.doubleToLongBits(d3) - 1) : d4;
    }

    public final DoubleStream doubles() {
        return H.k(D0.i0(new x(0, Long.MAX_VALUE, Double.MAX_VALUE, 0.0d)));
    }

    public final DoubleStream doubles(double d2, double d3) {
        if (d2 < d3) {
            return H.k(D0.i0(new x(0, Long.MAX_VALUE, d2, d3)));
        }
        throw new IllegalArgumentException("bound must be greater than origin");
    }

    public final DoubleStream doubles(long j) {
        if (j >= 0) {
            return H.k(D0.i0(new x(0, j, Double.MAX_VALUE, 0.0d)));
        }
        throw new IllegalArgumentException("size must be non-negative");
    }

    public final DoubleStream doubles(long j, double d2, double d3) {
        if (j < 0) {
            throw new IllegalArgumentException("size must be non-negative");
        } else if (d2 < d3) {
            return H.k(D0.i0(new x(0, j, d2, d3)));
        } else {
            throw new IllegalArgumentException("bound must be greater than origin");
        }
    }

    /* access modifiers changed from: package-private */
    public final int e(int i, int i2) {
        int i3;
        int h = h(j());
        if (i >= i2) {
            return h;
        }
        int i4 = i2 - i;
        int i5 = i4 - 1;
        if ((i4 & i5) == 0) {
            i3 = h & i5;
        } else if (i4 > 0) {
            int i6 = h >>> 1;
            while (true) {
                int i7 = i6 + i5;
                i3 = i6 % i4;
                if (i7 - i3 >= 0) {
                    break;
                }
                i6 = h(j()) >>> 1;
            }
        } else {
            while (true) {
                if (h >= i && h < i2) {
                    return h;
                }
                h = h(j());
            }
        }
        return i3 + i;
    }

    /* access modifiers changed from: package-private */
    public final long f(long j, long j2) {
        long i = i(j());
        if (j >= j2) {
            return i;
        }
        long j3 = j2 - j;
        long j4 = j3 - 1;
        if ((j3 & j4) == 0) {
            return (i & j4) + j;
        }
        if (j3 > 0) {
            while (true) {
                long j5 = i >>> 1;
                long j6 = j5 + j4;
                long j7 = j5 % j3;
                if (j6 - j7 >= 0) {
                    return j7 + j;
                }
                i = i(j());
            }
        } else {
            while (true) {
                if (i >= j && i < j2) {
                    return i;
                }
                i = i(j());
            }
        }
    }

    public final IntStream ints() {
        return C0121j0.k(D0.u0(new y(0, Long.MAX_VALUE, ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, 0)));
    }

    public final IntStream ints(int i, int i2) {
        if (i < i2) {
            return C0121j0.k(D0.u0(new y(0, Long.MAX_VALUE, i, i2)));
        }
        throw new IllegalArgumentException("bound must be greater than origin");
    }

    public final IntStream ints(long j) {
        if (j >= 0) {
            return C0121j0.k(D0.u0(new y(0, j, ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, 0)));
        }
        throw new IllegalArgumentException("size must be non-negative");
    }

    public final IntStream ints(long j, int i, int i2) {
        if (j < 0) {
            throw new IllegalArgumentException("size must be non-negative");
        } else if (i < i2) {
            return C0121j0.k(D0.u0(new y(0, j, i, i2)));
        } else {
            throw new IllegalArgumentException("bound must be greater than origin");
        }
    }

    /* access modifiers changed from: package-private */
    public final long j() {
        long j = this.a - 7046029254386353131L;
        this.a = j;
        return j;
    }

    public final LongStream longs() {
        return C0170t0.k(D0.w0(new z(0, Long.MAX_VALUE, Long.MAX_VALUE, 0)));
    }

    public final LongStream longs(long j) {
        if (j >= 0) {
            return C0170t0.k(D0.w0(new z(0, j, Long.MAX_VALUE, 0)));
        }
        throw new IllegalArgumentException("size must be non-negative");
    }

    public final LongStream longs(long j, long j2) {
        if (j < j2) {
            return C0170t0.k(D0.w0(new z(0, Long.MAX_VALUE, j, j2)));
        }
        throw new IllegalArgumentException("bound must be greater than origin");
    }

    public final LongStream longs(long j, long j2, long j3) {
        if (j < 0) {
            throw new IllegalArgumentException("size must be non-negative");
        } else if (j2 < j3) {
            return C0170t0.k(D0.w0(new z(0, j, j2, j3)));
        } else {
            throw new IllegalArgumentException("bound must be greater than origin");
        }
    }

    /* access modifiers changed from: protected */
    public final int next(int i) {
        return nextInt() >>> (32 - i);
    }

    public final boolean nextBoolean() {
        return h(j()) < 0;
    }

    public final double nextDouble() {
        return ((double) (i(j()) >>> 11)) * 1.1102230246251565E-16d;
    }

    public final float nextFloat() {
        return ((float) (h(j()) >>> 8)) * 5.9604645E-8f;
    }

    public final double nextGaussian() {
        ThreadLocal threadLocal = d;
        Double d2 = (Double) threadLocal.get();
        if (d2 != null) {
            threadLocal.set((Object) null);
            return d2.doubleValue();
        }
        while (true) {
            double nextDouble = (nextDouble() * 2.0d) - 1.0d;
            double nextDouble2 = (nextDouble() * 2.0d) - 1.0d;
            double d3 = (nextDouble2 * nextDouble2) + (nextDouble * nextDouble);
            if (d3 < 1.0d && d3 != 0.0d) {
                double sqrt = StrictMath.sqrt((StrictMath.log(d3) * -2.0d) / d3);
                threadLocal.set(Double.valueOf(nextDouble2 * sqrt));
                return nextDouble * sqrt;
            }
        }
    }

    public final int nextInt() {
        return h(j());
    }

    public final int nextInt(int i) {
        if (i > 0) {
            int h = h(j());
            int i2 = i - 1;
            if ((i & i2) == 0) {
                return h & i2;
            }
            while (true) {
                int i3 = h >>> 1;
                int i4 = i3 + i2;
                int i5 = i3 % i;
                if (i4 - i5 >= 0) {
                    return i5;
                }
                h = h(j());
            }
        } else {
            throw new IllegalArgumentException("bound must be positive");
        }
    }

    public final long nextLong() {
        return i(j());
    }

    public final void setSeed(long j) {
        if (this.c) {
            throw new UnsupportedOperationException();
        }
    }
}
