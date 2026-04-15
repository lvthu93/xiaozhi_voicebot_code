package defpackage;

import com.google.protobuf.aa;
import com.google.protobuf.ac;
import com.google.protobuf.ad;
import com.google.protobuf.ag;
import com.google.protobuf.ai;
import com.google.protobuf.j;
import com.google.protobuf.k;
import com.google.protobuf.n;
import com.google.protobuf.p;
import com.google.protobuf.t;
import com.google.protobuf.z;
import j$.util.concurrent.ConcurrentHashMap;
import java.nio.charset.Charset;

/* renamed from: f9  reason: default package */
public final class f9 {
    public static final f9 c = new f9();
    public final p4 a = new p4();
    public final ConcurrentHashMap b = new ConcurrentHashMap();

    public final <T> ac<T> a(Class<T> cls) {
        z<?> zVar;
        j<?> jVar;
        k kVar;
        aa aaVar;
        Charset charset = p.a;
        if (cls != null) {
            ConcurrentHashMap concurrentHashMap = this.b;
            ac<T> acVar = (ac) concurrentHashMap.get(cls);
            if (acVar != null) {
                return acVar;
            }
            p4 p4Var = this.a;
            p4Var.getClass();
            Class<?> cls2 = ad.a;
            Class<n> cls3 = n.class;
            if (!cls3.isAssignableFrom(cls)) {
                Class<?> cls4 = bb.a;
                Class<?> cls5 = ad.a;
                if (cls5 != null && !cls5.isAssignableFrom(cls)) {
                    throw new IllegalArgumentException("Message classes must extend GeneratedMessage or GeneratedMessageLite");
                }
            }
            n6 a2 = p4Var.a.a(cls);
            if (a2.a()) {
                Class<?> cls6 = bb.a;
                if (cls3.isAssignableFrom(cls)) {
                    aaVar = new aa(ad.c, v2.a, a2.b());
                } else {
                    ag<?, ?> agVar = ad.b;
                    j<?> jVar2 = v2.b;
                    if (jVar2 != null) {
                        aaVar = new aa(agVar, jVar2, a2.b());
                    } else {
                        throw new IllegalStateException("Protobuf runtime is not correctly loaded.");
                    }
                }
                zVar = aaVar;
            } else {
                Class<?> cls7 = bb.a;
                boolean z = false;
                if (cls3.isAssignableFrom(cls)) {
                    h7 h7Var = i7.b;
                    t tVar = h4.b;
                    ai aiVar = ad.c;
                    if (y2.ae(a2.c()) != 1) {
                        z = true;
                    }
                    if (z) {
                        kVar = v2.a;
                    } else {
                        kVar = null;
                    }
                    r4 r4Var = s4.b;
                    int[] iArr = z.q;
                    if (a2 instanceof x9) {
                        zVar = z.aa((x9) a2, h7Var, tVar, aiVar, kVar, r4Var);
                    } else {
                        rb rbVar = (rb) a2;
                        throw null;
                    }
                } else {
                    g7 g7Var = i7.a;
                    g4 g4Var = h4.a;
                    ag<?, ?> agVar2 = ad.b;
                    if (y2.ae(a2.c()) != 1) {
                        z = true;
                    }
                    if (z) {
                        j<?> jVar3 = v2.b;
                        if (jVar3 != null) {
                            jVar = jVar3;
                        } else {
                            throw new IllegalStateException("Protobuf runtime is not correctly loaded.");
                        }
                    } else {
                        jVar = null;
                    }
                    q4 q4Var = s4.a;
                    int[] iArr2 = z.q;
                    if (a2 instanceof x9) {
                        zVar = z.aa((x9) a2, g7Var, g4Var, agVar2, jVar, q4Var);
                    } else {
                        rb rbVar2 = (rb) a2;
                        throw null;
                    }
                }
            }
            ac<T> acVar2 = (ac) concurrentHashMap.putIfAbsent(cls, zVar);
            if (acVar2 != null) {
                return acVar2;
            }
            return zVar;
        }
        throw new NullPointerException("messageType");
    }

    public final <T> ac<T> b(T t) {
        return a(t.getClass());
    }
}
