package org.schabi.newpipe.extractor.services.youtube.extractors;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonParser;
import com.grack.nanojson.JsonParserException;
import j$.util.Collection$EL;
import j$.util.stream.Collectors;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.schabi.newpipe.extractor.NewPipe;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.downloader.Response;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.suggestion.SuggestionExtractor;
import org.schabi.newpipe.extractor.utils.Utils;

public class YoutubeSuggestionExtractor extends SuggestionExtractor {
    public YoutubeSuggestionExtractor(StreamingService streamingService) {
        super(streamingService);
    }

    public List<String> suggestionList(String str) throws IOException, ExtractionException {
        Class<JsonArray> cls = JsonArray.class;
        HashMap hashMap = new HashMap();
        hashMap.put("Origin", Collections.singletonList("https://www.youtube.com"));
        hashMap.put("Referer", Collections.singletonList("https://www.youtube.com"));
        Response response = NewPipe.getDownloader().get("https://suggestqueries-clients6.youtube.com/complete/search?client=youtube&ds=yt&gl=" + Utils.encodeUrlUtf8(getExtractorContentCountry().getCountryCode()) + "&q=" + Utils.encodeUrlUtf8(str) + "&xhr=t", hashMap, getExtractorLocalization());
        String header = response.getHeader("Content-Type");
        if (Utils.isNullOrEmpty(header) || !header.contains("application/json")) {
            throw new ExtractionException("Invalid response type (got \"" + header + "\", excepted a JSON response) (response code " + response.responseCode() + ")");
        }
        String responseBody = response.responseBody();
        if (!responseBody.isEmpty()) {
            try {
                return (List) Collection$EL.stream(JsonParser.array().from(responseBody).getArray(1)).filter(new uf(cls, 25)).map(new vf(cls, 25)).map(new fg(23)).filter(new mg(12)).collect(Collectors.toUnmodifiableList());
            } catch (JsonParserException e) {
                throw new ParsingException("Could not parse JSON response", e);
            }
        } else {
            throw new ExtractionException("Empty response received");
        }
    }
}
