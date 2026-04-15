package com.google.android.exoplayer2.video;

@Deprecated
public interface VideoListener {
    void onRenderedFirstFrame();

    void onSurfaceSizeChanged(int i, int i2);

    @Deprecated
    void onVideoSizeChanged(int i, int i2, int i3, float f);

    void onVideoSizeChanged(VideoSize videoSize);
}
