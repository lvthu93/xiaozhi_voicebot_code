package defpackage;

import com.google.android.exoplayer2.ui.b;

/* renamed from: xb  reason: default package */
public final /* synthetic */ class xb implements Runnable {
    public final /* synthetic */ int c;
    public final /* synthetic */ b f;

    public /* synthetic */ xb(b bVar, int i) {
        this.c = i;
        this.f = bVar;
    }

    /* JADX WARNING: Removed duplicated region for block: B:70:0x011f  */
    /* JADX WARNING: Removed duplicated region for block: B:91:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r11 = this;
            int r0 = r11.c
            r1 = 1
            r2 = 0
            com.google.android.exoplayer2.ui.b r3 = r11.f
            switch(r0) {
                case 0: goto L_0x009b;
                case 1: goto L_0x0095;
                case 2: goto L_0x008f;
                case 3: goto L_0x0080;
                case 4: goto L_0x007b;
                case 5: goto L_0x000b;
                default: goto L_0x0009;
            }
        L_0x0009:
            goto L_0x009f
        L_0x000b:
            android.view.ViewGroup r0 = r3.e
            r4 = 4
            if (r0 == 0) goto L_0x001a
            boolean r5 = r3.aa
            if (r5 == 0) goto L_0x0016
            r5 = 0
            goto L_0x0017
        L_0x0016:
            r5 = 4
        L_0x0017:
            r0.setVisibility(r5)
        L_0x001a:
            android.view.View r0 = r3.j
            if (r0 == 0) goto L_0x0057
            android.view.ViewGroup$LayoutParams r5 = r0.getLayoutParams()
            android.view.ViewGroup$MarginLayoutParams r5 = (android.view.ViewGroup.MarginLayoutParams) r5
            com.google.android.exoplayer2.ui.StyledPlayerControlView r6 = r3.a
            android.content.res.Resources r6 = r6.getResources()
            r7 = 2131099768(0x7f060078, float:1.7811899E38)
            int r6 = r6.getDimensionPixelSize(r7)
            boolean r7 = r3.aa
            if (r7 == 0) goto L_0x0036
            r6 = 0
        L_0x0036:
            r5.bottomMargin = r6
            r0.setLayoutParams(r5)
            boolean r5 = r0 instanceof com.google.android.exoplayer2.ui.DefaultTimeBar
            if (r5 == 0) goto L_0x0057
            com.google.android.exoplayer2.ui.DefaultTimeBar r0 = (com.google.android.exoplayer2.ui.DefaultTimeBar) r0
            boolean r5 = r3.aa
            if (r5 == 0) goto L_0x0049
            r0.hideScrubber((boolean) r1)
            goto L_0x0057
        L_0x0049:
            int r5 = r3.z
            if (r5 != r1) goto L_0x0051
            r0.hideScrubber((boolean) r2)
            goto L_0x0057
        L_0x0051:
            r1 = 3
            if (r5 == r1) goto L_0x0057
            r0.showScrubber()
        L_0x0057:
            java.util.ArrayList r0 = r3.y
            java.util.Iterator r0 = r0.iterator()
        L_0x005d:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x007a
            java.lang.Object r1 = r0.next()
            android.view.View r1 = (android.view.View) r1
            boolean r5 = r3.aa
            if (r5 == 0) goto L_0x0075
            boolean r5 = com.google.android.exoplayer2.ui.b.f(r1)
            if (r5 == 0) goto L_0x0075
            r5 = 4
            goto L_0x0076
        L_0x0075:
            r5 = 0
        L_0x0076:
            r1.setVisibility(r5)
            goto L_0x005d
        L_0x007a:
            return
        L_0x007b:
            r0 = 2
            r3.e(r0)
            return
        L_0x0080:
            android.animation.AnimatorSet r0 = r3.l
            r0.start()
            com.google.android.exoplayer2.ui.StyledPlayerControlView r0 = r3.a
            xb r1 = r3.u
            r2 = 2000(0x7d0, double:9.88E-321)
            r0.postDelayed(r1, r2)
            return
        L_0x008f:
            android.animation.AnimatorSet r0 = r3.m
            r0.start()
            return
        L_0x0095:
            android.animation.AnimatorSet r0 = r3.n
            r0.start()
            return
        L_0x009b:
            r3.g()
            return
        L_0x009f:
            android.view.ViewGroup r0 = r3.f
            if (r0 == 0) goto L_0x0157
            android.view.ViewGroup r4 = r3.g
            if (r4 != 0) goto L_0x00a9
            goto L_0x0157
        L_0x00a9:
            com.google.android.exoplayer2.ui.StyledPlayerControlView r5 = r3.a
            int r6 = r5.getWidth()
            int r7 = r5.getPaddingLeft()
            int r6 = r6 - r7
            int r5 = r5.getPaddingRight()
            int r6 = r6 - r5
        L_0x00b9:
            int r5 = r4.getChildCount()
            if (r5 <= r1) goto L_0x00d0
            int r5 = r4.getChildCount()
            int r5 = r5 + -2
            android.view.View r7 = r4.getChildAt(r5)
            r4.removeViewAt(r5)
            r0.addView(r7, r2)
            goto L_0x00b9
        L_0x00d0:
            android.view.View r5 = r3.k
            if (r5 == 0) goto L_0x00d9
            r7 = 8
            r5.setVisibility(r7)
        L_0x00d9:
            android.view.ViewGroup r7 = r3.i
            int r7 = com.google.android.exoplayer2.ui.b.c(r7)
            int r8 = r0.getChildCount()
            int r8 = r8 - r1
            r9 = 0
        L_0x00e5:
            if (r9 >= r8) goto L_0x00f3
            android.view.View r10 = r0.getChildAt(r9)
            int r10 = com.google.android.exoplayer2.ui.b.c(r10)
            int r7 = r7 + r10
            int r9 = r9 + 1
            goto L_0x00e5
        L_0x00f3:
            if (r7 <= r6) goto L_0x013d
            if (r5 == 0) goto L_0x00ff
            r5.setVisibility(r2)
            int r3 = com.google.android.exoplayer2.ui.b.c(r5)
            int r7 = r7 + r3
        L_0x00ff:
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            r5 = 0
        L_0x0105:
            if (r5 >= r8) goto L_0x0119
            android.view.View r9 = r0.getChildAt(r5)
            int r10 = com.google.android.exoplayer2.ui.b.c(r9)
            int r7 = r7 - r10
            r3.add(r9)
            if (r7 > r6) goto L_0x0116
            goto L_0x0119
        L_0x0116:
            int r5 = r5 + 1
            goto L_0x0105
        L_0x0119:
            boolean r5 = r3.isEmpty()
            if (r5 != 0) goto L_0x0157
            int r5 = r3.size()
            r0.removeViews(r2, r5)
        L_0x0126:
            int r0 = r3.size()
            if (r2 >= r0) goto L_0x0157
            int r0 = r4.getChildCount()
            int r0 = r0 - r1
            java.lang.Object r5 = r3.get(r2)
            android.view.View r5 = (android.view.View) r5
            r4.addView(r5, r0)
            int r2 = r2 + 1
            goto L_0x0126
        L_0x013d:
            android.view.ViewGroup r0 = r3.h
            if (r0 == 0) goto L_0x0157
            int r0 = r0.getVisibility()
            if (r0 != 0) goto L_0x0157
            android.animation.ValueAnimator r0 = r3.r
            boolean r1 = r0.isStarted()
            if (r1 != 0) goto L_0x0157
            android.animation.ValueAnimator r1 = r3.q
            r1.cancel()
            r0.start()
        L_0x0157:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.xb.run():void");
    }
}
