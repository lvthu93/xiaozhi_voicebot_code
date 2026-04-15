package com.google.android.exoplayer2.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.ui.TimeBar;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.Collections;
import java.util.Formatter;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArraySet;

public class DefaultTimeBar extends View implements TimeBar {
    public static final /* synthetic */ int at = 0;
    public final qb aa;
    public final CopyOnWriteArraySet<TimeBar.OnScrubListener> ab;
    public final Point ac;
    public final float ad;
    public int ae;
    public long af;
    public int ag;
    public Rect ah;
    public final ValueAnimator ai;
    public float aj;
    public boolean ak;
    public boolean al;
    public long am;
    public long an;
    public long ao;
    public long ap;
    public int aq;
    @Nullable
    public long[] ar;
    @Nullable
    public boolean[] as;
    public final Rect c;
    public final Rect f;
    public final Rect g;
    public final Rect h;
    public final Paint i;
    public final Paint j;
    public final Paint k;
    public final Paint l;
    public final Paint m;
    public final Paint n;
    @Nullable
    public final Drawable o;
    public final int p;
    public final int q;
    public final int r;
    public final int s;
    public final int t;
    public final int u;
    public final int v;
    public final int w;
    public final int x;
    public final StringBuilder y;
    public final Formatter z;

    public DefaultTimeBar(Context context) {
        this(context, (AttributeSet) null);
    }

    public static int a(float f2, int i2) {
        return (int) ((((float) i2) * f2) + 0.5f);
    }

    private long getPositionIncrement() {
        long j2 = this.af;
        if (j2 != -9223372036854775807L) {
            return j2;
        }
        long j3 = this.an;
        if (j3 == -9223372036854775807L) {
            return 0;
        }
        return j3 / ((long) this.ae);
    }

    private String getProgressText() {
        return Util.getStringForTime(this.y, this.z, this.ao);
    }

    private long getScrubberPosition() {
        Rect rect = this.f;
        if (rect.width() <= 0 || this.an == -9223372036854775807L) {
            return 0;
        }
        return (((long) this.h.width()) * this.an) / ((long) rect.width());
    }

    public void addListener(TimeBar.OnScrubListener onScrubListener) {
        Assertions.checkNotNull(onScrubListener);
        this.ab.add(onScrubListener);
    }

    public final boolean b(long j2) {
        long j3;
        long j4 = this.an;
        if (j4 <= 0) {
            return false;
        }
        if (this.al) {
            j3 = this.am;
        } else {
            j3 = this.ao;
        }
        long j5 = j3;
        long constrainValue = Util.constrainValue(j5 + j2, 0, j4);
        if (constrainValue == j5) {
            return false;
        }
        if (!this.al) {
            c(constrainValue);
        } else {
            f(constrainValue);
        }
        e();
        return true;
    }

    public final void c(long j2) {
        this.am = j2;
        this.al = true;
        setPressed(true);
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
        Iterator<TimeBar.OnScrubListener> it = this.ab.iterator();
        while (it.hasNext()) {
            it.next().onScrubStart(this, j2);
        }
    }

    public final void d(boolean z2) {
        removeCallbacks(this.aa);
        this.al = false;
        setPressed(false);
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(false);
        }
        invalidate();
        Iterator<TimeBar.OnScrubListener> it = this.ab.iterator();
        while (it.hasNext()) {
            it.next().onScrubStop(this, this.am, z2);
        }
    }

    public final void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable = this.o;
        if (drawable != null && drawable.isStateful() && drawable.setState(getDrawableState())) {
            invalidate();
        }
    }

    public final void e() {
        long j2;
        Rect rect = this.g;
        Rect rect2 = this.f;
        rect.set(rect2);
        Rect rect3 = this.h;
        rect3.set(rect2);
        if (this.al) {
            j2 = this.am;
        } else {
            j2 = this.ao;
        }
        if (this.an > 0) {
            rect.right = Math.min(rect2.left + ((int) ((((long) rect2.width()) * this.ap) / this.an)), rect2.right);
            rect3.right = Math.min(rect2.left + ((int) ((((long) rect2.width()) * j2) / this.an)), rect2.right);
        } else {
            int i2 = rect2.left;
            rect.right = i2;
            rect3.right = i2;
        }
        invalidate(this.c);
    }

    public final void f(long j2) {
        if (this.am != j2) {
            this.am = j2;
            Iterator<TimeBar.OnScrubListener> it = this.ab.iterator();
            while (it.hasNext()) {
                it.next().onScrubMove(this, j2);
            }
        }
    }

    public long getPreferredUpdateDelay() {
        int width = (int) (((float) this.f.width()) / this.ad);
        if (width != 0) {
            long j2 = this.an;
            if (!(j2 == 0 || j2 == -9223372036854775807L)) {
                return j2 / ((long) width);
            }
        }
        return Long.MAX_VALUE;
    }

    public void hideScrubber(boolean z2) {
        ValueAnimator valueAnimator = this.ai;
        if (valueAnimator.isStarted()) {
            valueAnimator.cancel();
        }
        this.ak = z2;
        this.aj = 0.0f;
        invalidate(this.c);
    }

    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable = this.o;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v18, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v1, resolved type: boolean[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onDraw(android.graphics.Canvas r24) {
        /*
            r23 = this;
            r0 = r23
            r7 = r24
            r24.save()
            android.graphics.Rect r8 = r0.f
            int r1 = r8.height()
            int r2 = r8.centerY()
            int r3 = r1 / 2
            int r9 = r2 - r3
            int r10 = r1 + r9
            long r1 = r0.an
            android.graphics.Paint r6 = r0.k
            android.graphics.Rect r11 = r0.h
            r12 = 0
            int r3 = (r1 > r12 ? 1 : (r1 == r12 ? 0 : -1))
            if (r3 > 0) goto L_0x0032
            int r1 = r8.left
            float r2 = (float) r1
            float r3 = (float) r9
            int r1 = r8.right
            float r4 = (float) r1
            float r5 = (float) r10
            r1 = r24
            r1.drawRect(r2, r3, r4, r5, r6)
            goto L_0x00f2
        L_0x0032:
            android.graphics.Rect r1 = r0.g
            int r14 = r1.left
            int r15 = r1.right
            int r1 = r8.left
            int r1 = java.lang.Math.max(r1, r15)
            int r2 = r11.right
            int r1 = java.lang.Math.max(r1, r2)
            int r2 = r8.right
            if (r1 >= r2) goto L_0x0058
            float r3 = (float) r1
            float r4 = (float) r9
            float r5 = (float) r2
            float r2 = (float) r10
            r1 = r24
            r16 = r2
            r2 = r3
            r3 = r4
            r4 = r5
            r5 = r16
            r1.drawRect(r2, r3, r4, r5, r6)
        L_0x0058:
            int r1 = r11.right
            int r1 = java.lang.Math.max(r14, r1)
            if (r15 <= r1) goto L_0x006b
            float r2 = (float) r1
            float r3 = (float) r9
            float r4 = (float) r15
            float r5 = (float) r10
            android.graphics.Paint r6 = r0.j
            r1 = r24
            r1.drawRect(r2, r3, r4, r5, r6)
        L_0x006b:
            int r1 = r11.width()
            if (r1 <= 0) goto L_0x0080
            int r1 = r11.left
            float r2 = (float) r1
            float r3 = (float) r9
            int r1 = r11.right
            float r4 = (float) r1
            float r5 = (float) r10
            android.graphics.Paint r6 = r0.i
            r1 = r24
            r1.drawRect(r2, r3, r4, r5, r6)
        L_0x0080:
            int r1 = r0.aq
            if (r1 != 0) goto L_0x0086
            goto L_0x00f2
        L_0x0086:
            long[] r1 = r0.ar
            java.lang.Object r1 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r1)
            r14 = r1
            long[] r14 = (long[]) r14
            boolean[] r1 = r0.as
            java.lang.Object r1 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r1)
            r15 = r1
            boolean[] r15 = (boolean[]) r15
            int r6 = r0.s
            int r16 = r6 / 2
            r5 = 0
            r4 = 0
        L_0x009e:
            int r1 = r0.aq
            if (r4 >= r1) goto L_0x00f2
            r17 = r14[r4]
            r19 = 0
            long r1 = r0.an
            r21 = r1
            long r1 = com.google.android.exoplayer2.util.Util.constrainValue((long) r17, (long) r19, (long) r21)
            int r3 = r8.width()
            long r12 = (long) r3
            long r12 = r12 * r1
            long r1 = r0.an
            long r12 = r12 / r1
            int r1 = (int) r12
            int r1 = r1 - r16
            int r2 = r8.left
            int r3 = r8.width()
            int r3 = r3 - r6
            int r1 = java.lang.Math.max(r5, r1)
            int r1 = java.lang.Math.min(r3, r1)
            int r1 = r1 + r2
            boolean r2 = r15[r4]
            if (r2 == 0) goto L_0x00d2
            android.graphics.Paint r2 = r0.m
            goto L_0x00d4
        L_0x00d2:
            android.graphics.Paint r2 = r0.l
        L_0x00d4:
            r12 = r2
            float r2 = (float) r1
            float r3 = (float) r9
            int r1 = r1 + r6
            float r13 = (float) r1
            float r1 = (float) r10
            r19 = r1
            r1 = r24
            r20 = r4
            r4 = r13
            r13 = 0
            r5 = r19
            r19 = r6
            r6 = r12
            r1.drawRect(r2, r3, r4, r5, r6)
            int r4 = r20 + 1
            r6 = r19
            r5 = 0
            r12 = 0
            goto L_0x009e
        L_0x00f2:
            long r1 = r0.an
            r3 = 0
            int r5 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r5 > 0) goto L_0x00fb
            goto L_0x015c
        L_0x00fb:
            int r1 = r11.right
            int r2 = r11.left
            int r3 = r8.right
            int r1 = com.google.android.exoplayer2.util.Util.constrainValue((int) r1, (int) r2, (int) r3)
            int r2 = r11.centerY()
            android.graphics.drawable.Drawable r3 = r0.o
            if (r3 != 0) goto L_0x0138
            boolean r3 = r0.al
            if (r3 != 0) goto L_0x0124
            boolean r3 = r23.isFocused()
            if (r3 == 0) goto L_0x0118
            goto L_0x0124
        L_0x0118:
            boolean r3 = r23.isEnabled()
            if (r3 == 0) goto L_0x0121
            int r3 = r0.t
            goto L_0x0126
        L_0x0121:
            int r3 = r0.u
            goto L_0x0126
        L_0x0124:
            int r3 = r0.v
        L_0x0126:
            float r3 = (float) r3
            float r4 = r0.aj
            float r3 = r3 * r4
            r4 = 1073741824(0x40000000, float:2.0)
            float r3 = r3 / r4
            int r3 = (int) r3
            float r1 = (float) r1
            float r2 = (float) r2
            float r3 = (float) r3
            android.graphics.Paint r4 = r0.n
            r7.drawCircle(r1, r2, r3, r4)
            goto L_0x015c
        L_0x0138:
            int r4 = r3.getIntrinsicWidth()
            float r4 = (float) r4
            float r5 = r0.aj
            float r4 = r4 * r5
            int r4 = (int) r4
            int r5 = r3.getIntrinsicHeight()
            float r5 = (float) r5
            float r6 = r0.aj
            float r5 = r5 * r6
            int r5 = (int) r5
            int r4 = r4 / 2
            int r6 = r1 - r4
            int r5 = r5 / 2
            int r8 = r2 - r5
            int r1 = r1 + r4
            int r2 = r2 + r5
            r3.setBounds(r6, r8, r1, r2)
            r3.draw(r7)
        L_0x015c:
            r24.restore()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ui.DefaultTimeBar.onDraw(android.graphics.Canvas):void");
    }

    public final void onFocusChanged(boolean z2, int i2, @Nullable Rect rect) {
        super.onFocusChanged(z2, i2, rect);
        if (this.al && !z2) {
            d(false);
        }
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        if (accessibilityEvent.getEventType() == 4) {
            accessibilityEvent.getText().add(getProgressText());
        }
        accessibilityEvent.setClassName("android.widget.SeekBar");
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName("android.widget.SeekBar");
        accessibilityNodeInfo.setContentDescription(getProgressText());
        if (this.an > 0) {
            if (Util.a >= 21) {
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_FORWARD);
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_BACKWARD);
                return;
            }
            accessibilityNodeInfo.addAction(4096);
            accessibilityNodeInfo.addAction(8192);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0018, code lost:
        if (b(r0) == false) goto L_0x002e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x001a, code lost:
        r5 = r4.aa;
        removeCallbacks(r5);
        postDelayed(r5, 1000);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0024, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onKeyDown(int r5, android.view.KeyEvent r6) {
        /*
            r4 = this;
            boolean r0 = r4.isEnabled()
            if (r0 == 0) goto L_0x002e
            long r0 = r4.getPositionIncrement()
            r2 = 66
            r3 = 1
            if (r5 == r2) goto L_0x0025
            switch(r5) {
                case 21: goto L_0x0013;
                case 22: goto L_0x0014;
                case 23: goto L_0x0025;
                default: goto L_0x0012;
            }
        L_0x0012:
            goto L_0x002e
        L_0x0013:
            long r0 = -r0
        L_0x0014:
            boolean r0 = r4.b(r0)
            if (r0 == 0) goto L_0x002e
            qb r5 = r4.aa
            r4.removeCallbacks(r5)
            r0 = 1000(0x3e8, double:4.94E-321)
            r4.postDelayed(r5, r0)
            return r3
        L_0x0025:
            boolean r0 = r4.al
            if (r0 == 0) goto L_0x002e
            r5 = 0
            r4.d(r5)
            return r3
        L_0x002e:
            boolean r5 = super.onKeyDown(r5, r6)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ui.DefaultTimeBar.onKeyDown(int, android.view.KeyEvent):boolean");
    }

    public final void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        int i6;
        int i7;
        int i8;
        Rect rect;
        int i9 = i4 - i2;
        int i10 = i5 - i3;
        int paddingLeft = getPaddingLeft();
        int paddingRight = i9 - getPaddingRight();
        if (this.ak) {
            i6 = 0;
        } else {
            i6 = this.w;
        }
        int i11 = this.r;
        int i12 = this.p;
        int i13 = this.q;
        if (i11 == 1) {
            i8 = (i10 - getPaddingBottom()) - i13;
            i7 = ((i10 - getPaddingBottom()) - i12) - Math.max(i6 - (i12 / 2), 0);
        } else {
            i8 = (i10 - i13) / 2;
            i7 = (i10 - i12) / 2;
        }
        Rect rect2 = this.c;
        rect2.set(paddingLeft, i8, paddingRight, i13 + i8);
        this.f.set(rect2.left + i6, i7, rect2.right - i6, i12 + i7);
        if (Util.a >= 29 && !((rect = this.ah) != null && rect.width() == i9 && this.ah.height() == i10)) {
            Rect rect3 = new Rect(0, 0, i9, i10);
            this.ah = rect3;
            setSystemGestureExclusionRects(Collections.singletonList(rect3));
        }
        e();
    }

    public final void onMeasure(int i2, int i3) {
        int mode = View.MeasureSpec.getMode(i3);
        int size = View.MeasureSpec.getSize(i3);
        int i4 = this.q;
        if (mode == 0) {
            size = i4;
        } else if (mode != 1073741824) {
            size = Math.min(i4, size);
        }
        setMeasuredDimension(View.MeasureSpec.getSize(i2), size);
        Drawable drawable = this.o;
        if (drawable != null && drawable.isStateful() && drawable.setState(getDrawableState())) {
            invalidate();
        }
    }

    public void onRtlPropertiesChanged(int i2) {
        boolean z2;
        Drawable drawable = this.o;
        if (drawable != null) {
            if (Util.a < 23 || !drawable.setLayoutDirection(i2)) {
                z2 = false;
            } else {
                z2 = true;
            }
            if (z2) {
                invalidate();
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0035, code lost:
        if (r3 != 3) goto L_0x00a1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onTouchEvent(android.view.MotionEvent r10) {
        /*
            r9 = this;
            boolean r0 = r9.isEnabled()
            r1 = 0
            if (r0 == 0) goto L_0x00a1
            long r2 = r9.an
            r4 = 0
            int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r0 > 0) goto L_0x0011
            goto L_0x00a1
        L_0x0011:
            android.graphics.Point r0 = r9.ac
            float r2 = r10.getX()
            int r2 = (int) r2
            float r3 = r10.getY()
            int r3 = (int) r3
            r0.set(r2, r3)
            int r2 = r0.x
            int r0 = r0.y
            int r3 = r10.getAction()
            r4 = 1
            android.graphics.Rect r5 = r9.h
            android.graphics.Rect r6 = r9.f
            if (r3 == 0) goto L_0x007d
            r7 = 3
            if (r3 == r4) goto L_0x006e
            r8 = 2
            if (r3 == r8) goto L_0x0038
            if (r3 == r7) goto L_0x006e
            goto L_0x00a1
        L_0x0038:
            boolean r10 = r9.al
            if (r10 == 0) goto L_0x00a1
            int r10 = r9.x
            if (r0 >= r10) goto L_0x0052
            int r10 = r9.ag
            int r2 = r2 - r10
            int r2 = r2 / r7
            int r2 = r2 + r10
            float r10 = (float) r2
            int r10 = (int) r10
            int r0 = r6.left
            int r1 = r6.right
            int r10 = com.google.android.exoplayer2.util.Util.constrainValue((int) r10, (int) r0, (int) r1)
            r5.right = r10
            goto L_0x0060
        L_0x0052:
            r9.ag = r2
            float r10 = (float) r2
            int r10 = (int) r10
            int r0 = r6.left
            int r1 = r6.right
            int r10 = com.google.android.exoplayer2.util.Util.constrainValue((int) r10, (int) r0, (int) r1)
            r5.right = r10
        L_0x0060:
            long r0 = r9.getScrubberPosition()
            r9.f(r0)
            r9.e()
            r9.invalidate()
            return r4
        L_0x006e:
            boolean r0 = r9.al
            if (r0 == 0) goto L_0x00a1
            int r10 = r10.getAction()
            if (r10 != r7) goto L_0x0079
            r1 = 1
        L_0x0079:
            r9.d(r1)
            return r4
        L_0x007d:
            float r10 = (float) r2
            float r0 = (float) r0
            int r10 = (int) r10
            int r0 = (int) r0
            android.graphics.Rect r2 = r9.c
            boolean r0 = r2.contains(r10, r0)
            if (r0 == 0) goto L_0x00a1
            int r0 = r6.left
            int r1 = r6.right
            int r10 = com.google.android.exoplayer2.util.Util.constrainValue((int) r10, (int) r0, (int) r1)
            r5.right = r10
            long r0 = r9.getScrubberPosition()
            r9.c(r0)
            r9.e()
            r9.invalidate()
            return r4
        L_0x00a1:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ui.DefaultTimeBar.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public boolean performAccessibilityAction(int i2, @Nullable Bundle bundle) {
        if (super.performAccessibilityAction(i2, bundle)) {
            return true;
        }
        if (this.an <= 0) {
            return false;
        }
        if (i2 == 8192) {
            if (b(-getPositionIncrement())) {
                d(false);
            }
        } else if (i2 != 4096) {
            return false;
        } else {
            if (b(getPositionIncrement())) {
                d(false);
            }
        }
        sendAccessibilityEvent(4);
        return true;
    }

    public void removeListener(TimeBar.OnScrubListener onScrubListener) {
        this.ab.remove(onScrubListener);
    }

    public void setAdGroupTimesMs(@Nullable long[] jArr, @Nullable boolean[] zArr, int i2) {
        boolean z2;
        if (i2 == 0 || !(jArr == null || zArr == null)) {
            z2 = true;
        } else {
            z2 = false;
        }
        Assertions.checkArgument(z2);
        this.aq = i2;
        this.ar = jArr;
        this.as = zArr;
        e();
    }

    public void setAdMarkerColor(@ColorInt int i2) {
        this.l.setColor(i2);
        invalidate(this.c);
    }

    public void setBufferedColor(@ColorInt int i2) {
        this.j.setColor(i2);
        invalidate(this.c);
    }

    public void setBufferedPosition(long j2) {
        this.ap = j2;
        e();
    }

    public void setDuration(long j2) {
        this.an = j2;
        if (this.al && j2 == -9223372036854775807L) {
            d(true);
        }
        e();
    }

    public void setEnabled(boolean z2) {
        super.setEnabled(z2);
        if (this.al && !z2) {
            d(true);
        }
    }

    public void setKeyCountIncrement(int i2) {
        boolean z2;
        if (i2 > 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        Assertions.checkArgument(z2);
        this.ae = i2;
        this.af = -9223372036854775807L;
    }

    public void setKeyTimeIncrement(long j2) {
        boolean z2;
        if (j2 > 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        Assertions.checkArgument(z2);
        this.ae = -1;
        this.af = j2;
    }

    public void setPlayedAdMarkerColor(@ColorInt int i2) {
        this.m.setColor(i2);
        invalidate(this.c);
    }

    public void setPlayedColor(@ColorInt int i2) {
        this.i.setColor(i2);
        invalidate(this.c);
    }

    public void setPosition(long j2) {
        this.ao = j2;
        setContentDescription(getProgressText());
        e();
    }

    public void setScrubberColor(@ColorInt int i2) {
        this.n.setColor(i2);
        invalidate(this.c);
    }

    public void setUnplayedColor(@ColorInt int i2) {
        this.k.setColor(i2);
        invalidate(this.c);
    }

    public void showScrubber() {
        ValueAnimator valueAnimator = this.ai;
        if (valueAnimator.isStarted()) {
            valueAnimator.cancel();
        }
        this.ak = false;
        this.aj = 1.0f;
        invalidate(this.c);
    }

    public DefaultTimeBar(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DefaultTimeBar(Context context, @Nullable AttributeSet attributeSet, int i2) {
        this(context, attributeSet, i2, attributeSet);
    }

    public DefaultTimeBar(Context context, @Nullable AttributeSet attributeSet, int i2, @Nullable AttributeSet attributeSet2) {
        this(context, attributeSet, i2, attributeSet2, 0);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public DefaultTimeBar(Context context, @Nullable AttributeSet attributeSet, int i2, @Nullable AttributeSet attributeSet2, int i3) {
        super(context, attributeSet, i2);
        AttributeSet attributeSet3 = attributeSet2;
        this.c = new Rect();
        this.f = new Rect();
        this.g = new Rect();
        this.h = new Rect();
        Paint paint = new Paint();
        this.i = paint;
        Paint paint2 = new Paint();
        this.j = paint2;
        Paint paint3 = new Paint();
        this.k = paint3;
        Paint paint4 = new Paint();
        this.l = paint4;
        Paint paint5 = new Paint();
        this.m = paint5;
        Paint paint6 = new Paint();
        this.n = paint6;
        paint6.setAntiAlias(true);
        this.ab = new CopyOnWriteArraySet<>();
        this.ac = new Point();
        float f2 = context.getResources().getDisplayMetrics().density;
        this.ad = f2;
        this.x = a(f2, -50);
        int a = a(f2, 4);
        int a2 = a(f2, 26);
        int a3 = a(f2, 4);
        int a4 = a(f2, 12);
        int a5 = a(f2, 0);
        int a6 = a(f2, 16);
        if (attributeSet3 != null) {
            Paint paint7 = paint4;
            Paint paint8 = paint5;
            TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet3, R.styleable.b, i2, i3);
            try {
                Drawable drawable = obtainStyledAttributes.getDrawable(10);
                this.o = drawable;
                if (drawable != null) {
                    int i4 = Util.a;
                    if (i4 >= 23) {
                        int layoutDirection = getLayoutDirection();
                        if (i4 >= 23) {
                            boolean y2 = drawable.setLayoutDirection(layoutDirection);
                        }
                    }
                    a2 = Math.max(drawable.getMinimumHeight(), a2);
                }
                this.p = obtainStyledAttributes.getDimensionPixelSize(3, a);
                this.q = obtainStyledAttributes.getDimensionPixelSize(12, a2);
                this.r = obtainStyledAttributes.getInt(2, 0);
                this.s = obtainStyledAttributes.getDimensionPixelSize(1, a3);
                this.t = obtainStyledAttributes.getDimensionPixelSize(11, a4);
                this.u = obtainStyledAttributes.getDimensionPixelSize(8, a5);
                this.v = obtainStyledAttributes.getDimensionPixelSize(9, a6);
                int i5 = obtainStyledAttributes.getInt(6, -1);
                int i6 = obtainStyledAttributes.getInt(7, -1);
                int i7 = obtainStyledAttributes.getInt(4, -855638017);
                int i8 = obtainStyledAttributes.getInt(13, 872415231);
                int i9 = obtainStyledAttributes.getInt(0, -1291845888);
                int i10 = obtainStyledAttributes.getInt(5, 872414976);
                paint.setColor(i5);
                paint6.setColor(i6);
                paint2.setColor(i7);
                paint3.setColor(i8);
                paint7.setColor(i9);
                paint8.setColor(i10);
            } finally {
                obtainStyledAttributes.recycle();
            }
        } else {
            this.p = a;
            this.q = a2;
            this.r = 0;
            this.s = a3;
            this.t = a4;
            this.u = a5;
            this.v = a6;
            paint.setColor(-1);
            paint6.setColor(-1);
            paint2.setColor(-855638017);
            paint3.setColor(872415231);
            paint4.setColor(-1291845888);
            paint5.setColor(872414976);
            this.o = null;
        }
        StringBuilder sb = new StringBuilder();
        this.y = sb;
        this.z = new Formatter(sb, Locale.getDefault());
        this.aa = new qb(6, this);
        Drawable drawable2 = this.o;
        if (drawable2 != null) {
            this.w = (drawable2.getMinimumWidth() + 1) / 2;
        } else {
            this.w = (Math.max(this.u, Math.max(this.t, this.v)) + 1) / 2;
        }
        this.aj = 1.0f;
        ValueAnimator valueAnimator = new ValueAnimator();
        this.ai = valueAnimator;
        valueAnimator.addUpdateListener(new yb(4, this));
        this.an = -9223372036854775807L;
        this.af = -9223372036854775807L;
        this.ae = 20;
        setFocusable(true);
        if (getImportantForAccessibility() == 0) {
            setImportantForAccessibility(1);
        }
    }

    public void hideScrubber(long j2) {
        ValueAnimator valueAnimator = this.ai;
        if (valueAnimator.isStarted()) {
            valueAnimator.cancel();
        }
        valueAnimator.setFloatValues(new float[]{this.aj, 0.0f});
        valueAnimator.setDuration(j2);
        valueAnimator.start();
    }

    public void showScrubber(long j2) {
        ValueAnimator valueAnimator = this.ai;
        if (valueAnimator.isStarted()) {
            valueAnimator.cancel();
        }
        this.ak = false;
        valueAnimator.setFloatValues(new float[]{this.aj, 1.0f});
        valueAnimator.setDuration(j2);
        valueAnimator.start();
    }
}
