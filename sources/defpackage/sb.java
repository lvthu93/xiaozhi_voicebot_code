package defpackage;

import android.view.View;
import com.google.android.exoplayer2.ui.StyledPlayerControlView;

/* renamed from: sb  reason: default package */
public final /* synthetic */ class sb implements View.OnClickListener {
    public final /* synthetic */ int c;
    public final /* synthetic */ StyledPlayerControlView f;

    public /* synthetic */ sb(StyledPlayerControlView styledPlayerControlView, int i) {
        this.c = i;
        this.f = styledPlayerControlView;
    }

    public final void onClick(View view) {
        int i = this.c;
        StyledPlayerControlView styledPlayerControlView = this.f;
        switch (i) {
            case 0:
                StyledPlayerControlView.a(styledPlayerControlView);
                return;
            default:
                StyledPlayerControlView.a(styledPlayerControlView);
                return;
        }
    }
}
