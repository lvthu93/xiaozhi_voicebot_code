package org.schabi.newpipe.extractor.services.bandcamp.extractors;

import com.grack.nanojson.JsonObject;
import java.util.List;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.comments.CommentsInfoItemExtractor;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.localization.DateWrapper;
import org.schabi.newpipe.extractor.stream.Description;

public class BandcampCommentsInfoItemExtractor implements CommentsInfoItemExtractor {
    public final JsonObject a;
    public final String b;

    public BandcampCommentsInfoItemExtractor(JsonObject jsonObject, String str) {
        this.a = jsonObject;
        this.b = str;
    }

    public final /* synthetic */ String getCommentId() {
        return p0.a(this);
    }

    public Description getCommentText() throws ParsingException {
        return new Description(this.a.getString("why"), 3);
    }

    public final /* synthetic */ int getLikeCount() {
        return p0.c(this);
    }

    public String getName() throws ParsingException {
        return getCommentText().getContent();
    }

    public final /* synthetic */ Page getReplies() {
        return p0.d(this);
    }

    public final /* synthetic */ int getReplyCount() {
        return p0.e(this);
    }

    public final /* synthetic */ int getStreamPosition() {
        return p0.f(this);
    }

    public final /* synthetic */ String getTextualLikeCount() {
        return p0.g(this);
    }

    public final /* synthetic */ String getTextualUploadDate() {
        return p0.h(this);
    }

    public List<Image> getThumbnails() throws ParsingException {
        return getUploaderAvatars();
    }

    public final /* synthetic */ DateWrapper getUploadDate() {
        return p0.i(this);
    }

    public List<Image> getUploaderAvatars() {
        return BandcampExtractorHelper.getImagesFromImageId(this.a.getLong("image_id"), false);
    }

    public String getUploaderName() throws ParsingException {
        return this.a.getString("name");
    }

    public final /* synthetic */ String getUploaderUrl() {
        return p0.l(this);
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

    public final /* synthetic */ boolean isUploaderVerified() {
        return p0.q(this);
    }
}
