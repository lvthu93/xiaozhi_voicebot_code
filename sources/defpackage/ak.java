package defpackage;

import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.util.ListenerSet;

/* renamed from: ak  reason: default package */
public final /* synthetic */ class ak implements ListenerSet.Event {
    public final /* synthetic */ int a;
    public final /* synthetic */ AnalyticsListener.EventTime b;
    public final /* synthetic */ Exception c;

    public /* synthetic */ ak(AnalyticsListener.EventTime eventTime, Exception exc, int i) {
        this.a = i;
        this.b = eventTime;
        this.c = exc;
    }

    public final void invoke(Object obj) {
        int i = this.a;
        Exception exc = this.c;
        AnalyticsListener.EventTime eventTime = this.b;
        switch (i) {
            case 0:
                ((AnalyticsListener) obj).onDrmSessionManagerError(eventTime, exc);
                return;
            case 1:
                ((AnalyticsListener) obj).onVideoCodecError(eventTime, exc);
                return;
            case 2:
                ((AnalyticsListener) obj).onAudioSinkError(eventTime, exc);
                return;
            default:
                ((AnalyticsListener) obj).onAudioCodecError(eventTime, exc);
                return;
        }
    }
}
