package com.google.common.base;

import java.util.Arrays;

public final class MoreObjects {

    public static final class ToStringHelper {
        public final String a;
        public final ValueHolder b;
        public ValueHolder c;
        public boolean d = false;

        public static final class ValueHolder {
            public String a;
            public Object b;
            public ValueHolder c;
        }

        public ToStringHelper(String str) {
            ValueHolder valueHolder = new ValueHolder();
            this.b = valueHolder;
            this.c = valueHolder;
            this.a = (String) Preconditions.checkNotNull(str);
        }

        public final void a(Object obj) {
            ValueHolder valueHolder = new ValueHolder();
            this.c.c = valueHolder;
            this.c = valueHolder;
            valueHolder.b = obj;
        }

        public ToStringHelper add(String str, Object obj) {
            b(obj, str);
            return this;
        }

        public ToStringHelper addValue(Object obj) {
            a(obj);
            return this;
        }

        public final void b(Object obj, String str) {
            ValueHolder valueHolder = new ValueHolder();
            this.c.c = valueHolder;
            this.c = valueHolder;
            valueHolder.b = obj;
            valueHolder.a = (String) Preconditions.checkNotNull(str);
        }

        public ToStringHelper omitNullValues() {
            this.d = true;
            return this;
        }

        public String toString() {
            boolean z = this.d;
            StringBuilder sb = new StringBuilder(32);
            sb.append(this.a);
            sb.append('{');
            String str = "";
            for (ValueHolder valueHolder = this.b.c; valueHolder != null; valueHolder = valueHolder.c) {
                Object obj = valueHolder.b;
                if (!z || obj != null) {
                    sb.append(str);
                    String str2 = valueHolder.a;
                    if (str2 != null) {
                        sb.append(str2);
                        sb.append('=');
                    }
                    if (obj == null || !obj.getClass().isArray()) {
                        sb.append(obj);
                    } else {
                        String deepToString = Arrays.deepToString(new Object[]{obj});
                        sb.append(deepToString, 1, deepToString.length() - 1);
                    }
                    str = ", ";
                }
            }
            sb.append('}');
            return sb.toString();
        }

        public ToStringHelper add(String str, boolean z) {
            b(String.valueOf(z), str);
            return this;
        }

        public ToStringHelper addValue(boolean z) {
            a(String.valueOf(z));
            return this;
        }

        public ToStringHelper add(String str, char c2) {
            b(String.valueOf(c2), str);
            return this;
        }

        public ToStringHelper addValue(char c2) {
            a(String.valueOf(c2));
            return this;
        }

        public ToStringHelper add(String str, double d2) {
            b(String.valueOf(d2), str);
            return this;
        }

        public ToStringHelper addValue(double d2) {
            a(String.valueOf(d2));
            return this;
        }

        public ToStringHelper add(String str, float f) {
            b(String.valueOf(f), str);
            return this;
        }

        public ToStringHelper addValue(float f) {
            a(String.valueOf(f));
            return this;
        }

        public ToStringHelper add(String str, int i) {
            b(String.valueOf(i), str);
            return this;
        }

        public ToStringHelper addValue(int i) {
            a(String.valueOf(i));
            return this;
        }

        public ToStringHelper add(String str, long j) {
            b(String.valueOf(j), str);
            return this;
        }

        public ToStringHelper addValue(long j) {
            a(String.valueOf(j));
            return this;
        }
    }

    public static <T> T firstNonNull(T t, T t2) {
        if (t != null) {
            return t;
        }
        if (t2 != null) {
            return t2;
        }
        throw new NullPointerException("Both parameters are null");
    }

    public static ToStringHelper toStringHelper(Object obj) {
        return new ToStringHelper(obj.getClass().getSimpleName());
    }

    public static ToStringHelper toStringHelper(Class<?> cls) {
        return new ToStringHelper(cls.getSimpleName());
    }

    public static ToStringHelper toStringHelper(String str) {
        return new ToStringHelper(str);
    }
}
