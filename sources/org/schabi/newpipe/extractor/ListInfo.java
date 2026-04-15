package org.schabi.newpipe.extractor;

import java.util.List;
import org.schabi.newpipe.extractor.InfoItem;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;

public abstract class ListInfo<T extends InfoItem> extends Info {
    public List<T> k;
    public Page l = null;
    public final List<String> m;
    public final String n;

    public ListInfo(int i, String str, String str2, String str3, String str4, List<String> list, String str5) {
        super(i, str, str2, str3, str4);
        this.m = list;
        this.n = str5;
    }

    public List<String> getContentFilters() {
        return this.m;
    }

    public Page getNextPage() {
        return this.l;
    }

    public List<T> getRelatedItems() {
        return this.k;
    }

    public String getSortFilter() {
        return this.n;
    }

    public boolean hasNextPage() {
        return Page.isValid(this.l);
    }

    public void setNextPage(Page page) {
        this.l = page;
    }

    public void setRelatedItems(List<T> list) {
        this.k = list;
    }

    public ListInfo(int i, ListLinkHandler listLinkHandler, String str) {
        super(i, listLinkHandler, str);
        this.m = listLinkHandler.getContentFilters();
        this.n = listLinkHandler.getSortFilter();
    }
}
