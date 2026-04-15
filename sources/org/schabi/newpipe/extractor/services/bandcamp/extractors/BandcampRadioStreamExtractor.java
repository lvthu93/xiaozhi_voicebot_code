package org.schabi.newpipe.extractor.services.bandcamp.extractors;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonParser;
import com.grack.nanojson.JsonParserException;
import j$.util.Collection$EL;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.jsoup.Jsoup;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.MediaFormat;
import org.schabi.newpipe.extractor.NewPipe;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.ContentNotSupportedException;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.exceptions.ReCaptchaException;
import org.schabi.newpipe.extractor.linkhandler.LinkHandler;
import org.schabi.newpipe.extractor.playlist.PlaylistInfoItemsCollector;
import org.schabi.newpipe.extractor.stream.AudioStream;
import org.schabi.newpipe.extractor.stream.Description;
import org.schabi.newpipe.extractor.stream.StreamSegment;

public class BandcampRadioStreamExtractor extends BandcampStreamExtractor {
    public JsonObject j;

    public BandcampRadioStreamExtractor(StreamingService streamingService, LinkHandler linkHandler) {
        super(streamingService, linkHandler);
    }

    public List<AudioStream> getAudioStreams() {
        ArrayList arrayList = new ArrayList();
        JsonObject object = this.j.getObject("audio_stream");
        if (object.has("mp3-128")) {
            arrayList.add(new AudioStream.Builder().setId("mp3-128").setContent(object.getString("mp3-128"), true).setMediaFormat(MediaFormat.MP3).setAverageBitrate(128).build());
        }
        if (object.has("opus-lo")) {
            arrayList.add(new AudioStream.Builder().setId("opus-lo").setContent(object.getString("opus-lo"), true).setMediaFormat(MediaFormat.OPUS).setAverageBitrate(100).build());
        }
        return arrayList;
    }

    public String getCategory() {
        return "";
    }

    public Description getDescription() {
        return new Description(this.j.getString("desc"), 3);
    }

    public long getLength() {
        return this.j.getLong("audio_duration");
    }

    public String getLicence() {
        return "";
    }

    public String getName() throws ParsingException {
        return this.j.getString("subtitle");
    }

    public PlaylistInfoItemsCollector getRelatedItems() {
        return null;
    }

    public List<StreamSegment> getStreamSegments() throws ParsingException {
        JsonArray array = this.j.getArray("tracks");
        ArrayList arrayList = new ArrayList(array.size());
        Iterator it = array.iterator();
        while (it.hasNext()) {
            JsonObject jsonObject = (JsonObject) it.next();
            StreamSegment streamSegment = new StreamSegment(jsonObject.getString("title"), jsonObject.getInt("timecode"));
            streamSegment.setPreviewUrl(BandcampExtractorHelper.getImageUrl(jsonObject.getLong("track_art_id"), true));
            streamSegment.setChannelName(jsonObject.getString("artist"));
            arrayList.add(streamSegment);
        }
        return arrayList;
    }

    public List<String> getTags() {
        return Collections.emptyList();
    }

    public String getTextualUploadDate() {
        return this.j.getString("published_date");
    }

    public List<Image> getThumbnails() throws ParsingException {
        return BandcampExtractorHelper.getImagesFromImageId(this.j.getLong("show_image_id"), false);
    }

    public List<Image> getUploaderAvatars() {
        return Collections.singletonList(new Image("https://bandcamp.com/img/buttons/bandcamp-button-circle-whitecolor-512.png", 512, 512, Image.ResolutionLevel.MEDIUM));
    }

    public String getUploaderName() throws ParsingException {
        return (String) Collection$EL.stream(Jsoup.parse(this.j.getString("image_caption")).getElementsByTag("a")).map(new z5(13)).findFirst().orElseThrow(new cb(1));
    }

    public String getUploaderUrl() throws ContentNotSupportedException {
        throw new ContentNotSupportedException("Fan pages are not supported");
    }

    public String getUrl() throws ParsingException {
        return getLinkHandler().getUrl();
    }

    public void onFetchPage(Downloader downloader) throws IOException, ExtractionException {
        int parseInt = Integer.parseInt(getId());
        try {
            JsonParser.JsonParserContext<JsonObject> object = JsonParser.object();
            Downloader downloader2 = NewPipe.getDownloader();
            this.j = object.from(downloader2.get("https://bandcamp.com/api/bcweekly/1/get?id=" + parseInt).responseBody());
        } catch (JsonParserException | IOException | ReCaptchaException e) {
            throw new ParsingException("could not get show data", e);
        }
    }
}
