package org.schabi.newpipe.extractor.linkhandler;

import j$.util.Objects;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.utils.Utils;

public abstract class LinkHandlerFactory {
    public boolean acceptUrl(String str) throws ParsingException {
        return onAcceptUrl(str);
    }

    public LinkHandler fromId(String str) throws ParsingException {
        Objects.requireNonNull(str, "ID cannot be null");
        String url = getUrl(str);
        return new LinkHandler(url, url, str);
    }

    public LinkHandler fromUrl(String str) throws ParsingException {
        if (!Utils.isNullOrEmpty(str)) {
            String followGoogleRedirectIfNeeded = Utils.followGoogleRedirectIfNeeded(str);
            return fromUrl(followGoogleRedirectIfNeeded, Utils.getBaseUrl(followGoogleRedirectIfNeeded));
        }
        throw new IllegalArgumentException("The url is null or empty");
    }

    public abstract String getId(String str) throws ParsingException, UnsupportedOperationException;

    public abstract String getUrl(String str) throws ParsingException, UnsupportedOperationException;

    public String getUrl(String str, String str2) throws ParsingException, UnsupportedOperationException {
        return getUrl(str);
    }

    public abstract boolean onAcceptUrl(String str) throws ParsingException;

    public LinkHandler fromId(String str, String str2) throws ParsingException {
        Objects.requireNonNull(str, "ID cannot be null");
        String url = getUrl(str, str2);
        return new LinkHandler(url, url, str);
    }

    public LinkHandler fromUrl(String str, String str2) throws ParsingException {
        Objects.requireNonNull(str, "URL cannot be null");
        if (acceptUrl(str)) {
            String id = getId(str);
            return new LinkHandler(str, getUrl(id, str2), id);
        }
        throw new ParsingException("URL not accepted: ".concat(str));
    }
}
