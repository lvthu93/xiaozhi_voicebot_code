package org.schabi.newpipe.extractor.services.bandcamp.extractors;

import com.grack.nanojson.JsonObject;
import java.util.List;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.localization.DateWrapper;
import org.schabi.newpipe.extractor.stream.ContentAvailability;
import org.schabi.newpipe.extractor.stream.StreamInfoItemExtractor;
import org.schabi.newpipe.extractor.stream.StreamType;

public class BandcampRadioInfoItemExtractor implements StreamInfoItemExtractor {
    public final JsonObject a;

    public BandcampRadioInfoItemExtractor(JsonObject jsonObject) {
        this.a = jsonObject;
    }

    public final /* synthetic */ ContentAvailability getContentAvailability() {
        return ob.a(this);
    }

    public long getDuration() {
        return 0;
    }

    public String getName() throws ParsingException {
        return this.a.getString("subtitle");
    }

    public String getShortDescription() {
        return this.a.getString("desc");
    }

    public StreamType getStreamType() {
        return StreamType.AUDIO_STREAM;
    }

    public String getTextualUploadDate() {
        return this.a.getString("date");
    }

    public List<Image> getThumbnails() {
        return BandcampExtractorHelper.getImagesFromImageId(this.a.getLong("image_id"), false);
    }

    public DateWrapper getUploadDate() throws ParsingException {
        return BandcampExtractorHelper.parseDate(getTextualUploadDate());
    }

    public final /* synthetic */ List getUploaderAvatars() {
        return ob.c(this);
    }

    public String getUploaderName() {
        return this.a.getString("title");
    }

    public String getUploaderUrl() {
        return "";
    }

    public String getUrl() {
        return "https://bandcamp.com/?show=" + this.a.getInt("id");
    }

    public long getViewCount() {
        return -1;
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
