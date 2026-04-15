package defpackage;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.util.regex.Pattern;

/* renamed from: x0  reason: default package */
public final class x0 {
    public static final Pattern c = Pattern.compile("\\[voice=\"([^\"]*)\"\\]");
    public final ParsableByteArray a = new ParsableByteArray();
    public final StringBuilder b = new StringBuilder();

    public static String a(ParsableByteArray parsableByteArray, StringBuilder sb) {
        boolean z = false;
        sb.setLength(0);
        int position = parsableByteArray.getPosition();
        int limit = parsableByteArray.limit();
        while (position < limit && !z) {
            char c2 = (char) parsableByteArray.getData()[position];
            if ((c2 < 'A' || c2 > 'Z') && ((c2 < 'a' || c2 > 'z') && !((c2 >= '0' && c2 <= '9') || c2 == '#' || c2 == '-' || c2 == '.' || c2 == '_'))) {
                z = true;
            } else {
                position++;
                sb.append(c2);
            }
        }
        parsableByteArray.skipBytes(position - parsableByteArray.getPosition());
        return sb.toString();
    }

    @Nullable
    public static String b(ParsableByteArray parsableByteArray, StringBuilder sb) {
        c(parsableByteArray);
        if (parsableByteArray.bytesLeft() == 0) {
            return null;
        }
        String a2 = a(parsableByteArray, sb);
        if (!"".equals(a2)) {
            return a2;
        }
        StringBuilder sb2 = new StringBuilder(1);
        sb2.append((char) parsableByteArray.readUnsignedByte());
        return sb2.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:35:0x0074 A[LOOP:1: B:2:0x0002->B:35:0x0074, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x0001 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void c(com.google.android.exoplayer2.util.ParsableByteArray r8) {
        /*
            r0 = 1
        L_0x0001:
            r1 = 1
        L_0x0002:
            int r2 = r8.bytesLeft()
            if (r2 <= 0) goto L_0x0076
            if (r1 == 0) goto L_0x0076
            int r1 = r8.getPosition()
            byte[] r2 = r8.getData()
            byte r1 = r2[r1]
            char r1 = (char) r1
            r2 = 9
            r3 = 0
            if (r1 == r2) goto L_0x002c
            r2 = 10
            if (r1 == r2) goto L_0x002c
            r2 = 12
            if (r1 == r2) goto L_0x002c
            r2 = 13
            if (r1 == r2) goto L_0x002c
            r2 = 32
            if (r1 == r2) goto L_0x002c
            r1 = 0
            goto L_0x0030
        L_0x002c:
            r8.skipBytes(r0)
            r1 = 1
        L_0x0030:
            if (r1 != 0) goto L_0x0001
            int r1 = r8.getPosition()
            int r2 = r8.limit()
            byte[] r4 = r8.getData()
            int r5 = r1 + 2
            if (r5 > r2) goto L_0x0070
            int r5 = r1 + 1
            byte r1 = r4[r1]
            r6 = 47
            if (r1 != r6) goto L_0x0070
            int r1 = r5 + 1
            byte r5 = r4[r5]
            r7 = 42
            if (r5 != r7) goto L_0x0070
        L_0x0052:
            int r5 = r1 + 1
            if (r5 >= r2) goto L_0x0066
            byte r1 = r4[r1]
            char r1 = (char) r1
            if (r1 != r7) goto L_0x0064
            byte r1 = r4[r5]
            char r1 = (char) r1
            if (r1 != r6) goto L_0x0064
            int r2 = r5 + 1
            r1 = r2
            goto L_0x0052
        L_0x0064:
            r1 = r5
            goto L_0x0052
        L_0x0066:
            int r1 = r8.getPosition()
            int r2 = r2 - r1
            r8.skipBytes(r2)
            r1 = 1
            goto L_0x0071
        L_0x0070:
            r1 = 0
        L_0x0071:
            if (r1 == 0) goto L_0x0074
            goto L_0x0001
        L_0x0074:
            r1 = 0
            goto L_0x0002
        L_0x0076:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.x0.c(com.google.android.exoplayer2.util.ParsableByteArray):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00a2, code lost:
        if (")".equals(b(r6, r1)) == false) goto L_0x003a;
     */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00b1 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00b2  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<com.google.android.exoplayer2.text.webvtt.WebvttCssStyle> parseBlock(com.google.android.exoplayer2.util.ParsableByteArray r18) {
        /*
            r17 = this;
            r0 = r17
            java.lang.StringBuilder r1 = r0.b
            r2 = 0
            r1.setLength(r2)
            int r3 = r18.getPosition()
        L_0x000c:
            java.lang.String r4 = r18.readLine()
            boolean r4 = android.text.TextUtils.isEmpty(r4)
            if (r4 == 0) goto L_0x000c
            byte[] r4 = r18.getData()
            int r5 = r18.getPosition()
            com.google.android.exoplayer2.util.ParsableByteArray r6 = r0.a
            r6.reset(r4, r5)
            r6.setPosition(r3)
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
        L_0x002b:
            c(r6)
            int r4 = r6.bytesLeft()
            java.lang.String r5 = "{"
            java.lang.String r7 = ""
            r9 = 1
            r10 = 5
            if (r4 >= r10) goto L_0x003d
        L_0x003a:
            r4 = 0
            goto L_0x00a5
        L_0x003d:
            java.lang.String r4 = r6.readString(r10)
            java.lang.String r10 = "::cue"
            boolean r4 = r10.equals(r4)
            if (r4 != 0) goto L_0x004a
            goto L_0x003a
        L_0x004a:
            int r4 = r6.getPosition()
            java.lang.String r10 = b(r6, r1)
            if (r10 != 0) goto L_0x0055
            goto L_0x003a
        L_0x0055:
            boolean r11 = r5.equals(r10)
            if (r11 == 0) goto L_0x0060
            r6.setPosition(r4)
            r4 = r7
            goto L_0x00a5
        L_0x0060:
            java.lang.String r4 = "("
            boolean r4 = r4.equals(r10)
            if (r4 == 0) goto L_0x0097
            int r4 = r6.getPosition()
            int r10 = r6.limit()
            r11 = 0
        L_0x0071:
            if (r4 >= r10) goto L_0x0087
            if (r11 != 0) goto L_0x0087
            byte[] r11 = r6.getData()
            int r12 = r4 + 1
            byte r4 = r11[r4]
            char r4 = (char) r4
            r11 = 41
            if (r4 != r11) goto L_0x0084
            r11 = 1
            goto L_0x0085
        L_0x0084:
            r11 = 0
        L_0x0085:
            r4 = r12
            goto L_0x0071
        L_0x0087:
            int r4 = r4 + -1
            int r10 = r6.getPosition()
            int r4 = r4 - r10
            java.lang.String r4 = r6.readString(r4)
            java.lang.String r4 = r4.trim()
            goto L_0x0098
        L_0x0097:
            r4 = 0
        L_0x0098:
            java.lang.String r10 = b(r6, r1)
            java.lang.String r11 = ")"
            boolean r10 = r11.equals(r10)
            if (r10 != 0) goto L_0x00a5
            goto L_0x003a
        L_0x00a5:
            if (r4 == 0) goto L_0x0268
            java.lang.String r10 = b(r6, r1)
            boolean r5 = r5.equals(r10)
            if (r5 != 0) goto L_0x00b2
            return r3
        L_0x00b2:
            com.google.android.exoplayer2.text.webvtt.WebvttCssStyle r5 = new com.google.android.exoplayer2.text.webvtt.WebvttCssStyle
            r5.<init>()
            boolean r10 = r7.equals(r4)
            if (r10 == 0) goto L_0x00be
            goto L_0x0119
        L_0x00be:
            r10 = 91
            int r10 = r4.indexOf(r10)
            r11 = -1
            if (r10 == r11) goto L_0x00e8
            java.util.regex.Pattern r12 = c
            java.lang.String r13 = r4.substring(r10)
            java.util.regex.Matcher r12 = r12.matcher(r13)
            boolean r13 = r12.matches()
            if (r13 == 0) goto L_0x00e4
            java.lang.String r12 = r12.group(r9)
            java.lang.Object r12 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r12)
            java.lang.String r12 = (java.lang.String) r12
            r5.setTargetVoice(r12)
        L_0x00e4:
            java.lang.String r4 = r4.substring(r2, r10)
        L_0x00e8:
            java.lang.String r10 = "\\."
            java.lang.String[] r4 = com.google.android.exoplayer2.util.Util.split(r4, r10)
            r10 = r4[r2]
            r12 = 35
            int r12 = r10.indexOf(r12)
            if (r12 == r11) goto L_0x0109
            java.lang.String r11 = r10.substring(r2, r12)
            r5.setTargetTagName(r11)
            int r12 = r12 + 1
            java.lang.String r10 = r10.substring(r12)
            r5.setTargetId(r10)
            goto L_0x010c
        L_0x0109:
            r5.setTargetTagName(r10)
        L_0x010c:
            int r10 = r4.length
            if (r10 <= r9) goto L_0x0119
            int r10 = r4.length
            java.lang.Object[] r4 = com.google.android.exoplayer2.util.Util.nullSafeArrayCopyOfRange(r4, r9, r10)
            java.lang.String[] r4 = (java.lang.String[]) r4
            r5.setTargetClasses(r4)
        L_0x0119:
            r4 = 0
            r10 = 0
        L_0x011b:
            java.lang.String r11 = "}"
            if (r4 != 0) goto L_0x025c
            int r4 = r6.getPosition()
            java.lang.String r10 = b(r6, r1)
            if (r10 == 0) goto L_0x0132
            boolean r12 = r11.equals(r10)
            if (r12 == 0) goto L_0x0130
            goto L_0x0132
        L_0x0130:
            r12 = 0
            goto L_0x0133
        L_0x0132:
            r12 = 1
        L_0x0133:
            if (r12 != 0) goto L_0x0258
            r6.setPosition(r4)
            c(r6)
            java.lang.String r4 = a(r6, r1)
            boolean r13 = r7.equals(r4)
            if (r13 == 0) goto L_0x0147
            goto L_0x0258
        L_0x0147:
            java.lang.String r13 = ":"
            java.lang.String r14 = b(r6, r1)
            boolean r13 = r13.equals(r14)
            if (r13 != 0) goto L_0x0155
            goto L_0x0258
        L_0x0155:
            c(r6)
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r14 = 0
        L_0x015e:
            java.lang.String r15 = ";"
            if (r14 != 0) goto L_0x0186
            int r2 = r6.getPosition()
            java.lang.String r8 = b(r6, r1)
            if (r8 != 0) goto L_0x016e
            r2 = 0
            goto L_0x018a
        L_0x016e:
            boolean r16 = r11.equals(r8)
            if (r16 != 0) goto L_0x0180
            boolean r15 = r15.equals(r8)
            if (r15 == 0) goto L_0x017b
            goto L_0x0180
        L_0x017b:
            r13.append(r8)
            r2 = 0
            goto L_0x015e
        L_0x0180:
            r6.setPosition(r2)
            r2 = 0
            r14 = 1
            goto L_0x015e
        L_0x0186:
            java.lang.String r2 = r13.toString()
        L_0x018a:
            if (r2 == 0) goto L_0x0258
            boolean r8 = r7.equals(r2)
            if (r8 == 0) goto L_0x0194
            goto L_0x0258
        L_0x0194:
            int r8 = r6.getPosition()
            java.lang.String r13 = b(r6, r1)
            boolean r14 = r15.equals(r13)
            if (r14 == 0) goto L_0x01a3
            goto L_0x01ac
        L_0x01a3:
            boolean r11 = r11.equals(r13)
            if (r11 == 0) goto L_0x0258
            r6.setPosition(r8)
        L_0x01ac:
            java.lang.String r8 = "color"
            boolean r8 = r8.equals(r4)
            if (r8 == 0) goto L_0x01bd
            int r2 = com.google.android.exoplayer2.util.ColorParser.parseCssColor(r2)
            r5.setFontColor(r2)
            goto L_0x0258
        L_0x01bd:
            java.lang.String r8 = "background-color"
            boolean r8 = r8.equals(r4)
            if (r8 == 0) goto L_0x01ce
            int r2 = com.google.android.exoplayer2.util.ColorParser.parseCssColor(r2)
            r5.setBackgroundColor(r2)
            goto L_0x0258
        L_0x01ce:
            java.lang.String r8 = "ruby-position"
            boolean r8 = r8.equals(r4)
            if (r8 == 0) goto L_0x01f1
            java.lang.String r4 = "over"
            boolean r4 = r4.equals(r2)
            if (r4 == 0) goto L_0x01e3
            r5.setRubyPosition(r9)
            goto L_0x0258
        L_0x01e3:
            java.lang.String r4 = "under"
            boolean r2 = r4.equals(r2)
            if (r2 == 0) goto L_0x0258
            r2 = 2
            r5.setRubyPosition(r2)
            goto L_0x0258
        L_0x01f1:
            java.lang.String r8 = "text-combine-upright"
            boolean r8 = r8.equals(r4)
            if (r8 == 0) goto L_0x0211
            java.lang.String r4 = "all"
            boolean r4 = r4.equals(r2)
            if (r4 != 0) goto L_0x020c
            java.lang.String r4 = "digits"
            boolean r2 = r2.startsWith(r4)
            if (r2 == 0) goto L_0x020a
            goto L_0x020c
        L_0x020a:
            r2 = 0
            goto L_0x020d
        L_0x020c:
            r2 = 1
        L_0x020d:
            r5.setCombineUpright(r2)
            goto L_0x0258
        L_0x0211:
            java.lang.String r8 = "text-decoration"
            boolean r8 = r8.equals(r4)
            if (r8 == 0) goto L_0x0225
            java.lang.String r4 = "underline"
            boolean r2 = r4.equals(r2)
            if (r2 == 0) goto L_0x0258
            r5.setUnderline(r9)
            goto L_0x0258
        L_0x0225:
            java.lang.String r8 = "font-family"
            boolean r8 = r8.equals(r4)
            if (r8 == 0) goto L_0x0231
            r5.setFontFamily(r2)
            goto L_0x0258
        L_0x0231:
            java.lang.String r8 = "font-weight"
            boolean r8 = r8.equals(r4)
            if (r8 == 0) goto L_0x0245
            java.lang.String r4 = "bold"
            boolean r2 = r4.equals(r2)
            if (r2 == 0) goto L_0x0258
            r5.setBold(r9)
            goto L_0x0258
        L_0x0245:
            java.lang.String r8 = "font-style"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x0258
            java.lang.String r4 = "italic"
            boolean r2 = r4.equals(r2)
            if (r2 == 0) goto L_0x0258
            r5.setItalic(r9)
        L_0x0258:
            r4 = r12
            r2 = 0
            goto L_0x011b
        L_0x025c:
            boolean r2 = r11.equals(r10)
            if (r2 == 0) goto L_0x0265
            r3.add(r5)
        L_0x0265:
            r2 = 0
            goto L_0x002b
        L_0x0268:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.x0.parseBlock(com.google.android.exoplayer2.util.ParsableByteArray):java.util.List");
    }
}
