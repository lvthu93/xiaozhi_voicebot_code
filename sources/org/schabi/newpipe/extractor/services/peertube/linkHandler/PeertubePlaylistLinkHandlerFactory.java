package org.schabi.newpipe.extractor.services.peertube.linkHandler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.schabi.newpipe.extractor.ServiceList;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandlerFactory;
import org.schabi.newpipe.extractor.utils.Parser;

public final class PeertubePlaylistLinkHandlerFactory extends ListLinkHandlerFactory {
    public static final PeertubePlaylistLinkHandlerFactory a = new PeertubePlaylistLinkHandlerFactory();

    public static PeertubePlaylistLinkHandlerFactory getInstance() {
        return a;
    }

    public String getId(String str) throws ParsingException, UnsupportedOperationException {
        try {
            return Parser.matchGroup("(/videos/watch/playlist/|/w/p/)([^/?&#]*)", str, 2);
        } catch (ParsingException unused) {
            return Parser.matchGroup1("/video-playlists/([^/?&#]*)", str);
        }
    }

    public String getUrl(String str, List<String> list, String str2) throws ParsingException, UnsupportedOperationException {
        return getUrl(str, list, str2, ServiceList.c.getBaseUrl());
    }

    public boolean onAcceptUrl(String str) {
        try {
            new URL(str);
            getId(str);
            return true;
        } catch (MalformedURLException | ParsingException unused) {
            return false;
        }
    }

    public String getUrl(String str, List<String> list, String str2, String str3) throws ParsingException, UnsupportedOperationException {
        return str3 + "/api/v1/video-playlists/" + str;
    }
}
