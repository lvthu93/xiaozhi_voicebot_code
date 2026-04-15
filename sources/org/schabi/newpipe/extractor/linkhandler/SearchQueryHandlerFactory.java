package org.schabi.newpipe.extractor.linkhandler;

import java.util.Collections;
import java.util.List;
import org.schabi.newpipe.extractor.exceptions.ParsingException;

public abstract class SearchQueryHandlerFactory extends ListLinkHandlerFactory {
    public String getId(String str) throws ParsingException, UnsupportedOperationException {
        return getSearchString(str);
    }

    public String getSearchString(String str) {
        return "";
    }

    public abstract String getUrl(String str, List<String> list, String str2) throws ParsingException, UnsupportedOperationException;

    public boolean onAcceptUrl(String str) {
        return false;
    }

    public SearchQueryHandler fromQuery(String str, List<String> list, String str2) throws ParsingException {
        return new SearchQueryHandler(super.fromQuery(str, list, str2));
    }

    public SearchQueryHandler fromQuery(String str) throws ParsingException {
        return fromQuery(str, Collections.emptyList(), "");
    }
}
