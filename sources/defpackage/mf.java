package defpackage;

import com.grack.nanojson.JsonObject;
import java.util.List;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.playlist.PlaylistInfo;
import org.schabi.newpipe.extractor.playlist.PlaylistInfoItemExtractor;
import org.schabi.newpipe.extractor.services.youtube.YoutubeParsingHelper;
import org.schabi.newpipe.extractor.stream.Description;
import org.schabi.newpipe.extractor.utils.Utils;

/* renamed from: mf  reason: default package */
public abstract class mf implements PlaylistInfoItemExtractor {
    public final JsonObject a;

    public mf(JsonObject jsonObject) {
        this.a = jsonObject;
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

    public long getStreamCount() throws ParsingException {
        String textFromObject = YoutubeParsingHelper.getTextFromObject(this.a.getObject("thumbnailOverlays").getObject("thumbnailOverlayBottomPanelRenderer").getObject("text"));
        if (textFromObject != null) {
            try {
                return Long.parseLong(Utils.removeNonDigitCharacters(textFromObject));
            } catch (NumberFormatException e) {
                throw new ParsingException("Could not convert stream count to a long", e);
            }
        } else {
            throw new ParsingException("Could not get stream count");
        }
    }

    public List<Image> getThumbnails() throws ParsingException {
        return YoutubeParsingHelper.getThumbnailsFromInfoItem(this.a.getObject("thumbnailRenderer").getObject("showCustomThumbnailRenderer"));
    }

    public String getUrl() throws ParsingException {
        return YoutubeParsingHelper.getUrlFromNavigationEndpoint(this.a.getObject("navigationEndpoint"));
    }
}
