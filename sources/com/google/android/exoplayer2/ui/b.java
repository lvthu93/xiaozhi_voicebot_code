package com.google.android.exoplayer2.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.ui.StyledPlayerControlView;
import info.dourok.voicebot.R;
import java.util.ArrayList;
import java.util.Iterator;

public final class b {
    public final StyledPlayerControlView a;
    public boolean aa;
    public boolean ab;
    public boolean ac = true;
    @Nullable
    public final View b;
    @Nullable
    public final ViewGroup c;
    @Nullable
    public final ViewGroup d;
    @Nullable
    public final ViewGroup e;
    @Nullable
    public final ViewGroup f;
    @Nullable
    public final ViewGroup g;
    @Nullable
    public final ViewGroup h;
    @Nullable
    public final ViewGroup i;
    @Nullable
    public final View j;
    @Nullable
    public final View k;
    public final AnimatorSet l;
    public final AnimatorSet m;
    public final AnimatorSet n;
    public final AnimatorSet o;
    public final AnimatorSet p;
    public final ValueAnimator q;
    public final ValueAnimator r;
    public final xb s = new xb(this, 0);
    public final xb t = new xb(this, 1);
    public final xb u = new xb(this, 2);
    public final xb v = new xb(this, 3);
    public final xb w = new xb(this, 4);
    public final tb x = new tb(1, this);
    public final ArrayList y = new ArrayList();
    public int z = 0;

    public class a extends AnimatorListenerAdapter {
        public a() {
        }

        public void onAnimationEnd(Animator animator) {
            b bVar = b.this;
            View view = bVar.b;
            if (view != null) {
                view.setVisibility(4);
            }
            ViewGroup viewGroup = bVar.c;
            if (viewGroup != null) {
                viewGroup.setVisibility(4);
            }
            ViewGroup viewGroup2 = bVar.e;
            if (viewGroup2 != null) {
                viewGroup2.setVisibility(4);
            }
        }

        public void onAnimationStart(Animator animator) {
            b bVar = b.this;
            View view = bVar.j;
            if ((view instanceof DefaultTimeBar) && !bVar.aa) {
                ((DefaultTimeBar) view).hideScrubber(250);
            }
        }
    }

    /* renamed from: com.google.android.exoplayer2.ui.b$b  reason: collision with other inner class name */
    public class C0020b extends AnimatorListenerAdapter {
        public C0020b() {
        }

        public void onAnimationStart(Animator animator) {
            b bVar = b.this;
            View view = bVar.b;
            int i = 0;
            if (view != null) {
                view.setVisibility(0);
            }
            ViewGroup viewGroup = bVar.c;
            if (viewGroup != null) {
                viewGroup.setVisibility(0);
            }
            ViewGroup viewGroup2 = bVar.e;
            if (viewGroup2 != null) {
                if (!bVar.aa) {
                    i = 4;
                }
                viewGroup2.setVisibility(i);
            }
            View view2 = bVar.j;
            if ((view2 instanceof DefaultTimeBar) && !bVar.aa) {
                ((DefaultTimeBar) view2).showScrubber(250);
            }
        }
    }

    public class c extends AnimatorListenerAdapter {
        public final /* synthetic */ StyledPlayerControlView a;

        public c(StyledPlayerControlView styledPlayerControlView) {
            this.a = styledPlayerControlView;
        }

        public void onAnimationEnd(Animator animator) {
            b bVar = b.this;
            bVar.e(1);
            if (bVar.ab) {
                this.a.post(bVar.s);
                bVar.ab = false;
            }
        }

        public void onAnimationStart(Animator animator) {
            b.this.e(3);
        }
    }

    public class d extends AnimatorListenerAdapter {
        public final /* synthetic */ StyledPlayerControlView a;

        public d(StyledPlayerControlView styledPlayerControlView) {
            this.a = styledPlayerControlView;
        }

        public void onAnimationEnd(Animator animator) {
            b bVar = b.this;
            bVar.e(2);
            if (bVar.ab) {
                this.a.post(bVar.s);
                bVar.ab = false;
            }
        }

        public void onAnimationStart(Animator animator) {
            b.this.e(3);
        }
    }

    public class e extends AnimatorListenerAdapter {
        public final /* synthetic */ StyledPlayerControlView a;

        public e(StyledPlayerControlView styledPlayerControlView) {
            this.a = styledPlayerControlView;
        }

        public void onAnimationEnd(Animator animator) {
            b bVar = b.this;
            bVar.e(2);
            if (bVar.ab) {
                this.a.post(bVar.s);
                bVar.ab = false;
            }
        }

        public void onAnimationStart(Animator animator) {
            b.this.e(3);
        }
    }

    public class f extends AnimatorListenerAdapter {
        public f() {
        }

        public void onAnimationEnd(Animator animator) {
            b.this.e(0);
        }

        public void onAnimationStart(Animator animator) {
            b.this.e(4);
        }
    }

    public class g extends AnimatorListenerAdapter {
        public g() {
        }

        public void onAnimationEnd(Animator animator) {
            b.this.e(0);
        }

        public void onAnimationStart(Animator animator) {
            b.this.e(4);
        }
    }

    public class h extends AnimatorListenerAdapter {
        public h() {
        }

        public void onAnimationEnd(Animator animator) {
            ViewGroup viewGroup = b.this.f;
            if (viewGroup != null) {
                viewGroup.setVisibility(4);
            }
        }

        public void onAnimationStart(Animator animator) {
            b bVar = b.this;
            ViewGroup viewGroup = bVar.h;
            if (viewGroup != null) {
                viewGroup.setVisibility(0);
                ViewGroup viewGroup2 = bVar.h;
                viewGroup2.setTranslationX((float) viewGroup2.getWidth());
                ViewGroup viewGroup3 = bVar.h;
                viewGroup3.scrollTo(viewGroup3.getWidth(), 0);
            }
        }
    }

    public class i extends AnimatorListenerAdapter {
        public i() {
        }

        public void onAnimationEnd(Animator animator) {
            ViewGroup viewGroup = b.this.h;
            if (viewGroup != null) {
                viewGroup.setVisibility(4);
            }
        }

        public void onAnimationStart(Animator animator) {
            ViewGroup viewGroup = b.this.f;
            if (viewGroup != null) {
                viewGroup.setVisibility(0);
            }
        }
    }

    public b(StyledPlayerControlView styledPlayerControlView) {
        this.a = styledPlayerControlView;
        this.b = styledPlayerControlView.findViewById(R.id.exo_controls_background);
        this.c = (ViewGroup) styledPlayerControlView.findViewById(R.id.exo_center_controls);
        this.e = (ViewGroup) styledPlayerControlView.findViewById(R.id.exo_minimal_controls);
        ViewGroup viewGroup = (ViewGroup) styledPlayerControlView.findViewById(R.id.exo_bottom_bar);
        this.d = viewGroup;
        this.i = (ViewGroup) styledPlayerControlView.findViewById(R.id.exo_time);
        View findViewById = styledPlayerControlView.findViewById(R.id.exo_progress);
        this.j = findViewById;
        this.f = (ViewGroup) styledPlayerControlView.findViewById(R.id.exo_basic_controls);
        this.g = (ViewGroup) styledPlayerControlView.findViewById(R.id.exo_extra_controls);
        this.h = (ViewGroup) styledPlayerControlView.findViewById(R.id.exo_extra_controls_scroll_view);
        View findViewById2 = styledPlayerControlView.findViewById(R.id.exo_overflow_show);
        this.k = findViewById2;
        View findViewById3 = styledPlayerControlView.findViewById(R.id.exo_overflow_hide);
        if (!(findViewById2 == null || findViewById3 == null)) {
            findViewById2.setOnClickListener(new zb(this, 0));
            findViewById3.setOnClickListener(new zb(this, 1));
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{1.0f, 0.0f});
        ofFloat.setInterpolator(new LinearInterpolator());
        ofFloat.addUpdateListener(new yb(2, this));
        ofFloat.addListener(new a());
        ValueAnimator ofFloat2 = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        ofFloat2.setInterpolator(new LinearInterpolator());
        ofFloat2.addUpdateListener(new yb(3, this));
        ofFloat2.addListener(new C0020b());
        Resources resources = styledPlayerControlView.getResources();
        float dimension = resources.getDimension(R.dimen.exo_styled_bottom_bar_height) - resources.getDimension(R.dimen.exo_styled_progress_bar_height);
        float dimension2 = resources.getDimension(R.dimen.exo_styled_bottom_bar_height);
        AnimatorSet animatorSet = new AnimatorSet();
        this.l = animatorSet;
        animatorSet.setDuration(250);
        animatorSet.addListener(new c(styledPlayerControlView));
        animatorSet.play(ofFloat).with(d(0.0f, dimension, findViewById)).with(d(0.0f, dimension, viewGroup));
        AnimatorSet animatorSet2 = new AnimatorSet();
        this.m = animatorSet2;
        animatorSet2.setDuration(250);
        animatorSet2.addListener(new d(styledPlayerControlView));
        animatorSet2.play(d(dimension, dimension2, findViewById)).with(d(dimension, dimension2, viewGroup));
        AnimatorSet animatorSet3 = new AnimatorSet();
        this.n = animatorSet3;
        animatorSet3.setDuration(250);
        animatorSet3.addListener(new e(styledPlayerControlView));
        animatorSet3.play(ofFloat).with(d(0.0f, dimension2, findViewById)).with(d(0.0f, dimension2, viewGroup));
        AnimatorSet animatorSet4 = new AnimatorSet();
        this.o = animatorSet4;
        animatorSet4.setDuration(250);
        animatorSet4.addListener(new f());
        animatorSet4.play(ofFloat2).with(d(dimension, 0.0f, findViewById)).with(d(dimension, 0.0f, viewGroup));
        AnimatorSet animatorSet5 = new AnimatorSet();
        this.p = animatorSet5;
        animatorSet5.setDuration(250);
        animatorSet5.addListener(new g());
        animatorSet5.play(ofFloat2).with(d(dimension2, 0.0f, findViewById)).with(d(dimension2, 0.0f, viewGroup));
        ValueAnimator ofFloat3 = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        this.q = ofFloat3;
        ofFloat3.setDuration(250);
        ofFloat3.addUpdateListener(new yb(0, this));
        ofFloat3.addListener(new h());
        ValueAnimator ofFloat4 = ValueAnimator.ofFloat(new float[]{1.0f, 0.0f});
        this.r = ofFloat4;
        ofFloat4.setDuration(250);
        ofFloat4.addUpdateListener(new yb(1, this));
        ofFloat4.addListener(new i());
    }

    public static void a(b bVar, View view) {
        bVar.resetHideCallbacks();
        if (view.getId() == R.id.exo_overflow_show) {
            bVar.q.start();
        } else if (view.getId() == R.id.exo_overflow_hide) {
            bVar.r.start();
        }
    }

    public static int c(@Nullable View view) {
        if (view == null) {
            return 0;
        }
        int width = view.getWidth();
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (!(layoutParams instanceof ViewGroup.MarginLayoutParams)) {
            return width;
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
        return width + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin;
    }

    public static ObjectAnimator d(float f2, float f3, View view) {
        return ObjectAnimator.ofFloat(view, "translationY", new float[]{f2, f3});
    }

    public static boolean f(View view) {
        int id = view.getId();
        return id == R.id.exo_bottom_bar || id == R.id.exo_prev || id == R.id.exo_next || id == R.id.exo_rew || id == R.id.exo_rew_with_amount || id == R.id.exo_ffwd || id == R.id.exo_ffwd_with_amount;
    }

    public final void b(float f2) {
        ViewGroup viewGroup = this.h;
        if (viewGroup != null) {
            viewGroup.setTranslationX((float) ((int) ((1.0f - f2) * ((float) viewGroup.getWidth()))));
        }
        ViewGroup viewGroup2 = this.i;
        if (viewGroup2 != null) {
            viewGroup2.setAlpha(1.0f - f2);
        }
        ViewGroup viewGroup3 = this.f;
        if (viewGroup3 != null) {
            viewGroup3.setAlpha(1.0f - f2);
        }
    }

    public final void e(int i2) {
        int i3 = this.z;
        this.z = i2;
        StyledPlayerControlView styledPlayerControlView = this.a;
        if (i2 == 2) {
            styledPlayerControlView.setVisibility(8);
        } else if (i3 == 2) {
            styledPlayerControlView.setVisibility(0);
        }
        if (i3 != i2) {
            Iterator<StyledPlayerControlView.VisibilityListener> it = styledPlayerControlView.f.iterator();
            while (it.hasNext()) {
                it.next().onVisibilityChange(styledPlayerControlView.getVisibility());
            }
        }
    }

    public final void g() {
        if (!this.ac) {
            e(0);
            resetHideCallbacks();
            return;
        }
        int i2 = this.z;
        if (i2 == 1) {
            this.o.start();
        } else if (i2 == 2) {
            this.p.start();
        } else if (i2 == 3) {
            this.ab = true;
        } else if (i2 == 4) {
            return;
        }
        resetHideCallbacks();
    }

    public boolean getShowButton(@Nullable View view) {
        return view != null && this.y.contains(view);
    }

    public void hide() {
        int i2 = this.z;
        if (i2 != 3 && i2 != 2) {
            removeHideCallbacks();
            if (!this.ac) {
                e(2);
            } else if (this.z == 1) {
                this.m.start();
            } else {
                this.n.start();
            }
        }
    }

    public void hideImmediately() {
        int i2 = this.z;
        if (i2 != 3 && i2 != 2) {
            removeHideCallbacks();
            e(2);
        }
    }

    public boolean isAnimationEnabled() {
        return this.ac;
    }

    public boolean isFullyVisible() {
        return this.z == 0 && this.a.isVisible();
    }

    public void onAttachedToWindow() {
        this.a.addOnLayoutChangeListener(this.x);
    }

    public void onDetachedFromWindow() {
        this.a.removeOnLayoutChangeListener(this.x);
    }

    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        View view = this.b;
        if (view != null) {
            view.layout(0, 0, i4 - i2, i5 - i3);
        }
    }

    public void removeHideCallbacks() {
        StyledPlayerControlView styledPlayerControlView = this.a;
        styledPlayerControlView.removeCallbacks(this.w);
        styledPlayerControlView.removeCallbacks(this.t);
        styledPlayerControlView.removeCallbacks(this.v);
        styledPlayerControlView.removeCallbacks(this.u);
    }

    public void resetHideCallbacks() {
        if (this.z != 3) {
            removeHideCallbacks();
            StyledPlayerControlView styledPlayerControlView = this.a;
            int showTimeoutMs = styledPlayerControlView.getShowTimeoutMs();
            if (showTimeoutMs <= 0) {
                return;
            }
            if (!this.ac) {
                long j2 = (long) showTimeoutMs;
                if (j2 >= 0) {
                    styledPlayerControlView.postDelayed(this.w, j2);
                }
            } else if (this.z == 1) {
                styledPlayerControlView.postDelayed(this.u, 2000);
            } else {
                long j3 = (long) showTimeoutMs;
                if (j3 >= 0) {
                    styledPlayerControlView.postDelayed(this.v, j3);
                }
            }
        }
    }

    public void setAnimationEnabled(boolean z2) {
        this.ac = z2;
    }

    public void setShowButton(@Nullable View view, boolean z2) {
        if (view != null) {
            ArrayList arrayList = this.y;
            if (!z2) {
                view.setVisibility(8);
                arrayList.remove(view);
                return;
            }
            if (!this.aa || !f(view)) {
                view.setVisibility(0);
            } else {
                view.setVisibility(4);
            }
            arrayList.add(view);
        }
    }

    public void show() {
        StyledPlayerControlView styledPlayerControlView = this.a;
        if (!styledPlayerControlView.isVisible()) {
            styledPlayerControlView.setVisibility(0);
            styledPlayerControlView.f();
            View view = styledPlayerControlView.i;
            if (view != null) {
                view.requestFocus();
            }
        }
        g();
    }
}
