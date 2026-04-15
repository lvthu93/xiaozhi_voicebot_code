package com.google.common.base;

import com.google.common.base.Platform;

public abstract class Ticker {
    public static final Ticker a = new Ticker() {
        public long read() {
            Platform.JdkPatternCompiler jdkPatternCompiler = Platform.a;
            return System.nanoTime();
        }
    };

    public static Ticker systemTicker() {
        return a;
    }

    public abstract long read();
}
