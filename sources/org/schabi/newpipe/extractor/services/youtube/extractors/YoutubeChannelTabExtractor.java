package org.schabi.newpipe.extractor.services.youtube.extractors;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonWriter;
import j$.util.Collection$EL;
import j$.util.Objects;
import j$.util.Optional;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.schabi.newpipe.extractor.InfoItem;
import org.schabi.newpipe.extractor.ListExtractor;
import org.schabi.newpipe.extractor.MultiInfoItemsCollector;
import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.channel.tabs.ChannelTabExtractor;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.schabi.newpipe.extractor.localization.TimeAgoParser;
import org.schabi.newpipe.extractor.services.youtube.YoutubeChannelHelper;
import org.schabi.newpipe.extractor.services.youtube.YoutubeParsingHelper;
import org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeChannelTabLinkHandlerFactory;
import org.schabi.newpipe.extractor.utils.Utils;

public class YoutubeChannelTabExtractor extends ChannelTabExtractor {
    public YoutubeChannelHelper.ChannelHeader g;
    public JsonObject h;
    public String i;

    public static final class VideosTabExtractor extends YoutubeChannelTabExtractor {
        public final JsonObject j;
        public final String k;
        public final String l;
        public final String m;

        public VideosTabExtractor(JsonObject jsonObject, String str, String str2, String str3, StreamingService streamingService, ListLinkHandler listLinkHandler, YoutubeChannelHelper.ChannelHeader channelHeader) {
            super(streamingService, listLinkHandler);
            this.g = channelHeader;
            this.j = jsonObject;
            this.k = str2;
            this.l = str;
            this.m = str3;
        }

        public final String d() {
            return this.l;
        }

        public final Optional<JsonObject> f() {
            return Optional.of(this.j);
        }

        public String getId() throws ParsingException {
            return this.k;
        }

        public String getUrl() throws ParsingException {
            return this.m;
        }

        public void onFetchPage(Downloader downloader) {
        }
    }

    public enum a {
        VERIFIED,
        UNVERIFIED,
        UNKNOWN
    }

    public static final class b extends mf {
        public final a b;
        public final String c;
        public final String d;

        public b(JsonObject jsonObject, a aVar, String str, String str2) {
            super(jsonObject);
            this.b = aVar;
            this.c = str;
            this.d = str2;
        }

        public String getUploaderName() {
            return this.c;
        }

        public String getUploaderUrl() {
            return this.d;
        }

        public boolean isUploaderVerified() throws ParsingException {
            int ordinal = this.b.ordinal();
            if (ordinal == 0) {
                return true;
            }
            if (ordinal == 1) {
                return false;
            }
            throw new ParsingException("Could not get uploader verification status");
        }
    }

    public YoutubeChannelTabExtractor(StreamingService streamingService, ListLinkHandler listLinkHandler) {
        super(streamingService, listLinkHandler);
    }

    public final Optional b(JsonObject jsonObject, String str, String str2, MultiInfoItemsCollector multiInfoItemsCollector, a aVar) {
        TimeAgoParser timeAgoParser = getTimeAgoParser();
        if (jsonObject.has("richItemRenderer")) {
            JsonObject object = jsonObject.getObject("richItemRenderer").getObject("content");
            if (object.has("videoRenderer")) {
                multiInfoItemsCollector.commit(new e(object.getObject("videoRenderer"), timeAgoParser, str, str2, aVar));
            } else if (object.has("reelItemRenderer")) {
                multiInfoItemsCollector.commit(new a(object.getObject("reelItemRenderer"), str, str2, aVar));
            } else if (object.has("shortsLockupViewModel")) {
                multiInfoItemsCollector.commit(new b(object.getObject("shortsLockupViewModel"), str, str2, aVar));
            } else if (object.has("playlistRenderer")) {
                multiInfoItemsCollector.commit(new f(object.getObject("playlistRenderer"), str, str2, aVar));
            }
        } else if (jsonObject.has("gridVideoRenderer")) {
            multiInfoItemsCollector.commit(new e(jsonObject.getObject("gridVideoRenderer"), timeAgoParser, str, str2, aVar));
        } else if (jsonObject.has("gridPlaylistRenderer")) {
            multiInfoItemsCollector.commit(new f(jsonObject.getObject("gridPlaylistRenderer"), str, str2, aVar));
        } else if (jsonObject.has("gridShowRenderer")) {
            multiInfoItemsCollector.commit(new b(jsonObject.getObject("gridShowRenderer"), aVar, str, str2));
        } else if (jsonObject.has("shelfRenderer")) {
            return b(jsonObject.getObject("shelfRenderer").getObject("content"), str, str2, multiInfoItemsCollector, aVar);
        } else if (jsonObject.has("itemSectionRenderer")) {
            return c(multiInfoItemsCollector, jsonObject.getObject("itemSectionRenderer").getArray("contents"), aVar, str, str2);
        } else if (jsonObject.has("horizontalListRenderer")) {
            return c(multiInfoItemsCollector, jsonObject.getObject("horizontalListRenderer").getArray("items"), aVar, str, str2);
        } else if (jsonObject.has("expandedShelfContentsRenderer")) {
            return c(multiInfoItemsCollector, jsonObject.getObject("expandedShelfContentsRenderer").getArray("items"), aVar, str, str2);
        } else if (jsonObject.has("lockupViewModel")) {
            JsonObject object2 = jsonObject.getObject("lockupViewModel");
            String string = object2.getString("contentType");
            if ("LOCKUP_CONTENT_TYPE_PLAYLIST".equals(string) || "LOCKUP_CONTENT_TYPE_PODCAST".equals(string)) {
                multiInfoItemsCollector.commit(new d(object2, str, str2, aVar));
            } else if ("LOCKUP_CONTENT_TYPE_VIDEO".equals(string)) {
                multiInfoItemsCollector.commit(new c(object2, timeAgoParser, str, str2, aVar));
            }
        } else if (jsonObject.has("continuationItemRenderer")) {
            return Optional.ofNullable(jsonObject.getObject("continuationItemRenderer"));
        }
        return Optional.empty();
    }

    public final Optional<JsonObject> c(MultiInfoItemsCollector multiInfoItemsCollector, JsonArray jsonArray, a aVar, String str, String str2) {
        Class<JsonObject> cls = JsonObject.class;
        return (Optional) y2.z(cls, 29, y2.s(cls, 29, Collection$EL.stream(jsonArray))).map(new sf(this, multiInfoItemsCollector, aVar, str, str2)).reduce(Optional.empty(), new ca(1));
    }

    public String d() throws ParsingException {
        return YoutubeChannelHelper.getChannelName(this.g, YoutubeChannelHelper.getChannelAgeGateRenderer(this.h), this.h);
    }

    public final Page e(JsonObject jsonObject, List<String> list) throws IOException, ExtractionException {
        if (Utils.isNullOrEmpty(jsonObject)) {
            return null;
        }
        return new Page("https://www.youtube.com/youtubei/v1/browse?prettyPrint=false", (String) null, list, (Map<String, String>) null, JsonWriter.string(YoutubeParsingHelper.prepareDesktopJsonBuilder(getExtractorLocalization(), getExtractorContentCountry()).value("continuation", jsonObject.getObject("continuationEndpoint").getObject("continuationCommand").getString("token")).done()).getBytes(StandardCharsets.UTF_8));
    }

    public Optional<JsonObject> f() {
        Class<JsonObject> cls = JsonObject.class;
        return y2.z(cls, 28, y2.s(cls, 28, Collection$EL.stream(this.h.getObject("contents").getObject("twoColumnBrowseResultsRenderer").getArray("tabs")))).filter(new bz(17)).map(new p8(22)).filter(new y5(YoutubeChannelTabLinkHandlerFactory.getUrlSuffix(getName()), 9)).findFirst().filter(new bz(18));
    }

    public String getId() throws ParsingException {
        return YoutubeChannelHelper.getChannelId(this.g, this.h, this.i);
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0072  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0076  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x00b0 A[LOOP:0: B:17:0x00ae->B:18:0x00b0, LOOP_END] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.schabi.newpipe.extractor.ListExtractor.InfoItemsPage<org.schabi.newpipe.extractor.InfoItem> getInitialPage() throws java.io.IOException, org.schabi.newpipe.extractor.exceptions.ExtractionException {
        /*
            r11 = this;
            org.schabi.newpipe.extractor.MultiInfoItemsCollector r6 = new org.schabi.newpipe.extractor.MultiInfoItemsCollector
            int r0 = r11.getServiceId()
            r6.<init>(r0)
            com.grack.nanojson.JsonArray r0 = new com.grack.nanojson.JsonArray
            r0.<init>()
            j$.util.Optional r1 = r11.f()
            boolean r2 = r1.isPresent()
            r7 = 0
            if (r2 == 0) goto L_0x006d
            java.lang.Object r0 = r1.get()
            com.grack.nanojson.JsonObject r0 = (com.grack.nanojson.JsonObject) r0
            java.lang.String r1 = "content"
            com.grack.nanojson.JsonObject r0 = r0.getObject(r1)
            java.lang.String r1 = "sectionListRenderer"
            com.grack.nanojson.JsonObject r2 = r0.getObject(r1)
            java.lang.String r3 = "contents"
            com.grack.nanojson.JsonArray r2 = r2.getArray(r3)
            com.grack.nanojson.JsonObject r2 = r2.getObject(r7)
            java.lang.String r4 = "itemSectionRenderer"
            com.grack.nanojson.JsonObject r2 = r2.getObject(r4)
            com.grack.nanojson.JsonArray r2 = r2.getArray(r3)
            com.grack.nanojson.JsonObject r2 = r2.getObject(r7)
            java.lang.String r4 = "gridRenderer"
            com.grack.nanojson.JsonObject r2 = r2.getObject(r4)
            java.lang.String r4 = "items"
            com.grack.nanojson.JsonArray r2 = r2.getArray(r4)
            boolean r4 = r2.isEmpty()
            if (r4 == 0) goto L_0x006e
            java.lang.String r2 = "richGridRenderer"
            com.grack.nanojson.JsonObject r2 = r0.getObject(r2)
            com.grack.nanojson.JsonArray r2 = r2.getArray(r3)
            boolean r4 = r2.isEmpty()
            if (r4 == 0) goto L_0x006e
            com.grack.nanojson.JsonObject r0 = r0.getObject(r1)
            com.grack.nanojson.JsonArray r0 = r0.getArray(r3)
        L_0x006d:
            r2 = r0
        L_0x006e:
            org.schabi.newpipe.extractor.services.youtube.YoutubeChannelHelper$ChannelHeader r0 = r11.g
            if (r0 != 0) goto L_0x0076
            org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeChannelTabExtractor$a r0 = org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeChannelTabExtractor.a.UNKNOWN
        L_0x0074:
            r8 = r0
            goto L_0x0082
        L_0x0076:
            boolean r0 = org.schabi.newpipe.extractor.services.youtube.YoutubeChannelHelper.isChannelVerified(r0)
            if (r0 == 0) goto L_0x007f
            org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeChannelTabExtractor$a r0 = org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeChannelTabExtractor.a.VERIFIED
            goto L_0x0074
        L_0x007f:
            org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeChannelTabExtractor$a r0 = org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeChannelTabExtractor.a.UNVERIFIED
            goto L_0x0074
        L_0x0082:
            java.lang.String r9 = r11.d()
            java.lang.String r10 = r11.getUrl()
            r0 = r11
            r1 = r6
            r3 = r8
            r4 = r9
            r5 = r10
            j$.util.Optional r0 = r0.c(r1, r2, r3, r4, r5)
            r1 = 0
            java.lang.Object r0 = r0.orElse(r1)
            com.grack.nanojson.JsonObject r0 = (com.grack.nanojson.JsonObject) r0
            java.lang.String r1 = r8.toString()
            r2 = 3
            java.lang.Object[] r3 = new java.lang.Object[r2]
            r3[r7] = r9
            r4 = 1
            r3[r4] = r10
            r4 = 2
            r3[r4] = r1
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>(r2)
        L_0x00ae:
            if (r7 >= r2) goto L_0x00bb
            r4 = r3[r7]
            j$.util.Objects.requireNonNull(r4)
            r1.add(r4)
            int r7 = r7 + 1
            goto L_0x00ae
        L_0x00bb:
            java.util.List r1 = java.util.Collections.unmodifiableList(r1)
            org.schabi.newpipe.extractor.Page r0 = r11.e(r0, r1)
            org.schabi.newpipe.extractor.ListExtractor$InfoItemsPage r1 = new org.schabi.newpipe.extractor.ListExtractor$InfoItemsPage
            r1.<init>(r6, r0)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeChannelTabExtractor.getInitialPage():org.schabi.newpipe.extractor.ListExtractor$InfoItemsPage");
    }

    public ListExtractor.InfoItemsPage<InfoItem> getPage(Page page) throws IOException, ExtractionException {
        String str;
        String str2;
        a aVar;
        if (page == null || Utils.isNullOrEmpty(page.getUrl())) {
            throw new IllegalArgumentException("Page doesn't contain an URL");
        }
        List<String> ids = page.getIds();
        MultiInfoItemsCollector multiInfoItemsCollector = new MultiInfoItemsCollector(getServiceId());
        Class<JsonObject> cls = JsonObject.class;
        JsonArray array = ((JsonObject) y2.z(cls, 27, y2.s(cls, 27, Collection$EL.stream(YoutubeParsingHelper.getJsonPostResponse("browse", page.getBody(), getExtractorLocalization()).getArray("onResponseReceivedActions")))).filter(new bz(16)).map(new p8(21)).findFirst().orElse(new JsonObject())).getArray("continuationItems");
        int size = ids.size();
        a aVar2 = a.UNKNOWN;
        if (size >= 3) {
            String str3 = ids.get(0);
            String str4 = ids.get(1);
            try {
                aVar2 = a.valueOf(ids.get(2));
            } catch (IllegalArgumentException unused) {
            }
            str2 = str3;
            aVar = aVar2;
            str = str4;
        } else {
            aVar = aVar2;
            str2 = null;
            str = null;
        }
        return new ListExtractor.InfoItemsPage<>(multiInfoItemsCollector, e(c(multiInfoItemsCollector, array, aVar, str2, str).orElse(null), ids));
    }

    public String getUrl() throws ParsingException {
        try {
            YoutubeChannelTabLinkHandlerFactory instance = YoutubeChannelTabLinkHandlerFactory.getInstance();
            String str = "channel/" + getId();
            Object[] objArr = {getName()};
            ArrayList arrayList = new ArrayList(1);
            Object obj = objArr[0];
            Objects.requireNonNull(obj);
            arrayList.add(obj);
            return instance.getUrl(str, Collections.unmodifiableList(arrayList), "");
        } catch (ParsingException unused) {
            return super.getUrl();
        }
    }

    public void onFetchPage(Downloader downloader) throws IOException, ExtractionException {
        String str;
        String resolveChannelId = YoutubeChannelHelper.resolveChannelId(super.getId());
        String name = getName();
        name.getClass();
        char c = 65535;
        switch (name.hashCode()) {
            case -1865828127:
                if (name.equals("playlists")) {
                    c = 0;
                    break;
                }
                break;
            case -1415163932:
                if (name.equals("albums")) {
                    c = 1;
                    break;
                }
                break;
            case -903148681:
                if (name.equals("shorts")) {
                    c = 2;
                    break;
                }
                break;
            case -816678056:
                if (name.equals("videos")) {
                    c = 3;
                    break;
                }
                break;
            case -439267705:
                if (name.equals("livestreams")) {
                    c = 4;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                str = "EglwbGF5bGlzdHPyBgQKAkIA";
                break;
            case 1:
                str = "EghyZWxlYXNlc_IGBQoDsgEA";
                break;
            case 2:
                str = "EgZzaG9ydHPyBgUKA5oBAA%3D%3D";
                break;
            case 3:
                str = "EgZ2aWRlb3PyBgQKAjoA";
                break;
            case 4:
                str = "EgdzdHJlYW1z8gYECgJ6AA%3D%3D";
                break;
            default:
                throw new ParsingException("Unsupported channel tab: ".concat(name));
        }
        YoutubeChannelHelper.ChannelResponseData channelResponse = YoutubeChannelHelper.getChannelResponse(resolveChannelId, str, getExtractorLocalization(), getExtractorContentCountry());
        JsonObject jsonObject = channelResponse.a;
        this.h = jsonObject;
        this.g = YoutubeChannelHelper.getChannelHeader(jsonObject);
        this.i = channelResponse.b;
    }
}
