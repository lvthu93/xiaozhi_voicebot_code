package com.google.android.exoplayer2;

import android.util.Pair;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ShuffleOrder;
import com.google.android.exoplayer2.util.Assertions;

public abstract class AbstractConcatenatedTimeline extends Timeline {
    public final int f;
    public final ShuffleOrder g;
    public final boolean h;

    public AbstractConcatenatedTimeline(boolean z, ShuffleOrder shuffleOrder) {
        this.h = z;
        this.g = shuffleOrder;
        this.f = shuffleOrder.getLength();
    }

    public static Object getChildPeriodUidFromConcatenatedUid(Object obj) {
        return ((Pair) obj).second;
    }

    public static Object getChildTimelineUidFromConcatenatedUid(Object obj) {
        return ((Pair) obj).first;
    }

    public static Object getConcatenatedUid(Object obj, Object obj2) {
        return Pair.create(obj, obj2);
    }

    public abstract int b(Object obj);

    public abstract int c(int i);

    public abstract int d(int i);

    public abstract Object e(int i);

    public abstract int f(int i);

    public abstract int g(int i);

    public int getFirstWindowIndex(boolean z) {
        if (this.f == 0) {
            return -1;
        }
        int i = 0;
        if (this.h) {
            z = false;
        }
        if (z) {
            i = this.g.getFirstIndex();
        }
        while (i(i).isEmpty()) {
            i = h(i, z);
            if (i == -1) {
                return -1;
            }
        }
        return i(i).getFirstWindowIndex(z) + g(i);
    }

    public final int getIndexOfPeriod(Object obj) {
        int indexOfPeriod;
        if (!(obj instanceof Pair)) {
            return -1;
        }
        Object childTimelineUidFromConcatenatedUid = getChildTimelineUidFromConcatenatedUid(obj);
        Object childPeriodUidFromConcatenatedUid = getChildPeriodUidFromConcatenatedUid(obj);
        int b = b(childTimelineUidFromConcatenatedUid);
        if (b == -1 || (indexOfPeriod = i(b).getIndexOfPeriod(childPeriodUidFromConcatenatedUid)) == -1) {
            return -1;
        }
        return f(b) + indexOfPeriod;
    }

    public int getLastWindowIndex(boolean z) {
        int i;
        int i2;
        int i3 = this.f;
        if (i3 == 0) {
            return -1;
        }
        if (this.h) {
            z = false;
        }
        ShuffleOrder shuffleOrder = this.g;
        if (z) {
            i = shuffleOrder.getLastIndex();
        } else {
            i = i3 - 1;
        }
        while (i(i).isEmpty()) {
            if (z) {
                i2 = shuffleOrder.getPreviousIndex(i);
                continue;
            } else if (i > 0) {
                i2 = i - 1;
                continue;
            } else {
                i2 = -1;
                continue;
            }
            if (i == -1) {
                return -1;
            }
        }
        return i(i).getLastWindowIndex(z) + g(i);
    }

    public int getNextWindowIndex(int i, int i2, boolean z) {
        int i3 = 0;
        if (this.h) {
            if (i2 == 1) {
                i2 = 2;
            }
            z = false;
        }
        int d = d(i);
        int g2 = g(d);
        Timeline i4 = i(d);
        int i5 = i - g2;
        if (i2 != 2) {
            i3 = i2;
        }
        int nextWindowIndex = i4.getNextWindowIndex(i5, i3, z);
        if (nextWindowIndex != -1) {
            return g2 + nextWindowIndex;
        }
        int h2 = h(d, z);
        while (h2 != -1 && i(h2).isEmpty()) {
            h2 = h(h2, z);
        }
        if (h2 != -1) {
            return i(h2).getFirstWindowIndex(z) + g(h2);
        } else if (i2 == 2) {
            return getFirstWindowIndex(z);
        } else {
            return -1;
        }
    }

    public final Timeline.Period getPeriod(int i, Timeline.Period period, boolean z) {
        int c = c(i);
        int g2 = g(c);
        i(c).getPeriod(i - f(c), period, z);
        period.g += g2;
        if (z) {
            period.f = getConcatenatedUid(e(c), Assertions.checkNotNull(period.f));
        }
        return period;
    }

    public final Timeline.Period getPeriodByUid(Object obj, Timeline.Period period) {
        Object childTimelineUidFromConcatenatedUid = getChildTimelineUidFromConcatenatedUid(obj);
        Object childPeriodUidFromConcatenatedUid = getChildPeriodUidFromConcatenatedUid(obj);
        int b = b(childTimelineUidFromConcatenatedUid);
        int g2 = g(b);
        i(b).getPeriodByUid(childPeriodUidFromConcatenatedUid, period);
        period.g += g2;
        period.f = obj;
        return period;
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x003f  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x004b A[EDGE_INSN: B:34:0x004b->B:26:0x004b ?: BREAK  
    EDGE_INSN: B:36:0x004b->B:26:0x004b ?: BREAK  ] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x004d  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x005b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getPreviousWindowIndex(int r6, int r7, boolean r8) {
        /*
            r5 = this;
            boolean r0 = r5.h
            r1 = 0
            r2 = 2
            if (r0 == 0) goto L_0x000b
            r8 = 1
            if (r7 != r8) goto L_0x000a
            r7 = 2
        L_0x000a:
            r8 = 0
        L_0x000b:
            int r0 = r5.d(r6)
            int r3 = r5.g(r0)
            com.google.android.exoplayer2.Timeline r4 = r5.i(r0)
            int r6 = r6 - r3
            if (r7 != r2) goto L_0x001b
            goto L_0x001c
        L_0x001b:
            r1 = r7
        L_0x001c:
            int r6 = r4.getPreviousWindowIndex(r6, r1, r8)
            r1 = -1
            if (r6 == r1) goto L_0x0025
            int r3 = r3 + r6
            return r3
        L_0x0025:
            com.google.android.exoplayer2.source.ShuffleOrder r6 = r5.g
            if (r8 == 0) goto L_0x002e
            int r0 = r6.getPreviousIndex(r0)
            goto L_0x0033
        L_0x002e:
            if (r0 <= 0) goto L_0x0032
            int r0 = r0 + r1
            goto L_0x0033
        L_0x0032:
            r0 = -1
        L_0x0033:
            if (r0 == r1) goto L_0x004b
            com.google.android.exoplayer2.Timeline r3 = r5.i(r0)
            boolean r3 = r3.isEmpty()
            if (r3 == 0) goto L_0x004b
            if (r8 == 0) goto L_0x0046
            int r0 = r6.getPreviousIndex(r0)
            goto L_0x0033
        L_0x0046:
            if (r0 <= 0) goto L_0x0032
            int r0 = r0 + -1
            goto L_0x0033
        L_0x004b:
            if (r0 == r1) goto L_0x005b
            int r6 = r5.g(r0)
            com.google.android.exoplayer2.Timeline r7 = r5.i(r0)
            int r7 = r7.getLastWindowIndex(r8)
            int r7 = r7 + r6
            return r7
        L_0x005b:
            if (r7 != r2) goto L_0x0062
            int r6 = r5.getLastWindowIndex(r8)
            return r6
        L_0x0062:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.AbstractConcatenatedTimeline.getPreviousWindowIndex(int, int, boolean):int");
    }

    public final Object getUidOfPeriod(int i) {
        int c = c(i);
        return getConcatenatedUid(e(c), i(c).getUidOfPeriod(i - f(c)));
    }

    public final Timeline.Window getWindow(int i, Timeline.Window window, long j) {
        int d = d(i);
        int g2 = g(d);
        int f2 = f(d);
        i(d).getWindow(i - g2, window, j);
        Object e = e(d);
        if (!Timeline.Window.v.equals(window.c)) {
            e = getConcatenatedUid(e, window.c);
        }
        window.c = e;
        window.s += f2;
        window.t += f2;
        return window;
    }

    public final int h(int i, boolean z) {
        if (z) {
            return this.g.getNextIndex(i);
        }
        if (i < this.f - 1) {
            return i + 1;
        }
        return -1;
    }

    public abstract Timeline i(int i);
}
