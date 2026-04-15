package org.mozilla.javascript.typedarrays;

import androidx.core.internal.view.SupportMenu;
import org.mozilla.javascript.ScriptRuntime;

public class Conversions {
    public static int toInt16(Object obj) {
        return (short) ScriptRuntime.toInt32(obj);
    }

    public static int toInt32(Object obj) {
        return ScriptRuntime.toInt32(obj);
    }

    public static int toInt8(Object obj) {
        return (byte) ScriptRuntime.toInt32(obj);
    }

    public static int toUint16(Object obj) {
        return ScriptRuntime.toInt32(obj) & SupportMenu.USER_MASK;
    }

    public static long toUint32(Object obj) {
        return ScriptRuntime.toUint32(obj);
    }

    public static int toUint8(Object obj) {
        return ScriptRuntime.toInt32(obj) & 255;
    }

    public static int toUint8Clamp(Object obj) {
        double number = ScriptRuntime.toNumber(obj);
        if (number <= 0.0d) {
            return 0;
        }
        if (number >= 255.0d) {
            return 255;
        }
        double floor = Math.floor(number);
        double d = 0.5d + floor;
        if (d < number) {
            return (int) (floor + 1.0d);
        }
        if (number < d) {
            return (int) floor;
        }
        int i = (int) floor;
        if (i % 2 != 0) {
            return i + 1;
        }
        return i;
    }
}
