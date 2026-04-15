package org.schabi.newpipe.extractor.services.youtube.extractors;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonObject;
import java.util.List;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.channel.ChannelInfoItemExtractor;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.services.youtube.YoutubeParsingHelper;
import org.schabi.newpipe.extractor.utils.Parser;
import org.schabi.newpipe.extractor.utils.Utils;

public class YoutubeMusicArtistInfoItemExtractor implements ChannelInfoItemExtractor {
    public final JsonObject a;

    public YoutubeMusicArtistInfoItemExtractor(JsonObject jsonObject) {
        this.a = jsonObject;
    }

    public String getDescription() {
        return null;
    }

    public String getName() throws ParsingException {
        String textFromObject = YoutubeParsingHelper.getTextFromObject(this.a.getArray("flexColumns").getObject(0).getObject("musicResponsiveListItemFlexColumnRenderer").getObject("text"));
        if (!Utils.isNullOrEmpty(textFromObject)) {
            return textFromObject;
        }
        throw new ParsingException("Could not get name");
    }

    public long getStreamCount() {
        return -1;
    }

    public long getSubscriberCount() throws ParsingException {
        JsonArray array = this.a.getArray("flexColumns");
        JsonArray array2 = array.getObject(array.size() - 1).getObject("musicResponsiveListItemFlexColumnRenderer").getObject("text").getArray("runs");
        String string = array2.getObject(array2.size() - 1).getString("text");
        if (!Utils.isNullOrEmpty(string)) {
            try {
                return Utils.mixedNumberWordToLong(string);
            } catch (Parser.RegexException unused) {
                return 0;
            }
        } else {
            throw new ParsingException("Could not get subscriber count");
        }
    }

    public List<Image> getThumbnails() throws ParsingException {
        try {
            return YoutubeParsingHelper.getImagesFromThumbnailsArray(this.a.getObject("thumbnail").getObject("musicThumbnailRenderer").getObject("thumbnail").getArray("thumbnails"));
        } catch (Exception e) {
            throw new ParsingException("Could not get thumbnails", e);
        }
    }

    public String getUrl() throws ParsingException {
        String urlFromNavigationEndpoint = YoutubeParsingHelper.getUrlFromNavigationEndpoint(this.a.getObject("navigationEndpoint"));
        if (!Utils.isNullOrEmpty(urlFromNavigationEndpoint)) {
            return urlFromNavigationEndpoint;
        }
        throw new ParsingException("Could not get URL");
    }

    public boolean isVerified() {
        return true;
    }
}
