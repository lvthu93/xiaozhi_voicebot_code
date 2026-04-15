package okhttp3.internal.http2;

import okhttp3.Headers;
import okhttp3.internal.Util;

public final class Header {
    public static final cq PSEUDO_PREFIX = cq.m(":");
    public static final cq RESPONSE_STATUS = cq.m(RESPONSE_STATUS_UTF8);
    public static final String RESPONSE_STATUS_UTF8 = ":status";
    public static final cq TARGET_AUTHORITY = cq.m(TARGET_AUTHORITY_UTF8);
    public static final String TARGET_AUTHORITY_UTF8 = ":authority";
    public static final cq TARGET_METHOD = cq.m(TARGET_METHOD_UTF8);
    public static final String TARGET_METHOD_UTF8 = ":method";
    public static final cq TARGET_PATH = cq.m(TARGET_PATH_UTF8);
    public static final String TARGET_PATH_UTF8 = ":path";
    public static final cq TARGET_SCHEME = cq.m(TARGET_SCHEME_UTF8);
    public static final String TARGET_SCHEME_UTF8 = ":scheme";
    final int hpackSize;
    public final cq name;
    public final cq value;

    public interface Listener {
        void onHeaders(Headers headers);
    }

    public Header(String str, String str2) {
        this(cq.m(str), cq.m(str2));
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Header)) {
            return false;
        }
        Header header = (Header) obj;
        if (!this.name.equals(header.name) || !this.value.equals(header.value)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return this.value.hashCode() + ((this.name.hashCode() + 527) * 31);
    }

    public String toString() {
        return Util.format("%s: %s", this.name.x(), this.value.x());
    }

    public Header(cq cqVar, String str) {
        this(cqVar, cq.m(str));
    }

    public Header(cq cqVar, cq cqVar2) {
        this.name = cqVar;
        this.value = cqVar2;
        this.hpackSize = cqVar2.t() + cqVar.t() + 32;
    }
}
