package org.schabi.newpipe.extractor.services.peertube.linkHandler;

import j$.util.Collection$EL;
import j$.util.Objects;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.schabi.newpipe.extractor.ServiceList;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandlerFactory;

public final class PeertubeTrendingLinkHandlerFactory extends ListLinkHandlerFactory {
    public static final PeertubeTrendingLinkHandlerFactory a = new PeertubeTrendingLinkHandlerFactory();
    public static final Map<String, String> b;

    static {
        int i = 0;
        Map.Entry[] entryArr = {new AbstractMap.SimpleEntry("Trending", "%s/api/v1/videos?sort=-trending"), new AbstractMap.SimpleEntry("Most liked", "%s/api/v1/videos?sort=-likes"), new AbstractMap.SimpleEntry("Recently added", "%s/api/v1/videos?sort=-publishedAt"), new AbstractMap.SimpleEntry("Local", "%s/api/v1/videos?sort=-publishedAt&isLocal=true")};
        HashMap hashMap = new HashMap(4);
        while (i < 4) {
            Map.Entry entry = entryArr[i];
            Object key = entry.getKey();
            Objects.requireNonNull(key);
            Object value = entry.getValue();
            Objects.requireNonNull(value);
            if (hashMap.put(key, value) == null) {
                i++;
            } else {
                throw new IllegalArgumentException("duplicate key: " + key);
            }
        }
        b = Collections.unmodifiableMap(hashMap);
    }

    public static PeertubeTrendingLinkHandlerFactory getInstance() {
        return a;
    }

    public String getId(String str) throws ParsingException, UnsupportedOperationException {
        String replace = str.replace(ServiceList.c.getBaseUrl(), "%s");
        if (replace.contains("/videos/trending")) {
            return "Trending";
        }
        if (replace.contains("/videos/most-liked")) {
            return "Most liked";
        }
        if (replace.contains("/videos/recently-added")) {
            return "Recently added";
        }
        if (replace.contains("/videos/local")) {
            return "Local";
        }
        return (String) Collection$EL.stream(b.entrySet()).filter(new y5(replace, 6)).findFirst().map(new p8(1)).orElseThrow(new cb(3));
    }

    public String getUrl(String str, List<String> list, String str2) throws ParsingException, UnsupportedOperationException {
        return getUrl(str, list, str2, ServiceList.c.getBaseUrl());
    }

    public boolean onAcceptUrl(String str) {
        try {
            new URL(str);
            if (str.contains("/videos?") || str.contains("/videos/trending") || str.contains("/videos/most-liked") || str.contains("/videos/recently-added") || str.contains("/videos/local")) {
                return true;
            }
            return false;
        } catch (MalformedURLException unused) {
            return false;
        }
    }

    public String getUrl(String str, List<String> list, String str2, String str3) throws ParsingException, UnsupportedOperationException {
        return String.format(b.get(str), new Object[]{str3});
    }
}
