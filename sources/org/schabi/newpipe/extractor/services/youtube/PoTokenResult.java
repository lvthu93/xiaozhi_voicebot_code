package org.schabi.newpipe.extractor.services.youtube;

import j$.util.Objects;

public final class PoTokenResult {
    public final String a;
    public final String b;
    public final String c;

    public PoTokenResult(String str, String str2, String str3) {
        Objects.requireNonNull(str);
        this.a = str;
        Objects.requireNonNull(str2);
        this.b = str2;
        this.c = str3;
    }
}
