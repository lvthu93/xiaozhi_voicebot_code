package com.google.android.exoplayer2.video;

import android.os.Handler;
import android.os.SystemClock;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.decoder.DecoderReuseEvaluation;
import com.google.android.exoplayer2.util.Assertions;

public interface VideoRendererEventListener {

    public static final class EventDispatcher {
        @Nullable
        public final Handler a;
        @Nullable
        public final VideoRendererEventListener b;

        public EventDispatcher(@Nullable Handler handler, @Nullable VideoRendererEventListener videoRendererEventListener) {
            Handler handler2;
            if (videoRendererEventListener != null) {
                handler2 = (Handler) Assertions.checkNotNull(handler);
            } else {
                handler2 = null;
            }
            this.a = handler2;
            this.b = videoRendererEventListener;
        }

        public void decoderInitialized(String str, long j, long j2) {
            Handler handler = this.a;
            if (handler != null) {
                handler.post(new bv(this, str, j, j2, 1));
            }
        }

        public void decoderReleased(String str) {
            Handler handler = this.a;
            if (handler != null) {
                handler.post(new h2(12, this, str));
            }
        }

        public void disabled(DecoderCounters decoderCounters) {
            decoderCounters.ensureUpdated();
            Handler handler = this.a;
            if (handler != null) {
                handler.post(new qd(this, decoderCounters, 0));
            }
        }

        public void droppedFrames(int i, long j) {
            Handler handler = this.a;
            if (handler != null) {
                handler.post(new rd(this, i, j));
            }
        }

        public void enabled(DecoderCounters decoderCounters) {
            Handler handler = this.a;
            if (handler != null) {
                handler.post(new qd(this, decoderCounters, 1));
            }
        }

        public void inputFormatChanged(Format format, @Nullable DecoderReuseEvaluation decoderReuseEvaluation) {
            Handler handler = this.a;
            if (handler != null) {
                handler.post(new d6(5, this, format, decoderReuseEvaluation));
            }
        }

        public void renderedFirstFrame(Object obj) {
            Handler handler = this.a;
            if (handler != null) {
                handler.post(new sd(this, obj, SystemClock.elapsedRealtime()));
            }
        }

        public void reportVideoFrameProcessingOffset(long j, int i) {
            Handler handler = this.a;
            if (handler != null) {
                handler.post(new rd(this, j, i));
            }
        }

        public void videoCodecError(Exception exc) {
            Handler handler = this.a;
            if (handler != null) {
                handler.post(new h2(14, this, exc));
            }
        }

        public void videoSizeChanged(VideoSize videoSize) {
            Handler handler = this.a;
            if (handler != null) {
                handler.post(new h2(13, this, videoSize));
            }
        }
    }

    void onDroppedFrames(int i, long j);

    void onRenderedFirstFrame(Object obj, long j);

    void onVideoCodecError(Exception exc);

    void onVideoDecoderInitialized(String str, long j, long j2);

    void onVideoDecoderReleased(String str);

    void onVideoDisabled(DecoderCounters decoderCounters);

    void onVideoEnabled(DecoderCounters decoderCounters);

    void onVideoFrameProcessingOffset(long j, int i);

    @Deprecated
    void onVideoInputFormatChanged(Format format);

    void onVideoInputFormatChanged(Format format, @Nullable DecoderReuseEvaluation decoderReuseEvaluation);

    void onVideoSizeChanged(VideoSize videoSize);
}
