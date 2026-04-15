package defpackage;

import android.view.View;
import com.google.android.exoplayer2.ui.StyledPlayerControlView;

/* renamed from: vb  reason: default package */
public final /* synthetic */ class vb implements View.OnClickListener {
    public final /* synthetic */ StyledPlayerControlView.c c;
    public final /* synthetic */ int f;

    public /* synthetic */ vb(StyledPlayerControlView.c cVar, int i) {
        this.c = cVar;
        this.f = i;
    }

    public final void onClick(View view) {
        StyledPlayerControlView.c cVar = this.c;
        int i = cVar.c;
        int i2 = this.f;
        StyledPlayerControlView styledPlayerControlView = StyledPlayerControlView.this;
        if (i2 != i) {
            styledPlayerControlView.setPlaybackSpeed(((float) cVar.b[i2]) / 100.0f);
        }
        styledPlayerControlView.br.dismiss();
    }
}
