package org.schabi.newpipe.extractor.services.soundcloud.extractors;

import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonParser;
import com.grack.nanojson.JsonParserException;
import j$.util.Objects;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.channel.ChannelExtractor;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.schabi.newpipe.extractor.services.soundcloud.SoundcloudParsingHelper;
import org.schabi.newpipe.extractor.services.soundcloud.linkHandler.SoundcloudChannelTabLinkHandlerFactory;

public class SoundcloudChannelExtractor extends ChannelExtractor {
    public String g;
    public JsonObject h;

    public SoundcloudChannelExtractor(StreamingService streamingService, ListLinkHandler listLinkHandler) {
        super(streamingService, listLinkHandler);
    }

    public List<Image> getAvatars() {
        return SoundcloudParsingHelper.getAllImagesFromArtworkOrAvatarUrl(this.h.getString("avatar_url"));
    }

    public List<Image> getBanners() {
        return SoundcloudParsingHelper.getAllImagesFromVisualUrl(this.h.getObject("visuals").getArray("visuals").getObject(0).getString("visual_url"));
    }

    public String getDescription() {
        return this.h.getString("description", "");
    }

    public String getFeedUrl() {
        return null;
    }

    public String getId() {
        return this.g;
    }

    public String getName() {
        return this.h.getString("username");
    }

    public List<Image> getParentChannelAvatars() {
        return Collections.emptyList();
    }

    public String getParentChannelName() {
        return "";
    }

    public String getParentChannelUrl() {
        return "";
    }

    public long getSubscriberCount() {
        return this.h.getLong("followers_count", 0);
    }

    public List<ListLinkHandler> getTabs() throws ParsingException {
        String url = getUrl();
        StringBuilder m = y2.m(url);
        m.append(SoundcloudChannelTabLinkHandlerFactory.getUrlSuffix("tracks"));
        String sb = m.toString();
        StringBuilder m2 = y2.m(url);
        m2.append(SoundcloudChannelTabLinkHandlerFactory.getUrlSuffix("playlists"));
        String sb2 = m2.toString();
        StringBuilder m3 = y2.m(url);
        m3.append(SoundcloudChannelTabLinkHandlerFactory.getUrlSuffix("albums"));
        String sb3 = m3.toString();
        StringBuilder m4 = y2.m(url);
        m4.append(SoundcloudChannelTabLinkHandlerFactory.getUrlSuffix("likes"));
        String sb4 = m4.toString();
        String id = getId();
        ArrayList arrayList = new ArrayList(1);
        Object obj = new Object[]{"tracks"}[0];
        ListLinkHandler listLinkHandler = new ListLinkHandler(sb, sb, id, y2.p(obj, arrayList, obj, arrayList), "");
        ArrayList arrayList2 = new ArrayList(1);
        Object obj2 = new Object[]{"playlists"}[0];
        String str = id;
        ListLinkHandler listLinkHandler2 = new ListLinkHandler(sb2, sb2, str, y2.p(obj2, arrayList2, obj2, arrayList2), "");
        ArrayList arrayList3 = new ArrayList(1);
        Object obj3 = new Object[]{"albums"}[0];
        ListLinkHandler listLinkHandler3 = new ListLinkHandler(sb3, sb3, str, y2.p(obj3, arrayList3, obj3, arrayList3), "");
        ArrayList arrayList4 = new ArrayList(1);
        Object obj4 = new Object[]{"likes"}[0];
        Object[] objArr = {listLinkHandler, listLinkHandler2, listLinkHandler3, new ListLinkHandler(sb4, sb4, str, y2.p(obj4, arrayList4, obj4, arrayList4), "")};
        ArrayList arrayList5 = new ArrayList(4);
        for (int i = 0; i < 4; i++) {
            Object obj5 = objArr[i];
            Objects.requireNonNull(obj5);
            arrayList5.add(obj5);
        }
        return Collections.unmodifiableList(arrayList5);
    }

    public boolean isVerified() throws ParsingException {
        return this.h.getBoolean("verified");
    }

    public void onFetchPage(Downloader downloader) throws IOException, ExtractionException {
        this.g = getLinkHandler().getId();
        try {
            this.h = JsonParser.object().from(downloader.get("https://api-v2.soundcloud.com/users/" + this.g + "?client_id=" + SoundcloudParsingHelper.clientId(), getExtractorLocalization()).responseBody());
        } catch (JsonParserException e) {
            throw new ParsingException("Could not parse json response", e);
        }
    }
}
