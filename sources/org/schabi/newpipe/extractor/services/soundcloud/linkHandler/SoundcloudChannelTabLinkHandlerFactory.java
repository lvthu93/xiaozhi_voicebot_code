package org.schabi.newpipe.extractor.services.soundcloud.linkHandler;

import java.util.List;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.exceptions.UnsupportedTabException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandlerFactory;

public final class SoundcloudChannelTabLinkHandlerFactory extends ListLinkHandlerFactory {
    public static final SoundcloudChannelTabLinkHandlerFactory a = new SoundcloudChannelTabLinkHandlerFactory();

    public static SoundcloudChannelTabLinkHandlerFactory getInstance() {
        return a;
    }

    public static String getUrlSuffix(String str) throws UnsupportedOperationException {
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
            case -865716088:
                if (str.equals("tracks")) {
                    c = 2;
                    break;
                }
                break;
            case 102974396:
                if (str.equals("likes")) {
                    c = 3;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return "/sets";
            case 1:
                return "/albums";
            case 2:
                return "/tracks";
            case 3:
                return "/likes";
            default:
                throw new UnsupportedTabException(str);
        }
    }

    public String[] getAvailableContentFilter() {
        return new String[]{"tracks", "playlists", "albums", "likes"};
    }

    public String getId(String str) throws ParsingException {
        return SoundcloudChannelLinkHandlerFactory.getInstance().getId(str);
    }

    public String getUrl(String str, List<String> list, String str2) throws ParsingException {
        return SoundcloudChannelLinkHandlerFactory.getInstance().getUrl(str) + getUrlSuffix(list.get(0));
    }

    public boolean onAcceptUrl(String str) throws ParsingException {
        return SoundcloudChannelLinkHandlerFactory.getInstance().onAcceptUrl(str);
    }
}
