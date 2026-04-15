package org.schabi.newpipe.extractor.services.youtube.extractors;

import com.grack.nanojson.JsonObject;
import j$.util.Collection$EL;
import java.util.List;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.playlist.PlaylistInfo;
import org.schabi.newpipe.extractor.playlist.PlaylistInfoItemExtractor;
import org.schabi.newpipe.extractor.services.youtube.YoutubeParsingHelper;
import org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubePlaylistLinkHandlerFactory;
import org.schabi.newpipe.extractor.stream.Description;
import org.schabi.newpipe.extractor.utils.Utils;

public class YoutubeMixOrPlaylistLockupInfoItemExtractor implements PlaylistInfoItemExtractor {
    public final JsonObject a;
    public final JsonObject b;
    public final JsonObject c;
    public final JsonObject d;
    public final PlaylistInfo.PlaylistType e;

    public YoutubeMixOrPlaylistLockupInfoItemExtractor(JsonObject jsonObject) {
        this.a = jsonObject;
        this.b = jsonObject.getObject("contentImage").getObject("collectionThumbnailViewModel").getObject("primaryThumbnail").getObject("thumbnailViewModel");
        JsonObject object = jsonObject.getObject("metadata").getObject("lockupMetadataViewModel");
        this.c = object;
        this.d = object.getObject("metadata").getObject("contentMetadataViewModel").getArray("metadataRows").getObject(0);
        try {
            this.e = YoutubeParsingHelper.extractPlaylistTypeFromPlaylistId(a());
        } catch (ParsingException unused) {
            this.e = PlaylistInfo.PlaylistType.NORMAL;
        }
    }

    public final String a() throws ParsingException {
        JsonObject jsonObject = this.a;
        String string = jsonObject.getString("contentId");
        if (Utils.isNullOrEmpty(string)) {
            string = jsonObject.getObject("rendererContext").getObject("commandContext").getObject("watchEndpoint").getString("playlistId");
        }
        if (!Utils.isNullOrEmpty(string)) {
            return string;
        }
        throw new ParsingException("Could not get playlist ID");
    }

    public final /* synthetic */ Description getDescription() {
        return y8.a(this);
    }

    public String getName() throws ParsingException {
        return this.c.getObject("title").getString("content");
    }

    public PlaylistInfo.PlaylistType getPlaylistType() throws ParsingException {
        return this.e;
    }

    public long getStreamCount() throws ParsingException {
        Class<JsonObject> cls = JsonObject.class;
        if (this.e != PlaylistInfo.PlaylistType.NORMAL) {
            return -2;
        }
        try {
            return Long.parseLong(Utils.removeNonDigitCharacters(((JsonObject) Collection$EL.stream(((JsonObject) Collection$EL.stream(this.b.getArray("overlays")).filter(new uf(cls, 3)).map(new vf(cls, 3)).filter(new bz(21)).findFirst().orElseThrow(new cb(9))).getObject("thumbnailOverlayBadgeViewModel").getArray("thumbnailBadges")).filter(new uf(cls, 4)).map(new vf(cls, 4)).filter(new bz(22)).findFirst().orElseThrow(new cb(10))).getObject("thumbnailBadgeViewModel").getString("text")));
        } catch (Exception e2) {
            throw new ParsingException("Could not get playlist stream count", e2);
        }
    }

    public List<Image> getThumbnails() throws ParsingException {
        return YoutubeParsingHelper.getImagesFromThumbnailsArray(this.b.getObject("image").getArray("sources"));
    }

    public String getUploaderName() throws ParsingException {
        return this.d.getArray("metadataParts").getObject(0).getObject("text").getString("content");
    }

    public String getUploaderUrl() throws ParsingException {
        if (this.e != PlaylistInfo.PlaylistType.NORMAL) {
            return null;
        }
        return YoutubeParsingHelper.getUrlFromNavigationEndpoint(this.d.getArray("metadataParts").getObject(0).getObject("text").getArray("commandRuns").getObject(0).getObject("onTap").getObject("innertubeCommand"));
    }

    public String getUrl() throws ParsingException {
        if (this.e == PlaylistInfo.PlaylistType.NORMAL) {
            try {
                return YoutubePlaylistLinkHandlerFactory.getInstance().getUrl(a());
            } catch (Exception unused) {
            }
        }
        return YoutubeParsingHelper.getUrlFromNavigationEndpoint(this.a.getObject("rendererContext").getObject("commandContext").getObject("onTap").getObject("innertubeCommand"));
    }

    public boolean isUploaderVerified() throws ParsingException {
        if (this.e != PlaylistInfo.PlaylistType.NORMAL) {
            return false;
        }
        return YoutubeParsingHelper.hasArtistOrVerifiedIconBadgeAttachment(this.d.getArray("metadataParts").getObject(0).getObject("text").getArray("attachmentRuns"));
    }
}
