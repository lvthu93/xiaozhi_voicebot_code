package org.schabi.newpipe.extractor.services.youtube;

import com.grack.nanojson.JsonBuilder;
import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonWriter;
import j$.util.Objects;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.schabi.newpipe.extractor.NewPipe;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.localization.ContentCountry;
import org.schabi.newpipe.extractor.localization.Localization;
import org.schabi.newpipe.extractor.services.youtube.InnertubeClientRequestInfo;
import org.schabi.newpipe.extractor.utils.JsonUtils;

public final class YoutubeStreamHelper {
    public static void a(JsonBuilder<JsonObject> jsonBuilder, String str, String str2) {
        jsonBuilder.value("videoId", str);
        if (str2 != null) {
            jsonBuilder.value("cpn", str2);
        }
        jsonBuilder.value("contentCheckOk", true).value("racyCheckOk", true);
    }

    public static Map<String, List<String>> b(String str) {
        int i = 0;
        Object[] objArr = {str};
        ArrayList arrayList = new ArrayList(1);
        Object obj = objArr[0];
        List p = y2.p(obj, arrayList, obj, arrayList);
        ArrayList arrayList2 = new ArrayList(1);
        Object obj2 = new Object[]{"2"}[0];
        Map.Entry[] entryArr = {new AbstractMap.SimpleEntry("User-Agent", p), new AbstractMap.SimpleEntry("X-Goog-Api-Format-Version", y2.p(obj2, arrayList2, obj2, arrayList2))};
        HashMap hashMap = new HashMap(2);
        while (i < 2) {
            Map.Entry entry = entryArr[i];
            Object key = entry.getKey();
            Objects.requireNonNull(key);
            Object value = entry.getValue();
            Objects.requireNonNull(value);
            if (hashMap.put(key, value) == null) {
                i++;
            } else {
                throw new IllegalArgumentException("duplicate key: " + key);
            }
        }
        return Collections.unmodifiableMap(hashMap);
    }

    public static JsonObject getAndroidPlayerResponse(ContentCountry contentCountry, Localization localization, String str, String str2, PoTokenResult poTokenResult) throws IOException, ExtractionException {
        InnertubeClientRequestInfo ofAndroidClient = InnertubeClientRequestInfo.ofAndroidClient();
        ofAndroidClient.a.e = poTokenResult.a;
        Map<String, List<String>> b = b(YoutubeParsingHelper.getAndroidUserAgent(localization));
        JsonBuilder<JsonObject> prepareJsonBuilder = YoutubeParsingHelper.prepareJsonBuilder(localization, contentCountry, ofAndroidClient, (String) null);
        a(prepareJsonBuilder, str, str2);
        prepareJsonBuilder.object("serviceIntegrityDimensions").value("poToken", poTokenResult.b).end();
        byte[] bytes = JsonWriter.string(prepareJsonBuilder.done()).getBytes(StandardCharsets.UTF_8);
        return JsonUtils.toJsonObject(YoutubeParsingHelper.getValidJsonResponseBody(NewPipe.getDownloader().postWithContentTypeJson("https://youtubei.googleapis.com/youtubei/v1/player?prettyPrint=false&t=" + YoutubeParsingHelper.generateTParameter() + "&id=" + str, b, bytes, localization)));
    }

    public static JsonObject getAndroidReelPlayerResponse(ContentCountry contentCountry, Localization localization, String str, String str2) throws IOException, ExtractionException {
        InnertubeClientRequestInfo ofAndroidClient = InnertubeClientRequestInfo.ofAndroidClient();
        Map<String, List<String>> b = b(YoutubeParsingHelper.getAndroidUserAgent(localization));
        ofAndroidClient.a.e = YoutubeParsingHelper.getVisitorDataFromInnertube(ofAndroidClient, localization, contentCountry, b, "https://youtubei.googleapis.com/youtubei/v1/", (String) null, false);
        JsonBuilder<JsonObject> prepareJsonBuilder = YoutubeParsingHelper.prepareJsonBuilder(localization, contentCountry, ofAndroidClient, (String) null);
        prepareJsonBuilder.object("playerRequest");
        a(prepareJsonBuilder, str, str2);
        prepareJsonBuilder.end().value("disablePlayerResponse", false);
        byte[] bytes = JsonWriter.string(prepareJsonBuilder.done()).getBytes(StandardCharsets.UTF_8);
        return JsonUtils.toJsonObject(YoutubeParsingHelper.getValidJsonResponseBody(NewPipe.getDownloader().postWithContentTypeJson("https://youtubei.googleapis.com/youtubei/v1/reel/reel_item_watch?prettyPrint=false&t=" + YoutubeParsingHelper.generateTParameter() + "&id=" + str + "&$fields=playerResponse", b, bytes, localization))).getObject("playerResponse");
    }

    public static JsonObject getIosPlayerResponse(ContentCountry contentCountry, Localization localization, String str, String str2, PoTokenResult poTokenResult) throws IOException, ExtractionException {
        String str3;
        InnertubeClientRequestInfo ofIosClient = InnertubeClientRequestInfo.ofIosClient();
        Map<String, List<String>> b = b(YoutubeParsingHelper.getIosUserAgent(localization));
        InnertubeClientRequestInfo.ClientInfo clientInfo = ofIosClient.a;
        if (poTokenResult == null) {
            str3 = YoutubeParsingHelper.getVisitorDataFromInnertube(ofIosClient, localization, contentCountry, b, "https://www.youtube.com/youtubei/v1/", (String) null, false);
        } else {
            str3 = poTokenResult.a;
        }
        clientInfo.e = str3;
        JsonBuilder<JsonObject> prepareJsonBuilder = YoutubeParsingHelper.prepareJsonBuilder(localization, contentCountry, ofIosClient, (String) null);
        a(prepareJsonBuilder, str, str2);
        if (poTokenResult != null) {
            prepareJsonBuilder.object("serviceIntegrityDimensions").value("poToken", poTokenResult.b).end();
        }
        byte[] bytes = JsonWriter.string(prepareJsonBuilder.done()).getBytes(StandardCharsets.UTF_8);
        return JsonUtils.toJsonObject(YoutubeParsingHelper.getValidJsonResponseBody(NewPipe.getDownloader().postWithContentTypeJson("https://youtubei.googleapis.com/youtubei/v1/player?prettyPrint=false&t=" + YoutubeParsingHelper.generateTParameter() + "&id=" + str, b, bytes, localization)));
    }

    public static JsonObject getWebEmbeddedPlayerResponse(Localization localization, ContentCountry contentCountry, String str, String str2, PoTokenResult poTokenResult, int i) throws IOException, ExtractionException {
        String str3;
        Localization localization2 = localization;
        String str4 = str;
        PoTokenResult poTokenResult2 = poTokenResult;
        InnertubeClientRequestInfo ofWebEmbeddedPlayerClient = InnertubeClientRequestInfo.ofWebEmbeddedPlayerClient();
        HashMap hashMap = new HashMap(YoutubeParsingHelper.getClientHeaders("56", "1.20250121.00.00"));
        hashMap.putAll(YoutubeParsingHelper.getOriginReferrerHeaders("https://www.youtube.com"));
        String str5 = "https://www.youtube.com/watch?v=" + str4;
        InnertubeClientRequestInfo.ClientInfo clientInfo = ofWebEmbeddedPlayerClient.a;
        if (poTokenResult2 == null) {
            str3 = YoutubeParsingHelper.getVisitorDataFromInnertube(ofWebEmbeddedPlayerClient, localization, contentCountry, hashMap, "https://www.youtube.com/youtubei/v1/", str5, false);
        } else {
            str3 = poTokenResult2.a;
        }
        clientInfo.e = str3;
        ContentCountry contentCountry2 = contentCountry;
        JsonBuilder<JsonObject> prepareJsonBuilder = YoutubeParsingHelper.prepareJsonBuilder(localization, contentCountry, ofWebEmbeddedPlayerClient, str5);
        a(prepareJsonBuilder, str4, str2);
        prepareJsonBuilder.object("playbackContext").object("contentPlaybackContext").value("signatureTimestamp", i).value("referer", str5).end().end();
        if (poTokenResult2 != null) {
            prepareJsonBuilder.object("serviceIntegrityDimensions").value("poToken", poTokenResult2.b).end();
        }
        return JsonUtils.toJsonObject(YoutubeParsingHelper.getValidJsonResponseBody(NewPipe.getDownloader().postWithContentTypeJson("https://www.youtube.com/youtubei/v1/player?prettyPrint=false", hashMap, JsonWriter.string(prepareJsonBuilder.done()).getBytes(StandardCharsets.UTF_8), localization)));
    }

    public static JsonObject getWebMetadataPlayerResponse(Localization localization, ContentCountry contentCountry, String str) throws IOException, ExtractionException {
        InnertubeClientRequestInfo ofWebClient = InnertubeClientRequestInfo.ofWebClient();
        ofWebClient.a.b = YoutubeParsingHelper.getClientVersion();
        Map<String, List<String>> youTubeHeaders = YoutubeParsingHelper.getYouTubeHeaders();
        ofWebClient.a.e = YoutubeParsingHelper.getVisitorDataFromInnertube(ofWebClient, localization, contentCountry, youTubeHeaders, "https://www.youtube.com/youtubei/v1/", (String) null, false);
        JsonBuilder<JsonObject> prepareJsonBuilder = YoutubeParsingHelper.prepareJsonBuilder(localization, contentCountry, ofWebClient, (String) null);
        a(prepareJsonBuilder, str, (String) null);
        return JsonUtils.toJsonObject(YoutubeParsingHelper.getValidJsonResponseBody(NewPipe.getDownloader().postWithContentTypeJson("https://www.youtube.com/youtubei/v1/player?prettyPrint=false&$fields=microformat,playabilityStatus,storyboards,videoDetails", youTubeHeaders, JsonWriter.string(prepareJsonBuilder.done()).getBytes(StandardCharsets.UTF_8), localization)));
    }
}
