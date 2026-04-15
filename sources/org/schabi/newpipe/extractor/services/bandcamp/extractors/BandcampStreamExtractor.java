package org.schabi.newpipe.extractor.services.bandcamp.extractors;

import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonParserException;
import j$.util.Collection$EL;
import j$.util.stream.Collectors;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.MediaFormat;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.PaidContentException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.LinkHandler;
import org.schabi.newpipe.extractor.localization.DateWrapper;
import org.schabi.newpipe.extractor.playlist.PlaylistInfoItemsCollector;
import org.schabi.newpipe.extractor.stream.AudioStream;
import org.schabi.newpipe.extractor.stream.Description;
import org.schabi.newpipe.extractor.stream.StreamExtractor;
import org.schabi.newpipe.extractor.stream.StreamType;
import org.schabi.newpipe.extractor.stream.VideoStream;
import org.schabi.newpipe.extractor.utils.JsonUtils;
import org.schabi.newpipe.extractor.utils.Utils;

public class BandcampStreamExtractor extends StreamExtractor {
    public JsonObject g;
    public JsonObject h;
    public Document i;

    public BandcampStreamExtractor(StreamingService streamingService, LinkHandler linkHandler) {
        super(streamingService, linkHandler);
    }

    public static JsonObject getAlbumInfoJson(String str) throws ParsingException {
        try {
            return JsonUtils.getJsonData(str, "data-tralbum");
        } catch (JsonParserException e) {
            throw new ParsingException("Faulty JSON; page likely does not contain album data", e);
        } catch (ArrayIndexOutOfBoundsException e2) {
            throw new ParsingException("JSON does not exist", e2);
        }
    }

    public List<AudioStream> getAudioStreams() {
        return Collections.singletonList(new AudioStream.Builder().setId("mp3-128").setContent(this.g.getArray("trackinfo").getObject(0).getObject("file").getString("mp3-128"), true).setMediaFormat(MediaFormat.MP3).setAverageBitrate(128).build());
    }

    public String getCategory() {
        return (String) Collection$EL.stream(this.i.getElementsByClass("tralbum-tags")).flatMap(new z5(20)).map(new z5(21)).findFirst().orElse("");
    }

    public Description getDescription() {
        return new Description(Utils.nonEmptyAndNullJoin("\n\n", this.h.getString("about"), this.h.getString("lyrics"), this.h.getString("credits")), 3);
    }

    public long getLength() throws ParsingException {
        return (long) this.g.getArray("trackinfo").getObject(0).getDouble("duration");
    }

    public String getLicence() {
        switch (this.h.getInt("license_type")) {
            case 1:
                return "All rights reserved ©";
            case 2:
                return "CC BY-NC-ND 3.0";
            case 3:
                return "CC BY-NC-SA 3.0";
            case 4:
                return "CC BY-NC 3.0";
            case 5:
                return "CC BY-ND 3.0";
            case 6:
                return "CC BY 3.0";
            case 8:
                return "CC BY-SA 3.0";
            default:
                return "Unknown";
        }
    }

    public String getName() throws ParsingException {
        return this.h.getString("title");
    }

    public StreamType getStreamType() {
        return StreamType.AUDIO_STREAM;
    }

    public List<String> getTags() {
        return (List) Collection$EL.stream(this.i.getElementsByAttributeValue("itemprop", "keywords")).map(new z5(19)).collect(Collectors.toList());
    }

    public String getTextualUploadDate() {
        return this.h.getString("publish_date");
    }

    public List<Image> getThumbnails() throws ParsingException {
        if (this.g.isNull("art_id")) {
            return Collections.emptyList();
        }
        return BandcampExtractorHelper.getImagesFromImageId(this.g.getLong("art_id"), true);
    }

    public DateWrapper getUploadDate() throws ParsingException {
        return BandcampExtractorHelper.parseDate(getTextualUploadDate());
    }

    public List<Image> getUploaderAvatars() {
        return BandcampExtractorHelper.getImagesFromImageUrl((String) Collection$EL.stream(this.i.getElementsByClass("band-photo")).map(new z5(18)).findFirst().orElse(""));
    }

    public String getUploaderName() throws ParsingException {
        return this.g.getString("artist");
    }

    public String getUploaderUrl() throws ParsingException {
        return y2.k(new StringBuilder("https://"), getUrl().split(MqttTopic.TOPIC_LEVEL_SEPARATOR)[2], MqttTopic.TOPIC_LEVEL_SEPARATOR);
    }

    public String getUrl() throws ParsingException {
        return Utils.replaceHttpWithHttps(this.g.getString("url"));
    }

    public List<VideoStream> getVideoOnlyStreams() {
        return Collections.emptyList();
    }

    public List<VideoStream> getVideoStreams() {
        return Collections.emptyList();
    }

    public void onFetchPage(Downloader downloader) throws IOException, ExtractionException {
        String responseBody = downloader.get(getLinkHandler().getUrl()).responseBody();
        this.i = Jsoup.parse(responseBody);
        JsonObject albumInfoJson = getAlbumInfoJson(responseBody);
        this.g = albumInfoJson;
        this.h = albumInfoJson.getObject("current");
        if (this.g.getArray("trackinfo").size() > 1) {
            throw new ExtractionException("Page is actually an album, not a track");
        } else if (this.g.getArray("trackinfo").getObject(0).isNull("file")) {
            throw new PaidContentException("This track is not available without being purchased");
        }
    }

    public PlaylistInfoItemsCollector getRelatedItems() {
        PlaylistInfoItemsCollector playlistInfoItemsCollector = new PlaylistInfoItemsCollector(getServiceId());
        Collection$EL.stream(this.i.getElementsByClass("recommended-album")).map(new z5(17)).forEach(new ce(0, playlistInfoItemsCollector));
        return playlistInfoItemsCollector;
    }
}
