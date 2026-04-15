package org.schabi.newpipe.extractor.services.soundcloud.linkHandler;

import java.util.List;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandlerFactory;
import org.schabi.newpipe.extractor.services.soundcloud.SoundcloudParsingHelper;
import org.schabi.newpipe.extractor.utils.Parser;
import org.schabi.newpipe.extractor.utils.Utils;

public final class SoundcloudChannelLinkHandlerFactory extends ListLinkHandlerFactory {
    public static final SoundcloudChannelLinkHandlerFactory a = new SoundcloudChannelLinkHandlerFactory();

    public static SoundcloudChannelLinkHandlerFactory getInstance() {
        return a;
    }

    public String getId(String str) throws ParsingException, UnsupportedOperationException {
        Utils.checkUrl("^https?://(www\\.|m\\.)?soundcloud.com/[0-9a-z_-]+(/((tracks|albums|sets|reposts|followers|following)/?)?)?([#?].*)?$", str);
        try {
            return SoundcloudParsingHelper.resolveIdWithWidgetApi(str);
        } catch (Exception e) {
            throw new ParsingException(e.getMessage(), e);
        }
    }

    public String getUrl(String str, List<String> list, String str2) throws ParsingException, UnsupportedOperationException {
        try {
            return SoundcloudParsingHelper.resolveUrlWithEmbedPlayer("https://api.soundcloud.com/users/" + str);
        } catch (Exception e) {
            throw new ParsingException(e.getMessage(), e);
        }
    }

    public boolean onAcceptUrl(String str) {
        return Parser.isMatch("^https?://(www\\.|m\\.)?soundcloud.com/[0-9a-z_-]+(/((tracks|albums|sets|reposts|followers|following)/?)?)?([#?].*)?$", str.toLowerCase());
    }
}
