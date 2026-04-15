package org.schabi.newpipe.extractor.services.peertube.linkHandler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.schabi.newpipe.extractor.ServiceList;
import org.schabi.newpipe.extractor.exceptions.FoundAdException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandlerFactory;

public final class PeertubeCommentsLinkHandlerFactory extends ListLinkHandlerFactory {
    public static final PeertubeCommentsLinkHandlerFactory a = new PeertubeCommentsLinkHandlerFactory();

    public static PeertubeCommentsLinkHandlerFactory getInstance() {
        return a;
    }

    public String getId(String str) throws ParsingException, UnsupportedOperationException {
        return PeertubeStreamLinkHandlerFactory.getInstance().getId(str);
    }

    public String getUrl(String str, List<String> list, String str2, String str3) throws ParsingException, UnsupportedOperationException {
        StringBuilder m = y2.m(str3);
        m.append(String.format("/api/v1/videos/%s/comment-threads", new Object[]{str}));
        return m.toString();
    }

    public boolean onAcceptUrl(String str) throws FoundAdException {
        try {
            new URL(str);
            if (str.contains("/videos/") || str.contains("/w/")) {
                return true;
            }
            return false;
        } catch (MalformedURLException unused) {
            return false;
        }
    }

    public String getUrl(String str, List<String> list, String str2) throws ParsingException, UnsupportedOperationException {
        return getUrl(str, list, str2, ServiceList.c.getBaseUrl());
    }
}
