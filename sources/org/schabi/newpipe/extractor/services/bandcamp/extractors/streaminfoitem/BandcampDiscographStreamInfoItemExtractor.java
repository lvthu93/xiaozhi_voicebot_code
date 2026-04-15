package org.schabi.newpipe.extractor.services.bandcamp.extractors.streaminfoitem;

import com.grack.nanojson.JsonObject;
import java.util.List;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.services.bandcamp.extractors.BandcampExtractorHelper;

public class BandcampDiscographStreamInfoItemExtractor extends BandcampStreamInfoItemExtractor {
    public final JsonObject b;

    public BandcampDiscographStreamInfoItemExtractor(JsonObject jsonObject, String str) {
        super(str);
        this.b = jsonObject;
    }

    public long getDuration() {
        return -1;
    }

    public String getName() {
        return this.b.getString("title");
    }

    public List<Image> getThumbnails() throws ParsingException {
        return BandcampExtractorHelper.getImagesFromImageId(this.b.getLong("art_id"), true);
    }

    public String getUploaderName() {
        return this.b.getString("band_name");
    }

    public String getUrl() throws ParsingException {
        JsonObject jsonObject = this.b;
        return BandcampExtractorHelper.getStreamUrlFromIds(jsonObject.getLong("band_id"), jsonObject.getLong("item_id"), jsonObject.getString("item_type"));
    }
}
