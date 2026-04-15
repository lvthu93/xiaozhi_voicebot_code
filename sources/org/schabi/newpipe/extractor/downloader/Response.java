package org.schabi.newpipe.extractor.downloader;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Response {
    public final int a;
    public final String b;
    public final Map<String, List<String>> c;
    public final String d;
    public final String e;

    public Response(int i, String str, Map<String, List<String>> map, String str2, String str3) {
        this.a = i;
        this.b = str;
        this.c = map == null ? Collections.emptyMap() : map;
        this.d = str2 == null ? "" : str2;
        this.e = str3;
    }

    public String getHeader(String str) {
        for (Map.Entry next : this.c.entrySet()) {
            String str2 = (String) next.getKey();
            if (str2 != null && str2.equalsIgnoreCase(str) && !((List) next.getValue()).isEmpty()) {
                return (String) ((List) next.getValue()).get(0);
            }
        }
        return null;
    }

    public String latestUrl() {
        return this.e;
    }

    public String responseBody() {
        return this.d;
    }

    public int responseCode() {
        return this.a;
    }

    public Map<String, List<String>> responseHeaders() {
        return this.c;
    }

    public String responseMessage() {
        return this.b;
    }
}
