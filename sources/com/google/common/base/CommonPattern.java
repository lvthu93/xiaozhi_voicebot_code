package com.google.common.base;

import com.google.common.base.Platform;

abstract class CommonPattern {
    public static CommonPattern compile(String str) {
        Platform.JdkPatternCompiler jdkPatternCompiler = Platform.a;
        Preconditions.checkNotNull(str);
        return Platform.a.compile(str);
    }

    public static boolean isPcreLike() {
        return Platform.a.isPcreLike();
    }

    public abstract int flags();

    public abstract CommonMatcher matcher(CharSequence charSequence);

    public abstract String pattern();

    public abstract String toString();
}
