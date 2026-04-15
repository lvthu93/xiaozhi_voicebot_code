package defpackage;

import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionUtil;
import com.google.android.exoplayer2.ui.TrackSelectionDialogBuilder;
import java.util.List;

/* renamed from: rc  reason: default package */
public final /* synthetic */ class rc implements TrackSelectionDialogBuilder.DialogCallback {
    public final /* synthetic */ DefaultTrackSelector a;
    public final /* synthetic */ DefaultTrackSelector.Parameters b;
    public final /* synthetic */ int c;
    public final /* synthetic */ TrackGroupArray d;

    public /* synthetic */ rc(DefaultTrackSelector defaultTrackSelector, DefaultTrackSelector.Parameters parameters, int i, TrackGroupArray trackGroupArray) {
        this.a = defaultTrackSelector;
        this.b = parameters;
        this.c = i;
        this.d = trackGroupArray;
    }

    public final void onTracksSelected(boolean z, List list) {
        DefaultTrackSelector.SelectionOverride selectionOverride;
        if (list.isEmpty()) {
            selectionOverride = null;
        } else {
            selectionOverride = (DefaultTrackSelector.SelectionOverride) list.get(0);
        }
        this.a.setParameters(TrackSelectionUtil.updateParametersWithOverride(this.b, this.c, this.d, z, selectionOverride));
    }
}
