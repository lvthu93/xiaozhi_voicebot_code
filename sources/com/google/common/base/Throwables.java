package com.google.common.base;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class Throwables {
    public static final Object a;
    public static final Method b;
    public static final Method c;

    /* JADX WARNING: Removed duplicated region for block: B:14:0x003e  */
    static {
        /*
            java.lang.String r0 = "getJavaLangAccess"
            java.lang.String r1 = "sun.misc.SharedSecrets"
            r2 = 0
            r3 = 0
            java.lang.Class r4 = java.lang.Class.forName(r1, r2, r3)     // Catch:{ ThreadDeath -> 0x007a, all -> 0x0017 }
            java.lang.Class[] r5 = new java.lang.Class[r2]     // Catch:{ ThreadDeath -> 0x007a, all -> 0x0017 }
            java.lang.reflect.Method r4 = r4.getMethod(r0, r5)     // Catch:{ ThreadDeath -> 0x007a, all -> 0x0017 }
            java.lang.Object[] r5 = new java.lang.Object[r2]     // Catch:{ ThreadDeath -> 0x007a, all -> 0x0017 }
            java.lang.Object r4 = r4.invoke(r3, r5)     // Catch:{ ThreadDeath -> 0x007a, all -> 0x0017 }
            goto L_0x0018
        L_0x0017:
            r4 = r3
        L_0x0018:
            a = r4
            java.lang.String r5 = "sun.misc.JavaLangAccess"
            r6 = 1
            java.lang.Class<java.lang.Throwable> r7 = java.lang.Throwable.class
            if (r4 != 0) goto L_0x0023
        L_0x0021:
            r8 = r3
            goto L_0x0039
        L_0x0023:
            r8 = 2
            java.lang.Class[] r8 = new java.lang.Class[r8]
            r8[r2] = r7
            java.lang.Class r9 = java.lang.Integer.TYPE
            r8[r6] = r9
            java.lang.String r9 = "getStackTraceElement"
            java.lang.Class r10 = java.lang.Class.forName(r5, r2, r3)     // Catch:{ ThreadDeath -> 0x0078, all -> 0x0037 }
            java.lang.reflect.Method r8 = r10.getMethod(r9, r8)     // Catch:{ ThreadDeath -> 0x0078, all -> 0x0037 }
            goto L_0x0039
        L_0x0037:
            goto L_0x0021
        L_0x0039:
            b = r8
            if (r4 != 0) goto L_0x003e
            goto L_0x0075
        L_0x003e:
            java.lang.String r4 = "getStackTraceDepth"
            java.lang.Class[] r8 = new java.lang.Class[r6]     // Catch:{ IllegalAccessException | UnsupportedOperationException | InvocationTargetException -> 0x0075 }
            r8[r2] = r7     // Catch:{ IllegalAccessException | UnsupportedOperationException | InvocationTargetException -> 0x0075 }
            java.lang.Class r5 = java.lang.Class.forName(r5, r2, r3)     // Catch:{ ThreadDeath -> 0x0073, all -> 0x004d }
            java.lang.reflect.Method r4 = r5.getMethod(r4, r8)     // Catch:{ ThreadDeath -> 0x0073, all -> 0x004d }
            goto L_0x004e
        L_0x004d:
            r4 = r3
        L_0x004e:
            if (r4 != 0) goto L_0x0051
            goto L_0x0075
        L_0x0051:
            java.lang.Class r1 = java.lang.Class.forName(r1, r2, r3)     // Catch:{ ThreadDeath -> 0x0071, all -> 0x0062 }
            java.lang.Class[] r5 = new java.lang.Class[r2]     // Catch:{ ThreadDeath -> 0x0071, all -> 0x0062 }
            java.lang.reflect.Method r0 = r1.getMethod(r0, r5)     // Catch:{ ThreadDeath -> 0x0071, all -> 0x0062 }
            java.lang.Object[] r1 = new java.lang.Object[r2]     // Catch:{ ThreadDeath -> 0x0071, all -> 0x0062 }
            java.lang.Object r0 = r0.invoke(r3, r1)     // Catch:{ ThreadDeath -> 0x0071, all -> 0x0062 }
            goto L_0x0063
        L_0x0062:
            r0 = r3
        L_0x0063:
            java.lang.Object[] r1 = new java.lang.Object[r6]     // Catch:{ IllegalAccessException | UnsupportedOperationException | InvocationTargetException -> 0x0075 }
            java.lang.Throwable r5 = new java.lang.Throwable     // Catch:{ IllegalAccessException | UnsupportedOperationException | InvocationTargetException -> 0x0075 }
            r5.<init>()     // Catch:{ IllegalAccessException | UnsupportedOperationException | InvocationTargetException -> 0x0075 }
            r1[r2] = r5     // Catch:{ IllegalAccessException | UnsupportedOperationException | InvocationTargetException -> 0x0075 }
            r4.invoke(r0, r1)     // Catch:{ IllegalAccessException | UnsupportedOperationException | InvocationTargetException -> 0x0075 }
            r3 = r4
            goto L_0x0075
        L_0x0071:
            r0 = move-exception
            throw r0     // Catch:{ IllegalAccessException | UnsupportedOperationException | InvocationTargetException -> 0x0075 }
        L_0x0073:
            r0 = move-exception
            throw r0     // Catch:{ IllegalAccessException | UnsupportedOperationException | InvocationTargetException -> 0x0075 }
        L_0x0075:
            c = r3
            return
        L_0x0078:
            r0 = move-exception
            throw r0
        L_0x007a:
            r0 = move-exception
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.base.Throwables.<clinit>():void");
    }

    public static List<Throwable> getCausalChain(Throwable th) {
        Preconditions.checkNotNull(th);
        ArrayList arrayList = new ArrayList(4);
        arrayList.add(th);
        boolean z = false;
        Throwable th2 = th;
        while (true) {
            th = th.getCause();
            if (th == null) {
                return Collections.unmodifiableList(arrayList);
            }
            arrayList.add(th);
            if (th != th2) {
                if (z) {
                    th2 = th2.getCause();
                }
                z = !z;
            } else {
                throw new IllegalArgumentException("Loop in causal chain detected.", th);
            }
        }
    }

    public static <X extends Throwable> X getCauseAs(Throwable th, Class<X> cls) {
        try {
            return (Throwable) cls.cast(th.getCause());
        } catch (ClassCastException e) {
            e.initCause(th);
            throw e;
        }
    }

    public static Throwable getRootCause(Throwable th) {
        boolean z = false;
        Throwable th2 = th;
        while (true) {
            Throwable cause = th.getCause();
            if (cause == null) {
                return th;
            }
            if (cause != th2) {
                if (z) {
                    th2 = th2.getCause();
                }
                z = !z;
                th = cause;
            } else {
                throw new IllegalArgumentException("Loop in causal chain detected.", cause);
            }
        }
    }

    public static String getStackTraceAsString(Throwable th) {
        StringWriter stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    public static List<StackTraceElement> lazyStackTrace(final Throwable th) {
        if (!lazyStackTraceIsLazy()) {
            return Collections.unmodifiableList(Arrays.asList(th.getStackTrace()));
        }
        Preconditions.checkNotNull(th);
        return new AbstractList<StackTraceElement>() {
            public StackTraceElement get(int i) {
                try {
                    return (StackTraceElement) Throwables.b.invoke(Throwables.a, new Object[]{th, Integer.valueOf(i)});
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e2) {
                    throw Throwables.propagate(e2.getCause());
                }
            }

            public int size() {
                try {
                    return ((Integer) Throwables.c.invoke(Throwables.a, new Object[]{th})).intValue();
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e2) {
                    throw Throwables.propagate(e2.getCause());
                }
            }
        };
    }

    public static boolean lazyStackTraceIsLazy() {
        return (b == null || c == null) ? false : true;
    }

    @Deprecated
    public static RuntimeException propagate(Throwable th) {
        throwIfUnchecked(th);
        throw new RuntimeException(th);
    }

    @Deprecated
    public static <X extends Throwable> void propagateIfInstanceOf(Throwable th, Class<X> cls) throws Throwable {
        if (th != null) {
            throwIfInstanceOf(th, cls);
        }
    }

    @Deprecated
    public static void propagateIfPossible(Throwable th) {
        if (th != null) {
            throwIfUnchecked(th);
        }
    }

    public static <X extends Throwable> void throwIfInstanceOf(Throwable th, Class<X> cls) throws Throwable {
        Preconditions.checkNotNull(th);
        if (cls.isInstance(th)) {
            throw ((Throwable) cls.cast(th));
        }
    }

    public static void throwIfUnchecked(Throwable th) {
        Preconditions.checkNotNull(th);
        if (th instanceof RuntimeException) {
            throw ((RuntimeException) th);
        } else if (th instanceof Error) {
            throw ((Error) th);
        }
    }

    public static <X extends Throwable> void propagateIfPossible(Throwable th, Class<X> cls) throws Throwable {
        propagateIfInstanceOf(th, cls);
        propagateIfPossible(th);
    }

    public static <X1 extends Throwable, X2 extends Throwable> void propagateIfPossible(Throwable th, Class<X1> cls, Class<X2> cls2) throws Throwable, Throwable {
        Preconditions.checkNotNull(cls2);
        propagateIfInstanceOf(th, cls);
        propagateIfPossible(th, cls2);
    }
}
