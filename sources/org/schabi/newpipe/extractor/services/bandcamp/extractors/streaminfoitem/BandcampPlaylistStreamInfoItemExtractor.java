package org.schabi.newpipe.extractor.services.bandcamp.extractors.streaminfoitem;

import com.grack.nanojson.JsonObject;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.stream.StreamExtractor;

public class BandcampPlaylistStreamInfoItemExtractor extends BandcampStreamInfoItemExtractor {
    public final JsonObject b;
    public final List<Image> c;
    public final StreamingService d;

    public BandcampPlaylistStreamInfoItemExtractor(JsonObject jsonObject, String str, StreamingService streamingService) {
        super(str);
        this.b = jsonObject;
        this.d = streamingService;
        this.c = Collections.emptyList();
    }

    public long getDuration() {
        return this.b.getLong("duration");
    }

    public String getName() {
        return this.b.getString("title");
    }

    public List<Image> getThumbnails() throws ParsingException {
        List<Image> list = this.c;
        if (!list.isEmpty() || getUrl() == null) {
            return list;
        }
        try {
            StreamExtractor streamExtractor = this.d.getStreamExtractor(getUrl());
            streamExtractor.fetchPage();
            return streamExtractor.getThumbnails();
        } catch (IOException | ExtractionException e) {
            throw new ParsingException("Could not download cover art location", e);
        }
    }

    public String getUploaderName() {
        return "";
    }

    public String getUrl() {
        String string = this.b.getString("title_link");
        if (string == null) {
            return null;
        }
        return getUploaderUrl() + string;
    }

    public BandcampPlaylistStreamInfoItemExtractor(JsonObject jsonObject, String str, List<Image> list) {
        this(jsonObject, str, (StreamingService) null);
        this.c = list;
    }
}
