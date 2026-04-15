package com.google.common.base;

import com.google.common.base.internal.Finalizer;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FinalizableReferenceQueue implements Closeable {
    public static final Logger h = Logger.getLogger(FinalizableReferenceQueue.class.getName());
    public static final Method i;
    public final ReferenceQueue<Object> c;
    public final PhantomReference<Object> f;
    public final boolean g;

    public static class DecoupledLoader implements FinalizerLoader {
        public final URL a() throws IOException {
            String str = "com.google.common.base.internal.Finalizer".replace('.', '/') + ".class";
            URL resource = getClass().getClassLoader().getResource(str);
            if (resource != null) {
                String url = resource.toString();
                if (url.endsWith(str)) {
                    return new URL(resource, url.substring(0, url.length() - str.length()));
                }
                throw new IOException("Unsupported path style: ".concat(url));
            }
            throw new FileNotFoundException(str);
        }

        public Class<?> loadFinalizer() {
            try {
                return new URLClassLoader(new URL[]{a()}, (ClassLoader) null).loadClass("com.google.common.base.internal.Finalizer");
            } catch (Exception e) {
                FinalizableReferenceQueue.h.log(Level.WARNING, "Could not load Finalizer in its own class loader. Loading Finalizer in the current class loader instead. As a result, you will not be able to garbage collect this class loader. To support reclaiming this class loader, either resolve the underlying issue, or move Guava to your system class path.", e);
                return null;
            }
        }
    }

    public static class DirectLoader implements FinalizerLoader {
        public Class<?> loadFinalizer() {
            Class<Finalizer> cls = Finalizer.class;
            try {
                Logger logger = Finalizer.h;
                return cls;
            } catch (ClassNotFoundException e) {
                throw new AssertionError(e);
            }
        }
    }

    public interface FinalizerLoader {
        Class<?> loadFinalizer();
    }

    public static class SystemLoader implements FinalizerLoader {
        public Class<?> loadFinalizer() {
            try {
                ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
                if (systemClassLoader == null) {
                    return null;
                }
                try {
                    return systemClassLoader.loadClass("com.google.common.base.internal.Finalizer");
                } catch (ClassNotFoundException unused) {
                    return null;
                }
            } catch (SecurityException unused2) {
                FinalizableReferenceQueue.h.info("Not allowed to access system class loader.");
                return null;
            }
        }
    }

    static {
        FinalizerLoader[] finalizerLoaderArr = {new SystemLoader(), new DecoupledLoader(), new DirectLoader()};
        int i2 = 0;
        while (i2 < 3) {
            Class<?> loadFinalizer = finalizerLoaderArr[i2].loadFinalizer();
            if (loadFinalizer != null) {
                try {
                    i = loadFinalizer.getMethod("startFinalizer", new Class[]{Class.class, ReferenceQueue.class, PhantomReference.class});
                    return;
                } catch (NoSuchMethodException e) {
                    throw new AssertionError(e);
                }
            } else {
                i2++;
            }
        }
        throw new AssertionError();
    }

    public FinalizableReferenceQueue() {
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        this.c = referenceQueue;
        PhantomReference<Object> phantomReference = new PhantomReference<>(this, referenceQueue);
        this.f = phantomReference;
        boolean z = false;
        try {
            i.invoke((Object) null, new Object[]{FinalizableReference.class, referenceQueue, phantomReference});
            z = true;
        } catch (IllegalAccessException e) {
            throw new AssertionError(e);
        } catch (Throwable th) {
            h.log(Level.INFO, "Failed to start reference finalizer thread. Reference cleanup will only occur when new references are created.", th);
        }
        this.g = z;
    }

    public void close() {
        this.f.enqueue();
        if (!this.g) {
            while (true) {
                Reference<? extends Object> poll = this.c.poll();
                if (poll != null) {
                    poll.clear();
                    try {
                        ((FinalizableReference) poll).finalizeReferent();
                    } catch (Throwable th) {
                        h.log(Level.SEVERE, "Error cleaning up after reference.", th);
                    }
                } else {
                    return;
                }
            }
        }
    }
}
