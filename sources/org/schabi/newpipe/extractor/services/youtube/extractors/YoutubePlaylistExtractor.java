package org.schabi.newpipe.extractor.services.youtube.extractors;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonBuilder;
import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonWriter;
import j$.util.Base64;
import j$.util.Collection$EL;
import j$.util.Objects;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.ListExtractor;
import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.schabi.newpipe.extractor.localization.Localization;
import org.schabi.newpipe.extractor.playlist.PlaylistExtractor;
import org.schabi.newpipe.extractor.playlist.PlaylistInfo;
import org.schabi.newpipe.extractor.services.youtube.YoutubeParsingHelper;
import org.schabi.newpipe.extractor.services.youtube.protos.playlist.PlaylistProtobufContinuation;
import org.schabi.newpipe.extractor.stream.Description;
import org.schabi.newpipe.extractor.stream.StreamInfoItem;
import org.schabi.newpipe.extractor.stream.StreamInfoItemsCollector;
import org.schabi.newpipe.extractor.utils.Utils;

public class YoutubePlaylistExtractor extends PlaylistExtractor {
    public JsonObject g;
    public JsonObject h;
    public JsonObject i;
    public JsonObject j;
    public JsonObject k;
    public boolean l;

    public YoutubePlaylistExtractor(StreamingService streamingService, ListLinkHandler listLinkHandler) {
        super(streamingService, listLinkHandler);
    }

    public final void b(StreamInfoItemsCollector streamInfoItemsCollector, JsonArray jsonArray) {
        Class<JsonObject> cls = JsonObject.class;
        y2.ab(cls, 10, y2.aa(cls, 10, Collection$EL.stream(jsonArray))).forEach(new gg(streamInfoItemsCollector, getTimeAgoParser(), 0));
    }

    public final Page c(JsonArray jsonArray) throws IOException, ExtractionException {
        if (Utils.isNullOrEmpty((Collection<?>) jsonArray)) {
            return null;
        }
        JsonObject object = jsonArray.getObject(jsonArray.size() - 1);
        if (!object.has("continuationItemRenderer")) {
            return null;
        }
        JsonObject object2 = object.getObject("continuationItemRenderer").getObject("continuationEndpoint");
        if (object2.has("commandExecutorCommand")) {
            Class<JsonObject> cls = JsonObject.class;
            object2 = (JsonObject) y2.ab(cls, 11, y2.aa(cls, 11, Collection$EL.stream(object2.getObject("commandExecutorCommand").getArray("commands")))).filter(new bz(29)).findFirst().orElse(new JsonObject());
        }
        String string = object2.getObject("continuationCommand").getString("token");
        if (Utils.isNullOrEmpty(string)) {
            return null;
        }
        return new Page("https://www.youtube.com/youtubei/v1/browse?prettyPrint=false", JsonWriter.string(YoutubeParsingHelper.prepareDesktopJsonBuilder(getExtractorLocalization(), getExtractorContentCountry()).value("continuation", string).done()).getBytes(StandardCharsets.UTF_8));
    }

    public final JsonObject d() {
        if (this.k == null) {
            this.k = this.g.getObject("header").getObject("playlistHeaderRenderer");
        }
        return this.k;
    }

    public final JsonObject e() throws ParsingException {
        if (this.i == null) {
            Class<JsonObject> cls = JsonObject.class;
            this.i = (JsonObject) y2.ab(cls, 8, y2.aa(cls, 8, Collection$EL.stream(this.g.getObject("sidebar").getObject("playlistSidebarRenderer").getArray("items")))).filter(new bz(27)).map(new fg(0)).findFirst().orElseThrow(new cb(11));
        }
        return this.i;
    }

    public final JsonObject f() throws ParsingException {
        if (this.j == null) {
            Class<JsonObject> cls = JsonObject.class;
            this.j = (JsonObject) y2.ab(cls, 9, y2.aa(cls, 9, Collection$EL.stream(this.g.getObject("sidebar").getObject("playlistSidebarRenderer").getArray("items")))).filter(new bz(28)).map(new fg(1)).findFirst().orElseThrow(new cb(12));
        }
        return this.j;
    }

    public Description getDescription() throws ParsingException {
        return new Description(YoutubeParsingHelper.getTextFromObject(e().getObject("description"), true), 1);
    }

    public ListExtractor.InfoItemsPage<StreamInfoItem> getInitialPage() throws IOException, ExtractionException {
        StreamInfoItemsCollector streamInfoItemsCollector = new StreamInfoItemsCollector(getServiceId());
        JsonArray array = this.h.getArray("onResponseReceivedActions").getObject(0).getObject("reloadContinuationItemsCommand").getArray("continuationItems");
        b(streamInfoItemsCollector, array);
        return new ListExtractor.InfoItemsPage<>(streamInfoItemsCollector, c(array));
    }

    public String getName() throws ParsingException {
        String textFromObject = YoutubeParsingHelper.getTextFromObject(e().getObject("title"));
        if (!Utils.isNullOrEmpty(textFromObject)) {
            return textFromObject;
        }
        return this.g.getObject("microformat").getObject("microformatDataRenderer").getString("title");
    }

    public ListExtractor.InfoItemsPage<StreamInfoItem> getPage(Page page) throws IOException, ExtractionException {
        if (page == null || Utils.isNullOrEmpty(page.getUrl())) {
            throw new IllegalArgumentException("Page doesn't contain an URL");
        }
        StreamInfoItemsCollector streamInfoItemsCollector = new StreamInfoItemsCollector(getServiceId());
        JsonArray array = YoutubeParsingHelper.getJsonPostResponse("browse", page.getBody(), getExtractorLocalization()).getArray("onResponseReceivedActions").getObject(0).getObject("appendContinuationItemsAction").getArray("continuationItems");
        b(streamInfoItemsCollector, array);
        return new ListExtractor.InfoItemsPage<>(streamInfoItemsCollector, c(array));
    }

    public PlaylistInfo.PlaylistType getPlaylistType() throws ParsingException {
        return YoutubeParsingHelper.extractPlaylistTypeFromPlaylistUrl(getUrl());
    }

    public long getStreamCount() throws ParsingException {
        JsonObject jsonObject;
        JsonObject jsonObject2;
        String textFromObject;
        String textFromObject2;
        if (this.l) {
            String textFromObject3 = YoutubeParsingHelper.getTextFromObject(d().getObject("numVideosText"));
            if (textFromObject3 != null) {
                try {
                    return Long.parseLong(Utils.removeNonDigitCharacters(textFromObject3));
                } catch (NumberFormatException unused) {
                }
            }
            String textFromObject4 = YoutubeParsingHelper.getTextFromObject(d().getArray("byline").getObject(0).getObject("text"));
            if (textFromObject4 != null) {
                try {
                    return Long.parseLong(Utils.removeNonDigitCharacters(textFromObject4));
                } catch (NumberFormatException unused2) {
                }
            }
        }
        if (this.l) {
            jsonObject = d();
        } else {
            jsonObject = e();
        }
        JsonArray array = jsonObject.getArray("briefStats");
        if (!array.isEmpty() && (textFromObject2 = YoutubeParsingHelper.getTextFromObject(array.getObject(0))) != null) {
            return Long.parseLong(Utils.removeNonDigitCharacters(textFromObject2));
        }
        if (this.l) {
            jsonObject2 = d();
        } else {
            jsonObject2 = e();
        }
        JsonArray array2 = jsonObject2.getArray("stats");
        if (array2.isEmpty() || (textFromObject = YoutubeParsingHelper.getTextFromObject(array2.getObject(0))) == null) {
            return -1;
        }
        return Long.parseLong(Utils.removeNonDigitCharacters(textFromObject));
    }

    public List<Image> getThumbnails() throws ParsingException {
        JsonArray jsonArray;
        if (this.l) {
            jsonArray = d().getObject("playlistHeaderBanner").getObject("heroPlaylistThumbnailRenderer").getObject("thumbnail").getArray("thumbnails");
        } else {
            jsonArray = this.i.getObject("thumbnailRenderer").getObject("playlistVideoThumbnailRenderer").getObject("thumbnail").getArray("thumbnails");
        }
        if (!Utils.isNullOrEmpty((Collection<?>) jsonArray)) {
            return YoutubeParsingHelper.getImagesFromThumbnailsArray(jsonArray);
        }
        JsonArray array = this.g.getObject("microformat").getObject("microformatDataRenderer").getObject("thumbnail").getArray("thumbnails");
        if (!Utils.isNullOrEmpty((Collection<?>) array)) {
            return YoutubeParsingHelper.getImagesFromThumbnailsArray(array);
        }
        throw new ParsingException("Could not get playlist thumbnails");
    }

    public List<Image> getUploaderAvatars() throws ParsingException {
        if (this.l) {
            return Collections.emptyList();
        }
        try {
            return YoutubeParsingHelper.getImagesFromThumbnailsArray(f().getObject("thumbnail").getArray("thumbnails"));
        } catch (Exception e) {
            throw new ParsingException("Could not get playlist uploader avatars", e);
        }
    }

    public String getUploaderName() throws ParsingException {
        JsonObject jsonObject;
        try {
            if (this.l) {
                jsonObject = d().getObject("ownerText");
            } else {
                jsonObject = f().getObject("title");
            }
            return YoutubeParsingHelper.getTextFromObject(jsonObject);
        } catch (Exception e) {
            throw new ParsingException("Could not get playlist uploader name", e);
        }
    }

    public String getUploaderUrl() throws ParsingException {
        JsonObject jsonObject;
        try {
            if (this.l) {
                jsonObject = d().getObject("ownerText").getArray("runs").getObject(0).getObject("navigationEndpoint");
            } else {
                jsonObject = f().getObject("navigationEndpoint");
            }
            return YoutubeParsingHelper.getUrlFromNavigationEndpoint(jsonObject);
        } catch (Exception e) {
            throw new ParsingException("Could not get playlist uploader url", e);
        }
    }

    public boolean isUploaderVerified() throws ParsingException {
        return false;
    }

    public void onFetchPage(Downloader downloader) throws IOException, ExtractionException {
        String id = getId();
        Localization extractorLocalization = getExtractorLocalization();
        JsonBuilder<JsonObject> prepareDesktopJsonBuilder = YoutubeParsingHelper.prepareDesktopJsonBuilder(extractorLocalization, getExtractorContentCountry());
        byte[] bytes = JsonWriter.string(prepareDesktopJsonBuilder.value("browseId", "VL" + id).value("params", "wgYCCAA%3D").done()).getBytes(StandardCharsets.UTF_8);
        boolean z = true;
        ArrayList arrayList = new ArrayList(1);
        Object obj = new Object[]{"$fields=sidebar,header,microformat,alerts"}[0];
        Objects.requireNonNull(obj);
        arrayList.add(obj);
        JsonObject jsonPostResponse = YoutubeParsingHelper.getJsonPostResponse("browse", Collections.unmodifiableList(arrayList), bytes, extractorLocalization);
        this.g = jsonPostResponse;
        YoutubeParsingHelper.defaultAlertsCheck(jsonPostResponse);
        if (!this.g.has("header") || this.g.has("sidebar")) {
            z = false;
        }
        this.l = z;
        PlaylistProtobufContinuation.PlaylistContinuation.Builder newBuilder = PlaylistProtobufContinuation.PlaylistContinuation.newBuilder();
        PlaylistProtobufContinuation.ContinuationParams.Builder newBuilder2 = PlaylistProtobufContinuation.ContinuationParams.newBuilder();
        this.h = YoutubeParsingHelper.getJsonPostResponse("browse", JsonWriter.string(YoutubeParsingHelper.prepareDesktopJsonBuilder(extractorLocalization, getExtractorContentCountry()).value("continuation", Utils.encodeUrlUtf8(Base64.getUrlEncoder().encodeToString(((PlaylistProtobufContinuation.PlaylistContinuation) newBuilder.setParameters((PlaylistProtobufContinuation.ContinuationParams) newBuilder2.setBrowseId("VL" + id).setPlaylistId(id).setContinuationProperties("CADCBgIIAA%3D%3D").build()).build()).toByteArray()))).done()).getBytes(StandardCharsets.UTF_8), extractorLocalization);
    }
}
