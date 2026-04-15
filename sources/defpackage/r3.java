package defpackage;

import android.graphics.Color;
import androidx.annotation.ColorInt;
import com.google.android.exoplayer2.util.Util;

/* renamed from: r3  reason: default package */
public final class r3 {
    public static String cssAllClassDescendantsSelector(String str) {
        StringBuilder l = y2.l(y2.c(str, y2.c(str, 5)), ".", str, ",.", str);
        l.append(" *");
        return l.toString();
    }

    public static String toCssRgba(@ColorInt int i) {
        return Util.formatInvariant("rgba(%d,%d,%d,%.3f)", Integer.valueOf(Color.red(i)), Integer.valueOf(Color.green(i)), Integer.valueOf(Color.blue(i)), Double.valueOf(((double) Color.alpha(i)) / 255.0d));
    }
}
