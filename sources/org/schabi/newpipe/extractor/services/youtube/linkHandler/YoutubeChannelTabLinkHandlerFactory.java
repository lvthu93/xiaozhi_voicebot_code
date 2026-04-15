package org.schabi.newpipe.extractor.services.youtube.linkHandler;

import java.util.List;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.exceptions.UnsupportedTabException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandlerFactory;

public final class YoutubeChannelTabLinkHandlerFactory extends ListLinkHandlerFactory {
    public static final YoutubeChannelTabLinkHandlerFactory a = new YoutubeChannelTabLinkHandlerFactory();

    public static YoutubeChannelTabLinkHandlerFactory getInstance() {
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
            case -1415163932:
                if (str.equals("albums")) {
                    c = 1;
                    break;
                }
                break;
            case -903148681:
                if (str.equals("shorts")) {
                    c = 2;
                    break;
                }
                break;
            case -816678056:
                if (str.equals("videos")) {
                    c = 3;
                    break;
                }
                break;
            case -439267705:
                if (str.equals("livestreams")) {
                    c = 4;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return "/playlists";
            case 1:
                return "/releases";
            case 2:
                return "/shorts";
            case 3:
                return "/videos";
            case 4:
                return "/streams";
            default:
                throw new UnsupportedTabException(str);
        }
    }

    public String[] getAvailableContentFilter() {
        return new String[]{"videos", "shorts", "livestreams", "albums", "playlists"};
    }

    public String getId(String str) throws ParsingException, UnsupportedOperationException {
        return YoutubeChannelLinkHandlerFactory.getInstance().getId(str);
    }

    public String getUrl(String str, List<String> list, String str2) throws ParsingException, UnsupportedOperationException {
        return "https://www.youtube.com/" + str + getUrlSuffix(list.get(0));
    }

    public boolean onAcceptUrl(String str) throws ParsingException {
        try {
            getId(str);
            return true;
        } catch (ParsingException unused) {
            return false;
        }
    }
}
