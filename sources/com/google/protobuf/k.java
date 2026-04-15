package com.google.protobuf;

import com.google.protobuf.i;
import com.google.protobuf.n;
import com.google.protobuf.x;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class k extends j<n.d> {
    public final int a(Map.Entry<?, ?> entry) {
        return ((n.d) entry.getKey()).c;
    }

    public final n.e b(i iVar, x xVar, int i) {
        iVar.getClass();
        return iVar.a.get(new i.a(i, xVar));
    }

    public final l<n.d> c(Object obj) {
        return ((n.c) obj).c;
    }

    public final l<n.d> d(Object obj) {
        n.c cVar = (n.c) obj;
        l<n.d> lVar = cVar.c;
        if (lVar.b) {
            cVar.c = lVar.clone();
        }
        return cVar.c;
    }

    public final boolean e(x xVar) {
        return xVar instanceof n.c;
    }

    public final void f(Object obj) {
        ((n.c) obj).c.l();
    }

    public final Object g(y9 y9Var, Object obj, i iVar, l lVar, Object obj2) throws IOException {
        Object e;
        ArrayList arrayList;
        n.e eVar = (n.e) obj;
        n.d dVar = eVar.d;
        int i = dVar.c;
        boolean z = dVar.g;
        hf hfVar = dVar.f;
        if (!z || !dVar.h) {
            Object obj3 = null;
            if (hfVar != hf.ENUM) {
                int ordinal = hfVar.ordinal();
                x xVar = eVar.c;
                switch (ordinal) {
                    case 0:
                        g gVar = (g) y9Var;
                        gVar.aa(1);
                        obj3 = Double.valueOf(gVar.a.m());
                        break;
                    case 1:
                        g gVar2 = (g) y9Var;
                        gVar2.aa(5);
                        obj3 = Float.valueOf(gVar2.a.q());
                        break;
                    case 2:
                        obj3 = Long.valueOf(((g) y9Var).p());
                        break;
                    case 3:
                        g gVar3 = (g) y9Var;
                        gVar3.aa(0);
                        obj3 = Long.valueOf(gVar3.a.ac());
                        break;
                    case 4:
                        obj3 = Integer.valueOf(((g) y9Var).n());
                        break;
                    case 5:
                        obj3 = Long.valueOf(((g) y9Var).k());
                        break;
                    case 6:
                        obj3 = Integer.valueOf(((g) y9Var).i());
                        break;
                    case 7:
                        g gVar4 = (g) y9Var;
                        gVar4.aa(0);
                        obj3 = Boolean.valueOf(gVar4.a.k());
                        break;
                    case 8:
                        g gVar5 = (g) y9Var;
                        gVar5.aa(2);
                        obj3 = gVar5.a.y();
                        break;
                    case 9:
                        if (!dVar.g) {
                            Object e2 = lVar.e(dVar);
                            if (e2 instanceof n) {
                                ac b = f9.c.b(e2);
                                if (!((n) e2).isMutable()) {
                                    Object i2 = b.i();
                                    b.a(i2, e2);
                                    lVar.n(dVar, i2);
                                    e2 = i2;
                                }
                                g gVar6 = (g) y9Var;
                                gVar6.aa(3);
                                gVar6.b(e2, b, iVar);
                                return obj2;
                            }
                        }
                        Class<?> cls = xVar.getClass();
                        g gVar7 = (g) y9Var;
                        gVar7.aa(3);
                        ac<?> a = f9.c.a(cls);
                        obj3 = a.i();
                        gVar7.b(obj3, a, iVar);
                        a.b(obj3);
                        break;
                    case 10:
                        if (!dVar.g) {
                            Object e3 = lVar.e(dVar);
                            if (e3 instanceof n) {
                                ac b2 = f9.c.b(e3);
                                if (!((n) e3).isMutable()) {
                                    Object i3 = b2.i();
                                    b2.a(i3, e3);
                                    lVar.n(dVar, i3);
                                    e3 = i3;
                                }
                                g gVar8 = (g) y9Var;
                                gVar8.aa(2);
                                gVar8.c(e3, b2, iVar);
                                return obj2;
                            }
                        }
                        Class<?> cls2 = xVar.getClass();
                        g gVar9 = (g) y9Var;
                        gVar9.aa(2);
                        ac<?> a2 = f9.c.a(cls2);
                        obj3 = a2.i();
                        gVar9.c(obj3, a2, iVar);
                        a2.b(obj3);
                        break;
                    case 11:
                        obj3 = ((g) y9Var).e();
                        break;
                    case 12:
                        obj3 = Integer.valueOf(((g) y9Var).w());
                        break;
                    case 13:
                        throw new IllegalStateException("Shouldn't reach here.");
                    case 14:
                        g gVar10 = (g) y9Var;
                        gVar10.aa(5);
                        obj3 = Integer.valueOf(gVar10.a.u());
                        break;
                    case 15:
                        g gVar11 = (g) y9Var;
                        gVar11.aa(1);
                        obj3 = Long.valueOf(gVar11.a.v());
                        break;
                    case 16:
                        g gVar12 = (g) y9Var;
                        gVar12.aa(0);
                        obj3 = Integer.valueOf(gVar12.a.w());
                        break;
                    case 17:
                        g gVar13 = (g) y9Var;
                        gVar13.aa(0);
                        obj3 = Long.valueOf(gVar13.a.x());
                        break;
                }
                if (dVar.g) {
                    lVar.a(dVar, obj3);
                } else {
                    int ordinal2 = dVar.f.ordinal();
                    if ((ordinal2 == 9 || ordinal2 == 10) && (e = lVar.e(dVar)) != null) {
                        obj3 = ((x) e).toBuilder().mergeFrom((x) obj3).buildPartial();
                    }
                    lVar.n(dVar, obj3);
                }
            } else {
                ((g) y9Var).n();
                dVar.getClass();
                throw null;
            }
        } else {
            switch (hfVar.ordinal()) {
                case 0:
                    arrayList = new ArrayList();
                    ((g) y9Var).g(arrayList);
                    break;
                case 1:
                    arrayList = new ArrayList();
                    ((g) y9Var).m(arrayList);
                    break;
                case 2:
                    arrayList = new ArrayList();
                    ((g) y9Var).q(arrayList);
                    break;
                case 3:
                    arrayList = new ArrayList();
                    ((g) y9Var).y(arrayList);
                    break;
                case 4:
                    arrayList = new ArrayList();
                    ((g) y9Var).o(arrayList);
                    break;
                case 5:
                    arrayList = new ArrayList();
                    ((g) y9Var).l(arrayList);
                    break;
                case 6:
                    arrayList = new ArrayList();
                    ((g) y9Var).j(arrayList);
                    break;
                case 7:
                    arrayList = new ArrayList();
                    ((g) y9Var).d(arrayList);
                    break;
                case 12:
                    arrayList = new ArrayList();
                    ((g) y9Var).x(arrayList);
                    break;
                case 13:
                    arrayList = new ArrayList();
                    ((g) y9Var).h(arrayList);
                    dVar.getClass();
                    Class<?> cls3 = ad.a;
                    break;
                case 14:
                    arrayList = new ArrayList();
                    ((g) y9Var).r(arrayList);
                    break;
                case 15:
                    arrayList = new ArrayList();
                    ((g) y9Var).s(arrayList);
                    break;
                case 16:
                    arrayList = new ArrayList();
                    ((g) y9Var).t(arrayList);
                    break;
                case 17:
                    arrayList = new ArrayList();
                    ((g) y9Var).u(arrayList);
                    break;
                default:
                    StringBuilder sb = new StringBuilder("Type cannot be packed: ");
                    sb.append(dVar.f);
                    throw new IllegalStateException(sb.toString());
            }
            lVar.n(dVar, arrayList);
        }
        return obj2;
    }

    public final void h(y9 y9Var, Object obj, i iVar, l<n.d> lVar) throws IOException {
        n.e eVar = (n.e) obj;
        Class<?> cls = eVar.c.getClass();
        g gVar = (g) y9Var;
        gVar.aa(2);
        ac<?> a = f9.c.a(cls);
        Object i = a.i();
        gVar.c(i, a, iVar);
        a.b(i);
        lVar.n(eVar.d, i);
    }

    public final void i(cp cpVar, Object obj, i iVar, l<n.d> lVar) throws IOException {
        n.e eVar = (n.e) obj;
        x.a newBuilderForType = eVar.c.newBuilderForType();
        f m = cpVar.m();
        newBuilderForType.mergeFrom(m, iVar);
        lVar.n(eVar.d, newBuilderForType.buildPartial());
        m.a(0);
    }

    public final void j(jf jfVar, Map.Entry<?, ?> entry) throws IOException {
        n.d dVar = (n.d) entry.getKey();
        boolean z = dVar.g;
        hf hfVar = dVar.f;
        int i = dVar.c;
        if (z) {
            int ordinal = hfVar.ordinal();
            boolean z2 = dVar.h;
            switch (ordinal) {
                case 0:
                    ad.ag(i, (List) entry.getValue(), jfVar, z2);
                    return;
                case 1:
                    ad.ak(i, (List) entry.getValue(), jfVar, z2);
                    return;
                case 2:
                    ad.an(i, (List) entry.getValue(), jfVar, z2);
                    return;
                case 3:
                    ad.av(i, (List) entry.getValue(), jfVar, z2);
                    return;
                case 4:
                    ad.am(i, (List) entry.getValue(), jfVar, z2);
                    return;
                case 5:
                    ad.aj(i, (List) entry.getValue(), jfVar, z2);
                    return;
                case 6:
                    ad.ai(i, (List) entry.getValue(), jfVar, z2);
                    return;
                case 7:
                    ad.ae(i, (List) entry.getValue(), jfVar, z2);
                    return;
                case 8:
                    ad.at(i, (List) entry.getValue(), jfVar);
                    return;
                case 9:
                    List list = (List) entry.getValue();
                    if (list != null && !list.isEmpty()) {
                        ad.al(i, (List) entry.getValue(), jfVar, f9.c.a(list.get(0).getClass()));
                        return;
                    }
                    return;
                case 10:
                    List list2 = (List) entry.getValue();
                    if (list2 != null && !list2.isEmpty()) {
                        ad.ao(i, (List) entry.getValue(), jfVar, f9.c.a(list2.get(0).getClass()));
                        return;
                    }
                    return;
                case 11:
                    ad.af(i, (List) entry.getValue(), jfVar);
                    return;
                case 12:
                    ad.au(i, (List) entry.getValue(), jfVar, z2);
                    return;
                case 13:
                    ad.am(i, (List) entry.getValue(), jfVar, z2);
                    return;
                case 14:
                    ad.ap(i, (List) entry.getValue(), jfVar, z2);
                    return;
                case 15:
                    ad.aq(i, (List) entry.getValue(), jfVar, z2);
                    return;
                case 16:
                    ad.ar(i, (List) entry.getValue(), jfVar, z2);
                    return;
                case 17:
                    ad.as(i, (List) entry.getValue(), jfVar, z2);
                    return;
                default:
                    return;
            }
        } else {
            switch (hfVar.ordinal()) {
                case 0:
                    ((o0) jfVar).c(i, ((Double) entry.getValue()).doubleValue());
                    return;
                case 1:
                    ((o0) jfVar).h(((Float) entry.getValue()).floatValue(), i);
                    return;
                case 2:
                    ((o0) jfVar).k(i, ((Long) entry.getValue()).longValue());
                    return;
                case 3:
                    ((o0) jfVar).t(i, ((Long) entry.getValue()).longValue());
                    return;
                case 4:
                    ((o0) jfVar).j(i, ((Integer) entry.getValue()).intValue());
                    return;
                case 5:
                    ((o0) jfVar).g(i, ((Long) entry.getValue()).longValue());
                    return;
                case 6:
                    ((o0) jfVar).f(i, ((Integer) entry.getValue()).intValue());
                    return;
                case 7:
                    ((o0) jfVar).a(i, ((Boolean) entry.getValue()).booleanValue());
                    return;
                case 8:
                    ((o0) jfVar).a.aj(i, (String) entry.getValue());
                    return;
                case 9:
                    o0 o0Var = (o0) jfVar;
                    o0Var.i(i, f9.c.a(entry.getValue().getClass()), entry.getValue());
                    return;
                case 10:
                    o0 o0Var2 = (o0) jfVar;
                    o0Var2.l(i, f9.c.a(entry.getValue().getClass()), entry.getValue());
                    return;
                case 11:
                    ((o0) jfVar).b(i, (cp) entry.getValue());
                    return;
                case 12:
                    ((o0) jfVar).s(i, ((Integer) entry.getValue()).intValue());
                    return;
                case 13:
                    ((o0) jfVar).j(i, ((Integer) entry.getValue()).intValue());
                    return;
                case 14:
                    ((o0) jfVar).n(i, ((Integer) entry.getValue()).intValue());
                    return;
                case 15:
                    ((o0) jfVar).o(i, ((Long) entry.getValue()).longValue());
                    return;
                case 16:
                    ((o0) jfVar).p(i, ((Integer) entry.getValue()).intValue());
                    return;
                case 17:
                    ((o0) jfVar).q(i, ((Long) entry.getValue()).longValue());
                    return;
                default:
                    return;
            }
        }
    }
}
