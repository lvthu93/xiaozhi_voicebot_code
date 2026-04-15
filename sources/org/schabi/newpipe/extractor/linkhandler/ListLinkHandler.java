package org.schabi.newpipe.extractor.linkhandler;

import java.util.Collections;
import java.util.List;

public class ListLinkHandler extends LinkHandler {
    public final List<String> h;
    public final String i;

    public ListLinkHandler(String str, String str2, String str3, List<String> list, String str4) {
        super(str, str2, str3);
        this.h = Collections.unmodifiableList(list);
        this.i = str4;
    }

    public List<String> getContentFilters() {
        return this.h;
    }

    public String getSortFilter() {
        return this.i;
    }

    public ListLinkHandler(ListLinkHandler listLinkHandler) {
        this(listLinkHandler.c, listLinkHandler.f, listLinkHandler.g, listLinkHandler.h, listLinkHandler.i);
    }

    public ListLinkHandler(LinkHandler linkHandler) {
        this(linkHandler.c, linkHandler.f, linkHandler.g, Collections.emptyList(), "");
    }
}
