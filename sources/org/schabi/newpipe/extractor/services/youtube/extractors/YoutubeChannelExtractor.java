package org.schabi.newpipe.extractor.services.youtube.extractors;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonObject;
import j$.util.Collection$EL;
import j$.util.Optional;
import j$.util.stream.Collectors;
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
import org.schabi.newpipe.extractor.services.youtube.YoutubeChannelHelper;
import org.schabi.newpipe.extractor.services.youtube.YoutubeParsingHelper;
import org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeChannelLinkHandlerFactory;
import org.schabi.newpipe.extractor.utils.Utils;

public class YoutubeChannelExtractor extends ChannelExtractor {
    public JsonObject g;
    public YoutubeChannelHelper.ChannelHeader h;
    public String i;
    public JsonObject j;

    public YoutubeChannelExtractor(StreamingService streamingService, ListLinkHandler listLinkHandler) {
        super(streamingService, listLinkHandler);
    }

    public List<Image> getAvatars() throws ParsingException {
        a();
        JsonObject jsonObject = this.j;
        if (jsonObject != null) {
            return (List) Optional.ofNullable(jsonObject.getObject("avatar").getArray("thumbnails")).map(new p8(17)).orElseThrow(new cb(5));
        }
        return (List) Optional.ofNullable(this.h).map(new p8(18)).map(new p8(19)).orElseThrow(new cb(6));
    }

    public List<Image> getBanners() {
        a();
        if (this.j != null) {
            return Collections.emptyList();
        }
        return (List) Optional.ofNullable(this.h).map(new p8(15)).map(new p8(16)).orElse(Collections.emptyList());
    }

    public String getDescription() throws ParsingException {
        a();
        if (this.j != null) {
            return null;
        }
        try {
            YoutubeChannelHelper.ChannelHeader channelHeader = this.h;
            if (channelHeader != null) {
                if (channelHeader.f == YoutubeChannelHelper.ChannelHeader.HeaderType.INTERACTIVE_TABBED) {
                    return YoutubeParsingHelper.getTextFromObject(channelHeader.c.getObject("description"));
                }
            }
            return this.g.getObject("metadata").getObject("channelMetadataRenderer").getString("description");
        } catch (Exception e) {
            throw new ParsingException("Could not get channel description", e);
        }
    }

    public String getFeedUrl() throws ParsingException {
        try {
            return YoutubeParsingHelper.getFeedUrlFrom(getId());
        } catch (Exception e) {
            throw new ParsingException("Could not get feed URL", e);
        }
    }

    public String getId() throws ParsingException {
        a();
        return YoutubeChannelHelper.getChannelId(this.h, this.g, this.i);
    }

    public String getName() throws ParsingException {
        a();
        return YoutubeChannelHelper.getChannelName(this.h, this.j, this.g);
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
        YoutubeChannelHelper.ChannelHeader channelHeader;
        YoutubeChannelHelper.ChannelHeader.HeaderType headerType;
        JsonObject jsonObject;
        a();
        if (this.j != null || (channelHeader = this.h) == null || (headerType = channelHeader.f) == YoutubeChannelHelper.ChannelHeader.HeaderType.INTERACTIVE_TABBED) {
            return -1;
        }
        JsonObject jsonObject2 = channelHeader.c;
        if (headerType == YoutubeChannelHelper.ChannelHeader.HeaderType.PAGE) {
            JsonObject object = jsonObject2.getObject("content").getObject("pageHeaderViewModel").getObject("metadata");
            if (!object.has("contentMetadataViewModel")) {
                return -1;
            }
            JsonArray array = object.getObject("contentMetadataViewModel").getArray("metadataRows");
            JsonArray array2 = array.getObject(Math.max(0, array.size() - 1)).getArray("metadataParts");
            if (array2.size() < 2) {
                return -1;
            }
            try {
                return Utils.mixedNumberWordToLong(array2.getObject(0).getObject("text").getString("content"));
            } catch (NumberFormatException e) {
                throw new ParsingException("Could not get subscriber count", e);
            }
        } else {
            if (jsonObject2.has("subscriberCountText")) {
                jsonObject = jsonObject2.getObject("subscriberCountText");
            } else if (jsonObject2.has("subtitle")) {
                jsonObject = jsonObject2.getObject("subtitle");
            } else {
                jsonObject = null;
            }
            if (jsonObject != null) {
                try {
                    return Utils.mixedNumberWordToLong(YoutubeParsingHelper.getTextFromObject(jsonObject));
                } catch (NumberFormatException e2) {
                    throw new ParsingException("Could not get subscriber count", e2);
                }
            }
            return -1;
        }
    }

    public List<ListLinkHandler> getTabs() throws ParsingException {
        a();
        if (this.j == null) {
            JsonArray array = this.g.getObject("contents").getObject("twoColumnBrowseResultsRenderer").getArray("tabs");
            ArrayList arrayList = new ArrayList();
            hb hbVar = new hb(2, this, arrayList);
            String name = getName();
            String url = getUrl();
            Class<JsonObject> cls = JsonObject.class;
            y2.z(cls, 26, y2.s(cls, 26, Collection$EL.stream(array))).filter(new bz(15)).map(new p8(20)).forEach(new nf(this, arrayList, name, getId(), url, hbVar));
            return Collections.unmodifiableList(arrayList);
        }
        ArrayList arrayList2 = new ArrayList();
        of ofVar = new of(this, arrayList2, getUrl());
        ofVar.accept("videos");
        ofVar.accept("shorts");
        ofVar.accept("livestreams");
        return Collections.unmodifiableList(arrayList2);
    }

    public List<String> getTags() throws ParsingException {
        a();
        if (this.j != null) {
            return Collections.emptyList();
        }
        Class<String> cls = String.class;
        return (List) y2.z(cls, 25, y2.s(cls, 25, Collection$EL.stream(this.g.getObject("microformat").getObject("microformatDataRenderer").getArray("tags")))).collect(Collectors.toUnmodifiableList());
    }

    public String getUrl() throws ParsingException {
        try {
            YoutubeChannelLinkHandlerFactory instance = YoutubeChannelLinkHandlerFactory.getInstance();
            return instance.getUrl("channel/" + getId());
        } catch (ParsingException unused) {
            return super.getUrl();
        }
    }

    public boolean isVerified() throws ParsingException {
        a();
        if (this.j != null) {
            return false;
        }
        YoutubeChannelHelper.ChannelHeader channelHeader = this.h;
        if (channelHeader != null) {
            return YoutubeChannelHelper.isChannelVerified(channelHeader);
        }
        throw new ParsingException("Could not get channel verified status, no channel header has been extracted");
    }

    public void onFetchPage(Downloader downloader) throws IOException, ExtractionException {
        YoutubeChannelHelper.ChannelResponseData channelResponse = YoutubeChannelHelper.getChannelResponse(YoutubeChannelHelper.resolveChannelId(super.getId()), "EgZ2aWRlb3PyBgQKAjoA", getExtractorLocalization(), getExtractorContentCountry());
        JsonObject jsonObject = channelResponse.a;
        this.g = jsonObject;
        this.h = YoutubeChannelHelper.getChannelHeader(jsonObject);
        this.i = channelResponse.b;
        this.j = YoutubeChannelHelper.getChannelAgeGateRenderer(this.g);
    }
}
