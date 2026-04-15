package com.google.android.exoplayer2.ui;

import android.text.Html;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Pattern;

public final class SpannedToHtmlConverter {
    public static final Pattern a = Pattern.compile("(&#13;)?&#10;");

    public static class HtmlAndCss {
        public final String a;
        public final Map<String, String> b;

        public HtmlAndCss() {
            throw null;
        }

        public HtmlAndCss(String str, Map map) {
            this.a = str;
            this.b = map;
        }
    }

    public static final class a {
        public static final db e = new db(10);
        public static final db f = new db(11);
        public final int a;
        public final int b;
        public final String c;
        public final String d;

        public a(int i, int i2, String str, String str2) {
            this.a = i;
            this.b = i2;
            this.c = str;
            this.d = str2;
        }
    }

    public static final class b {
        public final ArrayList a = new ArrayList();
        public final ArrayList b = new ArrayList();
    }

    public static String a(CharSequence charSequence) {
        return a.matcher(Html.escapeHtml(charSequence)).replaceAll("<br>");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:103:0x0216, code lost:
        if (((android.text.style.TypefaceSpan) r8).getFamily() != null) goto L_0x0267;
     */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x0273  */
    /* JADX WARNING: Removed duplicated region for block: B:149:0x02a5 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.android.exoplayer2.ui.SpannedToHtmlConverter.HtmlAndCss convert(@androidx.annotation.Nullable java.lang.CharSequence r16, float r17) {
        /*
            r0 = r16
            if (r0 != 0) goto L_0x0010
            com.google.android.exoplayer2.ui.SpannedToHtmlConverter$HtmlAndCss r0 = new com.google.android.exoplayer2.ui.SpannedToHtmlConverter$HtmlAndCss
            java.lang.String r1 = ""
            com.google.common.collect.ImmutableMap r2 = com.google.common.collect.ImmutableMap.of()
            r0.<init>(r1, r2)
            return r0
        L_0x0010:
            boolean r1 = r0 instanceof android.text.Spanned
            if (r1 != 0) goto L_0x0022
            com.google.android.exoplayer2.ui.SpannedToHtmlConverter$HtmlAndCss r1 = new com.google.android.exoplayer2.ui.SpannedToHtmlConverter$HtmlAndCss
            java.lang.String r0 = a(r16)
            com.google.common.collect.ImmutableMap r2 = com.google.common.collect.ImmutableMap.of()
            r1.<init>(r0, r2)
            return r1
        L_0x0022:
            android.text.Spanned r0 = (android.text.Spanned) r0
            java.util.HashSet r1 = new java.util.HashSet
            r1.<init>()
            int r2 = r0.length()
            java.lang.Class<android.text.style.BackgroundColorSpan> r3 = android.text.style.BackgroundColorSpan.class
            r4 = 0
            java.lang.Object[] r2 = r0.getSpans(r4, r2, r3)
            android.text.style.BackgroundColorSpan[] r2 = (android.text.style.BackgroundColorSpan[]) r2
            int r3 = r2.length
            r5 = 0
        L_0x0038:
            if (r5 >= r3) goto L_0x004a
            r6 = r2[r5]
            int r6 = r6.getBackgroundColor()
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)
            r1.add(r6)
            int r5 = r5 + 1
            goto L_0x0038
        L_0x004a:
            java.util.HashMap r2 = new java.util.HashMap
            r2.<init>()
            java.util.Iterator r1 = r1.iterator()
        L_0x0053:
            boolean r3 = r1.hasNext()
            r5 = 1
            if (r3 == 0) goto L_0x008d
            java.lang.Object r3 = r1.next()
            java.lang.Integer r3 = (java.lang.Integer) r3
            int r3 = r3.intValue()
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r7 = 14
            r6.<init>(r7)
            java.lang.String r7 = "bg_"
            r6.append(r7)
            r6.append(r3)
            java.lang.String r6 = r6.toString()
            java.lang.String r6 = defpackage.r3.cssAllClassDescendantsSelector(r6)
            java.lang.Object[] r5 = new java.lang.Object[r5]
            java.lang.String r3 = defpackage.r3.toCssRgba(r3)
            r5[r4] = r3
            java.lang.String r3 = "background-color:%s;"
            java.lang.String r3 = com.google.android.exoplayer2.util.Util.formatInvariant(r3, r5)
            r2.put(r6, r3)
            goto L_0x0053
        L_0x008d:
            android.util.SparseArray r1 = new android.util.SparseArray
            r1.<init>()
            int r3 = r0.length()
            java.lang.Class<java.lang.Object> r6 = java.lang.Object.class
            java.lang.Object[] r3 = r0.getSpans(r4, r3, r6)
            int r6 = r3.length
            r7 = 0
        L_0x009e:
            if (r7 >= r6) goto L_0x02a9
            r8 = r3[r7]
            boolean r9 = r8 instanceof android.text.style.StrikethroughSpan
            r10 = 2
            r11 = 3
            if (r9 == 0) goto L_0x00ac
            java.lang.String r12 = "<span style='text-decoration:line-through;'>"
            goto L_0x01f0
        L_0x00ac:
            boolean r12 = r8 instanceof android.text.style.ForegroundColorSpan
            if (r12 == 0) goto L_0x00c7
            r12 = r8
            android.text.style.ForegroundColorSpan r12 = (android.text.style.ForegroundColorSpan) r12
            java.lang.Object[] r13 = new java.lang.Object[r5]
            int r12 = r12.getForegroundColor()
            java.lang.String r12 = defpackage.r3.toCssRgba(r12)
            r13[r4] = r12
            java.lang.String r12 = "<span style='color:%s;'>"
            java.lang.String r12 = com.google.android.exoplayer2.util.Util.formatInvariant(r12, r13)
            goto L_0x01f0
        L_0x00c7:
            boolean r12 = r8 instanceof android.text.style.BackgroundColorSpan
            if (r12 == 0) goto L_0x00e2
            r12 = r8
            android.text.style.BackgroundColorSpan r12 = (android.text.style.BackgroundColorSpan) r12
            java.lang.Object[] r13 = new java.lang.Object[r5]
            int r12 = r12.getBackgroundColor()
            java.lang.Integer r12 = java.lang.Integer.valueOf(r12)
            r13[r4] = r12
            java.lang.String r12 = "<span class='bg_%s'>"
            java.lang.String r12 = com.google.android.exoplayer2.util.Util.formatInvariant(r12, r13)
            goto L_0x01f0
        L_0x00e2:
            boolean r12 = r8 instanceof com.google.android.exoplayer2.text.span.HorizontalTextInVerticalContextSpan
            if (r12 == 0) goto L_0x00ea
            java.lang.String r12 = "<span style='text-combine-upright:all;'>"
            goto L_0x01f0
        L_0x00ea:
            boolean r12 = r8 instanceof android.text.style.AbsoluteSizeSpan
            if (r12 == 0) goto L_0x0114
            r12 = r8
            android.text.style.AbsoluteSizeSpan r12 = (android.text.style.AbsoluteSizeSpan) r12
            boolean r13 = r12.getDip()
            if (r13 == 0) goto L_0x00fd
            int r12 = r12.getSize()
            float r12 = (float) r12
            goto L_0x0104
        L_0x00fd:
            int r12 = r12.getSize()
            float r12 = (float) r12
            float r12 = r12 / r17
        L_0x0104:
            java.lang.Object[] r13 = new java.lang.Object[r5]
            java.lang.Float r12 = java.lang.Float.valueOf(r12)
            r13[r4] = r12
            java.lang.String r12 = "<span style='font-size:%.2fpx;'>"
            java.lang.String r12 = com.google.android.exoplayer2.util.Util.formatInvariant(r12, r13)
            goto L_0x01f0
        L_0x0114:
            boolean r12 = r8 instanceof android.text.style.RelativeSizeSpan
            if (r12 == 0) goto L_0x0133
            java.lang.Object[] r12 = new java.lang.Object[r5]
            r13 = r8
            android.text.style.RelativeSizeSpan r13 = (android.text.style.RelativeSizeSpan) r13
            float r13 = r13.getSizeChange()
            r14 = 1120403456(0x42c80000, float:100.0)
            float r13 = r13 * r14
            java.lang.Float r13 = java.lang.Float.valueOf(r13)
            r12[r4] = r13
            java.lang.String r13 = "<span style='font-size:%.2f%%;'>"
            java.lang.String r12 = com.google.android.exoplayer2.util.Util.formatInvariant(r13, r12)
            goto L_0x01f0
        L_0x0133:
            boolean r12 = r8 instanceof android.text.style.TypefaceSpan
            if (r12 == 0) goto L_0x014c
            r12 = r8
            android.text.style.TypefaceSpan r12 = (android.text.style.TypefaceSpan) r12
            java.lang.String r12 = r12.getFamily()
            if (r12 == 0) goto L_0x01ef
            java.lang.Object[] r13 = new java.lang.Object[r5]
            r13[r4] = r12
            java.lang.String r12 = "<span style='font-family:\"%s\";'>"
            java.lang.String r12 = com.google.android.exoplayer2.util.Util.formatInvariant(r12, r13)
            goto L_0x01f0
        L_0x014c:
            boolean r12 = r8 instanceof android.text.style.StyleSpan
            if (r12 == 0) goto L_0x016b
            r12 = r8
            android.text.style.StyleSpan r12 = (android.text.style.StyleSpan) r12
            int r12 = r12.getStyle()
            if (r12 == r5) goto L_0x0167
            if (r12 == r10) goto L_0x0163
            if (r12 == r11) goto L_0x015f
            goto L_0x01ef
        L_0x015f:
            java.lang.String r12 = "<b><i>"
            goto L_0x01f0
        L_0x0163:
            java.lang.String r12 = "<i>"
            goto L_0x01f0
        L_0x0167:
            java.lang.String r12 = "<b>"
            goto L_0x01f0
        L_0x016b:
            boolean r12 = r8 instanceof com.google.android.exoplayer2.text.span.RubySpan
            if (r12 == 0) goto L_0x0189
            r12 = r8
            com.google.android.exoplayer2.text.span.RubySpan r12 = (com.google.android.exoplayer2.text.span.RubySpan) r12
            int r12 = r12.b
            r13 = -1
            if (r12 == r13) goto L_0x0185
            if (r12 == r5) goto L_0x0181
            if (r12 == r10) goto L_0x017d
            goto L_0x01ef
        L_0x017d:
            java.lang.String r12 = "<ruby style='ruby-position:under;'>"
            goto L_0x01f0
        L_0x0181:
            java.lang.String r12 = "<ruby style='ruby-position:over;'>"
            goto L_0x01f0
        L_0x0185:
            java.lang.String r12 = "<ruby style='ruby-position:unset;'>"
            goto L_0x01f0
        L_0x0189:
            boolean r12 = r8 instanceof android.text.style.UnderlineSpan
            if (r12 == 0) goto L_0x0190
            java.lang.String r12 = "<u>"
            goto L_0x01f0
        L_0x0190:
            boolean r12 = r8 instanceof com.google.android.exoplayer2.text.span.TextEmphasisSpan
            if (r12 == 0) goto L_0x01ef
            r12 = r8
            com.google.android.exoplayer2.text.span.TextEmphasisSpan r12 = (com.google.android.exoplayer2.text.span.TextEmphasisSpan) r12
            int r13 = r12.a
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            int r15 = r12.b
            if (r15 == r5) goto L_0x01ab
            if (r15 == r10) goto L_0x01a5
            goto L_0x01b0
        L_0x01a5:
            java.lang.String r15 = "open "
            r14.append(r15)
            goto L_0x01b0
        L_0x01ab:
            java.lang.String r15 = "filled "
            r14.append(r15)
        L_0x01b0:
            if (r13 == 0) goto L_0x01d0
            if (r13 == r5) goto L_0x01ca
            if (r13 == r10) goto L_0x01c4
            if (r13 == r11) goto L_0x01be
            java.lang.String r13 = "unset"
            r14.append(r13)
            goto L_0x01d5
        L_0x01be:
            java.lang.String r13 = "sesame"
            r14.append(r13)
            goto L_0x01d5
        L_0x01c4:
            java.lang.String r13 = "dot"
            r14.append(r13)
            goto L_0x01d5
        L_0x01ca:
            java.lang.String r13 = "circle"
            r14.append(r13)
            goto L_0x01d5
        L_0x01d0:
            java.lang.String r13 = "none"
            r14.append(r13)
        L_0x01d5:
            java.lang.String r13 = r14.toString()
            int r12 = r12.c
            if (r12 == r10) goto L_0x01e0
            java.lang.String r12 = "over right"
            goto L_0x01e2
        L_0x01e0:
            java.lang.String r12 = "under left"
        L_0x01e2:
            java.lang.Object[] r14 = new java.lang.Object[r10]
            r14[r4] = r13
            r14[r5] = r12
            java.lang.String r12 = "<span style='-webkit-text-emphasis-style:%1$s;text-emphasis-style:%1$s;-webkit-text-emphasis-position:%2$s;text-emphasis-position:%2$s;display:inline-block;'>"
            java.lang.String r12 = com.google.android.exoplayer2.util.Util.formatInvariant(r12, r14)
            goto L_0x01f0
        L_0x01ef:
            r12 = 0
        L_0x01f0:
            if (r9 != 0) goto L_0x0267
            boolean r9 = r8 instanceof android.text.style.ForegroundColorSpan
            if (r9 != 0) goto L_0x0267
            boolean r9 = r8 instanceof android.text.style.BackgroundColorSpan
            if (r9 != 0) goto L_0x0267
            boolean r9 = r8 instanceof com.google.android.exoplayer2.text.span.HorizontalTextInVerticalContextSpan
            if (r9 != 0) goto L_0x0267
            boolean r9 = r8 instanceof android.text.style.AbsoluteSizeSpan
            if (r9 != 0) goto L_0x0267
            boolean r9 = r8 instanceof android.text.style.RelativeSizeSpan
            if (r9 != 0) goto L_0x0267
            boolean r9 = r8 instanceof com.google.android.exoplayer2.text.span.TextEmphasisSpan
            if (r9 == 0) goto L_0x020b
            goto L_0x0267
        L_0x020b:
            boolean r9 = r8 instanceof android.text.style.TypefaceSpan
            if (r9 == 0) goto L_0x0219
            r9 = r8
            android.text.style.TypefaceSpan r9 = (android.text.style.TypefaceSpan) r9
            java.lang.String r9 = r9.getFamily()
            if (r9 == 0) goto L_0x0265
            goto L_0x0267
        L_0x0219:
            boolean r9 = r8 instanceof android.text.style.StyleSpan
            if (r9 == 0) goto L_0x0234
            r9 = r8
            android.text.style.StyleSpan r9 = (android.text.style.StyleSpan) r9
            int r9 = r9.getStyle()
            if (r9 == r5) goto L_0x0231
            if (r9 == r10) goto L_0x022e
            if (r9 == r11) goto L_0x022b
            goto L_0x0265
        L_0x022b:
            java.lang.String r9 = "</i></b>"
            goto L_0x0269
        L_0x022e:
            java.lang.String r9 = "</i>"
            goto L_0x0269
        L_0x0231:
            java.lang.String r9 = "</b>"
            goto L_0x0269
        L_0x0234:
            boolean r9 = r8 instanceof com.google.android.exoplayer2.text.span.RubySpan
            if (r9 == 0) goto L_0x025e
            r9 = r8
            com.google.android.exoplayer2.text.span.RubySpan r9 = (com.google.android.exoplayer2.text.span.RubySpan) r9
            java.lang.String r9 = r9.a
            java.lang.String r9 = a(r9)
            r10 = 16
            int r10 = defpackage.y2.c(r9, r10)
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>(r10)
            java.lang.String r10 = "<rt>"
            r11.append(r10)
            r11.append(r9)
            java.lang.String r9 = "</rt></ruby>"
            r11.append(r9)
            java.lang.String r9 = r11.toString()
            goto L_0x0269
        L_0x025e:
            boolean r9 = r8 instanceof android.text.style.UnderlineSpan
            if (r9 == 0) goto L_0x0265
            java.lang.String r9 = "</u>"
            goto L_0x0269
        L_0x0265:
            r9 = 0
            goto L_0x0269
        L_0x0267:
            java.lang.String r9 = "</span>"
        L_0x0269:
            int r10 = r0.getSpanStart(r8)
            int r8 = r0.getSpanEnd(r8)
            if (r12 == 0) goto L_0x02a5
            com.google.android.exoplayer2.util.Assertions.checkNotNull(r9)
            com.google.android.exoplayer2.ui.SpannedToHtmlConverter$a r11 = new com.google.android.exoplayer2.ui.SpannedToHtmlConverter$a
            r11.<init>(r10, r8, r12, r9)
            java.lang.Object r9 = r1.get(r10)
            com.google.android.exoplayer2.ui.SpannedToHtmlConverter$b r9 = (com.google.android.exoplayer2.ui.SpannedToHtmlConverter.b) r9
            if (r9 != 0) goto L_0x028b
            com.google.android.exoplayer2.ui.SpannedToHtmlConverter$b r9 = new com.google.android.exoplayer2.ui.SpannedToHtmlConverter$b
            r9.<init>()
            r1.put(r10, r9)
        L_0x028b:
            java.util.ArrayList r9 = r9.a
            r9.add(r11)
            java.lang.Object r9 = r1.get(r8)
            com.google.android.exoplayer2.ui.SpannedToHtmlConverter$b r9 = (com.google.android.exoplayer2.ui.SpannedToHtmlConverter.b) r9
            if (r9 != 0) goto L_0x02a0
            com.google.android.exoplayer2.ui.SpannedToHtmlConverter$b r9 = new com.google.android.exoplayer2.ui.SpannedToHtmlConverter$b
            r9.<init>()
            r1.put(r8, r9)
        L_0x02a0:
            java.util.ArrayList r8 = r9.b
            r8.add(r11)
        L_0x02a5:
            int r7 = r7 + 1
            goto L_0x009e
        L_0x02a9:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            int r5 = r0.length()
            r3.<init>(r5)
            r5 = 0
        L_0x02b3:
            int r6 = r1.size()
            if (r4 >= r6) goto L_0x030e
            int r6 = r1.keyAt(r4)
            java.lang.CharSequence r5 = r0.subSequence(r5, r6)
            java.lang.String r5 = a(r5)
            r3.append(r5)
            java.lang.Object r5 = r1.get(r6)
            com.google.android.exoplayer2.ui.SpannedToHtmlConverter$b r5 = (com.google.android.exoplayer2.ui.SpannedToHtmlConverter.b) r5
            java.util.ArrayList r7 = r5.b
            db r8 = com.google.android.exoplayer2.ui.SpannedToHtmlConverter.a.f
            java.util.Collections.sort(r7, r8)
            java.util.ArrayList r7 = r5.b
            java.util.Iterator r7 = r7.iterator()
        L_0x02db:
            boolean r8 = r7.hasNext()
            if (r8 == 0) goto L_0x02ed
            java.lang.Object r8 = r7.next()
            com.google.android.exoplayer2.ui.SpannedToHtmlConverter$a r8 = (com.google.android.exoplayer2.ui.SpannedToHtmlConverter.a) r8
            java.lang.String r8 = r8.d
            r3.append(r8)
            goto L_0x02db
        L_0x02ed:
            java.util.ArrayList r5 = r5.a
            db r7 = com.google.android.exoplayer2.ui.SpannedToHtmlConverter.a.e
            java.util.Collections.sort(r5, r7)
            java.util.Iterator r5 = r5.iterator()
        L_0x02f8:
            boolean r7 = r5.hasNext()
            if (r7 == 0) goto L_0x030a
            java.lang.Object r7 = r5.next()
            com.google.android.exoplayer2.ui.SpannedToHtmlConverter$a r7 = (com.google.android.exoplayer2.ui.SpannedToHtmlConverter.a) r7
            java.lang.String r7 = r7.c
            r3.append(r7)
            goto L_0x02f8
        L_0x030a:
            int r4 = r4 + 1
            r5 = r6
            goto L_0x02b3
        L_0x030e:
            int r1 = r0.length()
            java.lang.CharSequence r0 = r0.subSequence(r5, r1)
            java.lang.String r0 = a(r0)
            r3.append(r0)
            com.google.android.exoplayer2.ui.SpannedToHtmlConverter$HtmlAndCss r0 = new com.google.android.exoplayer2.ui.SpannedToHtmlConverter$HtmlAndCss
            java.lang.String r1 = r3.toString()
            r0.<init>(r1, r2)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ui.SpannedToHtmlConverter.convert(java.lang.CharSequence, float):com.google.android.exoplayer2.ui.SpannedToHtmlConverter$HtmlAndCss");
    }
}
