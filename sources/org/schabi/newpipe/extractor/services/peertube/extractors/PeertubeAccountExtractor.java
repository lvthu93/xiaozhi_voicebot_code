package org.schabi.newpipe.extractor.services.peertube.extractors;

import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonParser;
import com.grack.nanojson.JsonParserException;
import j$.util.Objects;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.channel.ChannelExtractor;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.downloader.Response;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.exceptions.ReCaptchaException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.schabi.newpipe.extractor.services.peertube.PeertubeParsingHelper;
import org.schabi.newpipe.extractor.services.peertube.linkHandler.PeertubeChannelTabLinkHandlerFactory;
import org.schabi.newpipe.extractor.utils.JsonUtils;

public class PeertubeAccountExtractor extends ChannelExtractor {
    public JsonObject g;
    public final String h = getBaseUrl();

    public PeertubeAccountExtractor(StreamingService streamingService, ListLinkHandler listLinkHandler) throws ParsingException {
        super(streamingService, listLinkHandler);
    }

    public List<Image> getAvatars() {
        return PeertubeParsingHelper.getAvatarsFromOwnerAccountOrVideoChannelObject(this.h, this.g);
    }

    public List<Image> getBanners() {
        return PeertubeParsingHelper.getBannersFromAccountOrVideoChannelObject(this.h, this.g);
    }

    public String getDescription() {
        return this.g.getString("description");
    }

    public String getFeedUrl() throws ParsingException {
        return getBaseUrl() + "/feeds/videos.xml?accountId=" + this.g.get("id");
    }

    public String getName() throws ParsingException {
        return JsonUtils.getString(this.g, "displayName");
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

    public long getSubscriberCount() throws ParsingException {
        String str;
        long j = this.g.getLong("followersCount");
        String k = y2.k(new StringBuilder(), this.h, "/api/v1/");
        if (getId().contains("accounts/")) {
            StringBuilder m = y2.m(k);
            m.append(getId());
            str = m.toString();
        } else {
            StringBuilder o = y2.o(k, "accounts/");
            o.append(getId());
            str = o.toString();
        }
        try {
            Iterator it = JsonParser.object().from(getDownloader().get(y2.x(str, "/video-channels")).responseBody()).getArray("data").iterator();
            while (it.hasNext()) {
                j += (long) ((JsonObject) it.next()).getInt("followersCount");
            }
        } catch (JsonParserException | IOException | ReCaptchaException unused) {
        }
        return j;
    }

    public List<ListLinkHandler> getTabs() throws ParsingException {
        PeertubeChannelTabLinkHandlerFactory instance = PeertubeChannelTabLinkHandlerFactory.getInstance();
        String id = getId();
        ArrayList arrayList = new ArrayList(1);
        Object obj = new Object[]{"videos"}[0];
        ListLinkHandler fromQuery = instance.fromQuery(id, y2.p(obj, arrayList, obj, arrayList), "", getBaseUrl());
        PeertubeChannelTabLinkHandlerFactory instance2 = PeertubeChannelTabLinkHandlerFactory.getInstance();
        String id2 = getId();
        ArrayList arrayList2 = new ArrayList(1);
        Object obj2 = new Object[]{"channels"}[0];
        Object[] objArr = {fromQuery, instance2.fromQuery(id2, y2.p(obj2, arrayList2, obj2, arrayList2), "", getBaseUrl())};
        ArrayList arrayList3 = new ArrayList(2);
        for (int i = 0; i < 2; i++) {
            Object obj3 = objArr[i];
            Objects.requireNonNull(obj3);
            arrayList3.add(obj3);
        }
        return Collections.unmodifiableList(arrayList3);
    }

    public boolean isVerified() throws ParsingException {
        return false;
    }

    public void onFetchPage(Downloader downloader) throws IOException, ExtractionException {
        Response response = downloader.get(this.h + "/api/v1/" + getId());
        if (response != null) {
            try {
                JsonObject from = JsonParser.object().from(response.responseBody());
                this.g = from;
                if (from == null) {
                    throw new ExtractionException("Unable to extract PeerTube account data");
                }
            } catch (JsonParserException e) {
                throw new ExtractionException("Unable to extract PeerTube account data", e);
            }
        } else {
            throw new ExtractionException("Unable to extract PeerTube account data");
        }
    }
}
