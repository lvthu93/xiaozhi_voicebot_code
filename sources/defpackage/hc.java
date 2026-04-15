package defpackage;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;

/* renamed from: hc  reason: default package */
public final class hc {
    public final float a;
    public int aa;
    public int ab;
    public int ac;
    public int ad;
    public StaticLayout ae;
    public StaticLayout af;
    public int ag;
    public int ah;
    public int ai;
    public Rect aj;
    public final float b;
    public final float c;
    public final float d;
    public final float e;
    public final TextPaint f;
    public final Paint g;
    public final Paint h;
    @Nullable
    public CharSequence i;
    @Nullable
    public Layout.Alignment j;
    @Nullable
    public Bitmap k;
    public float l;
    public int m;
    public int n;
    public float o;
    public int p;
    public float q;
    public float r;
    public int s;
    public int t;
    public int u;
    public int v;
    public int w;
    public float x;
    public float y;
    public float z;

    public hc(Context context) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes((AttributeSet) null, new int[]{16843287, 16843288}, 0, 0);
        this.e = (float) obtainStyledAttributes.getDimensionPixelSize(0, 0);
        this.d = obtainStyledAttributes.getFloat(1, 1.0f);
        obtainStyledAttributes.recycle();
        float round = (float) Math.round((((float) context.getResources().getDisplayMetrics().densityDpi) * 2.0f) / 160.0f);
        this.a = round;
        this.b = round;
        this.c = round;
        TextPaint textPaint = new TextPaint();
        this.f = textPaint;
        textPaint.setAntiAlias(true);
        textPaint.setSubpixelText(true);
        Paint paint = new Paint();
        this.g = paint;
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        Paint paint2 = new Paint();
        this.h = paint2;
        paint2.setAntiAlias(true);
        paint2.setFilterBitmap(true);
    }

    public final void a(Canvas canvas, boolean z2) {
        int i2;
        if (z2) {
            StaticLayout staticLayout = this.ae;
            StaticLayout staticLayout2 = this.af;
            if (staticLayout != null && staticLayout2 != null) {
                int save = canvas.save();
                canvas.translate((float) this.ag, (float) this.ah);
                if (Color.alpha(this.u) > 0) {
                    Paint paint = this.g;
                    paint.setColor(this.u);
                    canvas.drawRect((float) (-this.ai), 0.0f, (float) (staticLayout.getWidth() + this.ai), (float) staticLayout.getHeight(), paint);
                }
                int i3 = this.w;
                TextPaint textPaint = this.f;
                boolean z3 = true;
                if (i3 == 1) {
                    textPaint.setStrokeJoin(Paint.Join.ROUND);
                    textPaint.setStrokeWidth(this.a);
                    textPaint.setColor(this.v);
                    textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                    staticLayout2.draw(canvas);
                } else {
                    float f2 = this.b;
                    if (i3 == 2) {
                        float f3 = this.c;
                        textPaint.setShadowLayer(f2, f3, f3, this.v);
                    } else if (i3 == 3 || i3 == 4) {
                        if (i3 != 3) {
                            z3 = false;
                        }
                        int i4 = -1;
                        if (z3) {
                            i2 = -1;
                        } else {
                            i2 = this.v;
                        }
                        if (z3) {
                            i4 = this.v;
                        }
                        float f4 = f2 / 2.0f;
                        textPaint.setColor(this.s);
                        textPaint.setStyle(Paint.Style.FILL);
                        float f5 = -f4;
                        textPaint.setShadowLayer(f2, f5, f5, i2);
                        staticLayout2.draw(canvas);
                        textPaint.setShadowLayer(f2, f4, f4, i4);
                    }
                }
                textPaint.setColor(this.s);
                textPaint.setStyle(Paint.Style.FILL);
                staticLayout.draw(canvas);
                textPaint.setShadowLayer(0.0f, 0.0f, 0.0f, 0);
                canvas.restoreToCount(save);
                return;
            }
            return;
        }
        Assertions.checkNotNull(this.aj);
        Assertions.checkNotNull(this.k);
        canvas.drawBitmap(this.k, (Rect) null, this.aj, this.h);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:148:0x034a, code lost:
        if (r6 < r2) goto L_0x0359;
     */
    /* JADX WARNING: Removed duplicated region for block: B:146:0x0345  */
    /* JADX WARNING: Removed duplicated region for block: B:147:0x0348  */
    /* JADX WARNING: Removed duplicated region for block: B:152:0x0392  */
    /* JADX WARNING: Removed duplicated region for block: B:165:0x03f4  */
    /* JADX WARNING: Removed duplicated region for block: B:166:0x03f6  */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x019b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void draw(com.google.android.exoplayer2.text.Cue r27, com.google.android.exoplayer2.ui.CaptionStyleCompat r28, float r29, float r30, float r31, android.graphics.Canvas r32, int r33, int r34, int r35, int r36) {
        /*
            r26 = this;
            r0 = r26
            r1 = r27
            r2 = r28
            r3 = r29
            r4 = r30
            r5 = r31
            r6 = r32
            r7 = r33
            r8 = r34
            r9 = r35
            r10 = r36
            android.graphics.Bitmap r11 = r1.d
            if (r11 != 0) goto L_0x001c
            r11 = 1
            goto L_0x001d
        L_0x001c:
            r11 = 0
        L_0x001d:
            java.lang.CharSequence r14 = r1.a
            if (r11 == 0) goto L_0x0032
            boolean r15 = android.text.TextUtils.isEmpty(r14)
            if (r15 == 0) goto L_0x0028
            return
        L_0x0028:
            boolean r15 = r1.l
            if (r15 == 0) goto L_0x002f
            int r15 = r1.m
            goto L_0x0034
        L_0x002f:
            int r15 = r2.c
            goto L_0x0034
        L_0x0032:
            r15 = -16777216(0xffffffffff000000, float:-1.7014118E38)
        L_0x0034:
            java.lang.CharSequence r13 = r0.i
            if (r13 == r14) goto L_0x0043
            if (r13 == 0) goto L_0x0041
            boolean r13 = r13.equals(r14)
            if (r13 == 0) goto L_0x0041
            goto L_0x0043
        L_0x0041:
            r13 = 0
            goto L_0x0044
        L_0x0043:
            r13 = 1
        L_0x0044:
            android.text.TextPaint r12 = r0.f
            r16 = r14
            float r14 = r1.k
            float r6 = r1.j
            r24 = r11
            int r11 = r1.i
            float r10 = r1.h
            int r9 = r1.g
            int r8 = r1.f
            float r7 = r1.e
            android.graphics.Bitmap r5 = r1.d
            android.text.Layout$Alignment r1 = r1.b
            if (r13 == 0) goto L_0x0145
            android.text.Layout$Alignment r13 = r0.j
            boolean r13 = com.google.android.exoplayer2.util.Util.areEqual(r13, r1)
            if (r13 == 0) goto L_0x0145
            android.graphics.Bitmap r13 = r0.k
            if (r13 != r5) goto L_0x0145
            float r13 = r0.l
            int r13 = (r13 > r7 ? 1 : (r13 == r7 ? 0 : -1))
            if (r13 != 0) goto L_0x0145
            int r13 = r0.m
            if (r13 != r8) goto L_0x0145
            int r13 = r0.n
            java.lang.Integer r13 = java.lang.Integer.valueOf(r13)
            r17 = r5
            java.lang.Integer r5 = java.lang.Integer.valueOf(r9)
            boolean r5 = com.google.android.exoplayer2.util.Util.areEqual(r13, r5)
            if (r5 == 0) goto L_0x013d
            float r5 = r0.o
            int r5 = (r5 > r10 ? 1 : (r5 == r10 ? 0 : -1))
            if (r5 != 0) goto L_0x013d
            int r5 = r0.p
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            java.lang.Integer r13 = java.lang.Integer.valueOf(r11)
            boolean r5 = com.google.android.exoplayer2.util.Util.areEqual(r5, r13)
            if (r5 == 0) goto L_0x013d
            float r5 = r0.q
            int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r5 != 0) goto L_0x013d
            float r5 = r0.r
            int r5 = (r5 > r14 ? 1 : (r5 == r14 ? 0 : -1))
            if (r5 != 0) goto L_0x013d
            int r5 = r0.s
            int r13 = r2.a
            if (r5 != r13) goto L_0x013d
            int r5 = r0.t
            int r13 = r2.b
            if (r5 != r13) goto L_0x013d
            int r5 = r0.u
            if (r5 != r15) goto L_0x013d
            int r5 = r0.w
            int r13 = r2.d
            if (r5 != r13) goto L_0x013d
            int r5 = r0.v
            int r13 = r2.e
            if (r5 != r13) goto L_0x013d
            android.graphics.Typeface r5 = r12.getTypeface()
            android.graphics.Typeface r13 = r2.f
            boolean r5 = com.google.android.exoplayer2.util.Util.areEqual(r5, r13)
            if (r5 == 0) goto L_0x013d
            float r5 = r0.x
            int r5 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r5 != 0) goto L_0x013d
            float r5 = r0.y
            int r5 = (r5 > r4 ? 1 : (r5 == r4 ? 0 : -1))
            if (r5 != 0) goto L_0x013d
            float r5 = r0.z
            r13 = r31
            r25 = r17
            r17 = r6
            r6 = r25
            int r5 = (r5 > r13 ? 1 : (r5 == r13 ? 0 : -1))
            if (r5 != 0) goto L_0x0137
            int r5 = r0.aa
            r18 = r8
            r8 = r7
            r7 = r33
            if (r5 != r7) goto L_0x012e
            int r5 = r0.ab
            r19 = r9
            r9 = r34
            r25 = r18
            r18 = r10
            r10 = r25
            if (r5 != r9) goto L_0x0125
            int r5 = r0.ac
            r9 = r35
            r7 = r19
            if (r5 != r9) goto L_0x011e
            int r5 = r0.ad
            r9 = r36
            r13 = r18
            if (r5 != r9) goto L_0x0119
            r5 = r32
            r9 = r24
            r0.a(r5, r9)
            return
        L_0x0119:
            r5 = r32
            r4 = r17
            goto L_0x014d
        L_0x011e:
            r5 = r32
            r4 = r17
            r13 = r18
            goto L_0x014d
        L_0x0125:
            r5 = r32
            r4 = r17
            r13 = r18
            r7 = r19
            goto L_0x014d
        L_0x012e:
            r5 = r32
            r7 = r9
            r13 = r10
            r4 = r17
            r10 = r18
            goto L_0x014d
        L_0x0137:
            r5 = r32
            r13 = r10
            r4 = r17
            goto L_0x0143
        L_0x013d:
            r5 = r32
            r4 = r6
            r13 = r10
            r6 = r17
        L_0x0143:
            r10 = r8
            goto L_0x014b
        L_0x0145:
            r4 = r6
            r13 = r10
            r6 = r5
            r10 = r8
            r5 = r32
        L_0x014b:
            r8 = r7
            r7 = r9
        L_0x014d:
            r9 = r16
            r0.i = r9
            r0.j = r1
            r0.k = r6
            r0.l = r8
            r0.m = r10
            r0.n = r7
            r0.o = r13
            r0.p = r11
            r0.q = r4
            r0.r = r14
            int r1 = r2.a
            r0.s = r1
            int r1 = r2.b
            r0.t = r1
            r0.u = r15
            int r1 = r2.d
            r0.w = r1
            int r1 = r2.e
            r0.v = r1
            android.graphics.Typeface r1 = r2.f
            r12.setTypeface(r1)
            r0.x = r3
            r1 = r30
            r0.y = r1
            r1 = r31
            r0.z = r1
            r1 = r33
            r0.aa = r1
            r1 = r34
            r2 = r35
            r0.ab = r1
            r0.ac = r2
            r1 = r36
            r13 = r24
            r0.ad = r1
            r1 = -8388609(0xffffffffff7fffff, float:-3.4028235E38)
            if (r13 == 0) goto L_0x0392
            java.lang.CharSequence r3 = r0.i
            com.google.android.exoplayer2.util.Assertions.checkNotNull(r3)
            java.lang.CharSequence r3 = r0.i
            boolean r4 = r3 instanceof android.text.SpannableStringBuilder
            if (r4 == 0) goto L_0x01a9
            android.text.SpannableStringBuilder r3 = (android.text.SpannableStringBuilder) r3
            goto L_0x01b0
        L_0x01a9:
            android.text.SpannableStringBuilder r3 = new android.text.SpannableStringBuilder
            java.lang.CharSequence r4 = r0.i
            r3.<init>(r4)
        L_0x01b0:
            int r4 = r0.ac
            int r6 = r0.aa
            int r4 = r4 - r6
            int r6 = r0.ad
            int r7 = r0.ab
            int r6 = r6 - r7
            float r7 = r0.x
            r12.setTextSize(r7)
            float r7 = r0.x
            r8 = 1040187392(0x3e000000, float:0.125)
            float r7 = r7 * r8
            r8 = 1056964608(0x3f000000, float:0.5)
            float r7 = r7 + r8
            int r7 = (int) r7
            int r8 = r7 * 2
            int r9 = r4 - r8
            float r10 = r0.q
            int r11 = (r10 > r1 ? 1 : (r10 == r1 ? 0 : -1))
            if (r11 == 0) goto L_0x01d7
            float r9 = (float) r9
            float r9 = r9 * r10
            int r9 = (int) r9
        L_0x01d7:
            java.lang.String r10 = "SubtitlePainter"
            if (r9 > 0) goto L_0x01e4
            java.lang.String r1 = "Skipped drawing subtitle cue (insufficient space)"
            com.google.android.exoplayer2.util.Log.w(r10, r1)
            r24 = r13
            goto L_0x040a
        L_0x01e4:
            float r11 = r0.y
            r14 = 0
            r15 = 16711680(0xff0000, float:2.3418052E-38)
            int r11 = (r11 > r14 ? 1 : (r11 == r14 ? 0 : -1))
            if (r11 <= 0) goto L_0x01fe
            android.text.style.AbsoluteSizeSpan r11 = new android.text.style.AbsoluteSizeSpan
            float r14 = r0.y
            int r14 = (int) r14
            r11.<init>(r14)
            int r14 = r3.length()
            r1 = 0
            r3.setSpan(r11, r1, r14, r15)
            goto L_0x01ff
        L_0x01fe:
            r1 = 0
        L_0x01ff:
            android.text.SpannableStringBuilder r11 = new android.text.SpannableStringBuilder
            r11.<init>(r3)
            int r14 = r0.w
            r15 = 1
            if (r14 != r15) goto L_0x0221
            int r14 = r11.length()
            java.lang.Class<android.text.style.ForegroundColorSpan> r15 = android.text.style.ForegroundColorSpan.class
            java.lang.Object[] r14 = r11.getSpans(r1, r14, r15)
            android.text.style.ForegroundColorSpan[] r14 = (android.text.style.ForegroundColorSpan[]) r14
            int r1 = r14.length
            r15 = 0
        L_0x0217:
            if (r15 >= r1) goto L_0x0221
            r2 = r14[r15]
            r11.removeSpan(r2)
            int r15 = r15 + 1
            goto L_0x0217
        L_0x0221:
            int r1 = r0.t
            int r1 = android.graphics.Color.alpha(r1)
            if (r1 <= 0) goto L_0x0254
            int r1 = r0.w
            if (r1 == 0) goto L_0x0243
            r2 = 2
            if (r1 != r2) goto L_0x0231
            goto L_0x0243
        L_0x0231:
            android.text.style.BackgroundColorSpan r1 = new android.text.style.BackgroundColorSpan
            int r2 = r0.t
            r1.<init>(r2)
            int r2 = r11.length()
            r14 = 0
            r15 = 16711680(0xff0000, float:2.3418052E-38)
            r11.setSpan(r1, r14, r2, r15)
            goto L_0x0254
        L_0x0243:
            r14 = 0
            r15 = 16711680(0xff0000, float:2.3418052E-38)
            android.text.style.BackgroundColorSpan r1 = new android.text.style.BackgroundColorSpan
            int r2 = r0.t
            r1.<init>(r2)
            int r2 = r3.length()
            r3.setSpan(r1, r14, r2, r15)
        L_0x0254:
            android.text.Layout$Alignment r1 = r0.j
            if (r1 != 0) goto L_0x025a
            android.text.Layout$Alignment r1 = android.text.Layout.Alignment.ALIGN_CENTER
        L_0x025a:
            android.text.StaticLayout r2 = new android.text.StaticLayout
            float r14 = r0.d
            float r15 = r0.e
            r23 = 1
            r16 = r2
            r17 = r3
            r18 = r12
            r19 = r9
            r20 = r1
            r21 = r14
            r22 = r15
            r16.<init>(r17, r18, r19, r20, r21, r22, r23)
            r0.ae = r2
            int r2 = r2.getHeight()
            android.text.StaticLayout r14 = r0.ae
            int r14 = r14.getLineCount()
            r5 = 0
            r15 = 0
        L_0x0281:
            if (r15 >= r14) goto L_0x029e
            r29 = r14
            android.text.StaticLayout r14 = r0.ae
            float r14 = r14.getLineWidth(r15)
            r24 = r13
            double r13 = (double) r14
            double r13 = java.lang.Math.ceil(r13)
            int r13 = (int) r13
            int r5 = java.lang.Math.max(r13, r5)
            int r15 = r15 + 1
            r14 = r29
            r13 = r24
            goto L_0x0281
        L_0x029e:
            r24 = r13
            float r13 = r0.q
            r14 = -8388609(0xffffffffff7fffff, float:-3.4028235E38)
            int r13 = (r13 > r14 ? 1 : (r13 == r14 ? 0 : -1))
            if (r13 == 0) goto L_0x02ac
            if (r5 >= r9) goto L_0x02ac
            goto L_0x02ad
        L_0x02ac:
            r9 = r5
        L_0x02ad:
            int r9 = r9 + r8
            float r5 = r0.o
            int r8 = (r5 > r14 ? 1 : (r5 == r14 ? 0 : -1))
            if (r8 == 0) goto L_0x02da
            float r4 = (float) r4
            float r4 = r4 * r5
            int r4 = java.lang.Math.round(r4)
            int r5 = r0.aa
            int r4 = r4 + r5
            int r8 = r0.p
            r13 = 1
            if (r8 == r13) goto L_0x02c9
            r13 = 2
            if (r8 == r13) goto L_0x02c7
            goto L_0x02ce
        L_0x02c7:
            int r4 = r4 - r9
            goto L_0x02ce
        L_0x02c9:
            r13 = 2
            int r4 = r4 * 2
            int r4 = r4 - r9
            int r4 = r4 / r13
        L_0x02ce:
            int r4 = java.lang.Math.max(r4, r5)
            int r9 = r9 + r4
            int r5 = r0.ac
            int r5 = java.lang.Math.min(r9, r5)
            goto L_0x02e2
        L_0x02da:
            r13 = 2
            int r4 = r4 - r9
            int r4 = r4 / r13
            int r5 = r0.aa
            int r4 = r4 + r5
            int r5 = r4 + r9
        L_0x02e2:
            int r5 = r5 - r4
            if (r5 > 0) goto L_0x02ec
            java.lang.String r1 = "Skipped drawing subtitle cue (invalid horizontal positioning)"
            com.google.android.exoplayer2.util.Log.w(r10, r1)
            goto L_0x040a
        L_0x02ec:
            float r8 = r0.l
            r9 = -8388609(0xffffffffff7fffff, float:-3.4028235E38)
            int r9 = (r8 > r9 ? 1 : (r8 == r9 ? 0 : -1))
            if (r9 == 0) goto L_0x034d
            int r9 = r0.m
            if (r9 != 0) goto L_0x0311
            float r6 = (float) r6
            float r6 = r6 * r8
            int r6 = java.lang.Math.round(r6)
            int r8 = r0.ab
            int r6 = r6 + r8
            int r8 = r0.n
            r9 = 2
            if (r8 != r9) goto L_0x0309
            goto L_0x033e
        L_0x0309:
            r10 = 1
            if (r8 != r10) goto L_0x033f
            int r6 = r6 * 2
            int r6 = r6 - r2
            int r6 = r6 / r9
            goto L_0x033f
        L_0x0311:
            android.text.StaticLayout r6 = r0.ae
            r8 = 0
            int r6 = r6.getLineBottom(r8)
            android.text.StaticLayout r9 = r0.ae
            int r8 = r9.getLineTop(r8)
            int r6 = r6 - r8
            float r8 = r0.l
            r9 = 0
            int r9 = (r8 > r9 ? 1 : (r8 == r9 ? 0 : -1))
            if (r9 < 0) goto L_0x0331
            float r6 = (float) r6
            float r8 = r8 * r6
            int r6 = java.lang.Math.round(r8)
            int r8 = r0.ab
            int r6 = r6 + r8
            goto L_0x033f
        L_0x0331:
            r9 = 1065353216(0x3f800000, float:1.0)
            float r8 = r8 + r9
            float r6 = (float) r6
            float r8 = r8 * r6
            int r6 = java.lang.Math.round(r8)
            int r8 = r0.ad
            int r6 = r6 + r8
        L_0x033e:
            int r6 = r6 - r2
        L_0x033f:
            int r8 = r6 + r2
            int r9 = r0.ad
            if (r8 <= r9) goto L_0x0348
            int r6 = r9 - r2
            goto L_0x0358
        L_0x0348:
            int r2 = r0.ab
            if (r6 >= r2) goto L_0x0358
            goto L_0x0359
        L_0x034d:
            int r8 = r0.ad
            int r8 = r8 - r2
            float r2 = (float) r6
            float r6 = r0.z
            float r2 = r2 * r6
            int r2 = (int) r2
            int r6 = r8 - r2
        L_0x0358:
            r2 = r6
        L_0x0359:
            android.text.StaticLayout r6 = new android.text.StaticLayout
            float r8 = r0.d
            float r9 = r0.e
            r10 = 1
            r23 = 1
            r16 = r6
            r17 = r3
            r18 = r12
            r19 = r5
            r20 = r1
            r21 = r8
            r22 = r9
            r16.<init>(r17, r18, r19, r20, r21, r22, r23)
            r0.ae = r6
            android.text.StaticLayout r3 = new android.text.StaticLayout
            float r6 = r0.d
            float r8 = r0.e
            r16 = r3
            r17 = r11
            r21 = r6
            r22 = r8
            r23 = r10
            r16.<init>(r17, r18, r19, r20, r21, r22, r23)
            r0.af = r3
            r0.ag = r4
            r0.ah = r2
            r0.ai = r7
            goto L_0x040a
        L_0x0392:
            r24 = r13
            android.graphics.Bitmap r1 = r0.k
            com.google.android.exoplayer2.util.Assertions.checkNotNull(r1)
            android.graphics.Bitmap r1 = r0.k
            int r2 = r0.ac
            int r3 = r0.aa
            int r2 = r2 - r3
            int r4 = r0.ad
            int r5 = r0.ab
            int r4 = r4 - r5
            float r3 = (float) r3
            float r2 = (float) r2
            float r6 = r0.o
            float r6 = r6 * r2
            float r6 = r6 + r3
            float r3 = (float) r5
            float r4 = (float) r4
            float r5 = r0.l
            float r5 = r5 * r4
            float r5 = r5 + r3
            float r3 = r0.q
            float r2 = r2 * r3
            int r2 = java.lang.Math.round(r2)
            float r3 = r0.r
            r7 = -8388609(0xffffffffff7fffff, float:-3.4028235E38)
            int r7 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1))
            if (r7 == 0) goto L_0x03cb
            float r4 = r4 * r3
            int r1 = java.lang.Math.round(r4)
            goto L_0x03dd
        L_0x03cb:
            float r3 = (float) r2
            int r4 = r1.getHeight()
            float r4 = (float) r4
            int r1 = r1.getWidth()
            float r1 = (float) r1
            float r4 = r4 / r1
            float r4 = r4 * r3
            int r1 = java.lang.Math.round(r4)
        L_0x03dd:
            int r3 = r0.p
            r4 = 2
            if (r3 != r4) goto L_0x03e4
            float r3 = (float) r2
            goto L_0x03ea
        L_0x03e4:
            r4 = 1
            if (r3 != r4) goto L_0x03eb
            int r3 = r2 / 2
            float r3 = (float) r3
        L_0x03ea:
            float r6 = r6 - r3
        L_0x03eb:
            int r3 = java.lang.Math.round(r6)
            int r4 = r0.n
            r6 = 2
            if (r4 != r6) goto L_0x03f6
            float r4 = (float) r1
            goto L_0x03fc
        L_0x03f6:
            r6 = 1
            if (r4 != r6) goto L_0x03fd
            int r4 = r1 / 2
            float r4 = (float) r4
        L_0x03fc:
            float r5 = r5 - r4
        L_0x03fd:
            int r4 = java.lang.Math.round(r5)
            android.graphics.Rect r5 = new android.graphics.Rect
            int r2 = r2 + r3
            int r1 = r1 + r4
            r5.<init>(r3, r4, r2, r1)
            r0.aj = r5
        L_0x040a:
            r1 = r32
            r12 = r24
            r0.a(r1, r12)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.hc.draw(com.google.android.exoplayer2.text.Cue, com.google.android.exoplayer2.ui.CaptionStyleCompat, float, float, float, android.graphics.Canvas, int, int, int, int):void");
    }
}
