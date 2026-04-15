package defpackage;

import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.exoplayer2.ui.DefaultTimeBar;
import com.google.android.exoplayer2.ui.b;

/* renamed from: yb  reason: default package */
public final /* synthetic */ class yb implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ int a;
    public final /* synthetic */ Object b;

    public /* synthetic */ yb(int i, Object obj) {
        this.a = i;
        this.b = obj;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        int i = this.a;
        Object obj = this.b;
        switch (i) {
            case 0:
                b bVar = (b) obj;
                bVar.getClass();
                bVar.b(((Float) valueAnimator.getAnimatedValue()).floatValue());
                return;
            case 1:
                b bVar2 = (b) obj;
                bVar2.getClass();
                bVar2.b(((Float) valueAnimator.getAnimatedValue()).floatValue());
                return;
            case 2:
                b bVar3 = (b) obj;
                bVar3.getClass();
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                View view = bVar3.b;
                if (view != null) {
                    view.setAlpha(floatValue);
                }
                ViewGroup viewGroup = bVar3.c;
                if (viewGroup != null) {
                    viewGroup.setAlpha(floatValue);
                }
                ViewGroup viewGroup2 = bVar3.e;
                if (viewGroup2 != null) {
                    viewGroup2.setAlpha(floatValue);
                    return;
                }
                return;
            case 3:
                b bVar4 = (b) obj;
                bVar4.getClass();
                float floatValue2 = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                View view2 = bVar4.b;
                if (view2 != null) {
                    view2.setAlpha(floatValue2);
                }
                ViewGroup viewGroup3 = bVar4.c;
                if (viewGroup3 != null) {
                    viewGroup3.setAlpha(floatValue2);
                }
                ViewGroup viewGroup4 = bVar4.e;
                if (viewGroup4 != null) {
                    viewGroup4.setAlpha(floatValue2);
                    return;
                }
                return;
            default:
                DefaultTimeBar defaultTimeBar = (DefaultTimeBar) obj;
                int i2 = DefaultTimeBar.at;
                defaultTimeBar.getClass();
                defaultTimeBar.aj = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                defaultTimeBar.invalidate(defaultTimeBar.c);
                return;
        }
    }
}
