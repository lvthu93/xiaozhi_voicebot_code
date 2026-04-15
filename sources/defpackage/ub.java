package defpackage;

import android.view.View;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.StyledPlayerControlView;
import com.google.android.exoplayer2.util.Assertions;
import info.dourok.voicebot.R;

/* renamed from: ub  reason: default package */
public final /* synthetic */ class ub implements View.OnClickListener {
    public final /* synthetic */ int c;
    public final /* synthetic */ Object f;

    public /* synthetic */ ub(int i, Object obj) {
        this.c = i;
        this.f = obj;
    }

    public final void onClick(View view) {
        int i = this.c;
        int i2 = 0;
        Object obj = this.f;
        switch (i) {
            case 0:
                StyledPlayerControlView.a aVar = (StyledPlayerControlView.a) obj;
                StyledPlayerControlView styledPlayerControlView = StyledPlayerControlView.this;
                DefaultTrackSelector defaultTrackSelector = styledPlayerControlView.bu;
                if (defaultTrackSelector != null) {
                    DefaultTrackSelector.ParametersBuilder buildUpon = defaultTrackSelector.getParameters().buildUpon();
                    while (i2 < aVar.a.size()) {
                        buildUpon = buildUpon.clearSelectionOverrides(aVar.a.get(i2).intValue());
                        i2++;
                    }
                    ((DefaultTrackSelector) Assertions.checkNotNull(styledPlayerControlView.bu)).setParameters(buildUpon);
                }
                styledPlayerControlView.bp.setSubTextAtPosition(1, styledPlayerControlView.getResources().getString(R.string.exo_track_selection_auto));
                styledPlayerControlView.br.dismiss();
                return;
            case 1:
                StyledPlayerControlView.d dVar = (StyledPlayerControlView.d) obj;
                int i3 = StyledPlayerControlView.d.e;
                int adapterPosition = dVar.getAdapterPosition();
                StyledPlayerControlView styledPlayerControlView2 = StyledPlayerControlView.this;
                if (adapterPosition == 0) {
                    styledPlayerControlView2.d(styledPlayerControlView2.bq);
                    return;
                } else if (adapterPosition == 1) {
                    styledPlayerControlView2.d(styledPlayerControlView2.bw);
                    return;
                } else {
                    styledPlayerControlView2.br.dismiss();
                    return;
                }
            default:
                StyledPlayerControlView.g gVar = (StyledPlayerControlView.g) obj;
                StyledPlayerControlView styledPlayerControlView3 = StyledPlayerControlView.this;
                DefaultTrackSelector defaultTrackSelector2 = styledPlayerControlView3.bu;
                if (defaultTrackSelector2 != null) {
                    DefaultTrackSelector.ParametersBuilder buildUpon2 = defaultTrackSelector2.getParameters().buildUpon();
                    while (i2 < gVar.a.size()) {
                        int intValue = gVar.a.get(i2).intValue();
                        buildUpon2 = buildUpon2.clearSelectionOverrides(intValue).setRendererDisabled(intValue, true);
                        i2++;
                    }
                    ((DefaultTrackSelector) Assertions.checkNotNull(styledPlayerControlView3.bu)).setParameters(buildUpon2);
                    styledPlayerControlView3.br.dismiss();
                    return;
                }
                return;
        }
    }
}
