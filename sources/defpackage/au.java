package defpackage;

import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.util.ListenerSet;

/* renamed from: au  reason: default package */
public final /* synthetic */ class au implements ListenerSet.Event {
    public final /* synthetic */ int a;
    public final /* synthetic */ AnalyticsListener.EventTime b;
    public final /* synthetic */ DecoderCounters c;

    public /* synthetic */ au(AnalyticsListener.EventTime eventTime, int i, DecoderCounters decoderCounters) {
        this.a = i;
        this.b = eventTime;
        this.c = decoderCounters;
    }

    public final void invoke(Object obj) {
        int i = this.a;
        DecoderCounters decoderCounters = this.c;
        AnalyticsListener.EventTime eventTime = this.b;
        switch (i) {
            case 0:
                AnalyticsListener analyticsListener = (AnalyticsListener) obj;
                analyticsListener.onAudioDisabled(eventTime, decoderCounters);
                analyticsListener.onDecoderDisabled(eventTime, 1, decoderCounters);
                return;
            case 1:
                AnalyticsListener analyticsListener2 = (AnalyticsListener) obj;
                analyticsListener2.onAudioEnabled(eventTime, decoderCounters);
                analyticsListener2.onDecoderEnabled(eventTime, 1, decoderCounters);
                return;
            case 2:
                AnalyticsListener analyticsListener3 = (AnalyticsListener) obj;
                analyticsListener3.onVideoDisabled(eventTime, decoderCounters);
                analyticsListener3.onDecoderDisabled(eventTime, 2, decoderCounters);
                return;
            default:
                AnalyticsListener analyticsListener4 = (AnalyticsListener) obj;
                analyticsListener4.onVideoEnabled(eventTime, decoderCounters);
                analyticsListener4.onDecoderEnabled(eventTime, 2, decoderCounters);
                return;
        }
    }
}
