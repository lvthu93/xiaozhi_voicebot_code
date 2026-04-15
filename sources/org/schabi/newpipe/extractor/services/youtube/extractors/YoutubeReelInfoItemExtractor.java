package org.schabi.newpipe.extractor.services.youtube.extractors;

import com.grack.nanojson.JsonObject;
import java.util.List;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.localization.DateWrapper;
import org.schabi.newpipe.extractor.services.youtube.YoutubeParsingHelper;
import org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeStreamLinkHandlerFactory;
import org.schabi.newpipe.extractor.stream.ContentAvailability;
import org.schabi.newpipe.extractor.stream.StreamInfoItemExtractor;
import org.schabi.newpipe.extractor.stream.StreamType;
import org.schabi.newpipe.extractor.utils.Utils;

public class YoutubeReelInfoItemExtractor implements StreamInfoItemExtractor {
    public final JsonObject a;

    public YoutubeReelInfoItemExtractor(JsonObject jsonObject) {
        this.a = jsonObject;
    }

    public final /* synthetic */ ContentAvailability getContentAvailability() {
        return ob.a(this);
    }

    public long getDuration() throws ParsingException {
        return -1;
    }

    public String getName() throws ParsingException {
        return YoutubeParsingHelper.getTextFromObject(this.a.getObject("headline"));
    }

    public final /* synthetic */ String getShortDescription() {
        return ob.b(this);
    }

    public StreamType getStreamType() throws ParsingException {
        return StreamType.VIDEO_STREAM;
    }

    public String getTextualUploadDate() throws ParsingException {
        return null;
    }

    public List<Image> getThumbnails() throws ParsingException {
        return YoutubeParsingHelper.getThumbnailsFromInfoItem(this.a);
    }

    public DateWrapper getUploadDate() throws ParsingException {
        return null;
    }

    public final /* synthetic */ List getUploaderAvatars() {
        return ob.c(this);
    }

    public String getUploaderName() throws ParsingException {
        return null;
    }

    public String getUploaderUrl() throws ParsingException {
        return null;
    }

    public String getUrl() throws ParsingException {
        try {
            return YoutubeStreamLinkHandlerFactory.getInstance().getUrl(this.a.getString("videoId"));
        } catch (Exception e) {
            throw new ParsingException("Could not get URL", e);
        }
    }

    public long getViewCount() throws ParsingException {
        String textFromObject = YoutubeParsingHelper.getTextFromObject(this.a.getObject("viewCountText"));
        if (Utils.isNullOrEmpty(textFromObject)) {
            throw new ParsingException("Could not get short view count");
        } else if (textFromObject.toLowerCase().contains("no views")) {
            return 0;
        } else {
            return Utils.mixedNumberWordToLong(textFromObject);
        }
    }

    public boolean isAd() throws ParsingException {
        return false;
    }

    public boolean isShortFormContent() {
        return true;
    }

    public boolean isUploaderVerified() throws ParsingException {
        return false;
    }
}
