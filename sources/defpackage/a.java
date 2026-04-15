package defpackage;

import com.google.common.base.Preconditions;
import java.lang.reflect.Field;
import java.security.PrivilegedExceptionAction;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.locks.LockSupport;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.Unsafe;

/* renamed from: a  reason: default package */
public abstract class a<V> extends co implements i4<V> {
    public static final boolean h = Boolean.parseBoolean(System.getProperty("guava.concurrent.generate_cancellation_cause", "false"));
    public static final Logger i;
    public static final C0000a j;
    public static final Object k = new Object();
    public volatile Object c;
    public volatile d f;
    public volatile j g;

    /* renamed from: a$a  reason: collision with other inner class name */
    public static abstract class C0000a {
        public abstract boolean a(a aVar, d dVar);

        public abstract boolean b(a<?> aVar, Object obj, Object obj2);

        public abstract boolean c(a<?> aVar, j jVar, j jVar2);

        public abstract void d(j jVar, j jVar2);

        public abstract void e(j jVar, Thread thread);
    }

    /* renamed from: a$b */
    public static final class b {
        public static final b b;
        public static final b c;
        public final Throwable a;

        static {
            if (a.h) {
                c = null;
                b = null;
                return;
            }
            c = new b(false, (Throwable) null);
            b = new b(true, (Throwable) null);
        }

        public b(boolean z, Throwable th) {
            this.a = th;
        }
    }

    /* renamed from: a$c */
    public static final class c {
        public final Throwable a;

        /* renamed from: a$c$a  reason: collision with other inner class name */
        public static class C0001a extends Throwable {
            public C0001a() {
                super("Failure occurred while trying to finish a future.");
            }

            public final synchronized Throwable fillInStackTrace() {
                return this;
            }
        }

        static {
            new c(new C0001a());
        }

        public c(Throwable th) {
            this.a = (Throwable) Preconditions.checkNotNull(th);
        }
    }

    /* renamed from: a$d */
    public static final class d {
        public static final d d = new d();
        public final Runnable a = null;
        public final Executor b = null;
        public d c;
    }

    /* renamed from: a$e */
    public static final class e extends C0000a {
        public final AtomicReferenceFieldUpdater<j, Thread> a;
        public final AtomicReferenceFieldUpdater<j, j> b;
        public final AtomicReferenceFieldUpdater<a, j> c;
        public final AtomicReferenceFieldUpdater<a, d> d;
        public final AtomicReferenceFieldUpdater<a, Object> e;

        public e(AtomicReferenceFieldUpdater<j, Thread> atomicReferenceFieldUpdater, AtomicReferenceFieldUpdater<j, j> atomicReferenceFieldUpdater2, AtomicReferenceFieldUpdater<a, j> atomicReferenceFieldUpdater3, AtomicReferenceFieldUpdater<a, d> atomicReferenceFieldUpdater4, AtomicReferenceFieldUpdater<a, Object> atomicReferenceFieldUpdater5) {
            this.a = atomicReferenceFieldUpdater;
            this.b = atomicReferenceFieldUpdater2;
            this.c = atomicReferenceFieldUpdater3;
            this.d = atomicReferenceFieldUpdater4;
            this.e = atomicReferenceFieldUpdater5;
        }

        public final boolean a(a aVar, d dVar) {
            AtomicReferenceFieldUpdater<a, d> atomicReferenceFieldUpdater;
            d dVar2 = d.d;
            do {
                atomicReferenceFieldUpdater = this.d;
                if (atomicReferenceFieldUpdater.compareAndSet(aVar, dVar, dVar2)) {
                    return true;
                }
            } while (atomicReferenceFieldUpdater.get(aVar) == dVar);
            return false;
        }

        public final boolean b(a<?> aVar, Object obj, Object obj2) {
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater;
            do {
                atomicReferenceFieldUpdater = this.e;
                if (atomicReferenceFieldUpdater.compareAndSet(aVar, obj, obj2)) {
                    return true;
                }
            } while (atomicReferenceFieldUpdater.get(aVar) == obj);
            return false;
        }

        public final boolean c(a<?> aVar, j jVar, j jVar2) {
            AtomicReferenceFieldUpdater<a, j> atomicReferenceFieldUpdater;
            do {
                atomicReferenceFieldUpdater = this.c;
                if (atomicReferenceFieldUpdater.compareAndSet(aVar, jVar, jVar2)) {
                    return true;
                }
            } while (atomicReferenceFieldUpdater.get(aVar) == jVar);
            return false;
        }

        public final void d(j jVar, j jVar2) {
            this.b.lazySet(jVar, jVar2);
        }

        public final void e(j jVar, Thread thread) {
            this.a.lazySet(jVar, thread);
        }
    }

    /* renamed from: a$f */
    public static final class f<V> implements Runnable {
        public final void run() {
            throw null;
        }
    }

    /* renamed from: a$g */
    public static final class g extends C0000a {
        public final boolean a(a aVar, d dVar) {
            d dVar2 = d.d;
            synchronized (aVar) {
                if (aVar.f != dVar) {
                    return false;
                }
                aVar.f = dVar2;
                return true;
            }
        }

        public final boolean b(a<?> aVar, Object obj, Object obj2) {
            synchronized (aVar) {
                if (aVar.c != obj) {
                    return false;
                }
                aVar.c = obj2;
                return true;
            }
        }

        public final boolean c(a<?> aVar, j jVar, j jVar2) {
            synchronized (aVar) {
                if (aVar.g != jVar) {
                    return false;
                }
                aVar.g = jVar2;
                return true;
            }
        }

        public final void d(j jVar, j jVar2) {
            jVar.b = jVar2;
        }

        public final void e(j jVar, Thread thread) {
            jVar.a = thread;
        }
    }

    /* renamed from: a$h */
    public static abstract class h<V> extends a<V> {
        public final boolean cancel(boolean z) {
            return a.super.cancel(z);
        }

        public final V get() throws InterruptedException, ExecutionException {
            return a.super.get();
        }

        public final boolean isCancelled() {
            return this.c instanceof b;
        }

        public final boolean isDone() {
            return a.super.isDone();
        }

        public final V get(long j, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
            return a.super.get(j, timeUnit);
        }
    }

    /* renamed from: a$i */
    public static final class i extends C0000a {
        public static final Unsafe a;
        public static final long b;
        public static final long c;
        public static final long d;
        public static final long e;
        public static final long f;

        /* renamed from: a$i$a  reason: collision with other inner class name */
        public static class C0002a implements PrivilegedExceptionAction<Unsafe> {
            public static Unsafe a() throws Exception {
                Class<Unsafe> cls = Unsafe.class;
                for (Field field : cls.getDeclaredFields()) {
                    field.setAccessible(true);
                    Object obj = field.get((Object) null);
                    if (cls.isInstance(obj)) {
                        return cls.cast(obj);
                    }
                }
                throw new NoSuchFieldError("the Unsafe");
            }

            public final /* bridge */ /* synthetic */ Object run() throws Exception {
                return a();
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:12:0x005d, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x0069, code lost:
            throw new java.lang.RuntimeException("Could not initialize intrinsics", r0.getCause());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:4:?, code lost:
            r1 = (sun.misc.Unsafe) java.security.AccessController.doPrivileged(new defpackage.a.i.C0002a());
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0007 */
        static {
            /*
                java.lang.Class<a$j> r0 = defpackage.a.j.class
                sun.misc.Unsafe r1 = sun.misc.Unsafe.getUnsafe()     // Catch:{ SecurityException -> 0x0007 }
                goto L_0x0012
            L_0x0007:
                a$i$a r1 = new a$i$a     // Catch:{ PrivilegedActionException -> 0x005d }
                r1.<init>()     // Catch:{ PrivilegedActionException -> 0x005d }
                java.lang.Object r1 = java.security.AccessController.doPrivileged(r1)     // Catch:{ PrivilegedActionException -> 0x005d }
                sun.misc.Unsafe r1 = (sun.misc.Unsafe) r1     // Catch:{ PrivilegedActionException -> 0x005d }
            L_0x0012:
                java.lang.Class<a> r2 = defpackage.a.class
                java.lang.String r3 = "g"
                java.lang.reflect.Field r3 = r2.getDeclaredField(r3)     // Catch:{ Exception -> 0x0053 }
                long r3 = r1.objectFieldOffset(r3)     // Catch:{ Exception -> 0x0053 }
                c = r3     // Catch:{ Exception -> 0x0053 }
                java.lang.String r3 = "f"
                java.lang.reflect.Field r3 = r2.getDeclaredField(r3)     // Catch:{ Exception -> 0x0053 }
                long r3 = r1.objectFieldOffset(r3)     // Catch:{ Exception -> 0x0053 }
                b = r3     // Catch:{ Exception -> 0x0053 }
                java.lang.String r3 = "c"
                java.lang.reflect.Field r2 = r2.getDeclaredField(r3)     // Catch:{ Exception -> 0x0053 }
                long r2 = r1.objectFieldOffset(r2)     // Catch:{ Exception -> 0x0053 }
                d = r2     // Catch:{ Exception -> 0x0053 }
                java.lang.String r2 = "a"
                java.lang.reflect.Field r2 = r0.getDeclaredField(r2)     // Catch:{ Exception -> 0x0053 }
                long r2 = r1.objectFieldOffset(r2)     // Catch:{ Exception -> 0x0053 }
                e = r2     // Catch:{ Exception -> 0x0053 }
                java.lang.String r2 = "b"
                java.lang.reflect.Field r0 = r0.getDeclaredField(r2)     // Catch:{ Exception -> 0x0053 }
                long r2 = r1.objectFieldOffset(r0)     // Catch:{ Exception -> 0x0053 }
                f = r2     // Catch:{ Exception -> 0x0053 }
                a = r1     // Catch:{ Exception -> 0x0053 }
                return
            L_0x0053:
                r0 = move-exception
                com.google.common.base.Throwables.throwIfUnchecked(r0)
                java.lang.RuntimeException r1 = new java.lang.RuntimeException
                r1.<init>(r0)
                throw r1
            L_0x005d:
                r0 = move-exception
                java.lang.RuntimeException r1 = new java.lang.RuntimeException
                java.lang.String r2 = "Could not initialize intrinsics"
                java.lang.Throwable r0 = r0.getCause()
                r1.<init>(r2, r0)
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: defpackage.a.i.<clinit>():void");
        }

        public final boolean a(a aVar, d dVar) {
            return b.a(a, aVar, b, dVar);
        }

        public final boolean b(a<?> aVar, Object obj, Object obj2) {
            return b.b(a, aVar, d, obj, obj2);
        }

        public final boolean c(a<?> aVar, j jVar, j jVar2) {
            return b.b(a, aVar, c, jVar, jVar2);
        }

        public final void d(j jVar, j jVar2) {
            a.putObject(jVar, f, jVar2);
        }

        public final void e(j jVar, Thread thread) {
            a.putObject(jVar, e, thread);
        }
    }

    /* renamed from: a$j */
    public static final class j {
        public static final j c = new j(0);
        public volatile Thread a;
        public volatile j b;

        public j() {
            a.j.e(this, Thread.currentThread());
        }

        public j(int i) {
        }
    }

    static {
        C0000a aVar;
        Class<j> cls = j.class;
        Class<a> cls2 = a.class;
        i = Logger.getLogger(cls2.getName());
        Throwable th = null;
        try {
            aVar = new i();
            th = null;
        } catch (Throwable th2) {
            th = th2;
            aVar = new g();
        }
        j = aVar;
        if (th != null) {
            Logger logger = i;
            Level level = Level.SEVERE;
            logger.log(level, "UnsafeAtomicHelper is broken!", th);
            logger.log(level, "SafeAtomicHelper is broken!", th);
        }
    }

    public static void c(a<?> aVar) {
        j jVar;
        d dVar;
        do {
            jVar = aVar.g;
        } while (!j.c(aVar, jVar, j.c));
        while (jVar != null) {
            Thread thread = jVar.a;
            if (thread != null) {
                jVar.a = null;
                LockSupport.unpark(thread);
            }
            jVar = jVar.b;
        }
        do {
            dVar = aVar.f;
        } while (!j.a(aVar, dVar));
        d dVar2 = null;
        while (dVar != null) {
            d dVar3 = dVar.c;
            dVar.c = dVar2;
            dVar2 = dVar;
            dVar = dVar3;
        }
        while (dVar2 != null) {
            d dVar4 = dVar2.c;
            Runnable runnable = dVar2.a;
            if (!(runnable instanceof f)) {
                Executor executor = dVar2.b;
                try {
                    executor.execute(runnable);
                } catch (RuntimeException e2) {
                    i.log(Level.SEVERE, "RuntimeException while executing runnable " + runnable + " with executor " + executor, e2);
                }
                dVar2 = dVar4;
            } else {
                ((f) runnable).getClass();
                throw null;
            }
        }
    }

    public static Object d(Object obj) throws ExecutionException {
        if (obj instanceof b) {
            Throwable th = ((b) obj).a;
            CancellationException cancellationException = new CancellationException("Task was cancelled.");
            cancellationException.initCause(th);
            throw cancellationException;
        } else if (obj instanceof c) {
            throw new ExecutionException(((c) obj).a);
        } else if (obj == k) {
            return null;
        } else {
            return obj;
        }
    }

    public final void b(StringBuilder sb) {
        Object obj;
        String str;
        boolean z = false;
        while (true) {
            try {
                obj = get();
                break;
            } catch (InterruptedException unused) {
                z = true;
            } catch (ExecutionException e2) {
                sb.append("FAILURE, cause=[");
                sb.append(e2.getCause());
                sb.append("]");
                return;
            } catch (CancellationException unused2) {
                sb.append("CANCELLED");
                return;
            } catch (RuntimeException e3) {
                sb.append("UNKNOWN, cause=[");
                sb.append(e3.getClass());
                sb.append(" thrown from get()]");
                return;
            } catch (Throwable th) {
                if (z) {
                    Thread.currentThread().interrupt();
                }
                throw th;
            }
        }
        if (z) {
            Thread.currentThread().interrupt();
        }
        sb.append("SUCCESS, result=[");
        if (obj == this) {
            str = "this future";
        } else {
            str = String.valueOf(obj);
        }
        sb.append(str);
        sb.append("]");
    }

    public boolean cancel(boolean z) {
        boolean z2;
        b bVar;
        Object obj = this.c;
        if (obj == null) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (!z2 && !(obj instanceof f)) {
            return false;
        }
        if (h) {
            bVar = new b(z, new CancellationException("Future.cancel() was called."));
        } else if (z) {
            bVar = b.b;
        } else {
            bVar = b.c;
        }
        while (!j.b(this, obj, bVar)) {
            obj = this.c;
            if (!(obj instanceof f)) {
            }
        }
        c(this);
        if (!(obj instanceof f)) {
            return true;
        }
        ((f) obj).getClass();
        throw null;
        return false;
    }

    public final String e() {
        Object obj = this.c;
        if (obj instanceof f) {
            StringBuilder sb = new StringBuilder("setFuture=[");
            ((f) obj).getClass();
            sb.append("null");
            sb.append("]");
            return sb.toString();
        } else if (!(this instanceof ScheduledFuture)) {
            return null;
        } else {
            return "remaining delay=[" + ((ScheduledFuture) this).getDelay(TimeUnit.MILLISECONDS) + " ms]";
        }
    }

    public final void f(j jVar) {
        jVar.a = null;
        while (true) {
            j jVar2 = this.g;
            if (jVar2 != j.c) {
                j jVar3 = null;
                while (jVar2 != null) {
                    j jVar4 = jVar2.b;
                    if (jVar2.a != null) {
                        jVar3 = jVar2;
                    } else if (jVar3 != null) {
                        jVar3.b = jVar4;
                        if (jVar3.a == null) {
                        }
                    } else if (!j.c(this, jVar2, jVar4)) {
                    }
                    jVar2 = jVar4;
                }
                return;
            }
            return;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:43:0x0095  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00bc  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public V get(long r18, java.util.concurrent.TimeUnit r20) throws java.lang.InterruptedException, java.util.concurrent.TimeoutException, java.util.concurrent.ExecutionException {
        /*
            r17 = this;
            r0 = r17
            r1 = r18
            r3 = r20
            long r4 = r3.toNanos(r1)
            boolean r6 = java.lang.Thread.interrupted()
            if (r6 != 0) goto L_0x017b
            java.lang.Object r6 = r0.c
            r7 = 1
            if (r6 == 0) goto L_0x0017
            r8 = 1
            goto L_0x0018
        L_0x0017:
            r8 = 0
        L_0x0018:
            boolean r9 = r6 instanceof defpackage.a.f
            r9 = r9 ^ r7
            r8 = r8 & r9
            if (r8 == 0) goto L_0x0023
            java.lang.Object r1 = d(r6)
            return r1
        L_0x0023:
            r8 = 0
            int r6 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r6 <= 0) goto L_0x002f
            long r10 = java.lang.System.nanoTime()
            long r10 = r10 + r4
            goto L_0x0030
        L_0x002f:
            r10 = r8
        L_0x0030:
            r12 = 1000(0x3e8, double:4.94E-321)
            int r6 = (r4 > r12 ? 1 : (r4 == r12 ? 0 : -1))
            if (r6 < 0) goto L_0x008d
            a$j r6 = r0.g
            a$j r8 = defpackage.a.j.c
            if (r6 == r8) goto L_0x0086
            a$j r9 = new a$j
            r9.<init>()
        L_0x0041:
            a$a r14 = j
            r14.d(r9, r6)
            boolean r6 = r14.c(r0, r6, r9)
            if (r6 == 0) goto L_0x0082
        L_0x004c:
            java.util.concurrent.locks.LockSupport.parkNanos(r0, r4)
            boolean r4 = java.lang.Thread.interrupted()
            if (r4 != 0) goto L_0x0079
            java.lang.Object r4 = r0.c
            if (r4 == 0) goto L_0x005b
            r5 = 1
            goto L_0x005c
        L_0x005b:
            r5 = 0
        L_0x005c:
            boolean r6 = r4 instanceof defpackage.a.f
            r6 = r6 ^ r7
            r5 = r5 & r6
            if (r5 == 0) goto L_0x0067
            java.lang.Object r1 = d(r4)
            return r1
        L_0x0067:
            long r4 = java.lang.System.nanoTime()
            long r4 = r10 - r4
            int r6 = (r4 > r12 ? 1 : (r4 == r12 ? 0 : -1))
            if (r6 >= 0) goto L_0x004c
            r0.f(r9)
            r14 = r0
            r15 = r14
            r5 = r4
            r4 = r3
            goto L_0x00b3
        L_0x0079:
            r0.f(r9)
            java.lang.InterruptedException r1 = new java.lang.InterruptedException
            r1.<init>()
            throw r1
        L_0x0082:
            a$j r6 = r0.g
            if (r6 != r8) goto L_0x0041
        L_0x0086:
            java.lang.Object r1 = r0.c
            java.lang.Object r1 = d(r1)
            return r1
        L_0x008d:
            r14 = r0
            r15 = r14
            r5 = r4
            r4 = r3
        L_0x0091:
            int r16 = (r5 > r8 ? 1 : (r5 == r8 ? 0 : -1))
            if (r16 <= 0) goto L_0x00bc
            java.lang.Object r5 = r15.c
            if (r5 == 0) goto L_0x009b
            r6 = 1
            goto L_0x009c
        L_0x009b:
            r6 = 0
        L_0x009c:
            boolean r8 = r5 instanceof defpackage.a.f
            r8 = r8 ^ r7
            r6 = r6 & r8
            if (r6 == 0) goto L_0x00a7
            java.lang.Object r1 = d(r5)
            return r1
        L_0x00a7:
            boolean r5 = java.lang.Thread.interrupted()
            if (r5 != 0) goto L_0x00b6
            long r5 = java.lang.System.nanoTime()
            long r5 = r10 - r5
        L_0x00b3:
            r8 = 0
            goto L_0x0091
        L_0x00b6:
            java.lang.InterruptedException r1 = new java.lang.InterruptedException
            r1.<init>()
            throw r1
        L_0x00bc:
            java.lang.String r7 = r14.toString()
            java.lang.String r8 = r3.toString()
            java.util.Locale r9 = java.util.Locale.ROOT
            java.lang.String r8 = r8.toLowerCase(r9)
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            java.lang.String r11 = "Waited "
            r10.<init>(r11)
            r10.append(r1)
            java.lang.String r1 = " "
            r10.append(r1)
            java.lang.String r2 = r3.toString()
            java.lang.String r2 = r2.toLowerCase(r9)
            r10.append(r2)
            java.lang.String r2 = r10.toString()
            long r9 = r5 + r12
            r15 = 0
            int r3 = (r9 > r15 ? 1 : (r9 == r15 ? 0 : -1))
            if (r3 >= 0) goto L_0x014f
            java.lang.String r3 = " (plus "
            java.lang.String r2 = defpackage.y2.x(r2, r3)
            long r5 = -r5
            java.util.concurrent.TimeUnit r3 = java.util.concurrent.TimeUnit.NANOSECONDS
            long r9 = r4.convert(r5, r3)
            long r3 = r4.toNanos(r9)
            long r5 = r5 - r3
            r3 = 0
            int r11 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1))
            if (r11 == 0) goto L_0x010f
            int r3 = (r5 > r12 ? 1 : (r5 == r12 ? 0 : -1))
            if (r3 <= 0) goto L_0x010d
            goto L_0x010f
        L_0x010d:
            r3 = 0
            goto L_0x0110
        L_0x010f:
            r3 = 1
        L_0x0110:
            if (r11 <= 0) goto L_0x0133
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r2)
            r4.append(r9)
            r4.append(r1)
            r4.append(r8)
            java.lang.String r2 = r4.toString()
            if (r3 == 0) goto L_0x012f
            java.lang.String r4 = ","
            java.lang.String r2 = defpackage.y2.x(r2, r4)
        L_0x012f:
            java.lang.String r2 = defpackage.y2.x(r2, r1)
        L_0x0133:
            if (r3 == 0) goto L_0x0149
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r2)
            r1.append(r5)
            java.lang.String r2 = " nanoseconds "
            r1.append(r2)
            java.lang.String r2 = r1.toString()
        L_0x0149:
            java.lang.String r1 = "delay)"
            java.lang.String r2 = defpackage.y2.x(r2, r1)
        L_0x014f:
            boolean r1 = r14.isDone()
            if (r1 == 0) goto L_0x0161
            java.util.concurrent.TimeoutException r1 = new java.util.concurrent.TimeoutException
            java.lang.String r3 = " but future completed as timeout expired"
            java.lang.String r2 = defpackage.y2.x(r2, r3)
            r1.<init>(r2)
            throw r1
        L_0x0161:
            java.util.concurrent.TimeoutException r1 = new java.util.concurrent.TimeoutException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r2)
            java.lang.String r2 = " for "
            r3.append(r2)
            r3.append(r7)
            java.lang.String r2 = r3.toString()
            r1.<init>(r2)
            throw r1
        L_0x017b:
            java.lang.InterruptedException r1 = new java.lang.InterruptedException
            r1.<init>()
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.a.get(long, java.util.concurrent.TimeUnit):java.lang.Object");
    }

    public boolean isCancelled() {
        return this.c instanceof b;
    }

    public boolean isDone() {
        boolean z;
        Object obj = this.c;
        if (obj != null) {
            z = true;
        } else {
            z = false;
        }
        return (!(obj instanceof f)) & z;
    }

    public final String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("[status=");
        if (isCancelled()) {
            sb.append("CANCELLED");
        } else if (isDone()) {
            b(sb);
        } else {
            try {
                str = e();
            } catch (RuntimeException e2) {
                str = "Exception thrown from implementation: " + e2.getClass();
            }
            if (str != null && !str.isEmpty()) {
                sb.append("PENDING, info=[");
                sb.append(str);
                sb.append("]");
            } else if (isDone()) {
                b(sb);
            } else {
                sb.append("PENDING");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public V get() throws InterruptedException, ExecutionException {
        Object obj;
        if (!Thread.interrupted()) {
            Object obj2 = this.c;
            if ((obj2 != null) && (!(obj2 instanceof f))) {
                return d(obj2);
            }
            j jVar = this.g;
            j jVar2 = j.c;
            if (jVar != jVar2) {
                j jVar3 = new j();
                do {
                    C0000a aVar = j;
                    aVar.d(jVar3, jVar);
                    if (aVar.c(this, jVar, jVar3)) {
                        do {
                            LockSupport.park(this);
                            if (!Thread.interrupted()) {
                                obj = this.c;
                            } else {
                                f(jVar3);
                                throw new InterruptedException();
                            }
                        } while (!((obj != null) & (!(obj instanceof f))));
                        return d(obj);
                    }
                    jVar = this.g;
                } while (jVar != jVar2);
            }
            return d(this.c);
        }
        throw new InterruptedException();
    }
}
