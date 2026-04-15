package defpackage;

import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.util.ListenerSet;

/* renamed from: al  reason: default package */
public final /* synthetic */ class al implements ListenerSet.Event {
    public final /* synthetic */ int a;
    public final /* synthetic */ AnalyticsListener.EventTime b;
    public final /* synthetic */ String c;
    public final /* synthetic */ long d;
    public final /* synthetic */ long e;

    public /* synthetic */ al(AnalyticsListener.EventTime eventTime, String str, long j, long j2, int i) {
        this.a = i;
        this.b = eventTime;
        this.c = str;
        this.d = j;
        this.e = j2;
    }

    public final void invoke(Object obj) {
        switch (this.a) {
            case 0:
                AnalyticsListener.EventTime eventTime = this.b;
                String str = this.c;
                long j = this.d;
                long j2 = this.e;
                AnalyticsListener analyticsListener = (AnalyticsListener) obj;
                analyticsListener.onAudioDecoderInitialized(eventTime, str, j);
                AnalyticsListener analyticsListener2 = analyticsListener;
                AnalyticsListener.EventTime eventTime2 = eventTime;
                analyticsListener2.onAudioDecoderInitialized(eventTime2, str, j2, j);
                analyticsListener2.onDecoderInitialized(eventTime2, 1, str, j);
                return;
            default:
                AnalyticsListener.EventTime eventTime3 = this.b;
                String str2 = this.c;
                long j3 = this.d;
                long j4 = this.e;
                AnalyticsListener analyticsListener3 = (AnalyticsListener) obj;
                analyticsListener3.onVideoDecoderInitialized(eventTime3, str2, j3);
                AnalyticsListener analyticsListener4 = analyticsListener3;
                AnalyticsListener.EventTime eventTime4 = eventTime3;
                analyticsListener4.onVideoDecoderInitialized(eventTime4, str2, j4, j3);
                analyticsListener4.onDecoderInitialized(eventTime4, 2, str2, j3);
                return;
        }
    }
}
