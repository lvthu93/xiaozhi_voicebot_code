package org.schabi.newpipe.extractor.services.bandcamp.extractors;

import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonParser;
import com.grack.nanojson.JsonParserException;
import com.grack.nanojson.JsonStringWriter;
import com.grack.nanojson.JsonWriter;
import j$.util.Collection$EL;
import j$.util.stream.Collectors;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import org.schabi.newpipe.extractor.NewPipe;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.suggestion.SuggestionExtractor;

public class BandcampSuggestionExtractor extends SuggestionExtractor {
    public BandcampSuggestionExtractor(StreamingService streamingService) {
        super(streamingService);
    }

    public List<String> suggestionList(String str) throws IOException, ExtractionException {
        Class<JsonObject> cls = JsonObject.class;
        try {
            return (List) Collection$EL.stream(JsonParser.object().from(NewPipe.getDownloader().postWithContentTypeJson("https://bandcamp.com/api/bcsearch_public_api/1/autocomplete_elastic", Collections.emptyMap(), ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) JsonWriter.string().object()).value("fan_id", (String) null)).value("full_page", false)).value("search_filter", "")).value("search_text", str)).end()).done().getBytes(StandardCharsets.UTF_8)).responseBody()).getObject("auto").getArray("results")).filter(new c4(cls, 2)).map(new d4(cls, 2)).map(new z5(22)).distinct().collect(Collectors.toList());
        } catch (JsonParserException unused) {
            return Collections.emptyList();
        }
    }
}
