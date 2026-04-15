package org.schabi.newpipe.extractor.services.peertube.extractors;

import com.grack.nanojson.JsonObject;
import java.util.List;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.playlist.PlaylistInfo;
import org.schabi.newpipe.extractor.playlist.PlaylistInfoItemExtractor;
import org.schabi.newpipe.extractor.services.peertube.PeertubeParsingHelper;
import org.schabi.newpipe.extractor.stream.Description;
import org.schabi.newpipe.extractor.utils.Utils;

public class PeertubePlaylistInfoItemExtractor implements PlaylistInfoItemExtractor {
    public final JsonObject a;
    public final JsonObject b;
    public final String c;

    public PeertubePlaylistInfoItemExtractor(JsonObject jsonObject, String str) {
        this.a = jsonObject;
        this.b = jsonObject.getObject("uploader");
        this.c = str;
    }

    public Description getDescription() throws ParsingException {
        String string = this.a.getString("description");
        if (Utils.isNullOrEmpty(string)) {
            return Description.g;
        }
        return new Description(string, 3);
    }

    public String getName() throws ParsingException {
        return this.a.getString("displayName");
    }

    public final /* synthetic */ PlaylistInfo.PlaylistType getPlaylistType() {
        return y8.b(this);
    }

    public long getStreamCount() throws ParsingException {
        return (long) this.a.getInt("videosLength");
    }

    public List<Image> getThumbnails() throws ParsingException {
        return PeertubeParsingHelper.getThumbnailsFromPlaylistOrVideoItem(this.c, this.a);
    }

    public String getUploaderName() throws ParsingException {
        return this.b.getString("displayName");
    }

    public String getUploaderUrl() throws ParsingException {
        return this.b.getString("url");
    }

    public String getUrl() throws ParsingException {
        return this.a.getString("url");
    }

    public boolean isUploaderVerified() throws ParsingException {
        return false;
    }
}
