package org.schabi.newpipe.extractor.linkhandler;

import java.util.List;

public class SearchQueryHandler extends ListLinkHandler {
    public SearchQueryHandler(String str, String str2, String str3, List<String> list, String str4) {
        super(str, str2, str3, list, str4);
    }

    public String getSearchString() {
        return getId();
    }

    public SearchQueryHandler(ListLinkHandler listLinkHandler) {
        this(listLinkHandler.c, listLinkHandler.f, listLinkHandler.g, listLinkHandler.h, listLinkHandler.i);
    }
}
