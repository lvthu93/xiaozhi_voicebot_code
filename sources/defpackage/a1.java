package defpackage;

import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.util.NetworkTypeObserver;
import com.google.common.collect.ImmutableListMultimap;

/* renamed from: a1  reason: default package */
public final /* synthetic */ class a1 implements NetworkTypeObserver.Listener {
    public final /* synthetic */ DefaultBandwidthMeter a;

    public /* synthetic */ a1(DefaultBandwidthMeter defaultBandwidthMeter) {
        this.a = defaultBandwidthMeter;
    }

    public final void onNetworkTypeChanged(int i) {
        ImmutableListMultimap<String, Integer> immutableListMultimap = DefaultBandwidthMeter.p;
        this.a.c(i);
    }
}
