package defpackage;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.util.ListenerSet;
import com.google.android.exoplayer2.video.VideoSize;
import java.util.List;

/* renamed from: o2  reason: default package */
public final /* synthetic */ class o2 implements ListenerSet.Event {
    public final /* synthetic */ int a;
    public final /* synthetic */ Object b;
    public final /* synthetic */ Object c;

    public /* synthetic */ o2(int i, Object obj, Object obj2) {
        this.a = i;
        this.b = obj;
        this.c = obj2;
    }

    public final void invoke(Object obj) {
        int i = this.a;
        Object obj2 = this.c;
        Object obj3 = this.b;
        switch (i) {
            case 0:
                ((Player.EventListener) obj).onTracksChanged(((s8) obj3).h, (TrackSelectionArray) obj2);
                return;
            case 1:
                AnalyticsListener.EventTime eventTime = (AnalyticsListener.EventTime) obj3;
                VideoSize videoSize = (VideoSize) obj2;
                AnalyticsListener analyticsListener = (AnalyticsListener) obj;
                analyticsListener.onVideoSizeChanged(eventTime, videoSize);
                analyticsListener.onVideoSizeChanged(eventTime, videoSize.c, videoSize.f, videoSize.g, videoSize.h);
                return;
            case 2:
                ((AnalyticsListener) obj).onMetadata((AnalyticsListener.EventTime) obj3, (Metadata) obj2);
                return;
            case 3:
                ((AnalyticsListener) obj).onPlayerError((AnalyticsListener.EventTime) obj3, (ExoPlaybackException) obj2);
                return;
            case 4:
                ((AnalyticsListener) obj).onStaticMetadataChanged((AnalyticsListener.EventTime) obj3, (List) obj2);
                return;
            case 5:
                ((AnalyticsListener) obj).onMediaMetadataChanged((AnalyticsListener.EventTime) obj3, (MediaMetadata) obj2);
                return;
            case 6:
                ((AnalyticsListener) obj).onPlaybackParametersChanged((AnalyticsListener.EventTime) obj3, (PlaybackParameters) obj2);
                return;
            default:
                ((AnalyticsListener) obj).onAudioAttributesChanged((AnalyticsListener.EventTime) obj3, (AudioAttributes) obj2);
                return;
        }
    }
}
