package org.schabi.newpipe.extractor.services.bandcamp.extractors;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonParserException;
import j$.util.Collection$EL;
import j$.util.Objects;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.ListExtractor;
import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.PaidContentException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.schabi.newpipe.extractor.playlist.PlaylistExtractor;
import org.schabi.newpipe.extractor.services.bandcamp.extractors.streaminfoitem.BandcampPlaylistStreamInfoItemExtractor;
import org.schabi.newpipe.extractor.stream.Description;
import org.schabi.newpipe.extractor.stream.StreamInfoItem;
import org.schabi.newpipe.extractor.stream.StreamInfoItemExtractor;
import org.schabi.newpipe.extractor.stream.StreamInfoItemsCollector;
import org.schabi.newpipe.extractor.utils.JsonUtils;

public class BandcampPlaylistExtractor extends PlaylistExtractor {
    public Document g;
    public JsonObject h;
    public JsonArray i;
    public String j;

    public BandcampPlaylistExtractor(StreamingService streamingService, ListLinkHandler listLinkHandler) {
        super(streamingService, listLinkHandler);
    }

    public Description getDescription() throws ParsingException {
        Element elementById = this.g.getElementById("trackInfo");
        if (elementById != null) {
            Elements elementsByClass = elementById.getElementsByClass("tralbum-about");
            Elements elementsByClass2 = elementById.getElementsByClass("tralbum-credits");
            Element elementById2 = this.g.getElementById("license");
            if (elementsByClass.isEmpty() && elementsByClass2.isEmpty() && elementById2 == null) {
                return Description.g;
            }
            StringBuilder sb = new StringBuilder();
            if (!elementsByClass.isEmpty()) {
                Element first = elementsByClass.first();
                Objects.requireNonNull(first);
                sb.append(first.html());
            }
            if (!elementsByClass2.isEmpty()) {
                Element first2 = elementsByClass2.first();
                Objects.requireNonNull(first2);
                sb.append(first2.html());
            }
            if (elementById2 != null) {
                sb.append(elementById2.html());
            }
            return new Description(sb.toString(), 1);
        }
        throw new ParsingException("Could not find trackInfo in document");
    }

    public ListExtractor.InfoItemsPage<StreamInfoItem> getInitialPage() throws ExtractionException {
        StreamInfoItemsCollector streamInfoItemsCollector = new StreamInfoItemsCollector(getServiceId());
        for (int i2 = 0; i2 < this.i.size(); i2++) {
            JsonObject object = this.i.getObject(i2);
            if (this.i.size() < 10) {
                streamInfoItemsCollector.commit((StreamInfoItemExtractor) new BandcampPlaylistStreamInfoItemExtractor(object, getUploaderUrl(), getService()));
            } else {
                streamInfoItemsCollector.commit((StreamInfoItemExtractor) new BandcampPlaylistStreamInfoItemExtractor(object, getUploaderUrl(), getThumbnails()));
            }
        }
        return new ListExtractor.InfoItemsPage<>(streamInfoItemsCollector, (Page) null);
    }

    public String getName() throws ParsingException {
        return this.j;
    }

    public ListExtractor.InfoItemsPage<StreamInfoItem> getPage(Page page) {
        return null;
    }

    public long getStreamCount() {
        return (long) this.i.size();
    }

    public List<Image> getThumbnails() throws ParsingException {
        if (this.h.isNull("art_id")) {
            return Collections.emptyList();
        }
        return BandcampExtractorHelper.getImagesFromImageId(this.h.getLong("art_id"), true);
    }

    public List<Image> getUploaderAvatars() {
        return BandcampExtractorHelper.getImagesFromImageUrl((String) Collection$EL.stream(this.g.getElementsByClass("band-photo")).map(new z5(12)).findFirst().orElse(""));
    }

    public String getUploaderName() {
        return this.h.getString("artist");
    }

    public String getUploaderUrl() throws ParsingException {
        return y2.k(new StringBuilder("https://"), getUrl().split(MqttTopic.TOPIC_LEVEL_SEPARATOR)[2], MqttTopic.TOPIC_LEVEL_SEPARATOR);
    }

    public boolean isUploaderVerified() throws ParsingException {
        return false;
    }

    public void onFetchPage(Downloader downloader) throws IOException, ExtractionException {
        String responseBody = downloader.get(getLinkHandler().getUrl()).responseBody();
        this.g = Jsoup.parse(responseBody);
        JsonObject albumInfoJson = BandcampStreamExtractor.getAlbumInfoJson(responseBody);
        this.h = albumInfoJson;
        this.i = albumInfoJson.getArray("trackinfo");
        try {
            this.j = JsonUtils.getJsonData(responseBody, "data-embed").getString("album_title");
            if (this.i.isEmpty()) {
                throw new PaidContentException("Album needs to be purchased");
            }
        } catch (JsonParserException e) {
            throw new ParsingException("Faulty JSON; page likely does not contain album data", e);
        } catch (ArrayIndexOutOfBoundsException e2) {
            throw new ParsingException("JSON does not exist", e2);
        }
    }
}
