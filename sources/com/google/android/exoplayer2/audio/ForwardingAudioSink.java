package com.google.android.exoplayer2.audio;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.audio.AudioSink;
import java.nio.ByteBuffer;

public class ForwardingAudioSink implements AudioSink {
    public final AudioSink a;

    public ForwardingAudioSink(AudioSink audioSink) {
        this.a = audioSink;
    }

    public void configure(Format format, int i, @Nullable int[] iArr) throws AudioSink.ConfigurationException {
        this.a.configure(format, i, iArr);
    }

    public void disableTunneling() {
        this.a.disableTunneling();
    }

    public void enableTunnelingV21() {
        this.a.enableTunnelingV21();
    }

    public void experimentalFlushWithoutAudioTrackRelease() {
        this.a.experimentalFlushWithoutAudioTrackRelease();
    }

    public void flush() {
        this.a.flush();
    }

    public long getCurrentPositionUs(boolean z) {
        return this.a.getCurrentPositionUs(z);
    }

    public int getFormatSupport(Format format) {
        return this.a.getFormatSupport(format);
    }

    public PlaybackParameters getPlaybackParameters() {
        return this.a.getPlaybackParameters();
    }

    public boolean getSkipSilenceEnabled() {
        return this.a.getSkipSilenceEnabled();
    }

    public boolean handleBuffer(ByteBuffer byteBuffer, long j, int i) throws AudioSink.InitializationException, AudioSink.WriteException {
        return this.a.handleBuffer(byteBuffer, j, i);
    }

    public void handleDiscontinuity() {
        this.a.handleDiscontinuity();
    }

    public boolean hasPendingData() {
        return this.a.hasPendingData();
    }

    public boolean isEnded() {
        return this.a.isEnded();
    }

    public void pause() {
        this.a.pause();
    }

    public void play() {
        this.a.play();
    }

    public void playToEndOfStream() throws AudioSink.WriteException {
        this.a.playToEndOfStream();
    }

    public void reset() {
        this.a.reset();
    }

    public void setAudioAttributes(AudioAttributes audioAttributes) {
        this.a.setAudioAttributes(audioAttributes);
    }

    public void setAudioSessionId(int i) {
        this.a.setAudioSessionId(i);
    }

    public void setAuxEffectInfo(AuxEffectInfo auxEffectInfo) {
        this.a.setAuxEffectInfo(auxEffectInfo);
    }

    public void setListener(AudioSink.Listener listener) {
        this.a.setListener(listener);
    }

    public void setPlaybackParameters(PlaybackParameters playbackParameters) {
        this.a.setPlaybackParameters(playbackParameters);
    }

    public void setSkipSilenceEnabled(boolean z) {
        this.a.setSkipSilenceEnabled(z);
    }

    public void setVolume(float f) {
        this.a.setVolume(f);
    }

    public boolean supportsFormat(Format format) {
        return this.a.supportsFormat(format);
    }
}
