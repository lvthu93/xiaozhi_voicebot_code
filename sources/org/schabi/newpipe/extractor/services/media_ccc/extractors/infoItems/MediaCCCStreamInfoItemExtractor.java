package org.schabi.newpipe.extractor.services.media_ccc.extractors.infoItems;

import com.grack.nanojson.JsonObject;
import java.util.List;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.localization.DateWrapper;
import org.schabi.newpipe.extractor.services.media_ccc.extractors.MediaCCCParsingHelper;
import org.schabi.newpipe.extractor.stream.ContentAvailability;
import org.schabi.newpipe.extractor.stream.StreamInfoItemExtractor;
import org.schabi.newpipe.extractor.stream.StreamType;

public class MediaCCCStreamInfoItemExtractor implements StreamInfoItemExtractor {
    public final JsonObject a;

    public MediaCCCStreamInfoItemExtractor(JsonObject jsonObject) {
        this.a = jsonObject;
    }

    public final /* synthetic */ ContentAvailability getContentAvailability() {
        return ob.a(this);
    }

    public long getDuration() {
        return (long) this.a.getInt("length");
    }

    public String getName() throws ParsingException {
        return this.a.getString("title");
    }

    public final /* synthetic */ String getShortDescription() {
        return ob.b(this);
    }

    public StreamType getStreamType() {
        return StreamType.VIDEO_STREAM;
    }

    public String getTextualUploadDate() {
        return this.a.getString("release_date");
    }

    public List<Image> getThumbnails() {
        return MediaCCCParsingHelper.getThumbnailsFromStreamItem(this.a);
    }

    public DateWrapper getUploadDate() throws ParsingException {
        return DateWrapper.fromOffsetDateTime(getTextualUploadDate());
    }

    public final /* synthetic */ List getUploaderAvatars() {
        return ob.c(this);
    }

    public String getUploaderName() {
        return this.a.getString("conference_title");
    }

    public String getUploaderUrl() {
        return this.a.getString("conference_url");
    }

    public String getUrl() throws ParsingException {
        return "https://media.ccc.de/public/events/" + this.a.getString("guid");
    }

    public long getViewCount() {
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
