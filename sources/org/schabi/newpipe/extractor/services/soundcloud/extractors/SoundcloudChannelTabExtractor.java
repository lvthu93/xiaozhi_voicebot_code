package org.schabi.newpipe.extractor.services.soundcloud.extractors;

import java.io.IOException;
import java.util.HashSet;
import org.schabi.newpipe.extractor.InfoItem;
import org.schabi.newpipe.extractor.ListExtractor;
import org.schabi.newpipe.extractor.MultiInfoItemsCollector;
import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.channel.tabs.ChannelTabExtractor;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.schabi.newpipe.extractor.services.soundcloud.SoundcloudParsingHelper;
import org.schabi.newpipe.extractor.utils.Utils;

public class SoundcloudChannelTabExtractor extends ChannelTabExtractor {
    public final String g = getLinkHandler().getId();

    public SoundcloudChannelTabExtractor(StreamingService streamingService, ListLinkHandler listLinkHandler) {
        super(streamingService, listLinkHandler);
    }

    public String getId() {
        return this.g;
    }

    public ListExtractor.InfoItemsPage<InfoItem> getInitialPage() throws IOException, ExtractionException {
        String str;
        StringBuilder sb = new StringBuilder("https://api-v2.soundcloud.com/users/");
        sb.append(this.g);
        String name = getName();
        name.getClass();
        char c = 65535;
        switch (name.hashCode()) {
            case -1865828127:
                if (name.equals("playlists")) {
                    c = 0;
                    break;
                }
                break;
            case -1415163932:
                if (name.equals("albums")) {
                    c = 1;
                    break;
                }
                break;
            case -865716088:
                if (name.equals("tracks")) {
                    c = 2;
                    break;
                }
                break;
            case 102974396:
                if (name.equals("likes")) {
                    c = 3;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                str = "/playlists_without_albums";
                break;
            case 1:
                str = "/albums";
                break;
            case 2:
                str = "/tracks";
                break;
            case 3:
                str = "/likes";
                break;
            default:
                throw new ParsingException("Unsupported tab: " + getName());
        }
        sb.append(str);
        sb.append("?client_id=");
        sb.append(SoundcloudParsingHelper.clientId());
        sb.append("&limit=20&linked_partitioning=1");
        return getPage(new Page(sb.toString()));
    }

    public ListExtractor.InfoItemsPage<InfoItem> getPage(Page page) throws IOException, ExtractionException {
        String str;
        Page page2;
        boolean z;
        if (page == null || Utils.isNullOrEmpty(page.getUrl())) {
            throw new IllegalArgumentException("Page doesn't contain an URL");
        }
        MultiInfoItemsCollector multiInfoItemsCollector = new MultiInfoItemsCollector(getServiceId());
        HashSet hashSet = new HashSet();
        String url = page.getUrl();
        int i = 0;
        while (true) {
            str = "";
            if (Utils.isNullOrEmpty(url) || !hashSet.add(url)) {
                break;
            }
            int size = multiInfoItemsCollector.getItems().size();
            url = SoundcloudParsingHelper.getInfoItemsFromApi(multiInfoItemsCollector, url);
            if (multiInfoItemsCollector.getItems().size() > size) {
                z = true;
            } else {
                z = false;
            }
            if (!z) {
                i++;
                if (i < 3) {
                    if (Utils.isNullOrEmpty(url)) {
                        break;
                    }
                } else {
                    break;
                }
            } else {
                str = url;
                break;
            }
        }
        if (Utils.isNullOrEmpty(str)) {
            page2 = null;
        } else {
            page2 = new Page(str);
        }
        return new ListExtractor.InfoItemsPage<>(multiInfoItemsCollector, page2);
    }

    public void onFetchPage(Downloader downloader) {
    }
}
