package defpackage;

import com.google.android.exoplayer2.audio.MpegAudioUtil;
import com.google.android.exoplayer2.extractor.ConstantBitrateSeekMap;
import com.google.android.exoplayer2.extractor.mp3.Seeker;

/* renamed from: r0  reason: default package */
public final class r0 extends ConstantBitrateSeekMap implements Seeker {
    public r0(long j, long j2, MpegAudioUtil.Header header) {
        super(j, j2, header.f, header.c);
    }

    public long getDataEndPosition() {
        return -1;
    }

    public long getTimeUs(long j) {
        return getTimeUsAtPosition(j);
    }
}
