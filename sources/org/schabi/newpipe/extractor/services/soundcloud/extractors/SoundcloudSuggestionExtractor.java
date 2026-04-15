package org.schabi.newpipe.extractor.services.soundcloud.extractors;

import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonParser;
import com.grack.nanojson.JsonParserException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.schabi.newpipe.extractor.NewPipe;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.services.soundcloud.SoundcloudParsingHelper;
import org.schabi.newpipe.extractor.suggestion.SuggestionExtractor;
import org.schabi.newpipe.extractor.utils.Utils;

public class SoundcloudSuggestionExtractor extends SuggestionExtractor {
    public SoundcloudSuggestionExtractor(StreamingService streamingService) {
        super(streamingService);
    }

    public List<String> suggestionList(String str) throws IOException, ExtractionException {
        ArrayList arrayList = new ArrayList();
        Downloader downloader = NewPipe.getDownloader();
        try {
            Iterator it = JsonParser.object().from(downloader.get("https://api-v2.soundcloud.com/search/queries?q=" + Utils.encodeUrlUtf8(str) + "&client_id=" + SoundcloudParsingHelper.clientId() + "&limit=10", getExtractorLocalization()).responseBody()).getArray("collection").iterator();
            while (it.hasNext()) {
                Object next = it.next();
                if (next instanceof JsonObject) {
                    arrayList.add(((JsonObject) next).getString("query"));
                }
            }
            return arrayList;
        } catch (JsonParserException e) {
            throw new ParsingException("Could not parse json response", e);
        }
    }
}
