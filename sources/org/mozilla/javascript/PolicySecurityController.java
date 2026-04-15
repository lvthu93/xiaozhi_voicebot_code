package org.mozilla.javascript;

import androidx.core.app.NotificationCompat;
import java.lang.ref.SoftReference;
import java.security.AccessController;
import java.security.CodeSource;
import java.security.PrivilegedAction;
import java.security.SecureClassLoader;
import java.util.Map;
import java.util.WeakHashMap;

public class PolicySecurityController extends SecurityController {
    private static final Map<CodeSource, Map<ClassLoader, SoftReference<SecureCaller>>> callers = new WeakHashMap();
    /* access modifiers changed from: private */
    public static final byte[] secureCallerImplBytecode = loadBytecode();

    public static class Loader extends SecureClassLoader implements GeneratedClassLoader {
        private final CodeSource codeSource;

        public Loader(ClassLoader classLoader, CodeSource codeSource2) {
            super(classLoader);
            this.codeSource = codeSource2;
        }

        public Class<?> defineClass(String str, byte[] bArr) {
            return defineClass(str, bArr, 0, bArr.length, this.codeSource);
        }

        public void linkClass(Class<?> cls) {
            resolveClass(cls);
        }
    }

    public static abstract class SecureCaller {
        public abstract Object call(Callable callable, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr);
    }

    private static byte[] loadBytecode() {
        String name = SecureCaller.class.getName();
        l0 l0Var = new l0(name.concat("Impl"), name, "<generated>");
        l0Var.ap("<init>", "()V", 1);
        l0Var.g(0);
        l0Var.m(name, 183, "<init>", "()V");
        l0Var.c(177);
        l0Var.aq(1);
        l0Var.ap(NotificationCompat.CATEGORY_CALL, "(Lorg/mozilla/javascript/Callable;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/Scriptable;[Ljava/lang/Object;)Ljava/lang/Object;", 17);
        for (int i = 1; i < 6; i++) {
            l0Var.g(i);
        }
        l0Var.m("org/mozilla/javascript/Callable", 185, NotificationCompat.CATEGORY_CALL, "(Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/Scriptable;[Ljava/lang/Object;)Ljava/lang/Object;");
        l0Var.c(176);
        l0Var.aq(6);
        return l0Var.ar();
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: org.mozilla.javascript.PolicySecurityController$SecureCaller} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object callWithDomain(java.lang.Object r10, final org.mozilla.javascript.Context r11, org.mozilla.javascript.Callable r12, org.mozilla.javascript.Scriptable r13, org.mozilla.javascript.Scriptable r14, java.lang.Object[] r15) {
        /*
            r9 = this;
            org.mozilla.javascript.PolicySecurityController$2 r0 = new org.mozilla.javascript.PolicySecurityController$2
            r0.<init>(r11)
            java.lang.Object r0 = java.security.AccessController.doPrivileged(r0)
            java.lang.ClassLoader r0 = (java.lang.ClassLoader) r0
            java.security.CodeSource r10 = (java.security.CodeSource) r10
            java.util.Map<java.security.CodeSource, java.util.Map<java.lang.ClassLoader, java.lang.ref.SoftReference<org.mozilla.javascript.PolicySecurityController$SecureCaller>>> r1 = callers
            monitor-enter(r1)
            java.lang.Object r2 = r1.get(r10)     // Catch:{ all -> 0x0063 }
            java.util.Map r2 = (java.util.Map) r2     // Catch:{ all -> 0x0063 }
            if (r2 != 0) goto L_0x0020
            java.util.WeakHashMap r2 = new java.util.WeakHashMap     // Catch:{ all -> 0x0063 }
            r2.<init>()     // Catch:{ all -> 0x0063 }
            r1.put(r10, r2)     // Catch:{ all -> 0x0063 }
        L_0x0020:
            monitor-exit(r1)     // Catch:{ all -> 0x0063 }
            monitor-enter(r2)
            java.lang.Object r1 = r2.get(r0)     // Catch:{ all -> 0x0060 }
            java.lang.ref.SoftReference r1 = (java.lang.ref.SoftReference) r1     // Catch:{ all -> 0x0060 }
            if (r1 == 0) goto L_0x0031
            java.lang.Object r1 = r1.get()     // Catch:{ all -> 0x0060 }
            org.mozilla.javascript.PolicySecurityController$SecureCaller r1 = (org.mozilla.javascript.PolicySecurityController.SecureCaller) r1     // Catch:{ all -> 0x0060 }
            goto L_0x0032
        L_0x0031:
            r1 = 0
        L_0x0032:
            if (r1 != 0) goto L_0x0054
            org.mozilla.javascript.PolicySecurityController$3 r1 = new org.mozilla.javascript.PolicySecurityController$3     // Catch:{ PrivilegedActionException -> 0x0049 }
            r1.<init>(r0, r10)     // Catch:{ PrivilegedActionException -> 0x0049 }
            java.lang.Object r10 = java.security.AccessController.doPrivileged(r1)     // Catch:{ PrivilegedActionException -> 0x0049 }
            r1 = r10
            org.mozilla.javascript.PolicySecurityController$SecureCaller r1 = (org.mozilla.javascript.PolicySecurityController.SecureCaller) r1     // Catch:{ PrivilegedActionException -> 0x0049 }
            java.lang.ref.SoftReference r10 = new java.lang.ref.SoftReference     // Catch:{ PrivilegedActionException -> 0x0049 }
            r10.<init>(r1)     // Catch:{ PrivilegedActionException -> 0x0049 }
            r2.put(r0, r10)     // Catch:{ PrivilegedActionException -> 0x0049 }
            goto L_0x0054
        L_0x0049:
            r10 = move-exception
            java.lang.reflect.UndeclaredThrowableException r11 = new java.lang.reflect.UndeclaredThrowableException     // Catch:{ all -> 0x0060 }
            java.lang.Throwable r10 = r10.getCause()     // Catch:{ all -> 0x0060 }
            r11.<init>(r10)     // Catch:{ all -> 0x0060 }
            throw r11     // Catch:{ all -> 0x0060 }
        L_0x0054:
            r3 = r1
            monitor-exit(r2)     // Catch:{ all -> 0x0060 }
            r4 = r12
            r5 = r11
            r6 = r13
            r7 = r14
            r8 = r15
            java.lang.Object r10 = r3.call(r4, r5, r6, r7, r8)
            return r10
        L_0x0060:
            r10 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0060 }
            throw r10
        L_0x0063:
            r10 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0063 }
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.PolicySecurityController.callWithDomain(java.lang.Object, org.mozilla.javascript.Context, org.mozilla.javascript.Callable, org.mozilla.javascript.Scriptable, org.mozilla.javascript.Scriptable, java.lang.Object[]):java.lang.Object");
    }

    public GeneratedClassLoader createClassLoader(final ClassLoader classLoader, final Object obj) {
        return (Loader) AccessController.doPrivileged(new PrivilegedAction<Object>() {
            public Object run() {
                return new Loader(classLoader, (CodeSource) obj);
            }
        });
    }

    public Object getDynamicSecurityDomain(Object obj) {
        return obj;
    }

    public Class<?> getStaticSecurityDomainClassInternal() {
        return CodeSource.class;
    }
}
