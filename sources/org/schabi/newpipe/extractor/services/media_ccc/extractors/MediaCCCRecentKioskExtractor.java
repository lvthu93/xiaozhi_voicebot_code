package org.schabi.newpipe.extractor.services.media_ccc.extractors;

import com.grack.nanojson.JsonObject;
import j$.time.ZonedDateTime;
import j$.time.format.DateTimeFormatter;
import java.util.List;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.localization.DateWrapper;
import org.schabi.newpipe.extractor.services.media_ccc.linkHandler.MediaCCCConferenceLinkHandlerFactory;
import org.schabi.newpipe.extractor.stream.ContentAvailability;
import org.schabi.newpipe.extractor.stream.StreamInfoItemExtractor;
import org.schabi.newpipe.extractor.stream.StreamType;

public class MediaCCCRecentKioskExtractor implements StreamInfoItemExtractor {
    public final JsonObject a;

    public MediaCCCRecentKioskExtractor(JsonObject jsonObject) {
        this.a = jsonObject;
    }

    public final /* synthetic */ ContentAvailability getContentAvailability() {
        return ob.a(this);
    }

    public long getDuration() {
        return (long) this.a.getInt("duration");
    }

    public String getName() throws ParsingException {
        return this.a.getString("title");
    }

    public final /* synthetic */ String getShortDescription() {
        return ob.b(this);
    }

    public StreamType getStreamType() throws ParsingException {
        return StreamType.VIDEO_STREAM;
    }

    public String getTextualUploadDate() throws ParsingException {
        return this.a.getString("date");
    }

    public List<Image> getThumbnails() throws ParsingException {
        return MediaCCCParsingHelper.getImageListFromLogoImageUrl(this.a.getString("poster_url"));
    }

    public DateWrapper getUploadDate() throws ParsingException {
        return new DateWrapper(ZonedDateTime.parse(this.a.getString("date"), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSzzzz")).toInstant());
    }

    public final /* synthetic */ List getUploaderAvatars() {
        return ob.c(this);
    }

    public String getUploaderName() throws ParsingException {
        return this.a.getString("conference_title");
    }

    public String getUploaderUrl() throws ParsingException {
        return MediaCCCConferenceLinkHandlerFactory.getInstance().fromUrl(this.a.getString("conference_url")).getUrl();
    }

    public String getUrl() throws ParsingException {
        return this.a.getString("frontend_link");
    }

    public long getViewCount() throws ParsingException {
        return (long) this.a.getInt("view_count");
    }

    public boolean isAd() {
        return false;
    }

    public final /* synthetic */ boolean isShortFormContent() {
        return ob.d(this);
    }

    public boolean isUploaderVerified() throws ParsingException {
        return false;
    }
}
