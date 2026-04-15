package defpackage;

import android.content.DialogInterface;
import com.google.android.exoplayer2.ui.TrackSelectionDialogBuilder;
import com.google.android.exoplayer2.ui.TrackSelectionView;

/* renamed from: qc  reason: default package */
public final /* synthetic */ class qc implements DialogInterface.OnClickListener {
    public final /* synthetic */ TrackSelectionDialogBuilder c;
    public final /* synthetic */ TrackSelectionView f;

    public /* synthetic */ qc(TrackSelectionDialogBuilder trackSelectionDialogBuilder, TrackSelectionView trackSelectionView) {
        this.c = trackSelectionDialogBuilder;
        this.f = trackSelectionView;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        TrackSelectionDialogBuilder trackSelectionDialogBuilder = this.c;
        trackSelectionDialogBuilder.getClass();
        TrackSelectionView trackSelectionView = this.f;
        trackSelectionDialogBuilder.f.onTracksSelected(trackSelectionView.getIsDisabled(), trackSelectionView.getOverrides());
    }
}
