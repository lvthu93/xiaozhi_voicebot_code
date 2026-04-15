package defpackage;

import java.lang.reflect.Field;
import java.nio.Buffer;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.Unsafe;

/* renamed from: fd  reason: default package */
public final class fd {
    public static final Unsafe a;
    public static final Class<?> b = bb.a;
    public static final e c;
    public static final boolean d;
    public static final boolean e;
    public static final long f = ((long) c(byte[].class));
    public static final long g;
    public static final boolean h;

    /* renamed from: fd$a */
    public class a implements PrivilegedExceptionAction<Unsafe> {
        public static Unsafe a() throws Exception {
            Class<Unsafe> cls = Unsafe.class;
            for (Field field : cls.getDeclaredFields()) {
                field.setAccessible(true);
                Object obj = field.get((Object) null);
                if (cls.isInstance(obj)) {
                    return cls.cast(obj);
                }
            }
            return null;
        }

        public final /* bridge */ /* synthetic */ Object run() throws Exception {
            return a();
        }
    }

    /* renamed from: fd$b */
    public static final class b extends e {
        public b(Unsafe unsafe) {
            super(unsafe);
        }

        public final void c(long j, byte[] bArr, long j2, long j3) {
            throw new UnsupportedOperationException();
        }

        public final boolean d(long j, Object obj) {
            if (fd.h) {
                if (fd.j(j, obj) != 0) {
                    return true;
                }
                return false;
            } else if (fd.k(j, obj) != 0) {
                return true;
            } else {
                return false;
            }
        }

        public final byte e(long j) {
            throw new UnsupportedOperationException();
        }

        public final byte f(long j, Object obj) {
            if (fd.h) {
                return fd.j(j, obj);
            }
            return fd.k(j, obj);
        }

        public final double g(long j, Object obj) {
            return Double.longBitsToDouble(j(j, obj));
        }

        public final float h(long j, Object obj) {
            return Float.intBitsToFloat(i(j, obj));
        }

        public final void m(Object obj, long j, boolean z) {
            if (fd.h) {
                fd.t(obj, j, z ? (byte) 1 : 0);
            } else {
                fd.u(obj, j, z ? (byte) 1 : 0);
            }
        }

        public final void n(Object obj, long j, byte b) {
            if (fd.h) {
                fd.t(obj, j, b);
            } else {
                fd.u(obj, j, b);
            }
        }

        public final void o(Object obj, long j, double d) {
            r(obj, j, Double.doubleToLongBits(d));
        }

        public final void p(Object obj, long j, float f) {
            q(Float.floatToIntBits(f), j, obj);
        }

        public final boolean u() {
            return false;
        }
    }

    /* renamed from: fd$c */
    public static final class c extends e {
        public c(Unsafe unsafe) {
            super(unsafe);
        }

        public final void c(long j, byte[] bArr, long j2, long j3) {
            throw new UnsupportedOperationException();
        }

        public final boolean d(long j, Object obj) {
            if (fd.h) {
                if (fd.j(j, obj) != 0) {
                    return true;
                }
                return false;
            } else if (fd.k(j, obj) != 0) {
                return true;
            } else {
                return false;
            }
        }

        public final byte e(long j) {
            throw new UnsupportedOperationException();
        }

        public final byte f(long j, Object obj) {
            if (fd.h) {
                return fd.j(j, obj);
            }
            return fd.k(j, obj);
        }

        public final double g(long j, Object obj) {
            return Double.longBitsToDouble(j(j, obj));
        }

        public final float h(long j, Object obj) {
            return Float.intBitsToFloat(i(j, obj));
        }

        public final void m(Object obj, long j, boolean z) {
            if (fd.h) {
                fd.t(obj, j, z ? (byte) 1 : 0);
            } else {
                fd.u(obj, j, z ? (byte) 1 : 0);
            }
        }

        public final void n(Object obj, long j, byte b) {
            if (fd.h) {
                fd.t(obj, j, b);
            } else {
                fd.u(obj, j, b);
            }
        }

        public final void o(Object obj, long j, double d) {
            r(obj, j, Double.doubleToLongBits(d));
        }

        public final void p(Object obj, long j, float f) {
            q(Float.floatToIntBits(f), j, obj);
        }

        public final boolean u() {
            return false;
        }
    }

    /* renamed from: fd$d */
    public static final class d extends e {
        public d(Unsafe unsafe) {
            super(unsafe);
        }

        public final void c(long j, byte[] bArr, long j2, long j3) {
            this.a.copyMemory((Object) null, j, bArr, fd.f + j2, j3);
        }

        public final boolean d(long j, Object obj) {
            return this.a.getBoolean(obj, j);
        }

        public final byte e(long j) {
            return this.a.getByte(j);
        }

        public final byte f(long j, Object obj) {
            return this.a.getByte(obj, j);
        }

        public final double g(long j, Object obj) {
            return this.a.getDouble(obj, j);
        }

        public final float h(long j, Object obj) {
            return this.a.getFloat(obj, j);
        }

        public final void m(Object obj, long j, boolean z) {
            this.a.putBoolean(obj, j, z);
        }

        public final void n(Object obj, long j, byte b) {
            this.a.putByte(obj, j, b);
        }

        public final void o(Object obj, long j, double d) {
            this.a.putDouble(obj, j, d);
        }

        public final void p(Object obj, long j, float f) {
            this.a.putFloat(obj, j, f);
        }

        public final boolean t() {
            Class<Object> cls = Object.class;
            if (!super.t()) {
                return false;
            }
            try {
                Class<?> cls2 = this.a.getClass();
                Class cls3 = Long.TYPE;
                cls2.getMethod("getByte", new Class[]{cls, cls3});
                cls2.getMethod("putByte", new Class[]{cls, cls3, Byte.TYPE});
                cls2.getMethod("getBoolean", new Class[]{cls, cls3});
                cls2.getMethod("putBoolean", new Class[]{cls, cls3, Boolean.TYPE});
                cls2.getMethod("getFloat", new Class[]{cls, cls3});
                cls2.getMethod("putFloat", new Class[]{cls, cls3, Float.TYPE});
                cls2.getMethod("getDouble", new Class[]{cls, cls3});
                cls2.getMethod("putDouble", new Class[]{cls, cls3, Double.TYPE});
                return true;
            } catch (Throwable th) {
                fd.a(th);
                return false;
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:10:0x0039 A[RETURN] */
        /* JADX WARNING: Removed duplicated region for block: B:11:0x003a A[SYNTHETIC, Splitter:B:11:0x003a] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final boolean u() {
            /*
                r11 = this;
                java.lang.String r0 = "copyMemory"
                java.lang.String r1 = "getLong"
                sun.misc.Unsafe r2 = r11.a
                java.lang.Class<java.lang.Object> r3 = java.lang.Object.class
                r4 = 2
                r5 = 1
                r6 = 0
                if (r2 != 0) goto L_0x000f
            L_0x000d:
                r7 = 0
                goto L_0x0037
            L_0x000f:
                java.lang.Class r7 = r2.getClass()     // Catch:{ all -> 0x0032 }
                java.lang.String r8 = "objectFieldOffset"
                java.lang.Class[] r9 = new java.lang.Class[r5]     // Catch:{ all -> 0x0032 }
                java.lang.Class<java.lang.reflect.Field> r10 = java.lang.reflect.Field.class
                r9[r6] = r10     // Catch:{ all -> 0x0032 }
                r7.getMethod(r8, r9)     // Catch:{ all -> 0x0032 }
                java.lang.Class[] r8 = new java.lang.Class[r4]     // Catch:{ all -> 0x0032 }
                r8[r6] = r3     // Catch:{ all -> 0x0032 }
                java.lang.Class r9 = java.lang.Long.TYPE     // Catch:{ all -> 0x0032 }
                r8[r5] = r9     // Catch:{ all -> 0x0032 }
                r7.getMethod(r1, r8)     // Catch:{ all -> 0x0032 }
                java.lang.reflect.Field r7 = defpackage.fd.e()     // Catch:{ all -> 0x0032 }
                if (r7 != 0) goto L_0x0030
                goto L_0x000d
            L_0x0030:
                r7 = 1
                goto L_0x0037
            L_0x0032:
                r7 = move-exception
                defpackage.fd.a(r7)
                goto L_0x000d
            L_0x0037:
                if (r7 != 0) goto L_0x003a
                return r6
            L_0x003a:
                java.lang.Class r2 = r2.getClass()     // Catch:{ all -> 0x009c }
                java.lang.String r7 = "getByte"
                java.lang.Class[] r8 = new java.lang.Class[r5]     // Catch:{ all -> 0x009c }
                java.lang.Class r9 = java.lang.Long.TYPE     // Catch:{ all -> 0x009c }
                r8[r6] = r9     // Catch:{ all -> 0x009c }
                r2.getMethod(r7, r8)     // Catch:{ all -> 0x009c }
                java.lang.String r7 = "putByte"
                java.lang.Class[] r8 = new java.lang.Class[r4]     // Catch:{ all -> 0x009c }
                r8[r6] = r9     // Catch:{ all -> 0x009c }
                java.lang.Class r10 = java.lang.Byte.TYPE     // Catch:{ all -> 0x009c }
                r8[r5] = r10     // Catch:{ all -> 0x009c }
                r2.getMethod(r7, r8)     // Catch:{ all -> 0x009c }
                java.lang.String r7 = "getInt"
                java.lang.Class[] r8 = new java.lang.Class[r5]     // Catch:{ all -> 0x009c }
                r8[r6] = r9     // Catch:{ all -> 0x009c }
                r2.getMethod(r7, r8)     // Catch:{ all -> 0x009c }
                java.lang.String r7 = "putInt"
                java.lang.Class[] r8 = new java.lang.Class[r4]     // Catch:{ all -> 0x009c }
                r8[r6] = r9     // Catch:{ all -> 0x009c }
                java.lang.Class r10 = java.lang.Integer.TYPE     // Catch:{ all -> 0x009c }
                r8[r5] = r10     // Catch:{ all -> 0x009c }
                r2.getMethod(r7, r8)     // Catch:{ all -> 0x009c }
                java.lang.Class[] r7 = new java.lang.Class[r5]     // Catch:{ all -> 0x009c }
                r7[r6] = r9     // Catch:{ all -> 0x009c }
                r2.getMethod(r1, r7)     // Catch:{ all -> 0x009c }
                java.lang.String r1 = "putLong"
                java.lang.Class[] r7 = new java.lang.Class[r4]     // Catch:{ all -> 0x009c }
                r7[r6] = r9     // Catch:{ all -> 0x009c }
                r7[r5] = r9     // Catch:{ all -> 0x009c }
                r2.getMethod(r1, r7)     // Catch:{ all -> 0x009c }
                r1 = 3
                java.lang.Class[] r7 = new java.lang.Class[r1]     // Catch:{ all -> 0x009c }
                r7[r6] = r9     // Catch:{ all -> 0x009c }
                r7[r5] = r9     // Catch:{ all -> 0x009c }
                r7[r4] = r9     // Catch:{ all -> 0x009c }
                r2.getMethod(r0, r7)     // Catch:{ all -> 0x009c }
                r7 = 5
                java.lang.Class[] r7 = new java.lang.Class[r7]     // Catch:{ all -> 0x009c }
                r7[r6] = r3     // Catch:{ all -> 0x009c }
                r7[r5] = r9     // Catch:{ all -> 0x009c }
                r7[r4] = r3     // Catch:{ all -> 0x009c }
                r7[r1] = r9     // Catch:{ all -> 0x009c }
                r1 = 4
                r7[r1] = r9     // Catch:{ all -> 0x009c }
                r2.getMethod(r0, r7)     // Catch:{ all -> 0x009c }
                return r5
            L_0x009c:
                r0 = move-exception
                defpackage.fd.a(r0)
                return r6
            */
            throw new UnsupportedOperationException("Method not decompiled: defpackage.fd.d.u():boolean");
        }
    }

    /* renamed from: fd$e */
    public static abstract class e {
        public final Unsafe a;

        public e(Unsafe unsafe) {
            this.a = unsafe;
        }

        public final int a(Class<?> cls) {
            return this.a.arrayBaseOffset(cls);
        }

        public final int b(Class<?> cls) {
            return this.a.arrayIndexScale(cls);
        }

        public abstract void c(long j, byte[] bArr, long j2, long j3);

        public abstract boolean d(long j, Object obj);

        public abstract byte e(long j);

        public abstract byte f(long j, Object obj);

        public abstract double g(long j, Object obj);

        public abstract float h(long j, Object obj);

        public final int i(long j, Object obj) {
            return this.a.getInt(obj, j);
        }

        public final long j(long j, Object obj) {
            return this.a.getLong(obj, j);
        }

        public final Object k(long j, Object obj) {
            return this.a.getObject(obj, j);
        }

        public final long l(Field field) {
            return this.a.objectFieldOffset(field);
        }

        public abstract void m(Object obj, long j, boolean z);

        public abstract void n(Object obj, long j, byte b);

        public abstract void o(Object obj, long j, double d);

        public abstract void p(Object obj, long j, float f);

        public final void q(int i, long j, Object obj) {
            this.a.putInt(obj, j, i);
        }

        public final void r(Object obj, long j, long j2) {
            this.a.putLong(obj, j, j2);
        }

        public final void s(long j, Object obj, Object obj2) {
            this.a.putObject(obj, j, obj2);
        }

        public boolean t() {
            Class<Class> cls = Class.class;
            Class<Object> cls2 = Object.class;
            Unsafe unsafe = this.a;
            if (unsafe == null) {
                return false;
            }
            try {
                Class<?> cls3 = unsafe.getClass();
                cls3.getMethod("objectFieldOffset", new Class[]{Field.class});
                cls3.getMethod("arrayBaseOffset", new Class[]{cls});
                cls3.getMethod("arrayIndexScale", new Class[]{cls});
                Class cls4 = Long.TYPE;
                cls3.getMethod("getInt", new Class[]{cls2, cls4});
                cls3.getMethod("putInt", new Class[]{cls2, cls4, Integer.TYPE});
                cls3.getMethod("getLong", new Class[]{cls2, cls4});
                cls3.getMethod("putLong", new Class[]{cls2, cls4, cls4});
                cls3.getMethod("getObject", new Class[]{cls2, cls4});
                cls3.getMethod("putObject", new Class[]{cls2, cls4, cls2});
                return true;
            } catch (Throwable th) {
                fd.a(th);
                return false;
            }
        }

        public abstract boolean u();
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x003b  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x003d  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0045  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0047  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x00a0  */
    static {
        /*
            sun.misc.Unsafe r0 = q()
            a = r0
            java.lang.Class<?> r1 = defpackage.bb.a
            b = r1
            java.lang.Class r1 = java.lang.Long.TYPE
            boolean r1 = f(r1)
            java.lang.Class r2 = java.lang.Integer.TYPE
            boolean r2 = f(r2)
            if (r0 != 0) goto L_0x0019
            goto L_0x002f
        L_0x0019:
            boolean r3 = defpackage.bb.a()
            if (r3 == 0) goto L_0x0031
            if (r1 == 0) goto L_0x0027
            fd$c r1 = new fd$c
            r1.<init>(r0)
            goto L_0x0036
        L_0x0027:
            if (r2 == 0) goto L_0x002f
            fd$b r1 = new fd$b
            r1.<init>(r0)
            goto L_0x0036
        L_0x002f:
            r1 = 0
            goto L_0x0036
        L_0x0031:
            fd$d r1 = new fd$d
            r1.<init>(r0)
        L_0x0036:
            c = r1
            r0 = 0
            if (r1 != 0) goto L_0x003d
            r2 = 0
            goto L_0x0041
        L_0x003d:
            boolean r2 = r1.u()
        L_0x0041:
            d = r2
            if (r1 != 0) goto L_0x0047
            r2 = 0
            goto L_0x004b
        L_0x0047:
            boolean r2 = r1.t()
        L_0x004b:
            e = r2
            java.lang.Class<byte[]> r2 = byte[].class
            int r2 = c(r2)
            long r2 = (long) r2
            f = r2
            java.lang.Class<boolean[]> r2 = boolean[].class
            c(r2)
            d(r2)
            java.lang.Class<int[]> r2 = int[].class
            c(r2)
            d(r2)
            java.lang.Class<long[]> r2 = long[].class
            c(r2)
            d(r2)
            java.lang.Class<float[]> r2 = float[].class
            c(r2)
            d(r2)
            java.lang.Class<double[]> r2 = double[].class
            c(r2)
            d(r2)
            java.lang.Class<java.lang.Object[]> r2 = java.lang.Object[].class
            c(r2)
            d(r2)
            java.lang.reflect.Field r2 = e()
            if (r2 == 0) goto L_0x0094
            if (r1 != 0) goto L_0x008f
            goto L_0x0094
        L_0x008f:
            long r1 = r1.l(r2)
            goto L_0x0096
        L_0x0094:
            r1 = -1
        L_0x0096:
            g = r1
            java.nio.ByteOrder r1 = java.nio.ByteOrder.nativeOrder()
            java.nio.ByteOrder r2 = java.nio.ByteOrder.BIG_ENDIAN
            if (r1 != r2) goto L_0x00a1
            r0 = 1
        L_0x00a1:
            h = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.fd.<clinit>():void");
    }

    public static void a(Throwable th) {
        Logger logger = Logger.getLogger(fd.class.getName());
        Level level = Level.WARNING;
        logger.log(level, "platform method missing - proto runtime falling back to safer methods: " + th);
    }

    public static <T> T b(Class<T> cls) {
        try {
            return a.allocateInstance(cls);
        } catch (InstantiationException e2) {
            throw new IllegalStateException(e2);
        }
    }

    public static int c(Class<?> cls) {
        if (e) {
            return c.a(cls);
        }
        return -1;
    }

    public static void d(Class cls) {
        if (e) {
            c.b(cls);
        }
    }

    public static Field e() {
        Field field;
        Field field2;
        Class<Buffer> cls = Buffer.class;
        if (bb.a()) {
            try {
                field2 = cls.getDeclaredField("effectiveDirectAddress");
            } catch (Throwable unused) {
                field2 = null;
            }
            if (field2 != null) {
                return field2;
            }
        }
        try {
            field = cls.getDeclaredField("address");
        } catch (Throwable unused2) {
            field = null;
        }
        if (field == null || field.getType() != Long.TYPE) {
            return null;
        }
        return field;
    }

    public static boolean f(Class<?> cls) {
        Class<byte[]> cls2 = byte[].class;
        if (!bb.a()) {
            return false;
        }
        try {
            Class<?> cls3 = b;
            Class cls4 = Boolean.TYPE;
            cls3.getMethod("peekLong", new Class[]{cls, cls4});
            cls3.getMethod("pokeLong", new Class[]{cls, Long.TYPE, cls4});
            Class cls5 = Integer.TYPE;
            cls3.getMethod("pokeInt", new Class[]{cls, cls5, cls4});
            cls3.getMethod("peekInt", new Class[]{cls, cls4});
            cls3.getMethod("pokeByte", new Class[]{cls, Byte.TYPE});
            cls3.getMethod("peekByte", new Class[]{cls});
            cls3.getMethod("pokeByteArray", new Class[]{cls, cls2, cls5, cls5});
            cls3.getMethod("peekByteArray", new Class[]{cls, cls2, cls5, cls5});
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    public static boolean g(long j, Object obj) {
        return c.d(j, obj);
    }

    public static byte h(long j) {
        return c.e(j);
    }

    public static byte i(long j, byte[] bArr) {
        return c.f(f + j, bArr);
    }

    public static byte j(long j, Object obj) {
        return (byte) ((n(-4 & j, obj) >>> ((int) (((~j) & 3) << 3))) & 255);
    }

    public static byte k(long j, Object obj) {
        return (byte) ((n(-4 & j, obj) >>> ((int) ((j & 3) << 3))) & 255);
    }

    public static double l(long j, Object obj) {
        return c.g(j, obj);
    }

    public static float m(long j, Object obj) {
        return c.h(j, obj);
    }

    public static int n(long j, Object obj) {
        return c.i(j, obj);
    }

    public static long o(long j, Object obj) {
        return c.j(j, obj);
    }

    public static Object p(long j, Object obj) {
        return c.k(j, obj);
    }

    public static Unsafe q() {
        try {
            return (Unsafe) AccessController.doPrivileged(new a());
        } catch (Throwable unused) {
            return null;
        }
    }

    public static void r(Object obj, long j, boolean z) {
        c.m(obj, j, z);
    }

    public static void s(byte[] bArr, long j, byte b2) {
        c.n(bArr, f + j, b2);
    }

    public static void t(Object obj, long j, byte b2) {
        long j2 = -4 & j;
        int n = n(j2, obj);
        int i = ((~((int) j)) & 3) << 3;
        x(((255 & b2) << i) | (n & (~(255 << i))), j2, obj);
    }

    public static void u(Object obj, long j, byte b2) {
        long j2 = -4 & j;
        int i = (((int) j) & 3) << 3;
        x(((255 & b2) << i) | (n(j2, obj) & (~(255 << i))), j2, obj);
    }

    public static void v(Object obj, long j, double d2) {
        c.o(obj, j, d2);
    }

    public static void w(Object obj, long j, float f2) {
        c.p(obj, j, f2);
    }

    public static void x(int i, long j, Object obj) {
        c.q(i, j, obj);
    }

    public static void y(Object obj, long j, long j2) {
        c.r(obj, j, j2);
    }

    public static void z(long j, Object obj, Object obj2) {
        c.s(j, obj, obj2);
    }
}
