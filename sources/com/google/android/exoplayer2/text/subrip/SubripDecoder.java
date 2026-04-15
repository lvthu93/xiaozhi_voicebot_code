package com.google.android.exoplayer2.text.subrip;

import com.google.android.exoplayer2.text.SimpleSubtitleDecoder;
import com.google.android.exoplayer2.util.Assertions;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class SubripDecoder extends SimpleSubtitleDecoder {
    public static final Pattern q = Pattern.compile("\\s*((?:(\\d+):)?(\\d+):(\\d+)(?:,(\\d+))?)\\s*-->\\s*((?:(\\d+):)?(\\d+):(\\d+)(?:,(\\d+))?)\\s*");
    public static final Pattern r = Pattern.compile("\\{\\\\.*?\\}");
    public final StringBuilder o = new StringBuilder();
    public final ArrayList<String> p = new ArrayList<>();

    public SubripDecoder() {
        super("SubripDecoder");
    }

    public static long f(Matcher matcher, int i) {
        long j;
        String group = matcher.group(i + 1);
        if (group != null) {
            j = Long.parseLong(group) * 60 * 60 * 1000;
        } else {
            j = 0;
        }
        long parseLong = (Long.parseLong((String) Assertions.checkNotNull(matcher.group(i + 3))) * 1000) + (Long.parseLong((String) Assertions.checkNotNull(matcher.group(i + 2))) * 60 * 1000) + j;
        String group2 = matcher.group(i + 4);
        if (group2 != null) {
            parseLong += Long.parseLong(group2);
        }
        return parseLong * 1000;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.android.exoplayer2.text.Subtitle e(byte[] r18, int r19, boolean r20) {
        /*
            r17 = this;
            r0 = r17
            java.lang.String r1 = "SubripDecoder"
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            com.google.android.exoplayer2.util.LongArray r3 = new com.google.android.exoplayer2.util.LongArray
            r3.<init>()
            com.google.android.exoplayer2.util.ParsableByteArray r4 = new com.google.android.exoplayer2.util.ParsableByteArray
            r5 = r18
            r6 = r19
            r4.<init>(r5, r6)
        L_0x0017:
            java.lang.String r5 = r4.readLine()
            r6 = 0
            if (r5 == 0) goto L_0x025f
            int r7 = r5.length()
            if (r7 != 0) goto L_0x0025
            goto L_0x0017
        L_0x0025:
            java.lang.Integer.parseInt(r5)     // Catch:{ NumberFormatException -> 0x0242 }
            java.lang.String r5 = r4.readLine()
            if (r5 != 0) goto L_0x0035
            java.lang.String r4 = "Unexpected end"
            com.google.android.exoplayer2.util.Log.w(r1, r4)
            goto L_0x025f
        L_0x0035:
            java.util.regex.Pattern r7 = q
            java.util.regex.Matcher r7 = r7.matcher(r5)
            boolean r8 = r7.matches()
            if (r8 == 0) goto L_0x022a
            r5 = 1
            long r8 = f(r7, r5)
            r3.add(r8)
            r8 = 6
            long r9 = f(r7, r8)
            r3.add(r9)
            java.lang.StringBuilder r7 = r0.o
            r7.setLength(r6)
            java.util.ArrayList<java.lang.String> r9 = r0.p
            r9.clear()
            java.lang.String r10 = r4.readLine()
        L_0x005f:
            boolean r11 = android.text.TextUtils.isEmpty(r10)
            if (r11 != 0) goto L_0x00ad
            int r11 = r7.length()
            if (r11 <= 0) goto L_0x0070
            java.lang.String r11 = "<br>"
            r7.append(r11)
        L_0x0070:
            java.lang.String r10 = r10.trim()
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>(r10)
            java.util.regex.Pattern r12 = r
            java.util.regex.Matcher r10 = r12.matcher(r10)
            r12 = 0
        L_0x0080:
            boolean r13 = r10.find()
            if (r13 == 0) goto L_0x00a0
            java.lang.String r13 = r10.group()
            r9.add(r13)
            int r14 = r10.start()
            int r14 = r14 - r12
            int r13 = r13.length()
            int r15 = r14 + r13
            java.lang.String r8 = ""
            r11.replace(r14, r15, r8)
            int r12 = r12 + r13
            r8 = 6
            goto L_0x0080
        L_0x00a0:
            java.lang.String r8 = r11.toString()
            r7.append(r8)
            java.lang.String r10 = r4.readLine()
            r8 = 6
            goto L_0x005f
        L_0x00ad:
            java.lang.String r7 = r7.toString()
            android.text.Spanned r7 = android.text.Html.fromHtml(r7)
            r8 = 0
        L_0x00b6:
            int r10 = r9.size()
            if (r8 >= r10) goto L_0x00ce
            java.lang.Object r10 = r9.get(r8)
            java.lang.String r10 = (java.lang.String) r10
            java.lang.String r11 = "\\{\\\\an[1-9]\\}"
            boolean r11 = r10.matches(r11)
            if (r11 == 0) goto L_0x00cb
            goto L_0x00cf
        L_0x00cb:
            int r8 = r8 + 1
            goto L_0x00b6
        L_0x00ce:
            r10 = 0
        L_0x00cf:
            com.google.android.exoplayer2.text.Cue$Builder r8 = new com.google.android.exoplayer2.text.Cue$Builder
            r8.<init>()
            com.google.android.exoplayer2.text.Cue$Builder r7 = r8.setText(r7)
            if (r10 != 0) goto L_0x00e2
            com.google.android.exoplayer2.text.Cue r5 = r7.build()
            r16 = r4
            goto L_0x0221
        L_0x00e2:
            int r8 = r10.hashCode()
            java.lang.String r11 = "{\\an9}"
            java.lang.String r12 = "{\\an8}"
            java.lang.String r6 = "{\\an7}"
            java.lang.String r13 = "{\\an6}"
            java.lang.String r14 = "{\\an5}"
            java.lang.String r15 = "{\\an4}"
            java.lang.String r9 = "{\\an3}"
            java.lang.String r5 = "{\\an2}"
            java.lang.String r0 = "{\\an1}"
            switch(r8) {
                case -685620710: goto L_0x013d;
                case -685620679: goto L_0x0135;
                case -685620648: goto L_0x012d;
                case -685620617: goto L_0x0125;
                case -685620586: goto L_0x011d;
                case -685620555: goto L_0x0115;
                case -685620524: goto L_0x010d;
                case -685620493: goto L_0x0104;
                case -685620462: goto L_0x00fc;
                default: goto L_0x00fb;
            }
        L_0x00fb:
            goto L_0x0145
        L_0x00fc:
            boolean r8 = r10.equals(r11)
            if (r8 == 0) goto L_0x0145
            r8 = 5
            goto L_0x0146
        L_0x0104:
            boolean r8 = r10.equals(r12)
            if (r8 == 0) goto L_0x0145
            r8 = 8
            goto L_0x0146
        L_0x010d:
            boolean r8 = r10.equals(r6)
            if (r8 == 0) goto L_0x0145
            r8 = 2
            goto L_0x0146
        L_0x0115:
            boolean r8 = r10.equals(r13)
            if (r8 == 0) goto L_0x0145
            r8 = 4
            goto L_0x0146
        L_0x011d:
            boolean r8 = r10.equals(r14)
            if (r8 == 0) goto L_0x0145
            r8 = 7
            goto L_0x0146
        L_0x0125:
            boolean r8 = r10.equals(r15)
            if (r8 == 0) goto L_0x0145
            r8 = 1
            goto L_0x0146
        L_0x012d:
            boolean r8 = r10.equals(r9)
            if (r8 == 0) goto L_0x0145
            r8 = 3
            goto L_0x0146
        L_0x0135:
            boolean r8 = r10.equals(r5)
            if (r8 == 0) goto L_0x0145
            r8 = 6
            goto L_0x0146
        L_0x013d:
            boolean r8 = r10.equals(r0)
            if (r8 == 0) goto L_0x0145
            r8 = 0
            goto L_0x0146
        L_0x0145:
            r8 = -1
        L_0x0146:
            r16 = r4
            if (r8 == 0) goto L_0x0163
            r4 = 1
            if (r8 == r4) goto L_0x0163
            r4 = 2
            if (r8 == r4) goto L_0x0163
            r4 = 3
            if (r8 == r4) goto L_0x015e
            r4 = 4
            if (r8 == r4) goto L_0x015e
            r4 = 5
            if (r8 == r4) goto L_0x015e
            r4 = 1
            r7.setPositionAnchor(r4)
            goto L_0x0167
        L_0x015e:
            r4 = 2
            r7.setPositionAnchor(r4)
            goto L_0x0167
        L_0x0163:
            r4 = 0
            r7.setPositionAnchor(r4)
        L_0x0167:
            int r4 = r10.hashCode()
            switch(r4) {
                case -685620710: goto L_0x01b0;
                case -685620679: goto L_0x01a8;
                case -685620648: goto L_0x01a0;
                case -685620617: goto L_0x0198;
                case -685620586: goto L_0x0190;
                case -685620555: goto L_0x0187;
                case -685620524: goto L_0x017f;
                case -685620493: goto L_0x0177;
                case -685620462: goto L_0x016f;
                default: goto L_0x016e;
            }
        L_0x016e:
            goto L_0x01b8
        L_0x016f:
            boolean r0 = r10.equals(r11)
            if (r0 == 0) goto L_0x01b8
            r8 = 5
            goto L_0x01b9
        L_0x0177:
            boolean r0 = r10.equals(r12)
            if (r0 == 0) goto L_0x01b8
            r8 = 4
            goto L_0x01b9
        L_0x017f:
            boolean r0 = r10.equals(r6)
            if (r0 == 0) goto L_0x01b8
            r8 = 3
            goto L_0x01b9
        L_0x0187:
            boolean r0 = r10.equals(r13)
            if (r0 == 0) goto L_0x01b8
            r8 = 8
            goto L_0x01b9
        L_0x0190:
            boolean r0 = r10.equals(r14)
            if (r0 == 0) goto L_0x01b8
            r8 = 7
            goto L_0x01b9
        L_0x0198:
            boolean r0 = r10.equals(r15)
            if (r0 == 0) goto L_0x01b8
            r8 = 6
            goto L_0x01b9
        L_0x01a0:
            boolean r0 = r10.equals(r9)
            if (r0 == 0) goto L_0x01b8
            r8 = 2
            goto L_0x01b9
        L_0x01a8:
            boolean r0 = r10.equals(r5)
            if (r0 == 0) goto L_0x01b8
            r8 = 1
            goto L_0x01b9
        L_0x01b0:
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x01b8
            r8 = 0
            goto L_0x01b9
        L_0x01b8:
            r8 = -1
        L_0x01b9:
            if (r8 == 0) goto L_0x01d4
            r0 = 1
            if (r8 == r0) goto L_0x01d4
            r4 = 2
            if (r8 == r4) goto L_0x01d4
            r4 = 3
            if (r8 == r4) goto L_0x01ce
            r4 = 4
            if (r8 == r4) goto L_0x01ce
            r4 = 5
            if (r8 == r4) goto L_0x01ce
            r7.setLineAnchor(r0)
            goto L_0x01d2
        L_0x01ce:
            r0 = 0
            r7.setLineAnchor(r0)
        L_0x01d2:
            r0 = 2
            goto L_0x01d8
        L_0x01d4:
            r0 = 2
            r7.setLineAnchor(r0)
        L_0x01d8:
            int r4 = r7.getPositionAnchor()
            r5 = 1064011039(0x3f6b851f, float:0.92)
            r6 = 1056964608(0x3f000000, float:0.5)
            r8 = 1034147594(0x3da3d70a, float:0.08)
            if (r4 == 0) goto L_0x01f8
            r9 = 1
            if (r4 == r9) goto L_0x01f5
            if (r4 != r0) goto L_0x01ef
            r0 = 1064011039(0x3f6b851f, float:0.92)
            goto L_0x01fb
        L_0x01ef:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            r0.<init>()
            throw r0
        L_0x01f5:
            r0 = 1056964608(0x3f000000, float:0.5)
            goto L_0x01fb
        L_0x01f8:
            r0 = 1034147594(0x3da3d70a, float:0.08)
        L_0x01fb:
            com.google.android.exoplayer2.text.Cue$Builder r0 = r7.setPosition(r0)
            int r4 = r7.getLineAnchor()
            if (r4 == 0) goto L_0x0215
            r7 = 1
            if (r4 == r7) goto L_0x0212
            r7 = 2
            if (r4 != r7) goto L_0x020c
            goto L_0x0218
        L_0x020c:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            r0.<init>()
            throw r0
        L_0x0212:
            r5 = 1056964608(0x3f000000, float:0.5)
            goto L_0x0218
        L_0x0215:
            r5 = 1034147594(0x3da3d70a, float:0.08)
        L_0x0218:
            r4 = 0
            com.google.android.exoplayer2.text.Cue$Builder r0 = r0.setLine(r5, r4)
            com.google.android.exoplayer2.text.Cue r5 = r0.build()
        L_0x0221:
            r2.add(r5)
            com.google.android.exoplayer2.text.Cue r0 = com.google.android.exoplayer2.text.Cue.r
            r2.add(r0)
            goto L_0x0259
        L_0x022a:
            r16 = r4
            int r0 = r5.length()
            java.lang.String r4 = "Skipping invalid timing: "
            if (r0 == 0) goto L_0x0239
            java.lang.String r0 = r4.concat(r5)
            goto L_0x023e
        L_0x0239:
            java.lang.String r0 = new java.lang.String
            r0.<init>(r4)
        L_0x023e:
            com.google.android.exoplayer2.util.Log.w(r1, r0)
            goto L_0x0259
        L_0x0242:
            r16 = r4
            int r0 = r5.length()
            java.lang.String r4 = "Skipping invalid index: "
            if (r0 == 0) goto L_0x0251
            java.lang.String r0 = r4.concat(r5)
            goto L_0x0256
        L_0x0251:
            java.lang.String r0 = new java.lang.String
            r0.<init>(r4)
        L_0x0256:
            com.google.android.exoplayer2.util.Log.w(r1, r0)
        L_0x0259:
            r0 = r17
            r4 = r16
            goto L_0x0017
        L_0x025f:
            r0 = 0
            com.google.android.exoplayer2.text.Cue[] r0 = new com.google.android.exoplayer2.text.Cue[r0]
            java.lang.Object[] r0 = r2.toArray(r0)
            com.google.android.exoplayer2.text.Cue[] r0 = (com.google.android.exoplayer2.text.Cue[]) r0
            long[] r1 = r3.toArray()
            dc r2 = new dc
            r2.<init>(r0, r1)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.subrip.SubripDecoder.e(byte[], int, boolean):com.google.android.exoplayer2.text.Subtitle");
    }
}
