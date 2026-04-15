package org.schabi.newpipe.extractor.services.youtube.extractors;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonObject;
import java.util.List;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.playlist.PlaylistInfo;
import org.schabi.newpipe.extractor.playlist.PlaylistInfoItemExtractor;
import org.schabi.newpipe.extractor.services.youtube.YoutubeParsingHelper;
import org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubePlaylistLinkHandlerFactory;
import org.schabi.newpipe.extractor.stream.Description;
import org.schabi.newpipe.extractor.utils.Utils;

public class YoutubePlaylistInfoItemExtractor implements PlaylistInfoItemExtractor {
    public final JsonObject a;

    public YoutubePlaylistInfoItemExtractor(JsonObject jsonObject) {
        this.a = jsonObject;
    }

    public final /* synthetic */ Description getDescription() {
        return y8.a(this);
    }

    public String getName() throws ParsingException {
        try {
            return YoutubeParsingHelper.getTextFromObject(this.a.getObject("title"));
        } catch (Exception e) {
            throw new ParsingException("Could not get name", e);
        }
    }

    public final /* synthetic */ PlaylistInfo.PlaylistType getPlaylistType() {
        return y8.b(this);
    }

    public long getStreamCount() throws ParsingException {
        JsonObject jsonObject = this.a;
        String string = jsonObject.getString("videoCount");
        if (string == null) {
            string = YoutubeParsingHelper.getTextFromObject(jsonObject.getObject("videoCountText"));
        }
        if (string == null) {
            string = YoutubeParsingHelper.getTextFromObject(jsonObject.getObject("videoCountShortText"));
        }
        if (string != null) {
            try {
                return Long.parseLong(Utils.removeNonDigitCharacters(string));
            } catch (Exception e) {
                throw new ParsingException("Could not get stream count", e);
            }
        } else {
            throw new ParsingException("Could not get stream count");
        }
    }

    public List<Image> getThumbnails() throws ParsingException {
        JsonObject jsonObject = this.a;
        try {
            JsonArray array = jsonObject.getArray("thumbnails").getObject(0).getArray("thumbnails");
            if (array.isEmpty()) {
                array = jsonObject.getObject("thumbnail").getArray("thumbnails");
            }
            return YoutubeParsingHelper.getImagesFromThumbnailsArray(array);
        } catch (Exception e) {
            throw new ParsingException("Could not get thumbnails", e);
        }
    }

    public String getUploaderName() throws ParsingException {
        try {
            return YoutubeParsingHelper.getTextFromObject(this.a.getObject("longBylineText"));
        } catch (Exception e) {
            throw new ParsingException("Could not get uploader name", e);
        }
    }

    public String getUploaderUrl() throws ParsingException {
        try {
            return YoutubeParsingHelper.getUrlFromObject(this.a.getObject("longBylineText"));
        } catch (Exception e) {
            throw new ParsingException("Could not get uploader url", e);
        }
    }

    public String getUrl() throws ParsingException {
        try {
            return YoutubePlaylistLinkHandlerFactory.getInstance().getUrl(this.a.getString("playlistId"));
        } catch (Exception e) {
            throw new ParsingException("Could not get url", e);
        }
    }

    public boolean isUploaderVerified() throws ParsingException {
        try {
            return YoutubeParsingHelper.isVerified(this.a.getArray("ownerBadges"));
        } catch (Exception e) {
            throw new ParsingException("Could not get uploader verification info", e);
        }
    }
}
