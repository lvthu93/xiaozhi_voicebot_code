package com.google.protobuf;

import androidx.core.internal.view.SupportMenu;
import com.google.protobuf.n;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class i {
    public static volatile i b;
    public static final i c = new i(0);
    public final Map<a, n.e<?, ?>> a;

    public static final class a {
        public final Object a;
        public final int b;

        public a(int i, Object obj) {
            this.a = obj;
            this.b = i;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:3:0x0006, code lost:
            r4 = (com.google.protobuf.i.a) r4;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final boolean equals(java.lang.Object r4) {
            /*
                r3 = this;
                boolean r0 = r4 instanceof com.google.protobuf.i.a
                r1 = 0
                if (r0 != 0) goto L_0x0006
                return r1
            L_0x0006:
                com.google.protobuf.i$a r4 = (com.google.protobuf.i.a) r4
                java.lang.Object r0 = r4.a
                java.lang.Object r2 = r3.a
                if (r2 != r0) goto L_0x0015
                int r0 = r3.b
                int r4 = r4.b
                if (r0 != r4) goto L_0x0015
                r1 = 1
            L_0x0015:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.i.a.equals(java.lang.Object):boolean");
        }

        public final int hashCode() {
            return (System.identityHashCode(this.a) * SupportMenu.USER_MASK) + this.b;
        }
    }

    public i() {
        this.a = new HashMap();
    }

    public static i a() {
        Class<?> cls = bb.a;
        i iVar = b;
        if (iVar == null) {
            synchronized (i.class) {
                iVar = b;
                if (iVar == null) {
                    Class<?> cls2 = u2.a;
                    i iVar2 = null;
                    if (cls2 != null) {
                        try {
                            iVar2 = (i) cls2.getDeclaredMethod("getEmptyRegistry", new Class[0]).invoke((Object) null, new Object[0]);
                        } catch (Exception unused) {
                        }
                    }
                    if (iVar2 == null) {
                        iVar2 = c;
                    }
                    b = iVar2;
                    iVar = iVar2;
                }
            }
        }
        return iVar;
    }

    public i(int i) {
        this.a = Collections.emptyMap();
    }
}
