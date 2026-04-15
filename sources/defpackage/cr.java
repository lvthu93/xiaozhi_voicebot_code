package defpackage;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.audio.MpegAudioUtil;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import java.nio.ByteBuffer;

/* renamed from: cr  reason: default package */
public final class cr {
    public long a;
    public long b;
    public boolean c;

    public void reset() {
        this.a = 0;
        this.b = 0;
        this.c = false;
    }

    public long updateAndGetPresentationTimeUs(Format format, DecoderInputBuffer decoderInputBuffer) {
        if (this.c) {
            return decoderInputBuffer.i;
        }
        ByteBuffer byteBuffer = (ByteBuffer) Assertions.checkNotNull(decoderInputBuffer.g);
        byte b2 = 0;
        for (int i = 0; i < 4; i++) {
            b2 = (b2 << 8) | (byteBuffer.get(i) & 255);
        }
        int parseMpegAudioFrameSampleCount = MpegAudioUtil.parseMpegAudioFrameSampleCount(b2);
        if (parseMpegAudioFrameSampleCount == -1) {
            this.c = true;
            Log.w("C2Mp3TimestampTracker", "MPEG audio header is invalid.");
            return decoderInputBuffer.i;
        }
        long j = this.a;
        if (j == 0) {
            long j2 = decoderInputBuffer.i;
            this.b = j2;
            this.a = ((long) parseMpegAudioFrameSampleCount) - 529;
            return j2;
        }
        long j3 = (1000000 * j) / ((long) format.ad);
        this.a = j + ((long) parseMpegAudioFrameSampleCount);
        return this.b + j3;
    }
}
