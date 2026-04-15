package defpackage;

import com.google.android.exoplayer2.AbstractConcatenatedTimeline;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ShuffleOrder;
import com.google.android.exoplayer2.util.Util;
import java.util.Collection;
import java.util.HashMap;

/* renamed from: z8  reason: default package */
public final class z8 extends AbstractConcatenatedTimeline {
    public final int i;
    public final int j;
    public final int[] k;
    public final int[] l;
    public final Timeline[] m;
    public final Object[] n;
    public final HashMap<Object, Integer> o = new HashMap<>();

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public z8(Collection<? extends k6> collection, ShuffleOrder shuffleOrder) {
        super(false, shuffleOrder);
        int i2 = 0;
        int size = collection.size();
        this.k = new int[size];
        this.l = new int[size];
        this.m = new Timeline[size];
        this.n = new Object[size];
        int i3 = 0;
        int i4 = 0;
        for (k6 k6Var : collection) {
            this.m[i4] = k6Var.getTimeline();
            this.l[i4] = i2;
            this.k[i4] = i3;
            i2 += this.m[i4].getWindowCount();
            i3 += this.m[i4].getPeriodCount();
            this.n[i4] = k6Var.getUid();
            this.o.put(this.n[i4], Integer.valueOf(i4));
            i4++;
        }
        this.i = i2;
        this.j = i3;
    }

    public final int b(Object obj) {
        Integer num = this.o.get(obj);
        if (num == null) {
            return -1;
        }
        return num.intValue();
    }

    public final int c(int i2) {
        return Util.binarySearchFloor(this.k, i2 + 1, false, false);
    }

    public final int d(int i2) {
        return Util.binarySearchFloor(this.l, i2 + 1, false, false);
    }

    public final Object e(int i2) {
        return this.n[i2];
    }

    public final int f(int i2) {
        return this.k[i2];
    }

    public final int g(int i2) {
        return this.l[i2];
    }

    public int getPeriodCount() {
        return this.j;
    }

    public int getWindowCount() {
        return this.i;
    }

    public final Timeline i(int i2) {
        return this.m[i2];
    }
}
