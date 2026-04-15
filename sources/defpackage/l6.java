package defpackage;

import com.google.android.exoplayer2.MediaSourceList;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;

/* renamed from: l6  reason: default package */
public final /* synthetic */ class l6 implements MediaSource.MediaSourceCaller {
    public final /* synthetic */ MediaSourceList c;

    public /* synthetic */ l6(MediaSourceList mediaSourceList) {
        this.c = mediaSourceList;
    }

    public final void onSourceInfoRefreshed(MediaSource mediaSource, Timeline timeline) {
        this.c.d.onPlaylistUpdateRequested();
    }
}
