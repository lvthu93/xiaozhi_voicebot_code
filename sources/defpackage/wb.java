package defpackage;

import android.view.View;
import com.google.android.exoplayer2.ui.StyledPlayerControlView;

/* renamed from: wb  reason: default package */
public final /* synthetic */ class wb implements View.OnClickListener {
    public final /* synthetic */ StyledPlayerControlView.i c;
    public final /* synthetic */ StyledPlayerControlView.h f;

    public /* synthetic */ wb(StyledPlayerControlView.i iVar, StyledPlayerControlView.h hVar) {
        this.c = iVar;
        this.f = hVar;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0006, code lost:
        r0 = com.google.android.exoplayer2.ui.StyledPlayerControlView.this;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onClick(android.view.View r11) {
        /*
            r10 = this;
            com.google.android.exoplayer2.ui.StyledPlayerControlView$i r11 = r10.c
            com.google.android.exoplayer2.trackselection.MappingTrackSelector$MappedTrackInfo r0 = r11.c
            if (r0 == 0) goto L_0x0073
            com.google.android.exoplayer2.ui.StyledPlayerControlView r0 = com.google.android.exoplayer2.ui.StyledPlayerControlView.this
            com.google.android.exoplayer2.trackselection.DefaultTrackSelector r1 = r0.bu
            if (r1 == 0) goto L_0x0073
            com.google.android.exoplayer2.trackselection.DefaultTrackSelector$Parameters r1 = r1.getParameters()
            com.google.android.exoplayer2.trackselection.DefaultTrackSelector$ParametersBuilder r1 = r1.buildUpon()
            r2 = 0
            r3 = 0
        L_0x0016:
            java.util.List<java.lang.Integer> r4 = r11.a
            int r4 = r4.size()
            com.google.android.exoplayer2.ui.StyledPlayerControlView$h r5 = r10.f
            if (r3 >= r4) goto L_0x005e
            java.util.List<java.lang.Integer> r4 = r11.a
            java.lang.Object r4 = r4.get(r3)
            java.lang.Integer r4 = (java.lang.Integer) r4
            int r4 = r4.intValue()
            int r6 = r5.a
            r7 = 1
            if (r4 != r6) goto L_0x0053
            com.google.android.exoplayer2.trackselection.MappingTrackSelector$MappedTrackInfo r6 = r11.c
            java.lang.Object r6 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r6)
            com.google.android.exoplayer2.trackselection.MappingTrackSelector$MappedTrackInfo r6 = (com.google.android.exoplayer2.trackselection.MappingTrackSelector.MappedTrackInfo) r6
            com.google.android.exoplayer2.source.TrackGroupArray r6 = r6.getTrackGroups(r4)
            com.google.android.exoplayer2.trackselection.DefaultTrackSelector$SelectionOverride r8 = new com.google.android.exoplayer2.trackselection.DefaultTrackSelector$SelectionOverride
            int[] r7 = new int[r7]
            int r9 = r5.c
            r7[r2] = r9
            int r5 = r5.b
            r8.<init>(r5, r7)
            com.google.android.exoplayer2.trackselection.DefaultTrackSelector$ParametersBuilder r1 = r1.setSelectionOverride(r4, r6, r8)
            com.google.android.exoplayer2.trackselection.DefaultTrackSelector$ParametersBuilder r1 = r1.setRendererDisabled(r4, r2)
            goto L_0x005b
        L_0x0053:
            com.google.android.exoplayer2.trackselection.DefaultTrackSelector$ParametersBuilder r1 = r1.clearSelectionOverrides(r4)
            com.google.android.exoplayer2.trackselection.DefaultTrackSelector$ParametersBuilder r1 = r1.setRendererDisabled(r4, r7)
        L_0x005b:
            int r3 = r3 + 1
            goto L_0x0016
        L_0x005e:
            com.google.android.exoplayer2.trackselection.DefaultTrackSelector r2 = r0.bu
            java.lang.Object r2 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r2)
            com.google.android.exoplayer2.trackselection.DefaultTrackSelector r2 = (com.google.android.exoplayer2.trackselection.DefaultTrackSelector) r2
            r2.setParameters((com.google.android.exoplayer2.trackselection.DefaultTrackSelector.ParametersBuilder) r1)
            java.lang.String r1 = r5.d
            r11.onTrackSelection(r1)
            android.widget.PopupWindow r11 = r0.br
            r11.dismiss()
        L_0x0073:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.wb.onClick(android.view.View):void");
    }
}
