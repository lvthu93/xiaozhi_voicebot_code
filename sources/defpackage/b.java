package defpackage;

import defpackage.a;
import sun.misc.Unsafe;

/* renamed from: b  reason: default package */
public final /* synthetic */ class b {
    public static /* synthetic */ boolean a(Unsafe unsafe, Object obj, long j, Object obj2) {
        a.d dVar = a.d.d;
        while (!unsafe.compareAndSwapObject(obj, j, obj2, dVar)) {
            if (unsafe.getObject(obj, j) != obj2) {
                return false;
            }
        }
        return true;
    }

    public static /* synthetic */ boolean b(Unsafe unsafe, Object obj, long j, Object obj2, Object obj3) {
        while (!unsafe.compareAndSwapObject(obj, j, obj2, obj3)) {
            if (unsafe.getObject(obj, j) != obj2) {
                return false;
            }
        }
        return true;
    }
}
