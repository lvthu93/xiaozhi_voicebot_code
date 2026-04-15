package org.schabi.newpipe.extractor.services.media_ccc.extractors.infoItems;

import com.grack.nanojson.JsonObject;
import java.util.List;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.channel.ChannelInfoItemExtractor;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.services.media_ccc.extractors.MediaCCCParsingHelper;

public class MediaCCCConferenceInfoItemExtractor implements ChannelInfoItemExtractor {
    public final JsonObject a;

    public MediaCCCConferenceInfoItemExtractor(JsonObject jsonObject) {
        this.a = jsonObject;
    }

    public String getDescription() {
        return "";
    }

    public String getName() throws ParsingException {
        return this.a.getString("title");
    }

    public long getStreamCount() {
        return -1;
    }

    public long getSubscriberCount() {
        return -1;
    }

    public List<Image> getThumbnails() {
        return MediaCCCParsingHelper.getImageListFromLogoImageUrl(this.a.getString("logo_url"));
    }

    public String getUrl() throws ParsingException {
        return this.a.getString("url");
    }

    public boolean isVerified() throws ParsingException {
        return false;
    }
}
