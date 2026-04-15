package org.schabi.newpipe.extractor.services.media_ccc.extractors;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonParser;
import com.grack.nanojson.JsonParserException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.MediaFormat;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.LinkHandler;
import org.schabi.newpipe.extractor.localization.DateWrapper;
import org.schabi.newpipe.extractor.localization.Localization;
import org.schabi.newpipe.extractor.stream.AudioStream;
import org.schabi.newpipe.extractor.stream.Description;
import org.schabi.newpipe.extractor.stream.StreamExtractor;
import org.schabi.newpipe.extractor.stream.StreamType;
import org.schabi.newpipe.extractor.stream.VideoStream;
import org.schabi.newpipe.extractor.utils.JsonUtils;
import org.schabi.newpipe.extractor.utils.LocaleCompat;

public class MediaCCCStreamExtractor extends StreamExtractor {
    public JsonObject g;
    public JsonObject h;

    public MediaCCCStreamExtractor(StreamingService streamingService, LinkHandler linkHandler) {
        super(streamingService, linkHandler);
    }

    public List<AudioStream> getAudioStreams() throws ExtractionException {
        MediaFormat mediaFormat;
        JsonArray array = this.g.getArray("recordings");
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < array.size(); i++) {
            JsonObject object = array.getObject(i);
            String string = object.getString("mime_type");
            if (string.startsWith("audio")) {
                if (string.endsWith("opus")) {
                    mediaFormat = MediaFormat.OPUS;
                } else if (string.endsWith("mpeg")) {
                    mediaFormat = MediaFormat.MP3;
                } else if (string.endsWith("ogg")) {
                    mediaFormat = MediaFormat.OGG;
                } else {
                    mediaFormat = null;
                }
                AudioStream.Builder averageBitrate = new AudioStream.Builder().setId(object.getString("filename", " ")).setContent(object.getString("recording_url"), true).setMediaFormat(mediaFormat).setAverageBitrate(-1);
                String string2 = object.getString("language");
                if (string2 != null && !string2.contains("-")) {
                    averageBitrate.setAudioLocale(LocaleCompat.forLanguageTag(string2).orElseThrow(new j7(string2, 3)));
                }
                arrayList.add(averageBitrate.build());
            }
        }
        return arrayList;
    }

    public Description getDescription() {
        return new Description(this.g.getString("description"), 3);
    }

    public Locale getLanguageInfo() throws ParsingException {
        return Localization.getLocaleFromThreeLetterCode(this.g.getString("original_language"));
    }

    public long getLength() {
        return (long) this.g.getInt("length");
    }

    public String getName() throws ParsingException {
        return this.g.getString("title");
    }

    public String getOriginalUrl() {
        return this.g.getString("frontend_link");
    }

    public StreamType getStreamType() {
        return StreamType.VIDEO_STREAM;
    }

    public List<String> getTags() {
        return JsonUtils.getStringListFromJsonArray(this.g.getArray("tags"));
    }

    public String getTextualUploadDate() {
        return this.g.getString("release_date");
    }

    public List<Image> getThumbnails() {
        return MediaCCCParsingHelper.getThumbnailsFromStreamItem(this.g);
    }

    public DateWrapper getUploadDate() throws ParsingException {
        return DateWrapper.fromOffsetDateTime(getTextualUploadDate());
    }

    public List<Image> getUploaderAvatars() {
        return MediaCCCParsingHelper.getImageListFromLogoImageUrl(this.h.getString("logo_url"));
    }

    public String getUploaderName() {
        return this.g.getString("conference_url").replaceFirst("https://(api\\.)?media\\.ccc\\.de/public/conferences/", "");
    }

    public String getUploaderUrl() {
        return "https://media.ccc.de/c/" + getUploaderName();
    }

    public List<VideoStream> getVideoOnlyStreams() {
        return Collections.emptyList();
    }

    public List<VideoStream> getVideoStreams() throws ExtractionException {
        MediaFormat mediaFormat;
        JsonArray array = this.g.getArray("recordings");
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < array.size(); i++) {
            JsonObject object = array.getObject(i);
            String string = object.getString("mime_type");
            if (string.startsWith("video")) {
                if (string.endsWith("webm")) {
                    mediaFormat = MediaFormat.WEBM;
                } else if (string.endsWith("mp4")) {
                    mediaFormat = MediaFormat.MPEG_4;
                } else {
                    mediaFormat = null;
                }
                VideoStream.Builder mediaFormat2 = new VideoStream.Builder().setId(object.getString("filename", " ")).setContent(object.getString("recording_url"), true).setIsVideoOnly(false).setMediaFormat(mediaFormat);
                arrayList.add(mediaFormat2.setResolution(object.getInt("height") + "p").build());
            }
        }
        return arrayList;
    }

    public long getViewCount() {
        return (long) this.g.getInt("view_count");
    }

    public void onFetchPage(Downloader downloader) throws IOException, ExtractionException {
        String str = "https://api.media.ccc.de/public/events/" + getId();
        try {
            this.g = JsonParser.object().from(downloader.get(str).responseBody());
            this.h = JsonParser.object().from(downloader.get(this.g.getString("conference_url")).responseBody());
        } catch (JsonParserException e) {
            throw new ExtractionException(y2.i("Could not parse json returned by URL: ", str), e);
        }
    }
}
