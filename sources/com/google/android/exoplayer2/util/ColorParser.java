package com.google.android.exoplayer2.util;

import android.graphics.Color;
import android.text.TextUtils;
import androidx.annotation.ColorInt;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.InputDeviceCompat;
import androidx.core.view.ViewCompat;
import com.google.common.base.Ascii;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ColorParser {
    public static final Pattern a = Pattern.compile("^rgb\\((\\d{1,3}),(\\d{1,3}),(\\d{1,3})\\)$");
    public static final Pattern b = Pattern.compile("^rgba\\((\\d{1,3}),(\\d{1,3}),(\\d{1,3}),(\\d{1,3})\\)$");
    public static final Pattern c = Pattern.compile("^rgba\\((\\d{1,3}),(\\d{1,3}),(\\d{1,3}),(\\d*\\.?\\d*?)\\)$");
    public static final HashMap d;

    static {
        HashMap hashMap = new HashMap();
        d = hashMap;
        hashMap.put("aliceblue", -984833);
        hashMap.put("antiquewhite", -332841);
        hashMap.put("aqua", -16711681);
        HashMap hashMap2 = hashMap;
        y2.u(-8388652, hashMap2, "aquamarine", -983041, "azure", -657956, "beige", -6972, "bisque");
        y2.u(ViewCompat.MEASURED_STATE_MASK, hashMap2, "black", -5171, "blanchedalmond", -16776961, "blue", -7722014, "blueviolet");
        y2.u(-5952982, hashMap2, "brown", -2180985, "burlywood", -10510688, "cadetblue", -8388864, "chartreuse");
        y2.u(-2987746, hashMap2, "chocolate", -32944, "coral", -10185235, "cornflowerblue", -1828, "cornsilk");
        hashMap.put("crimson", -2354116);
        hashMap.put("cyan", -16711681);
        hashMap.put("darkblue", -16777077);
        hashMap.put("darkcyan", -16741493);
        hashMap.put("darkgoldenrod", -4684277);
        hashMap.put("darkgray", -5658199);
        hashMap.put("darkgreen", -16751616);
        hashMap.put("darkgrey", -5658199);
        hashMap.put("darkkhaki", -4343957);
        HashMap hashMap3 = hashMap;
        y2.u(-7667573, hashMap3, "darkmagenta", -11179217, "darkolivegreen", -29696, "darkorange", -6737204, "darkorchid");
        y2.u(-7667712, hashMap3, "darkred", -1468806, "darksalmon", -7357297, "darkseagreen", -12042869, "darkslateblue");
        hashMap.put("darkslategray", -13676721);
        hashMap.put("darkslategrey", -13676721);
        hashMap.put("darkturquoise", -16724271);
        hashMap.put("darkviolet", -7077677);
        hashMap.put("deeppink", -60269);
        hashMap.put("deepskyblue", -16728065);
        hashMap.put("dimgray", -9868951);
        hashMap.put("dimgrey", -9868951);
        y2.u(-14774017, hashMap, "dodgerblue", -5103070, "firebrick", -1296, "floralwhite", -14513374, "forestgreen");
        hashMap.put("fuchsia", -65281);
        hashMap.put("gainsboro", -2302756);
        hashMap.put("ghostwhite", -460545);
        hashMap.put("gold", -10496);
        hashMap.put("goldenrod", -2448096);
        hashMap.put("gray", -8355712);
        hashMap.put("green", -16744448);
        hashMap.put("greenyellow", -5374161);
        hashMap.put("grey", -8355712);
        HashMap hashMap4 = hashMap;
        y2.u(-983056, hashMap4, "honeydew", -38476, "hotpink", -3318692, "indianred", -11861886, "indigo");
        y2.u(-16, hashMap4, "ivory", -989556, "khaki", -1644806, "lavender", -3851, "lavenderblush");
        y2.u(-8586240, hashMap4, "lawngreen", -1331, "lemonchiffon", -5383962, "lightblue", -1015680, "lightcoral");
        hashMap.put("lightcyan", -2031617);
        hashMap.put("lightgoldenrodyellow", -329006);
        hashMap.put("lightgray", -2894893);
        hashMap.put("lightgreen", -7278960);
        hashMap.put("lightgrey", -2894893);
        hashMap.put("lightpink", -18751);
        hashMap.put("lightsalmon", -24454);
        hashMap.put("lightseagreen", -14634326);
        hashMap.put("lightskyblue", -7876870);
        hashMap.put("lightslategray", -8943463);
        hashMap.put("lightslategrey", -8943463);
        hashMap.put("lightsteelblue", -5192482);
        hashMap.put("lightyellow", -32);
        hashMap.put("lime", -16711936);
        hashMap.put("limegreen", -13447886);
        hashMap.put("linen", -331546);
        hashMap.put("magenta", -65281);
        hashMap.put("maroon", -8388608);
        hashMap.put("mediumaquamarine", -10039894);
        HashMap hashMap5 = hashMap;
        y2.u(-16777011, hashMap5, "mediumblue", -4565549, "mediumorchid", -7114533, "mediumpurple", -12799119, "mediumseagreen");
        y2.u(-8689426, hashMap5, "mediumslateblue", -16713062, "mediumspringgreen", -12004916, "mediumturquoise", -3730043, "mediumvioletred");
        y2.u(-15132304, hashMap5, "midnightblue", -655366, "mintcream", -6943, "mistyrose", -6987, "moccasin");
        y2.u(-8531, hashMap5, "navajowhite", -16777088, "navy", -133658, "oldlace", -8355840, "olive");
        y2.u(-9728477, hashMap5, "olivedrab", -23296, "orange", -47872, "orangered", -2461482, "orchid");
        y2.u(-1120086, hashMap5, "palegoldenrod", -6751336, "palegreen", -5247250, "paleturquoise", -2396013, "palevioletred");
        y2.u(-4139, hashMap5, "papayawhip", -9543, "peachpuff", -3308225, "peru", -16181, "pink");
        y2.u(-2252579, hashMap5, "plum", -5185306, "powderblue", -8388480, "purple", -10079335, "rebeccapurple");
        y2.u(SupportMenu.CATEGORY_MASK, hashMap5, "red", -4419697, "rosybrown", -12490271, "royalblue", -7650029, "saddlebrown");
        y2.u(-360334, hashMap5, "salmon", -744352, "sandybrown", -13726889, "seagreen", -2578, "seashell");
        y2.u(-6270419, hashMap5, "sienna", -4144960, "silver", -7876885, "skyblue", -9807155, "slateblue");
        hashMap.put("slategray", -9404272);
        hashMap.put("slategrey", -9404272);
        hashMap.put("snow", -1286);
        hashMap.put("springgreen", -16711809);
        HashMap hashMap6 = hashMap;
        y2.u(-12156236, hashMap6, "steelblue", -2968436, "tan", -16744320, "teal", -2572328, "thistle");
        y2.u(-40121, hashMap6, "tomato", 0, "transparent", -12525360, "turquoise", -1146130, "violet");
        y2.u(-663885, hashMap6, "wheat", -1, "white", -657931, "whitesmoke", InputDeviceCompat.SOURCE_ANY, "yellow");
        hashMap.put("yellowgreen", -6632142);
    }

    @ColorInt
    public static int a(String str, boolean z) {
        Pattern pattern;
        int i;
        Assertions.checkArgument(!TextUtils.isEmpty(str));
        String replace = str.replace(" ", "");
        if (replace.charAt(0) == '#') {
            int parseLong = (int) Long.parseLong(replace.substring(1), 16);
            if (replace.length() == 7) {
                return -16777216 | parseLong;
            }
            if (replace.length() == 9) {
                return ((parseLong & 255) << 24) | (parseLong >>> 8);
            }
            throw new IllegalArgumentException();
        }
        if (replace.startsWith("rgba")) {
            if (z) {
                pattern = c;
            } else {
                pattern = b;
            }
            Matcher matcher = pattern.matcher(replace);
            if (matcher.matches()) {
                if (z) {
                    i = (int) (Float.parseFloat((String) Assertions.checkNotNull(matcher.group(4))) * 255.0f);
                } else {
                    i = Integer.parseInt((String) Assertions.checkNotNull(matcher.group(4)), 10);
                }
                return Color.argb(i, Integer.parseInt((String) Assertions.checkNotNull(matcher.group(1)), 10), Integer.parseInt((String) Assertions.checkNotNull(matcher.group(2)), 10), Integer.parseInt((String) Assertions.checkNotNull(matcher.group(3)), 10));
            }
        } else if (replace.startsWith("rgb")) {
            Matcher matcher2 = a.matcher(replace);
            if (matcher2.matches()) {
                return Color.rgb(Integer.parseInt((String) Assertions.checkNotNull(matcher2.group(1)), 10), Integer.parseInt((String) Assertions.checkNotNull(matcher2.group(2)), 10), Integer.parseInt((String) Assertions.checkNotNull(matcher2.group(3)), 10));
            }
        } else {
            Integer num = (Integer) d.get(Ascii.toLowerCase(replace));
            if (num != null) {
                return num.intValue();
            }
        }
        throw new IllegalArgumentException();
    }

    @ColorInt
    public static int parseCssColor(String str) {
        return a(str, true);
    }

    @ColorInt
    public static int parseTtmlColor(String str) {
        return a(str, false);
    }
}
