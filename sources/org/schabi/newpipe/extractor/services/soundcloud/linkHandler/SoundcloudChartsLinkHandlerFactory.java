package org.schabi.newpipe.extractor.services.soundcloud.linkHandler;

import java.util.List;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandlerFactory;
import org.schabi.newpipe.extractor.utils.Parser;

public final class SoundcloudChartsLinkHandlerFactory extends ListLinkHandlerFactory {
    public static final SoundcloudChartsLinkHandlerFactory a = new SoundcloudChartsLinkHandlerFactory();

    public static SoundcloudChartsLinkHandlerFactory getInstance() {
        return a;
    }

    public String getId(String str) throws ParsingException, UnsupportedOperationException {
        return "New & hot";
    }

    public String getUrl(String str, List<String> list, String str2) throws ParsingException, UnsupportedOperationException {
        return "https://soundcloud.com/charts/new";
    }

    public boolean onAcceptUrl(String str) {
        return Parser.isMatch("^https?://(www\\.|m\\.)?soundcloud.com/charts(/top|/new)?/?([#?].*)?$", str.toLowerCase());
    }
}
