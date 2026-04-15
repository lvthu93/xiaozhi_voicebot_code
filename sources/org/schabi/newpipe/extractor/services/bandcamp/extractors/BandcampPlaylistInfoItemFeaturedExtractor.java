package org.schabi.newpipe.extractor.services.bandcamp.extractors;

import com.grack.nanojson.JsonObject;
import java.util.List;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.playlist.PlaylistInfo;
import org.schabi.newpipe.extractor.playlist.PlaylistInfoItemExtractor;
import org.schabi.newpipe.extractor.stream.Description;
import org.schabi.newpipe.extractor.utils.Utils;

public class BandcampPlaylistInfoItemFeaturedExtractor implements PlaylistInfoItemExtractor {
    public final JsonObject a;

    public BandcampPlaylistInfoItemFeaturedExtractor(JsonObject jsonObject) {
        this.a = jsonObject;
    }

    public final /* synthetic */ Description getDescription() {
        return y8.a(this);
    }

    public String getName() {
        return this.a.getString("album_title");
    }

    public final /* synthetic */ PlaylistInfo.PlaylistType getPlaylistType() {
        return y8.b(this);
    }

    public long getStreamCount() {
        return (long) this.a.getInt("num_streamable_tracks");
    }

    public List<Image> getThumbnails() {
        JsonObject jsonObject = this.a;
        if (jsonObject.has("art_id")) {
            return BandcampExtractorHelper.getImagesFromImageId(jsonObject.getLong("art_id"), true);
        }
        return BandcampExtractorHelper.getImagesFromImageId(jsonObject.getLong("item_art_id"), true);
    }

    public String getUploaderName() {
        return this.a.getString("band_name");
    }

    public String getUploaderUrl() {
        return null;
    }

    public String getUrl() {
        return Utils.replaceHttpWithHttps(this.a.getString("item_url"));
    }

    public boolean isUploaderVerified() {
        return false;
    }
}
