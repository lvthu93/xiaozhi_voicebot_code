package org.schabi.newpipe.extractor.services.media_ccc.extractors;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonObject;
import j$.util.Collection$EL;
import j$.util.stream.Collectors;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.LinkHandler;
import org.schabi.newpipe.extractor.stream.AudioStream;
import org.schabi.newpipe.extractor.stream.Description;
import org.schabi.newpipe.extractor.stream.StreamExtractor;
import org.schabi.newpipe.extractor.stream.StreamType;
import org.schabi.newpipe.extractor.stream.VideoStream;

public class MediaCCCLiveStreamExtractor extends StreamExtractor {
    public JsonObject g = null;
    public String h = "";
    public JsonObject i = null;

    public static final class a {
        public final JsonObject a;
        public final String b;
        public final JsonObject c;

        public a(JsonObject jsonObject, String str, JsonObject jsonObject2) {
            this.a = jsonObject;
            this.b = str;
            this.c = jsonObject2;
        }
    }

    public MediaCCCLiveStreamExtractor(StreamingService streamingService, LinkHandler linkHandler) {
        super(streamingService, linkHandler);
    }

    public final String c(String str) {
        Class<JsonObject> cls = JsonObject.class;
        return (String) y2.z(cls, 4, y2.s(cls, 4, Collection$EL.stream(this.i.getArray("streams")))).map(new z5(25)).filter(new y5(str, 4)).map(new cc(str, 1)).findFirst().orElse("");
    }

    public final List d(String str, z5 z5Var) {
        Class<JsonObject> cls = JsonObject.class;
        return (List) y2.z(cls, 5, y2.s(cls, 5, Collection$EL.stream(this.i.getArray("streams")))).filter(new y5(str, 5)).flatMap(new z5(26)).filter(new bz(3)).map(z5Var).collect(Collectors.toList());
    }

    public List<AudioStream> getAudioStreams() throws IOException, ExtractionException {
        return d("audio", new z5(23));
    }

    public String getCategory() {
        return this.h;
    }

    public String getDashMpdUrl() throws ParsingException {
        return c("dash");
    }

    public Description getDescription() throws ParsingException {
        return new Description(this.g.getString("description") + " - " + this.h, 3);
    }

    public String getHlsUrl() {
        return c("hls");
    }

    public String getName() throws ParsingException {
        return this.i.getString("display");
    }

    public StreamType getStreamType() throws ParsingException {
        return StreamType.LIVE_STREAM;
    }

    public List<Image> getThumbnails() throws ParsingException {
        return MediaCCCParsingHelper.getThumbnailsFromLiveStreamItem(this.i);
    }

    public String getUploaderName() throws ParsingException {
        return this.g.getString("conference");
    }

    public String getUploaderUrl() throws ParsingException {
        return "https://streaming.media.ccc.de/" + this.g.getString("slug");
    }

    public List<VideoStream> getVideoOnlyStreams() {
        return Collections.emptyList();
    }

    public List<VideoStream> getVideoStreams() throws IOException, ExtractionException {
        return d("video", new z5(24));
    }

    public long getViewCount() {
        return -1;
    }

    public void onFetchPage(Downloader downloader) throws IOException, ExtractionException {
        JsonArray liveStreams = MediaCCCParsingHelper.getLiveStreams(downloader, getExtractorLocalization());
        for (int i2 = 0; i2 < liveStreams.size(); i2++) {
            JsonObject object = liveStreams.getObject(i2);
            JsonArray array = object.getArray("groups");
            for (int i3 = 0; i3 < array.size(); i3++) {
                String string = array.getObject(i3).getString("group");
                JsonArray array2 = array.getObject(i3).getArray("rooms");
                for (int i4 = 0; i4 < array2.size(); i4++) {
                    JsonObject object2 = array2.getObject(i4);
                    String id = getId();
                    if (id.equals(object.getString("slug") + MqttTopic.TOPIC_LEVEL_SEPARATOR + object2.getString("slug"))) {
                        this.g = object;
                        this.h = string;
                        this.i = object2;
                        return;
                    }
                }
            }
        }
        throw new ExtractionException("Could not find room matching id: '" + getId() + "'");
    }
}
