package org.schabi.newpipe.extractor;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.schabi.newpipe.extractor.utils.Utils;

public class Page implements Serializable {
    public final String c;
    public final String f;
    public final List<String> g;
    public final Map<String, String> h;
    public final byte[] i;

    public Page(String str, String str2, List<String> list, Map<String, String> map, byte[] bArr) {
        this.c = str;
        this.f = str2;
        this.g = list;
        this.h = map;
        this.i = bArr;
    }

    public static boolean isValid(Page page) {
        if (page == null || (Utils.isNullOrEmpty(page.getUrl()) && Utils.isNullOrEmpty((Collection<?>) page.getIds()))) {
            return false;
        }
        return true;
    }

    public byte[] getBody() {
        return this.i;
    }

    public Map<String, String> getCookies() {
        return this.h;
    }

    public String getId() {
        return this.f;
    }

    public List<String> getIds() {
        return this.g;
    }

    public String getUrl() {
        return this.c;
    }

    public Page(String str) {
        this(str, (String) null, (List<String>) null, (Map<String, String>) null, (byte[]) null);
    }

    public Page(String str, String str2) {
        this(str, str2, (List<String>) null, (Map<String, String>) null, (byte[]) null);
    }

    public Page(String str, String str2, byte[] bArr) {
        this(str, str2, (List<String>) null, (Map<String, String>) null, bArr);
    }

    public Page(String str, byte[] bArr) {
        this(str, (String) null, (List<String>) null, (Map<String, String>) null, bArr);
    }

    public Page(String str, Map<String, String> map) {
        this(str, (String) null, (List<String>) null, map, (byte[]) null);
    }

    public Page(List<String> list) {
        this((String) null, (String) null, list, (Map<String, String>) null, (byte[]) null);
    }

    public Page(List<String> list, Map<String, String> map) {
        this((String) null, (String) null, list, map, (byte[]) null);
    }
}
