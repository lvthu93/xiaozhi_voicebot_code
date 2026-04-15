package com.google.common.base.internal;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Finalizer implements Runnable {
    public static final Logger h = Logger.getLogger(Finalizer.class.getName());
    public static final Constructor<Thread> i;
    public static final Field j;
    public final WeakReference<Class<?>> c;
    public final PhantomReference<Object> f;
    public final ReferenceQueue<Object> g;

    static {
        Constructor<Thread> constructor;
        Class<Thread> cls = Thread.class;
        Field field = null;
        try {
            constructor = cls.getConstructor(new Class[]{ThreadGroup.class, Runnable.class, String.class, Long.TYPE, Boolean.TYPE});
        } catch (Throwable unused) {
            constructor = null;
        }
        i = constructor;
        if (constructor == null) {
            try {
                Field declaredField = cls.getDeclaredField("inheritableThreadLocals");
                declaredField.setAccessible(true);
                field = declaredField;
            } catch (Throwable unused2) {
                h.log(Level.INFO, "Couldn't access Thread.inheritableThreadLocals. Reference finalizer threads will inherit thread local values.");
            }
        }
        j = field;
    }

    public Finalizer(Class<?> cls, ReferenceQueue<Object> referenceQueue, PhantomReference<Object> phantomReference) {
        this.g = referenceQueue;
        this.c = new WeakReference<>(cls);
        this.f = phantomReference;
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x004a  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0056 A[Catch:{ all -> 0x005a }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void startFinalizer(java.lang.Class<?> r6, java.lang.ref.ReferenceQueue<java.lang.Object> r7, java.lang.ref.PhantomReference<java.lang.Object> r8) {
        /*
            java.lang.String r0 = r6.getName()
            java.lang.String r1 = "com.google.common.base.FinalizableReference"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0066
            com.google.common.base.internal.Finalizer r0 = new com.google.common.base.internal.Finalizer
            r0.<init>(r6, r7, r8)
            java.lang.Class<com.google.common.base.internal.Finalizer> r6 = com.google.common.base.internal.Finalizer.class
            java.lang.String r6 = r6.getName()
            java.util.logging.Logger r7 = h
            r8 = 1
            r1 = 0
            java.lang.reflect.Constructor<java.lang.Thread> r2 = i
            if (r2 == 0) goto L_0x0047
            r3 = 5
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x003f }
            r4 = 0
            r3[r4] = r1     // Catch:{ all -> 0x003f }
            r3[r8] = r0     // Catch:{ all -> 0x003f }
            r4 = 2
            r3[r4] = r6     // Catch:{ all -> 0x003f }
            r4 = 0
            java.lang.Long r4 = java.lang.Long.valueOf(r4)     // Catch:{ all -> 0x003f }
            r5 = 3
            r3[r5] = r4     // Catch:{ all -> 0x003f }
            java.lang.Boolean r4 = java.lang.Boolean.FALSE     // Catch:{ all -> 0x003f }
            r5 = 4
            r3[r5] = r4     // Catch:{ all -> 0x003f }
            java.lang.Object r2 = r2.newInstance(r3)     // Catch:{ all -> 0x003f }
            java.lang.Thread r2 = (java.lang.Thread) r2     // Catch:{ all -> 0x003f }
            goto L_0x0048
        L_0x003f:
            r2 = move-exception
            java.util.logging.Level r3 = java.util.logging.Level.INFO
            java.lang.String r4 = "Failed to create a thread without inherited thread-local values"
            r7.log(r3, r4, r2)
        L_0x0047:
            r2 = r1
        L_0x0048:
            if (r2 != 0) goto L_0x004f
            java.lang.Thread r2 = new java.lang.Thread
            r2.<init>(r1, r0, r6)
        L_0x004f:
            r2.setDaemon(r8)
            java.lang.reflect.Field r6 = j     // Catch:{ all -> 0x005a }
            if (r6 == 0) goto L_0x0062
            r6.set(r2, r1)     // Catch:{ all -> 0x005a }
            goto L_0x0062
        L_0x005a:
            r6 = move-exception
            java.util.logging.Level r8 = java.util.logging.Level.INFO
            java.lang.String r0 = "Failed to clear thread local values inherited by reference finalizer thread."
            r7.log(r8, r0, r6)
        L_0x0062:
            r2.start()
            return
        L_0x0066:
            java.lang.IllegalArgumentException r6 = new java.lang.IllegalArgumentException
            java.lang.String r7 = "Expected com.google.common.base.FinalizableReference."
            r6.<init>(r7)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.base.internal.Finalizer.startFinalizer(java.lang.Class, java.lang.ref.ReferenceQueue, java.lang.ref.PhantomReference):void");
    }

    public final boolean a(Reference<?> reference) {
        Method method;
        Class cls = this.c.get();
        if (cls == null) {
            method = null;
        } else {
            try {
                method = cls.getMethod("finalizeReferent", new Class[0]);
            } catch (NoSuchMethodException e) {
                throw new AssertionError(e);
            }
        }
        if (method == null) {
            return false;
        }
        do {
            reference.clear();
            if (reference == this.f) {
                return false;
            }
            try {
                method.invoke(reference, new Object[0]);
            } catch (Throwable th) {
                h.log(Level.SEVERE, "Error cleaning up after reference.", th);
            }
            reference = this.g.poll();
        } while (reference != null);
        return true;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(2:0|1) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:0:0x0000 */
    /* JADX WARNING: Removed duplicated region for block: B:0:0x0000 A[LOOP:0: B:0:0x0000->B:2:0x000a, LOOP_START, MTH_ENTER_BLOCK, SYNTHETIC, Splitter:B:0:0x0000] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
            r1 = this;
        L_0x0000:
            java.lang.ref.ReferenceQueue<java.lang.Object> r0 = r1.g     // Catch:{ InterruptedException -> 0x0000 }
            java.lang.ref.Reference r0 = r0.remove()     // Catch:{ InterruptedException -> 0x0000 }
            boolean r0 = r1.a(r0)     // Catch:{ InterruptedException -> 0x0000 }
            if (r0 != 0) goto L_0x0000
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.base.internal.Finalizer.run():void");
    }
}
