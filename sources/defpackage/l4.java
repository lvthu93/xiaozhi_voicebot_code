package defpackage;

import defpackage.id;
import j$.util.Objects;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.concurrent.LinkedBlockingQueue;
import org.slf4j.impl.StaticLoggerBinder;

/* renamed from: l4  reason: default package */
public final class l4 {
    public static volatile int a;
    public static final fc b = new fc();
    public static final cg c = new cg();
    public static final boolean d;
    public static final String[] e = {"1.6", "1.7"};
    public static final String f = "org/slf4j/impl/StaticLoggerBinder.class";

    static {
        String str;
        boolean z;
        try {
            str = System.getProperty("slf4j.detectLoggerNameMismatch");
        } catch (SecurityException unused) {
            str = null;
        }
        if (str == null) {
            z = false;
        } else {
            z = str.equalsIgnoreCase("true");
        }
        d = z;
    }

    public static final void a() {
        boolean z;
        LinkedHashSet linkedHashSet;
        try {
            if (!g()) {
                linkedHashSet = b();
                j(linkedHashSet);
            } else {
                linkedHashSet = null;
            }
            StaticLoggerBinder.getSingleton();
            a = 3;
            i(linkedHashSet);
            c();
            h();
            b.b();
        } catch (NoClassDefFoundError e2) {
            String message = e2.getMessage();
            if (message != null && (message.contains("org/slf4j/impl/StaticLoggerBinder") || message.contains("org.slf4j.impl.StaticLoggerBinder"))) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                a = 4;
                id.a();
                id.a();
                id.a();
                return;
            }
            a = 2;
            id.b(e2);
            throw e2;
        } catch (NoSuchMethodError e3) {
            String message2 = e3.getMessage();
            if (message2 != null && message2.contains("org.slf4j.impl.StaticLoggerBinder.getSingleton()")) {
                a = 2;
                id.a();
                id.a();
                id.a();
            }
            throw e3;
        } catch (Exception e4) {
            a = 2;
            id.b(e4);
            throw new IllegalStateException("Unexpected initialization failure", e4);
        }
    }

    public static LinkedHashSet b() {
        Enumeration<URL> enumeration;
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        try {
            ClassLoader classLoader = l4.class.getClassLoader();
            String str = f;
            if (classLoader == null) {
                enumeration = ClassLoader.getSystemResources(str);
            } else {
                enumeration = classLoader.getResources(str);
            }
            while (enumeration.hasMoreElements()) {
                linkedHashSet.add(enumeration.nextElement());
            }
        } catch (IOException e2) {
            id.b(e2);
        }
        return linkedHashSet;
    }

    public static void c() {
        fc fcVar = b;
        synchronized (fcVar) {
            fcVar.a = true;
            Iterator it = new ArrayList(fcVar.b.values()).iterator();
            while (it.hasNext()) {
                ec ecVar = (ec) it.next();
                ecVar.f = f(ecVar.c);
            }
        }
    }

    public static ILoggerFactory d() {
        if (a == 0) {
            synchronized (l4.class) {
                if (a == 0) {
                    a = 1;
                    a();
                    if (a == 3) {
                        k();
                    }
                }
            }
        }
        int i = a;
        if (i == 1) {
            return b;
        }
        if (i == 2) {
            throw new IllegalStateException("org.slf4j.LoggerFactory in failed state. Original exception was thrown EARLIER. See also http://www.slf4j.org/codes.html#unsuccessfulInit");
        } else if (i == 3) {
            return StaticLoggerBinder.getSingleton().getLoggerFactory();
        } else {
            if (i == 4) {
                return c;
            }
            throw new IllegalStateException("Unreachable code");
        }
    }

    public static k4 e(Class<?> cls) {
        int i;
        k4 f2 = f(cls.getName());
        if (d) {
            id.a aVar = id.a;
            Class cls2 = null;
            if (aVar == null) {
                if (id.b) {
                    aVar = null;
                } else {
                    try {
                        aVar = new id.a();
                    } catch (SecurityException unused) {
                        aVar = null;
                    }
                    id.a = aVar;
                    id.b = true;
                }
            }
            if (aVar != null) {
                Class[] classContext = aVar.getClassContext();
                String name = id.class.getName();
                int i2 = 0;
                while (i2 < classContext.length && !name.equals(classContext[i2].getName())) {
                    i2++;
                }
                if (i2 >= classContext.length || (i = i2 + 2) >= classContext.length) {
                    throw new IllegalStateException("Failed to find org.slf4j.helpers.Util or its caller in the stack; this should not happen");
                }
                cls2 = classContext[i];
            }
            if (cls2 != null && (!cls2.isAssignableFrom(cls))) {
                String.format("Detected logger name mismatch. Given name: \"%s\"; computed name: \"%s\".", new Object[]{f2.getName(), cls2.getName()});
                id.a();
                id.a();
            }
        }
        return f2;
    }

    public static k4 f(String str) {
        return d().a(str);
    }

    public static boolean g() {
        String str;
        try {
            str = System.getProperty("java.vendor.url");
        } catch (SecurityException unused) {
            str = null;
        }
        if (str == null) {
            return false;
        }
        return str.toLowerCase().contains("android");
    }

    public static void h() {
        boolean z;
        LinkedBlockingQueue<gc> linkedBlockingQueue = b.c;
        linkedBlockingQueue.size();
        ArrayList arrayList = new ArrayList(128);
        int i = 0;
        while (linkedBlockingQueue.drainTo(arrayList, 128) != 0) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                gc gcVar = (gc) it.next();
                if (gcVar != null) {
                    ec ecVar = gcVar.a;
                    String str = ecVar.c;
                    if (ecVar.f == null) {
                        z = true;
                    } else {
                        z = false;
                    }
                    if (z) {
                        throw new IllegalStateException("Delegate logger cannot be null at this state.");
                    } else if (!(ecVar.f instanceof d7)) {
                        if (!ecVar.k()) {
                            id.a();
                        } else if (ecVar.k()) {
                            try {
                                ecVar.h.invoke(ecVar.f, new Object[]{gcVar});
                            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException unused) {
                            }
                        }
                    }
                }
                int i2 = i + 1;
                if (i == 0) {
                    if (gcVar.a.k()) {
                        id.a();
                        id.a();
                        id.a();
                    } else if (!(gcVar.a.f instanceof d7)) {
                        id.a();
                        id.a();
                        id.a();
                        id.a();
                        id.a();
                    }
                }
                i = i2;
            }
            arrayList.clear();
        }
    }

    public static void i(LinkedHashSet linkedHashSet) {
        if (linkedHashSet != null) {
            boolean z = true;
            if (linkedHashSet.size() <= 1) {
                z = false;
            }
            if (z) {
                StaticLoggerBinder.getSingleton().getLoggerFactoryClassStr();
                id.a();
            }
        }
    }

    public static void j(LinkedHashSet linkedHashSet) {
        boolean z = true;
        if (linkedHashSet.size() <= 1) {
            z = false;
        }
        if (z) {
            id.a();
            Iterator it = linkedHashSet.iterator();
            while (it.hasNext()) {
                Objects.toString((URL) it.next());
                id.a();
            }
            id.a();
        }
    }

    public static final void k() {
        try {
            String str = StaticLoggerBinder.REQUESTED_API_VERSION;
            boolean z = false;
            for (String startsWith : e) {
                if (str.startsWith(startsWith)) {
                    z = true;
                }
            }
            if (!z) {
                Arrays.asList(e).toString();
                id.a();
                id.a();
            }
        } catch (NoSuchFieldError unused) {
        } catch (Throwable th) {
            id.b(th);
        }
    }
}
