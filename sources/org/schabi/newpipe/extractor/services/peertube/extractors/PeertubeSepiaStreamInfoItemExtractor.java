package org.schabi.newpipe.extractor.services.peertube.extractors;

import com.grack.nanojson.JsonObject;

public class PeertubeSepiaStreamInfoItemExtractor extends PeertubeStreamInfoItemExtractor {
    public PeertubeSepiaStreamInfoItemExtractor(JsonObject jsonObject, String str) {
        super(jsonObject, str);
        this.b = this.a.getString("embedUrl").replace(this.a.getString("embedPath"), "");
    }
}
