package com.google.protobuf;

import com.google.protobuf.p;

public final class t implements g4 {
    public final p.i a(long j, Object obj) {
        int i;
        p.i iVar = (p.i) fd.p(j, obj);
        if (iVar.y()) {
            return iVar;
        }
        int size = iVar.size();
        if (size == 0) {
            i = 10;
        } else {
            i = size * 2;
        }
        p.i h = iVar.h(i);
        fd.z(j, obj, h);
        return h;
    }

    public final void b(long j, Object obj, Object obj2) {
        p.i iVar = (p.i) fd.p(j, obj);
        p.i iVar2 = (p.i) fd.p(j, obj2);
        int size = iVar.size();
        int size2 = iVar2.size();
        if (size > 0 && size2 > 0) {
            if (!iVar.y()) {
                iVar = iVar.h(size2 + size);
            }
            iVar.addAll(iVar2);
        }
        if (size > 0) {
            iVar2 = iVar;
        }
        fd.z(j, obj, iVar2);
    }

    public final void c(long j, Object obj) {
        ((p.i) fd.p(j, obj)).e();
    }
}
