package j$.util.concurrent;

import j$.lang.a;
import j$.util.function.BiFunction$CC;
import j$.util.function.Consumer$CC;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public final /* synthetic */ class u implements BiConsumer, BiFunction, Consumer {
    public final /* synthetic */ int a;
    public final /* synthetic */ Object b;
    public final /* synthetic */ Object c;

    public /* synthetic */ u(int i, Object obj, Object obj2) {
        this.a = i;
        this.b = obj;
        this.c = obj2;
    }

    public /* synthetic */ u(BiFunction biFunction, Function function) {
        this.a = 2;
        this.c = biFunction;
        this.b = function;
    }

    public final /* synthetic */ BiConsumer a(BiConsumer biConsumer) {
        switch (this.a) {
            case 0:
                return a.a(this, biConsumer);
            default:
                return a.a(this, biConsumer);
        }
    }

    public final void accept(Object obj) {
        ((Consumer) this.b).accept(obj);
        ((Consumer) this.c).accept(obj);
    }

    /* JADX WARNING: Removed duplicated region for block: B:3:0x000e A[LOOP:0: B:3:0x000e->B:6:0x001c, LOOP_START, PHI: r5 
      PHI: (r5v1 java.lang.Object) = (r5v0 java.lang.Object), (r5v3 java.lang.Object) binds: [B:2:0x000a, B:6:0x001c] A[DONT_GENERATE, DONT_INLINE]] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void accept(java.lang.Object r4, java.lang.Object r5) {
        /*
            r3 = this;
            int r0 = r3.a
            java.lang.Object r1 = r3.c
            java.lang.Object r2 = r3.b
            switch(r0) {
                case 0: goto L_0x000a;
                default: goto L_0x0009;
            }
        L_0x0009:
            goto L_0x001f
        L_0x000a:
            java.util.concurrent.ConcurrentMap r2 = (java.util.concurrent.ConcurrentMap) r2
            java.util.function.BiFunction r1 = (java.util.function.BiFunction) r1
        L_0x000e:
            java.lang.Object r0 = r1.apply(r4, r5)
            boolean r5 = r2.replace(r4, r5, r0)
            if (r5 != 0) goto L_0x001e
            java.lang.Object r5 = r2.get(r4)
            if (r5 != 0) goto L_0x000e
        L_0x001e:
            return
        L_0x001f:
            java.util.function.BiConsumer r2 = (java.util.function.BiConsumer) r2
            java.util.function.BiConsumer r1 = (java.util.function.BiConsumer) r1
            j$.lang.a.e(r2, r1, r4, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.util.concurrent.u.accept(java.lang.Object, java.lang.Object):void");
    }

    public final /* synthetic */ BiFunction andThen(Function function) {
        return BiFunction$CC.$default$andThen(this, function);
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }

    public final Object apply(Object obj, Object obj2) {
        return ((Function) this.b).apply(((BiFunction) this.c).apply(obj, obj2));
    }
}
