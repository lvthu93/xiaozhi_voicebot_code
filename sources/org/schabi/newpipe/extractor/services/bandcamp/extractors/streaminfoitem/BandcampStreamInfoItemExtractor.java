package org.schabi.newpipe.extractor.services.bandcamp.extractors.streaminfoitem;

import java.util.List;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.localization.DateWrapper;
import org.schabi.newpipe.extractor.stream.ContentAvailability;
import org.schabi.newpipe.extractor.stream.StreamInfoItemExtractor;
import org.schabi.newpipe.extractor.stream.StreamType;

public abstract class BandcampStreamInfoItemExtractor implements StreamInfoItemExtractor {
    public final String a;

    public BandcampStreamInfoItemExtractor(String str) {
        this.a = str;
    }

    public final /* synthetic */ ContentAvailability getContentAvailability() {
        return ob.a(this);
    }

    public final /* synthetic */ String getShortDescription() {
        return ob.b(this);
    }

    public StreamType getStreamType() {
        return StreamType.AUDIO_STREAM;
    }

    public String getTextualUploadDate() {
        return null;
    }

    public DateWrapper getUploadDate() {
        return null;
    }

    public final /* synthetic */ List getUploaderAvatars() {
        return ob.c(this);
    }

    public String getUploaderUrl() {
        return this.a;
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
