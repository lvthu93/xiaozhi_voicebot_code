package org.schabi.newpipe.extractor.services.youtube.extractors;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonObject;
import java.util.Iterator;
import java.util.List;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.playlist.PlaylistInfo;
import org.schabi.newpipe.extractor.playlist.PlaylistInfoItemExtractor;
import org.schabi.newpipe.extractor.services.youtube.YoutubeParsingHelper;
import org.schabi.newpipe.extractor.stream.Description;
import org.schabi.newpipe.extractor.utils.Utils;

public class YoutubeMusicAlbumOrPlaylistInfoItemExtractor implements PlaylistInfoItemExtractor {
    public final JsonObject a;
    public final JsonObject b;

    public YoutubeMusicAlbumOrPlaylistInfoItemExtractor(JsonObject jsonObject, JsonArray jsonArray, String str) {
        int i;
        this.a = jsonObject;
        if ("music_albums".equals(str)) {
            i = 2;
        } else {
            i = 0;
        }
        this.b = jsonArray.getObject(i);
    }

    public final /* synthetic */ Description getDescription() {
        return y8.a(this);
    }

    public String getName() throws ParsingException {
        String textFromObject = YoutubeParsingHelper.getTextFromObject(this.a.getArray("flexColumns").getObject(0).getObject("musicResponsiveListItemFlexColumnRenderer").getObject("text"));
        if (!Utils.isNullOrEmpty(textFromObject)) {
            return textFromObject;
        }
        throw new ParsingException("Could not get name");
    }

    public final /* synthetic */ PlaylistInfo.PlaylistType getPlaylistType() {
        return y8.b(this);
    }

    public long getStreamCount() throws ParsingException {
        return -1;
    }

    public List<Image> getThumbnails() throws ParsingException {
        try {
            return YoutubeParsingHelper.getImagesFromThumbnailsArray(this.a.getObject("thumbnail").getObject("musicThumbnailRenderer").getObject("thumbnail").getArray("thumbnails"));
        } catch (Exception e) {
            throw new ParsingException("Could not get thumbnails", e);
        }
    }

    public String getUploaderName() throws ParsingException {
        String string = this.b.getString("text");
        if (!Utils.isNullOrEmpty(string)) {
            return string;
        }
        throw new ParsingException("Could not get uploader name");
    }

    public String getUploaderUrl() throws ParsingException {
        Iterator it = this.a.getObject("menu").getObject("menuRenderer").getArray("items").iterator();
        while (it.hasNext()) {
            JsonObject object = ((JsonObject) it.next()).getObject("menuNavigationItemRenderer");
            if (object.getObject("icon").getString("iconType", "").equals("ARTIST")) {
                return YoutubeParsingHelper.getUrlFromNavigationEndpoint(object.getObject("navigationEndpoint"));
            }
        }
        JsonObject jsonObject = this.b;
        if (!jsonObject.has("navigationEndpoint")) {
            return null;
        }
        return YoutubeParsingHelper.getUrlFromNavigationEndpoint(jsonObject.getObject("navigationEndpoint"));
    }

    public String getUrl() throws ParsingException {
        JsonObject jsonObject = this.a;
        String string = jsonObject.getObject("menu").getObject("menuRenderer").getArray("items").getObject(4).getObject("toggleMenuServiceItemRenderer").getObject("toggledServiceEndpoint").getObject("likeEndpoint").getObject("target").getString("playlistId");
        if (Utils.isNullOrEmpty(string)) {
            string = jsonObject.getObject("overlay").getObject("musicItemThumbnailOverlayRenderer").getObject("content").getObject("musicPlayButtonRenderer").getObject("playNavigationEndpoint").getObject("watchPlaylistEndpoint").getString("playlistId");
        }
        if (!Utils.isNullOrEmpty(string)) {
            return y2.i("https://music.youtube.com/playlist?list=", string);
        }
        throw new ParsingException("Could not get URL");
    }

    public boolean isUploaderVerified() throws ParsingException {
        return false;
    }
}
