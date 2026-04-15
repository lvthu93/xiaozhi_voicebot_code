package org.schabi.newpipe.extractor.services.bandcamp.extractors;

import com.grack.nanojson.JsonObject;
import java.util.List;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.playlist.PlaylistInfo;
import org.schabi.newpipe.extractor.playlist.PlaylistInfoItemExtractor;
import org.schabi.newpipe.extractor.stream.Description;

public class BandcampAlbumInfoItemExtractor implements PlaylistInfoItemExtractor {
    public final JsonObject a;
    public final String b;

    public BandcampAlbumInfoItemExtractor(JsonObject jsonObject, String str) {
        this.a = jsonObject;
        this.b = str;
    }

    public final /* synthetic */ Description getDescription() {
        return y8.a(this);
    }

    public String getName() throws ParsingException {
        return this.a.getString("title");
    }

    public final /* synthetic */ PlaylistInfo.PlaylistType getPlaylistType() {
        return y8.b(this);
    }

    public long getStreamCount() {
        return -1;
    }

    public List<Image> getThumbnails() throws ParsingException {
        return BandcampExtractorHelper.getImagesFromImageId(this.a.getLong("art_id"), true);
    }

    public String getUploaderName() throws ParsingException {
        return this.a.getString("band_name");
    }

    public String getUploaderUrl() {
        return this.b;
    }

    public String getUrl() throws ParsingException {
        JsonObject jsonObject = this.a;
        return BandcampExtractorHelper.getStreamUrlFromIds(jsonObject.getLong("band_id"), jsonObject.getLong("item_id"), jsonObject.getString("item_type"));
    }

    public boolean isUploaderVerified() {
        return false;
    }
}
