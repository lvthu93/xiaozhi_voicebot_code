package com.google.android.exoplayer2.source.ads;

import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.CheckResult;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Bundleable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;

public final class AdPlaybackState implements Bundleable {
    public static final AdPlaybackState k = new AdPlaybackState((Object) null, new long[0], (AdGroup[]) null, 0, -9223372036854775807L);
    public static final z6 l = new z6(15);
    @Nullable
    public final Object c;
    public final int f;
    public final long[] g;
    public final AdGroup[] h;
    public final long i;
    public final long j;

    public static final class AdGroup implements Bundleable {
        public static final z6 i = new z6(16);
        public final int c;
        public final Uri[] f;
        public final int[] g;
        public final long[] h;

        public AdGroup() {
            this(-1, new int[0], new Uri[0], new long[0]);
        }

        @CheckResult
        public static long[] a(long[] jArr, int i2) {
            int length = jArr.length;
            int max = Math.max(i2, length);
            long[] copyOf = Arrays.copyOf(jArr, max);
            Arrays.fill(copyOf, length, max, -9223372036854775807L);
            return copyOf;
        }

        public static String b(int i2) {
            return Integer.toString(i2, 36);
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || AdGroup.class != obj.getClass()) {
                return false;
            }
            AdGroup adGroup = (AdGroup) obj;
            if (this.c != adGroup.c || !Arrays.equals(this.f, adGroup.f) || !Arrays.equals(this.g, adGroup.g) || !Arrays.equals(this.h, adGroup.h)) {
                return false;
            }
            return true;
        }

        public int getFirstAdIndexToPlay() {
            return getNextAdIndexToPlay(-1);
        }

        public int getNextAdIndexToPlay(int i2) {
            int i3;
            int i4 = i2 + 1;
            while (true) {
                int[] iArr = this.g;
                if (i4 >= iArr.length || (i3 = iArr[i4]) == 0 || i3 == 1) {
                    return i4;
                }
                i4++;
            }
            return i4;
        }

        public boolean hasUnplayedAds() {
            int i2 = this.c;
            return i2 == -1 || getFirstAdIndexToPlay() < i2;
        }

        public int hashCode() {
            int hashCode = Arrays.hashCode(this.g);
            return Arrays.hashCode(this.h) + ((hashCode + (((this.c * 31) + Arrays.hashCode(this.f)) * 31)) * 31);
        }

        public Bundle toBundle() {
            Bundle bundle = new Bundle();
            bundle.putInt(b(0), this.c);
            bundle.putParcelableArrayList(b(1), new ArrayList(Arrays.asList(this.f)));
            bundle.putIntArray(b(2), this.g);
            bundle.putLongArray(b(3), this.h);
            return bundle;
        }

        @CheckResult
        public AdGroup withAdCount(int i2) {
            int[] iArr = this.g;
            int length = iArr.length;
            int max = Math.max(i2, length);
            int[] copyOf = Arrays.copyOf(iArr, max);
            Arrays.fill(copyOf, length, max, 0);
            return new AdGroup(i2, copyOf, (Uri[]) Arrays.copyOf(this.f, i2), a(this.h, i2));
        }

        @CheckResult
        public AdGroup withAdDurationsUs(long[] jArr) {
            int length = jArr.length;
            Uri[] uriArr = this.f;
            int length2 = uriArr.length;
            int i2 = this.c;
            if (length < length2) {
                jArr = a(jArr, uriArr.length);
            } else if (i2 != -1 && jArr.length > uriArr.length) {
                jArr = Arrays.copyOf(jArr, uriArr.length);
            }
            return new AdGroup(i2, this.g, uriArr, jArr);
        }

        @CheckResult
        public AdGroup withAdState(int i2, int i3) {
            boolean z;
            boolean z2 = false;
            int i4 = this.c;
            if (i4 == -1 || i3 < i4) {
                z = true;
            } else {
                z = false;
            }
            Assertions.checkArgument(z);
            int[] iArr = this.g;
            int length = iArr.length;
            int max = Math.max(i3 + 1, length);
            int[] copyOf = Arrays.copyOf(iArr, max);
            Arrays.fill(copyOf, length, max, 0);
            int i5 = copyOf[i3];
            if (i5 == 0 || i5 == 1 || i5 == i2) {
                z2 = true;
            }
            Assertions.checkArgument(z2);
            long[] jArr = this.h;
            if (jArr.length != copyOf.length) {
                jArr = a(jArr, copyOf.length);
            }
            Uri[] uriArr = this.f;
            if (uriArr.length != copyOf.length) {
                uriArr = (Uri[]) Arrays.copyOf(uriArr, copyOf.length);
            }
            copyOf[i3] = i2;
            return new AdGroup(i4, copyOf, uriArr, jArr);
        }

        @CheckResult
        public AdGroup withAdUri(Uri uri, int i2) {
            int[] iArr = this.g;
            int length = iArr.length;
            int max = Math.max(i2 + 1, length);
            int[] copyOf = Arrays.copyOf(iArr, max);
            Arrays.fill(copyOf, length, max, 0);
            long[] jArr = this.h;
            if (jArr.length != copyOf.length) {
                jArr = a(jArr, copyOf.length);
            }
            Uri[] uriArr = (Uri[]) Arrays.copyOf(this.f, copyOf.length);
            uriArr[i2] = uri;
            copyOf[i2] = 1;
            return new AdGroup(this.c, copyOf, uriArr, jArr);
        }

        @CheckResult
        public AdGroup withAllAdsSkipped() {
            if (this.c == -1) {
                return new AdGroup(0, new int[0], new Uri[0], new long[0]);
            }
            int[] iArr = this.g;
            int length = iArr.length;
            int[] copyOf = Arrays.copyOf(iArr, length);
            for (int i2 = 0; i2 < length; i2++) {
                int i3 = copyOf[i2];
                if (i3 == 1 || i3 == 0) {
                    copyOf[i2] = 2;
                }
            }
            return new AdGroup(length, copyOf, this.f, this.h);
        }

        public AdGroup(int i2, int[] iArr, Uri[] uriArr, long[] jArr) {
            Assertions.checkArgument(iArr.length == uriArr.length);
            this.c = i2;
            this.g = iArr;
            this.f = uriArr;
            this.h = jArr;
        }
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface AdState {
    }

    public AdPlaybackState(Object obj, long... jArr) {
        this(obj, jArr, (AdGroup[]) null, 0, -9223372036854775807L);
    }

    public static String a(int i2) {
        return Integer.toString(i2, 36);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || AdPlaybackState.class != obj.getClass()) {
            return false;
        }
        AdPlaybackState adPlaybackState = (AdPlaybackState) obj;
        if (!Util.areEqual(this.c, adPlaybackState.c) || this.f != adPlaybackState.f || this.i != adPlaybackState.i || this.j != adPlaybackState.j || !Arrays.equals(this.g, adPlaybackState.g) || !Arrays.equals(this.h, adPlaybackState.h)) {
            return false;
        }
        return true;
    }

    public int getAdGroupIndexAfterPositionUs(long j2, long j3) {
        long[] jArr;
        if (j2 == Long.MIN_VALUE) {
            return -1;
        }
        if (j3 != -9223372036854775807L && j2 >= j3) {
            return -1;
        }
        int i2 = 0;
        while (true) {
            jArr = this.g;
            if (i2 >= jArr.length) {
                break;
            }
            long j4 = jArr[i2];
            if ((j4 == Long.MIN_VALUE || j4 > j2) && this.h[i2].hasUnplayedAds()) {
                break;
            }
            i2++;
        }
        if (i2 < jArr.length) {
            return i2;
        }
        return -1;
    }

    public int getAdGroupIndexForPositionUs(long j2, long j3) {
        long[] jArr = this.g;
        int length = jArr.length - 1;
        while (length >= 0) {
            boolean z = false;
            if (j2 != Long.MIN_VALUE) {
                long j4 = jArr[length];
                if (j4 != Long.MIN_VALUE ? j2 < j4 : !(j3 != -9223372036854775807L && j2 >= j3)) {
                    z = true;
                }
            }
            if (!z) {
                break;
            }
            length--;
        }
        if (length < 0 || !this.h[length].hasUnplayedAds()) {
            return -1;
        }
        return length;
    }

    public int hashCode() {
        int i2;
        int i3 = this.f * 31;
        Object obj = this.c;
        if (obj == null) {
            i2 = 0;
        } else {
            i2 = obj.hashCode();
        }
        return ((Arrays.hashCode(this.g) + ((((((i3 + i2) * 31) + ((int) this.i)) * 31) + ((int) this.j)) * 31)) * 31) + Arrays.hashCode(this.h);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0007, code lost:
        r4 = r0[r4];
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isAdInErrorState(int r4, int r5) {
        /*
            r3 = this;
            com.google.android.exoplayer2.source.ads.AdPlaybackState$AdGroup[] r0 = r3.h
            int r1 = r0.length
            r2 = 0
            if (r4 < r1) goto L_0x0007
            return r2
        L_0x0007:
            r4 = r0[r4]
            int r0 = r4.c
            r1 = -1
            if (r0 == r1) goto L_0x0019
            if (r5 < r0) goto L_0x0011
            goto L_0x0019
        L_0x0011:
            int[] r4 = r4.g
            r4 = r4[r5]
            r5 = 4
            if (r4 != r5) goto L_0x0019
            r2 = 1
        L_0x0019:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.ads.AdPlaybackState.isAdInErrorState(int, int):boolean");
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putLongArray(a(1), this.g);
        ArrayList arrayList = new ArrayList();
        for (AdGroup bundle2 : this.h) {
            arrayList.add(bundle2.toBundle());
        }
        bundle.putParcelableArrayList(a(2), arrayList);
        bundle.putLong(a(3), this.i);
        bundle.putLong(a(4), this.j);
        return bundle;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("AdPlaybackState(adsId=");
        sb.append(this.c);
        sb.append(", adResumePositionUs=");
        sb.append(this.i);
        sb.append(", adGroups=[");
        int i2 = 0;
        while (true) {
            AdGroup[] adGroupArr = this.h;
            if (i2 < adGroupArr.length) {
                sb.append("adGroup(timeUs=");
                sb.append(this.g[i2]);
                sb.append(", ads=[");
                for (int i3 = 0; i3 < adGroupArr[i2].g.length; i3++) {
                    sb.append("ad(state=");
                    int i4 = adGroupArr[i2].g[i3];
                    if (i4 == 0) {
                        sb.append('_');
                    } else if (i4 == 1) {
                        sb.append('R');
                    } else if (i4 == 2) {
                        sb.append('S');
                    } else if (i4 == 3) {
                        sb.append('P');
                    } else if (i4 != 4) {
                        sb.append('?');
                    } else {
                        sb.append('!');
                    }
                    sb.append(", durationUs=");
                    sb.append(adGroupArr[i2].h[i3]);
                    sb.append(')');
                    if (i3 < adGroupArr[i2].g.length - 1) {
                        sb.append(", ");
                    }
                }
                sb.append("])");
                if (i2 < adGroupArr.length - 1) {
                    sb.append(", ");
                }
                i2++;
            } else {
                sb.append("])");
                return sb.toString();
            }
        }
    }

    @CheckResult
    public AdPlaybackState withAdCount(int i2, int i3) {
        boolean z;
        if (i3 > 0) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkArgument(z);
        AdGroup[] adGroupArr = this.h;
        if (adGroupArr[i2].c == i3) {
            return this;
        }
        AdGroup[] adGroupArr2 = (AdGroup[]) Util.nullSafeArrayCopy(adGroupArr, adGroupArr.length);
        adGroupArr2[i2] = adGroupArr[i2].withAdCount(i3);
        return new AdPlaybackState(this.c, this.g, adGroupArr2, this.i, this.j);
    }

    @CheckResult
    public AdPlaybackState withAdDurationsUs(long[][] jArr) {
        AdGroup[] adGroupArr = this.h;
        AdGroup[] adGroupArr2 = (AdGroup[]) Util.nullSafeArrayCopy(adGroupArr, adGroupArr.length);
        for (int i2 = 0; i2 < this.f; i2++) {
            adGroupArr2[i2] = adGroupArr2[i2].withAdDurationsUs(jArr[i2]);
        }
        return new AdPlaybackState(this.c, this.g, adGroupArr2, this.i, this.j);
    }

    @CheckResult
    public AdPlaybackState withAdLoadError(int i2, int i3) {
        AdGroup[] adGroupArr = this.h;
        AdGroup[] adGroupArr2 = (AdGroup[]) Util.nullSafeArrayCopy(adGroupArr, adGroupArr.length);
        adGroupArr2[i2] = adGroupArr2[i2].withAdState(4, i3);
        return new AdPlaybackState(this.c, this.g, adGroupArr2, this.i, this.j);
    }

    @CheckResult
    public AdPlaybackState withAdResumePositionUs(long j2) {
        if (this.i == j2) {
            return this;
        }
        return new AdPlaybackState(this.c, this.g, this.h, j2, this.j);
    }

    @CheckResult
    public AdPlaybackState withAdUri(int i2, int i3, Uri uri) {
        AdGroup[] adGroupArr = this.h;
        AdGroup[] adGroupArr2 = (AdGroup[]) Util.nullSafeArrayCopy(adGroupArr, adGroupArr.length);
        adGroupArr2[i2] = adGroupArr2[i2].withAdUri(uri, i3);
        return new AdPlaybackState(this.c, this.g, adGroupArr2, this.i, this.j);
    }

    @CheckResult
    public AdPlaybackState withContentDurationUs(long j2) {
        if (this.j == j2) {
            return this;
        }
        return new AdPlaybackState(this.c, this.g, this.h, this.i, j2);
    }

    @CheckResult
    public AdPlaybackState withPlayedAd(int i2, int i3) {
        AdGroup[] adGroupArr = this.h;
        AdGroup[] adGroupArr2 = (AdGroup[]) Util.nullSafeArrayCopy(adGroupArr, adGroupArr.length);
        adGroupArr2[i2] = adGroupArr2[i2].withAdState(3, i3);
        return new AdPlaybackState(this.c, this.g, adGroupArr2, this.i, this.j);
    }

    @CheckResult
    public AdPlaybackState withSkippedAd(int i2, int i3) {
        AdGroup[] adGroupArr = this.h;
        AdGroup[] adGroupArr2 = (AdGroup[]) Util.nullSafeArrayCopy(adGroupArr, adGroupArr.length);
        adGroupArr2[i2] = adGroupArr2[i2].withAdState(2, i3);
        return new AdPlaybackState(this.c, this.g, adGroupArr2, this.i, this.j);
    }

    @CheckResult
    public AdPlaybackState withSkippedAdGroup(int i2) {
        AdGroup[] adGroupArr = this.h;
        AdGroup[] adGroupArr2 = (AdGroup[]) Util.nullSafeArrayCopy(adGroupArr, adGroupArr.length);
        adGroupArr2[i2] = adGroupArr2[i2].withAllAdsSkipped();
        return new AdPlaybackState(this.c, this.g, adGroupArr2, this.i, this.j);
    }

    public AdPlaybackState(@Nullable Object obj, long[] jArr, @Nullable AdGroup[] adGroupArr, long j2, long j3) {
        Assertions.checkArgument(adGroupArr == null || adGroupArr.length == jArr.length);
        this.c = obj;
        this.g = jArr;
        this.i = j2;
        this.j = j3;
        int length = jArr.length;
        this.f = length;
        if (adGroupArr == null) {
            adGroupArr = new AdGroup[length];
            for (int i2 = 0; i2 < this.f; i2++) {
                adGroupArr[i2] = new AdGroup();
            }
        }
        this.h = adGroupArr;
    }
}
