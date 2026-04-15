package org.schabi.newpipe.extractor.services.peertube.extractors;

import com.grack.nanojson.JsonObject;
import java.util.List;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.channel.ChannelInfoItemExtractor;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.services.peertube.PeertubeParsingHelper;

public class PeertubeChannelInfoItemExtractor implements ChannelInfoItemExtractor {
    public final JsonObject a;
    public final String b;

    public PeertubeChannelInfoItemExtractor(JsonObject jsonObject, String str) {
        this.a = jsonObject;
        this.b = str;
    }

    public String getDescription() throws ParsingException {
        return this.a.getString("description");
    }

    public String getName() throws ParsingException {
        return this.a.getString("displayName");
    }

    public long getStreamCount() throws ParsingException {
        return -1;
    }

    public long getSubscriberCount() throws ParsingException {
        return (long) this.a.getInt("followersCount");
    }

    public List<Image> getThumbnails() throws ParsingException {
        return PeertubeParsingHelper.getAvatarsFromOwnerAccountOrVideoChannelObject(this.b, this.a);
    }

    public String getUrl() throws ParsingException {
        return this.a.getString("url");
    }

    public boolean isVerified() throws ParsingException {
        return false;
    }
}
