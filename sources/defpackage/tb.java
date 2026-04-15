package defpackage;

import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import com.google.android.exoplayer2.ui.StyledPlayerControlView;
import com.google.android.exoplayer2.ui.b;

/* renamed from: tb  reason: default package */
public final /* synthetic */ class tb implements View.OnLayoutChangeListener {
    public final /* synthetic */ int c;
    public final /* synthetic */ Object f;

    public /* synthetic */ tb(int i, Object obj) {
        this.c = i;
        this.f = obj;
    }

    public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        int i9;
        int i10;
        int i11;
        int i12;
        boolean z;
        View view2 = view;
        int i13 = this.c;
        Object obj = this.f;
        switch (i13) {
            case 0:
                StyledPlayerControlView styledPlayerControlView = (StyledPlayerControlView) obj;
                int i14 = StyledPlayerControlView.cf;
                styledPlayerControlView.getClass();
                int i15 = i4 - i2;
                int i16 = i8 - i6;
                if (i3 - i != i7 - i5 || i15 != i16) {
                    PopupWindow popupWindow = styledPlayerControlView.br;
                    if (popupWindow.isShowing()) {
                        styledPlayerControlView.l();
                        int i17 = styledPlayerControlView.bt;
                        popupWindow.update(view, (styledPlayerControlView.getWidth() - popupWindow.getWidth()) - i17, (-popupWindow.getHeight()) - i17, -1, -1);
                        return;
                    }
                    return;
                }
                return;
            default:
                b bVar = (b) obj;
                StyledPlayerControlView styledPlayerControlView2 = bVar.a;
                int width = (styledPlayerControlView2.getWidth() - styledPlayerControlView2.getPaddingLeft()) - styledPlayerControlView2.getPaddingRight();
                int height = (styledPlayerControlView2.getHeight() - styledPlayerControlView2.getPaddingBottom()) - styledPlayerControlView2.getPaddingTop();
                ViewGroup viewGroup = bVar.c;
                int c2 = b.c(viewGroup);
                boolean z2 = false;
                if (viewGroup != null) {
                    i9 = viewGroup.getPaddingRight() + viewGroup.getPaddingLeft();
                } else {
                    i9 = 0;
                }
                int i18 = c2 - i9;
                if (viewGroup == null) {
                    i10 = 0;
                } else {
                    i10 = viewGroup.getHeight();
                    ViewGroup.LayoutParams layoutParams = viewGroup.getLayoutParams();
                    if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                        i10 += marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
                    }
                }
                if (viewGroup != null) {
                    i11 = viewGroup.getPaddingBottom() + viewGroup.getPaddingTop();
                } else {
                    i11 = 0;
                }
                int i19 = i10 - i11;
                int max = Math.max(i18, b.c(bVar.k) + b.c(bVar.i));
                ViewGroup viewGroup2 = bVar.d;
                if (viewGroup2 == null) {
                    i12 = 0;
                } else {
                    i12 = viewGroup2.getHeight();
                    ViewGroup.LayoutParams layoutParams2 = viewGroup2.getLayoutParams();
                    if (layoutParams2 instanceof ViewGroup.MarginLayoutParams) {
                        ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) layoutParams2;
                        i12 += marginLayoutParams2.topMargin + marginLayoutParams2.bottomMargin;
                    }
                }
                int i20 = (i12 * 2) + i19;
                if (width <= max || height <= i20) {
                    z = true;
                } else {
                    z = false;
                }
                if (bVar.aa != z) {
                    bVar.aa = z;
                    view.post(new xb(bVar, 5));
                }
                if (i3 - i != i7 - i5) {
                    z2 = true;
                }
                if (!bVar.aa && z2) {
                    view.post(new xb(bVar, 6));
                    return;
                }
                return;
        }
    }
}
