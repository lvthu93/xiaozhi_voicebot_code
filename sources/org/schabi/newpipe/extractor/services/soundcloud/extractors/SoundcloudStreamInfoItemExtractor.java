package org.schabi.newpipe.extractor.services.soundcloud.extractors;

import com.grack.nanojson.JsonObject;
import java.util.List;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.localization.DateWrapper;
import org.schabi.newpipe.extractor.services.soundcloud.SoundcloudParsingHelper;
import org.schabi.newpipe.extractor.stream.ContentAvailability;
import org.schabi.newpipe.extractor.stream.StreamInfoItemExtractor;
import org.schabi.newpipe.extractor.stream.StreamType;
import org.schabi.newpipe.extractor.utils.Utils;

public class SoundcloudStreamInfoItemExtractor implements StreamInfoItemExtractor {
    public final JsonObject a;

    public SoundcloudStreamInfoItemExtractor(JsonObject jsonObject) {
        this.a = jsonObject;
    }

    public final /* synthetic */ ContentAvailability getContentAvailability() {
        return ob.a(this);
    }

    public long getDuration() {
        return this.a.getLong("duration") / 1000;
    }

    public String getName() {
        return this.a.getString("title");
    }

    public final /* synthetic */ String getShortDescription() {
        return ob.b(this);
    }

    public StreamType getStreamType() {
        return StreamType.AUDIO_STREAM;
    }

    public String getTextualUploadDate() {
        return this.a.getString("created_at");
    }

    public List<Image> getThumbnails() throws ParsingException {
        return SoundcloudParsingHelper.getAllImagesFromTrackObject(this.a);
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
        return Utils.replaceHttpWithHttps(this.a.getObject("user").getString("permalink_url"));
    }

    public String getUrl() {
        return Utils.replaceHttpWithHttps(this.a.getString("permalink_url"));
    }

    public long getViewCount() {
        return this.a.getLong("playback_count");
    }

    public boolean isAd() {
        return false;
    }

    public final /* synthetic */ boolean isShortFormContent() {
        return ob.d(this);
    }

    public boolean isUploaderVerified() throws ParsingException {
        return this.a.getObject("user").getBoolean("verified");
    }
}
