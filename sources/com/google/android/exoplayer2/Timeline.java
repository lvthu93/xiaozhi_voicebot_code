package com.google.android.exoplayer2;

import android.net.Uri;
import android.os.Bundle;
import android.util.Pair;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.source.ads.AdPlaybackState;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.BundleUtil;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;

public abstract class Timeline implements Bundleable {
    public static final a c = new a();

    public static final class Period implements Bundleable {
        @Nullable
        public Object c;
        @Nullable
        public Object f;
        public int g;
        public long h;
        public long i;
        public boolean j;
        public AdPlaybackState k = AdPlaybackState.k;

        static {
            new f2(13);
        }

        public static String a(int i2) {
            return Integer.toString(i2, 36);
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || !Period.class.equals(obj.getClass())) {
                return false;
            }
            Period period = (Period) obj;
            if (Util.areEqual(this.c, period.c) && Util.areEqual(this.f, period.f) && this.g == period.g && this.h == period.h && this.i == period.i && this.j == period.j && Util.areEqual(this.k, period.k)) {
                return true;
            }
            return false;
        }

        public int getAdCountInAdGroup(int i2) {
            return this.k.h[i2].c;
        }

        public long getAdDurationUs(int i2, int i3) {
            AdPlaybackState.AdGroup adGroup = this.k.h[i2];
            if (adGroup.c != -1) {
                return adGroup.h[i3];
            }
            return -9223372036854775807L;
        }

        public int getAdGroupCount() {
            return this.k.f;
        }

        public int getAdGroupIndexAfterPositionUs(long j2) {
            return this.k.getAdGroupIndexAfterPositionUs(j2, this.h);
        }

        public int getAdGroupIndexForPositionUs(long j2) {
            return this.k.getAdGroupIndexForPositionUs(j2, this.h);
        }

        public long getAdGroupTimeUs(int i2) {
            return this.k.g[i2];
        }

        public long getAdResumePositionUs() {
            return this.k.i;
        }

        @Nullable
        public Object getAdsId() {
            return this.k.c;
        }

        public long getDurationMs() {
            return C.usToMs(this.h);
        }

        public long getDurationUs() {
            return this.h;
        }

        public int getFirstAdIndexToPlay(int i2) {
            return this.k.h[i2].getFirstAdIndexToPlay();
        }

        public int getNextAdIndexToPlay(int i2, int i3) {
            return this.k.h[i2].getNextAdIndexToPlay(i3);
        }

        public long getPositionInWindowMs() {
            return C.usToMs(this.i);
        }

        public long getPositionInWindowUs() {
            return this.i;
        }

        public boolean hasPlayedAdGroup(int i2) {
            return !this.k.h[i2].hasUnplayedAds();
        }

        public int hashCode() {
            int i2;
            Object obj = this.c;
            int i3 = 0;
            if (obj == null) {
                i2 = 0;
            } else {
                i2 = obj.hashCode();
            }
            int i4 = (217 + i2) * 31;
            Object obj2 = this.f;
            if (obj2 != null) {
                i3 = obj2.hashCode();
            }
            long j2 = this.h;
            long j3 = this.i;
            return this.k.hashCode() + ((((((((((i4 + i3) * 31) + this.g) * 31) + ((int) (j2 ^ (j2 >>> 32)))) * 31) + ((int) (j3 ^ (j3 >>> 32)))) * 31) + (this.j ? 1 : 0)) * 31);
        }

        public Period set(@Nullable Object obj, @Nullable Object obj2, int i2, long j2, long j3) {
            return set(obj, obj2, i2, j2, j3, AdPlaybackState.k, false);
        }

        public Bundle toBundle() {
            Bundle bundle = new Bundle();
            bundle.putInt(a(0), this.g);
            bundle.putLong(a(1), this.h);
            bundle.putLong(a(2), this.i);
            bundle.putBoolean(a(3), this.j);
            bundle.putBundle(a(4), this.k.toBundle());
            return bundle;
        }

        public Period set(@Nullable Object obj, @Nullable Object obj2, int i2, long j2, long j3, AdPlaybackState adPlaybackState, boolean z) {
            this.c = obj;
            this.f = obj2;
            this.g = i2;
            this.h = j2;
            this.i = j3;
            this.k = adPlaybackState;
            this.j = z;
            return this;
        }
    }

    public static final class Window implements Bundleable {
        public static final Object v = new Object();
        public static final Object w = new Object();
        public static final MediaItem x = new MediaItem.Builder().setMediaId("com.google.android.exoplayer2.Timeline").setUri(Uri.EMPTY).build();
        public Object c = v;
        @Deprecated
        @Nullable
        public Object f;
        public MediaItem g = x;
        @Nullable
        public Object h;
        public long i;
        public long j;
        public long k;
        public boolean l;
        public boolean m;
        @Deprecated
        public boolean n;
        @Nullable
        public MediaItem.LiveConfiguration o;
        public boolean p;
        public long q;
        public long r;
        public int s;
        public int t;
        public long u;

        static {
            new f2(14);
        }

        public static String a(int i2) {
            return Integer.toString(i2, 36);
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || !Window.class.equals(obj.getClass())) {
                return false;
            }
            Window window = (Window) obj;
            if (Util.areEqual(this.c, window.c) && Util.areEqual(this.g, window.g) && Util.areEqual(this.h, window.h) && Util.areEqual(this.o, window.o) && this.i == window.i && this.j == window.j && this.k == window.k && this.l == window.l && this.m == window.m && this.p == window.p && this.q == window.q && this.r == window.r && this.s == window.s && this.t == window.t && this.u == window.u) {
                return true;
            }
            return false;
        }

        public long getCurrentUnixTimeMs() {
            return Util.getNowUnixTimeMs(this.k);
        }

        public long getDefaultPositionMs() {
            return C.usToMs(this.q);
        }

        public long getDefaultPositionUs() {
            return this.q;
        }

        public long getDurationMs() {
            return C.usToMs(this.r);
        }

        public long getDurationUs() {
            return this.r;
        }

        public long getPositionInFirstPeriodMs() {
            return C.usToMs(this.u);
        }

        public long getPositionInFirstPeriodUs() {
            return this.u;
        }

        public int hashCode() {
            int i2;
            int hashCode = (this.g.hashCode() + ((this.c.hashCode() + 217) * 31)) * 31;
            Object obj = this.h;
            int i3 = 0;
            if (obj == null) {
                i2 = 0;
            } else {
                i2 = obj.hashCode();
            }
            int i4 = (hashCode + i2) * 31;
            MediaItem.LiveConfiguration liveConfiguration = this.o;
            if (liveConfiguration != null) {
                i3 = liveConfiguration.hashCode();
            }
            long j2 = this.i;
            long j3 = this.j;
            long j4 = this.k;
            long j5 = this.q;
            long j6 = this.r;
            long j7 = this.u;
            return ((((((((((((((((((((((i4 + i3) * 31) + ((int) (j2 ^ (j2 >>> 32)))) * 31) + ((int) (j3 ^ (j3 >>> 32)))) * 31) + ((int) (j4 ^ (j4 >>> 32)))) * 31) + (this.l ? 1 : 0)) * 31) + (this.m ? 1 : 0)) * 31) + (this.p ? 1 : 0)) * 31) + ((int) (j5 ^ (j5 >>> 32)))) * 31) + ((int) (j6 ^ (j6 >>> 32)))) * 31) + this.s) * 31) + this.t) * 31) + ((int) (j7 ^ (j7 >>> 32)));
        }

        public boolean isLive() {
            boolean z;
            boolean z2;
            boolean z3 = this.n;
            if (this.o != null) {
                z = true;
            } else {
                z = false;
            }
            if (z3 == z) {
                z2 = true;
            } else {
                z2 = false;
            }
            Assertions.checkState(z2);
            if (this.o != null) {
                return true;
            }
            return false;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:6:0x0011, code lost:
            r1 = r1.f;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public com.google.android.exoplayer2.Timeline.Window set(java.lang.Object r6, @androidx.annotation.Nullable com.google.android.exoplayer2.MediaItem r7, @androidx.annotation.Nullable java.lang.Object r8, long r9, long r11, long r13, boolean r15, boolean r16, @androidx.annotation.Nullable com.google.android.exoplayer2.MediaItem.LiveConfiguration r17, long r18, long r20, int r22, int r23, long r24) {
            /*
                r5 = this;
                r0 = r5
                r1 = r7
                r2 = r17
                r3 = r6
                r0.c = r3
                if (r1 == 0) goto L_0x000b
                r3 = r1
                goto L_0x000d
            L_0x000b:
                com.google.android.exoplayer2.MediaItem r3 = x
            L_0x000d:
                r0.g = r3
                if (r1 == 0) goto L_0x0018
                com.google.android.exoplayer2.MediaItem$PlaybackProperties r1 = r1.f
                if (r1 == 0) goto L_0x0018
                java.lang.Object r1 = r1.h
                goto L_0x0019
            L_0x0018:
                r1 = 0
            L_0x0019:
                r0.f = r1
                r1 = r8
                r0.h = r1
                r3 = r9
                r0.i = r3
                r3 = r11
                r0.j = r3
                r3 = r13
                r0.k = r3
                r1 = r15
                r0.l = r1
                r1 = r16
                r0.m = r1
                r1 = 0
                if (r2 == 0) goto L_0x0033
                r3 = 1
                goto L_0x0034
            L_0x0033:
                r3 = 0
            L_0x0034:
                r0.n = r3
                r0.o = r2
                r2 = r18
                r0.q = r2
                r2 = r20
                r0.r = r2
                r2 = r22
                r0.s = r2
                r2 = r23
                r0.t = r2
                r2 = r24
                r0.u = r2
                r0.p = r1
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.Timeline.Window.set(java.lang.Object, com.google.android.exoplayer2.MediaItem, java.lang.Object, long, long, long, boolean, boolean, com.google.android.exoplayer2.MediaItem$LiveConfiguration, long, long, int, int, long):com.google.android.exoplayer2.Timeline$Window");
        }

        public Bundle toBundle() {
            Bundle bundle = new Bundle();
            bundle.putBundle(a(1), this.g.toBundle());
            bundle.putLong(a(2), this.i);
            bundle.putLong(a(3), this.j);
            bundle.putLong(a(4), this.k);
            bundle.putBoolean(a(5), this.l);
            bundle.putBoolean(a(6), this.m);
            MediaItem.LiveConfiguration liveConfiguration = this.o;
            if (liveConfiguration != null) {
                bundle.putBundle(a(7), liveConfiguration.toBundle());
            }
            bundle.putBoolean(a(8), this.p);
            bundle.putLong(a(9), this.q);
            bundle.putLong(a(10), this.r);
            bundle.putInt(a(11), this.s);
            bundle.putInt(a(12), this.t);
            bundle.putLong(a(13), this.u);
            return bundle;
        }
    }

    public class a extends Timeline {
        public int getIndexOfPeriod(Object obj) {
            return -1;
        }

        public Period getPeriod(int i, Period period, boolean z) {
            throw new IndexOutOfBoundsException();
        }

        public int getPeriodCount() {
            return 0;
        }

        public Object getUidOfPeriod(int i) {
            throw new IndexOutOfBoundsException();
        }

        public Window getWindow(int i, Window window, long j) {
            throw new IndexOutOfBoundsException();
        }

        public int getWindowCount() {
            return 0;
        }
    }

    public static String a(int i) {
        return Integer.toString(i, 36);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Timeline)) {
            return false;
        }
        Timeline timeline = (Timeline) obj;
        if (timeline.getWindowCount() != getWindowCount() || timeline.getPeriodCount() != getPeriodCount()) {
            return false;
        }
        Window window = new Window();
        Period period = new Period();
        Window window2 = new Window();
        Period period2 = new Period();
        for (int i = 0; i < getWindowCount(); i++) {
            if (!getWindow(i, window).equals(timeline.getWindow(i, window2))) {
                return false;
            }
        }
        for (int i2 = 0; i2 < getPeriodCount(); i2++) {
            if (!getPeriod(i2, period, true).equals(timeline.getPeriod(i2, period2, true))) {
                return false;
            }
        }
        return true;
    }

    public int getFirstWindowIndex(boolean z) {
        return isEmpty() ? -1 : 0;
    }

    public abstract int getIndexOfPeriod(Object obj);

    public int getLastWindowIndex(boolean z) {
        if (isEmpty()) {
            return -1;
        }
        return getWindowCount() - 1;
    }

    public final int getNextPeriodIndex(int i, Period period, Window window, int i2, boolean z) {
        int i3 = getPeriod(i, period).g;
        if (getWindow(i3, window).t != i) {
            return i + 1;
        }
        int nextWindowIndex = getNextWindowIndex(i3, i2, z);
        if (nextWindowIndex == -1) {
            return -1;
        }
        return getWindow(nextWindowIndex, window).s;
    }

    public int getNextWindowIndex(int i, int i2, boolean z) {
        if (i2 != 0) {
            if (i2 == 1) {
                return i;
            }
            if (i2 != 2) {
                throw new IllegalStateException();
            } else if (i == getLastWindowIndex(z)) {
                return getFirstWindowIndex(z);
            } else {
                return i + 1;
            }
        } else if (i == getLastWindowIndex(z)) {
            return -1;
        } else {
            return i + 1;
        }
    }

    public final Period getPeriod(int i, Period period) {
        return getPeriod(i, period, false);
    }

    public abstract Period getPeriod(int i, Period period, boolean z);

    public Period getPeriodByUid(Object obj, Period period) {
        return getPeriod(getIndexOfPeriod(obj), period, true);
    }

    public abstract int getPeriodCount();

    public final Pair<Object, Long> getPeriodPosition(Window window, Period period, int i, long j) {
        return (Pair) Assertions.checkNotNull(getPeriodPosition(window, period, i, j, 0));
    }

    public int getPreviousWindowIndex(int i, int i2, boolean z) {
        if (i2 != 0) {
            if (i2 == 1) {
                return i;
            }
            if (i2 != 2) {
                throw new IllegalStateException();
            } else if (i == getFirstWindowIndex(z)) {
                return getLastWindowIndex(z);
            } else {
                return i - 1;
            }
        } else if (i == getFirstWindowIndex(z)) {
            return -1;
        } else {
            return i - 1;
        }
    }

    public abstract Object getUidOfPeriod(int i);

    public final Window getWindow(int i, Window window) {
        return getWindow(i, window, 0);
    }

    public abstract Window getWindow(int i, Window window, long j);

    public abstract int getWindowCount();

    public int hashCode() {
        Window window = new Window();
        Period period = new Period();
        int windowCount = getWindowCount() + 217;
        for (int i = 0; i < getWindowCount(); i++) {
            windowCount = (windowCount * 31) + getWindow(i, window).hashCode();
        }
        int periodCount = getPeriodCount() + (windowCount * 31);
        for (int i2 = 0; i2 < getPeriodCount(); i2++) {
            periodCount = (periodCount * 31) + getPeriod(i2, period, true).hashCode();
        }
        return periodCount;
    }

    public final boolean isEmpty() {
        return getWindowCount() == 0;
    }

    public final boolean isLastPeriod(int i, Period period, Window window, int i2, boolean z) {
        return getNextPeriodIndex(i, period, window, i2, z) == -1;
    }

    public final Bundle toBundle() {
        ArrayList arrayList = new ArrayList();
        int windowCount = getWindowCount();
        Window window = new Window();
        for (int i = 0; i < windowCount; i++) {
            arrayList.add(getWindow(i, window, 0).toBundle());
        }
        ArrayList arrayList2 = new ArrayList();
        int periodCount = getPeriodCount();
        Period period = new Period();
        for (int i2 = 0; i2 < periodCount; i2++) {
            arrayList2.add(getPeriod(i2, period, false).toBundle());
        }
        int[] iArr = new int[windowCount];
        if (windowCount > 0) {
            iArr[0] = getFirstWindowIndex(true);
        }
        for (int i3 = 1; i3 < windowCount; i3++) {
            iArr[i3] = getNextWindowIndex(iArr[i3 - 1], 0, true);
        }
        Bundle bundle = new Bundle();
        BundleUtil.putBinder(bundle, a(0), new BundleListRetriever(arrayList));
        BundleUtil.putBinder(bundle, a(1), new BundleListRetriever(arrayList2));
        bundle.putIntArray(a(2), iArr);
        return bundle;
    }

    @Deprecated
    public final Window getWindow(int i, Window window, boolean z) {
        return getWindow(i, window, 0);
    }

    @Nullable
    public final Pair<Object, Long> getPeriodPosition(Window window, Period period, int i, long j, long j2) {
        Assertions.checkIndex(i, 0, getWindowCount());
        getWindow(i, window, j2);
        if (j == -9223372036854775807L) {
            j = window.getDefaultPositionUs();
            if (j == -9223372036854775807L) {
                return null;
            }
        }
        int i2 = window.s;
        getPeriod(i2, period);
        while (i2 < window.t && period.i != j) {
            int i3 = i2 + 1;
            if (getPeriod(i3, period).i > j) {
                break;
            }
            i2 = i3;
        }
        getPeriod(i2, period, true);
        return Pair.create(Assertions.checkNotNull(period.f), Long.valueOf(j - period.i));
    }
}
