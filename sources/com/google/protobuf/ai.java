package com.google.protobuf;

import java.io.IOException;

public final class ai extends ag<ah, ah> {
    public final void a(int i, int i2, Object obj) {
        ((ah) obj).f((i << 3) | 5, Integer.valueOf(i2));
    }

    public final void b(int i, long j, Object obj) {
        ((ah) obj).f((i << 3) | 1, Long.valueOf(j));
    }

    public final void c(int i, Object obj, Object obj2) {
        ((ah) obj).f((i << 3) | 3, (ah) obj2);
    }

    public final void d(Object obj, int i, cp cpVar) {
        ((ah) obj).f((i << 3) | 2, cpVar);
    }

    public final void e(int i, long j, Object obj) {
        ((ah) obj).f((i << 3) | 0, Long.valueOf(j));
    }

    public final ah f(Object obj) {
        n nVar = (n) obj;
        ah ahVar = nVar.unknownFields;
        if (ahVar != ah.f) {
            return ahVar;
        }
        ah ahVar2 = new ah();
        nVar.unknownFields = ahVar2;
        return ahVar2;
    }

    public final ah g(Object obj) {
        return ((n) obj).unknownFields;
    }

    public final int h(Object obj) {
        return ((ah) obj).c();
    }

    public final int i(Object obj) {
        ah ahVar = (ah) obj;
        int i = ahVar.d;
        if (i != -1) {
            return i;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < ahVar.a; i3++) {
            int t = n0.t(2, ahVar.b[i3] >>> 3);
            i2 += n0.c(3, (cp) ahVar.c[i3]) + t + (n0.s(1) * 2);
        }
        ahVar.d = i2;
        return i2;
    }

    public final void j(Object obj) {
        ah ahVar = ((n) obj).unknownFields;
        if (ahVar.e) {
            ahVar.e = false;
        }
    }

    public final ah k(Object obj, Object obj2) {
        ah ahVar = (ah) obj;
        ah ahVar2 = (ah) obj2;
        ah ahVar3 = ah.f;
        if (ahVar3.equals(ahVar2)) {
            return ahVar;
        }
        if (ahVar3.equals(ahVar)) {
            return ah.e(ahVar, ahVar2);
        }
        ahVar.getClass();
        if (ahVar2.equals(ahVar3)) {
            return ahVar;
        }
        ahVar.a();
        int i = ahVar.a + ahVar2.a;
        ahVar.b(i);
        System.arraycopy(ahVar2.b, 0, ahVar.b, ahVar.a, ahVar2.a);
        System.arraycopy(ahVar2.c, 0, ahVar.c, ahVar.a, ahVar2.a);
        ahVar.a = i;
        return ahVar;
    }

    public final ah m() {
        return new ah();
    }

    public final void n(Object obj, Object obj2) {
        ((n) obj).unknownFields = (ah) obj2;
    }

    public final void o(Object obj, Object obj2) {
        ((n) obj).unknownFields = (ah) obj2;
    }

    public final void p() {
    }

    public final ah q(Object obj) {
        ah ahVar = (ah) obj;
        if (ahVar.e) {
            ahVar.e = false;
        }
        return ahVar;
    }

    public final void r(Object obj, jf jfVar) throws IOException {
        ah ahVar = (ah) obj;
        ahVar.getClass();
        jfVar.getClass();
        for (int i = 0; i < ahVar.a; i++) {
            ((o0) jfVar).m(ahVar.b[i] >>> 3, ahVar.c[i]);
        }
    }

    public final void s(Object obj, jf jfVar) throws IOException {
        ((ah) obj).g(jfVar);
    }
}
