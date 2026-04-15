package defpackage;

import android.os.Parcelable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.decoder.DecoderReuseEvaluation;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.util.ListenerSet;

/* renamed from: ay  reason: default package */
public final /* synthetic */ class ay implements ListenerSet.Event {
    public final /* synthetic */ int a;
    public final /* synthetic */ AnalyticsListener.EventTime b;
    public final /* synthetic */ Parcelable c;
    public final /* synthetic */ Object d;

    public /* synthetic */ ay(AnalyticsListener.EventTime eventTime, Parcelable parcelable, Object obj, int i) {
        this.a = i;
        this.b = eventTime;
        this.c = parcelable;
        this.d = obj;
    }

    public final void invoke(Object obj) {
        int i = this.a;
        AnalyticsListener.EventTime eventTime = this.b;
        Object obj2 = this.d;
        Parcelable parcelable = this.c;
        switch (i) {
            case 0:
                Format format = (Format) parcelable;
                AnalyticsListener analyticsListener = (AnalyticsListener) obj;
                analyticsListener.onVideoInputFormatChanged(eventTime, format);
                analyticsListener.onVideoInputFormatChanged(eventTime, format, (DecoderReuseEvaluation) obj2);
                analyticsListener.onDecoderInputFormatChanged(eventTime, 2, format);
                return;
            case 1:
                Format format2 = (Format) parcelable;
                AnalyticsListener analyticsListener2 = (AnalyticsListener) obj;
                analyticsListener2.onAudioInputFormatChanged(eventTime, format2);
                analyticsListener2.onAudioInputFormatChanged(eventTime, format2, (DecoderReuseEvaluation) obj2);
                analyticsListener2.onDecoderInputFormatChanged(eventTime, 1, format2);
                return;
            default:
                ((AnalyticsListener) obj).onTracksChanged(eventTime, (TrackGroupArray) parcelable, (TrackSelectionArray) obj2);
                return;
        }
    }
}
