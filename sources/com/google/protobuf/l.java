package com.google.protobuf;

import com.google.protobuf.af;
import com.google.protobuf.l.a;
import com.google.protobuf.n;
import com.google.protobuf.p;
import com.google.protobuf.r;
import com.google.protobuf.x;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public final class l<T extends a<T>> {
    public static final l<?> d = new l<>(0);
    public final af<T, Object> a = new ae();
    public boolean b;
    public boolean c;

    public interface a<T extends a<T>> extends Comparable<T> {
        int a();

        boolean c();

        void f(Object obj, Object obj2);

        hf g();

        boolean j(Object obj);

        Cif k();

        boolean l();
    }

    public l() {
        int i = af.k;
    }

    public static int c(hf hfVar, Object obj) {
        switch (hfVar.ordinal()) {
            case 0:
                ((Double) obj).doubleValue();
                Logger logger = n0.f;
                return 8;
            case 1:
                ((Float) obj).floatValue();
                Logger logger2 = n0.f;
                return 4;
            case 2:
                return n0.w(((Long) obj).longValue());
            case 3:
                return n0.w(((Long) obj).longValue());
            case 4:
                return n0.w((long) ((Integer) obj).intValue());
            case 5:
                ((Long) obj).longValue();
                Logger logger3 = n0.f;
                return 8;
            case 6:
                ((Integer) obj).intValue();
                Logger logger4 = n0.f;
                return 4;
            case 7:
                ((Boolean) obj).booleanValue();
                Logger logger5 = n0.f;
                return 1;
            case 8:
                if (!(obj instanceof cp)) {
                    return n0.r((String) obj);
                }
                Logger logger6 = n0.f;
                int size = ((cp) obj).size();
                return n0.u(size) + size;
            case 9:
                return ((x) obj).getSerializedSize();
            case 10:
                if (obj instanceof r) {
                    return ((r) obj).a();
                }
                Logger logger7 = n0.f;
                int serializedSize = ((x) obj).getSerializedSize();
                return n0.u(serializedSize) + serializedSize;
            case 11:
                if (obj instanceof cp) {
                    Logger logger8 = n0.f;
                    int size2 = ((cp) obj).size();
                    return n0.u(size2) + size2;
                }
                Logger logger9 = n0.f;
                int length = ((byte[]) obj).length;
                return n0.u(length) + length;
            case 12:
                return n0.u(((Integer) obj).intValue());
            case 13:
                if (obj instanceof p.c) {
                    return n0.w((long) ((p.c) obj).a());
                }
                return n0.w((long) ((Integer) obj).intValue());
            case 14:
                ((Integer) obj).intValue();
                Logger logger10 = n0.f;
                return 4;
            case 15:
                ((Long) obj).longValue();
                Logger logger11 = n0.f;
                return 8;
            case 16:
                return n0.n(((Integer) obj).intValue());
            case 17:
                return n0.p(((Long) obj).longValue());
            default:
                throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
        }
    }

    public static int d(a<?> aVar, Object obj) {
        hf g = aVar.g();
        int a2 = aVar.a();
        if (aVar.c()) {
            List list = (List) obj;
            int size = list.size();
            int i = 0;
            if (!aVar.l()) {
                int i2 = 0;
                while (i < size) {
                    Object obj2 = list.get(i);
                    int s = n0.s(a2);
                    if (g == hf.f) {
                        s *= 2;
                    }
                    i2 += c(g, obj2) + s;
                    i++;
                }
                return i2;
            } else if (list.isEmpty()) {
                return 0;
            } else {
                int i3 = 0;
                while (i < size) {
                    i3 += c(g, list.get(i));
                    i++;
                }
                return n0.u(i3) + n0.s(a2) + i3;
            }
        } else {
            int s2 = n0.s(a2);
            if (g == hf.f) {
                s2 *= 2;
            }
            return c(g, obj) + s2;
        }
    }

    public static int f(Map.Entry entry) {
        a aVar = (a) entry.getKey();
        Object value = entry.getValue();
        if (aVar.k() != Cif.n || aVar.c() || aVar.l()) {
            return d(aVar, value);
        }
        if (value instanceof r) {
            r rVar = (r) value;
            int a2 = ((a) entry.getKey()).a();
            rVar.getClass();
            return rVar.a() + n0.s(3) + n0.t(2, a2) + (n0.s(1) * 2);
        }
        int t = n0.t(2, ((a) entry.getKey()).a()) + (n0.s(1) * 2);
        int s = n0.s(3);
        int serializedSize = ((x) value).getSerializedSize();
        return n0.u(serializedSize) + serializedSize + s + t;
    }

    public static <T extends a<T>> boolean j(Map.Entry<T, Object> entry) {
        boolean z;
        a aVar = (a) entry.getKey();
        if (aVar.k() == Cif.n) {
            if (aVar.c()) {
                List list = (List) entry.getValue();
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    Object obj = list.get(i);
                    if (obj instanceof p6) {
                        z = ((p6) obj).isInitialized();
                    } else if (obj instanceof r) {
                        z = true;
                    } else {
                        throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
                    }
                    if (!z) {
                        return false;
                    }
                }
            } else {
                Object value = entry.getValue();
                if (value instanceof p6) {
                    return ((p6) value).isInitialized();
                }
                if (value instanceof r) {
                    return true;
                }
                throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
            }
        }
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x002d, code lost:
        if ((r6 instanceof byte[]) == false) goto L_0x0043;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x002f, code lost:
        r0 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0043, code lost:
        r0 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0044, code lost:
        if (r0 == false) goto L_0x0047;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0046, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0072, code lost:
        throw new java.lang.IllegalArgumentException(java.lang.String.format("Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n", new java.lang.Object[]{java.lang.Integer.valueOf(r5.a()), r5.g().c, r6.getClass().getName()}));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x001b, code lost:
        if ((r6 instanceof com.google.protobuf.r) == false) goto L_0x0043;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0024, code lost:
        if ((r6 instanceof com.google.protobuf.p.c) == false) goto L_0x0043;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void o(com.google.protobuf.l.a r5, java.lang.Object r6) {
        /*
            hf r0 = r5.g()
            java.nio.charset.Charset r1 = com.google.protobuf.p.a
            r6.getClass()
            if r0 = r0.c
            int r0 = r0.ordinal()
            r1 = 1
            r2 = 0
            switch(r0) {
                case 0: goto L_0x0040;
                case 1: goto L_0x003d;
                case 2: goto L_0x003a;
                case 3: goto L_0x0037;
                case 4: goto L_0x0034;
                case 5: goto L_0x0031;
                case 6: goto L_0x0027;
                case 7: goto L_0x001e;
                case 8: goto L_0x0015;
                default: goto L_0x0014;
            }
        L_0x0014:
            goto L_0x0043
        L_0x0015:
            boolean r0 = r6 instanceof com.google.protobuf.x
            if (r0 != 0) goto L_0x002f
            boolean r0 = r6 instanceof com.google.protobuf.r
            if (r0 == 0) goto L_0x0043
            goto L_0x002f
        L_0x001e:
            boolean r0 = r6 instanceof java.lang.Integer
            if (r0 != 0) goto L_0x002f
            boolean r0 = r6 instanceof com.google.protobuf.p.c
            if (r0 == 0) goto L_0x0043
            goto L_0x002f
        L_0x0027:
            boolean r0 = r6 instanceof defpackage.cp
            if (r0 != 0) goto L_0x002f
            boolean r0 = r6 instanceof byte[]
            if (r0 == 0) goto L_0x0043
        L_0x002f:
            r0 = 1
            goto L_0x0044
        L_0x0031:
            boolean r0 = r6 instanceof java.lang.String
            goto L_0x0044
        L_0x0034:
            boolean r0 = r6 instanceof java.lang.Boolean
            goto L_0x0044
        L_0x0037:
            boolean r0 = r6 instanceof java.lang.Double
            goto L_0x0044
        L_0x003a:
            boolean r0 = r6 instanceof java.lang.Float
            goto L_0x0044
        L_0x003d:
            boolean r0 = r6 instanceof java.lang.Long
            goto L_0x0044
        L_0x0040:
            boolean r0 = r6 instanceof java.lang.Integer
            goto L_0x0044
        L_0x0043:
            r0 = 0
        L_0x0044:
            if (r0 == 0) goto L_0x0047
            return
        L_0x0047:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            r3 = 3
            java.lang.Object[] r3 = new java.lang.Object[r3]
            int r4 = r5.a()
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r3[r2] = r4
            hf r5 = r5.g()
            if r5 = r5.c
            r3[r1] = r5
            java.lang.Class r5 = r6.getClass()
            java.lang.String r5 = r5.getName()
            r6 = 2
            r3[r6] = r5
            java.lang.String r5 = "Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n"
            java.lang.String r5 = java.lang.String.format(r5, r3)
            r0.<init>(r5)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.l.o(com.google.protobuf.l$a, java.lang.Object):void");
    }

    public final void a(n.d dVar, Object obj) {
        List list;
        if (dVar.g) {
            o(dVar, obj);
            Object e = e(dVar);
            if (e == null) {
                list = new ArrayList();
                this.a.put(dVar, list);
            } else {
                list = (List) e;
            }
            list.add(obj);
            return;
        }
        throw new IllegalArgumentException("addRepeatedField() can only be called on repeated fields.");
    }

    /* renamed from: b */
    public final l<T> clone() {
        l<T> lVar = new l<>();
        af<T, Object> afVar = this.a;
        int i = afVar.f;
        for (int i2 = 0; i2 < i; i2++) {
            af.a c2 = afVar.c(i2);
            lVar.n((a) c2.c, c2.f);
        }
        for (Map.Entry entry : afVar.d()) {
            lVar.n((a) entry.getKey(), entry.getValue());
        }
        lVar.c = this.c;
        return lVar;
    }

    public final Object e(T t) {
        Object obj = this.a.get(t);
        if (obj instanceof r) {
            return ((r) obj).b((x) null);
        }
        return obj;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof l)) {
            return false;
        }
        return this.a.equals(((l) obj).a);
    }

    public final int g() {
        af<T, Object> afVar = this.a;
        int i = afVar.f;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            af.a c2 = afVar.c(i3);
            i2 += d((a) c2.c, c2.f);
        }
        for (Map.Entry entry : afVar.d()) {
            i2 += d((a) entry.getKey(), entry.getValue());
        }
        return i2;
    }

    public final boolean h() {
        return this.a.isEmpty();
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    public final boolean i() {
        af<T, Object> afVar = this.a;
        int i = afVar.f;
        for (int i2 = 0; i2 < i; i2++) {
            if (!j(afVar.c(i2))) {
                return false;
            }
        }
        for (Map.Entry j : afVar.d()) {
            if (!j(j)) {
                return false;
            }
        }
        return true;
    }

    public final Iterator<Map.Entry<T, Object>> k() {
        if (h()) {
            return Collections.emptyIterator();
        }
        boolean z = this.c;
        af<T, Object> afVar = this.a;
        if (z) {
            return new r.b(afVar.entrySet().iterator());
        }
        return afVar.entrySet().iterator();
    }

    public final void l() {
        if (!this.b) {
            af<T, Object> afVar = this.a;
            int i = afVar.f;
            for (int i2 = 0; i2 < i; i2++) {
                V v = afVar.c(i2).f;
                if (v instanceof n) {
                    ((n) v).makeImmutable();
                }
            }
            for (Map.Entry value : afVar.d()) {
                Object value2 = value.getValue();
                if (value2 instanceof n) {
                    ((n) value2).makeImmutable();
                }
            }
            afVar.f();
            this.b = true;
        }
    }

    public final void m(Map.Entry<T, Object> entry) {
        a aVar = (a) entry.getKey();
        Object value = entry.getValue();
        boolean z = value instanceof r;
        boolean c2 = aVar.c();
        af<T, Object> afVar = this.a;
        if (c2) {
            if (!z) {
                Object e = e(aVar);
                List list = (List) value;
                int size = list.size();
                if (e == null) {
                    e = new ArrayList(size);
                }
                List list2 = (List) e;
                for (int i = 0; i < size; i++) {
                    Object obj = list.get(i);
                    if (obj instanceof byte[]) {
                        byte[] bArr = (byte[]) obj;
                        byte[] bArr2 = new byte[bArr.length];
                        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
                        obj = bArr2;
                    }
                    list2.add(obj);
                }
                afVar.put(aVar, e);
                return;
            }
            throw new IllegalStateException("Lazy fields can not be repeated");
        } else if (aVar.k() == Cif.n) {
            Object e2 = e(aVar);
            if (e2 == null) {
                if (value instanceof byte[]) {
                    byte[] bArr3 = (byte[]) value;
                    byte[] bArr4 = new byte[bArr3.length];
                    System.arraycopy(bArr3, 0, bArr4, 0, bArr3.length);
                    value = bArr4;
                }
                afVar.put(aVar, value);
                if (z) {
                    this.c = true;
                    return;
                }
                return;
            }
            if (z) {
                value = ((r) value).b((x) null);
            }
            if (aVar.j(e2)) {
                x.a builder = ((x) e2).toBuilder();
                aVar.f(builder, value);
                afVar.put(aVar, builder.build());
                return;
            }
            aVar.f(e2, value);
        } else if (!z) {
            if (value instanceof byte[]) {
                byte[] bArr5 = (byte[]) value;
                byte[] bArr6 = new byte[bArr5.length];
                System.arraycopy(bArr5, 0, bArr6, 0, bArr5.length);
                value = bArr6;
            }
            afVar.put(aVar, value);
        } else {
            throw new IllegalStateException("Lazy fields must be message-valued");
        }
    }

    public final void n(T t, Object obj) {
        if (!t.c()) {
            o(t, obj);
        } else if (obj instanceof List) {
            List list = (List) obj;
            int size = list.size();
            ArrayList arrayList = new ArrayList(size);
            for (int i = 0; i < size; i++) {
                Object obj2 = list.get(i);
                o(t, obj2);
                arrayList.add(obj2);
            }
            obj = arrayList;
        } else {
            throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
        }
        if (obj instanceof r) {
            this.c = true;
        }
        this.a.put(t, obj);
    }

    public l(int i) {
        int i2 = af.k;
        l();
        l();
    }
}
