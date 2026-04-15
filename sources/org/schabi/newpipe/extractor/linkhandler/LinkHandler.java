package org.schabi.newpipe.extractor.linkhandler;

import java.io.Serializable;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.utils.Utils;

public class LinkHandler implements Serializable {
    public final String c;
    public final String f;
    public final String g;

    public LinkHandler(String str, String str2, String str3) {
        this.c = str;
        this.f = str2;
        this.g = str3;
    }

    public String getBaseUrl() throws ParsingException {
        return Utils.getBaseUrl(this.f);
    }

    public String getId() {
        return this.g;
    }

    public String getOriginalUrl() {
        return this.c;
    }

    public String getUrl() {
        return this.f;
    }

    public LinkHandler(LinkHandler linkHandler) {
        this(linkHandler.c, linkHandler.f, linkHandler.g);
    }
}
