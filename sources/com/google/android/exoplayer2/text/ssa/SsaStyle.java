package com.google.android.exoplayer2.text.ssa;

import android.graphics.Color;
import android.graphics.PointF;
import android.text.TextUtils;
import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class SsaStyle {
    public final String a;
    public final int b;
    @ColorInt
    @Nullable
    public final Integer c;
    public final float d;
    public final boolean e;
    public final boolean f;
    public final boolean g;
    public final boolean h;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface SsaAlignment {
    }

    public static final class a {
        public final int a;
        public final int b;
        public final int c;
        public final int d;
        public final int e;
        public final int f;
        public final int g;
        public final int h;
        public final int i;

        public a(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
            this.a = i2;
            this.b = i3;
            this.c = i4;
            this.d = i5;
            this.e = i6;
            this.f = i7;
            this.g = i8;
            this.h = i9;
            this.i = i10;
        }

        /* JADX WARNING: Can't fix incorrect switch cases order */
        @androidx.annotation.Nullable
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static com.google.android.exoplayer2.text.ssa.SsaStyle.a fromFormatLine(java.lang.String r14) {
            /*
                r0 = 7
                java.lang.String r14 = r14.substring(r0)
                java.lang.String r1 = ","
                java.lang.String[] r14 = android.text.TextUtils.split(r14, r1)
                r1 = -1
                r2 = 0
                r3 = 0
                r5 = -1
                r6 = -1
                r7 = -1
                r8 = -1
                r9 = -1
                r10 = -1
                r11 = -1
                r12 = -1
            L_0x0016:
                int r4 = r14.length
                if (r3 >= r4) goto L_0x009e
                r4 = r14[r3]
                java.lang.String r4 = r4.trim()
                java.lang.String r4 = com.google.common.base.Ascii.toLowerCase((java.lang.String) r4)
                r4.getClass()
                int r13 = r4.hashCode()
                switch(r13) {
                    case -1178781136: goto L_0x007d;
                    case -1026963764: goto L_0x0072;
                    case -192095652: goto L_0x0067;
                    case -70925746: goto L_0x005c;
                    case 3029637: goto L_0x0051;
                    case 3373707: goto L_0x0046;
                    case 366554320: goto L_0x003b;
                    case 1767875043: goto L_0x0030;
                    default: goto L_0x002d;
                }
            L_0x002d:
                r4 = -1
                goto L_0x0087
            L_0x0030:
                java.lang.String r13 = "alignment"
                boolean r4 = r4.equals(r13)
                if (r4 != 0) goto L_0x0039
                goto L_0x002d
            L_0x0039:
                r4 = 7
                goto L_0x0087
            L_0x003b:
                java.lang.String r13 = "fontsize"
                boolean r4 = r4.equals(r13)
                if (r4 != 0) goto L_0x0044
                goto L_0x002d
            L_0x0044:
                r4 = 6
                goto L_0x0087
            L_0x0046:
                java.lang.String r13 = "name"
                boolean r4 = r4.equals(r13)
                if (r4 != 0) goto L_0x004f
                goto L_0x002d
            L_0x004f:
                r4 = 5
                goto L_0x0087
            L_0x0051:
                java.lang.String r13 = "bold"
                boolean r4 = r4.equals(r13)
                if (r4 != 0) goto L_0x005a
                goto L_0x002d
            L_0x005a:
                r4 = 4
                goto L_0x0087
            L_0x005c:
                java.lang.String r13 = "primarycolour"
                boolean r4 = r4.equals(r13)
                if (r4 != 0) goto L_0x0065
                goto L_0x002d
            L_0x0065:
                r4 = 3
                goto L_0x0087
            L_0x0067:
                java.lang.String r13 = "strikeout"
                boolean r4 = r4.equals(r13)
                if (r4 != 0) goto L_0x0070
                goto L_0x002d
            L_0x0070:
                r4 = 2
                goto L_0x0087
            L_0x0072:
                java.lang.String r13 = "underline"
                boolean r4 = r4.equals(r13)
                if (r4 != 0) goto L_0x007b
                goto L_0x002d
            L_0x007b:
                r4 = 1
                goto L_0x0087
            L_0x007d:
                java.lang.String r13 = "italic"
                boolean r4 = r4.equals(r13)
                if (r4 != 0) goto L_0x0086
                goto L_0x002d
            L_0x0086:
                r4 = 0
            L_0x0087:
                switch(r4) {
                    case 0: goto L_0x0099;
                    case 1: goto L_0x0097;
                    case 2: goto L_0x0095;
                    case 3: goto L_0x0093;
                    case 4: goto L_0x0091;
                    case 5: goto L_0x008f;
                    case 6: goto L_0x008d;
                    case 7: goto L_0x008b;
                    default: goto L_0x008a;
                }
            L_0x008a:
                goto L_0x009a
            L_0x008b:
                r6 = r3
                goto L_0x009a
            L_0x008d:
                r8 = r3
                goto L_0x009a
            L_0x008f:
                r5 = r3
                goto L_0x009a
            L_0x0091:
                r9 = r3
                goto L_0x009a
            L_0x0093:
                r7 = r3
                goto L_0x009a
            L_0x0095:
                r12 = r3
                goto L_0x009a
            L_0x0097:
                r11 = r3
                goto L_0x009a
            L_0x0099:
                r10 = r3
            L_0x009a:
                int r3 = r3 + 1
                goto L_0x0016
            L_0x009e:
                if (r5 == r1) goto L_0x00a8
                com.google.android.exoplayer2.text.ssa.SsaStyle$a r0 = new com.google.android.exoplayer2.text.ssa.SsaStyle$a
                int r13 = r14.length
                r4 = r0
                r4.<init>(r5, r6, r7, r8, r9, r10, r11, r12, r13)
                goto L_0x00a9
            L_0x00a8:
                r0 = 0
            L_0x00a9:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.ssa.SsaStyle.a.fromFormatLine(java.lang.String):com.google.android.exoplayer2.text.ssa.SsaStyle$a");
        }
    }

    public static final class b {
        public static final Pattern c = Pattern.compile("\\{([^}]*)\\}");
        public static final Pattern d = Pattern.compile(Util.formatInvariant("\\\\pos\\((%1$s),(%1$s)\\)", "\\s*\\d+(?:\\.\\d+)?\\s*"));
        public static final Pattern e = Pattern.compile(Util.formatInvariant("\\\\move\\(%1$s,%1$s,(%1$s),(%1$s)(?:,%1$s,%1$s)?\\)", "\\s*\\d+(?:\\.\\d+)?\\s*"));
        public static final Pattern f = Pattern.compile("\\\\an(\\d+)");
        public final int a;
        @Nullable
        public final PointF b;

        public b(int i, @Nullable PointF pointF) {
            this.a = i;
            this.b = pointF;
        }

        @Nullable
        public static PointF a(String str) {
            String str2;
            String str3;
            Matcher matcher = d.matcher(str);
            Matcher matcher2 = e.matcher(str);
            boolean find = matcher.find();
            boolean find2 = matcher2.find();
            if (find) {
                if (find2) {
                    StringBuilder sb = new StringBuilder(y2.c(str, 82));
                    sb.append("Override has both \\pos(x,y) and \\move(x1,y1,x2,y2); using \\pos values. override='");
                    sb.append(str);
                    sb.append("'");
                    Log.i("SsaStyle.Overrides", sb.toString());
                }
                str2 = matcher.group(1);
                str3 = matcher.group(2);
            } else if (!find2) {
                return null;
            } else {
                str2 = matcher2.group(1);
                str3 = matcher2.group(2);
            }
            return new PointF(Float.parseFloat(((String) Assertions.checkNotNull(str2)).trim()), Float.parseFloat(((String) Assertions.checkNotNull(str3)).trim()));
        }

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:8:0x0021 */
        /* JADX WARNING: Removed duplicated region for block: B:11:0x002d A[Catch:{ RuntimeException -> 0x003c }] */
        /* JADX WARNING: Removed duplicated region for block: B:13:0x003e  */
        /* JADX WARNING: Removed duplicated region for block: B:15:0x0041  */
        /* JADX WARNING: Removed duplicated region for block: B:20:0x0009 A[SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static com.google.android.exoplayer2.text.ssa.SsaStyle.b parseFromDialogue(java.lang.String r6) {
            /*
                java.util.regex.Pattern r0 = c
                java.util.regex.Matcher r6 = r0.matcher(r6)
                r0 = -1
                r1 = 0
                r2 = -1
            L_0x0009:
                boolean r3 = r6.find()
                if (r3 == 0) goto L_0x0043
                r3 = 1
                java.lang.String r4 = r6.group(r3)
                java.lang.Object r4 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r4)
                java.lang.String r4 = (java.lang.String) r4
                android.graphics.PointF r5 = a(r4)     // Catch:{ RuntimeException -> 0x0021 }
                if (r5 == 0) goto L_0x0021
                r1 = r5
            L_0x0021:
                java.util.regex.Pattern r5 = f     // Catch:{ RuntimeException -> 0x003c }
                java.util.regex.Matcher r4 = r5.matcher(r4)     // Catch:{ RuntimeException -> 0x003c }
                boolean r5 = r4.find()     // Catch:{ RuntimeException -> 0x003c }
                if (r5 == 0) goto L_0x003e
                java.lang.String r3 = r4.group(r3)     // Catch:{ RuntimeException -> 0x003c }
                java.lang.Object r3 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r3)     // Catch:{ RuntimeException -> 0x003c }
                java.lang.String r3 = (java.lang.String) r3     // Catch:{ RuntimeException -> 0x003c }
                int r3 = com.google.android.exoplayer2.text.ssa.SsaStyle.a(r3)     // Catch:{ RuntimeException -> 0x003c }
                goto L_0x003f
            L_0x003c:
                goto L_0x0009
            L_0x003e:
                r3 = -1
            L_0x003f:
                if (r3 == r0) goto L_0x0009
                r2 = r3
                goto L_0x0009
            L_0x0043:
                com.google.android.exoplayer2.text.ssa.SsaStyle$b r6 = new com.google.android.exoplayer2.text.ssa.SsaStyle$b
                r6.<init>(r2, r1)
                return r6
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.ssa.SsaStyle.b.parseFromDialogue(java.lang.String):com.google.android.exoplayer2.text.ssa.SsaStyle$b");
        }

        public static String stripStyleOverrides(String str) {
            return c.matcher(str).replaceAll("");
        }
    }

    public SsaStyle(String str, int i, @ColorInt @Nullable Integer num, float f2, boolean z, boolean z2, boolean z3, boolean z4) {
        this.a = str;
        this.b = i;
        this.c = num;
        this.d = f2;
        this.e = z;
        this.f = z2;
        this.g = z3;
        this.h = z4;
    }

    public static int a(String str) {
        String str2;
        boolean z;
        try {
            int parseInt = Integer.parseInt(str.trim());
            switch (parseInt) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    z = true;
                    break;
                default:
                    z = false;
                    break;
            }
            if (z) {
                return parseInt;
            }
        } catch (NumberFormatException unused) {
        }
        String valueOf = String.valueOf(str);
        if (valueOf.length() != 0) {
            str2 = "Ignoring unknown alignment: ".concat(valueOf);
        } else {
            str2 = new String("Ignoring unknown alignment: ");
        }
        Log.w("SsaStyle", str2);
        return -1;
    }

    public static boolean b(String str) {
        try {
            int parseInt = Integer.parseInt(str);
            if (parseInt == 1 || parseInt == -1) {
                return true;
            }
            return false;
        } catch (NumberFormatException e2) {
            StringBuilder sb = new StringBuilder(y2.c(str, 33));
            sb.append("Failed to parse boolean value: '");
            sb.append(str);
            sb.append("'");
            Log.w("SsaStyle", sb.toString(), e2);
            return false;
        }
    }

    @Nullable
    public static SsaStyle fromStyleLine(String str, a aVar) {
        int i;
        Integer num;
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        String str2 = str;
        a aVar2 = aVar;
        Assertions.checkArgument(str2.startsWith("Style:"));
        String[] split = TextUtils.split(str2.substring(6), ",");
        int length = split.length;
        int i2 = aVar2.i;
        if (length != i2) {
            Log.w("SsaStyle", Util.formatInvariant("Skipping malformed 'Style:' line (expected %s values, found %s): '%s'", Integer.valueOf(i2), Integer.valueOf(split.length), str2));
            return null;
        }
        try {
            String trim = split[aVar2.a].trim();
            int i3 = aVar2.b;
            if (i3 != -1) {
                i = a(split[i3].trim());
            } else {
                i = -1;
            }
            int i4 = aVar2.c;
            if (i4 != -1) {
                num = parseColor(split[i4].trim());
            } else {
                num = null;
            }
            int i5 = aVar2.d;
            float f2 = -3.4028235E38f;
            if (i5 != -1) {
                String trim2 = split[i5].trim();
                try {
                    f2 = Float.parseFloat(trim2);
                } catch (NumberFormatException e2) {
                    NumberFormatException numberFormatException = e2;
                    StringBuilder sb = new StringBuilder(String.valueOf(trim2).length() + 29);
                    sb.append("Failed to parse font size: '");
                    sb.append(trim2);
                    sb.append("'");
                    Log.w("SsaStyle", sb.toString(), numberFormatException);
                }
            }
            int i6 = aVar2.e;
            if (i6 != -1) {
                z = b(split[i6].trim());
            } else {
                z = false;
            }
            int i7 = aVar2.f;
            if (i7 != -1) {
                z2 = b(split[i7].trim());
            } else {
                z2 = false;
            }
            int i8 = aVar2.g;
            if (i8 != -1) {
                z3 = b(split[i8].trim());
            } else {
                z3 = false;
            }
            int i9 = aVar2.h;
            if (i9 != -1) {
                z4 = b(split[i9].trim());
            } else {
                z4 = false;
            }
            return new SsaStyle(trim, i, num, f2, z, z2, z3, z4);
        } catch (RuntimeException e3) {
            StringBuilder sb2 = new StringBuilder(str.length() + 36);
            sb2.append("Skipping malformed 'Style:' line: '");
            sb2.append(str2);
            sb2.append("'");
            Log.w("SsaStyle", sb2.toString(), e3);
            return null;
        }
    }

    @ColorInt
    @Nullable
    public static Integer parseColor(String str) {
        long j;
        boolean z;
        try {
            if (str.startsWith("&H")) {
                j = Long.parseLong(str.substring(2), 16);
            } else {
                j = Long.parseLong(str);
            }
            if (j <= 4294967295L) {
                z = true;
            } else {
                z = false;
            }
            Assertions.checkArgument(z);
            int a2 = y3.a(((j >> 24) & 255) ^ 255);
            int a3 = y3.a((j >> 16) & 255);
            return Integer.valueOf(Color.argb(a2, y3.a(j & 255), y3.a((j >> 8) & 255), a3));
        } catch (IllegalArgumentException e2) {
            StringBuilder sb = new StringBuilder(y2.c(str, 36));
            sb.append("Failed to parse color expression: '");
            sb.append(str);
            sb.append("'");
            Log.w("SsaStyle", sb.toString(), e2);
            return null;
        }
    }
}
