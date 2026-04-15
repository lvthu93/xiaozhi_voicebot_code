package j$.util;

import java.util.Arrays;

public final class Objects {
    public static boolean deepEquals(Object obj, Object obj2) {
        if (obj == obj2) {
            return true;
        }
        if (obj == null || obj2 == null) {
            return false;
        }
        return (!(obj instanceof Object[]) || !(obj2 instanceof Object[])) ? (!(obj instanceof byte[]) || !(obj2 instanceof byte[])) ? (!(obj instanceof short[]) || !(obj2 instanceof short[])) ? (!(obj instanceof int[]) || !(obj2 instanceof int[])) ? (!(obj instanceof long[]) || !(obj2 instanceof long[])) ? (!(obj instanceof char[]) || !(obj2 instanceof char[])) ? (!(obj instanceof float[]) || !(obj2 instanceof float[])) ? (!(obj instanceof double[]) || !(obj2 instanceof double[])) ? (!(obj instanceof boolean[]) || !(obj2 instanceof boolean[])) ? obj.equals(obj2) : Arrays.equals((boolean[]) obj, (boolean[]) obj2) : Arrays.equals((double[]) obj, (double[]) obj2) : Arrays.equals((float[]) obj, (float[]) obj2) : Arrays.equals((char[]) obj, (char[]) obj2) : Arrays.equals((long[]) obj, (long[]) obj2) : Arrays.equals((int[]) obj, (int[]) obj2) : Arrays.equals((short[]) obj, (short[]) obj2) : Arrays.equals((byte[]) obj, (byte[]) obj2) : Arrays.deepEquals((Object[]) obj, (Object[]) obj2);
    }

    public static boolean equals(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    public static int hash(Object... objArr) {
        return Arrays.hashCode(objArr);
    }

    public static int hashCode(Object obj) {
        if (obj != null) {
            return obj.hashCode();
        }
        return 0;
    }

    public static boolean nonNull(Object obj) {
        return obj != null;
    }

    public static <T> T requireNonNull(T t) {
        t.getClass();
        return t;
    }

    public static <T> T requireNonNull(T t, String str) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(str);
    }

    public static <T> T requireNonNullElse(T t, T t2) {
        return t != null ? t : requireNonNull(t2, "defaultObj");
    }

    public static String toString(Object obj) {
        return String.valueOf(obj);
    }

    public static String toString(Object obj, String str) {
        return obj != null ? obj.toString() : str;
    }
}
