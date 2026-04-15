package org.schabi.newpipe.extractor.services.youtube.extractors;

import com.grack.nanojson.JsonObject;
import java.util.List;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.comments.CommentsInfoItemExtractor;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.localization.DateWrapper;
import org.schabi.newpipe.extractor.localization.TimeAgoParser;
import org.schabi.newpipe.extractor.services.youtube.YoutubeParsingHelper;
import org.schabi.newpipe.extractor.stream.Description;
import org.schabi.newpipe.extractor.utils.JsonUtils;
import org.schabi.newpipe.extractor.utils.Utils;

public class YoutubeCommentsInfoItemExtractor implements CommentsInfoItemExtractor {
    public final JsonObject a;
    public final JsonObject b;
    public final String c;
    public final TimeAgoParser d;

    public YoutubeCommentsInfoItemExtractor(JsonObject jsonObject, JsonObject jsonObject2, String str, TimeAgoParser timeAgoParser) {
        this.a = jsonObject;
        this.b = jsonObject2;
        this.c = str;
        this.d = timeAgoParser;
    }

    public String getCommentId() throws ParsingException {
        try {
            return JsonUtils.getString(this.a, "commentId");
        } catch (Exception e) {
            throw new ParsingException("Could not get comment id", e);
        }
    }

    public Description getCommentText() throws ParsingException {
        try {
            JsonObject object = JsonUtils.getObject(this.a, "contentText");
            if (object.isEmpty()) {
                return Description.g;
            }
            return new Description(Utils.removeUTF8BOM(YoutubeParsingHelper.getTextFromObject(object, true)), 1);
        } catch (Exception e) {
            throw new ParsingException("Could not get comment text", e);
        }
    }

    public int getLikeCount() throws ParsingException {
        try {
            String removeNonDigitCharacters = Utils.removeNonDigitCharacters(JsonUtils.getString(this.a, "actionButtons.commentActionButtonsRenderer.likeButton.toggleButtonRenderer.accessibilityData.accessibilityData.label"));
            try {
                if (Utils.isBlank(removeNonDigitCharacters)) {
                    return 0;
                }
                return Integer.parseInt(removeNonDigitCharacters);
            } catch (Exception e) {
                throw new ParsingException("Unexpected error while parsing like count as Integer", e);
            }
        } catch (Exception unused) {
            String textualLikeCount = getTextualLikeCount();
            try {
                if (Utils.isBlank(textualLikeCount)) {
                    return 0;
                }
                return (int) Utils.mixedNumberWordToLong(textualLikeCount);
            } catch (Exception e2) {
                throw new ParsingException("Unexpected error while converting textual like count to like count", e2);
            }
        }
    }

    public String getName() throws ParsingException {
        try {
            return YoutubeParsingHelper.getTextFromObject(JsonUtils.getObject(this.a, "authorText"));
        } catch (Exception unused) {
            return "";
        }
    }

    public Page getReplies() {
        JsonObject jsonObject = this.b;
        if (jsonObject == null) {
            return null;
        }
        try {
            return new Page(this.c, JsonUtils.getString(JsonUtils.getArray(jsonObject, "contents").getObject(0), "continuationItemRenderer.continuationEndpoint.continuationCommand.token"));
        } catch (Exception unused) {
            return null;
        }
    }

    public int getReplyCount() {
        JsonObject jsonObject = this.a;
        if (jsonObject.has("replyCount")) {
            return jsonObject.getInt("replyCount");
        }
        return -1;
    }

    public final /* synthetic */ int getStreamPosition() {
        return p0.f(this);
    }

    public String getTextualLikeCount() throws ParsingException {
        JsonObject jsonObject = this.a;
        try {
            if (!jsonObject.has("voteCount")) {
                return "";
            }
            JsonObject object = JsonUtils.getObject(jsonObject, "voteCount");
            if (object.isEmpty()) {
                return "";
            }
            return YoutubeParsingHelper.getTextFromObject(object);
        } catch (Exception e) {
            throw new ParsingException("Could not get the vote count", e);
        }
    }

    public String getTextualUploadDate() throws ParsingException {
        try {
            return YoutubeParsingHelper.getTextFromObject(JsonUtils.getObject(this.a, "publishedTimeText"));
        } catch (Exception e) {
            throw new ParsingException("Could not get publishedTimeText", e);
        }
    }

    public List<Image> getThumbnails() throws ParsingException {
        try {
            return YoutubeParsingHelper.getImagesFromThumbnailsArray(JsonUtils.getArray(this.a, "authorThumbnail.thumbnails"));
        } catch (Exception e) {
            throw new ParsingException("Could not get author thumbnails", e);
        }
    }

    public DateWrapper getUploadDate() throws ParsingException {
        String textualUploadDate = getTextualUploadDate();
        if (textualUploadDate == null || textualUploadDate.isEmpty()) {
            return null;
        }
        return this.d.parse(textualUploadDate);
    }

    public List<Image> getUploaderAvatars() throws ParsingException {
        try {
            return YoutubeParsingHelper.getImagesFromThumbnailsArray(JsonUtils.getArray(this.a, "authorThumbnail.thumbnails"));
        } catch (Exception e) {
            throw new ParsingException("Could not get author thumbnails", e);
        }
    }

    public String getUploaderName() throws ParsingException {
        try {
            return YoutubeParsingHelper.getTextFromObject(JsonUtils.getObject(this.a, "authorText"));
        } catch (Exception unused) {
            return "";
        }
    }

    public String getUploaderUrl() throws ParsingException {
        try {
            return "https://www.youtube.com/channel/" + JsonUtils.getString(this.a, "authorEndpoint.browseEndpoint.browseId");
        } catch (Exception unused) {
            return "";
        }
    }

    public String getUrl() throws ParsingException {
        return this.c;
    }

    public boolean hasCreatorReply() {
        JsonObject jsonObject = this.b;
        if (jsonObject == null) {
            return false;
        }
        return jsonObject.has("viewRepliesCreatorThumbnail");
    }

    public boolean isChannelOwner() {
        return this.a.getBoolean("authorIsChannelOwner");
    }

    public boolean isHeartedByUploader() {
        return this.a.getObject("actionButtons").getObject("commentActionButtonsRenderer").has("creatorHeart");
    }

    public boolean isPinned() {
        return this.a.has("pinnedCommentBadge");
    }

    public boolean isUploaderVerified() throws ParsingException {
        return this.a.has("authorCommentBadge");
    }
}
