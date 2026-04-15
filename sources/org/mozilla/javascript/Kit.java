package org.mozilla.javascript;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class Kit {

    public static final class ComplexKey {
        private int hash;
        private Object key1;
        private Object key2;

        public ComplexKey(Object obj, Object obj2) {
            this.key1 = obj;
            this.key2 = obj2;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof ComplexKey)) {
                return false;
            }
            ComplexKey complexKey = (ComplexKey) obj;
            if (!this.key1.equals(complexKey.key1) || !this.key2.equals(complexKey.key2)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            if (this.hash == 0) {
                this.hash = this.key1.hashCode() ^ this.key2.hashCode();
            }
            return this.hash;
        }
    }

    public static Object addListener(Object obj, Object obj2) {
        if (obj2 == null) {
            throw new IllegalArgumentException();
        } else if (obj2 instanceof Object[]) {
            throw new IllegalArgumentException();
        } else if (obj == null) {
            return obj2;
        } else {
            if (!(obj instanceof Object[])) {
                return new Object[]{obj, obj2};
            }
            Object[] objArr = (Object[]) obj;
            int length = objArr.length;
            if (length >= 2) {
                Object[] objArr2 = new Object[(length + 1)];
                System.arraycopy(objArr, 0, objArr2, 0, length);
                objArr2[length] = obj2;
                return objArr2;
            }
            throw new IllegalArgumentException();
        }
    }

    public static Class<?> classOrNull(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException | IllegalArgumentException | LinkageError | SecurityException unused) {
            return null;
        }
    }

    public static RuntimeException codeBug(String str) throws RuntimeException {
        IllegalStateException illegalStateException = new IllegalStateException(y2.i("FAILED ASSERTION: ", str));
        illegalStateException.printStackTrace(System.err);
        throw illegalStateException;
    }

    public static Object getListener(Object obj, int i) {
        if (i == 0) {
            if (obj == null) {
                return null;
            }
            if (!(obj instanceof Object[])) {
                return obj;
            }
            Object[] objArr = (Object[]) obj;
            if (objArr.length >= 2) {
                return objArr[0];
            }
            throw new IllegalArgumentException();
        } else if (i != 1) {
            Object[] objArr2 = (Object[]) obj;
            int length = objArr2.length;
            if (length < 2) {
                throw new IllegalArgumentException();
            } else if (i == length) {
                return null;
            } else {
                return objArr2[i];
            }
        } else if (obj instanceof Object[]) {
            return ((Object[]) obj)[1];
        } else {
            if (obj != null) {
                return null;
            }
            throw new IllegalArgumentException();
        }
    }

    public static Object initHash(Map<Object, Object> map, Object obj, Object obj2) {
        synchronized (map) {
            Object obj3 = map.get(obj);
            if (obj3 == null) {
                map.put(obj, obj2);
            } else {
                obj2 = obj3;
            }
        }
        return obj2;
    }

    public static Object makeHashKeyFromPair(Object obj, Object obj2) {
        if (obj == null) {
            throw new IllegalArgumentException();
        } else if (obj2 != null) {
            return new ComplexKey(obj, obj2);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static Object newInstanceOrNull(Class<?> cls) {
        try {
            return cls.newInstance();
        } catch (IllegalAccessException | InstantiationException | LinkageError | SecurityException unused) {
            return null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0024, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0029, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x002a, code lost:
        r6.addSuppressed(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x002d, code lost:
        throw r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String readReader(java.io.Reader r6) throws java.io.IOException {
        /*
            java.io.BufferedReader r0 = new java.io.BufferedReader
            r0.<init>(r6)
            r6 = 1024(0x400, float:1.435E-42)
            char[] r1 = new char[r6]     // Catch:{ all -> 0x0022 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0022 }
            r2.<init>(r6)     // Catch:{ all -> 0x0022 }
        L_0x000e:
            r3 = 0
            int r4 = r0.read(r1, r3, r6)     // Catch:{ all -> 0x0022 }
            r5 = -1
            if (r4 == r5) goto L_0x001a
            r2.append(r1, r3, r4)     // Catch:{ all -> 0x0022 }
            goto L_0x000e
        L_0x001a:
            java.lang.String r6 = r2.toString()     // Catch:{ all -> 0x0022 }
            r0.close()
            return r6
        L_0x0022:
            r6 = move-exception
            throw r6     // Catch:{ all -> 0x0024 }
        L_0x0024:
            r1 = move-exception
            r0.close()     // Catch:{ all -> 0x0029 }
            goto L_0x002d
        L_0x0029:
            r0 = move-exception
            r6.addSuppressed(r0)
        L_0x002d:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.Kit.readReader(java.io.Reader):java.lang.String");
    }

    public static byte[] readStream(InputStream inputStream, int i) throws IOException {
        if (i > 0) {
            byte[] bArr = new byte[i];
            int i2 = 0;
            while (true) {
                int read = inputStream.read(bArr, i2, bArr.length - i2);
                if (read < 0) {
                    break;
                }
                i2 += read;
                if (i2 == bArr.length) {
                    byte[] bArr2 = new byte[(bArr.length * 2)];
                    System.arraycopy(bArr, 0, bArr2, 0, i2);
                    bArr = bArr2;
                }
            }
            if (i2 == bArr.length) {
                return bArr;
            }
            byte[] bArr3 = new byte[i2];
            System.arraycopy(bArr, 0, bArr3, 0, i2);
            return bArr3;
        }
        throw new IllegalArgumentException(y2.f("Bad initialBufferCapacity: ", i));
    }

    public static Object removeListener(Object obj, Object obj2) {
        if (obj2 == null) {
            throw new IllegalArgumentException();
        } else if (obj2 instanceof Object[]) {
            throw new IllegalArgumentException();
        } else if (obj == obj2) {
            return null;
        } else {
            if (!(obj instanceof Object[])) {
                return obj;
            }
            Object[] objArr = (Object[]) obj;
            int length = objArr.length;
            if (length < 2) {
                throw new IllegalArgumentException();
            } else if (length == 2) {
                Object obj3 = objArr[1];
                if (obj3 == obj2) {
                    return objArr[0];
                }
                if (objArr[0] == obj2) {
                    return obj3;
                }
                return obj;
            } else {
                int i = length;
                do {
                    i--;
                    if (objArr[i] == obj2) {
                        Object[] objArr2 = new Object[(length - 1)];
                        System.arraycopy(objArr, 0, objArr2, 0, i);
                        int i2 = i + 1;
                        System.arraycopy(objArr, i2, objArr2, i, length - i2);
                        return objArr2;
                    }
                } while (i != 0);
                return obj;
            }
        }
    }

    public static boolean testIfCanLoadRhinoClasses(ClassLoader classLoader) {
        Class<?> cls = ScriptRuntime.ContextFactoryClass;
        if (classOrNull(classLoader, cls.getName()) != cls) {
            return false;
        }
        return true;
    }

    public static int xDigitToInt(int i, int i2) {
        int i3;
        if (i <= 57) {
            i3 = i - 48;
            if (i3 < 0) {
                return -1;
            }
        } else if (i <= 70) {
            if (65 > i) {
                return -1;
            }
            i3 = i - 55;
        } else if (i > 102 || 97 > i) {
            return -1;
        } else {
            i3 = i - 87;
        }
        return i3 | (i2 << 4);
    }

    public static Class<?> classOrNull(ClassLoader classLoader, String str) {
        try {
            return classLoader.loadClass(str);
        } catch (ClassNotFoundException | IllegalArgumentException | LinkageError | SecurityException unused) {
            return null;
        }
    }

    public static RuntimeException codeBug() throws RuntimeException {
        IllegalStateException illegalStateException = new IllegalStateException("FAILED ASSERTION");
        illegalStateException.printStackTrace(System.err);
        throw illegalStateException;
    }
}
