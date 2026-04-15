package org.schabi.newpipe.extractor.linkhandler;

import j$.util.Objects;
import java.util.ArrayList;
import java.util.List;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.utils.Utils;

public abstract class ListLinkHandlerFactory extends LinkHandlerFactory {
    public ListLinkHandler fromQuery(String str, List<String> list, String str2) throws ParsingException {
        String url = getUrl(str, list, str2);
        return new ListLinkHandler(url, url, str, list, str2);
    }

    public String[] getAvailableContentFilter() {
        return new String[0];
    }

    public String[] getAvailableSortFilter() {
        return new String[0];
    }

    public abstract String getUrl(String str, List<String> list, String str2) throws ParsingException, UnsupportedOperationException;

    public String getUrl(String str, List<String> list, String str2, String str3) throws ParsingException, UnsupportedOperationException {
        return getUrl(str, list, str2);
    }

    public String getUrl(String str) throws ParsingException, UnsupportedOperationException {
        return getUrl(str, new ArrayList(0), "");
    }

    public ListLinkHandler fromId(String str) throws ParsingException {
        return new ListLinkHandler(super.fromId(str));
    }

    public ListLinkHandler fromQuery(String str, List<String> list, String str2, String str3) throws ParsingException {
        String url = getUrl(str, list, str2, str3);
        return new ListLinkHandler(url, url, str, list, str2);
    }

    public ListLinkHandler fromUrl(String str) throws ParsingException {
        String followGoogleRedirectIfNeeded = Utils.followGoogleRedirectIfNeeded(str);
        return fromUrl(followGoogleRedirectIfNeeded, Utils.getBaseUrl(followGoogleRedirectIfNeeded));
    }

    public String getUrl(String str, String str2) throws ParsingException {
        return getUrl(str, new ArrayList(0), "", str2);
    }

    public ListLinkHandler fromId(String str, String str2) throws ParsingException {
        return new ListLinkHandler(super.fromId(str, str2));
    }

    public ListLinkHandler fromUrl(String str, String str2) throws ParsingException {
        Objects.requireNonNull(str, "URL may not be null");
        return new ListLinkHandler(super.fromUrl(str, str2));
    }
}
