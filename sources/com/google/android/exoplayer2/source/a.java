package com.google.android.exoplayer2.source;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.util.Assertions;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;

public final class a implements MediaPeriod, MediaPeriod.Callback {
    public final MediaPeriod[] c;
    public final IdentityHashMap<SampleStream, Integer> f;
    public final CompositeSequenceableLoaderFactory g;
    public final ArrayList<MediaPeriod> h = new ArrayList<>();
    @Nullable
    public MediaPeriod.Callback i;
    @Nullable
    public TrackGroupArray j;
    public MediaPeriod[] k;
    public SequenceableLoader l;

    /* renamed from: com.google.android.exoplayer2.source.a$a  reason: collision with other inner class name */
    public static final class C0017a implements MediaPeriod, MediaPeriod.Callback {
        public final MediaPeriod c;
        public final long f;
        public MediaPeriod.Callback g;

        public C0017a(MediaPeriod mediaPeriod, long j) {
            this.c = mediaPeriod;
            this.f = j;
        }

        public boolean continueLoading(long j) {
            return this.c.continueLoading(j - this.f);
        }

        public void discardBuffer(long j, boolean z) {
            this.c.discardBuffer(j - this.f, z);
        }

        public long getAdjustedSeekPositionUs(long j, SeekParameters seekParameters) {
            long j2 = this.f;
            return this.c.getAdjustedSeekPositionUs(j - j2, seekParameters) + j2;
        }

        public long getBufferedPositionUs() {
            long bufferedPositionUs = this.c.getBufferedPositionUs();
            if (bufferedPositionUs == Long.MIN_VALUE) {
                return Long.MIN_VALUE;
            }
            return this.f + bufferedPositionUs;
        }

        public long getNextLoadPositionUs() {
            long nextLoadPositionUs = this.c.getNextLoadPositionUs();
            if (nextLoadPositionUs == Long.MIN_VALUE) {
                return Long.MIN_VALUE;
            }
            return this.f + nextLoadPositionUs;
        }

        public List<StreamKey> getStreamKeys(List<ExoTrackSelection> list) {
            return this.c.getStreamKeys(list);
        }

        public TrackGroupArray getTrackGroups() {
            return this.c.getTrackGroups();
        }

        public boolean isLoading() {
            return this.c.isLoading();
        }

        public void maybeThrowPrepareError() throws IOException {
            this.c.maybeThrowPrepareError();
        }

        public void onPrepared(MediaPeriod mediaPeriod) {
            ((MediaPeriod.Callback) Assertions.checkNotNull(this.g)).onPrepared(this);
        }

        public void prepare(MediaPeriod.Callback callback, long j) {
            this.g = callback;
            this.c.prepare(this, j - this.f);
        }

        public long readDiscontinuity() {
            long readDiscontinuity = this.c.readDiscontinuity();
            if (readDiscontinuity == -9223372036854775807L) {
                return -9223372036854775807L;
            }
            return this.f + readDiscontinuity;
        }

        public void reevaluateBuffer(long j) {
            this.c.reevaluateBuffer(j - this.f);
        }

        public long seekToUs(long j) {
            long j2 = this.f;
            return this.c.seekToUs(j - j2) + j2;
        }

        public long selectTracks(ExoTrackSelection[] exoTrackSelectionArr, boolean[] zArr, SampleStream[] sampleStreamArr, boolean[] zArr2, long j) {
            SampleStream[] sampleStreamArr2 = sampleStreamArr;
            SampleStream[] sampleStreamArr3 = new SampleStream[sampleStreamArr2.length];
            int i = 0;
            while (true) {
                SampleStream sampleStream = null;
                if (i >= sampleStreamArr2.length) {
                    break;
                }
                b bVar = (b) sampleStreamArr2[i];
                if (bVar != null) {
                    sampleStream = bVar.getChildStream();
                }
                sampleStreamArr3[i] = sampleStream;
                i++;
            }
            MediaPeriod mediaPeriod = this.c;
            long j2 = this.f;
            long selectTracks = mediaPeriod.selectTracks(exoTrackSelectionArr, zArr, sampleStreamArr3, zArr2, j - j2);
            for (int i2 = 0; i2 < sampleStreamArr2.length; i2++) {
                SampleStream sampleStream2 = sampleStreamArr3[i2];
                if (sampleStream2 == null) {
                    sampleStreamArr2[i2] = null;
                } else {
                    SampleStream sampleStream3 = sampleStreamArr2[i2];
                    if (sampleStream3 == null || ((b) sampleStream3).getChildStream() != sampleStream2) {
                        sampleStreamArr2[i2] = new b(sampleStream2, j2);
                    }
                }
            }
            return selectTracks + j2;
        }

        public void onContinueLoadingRequested(MediaPeriod mediaPeriod) {
            ((MediaPeriod.Callback) Assertions.checkNotNull(this.g)).onContinueLoadingRequested(this);
        }
    }

    public static final class b implements SampleStream {
        public final SampleStream c;
        public final long f;

        public b(SampleStream sampleStream, long j) {
            this.c = sampleStream;
            this.f = j;
        }

        public SampleStream getChildStream() {
            return this.c;
        }

        public boolean isReady() {
            return this.c.isReady();
        }

        public void maybeThrowError() throws IOException {
            this.c.maybeThrowError();
        }

        public int readData(FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, int i) {
            int readData = this.c.readData(formatHolder, decoderInputBuffer, i);
            if (readData == -4) {
                decoderInputBuffer.i = Math.max(0, decoderInputBuffer.i + this.f);
            }
            return readData;
        }

        public int skipData(long j) {
            return this.c.skipData(j - this.f);
        }
    }

    public a(CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory, long[] jArr, MediaPeriod... mediaPeriodArr) {
        this.g = compositeSequenceableLoaderFactory;
        this.c = mediaPeriodArr;
        this.l = compositeSequenceableLoaderFactory.createCompositeSequenceableLoader(new SequenceableLoader[0]);
        this.f = new IdentityHashMap<>();
        this.k = new MediaPeriod[0];
        for (int i2 = 0; i2 < mediaPeriodArr.length; i2++) {
            long j2 = jArr[i2];
            if (j2 != 0) {
                this.c[i2] = new C0017a(mediaPeriodArr[i2], j2);
            }
        }
    }

    public boolean continueLoading(long j2) {
        ArrayList<MediaPeriod> arrayList = this.h;
        if (arrayList.isEmpty()) {
            return this.l.continueLoading(j2);
        }
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            arrayList.get(i2).continueLoading(j2);
        }
        return false;
    }

    public void discardBuffer(long j2, boolean z) {
        for (MediaPeriod discardBuffer : this.k) {
            discardBuffer.discardBuffer(j2, z);
        }
    }

    public long getAdjustedSeekPositionUs(long j2, SeekParameters seekParameters) {
        MediaPeriod mediaPeriod;
        MediaPeriod[] mediaPeriodArr = this.k;
        if (mediaPeriodArr.length > 0) {
            mediaPeriod = mediaPeriodArr[0];
        } else {
            mediaPeriod = this.c[0];
        }
        return mediaPeriod.getAdjustedSeekPositionUs(j2, seekParameters);
    }

    public long getBufferedPositionUs() {
        return this.l.getBufferedPositionUs();
    }

    public MediaPeriod getChildPeriod(int i2) {
        MediaPeriod mediaPeriod = this.c[i2];
        if (mediaPeriod instanceof C0017a) {
            return ((C0017a) mediaPeriod).c;
        }
        return mediaPeriod;
    }

    public long getNextLoadPositionUs() {
        return this.l.getNextLoadPositionUs();
    }

    public /* bridge */ /* synthetic */ List getStreamKeys(List list) {
        return a6.a(this, list);
    }

    public TrackGroupArray getTrackGroups() {
        return (TrackGroupArray) Assertions.checkNotNull(this.j);
    }

    public boolean isLoading() {
        return this.l.isLoading();
    }

    public void maybeThrowPrepareError() throws IOException {
        for (MediaPeriod maybeThrowPrepareError : this.c) {
            maybeThrowPrepareError.maybeThrowPrepareError();
        }
    }

    public void onPrepared(MediaPeriod mediaPeriod) {
        ArrayList<MediaPeriod> arrayList = this.h;
        arrayList.remove(mediaPeriod);
        if (arrayList.isEmpty()) {
            MediaPeriod[] mediaPeriodArr = this.c;
            int i2 = 0;
            for (MediaPeriod trackGroups : mediaPeriodArr) {
                i2 += trackGroups.getTrackGroups().c;
            }
            TrackGroup[] trackGroupArr = new TrackGroup[i2];
            int i3 = 0;
            for (MediaPeriod trackGroups2 : mediaPeriodArr) {
                TrackGroupArray trackGroups3 = trackGroups2.getTrackGroups();
                int i4 = trackGroups3.c;
                int i5 = 0;
                while (i5 < i4) {
                    trackGroupArr[i3] = trackGroups3.get(i5);
                    i5++;
                    i3++;
                }
            }
            this.j = new TrackGroupArray(trackGroupArr);
            ((MediaPeriod.Callback) Assertions.checkNotNull(this.i)).onPrepared(this);
        }
    }

    public void prepare(MediaPeriod.Callback callback, long j2) {
        this.i = callback;
        ArrayList<MediaPeriod> arrayList = this.h;
        MediaPeriod[] mediaPeriodArr = this.c;
        Collections.addAll(arrayList, mediaPeriodArr);
        for (MediaPeriod prepare : mediaPeriodArr) {
            prepare.prepare(this, j2);
        }
    }

    public long readDiscontinuity() {
        long j2 = -9223372036854775807L;
        for (MediaPeriod mediaPeriod : this.k) {
            long readDiscontinuity = mediaPeriod.readDiscontinuity();
            if (readDiscontinuity != -9223372036854775807L) {
                if (j2 == -9223372036854775807L) {
                    MediaPeriod[] mediaPeriodArr = this.k;
                    int length = mediaPeriodArr.length;
                    int i2 = 0;
                    while (i2 < length) {
                        MediaPeriod mediaPeriod2 = mediaPeriodArr[i2];
                        if (mediaPeriod2 == mediaPeriod) {
                            break;
                        } else if (mediaPeriod2.seekToUs(readDiscontinuity) == readDiscontinuity) {
                            i2++;
                        } else {
                            throw new IllegalStateException("Unexpected child seekToUs result.");
                        }
                    }
                    j2 = readDiscontinuity;
                } else if (readDiscontinuity != j2) {
                    throw new IllegalStateException("Conflicting discontinuities.");
                }
            } else if (!(j2 == -9223372036854775807L || mediaPeriod.seekToUs(j2) == j2)) {
                throw new IllegalStateException("Unexpected child seekToUs result.");
            }
        }
        return j2;
    }

    public void reevaluateBuffer(long j2) {
        this.l.reevaluateBuffer(j2);
    }

    public long seekToUs(long j2) {
        long seekToUs = this.k[0].seekToUs(j2);
        int i2 = 1;
        while (true) {
            MediaPeriod[] mediaPeriodArr = this.k;
            if (i2 >= mediaPeriodArr.length) {
                return seekToUs;
            }
            if (mediaPeriodArr[i2].seekToUs(seekToUs) == seekToUs) {
                i2++;
            } else {
                throw new IllegalStateException("Unexpected child seekToUs result.");
            }
        }
    }

    public long selectTracks(ExoTrackSelection[] exoTrackSelectionArr, boolean[] zArr, SampleStream[] sampleStreamArr, boolean[] zArr2, long j2) {
        IdentityHashMap<SampleStream, Integer> identityHashMap;
        MediaPeriod[] mediaPeriodArr;
        SampleStream sampleStream;
        ExoTrackSelection exoTrackSelection;
        Integer num;
        int i2;
        ExoTrackSelection[] exoTrackSelectionArr2 = exoTrackSelectionArr;
        SampleStream[] sampleStreamArr2 = sampleStreamArr;
        int[] iArr = new int[exoTrackSelectionArr2.length];
        int[] iArr2 = new int[exoTrackSelectionArr2.length];
        int i3 = 0;
        while (true) {
            int length = exoTrackSelectionArr2.length;
            identityHashMap = this.f;
            mediaPeriodArr = this.c;
            if (i3 >= length) {
                break;
            }
            SampleStream sampleStream2 = sampleStreamArr2[i3];
            if (sampleStream2 == null) {
                num = null;
            } else {
                num = identityHashMap.get(sampleStream2);
            }
            if (num == null) {
                i2 = -1;
            } else {
                i2 = num.intValue();
            }
            iArr[i3] = i2;
            iArr2[i3] = -1;
            ExoTrackSelection exoTrackSelection2 = exoTrackSelectionArr2[i3];
            if (exoTrackSelection2 != null) {
                TrackGroup trackGroup = exoTrackSelection2.getTrackGroup();
                int i4 = 0;
                while (true) {
                    if (i4 >= mediaPeriodArr.length) {
                        break;
                    } else if (mediaPeriodArr[i4].getTrackGroups().indexOf(trackGroup) != -1) {
                        iArr2[i3] = i4;
                        break;
                    } else {
                        i4++;
                    }
                }
            }
            i3++;
        }
        identityHashMap.clear();
        int length2 = exoTrackSelectionArr2.length;
        SampleStream[] sampleStreamArr3 = new SampleStream[length2];
        SampleStream[] sampleStreamArr4 = new SampleStream[exoTrackSelectionArr2.length];
        ExoTrackSelection[] exoTrackSelectionArr3 = new ExoTrackSelection[exoTrackSelectionArr2.length];
        ArrayList arrayList = new ArrayList(mediaPeriodArr.length);
        long j3 = j2;
        int i5 = 0;
        while (i5 < mediaPeriodArr.length) {
            for (int i6 = 0; i6 < exoTrackSelectionArr2.length; i6++) {
                if (iArr[i6] == i5) {
                    sampleStream = sampleStreamArr2[i6];
                } else {
                    sampleStream = null;
                }
                sampleStreamArr4[i6] = sampleStream;
                if (iArr2[i6] == i5) {
                    exoTrackSelection = exoTrackSelectionArr2[i6];
                } else {
                    exoTrackSelection = null;
                }
                exoTrackSelectionArr3[i6] = exoTrackSelection;
            }
            int i7 = i5;
            ArrayList arrayList2 = arrayList;
            ExoTrackSelection[] exoTrackSelectionArr4 = exoTrackSelectionArr3;
            long selectTracks = mediaPeriodArr[i5].selectTracks(exoTrackSelectionArr3, zArr, sampleStreamArr4, zArr2, j3);
            if (i7 == 0) {
                j3 = selectTracks;
            } else if (selectTracks != j3) {
                throw new IllegalStateException("Children enabled at different positions.");
            }
            boolean z = false;
            for (int i8 = 0; i8 < exoTrackSelectionArr2.length; i8++) {
                boolean z2 = true;
                if (iArr2[i8] == i7) {
                    sampleStreamArr3[i8] = sampleStreamArr4[i8];
                    identityHashMap.put((SampleStream) Assertions.checkNotNull(sampleStreamArr4[i8]), Integer.valueOf(i7));
                    z = true;
                } else if (iArr[i8] == i7) {
                    if (sampleStreamArr4[i8] != null) {
                        z2 = false;
                    }
                    Assertions.checkState(z2);
                }
            }
            if (z) {
                arrayList2.add(mediaPeriodArr[i7]);
            }
            i5 = i7 + 1;
            arrayList = arrayList2;
            exoTrackSelectionArr3 = exoTrackSelectionArr4;
        }
        System.arraycopy(sampleStreamArr3, 0, sampleStreamArr2, 0, length2);
        MediaPeriod[] mediaPeriodArr2 = (MediaPeriod[]) arrayList.toArray(new MediaPeriod[0]);
        this.k = mediaPeriodArr2;
        this.l = this.g.createCompositeSequenceableLoader(mediaPeriodArr2);
        return j3;
    }

    public void onContinueLoadingRequested(MediaPeriod mediaPeriod) {
        ((MediaPeriod.Callback) Assertions.checkNotNull(this.i)).onContinueLoadingRequested(this);
    }
}
