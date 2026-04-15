package j$.util.concurrent;

import androidx.appcompat.widget.ActivityChooserView;
import androidx.core.internal.view.SupportMenu;
import j$.sun.misc.b;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import org.mozilla.javascript.ES6Iterator;

public class ConcurrentHashMap<K, V> extends AbstractMap<K, V> implements ConcurrentMap<K, V>, Serializable, ConcurrentMap<K, V> {
    static final int g = Runtime.getRuntime().availableProcessors();
    private static final b h;
    private static final long i;
    private static final long j;
    private static final long k;
    private static final long l;
    private static final long m;
    private static final int n;
    private static final int o;
    private static final ObjectStreamField[] serialPersistentFields;
    private static final long serialVersionUID = 7249069246763182397L;
    volatile transient l[] a;
    private volatile transient l[] b;
    private volatile transient long baseCount;
    private volatile transient c[] c;
    private volatile transient int cellsBusy;
    private transient i d;
    private transient t e;
    private transient e f;
    private volatile transient int sizeCtl;
    private volatile transient int transferIndex;

    static {
        Class cls = Integer.TYPE;
        serialPersistentFields = new ObjectStreamField[]{new ObjectStreamField("segments", o[].class), new ObjectStreamField("segmentMask", cls), new ObjectStreamField("segmentShift", cls)};
        b h2 = b.h();
        h = h2;
        Class<ConcurrentHashMap> cls2 = ConcurrentHashMap.class;
        i = h2.j(cls2, "sizeCtl");
        j = h2.j(cls2, "transferIndex");
        k = h2.j(cls2, "baseCount");
        l = h2.j(cls2, "cellsBusy");
        m = h2.j(c.class, ES6Iterator.VALUE_PROPERTY);
        Class<l[]> cls3 = l[].class;
        n = h2.a(cls3);
        int b2 = h2.b(cls3);
        if (((b2 - 1) & b2) == 0) {
            o = 31 - Integer.numberOfLeadingZeros(b2);
            return;
        }
        throw new ExceptionInInitializerError("array index scale not a power of two");
    }

    public ConcurrentHashMap() {
    }

    public ConcurrentHashMap(int i2, float f2, int i3) {
        if (f2 <= 0.0f || i2 < 0 || i3 <= 0) {
            throw new IllegalArgumentException();
        }
        long j2 = (long) (((double) (((float) ((long) (i2 < i3 ? i3 : i2))) / f2)) + 1.0d);
        this.sizeCtl = j2 >= 1073741824 ? 1073741824 : m((int) j2);
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: Code restructure failed: missing block: B:3:0x001a, code lost:
        if (r1.d(r25, r3, r5, r14) == false) goto L_0x001c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x013b, code lost:
        if (r9.c != r7) goto L_0x0147;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:0x013d, code lost:
        r9.c = (j$.util.concurrent.c[]) java.util.Arrays.copyOf(r7, r8 << 1);
     */
    /* JADX WARNING: Removed duplicated region for block: B:131:0x019b A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:136:0x00ba A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void a(long r26, int r28) {
        /*
            r25 = this;
            r9 = r25
            r10 = r26
            r0 = r28
            j$.util.concurrent.c[] r12 = r9.c
            r13 = 2
            if (r12 != 0) goto L_0x001c
            j$.sun.misc.b r1 = h
            long r3 = k
            long r5 = r9.baseCount
            long r14 = r5 + r10
            r2 = r25
            r7 = r14
            boolean r1 = r1.d(r2, r3, r5, r7)
            if (r1 != 0) goto L_0x0047
        L_0x001c:
            r14 = 1
            if (r12 == 0) goto L_0x00a7
            int r1 = r12.length
            int r1 = r1 - r14
            if (r1 < 0) goto L_0x00a7
            int r2 = j$.util.concurrent.A.c()
            r1 = r1 & r2
            r1 = r12[r1]
            if (r1 == 0) goto L_0x00a7
            j$.sun.misc.b r15 = h
            long r17 = m
            long r2 = r1.value
            long r21 = r2 + r10
            r16 = r1
            r19 = r2
            boolean r1 = r15.d(r16, r17, r19, r21)
            if (r1 != 0) goto L_0x0040
            goto L_0x00a8
        L_0x0040:
            if (r0 > r14) goto L_0x0043
            return
        L_0x0043:
            long r14 = r25.k()
        L_0x0047:
            if (r0 < 0) goto L_0x00a6
        L_0x0049:
            int r5 = r9.sizeCtl
            long r0 = (long) r5
            int r2 = (r14 > r0 ? 1 : (r14 == r0 ? 0 : -1))
            if (r2 < 0) goto L_0x00a6
            j$.util.concurrent.l[] r0 = r9.a
            if (r0 == 0) goto L_0x00a6
            int r1 = r0.length
            r2 = 1073741824(0x40000000, float:2.0)
            if (r1 >= r2) goto L_0x00a6
            int r1 = java.lang.Integer.numberOfLeadingZeros(r1)
            r2 = 32768(0x8000, float:4.5918E-41)
            r1 = r1 | r2
            if (r5 >= 0) goto L_0x008c
            int r2 = r5 >>> 16
            if (r2 != r1) goto L_0x00a6
            int r2 = r1 + 1
            if (r5 == r2) goto L_0x00a6
            r2 = 65535(0xffff, float:9.1834E-41)
            int r1 = r1 + r2
            if (r5 == r1) goto L_0x00a6
            j$.util.concurrent.l[] r7 = r9.b
            if (r7 == 0) goto L_0x00a6
            int r1 = r9.transferIndex
            if (r1 > 0) goto L_0x007a
            goto L_0x00a6
        L_0x007a:
            j$.sun.misc.b r1 = h
            long r3 = i
            int r6 = r5 + 1
            r2 = r25
            boolean r1 = r1.c(r2, r3, r5, r6)
            if (r1 == 0) goto L_0x00a1
            r9.n(r0, r7)
            goto L_0x00a1
        L_0x008c:
            j$.sun.misc.b r2 = h
            long r3 = i
            int r1 = r1 << 16
            int r6 = r1 + 2
            r1 = r2
            r2 = r25
            boolean r1 = r1.c(r2, r3, r5, r6)
            if (r1 == 0) goto L_0x00a1
            r1 = 0
            r9.n(r0, r1)
        L_0x00a1:
            long r14 = r25.k()
            goto L_0x0049
        L_0x00a6:
            return
        L_0x00a7:
            r1 = 1
        L_0x00a8:
            int r0 = j$.util.concurrent.A.c()
            if (r0 != 0) goto L_0x00b6
            j$.util.concurrent.A.g()
            int r0 = j$.util.concurrent.A.c()
            r1 = 1
        L_0x00b6:
            r12 = 0
            r15 = r1
            r16 = 0
        L_0x00ba:
            j$.util.concurrent.c[] r7 = r9.c
            if (r7 == 0) goto L_0x0158
            int r8 = r7.length
            if (r8 <= 0) goto L_0x0158
            int r1 = r8 + -1
            r1 = r1 & r0
            r1 = r7[r1]
            if (r1 != 0) goto L_0x0100
            int r1 = r9.cellsBusy
            if (r1 != 0) goto L_0x0150
            j$.util.concurrent.c r7 = new j$.util.concurrent.c
            r7.<init>(r10)
            int r1 = r9.cellsBusy
            if (r1 != 0) goto L_0x0150
            j$.sun.misc.b r1 = h
            long r3 = l
            r5 = 0
            r6 = 1
            r2 = r25
            boolean r1 = r1.c(r2, r3, r5, r6)
            if (r1 == 0) goto L_0x0150
            j$.util.concurrent.c[] r1 = r9.c     // Catch:{ all -> 0x00fc }
            if (r1 == 0) goto L_0x00f5
            int r2 = r1.length     // Catch:{ all -> 0x00fc }
            if (r2 <= 0) goto L_0x00f5
            int r2 = r2 + -1
            r2 = r2 & r0
            r3 = r1[r2]     // Catch:{ all -> 0x00fc }
            if (r3 != 0) goto L_0x00f5
            r1[r2] = r7     // Catch:{ all -> 0x00fc }
            r1 = 1
            goto L_0x00f6
        L_0x00f5:
            r1 = 0
        L_0x00f6:
            r9.cellsBusy = r12
            if (r1 == 0) goto L_0x00ba
            goto L_0x019b
        L_0x00fc:
            r0 = move-exception
            r9.cellsBusy = r12
            throw r0
        L_0x0100:
            if (r15 != 0) goto L_0x0104
            r15 = 1
            goto L_0x0152
        L_0x0104:
            j$.sun.misc.b r2 = h
            long r19 = m
            long r3 = r1.value
            long r23 = r3 + r10
            r17 = r2
            r18 = r1
            r21 = r3
            boolean r1 = r17.d(r18, r19, r21, r23)
            if (r1 == 0) goto L_0x011a
            goto L_0x019b
        L_0x011a:
            j$.util.concurrent.c[] r1 = r9.c
            if (r1 != r7) goto L_0x0150
            int r1 = g
            if (r8 < r1) goto L_0x0123
            goto L_0x0150
        L_0x0123:
            if (r16 != 0) goto L_0x0128
            r16 = 1
            goto L_0x0152
        L_0x0128:
            int r1 = r9.cellsBusy
            if (r1 != 0) goto L_0x0152
            long r3 = l
            r5 = 0
            r6 = 1
            r1 = r2
            r2 = r25
            boolean r1 = r1.c(r2, r3, r5, r6)
            if (r1 == 0) goto L_0x0152
            j$.util.concurrent.c[] r1 = r9.c     // Catch:{ all -> 0x014c }
            if (r1 != r7) goto L_0x0147
            int r1 = r8 << 1
            java.lang.Object[] r1 = java.util.Arrays.copyOf(r7, r1)     // Catch:{ all -> 0x014c }
            j$.util.concurrent.c[] r1 = (j$.util.concurrent.c[]) r1     // Catch:{ all -> 0x014c }
            r9.c = r1     // Catch:{ all -> 0x014c }
        L_0x0147:
            r9.cellsBusy = r12
            r1 = r15
            goto L_0x00b6
        L_0x014c:
            r0 = move-exception
            r9.cellsBusy = r12
            throw r0
        L_0x0150:
            r16 = 0
        L_0x0152:
            int r0 = j$.util.concurrent.A.a(r0)
            goto L_0x00ba
        L_0x0158:
            int r1 = r9.cellsBusy
            if (r1 != 0) goto L_0x018b
            j$.util.concurrent.c[] r1 = r9.c
            if (r1 != r7) goto L_0x018b
            j$.sun.misc.b r1 = h
            long r3 = l
            r5 = 0
            r6 = 1
            r2 = r25
            boolean r1 = r1.c(r2, r3, r5, r6)
            if (r1 == 0) goto L_0x018b
            j$.util.concurrent.c[] r1 = r9.c     // Catch:{ all -> 0x0187 }
            if (r1 != r7) goto L_0x0181
            j$.util.concurrent.c[] r1 = new j$.util.concurrent.c[r13]     // Catch:{ all -> 0x0187 }
            r2 = r0 & 1
            j$.util.concurrent.c r3 = new j$.util.concurrent.c     // Catch:{ all -> 0x0187 }
            r3.<init>(r10)     // Catch:{ all -> 0x0187 }
            r1[r2] = r3     // Catch:{ all -> 0x0187 }
            r9.c = r1     // Catch:{ all -> 0x0187 }
            r1 = 1
            goto L_0x0182
        L_0x0181:
            r1 = 0
        L_0x0182:
            r9.cellsBusy = r12
            if (r1 == 0) goto L_0x00ba
            goto L_0x019b
        L_0x0187:
            r0 = move-exception
            r9.cellsBusy = r12
            throw r0
        L_0x018b:
            j$.sun.misc.b r1 = h
            long r3 = k
            long r5 = r9.baseCount
            long r7 = r5 + r10
            r2 = r25
            boolean r1 = r1.d(r2, r3, r5, r7)
            if (r1 == 0) goto L_0x00ba
        L_0x019b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.util.concurrent.ConcurrentHashMap.a(long, int):void");
    }

    static final boolean b(l[] lVarArr, int i2, l lVar) {
        return h.e(lVarArr, (((long) i2) << o) + ((long) n), lVar);
    }

    static Class c(Object obj) {
        Type[] actualTypeArguments;
        if (!(obj instanceof Comparable)) {
            return null;
        }
        Class<?> cls = obj.getClass();
        if (cls == String.class) {
            return cls;
        }
        Type[] genericInterfaces = cls.getGenericInterfaces();
        if (genericInterfaces == null) {
            return null;
        }
        for (Type type : genericInterfaces) {
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                if (parameterizedType.getRawType() == Comparable.class && (actualTypeArguments = parameterizedType.getActualTypeArguments()) != null && actualTypeArguments.length == 1 && actualTypeArguments[0] == cls) {
                    return cls;
                }
            }
        }
        return null;
    }

    static int d(Class cls, Object obj, Object obj2) {
        if (obj2 == null || obj2.getClass() != cls) {
            return 0;
        }
        return ((Comparable) obj).compareTo(obj2);
    }

    /* JADX INFO: finally extract failed */
    private final l[] f() {
        while (true) {
            l[] lVarArr = this.a;
            if (lVarArr != null && lVarArr.length != 0) {
                return lVarArr;
            }
            int i2 = this.sizeCtl;
            if (i2 < 0) {
                Thread.yield();
            } else {
                if (h.c(this, i, i2, -1)) {
                    try {
                        l[] lVarArr2 = this.a;
                        if (lVarArr2 == null || lVarArr2.length == 0) {
                            int i3 = i2 > 0 ? i2 : 16;
                            l[] lVarArr3 = new l[i3];
                            this.a = lVarArr3;
                            i2 = i3 - (i3 >>> 2);
                            lVarArr2 = lVarArr3;
                        }
                        this.sizeCtl = i2;
                        return lVarArr2;
                    } catch (Throwable th) {
                        this.sizeCtl = i2;
                        throw th;
                    }
                }
            }
        }
    }

    static final void i(l[] lVarArr, int i2, l lVar) {
        h.l(lVarArr, (((long) i2) << o) + ((long) n), lVar);
    }

    static final int j(int i2) {
        return (i2 ^ (i2 >>> 16)) & ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
    }

    static final l l(l[] lVarArr, int i2) {
        return (l) h.g(lVarArr, (((long) i2) << o) + ((long) n));
    }

    private static final int m(int i2) {
        int numberOfLeadingZeros = -1 >>> Integer.numberOfLeadingZeros(i2 - 1);
        if (numberOfLeadingZeros < 0) {
            return 1;
        }
        if (numberOfLeadingZeros >= 1073741824) {
            return 1073741824;
        }
        return 1 + numberOfLeadingZeros;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v7, resolved type: j$.util.concurrent.s} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v8, resolved type: j$.util.concurrent.s} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v9, resolved type: j$.util.concurrent.s} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v10, resolved type: j$.util.concurrent.s} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v9, resolved type: j$.util.concurrent.l} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v10, resolved type: j$.util.concurrent.s} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v11, resolved type: j$.util.concurrent.s} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v11, resolved type: j$.util.concurrent.s} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v12, resolved type: j$.util.concurrent.s} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v10, resolved type: j$.util.concurrent.s} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v13, resolved type: j$.util.concurrent.s} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v13, resolved type: j$.util.concurrent.s} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v14, resolved type: j$.util.concurrent.s} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v15, resolved type: j$.util.concurrent.s} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v14, resolved type: j$.util.concurrent.s} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v15, resolved type: j$.util.concurrent.s} */
    /* JADX WARNING: type inference failed for: r6v12, types: [j$.util.concurrent.l] */
    /* JADX WARNING: type inference failed for: r13v11, types: [j$.util.concurrent.l] */
    /* JADX WARNING: type inference failed for: r6v17, types: [j$.util.concurrent.l] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void n(j$.util.concurrent.l[] r31, j$.util.concurrent.l[] r32) {
        /*
            r30 = this;
            r7 = r30
            r0 = r31
            int r8 = r0.length
            int r1 = g
            r9 = 1
            if (r1 <= r9) goto L_0x000e
            int r2 = r8 >>> 3
            int r2 = r2 / r1
            goto L_0x000f
        L_0x000e:
            r2 = r8
        L_0x000f:
            r10 = 16
            if (r2 >= r10) goto L_0x0016
            r11 = 16
            goto L_0x0017
        L_0x0016:
            r11 = r2
        L_0x0017:
            if (r32 != 0) goto L_0x0029
            int r1 = r8 << 1
            j$.util.concurrent.l[] r1 = new j$.util.concurrent.l[r1]     // Catch:{ all -> 0x0023 }
            r7.b = r1
            r7.transferIndex = r8
            r12 = r1
            goto L_0x002b
        L_0x0023:
            r0 = 2147483647(0x7fffffff, float:NaN)
            r7.sizeCtl = r0
            return
        L_0x0029:
            r12 = r32
        L_0x002b:
            int r13 = r12.length
            j$.util.concurrent.g r14 = new j$.util.concurrent.g
            r14.<init>(r12)
            r4 = r0
            r3 = r7
            r5 = 0
            r6 = 0
            r16 = 1
            r17 = 0
        L_0x0039:
            r1 = -1
            if (r16 == 0) goto L_0x008d
            int r5 = r5 + -1
            if (r5 >= r6) goto L_0x007c
            if (r17 == 0) goto L_0x0043
            goto L_0x007c
        L_0x0043:
            int r2 = r3.transferIndex
            if (r2 > 0) goto L_0x004c
            r22 = r3
            r15 = r4
            r5 = -1
            goto L_0x0087
        L_0x004c:
            j$.sun.misc.b r1 = h
            long r18 = j
            if (r2 <= r11) goto L_0x0055
            int r20 = r2 - r11
            goto L_0x0057
        L_0x0055:
            r20 = 0
        L_0x0057:
            r21 = r2
            r2 = r30
            r22 = r3
            r15 = r4
            r3 = r18
            r18 = r5
            r5 = r21
            r19 = r6
            r6 = r20
            boolean r1 = r1.c(r2, r3, r5, r6)
            if (r1 == 0) goto L_0x0074
            int r2 = r21 + -1
            r5 = r2
            r6 = r20
            goto L_0x0087
        L_0x0074:
            r4 = r15
            r5 = r18
            r6 = r19
            r3 = r22
            goto L_0x0039
        L_0x007c:
            r22 = r3
            r15 = r4
            r18 = r5
            r19 = r6
            r5 = r18
            r6 = r19
        L_0x0087:
            r4 = r15
            r3 = r22
            r16 = 0
            goto L_0x0039
        L_0x008d:
            r22 = r3
            r15 = r4
            r19 = r6
            r2 = 0
            if (r5 < 0) goto L_0x01c3
            if (r5 >= r8) goto L_0x01c3
            int r3 = r5 + r8
            if (r3 < r13) goto L_0x009d
            goto L_0x01c3
        L_0x009d:
            j$.util.concurrent.l r4 = l(r15, r5)
            if (r4 != 0) goto L_0x00b3
            boolean r1 = b(r15, r5, r14)
            r16 = r1
            r21 = r11
            r7 = r14
            r4 = r15
            r3 = r22
            r22 = r13
            goto L_0x01ba
        L_0x00b3:
            int r6 = r4.a
            if (r6 != r1) goto L_0x00c7
            r9 = r7
            r21 = r11
            r7 = r14
            r4 = r15
            r3 = r22
            r2 = 16
            r10 = 1
            r16 = 1
            r22 = r13
            goto L_0x020b
        L_0x00c7:
            monitor-enter(r4)
            j$.util.concurrent.l r1 = l(r15, r5)     // Catch:{ all -> 0x01c0 }
            if (r1 != r4) goto L_0x01b1
            if (r6 < 0) goto L_0x0123
            r1 = r6 & r8
            j$.util.concurrent.l r6 = r4.d     // Catch:{ all -> 0x01c0 }
            r10 = r4
        L_0x00d5:
            if (r6 == 0) goto L_0x00e2
            int r9 = r6.a     // Catch:{ all -> 0x01c0 }
            r9 = r9 & r8
            if (r9 == r1) goto L_0x00de
            r10 = r6
            r1 = r9
        L_0x00de:
            j$.util.concurrent.l r6 = r6.d     // Catch:{ all -> 0x01c0 }
            r9 = 1
            goto L_0x00d5
        L_0x00e2:
            if (r1 != 0) goto L_0x00e6
            r1 = r10
            goto L_0x00e8
        L_0x00e6:
            r1 = r2
            r2 = r10
        L_0x00e8:
            r6 = r4
        L_0x00e9:
            if (r6 == r10) goto L_0x0113
            int r9 = r6.a     // Catch:{ all -> 0x01c0 }
            r16 = r10
            java.lang.Object r10 = r6.b     // Catch:{ all -> 0x01c0 }
            r21 = r11
            java.lang.Object r11 = r6.c     // Catch:{ all -> 0x01c0 }
            r22 = r9 & r8
            if (r22 != 0) goto L_0x0102
            r22 = r13
            j$.util.concurrent.l r13 = new j$.util.concurrent.l     // Catch:{ all -> 0x01c0 }
            r13.<init>(r9, r10, r11, r1)     // Catch:{ all -> 0x01c0 }
            r1 = r13
            goto L_0x010a
        L_0x0102:
            r22 = r13
            j$.util.concurrent.l r13 = new j$.util.concurrent.l     // Catch:{ all -> 0x01c0 }
            r13.<init>(r9, r10, r11, r2)     // Catch:{ all -> 0x01c0 }
            r2 = r13
        L_0x010a:
            j$.util.concurrent.l r6 = r6.d     // Catch:{ all -> 0x01c0 }
            r10 = r16
            r11 = r21
            r13 = r22
            goto L_0x00e9
        L_0x0113:
            r21 = r11
            r22 = r13
            i(r12, r5, r1)     // Catch:{ all -> 0x01c0 }
            i(r12, r3, r2)     // Catch:{ all -> 0x01c0 }
            i(r15, r5, r14)     // Catch:{ all -> 0x01c0 }
            r7 = r14
            goto L_0x01ae
        L_0x0123:
            r21 = r11
            r22 = r13
            boolean r1 = r4 instanceof j$.util.concurrent.r     // Catch:{ all -> 0x01c0 }
            if (r1 == 0) goto L_0x01b5
            r1 = r4
            j$.util.concurrent.r r1 = (j$.util.concurrent.r) r1     // Catch:{ all -> 0x01c0 }
            j$.util.concurrent.s r6 = r1.f     // Catch:{ all -> 0x01c0 }
            r9 = r2
            r10 = r9
            r11 = r6
            r13 = 0
            r15 = 0
            r6 = r10
        L_0x0136:
            if (r11 == 0) goto L_0x0179
            r16 = r1
            int r1 = r11.a     // Catch:{ all -> 0x01c0 }
            j$.util.concurrent.s r7 = new j$.util.concurrent.s     // Catch:{ all -> 0x01c0 }
            java.lang.Object r0 = r11.b     // Catch:{ all -> 0x01c0 }
            r29 = r14
            java.lang.Object r14 = r11.c     // Catch:{ all -> 0x01c0 }
            r27 = 0
            r28 = 0
            r23 = r7
            r24 = r1
            r25 = r0
            r26 = r14
            r23.<init>(r24, r25, r26, r27, r28)     // Catch:{ all -> 0x01c0 }
            r0 = r1 & r8
            if (r0 != 0) goto L_0x0163
            r7.h = r10     // Catch:{ all -> 0x01c0 }
            if (r10 != 0) goto L_0x015d
            r2 = r7
            goto L_0x015f
        L_0x015d:
            r10.d = r7     // Catch:{ all -> 0x01c0 }
        L_0x015f:
            int r13 = r13 + 1
            r10 = r7
            goto L_0x016e
        L_0x0163:
            r7.h = r9     // Catch:{ all -> 0x01c0 }
            if (r9 != 0) goto L_0x0169
            r6 = r7
            goto L_0x016b
        L_0x0169:
            r9.d = r7     // Catch:{ all -> 0x01c0 }
        L_0x016b:
            int r15 = r15 + 1
            r9 = r7
        L_0x016e:
            j$.util.concurrent.l r11 = r11.d     // Catch:{ all -> 0x01c0 }
            r7 = r30
            r0 = r31
            r1 = r16
            r14 = r29
            goto L_0x0136
        L_0x0179:
            r16 = r1
            r29 = r14
            r0 = 6
            if (r13 > r0) goto L_0x0185
            j$.util.concurrent.l r1 = q(r2)     // Catch:{ all -> 0x01c0 }
            goto L_0x018f
        L_0x0185:
            if (r15 == 0) goto L_0x018d
            j$.util.concurrent.r r1 = new j$.util.concurrent.r     // Catch:{ all -> 0x01c0 }
            r1.<init>(r2)     // Catch:{ all -> 0x01c0 }
            goto L_0x018f
        L_0x018d:
            r1 = r16
        L_0x018f:
            if (r15 > r0) goto L_0x0196
            j$.util.concurrent.l r0 = q(r6)     // Catch:{ all -> 0x01c0 }
            goto L_0x01a0
        L_0x0196:
            if (r13 == 0) goto L_0x019e
            j$.util.concurrent.r r0 = new j$.util.concurrent.r     // Catch:{ all -> 0x01c0 }
            r0.<init>(r6)     // Catch:{ all -> 0x01c0 }
            goto L_0x01a0
        L_0x019e:
            r0 = r16
        L_0x01a0:
            i(r12, r5, r1)     // Catch:{ all -> 0x01c0 }
            i(r12, r3, r0)     // Catch:{ all -> 0x01c0 }
            r0 = r31
            r7 = r29
            i(r0, r5, r7)     // Catch:{ all -> 0x01c0 }
            r15 = r0
        L_0x01ae:
            r16 = 1
            goto L_0x01b6
        L_0x01b1:
            r21 = r11
            r22 = r13
        L_0x01b5:
            r7 = r14
        L_0x01b6:
            monitor-exit(r4)     // Catch:{ all -> 0x01c0 }
            r3 = r30
            r4 = r15
        L_0x01ba:
            r2 = 16
            r10 = 1
            r9 = r30
            goto L_0x020b
        L_0x01c0:
            r0 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x01c0 }
            throw r0
        L_0x01c3:
            r21 = r11
            r22 = r13
            r7 = r14
            if (r17 == 0) goto L_0x01d9
            r9 = r30
            r9.b = r2
            r9.a = r12
            int r0 = r8 << 1
            r10 = 1
            int r1 = r8 >>> 1
            int r0 = r0 - r1
            r9.sizeCtl = r0
            return
        L_0x01d9:
            r10 = 1
            r9 = r30
            j$.sun.misc.b r1 = h
            long r3 = i
            int r11 = r9.sizeCtl
            int r6 = r11 + -1
            r2 = r30
            r13 = r5
            r5 = r11
            boolean r1 = r1.c(r2, r3, r5, r6)
            if (r1 == 0) goto L_0x0206
            int r11 = r11 + -2
            int r1 = java.lang.Integer.numberOfLeadingZeros(r8)
            r2 = 32768(0x8000, float:4.5918E-41)
            r1 = r1 | r2
            r2 = 16
            int r1 = r1 << r2
            if (r11 == r1) goto L_0x01fe
            return
        L_0x01fe:
            r5 = r8
            r3 = r9
            r4 = r15
            r16 = 1
            r17 = 1
            goto L_0x020b
        L_0x0206:
            r2 = 16
            r3 = r9
            r5 = r13
            r4 = r15
        L_0x020b:
            r14 = r7
            r7 = r9
            r6 = r19
            r11 = r21
            r13 = r22
            r9 = 1
            r10 = 16
            goto L_0x0039
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.util.concurrent.ConcurrentHashMap.n(j$.util.concurrent.l[], j$.util.concurrent.l[]):void");
    }

    private final void o(l[] lVarArr, int i2) {
        int length = lVarArr.length;
        if (length < 64) {
            p(length << 1);
            return;
        }
        l l2 = l(lVarArr, i2);
        if (l2 != null && l2.a >= 0) {
            synchronized (l2) {
                if (l(lVarArr, i2) == l2) {
                    s sVar = null;
                    l lVar = l2;
                    s sVar2 = null;
                    while (lVar != null) {
                        s sVar3 = new s(lVar.a, lVar.b, lVar.c, (s) null, (s) null);
                        sVar3.h = sVar2;
                        if (sVar2 == null) {
                            sVar = sVar3;
                        } else {
                            sVar2.d = sVar3;
                        }
                        lVar = lVar.d;
                        sVar2 = sVar3;
                    }
                    i(lVarArr, i2, new r(sVar));
                }
            }
        }
    }

    private final void p(int i2) {
        int length;
        int m2 = i2 >= 536870912 ? 1073741824 : m(i2 + (i2 >>> 1) + 1);
        while (true) {
            int i3 = this.sizeCtl;
            if (i3 >= 0) {
                l[] lVarArr = this.a;
                if (lVarArr == null || (length = lVarArr.length) == 0) {
                    int i4 = i3 > m2 ? i3 : m2;
                    if (h.c(this, i, i3, -1)) {
                        try {
                            if (this.a == lVarArr) {
                                this.a = new l[i4];
                                i3 = i4 - (i4 >>> 2);
                            }
                        } finally {
                            this.sizeCtl = i3;
                        }
                    }
                } else if (m2 > i3 && length < 1073741824) {
                    if (lVarArr == this.a) {
                        if (h.c(this, i, i3, ((Integer.numberOfLeadingZeros(length) | 32768) << 16) + 2)) {
                            n(lVarArr, (l[]) null);
                        }
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    static l q(s sVar) {
        l lVar = null;
        l lVar2 = null;
        for (l lVar3 = sVar; lVar3 != null; lVar3 = lVar3.d) {
            l lVar4 = new l(lVar3.a, lVar3.b, lVar3.c);
            if (lVar2 == null) {
                lVar = lVar4;
            } else {
                lVar2.d = lVar4;
            }
            lVar2 = lVar4;
        }
        return lVar;
    }

    private void readObject(ObjectInputStream objectInputStream) {
        long j2;
        boolean z;
        boolean z2;
        Object obj;
        this.sizeCtl = -1;
        objectInputStream.defaultReadObject();
        long j3 = 0;
        long j4 = 0;
        l lVar = null;
        while (true) {
            Object readObject = objectInputStream.readObject();
            Object readObject2 = objectInputStream.readObject();
            j2 = 1;
            if (readObject != null && readObject2 != null) {
                j4++;
                lVar = new l(j(readObject.hashCode()), readObject, readObject2, lVar);
            }
        }
        if (j4 == 0) {
            this.sizeCtl = 0;
            return;
        }
        long j5 = (long) (((double) (((float) j4) / 0.75f)) + 1.0d);
        int m2 = j5 >= 1073741824 ? 1073741824 : m((int) j5);
        l[] lVarArr = new l[m2];
        int i2 = m2 - 1;
        while (lVar != null) {
            l lVar2 = lVar.d;
            int i3 = lVar.a;
            int i4 = i3 & i2;
            l l2 = l(lVarArr, i4);
            if (l2 == null) {
                z = true;
            } else {
                Object obj2 = lVar.b;
                if (l2.a >= 0) {
                    l lVar3 = l2;
                    int i5 = 0;
                    while (true) {
                        if (lVar3 == null) {
                            z2 = true;
                            break;
                        } else if (lVar3.a != i3 || ((obj = lVar3.b) != obj2 && (obj == null || !obj2.equals(obj)))) {
                            i5++;
                            lVar3 = lVar3.d;
                        }
                    }
                    z2 = false;
                    if (!z2 || i5 < 8) {
                        z = z2;
                    } else {
                        long j6 = j3 + 1;
                        lVar.d = l2;
                        l lVar4 = lVar;
                        s sVar = null;
                        s sVar2 = null;
                        while (lVar4 != null) {
                            long j7 = j6;
                            s sVar3 = new s(lVar4.a, lVar4.b, lVar4.c, (s) null, (s) null);
                            sVar3.h = sVar2;
                            if (sVar2 == null) {
                                sVar = sVar3;
                            } else {
                                sVar2.d = sVar3;
                            }
                            lVar4 = lVar4.d;
                            sVar2 = sVar3;
                            j6 = j7;
                        }
                        i(lVarArr, i4, new r(sVar));
                        j3 = j6;
                    }
                } else if (((r) l2).e(i3, obj2, lVar.c) == null) {
                    j3 += j2;
                }
                z = false;
            }
            j2 = 1;
            if (z) {
                j3++;
                lVar.d = l2;
                i(lVarArr, i4, lVar);
            }
            lVar = lVar2;
        }
        this.a = lVarArr;
        this.sizeCtl = m2 - (m2 >>> 2);
        this.baseCount = j3;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) {
        int i2 = 1;
        int i3 = 0;
        while (i2 < 16) {
            i3++;
            i2 <<= 1;
        }
        int i4 = 32 - i3;
        int i5 = i2 - 1;
        o[] oVarArr = new o[16];
        for (int i6 = 0; i6 < 16; i6++) {
            oVarArr[i6] = new o();
        }
        ObjectOutputStream.PutField putFields = objectOutputStream.putFields();
        putFields.put("segments", oVarArr);
        putFields.put("segmentShift", i4);
        putFields.put("segmentMask", i5);
        objectOutputStream.writeFields();
        l[] lVarArr = this.a;
        if (lVarArr != null) {
            q qVar = new q(lVarArr, lVarArr.length, 0, lVarArr.length);
            while (true) {
                l a2 = qVar.a();
                if (a2 == null) {
                    break;
                }
                objectOutputStream.writeObject(a2.b);
                objectOutputStream.writeObject(a2.c);
            }
        }
        objectOutputStream.writeObject((Object) null);
        objectOutputStream.writeObject((Object) null);
    }

    public void clear() {
        l[] lVarArr = this.a;
        long j2 = 0;
        loop0:
        while (true) {
            int i2 = 0;
            while (lVarArr != null && i2 < lVarArr.length) {
                l l2 = l(lVarArr, i2);
                if (l2 == null) {
                    i2++;
                } else {
                    int i3 = l2.a;
                    if (i3 == -1) {
                        lVarArr = e(lVarArr, l2);
                    } else {
                        synchronized (l2) {
                            if (l(lVarArr, i2) == l2) {
                                for (l lVar = i3 >= 0 ? l2 : l2 instanceof r ? ((r) l2).f : null; lVar != null; lVar = lVar.d) {
                                    j2--;
                                }
                                i(lVarArr, i2, (l) null);
                                i2++;
                            }
                        }
                    }
                }
            }
        }
        if (j2 != 0) {
            a(j2, -1);
        }
    }

    /* JADX INFO: finally extract failed */
    public final Object compute(Object obj, BiFunction biFunction) {
        Object obj2;
        Object obj3;
        int i2;
        l lVar;
        if (obj == null || biFunction == null) {
            throw null;
        }
        int j2 = j(obj.hashCode());
        l[] lVarArr = this.a;
        int i3 = 0;
        Object obj4 = null;
        int i4 = 0;
        while (true) {
            if (lVarArr != null) {
                int length = lVarArr.length;
                if (length != 0) {
                    int i5 = (length - 1) & j2;
                    l l2 = l(lVarArr, i5);
                    if (l2 == null) {
                        m mVar = new m();
                        synchronized (mVar) {
                            if (b(lVarArr, i5, mVar)) {
                                try {
                                    Object apply = biFunction.apply(obj, null);
                                    if (apply != null) {
                                        lVar = new l(j2, obj, apply);
                                        i2 = 1;
                                    } else {
                                        i2 = i3;
                                        lVar = null;
                                    }
                                    i(lVarArr, i5, lVar);
                                    i3 = i2;
                                    obj4 = apply;
                                    i4 = 1;
                                } catch (Throwable th) {
                                    i(lVarArr, i5, (l) null);
                                    throw th;
                                }
                            }
                        }
                        if (i4 != 0) {
                            break;
                        }
                    } else {
                        int i6 = l2.a;
                        if (i6 == -1) {
                            lVarArr = e(lVarArr, l2);
                        } else {
                            synchronized (l2) {
                                try {
                                    if (l(lVarArr, i5) == l2) {
                                        if (i6 >= 0) {
                                            l lVar2 = null;
                                            l lVar3 = l2;
                                            int i7 = 1;
                                            while (true) {
                                                if (lVar3.a != j2 || ((obj3 = lVar3.b) != obj && (obj3 == null || !obj.equals(obj3)))) {
                                                    l lVar4 = lVar3.d;
                                                    if (lVar4 == null) {
                                                        Object apply2 = biFunction.apply(obj, null);
                                                        if (apply2 == null) {
                                                            obj2 = apply2;
                                                        } else if (lVar3.d == null) {
                                                            lVar3.d = new l(j2, obj, apply2);
                                                            obj2 = apply2;
                                                            i3 = 1;
                                                        } else {
                                                            throw new IllegalStateException("Recursive update");
                                                        }
                                                    } else {
                                                        i7++;
                                                        l lVar5 = lVar4;
                                                        lVar2 = lVar3;
                                                        lVar3 = lVar5;
                                                    }
                                                }
                                            }
                                            obj2 = biFunction.apply(obj, lVar3.c);
                                            if (obj2 != null) {
                                                lVar3.c = obj2;
                                            } else {
                                                l lVar6 = lVar3.d;
                                                if (lVar2 != null) {
                                                    lVar2.d = lVar6;
                                                } else {
                                                    i(lVarArr, i5, lVar6);
                                                }
                                                i3 = -1;
                                            }
                                            i4 = i7;
                                            obj4 = obj2;
                                        } else if (l2 instanceof r) {
                                            r rVar = (r) l2;
                                            s sVar = rVar.e;
                                            s b2 = sVar != null ? sVar.b(j2, obj, (Class) null) : null;
                                            Object apply3 = biFunction.apply(obj, b2 == null ? null : b2.c);
                                            if (apply3 != null) {
                                                if (b2 != null) {
                                                    b2.c = apply3;
                                                } else {
                                                    rVar.e(j2, obj, apply3);
                                                    i3 = 1;
                                                }
                                            } else if (b2 != null) {
                                                if (rVar.f(b2)) {
                                                    i(lVarArr, i5, q(rVar.f));
                                                }
                                                i3 = -1;
                                            }
                                            obj4 = apply3;
                                            i4 = 1;
                                        } else if (l2 instanceof m) {
                                            throw new IllegalStateException("Recursive update");
                                        }
                                    }
                                } catch (Throwable th2) {
                                    while (true) {
                                        throw th2;
                                    }
                                }
                            }
                            if (i4 != 0) {
                                if (i4 >= 8) {
                                    o(lVarArr, i5);
                                }
                            }
                        }
                    }
                }
            }
            lVarArr = f();
        }
        if (i3 != 0) {
            a((long) i3, i4);
        }
        return obj4;
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x00f0, code lost:
        if (r5 == null) goto L_0x00f7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x00f2, code lost:
        a(1, r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:0x00f7, code lost:
        return r5;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object computeIfAbsent(java.lang.Object r12, java.util.function.Function r13) {
        /*
            r11 = this;
            r0 = 0
            if (r12 == 0) goto L_0x0101
            if (r13 == 0) goto L_0x0101
            int r1 = r12.hashCode()
            int r1 = j(r1)
            j$.util.concurrent.l[] r2 = r11.a
            r3 = 0
            r5 = r0
            r4 = 0
        L_0x0012:
            if (r2 == 0) goto L_0x00fb
            int r6 = r2.length
            if (r6 != 0) goto L_0x0019
            goto L_0x00fb
        L_0x0019:
            int r6 = r6 + -1
            r6 = r6 & r1
            j$.util.concurrent.l r7 = l(r2, r6)
            r8 = 1
            if (r7 != 0) goto L_0x004f
            j$.util.concurrent.m r9 = new j$.util.concurrent.m
            r9.<init>()
            monitor-enter(r9)
            boolean r7 = b(r2, r6, r9)     // Catch:{ all -> 0x004c }
            if (r7 == 0) goto L_0x0047
            java.lang.Object r4 = r13.apply(r12)     // Catch:{ all -> 0x0042 }
            if (r4 == 0) goto L_0x003b
            j$.util.concurrent.l r5 = new j$.util.concurrent.l     // Catch:{ all -> 0x0042 }
            r5.<init>(r1, r12, r4)     // Catch:{ all -> 0x0042 }
            goto L_0x003c
        L_0x003b:
            r5 = r0
        L_0x003c:
            i(r2, r6, r5)     // Catch:{ all -> 0x004c }
            r5 = r4
            r4 = 1
            goto L_0x0047
        L_0x0042:
            r12 = move-exception
            i(r2, r6, r0)     // Catch:{ all -> 0x004c }
            throw r12     // Catch:{ all -> 0x004c }
        L_0x0047:
            monitor-exit(r9)     // Catch:{ all -> 0x004c }
            if (r4 == 0) goto L_0x0012
            goto L_0x00f0
        L_0x004c:
            r12 = move-exception
            monitor-exit(r9)     // Catch:{ all -> 0x004c }
            throw r12
        L_0x004f:
            int r9 = r7.a
            r10 = -1
            if (r9 != r10) goto L_0x0059
            j$.util.concurrent.l[] r2 = r11.e(r2, r7)
            goto L_0x0012
        L_0x0059:
            if (r9 != r1) goto L_0x006c
            java.lang.Object r10 = r7.b
            if (r10 == r12) goto L_0x0067
            if (r10 == 0) goto L_0x006c
            boolean r10 = r12.equals(r10)
            if (r10 == 0) goto L_0x006c
        L_0x0067:
            java.lang.Object r10 = r7.c
            if (r10 == 0) goto L_0x006c
            return r10
        L_0x006c:
            monitor-enter(r7)
            j$.util.concurrent.l r10 = l(r2, r6)     // Catch:{ all -> 0x00f8 }
            if (r10 != r7) goto L_0x00e2
            if (r9 < 0) goto L_0x00b2
            r4 = r7
            r5 = 1
        L_0x0077:
            int r9 = r4.a     // Catch:{ all -> 0x00f8 }
            if (r9 != r1) goto L_0x008a
            java.lang.Object r9 = r4.b     // Catch:{ all -> 0x00f8 }
            if (r9 == r12) goto L_0x0087
            if (r9 == 0) goto L_0x008a
            boolean r9 = r12.equals(r9)     // Catch:{ all -> 0x00f8 }
            if (r9 == 0) goto L_0x008a
        L_0x0087:
            java.lang.Object r4 = r4.c     // Catch:{ all -> 0x00f8 }
            goto L_0x00a9
        L_0x008a:
            j$.util.concurrent.l r9 = r4.d     // Catch:{ all -> 0x00f8 }
            if (r9 != 0) goto L_0x00ae
            java.lang.Object r9 = r13.apply(r12)     // Catch:{ all -> 0x00f8 }
            if (r9 == 0) goto L_0x00a8
            j$.util.concurrent.l r10 = r4.d     // Catch:{ all -> 0x00f8 }
            if (r10 != 0) goto L_0x00a0
            j$.util.concurrent.l r10 = new j$.util.concurrent.l     // Catch:{ all -> 0x00f8 }
            r10.<init>(r1, r12, r9)     // Catch:{ all -> 0x00f8 }
            r4.d = r10     // Catch:{ all -> 0x00f8 }
            goto L_0x00ab
        L_0x00a0:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException     // Catch:{ all -> 0x00f8 }
            java.lang.String r13 = "Recursive update"
            r12.<init>(r13)     // Catch:{ all -> 0x00f8 }
            throw r12     // Catch:{ all -> 0x00f8 }
        L_0x00a8:
            r4 = r9
        L_0x00a9:
            r9 = r4
            r8 = 0
        L_0x00ab:
            r4 = r5
            r5 = r9
            goto L_0x00e3
        L_0x00ae:
            int r5 = r5 + 1
            r4 = r9
            goto L_0x0077
        L_0x00b2:
            boolean r9 = r7 instanceof j$.util.concurrent.r     // Catch:{ all -> 0x00f8 }
            if (r9 == 0) goto L_0x00d5
            r4 = r7
            j$.util.concurrent.r r4 = (j$.util.concurrent.r) r4     // Catch:{ all -> 0x00f8 }
            j$.util.concurrent.s r5 = r4.e     // Catch:{ all -> 0x00f8 }
            if (r5 == 0) goto L_0x00c6
            j$.util.concurrent.s r5 = r5.b(r1, r12, r0)     // Catch:{ all -> 0x00f8 }
            if (r5 == 0) goto L_0x00c6
            java.lang.Object r4 = r5.c     // Catch:{ all -> 0x00f8 }
            goto L_0x00d1
        L_0x00c6:
            java.lang.Object r5 = r13.apply(r12)     // Catch:{ all -> 0x00f8 }
            if (r5 == 0) goto L_0x00d0
            r4.e(r1, r12, r5)     // Catch:{ all -> 0x00f8 }
            goto L_0x00d3
        L_0x00d0:
            r4 = r5
        L_0x00d1:
            r5 = r4
            r8 = 0
        L_0x00d3:
            r4 = 2
            goto L_0x00e3
        L_0x00d5:
            boolean r8 = r7 instanceof j$.util.concurrent.m     // Catch:{ all -> 0x00f8 }
            if (r8 != 0) goto L_0x00da
            goto L_0x00e2
        L_0x00da:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException     // Catch:{ all -> 0x00f8 }
            java.lang.String r13 = "Recursive update"
            r12.<init>(r13)     // Catch:{ all -> 0x00f8 }
            throw r12     // Catch:{ all -> 0x00f8 }
        L_0x00e2:
            r8 = 0
        L_0x00e3:
            monitor-exit(r7)     // Catch:{ all -> 0x00f8 }
            if (r4 == 0) goto L_0x0012
            r12 = 8
            if (r4 < r12) goto L_0x00ed
            r11.o(r2, r6)
        L_0x00ed:
            if (r8 != 0) goto L_0x00f0
            return r5
        L_0x00f0:
            if (r5 == 0) goto L_0x00f7
            r12 = 1
            r11.a(r12, r4)
        L_0x00f7:
            return r5
        L_0x00f8:
            r12 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x00f8 }
            throw r12
        L_0x00fb:
            j$.util.concurrent.l[] r2 = r11.f()
            goto L_0x0012
        L_0x0101:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.util.concurrent.ConcurrentHashMap.computeIfAbsent(java.lang.Object, java.util.function.Function):java.lang.Object");
    }

    public final Object computeIfPresent(Object obj, BiFunction biFunction) {
        s b2;
        Object obj2;
        if (obj == null || biFunction == null) {
            throw null;
        }
        int j2 = j(obj.hashCode());
        l[] lVarArr = this.a;
        int i2 = 0;
        Object obj3 = null;
        int i3 = 0;
        while (true) {
            if (lVarArr != null) {
                int length = lVarArr.length;
                if (length != 0) {
                    int i4 = (length - 1) & j2;
                    l l2 = l(lVarArr, i4);
                    if (l2 == null) {
                        break;
                    }
                    int i5 = l2.a;
                    if (i5 == -1) {
                        lVarArr = e(lVarArr, l2);
                    } else {
                        synchronized (l2) {
                            try {
                                if (l(lVarArr, i4) == l2) {
                                    if (i5 >= 0) {
                                        i3 = 1;
                                        l lVar = null;
                                        l lVar2 = l2;
                                        while (true) {
                                            if (lVar2.a != j2 || ((obj2 = lVar2.b) != obj && (obj2 == null || !obj.equals(obj2)))) {
                                                l lVar3 = lVar2.d;
                                                if (lVar3 == null) {
                                                    break;
                                                }
                                                i3++;
                                                l lVar4 = lVar3;
                                                lVar = lVar2;
                                                lVar2 = lVar4;
                                            }
                                        }
                                        obj3 = biFunction.apply(obj, lVar2.c);
                                        if (obj3 != null) {
                                            lVar2.c = obj3;
                                        } else {
                                            l lVar5 = lVar2.d;
                                            if (lVar != null) {
                                                lVar.d = lVar5;
                                            } else {
                                                i(lVarArr, i4, lVar5);
                                            }
                                            i2 = -1;
                                        }
                                    } else if (l2 instanceof r) {
                                        r rVar = (r) l2;
                                        s sVar = rVar.e;
                                        if (!(sVar == null || (b2 = sVar.b(j2, obj, (Class) null)) == null)) {
                                            obj3 = biFunction.apply(obj, b2.c);
                                            if (obj3 != null) {
                                                b2.c = obj3;
                                            } else {
                                                if (rVar.f(b2)) {
                                                    i(lVarArr, i4, q(rVar.f));
                                                }
                                                i2 = -1;
                                            }
                                        }
                                        i3 = 2;
                                    } else if (l2 instanceof m) {
                                        throw new IllegalStateException("Recursive update");
                                    }
                                }
                            } catch (Throwable th) {
                                while (true) {
                                    throw th;
                                }
                            }
                        }
                        if (i3 != 0) {
                            break;
                        }
                    }
                }
            }
            lVarArr = f();
        }
        if (i2 != 0) {
            a((long) i2, i3);
        }
        return obj3;
    }

    public boolean containsKey(Object obj) {
        return get(obj) != null;
    }

    public final boolean containsValue(Object obj) {
        obj.getClass();
        l[] lVarArr = this.a;
        if (lVarArr != null) {
            q qVar = new q(lVarArr, lVarArr.length, 0, lVarArr.length);
            while (true) {
                l a2 = qVar.a();
                if (a2 == null) {
                    break;
                }
                Object obj2 = a2.c;
                if (obj2 == obj) {
                    return true;
                }
                if (obj2 != null && obj.equals(obj2)) {
                    return true;
                }
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public final l[] e(l[] lVarArr, l lVar) {
        l[] lVarArr2;
        int i2;
        if (!(lVar instanceof g) || (lVarArr2 = ((g) lVar).e) == null) {
            return this.a;
        }
        int numberOfLeadingZeros = Integer.numberOfLeadingZeros(lVarArr.length) | 32768;
        while (true) {
            if (lVarArr2 != this.b || this.a != lVarArr || (i2 = this.sizeCtl) >= 0 || (i2 >>> 16) != numberOfLeadingZeros || i2 == numberOfLeadingZeros + 1 || i2 == SupportMenu.USER_MASK + numberOfLeadingZeros || this.transferIndex <= 0) {
                break;
            }
            if (h.c(this, i, i2, i2 + 1)) {
                n(lVarArr, lVarArr2);
                break;
            }
        }
        return lVarArr2;
    }

    public Set<Map.Entry<K, V>> entrySet() {
        e eVar = this.f;
        if (eVar != null) {
            return eVar;
        }
        e eVar2 = new e(this);
        this.f = eVar2;
        return eVar2;
    }

    public final boolean equals(Object obj) {
        Object value;
        Object obj2;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Map)) {
            return false;
        }
        Map map = (Map) obj;
        l[] lVarArr = this.a;
        int length = lVarArr == null ? 0 : lVarArr.length;
        q qVar = new q(lVarArr, length, 0, length);
        while (true) {
            l a2 = qVar.a();
            if (a2 != null) {
                Object obj3 = a2.c;
                Object obj4 = map.get(a2.b);
                if (obj4 == null || (obj4 != obj3 && !obj4.equals(obj3))) {
                    return false;
                }
            } else {
                for (Map.Entry entry : map.entrySet()) {
                    Object key = entry.getKey();
                    if (key == null || (value = entry.getValue()) == null || (obj2 = get(key)) == null || (value != obj2 && !value.equals(obj2))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public final void forEach(BiConsumer biConsumer) {
        biConsumer.getClass();
        l[] lVarArr = this.a;
        if (lVarArr != null) {
            q qVar = new q(lVarArr, lVarArr.length, 0, lVarArr.length);
            while (true) {
                l a2 = qVar.a();
                if (a2 != null) {
                    biConsumer.accept(a2.b, a2.c);
                } else {
                    return;
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x00b1, code lost:
        a(1, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x00b6, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object g(java.lang.Object r9, java.lang.Object r10, boolean r11) {
        /*
            r8 = this;
            r0 = 0
            if (r9 == 0) goto L_0x00c0
            if (r10 == 0) goto L_0x00c0
            int r1 = r9.hashCode()
            int r1 = j(r1)
            j$.util.concurrent.l[] r2 = r8.a
            r3 = 0
        L_0x0010:
            if (r2 == 0) goto L_0x00ba
            int r4 = r2.length
            if (r4 != 0) goto L_0x0017
            goto L_0x00ba
        L_0x0017:
            int r4 = r4 + -1
            r4 = r4 & r1
            j$.util.concurrent.l r5 = l(r2, r4)
            if (r5 != 0) goto L_0x002d
            j$.util.concurrent.l r5 = new j$.util.concurrent.l
            r5.<init>(r1, r9, r10)
            boolean r4 = b(r2, r4, r5)
            if (r4 == 0) goto L_0x0010
            goto L_0x00b1
        L_0x002d:
            int r6 = r5.a
            r7 = -1
            if (r6 != r7) goto L_0x0037
            j$.util.concurrent.l[] r2 = r8.e(r2, r5)
            goto L_0x0010
        L_0x0037:
            if (r11 == 0) goto L_0x004c
            if (r6 != r1) goto L_0x004c
            java.lang.Object r7 = r5.b
            if (r7 == r9) goto L_0x0047
            if (r7 == 0) goto L_0x004c
            boolean r7 = r9.equals(r7)
            if (r7 == 0) goto L_0x004c
        L_0x0047:
            java.lang.Object r7 = r5.c
            if (r7 == 0) goto L_0x004c
            return r7
        L_0x004c:
            monitor-enter(r5)
            j$.util.concurrent.l r7 = l(r2, r4)     // Catch:{ all -> 0x00b7 }
            if (r7 != r5) goto L_0x00a3
            if (r6 < 0) goto L_0x007e
            r3 = 1
            r6 = r5
        L_0x0057:
            int r7 = r6.a     // Catch:{ all -> 0x00b7 }
            if (r7 != r1) goto L_0x006e
            java.lang.Object r7 = r6.b     // Catch:{ all -> 0x00b7 }
            if (r7 == r9) goto L_0x0067
            if (r7 == 0) goto L_0x006e
            boolean r7 = r9.equals(r7)     // Catch:{ all -> 0x00b7 }
            if (r7 == 0) goto L_0x006e
        L_0x0067:
            java.lang.Object r7 = r6.c     // Catch:{ all -> 0x00b7 }
            if (r11 != 0) goto L_0x00a4
            r6.c = r10     // Catch:{ all -> 0x00b7 }
            goto L_0x00a4
        L_0x006e:
            j$.util.concurrent.l r7 = r6.d     // Catch:{ all -> 0x00b7 }
            if (r7 != 0) goto L_0x007a
            j$.util.concurrent.l r7 = new j$.util.concurrent.l     // Catch:{ all -> 0x00b7 }
            r7.<init>(r1, r9, r10)     // Catch:{ all -> 0x00b7 }
            r6.d = r7     // Catch:{ all -> 0x00b7 }
            goto L_0x00a3
        L_0x007a:
            int r3 = r3 + 1
            r6 = r7
            goto L_0x0057
        L_0x007e:
            boolean r6 = r5 instanceof j$.util.concurrent.r     // Catch:{ all -> 0x00b7 }
            if (r6 == 0) goto L_0x0096
            r3 = r5
            j$.util.concurrent.r r3 = (j$.util.concurrent.r) r3     // Catch:{ all -> 0x00b7 }
            j$.util.concurrent.s r3 = r3.e(r1, r9, r10)     // Catch:{ all -> 0x00b7 }
            if (r3 == 0) goto L_0x0093
            java.lang.Object r6 = r3.c     // Catch:{ all -> 0x00b7 }
            if (r11 != 0) goto L_0x0091
            r3.c = r10     // Catch:{ all -> 0x00b7 }
        L_0x0091:
            r7 = r6
            goto L_0x0094
        L_0x0093:
            r7 = r0
        L_0x0094:
            r3 = 2
            goto L_0x00a4
        L_0x0096:
            boolean r6 = r5 instanceof j$.util.concurrent.m     // Catch:{ all -> 0x00b7 }
            if (r6 != 0) goto L_0x009b
            goto L_0x00a3
        L_0x009b:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException     // Catch:{ all -> 0x00b7 }
            java.lang.String r10 = "Recursive update"
            r9.<init>(r10)     // Catch:{ all -> 0x00b7 }
            throw r9     // Catch:{ all -> 0x00b7 }
        L_0x00a3:
            r7 = r0
        L_0x00a4:
            monitor-exit(r5)     // Catch:{ all -> 0x00b7 }
            if (r3 == 0) goto L_0x0010
            r9 = 8
            if (r3 < r9) goto L_0x00ae
            r8.o(r2, r4)
        L_0x00ae:
            if (r7 == 0) goto L_0x00b1
            return r7
        L_0x00b1:
            r9 = 1
            r8.a(r9, r3)
            return r0
        L_0x00b7:
            r9 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x00b7 }
            throw r9
        L_0x00ba:
            j$.util.concurrent.l[] r2 = r8.f()
            goto L_0x0010
        L_0x00c0:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.util.concurrent.ConcurrentHashMap.g(java.lang.Object, java.lang.Object, boolean):java.lang.Object");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:29:0x004d, code lost:
        return r1.c;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public V get(java.lang.Object r5) {
        /*
            r4 = this;
            int r0 = r5.hashCode()
            int r0 = j(r0)
            j$.util.concurrent.l[] r1 = r4.a
            r2 = 0
            if (r1 == 0) goto L_0x004e
            int r3 = r1.length
            if (r3 <= 0) goto L_0x004e
            int r3 = r3 + -1
            r3 = r3 & r0
            j$.util.concurrent.l r1 = l(r1, r3)
            if (r1 == 0) goto L_0x004e
            int r3 = r1.a
            if (r3 != r0) goto L_0x002c
            java.lang.Object r3 = r1.b
            if (r3 == r5) goto L_0x0029
            if (r3 == 0) goto L_0x0037
            boolean r3 = r5.equals(r3)
            if (r3 == 0) goto L_0x0037
        L_0x0029:
            java.lang.Object r5 = r1.c
            return r5
        L_0x002c:
            if (r3 >= 0) goto L_0x0037
            j$.util.concurrent.l r5 = r1.a(r5, r0)
            if (r5 == 0) goto L_0x0036
            java.lang.Object r2 = r5.c
        L_0x0036:
            return r2
        L_0x0037:
            j$.util.concurrent.l r1 = r1.d
            if (r1 == 0) goto L_0x004e
            int r3 = r1.a
            if (r3 != r0) goto L_0x0037
            java.lang.Object r3 = r1.b
            if (r3 == r5) goto L_0x004b
            if (r3 == 0) goto L_0x0037
            boolean r3 = r5.equals(r3)
            if (r3 == 0) goto L_0x0037
        L_0x004b:
            java.lang.Object r5 = r1.c
            return r5
        L_0x004e:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.util.concurrent.ConcurrentHashMap.get(java.lang.Object):java.lang.Object");
    }

    public final Object getOrDefault(Object obj, Object obj2) {
        Object obj3 = get(obj);
        return obj3 == null ? obj2 : obj3;
    }

    /* access modifiers changed from: package-private */
    public final Object h(Object obj, Object obj2, Object obj3) {
        int length;
        int i2;
        l l2;
        Object obj4;
        boolean z;
        l lVar;
        s b2;
        Object obj5;
        int j2 = j(obj.hashCode());
        l[] lVarArr = this.a;
        while (true) {
            if (lVarArr == null || (length = lVarArr.length) == 0 || (l2 = l(lVarArr, i2)) == null) {
                break;
            }
            int i3 = l2.a;
            if (i3 == -1) {
                lVarArr = e(lVarArr, l2);
            } else {
                synchronized (l2) {
                    try {
                        if (l(lVarArr, (i2 = (length - 1) & j2)) == l2) {
                            z = true;
                            if (i3 >= 0) {
                                l lVar2 = null;
                                l lVar3 = l2;
                                while (true) {
                                    if (lVar3.a != j2 || ((obj5 = lVar3.b) != obj && (obj5 == null || !obj.equals(obj5)))) {
                                        l lVar4 = lVar3.d;
                                        if (lVar4 == null) {
                                            break;
                                        }
                                        l lVar5 = lVar4;
                                        lVar2 = lVar3;
                                        lVar3 = lVar5;
                                    }
                                }
                                obj4 = lVar3.c;
                                if (obj3 == null || obj3 == obj4 || (obj4 != null && obj3.equals(obj4))) {
                                    if (obj2 != null) {
                                        lVar3.c = obj2;
                                    } else if (lVar2 != null) {
                                        lVar2.d = lVar3.d;
                                    } else {
                                        lVar = lVar3.d;
                                    }
                                }
                                obj4 = null;
                            } else if (l2 instanceof r) {
                                r rVar = (r) l2;
                                s sVar = rVar.e;
                                if (sVar != null && (b2 = sVar.b(j2, obj, (Class) null)) != null) {
                                    obj4 = b2.c;
                                    if (obj3 == null || obj3 == obj4 || (obj4 != null && obj3.equals(obj4))) {
                                        if (obj2 != null) {
                                            b2.c = obj2;
                                        } else if (rVar.f(b2)) {
                                            lVar = q(rVar.f);
                                        }
                                    }
                                }
                                obj4 = null;
                            } else if (l2 instanceof m) {
                                throw new IllegalStateException("Recursive update");
                            }
                            i(lVarArr, i2, lVar);
                        }
                        z = false;
                        obj4 = null;
                    } catch (Throwable th) {
                        while (true) {
                            throw th;
                        }
                    }
                }
                if (z) {
                    if (obj4 != null) {
                        if (obj2 == null) {
                            a(-1, -1);
                        }
                        return obj4;
                    }
                }
            }
        }
        return null;
    }

    public final int hashCode() {
        l[] lVarArr = this.a;
        int i2 = 0;
        if (lVarArr != null) {
            q qVar = new q(lVarArr, lVarArr.length, 0, lVarArr.length);
            while (true) {
                l a2 = qVar.a();
                if (a2 == null) {
                    break;
                }
                i2 += a2.c.hashCode() ^ a2.b.hashCode();
            }
        }
        return i2;
    }

    public boolean isEmpty() {
        return k() <= 0;
    }

    /* access modifiers changed from: package-private */
    public final long k() {
        c[] cVarArr = this.c;
        long j2 = this.baseCount;
        if (cVarArr != null) {
            for (c cVar : cVarArr) {
                if (cVar != null) {
                    j2 += cVar.value;
                }
            }
        }
        return j2;
    }

    public final Set keySet() {
        i iVar = this.d;
        if (iVar != null) {
            return iVar;
        }
        i iVar2 = new i(this);
        this.d = iVar2;
        return iVar2;
    }

    public final Object merge(Object obj, Object obj2, BiFunction biFunction) {
        int i2;
        Object obj3;
        Object obj4;
        Object obj5 = obj;
        Object obj6 = obj2;
        BiFunction biFunction2 = biFunction;
        if (obj5 == null || obj6 == null || biFunction2 == null) {
            throw null;
        }
        int j2 = j(obj.hashCode());
        l[] lVarArr = this.a;
        int i3 = 0;
        Object obj7 = null;
        int i4 = 0;
        while (true) {
            if (lVarArr != null) {
                int length = lVarArr.length;
                if (length != 0) {
                    int i5 = (length - 1) & j2;
                    l l2 = l(lVarArr, i5);
                    i2 = 1;
                    if (l2 != null) {
                        int i6 = l2.a;
                        if (i6 == -1) {
                            lVarArr = e(lVarArr, l2);
                        } else {
                            synchronized (l2) {
                                try {
                                    if (l(lVarArr, i5) == l2) {
                                        if (i6 >= 0) {
                                            l lVar = null;
                                            l lVar2 = l2;
                                            int i7 = 1;
                                            while (true) {
                                                if (lVar2.a != j2 || ((obj4 = lVar2.b) != obj5 && (obj4 == null || !obj5.equals(obj4)))) {
                                                    l lVar3 = lVar2.d;
                                                    if (lVar3 == null) {
                                                        lVar2.d = new l(j2, obj5, obj6);
                                                        obj3 = obj6;
                                                        i4 = 1;
                                                        break;
                                                    }
                                                    i7++;
                                                    l lVar4 = lVar3;
                                                    lVar = lVar2;
                                                    lVar2 = lVar4;
                                                }
                                            }
                                            obj3 = biFunction2.apply(lVar2.c, obj6);
                                            if (obj3 != null) {
                                                lVar2.c = obj3;
                                            } else {
                                                l lVar5 = lVar2.d;
                                                if (lVar != null) {
                                                    lVar.d = lVar5;
                                                } else {
                                                    i(lVarArr, i5, lVar5);
                                                }
                                                i4 = -1;
                                            }
                                            i3 = i7;
                                            obj7 = obj3;
                                        } else if (l2 instanceof r) {
                                            r rVar = (r) l2;
                                            s sVar = rVar.e;
                                            s b2 = sVar == null ? null : sVar.b(j2, obj5, (Class) null);
                                            Object apply = b2 == null ? obj6 : biFunction2.apply(b2.c, obj6);
                                            if (apply != null) {
                                                if (b2 != null) {
                                                    b2.c = apply;
                                                } else {
                                                    rVar.e(j2, obj5, apply);
                                                    i4 = 1;
                                                }
                                            } else if (b2 != null) {
                                                if (rVar.f(b2)) {
                                                    i(lVarArr, i5, q(rVar.f));
                                                }
                                                i4 = -1;
                                            }
                                            i3 = 2;
                                            obj7 = apply;
                                        } else if (l2 instanceof m) {
                                            throw new IllegalStateException("Recursive update");
                                        }
                                    }
                                } catch (Throwable th) {
                                    while (true) {
                                        throw th;
                                    }
                                }
                            }
                            if (i3 != 0) {
                                if (i3 >= 8) {
                                    o(lVarArr, i5);
                                }
                                i2 = i4;
                                obj6 = obj7;
                            }
                        }
                    } else if (b(lVarArr, i5, new l(j2, obj5, obj6))) {
                        break;
                    }
                }
            }
            lVarArr = f();
        }
        if (i2 != 0) {
            a((long) i2, i3);
        }
        return obj6;
    }

    public V put(K k2, V v) {
        return g(k2, v, false);
    }

    public final void putAll(Map map) {
        p(map.size());
        for (Map.Entry entry : map.entrySet()) {
            g(entry.getKey(), entry.getValue(), false);
        }
    }

    public V putIfAbsent(K k2, V v) {
        return g(k2, v, true);
    }

    public final Object remove(Object obj) {
        return h(obj, (Object) null, (Object) null);
    }

    public boolean remove(Object obj, Object obj2) {
        obj.getClass();
        return (obj2 == null || h(obj, (Object) null, obj2) == null) ? false : true;
    }

    public final Object replace(Object obj, Object obj2) {
        if (obj != null && obj2 != null) {
            return h(obj, obj2, (Object) null);
        }
        throw null;
    }

    public final boolean replace(Object obj, Object obj2, Object obj3) {
        if (obj != null && obj2 != null && obj3 != null) {
            return h(obj, obj3, obj2) != null;
        }
        throw null;
    }

    public final void replaceAll(BiFunction biFunction) {
        biFunction.getClass();
        l[] lVarArr = this.a;
        if (lVarArr != null) {
            q qVar = new q(lVarArr, lVarArr.length, 0, lVarArr.length);
            while (true) {
                l a2 = qVar.a();
                if (a2 != null) {
                    Object obj = a2.c;
                    Object obj2 = a2.b;
                    do {
                        Object apply = biFunction.apply(obj2, obj);
                        apply.getClass();
                        if (h(obj2, apply, obj) != null || (obj = get(obj2)) == null) {
                        }
                        Object apply2 = biFunction.apply(obj2, obj);
                        apply2.getClass();
                        break;
                    } while ((obj = get(obj2)) == null);
                } else {
                    return;
                }
            }
        }
    }

    public int size() {
        long k2 = k();
        if (k2 < 0) {
            return 0;
        }
        return k2 > 2147483647L ? ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED : (int) k2;
    }

    public final String toString() {
        l[] lVarArr = this.a;
        int length = lVarArr == null ? 0 : lVarArr.length;
        q qVar = new q(lVarArr, length, 0, length);
        StringBuilder sb = new StringBuilder("{");
        l a2 = qVar.a();
        if (a2 != null) {
            while (true) {
                Object obj = a2.b;
                Object obj2 = a2.c;
                if (obj == this) {
                    obj = "(this Map)";
                }
                sb.append(obj);
                sb.append('=');
                if (obj2 == this) {
                    obj2 = "(this Map)";
                }
                sb.append(obj2);
                a2 = qVar.a();
                if (a2 == null) {
                    break;
                }
                sb.append(", ");
            }
        }
        sb.append('}');
        return sb.toString();
    }

    public final Collection values() {
        t tVar = this.e;
        if (tVar != null) {
            return tVar;
        }
        t tVar2 = new t(this);
        this.e = tVar2;
        return tVar2;
    }
}
