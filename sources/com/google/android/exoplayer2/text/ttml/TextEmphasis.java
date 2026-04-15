package com.google.android.exoplayer2.text.ttml;

import com.google.common.collect.ImmutableSet;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.regex.Pattern;

public final class TextEmphasis {
    public static final Pattern d = Pattern.compile("\\s+");
    public static final ImmutableSet<String> e = ImmutableSet.of("auto", "none");
    public static final ImmutableSet<String> f = ImmutableSet.of("dot", "sesame", "circle");
    public static final ImmutableSet<String> g = ImmutableSet.of("filled", "open");
    public static final ImmutableSet<String> h = ImmutableSet.of("after", "before", "outside");
    public final int a;
    public final int b;
    public final int c;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Position {
    }

    public TextEmphasis(int i, int i2, int i3) {
        this.a = i;
        this.b = i2;
        this.c = i3;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:37:0x009e, code lost:
        if (r9.equals("auto") != false) goto L_0x00a2;
     */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0062  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0068  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0075  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00a5  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00ad  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00f8  */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x00fa  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x010c  */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x012b  */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x0134  */
    @androidx.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.android.exoplayer2.text.ttml.TextEmphasis parse(@androidx.annotation.Nullable java.lang.String r9) {
        /*
            r0 = 0
            if (r9 != 0) goto L_0x0004
            return r0
        L_0x0004:
            java.lang.String r9 = r9.trim()
            java.lang.String r9 = com.google.common.base.Ascii.toLowerCase((java.lang.String) r9)
            boolean r1 = r9.isEmpty()
            if (r1 == 0) goto L_0x0013
            return r0
        L_0x0013:
            java.util.regex.Pattern r0 = d
            java.lang.String[] r9 = android.text.TextUtils.split(r9, r0)
            com.google.common.collect.ImmutableSet r9 = com.google.common.collect.ImmutableSet.copyOf((E[]) r9)
            com.google.common.collect.ImmutableSet<java.lang.String> r0 = h
            com.google.common.collect.Sets$SetView r0 = com.google.common.collect.Sets.intersection(r0, r9)
            java.lang.String r1 = "outside"
            java.lang.Object r0 = com.google.common.collect.Iterables.getFirst(r0, r1)
            java.lang.String r0 = (java.lang.String) r0
            int r2 = r0.hashCode()
            r3 = -1392885889(0xffffffffacfa3f7f, float:-7.112477E-12)
            r4 = -1
            r5 = 0
            r6 = 2
            r7 = 1
            if (r2 == r3) goto L_0x0055
            r3 = -1106037339(0xffffffffbe1335a5, float:-0.14375933)
            if (r2 == r3) goto L_0x004d
            r1 = 92734940(0x58705dc, float:1.2697491E-35)
            if (r2 == r1) goto L_0x0043
            goto L_0x005f
        L_0x0043:
            java.lang.String r1 = "after"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x005f
            r0 = 0
            goto L_0x0060
        L_0x004d:
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x005f
            r0 = 1
            goto L_0x0060
        L_0x0055:
            java.lang.String r1 = "before"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x005f
            r0 = 2
            goto L_0x0060
        L_0x005f:
            r0 = -1
        L_0x0060:
            if (r0 == 0) goto L_0x0068
            if (r0 == r7) goto L_0x0066
            r0 = 1
            goto L_0x0069
        L_0x0066:
            r0 = -2
            goto L_0x0069
        L_0x0068:
            r0 = 2
        L_0x0069:
            com.google.common.collect.ImmutableSet<java.lang.String> r1 = e
            com.google.common.collect.Sets$SetView r1 = com.google.common.collect.Sets.intersection(r1, r9)
            boolean r2 = r1.isEmpty()
            if (r2 != 0) goto L_0x00ad
            java.util.Iterator r9 = r1.iterator()
            java.lang.Object r9 = r9.next()
            java.lang.String r9 = (java.lang.String) r9
            int r1 = r9.hashCode()
            r2 = 3005871(0x2dddaf, float:4.212122E-39)
            if (r1 == r2) goto L_0x0098
            r2 = 3387192(0x33af38, float:4.746467E-39)
            if (r1 == r2) goto L_0x008e
            goto L_0x00a1
        L_0x008e:
            java.lang.String r1 = "none"
            boolean r9 = r9.equals(r1)
            if (r9 == 0) goto L_0x00a1
            r7 = 0
            goto L_0x00a2
        L_0x0098:
            java.lang.String r1 = "auto"
            boolean r9 = r9.equals(r1)
            if (r9 == 0) goto L_0x00a1
            goto L_0x00a2
        L_0x00a1:
            r7 = -1
        L_0x00a2:
            if (r7 == 0) goto L_0x00a5
            goto L_0x00a6
        L_0x00a5:
            r4 = 0
        L_0x00a6:
            com.google.android.exoplayer2.text.ttml.TextEmphasis r9 = new com.google.android.exoplayer2.text.ttml.TextEmphasis
            r9.<init>(r4, r5, r0)
            goto L_0x013e
        L_0x00ad:
            com.google.common.collect.ImmutableSet<java.lang.String> r1 = g
            com.google.common.collect.Sets$SetView r1 = com.google.common.collect.Sets.intersection(r1, r9)
            com.google.common.collect.ImmutableSet<java.lang.String> r2 = f
            com.google.common.collect.Sets$SetView r9 = com.google.common.collect.Sets.intersection(r2, r9)
            boolean r2 = r1.isEmpty()
            if (r2 == 0) goto L_0x00cc
            boolean r2 = r9.isEmpty()
            if (r2 == 0) goto L_0x00cc
            com.google.android.exoplayer2.text.ttml.TextEmphasis r9 = new com.google.android.exoplayer2.text.ttml.TextEmphasis
            r9.<init>(r4, r5, r0)
            goto L_0x013e
        L_0x00cc:
            java.lang.String r2 = "filled"
            java.lang.Object r1 = com.google.common.collect.Iterables.getFirst(r1, r2)
            java.lang.String r1 = (java.lang.String) r1
            int r3 = r1.hashCode()
            r8 = -1274499742(0xffffffffb408ad62, float:-1.2729063E-7)
            if (r3 == r8) goto L_0x00ed
            r2 = 3417674(0x34264a, float:4.789181E-39)
            if (r3 == r2) goto L_0x00e3
            goto L_0x00f5
        L_0x00e3:
            java.lang.String r2 = "open"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x00f5
            r1 = 0
            goto L_0x00f6
        L_0x00ed:
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x00f5
            r1 = 1
            goto L_0x00f6
        L_0x00f5:
            r1 = -1
        L_0x00f6:
            if (r1 == 0) goto L_0x00fa
            r1 = 1
            goto L_0x00fb
        L_0x00fa:
            r1 = 2
        L_0x00fb:
            java.lang.String r2 = "circle"
            java.lang.Object r9 = com.google.common.collect.Iterables.getFirst(r9, r2)
            java.lang.String r9 = (java.lang.String) r9
            int r3 = r9.hashCode()
            r8 = -1360216880(0xffffffffaeecbcd0, float:-1.0765577E-10)
            if (r3 == r8) goto L_0x012b
            r2 = -905816648(0xffffffffca0255b8, float:-2135406.0)
            if (r3 == r2) goto L_0x0121
            r2 = 99657(0x18549, float:1.39649E-40)
            if (r3 == r2) goto L_0x0117
            goto L_0x0132
        L_0x0117:
            java.lang.String r2 = "dot"
            boolean r9 = r9.equals(r2)
            if (r9 == 0) goto L_0x0132
            r4 = 0
            goto L_0x0132
        L_0x0121:
            java.lang.String r2 = "sesame"
            boolean r9 = r9.equals(r2)
            if (r9 == 0) goto L_0x0132
            r4 = 1
            goto L_0x0132
        L_0x012b:
            boolean r9 = r9.equals(r2)
            if (r9 == 0) goto L_0x0132
            r4 = 2
        L_0x0132:
            if (r4 == 0) goto L_0x0139
            if (r4 == r7) goto L_0x0138
            r6 = 1
            goto L_0x0139
        L_0x0138:
            r6 = 3
        L_0x0139:
            com.google.android.exoplayer2.text.ttml.TextEmphasis r9 = new com.google.android.exoplayer2.text.ttml.TextEmphasis
            r9.<init>(r6, r1, r0)
        L_0x013e:
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.ttml.TextEmphasis.parse(java.lang.String):com.google.android.exoplayer2.text.ttml.TextEmphasis");
    }
}
