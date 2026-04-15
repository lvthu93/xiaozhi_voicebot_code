package defpackage;

import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.CompositeMediaSource;
import com.google.android.exoplayer2.source.MediaSource;

/* renamed from: q0  reason: default package */
public final /* synthetic */ class q0 implements MediaSource.MediaSourceCaller {
    public final /* synthetic */ CompositeMediaSource c;
    public final /* synthetic */ Object f;

    public /* synthetic */ q0(CompositeMediaSource compositeMediaSource, Object obj) {
        this.c = compositeMediaSource;
        this.f = obj;
    }

    public final void onSourceInfoRefreshed(MediaSource mediaSource, Timeline timeline) {
        this.c.f(this.f, mediaSource, timeline);
    }
}
