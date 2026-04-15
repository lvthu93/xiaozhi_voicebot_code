package defpackage;

import com.grack.nanojson.JsonObject;
import j$.util.Collection$EL;
import java.util.List;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.comments.CommentsInfoItemExtractor;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.localization.DateWrapper;
import org.schabi.newpipe.extractor.localization.TimeAgoParser;
import org.schabi.newpipe.extractor.services.youtube.YoutubeDescriptionHelper;
import org.schabi.newpipe.extractor.services.youtube.YoutubeParsingHelper;
import org.schabi.newpipe.extractor.stream.Description;
import org.schabi.newpipe.extractor.utils.Utils;

/* renamed from: wf  reason: default package */
public final class wf implements CommentsInfoItemExtractor {
    public final JsonObject a;
    public final JsonObject b;
    public final JsonObject c;
    public final JsonObject d;
    public final String e;
    public final TimeAgoParser f;

    public wf(JsonObject jsonObject, JsonObject jsonObject2, JsonObject jsonObject3, JsonObject jsonObject4, String str, TimeAgoParser timeAgoParser) {
        this.a = jsonObject;
        this.b = jsonObject2;
        this.c = jsonObject3;
        this.d = jsonObject4;
        this.e = str;
        this.f = timeAgoParser;
    }

    public String getCommentId() throws ParsingException {
        String string = this.c.getObject("properties").getString("commentId");
        if (Utils.isNullOrEmpty(string)) {
            string = this.a.getString("commentId");
            if (Utils.isNullOrEmpty(string)) {
                throw new ParsingException("Could not get comment ID");
            }
        }
        return string;
    }

    public Description getCommentText() throws ParsingException {
        return new Description(YoutubeDescriptionHelper.attributedDescriptionToHtml(this.c.getObject("properties").getObject("content")), 1);
    }

    public int getLikeCount() throws ParsingException {
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

    public String getName() throws ParsingException {
        return getUploaderName();
    }

    public Page getReplies() throws ParsingException {
        JsonObject jsonObject = this.b;
        if (Utils.isNullOrEmpty(jsonObject)) {
            return null;
        }
        Class<JsonObject> cls = JsonObject.class;
        return new Page(this.e, (String) y2.ab(cls, 0, y2.aa(cls, 0, Collection$EL.stream(jsonObject.getArray("contents")))).map(new p8(23)).filter(new bz(19)).findFirst().map(new p8(24)).orElseThrow(new cb(7)));
    }

    public int getReplyCount() throws ParsingException {
        String string = this.c.getObject("toolbar").getString("replyCount");
        if (Utils.isNullOrEmpty(string)) {
            return 0;
        }
        return (int) Utils.mixedNumberWordToLong(string);
    }

    public final /* synthetic */ int getStreamPosition() {
        return p0.f(this);
    }

    public String getTextualLikeCount() {
        return this.c.getObject("toolbar").getString("likeCountNotliked");
    }

    public String getTextualUploadDate() throws ParsingException {
        return this.c.getObject("properties").getString("publishedTime");
    }

    public List<Image> getThumbnails() throws ParsingException {
        return getUploaderAvatars();
    }

    public DateWrapper getUploadDate() throws ParsingException {
        String textualUploadDate = getTextualUploadDate();
        if (Utils.isNullOrEmpty(textualUploadDate)) {
            return null;
        }
        return this.f.parse(textualUploadDate);
    }

    public List<Image> getUploaderAvatars() throws ParsingException {
        return YoutubeParsingHelper.getImagesFromThumbnailsArray(this.c.getObject("avatar").getObject("image").getArray("sources"));
    }

    public String getUploaderName() throws ParsingException {
        return this.c.getObject("author").getString("displayName");
    }

    public String getUploaderUrl() throws ParsingException {
        JsonObject object = this.c.getObject("author");
        String string = object.getString("channelId");
        if (Utils.isNullOrEmpty(string)) {
            string = object.getObject("channelCommand").getObject("innertubeCommand").getObject("browseEndpoint").getString("browseId");
            if (Utils.isNullOrEmpty(string)) {
                string = object.getObject("avatar").getObject("endpoint").getObject("innertubeCommand").getObject("browseEndpoint").getString("browseId");
                if (Utils.isNullOrEmpty(string)) {
                    throw new ParsingException("Could not get channel ID");
                }
            }
        }
        return y2.i("https://www.youtube.com/channel/", string);
    }

    public String getUrl() throws ParsingException {
        return this.e;
    }

    public boolean hasCreatorReply() {
        JsonObject jsonObject = this.b;
        if (jsonObject == null || !jsonObject.has("viewRepliesCreatorThumbnail")) {
            return false;
        }
        return true;
    }

    public boolean isChannelOwner() {
        return this.c.getObject("author").getBoolean("isCreator");
    }

    public boolean isHeartedByUploader() {
        return "TOOLBAR_HEART_STATE_HEARTED".equals(this.d.getString("heartState"));
    }

    public boolean isPinned() {
        return this.a.has("pinnedText");
    }

    public boolean isUploaderVerified() throws ParsingException {
        JsonObject object = this.c.getObject("author");
        if (object.getBoolean("isVerified") || object.getBoolean("isArtist")) {
            return true;
        }
        return false;
    }
}
