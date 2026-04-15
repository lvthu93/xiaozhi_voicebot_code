package com.google.common.base;

import java.io.Serializable;

public enum CaseFormat {
    ;
    
    public final CharMatcher c;
    public final String f;

    public static final class StringConverter extends Converter<String, String> implements Serializable {
        private static final long serialVersionUID = 0;
        public final CaseFormat g;
        public final CaseFormat h;

        public StringConverter(CaseFormat caseFormat, CaseFormat caseFormat2) {
            this.g = (CaseFormat) Preconditions.checkNotNull(caseFormat);
            this.h = (CaseFormat) Preconditions.checkNotNull(caseFormat2);
        }

        public final Object d(Object obj) {
            return this.h.to(this.g, (String) obj);
        }

        public final Object e(Object obj) {
            return this.g.to(this.h, (String) obj);
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof StringConverter)) {
                return false;
            }
            StringConverter stringConverter = (StringConverter) obj;
            if (!this.g.equals(stringConverter.g) || !this.h.equals(stringConverter.h)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return this.g.hashCode() ^ this.h.hashCode();
        }

        public String toString() {
            return this.g + ".converterTo(" + this.h + ")";
        }
    }

    /* access modifiers changed from: public */
    CaseFormat() {
        throw null;
    }

    /* access modifiers changed from: public */
    CaseFormat(CharMatcher charMatcher, String str) {
        this.c = charMatcher;
        this.f = str;
    }

    public static String b(String str) {
        if (str.isEmpty()) {
            return str;
        }
        return Ascii.toUpperCase(str.charAt(0)) + Ascii.toLowerCase(str.substring(1));
    }

    public Converter<String, String> converterTo(CaseFormat caseFormat) {
        return new StringConverter(this, caseFormat);
    }

    public String d(CaseFormat caseFormat, String str) {
        CaseFormat caseFormat2;
        String str2;
        StringBuilder sb = null;
        int i2 = 0;
        int i3 = -1;
        while (true) {
            i3 = this.c.indexIn(str, i3 + 1);
            caseFormat2 = i;
            if (i3 == -1) {
                break;
            }
            String str3 = this.f;
            if (i2 == 0) {
                sb = new StringBuilder((str3.length() * 4) + str.length());
                String substring = str.substring(i2, i3);
                if (caseFormat == caseFormat2) {
                    caseFormat.getClass();
                    str2 = Ascii.toLowerCase(substring);
                } else {
                    str2 = caseFormat.e(substring);
                }
                sb.append(str2);
            } else {
                sb.append(caseFormat.e(str.substring(i2, i3)));
            }
            sb.append(caseFormat.f);
            i2 = str3.length() + i3;
        }
        if (i2 != 0) {
            sb.append(caseFormat.e(str.substring(i2)));
            return sb.toString();
        } else if (caseFormat != caseFormat2) {
            return caseFormat.e(str);
        } else {
            caseFormat.getClass();
            return Ascii.toLowerCase(str);
        }
    }

    public abstract String e(String str);

    public final String to(CaseFormat caseFormat, String str) {
        Preconditions.checkNotNull(caseFormat);
        Preconditions.checkNotNull(str);
        if (caseFormat == this) {
            return str;
        }
        return d(caseFormat, str);
    }
}
