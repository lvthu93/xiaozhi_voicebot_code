package com.google.android.exoplayer2.audio;

import android.os.Handler;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.decoder.DecoderReuseEvaluation;
import com.google.android.exoplayer2.util.Assertions;

public interface AudioRendererEventListener {

    public static final class EventDispatcher {
        @Nullable
        public final Handler a;
        @Nullable
        public final AudioRendererEventListener b;

        public EventDispatcher(@Nullable Handler handler, @Nullable AudioRendererEventListener audioRendererEventListener) {
            Handler handler2;
            if (audioRendererEventListener != null) {
                handler2 = (Handler) Assertions.checkNotNull(handler);
            } else {
                handler2 = null;
            }
            this.a = handler2;
            this.b = audioRendererEventListener;
        }

        public void audioCodecError(Exception exc) {
            Handler handler = this.a;
            if (handler != null) {
                handler.post(new bu(this, exc, 1));
            }
        }

        public void audioSinkError(Exception exc) {
            Handler handler = this.a;
            if (handler != null) {
                handler.post(new bu(this, exc, 0));
            }
        }

        public void decoderInitialized(String str, long j, long j2) {
            Handler handler = this.a;
            if (handler != null) {
                handler.post(new bv(this, str, j, j2, 0));
            }
        }

        public void decoderReleased(String str) {
            Handler handler = this.a;
            if (handler != null) {
                handler.post(new h2(2, this, str));
            }
        }

        public void disabled(DecoderCounters decoderCounters) {
            decoderCounters.ensureUpdated();
            Handler handler = this.a;
            if (handler != null) {
                handler.post(new bs(this, decoderCounters, 0));
            }
        }

        public void enabled(DecoderCounters decoderCounters) {
            Handler handler = this.a;
            if (handler != null) {
                handler.post(new bs(this, decoderCounters, 1));
            }
        }

        public void inputFormatChanged(Format format, @Nullable DecoderReuseEvaluation decoderReuseEvaluation) {
            Handler handler = this.a;
            if (handler != null) {
                handler.post(new d6(1, this, format, decoderReuseEvaluation));
            }
        }

        public void positionAdvancing(long j) {
            Handler handler = this.a;
            if (handler != null) {
                handler.post(new br(this, j));
            }
        }

        public void skipSilenceEnabledChanged(boolean z) {
            Handler handler = this.a;
            if (handler != null) {
                handler.post(new bq(this, z));
            }
        }

        public void underrun(int i, long j, long j2) {
            Handler handler = this.a;
            if (handler != null) {
                handler.post(new bt(this, i, j, j2, 0));
            }
        }
    }

    void onAudioCodecError(Exception exc);

    void onAudioDecoderInitialized(String str, long j, long j2);

    void onAudioDecoderReleased(String str);

    void onAudioDisabled(DecoderCounters decoderCounters);

    void onAudioEnabled(DecoderCounters decoderCounters);

    @Deprecated
    void onAudioInputFormatChanged(Format format);

    void onAudioInputFormatChanged(Format format, @Nullable DecoderReuseEvaluation decoderReuseEvaluation);

    void onAudioPositionAdvancing(long j);

    void onAudioSinkError(Exception exc);

    void onAudioUnderrun(int i, long j, long j2);

    void onSkipSilenceEnabledChanged(boolean z);
}
