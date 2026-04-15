package org.schabi.newpipe.extractor.services.bandcamp.linkHandler;

import java.util.List;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.exceptions.UnsupportedTabException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandlerFactory;

public final class BandcampChannelTabLinkHandlerFactory extends ListLinkHandlerFactory {
    public static final BandcampChannelTabLinkHandlerFactory a = new BandcampChannelTabLinkHandlerFactory();

    public static BandcampChannelTabLinkHandlerFactory getInstance() {
        return a;
    }

    public static String getUrlSuffix(String str) throws UnsupportedTabException {
        str.getClass();
        if (str.equals("albums")) {
            return "/album";
        }
        if (str.equals("tracks")) {
            return "/track";
        }
        throw new UnsupportedTabException(str);
    }

    public String[] getAvailableContentFilter() {
        return new String[]{"tracks", "albums"};
    }

    public String getId(String str) throws ParsingException, UnsupportedOperationException {
        return BandcampChannelLinkHandlerFactory.getInstance().getId(str);
    }

    public String getUrl(String str, List<String> list, String str2) throws ParsingException, UnsupportedOperationException {
        return BandcampChannelLinkHandlerFactory.getInstance().getUrl(str) + getUrlSuffix(list.get(0));
    }

    public boolean onAcceptUrl(String str) throws ParsingException {
        return BandcampChannelLinkHandlerFactory.getInstance().onAcceptUrl(str);
    }
}
