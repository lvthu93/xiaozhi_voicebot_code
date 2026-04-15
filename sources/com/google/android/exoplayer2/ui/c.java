package com.google.android.exoplayer2.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;
import android.widget.FrameLayout;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.ui.SubtitleView;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class c extends FrameLayout implements SubtitleView.a {
    public final a c;
    public final a f;
    public List<Cue> g;
    public CaptionStyleCompat h;
    public float i;
    public int j;
    public float k;

    public class a extends WebView {
        public a(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            super.onTouchEvent(motionEvent);
            return false;
        }

        public boolean performClick() {
            super.performClick();
            return false;
        }
    }

    public static /* synthetic */ class b {
        public static final /* synthetic */ int[] a;

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|(3:5|6|8)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        static {
            /*
                android.text.Layout$Alignment[] r0 = android.text.Layout.Alignment.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                a = r0
                android.text.Layout$Alignment r1 = android.text.Layout.Alignment.ALIGN_NORMAL     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x001d }
                android.text.Layout$Alignment r1 = android.text.Layout.Alignment.ALIGN_OPPOSITE     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0028 }
                android.text.Layout$Alignment r1 = android.text.Layout.Alignment.ALIGN_CENTER     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ui.c.b.<clinit>():void");
        }
    }

    public c(Context context) {
        this(context, (AttributeSet) null);
    }

    public final String a(float f2, int i2) {
        float resolveTextSize = ic.resolveTextSize(i2, f2, getHeight(), (getHeight() - getPaddingTop()) - getPaddingBottom());
        if (resolveTextSize == -3.4028235E38f) {
            return "unset";
        }
        return Util.formatInvariant("%.2fpx", Float.valueOf(resolveTextSize / getContext().getResources().getDisplayMetrics().density));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:77:0x01d5, code lost:
        if (r8 != false) goto L_0x01db;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x01d8, code lost:
        if (r8 != false) goto L_0x01dd;
     */
    /* JADX WARNING: Removed duplicated region for block: B:104:0x02b2  */
    /* JADX WARNING: Removed duplicated region for block: B:107:0x02d4  */
    /* JADX WARNING: Removed duplicated region for block: B:114:0x0304  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x0163  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0173  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x017f  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x0184  */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x01a0  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x01a8  */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x01b8  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x01bb  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x01cc  */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x01d8  */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x01ea  */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x01ef  */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x0219  */
    /* JADX WARNING: Removed duplicated region for block: B:98:0x0294  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void b() {
        /*
            r31 = this;
            r0 = r31
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r2 = 4
            java.lang.Object[] r3 = new java.lang.Object[r2]
            com.google.android.exoplayer2.ui.CaptionStyleCompat r4 = r0.h
            int r4 = r4.a
            java.lang.String r4 = defpackage.r3.toCssRgba(r4)
            r5 = 0
            r3[r5] = r4
            int r4 = r0.j
            float r6 = r0.i
            java.lang.String r4 = r0.a(r6, r4)
            r6 = 1
            r3[r6] = r4
            r4 = 1067030938(0x3f99999a, float:1.2)
            java.lang.Float r7 = java.lang.Float.valueOf(r4)
            r8 = 2
            r3[r8] = r7
            com.google.android.exoplayer2.ui.CaptionStyleCompat r7 = r0.h
            int r9 = r7.d
            r10 = 3
            int r7 = r7.e
            if (r9 == r6) goto L_0x0069
            if (r9 == r8) goto L_0x005a
            if (r9 == r10) goto L_0x004b
            if (r9 == r2) goto L_0x003c
            java.lang.String r7 = "unset"
            goto L_0x0077
        L_0x003c:
            java.lang.Object[] r9 = new java.lang.Object[r6]
            java.lang.String r7 = defpackage.r3.toCssRgba(r7)
            r9[r5] = r7
            java.lang.String r7 = "-0.05em -0.05em 0.15em %s"
            java.lang.String r7 = com.google.android.exoplayer2.util.Util.formatInvariant(r7, r9)
            goto L_0x0077
        L_0x004b:
            java.lang.Object[] r9 = new java.lang.Object[r6]
            java.lang.String r7 = defpackage.r3.toCssRgba(r7)
            r9[r5] = r7
            java.lang.String r7 = "0.06em 0.08em 0.15em %s"
            java.lang.String r7 = com.google.android.exoplayer2.util.Util.formatInvariant(r7, r9)
            goto L_0x0077
        L_0x005a:
            java.lang.Object[] r9 = new java.lang.Object[r6]
            java.lang.String r7 = defpackage.r3.toCssRgba(r7)
            r9[r5] = r7
            java.lang.String r7 = "0.1em 0.12em 0.15em %s"
            java.lang.String r7 = com.google.android.exoplayer2.util.Util.formatInvariant(r7, r9)
            goto L_0x0077
        L_0x0069:
            java.lang.Object[] r9 = new java.lang.Object[r6]
            java.lang.String r7 = defpackage.r3.toCssRgba(r7)
            r9[r5] = r7
            java.lang.String r7 = "1px 1px 0 %1$s, 1px -1px 0 %1$s, -1px 1px 0 %1$s, -1px -1px 0 %1$s"
            java.lang.String r7 = com.google.android.exoplayer2.util.Util.formatInvariant(r7, r9)
        L_0x0077:
            r3[r10] = r7
            java.lang.String r7 = "<body><div style='-webkit-user-select:none;position:fixed;top:0;bottom:0;left:0;right:0;color:%s;font-size:%s;line-height:%.2f;text-shadow:%s;'>"
            java.lang.String r3 = com.google.android.exoplayer2.util.Util.formatInvariant(r7, r3)
            r1.append(r3)
            java.util.HashMap r3 = new java.util.HashMap
            r3.<init>()
            java.lang.String r7 = "default_bg"
            java.lang.String r9 = defpackage.r3.cssAllClassDescendantsSelector(r7)
            java.lang.Object[] r11 = new java.lang.Object[r6]
            com.google.android.exoplayer2.ui.CaptionStyleCompat r12 = r0.h
            int r12 = r12.b
            java.lang.String r12 = defpackage.r3.toCssRgba(r12)
            r11[r5] = r12
            java.lang.String r12 = "background-color:%s;"
            java.lang.String r11 = com.google.android.exoplayer2.util.Util.formatInvariant(r12, r11)
            r3.put(r9, r11)
            r9 = 0
        L_0x00a3:
            java.util.List<com.google.android.exoplayer2.text.Cue> r11 = r0.g
            int r11 = r11.size()
            if (r9 >= r11) goto L_0x0321
            java.util.List<com.google.android.exoplayer2.text.Cue> r11 = r0.g
            java.lang.Object r11 = r11.get(r9)
            com.google.android.exoplayer2.text.Cue r11 = (com.google.android.exoplayer2.text.Cue) r11
            float r12 = r11.h
            r13 = -8388609(0xffffffffff7fffff, float:-3.4028235E38)
            r14 = 1120403456(0x42c80000, float:100.0)
            int r15 = (r12 > r13 ? 1 : (r12 == r13 ? 0 : -1))
            if (r15 == 0) goto L_0x00c1
            float r12 = r12 * r14
            goto L_0x00c3
        L_0x00c1:
            r12 = 1112014848(0x42480000, float:50.0)
        L_0x00c3:
            r16 = -100
            int r15 = r11.i
            if (r15 == r6) goto L_0x00d0
            if (r15 == r8) goto L_0x00cd
            r15 = 0
            goto L_0x00d2
        L_0x00cd:
            r15 = -100
            goto L_0x00d2
        L_0x00d0:
            r15 = -50
        L_0x00d2:
            r17 = 0
            r18 = 1065353216(0x3f800000, float:1.0)
            java.lang.String r2 = "%.2f%%"
            int r10 = r11.p
            float r4 = r11.e
            int r20 = (r4 > r13 ? 1 : (r4 == r13 ? 0 : -1))
            if (r20 == 0) goto L_0x0144
            int r13 = r11.f
            if (r13 == r6) goto L_0x0112
            java.lang.Object[] r13 = new java.lang.Object[r6]
            float r4 = r4 * r14
            java.lang.Float r4 = java.lang.Float.valueOf(r4)
            r13[r5] = r4
            java.lang.String r4 = com.google.android.exoplayer2.util.Util.formatInvariant(r2, r13)
            int r13 = r11.g
            if (r10 != r6) goto L_0x0105
            if (r13 == r6) goto L_0x00ff
            if (r13 == r8) goto L_0x00fc
            r13 = 0
            goto L_0x0101
        L_0x00fc:
            r13 = -100
            goto L_0x0101
        L_0x00ff:
            r13 = -50
        L_0x0101:
            int r13 = -r13
            r16 = r13
            goto L_0x010e
        L_0x0105:
            if (r13 == r6) goto L_0x010c
            if (r13 == r8) goto L_0x010e
            r16 = 0
            goto L_0x010e
        L_0x010c:
            r16 = -50
        L_0x010e:
            r19 = 1067030938(0x3f99999a, float:1.2)
            goto L_0x0159
        L_0x0112:
            java.lang.String r13 = "%.2fem"
            int r16 = (r4 > r17 ? 1 : (r4 == r17 ? 0 : -1))
            if (r16 < 0) goto L_0x012c
            java.lang.Object[] r8 = new java.lang.Object[r6]
            r19 = 1067030938(0x3f99999a, float:1.2)
            float r4 = r4 * r19
            java.lang.Float r4 = java.lang.Float.valueOf(r4)
            r8[r5] = r4
            java.lang.String r4 = com.google.android.exoplayer2.util.Util.formatInvariant(r13, r8)
            r16 = 0
            goto L_0x0159
        L_0x012c:
            r19 = 1067030938(0x3f99999a, float:1.2)
            java.lang.Object[] r8 = new java.lang.Object[r6]
            float r4 = -r4
            float r4 = r4 - r18
            float r4 = r4 * r19
            java.lang.Float r4 = java.lang.Float.valueOf(r4)
            r8[r5] = r4
            java.lang.String r4 = com.google.android.exoplayer2.util.Util.formatInvariant(r13, r8)
            r8 = 1
            r16 = 0
            goto L_0x015a
        L_0x0144:
            r19 = 1067030938(0x3f99999a, float:1.2)
            java.lang.Object[] r4 = new java.lang.Object[r6]
            float r8 = r0.k
            float r18 = r18 - r8
            float r18 = r18 * r14
            java.lang.Float r8 = java.lang.Float.valueOf(r18)
            r4[r5] = r8
            java.lang.String r4 = com.google.android.exoplayer2.util.Util.formatInvariant(r2, r4)
        L_0x0159:
            r8 = 0
        L_0x015a:
            float r13 = r11.j
            r18 = -8388609(0xffffffffff7fffff, float:-3.4028235E38)
            int r18 = (r13 > r18 ? 1 : (r13 == r18 ? 0 : -1))
            if (r18 == 0) goto L_0x0173
            java.lang.Object[] r5 = new java.lang.Object[r6]
            float r13 = r13 * r14
            java.lang.Float r13 = java.lang.Float.valueOf(r13)
            r14 = 0
            r5[r14] = r13
            java.lang.String r2 = com.google.android.exoplayer2.util.Util.formatInvariant(r2, r5)
            goto L_0x0175
        L_0x0173:
            java.lang.String r2 = "fit-content"
        L_0x0175:
            java.lang.String r5 = "end"
            java.lang.String r13 = "start"
            java.lang.String r14 = "center"
            android.text.Layout$Alignment r6 = r11.b
            if (r6 != 0) goto L_0x0184
            r21 = r5
            r6 = r14
            r5 = 2
            goto L_0x019b
        L_0x0184:
            int[] r21 = com.google.android.exoplayer2.ui.c.b.a
            int r6 = r6.ordinal()
            r6 = r21[r6]
            r21 = r5
            r5 = 1
            if (r6 == r5) goto L_0x0199
            r5 = 2
            if (r6 == r5) goto L_0x0196
            r6 = r14
            goto L_0x019b
        L_0x0196:
            r6 = r21
            goto L_0x019b
        L_0x0199:
            r5 = 2
            r6 = r13
        L_0x019b:
            r22 = r13
            r13 = 1
            if (r10 == r13) goto L_0x01a8
            if (r10 == r5) goto L_0x01a5
            java.lang.String r5 = "horizontal-tb"
            goto L_0x01aa
        L_0x01a5:
            java.lang.String r5 = "vertical-lr"
            goto L_0x01aa
        L_0x01a8:
            java.lang.String r5 = "vertical-rl"
        L_0x01aa:
            int r13 = r11.n
            r23 = r14
            float r14 = r11.o
            java.lang.String r13 = r0.a(r14, r13)
            boolean r14 = r11.l
            if (r14 == 0) goto L_0x01bb
            int r14 = r11.m
            goto L_0x01bf
        L_0x01bb:
            com.google.android.exoplayer2.ui.CaptionStyleCompat r14 = r0.h
            int r14 = r14.c
        L_0x01bf:
            java.lang.String r14 = defpackage.r3.toCssRgba(r14)
            java.lang.String r24 = "left"
            java.lang.String r25 = "top"
            r26 = r15
            r15 = 1
            if (r10 == r15) goto L_0x01d8
            r15 = 2
            if (r10 == r15) goto L_0x01d5
            if (r8 == 0) goto L_0x01d3
            java.lang.String r25 = "bottom"
        L_0x01d3:
            r8 = 2
            goto L_0x01e4
        L_0x01d5:
            if (r8 == 0) goto L_0x01dd
            goto L_0x01db
        L_0x01d8:
            if (r8 == 0) goto L_0x01db
            goto L_0x01dd
        L_0x01db:
            java.lang.String r24 = "right"
        L_0x01dd:
            r8 = 2
            r30 = r25
            r25 = r24
            r24 = r30
        L_0x01e4:
            if (r10 == r8) goto L_0x01ef
            r8 = 1
            if (r10 != r8) goto L_0x01ea
            goto L_0x01ef
        L_0x01ea:
            java.lang.String r8 = "width"
            r15 = r26
            goto L_0x01f5
        L_0x01ef:
            java.lang.String r8 = "height"
            r15 = r16
            r16 = r26
        L_0x01f5:
            android.content.Context r26 = r31.getContext()
            android.content.res.Resources r26 = r26.getResources()
            android.util.DisplayMetrics r0 = r26.getDisplayMetrics()
            float r0 = r0.density
            r26 = r7
            java.lang.CharSequence r7 = r11.a
            com.google.android.exoplayer2.ui.SpannedToHtmlConverter$HtmlAndCss r0 = com.google.android.exoplayer2.ui.SpannedToHtmlConverter.convert(r7, r0)
            java.util.Set r7 = r3.keySet()
            java.util.Iterator r7 = r7.iterator()
        L_0x0213:
            boolean r27 = r7.hasNext()
            if (r27 == 0) goto L_0x024b
            java.lang.Object r27 = r7.next()
            r28 = r7
            r7 = r27
            java.lang.String r7 = (java.lang.String) r7
            java.lang.Object r27 = r3.get(r7)
            r29 = r0
            r0 = r27
            java.lang.String r0 = (java.lang.String) r0
            java.lang.Object r0 = r3.put(r7, r0)
            java.lang.String r0 = (java.lang.String) r0
            if (r0 == 0) goto L_0x0242
            java.lang.Object r7 = r3.get(r7)
            boolean r0 = r0.equals(r7)
            if (r0 == 0) goto L_0x0240
            goto L_0x0242
        L_0x0240:
            r0 = 0
            goto L_0x0243
        L_0x0242:
            r0 = 1
        L_0x0243:
            com.google.android.exoplayer2.util.Assertions.checkState(r0)
            r7 = r28
            r0 = r29
            goto L_0x0213
        L_0x024b:
            r29 = r0
            r0 = 14
            java.lang.Object[] r0 = new java.lang.Object[r0]
            java.lang.Integer r7 = java.lang.Integer.valueOf(r9)
            r18 = 0
            r0[r18] = r7
            r7 = 1
            r0[r7] = r24
            java.lang.Float r7 = java.lang.Float.valueOf(r12)
            r12 = 2
            r0[r12] = r7
            r7 = 3
            r0[r7] = r25
            r12 = 4
            r0[r12] = r4
            r4 = 5
            r0[r4] = r8
            r4 = 6
            r0[r4] = r2
            r2 = 7
            r0[r2] = r6
            r2 = 8
            r0[r2] = r5
            r2 = 9
            r0[r2] = r13
            r2 = 10
            r0[r2] = r14
            r2 = 11
            java.lang.Integer r4 = java.lang.Integer.valueOf(r15)
            r0[r2] = r4
            r2 = 12
            java.lang.Integer r4 = java.lang.Integer.valueOf(r16)
            r0[r2] = r4
            float r2 = r11.q
            int r4 = (r2 > r17 ? 1 : (r2 == r17 ? 0 : -1))
            if (r4 == 0) goto L_0x02b2
            r4 = 2
            r5 = 1
            if (r10 == r4) goto L_0x029e
            if (r10 != r5) goto L_0x029b
            goto L_0x029e
        L_0x029b:
            java.lang.String r6 = "skewX"
            goto L_0x02a0
        L_0x029e:
            java.lang.String r6 = "skewY"
        L_0x02a0:
            java.lang.Object[] r8 = new java.lang.Object[r4]
            r4 = 0
            r8[r4] = r6
            java.lang.Float r2 = java.lang.Float.valueOf(r2)
            r8[r5] = r2
            java.lang.String r2 = "%s(%.2fdeg)"
            java.lang.String r2 = com.google.android.exoplayer2.util.Util.formatInvariant(r2, r8)
            goto L_0x02b5
        L_0x02b2:
            r5 = 1
            java.lang.String r2 = ""
        L_0x02b5:
            r4 = 13
            r0[r4] = r2
            java.lang.String r2 = "<div style='position:absolute;z-index:%s;%s:%.2f%%;%s:%s;%s:%s;text-align:%s;writing-mode:%s;font-size:%s;background-color:%s;transform:translate(%s%%,%s%%)%s;'>"
            java.lang.String r0 = com.google.android.exoplayer2.util.Util.formatInvariant(r2, r0)
            r1.append(r0)
            java.lang.Object[] r0 = new java.lang.Object[r5]
            r2 = 0
            r0[r2] = r26
            java.lang.String r2 = "<span class='%s'>"
            java.lang.String r0 = com.google.android.exoplayer2.util.Util.formatInvariant(r2, r0)
            r1.append(r0)
            android.text.Layout$Alignment r0 = r11.c
            if (r0 == 0) goto L_0x0304
            java.lang.Object[] r2 = new java.lang.Object[r5]
            int[] r4 = com.google.android.exoplayer2.ui.c.b.a
            int r0 = r0.ordinal()
            r0 = r4[r0]
            r4 = 2
            if (r0 == r5) goto L_0x02e9
            if (r0 == r4) goto L_0x02e6
            r5 = r23
            goto L_0x02eb
        L_0x02e6:
            r5 = r21
            goto L_0x02eb
        L_0x02e9:
            r5 = r22
        L_0x02eb:
            r0 = 0
            r2[r0] = r5
            java.lang.String r0 = "<span style='display:inline-block; text-align:%s;'>"
            java.lang.String r0 = com.google.android.exoplayer2.util.Util.formatInvariant(r0, r2)
            r1.append(r0)
            r0 = r29
            java.lang.String r0 = r0.a
            r1.append(r0)
            java.lang.String r0 = "</span>"
            r1.append(r0)
            goto L_0x030c
        L_0x0304:
            r0 = r29
            r4 = 2
            java.lang.String r0 = r0.a
            r1.append(r0)
        L_0x030c:
            java.lang.String r0 = "</span></div>"
            r1.append(r0)
            int r9 = r9 + 1
            r7 = r26
            r2 = 4
            r4 = 1067030938(0x3f99999a, float:1.2)
            r5 = 0
            r6 = 1
            r8 = 2
            r10 = 3
            r0 = r31
            goto L_0x00a3
        L_0x0321:
            java.lang.String r0 = "</div></body></html>"
            r1.append(r0)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r2 = "<html><head><style>"
            r0.<init>(r2)
            java.util.Set r2 = r3.keySet()
            java.util.Iterator r2 = r2.iterator()
        L_0x0335:
            boolean r4 = r2.hasNext()
            if (r4 == 0) goto L_0x0358
            java.lang.Object r4 = r2.next()
            java.lang.String r4 = (java.lang.String) r4
            r0.append(r4)
            java.lang.String r5 = "{"
            r0.append(r5)
            java.lang.Object r4 = r3.get(r4)
            java.lang.String r4 = (java.lang.String) r4
            r0.append(r4)
            java.lang.String r4 = "}"
            r0.append(r4)
            goto L_0x0335
        L_0x0358:
            java.lang.String r2 = "</style></head>"
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            r2 = 0
            r1.insert(r2, r0)
            java.lang.String r0 = r1.toString()
            java.nio.charset.Charset r1 = com.google.common.base.Charsets.c
            byte[] r0 = r0.getBytes(r1)
            r1 = 1
            java.lang.String r0 = android.util.Base64.encodeToString(r0, r1)
            java.lang.String r1 = "text/html"
            java.lang.String r2 = "base64"
            r3 = r31
            com.google.android.exoplayer2.ui.c$a r4 = r3.f
            r4.loadData(r0, r1, r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ui.c.b():void");
    }

    public void destroy() {
        this.f.destroy();
    }

    public final void onLayout(boolean z, int i2, int i3, int i4, int i5) {
        super.onLayout(z, i2, i3, i4, i5);
        if (z && !this.g.isEmpty()) {
            b();
        }
    }

    public void update(List<Cue> list, CaptionStyleCompat captionStyleCompat, float f2, int i2, float f3) {
        this.h = captionStyleCompat;
        this.i = f2;
        this.j = i2;
        this.k = f3;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (int i3 = 0; i3 < list.size(); i3++) {
            Cue cue = list.get(i3);
            if (cue.d != null) {
                arrayList.add(cue);
            } else {
                arrayList2.add(cue);
            }
        }
        if (!this.g.isEmpty() || !arrayList2.isEmpty()) {
            this.g = arrayList2;
            b();
        }
        this.c.update(arrayList, captionStyleCompat, f2, i2, f3);
        invalidate();
    }

    public c(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.g = Collections.emptyList();
        this.h = CaptionStyleCompat.g;
        this.i = 0.0533f;
        this.j = 0;
        this.k = 0.08f;
        a aVar = new a(context, attributeSet);
        this.c = aVar;
        a aVar2 = new a(context, attributeSet);
        this.f = aVar2;
        aVar2.setBackgroundColor(0);
        addView(aVar);
        addView(aVar2);
    }
}
