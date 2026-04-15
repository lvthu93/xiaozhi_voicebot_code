package org.schabi.newpipe.extractor.services.soundcloud.extractors;

import com.grack.nanojson.JsonObject;
import j$.util.Objects;
import java.util.List;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.comments.CommentsInfoItemExtractor;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.localization.DateWrapper;
import org.schabi.newpipe.extractor.services.soundcloud.SoundcloudParsingHelper;
import org.schabi.newpipe.extractor.stream.Description;

public class SoundcloudCommentsInfoItemExtractor implements CommentsInfoItemExtractor {
    public final JsonObject a;
    public final String b;

    public SoundcloudCommentsInfoItemExtractor(JsonObject jsonObject, String str) {
        this.a = jsonObject;
        this.b = str;
    }

    public String getCommentId() {
        return Objects.toString(Long.valueOf(this.a.getLong("id")), (String) null);
    }

    public Description getCommentText() {
        return new Description(this.a.getString("body"), 3);
    }

    public final /* synthetic */ int getLikeCount() {
        return p0.c(this);
    }

    public String getName() throws ParsingException {
        return this.a.getObject("user").getString("permalink");
    }

    public final /* synthetic */ Page getReplies() {
        return p0.d(this);
    }

    public final /* synthetic */ int getReplyCount() {
        return p0.e(this);
    }

    public int getStreamPosition() {
        return this.a.getInt("timestamp") / 1000;
    }

    public final /* synthetic */ String getTextualLikeCount() {
        return p0.g(this);
    }

    public String getTextualUploadDate() {
        return this.a.getString("created_at");
    }

    public List<Image> getThumbnails() {
        return SoundcloudParsingHelper.getAllImagesFromArtworkOrAvatarUrl(this.a.getObject("user").getString("avatar_url"));
    }

    public DateWrapper getUploadDate() throws ParsingException {
        return SoundcloudParsingHelper.parseDate(getTextualUploadDate());
    }

    public List<Image> getUploaderAvatars() {
        return SoundcloudParsingHelper.getAllImagesFromArtworkOrAvatarUrl(this.a.getObject("user").getString("avatar_url"));
    }

    public String getUploaderName() {
        return this.a.getObject("user").getString("username");
    }

    public String getUploaderUrl() {
        return this.a.getObject("user").getString("permalink_url");
    }

    public String getUrl() {
        return this.b;
    }

    public final /* synthetic */ boolean hasCreatorReply() {
        return p0.m(this);
    }

    public final /* synthetic */ boolean isChannelOwner() {
        return p0.n(this);
    }

    public final /* synthetic */ boolean isHeartedByUploader() {
        return p0.o(this);
    }

    public final /* synthetic */ boolean isPinned() {
        return p0.p(this);
    }

    public boolean isUploaderVerified() throws ParsingException {
        return this.a.getObject("user").getBoolean("verified");
    }
}
