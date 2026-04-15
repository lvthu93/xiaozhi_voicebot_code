package com.google.common.base;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class JdkPattern extends CommonPattern implements Serializable {
    private static final long serialVersionUID = 0;
    public final Pattern c;

    public static final class JdkMatcher extends CommonMatcher {
        public final Matcher a;

        public JdkMatcher(Matcher matcher) {
            this.a = (Matcher) Preconditions.checkNotNull(matcher);
        }

        public int end() {
            return this.a.end();
        }

        public boolean find() {
            return this.a.find();
        }

        public boolean matches() {
            return this.a.matches();
        }

        public String replaceAll(String str) {
            return this.a.replaceAll(str);
        }

        public int start() {
            return this.a.start();
        }

        public boolean find(int i) {
            return this.a.find(i);
        }
    }

    public JdkPattern(Pattern pattern) {
        this.c = (Pattern) Preconditions.checkNotNull(pattern);
    }

    public int flags() {
        return this.c.flags();
    }

    public CommonMatcher matcher(CharSequence charSequence) {
        return new JdkMatcher(this.c.matcher(charSequence));
    }

    public String pattern() {
        return this.c.pattern();
    }

    public String toString() {
        return this.c.toString();
    }
}
