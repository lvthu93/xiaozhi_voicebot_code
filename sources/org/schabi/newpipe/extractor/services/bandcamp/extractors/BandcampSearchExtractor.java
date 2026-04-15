package org.schabi.newpipe.extractor.services.bandcamp.extractors;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.schabi.newpipe.extractor.InfoItem;
import org.schabi.newpipe.extractor.ListExtractor;
import org.schabi.newpipe.extractor.MetaInfo;
import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.SearchQueryHandler;
import org.schabi.newpipe.extractor.search.SearchExtractor;

public class BandcampSearchExtractor extends SearchExtractor {
    public BandcampSearchExtractor(StreamingService streamingService, SearchQueryHandler searchQueryHandler) {
        super(streamingService, searchQueryHandler);
    }

    public ListExtractor.InfoItemsPage<InfoItem> getInitialPage() throws IOException, ExtractionException {
        return getPage(new Page(getUrl()));
    }

    public List<MetaInfo> getMetaInfo() throws ParsingException {
        return Collections.emptyList();
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0076, code lost:
        if (r9.equals("ARTIST") == false) goto L_0x006e;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.schabi.newpipe.extractor.ListExtractor.InfoItemsPage<org.schabi.newpipe.extractor.InfoItem> getPage(org.schabi.newpipe.extractor.Page r13) throws java.io.IOException, org.schabi.newpipe.extractor.exceptions.ExtractionException {
        /*
            r12 = this;
            org.schabi.newpipe.extractor.MultiInfoItemsCollector r0 = new org.schabi.newpipe.extractor.MultiInfoItemsCollector
            int r1 = r12.getServiceId()
            r0.<init>(r1)
            org.schabi.newpipe.extractor.downloader.Downloader r1 = r12.getDownloader()
            java.lang.String r2 = r13.getUrl()
            org.schabi.newpipe.extractor.downloader.Response r1 = r1.get(r2)
            java.lang.String r1 = r1.responseBody()
            org.jsoup.nodes.Document r1 = org.jsoup.Jsoup.parse(r1)
            java.lang.String r2 = "searchresult"
            org.jsoup.select.Elements r2 = r1.getElementsByClass(r2)
            java.util.Iterator r2 = r2.iterator()
        L_0x0027:
            boolean r3 = r2.hasNext()
            r4 = 2
            r5 = -1
            r6 = 0
            r7 = 0
            r8 = 1
            if (r3 == 0) goto L_0x00ae
            java.lang.Object r3 = r2.next()
            org.jsoup.nodes.Element r3 = (org.jsoup.nodes.Element) r3
            java.lang.String r9 = "result-info"
            org.jsoup.select.Elements r9 = r3.getElementsByClass(r9)
            j$.util.stream.Stream r9 = j$.util.Collection$EL.stream(r9)
            z5 r10 = new z5
            r11 = 14
            r10.<init>(r11)
            j$.util.stream.Stream r9 = r9.flatMap(r10)
            z5 r10 = new z5
            r11 = 15
            r10.<init>(r11)
            j$.util.stream.Stream r9 = r9.map(r10)
            j$.util.Optional r9 = r9.findFirst()
            java.lang.String r10 = ""
            java.lang.Object r9 = r9.orElse(r10)
            java.lang.String r9 = (java.lang.String) r9
            r9.getClass()
            int r10 = r9.hashCode()
            switch(r10) {
                case 62359119: goto L_0x0084;
                case 80083243: goto L_0x0079;
                case 1939198791: goto L_0x0070;
                default: goto L_0x006e;
            }
        L_0x006e:
            r4 = -1
            goto L_0x008e
        L_0x0070:
            java.lang.String r7 = "ARTIST"
            boolean r7 = r9.equals(r7)
            if (r7 != 0) goto L_0x008e
            goto L_0x006e
        L_0x0079:
            java.lang.String r4 = "TRACK"
            boolean r4 = r9.equals(r4)
            if (r4 != 0) goto L_0x0082
            goto L_0x006e
        L_0x0082:
            r4 = 1
            goto L_0x008e
        L_0x0084:
            java.lang.String r4 = "ALBUM"
            boolean r4 = r9.equals(r4)
            if (r4 != 0) goto L_0x008d
            goto L_0x006e
        L_0x008d:
            r4 = 0
        L_0x008e:
            switch(r4) {
                case 0: goto L_0x00a4;
                case 1: goto L_0x009b;
                case 2: goto L_0x0092;
                default: goto L_0x0091;
            }
        L_0x0091:
            goto L_0x0027
        L_0x0092:
            org.schabi.newpipe.extractor.services.bandcamp.extractors.BandcampChannelInfoItemExtractor r4 = new org.schabi.newpipe.extractor.services.bandcamp.extractors.BandcampChannelInfoItemExtractor
            r4.<init>(r3)
            r0.commit(r4)
            goto L_0x0027
        L_0x009b:
            org.schabi.newpipe.extractor.services.bandcamp.extractors.streaminfoitem.BandcampSearchStreamInfoItemExtractor r4 = new org.schabi.newpipe.extractor.services.bandcamp.extractors.streaminfoitem.BandcampSearchStreamInfoItemExtractor
            r4.<init>(r3, r6)
            r0.commit(r4)
            goto L_0x0027
        L_0x00a4:
            org.schabi.newpipe.extractor.services.bandcamp.extractors.BandcampPlaylistInfoItemExtractor r4 = new org.schabi.newpipe.extractor.services.bandcamp.extractors.BandcampPlaylistInfoItemExtractor
            r4.<init>(r3)
            r0.commit(r4)
            goto L_0x0027
        L_0x00ae:
            java.lang.String r2 = "pagelist"
            org.jsoup.select.Elements r1 = r1.getElementsByClass(r2)
            boolean r2 = r1.isEmpty()
            if (r2 == 0) goto L_0x00c0
            org.schabi.newpipe.extractor.ListExtractor$InfoItemsPage r13 = new org.schabi.newpipe.extractor.ListExtractor$InfoItemsPage
            r13.<init>(r0, r6)
            return r13
        L_0x00c0:
            j$.util.stream.Stream r1 = j$.util.Collection$EL.stream(r1)
            z5 r2 = new z5
            r3 = 16
            r2.<init>(r3)
            j$.util.stream.Stream r1 = r1.map(r2)
            j$.util.Optional r1 = r1.findFirst()
            cb r2 = new cb
            r2.<init>(r4)
            java.lang.Object r1 = r1.orElseGet(r2)
            org.jsoup.select.Elements r1 = (org.jsoup.select.Elements) r1
            r2 = 0
        L_0x00df:
            int r3 = r1.size()
            if (r2 >= r3) goto L_0x00fd
            java.lang.Object r3 = r1.get(r2)
            org.jsoup.nodes.Element r3 = (org.jsoup.nodes.Element) r3
            java.lang.String r4 = "span"
            org.jsoup.select.Elements r3 = r3.getElementsByTag(r4)
            boolean r3 = r3.isEmpty()
            if (r3 != 0) goto L_0x00fa
            int r5 = r2 + 1
            goto L_0x00fd
        L_0x00fa:
            int r2 = r2 + 1
            goto L_0x00df
        L_0x00fd:
            int r1 = r1.size()
            if (r5 >= r1) goto L_0x0124
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = r13.getUrl()
            java.lang.String r13 = r13.getUrl()
            int r13 = r13.length()
            int r13 = r13 - r8
            java.lang.String r13 = r2.substring(r7, r13)
            r1.append(r13)
            int r5 = r5 + r8
            r1.append(r5)
            java.lang.String r6 = r1.toString()
        L_0x0124:
            org.schabi.newpipe.extractor.ListExtractor$InfoItemsPage r13 = new org.schabi.newpipe.extractor.ListExtractor$InfoItemsPage
            org.schabi.newpipe.extractor.Page r1 = new org.schabi.newpipe.extractor.Page
            r1.<init>((java.lang.String) r6)
            r13.<init>(r0, r1)
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: org.schabi.newpipe.extractor.services.bandcamp.extractors.BandcampSearchExtractor.getPage(org.schabi.newpipe.extractor.Page):org.schabi.newpipe.extractor.ListExtractor$InfoItemsPage");
    }

    public String getSearchSuggestion() {
        return "";
    }

    public boolean isCorrectedSearch() {
        return false;
    }

    public void onFetchPage(Downloader downloader) throws IOException, ExtractionException {
    }
}
