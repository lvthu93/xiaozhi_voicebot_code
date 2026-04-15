package org.schabi.newpipe.extractor.services.youtube.extractors;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonObject;
import java.util.Iterator;
import java.util.List;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.localization.DateWrapper;
import org.schabi.newpipe.extractor.services.youtube.YoutubeParsingHelper;
import org.schabi.newpipe.extractor.stream.ContentAvailability;
import org.schabi.newpipe.extractor.stream.StreamInfoItemExtractor;
import org.schabi.newpipe.extractor.stream.StreamType;
import org.schabi.newpipe.extractor.utils.Parser;
import org.schabi.newpipe.extractor.utils.Utils;

public class YoutubeMusicSongOrVideoInfoItemExtractor implements StreamInfoItemExtractor {
    public final JsonObject a;
    public final JsonArray b;
    public final String c;

    public YoutubeMusicSongOrVideoInfoItemExtractor(JsonObject jsonObject, JsonArray jsonArray, String str) {
        this.a = jsonObject;
        this.b = jsonArray;
        this.c = str;
    }

    public final /* synthetic */ ContentAvailability getContentAvailability() {
        return ob.a(this);
    }

    public long getDuration() throws ParsingException {
        JsonArray jsonArray = this.b;
        String string = jsonArray.getObject(jsonArray.size() - 1).getString("text");
        if (!Utils.isNullOrEmpty(string)) {
            return (long) YoutubeParsingHelper.parseDurationString(string);
        }
        throw new ParsingException("Could not get duration");
    }

    public String getName() throws ParsingException {
        String textFromObject = YoutubeParsingHelper.getTextFromObject(this.a.getArray("flexColumns").getObject(0).getObject("musicResponsiveListItemFlexColumnRenderer").getObject("text"));
        if (!Utils.isNullOrEmpty(textFromObject)) {
            return textFromObject;
        }
        throw new ParsingException("Could not get name");
    }

    public final /* synthetic */ String getShortDescription() {
        return ob.b(this);
    }

    public StreamType getStreamType() {
        return StreamType.VIDEO_STREAM;
    }

    public String getTextualUploadDate() {
        return null;
    }

    public List<Image> getThumbnails() throws ParsingException {
        try {
            return YoutubeParsingHelper.getImagesFromThumbnailsArray(this.a.getObject("thumbnail").getObject("musicThumbnailRenderer").getObject("thumbnail").getArray("thumbnails"));
        } catch (Exception e) {
            throw new ParsingException("Could not get thumbnails", e);
        }
    }

    public DateWrapper getUploadDate() {
        return null;
    }

    public final /* synthetic */ List getUploaderAvatars() {
        return ob.c(this);
    }

    public String getUploaderName() throws ParsingException {
        String string = this.b.getObject(0).getString("text");
        if (!Utils.isNullOrEmpty(string)) {
            return string;
        }
        throw new ParsingException("Could not get uploader name");
    }

    public String getUploaderUrl() throws ParsingException {
        boolean equals = this.c.equals("music_videos");
        JsonObject jsonObject = this.a;
        if (equals) {
            Iterator it = jsonObject.getObject("menu").getObject("menuRenderer").getArray("items").iterator();
            while (it.hasNext()) {
                JsonObject object = ((JsonObject) it.next()).getObject("menuNavigationItemRenderer");
                if (object.getObject("icon").getString("iconType", "").equals("ARTIST")) {
                    return YoutubeParsingHelper.getUrlFromNavigationEndpoint(object.getObject("navigationEndpoint"));
                }
            }
            return null;
        }
        JsonObject object2 = jsonObject.getArray("flexColumns").getObject(1).getObject("musicResponsiveListItemFlexColumnRenderer").getObject("text").getArray("runs").getObject(0);
        if (!object2.has("navigationEndpoint")) {
            return null;
        }
        String urlFromNavigationEndpoint = YoutubeParsingHelper.getUrlFromNavigationEndpoint(object2.getObject("navigationEndpoint"));
        if (!Utils.isNullOrEmpty(urlFromNavigationEndpoint)) {
            return urlFromNavigationEndpoint;
        }
        throw new ParsingException("Could not get uploader URL");
    }

    public String getUrl() throws ParsingException {
        String string = this.a.getObject("playlistItemData").getString("videoId");
        if (!Utils.isNullOrEmpty(string)) {
            return y2.i("https://music.youtube.com/watch?v=", string);
        }
        throw new ParsingException("Could not get URL");
    }

    public long getViewCount() throws ParsingException {
        if (this.c.equals("music_songs")) {
            return -1;
        }
        JsonArray jsonArray = this.b;
        String string = jsonArray.getObject(jsonArray.size() - 3).getString("text");
        if (!Utils.isNullOrEmpty(string)) {
            try {
                return Utils.mixedNumberWordToLong(string);
            } catch (Parser.RegexException unused) {
                return 0;
            }
        } else {
            throw new ParsingException("Could not get view count");
        }
    }

    public boolean isAd() {
        return false;
    }

    public final /* synthetic */ boolean isShortFormContent() {
        return ob.d(this);
    }

    public boolean isUploaderVerified() {
        return false;
    }
}
