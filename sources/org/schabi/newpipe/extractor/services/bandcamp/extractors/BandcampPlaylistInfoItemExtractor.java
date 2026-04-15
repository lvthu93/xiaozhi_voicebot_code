package org.schabi.newpipe.extractor.services.bandcamp.extractors;

import java.util.List;
import org.jsoup.nodes.Element;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.playlist.PlaylistInfo;
import org.schabi.newpipe.extractor.playlist.PlaylistInfoItemExtractor;
import org.schabi.newpipe.extractor.stream.Description;

public class BandcampPlaylistInfoItemExtractor implements PlaylistInfoItemExtractor {
    public final Element a;
    public final Element b;

    public BandcampPlaylistInfoItemExtractor(Element element) {
        this.a = element;
        this.b = element.getElementsByClass("result-info").first();
    }

    public final /* synthetic */ Description getDescription() {
        return y8.a(this);
    }

    public String getName() {
        return this.b.getElementsByClass("heading").text();
    }

    public final /* synthetic */ PlaylistInfo.PlaylistType getPlaylistType() {
        return y8.b(this);
    }

    public long getStreamCount() {
        return (long) Integer.parseInt(this.b.getElementsByClass("length").text().split(" track")[0]);
    }

    public List<Image> getThumbnails() {
        return BandcampExtractorHelper.getImagesFromSearchResult(this.a);
    }

    public String getUploaderName() {
        return this.b.getElementsByClass("subhead").text().split(" by")[0];
    }

    public String getUploaderUrl() {
        return null;
    }

    public String getUrl() {
        return this.b.getElementsByClass("itemurl").text();
    }

    public boolean isUploaderVerified() {
        return false;
    }
}
