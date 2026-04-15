package org.schabi.newpipe.extractor;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.schabi.newpipe.extractor.InfoItem;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;

public abstract class ListExtractor<R extends InfoItem> extends Extractor {

    public static class InfoItemsPage<T extends InfoItem> {
        public static final InfoItemsPage<InfoItem> d = new InfoItemsPage<>(Collections.emptyList(), (Page) null, Collections.emptyList());
        public final List<T> a;
        public final Page b;
        public final List<Throwable> c;

        public InfoItemsPage(InfoItemsCollector<T, ?> infoItemsCollector, Page page) {
            this(infoItemsCollector.getItems(), page, infoItemsCollector.getErrors());
        }

        public static <T extends InfoItem> InfoItemsPage<T> emptyPage() {
            return d;
        }

        public List<Throwable> getErrors() {
            return this.c;
        }

        public List<T> getItems() {
            return this.a;
        }

        public Page getNextPage() {
            return this.b;
        }

        public boolean hasNextPage() {
            return Page.isValid(this.b);
        }

        public InfoItemsPage(List<T> list, Page page, List<Throwable> list2) {
            this.a = list;
            this.b = page;
            this.c = list2;
        }
    }

    public ListExtractor(StreamingService streamingService, ListLinkHandler listLinkHandler) {
        super(streamingService, listLinkHandler);
    }

    public abstract InfoItemsPage<R> getInitialPage() throws IOException, ExtractionException;

    public abstract InfoItemsPage<R> getPage(Page page) throws IOException, ExtractionException;

    public ListLinkHandler getLinkHandler() {
        return (ListLinkHandler) super.getLinkHandler();
    }
}
