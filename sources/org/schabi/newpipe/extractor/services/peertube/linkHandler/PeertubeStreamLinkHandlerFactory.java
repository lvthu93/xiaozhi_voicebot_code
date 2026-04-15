package org.schabi.newpipe.extractor.services.peertube.linkHandler;

import java.net.MalformedURLException;
import java.net.URL;
import org.schabi.newpipe.extractor.ServiceList;
import org.schabi.newpipe.extractor.exceptions.FoundAdException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.LinkHandlerFactory;
import org.schabi.newpipe.extractor.utils.Parser;

public final class PeertubeStreamLinkHandlerFactory extends LinkHandlerFactory {
    public static final PeertubeStreamLinkHandlerFactory a = new PeertubeStreamLinkHandlerFactory();

    public static PeertubeStreamLinkHandlerFactory getInstance() {
        return a;
    }

    public String getId(String str) throws ParsingException, UnsupportedOperationException {
        return Parser.matchGroup("(/w/|(/videos/(watch/|embed/)?))(?!p/)([^/?&#]*)", str, 4);
    }

    public String getUrl(String str) throws ParsingException, UnsupportedOperationException {
        return getUrl(str, ServiceList.c.getBaseUrl());
    }

    public boolean onAcceptUrl(String str) throws FoundAdException {
        if (str.contains("/playlist/")) {
            return false;
        }
        try {
            new URL(str);
            getId(str);
            return true;
        } catch (MalformedURLException | ParsingException unused) {
            return false;
        }
    }

    public String getUrl(String str, String str2) {
        return str2 + "/videos/watch/" + str;
    }
}
