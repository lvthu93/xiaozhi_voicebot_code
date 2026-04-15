package j$.util.concurrent;

final class s extends l {
    s e;
    s f;
    s g;
    s h;
    boolean i;

    s(int i2, Object obj, Object obj2, s sVar, s sVar2) {
        super(i2, obj, obj2, sVar);
        this.e = sVar2;
    }

    /* access modifiers changed from: package-private */
    public final l a(Object obj, int i2) {
        return b(i2, obj, (Class) null);
    }

    /* access modifiers changed from: package-private */
    public final s b(int i2, Object obj, Class cls) {
        int d;
        if (obj == null) {
            return null;
        }
        s sVar = this;
        do {
            s sVar2 = sVar.f;
            s sVar3 = sVar.g;
            int i3 = sVar.a;
            if (i3 <= i2) {
                if (i3 >= i2) {
                    Object obj2 = sVar.b;
                    if (obj2 == obj || (obj2 != null && obj.equals(obj2))) {
                        return sVar;
                    }
                    if (sVar2 != null) {
                        if (sVar3 != null) {
                            if ((cls == null && (cls = ConcurrentHashMap.c(obj)) == null) || (d = ConcurrentHashMap.d(cls, obj, obj2)) == 0) {
                                s b = sVar3.b(i2, obj, cls);
                                if (b != null) {
                                    return b;
                                }
                            } else if (d >= 0) {
                                sVar2 = sVar3;
                            }
                        }
                    }
                }
                sVar = sVar3;
                continue;
            }
            sVar = sVar2;
            continue;
        } while (sVar != null);
        return null;
    }
}
