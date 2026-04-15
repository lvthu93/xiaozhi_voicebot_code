package com.google.protobuf;

import com.google.protobuf.l;
import com.google.protobuf.p;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.RandomAccess;
import java.util.logging.Logger;

public final class ad {
    public static final Class<?> a;
    public static final ag<?, ?> b;
    public static final ai c = new ai();

    static {
        Class<?> cls;
        Class<?> cls2;
        Class<?> cls3 = bb.a;
        ag<?, ?> agVar = null;
        try {
            cls = Class.forName("com.google.protobuf.GeneratedMessage");
        } catch (Throwable unused) {
            cls = null;
        }
        a = cls;
        try {
            Class<?> cls4 = bb.a;
            try {
                cls2 = Class.forName("com.google.protobuf.UnknownFieldSetSchema");
            } catch (Throwable unused2) {
                cls2 = null;
            }
            if (cls2 != null) {
                agVar = (ag) cls2.getConstructor(new Class[0]).newInstance(new Object[0]);
            }
        } catch (Throwable unused3) {
        }
        b = agVar;
    }

    @Deprecated
    public static int a(int i, x xVar, ac acVar) {
        return ((a) xVar).getSerializedSize(acVar) + (n0.s(i) * 2);
    }

    public static <UT, UB> UB aa(Object obj, int i, List<Integer> list, p.e eVar, UB ub, ag<UT, UB> agVar) {
        if (eVar == null) {
            return ub;
        }
        if (list instanceof RandomAccess) {
            int size = list.size();
            int i2 = 0;
            for (int i3 = 0; i3 < size; i3++) {
                int intValue = list.get(i3).intValue();
                if (eVar.a()) {
                    if (i3 != i2) {
                        list.set(i2, Integer.valueOf(intValue));
                    }
                    i2++;
                } else {
                    ub = ad(obj, i, intValue, ub, agVar);
                }
            }
            if (i2 != size) {
                list.subList(i2, size).clear();
            }
        } else {
            Iterator<Integer> it = list.iterator();
            while (it.hasNext()) {
                int intValue2 = it.next().intValue();
                if (!eVar.a()) {
                    ub = ad(obj, i, intValue2, ub, agVar);
                    it.remove();
                }
            }
        }
        return ub;
    }

    public static <T, FT extends l.a<FT>> void ab(j<FT> jVar, T t, T t2) {
        l<FT> c2 = jVar.c(t2);
        if (!c2.h()) {
            l<FT> d = jVar.d(t);
            d.getClass();
            af<T, Object> afVar = c2.a;
            int i = afVar.f;
            for (int i2 = 0; i2 < i; i2++) {
                d.m(afVar.c(i2));
            }
            for (Map.Entry m : afVar.d()) {
                d.m(m);
            }
        }
    }

    public static boolean ac(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    public static <UT, UB> UB ad(Object obj, int i, int i2, UB ub, ag<UT, UB> agVar) {
        if (ub == null) {
            ub = agVar.f(obj);
        }
        agVar.e(i, (long) i2, ub);
        return ub;
    }

    public static void ae(int i, List<Boolean> list, jf jfVar, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            o0 o0Var = (o0) jfVar;
            o0Var.getClass();
            boolean z2 = list instanceof e;
            int i2 = 0;
            n0 n0Var = o0Var.a;
            if (z2) {
                e eVar = (e) list;
                if (z) {
                    n0Var.ak(i, 2);
                    int i3 = 0;
                    for (int i4 = 0; i4 < eVar.g; i4++) {
                        eVar.c(i4);
                        boolean z3 = eVar.f[i4];
                        Logger logger = n0.f;
                        i3++;
                    }
                    n0Var.am(i3);
                    while (i2 < eVar.g) {
                        eVar.c(i2);
                        n0Var.y(eVar.f[i2] ? (byte) 1 : 0);
                        i2++;
                    }
                    return;
                }
                while (i2 < eVar.g) {
                    eVar.c(i2);
                    n0Var.z(i, eVar.f[i2]);
                    i2++;
                }
            } else if (z) {
                n0Var.ak(i, 2);
                int i5 = 0;
                for (int i6 = 0; i6 < list.size(); i6++) {
                    list.get(i6).booleanValue();
                    Logger logger2 = n0.f;
                    i5++;
                }
                n0Var.am(i5);
                while (i2 < list.size()) {
                    n0Var.y(list.get(i2).booleanValue() ? (byte) 1 : 0);
                    i2++;
                }
            } else {
                while (i2 < list.size()) {
                    n0Var.z(i, list.get(i2).booleanValue());
                    i2++;
                }
            }
        }
    }

    public static void af(int i, List<cp> list, jf jfVar) throws IOException {
        if (list != null && !list.isEmpty()) {
            o0 o0Var = (o0) jfVar;
            o0Var.getClass();
            for (int i2 = 0; i2 < list.size(); i2++) {
                o0Var.a.aa(i, list.get(i2));
            }
        }
    }

    public static void ag(int i, List<Double> list, jf jfVar, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            o0 o0Var = (o0) jfVar;
            o0Var.getClass();
            boolean z2 = list instanceof h;
            int i2 = 0;
            n0 n0Var = o0Var.a;
            if (z2) {
                h hVar = (h) list;
                if (z) {
                    n0Var.ak(i, 2);
                    int i3 = 0;
                    for (int i4 = 0; i4 < hVar.g; i4++) {
                        hVar.c(i4);
                        double d = hVar.f[i4];
                        Logger logger = n0.f;
                        i3 += 8;
                    }
                    n0Var.am(i3);
                    while (i2 < hVar.g) {
                        hVar.c(i2);
                        n0Var.ae(Double.doubleToRawLongBits(hVar.f[i2]));
                        i2++;
                    }
                    return;
                }
                while (i2 < hVar.g) {
                    hVar.c(i2);
                    double d2 = hVar.f[i2];
                    n0Var.getClass();
                    n0Var.ad(i, Double.doubleToRawLongBits(d2));
                    i2++;
                }
            } else if (z) {
                n0Var.ak(i, 2);
                int i5 = 0;
                for (int i6 = 0; i6 < list.size(); i6++) {
                    list.get(i6).doubleValue();
                    Logger logger2 = n0.f;
                    i5 += 8;
                }
                n0Var.am(i5);
                while (i2 < list.size()) {
                    n0Var.ae(Double.doubleToRawLongBits(list.get(i2).doubleValue()));
                    i2++;
                }
            } else {
                while (i2 < list.size()) {
                    double doubleValue = list.get(i2).doubleValue();
                    n0Var.getClass();
                    n0Var.ad(i, Double.doubleToRawLongBits(doubleValue));
                    i2++;
                }
            }
        }
    }

    public static void ah(int i, List<Integer> list, jf jfVar, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            o0 o0Var = (o0) jfVar;
            o0Var.getClass();
            boolean z2 = list instanceof o;
            int i2 = 0;
            n0 n0Var = o0Var.a;
            if (z2) {
                o oVar = (o) list;
                if (z) {
                    n0Var.ak(i, 2);
                    int i3 = 0;
                    for (int i4 = 0; i4 < oVar.g; i4++) {
                        i3 += n0.w((long) oVar.f(i4));
                    }
                    n0Var.am(i3);
                    while (i2 < oVar.g) {
                        n0Var.ag(oVar.f(i2));
                        i2++;
                    }
                    return;
                }
                while (i2 < oVar.g) {
                    n0Var.af(i, oVar.f(i2));
                    i2++;
                }
            } else if (z) {
                n0Var.ak(i, 2);
                int i5 = 0;
                for (int i6 = 0; i6 < list.size(); i6++) {
                    i5 += n0.w((long) list.get(i6).intValue());
                }
                n0Var.am(i5);
                while (i2 < list.size()) {
                    n0Var.ag(list.get(i2).intValue());
                    i2++;
                }
            } else {
                while (i2 < list.size()) {
                    n0Var.af(i, list.get(i2).intValue());
                    i2++;
                }
            }
        }
    }

    public static void ai(int i, List<Integer> list, jf jfVar, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            o0 o0Var = (o0) jfVar;
            o0Var.getClass();
            boolean z2 = list instanceof o;
            int i2 = 0;
            n0 n0Var = o0Var.a;
            if (z2) {
                o oVar = (o) list;
                if (z) {
                    n0Var.ak(i, 2);
                    int i3 = 0;
                    for (int i4 = 0; i4 < oVar.g; i4++) {
                        oVar.f(i4);
                        Logger logger = n0.f;
                        i3 += 4;
                    }
                    n0Var.am(i3);
                    while (i2 < oVar.g) {
                        n0Var.ac(oVar.f(i2));
                        i2++;
                    }
                    return;
                }
                while (i2 < oVar.g) {
                    n0Var.ab(i, oVar.f(i2));
                    i2++;
                }
            } else if (z) {
                n0Var.ak(i, 2);
                int i5 = 0;
                for (int i6 = 0; i6 < list.size(); i6++) {
                    list.get(i6).intValue();
                    Logger logger2 = n0.f;
                    i5 += 4;
                }
                n0Var.am(i5);
                while (i2 < list.size()) {
                    n0Var.ac(list.get(i2).intValue());
                    i2++;
                }
            } else {
                while (i2 < list.size()) {
                    n0Var.ab(i, list.get(i2).intValue());
                    i2++;
                }
            }
        }
    }

    public static void aj(int i, List<Long> list, jf jfVar, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            o0 o0Var = (o0) jfVar;
            o0Var.getClass();
            boolean z2 = list instanceof u;
            int i2 = 0;
            n0 n0Var = o0Var.a;
            if (z2) {
                u uVar = (u) list;
                if (z) {
                    n0Var.ak(i, 2);
                    int i3 = 0;
                    for (int i4 = 0; i4 < uVar.g; i4++) {
                        uVar.f(i4);
                        Logger logger = n0.f;
                        i3 += 8;
                    }
                    n0Var.am(i3);
                    while (i2 < uVar.g) {
                        n0Var.ae(uVar.f(i2));
                        i2++;
                    }
                    return;
                }
                while (i2 < uVar.g) {
                    n0Var.ad(i, uVar.f(i2));
                    i2++;
                }
            } else if (z) {
                n0Var.ak(i, 2);
                int i5 = 0;
                for (int i6 = 0; i6 < list.size(); i6++) {
                    list.get(i6).longValue();
                    Logger logger2 = n0.f;
                    i5 += 8;
                }
                n0Var.am(i5);
                while (i2 < list.size()) {
                    n0Var.ae(list.get(i2).longValue());
                    i2++;
                }
            } else {
                while (i2 < list.size()) {
                    n0Var.ad(i, list.get(i2).longValue());
                    i2++;
                }
            }
        }
    }

    public static void ak(int i, List<Float> list, jf jfVar, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            o0 o0Var = (o0) jfVar;
            o0Var.getClass();
            boolean z2 = list instanceof m;
            int i2 = 0;
            n0 n0Var = o0Var.a;
            if (z2) {
                m mVar = (m) list;
                if (z) {
                    n0Var.ak(i, 2);
                    int i3 = 0;
                    for (int i4 = 0; i4 < mVar.g; i4++) {
                        mVar.c(i4);
                        float f = mVar.f[i4];
                        Logger logger = n0.f;
                        i3 += 4;
                    }
                    n0Var.am(i3);
                    while (i2 < mVar.g) {
                        mVar.c(i2);
                        n0Var.ac(Float.floatToRawIntBits(mVar.f[i2]));
                        i2++;
                    }
                    return;
                }
                while (i2 < mVar.g) {
                    mVar.c(i2);
                    float f2 = mVar.f[i2];
                    n0Var.getClass();
                    n0Var.ab(i, Float.floatToRawIntBits(f2));
                    i2++;
                }
            } else if (z) {
                n0Var.ak(i, 2);
                int i5 = 0;
                for (int i6 = 0; i6 < list.size(); i6++) {
                    list.get(i6).floatValue();
                    Logger logger2 = n0.f;
                    i5 += 4;
                }
                n0Var.am(i5);
                while (i2 < list.size()) {
                    n0Var.ac(Float.floatToRawIntBits(list.get(i2).floatValue()));
                    i2++;
                }
            } else {
                while (i2 < list.size()) {
                    float floatValue = list.get(i2).floatValue();
                    n0Var.getClass();
                    n0Var.ab(i, Float.floatToRawIntBits(floatValue));
                    i2++;
                }
            }
        }
    }

    public static void al(int i, List<?> list, jf jfVar, ac<?> acVar) throws IOException {
        if (list != null && !list.isEmpty()) {
            o0 o0Var = (o0) jfVar;
            o0Var.getClass();
            for (int i2 = 0; i2 < list.size(); i2++) {
                o0Var.i(i, acVar, list.get(i2));
            }
        }
    }

    public static void am(int i, List<Integer> list, jf jfVar, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            o0 o0Var = (o0) jfVar;
            o0Var.getClass();
            boolean z2 = list instanceof o;
            int i2 = 0;
            n0 n0Var = o0Var.a;
            if (z2) {
                o oVar = (o) list;
                if (z) {
                    n0Var.ak(i, 2);
                    int i3 = 0;
                    for (int i4 = 0; i4 < oVar.g; i4++) {
                        i3 += n0.w((long) oVar.f(i4));
                    }
                    n0Var.am(i3);
                    while (i2 < oVar.g) {
                        n0Var.ag(oVar.f(i2));
                        i2++;
                    }
                    return;
                }
                while (i2 < oVar.g) {
                    n0Var.af(i, oVar.f(i2));
                    i2++;
                }
            } else if (z) {
                n0Var.ak(i, 2);
                int i5 = 0;
                for (int i6 = 0; i6 < list.size(); i6++) {
                    i5 += n0.w((long) list.get(i6).intValue());
                }
                n0Var.am(i5);
                while (i2 < list.size()) {
                    n0Var.ag(list.get(i2).intValue());
                    i2++;
                }
            } else {
                while (i2 < list.size()) {
                    n0Var.af(i, list.get(i2).intValue());
                    i2++;
                }
            }
        }
    }

    public static void an(int i, List<Long> list, jf jfVar, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            o0 o0Var = (o0) jfVar;
            o0Var.getClass();
            boolean z2 = list instanceof u;
            int i2 = 0;
            n0 n0Var = o0Var.a;
            if (z2) {
                u uVar = (u) list;
                if (z) {
                    n0Var.ak(i, 2);
                    int i3 = 0;
                    for (int i4 = 0; i4 < uVar.g; i4++) {
                        i3 += n0.w(uVar.f(i4));
                    }
                    n0Var.am(i3);
                    while (i2 < uVar.g) {
                        n0Var.ao(uVar.f(i2));
                        i2++;
                    }
                    return;
                }
                while (i2 < uVar.g) {
                    n0Var.an(i, uVar.f(i2));
                    i2++;
                }
            } else if (z) {
                n0Var.ak(i, 2);
                int i5 = 0;
                for (int i6 = 0; i6 < list.size(); i6++) {
                    i5 += n0.w(list.get(i6).longValue());
                }
                n0Var.am(i5);
                while (i2 < list.size()) {
                    n0Var.ao(list.get(i2).longValue());
                    i2++;
                }
            } else {
                while (i2 < list.size()) {
                    n0Var.an(i, list.get(i2).longValue());
                    i2++;
                }
            }
        }
    }

    public static void ao(int i, List<?> list, jf jfVar, ac<?> acVar) throws IOException {
        if (list != null && !list.isEmpty()) {
            o0 o0Var = (o0) jfVar;
            o0Var.getClass();
            for (int i2 = 0; i2 < list.size(); i2++) {
                o0Var.l(i, acVar, list.get(i2));
            }
        }
    }

    public static void ap(int i, List<Integer> list, jf jfVar, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            o0 o0Var = (o0) jfVar;
            o0Var.getClass();
            boolean z2 = list instanceof o;
            int i2 = 0;
            n0 n0Var = o0Var.a;
            if (z2) {
                o oVar = (o) list;
                if (z) {
                    n0Var.ak(i, 2);
                    int i3 = 0;
                    for (int i4 = 0; i4 < oVar.g; i4++) {
                        oVar.f(i4);
                        Logger logger = n0.f;
                        i3 += 4;
                    }
                    n0Var.am(i3);
                    while (i2 < oVar.g) {
                        n0Var.ac(oVar.f(i2));
                        i2++;
                    }
                    return;
                }
                while (i2 < oVar.g) {
                    n0Var.ab(i, oVar.f(i2));
                    i2++;
                }
            } else if (z) {
                n0Var.ak(i, 2);
                int i5 = 0;
                for (int i6 = 0; i6 < list.size(); i6++) {
                    list.get(i6).intValue();
                    Logger logger2 = n0.f;
                    i5 += 4;
                }
                n0Var.am(i5);
                while (i2 < list.size()) {
                    n0Var.ac(list.get(i2).intValue());
                    i2++;
                }
            } else {
                while (i2 < list.size()) {
                    n0Var.ab(i, list.get(i2).intValue());
                    i2++;
                }
            }
        }
    }

    public static void aq(int i, List<Long> list, jf jfVar, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            o0 o0Var = (o0) jfVar;
            o0Var.getClass();
            boolean z2 = list instanceof u;
            int i2 = 0;
            n0 n0Var = o0Var.a;
            if (z2) {
                u uVar = (u) list;
                if (z) {
                    n0Var.ak(i, 2);
                    int i3 = 0;
                    for (int i4 = 0; i4 < uVar.g; i4++) {
                        uVar.f(i4);
                        Logger logger = n0.f;
                        i3 += 8;
                    }
                    n0Var.am(i3);
                    while (i2 < uVar.g) {
                        n0Var.ae(uVar.f(i2));
                        i2++;
                    }
                    return;
                }
                while (i2 < uVar.g) {
                    n0Var.ad(i, uVar.f(i2));
                    i2++;
                }
            } else if (z) {
                n0Var.ak(i, 2);
                int i5 = 0;
                for (int i6 = 0; i6 < list.size(); i6++) {
                    list.get(i6).longValue();
                    Logger logger2 = n0.f;
                    i5 += 8;
                }
                n0Var.am(i5);
                while (i2 < list.size()) {
                    n0Var.ae(list.get(i2).longValue());
                    i2++;
                }
            } else {
                while (i2 < list.size()) {
                    n0Var.ad(i, list.get(i2).longValue());
                    i2++;
                }
            }
        }
    }

    public static void ar(int i, List<Integer> list, jf jfVar, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            o0 o0Var = (o0) jfVar;
            o0Var.getClass();
            boolean z2 = list instanceof o;
            int i2 = 0;
            n0 n0Var = o0Var.a;
            if (z2) {
                o oVar = (o) list;
                if (z) {
                    n0Var.ak(i, 2);
                    int i3 = 0;
                    for (int i4 = 0; i4 < oVar.g; i4++) {
                        i3 += n0.n(oVar.f(i4));
                    }
                    n0Var.am(i3);
                    while (i2 < oVar.g) {
                        int f = oVar.f(i2);
                        n0Var.am((f >> 31) ^ (f << 1));
                        i2++;
                    }
                    return;
                }
                while (i2 < oVar.g) {
                    int f2 = oVar.f(i2);
                    n0Var.al(i, (f2 >> 31) ^ (f2 << 1));
                    i2++;
                }
            } else if (z) {
                n0Var.ak(i, 2);
                int i5 = 0;
                for (int i6 = 0; i6 < list.size(); i6++) {
                    i5 += n0.n(list.get(i6).intValue());
                }
                n0Var.am(i5);
                while (i2 < list.size()) {
                    int intValue = list.get(i2).intValue();
                    n0Var.am((intValue >> 31) ^ (intValue << 1));
                    i2++;
                }
            } else {
                while (i2 < list.size()) {
                    int intValue2 = list.get(i2).intValue();
                    n0Var.al(i, (intValue2 >> 31) ^ (intValue2 << 1));
                    i2++;
                }
            }
        }
    }

    public static void as(int i, List<Long> list, jf jfVar, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            o0 o0Var = (o0) jfVar;
            o0Var.getClass();
            boolean z2 = list instanceof u;
            int i2 = 0;
            n0 n0Var = o0Var.a;
            if (z2) {
                u uVar = (u) list;
                if (z) {
                    n0Var.ak(i, 2);
                    int i3 = 0;
                    for (int i4 = 0; i4 < uVar.g; i4++) {
                        i3 += n0.p(uVar.f(i4));
                    }
                    n0Var.am(i3);
                    while (i2 < uVar.g) {
                        long f = uVar.f(i2);
                        n0Var.ao((f >> 63) ^ (f << 1));
                        i2++;
                    }
                    return;
                }
                while (i2 < uVar.g) {
                    long f2 = uVar.f(i2);
                    n0Var.an(i, (f2 >> 63) ^ (f2 << 1));
                    i2++;
                }
            } else if (z) {
                n0Var.ak(i, 2);
                int i5 = 0;
                for (int i6 = 0; i6 < list.size(); i6++) {
                    i5 += n0.p(list.get(i6).longValue());
                }
                n0Var.am(i5);
                while (i2 < list.size()) {
                    long longValue = list.get(i2).longValue();
                    n0Var.ao((longValue >> 63) ^ (longValue << 1));
                    i2++;
                }
            } else {
                while (i2 < list.size()) {
                    long longValue2 = list.get(i2).longValue();
                    n0Var.an(i, (longValue2 >> 63) ^ (longValue2 << 1));
                    i2++;
                }
            }
        }
    }

    public static void at(int i, List<String> list, jf jfVar) throws IOException {
        if (list != null && !list.isEmpty()) {
            o0 o0Var = (o0) jfVar;
            o0Var.getClass();
            boolean z = list instanceof e4;
            n0 n0Var = o0Var.a;
            int i2 = 0;
            if (z) {
                e4 e4Var = (e4) list;
                while (i2 < list.size()) {
                    Object d = e4Var.d();
                    if (d instanceof String) {
                        n0Var.aj(i, (String) d);
                    } else {
                        n0Var.aa(i, (cp) d);
                    }
                    i2++;
                }
                return;
            }
            while (i2 < list.size()) {
                n0Var.aj(i, list.get(i2));
                i2++;
            }
        }
    }

    public static void au(int i, List<Integer> list, jf jfVar, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            o0 o0Var = (o0) jfVar;
            o0Var.getClass();
            boolean z2 = list instanceof o;
            int i2 = 0;
            n0 n0Var = o0Var.a;
            if (z2) {
                o oVar = (o) list;
                if (z) {
                    n0Var.ak(i, 2);
                    int i3 = 0;
                    for (int i4 = 0; i4 < oVar.g; i4++) {
                        i3 += n0.u(oVar.f(i4));
                    }
                    n0Var.am(i3);
                    while (i2 < oVar.g) {
                        n0Var.am(oVar.f(i2));
                        i2++;
                    }
                    return;
                }
                while (i2 < oVar.g) {
                    n0Var.al(i, oVar.f(i2));
                    i2++;
                }
            } else if (z) {
                n0Var.ak(i, 2);
                int i5 = 0;
                for (int i6 = 0; i6 < list.size(); i6++) {
                    i5 += n0.u(list.get(i6).intValue());
                }
                n0Var.am(i5);
                while (i2 < list.size()) {
                    n0Var.am(list.get(i2).intValue());
                    i2++;
                }
            } else {
                while (i2 < list.size()) {
                    n0Var.al(i, list.get(i2).intValue());
                    i2++;
                }
            }
        }
    }

    public static void av(int i, List<Long> list, jf jfVar, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            o0 o0Var = (o0) jfVar;
            o0Var.getClass();
            boolean z2 = list instanceof u;
            int i2 = 0;
            n0 n0Var = o0Var.a;
            if (z2) {
                u uVar = (u) list;
                if (z) {
                    n0Var.ak(i, 2);
                    int i3 = 0;
                    for (int i4 = 0; i4 < uVar.g; i4++) {
                        i3 += n0.w(uVar.f(i4));
                    }
                    n0Var.am(i3);
                    while (i2 < uVar.g) {
                        n0Var.ao(uVar.f(i2));
                        i2++;
                    }
                    return;
                }
                while (i2 < uVar.g) {
                    n0Var.an(i, uVar.f(i2));
                    i2++;
                }
            } else if (z) {
                n0Var.ak(i, 2);
                int i5 = 0;
                for (int i6 = 0; i6 < list.size(); i6++) {
                    i5 += n0.w(list.get(i6).longValue());
                }
                n0Var.am(i5);
                while (i2 < list.size()) {
                    n0Var.ao(list.get(i2).longValue());
                    i2++;
                }
            } else {
                while (i2 < list.size()) {
                    n0Var.an(i, list.get(i2).longValue());
                    i2++;
                }
            }
        }
    }

    public static int b(int i, List list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return n0.b(i) * size;
    }

    public static int c(List<?> list) {
        return list.size();
    }

    public static int d(int i, List<cp> list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int s = n0.s(i) * size;
        for (int i2 = 0; i2 < list.size(); i2++) {
            int size2 = list.get(i2).size();
            s += n0.u(size2) + size2;
        }
        return s;
    }

    public static int e(int i, List list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return (n0.s(i) * size) + f(list);
    }

    public static int f(List<Integer> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof o) {
            o oVar = (o) list;
            i = 0;
            while (i2 < size) {
                i += n0.w((long) oVar.f(i2));
                i2++;
            }
        } else {
            int i3 = 0;
            while (i2 < size) {
                i3 = i + n0.w((long) list.get(i2).intValue());
                i2++;
            }
        }
        return i;
    }

    public static int g(int i, List list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return n0.f(i) * size;
    }

    public static int h(List<?> list) {
        return list.size() * 4;
    }

    public static int i(int i, List list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return n0.g(i) * size;
    }

    public static int j(List<?> list) {
        return list.size() * 8;
    }

    public static int k(int i, List<x> list, ac<?> acVar) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            i2 += a(i, list.get(i3), acVar);
        }
        return i2;
    }

    public static int l(int i, List list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return (n0.s(i) * size) + m(list);
    }

    public static int m(List<Integer> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof o) {
            o oVar = (o) list;
            i = 0;
            while (i2 < size) {
                i += n0.w((long) oVar.f(i2));
                i2++;
            }
        } else {
            int i3 = 0;
            while (i2 < size) {
                i3 = i + n0.w((long) list.get(i2).intValue());
                i2++;
            }
        }
        return i;
    }

    public static int n(int i, List list) {
        if (list.size() == 0) {
            return 0;
        }
        return (n0.s(i) * list.size()) + o(list);
    }

    public static int o(List<Long> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof u) {
            u uVar = (u) list;
            i = 0;
            while (i2 < size) {
                i += n0.w(uVar.f(i2));
                i2++;
            }
        } else {
            int i3 = 0;
            while (i2 < size) {
                i3 = i + n0.w(list.get(i2).longValue());
                i2++;
            }
        }
        return i;
    }

    public static int p(int i, ac acVar, Object obj) {
        if (obj instanceof s) {
            s sVar = (s) obj;
            sVar.getClass();
            return sVar.a() + n0.s(i);
        }
        int s = n0.s(i);
        int serializedSize = ((a) obj).getSerializedSize(acVar);
        return n0.u(serializedSize) + serializedSize + s;
    }

    public static int q(int i, List<?> list, ac<?> acVar) {
        int i2;
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int s = n0.s(i) * size;
        for (int i3 = 0; i3 < size; i3++) {
            Object obj = list.get(i3);
            if (obj instanceof s) {
                i2 = ((s) obj).a();
            } else {
                int serializedSize = ((a) obj).getSerializedSize(acVar);
                i2 = serializedSize + n0.u(serializedSize);
            }
            s += i2;
        }
        return s;
    }

    public static int r(int i, List list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return (n0.s(i) * size) + s(list);
    }

    public static int s(List<Integer> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof o) {
            o oVar = (o) list;
            i = 0;
            while (i2 < size) {
                i += n0.n(oVar.f(i2));
                i2++;
            }
        } else {
            int i3 = 0;
            while (i2 < size) {
                i3 = i + n0.n(list.get(i2).intValue());
                i2++;
            }
        }
        return i;
    }

    public static int t(int i, List list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return (n0.s(i) * size) + u(list);
    }

    public static int u(List<Long> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof u) {
            u uVar = (u) list;
            i = 0;
            while (i2 < size) {
                i += n0.p(uVar.f(i2));
                i2++;
            }
        } else {
            int i3 = 0;
            while (i2 < size) {
                i3 = i + n0.p(list.get(i2).longValue());
                i2++;
            }
        }
        return i;
    }

    public static int v(int i, List<?> list) {
        int i2;
        int i3;
        int size = list.size();
        int i4 = 0;
        if (size == 0) {
            return 0;
        }
        int s = n0.s(i) * size;
        if (list instanceof e4) {
            e4 e4Var = (e4) list;
            while (i4 < size) {
                Object d = e4Var.d();
                if (d instanceof cp) {
                    int size2 = ((cp) d).size();
                    i3 = n0.u(size2) + size2;
                } else {
                    i3 = n0.r((String) d);
                }
                s += i3;
                i4++;
            }
        } else {
            while (i4 < size) {
                Object obj = list.get(i4);
                if (obj instanceof cp) {
                    int size3 = ((cp) obj).size();
                    i2 = n0.u(size3) + size3;
                } else {
                    i2 = n0.r((String) obj);
                }
                s += i2;
                i4++;
            }
        }
        return s;
    }

    public static int w(int i, List list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return (n0.s(i) * size) + x(list);
    }

    public static int x(List<Integer> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof o) {
            o oVar = (o) list;
            i = 0;
            while (i2 < size) {
                i += n0.u(oVar.f(i2));
                i2++;
            }
        } else {
            int i3 = 0;
            while (i2 < size) {
                i3 = i + n0.u(list.get(i2).intValue());
                i2++;
            }
        }
        return i;
    }

    public static int y(int i, List list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return (n0.s(i) * size) + z(list);
    }

    public static int z(List<Long> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof u) {
            u uVar = (u) list;
            i = 0;
            while (i2 < size) {
                i += n0.w(uVar.f(i2));
                i2++;
            }
        } else {
            int i3 = 0;
            while (i2 < size) {
                i3 = i + n0.w(list.get(i2).longValue());
                i2++;
            }
        }
        return i;
    }
}
