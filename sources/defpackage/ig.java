package defpackage;

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

/* renamed from: ig  reason: default package */
public class ig implements StreamInfoItemExtractor {
    public final JsonObject a;

    public ig(JsonObject jsonObject) {
        this.a = jsonObject;
    }

    public final /* synthetic */ ContentAvailability getContentAvailability() {
        return ob.a(this);
    }

    public long getDuration() throws ParsingException {
        return -1;
    }

    public String getName() throws ParsingException {
        return this.a.getObject("overlayMetadata").getObject("primaryText").getString("content");
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
        return YoutubeParsingHelper.getImagesFromThumbnailsArray(this.a.getObject("thumbnail").getArray("sources"));
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
        JsonObject jsonObject = this.a;
        String string = jsonObject.getObject("onTap").getObject("innertubeCommand").getObject("reelWatchEndpoint").getString("videoId");
        if (Utils.isNullOrEmpty(string)) {
            string = jsonObject.getObject("inlinePlayerData").getObject("onVisible").getObject("innertubeCommand").getObject("watchEndpoint").getString("videoId");
        }
        if (!Utils.isNullOrEmpty(string)) {
            try {
                return YoutubeStreamLinkHandlerFactory.getInstance().getUrl(string);
            } catch (Exception e) {
                throw new ParsingException("Could not get URL", e);
            }
        } else {
            throw new ParsingException("Could not get video ID");
        }
    }

    public long getViewCount() throws ParsingException {
        String string = this.a.getObject("overlayMetadata").getObject("secondaryText").getString("content");
        if (Utils.isNullOrEmpty(string)) {
            throw new ParsingException("Could not get short view count");
        } else if (string.contains("✪")) {
            return -1;
        } else {
            if (string.toLowerCase().contains("no views")) {
                return 0;
            }
            return Utils.mixedNumberWordToLong(string);
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
