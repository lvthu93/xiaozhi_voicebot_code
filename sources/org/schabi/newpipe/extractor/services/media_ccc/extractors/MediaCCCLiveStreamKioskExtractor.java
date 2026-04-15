package org.schabi.newpipe.extractor.services.media_ccc.extractors;

import com.grack.nanojson.JsonObject;
import java.util.Iterator;
import java.util.List;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.localization.DateWrapper;
import org.schabi.newpipe.extractor.stream.ContentAvailability;
import org.schabi.newpipe.extractor.stream.StreamInfoItemExtractor;
import org.schabi.newpipe.extractor.stream.StreamType;

public class MediaCCCLiveStreamKioskExtractor implements StreamInfoItemExtractor {
    public final JsonObject a;
    public final String b;
    public final JsonObject c;

    public MediaCCCLiveStreamKioskExtractor(JsonObject jsonObject, String str, JsonObject jsonObject2) {
        this.a = jsonObject;
        this.b = str;
        this.c = jsonObject2;
    }

    public final /* synthetic */ ContentAvailability getContentAvailability() {
        return ob.a(this);
    }

    public long getDuration() throws ParsingException {
        return 0;
    }

    public String getName() throws ParsingException {
        return this.c.getObject("talks").getObject("current").getString("title");
    }

    public final /* synthetic */ String getShortDescription() {
        return ob.b(this);
    }

    public StreamType getStreamType() throws ParsingException {
        boolean z;
        Iterator it = this.c.getArray("streams").iterator();
        while (true) {
            if (it.hasNext()) {
                if ("video".equals(((JsonObject) it.next()).getString("type"))) {
                    z = true;
                    break;
                }
            } else {
                z = false;
                break;
            }
        }
        if (z) {
            return StreamType.LIVE_STREAM;
        }
        return StreamType.AUDIO_LIVE_STREAM;
    }

    public String getTextualUploadDate() throws ParsingException {
        return null;
    }

    public List<Image> getThumbnails() throws ParsingException {
        return MediaCCCParsingHelper.getThumbnailsFromLiveStreamItem(this.c);
    }

    public DateWrapper getUploadDate() throws ParsingException {
        return null;
    }

    public final /* synthetic */ List getUploaderAvatars() {
        return ob.c(this);
    }

    public String getUploaderName() throws ParsingException {
        return this.a.getString("conference") + " - " + this.b + " - " + this.c.getString("display");
    }

    public String getUploaderUrl() throws ParsingException {
        return "https://media.ccc.de/c/" + this.a.getString("slug");
    }

    public String getUrl() throws ParsingException {
        return this.c.getString("link");
    }

    public long getViewCount() throws ParsingException {
        return -1;
    }

    public boolean isAd() throws ParsingException {
        return false;
    }

    public final /* synthetic */ boolean isShortFormContent() {
        return ob.d(this);
    }

    public boolean isUploaderVerified() throws ParsingException {
        return false;
    }
}
