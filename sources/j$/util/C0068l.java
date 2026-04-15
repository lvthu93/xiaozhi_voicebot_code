package j$.util;

import androidx.appcompat.widget.ActivityChooserView;
import j$.lang.a;
import java.util.function.IntConsumer;

/* renamed from: j$.util.l  reason: case insensitive filesystem */
public final class C0068l implements IntConsumer {
    private long count;
    private int max = Integer.MIN_VALUE;
    private int min = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
    private long sum;

    public final void a(C0068l lVar) {
        this.count += lVar.count;
        this.sum += lVar.sum;
        this.min = Math.min(this.min, lVar.min);
        this.max = Math.max(this.max, lVar.max);
    }

    public final void accept(int i) {
        this.count++;
        this.sum += (long) i;
        this.min = Math.min(this.min, i);
        this.max = Math.max(this.max, i);
    }

    public final /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
        return a.c(this, intConsumer);
    }

    public final String toString() {
        Object[] objArr = new Object[6];
        objArr[0] = C0068l.class.getSimpleName();
        objArr[1] = Long.valueOf(this.count);
        objArr[2] = Long.valueOf(this.sum);
        objArr[3] = Integer.valueOf(this.min);
        long j = this.count;
        objArr[4] = Double.valueOf(j > 0 ? ((double) this.sum) / ((double) j) : 0.0d);
        objArr[5] = Integer.valueOf(this.max);
        return String.format("%s{count=%d, sum=%d, min=%d, average=%f, max=%d}", objArr);
    }
}
