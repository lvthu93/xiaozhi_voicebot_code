package com.google.android.exoplayer2.text.ssa;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.text.SimpleSubtitleDecoder;
import com.google.android.exoplayer2.text.ssa.SsaStyle;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Ascii;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class SsaDecoder extends SimpleSubtitleDecoder {
    public static final Pattern t = Pattern.compile("(?:(\\d+):)?(\\d+):(\\d+)[:.](\\d+)");
    public final boolean o;
    @Nullable
    public final lb p;
    public LinkedHashMap q;
    public float r;
    public float s;

    public SsaDecoder() {
        this((List<byte[]>) null);
    }

    public static int f(long j, ArrayList arrayList, ArrayList arrayList2) {
        int i;
        ArrayList arrayList3;
        int size = arrayList.size();
        while (true) {
            size--;
            if (size >= 0) {
                if (((Long) arrayList.get(size)).longValue() != j) {
                    if (((Long) arrayList.get(size)).longValue() < j) {
                        i = size + 1;
                        break;
                    }
                } else {
                    return size;
                }
            } else {
                i = 0;
                break;
            }
        }
        arrayList.add(i, Long.valueOf(j));
        if (i != 0) {
            arrayList3 = new ArrayList((Collection) arrayList2.get(i - 1));
        }
        arrayList2.add(i, arrayList3);
        return i;
    }

    public static long h(String str) {
        Matcher matcher = t.matcher(str.trim());
        if (!matcher.matches()) {
            return -9223372036854775807L;
        }
        long parseLong = (Long.parseLong((String) Util.castNonNull(matcher.group(2))) * 60 * 1000000) + (Long.parseLong((String) Util.castNonNull(matcher.group(1))) * 60 * 60 * 1000000);
        return (Long.parseLong((String) Util.castNonNull(matcher.group(4))) * 10000) + (Long.parseLong((String) Util.castNonNull(matcher.group(3))) * 1000000) + parseLong;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:100:0x0200, code lost:
        if (r0 == null) goto L_0x0218;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:102:0x0204, code lost:
        if (r12 == -3.4028235E38f) goto L_0x0218;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:104:0x0208, code lost:
        if (r11 == -3.4028235E38f) goto L_0x0218;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:105:0x020a, code lost:
        r6.setPosition(r0.x / r11);
        r6.setLine(r0.y / r12, 0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:106:0x0218, code lost:
        r0 = r6.getPositionAnchor();
        r3 = 0.95f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:107:0x021f, code lost:
        if (r0 == 0) goto L_0x0232;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:109:0x0222, code lost:
        if (r0 == 1) goto L_0x022f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:111:0x0225, code lost:
        if (r0 == 2) goto L_0x022b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:112:0x0227, code lost:
        r0 = -3.4028235E38f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:113:0x022b, code lost:
        r0 = 0.95f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:114:0x022f, code lost:
        r0 = 0.5f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:115:0x0232, code lost:
        r0 = 0.05f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:116:0x0235, code lost:
        r6.setPosition(r0);
        r0 = r6.getLineAnchor();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:117:0x023c, code lost:
        if (r0 == 0) goto L_0x024b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:119:0x023f, code lost:
        if (r0 == 1) goto L_0x0248;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:121:0x0242, code lost:
        if (r0 == 2) goto L_0x024e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:122:0x0244, code lost:
        r3 = -3.4028235E38f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:123:0x0248, code lost:
        r3 = 0.5f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:124:0x024b, code lost:
        r3 = 0.05f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:125:0x024e, code lost:
        r6.setLine(r3, 0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:126:0x0252, code lost:
        r0 = r6.build();
        r3 = f(r8, r2, r1);
        r4 = f(r17, r2, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:127:0x0260, code lost:
        if (r3 >= r4) goto L_0x0272;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:128:0x0262, code lost:
        ((java.util.List) r1.get(r3)).add(r0);
        r3 = r3 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x01d5, code lost:
        r5 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x01d6, code lost:
        r5 = r6.setTextAlignment(r5);
        r13 = Integer.MIN_VALUE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x01dc, code lost:
        switch(r0) {
            case -1: goto L_0x01e9;
            case 0: goto L_0x01df;
            case 1: goto L_0x01e7;
            case 2: goto L_0x01e5;
            case 3: goto L_0x01e3;
            case 4: goto L_0x01e7;
            case 5: goto L_0x01e5;
            case 6: goto L_0x01e3;
            case 7: goto L_0x01e7;
            case 8: goto L_0x01e5;
            case 9: goto L_0x01e3;
            default: goto L_0x01df;
        };
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x01df, code lost:
        defpackage.y2.t(30, "Unknown alignment: ", r0, "SsaDecoder");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x01e3, code lost:
        r14 = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:0x01e5, code lost:
        r14 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x01e7, code lost:
        r14 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x01e9, code lost:
        r14 = Integer.MIN_VALUE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x01eb, code lost:
        r5 = r5.setPositionAnchor(r14);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x01ef, code lost:
        switch(r0) {
            case -1: goto L_0x01fb;
            case 0: goto L_0x01f2;
            case 1: goto L_0x01fa;
            case 2: goto L_0x01fa;
            case 3: goto L_0x01fa;
            case 4: goto L_0x01f8;
            case 5: goto L_0x01f8;
            case 6: goto L_0x01f8;
            case 7: goto L_0x01f6;
            case 8: goto L_0x01f6;
            case 9: goto L_0x01f6;
            default: goto L_0x01f2;
        };
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x01f2, code lost:
        defpackage.y2.t(30, "Unknown alignment: ", r0, "SsaDecoder");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x01f6, code lost:
        r13 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:0x01f8, code lost:
        r13 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:0x01fa, code lost:
        r13 = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:0x01fb, code lost:
        r5.setLineAnchor(r13);
        r0 = r10.b;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.android.exoplayer2.text.Subtitle e(byte[] r20, int r21, boolean r22) {
        /*
            r19 = this;
            r0 = r19
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            com.google.android.exoplayer2.util.ParsableByteArray r3 = new com.google.android.exoplayer2.util.ParsableByteArray
            r4 = r20
            r5 = r21
            r3.<init>(r4, r5)
            boolean r4 = r0.o
            if (r4 != 0) goto L_0x001c
            r0.g(r3)
        L_0x001c:
            if (r4 == 0) goto L_0x0021
            lb r4 = r0.p
            goto L_0x0022
        L_0x0021:
            r4 = 0
        L_0x0022:
            java.lang.String r5 = r3.readLine()
            if (r5 == 0) goto L_0x027a
            java.lang.String r6 = "Format:"
            boolean r6 = r5.startsWith(r6)
            if (r6 == 0) goto L_0x0035
            lb r4 = defpackage.lb.fromFormatLine(r5)
            goto L_0x0022
        L_0x0035:
            java.lang.String r6 = "Dialogue:"
            boolean r7 = r5.startsWith(r6)
            if (r7 == 0) goto L_0x026e
            java.lang.String r7 = "SsaDecoder"
            if (r4 != 0) goto L_0x0058
            int r6 = r5.length()
            java.lang.String r8 = "Skipping dialogue line before complete format: "
            if (r6 == 0) goto L_0x004e
            java.lang.String r5 = r8.concat(r5)
            goto L_0x0053
        L_0x004e:
            java.lang.String r5 = new java.lang.String
            r5.<init>(r8)
        L_0x0053:
            com.google.android.exoplayer2.util.Log.w(r7, r5)
            goto L_0x026e
        L_0x0058:
            boolean r6 = r5.startsWith(r6)
            com.google.android.exoplayer2.util.Assertions.checkArgument(r6)
            r6 = 9
            java.lang.String r6 = r5.substring(r6)
            java.lang.String r8 = ","
            int r9 = r4.e
            java.lang.String[] r6 = r6.split(r8, r9)
            int r8 = r6.length
            if (r8 == r9) goto L_0x0087
            int r6 = r5.length()
            java.lang.String r8 = "Skipping dialogue line with fewer columns than format: "
            if (r6 == 0) goto L_0x007d
            java.lang.String r5 = r8.concat(r5)
            goto L_0x0082
        L_0x007d:
            java.lang.String r5 = new java.lang.String
            r5.<init>(r8)
        L_0x0082:
            com.google.android.exoplayer2.util.Log.w(r7, r5)
            goto L_0x026e
        L_0x0087:
            int r8 = r4.a
            r8 = r6[r8]
            long r8 = h(r8)
            java.lang.String r10 = "Skipping invalid timing: "
            r11 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            int r13 = (r8 > r11 ? 1 : (r8 == r11 ? 0 : -1))
            if (r13 != 0) goto L_0x00af
            int r6 = r5.length()
            if (r6 == 0) goto L_0x00a5
            java.lang.String r5 = r10.concat(r5)
            goto L_0x00aa
        L_0x00a5:
            java.lang.String r5 = new java.lang.String
            r5.<init>(r10)
        L_0x00aa:
            com.google.android.exoplayer2.util.Log.w(r7, r5)
            goto L_0x026e
        L_0x00af:
            int r13 = r4.b
            r13 = r6[r13]
            long r13 = h(r13)
            int r15 = (r13 > r11 ? 1 : (r13 == r11 ? 0 : -1))
            if (r15 != 0) goto L_0x00d0
            int r6 = r5.length()
            if (r6 == 0) goto L_0x00c6
            java.lang.String r5 = r10.concat(r5)
            goto L_0x00cb
        L_0x00c6:
            java.lang.String r5 = new java.lang.String
            r5.<init>(r10)
        L_0x00cb:
            com.google.android.exoplayer2.util.Log.w(r7, r5)
            goto L_0x026e
        L_0x00d0:
            java.util.LinkedHashMap r5 = r0.q
            r10 = -1
            if (r5 == 0) goto L_0x00e6
            int r11 = r4.c
            if (r11 == r10) goto L_0x00e6
            r10 = r6[r11]
            java.lang.String r10 = r10.trim()
            java.lang.Object r5 = r5.get(r10)
            com.google.android.exoplayer2.text.ssa.SsaStyle r5 = (com.google.android.exoplayer2.text.ssa.SsaStyle) r5
            goto L_0x00e7
        L_0x00e6:
            r5 = 0
        L_0x00e7:
            int r10 = r4.d
            r6 = r6[r10]
            com.google.android.exoplayer2.text.ssa.SsaStyle$b r10 = com.google.android.exoplayer2.text.ssa.SsaStyle.b.parseFromDialogue(r6)
            java.lang.String r6 = com.google.android.exoplayer2.text.ssa.SsaStyle.b.stripStyleOverrides(r6)
            java.lang.String r11 = "\\N"
            java.lang.String r12 = "\n"
            java.lang.String r6 = r6.replace(r11, r12)
            java.lang.String r11 = "\\n"
            java.lang.String r6 = r6.replace(r11, r12)
            java.lang.String r11 = "\\h"
            java.lang.String r12 = " "
            java.lang.String r6 = r6.replace(r11, r12)
            float r11 = r0.r
            float r12 = r0.s
            android.text.SpannableString r15 = new android.text.SpannableString
            r15.<init>(r6)
            com.google.android.exoplayer2.text.Cue$Builder r6 = new com.google.android.exoplayer2.text.Cue$Builder
            r6.<init>()
            com.google.android.exoplayer2.text.Cue$Builder r6 = r6.setText(r15)
            r16 = -8388609(0xffffffffff7fffff, float:-3.4028235E38)
            if (r5 == 0) goto L_0x01af
            java.lang.Integer r0 = r5.c
            if (r0 == 0) goto L_0x013e
            r22 = r3
            android.text.style.ForegroundColorSpan r3 = new android.text.style.ForegroundColorSpan
            int r0 = r0.intValue()
            r3.<init>(r0)
            int r0 = r15.length()
            r20 = r4
            r4 = 33
            r17 = r13
            r13 = 0
            r15.setSpan(r3, r13, r0, r4)
            goto L_0x0144
        L_0x013e:
            r22 = r3
            r20 = r4
            r17 = r13
        L_0x0144:
            float r0 = r5.d
            int r3 = (r0 > r16 ? 1 : (r0 == r16 ? 0 : -1))
            if (r3 == 0) goto L_0x0153
            int r3 = (r12 > r16 ? 1 : (r12 == r16 ? 0 : -1))
            if (r3 == 0) goto L_0x0153
            float r0 = r0 / r12
            r3 = 1
            r6.setTextSize(r0, r3)
        L_0x0153:
            boolean r0 = r5.f
            boolean r3 = r5.e
            if (r3 == 0) goto L_0x016c
            if (r0 == 0) goto L_0x016c
            android.text.style.StyleSpan r0 = new android.text.style.StyleSpan
            r3 = 3
            r0.<init>(r3)
            int r3 = r15.length()
            r4 = 33
            r13 = 0
            r15.setSpan(r0, r13, r3, r4)
            goto L_0x018e
        L_0x016c:
            r4 = 33
            r13 = 0
            if (r3 == 0) goto L_0x017f
            android.text.style.StyleSpan r0 = new android.text.style.StyleSpan
            r3 = 1
            r0.<init>(r3)
            int r3 = r15.length()
            r15.setSpan(r0, r13, r3, r4)
            goto L_0x018e
        L_0x017f:
            if (r0 == 0) goto L_0x018e
            android.text.style.StyleSpan r0 = new android.text.style.StyleSpan
            r3 = 2
            r0.<init>(r3)
            int r3 = r15.length()
            r15.setSpan(r0, r13, r3, r4)
        L_0x018e:
            boolean r0 = r5.g
            if (r0 == 0) goto L_0x019e
            android.text.style.UnderlineSpan r0 = new android.text.style.UnderlineSpan
            r0.<init>()
            int r3 = r15.length()
            r15.setSpan(r0, r13, r3, r4)
        L_0x019e:
            boolean r0 = r5.h
            if (r0 == 0) goto L_0x01b5
            android.text.style.StrikethroughSpan r0 = new android.text.style.StrikethroughSpan
            r0.<init>()
            int r3 = r15.length()
            r15.setSpan(r0, r13, r3, r4)
            goto L_0x01b5
        L_0x01af:
            r22 = r3
            r20 = r4
            r17 = r13
        L_0x01b5:
            int r0 = r10.a
            r3 = -1
            if (r0 == r3) goto L_0x01bb
            goto L_0x01c1
        L_0x01bb:
            if (r5 == 0) goto L_0x01c0
            int r0 = r5.b
            goto L_0x01c1
        L_0x01c0:
            r0 = -1
        L_0x01c1:
            java.lang.String r3 = "Unknown alignment: "
            r4 = 30
            switch(r0) {
                case -1: goto L_0x01d5;
                case 0: goto L_0x01c8;
                case 1: goto L_0x01d2;
                case 2: goto L_0x01cf;
                case 3: goto L_0x01cc;
                case 4: goto L_0x01d2;
                case 5: goto L_0x01cf;
                case 6: goto L_0x01cc;
                case 7: goto L_0x01d2;
                case 8: goto L_0x01cf;
                case 9: goto L_0x01cc;
                default: goto L_0x01c8;
            }
        L_0x01c8:
            defpackage.y2.t(r4, r3, r0, r7)
            goto L_0x01d5
        L_0x01cc:
            android.text.Layout$Alignment r5 = android.text.Layout.Alignment.ALIGN_OPPOSITE
            goto L_0x01d6
        L_0x01cf:
            android.text.Layout$Alignment r5 = android.text.Layout.Alignment.ALIGN_CENTER
            goto L_0x01d6
        L_0x01d2:
            android.text.Layout$Alignment r5 = android.text.Layout.Alignment.ALIGN_NORMAL
            goto L_0x01d6
        L_0x01d5:
            r5 = 0
        L_0x01d6:
            com.google.android.exoplayer2.text.Cue$Builder r5 = r6.setTextAlignment(r5)
            r13 = -2147483648(0xffffffff80000000, float:-0.0)
            switch(r0) {
                case -1: goto L_0x01e9;
                case 0: goto L_0x01df;
                case 1: goto L_0x01e7;
                case 2: goto L_0x01e5;
                case 3: goto L_0x01e3;
                case 4: goto L_0x01e7;
                case 5: goto L_0x01e5;
                case 6: goto L_0x01e3;
                case 7: goto L_0x01e7;
                case 8: goto L_0x01e5;
                case 9: goto L_0x01e3;
                default: goto L_0x01df;
            }
        L_0x01df:
            defpackage.y2.t(r4, r3, r0, r7)
            goto L_0x01e9
        L_0x01e3:
            r14 = 2
            goto L_0x01eb
        L_0x01e5:
            r14 = 1
            goto L_0x01eb
        L_0x01e7:
            r14 = 0
            goto L_0x01eb
        L_0x01e9:
            r14 = -2147483648(0xffffffff80000000, float:-0.0)
        L_0x01eb:
            com.google.android.exoplayer2.text.Cue$Builder r5 = r5.setPositionAnchor(r14)
            switch(r0) {
                case -1: goto L_0x01fb;
                case 0: goto L_0x01f2;
                case 1: goto L_0x01fa;
                case 2: goto L_0x01fa;
                case 3: goto L_0x01fa;
                case 4: goto L_0x01f8;
                case 5: goto L_0x01f8;
                case 6: goto L_0x01f8;
                case 7: goto L_0x01f6;
                case 8: goto L_0x01f6;
                case 9: goto L_0x01f6;
                default: goto L_0x01f2;
            }
        L_0x01f2:
            defpackage.y2.t(r4, r3, r0, r7)
            goto L_0x01fb
        L_0x01f6:
            r13 = 0
            goto L_0x01fb
        L_0x01f8:
            r13 = 1
            goto L_0x01fb
        L_0x01fa:
            r13 = 2
        L_0x01fb:
            r5.setLineAnchor(r13)
            android.graphics.PointF r0 = r10.b
            if (r0 == 0) goto L_0x0218
            int r3 = (r12 > r16 ? 1 : (r12 == r16 ? 0 : -1))
            if (r3 == 0) goto L_0x0218
            int r3 = (r11 > r16 ? 1 : (r11 == r16 ? 0 : -1))
            if (r3 == 0) goto L_0x0218
            float r3 = r0.x
            float r3 = r3 / r11
            r6.setPosition(r3)
            float r0 = r0.y
            float r0 = r0 / r12
            r3 = 0
            r6.setLine(r0, r3)
            goto L_0x0252
        L_0x0218:
            int r0 = r6.getPositionAnchor()
            r3 = 1064514355(0x3f733333, float:0.95)
            if (r0 == 0) goto L_0x0232
            r4 = 1
            if (r0 == r4) goto L_0x022f
            r4 = 2
            if (r0 == r4) goto L_0x022b
            r0 = -8388609(0xffffffffff7fffff, float:-3.4028235E38)
            goto L_0x0235
        L_0x022b:
            r0 = 1064514355(0x3f733333, float:0.95)
            goto L_0x0235
        L_0x022f:
            r0 = 1056964608(0x3f000000, float:0.5)
            goto L_0x0235
        L_0x0232:
            r0 = 1028443341(0x3d4ccccd, float:0.05)
        L_0x0235:
            r6.setPosition(r0)
            int r0 = r6.getLineAnchor()
            if (r0 == 0) goto L_0x024b
            r4 = 1
            if (r0 == r4) goto L_0x0248
            r4 = 2
            if (r0 == r4) goto L_0x024e
            r3 = -8388609(0xffffffffff7fffff, float:-3.4028235E38)
            goto L_0x024e
        L_0x0248:
            r3 = 1056964608(0x3f000000, float:0.5)
            goto L_0x024e
        L_0x024b:
            r3 = 1028443341(0x3d4ccccd, float:0.05)
        L_0x024e:
            r0 = 0
            r6.setLine(r3, r0)
        L_0x0252:
            com.google.android.exoplayer2.text.Cue r0 = r6.build()
            int r3 = f(r8, r2, r1)
            r4 = r17
            int r4 = f(r4, r2, r1)
        L_0x0260:
            if (r3 >= r4) goto L_0x0272
            java.lang.Object r5 = r1.get(r3)
            java.util.List r5 = (java.util.List) r5
            r5.add(r0)
            int r3 = r3 + 1
            goto L_0x0260
        L_0x026e:
            r22 = r3
            r20 = r4
        L_0x0272:
            r0 = r19
            r4 = r20
            r3 = r22
            goto L_0x0022
        L_0x027a:
            mb r0 = new mb
            r0.<init>(r1, r2)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.ssa.SsaDecoder.e(byte[], int, boolean):com.google.android.exoplayer2.text.Subtitle");
    }

    public final void g(ParsableByteArray parsableByteArray) {
        String str;
        while (true) {
            String readLine = parsableByteArray.readLine();
            if (readLine == null) {
                return;
            }
            if ("[Script Info]".equalsIgnoreCase(readLine)) {
                while (true) {
                    String readLine2 = parsableByteArray.readLine();
                    if (readLine2 == null || (parsableByteArray.bytesLeft() != 0 && parsableByteArray.peekUnsignedByte() == 91)) {
                        break;
                    }
                    String[] split = readLine2.split(":");
                    if (split.length == 2) {
                        String lowerCase = Ascii.toLowerCase(split[0].trim());
                        lowerCase.getClass();
                        if (lowerCase.equals("playresx")) {
                            this.r = Float.parseFloat(split[1].trim());
                        } else if (lowerCase.equals("playresy")) {
                            try {
                                this.s = Float.parseFloat(split[1].trim());
                            } catch (NumberFormatException unused) {
                            }
                        }
                    }
                }
            } else if ("[V4+ Styles]".equalsIgnoreCase(readLine)) {
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                SsaStyle.a aVar = null;
                while (true) {
                    String readLine3 = parsableByteArray.readLine();
                    if (readLine3 == null || (parsableByteArray.bytesLeft() != 0 && parsableByteArray.peekUnsignedByte() == 91)) {
                        this.q = linkedHashMap;
                    } else if (readLine3.startsWith("Format:")) {
                        aVar = SsaStyle.a.fromFormatLine(readLine3);
                    } else if (readLine3.startsWith("Style:")) {
                        if (aVar == null) {
                            if (readLine3.length() != 0) {
                                str = "Skipping 'Style:' line before 'Format:' line: ".concat(readLine3);
                            } else {
                                str = new String("Skipping 'Style:' line before 'Format:' line: ");
                            }
                            Log.w("SsaDecoder", str);
                        } else {
                            SsaStyle fromStyleLine = SsaStyle.fromStyleLine(readLine3, aVar);
                            if (fromStyleLine != null) {
                                linkedHashMap.put(fromStyleLine.a, fromStyleLine);
                            }
                        }
                    }
                }
                this.q = linkedHashMap;
            } else if ("[V4 Styles]".equalsIgnoreCase(readLine)) {
                Log.i("SsaDecoder", "[V4 Styles] are not supported");
            } else if ("[Events]".equalsIgnoreCase(readLine)) {
                return;
            }
        }
    }

    public SsaDecoder(@Nullable List<byte[]> list) {
        super("SsaDecoder");
        this.r = -3.4028235E38f;
        this.s = -3.4028235E38f;
        if (list == null || list.isEmpty()) {
            this.o = false;
            this.p = null;
            return;
        }
        this.o = true;
        String fromUtf8Bytes = Util.fromUtf8Bytes(list.get(0));
        Assertions.checkArgument(fromUtf8Bytes.startsWith("Format:"));
        this.p = (lb) Assertions.checkNotNull(lb.fromFormatLine(fromUtf8Bytes));
        g(new ParsableByteArray(list.get(1)));
    }
}
