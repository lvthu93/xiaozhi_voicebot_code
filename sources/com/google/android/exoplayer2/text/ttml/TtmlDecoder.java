package com.google.android.exoplayer2.text.ttml;

import android.text.Layout;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.text.SimpleSubtitleDecoder;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.text.SubtitleDecoderException;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.util.XmlPullParserUtil;
import com.google.common.base.Ascii;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public final class TtmlDecoder extends SimpleSubtitleDecoder {
    public static final Pattern p = Pattern.compile("^([0-9][0-9]+):([0-9][0-9]):([0-9][0-9])(?:(\\.[0-9]+)|:([0-9][0-9])(?:\\.([0-9]+))?)?$");
    public static final Pattern q = Pattern.compile("^([0-9]+(?:\\.[0-9]+)?)(h|m|s|ms|f|t)$");
    public static final Pattern r = Pattern.compile("^(([0-9]*.)?[0-9]+)(px|em|%)$");
    public static final Pattern s = Pattern.compile("^([-+]?\\d+\\.?\\d*?)%$");
    public static final Pattern t = Pattern.compile("^(\\d+\\.?\\d*?)% (\\d+\\.?\\d*?)%$");
    public static final Pattern u = Pattern.compile("^(\\d+\\.?\\d*?)px (\\d+\\.?\\d*?)px$");
    public static final Pattern v = Pattern.compile("^(\\d+) (\\d+)$");
    public static final b w = new b(30.0f, 1, 1);
    public static final a x = new a(15);
    public final XmlPullParserFactory o;

    public static final class a {
        public final int a;

        public a(int i) {
            this.a = i;
        }
    }

    public static final class b {
        public final float a;
        public final int b;
        public final int c;

        public b(float f, int i, int i2) {
            this.a = f;
            this.b = i;
            this.c = i2;
        }
    }

    public static final class c {
        public final int a;
        public final int b;

        public c(int i, int i2) {
            this.a = i;
            this.b = i2;
        }
    }

    public TtmlDecoder() {
        super("TtmlDecoder");
        try {
            XmlPullParserFactory newInstance = XmlPullParserFactory.newInstance();
            this.o = newInstance;
            newInstance.setNamespaceAware(true);
        } catch (XmlPullParserException e) {
            throw new RuntimeException("Couldn't create XmlPullParserFactory instance", e);
        }
    }

    public static TtmlStyle f(@Nullable TtmlStyle ttmlStyle) {
        return ttmlStyle == null ? new TtmlStyle() : ttmlStyle;
    }

    public static boolean g(String str) {
        if (str.equals("tt") || str.equals("head") || str.equals("body") || str.equals("div") || str.equals("p") || str.equals("span") || str.equals("br") || str.equals("style") || str.equals("styling") || str.equals("layout") || str.equals("region") || str.equals("metadata") || str.equals("image") || str.equals("data") || str.equals("information")) {
            return true;
        }
        return false;
    }

    @Nullable
    public static Layout.Alignment h(String str) {
        String lowerCase = Ascii.toLowerCase(str);
        lowerCase.getClass();
        char c2 = 65535;
        switch (lowerCase.hashCode()) {
            case -1364013995:
                if (lowerCase.equals("center")) {
                    c2 = 0;
                    break;
                }
                break;
            case 100571:
                if (lowerCase.equals("end")) {
                    c2 = 1;
                    break;
                }
                break;
            case 3317767:
                if (lowerCase.equals("left")) {
                    c2 = 2;
                    break;
                }
                break;
            case 108511772:
                if (lowerCase.equals("right")) {
                    c2 = 3;
                    break;
                }
                break;
            case 109757538:
                if (lowerCase.equals("start")) {
                    c2 = 4;
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
                return Layout.Alignment.ALIGN_CENTER;
            case 1:
            case 3:
                return Layout.Alignment.ALIGN_OPPOSITE;
            case 2:
            case 4:
                return Layout.Alignment.ALIGN_NORMAL;
            default:
                return null;
        }
    }

    public static a i(XmlPullParser xmlPullParser, a aVar) throws SubtitleDecoderException {
        String str;
        String str2;
        String attributeValue = xmlPullParser.getAttributeValue("http://www.w3.org/ns/ttml#parameter", "cellResolution");
        if (attributeValue == null) {
            return aVar;
        }
        Matcher matcher = v.matcher(attributeValue);
        if (!matcher.matches()) {
            if (attributeValue.length() != 0) {
                str2 = "Ignoring malformed cell resolution: ".concat(attributeValue);
            } else {
                str2 = new String("Ignoring malformed cell resolution: ");
            }
            Log.w("TtmlDecoder", str2);
            return aVar;
        }
        try {
            int parseInt = Integer.parseInt((String) Assertions.checkNotNull(matcher.group(1)));
            int parseInt2 = Integer.parseInt((String) Assertions.checkNotNull(matcher.group(2)));
            if (parseInt != 0 && parseInt2 != 0) {
                return new a(parseInt2);
            }
            StringBuilder sb = new StringBuilder(47);
            sb.append("Invalid cell resolution ");
            sb.append(parseInt);
            sb.append(" ");
            sb.append(parseInt2);
            throw new SubtitleDecoderException(sb.toString());
        } catch (NumberFormatException unused) {
            if (attributeValue.length() != 0) {
                str = "Ignoring malformed cell resolution: ".concat(attributeValue);
            } else {
                str = new String("Ignoring malformed cell resolution: ");
            }
            Log.w("TtmlDecoder", str);
            return aVar;
        }
    }

    public static void j(String str, TtmlStyle ttmlStyle) throws SubtitleDecoderException {
        Matcher matcher;
        String[] split = Util.split(str, "\\s+");
        int length = split.length;
        Pattern pattern = r;
        if (length == 1) {
            matcher = pattern.matcher(str);
        } else if (split.length == 2) {
            matcher = pattern.matcher(split[1]);
            Log.w("TtmlDecoder", "Multiple values in fontSize attribute. Picking the second value for vertical font size and ignoring the first.");
        } else {
            int length2 = split.length;
            StringBuilder sb = new StringBuilder(52);
            sb.append("Invalid number of entries for fontSize: ");
            sb.append(length2);
            sb.append(".");
            throw new SubtitleDecoderException(sb.toString());
        }
        if (matcher.matches()) {
            String str2 = (String) Assertions.checkNotNull(matcher.group(3));
            str2.getClass();
            str2.hashCode();
            char c2 = 65535;
            switch (str2.hashCode()) {
                case 37:
                    if (str2.equals("%")) {
                        c2 = 0;
                        break;
                    }
                    break;
                case 3240:
                    if (str2.equals("em")) {
                        c2 = 1;
                        break;
                    }
                    break;
                case 3592:
                    if (str2.equals("px")) {
                        c2 = 2;
                        break;
                    }
                    break;
            }
            switch (c2) {
                case 0:
                    ttmlStyle.setFontSizeUnit(3);
                    break;
                case 1:
                    ttmlStyle.setFontSizeUnit(2);
                    break;
                case 2:
                    ttmlStyle.setFontSizeUnit(1);
                    break;
                default:
                    StringBuilder sb2 = new StringBuilder(str2.length() + 30);
                    sb2.append("Invalid unit for fontSize: '");
                    sb2.append(str2);
                    sb2.append("'.");
                    throw new SubtitleDecoderException(sb2.toString());
            }
            ttmlStyle.setFontSize(Float.parseFloat((String) Assertions.checkNotNull(matcher.group(1))));
            return;
        }
        StringBuilder sb3 = new StringBuilder(y2.c(str, 36));
        sb3.append("Invalid expression for fontSize: '");
        sb3.append(str);
        sb3.append("'.");
        throw new SubtitleDecoderException(sb3.toString());
    }

    public static b k(XmlPullParser xmlPullParser) throws SubtitleDecoderException {
        int i;
        float f;
        int i2;
        String attributeValue = xmlPullParser.getAttributeValue("http://www.w3.org/ns/ttml#parameter", "frameRate");
        if (attributeValue != null) {
            i = Integer.parseInt(attributeValue);
        } else {
            i = 30;
        }
        String attributeValue2 = xmlPullParser.getAttributeValue("http://www.w3.org/ns/ttml#parameter", "frameRateMultiplier");
        if (attributeValue2 != null) {
            String[] split = Util.split(attributeValue2, " ");
            if (split.length == 2) {
                f = ((float) Integer.parseInt(split[0])) / ((float) Integer.parseInt(split[1]));
            } else {
                throw new SubtitleDecoderException("frameRateMultiplier doesn't have 2 parts");
            }
        } else {
            f = 1.0f;
        }
        b bVar = w;
        int i3 = bVar.b;
        String attributeValue3 = xmlPullParser.getAttributeValue("http://www.w3.org/ns/ttml#parameter", "subFrameRate");
        if (attributeValue3 != null) {
            i3 = Integer.parseInt(attributeValue3);
        }
        String attributeValue4 = xmlPullParser.getAttributeValue("http://www.w3.org/ns/ttml#parameter", "tickRate");
        if (attributeValue4 != null) {
            i2 = Integer.parseInt(attributeValue4);
        } else {
            i2 = bVar.c;
        }
        return new b(((float) i) * f, i3, i2);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:92:0x0216, code lost:
        if (r6.equals("tb") == false) goto L_0x0218;
     */
    /* JADX WARNING: Removed duplicated region for block: B:128:0x029f  */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x01e0  */
    /* JADX WARNING: Removed duplicated region for block: B:95:0x021b A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void l(org.xmlpull.v1.XmlPullParser r19, java.util.HashMap r20, com.google.android.exoplayer2.text.ttml.TtmlDecoder.a r21, @androidx.annotation.Nullable com.google.android.exoplayer2.text.ttml.TtmlDecoder.c r22, java.util.HashMap r23, java.util.HashMap r24) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        /*
            r0 = r19
            r1 = r20
            r2 = r22
        L_0x0006:
            r19.next()
            java.lang.String r3 = "style"
            boolean r4 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r0, r3)
            r5 = 0
            if (r4 == 0) goto L_0x0052
            java.lang.String r3 = com.google.android.exoplayer2.util.XmlPullParserUtil.getAttributeValue(r0, r3)
            com.google.android.exoplayer2.text.ttml.TtmlStyle r4 = new com.google.android.exoplayer2.text.ttml.TtmlStyle
            r4.<init>()
            com.google.android.exoplayer2.text.ttml.TtmlStyle r4 = n(r0, r4)
            if (r3 == 0) goto L_0x0045
            java.lang.String r3 = r3.trim()
            boolean r6 = r3.isEmpty()
            if (r6 == 0) goto L_0x002e
            java.lang.String[] r3 = new java.lang.String[r5]
            goto L_0x0034
        L_0x002e:
            java.lang.String r6 = "\\s+"
            java.lang.String[] r3 = com.google.android.exoplayer2.util.Util.split(r3, r6)
        L_0x0034:
            int r6 = r3.length
        L_0x0035:
            if (r5 >= r6) goto L_0x0045
            r7 = r3[r5]
            java.lang.Object r7 = r1.get(r7)
            com.google.android.exoplayer2.text.ttml.TtmlStyle r7 = (com.google.android.exoplayer2.text.ttml.TtmlStyle) r7
            r4.chain(r7)
            int r5 = r5 + 1
            goto L_0x0035
        L_0x0045:
            java.lang.String r3 = r4.getId()
            if (r3 == 0) goto L_0x004e
            r1.put(r3, r4)
        L_0x004e:
            r5 = r23
            goto L_0x02d5
        L_0x0052:
            java.lang.String r3 = "region"
            boolean r3 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r0, r3)
            java.lang.String r4 = "id"
            if (r3 == 0) goto L_0x02a7
            java.lang.String r7 = com.google.android.exoplayer2.util.XmlPullParserUtil.getAttributeValue(r0, r4)
            if (r7 != 0) goto L_0x0064
            goto L_0x0299
        L_0x0064:
            java.lang.String r3 = "origin"
            java.lang.String r3 = com.google.android.exoplayer2.util.XmlPullParserUtil.getAttributeValue(r0, r3)
            java.lang.String r4 = "TtmlDecoder"
            if (r3 == 0) goto L_0x0294
            java.util.regex.Pattern r6 = t
            java.util.regex.Matcher r8 = r6.matcher(r3)
            java.util.regex.Pattern r9 = u
            java.util.regex.Matcher r10 = r9.matcher(r3)
            boolean r11 = r8.matches()
            r12 = 2
            r13 = 1
            java.lang.String r14 = "Ignoring region with malformed origin: "
            java.lang.String r15 = "Ignoring region with missing tts:extent: "
            r16 = 1120403456(0x42c80000, float:100.0)
            if (r11 == 0) goto L_0x00bf
            java.lang.String r10 = r8.group(r13)     // Catch:{ NumberFormatException -> 0x00a9 }
            java.lang.Object r10 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r10)     // Catch:{ NumberFormatException -> 0x00a9 }
            java.lang.String r10 = (java.lang.String) r10     // Catch:{ NumberFormatException -> 0x00a9 }
            float r10 = java.lang.Float.parseFloat(r10)     // Catch:{ NumberFormatException -> 0x00a9 }
            float r10 = r10 / r16
            java.lang.String r8 = r8.group(r12)     // Catch:{ NumberFormatException -> 0x00a9 }
            java.lang.Object r8 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r8)     // Catch:{ NumberFormatException -> 0x00a9 }
            java.lang.String r8 = (java.lang.String) r8     // Catch:{ NumberFormatException -> 0x00a9 }
            float r8 = java.lang.Float.parseFloat(r8)     // Catch:{ NumberFormatException -> 0x00a9 }
            float r8 = r8 / r16
            goto L_0x0107
        L_0x00a9:
            int r5 = r3.length()
            if (r5 == 0) goto L_0x00b5
            java.lang.String r3 = r14.concat(r3)
            goto L_0x00ba
        L_0x00b5:
            java.lang.String r3 = new java.lang.String
            r3.<init>(r14)
        L_0x00ba:
            com.google.android.exoplayer2.util.Log.w(r4, r3)
            goto L_0x0299
        L_0x00bf:
            boolean r8 = r10.matches()
            if (r8 == 0) goto L_0x027e
            if (r2 != 0) goto L_0x00dc
            int r5 = r3.length()
            if (r5 == 0) goto L_0x00d2
            java.lang.String r3 = r15.concat(r3)
            goto L_0x00d7
        L_0x00d2:
            java.lang.String r3 = new java.lang.String
            r3.<init>(r15)
        L_0x00d7:
            com.google.android.exoplayer2.util.Log.w(r4, r3)
            goto L_0x0299
        L_0x00dc:
            java.lang.String r8 = r10.group(r13)     // Catch:{ NumberFormatException -> 0x0269 }
            java.lang.Object r8 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r8)     // Catch:{ NumberFormatException -> 0x0269 }
            java.lang.String r8 = (java.lang.String) r8     // Catch:{ NumberFormatException -> 0x0269 }
            int r8 = java.lang.Integer.parseInt(r8)     // Catch:{ NumberFormatException -> 0x0269 }
            java.lang.String r10 = r10.group(r12)     // Catch:{ NumberFormatException -> 0x0269 }
            java.lang.Object r10 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r10)     // Catch:{ NumberFormatException -> 0x0269 }
            java.lang.String r10 = (java.lang.String) r10     // Catch:{ NumberFormatException -> 0x0269 }
            int r10 = java.lang.Integer.parseInt(r10)     // Catch:{ NumberFormatException -> 0x0269 }
            float r8 = (float) r8     // Catch:{ NumberFormatException -> 0x0269 }
            int r11 = r2.a     // Catch:{ NumberFormatException -> 0x0269 }
            float r11 = (float) r11     // Catch:{ NumberFormatException -> 0x0269 }
            float r8 = r8 / r11
            float r10 = (float) r10     // Catch:{ NumberFormatException -> 0x0269 }
            int r11 = r2.b     // Catch:{ NumberFormatException -> 0x0269 }
            float r11 = (float) r11
            float r10 = r10 / r11
            r18 = r10
            r10 = r8
            r8 = r18
        L_0x0107:
            java.lang.String r11 = "extent"
            java.lang.String r11 = com.google.android.exoplayer2.util.XmlPullParserUtil.getAttributeValue(r0, r11)
            if (r11 == 0) goto L_0x0263
            java.util.regex.Matcher r6 = r6.matcher(r11)
            java.util.regex.Matcher r9 = r9.matcher(r11)
            boolean r11 = r6.matches()
            java.lang.String r14 = "Ignoring region with malformed extent: "
            if (r11 == 0) goto L_0x0158
            java.lang.String r9 = r6.group(r13)     // Catch:{ NumberFormatException -> 0x0142 }
            java.lang.Object r9 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r9)     // Catch:{ NumberFormatException -> 0x0142 }
            java.lang.String r9 = (java.lang.String) r9     // Catch:{ NumberFormatException -> 0x0142 }
            float r9 = java.lang.Float.parseFloat(r9)     // Catch:{ NumberFormatException -> 0x0142 }
            float r9 = r9 / r16
            java.lang.String r6 = r6.group(r12)     // Catch:{ NumberFormatException -> 0x0142 }
            java.lang.Object r6 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r6)     // Catch:{ NumberFormatException -> 0x0142 }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ NumberFormatException -> 0x0142 }
            float r3 = java.lang.Float.parseFloat(r6)     // Catch:{ NumberFormatException -> 0x0142 }
            float r3 = r3 / r16
            r4 = r3
            r3 = r9
            goto L_0x019d
        L_0x0142:
            int r5 = r3.length()
            if (r5 == 0) goto L_0x014e
            java.lang.String r3 = r14.concat(r3)
            goto L_0x0153
        L_0x014e:
            java.lang.String r3 = new java.lang.String
            r3.<init>(r14)
        L_0x0153:
            com.google.android.exoplayer2.util.Log.w(r4, r3)
            goto L_0x0299
        L_0x0158:
            boolean r6 = r9.matches()
            if (r6 == 0) goto L_0x024d
            if (r2 != 0) goto L_0x0175
            int r5 = r3.length()
            if (r5 == 0) goto L_0x016b
            java.lang.String r3 = r15.concat(r3)
            goto L_0x0170
        L_0x016b:
            java.lang.String r3 = new java.lang.String
            r3.<init>(r15)
        L_0x0170:
            com.google.android.exoplayer2.util.Log.w(r4, r3)
            goto L_0x0299
        L_0x0175:
            java.lang.String r6 = r9.group(r13)     // Catch:{ NumberFormatException -> 0x0238 }
            java.lang.Object r6 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r6)     // Catch:{ NumberFormatException -> 0x0238 }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ NumberFormatException -> 0x0238 }
            int r6 = java.lang.Integer.parseInt(r6)     // Catch:{ NumberFormatException -> 0x0238 }
            java.lang.String r9 = r9.group(r12)     // Catch:{ NumberFormatException -> 0x0238 }
            java.lang.Object r9 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r9)     // Catch:{ NumberFormatException -> 0x0238 }
            java.lang.String r9 = (java.lang.String) r9     // Catch:{ NumberFormatException -> 0x0238 }
            int r9 = java.lang.Integer.parseInt(r9)     // Catch:{ NumberFormatException -> 0x0238 }
            float r6 = (float) r6     // Catch:{ NumberFormatException -> 0x0238 }
            int r11 = r2.a     // Catch:{ NumberFormatException -> 0x0238 }
            float r11 = (float) r11     // Catch:{ NumberFormatException -> 0x0238 }
            float r6 = r6 / r11
            float r9 = (float) r9     // Catch:{ NumberFormatException -> 0x0238 }
            int r3 = r2.b     // Catch:{ NumberFormatException -> 0x0238 }
            float r3 = (float) r3
            float r9 = r9 / r3
            r3 = r6
            r4 = r9
        L_0x019d:
            java.lang.String r6 = "displayAlign"
            java.lang.String r6 = com.google.android.exoplayer2.util.XmlPullParserUtil.getAttributeValue(r0, r6)
            if (r6 == 0) goto L_0x01cd
            java.lang.String r6 = com.google.common.base.Ascii.toLowerCase((java.lang.String) r6)
            r6.getClass()
            java.lang.String r9 = "center"
            boolean r9 = r6.equals(r9)
            if (r9 != 0) goto L_0x01c3
            java.lang.String r9 = "after"
            boolean r6 = r6.equals(r9)
            if (r6 != 0) goto L_0x01bd
            goto L_0x01cd
        L_0x01bd:
            float r8 = r8 + r4
            r15 = r21
            r9 = r8
            r11 = 2
            goto L_0x01d1
        L_0x01c3:
            r6 = 1073741824(0x40000000, float:2.0)
            float r6 = r4 / r6
            float r6 = r6 + r8
            r15 = r21
            r9 = r6
            r11 = 1
            goto L_0x01d1
        L_0x01cd:
            r15 = r21
            r9 = r8
            r11 = 0
        L_0x01d1:
            int r6 = r15.a
            float r6 = (float) r6
            r8 = 1065353216(0x3f800000, float:1.0)
            float r16 = r8 / r6
            java.lang.String r6 = "writingMode"
            java.lang.String r6 = com.google.android.exoplayer2.util.XmlPullParserUtil.getAttributeValue(r0, r6)
            if (r6 == 0) goto L_0x0224
            java.lang.String r6 = com.google.common.base.Ascii.toLowerCase((java.lang.String) r6)
            r6.getClass()
            int r8 = r6.hashCode()
            r14 = 3694(0xe6e, float:5.176E-42)
            if (r8 == r14) goto L_0x0210
            r5 = 3553396(0x363874, float:4.979368E-39)
            if (r8 == r5) goto L_0x0205
            r5 = 3553576(0x363928, float:4.97962E-39)
            if (r8 == r5) goto L_0x01fa
            goto L_0x0218
        L_0x01fa:
            java.lang.String r5 = "tbrl"
            boolean r5 = r6.equals(r5)
            if (r5 != 0) goto L_0x0203
            goto L_0x0218
        L_0x0203:
            r5 = 2
            goto L_0x0219
        L_0x0205:
            java.lang.String r5 = "tblr"
            boolean r5 = r6.equals(r5)
            if (r5 != 0) goto L_0x020e
            goto L_0x0218
        L_0x020e:
            r5 = 1
            goto L_0x0219
        L_0x0210:
            java.lang.String r8 = "tb"
            boolean r6 = r6.equals(r8)
            if (r6 != 0) goto L_0x0219
        L_0x0218:
            r5 = -1
        L_0x0219:
            if (r5 == 0) goto L_0x0222
            if (r5 == r13) goto L_0x0222
            if (r5 == r12) goto L_0x0220
            goto L_0x0224
        L_0x0220:
            r5 = 1
            goto L_0x0226
        L_0x0222:
            r5 = 2
            goto L_0x0226
        L_0x0224:
            r5 = -2147483648(0xffffffff80000000, float:-0.0)
        L_0x0226:
            vc r17 = new vc
            r12 = 0
            r14 = 1
            r6 = r17
            r8 = r10
            r10 = r12
            r12 = r3
            r13 = r4
            r15 = r16
            r16 = r5
            r6.<init>(r7, r8, r9, r10, r11, r12, r13, r14, r15, r16)
            goto L_0x029b
        L_0x0238:
            int r5 = r3.length()
            if (r5 == 0) goto L_0x0244
            java.lang.String r3 = r14.concat(r3)
            goto L_0x0249
        L_0x0244:
            java.lang.String r3 = new java.lang.String
            r3.<init>(r14)
        L_0x0249:
            com.google.android.exoplayer2.util.Log.w(r4, r3)
            goto L_0x0299
        L_0x024d:
            int r5 = r3.length()
            java.lang.String r6 = "Ignoring region with unsupported extent: "
            if (r5 == 0) goto L_0x025a
            java.lang.String r3 = r6.concat(r3)
            goto L_0x025f
        L_0x025a:
            java.lang.String r3 = new java.lang.String
            r3.<init>(r6)
        L_0x025f:
            com.google.android.exoplayer2.util.Log.w(r4, r3)
            goto L_0x0299
        L_0x0263:
            java.lang.String r3 = "Ignoring region without an extent"
            com.google.android.exoplayer2.util.Log.w(r4, r3)
            goto L_0x0299
        L_0x0269:
            int r5 = r3.length()
            if (r5 == 0) goto L_0x0275
            java.lang.String r3 = r14.concat(r3)
            goto L_0x027a
        L_0x0275:
            java.lang.String r3 = new java.lang.String
            r3.<init>(r14)
        L_0x027a:
            com.google.android.exoplayer2.util.Log.w(r4, r3)
            goto L_0x0299
        L_0x027e:
            int r5 = r3.length()
            java.lang.String r6 = "Ignoring region with unsupported origin: "
            if (r5 == 0) goto L_0x028b
            java.lang.String r3 = r6.concat(r3)
            goto L_0x0290
        L_0x028b:
            java.lang.String r3 = new java.lang.String
            r3.<init>(r6)
        L_0x0290:
            com.google.android.exoplayer2.util.Log.w(r4, r3)
            goto L_0x0299
        L_0x0294:
            java.lang.String r3 = "Ignoring region without an origin"
            com.google.android.exoplayer2.util.Log.w(r4, r3)
        L_0x0299:
            r17 = 0
        L_0x029b:
            r3 = r17
            if (r3 == 0) goto L_0x004e
            java.lang.String r4 = r3.a
            r5 = r23
            r5.put(r4, r3)
            goto L_0x02d5
        L_0x02a7:
            r5 = r23
            java.lang.String r3 = "metadata"
            boolean r6 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r0, r3)
            if (r6 == 0) goto L_0x02d5
        L_0x02b1:
            r19.next()
            java.lang.String r6 = "image"
            boolean r6 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r0, r6)
            if (r6 == 0) goto L_0x02cc
            java.lang.String r6 = com.google.android.exoplayer2.util.XmlPullParserUtil.getAttributeValue(r0, r4)
            if (r6 == 0) goto L_0x02cc
            java.lang.String r7 = r19.nextText()
            r8 = r24
            r8.put(r6, r7)
            goto L_0x02ce
        L_0x02cc:
            r8 = r24
        L_0x02ce:
            boolean r6 = com.google.android.exoplayer2.util.XmlPullParserUtil.isEndTag(r0, r3)
            if (r6 == 0) goto L_0x02b1
            goto L_0x02d7
        L_0x02d5:
            r8 = r24
        L_0x02d7:
            java.lang.String r3 = "head"
            boolean r3 = com.google.android.exoplayer2.util.XmlPullParserUtil.isEndTag(r0, r3)
            if (r3 == 0) goto L_0x0006
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.ttml.TtmlDecoder.l(org.xmlpull.v1.XmlPullParser, java.util.HashMap, com.google.android.exoplayer2.text.ttml.TtmlDecoder$a, com.google.android.exoplayer2.text.ttml.TtmlDecoder$c, java.util.HashMap, java.util.HashMap):void");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static defpackage.uc m(org.xmlpull.v1.XmlPullParser r19, @androidx.annotation.Nullable defpackage.uc r20, java.util.HashMap r21, com.google.android.exoplayer2.text.ttml.TtmlDecoder.b r22) throws com.google.android.exoplayer2.text.SubtitleDecoderException {
        /*
            r0 = r19
            r9 = r20
            r1 = r22
            int r2 = r19.getAttributeCount()
            r3 = 0
            com.google.android.exoplayer2.text.ttml.TtmlStyle r5 = n(r0, r3)
            java.lang.String r4 = ""
            r10 = r3
            r12 = r10
            r11 = r4
            r3 = 0
            r13 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            r15 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            r17 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
        L_0x0024:
            if (r3 >= r2) goto L_0x00dc
            java.lang.String r4 = r0.getAttributeName(r3)
            java.lang.String r7 = r0.getAttributeValue(r3)
            r4.getClass()
            int r8 = r4.hashCode()
            r6 = 1
            switch(r8) {
                case -934795532: goto L_0x0071;
                case 99841: goto L_0x0066;
                case 100571: goto L_0x005b;
                case 93616297: goto L_0x0050;
                case 109780401: goto L_0x0045;
                case 1292595405: goto L_0x003a;
                default: goto L_0x0039;
            }
        L_0x0039:
            goto L_0x007c
        L_0x003a:
            java.lang.String r8 = "backgroundImage"
            boolean r4 = r4.equals(r8)
            if (r4 != 0) goto L_0x0043
            goto L_0x007c
        L_0x0043:
            r4 = 5
            goto L_0x007d
        L_0x0045:
            java.lang.String r8 = "style"
            boolean r4 = r4.equals(r8)
            if (r4 != 0) goto L_0x004e
            goto L_0x007c
        L_0x004e:
            r4 = 4
            goto L_0x007d
        L_0x0050:
            java.lang.String r8 = "begin"
            boolean r4 = r4.equals(r8)
            if (r4 != 0) goto L_0x0059
            goto L_0x007c
        L_0x0059:
            r4 = 3
            goto L_0x007d
        L_0x005b:
            java.lang.String r8 = "end"
            boolean r4 = r4.equals(r8)
            if (r4 != 0) goto L_0x0064
            goto L_0x007c
        L_0x0064:
            r4 = 2
            goto L_0x007d
        L_0x0066:
            java.lang.String r8 = "dur"
            boolean r4 = r4.equals(r8)
            if (r4 != 0) goto L_0x006f
            goto L_0x007c
        L_0x006f:
            r4 = 1
            goto L_0x007d
        L_0x0071:
            java.lang.String r8 = "region"
            boolean r4 = r4.equals(r8)
            if (r4 != 0) goto L_0x007a
            goto L_0x007c
        L_0x007a:
            r4 = 0
            goto L_0x007d
        L_0x007c:
            r4 = -1
        L_0x007d:
            if (r4 == 0) goto L_0x00ce
            if (r4 == r6) goto L_0x00c4
            r8 = 2
            if (r4 == r8) goto L_0x00bd
            r8 = 3
            if (r4 == r8) goto L_0x00b6
            r8 = 4
            if (r4 == r8) goto L_0x009c
            r8 = 5
            if (r4 == r8) goto L_0x008e
            goto L_0x009a
        L_0x008e:
            java.lang.String r4 = "#"
            boolean r4 = r7.startsWith(r4)
            if (r4 == 0) goto L_0x009a
            java.lang.String r12 = r7.substring(r6)
        L_0x009a:
            r6 = 0
            goto L_0x00cb
        L_0x009c:
            java.lang.String r4 = r7.trim()
            boolean r6 = r4.isEmpty()
            if (r6 == 0) goto L_0x00aa
            r6 = 0
            java.lang.String[] r4 = new java.lang.String[r6]
            goto L_0x00b1
        L_0x00aa:
            r6 = 0
            java.lang.String r7 = "\\s+"
            java.lang.String[] r4 = com.google.android.exoplayer2.util.Util.split(r4, r7)
        L_0x00b1:
            int r7 = r4.length
            if (r7 <= 0) goto L_0x00cb
            r10 = r4
            goto L_0x00cb
        L_0x00b6:
            r6 = 0
            long r7 = o(r7, r1)
            r13 = r7
            goto L_0x00cb
        L_0x00bd:
            r6 = 0
            long r7 = o(r7, r1)
            r15 = r7
            goto L_0x00cb
        L_0x00c4:
            r6 = 0
            long r7 = o(r7, r1)
            r17 = r7
        L_0x00cb:
            r4 = r21
            goto L_0x00d8
        L_0x00ce:
            r4 = r21
            r6 = 0
            boolean r8 = r4.containsKey(r7)
            if (r8 == 0) goto L_0x00d8
            r11 = r7
        L_0x00d8:
            int r3 = r3 + 1
            goto L_0x0024
        L_0x00dc:
            if (r9 == 0) goto L_0x00f4
            long r1 = r9.d
            r3 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            int r6 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r6 == 0) goto L_0x00f9
            int r6 = (r13 > r3 ? 1 : (r13 == r3 ? 0 : -1))
            if (r6 == 0) goto L_0x00ee
            long r13 = r13 + r1
        L_0x00ee:
            int r6 = (r15 > r3 ? 1 : (r15 == r3 ? 0 : -1))
            if (r6 == 0) goto L_0x00f9
            long r15 = r15 + r1
            goto L_0x00f9
        L_0x00f4:
            r3 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
        L_0x00f9:
            r1 = r13
            int r6 = (r15 > r3 ? 1 : (r15 == r3 ? 0 : -1))
            if (r6 != 0) goto L_0x0111
            int r6 = (r17 > r3 ? 1 : (r17 == r3 ? 0 : -1))
            if (r6 == 0) goto L_0x0107
            long r17 = r1 + r17
            r3 = r17
            goto L_0x0112
        L_0x0107:
            if (r9 == 0) goto L_0x0111
            long r6 = r9.e
            int r8 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1))
            if (r8 == 0) goto L_0x0111
            r3 = r6
            goto L_0x0112
        L_0x0111:
            r3 = r15
        L_0x0112:
            java.lang.String r0 = r19.getName()
            r6 = r10
            r7 = r11
            r8 = r12
            r9 = r20
            uc r0 = defpackage.uc.buildNode(r0, r1, r3, r5, r6, r7, r8, r9)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.ttml.TtmlDecoder.m(org.xmlpull.v1.XmlPullParser, uc, java.util.HashMap, com.google.android.exoplayer2.text.ttml.TtmlDecoder$b):uc");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.android.exoplayer2.text.ttml.TtmlStyle n(org.xmlpull.v1.XmlPullParser r12, com.google.android.exoplayer2.text.ttml.TtmlStyle r13) {
        /*
            int r0 = r12.getAttributeCount()
            r1 = 0
            r2 = 0
        L_0x0006:
            if (r2 >= r0) goto L_0x036f
            java.lang.String r3 = r12.getAttributeValue(r2)
            java.lang.String r4 = r12.getAttributeName(r2)
            r4.getClass()
            int r5 = r4.hashCode()
            r6 = -1
            r7 = 5
            r8 = 4
            r9 = 2
            r10 = 3
            r11 = 1
            switch(r5) {
                case -1550943582: goto L_0x00d1;
                case -1224696685: goto L_0x00c6;
                case -1065511464: goto L_0x00bb;
                case -879295043: goto L_0x00b0;
                case -734428249: goto L_0x00a5;
                case 3355: goto L_0x009a;
                case 3511770: goto L_0x008f;
                case 94842723: goto L_0x0084;
                case 109403361: goto L_0x0076;
                case 110138194: goto L_0x0068;
                case 365601008: goto L_0x005a;
                case 921125321: goto L_0x004c;
                case 1115953443: goto L_0x003e;
                case 1287124693: goto L_0x0030;
                case 1754920356: goto L_0x0022;
                default: goto L_0x0020;
            }
        L_0x0020:
            goto L_0x00dc
        L_0x0022:
            java.lang.String r5 = "multiRowAlign"
            boolean r4 = r4.equals(r5)
            if (r4 != 0) goto L_0x002c
            goto L_0x00dc
        L_0x002c:
            r4 = 14
            goto L_0x00dd
        L_0x0030:
            java.lang.String r5 = "backgroundColor"
            boolean r4 = r4.equals(r5)
            if (r4 != 0) goto L_0x003a
            goto L_0x00dc
        L_0x003a:
            r4 = 13
            goto L_0x00dd
        L_0x003e:
            java.lang.String r5 = "rubyPosition"
            boolean r4 = r4.equals(r5)
            if (r4 != 0) goto L_0x0048
            goto L_0x00dc
        L_0x0048:
            r4 = 12
            goto L_0x00dd
        L_0x004c:
            java.lang.String r5 = "textEmphasis"
            boolean r4 = r4.equals(r5)
            if (r4 != 0) goto L_0x0056
            goto L_0x00dc
        L_0x0056:
            r4 = 11
            goto L_0x00dd
        L_0x005a:
            java.lang.String r5 = "fontSize"
            boolean r4 = r4.equals(r5)
            if (r4 != 0) goto L_0x0064
            goto L_0x00dc
        L_0x0064:
            r4 = 10
            goto L_0x00dd
        L_0x0068:
            java.lang.String r5 = "textCombine"
            boolean r4 = r4.equals(r5)
            if (r4 != 0) goto L_0x0072
            goto L_0x00dc
        L_0x0072:
            r4 = 9
            goto L_0x00dd
        L_0x0076:
            java.lang.String r5 = "shear"
            boolean r4 = r4.equals(r5)
            if (r4 != 0) goto L_0x0080
            goto L_0x00dc
        L_0x0080:
            r4 = 8
            goto L_0x00dd
        L_0x0084:
            java.lang.String r5 = "color"
            boolean r4 = r4.equals(r5)
            if (r4 != 0) goto L_0x008d
            goto L_0x00dc
        L_0x008d:
            r4 = 7
            goto L_0x00dd
        L_0x008f:
            java.lang.String r5 = "ruby"
            boolean r4 = r4.equals(r5)
            if (r4 != 0) goto L_0x0098
            goto L_0x00dc
        L_0x0098:
            r4 = 6
            goto L_0x00dd
        L_0x009a:
            java.lang.String r5 = "id"
            boolean r4 = r4.equals(r5)
            if (r4 != 0) goto L_0x00a3
            goto L_0x00dc
        L_0x00a3:
            r4 = 5
            goto L_0x00dd
        L_0x00a5:
            java.lang.String r5 = "fontWeight"
            boolean r4 = r4.equals(r5)
            if (r4 != 0) goto L_0x00ae
            goto L_0x00dc
        L_0x00ae:
            r4 = 4
            goto L_0x00dd
        L_0x00b0:
            java.lang.String r5 = "textDecoration"
            boolean r4 = r4.equals(r5)
            if (r4 != 0) goto L_0x00b9
            goto L_0x00dc
        L_0x00b9:
            r4 = 3
            goto L_0x00dd
        L_0x00bb:
            java.lang.String r5 = "textAlign"
            boolean r4 = r4.equals(r5)
            if (r4 != 0) goto L_0x00c4
            goto L_0x00dc
        L_0x00c4:
            r4 = 2
            goto L_0x00dd
        L_0x00c6:
            java.lang.String r5 = "fontFamily"
            boolean r4 = r4.equals(r5)
            if (r4 != 0) goto L_0x00cf
            goto L_0x00dc
        L_0x00cf:
            r4 = 1
            goto L_0x00dd
        L_0x00d1:
            java.lang.String r5 = "fontStyle"
            boolean r4 = r4.equals(r5)
            if (r4 != 0) goto L_0x00da
            goto L_0x00dc
        L_0x00da:
            r4 = 0
            goto L_0x00dd
        L_0x00dc:
            r4 = -1
        L_0x00dd:
            java.lang.String r5 = "TtmlDecoder"
            switch(r4) {
                case 0: goto L_0x035d;
                case 1: goto L_0x0354;
                case 2: goto L_0x0347;
                case 3: goto L_0x02e5;
                case 4: goto L_0x02d5;
                case 5: goto L_0x02bf;
                case 6: goto L_0x0239;
                case 7: goto L_0x0210;
                case 8: goto L_0x01a8;
                case 9: goto L_0x017b;
                case 10: goto L_0x0156;
                case 11: goto L_0x0148;
                case 12: goto L_0x011b;
                case 13: goto L_0x00f2;
                case 14: goto L_0x00e4;
                default: goto L_0x00e2;
            }
        L_0x00e2:
            goto L_0x036b
        L_0x00e4:
            com.google.android.exoplayer2.text.ttml.TtmlStyle r13 = f(r13)
            android.text.Layout$Alignment r3 = h(r3)
            com.google.android.exoplayer2.text.ttml.TtmlStyle r13 = r13.setMultiRowAlign(r3)
            goto L_0x036b
        L_0x00f2:
            com.google.android.exoplayer2.text.ttml.TtmlStyle r13 = f(r13)
            int r4 = com.google.android.exoplayer2.util.ColorParser.parseTtmlColor(r3)     // Catch:{ IllegalArgumentException -> 0x00ff }
            r13.setBackgroundColor(r4)     // Catch:{ IllegalArgumentException -> 0x00ff }
            goto L_0x036b
        L_0x00ff:
            java.lang.String r3 = java.lang.String.valueOf(r3)
            int r4 = r3.length()
            java.lang.String r6 = "Failed parsing background value: "
            if (r4 == 0) goto L_0x0111
            java.lang.String r3 = r6.concat(r3)
            goto L_0x0116
        L_0x0111:
            java.lang.String r3 = new java.lang.String
            r3.<init>(r6)
        L_0x0116:
            com.google.android.exoplayer2.util.Log.w(r5, r3)
            goto L_0x036b
        L_0x011b:
            java.lang.String r3 = com.google.common.base.Ascii.toLowerCase((java.lang.String) r3)
            r3.getClass()
            java.lang.String r4 = "before"
            boolean r4 = r3.equals(r4)
            if (r4 != 0) goto L_0x013e
            java.lang.String r4 = "after"
            boolean r3 = r3.equals(r4)
            if (r3 != 0) goto L_0x0134
            goto L_0x036b
        L_0x0134:
            com.google.android.exoplayer2.text.ttml.TtmlStyle r13 = f(r13)
            com.google.android.exoplayer2.text.ttml.TtmlStyle r13 = r13.setRubyPosition(r9)
            goto L_0x036b
        L_0x013e:
            com.google.android.exoplayer2.text.ttml.TtmlStyle r13 = f(r13)
            com.google.android.exoplayer2.text.ttml.TtmlStyle r13 = r13.setRubyPosition(r11)
            goto L_0x036b
        L_0x0148:
            com.google.android.exoplayer2.text.ttml.TtmlStyle r13 = f(r13)
            com.google.android.exoplayer2.text.ttml.TextEmphasis r3 = com.google.android.exoplayer2.text.ttml.TextEmphasis.parse(r3)
            com.google.android.exoplayer2.text.ttml.TtmlStyle r13 = r13.setTextEmphasis(r3)
            goto L_0x036b
        L_0x0156:
            com.google.android.exoplayer2.text.ttml.TtmlStyle r13 = f(r13)     // Catch:{ SubtitleDecoderException -> 0x015f }
            j(r3, r13)     // Catch:{ SubtitleDecoderException -> 0x015f }
            goto L_0x036b
        L_0x015f:
            java.lang.String r3 = java.lang.String.valueOf(r3)
            int r4 = r3.length()
            java.lang.String r6 = "Failed parsing fontSize value: "
            if (r4 == 0) goto L_0x0171
            java.lang.String r3 = r6.concat(r3)
            goto L_0x0176
        L_0x0171:
            java.lang.String r3 = new java.lang.String
            r3.<init>(r6)
        L_0x0176:
            com.google.android.exoplayer2.util.Log.w(r5, r3)
            goto L_0x036b
        L_0x017b:
            java.lang.String r3 = com.google.common.base.Ascii.toLowerCase((java.lang.String) r3)
            r3.getClass()
            java.lang.String r4 = "all"
            boolean r4 = r3.equals(r4)
            if (r4 != 0) goto L_0x019e
            java.lang.String r4 = "none"
            boolean r3 = r3.equals(r4)
            if (r3 != 0) goto L_0x0194
            goto L_0x036b
        L_0x0194:
            com.google.android.exoplayer2.text.ttml.TtmlStyle r13 = f(r13)
            com.google.android.exoplayer2.text.ttml.TtmlStyle r13 = r13.setTextCombine(r1)
            goto L_0x036b
        L_0x019e:
            com.google.android.exoplayer2.text.ttml.TtmlStyle r13 = f(r13)
            com.google.android.exoplayer2.text.ttml.TtmlStyle r13 = r13.setTextCombine(r11)
            goto L_0x036b
        L_0x01a8:
            com.google.android.exoplayer2.text.ttml.TtmlStyle r13 = f(r13)
            java.util.regex.Pattern r4 = s
            java.util.regex.Matcher r4 = r4.matcher(r3)
            boolean r6 = r4.matches()
            if (r6 != 0) goto L_0x01d2
            java.lang.String r3 = java.lang.String.valueOf(r3)
            int r4 = r3.length()
            java.lang.String r6 = "Invalid value for shear: "
            if (r4 == 0) goto L_0x01c9
            java.lang.String r3 = r6.concat(r3)
            goto L_0x01ce
        L_0x01c9:
            java.lang.String r3 = new java.lang.String
            r3.<init>(r6)
        L_0x01ce:
            com.google.android.exoplayer2.util.Log.w(r5, r3)
            goto L_0x0207
        L_0x01d2:
            java.lang.String r4 = r4.group(r11)     // Catch:{ NumberFormatException -> 0x01ed }
            java.lang.Object r4 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r4)     // Catch:{ NumberFormatException -> 0x01ed }
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ NumberFormatException -> 0x01ed }
            float r4 = java.lang.Float.parseFloat(r4)     // Catch:{ NumberFormatException -> 0x01ed }
            r6 = -1027080192(0xffffffffc2c80000, float:-100.0)
            float r4 = java.lang.Math.max(r6, r4)     // Catch:{ NumberFormatException -> 0x01ed }
            r6 = 1120403456(0x42c80000, float:100.0)
            float r3 = java.lang.Math.min(r6, r4)     // Catch:{ NumberFormatException -> 0x01ed }
            goto L_0x020a
        L_0x01ed:
            r4 = move-exception
            java.lang.String r3 = java.lang.String.valueOf(r3)
            int r6 = r3.length()
            java.lang.String r7 = "Failed to parse shear: "
            if (r6 == 0) goto L_0x01ff
            java.lang.String r3 = r7.concat(r3)
            goto L_0x0204
        L_0x01ff:
            java.lang.String r3 = new java.lang.String
            r3.<init>(r7)
        L_0x0204:
            com.google.android.exoplayer2.util.Log.w(r5, r3, r4)
        L_0x0207:
            r3 = 2139095039(0x7f7fffff, float:3.4028235E38)
        L_0x020a:
            com.google.android.exoplayer2.text.ttml.TtmlStyle r13 = r13.setShearPercentage(r3)
            goto L_0x036b
        L_0x0210:
            com.google.android.exoplayer2.text.ttml.TtmlStyle r13 = f(r13)
            int r4 = com.google.android.exoplayer2.util.ColorParser.parseTtmlColor(r3)     // Catch:{ IllegalArgumentException -> 0x021d }
            r13.setFontColor(r4)     // Catch:{ IllegalArgumentException -> 0x021d }
            goto L_0x036b
        L_0x021d:
            java.lang.String r3 = java.lang.String.valueOf(r3)
            int r4 = r3.length()
            java.lang.String r6 = "Failed parsing color value: "
            if (r4 == 0) goto L_0x022f
            java.lang.String r3 = r6.concat(r3)
            goto L_0x0234
        L_0x022f:
            java.lang.String r3 = new java.lang.String
            r3.<init>(r6)
        L_0x0234:
            com.google.android.exoplayer2.util.Log.w(r5, r3)
            goto L_0x036b
        L_0x0239:
            java.lang.String r3 = com.google.common.base.Ascii.toLowerCase((java.lang.String) r3)
            r3.getClass()
            int r4 = r3.hashCode()
            switch(r4) {
                case -618561360: goto L_0x027f;
                case -410956671: goto L_0x0274;
                case -250518009: goto L_0x0269;
                case -136074796: goto L_0x025e;
                case 3016401: goto L_0x0253;
                case 3556653: goto L_0x0248;
                default: goto L_0x0247;
            }
        L_0x0247:
            goto L_0x0289
        L_0x0248:
            java.lang.String r4 = "text"
            boolean r3 = r3.equals(r4)
            if (r3 != 0) goto L_0x0251
            goto L_0x0289
        L_0x0251:
            r6 = 5
            goto L_0x0289
        L_0x0253:
            java.lang.String r4 = "base"
            boolean r3 = r3.equals(r4)
            if (r3 != 0) goto L_0x025c
            goto L_0x0289
        L_0x025c:
            r6 = 4
            goto L_0x0289
        L_0x025e:
            java.lang.String r4 = "textContainer"
            boolean r3 = r3.equals(r4)
            if (r3 != 0) goto L_0x0267
            goto L_0x0289
        L_0x0267:
            r6 = 3
            goto L_0x0289
        L_0x0269:
            java.lang.String r4 = "delimiter"
            boolean r3 = r3.equals(r4)
            if (r3 != 0) goto L_0x0272
            goto L_0x0289
        L_0x0272:
            r6 = 2
            goto L_0x0289
        L_0x0274:
            java.lang.String r4 = "container"
            boolean r3 = r3.equals(r4)
            if (r3 != 0) goto L_0x027d
            goto L_0x0289
        L_0x027d:
            r6 = 1
            goto L_0x0289
        L_0x027f:
            java.lang.String r4 = "baseContainer"
            boolean r3 = r3.equals(r4)
            if (r3 != 0) goto L_0x0288
            goto L_0x0289
        L_0x0288:
            r6 = 0
        L_0x0289:
            if (r6 == 0) goto L_0x02b5
            if (r6 == r11) goto L_0x02ab
            if (r6 == r9) goto L_0x02a1
            if (r6 == r10) goto L_0x0297
            if (r6 == r8) goto L_0x02b5
            if (r6 == r7) goto L_0x0297
            goto L_0x036b
        L_0x0297:
            com.google.android.exoplayer2.text.ttml.TtmlStyle r13 = f(r13)
            com.google.android.exoplayer2.text.ttml.TtmlStyle r13 = r13.setRubyType(r10)
            goto L_0x036b
        L_0x02a1:
            com.google.android.exoplayer2.text.ttml.TtmlStyle r13 = f(r13)
            com.google.android.exoplayer2.text.ttml.TtmlStyle r13 = r13.setRubyType(r8)
            goto L_0x036b
        L_0x02ab:
            com.google.android.exoplayer2.text.ttml.TtmlStyle r13 = f(r13)
            com.google.android.exoplayer2.text.ttml.TtmlStyle r13 = r13.setRubyType(r11)
            goto L_0x036b
        L_0x02b5:
            com.google.android.exoplayer2.text.ttml.TtmlStyle r13 = f(r13)
            com.google.android.exoplayer2.text.ttml.TtmlStyle r13 = r13.setRubyType(r9)
            goto L_0x036b
        L_0x02bf:
            java.lang.String r4 = "style"
            java.lang.String r5 = r12.getName()
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x036b
            com.google.android.exoplayer2.text.ttml.TtmlStyle r13 = f(r13)
            com.google.android.exoplayer2.text.ttml.TtmlStyle r13 = r13.setId(r3)
            goto L_0x036b
        L_0x02d5:
            com.google.android.exoplayer2.text.ttml.TtmlStyle r13 = f(r13)
            java.lang.String r4 = "bold"
            boolean r3 = r4.equalsIgnoreCase(r3)
            com.google.android.exoplayer2.text.ttml.TtmlStyle r13 = r13.setBold(r3)
            goto L_0x036b
        L_0x02e5:
            java.lang.String r3 = com.google.common.base.Ascii.toLowerCase((java.lang.String) r3)
            r3.getClass()
            int r4 = r3.hashCode()
            switch(r4) {
                case -1461280213: goto L_0x0315;
                case -1026963764: goto L_0x030a;
                case 913457136: goto L_0x02ff;
                case 1679736913: goto L_0x02f4;
                default: goto L_0x02f3;
            }
        L_0x02f3:
            goto L_0x031f
        L_0x02f4:
            java.lang.String r4 = "linethrough"
            boolean r3 = r3.equals(r4)
            if (r3 != 0) goto L_0x02fd
            goto L_0x031f
        L_0x02fd:
            r6 = 3
            goto L_0x031f
        L_0x02ff:
            java.lang.String r4 = "nolinethrough"
            boolean r3 = r3.equals(r4)
            if (r3 != 0) goto L_0x0308
            goto L_0x031f
        L_0x0308:
            r6 = 2
            goto L_0x031f
        L_0x030a:
            java.lang.String r4 = "underline"
            boolean r3 = r3.equals(r4)
            if (r3 != 0) goto L_0x0313
            goto L_0x031f
        L_0x0313:
            r6 = 1
            goto L_0x031f
        L_0x0315:
            java.lang.String r4 = "nounderline"
            boolean r3 = r3.equals(r4)
            if (r3 != 0) goto L_0x031e
            goto L_0x031f
        L_0x031e:
            r6 = 0
        L_0x031f:
            switch(r6) {
                case 0: goto L_0x033e;
                case 1: goto L_0x0335;
                case 2: goto L_0x032c;
                case 3: goto L_0x0323;
                default: goto L_0x0322;
            }
        L_0x0322:
            goto L_0x036b
        L_0x0323:
            com.google.android.exoplayer2.text.ttml.TtmlStyle r13 = f(r13)
            com.google.android.exoplayer2.text.ttml.TtmlStyle r13 = r13.setLinethrough(r11)
            goto L_0x036b
        L_0x032c:
            com.google.android.exoplayer2.text.ttml.TtmlStyle r13 = f(r13)
            com.google.android.exoplayer2.text.ttml.TtmlStyle r13 = r13.setLinethrough(r1)
            goto L_0x036b
        L_0x0335:
            com.google.android.exoplayer2.text.ttml.TtmlStyle r13 = f(r13)
            com.google.android.exoplayer2.text.ttml.TtmlStyle r13 = r13.setUnderline(r11)
            goto L_0x036b
        L_0x033e:
            com.google.android.exoplayer2.text.ttml.TtmlStyle r13 = f(r13)
            com.google.android.exoplayer2.text.ttml.TtmlStyle r13 = r13.setUnderline(r1)
            goto L_0x036b
        L_0x0347:
            com.google.android.exoplayer2.text.ttml.TtmlStyle r13 = f(r13)
            android.text.Layout$Alignment r3 = h(r3)
            com.google.android.exoplayer2.text.ttml.TtmlStyle r13 = r13.setTextAlign(r3)
            goto L_0x036b
        L_0x0354:
            com.google.android.exoplayer2.text.ttml.TtmlStyle r13 = f(r13)
            com.google.android.exoplayer2.text.ttml.TtmlStyle r13 = r13.setFontFamily(r3)
            goto L_0x036b
        L_0x035d:
            com.google.android.exoplayer2.text.ttml.TtmlStyle r13 = f(r13)
            java.lang.String r4 = "italic"
            boolean r3 = r4.equalsIgnoreCase(r3)
            com.google.android.exoplayer2.text.ttml.TtmlStyle r13 = r13.setItalic(r3)
        L_0x036b:
            int r2 = r2 + 1
            goto L_0x0006
        L_0x036f:
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.ttml.TtmlDecoder.n(org.xmlpull.v1.XmlPullParser, com.google.android.exoplayer2.text.ttml.TtmlStyle):com.google.android.exoplayer2.text.ttml.TtmlStyle");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x00bf, code lost:
        if (r13.equals("ms") == false) goto L_0x00b7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00f6, code lost:
        r8 = r8 / r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0104, code lost:
        r8 = r8 * r13;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static long o(java.lang.String r13, com.google.android.exoplayer2.text.ttml.TtmlDecoder.b r14) throws com.google.android.exoplayer2.text.SubtitleDecoderException {
        /*
            java.util.regex.Pattern r0 = p
            java.util.regex.Matcher r0 = r0.matcher(r13)
            boolean r1 = r0.matches()
            r2 = 4696837146684686336(0x412e848000000000, double:1000000.0)
            r4 = 4
            r5 = 3
            r6 = 2
            r7 = 1
            if (r1 == 0) goto L_0x0088
            java.lang.String r13 = r0.group(r7)
            java.lang.Object r13 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r13)
            java.lang.String r13 = (java.lang.String) r13
            long r7 = java.lang.Long.parseLong(r13)
            r9 = 3600(0xe10, double:1.7786E-320)
            long r7 = r7 * r9
            double r7 = (double) r7
            java.lang.String r13 = r0.group(r6)
            java.lang.Object r13 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r13)
            java.lang.String r13 = (java.lang.String) r13
            long r9 = java.lang.Long.parseLong(r13)
            r11 = 60
            long r9 = r9 * r11
            double r9 = (double) r9
            double r7 = r7 + r9
            java.lang.String r13 = r0.group(r5)
            java.lang.Object r13 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r13)
            java.lang.String r13 = (java.lang.String) r13
            long r5 = java.lang.Long.parseLong(r13)
            double r5 = (double) r5
            double r7 = r7 + r5
            java.lang.String r13 = r0.group(r4)
            r4 = 0
            if (r13 == 0) goto L_0x0059
            double r9 = java.lang.Double.parseDouble(r13)
            goto L_0x005a
        L_0x0059:
            r9 = r4
        L_0x005a:
            double r7 = r7 + r9
            r13 = 5
            java.lang.String r13 = r0.group(r13)
            if (r13 == 0) goto L_0x006c
            long r9 = java.lang.Long.parseLong(r13)
            float r13 = (float) r9
            float r1 = r14.a
            float r13 = r13 / r1
            double r9 = (double) r13
            goto L_0x006d
        L_0x006c:
            r9 = r4
        L_0x006d:
            double r7 = r7 + r9
            r13 = 6
            java.lang.String r13 = r0.group(r13)
            if (r13 == 0) goto L_0x0083
            long r0 = java.lang.Long.parseLong(r13)
            double r0 = (double) r0
            int r13 = r14.b
            double r4 = (double) r13
            double r0 = r0 / r4
            float r13 = r14.a
            double r13 = (double) r13
            double r4 = r0 / r13
        L_0x0083:
            double r7 = r7 + r4
            double r7 = r7 * r2
            long r13 = (long) r7
            return r13
        L_0x0088:
            java.util.regex.Pattern r0 = q
            java.util.regex.Matcher r0 = r0.matcher(r13)
            boolean r1 = r0.matches()
            if (r1 == 0) goto L_0x010f
            java.lang.String r13 = r0.group(r7)
            java.lang.Object r13 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r13)
            java.lang.String r13 = (java.lang.String) r13
            double r8 = java.lang.Double.parseDouble(r13)
            java.lang.String r13 = r0.group(r6)
            java.lang.Object r13 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r13)
            java.lang.String r13 = (java.lang.String) r13
            r13.getClass()
            int r0 = r13.hashCode()
            r1 = -1
            switch(r0) {
                case 102: goto L_0x00e3;
                case 104: goto L_0x00d8;
                case 109: goto L_0x00cd;
                case 116: goto L_0x00c2;
                case 3494: goto L_0x00b9;
                default: goto L_0x00b7;
            }
        L_0x00b7:
            r4 = -1
            goto L_0x00ed
        L_0x00b9:
            java.lang.String r0 = "ms"
            boolean r13 = r13.equals(r0)
            if (r13 != 0) goto L_0x00ed
            goto L_0x00b7
        L_0x00c2:
            java.lang.String r0 = "t"
            boolean r13 = r13.equals(r0)
            if (r13 != 0) goto L_0x00cb
            goto L_0x00b7
        L_0x00cb:
            r4 = 3
            goto L_0x00ed
        L_0x00cd:
            java.lang.String r0 = "m"
            boolean r13 = r13.equals(r0)
            if (r13 != 0) goto L_0x00d6
            goto L_0x00b7
        L_0x00d6:
            r4 = 2
            goto L_0x00ed
        L_0x00d8:
            java.lang.String r0 = "h"
            boolean r13 = r13.equals(r0)
            if (r13 != 0) goto L_0x00e1
            goto L_0x00b7
        L_0x00e1:
            r4 = 1
            goto L_0x00ed
        L_0x00e3:
            java.lang.String r0 = "f"
            boolean r13 = r13.equals(r0)
            if (r13 != 0) goto L_0x00ec
            goto L_0x00b7
        L_0x00ec:
            r4 = 0
        L_0x00ed:
            switch(r4) {
                case 0: goto L_0x0107;
                case 1: goto L_0x00ff;
                case 2: goto L_0x00fc;
                case 3: goto L_0x00f8;
                case 4: goto L_0x00f1;
                default: goto L_0x00f0;
            }
        L_0x00f0:
            goto L_0x010b
        L_0x00f1:
            r13 = 4652007308841189376(0x408f400000000000, double:1000.0)
        L_0x00f6:
            double r8 = r8 / r13
            goto L_0x010b
        L_0x00f8:
            int r13 = r14.c
            double r13 = (double) r13
            goto L_0x00f6
        L_0x00fc:
            r13 = 4633641066610819072(0x404e000000000000, double:60.0)
            goto L_0x0104
        L_0x00ff:
            r13 = 4660134898793709568(0x40ac200000000000, double:3600.0)
        L_0x0104:
            double r8 = r8 * r13
            goto L_0x010b
        L_0x0107:
            float r13 = r14.a
            double r13 = (double) r13
            goto L_0x00f6
        L_0x010b:
            double r8 = r8 * r2
            long r13 = (long) r8
            return r13
        L_0x010f:
            com.google.android.exoplayer2.text.SubtitleDecoderException r14 = new com.google.android.exoplayer2.text.SubtitleDecoderException
            java.lang.String r13 = java.lang.String.valueOf(r13)
            int r0 = r13.length()
            java.lang.String r1 = "Malformed time expression: "
            if (r0 == 0) goto L_0x0122
            java.lang.String r13 = r1.concat(r13)
            goto L_0x0127
        L_0x0122:
            java.lang.String r13 = new java.lang.String
            r13.<init>(r1)
        L_0x0127:
            r14.<init>((java.lang.String) r13)
            throw r14
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.ttml.TtmlDecoder.o(java.lang.String, com.google.android.exoplayer2.text.ttml.TtmlDecoder$b):long");
    }

    @Nullable
    public static c p(XmlPullParser xmlPullParser) {
        String str;
        String str2;
        String attributeValue = XmlPullParserUtil.getAttributeValue(xmlPullParser, "extent");
        if (attributeValue == null) {
            return null;
        }
        Matcher matcher = u.matcher(attributeValue);
        if (!matcher.matches()) {
            if (attributeValue.length() != 0) {
                str2 = "Ignoring non-pixel tts extent: ".concat(attributeValue);
            } else {
                str2 = new String("Ignoring non-pixel tts extent: ");
            }
            Log.w("TtmlDecoder", str2);
            return null;
        }
        try {
            return new c(Integer.parseInt((String) Assertions.checkNotNull(matcher.group(1))), Integer.parseInt((String) Assertions.checkNotNull(matcher.group(2))));
        } catch (NumberFormatException unused) {
            if (attributeValue.length() != 0) {
                str = "Ignoring malformed tts extent: ".concat(attributeValue);
            } else {
                str = new String("Ignoring malformed tts extent: ");
            }
            Log.w("TtmlDecoder", str);
            return null;
        }
    }

    public final Subtitle e(byte[] bArr, int i, boolean z) throws SubtitleDecoderException {
        a aVar;
        b bVar;
        String str;
        try {
            XmlPullParser newPullParser = this.o.newPullParser();
            HashMap hashMap = new HashMap();
            HashMap hashMap2 = new HashMap();
            HashMap hashMap3 = new HashMap();
            hashMap2.put("", new vc(""));
            c cVar = null;
            newPullParser.setInput(new ByteArrayInputStream(bArr, 0, i), (String) null);
            ArrayDeque arrayDeque = new ArrayDeque();
            int eventType = newPullParser.getEventType();
            b bVar2 = w;
            a aVar2 = x;
            xc xcVar = null;
            a aVar3 = aVar2;
            int i2 = 0;
            while (eventType != 1) {
                uc ucVar = (uc) arrayDeque.peek();
                if (i2 == 0) {
                    String name = newPullParser.getName();
                    if (eventType == 2) {
                        if ("tt".equals(name)) {
                            bVar2 = k(newPullParser);
                            aVar3 = i(newPullParser, aVar2);
                            cVar = p(newPullParser);
                        }
                        a aVar4 = aVar3;
                        c cVar2 = cVar;
                        b bVar3 = bVar2;
                        if (!g(name)) {
                            String valueOf = String.valueOf(newPullParser.getName());
                            if (valueOf.length() != 0) {
                                str = "Ignoring unsupported tag: ".concat(valueOf);
                            } else {
                                str = new String("Ignoring unsupported tag: ");
                            }
                            Log.i("TtmlDecoder", str);
                            i2++;
                            aVar = aVar4;
                            bVar = bVar3;
                        } else if ("head".equals(name)) {
                            aVar = aVar4;
                            bVar = bVar3;
                            l(newPullParser, hashMap, aVar4, cVar2, hashMap2, hashMap3);
                        } else {
                            aVar = aVar4;
                            bVar = bVar3;
                            try {
                                uc m = m(newPullParser, ucVar, hashMap2, bVar);
                                arrayDeque.push(m);
                                if (ucVar != null) {
                                    ucVar.addChild(m);
                                }
                            } catch (SubtitleDecoderException e) {
                                Log.w("TtmlDecoder", "Suppressing parser error", e);
                                i2++;
                            }
                        }
                        aVar3 = aVar;
                        bVar2 = bVar;
                        cVar = cVar2;
                    } else if (eventType == 4) {
                        ((uc) Assertions.checkNotNull(ucVar)).addChild(uc.buildTextNode(newPullParser.getText()));
                    } else if (eventType == 3) {
                        if (newPullParser.getName().equals("tt")) {
                            xcVar = new xc((uc) Assertions.checkNotNull((uc) arrayDeque.peek()), hashMap, hashMap2, hashMap3);
                        }
                        arrayDeque.pop();
                    }
                } else if (eventType == 2) {
                    i2++;
                } else if (eventType == 3) {
                    i2--;
                }
                newPullParser.next();
                eventType = newPullParser.getEventType();
            }
            if (xcVar != null) {
                return xcVar;
            }
            throw new SubtitleDecoderException("No TTML subtitles found");
        } catch (XmlPullParserException e2) {
            throw new SubtitleDecoderException("Unable to decode source", e2);
        } catch (IOException e3) {
            throw new IllegalStateException("Unexpected error when reading input.", e3);
        }
    }
}
