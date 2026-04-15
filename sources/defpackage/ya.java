package defpackage;

import com.google.android.exoplayer2.decoder.OutputBuffer;
import com.google.android.exoplayer2.text.SubtitleOutputBuffer;

/* renamed from: ya  reason: default package */
public final class ya extends SubtitleOutputBuffer {
    public final OutputBuffer.Owner<SubtitleOutputBuffer> j;

    public ya(OutputBuffer.Owner<SubtitleOutputBuffer> owner) {
        this.j = owner;
    }

    public final void release() {
        this.j.releaseOutputBuffer(this);
    }
}
