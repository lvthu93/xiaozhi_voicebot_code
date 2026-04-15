package org.schabi.newpipe.extractor.services.soundcloud.extractors;

import com.grack.nanojson.JsonObject;
import java.util.List;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.channel.ChannelInfoItemExtractor;
import org.schabi.newpipe.extractor.services.soundcloud.SoundcloudParsingHelper;
import org.schabi.newpipe.extractor.utils.Utils;

public class SoundcloudChannelInfoItemExtractor implements ChannelInfoItemExtractor {
    public final JsonObject a;

    public SoundcloudChannelInfoItemExtractor(JsonObject jsonObject) {
        this.a = jsonObject;
    }

    public String getDescription() {
        return this.a.getString("description", "");
    }

    public String getName() {
        return this.a.getString("username");
    }

    public long getStreamCount() {
        return this.a.getLong("track_count");
    }

    public long getSubscriberCount() {
        return this.a.getLong("followers_count");
    }

    public List<Image> getThumbnails() {
        return SoundcloudParsingHelper.getAllImagesFromArtworkOrAvatarUrl(this.a.getString("avatar_url"));
    }

    public String getUrl() {
        return Utils.replaceHttpWithHttps(this.a.getString("permalink_url"));
    }

    public boolean isVerified() {
        return this.a.getBoolean("verified");
    }
}
