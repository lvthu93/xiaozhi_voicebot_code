package org.schabi.newpipe.extractor.services.soundcloud.extractors;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonParser;
import com.grack.nanojson.JsonParserException;
import j$.util.Collection$EL;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.NewPipe;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.ContentNotAvailableException;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.GeographicRestrictionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.exceptions.SoundCloudGoPlusContentException;
import org.schabi.newpipe.extractor.linkhandler.LinkHandler;
import org.schabi.newpipe.extractor.localization.DateWrapper;
import org.schabi.newpipe.extractor.services.soundcloud.SoundcloudParsingHelper;
import org.schabi.newpipe.extractor.stream.AudioStream;
import org.schabi.newpipe.extractor.stream.Description;
import org.schabi.newpipe.extractor.stream.StreamExtractor;
import org.schabi.newpipe.extractor.stream.StreamInfoItemsCollector;
import org.schabi.newpipe.extractor.stream.StreamType;
import org.schabi.newpipe.extractor.stream.VideoStream;
import org.schabi.newpipe.extractor.utils.Utils;

public class SoundcloudStreamExtractor extends StreamExtractor {
    public JsonObject g;
    public boolean h = true;

    public SoundcloudStreamExtractor(StreamingService streamingService, LinkHandler linkHandler) {
        super(streamingService, linkHandler);
    }

    public final String c(String str) throws IOException, ExtractionException {
        StringBuilder o = y2.o(str, "?client_id=");
        o.append(SoundcloudParsingHelper.clientId());
        String sb = o.toString();
        String string = this.g.getString("track_authorization");
        if (!Utils.isNullOrEmpty(string)) {
            sb = sb + "&track_authorization=" + string;
        }
        try {
            return JsonParser.object().from(NewPipe.getDownloader().get(sb).responseBody()).getString("url");
        } catch (JsonParserException e) {
            throw new ParsingException("Could not parse streamable URL", e);
        }
    }

    public List<AudioStream> getAudioStreams() throws ExtractionException {
        ArrayList arrayList = new ArrayList();
        if (!this.g.getBoolean("streamable") || !this.h) {
            return arrayList;
        }
        try {
            JsonArray array = this.g.getObject("media").getArray("transcodings");
            if (!Utils.isNullOrEmpty((Collection<?>) array)) {
                Class<JsonObject> cls = JsonObject.class;
                Collection$EL.stream(array).filter(new c4(cls, 13)).map(new d4(cls, 13)).forEachOrdered(new hb(1, this, arrayList));
            }
            return arrayList;
        } catch (NullPointerException e) {
            throw new ExtractionException("Could not get audio streams", e);
        }
    }

    public String getCategory() {
        return this.g.getString("genre");
    }

    public Description getDescription() {
        return new Description(this.g.getString("description"), 3);
    }

    public String getId() {
        return String.valueOf(this.g.getInt("id"));
    }

    public long getLength() {
        return this.g.getLong("duration") / 1000;
    }

    public String getLicence() {
        return this.g.getString("license");
    }

    public long getLikeCount() {
        return this.g.getLong("likes_count", -1);
    }

    public String getName() {
        return this.g.getString("title");
    }

    public StreamExtractor.Privacy getPrivacy() {
        return this.g.getString("sharing").equals("public") ? StreamExtractor.Privacy.c : StreamExtractor.Privacy.PRIVATE;
    }

    public StreamType getStreamType() {
        return StreamType.AUDIO_STREAM;
    }

    public List<String> getTags() {
        String[] split = this.g.getString("tag_list").split(" ");
        ArrayList arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder();
        boolean z = false;
        for (String str : split) {
            if (str.startsWith("\"")) {
                sb.append(str.replace("\"", ""));
                z = true;
            } else if (z) {
                if (str.endsWith("\"")) {
                    sb.append(" ");
                    sb.append(str.replace("\"", ""));
                    arrayList.add(sb.toString());
                    z = false;
                } else {
                    sb.append(" ");
                    sb.append(str);
                }
            } else if (!str.isEmpty()) {
                arrayList.add(str);
            }
        }
        return arrayList;
    }

    public String getTextualUploadDate() {
        return this.g.getString("created_at");
    }

    public List<Image> getThumbnails() throws ParsingException {
        return SoundcloudParsingHelper.getAllImagesFromTrackObject(this.g);
    }

    public long getTimeStamp() throws ParsingException {
        long b = b("(#t=\\d{0,3}h?\\d{0,3}m?\\d{1,3}s?)");
        if (b == -2) {
            return 0;
        }
        return b;
    }

    public DateWrapper getUploadDate() throws ParsingException {
        return SoundcloudParsingHelper.parseDate(getTextualUploadDate());
    }

    public List<Image> getUploaderAvatars() {
        return SoundcloudParsingHelper.getAllImagesFromArtworkOrAvatarUrl(SoundcloudParsingHelper.getAvatarUrl(this.g));
    }

    public String getUploaderName() {
        return SoundcloudParsingHelper.getUploaderName(this.g);
    }

    public String getUploaderUrl() {
        return SoundcloudParsingHelper.getUploaderUrl(this.g);
    }

    public List<VideoStream> getVideoOnlyStreams() {
        return Collections.emptyList();
    }

    public List<VideoStream> getVideoStreams() {
        return Collections.emptyList();
    }

    public long getViewCount() {
        return this.g.getLong("playback_count");
    }

    public boolean isUploaderVerified() throws ParsingException {
        return this.g.getObject("user").getBoolean("verified");
    }

    public void onFetchPage(Downloader downloader) throws IOException, ExtractionException {
        JsonObject resolveFor = SoundcloudParsingHelper.resolveFor(downloader, getUrl());
        this.g = resolveFor;
        String string = resolveFor.getString("policy", "");
        if (!string.equals("ALLOW") && !string.equals("MONETIZE")) {
            this.h = false;
            if (string.equals("SNIP")) {
                throw new SoundCloudGoPlusContentException();
            } else if (string.equals("BLOCK")) {
                throw new GeographicRestrictionException("This track is not available in user's country");
            } else {
                throw new ContentNotAvailableException("Content not available: policy ".concat(string));
            }
        }
    }

    public StreamInfoItemsCollector getRelatedItems() throws IOException, ExtractionException {
        StreamInfoItemsCollector streamInfoItemsCollector = new StreamInfoItemsCollector(getServiceId());
        SoundcloudParsingHelper.getStreamsFromApi(streamInfoItemsCollector, "https://api-v2.soundcloud.com/tracks/" + Utils.encodeUrlUtf8(getId()) + "/related?client_id=" + Utils.encodeUrlUtf8(SoundcloudParsingHelper.clientId()));
        return streamInfoItemsCollector;
    }
}
