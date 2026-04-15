package defpackage;

import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.util.ListenerSet;

/* renamed from: ax  reason: default package */
public final /* synthetic */ class ax implements ListenerSet.Event {
    public final /* synthetic */ int a;
    public final /* synthetic */ AnalyticsListener.EventTime b;
    public final /* synthetic */ String c;

    public /* synthetic */ ax(AnalyticsListener.EventTime eventTime, String str, int i) {
        this.a = i;
        this.b = eventTime;
        this.c = str;
    }

    public final void invoke(Object obj) {
        int i = this.a;
        String str = this.c;
        AnalyticsListener.EventTime eventTime = this.b;
        switch (i) {
            case 0:
                ((AnalyticsListener) obj).onVideoDecoderReleased(eventTime, str);
                return;
            default:
                ((AnalyticsListener) obj).onAudioDecoderReleased(eventTime, str);
                return;
        }
    }
}
