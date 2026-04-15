package com.google.android.exoplayer2.trackselection;

import android.util.Pair;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.RendererCapabilities;
import com.google.android.exoplayer2.RendererConfiguration;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;

public abstract class MappingTrackSelector extends TrackSelector {
    @Nullable
    public MappedTrackInfo c;

    public abstract Pair<RendererConfiguration[], ExoTrackSelection[]> a(MappedTrackInfo mappedTrackInfo, int[][][] iArr, int[] iArr2, MediaSource.MediaPeriodId mediaPeriodId, Timeline timeline) throws ExoPlaybackException;

    @Nullable
    public final MappedTrackInfo getCurrentMappedTrackInfo() {
        return this.c;
    }

    public final void onSelectionActivated(@Nullable Object obj) {
        this.c = (MappedTrackInfo) obj;
    }

    public final TrackSelectorResult selectTracks(RendererCapabilities[] rendererCapabilitiesArr, TrackGroupArray trackGroupArray, MediaSource.MediaPeriodId mediaPeriodId, Timeline timeline) throws ExoPlaybackException {
        boolean z;
        int i;
        int[] iArr;
        boolean z2;
        RendererCapabilities[] rendererCapabilitiesArr2 = rendererCapabilitiesArr;
        TrackGroupArray trackGroupArray2 = trackGroupArray;
        int[] iArr2 = new int[(rendererCapabilitiesArr2.length + 1)];
        int length = rendererCapabilitiesArr2.length + 1;
        TrackGroup[][] trackGroupArr = new TrackGroup[length][];
        int[][][] iArr3 = new int[(rendererCapabilitiesArr2.length + 1)][][];
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            int i4 = trackGroupArray2.c;
            trackGroupArr[i3] = new TrackGroup[i4];
            iArr3[i3] = new int[i4][];
        }
        int length2 = rendererCapabilitiesArr2.length;
        int[] iArr4 = new int[length2];
        for (int i5 = 0; i5 < length2; i5++) {
            iArr4[i5] = rendererCapabilitiesArr2[i5].supportsMixedMimeTypeAdaptation();
        }
        int i6 = 0;
        while (i6 < trackGroupArray2.c) {
            TrackGroup trackGroup = trackGroupArray2.get(i6);
            if (MimeTypes.getTrackType(trackGroup.getFormat(i2).p) == 5) {
                z = true;
            } else {
                z = false;
            }
            int length3 = rendererCapabilitiesArr2.length;
            int i7 = 0;
            int i8 = 0;
            boolean z3 = true;
            while (true) {
                int length4 = rendererCapabilitiesArr2.length;
                i = trackGroup.c;
                if (i7 >= length4) {
                    break;
                }
                RendererCapabilities rendererCapabilities = rendererCapabilitiesArr2[i7];
                int i9 = 0;
                int i10 = 0;
                while (i10 < i) {
                    i9 = Math.max(i9, ha.d(rendererCapabilities.supportsFormat(trackGroup.getFormat(i10))));
                    i10++;
                    iArr4 = iArr4;
                }
                int[] iArr5 = iArr4;
                if (iArr2[i7] == 0) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (i9 > i8 || (i9 == i8 && z && !z3 && z2)) {
                    i8 = i9;
                    z3 = z2;
                    length3 = i7;
                }
                i7++;
                TrackGroupArray trackGroupArray3 = trackGroupArray;
                iArr4 = iArr5;
            }
            int[] iArr6 = iArr4;
            if (length3 == rendererCapabilitiesArr2.length) {
                iArr = new int[i];
            } else {
                RendererCapabilities rendererCapabilities2 = rendererCapabilitiesArr2[length3];
                int[] iArr7 = new int[i];
                for (int i11 = 0; i11 < i; i11++) {
                    iArr7[i11] = rendererCapabilities2.supportsFormat(trackGroup.getFormat(i11));
                }
                iArr = iArr7;
            }
            int i12 = iArr2[length3];
            trackGroupArr[length3][i12] = trackGroup;
            iArr3[length3][i12] = iArr;
            iArr2[length3] = i12 + 1;
            i6++;
            trackGroupArray2 = trackGroupArray;
            iArr4 = iArr6;
            i2 = 0;
        }
        int[] iArr8 = iArr4;
        TrackGroupArray[] trackGroupArrayArr = new TrackGroupArray[rendererCapabilitiesArr2.length];
        String[] strArr = new String[rendererCapabilitiesArr2.length];
        int[] iArr9 = new int[rendererCapabilitiesArr2.length];
        for (int i13 = 0; i13 < rendererCapabilitiesArr2.length; i13++) {
            int i14 = iArr2[i13];
            trackGroupArrayArr[i13] = new TrackGroupArray((TrackGroup[]) Util.nullSafeArrayCopy(trackGroupArr[i13], i14));
            iArr3[i13] = (int[][]) Util.nullSafeArrayCopy(iArr3[i13], i14);
            strArr[i13] = rendererCapabilitiesArr2[i13].getName();
            iArr9[i13] = rendererCapabilitiesArr2[i13].getTrackType();
        }
        int[] iArr10 = iArr8;
        int[][][] iArr11 = iArr3;
        MappedTrackInfo mappedTrackInfo = new MappedTrackInfo(strArr, iArr9, trackGroupArrayArr, iArr10, iArr11, new TrackGroupArray((TrackGroup[]) Util.nullSafeArrayCopy(trackGroupArr[rendererCapabilitiesArr2.length], iArr2[rendererCapabilitiesArr2.length])));
        Pair<RendererConfiguration[], ExoTrackSelection[]> a = a(mappedTrackInfo, iArr3, iArr8, mediaPeriodId, timeline);
        return new TrackSelectorResult((RendererConfiguration[]) a.first, (ExoTrackSelection[]) a.second, mappedTrackInfo);
    }

    public static final class MappedTrackInfo {
        public final int a;
        public final String[] b;
        public final int[] c;
        public final TrackGroupArray[] d;
        public final int[] e;
        public final int[][][] f;
        public final TrackGroupArray g;

        public MappedTrackInfo(String[] strArr, int[] iArr, TrackGroupArray[] trackGroupArrayArr, int[] iArr2, int[][][] iArr3, TrackGroupArray trackGroupArray) {
            this.b = strArr;
            this.c = iArr;
            this.d = trackGroupArrayArr;
            this.f = iArr3;
            this.e = iArr2;
            this.g = trackGroupArray;
            this.a = iArr.length;
        }

        public int getAdaptiveSupport(int i, int i2, boolean z) {
            int i3 = this.d[i].get(i2).c;
            int[] iArr = new int[i3];
            int i4 = 0;
            for (int i5 = 0; i5 < i3; i5++) {
                int trackSupport = getTrackSupport(i, i2, i5);
                if (trackSupport == 4 || (z && trackSupport == 3)) {
                    iArr[i4] = i5;
                    i4++;
                }
            }
            return getAdaptiveSupport(i, i2, Arrays.copyOf(iArr, i4));
        }

        public int getRendererCount() {
            return this.a;
        }

        public String getRendererName(int i) {
            return this.b[i];
        }

        public int getRendererSupport(int i) {
            int i2 = 0;
            for (int[] iArr : this.f[i]) {
                for (int d2 : r11[r2]) {
                    int d3 = ha.d(d2);
                    int i3 = 1;
                    if (!(d3 == 0 || d3 == 1 || d3 == 2)) {
                        if (d3 == 3) {
                            i3 = 2;
                        } else if (d3 == 4) {
                            return 3;
                        } else {
                            throw new IllegalStateException();
                        }
                    }
                    i2 = Math.max(i2, i3);
                }
            }
            return i2;
        }

        public int getRendererType(int i) {
            return this.c[i];
        }

        public TrackGroupArray getTrackGroups(int i) {
            return this.d[i];
        }

        public int getTrackSupport(int i, int i2, int i3) {
            return ha.d(this.f[i][i2][i3]);
        }

        public int getTypeSupport(int i) {
            int i2 = 0;
            for (int i3 = 0; i3 < this.a; i3++) {
                if (this.c[i3] == i) {
                    i2 = Math.max(i2, getRendererSupport(i3));
                }
            }
            return i2;
        }

        public TrackGroupArray getUnmappedTrackGroups() {
            return this.g;
        }

        public int getAdaptiveSupport(int i, int i2, int[] iArr) {
            int i3 = 0;
            String str = null;
            boolean z = false;
            int i4 = 0;
            int i5 = 16;
            while (i3 < iArr.length) {
                String str2 = this.d[i].get(i2).getFormat(iArr[i3]).p;
                int i6 = i4 + 1;
                if (i4 == 0) {
                    str = str2;
                } else {
                    z |= !Util.areEqual(str, str2);
                }
                i5 = Math.min(i5, ha.c(this.f[i][i2][i3]));
                i3++;
                i4 = i6;
            }
            return z ? Math.min(i5, this.e[i]) : i5;
        }
    }
}
