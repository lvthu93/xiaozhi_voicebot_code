package com.google.android.exoplayer2.text.webvtt;

import android.graphics.Color;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class WebvttCueParser {
    public static final Pattern a = Pattern.compile("^(\\S+)\\s+-->\\s+(\\S+)(.*)?$");
    public static final Pattern b = Pattern.compile("(\\S+?):(\\S+)");
    public static final Map<String, Integer> c;
    public static final Map<String, Integer> d;

    public static class a {
        public static final db c = new db(5);
        public final b a;
        public final int b;

        public a(b bVar, int i) {
            this.a = bVar;
            this.b = i;
        }
    }

    public static final class b {
        public final String a;
        public final int b;
        public final String c;
        public final Set<String> d;

        public b(String str, int i, String str2, Set<String> set) {
            this.b = i;
            this.a = str;
            this.c = str2;
            this.d = set;
        }

        public static b buildStartTag(String str, int i) {
            String str2;
            String trim = str.trim();
            Assertions.checkArgument(!trim.isEmpty());
            int indexOf = trim.indexOf(" ");
            if (indexOf == -1) {
                str2 = "";
            } else {
                String trim2 = trim.substring(indexOf).trim();
                trim = trim.substring(0, indexOf);
                str2 = trim2;
            }
            String[] split = Util.split(trim, "\\.");
            String str3 = split[0];
            HashSet hashSet = new HashSet();
            for (int i2 = 1; i2 < split.length; i2++) {
                hashSet.add(split[i2]);
            }
            return new b(str3, i, str2, hashSet);
        }

        public static b buildWholeCueVirtualTag() {
            return new b("", 0, "", Collections.emptySet());
        }
    }

    public static final class c implements Comparable<c> {
        public final int c;
        public final WebvttCssStyle f;

        public c(int i, WebvttCssStyle webvttCssStyle) {
            this.c = i;
            this.f = webvttCssStyle;
        }

        public int compareTo(c cVar) {
            return Integer.compare(this.c, cVar.c);
        }
    }

    public static final class d {
        public long a = 0;
        public long b = 0;
        public CharSequence c;
        public int d = 2;
        public float e = -3.4028235E38f;
        public int f = 1;
        public int g = 0;
        public float h = -3.4028235E38f;
        public int i = Integer.MIN_VALUE;
        public float j = 1.0f;
        public int k = Integer.MIN_VALUE;

        public WebvttCueInfo build() {
            return new WebvttCueInfo(toCueBuilder().build(), this.a, this.b);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:37:0x0076, code lost:
            if (r8 == 0) goto L_0x0078;
         */
        /* JADX WARNING: Removed duplicated region for block: B:36:0x0074  */
        /* JADX WARNING: Removed duplicated region for block: B:37:0x0076  */
        /* JADX WARNING: Removed duplicated region for block: B:41:0x0090  */
        /* JADX WARNING: Removed duplicated region for block: B:49:0x00ac  */
        /* JADX WARNING: Removed duplicated region for block: B:52:0x00c0  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public com.google.android.exoplayer2.text.Cue.Builder toCueBuilder() {
            /*
                r13 = this;
                float r0 = r13.h
                r1 = 1056964608(0x3f000000, float:0.5)
                r2 = 0
                r3 = 1065353216(0x3f800000, float:1.0)
                r4 = 5
                r5 = 4
                r6 = -8388609(0xffffffffff7fffff, float:-3.4028235E38)
                int r7 = (r0 > r6 ? 1 : (r0 == r6 ? 0 : -1))
                if (r7 == 0) goto L_0x0011
                goto L_0x001e
            L_0x0011:
                int r0 = r13.d
                if (r0 == r5) goto L_0x001d
                if (r0 == r4) goto L_0x001a
                r0 = 1056964608(0x3f000000, float:0.5)
                goto L_0x001e
            L_0x001a:
                r0 = 1065353216(0x3f800000, float:1.0)
                goto L_0x001e
            L_0x001d:
                r0 = 0
            L_0x001e:
                int r7 = r13.i
                r8 = -2147483648(0xffffffff80000000, float:-0.0)
                r9 = 3
                r10 = 2
                r11 = 1
                if (r7 == r8) goto L_0x0028
                goto L_0x0037
            L_0x0028:
                int r7 = r13.d
                if (r7 == r11) goto L_0x0036
                if (r7 == r9) goto L_0x0034
                if (r7 == r5) goto L_0x0036
                if (r7 == r4) goto L_0x0034
                r7 = 1
                goto L_0x0037
            L_0x0034:
                r7 = 2
                goto L_0x0037
            L_0x0036:
                r7 = 0
            L_0x0037:
                com.google.android.exoplayer2.text.Cue$Builder r8 = new com.google.android.exoplayer2.text.Cue$Builder
                r8.<init>()
                int r12 = r13.d
                if (r12 == r11) goto L_0x0059
                if (r12 == r10) goto L_0x0056
                if (r12 == r9) goto L_0x0053
                if (r12 == r5) goto L_0x0059
                if (r12 == r4) goto L_0x0053
                r4 = 34
                java.lang.String r5 = "Unknown textAlignment: "
                java.lang.String r9 = "WebvttCueParser"
                defpackage.y2.t(r4, r5, r12, r9)
                r4 = 0
                goto L_0x005b
            L_0x0053:
                android.text.Layout$Alignment r4 = android.text.Layout.Alignment.ALIGN_OPPOSITE
                goto L_0x005b
            L_0x0056:
                android.text.Layout$Alignment r4 = android.text.Layout.Alignment.ALIGN_CENTER
                goto L_0x005b
            L_0x0059:
                android.text.Layout$Alignment r4 = android.text.Layout.Alignment.ALIGN_NORMAL
            L_0x005b:
                com.google.android.exoplayer2.text.Cue$Builder r4 = r8.setTextAlignment(r4)
                float r5 = r13.e
                int r8 = r13.f
                int r9 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
                if (r9 == 0) goto L_0x0072
                if (r8 != 0) goto L_0x0072
                int r2 = (r5 > r2 ? 1 : (r5 == r2 ? 0 : -1))
                if (r2 < 0) goto L_0x0078
                int r2 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
                if (r2 <= 0) goto L_0x0072
                goto L_0x0078
            L_0x0072:
                if (r9 == 0) goto L_0x0076
                r6 = r5
                goto L_0x007a
            L_0x0076:
                if (r8 != 0) goto L_0x007a
            L_0x0078:
                r6 = 1065353216(0x3f800000, float:1.0)
            L_0x007a:
                com.google.android.exoplayer2.text.Cue$Builder r2 = r4.setLine(r6, r8)
                int r4 = r13.g
                com.google.android.exoplayer2.text.Cue$Builder r2 = r2.setLineAnchor(r4)
                com.google.android.exoplayer2.text.Cue$Builder r2 = r2.setPosition(r0)
                com.google.android.exoplayer2.text.Cue$Builder r2 = r2.setPositionAnchor(r7)
                float r4 = r13.j
                if (r7 == 0) goto L_0x00ac
                if (r7 == r11) goto L_0x009f
                if (r7 != r10) goto L_0x0095
                goto L_0x00ae
            L_0x0095:
                java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                java.lang.String r1 = java.lang.String.valueOf(r7)
                r0.<init>(r1)
                throw r0
            L_0x009f:
                r5 = 1073741824(0x40000000, float:2.0)
                int r1 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
                if (r1 > 0) goto L_0x00a8
                float r0 = r0 * r5
                goto L_0x00ae
            L_0x00a8:
                float r3 = r3 - r0
                float r0 = r3 * r5
                goto L_0x00ae
            L_0x00ac:
                float r0 = r3 - r0
            L_0x00ae:
                float r0 = java.lang.Math.min(r4, r0)
                com.google.android.exoplayer2.text.Cue$Builder r0 = r2.setSize(r0)
                int r1 = r13.k
                com.google.android.exoplayer2.text.Cue$Builder r0 = r0.setVerticalType(r1)
                java.lang.CharSequence r1 = r13.c
                if (r1 == 0) goto L_0x00c3
                r0.setText(r1)
            L_0x00c3:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.webvtt.WebvttCueParser.d.toCueBuilder():com.google.android.exoplayer2.text.Cue$Builder");
        }
    }

    static {
        HashMap hashMap = new HashMap();
        hashMap.put("white", Integer.valueOf(Color.rgb(255, 255, 255)));
        hashMap.put("lime", Integer.valueOf(Color.rgb(0, 255, 0)));
        hashMap.put("cyan", Integer.valueOf(Color.rgb(0, 255, 255)));
        hashMap.put("red", Integer.valueOf(Color.rgb(255, 0, 0)));
        hashMap.put("yellow", Integer.valueOf(Color.rgb(255, 255, 0)));
        hashMap.put("magenta", Integer.valueOf(Color.rgb(255, 0, 255)));
        hashMap.put("blue", Integer.valueOf(Color.rgb(0, 0, 255)));
        hashMap.put("black", Integer.valueOf(Color.rgb(0, 0, 0)));
        c = Collections.unmodifiableMap(hashMap);
        HashMap hashMap2 = new HashMap();
        hashMap2.put("bg_white", Integer.valueOf(Color.rgb(255, 255, 255)));
        hashMap2.put("bg_lime", Integer.valueOf(Color.rgb(0, 255, 0)));
        hashMap2.put("bg_cyan", Integer.valueOf(Color.rgb(0, 255, 255)));
        hashMap2.put("bg_red", Integer.valueOf(Color.rgb(255, 0, 0)));
        hashMap2.put("bg_yellow", Integer.valueOf(Color.rgb(255, 255, 0)));
        hashMap2.put("bg_magenta", Integer.valueOf(Color.rgb(255, 0, 255)));
        hashMap2.put("bg_blue", Integer.valueOf(Color.rgb(0, 0, 255)));
        hashMap2.put("bg_black", Integer.valueOf(Color.rgb(0, 0, 0)));
        d = Collections.unmodifiableMap(hashMap2);
    }

    /* JADX WARNING: Removed duplicated region for block: B:43:0x0097 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0098  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x0109  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x0112  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x011b  */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x0163  */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x0177  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void a(android.text.SpannableStringBuilder r18, com.google.android.exoplayer2.text.webvtt.WebvttCueParser.b r19, @androidx.annotation.Nullable java.lang.String r20, java.util.List r21, java.util.List r22) {
        /*
            r0 = r18
            r1 = r19
            r2 = r20
            r3 = r22
            int r4 = r1.b
            int r5 = r18.length()
            java.lang.String r6 = r1.a
            r6.getClass()
            int r7 = r6.hashCode()
            r9 = -1
            r12 = 2
            if (r7 == 0) goto L_0x0087
            r13 = 105(0x69, float:1.47E-43)
            if (r7 == r13) goto L_0x007c
            r13 = 3314158(0x3291ee, float:4.644125E-39)
            if (r7 == r13) goto L_0x0071
            r13 = 3511770(0x3595da, float:4.921038E-39)
            if (r7 == r13) goto L_0x0066
            r13 = 98
            if (r7 == r13) goto L_0x005b
            r13 = 99
            if (r7 == r13) goto L_0x0050
            r13 = 117(0x75, float:1.64E-43)
            if (r7 == r13) goto L_0x0045
            r13 = 118(0x76, float:1.65E-43)
            if (r7 == r13) goto L_0x003a
            goto L_0x008f
        L_0x003a:
            java.lang.String r7 = "v"
            boolean r6 = r6.equals(r7)
            if (r6 != 0) goto L_0x0043
            goto L_0x008f
        L_0x0043:
            r6 = 5
            goto L_0x0092
        L_0x0045:
            java.lang.String r7 = "u"
            boolean r6 = r6.equals(r7)
            if (r6 != 0) goto L_0x004e
            goto L_0x008f
        L_0x004e:
            r6 = 4
            goto L_0x0092
        L_0x0050:
            java.lang.String r7 = "c"
            boolean r6 = r6.equals(r7)
            if (r6 != 0) goto L_0x0059
            goto L_0x008f
        L_0x0059:
            r6 = 2
            goto L_0x0092
        L_0x005b:
            java.lang.String r7 = "b"
            boolean r6 = r6.equals(r7)
            if (r6 != 0) goto L_0x0064
            goto L_0x008f
        L_0x0064:
            r6 = 1
            goto L_0x0092
        L_0x0066:
            java.lang.String r7 = "ruby"
            boolean r6 = r6.equals(r7)
            if (r6 != 0) goto L_0x006f
            goto L_0x008f
        L_0x006f:
            r6 = 7
            goto L_0x0092
        L_0x0071:
            java.lang.String r7 = "lang"
            boolean r6 = r6.equals(r7)
            if (r6 != 0) goto L_0x007a
            goto L_0x008f
        L_0x007a:
            r6 = 6
            goto L_0x0092
        L_0x007c:
            java.lang.String r7 = "i"
            boolean r6 = r6.equals(r7)
            if (r6 != 0) goto L_0x0085
            goto L_0x008f
        L_0x0085:
            r6 = 3
            goto L_0x0092
        L_0x0087:
            java.lang.String r7 = ""
            boolean r6 = r6.equals(r7)
            if (r6 != 0) goto L_0x0091
        L_0x008f:
            r6 = -1
            goto L_0x0092
        L_0x0091:
            r6 = 0
        L_0x0092:
            r7 = 33
            switch(r6) {
                case 0: goto L_0x016c;
                case 1: goto L_0x0163;
                case 2: goto L_0x011b;
                case 3: goto L_0x0112;
                case 4: goto L_0x0109;
                case 5: goto L_0x016c;
                case 6: goto L_0x016c;
                case 7: goto L_0x0098;
                default: goto L_0x0097;
            }
        L_0x0097:
            return
        L_0x0098:
            int r6 = c(r3, r2, r1)
            java.util.ArrayList r13 = new java.util.ArrayList
            int r14 = r21.size()
            r13.<init>(r14)
            r14 = r21
            r13.addAll(r14)
            db r14 = com.google.android.exoplayer2.text.webvtt.WebvttCueParser.a.c
            java.util.Collections.sort(r13, r14)
            int r14 = r1.b
            r15 = 0
            r16 = 0
        L_0x00b4:
            int r10 = r13.size()
            if (r15 >= r10) goto L_0x016c
            java.lang.Object r10 = r13.get(r15)
            com.google.android.exoplayer2.text.webvtt.WebvttCueParser$a r10 = (com.google.android.exoplayer2.text.webvtt.WebvttCueParser.a) r10
            com.google.android.exoplayer2.text.webvtt.WebvttCueParser$b r10 = r10.a
            java.lang.String r10 = r10.a
            java.lang.String r8 = "rt"
            boolean r8 = r8.equals(r10)
            if (r8 != 0) goto L_0x00cd
            goto L_0x0105
        L_0x00cd:
            java.lang.Object r8 = r13.get(r15)
            com.google.android.exoplayer2.text.webvtt.WebvttCueParser$a r8 = (com.google.android.exoplayer2.text.webvtt.WebvttCueParser.a) r8
            com.google.android.exoplayer2.text.webvtt.WebvttCueParser$b r10 = r8.a
            int r10 = c(r3, r2, r10)
            if (r10 == r9) goto L_0x00dc
            goto L_0x00e1
        L_0x00dc:
            if (r6 == r9) goto L_0x00e0
            r10 = r6
            goto L_0x00e1
        L_0x00e0:
            r10 = 1
        L_0x00e1:
            com.google.android.exoplayer2.text.webvtt.WebvttCueParser$b r9 = r8.a
            int r9 = r9.b
            int r9 = r9 - r16
            int r8 = r8.b
            int r8 = r8 - r16
            java.lang.CharSequence r17 = r0.subSequence(r9, r8)
            r0.delete(r9, r8)
            com.google.android.exoplayer2.text.span.RubySpan r8 = new com.google.android.exoplayer2.text.span.RubySpan
            java.lang.String r11 = r17.toString()
            r8.<init>(r11, r10)
            r0.setSpan(r8, r14, r9, r7)
            int r8 = r17.length()
            int r16 = r8 + r16
            r14 = r9
        L_0x0105:
            int r15 = r15 + 1
            r9 = -1
            goto L_0x00b4
        L_0x0109:
            android.text.style.UnderlineSpan r6 = new android.text.style.UnderlineSpan
            r6.<init>()
            r0.setSpan(r6, r4, r5, r7)
            goto L_0x016c
        L_0x0112:
            android.text.style.StyleSpan r6 = new android.text.style.StyleSpan
            r6.<init>(r12)
            r0.setSpan(r6, r4, r5, r7)
            goto L_0x016c
        L_0x011b:
            java.util.Set<java.lang.String> r6 = r1.d
            java.util.Iterator r6 = r6.iterator()
        L_0x0121:
            boolean r8 = r6.hasNext()
            if (r8 == 0) goto L_0x016c
            java.lang.Object r8 = r6.next()
            java.lang.String r8 = (java.lang.String) r8
            java.util.Map<java.lang.String, java.lang.Integer> r9 = c
            boolean r10 = r9.containsKey(r8)
            if (r10 == 0) goto L_0x0148
            java.lang.Object r8 = r9.get(r8)
            java.lang.Integer r8 = (java.lang.Integer) r8
            int r8 = r8.intValue()
            android.text.style.ForegroundColorSpan r9 = new android.text.style.ForegroundColorSpan
            r9.<init>(r8)
            r0.setSpan(r9, r4, r5, r7)
            goto L_0x0121
        L_0x0148:
            java.util.Map<java.lang.String, java.lang.Integer> r9 = d
            boolean r10 = r9.containsKey(r8)
            if (r10 == 0) goto L_0x0121
            java.lang.Object r8 = r9.get(r8)
            java.lang.Integer r8 = (java.lang.Integer) r8
            int r8 = r8.intValue()
            android.text.style.BackgroundColorSpan r9 = new android.text.style.BackgroundColorSpan
            r9.<init>(r8)
            r0.setSpan(r9, r4, r5, r7)
            goto L_0x0121
        L_0x0163:
            android.text.style.StyleSpan r6 = new android.text.style.StyleSpan
            r8 = 1
            r6.<init>(r8)
            r0.setSpan(r6, r4, r5, r7)
        L_0x016c:
            java.util.ArrayList r1 = b(r3, r2, r1)
            r10 = 0
        L_0x0171:
            int r2 = r1.size()
            if (r10 >= r2) goto L_0x0238
            java.lang.Object r2 = r1.get(r10)
            com.google.android.exoplayer2.text.webvtt.WebvttCueParser$c r2 = (com.google.android.exoplayer2.text.webvtt.WebvttCueParser.c) r2
            com.google.android.exoplayer2.text.webvtt.WebvttCssStyle r2 = r2.f
            if (r2 != 0) goto L_0x0186
            r6 = -1
            r8 = 3
            r11 = 1
            goto L_0x0234
        L_0x0186:
            int r3 = r2.getStyle()
            r6 = -1
            if (r3 == r6) goto L_0x0199
            android.text.style.StyleSpan r3 = new android.text.style.StyleSpan
            int r8 = r2.getStyle()
            r3.<init>(r8)
            com.google.android.exoplayer2.text.span.SpanUtil.addOrReplaceSpan(r0, r3, r4, r5, r7)
        L_0x0199:
            boolean r3 = r2.isLinethrough()
            if (r3 == 0) goto L_0x01a7
            android.text.style.StrikethroughSpan r3 = new android.text.style.StrikethroughSpan
            r3.<init>()
            r0.setSpan(r3, r4, r5, r7)
        L_0x01a7:
            boolean r3 = r2.isUnderline()
            if (r3 == 0) goto L_0x01b5
            android.text.style.UnderlineSpan r3 = new android.text.style.UnderlineSpan
            r3.<init>()
            r0.setSpan(r3, r4, r5, r7)
        L_0x01b5:
            boolean r3 = r2.hasFontColor()
            if (r3 == 0) goto L_0x01c7
            android.text.style.ForegroundColorSpan r3 = new android.text.style.ForegroundColorSpan
            int r8 = r2.getFontColor()
            r3.<init>(r8)
            com.google.android.exoplayer2.text.span.SpanUtil.addOrReplaceSpan(r0, r3, r4, r5, r7)
        L_0x01c7:
            boolean r3 = r2.hasBackgroundColor()
            if (r3 == 0) goto L_0x01d9
            android.text.style.BackgroundColorSpan r3 = new android.text.style.BackgroundColorSpan
            int r8 = r2.getBackgroundColor()
            r3.<init>(r8)
            com.google.android.exoplayer2.text.span.SpanUtil.addOrReplaceSpan(r0, r3, r4, r5, r7)
        L_0x01d9:
            java.lang.String r3 = r2.getFontFamily()
            if (r3 == 0) goto L_0x01eb
            android.text.style.TypefaceSpan r3 = new android.text.style.TypefaceSpan
            java.lang.String r8 = r2.getFontFamily()
            r3.<init>(r8)
            com.google.android.exoplayer2.text.span.SpanUtil.addOrReplaceSpan(r0, r3, r4, r5, r7)
        L_0x01eb:
            int r3 = r2.getFontSizeUnit()
            r8 = 1
            if (r3 == r8) goto L_0x0217
            if (r3 == r12) goto L_0x0208
            r8 = 3
            if (r3 == r8) goto L_0x01f8
            goto L_0x0215
        L_0x01f8:
            android.text.style.RelativeSizeSpan r3 = new android.text.style.RelativeSizeSpan
            float r9 = r2.getFontSize()
            r11 = 1120403456(0x42c80000, float:100.0)
            float r9 = r9 / r11
            r3.<init>(r9)
            com.google.android.exoplayer2.text.span.SpanUtil.addOrReplaceSpan(r0, r3, r4, r5, r7)
            goto L_0x0215
        L_0x0208:
            r8 = 3
            android.text.style.RelativeSizeSpan r3 = new android.text.style.RelativeSizeSpan
            float r9 = r2.getFontSize()
            r3.<init>(r9)
            com.google.android.exoplayer2.text.span.SpanUtil.addOrReplaceSpan(r0, r3, r4, r5, r7)
        L_0x0215:
            r11 = 1
            goto L_0x0226
        L_0x0217:
            r8 = 3
            android.text.style.AbsoluteSizeSpan r3 = new android.text.style.AbsoluteSizeSpan
            float r9 = r2.getFontSize()
            int r9 = (int) r9
            r11 = 1
            r3.<init>(r9, r11)
            com.google.android.exoplayer2.text.span.SpanUtil.addOrReplaceSpan(r0, r3, r4, r5, r7)
        L_0x0226:
            boolean r2 = r2.getCombineUpright()
            if (r2 == 0) goto L_0x0234
            com.google.android.exoplayer2.text.span.HorizontalTextInVerticalContextSpan r2 = new com.google.android.exoplayer2.text.span.HorizontalTextInVerticalContextSpan
            r2.<init>()
            r0.setSpan(r2, r4, r5, r7)
        L_0x0234:
            int r10 = r10 + 1
            goto L_0x0171
        L_0x0238:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.webvtt.WebvttCueParser.a(android.text.SpannableStringBuilder, com.google.android.exoplayer2.text.webvtt.WebvttCueParser$b, java.lang.String, java.util.List, java.util.List):void");
    }

    public static ArrayList b(List list, @Nullable String str, b bVar) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            WebvttCssStyle webvttCssStyle = (WebvttCssStyle) list.get(i);
            int specificityScore = webvttCssStyle.getSpecificityScore(str, bVar.a, bVar.d, bVar.c);
            if (specificityScore > 0) {
                arrayList.add(new c(specificityScore, webvttCssStyle));
            }
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    public static int c(List<WebvttCssStyle> list, @Nullable String str, b bVar) {
        ArrayList b2 = b(list, str, bVar);
        for (int i = 0; i < b2.size(); i++) {
            WebvttCssStyle webvttCssStyle = ((c) b2.get(i)).f;
            if (webvttCssStyle.getRubyPosition() != -1) {
                return webvttCssStyle.getRubyPosition();
            }
        }
        return -1;
    }

    @Nullable
    public static WebvttCueInfo d(@Nullable String str, Matcher matcher, ParsableByteArray parsableByteArray, List<WebvttCssStyle> list) {
        String str2;
        d dVar = new d();
        try {
            dVar.a = WebvttParserUtil.parseTimestampUs((String) Assertions.checkNotNull(matcher.group(1)));
            dVar.b = WebvttParserUtil.parseTimestampUs((String) Assertions.checkNotNull(matcher.group(2)));
            e((String) Assertions.checkNotNull(matcher.group(3)), dVar);
            StringBuilder sb = new StringBuilder();
            String readLine = parsableByteArray.readLine();
            while (!TextUtils.isEmpty(readLine)) {
                if (sb.length() > 0) {
                    sb.append("\n");
                }
                sb.append(readLine.trim());
                readLine = parsableByteArray.readLine();
            }
            dVar.c = f(str, list, sb.toString());
            return dVar.build();
        } catch (NumberFormatException unused) {
            String valueOf = String.valueOf(matcher.group());
            if (valueOf.length() != 0) {
                str2 = "Skipping cue with bad header: ".concat(valueOf);
            } else {
                str2 = new String("Skipping cue with bad header: ");
            }
            Log.w("WebvttCueParser", str2);
            return null;
        }
    }

    public static void e(String str, d dVar) {
        String str2;
        String str3;
        String str4;
        Matcher matcher = b.matcher(str);
        while (matcher.find()) {
            int i = 1;
            String str5 = (String) Assertions.checkNotNull(matcher.group(1));
            String str6 = (String) Assertions.checkNotNull(matcher.group(2));
            try {
                if ("line".equals(str5)) {
                    g(str6, dVar);
                } else if ("align".equals(str5)) {
                    str6.getClass();
                    char c2 = 65535;
                    switch (str6.hashCode()) {
                        case -1364013995:
                            if (str6.equals("center")) {
                                c2 = 0;
                                break;
                            }
                            break;
                        case -1074341483:
                            if (str6.equals("middle")) {
                                c2 = 1;
                                break;
                            }
                            break;
                        case 100571:
                            if (str6.equals("end")) {
                                c2 = 2;
                                break;
                            }
                            break;
                        case 3317767:
                            if (str6.equals("left")) {
                                c2 = 3;
                                break;
                            }
                            break;
                        case 108511772:
                            if (str6.equals("right")) {
                                c2 = 4;
                                break;
                            }
                            break;
                        case 109757538:
                            if (str6.equals("start")) {
                                c2 = 5;
                                break;
                            }
                            break;
                    }
                    switch (c2) {
                        case 0:
                        case 1:
                            break;
                        case 2:
                            i = 3;
                            break;
                        case 3:
                            i = 4;
                            break;
                        case 4:
                            i = 5;
                            break;
                        case 5:
                            break;
                        default:
                            if (str6.length() != 0) {
                                str4 = "Invalid alignment value: ".concat(str6);
                            } else {
                                str4 = new String("Invalid alignment value: ");
                            }
                            Log.w("WebvttCueParser", str4);
                            break;
                    }
                    i = 2;
                    dVar.d = i;
                    continue;
                } else if ("position".equals(str5)) {
                    h(str6, dVar);
                } else if ("size".equals(str5)) {
                    dVar.j = WebvttParserUtil.parsePercentage(str6);
                } else if ("vertical".equals(str5)) {
                    str6.getClass();
                    if (str6.equals("lr")) {
                        i = 2;
                    } else if (!str6.equals("rl")) {
                        if (str6.length() != 0) {
                            str3 = "Invalid 'vertical' value: ".concat(str6);
                        } else {
                            str3 = new String("Invalid 'vertical' value: ");
                        }
                        Log.w("WebvttCueParser", str3);
                        i = Integer.MIN_VALUE;
                    }
                    dVar.k = i;
                } else {
                    StringBuilder sb = new StringBuilder(String.valueOf(str5).length() + 21 + String.valueOf(str6).length());
                    sb.append("Unknown cue setting ");
                    sb.append(str5);
                    sb.append(":");
                    sb.append(str6);
                    Log.w("WebvttCueParser", sb.toString());
                }
            } catch (NumberFormatException unused) {
                String valueOf = String.valueOf(matcher.group());
                if (valueOf.length() != 0) {
                    str2 = "Skipping bad cue setting: ".concat(valueOf);
                } else {
                    str2 = new String("Skipping bad cue setting: ");
                }
                Log.w("WebvttCueParser", str2);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:71:0x0115  */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x011b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.text.SpannedString f(@androidx.annotation.Nullable java.lang.String r16, java.util.List r17, java.lang.String r18) {
        /*
            r0 = r16
            r1 = r17
            r2 = r18
            android.text.SpannableStringBuilder r3 = new android.text.SpannableStringBuilder
            r3.<init>()
            java.util.ArrayDeque r4 = new java.util.ArrayDeque
            r4.<init>()
            java.util.ArrayList r5 = new java.util.ArrayList
            r5.<init>()
            r6 = 0
            r7 = 0
        L_0x0017:
            int r8 = r18.length()
            if (r7 >= r8) goto L_0x01f4
            char r8 = r2.charAt(r7)
            r11 = -1
            r12 = 1
            r13 = 62
            r14 = 60
            r15 = 38
            if (r8 == r15) goto L_0x015a
            if (r8 == r14) goto L_0x0034
            r3.append(r8)
            int r7 = r7 + 1
            goto L_0x01f1
        L_0x0034:
            int r8 = r7 + 1
            int r14 = r18.length()
            if (r8 < r14) goto L_0x003e
            goto L_0x0118
        L_0x003e:
            char r14 = r2.charAt(r8)
            r15 = 47
            if (r14 != r15) goto L_0x0048
            r14 = 1
            goto L_0x0049
        L_0x0048:
            r14 = 0
        L_0x0049:
            int r8 = r2.indexOf(r13, r8)
            if (r8 != r11) goto L_0x0054
            int r8 = r18.length()
            goto L_0x0056
        L_0x0054:
            int r8 = r8 + 1
        L_0x0056:
            int r13 = r8 + -2
            char r9 = r2.charAt(r13)
            if (r9 != r15) goto L_0x0060
            r9 = 1
            goto L_0x0061
        L_0x0060:
            r9 = 0
        L_0x0061:
            if (r14 == 0) goto L_0x0065
            r15 = 2
            goto L_0x0066
        L_0x0065:
            r15 = 1
        L_0x0066:
            int r7 = r7 + r15
            if (r9 == 0) goto L_0x006a
            goto L_0x006c
        L_0x006a:
            int r13 = r8 + -1
        L_0x006c:
            java.lang.String r7 = r2.substring(r7, r13)
            java.lang.String r13 = r7.trim()
            boolean r13 = r13.isEmpty()
            if (r13 == 0) goto L_0x007c
            goto L_0x0118
        L_0x007c:
            java.lang.String r13 = r7.trim()
            boolean r15 = r13.isEmpty()
            r15 = r15 ^ r12
            com.google.android.exoplayer2.util.Assertions.checkArgument(r15)
            java.lang.String r15 = "[ \\.]"
            java.lang.String[] r13 = com.google.android.exoplayer2.util.Util.splitAtFirst(r13, r15)
            r13 = r13[r6]
            r13.getClass()
            int r15 = r13.hashCode()
            r6 = 98
            if (r15 == r6) goto L_0x0107
            r6 = 99
            if (r15 == r6) goto L_0x00fc
            r6 = 105(0x69, float:1.47E-43)
            if (r15 == r6) goto L_0x00f1
            r6 = 3650(0xe42, float:5.115E-42)
            if (r15 == r6) goto L_0x00e6
            r6 = 3314158(0x3291ee, float:4.644125E-39)
            if (r15 == r6) goto L_0x00db
            r6 = 3511770(0x3595da, float:4.921038E-39)
            if (r15 == r6) goto L_0x00d0
            r6 = 117(0x75, float:1.64E-43)
            if (r15 == r6) goto L_0x00c5
            r6 = 118(0x76, float:1.65E-43)
            if (r15 == r6) goto L_0x00ba
            goto L_0x010f
        L_0x00ba:
            java.lang.String r6 = "v"
            boolean r6 = r13.equals(r6)
            if (r6 != 0) goto L_0x00c3
            goto L_0x010f
        L_0x00c3:
            r6 = 4
            goto L_0x0112
        L_0x00c5:
            java.lang.String r6 = "u"
            boolean r6 = r13.equals(r6)
            if (r6 != 0) goto L_0x00ce
            goto L_0x010f
        L_0x00ce:
            r6 = 3
            goto L_0x0112
        L_0x00d0:
            java.lang.String r6 = "ruby"
            boolean r6 = r13.equals(r6)
            if (r6 != 0) goto L_0x00d9
            goto L_0x010f
        L_0x00d9:
            r6 = 7
            goto L_0x0112
        L_0x00db:
            java.lang.String r6 = "lang"
            boolean r6 = r13.equals(r6)
            if (r6 != 0) goto L_0x00e4
            goto L_0x010f
        L_0x00e4:
            r6 = 6
            goto L_0x0112
        L_0x00e6:
            java.lang.String r6 = "rt"
            boolean r6 = r13.equals(r6)
            if (r6 != 0) goto L_0x00ef
            goto L_0x010f
        L_0x00ef:
            r6 = 5
            goto L_0x0112
        L_0x00f1:
            java.lang.String r6 = "i"
            boolean r6 = r13.equals(r6)
            if (r6 != 0) goto L_0x00fa
            goto L_0x010f
        L_0x00fa:
            r6 = 2
            goto L_0x0112
        L_0x00fc:
            java.lang.String r6 = "c"
            boolean r6 = r13.equals(r6)
            if (r6 != 0) goto L_0x0105
            goto L_0x010f
        L_0x0105:
            r6 = 1
            goto L_0x0112
        L_0x0107:
            java.lang.String r6 = "b"
            boolean r6 = r13.equals(r6)
            if (r6 != 0) goto L_0x0111
        L_0x010f:
            r6 = -1
            goto L_0x0112
        L_0x0111:
            r6 = 0
        L_0x0112:
            switch(r6) {
                case 0: goto L_0x0116;
                case 1: goto L_0x0116;
                case 2: goto L_0x0116;
                case 3: goto L_0x0116;
                case 4: goto L_0x0116;
                case 5: goto L_0x0116;
                case 6: goto L_0x0116;
                case 7: goto L_0x0116;
                default: goto L_0x0115;
            }
        L_0x0115:
            r12 = 0
        L_0x0116:
            if (r12 != 0) goto L_0x011b
        L_0x0118:
            r7 = r8
            goto L_0x01f1
        L_0x011b:
            if (r14 == 0) goto L_0x014c
        L_0x011d:
            boolean r6 = r4.isEmpty()
            if (r6 == 0) goto L_0x0124
            goto L_0x0118
        L_0x0124:
            java.lang.Object r6 = r4.pop()
            com.google.android.exoplayer2.text.webvtt.WebvttCueParser$b r6 = (com.google.android.exoplayer2.text.webvtt.WebvttCueParser.b) r6
            a(r3, r6, r0, r5, r1)
            boolean r7 = r4.isEmpty()
            if (r7 != 0) goto L_0x0140
            com.google.android.exoplayer2.text.webvtt.WebvttCueParser$a r7 = new com.google.android.exoplayer2.text.webvtt.WebvttCueParser$a
            int r9 = r3.length()
            r7.<init>(r6, r9)
            r5.add(r7)
            goto L_0x0143
        L_0x0140:
            r5.clear()
        L_0x0143:
            java.lang.String r6 = r6.a
            boolean r6 = r6.equals(r13)
            if (r6 == 0) goto L_0x011d
            goto L_0x0118
        L_0x014c:
            if (r9 != 0) goto L_0x0118
            int r6 = r3.length()
            com.google.android.exoplayer2.text.webvtt.WebvttCueParser$b r6 = com.google.android.exoplayer2.text.webvtt.WebvttCueParser.b.buildStartTag(r7, r6)
            r4.push(r6)
            goto L_0x0118
        L_0x015a:
            int r7 = r7 + 1
            r6 = 59
            int r6 = r2.indexOf(r6, r7)
            r9 = 32
            int r10 = r2.indexOf(r9, r7)
            if (r6 != r11) goto L_0x016c
            r6 = r10
            goto L_0x0173
        L_0x016c:
            if (r10 != r11) goto L_0x016f
            goto L_0x0173
        L_0x016f:
            int r6 = java.lang.Math.min(r6, r10)
        L_0x0173:
            if (r6 == r11) goto L_0x01ee
            java.lang.String r7 = r2.substring(r7, r6)
            r7.getClass()
            int r8 = r7.hashCode()
            switch(r8) {
                case 3309: goto L_0x01a5;
                case 3464: goto L_0x019a;
                case 96708: goto L_0x018f;
                case 3374865: goto L_0x0184;
                default: goto L_0x0183;
            }
        L_0x0183:
            goto L_0x01af
        L_0x0184:
            java.lang.String r8 = "nbsp"
            boolean r8 = r7.equals(r8)
            if (r8 != 0) goto L_0x018d
            goto L_0x01af
        L_0x018d:
            r11 = 3
            goto L_0x01af
        L_0x018f:
            java.lang.String r8 = "amp"
            boolean r8 = r7.equals(r8)
            if (r8 != 0) goto L_0x0198
            goto L_0x01af
        L_0x0198:
            r11 = 2
            goto L_0x01af
        L_0x019a:
            java.lang.String r8 = "lt"
            boolean r8 = r7.equals(r8)
            if (r8 != 0) goto L_0x01a3
            goto L_0x01af
        L_0x01a3:
            r11 = 1
            goto L_0x01af
        L_0x01a5:
            java.lang.String r8 = "gt"
            boolean r8 = r7.equals(r8)
            if (r8 != 0) goto L_0x01ae
            goto L_0x01af
        L_0x01ae:
            r11 = 0
        L_0x01af:
            switch(r11) {
                case 0: goto L_0x01e0;
                case 1: goto L_0x01dc;
                case 2: goto L_0x01d8;
                case 3: goto L_0x01d4;
                default: goto L_0x01b2;
            }
        L_0x01b2:
            int r8 = r7.length()
            int r8 = r8 + 33
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>(r8)
            java.lang.String r8 = "ignoring unsupported entity: '&"
            r9.append(r8)
            r9.append(r7)
            java.lang.String r7 = ";'"
            r9.append(r7)
            java.lang.String r7 = r9.toString()
            java.lang.String r8 = "WebvttCueParser"
            com.google.android.exoplayer2.util.Log.w(r8, r7)
            goto L_0x01e3
        L_0x01d4:
            r3.append(r9)
            goto L_0x01e3
        L_0x01d8:
            r3.append(r15)
            goto L_0x01e3
        L_0x01dc:
            r3.append(r14)
            goto L_0x01e3
        L_0x01e0:
            r3.append(r13)
        L_0x01e3:
            if (r6 != r10) goto L_0x01ea
            java.lang.String r7 = " "
            r3.append(r7)
        L_0x01ea:
            int r6 = r6 + 1
            r7 = r6
            goto L_0x01f1
        L_0x01ee:
            r3.append(r8)
        L_0x01f1:
            r6 = 0
            goto L_0x0017
        L_0x01f4:
            boolean r2 = r4.isEmpty()
            if (r2 != 0) goto L_0x0204
            java.lang.Object r2 = r4.pop()
            com.google.android.exoplayer2.text.webvtt.WebvttCueParser$b r2 = (com.google.android.exoplayer2.text.webvtt.WebvttCueParser.b) r2
            a(r3, r2, r0, r5, r1)
            goto L_0x01f4
        L_0x0204:
            com.google.android.exoplayer2.text.webvtt.WebvttCueParser$b r2 = com.google.android.exoplayer2.text.webvtt.WebvttCueParser.b.buildWholeCueVirtualTag()
            java.util.List r4 = java.util.Collections.emptyList()
            a(r3, r2, r0, r4, r1)
            android.text.SpannedString r0 = android.text.SpannedString.valueOf(r3)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.webvtt.WebvttCueParser.f(java.lang.String, java.util.List, java.lang.String):android.text.SpannedString");
    }

    public static void g(String str, d dVar) {
        String str2;
        int indexOf = str.indexOf(44);
        char c2 = 65535;
        if (indexOf != -1) {
            String substring = str.substring(indexOf + 1);
            substring.getClass();
            int i = 2;
            switch (substring.hashCode()) {
                case -1364013995:
                    if (substring.equals("center")) {
                        c2 = 0;
                        break;
                    }
                    break;
                case -1074341483:
                    if (substring.equals("middle")) {
                        c2 = 1;
                        break;
                    }
                    break;
                case 100571:
                    if (substring.equals("end")) {
                        c2 = 2;
                        break;
                    }
                    break;
                case 109757538:
                    if (substring.equals("start")) {
                        c2 = 3;
                        break;
                    }
                    break;
            }
            switch (c2) {
                case 0:
                case 1:
                    i = 1;
                    break;
                case 2:
                    break;
                case 3:
                    i = 0;
                    break;
                default:
                    if (substring.length() != 0) {
                        str2 = "Invalid anchor value: ".concat(substring);
                    } else {
                        str2 = new String("Invalid anchor value: ");
                    }
                    Log.w("WebvttCueParser", str2);
                    i = Integer.MIN_VALUE;
                    break;
            }
            dVar.g = i;
            str = str.substring(0, indexOf);
        }
        if (str.endsWith("%")) {
            dVar.e = WebvttParserUtil.parsePercentage(str);
            dVar.f = 0;
            return;
        }
        dVar.e = (float) Integer.parseInt(str);
        dVar.f = 1;
    }

    public static void h(String str, d dVar) {
        String str2;
        int indexOf = str.indexOf(44);
        char c2 = 65535;
        if (indexOf != -1) {
            String substring = str.substring(indexOf + 1);
            substring.getClass();
            int i = 2;
            switch (substring.hashCode()) {
                case -1842484672:
                    if (substring.equals("line-left")) {
                        c2 = 0;
                        break;
                    }
                    break;
                case -1364013995:
                    if (substring.equals("center")) {
                        c2 = 1;
                        break;
                    }
                    break;
                case -1276788989:
                    if (substring.equals("line-right")) {
                        c2 = 2;
                        break;
                    }
                    break;
                case -1074341483:
                    if (substring.equals("middle")) {
                        c2 = 3;
                        break;
                    }
                    break;
                case 100571:
                    if (substring.equals("end")) {
                        c2 = 4;
                        break;
                    }
                    break;
                case 109757538:
                    if (substring.equals("start")) {
                        c2 = 5;
                        break;
                    }
                    break;
            }
            switch (c2) {
                case 0:
                case 5:
                    i = 0;
                    break;
                case 1:
                case 3:
                    i = 1;
                    break;
                case 2:
                case 4:
                    break;
                default:
                    if (substring.length() != 0) {
                        str2 = "Invalid anchor value: ".concat(substring);
                    } else {
                        str2 = new String("Invalid anchor value: ");
                    }
                    Log.w("WebvttCueParser", str2);
                    i = Integer.MIN_VALUE;
                    break;
            }
            dVar.i = i;
            str = str.substring(0, indexOf);
        }
        dVar.h = WebvttParserUtil.parsePercentage(str);
    }

    @Nullable
    public static WebvttCueInfo parseCue(ParsableByteArray parsableByteArray, List<WebvttCssStyle> list) {
        String readLine = parsableByteArray.readLine();
        if (readLine == null) {
            return null;
        }
        Pattern pattern = a;
        Matcher matcher = pattern.matcher(readLine);
        if (matcher.matches()) {
            return d((String) null, matcher, parsableByteArray, list);
        }
        String readLine2 = parsableByteArray.readLine();
        if (readLine2 == null) {
            return null;
        }
        Matcher matcher2 = pattern.matcher(readLine2);
        if (matcher2.matches()) {
            return d(readLine.trim(), matcher2, parsableByteArray, list);
        }
        return null;
    }
}
