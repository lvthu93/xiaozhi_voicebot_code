package org.schabi.newpipe.extractor.services.peertube.linkHandler;

import java.util.List;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.exceptions.UnsupportedTabException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandlerFactory;

public final class PeertubeChannelTabLinkHandlerFactory extends ListLinkHandlerFactory {
    public static final PeertubeChannelTabLinkHandlerFactory a = new PeertubeChannelTabLinkHandlerFactory();

    public static PeertubeChannelTabLinkHandlerFactory getInstance() {
        return a;
    }

    public static String getUrlSuffix(String str) throws UnsupportedTabException {
        str.getClass();
        char c = 65535;
        switch (str.hashCode()) {
            case -1865828127:
                if (str.equals("playlists")) {
                    c = 0;
                    break;
                }
                break;
            case -816678056:
                if (str.equals("videos")) {
                    c = 1;
                    break;
                }
                break;
            case 1432626128:
                if (str.equals("channels")) {
                    c = 2;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return "/video-playlists";
            case 1:
                return "/videos";
            case 2:
                return "/video-channels";
            default:
                throw new UnsupportedTabException(str);
        }
    }

    public String[] getAvailableContentFilter() {
        return new String[]{"videos", "channels", "playlists"};
    }

    public String getId(String str) throws ParsingException, UnsupportedOperationException {
        return PeertubeChannelLinkHandlerFactory.getInstance().getId(str);
    }

    public String getUrl(String str, List<String> list, String str2) throws ParsingException, UnsupportedOperationException {
        return PeertubeChannelLinkHandlerFactory.getInstance().getUrl(str) + getUrlSuffix(list.get(0));
    }

    public boolean onAcceptUrl(String str) throws ParsingException {
        return PeertubeChannelLinkHandlerFactory.getInstance().onAcceptUrl(str);
    }

    public String getUrl(String str, List<String> list, String str2, String str3) throws ParsingException, UnsupportedOperationException {
        return PeertubeChannelLinkHandlerFactory.getInstance().getUrl(str, (List<String>) null, (String) null, str3) + getUrlSuffix(list.get(0));
    }
}
