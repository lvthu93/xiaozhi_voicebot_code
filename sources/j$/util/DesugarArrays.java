package j$.util;

import j$.util.stream.D0;
import j$.util.stream.Stream;

public final /* synthetic */ class DesugarArrays {
    public static <T> Stream<T> stream(T[] tArr) {
        return D0.H0(i0.m(tArr, 0, tArr.length), false);
    }
}
