package org.schabi.newpipe.extractor.services.peertube;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonObject;
import j$.util.Collection$EL;
import j$.util.stream.Collectors;
import j$.util.stream.Stream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.InfoItemExtractor;
import org.schabi.newpipe.extractor.InfoItemsCollector;
import org.schabi.newpipe.extractor.exceptions.ContentNotAvailableException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.services.peertube.extractors.PeertubeChannelInfoItemExtractor;
import org.schabi.newpipe.extractor.services.peertube.extractors.PeertubePlaylistInfoItemExtractor;
import org.schabi.newpipe.extractor.services.peertube.extractors.PeertubeSepiaStreamInfoItemExtractor;
import org.schabi.newpipe.extractor.services.peertube.extractors.PeertubeStreamInfoItemExtractor;
import org.schabi.newpipe.extractor.utils.JsonUtils;
import org.schabi.newpipe.extractor.utils.Utils;

public final class PeertubeParsingHelper {
    public static List a(JsonObject jsonObject, String str, String str2, String str3) {
        JsonArray array = jsonObject.getArray(str2);
        if (!Utils.isNullOrEmpty((Collection<?>) array)) {
            Stream stream = Collection$EL.stream(array);
            Class<JsonObject> cls = JsonObject.class;
            return (List) y2.z(cls, 7, y2.s(cls, 7, stream)).filter(new bz(6)).map(new cc(str, 2)).collect(Collectors.toUnmodifiableList());
        }
        JsonObject object = jsonObject.getObject(str3);
        String string = object.getString("path");
        if (Utils.isNullOrEmpty(string)) {
            return Collections.emptyList();
        }
        Object[] objArr = {new Image(y2.x(str, string), -1, object.getInt("width", -1), Image.ResolutionLevel.UNKNOWN)};
        ArrayList arrayList = new ArrayList(1);
        Object obj = objArr[0];
        return y2.p(obj, arrayList, obj, arrayList);
    }

    public static void collectItemsFrom(InfoItemsCollector infoItemsCollector, JsonObject jsonObject, String str) throws ParsingException {
        collectItemsFrom(infoItemsCollector, jsonObject, str, false);
    }

    public static List<Image> getAvatarsFromOwnerAccountOrVideoChannelObject(String str, JsonObject jsonObject) {
        return a(jsonObject, str, "avatars", "avatar");
    }

    public static List<Image> getBannersFromAccountOrVideoChannelObject(String str, JsonObject jsonObject) {
        return a(jsonObject, str, "banners", "banner");
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.schabi.newpipe.extractor.Page getNextPage(java.lang.String r6, long r7) {
        /*
            r0 = 0
            java.lang.String r1 = "start=(\\d*)"
            java.lang.String r1 = org.schabi.newpipe.extractor.utils.Parser.matchGroup1((java.lang.String) r1, (java.lang.String) r6)     // Catch:{ RegexException -> 0x0036 }
            boolean r2 = org.schabi.newpipe.extractor.utils.Utils.isBlank(r1)
            if (r2 == 0) goto L_0x000e
            return r0
        L_0x000e:
            long r2 = java.lang.Long.parseLong(r1)     // Catch:{  }
            r4 = 12
            long r2 = r2 + r4
            int r4 = (r2 > r7 ? 1 : (r2 == r7 ? 0 : -1))
            if (r4 < 0) goto L_0x001a
            return r0
        L_0x001a:
            org.schabi.newpipe.extractor.Page r7 = new org.schabi.newpipe.extractor.Page
            java.lang.String r8 = "start="
            java.lang.String r0 = defpackage.y2.i(r8, r1)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>(r8)
            r1.append(r2)
            java.lang.String r8 = r1.toString()
            java.lang.String r6 = r6.replace(r0, r8)
            r7.<init>((java.lang.String) r6)
            return r7
        L_0x0036:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.schabi.newpipe.extractor.services.peertube.PeertubeParsingHelper.getNextPage(java.lang.String, long):org.schabi.newpipe.extractor.Page");
    }

    public static List<Image> getThumbnailsFromPlaylistOrVideoItem(String str, JsonObject jsonObject) {
        ArrayList arrayList = new ArrayList(2);
        String string = jsonObject.getString("thumbnailPath");
        if (!Utils.isNullOrEmpty(string)) {
            arrayList.add(new Image(y2.x(str, string), -1, -1, Image.ResolutionLevel.LOW));
        }
        String string2 = jsonObject.getString("previewPath");
        if (!Utils.isNullOrEmpty(string2)) {
            arrayList.add(new Image(y2.x(str, string2), -1, -1, Image.ResolutionLevel.MEDIUM));
        }
        return Collections.unmodifiableList(arrayList);
    }

    public static void validate(JsonObject jsonObject) throws ContentNotAvailableException {
        String string = jsonObject.getString(MqttServiceConstants.TRACE_ERROR);
        if (!Utils.isBlank(string)) {
            throw new ContentNotAvailableException(string);
        }
    }

    public static void collectItemsFrom(InfoItemsCollector infoItemsCollector, JsonObject jsonObject, String str, boolean z) throws ParsingException {
        InfoItemExtractor infoItemExtractor;
        try {
            Iterator it = ((JsonArray) JsonUtils.getValue(jsonObject, "data")).iterator();
            while (it.hasNext()) {
                Object next = it.next();
                if (next instanceof JsonObject) {
                    JsonObject jsonObject2 = (JsonObject) next;
                    if (jsonObject2.has("video")) {
                        jsonObject2 = jsonObject2.getObject("video");
                    }
                    boolean has = jsonObject2.has("videosLength");
                    boolean has2 = jsonObject2.has("followersCount");
                    if (z) {
                        infoItemExtractor = new PeertubeSepiaStreamInfoItemExtractor(jsonObject2, str);
                    } else if (has) {
                        infoItemExtractor = new PeertubePlaylistInfoItemExtractor(jsonObject2, str);
                    } else if (has2) {
                        infoItemExtractor = new PeertubeChannelInfoItemExtractor(jsonObject2, str);
                    } else {
                        infoItemExtractor = new PeertubeStreamInfoItemExtractor(jsonObject2, str);
                    }
                    infoItemsCollector.commit(infoItemExtractor);
                }
            }
        } catch (Exception e) {
            throw new ParsingException("Unable to extract list info", e);
        }
    }
}
