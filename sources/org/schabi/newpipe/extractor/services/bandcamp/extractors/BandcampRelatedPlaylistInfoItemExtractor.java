package org.schabi.newpipe.extractor.services.bandcamp.extractors;

import java.util.List;
import org.jsoup.nodes.Element;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.playlist.PlaylistInfo;
import org.schabi.newpipe.extractor.playlist.PlaylistInfoItemExtractor;
import org.schabi.newpipe.extractor.stream.Description;

public class BandcampRelatedPlaylistInfoItemExtractor implements PlaylistInfoItemExtractor {
    public final Element a;

    public BandcampRelatedPlaylistInfoItemExtractor(Element element) {
        this.a = element;
    }

    public final /* synthetic */ Description getDescription() {
        return y8.a(this);
    }

    public String getName() throws ParsingException {
        return this.a.getElementsByClass("release-title").text();
    }

    public final /* synthetic */ PlaylistInfo.PlaylistType getPlaylistType() {
        return y8.b(this);
    }

    public long getStreamCount() throws ParsingException {
        return -1;
    }

    public List<Image> getThumbnails() throws ParsingException {
        return BandcampExtractorHelper.getImagesFromImageUrl(this.a.getElementsByClass("album-art").attr("src"));
    }

    public String getUploaderName() throws ParsingException {
        return this.a.getElementsByClass("by-artist").text().replace("by ", "");
    }

    public String getUploaderUrl() throws ParsingException {
        return null;
    }

    public String getUrl() throws ParsingException {
        return this.a.getElementsByClass("album-link").attr("abs:href");
    }

    public boolean isUploaderVerified() throws ParsingException {
        return false;
    }
}
