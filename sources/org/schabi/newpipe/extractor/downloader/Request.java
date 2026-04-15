package org.schabi.newpipe.extractor.downloader;

import j$.util.Objects;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.schabi.newpipe.extractor.localization.Localization;

public class Request {
    public final String a;
    public final String b;
    public final Map<String, List<String>> c;
    public final byte[] d;
    public final Localization e;

    public static final class Builder {
        public String a;
        public String b;
        public final LinkedHashMap c = new LinkedHashMap();
        public byte[] d;
        public Localization e;
        public boolean f = true;

        public Builder addHeader(String str, String str2) {
            return addHeaders(str, Collections.singletonList(str2));
        }

        public Builder addHeaders(String str, List<String> list) {
            LinkedHashMap linkedHashMap = this.c;
            List list2 = (List) linkedHashMap.get(str);
            if (list2 == null) {
                list2 = new ArrayList();
            }
            list2.addAll(list);
            linkedHashMap.put(str, list);
            return this;
        }

        public Builder automaticLocalizationHeader(boolean z) {
            this.f = z;
            return this;
        }

        public Request build() {
            return new Request(this.a, this.b, this.c, this.d, this.e, this.f);
        }

        public Builder dataToSend(byte[] bArr) {
            this.d = bArr;
            return this;
        }

        public Builder get(String str) {
            this.a = "GET";
            this.b = str;
            return this;
        }

        public Builder head(String str) {
            this.a = "HEAD";
            this.b = str;
            return this;
        }

        public Builder headers(Map<String, List<String>> map) {
            LinkedHashMap linkedHashMap = this.c;
            linkedHashMap.clear();
            if (map != null) {
                linkedHashMap.putAll(map);
            }
            return this;
        }

        public Builder httpMethod(String str) {
            this.a = str;
            return this;
        }

        public Builder localization(Localization localization) {
            this.e = localization;
            return this;
        }

        public Builder post(String str, byte[] bArr) {
            this.a = "POST";
            this.b = str;
            this.d = bArr;
            return this;
        }

        public Builder setHeader(String str, String str2) {
            return setHeaders(str, Collections.singletonList(str2));
        }

        public Builder setHeaders(String str, List<String> list) {
            LinkedHashMap linkedHashMap = this.c;
            linkedHashMap.remove(str);
            linkedHashMap.put(str, list);
            return this;
        }

        public Builder url(String str) {
            this.b = str;
            return this;
        }
    }

    public Request(String str, String str2, Map<String, List<String>> map, byte[] bArr, Localization localization, boolean z) {
        Objects.requireNonNull(str, "Request's httpMethod is null");
        this.a = str;
        Objects.requireNonNull(str2, "Request's url is null");
        this.b = str2;
        this.d = bArr;
        this.e = localization;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        if (map != null) {
            linkedHashMap.putAll(map);
        }
        if (z && localization != null) {
            linkedHashMap.putAll(getHeadersFromLocalization(localization));
        }
        this.c = Collections.unmodifiableMap(linkedHashMap);
    }

    public static Map<String, List<String>> getHeadersFromLocalization(Localization localization) {
        if (localization == null) {
            return Collections.emptyMap();
        }
        String languageCode = localization.getLanguageCode();
        if (!localization.getCountryCode().isEmpty()) {
            languageCode = localization.getLocalizationCode() + ", " + languageCode + ";q=0.9";
        }
        return Collections.singletonMap("Accept-Language", Collections.singletonList(languageCode));
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public byte[] dataToSend() {
        return this.d;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Request request = (Request) obj;
        if (!this.a.equals(request.a) || !this.b.equals(request.b) || !this.c.equals(request.c) || !Arrays.equals(this.d, request.d) || !Objects.equals(this.e, request.e)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        Object[] objArr = {this.a, this.b, this.c, this.e};
        return Arrays.hashCode(this.d) + (Objects.hash(objArr) * 31);
    }

    public Map<String, List<String>> headers() {
        return this.c;
    }

    public String httpMethod() {
        return this.a;
    }

    public Localization localization() {
        return this.e;
    }

    public String url() {
        return this.b;
    }
}
