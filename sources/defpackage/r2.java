package defpackage;

import com.google.android.exoplayer2.ExoPlayerImplInternal;
import com.google.common.base.Supplier;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: r2  reason: default package */
public final /* synthetic */ class r2 implements Supplier {
    public final /* synthetic */ int c;
    public final /* synthetic */ Object f;

    public /* synthetic */ r2(int i, Object obj) {
        this.c = i;
        this.f = obj;
    }

    public final Object get() {
        int i = this.c;
        Object obj = this.f;
        switch (i) {
            case 0:
                return Boolean.valueOf(((ExoPlayerImplInternal) obj).ac);
            default:
                return Boolean.valueOf(((AtomicBoolean) obj).get());
        }
    }
}
