package org.schabi.newpipe.extractor.services.youtube.extractors;

import com.grack.nanojson.JsonObject;
import java.util.List;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.playlist.PlaylistInfo;
import org.schabi.newpipe.extractor.playlist.PlaylistInfoItemExtractor;
import org.schabi.newpipe.extractor.services.youtube.YoutubeParsingHelper;
import org.schabi.newpipe.extractor.stream.Description;
import org.schabi.newpipe.extractor.utils.Utils;

public class YoutubeMixOrPlaylistInfoItemExtractor implements PlaylistInfoItemExtractor {
    public final JsonObject a;

    public YoutubeMixOrPlaylistInfoItemExtractor(JsonObject jsonObject) {
        this.a = jsonObject;
    }

    public final /* synthetic */ Description getDescription() {
        return y8.a(this);
    }

    public String getName() throws ParsingException {
        String textFromObject = YoutubeParsingHelper.getTextFromObject(this.a.getObject("title"));
        if (!Utils.isNullOrEmpty(textFromObject)) {
            return textFromObject;
        }
        throw new ParsingException("Could not get name");
    }

    public PlaylistInfo.PlaylistType getPlaylistType() throws ParsingException {
        return YoutubeParsingHelper.extractPlaylistTypeFromPlaylistUrl(getUrl());
    }

    public long getStreamCount() throws ParsingException {
        String textFromObject = YoutubeParsingHelper.getTextFromObject(this.a.getObject("videoCountShortText"));
        if (textFromObject != null) {
            try {
                return (long) Integer.parseInt(textFromObject);
            } catch (NumberFormatException unused) {
                return -2;
            }
        } else {
            throw new ParsingException("Could not extract item count for playlist/mix info item");
        }
    }

    public List<Image> getThumbnails() throws ParsingException {
        return YoutubeParsingHelper.getThumbnailsFromInfoItem(this.a);
    }

    public String getUploaderName() throws ParsingException {
        return YoutubeParsingHelper.getTextFromObject(this.a.getObject("longBylineText"));
    }

    public String getUploaderUrl() throws ParsingException {
        return null;
    }

    public String getUrl() throws ParsingException {
        String string = this.a.getString("shareUrl");
        if (!Utils.isNullOrEmpty(string)) {
            return string;
        }
        throw new ParsingException("Could not get url");
    }

    public boolean isUploaderVerified() throws ParsingException {
        return false;
    }
}
