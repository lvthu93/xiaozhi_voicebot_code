package org.schabi.newpipe.extractor.services.youtube.extractors;

import com.grack.nanojson.JsonObject;
import java.util.List;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.channel.ChannelInfoItemExtractor;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.services.youtube.YoutubeParsingHelper;
import org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeChannelLinkHandlerFactory;
import org.schabi.newpipe.extractor.utils.Utils;

public class YoutubeChannelInfoItemExtractor implements ChannelInfoItemExtractor {
    public final JsonObject a;
    public final boolean b;

    public YoutubeChannelInfoItemExtractor(JsonObject jsonObject) {
        boolean z;
        this.a = jsonObject;
        String textFromObject = YoutubeParsingHelper.getTextFromObject(jsonObject.getObject("subscriberCountText"));
        if (textFromObject != null) {
            z = textFromObject.startsWith("@");
        } else {
            z = false;
        }
        this.b = z;
    }

    public String getDescription() throws ParsingException {
        JsonObject jsonObject = this.a;
        try {
            if (!jsonObject.has("descriptionSnippet")) {
                return null;
            }
            return YoutubeParsingHelper.getTextFromObject(jsonObject.getObject("descriptionSnippet"));
        } catch (Exception e) {
            throw new ParsingException("Could not get description", e);
        }
    }

    public String getName() throws ParsingException {
        try {
            return YoutubeParsingHelper.getTextFromObject(this.a.getObject("title"));
        } catch (Exception e) {
            throw new ParsingException("Could not get name", e);
        }
    }

    public long getStreamCount() throws ParsingException {
        JsonObject jsonObject = this.a;
        try {
            if (this.b) {
                return -1;
            }
            if (!jsonObject.has("videoCountText")) {
                return -1;
            }
            return Long.parseLong(Utils.removeNonDigitCharacters(YoutubeParsingHelper.getTextFromObject(jsonObject.getObject("videoCountText"))));
        } catch (Exception e) {
            throw new ParsingException("Could not get stream count", e);
        }
    }

    public long getSubscriberCount() throws ParsingException {
        JsonObject jsonObject = this.a;
        try {
            if (!jsonObject.has("subscriberCountText")) {
                return -1;
            }
            if (!this.b) {
                return Utils.mixedNumberWordToLong(YoutubeParsingHelper.getTextFromObject(jsonObject.getObject("subscriberCountText")));
            }
            if (jsonObject.has("videoCountText")) {
                return Utils.mixedNumberWordToLong(YoutubeParsingHelper.getTextFromObject(jsonObject.getObject("videoCountText")));
            }
            return -1;
        } catch (Exception e) {
            throw new ParsingException("Could not get subscriber count", e);
        }
    }

    public List<Image> getThumbnails() throws ParsingException {
        try {
            return YoutubeParsingHelper.getThumbnailsFromInfoItem(this.a);
        } catch (Exception e) {
            throw new ParsingException("Could not get thumbnails", e);
        }
    }

    public String getUrl() throws ParsingException {
        try {
            return YoutubeChannelLinkHandlerFactory.getInstance().getUrl("channel/" + this.a.getString("channelId"));
        } catch (Exception e) {
            throw new ParsingException("Could not get url", e);
        }
    }

    public boolean isVerified() throws ParsingException {
        return YoutubeParsingHelper.isVerified(this.a.getArray("ownerBadges"));
    }
}
