package com.google.common.base;

import java.util.logging.Logger;
import java.util.regex.Pattern;

final class Platform {
    public static final JdkPatternCompiler a = new JdkPatternCompiler();

    public static final class JdkPatternCompiler implements PatternCompiler {
        public CommonPattern compile(String str) {
            return new JdkPattern(Pattern.compile(str));
        }

        public boolean isPcreLike() {
            return true;
        }
    }

    static {
        Logger.getLogger(Platform.class.getName());
    }
}
