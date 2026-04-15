package org.schabi.newpipe.extractor.services.bandcamp.extractors;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonObject;
import j$.util.stream.Collectors;
import j$.util.stream.Stream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.jsoup.Jsoup;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.channel.ChannelExtractor;
import org.schabi.newpipe.extractor.channel.tabs.ChannelTabExtractor;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.exceptions.ReCaptchaException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.schabi.newpipe.extractor.linkhandler.ReadyChannelTabListLinkHandler;
import org.schabi.newpipe.extractor.services.bandcamp.linkHandler.BandcampChannelTabLinkHandlerFactory;
import org.schabi.newpipe.extractor.utils.Utils;

public class BandcampChannelExtractor extends ChannelExtractor {
    public JsonObject g;

    public static final class a implements ReadyChannelTabListLinkHandler.ChannelTabExtractorBuilder {
        public final JsonArray c;

        public a(JsonArray jsonArray) {
            this.c = jsonArray;
        }

        public ChannelTabExtractor build(StreamingService streamingService, ListLinkHandler listLinkHandler) {
            return BandcampChannelTabExtractor.fromDiscography(streamingService, listLinkHandler, this.c);
        }
    }

    public BandcampChannelExtractor(StreamingService streamingService, ListLinkHandler listLinkHandler) {
        super(streamingService, listLinkHandler);
    }

    public List<Image> getAvatars() {
        return BandcampExtractorHelper.getImagesFromImageId(this.g.getLong("bio_image_id"), false);
    }

    public List<Image> getBanners() throws ParsingException {
        try {
            return (List) Stream.CC.of(Jsoup.parse(getDownloader().get(Utils.replaceHttpWithHttps(this.g.getString("bandcamp_url"))).responseBody()).getElementById("customHeader")).filter(new bz(0)).flatMap(new z5(6)).map(new z5(7)).filter(new bz(1)).map(new z5(8)).collect(Collectors.toUnmodifiableList());
        } catch (IOException | ReCaptchaException e) {
            throw new ParsingException("Could not download artist web site", e);
        }
    }

    public String getDescription() {
        return this.g.getString("bio");
    }

    public String getFeedUrl() {
        return null;
    }

    public String getName() {
        return this.g.getString("name");
    }

    public List<Image> getParentChannelAvatars() {
        return Collections.emptyList();
    }

    public String getParentChannelName() {
        return null;
    }

    public String getParentChannelUrl() {
        return null;
    }

    public long getSubscriberCount() {
        return -1;
    }

    public List<ListLinkHandler> getTabs() throws ParsingException {
        JsonArray array = this.g.getArray("discography");
        a aVar = new a(array);
        ArrayList arrayList = new ArrayList();
        Iterator it = array.iterator();
        boolean z = false;
        boolean z2 = false;
        while (it.hasNext()) {
            Object next = it.next();
            if (z && z2) {
                break;
            } else if (next instanceof JsonObject) {
                String string = ((JsonObject) next).getString("item_type");
                if (!z && "track".equals(string)) {
                    arrayList.add(new ReadyChannelTabListLinkHandler(getUrl() + BandcampChannelTabLinkHandlerFactory.getUrlSuffix("tracks"), getId(), "tracks", aVar));
                    z = true;
                }
                if (!z2 && "album".equals(string)) {
                    arrayList.add(new ReadyChannelTabListLinkHandler(getUrl() + BandcampChannelTabLinkHandlerFactory.getUrlSuffix("albums"), getId(), "albums", aVar));
                    z2 = true;
                }
            }
        }
        return Collections.unmodifiableList(arrayList);
    }

    public boolean isVerified() throws ParsingException {
        return false;
    }

    public void onFetchPage(Downloader downloader) throws IOException, ExtractionException {
        this.g = BandcampExtractorHelper.getArtistDetails(getId());
    }
}
