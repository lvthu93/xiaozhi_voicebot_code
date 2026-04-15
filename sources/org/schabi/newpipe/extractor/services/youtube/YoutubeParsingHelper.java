package org.schabi.newpipe.extractor.services.youtube;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonBuilder;
import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonParser;
import com.grack.nanojson.JsonParserException;
import com.grack.nanojson.JsonStringWriter;
import com.grack.nanojson.JsonWriter;
import fi.iki.elonen.NanoHTTPD;
import j$.util.Collection$EL;
import j$.util.Objects;
import j$.util.Optional;
import j$.util.stream.Collectors;
import j$.util.stream.Stream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;
import org.jsoup.nodes.Entities;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.NewPipe;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.downloader.Response;
import org.schabi.newpipe.extractor.exceptions.AccountTerminatedException;
import org.schabi.newpipe.extractor.exceptions.ContentNotAvailableException;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.exceptions.ReCaptchaException;
import org.schabi.newpipe.extractor.localization.ContentCountry;
import org.schabi.newpipe.extractor.localization.Localization;
import org.schabi.newpipe.extractor.playlist.PlaylistInfo;
import org.schabi.newpipe.extractor.services.youtube.InnertubeClientRequestInfo;
import org.schabi.newpipe.extractor.utils.JsonUtils;
import org.schabi.newpipe.extractor.utils.Parser;
import org.schabi.newpipe.extractor.utils.RandomStringFromAlphabetGenerator;
import org.schabi.newpipe.extractor.utils.Utils;

public final class YoutubeParsingHelper {
    public static String a = null;
    public static String b = null;
    public static boolean c = false;
    public static Optional<Boolean> d = Optional.empty();
    public static final String[] e = {"INNERTUBE_CONTEXT_CLIENT_VERSION\":\"([0-9\\.]+?)\"", "innertube_context_client_version\":\"([0-9\\.]+?)\"", "client.version=([0-9\\.]+)"};
    public static final String[] f = {"window\\[\"ytInitialData\"\\]\\s*=\\s*(\\{.*?\\});", "var\\s*ytInitialData\\s*=\\s*(\\{.*?\\});"};
    public static Random g = new Random();
    public static final Pattern h = Pattern.compile("&c=WEB");
    public static final Pattern i = Pattern.compile("&c=WEB_EMBEDDED_PLAYER");
    public static final Pattern j = Pattern.compile("&c=TVHTML5");
    public static final Pattern k = Pattern.compile("&c=ANDROID");
    public static final Pattern l = Pattern.compile("&c=IOS");
    public static final Set<String> m;
    public static final Set<String> n;
    public static final Set<String> o;
    public static boolean p = false;

    static {
        Object[] objArr = {"google.", "m.google.", "www.google."};
        HashSet hashSet = new HashSet(3);
        int i2 = 0;
        while (i2 < 3) {
            Object obj = objArr[i2];
            Objects.requireNonNull(obj);
            if (hashSet.add(obj)) {
                i2++;
            } else {
                throw new IllegalArgumentException("duplicate element: " + obj);
            }
        }
        m = Collections.unmodifiableSet(hashSet);
        String[] strArr = {"invidio.us", "dev.invidio.us", "www.invidio.us", "redirect.invidious.io", "invidious.snopyta.org", "yewtu.be", "tube.connect.cafe", "tubus.eduvid.org", "invidious.kavin.rocks", "invidious.site", "invidious-us.kavin.rocks", "piped.kavin.rocks", "vid.mint.lgbt", "invidiou.site", "invidious.fdn.fr", "invidious.048596.xyz", "invidious.zee.li", "vid.puffyan.us", "ytprivate.com", "invidious.namazso.eu", "invidious.silkky.cloud", "ytb.trom.tf", "invidious.exonip.de", "inv.riverside.rocks", "invidious.blamefran.net", "y.com.cm", "invidious.moomoo.me", "yt.cyberhost.uk"};
        HashSet hashSet2 = new HashSet(28);
        int i3 = 0;
        while (i3 < 28) {
            String str = strArr[i3];
            Objects.requireNonNull(str);
            if (hashSet2.add(str)) {
                i3++;
            } else {
                throw new IllegalArgumentException("duplicate element: " + str);
            }
        }
        n = Collections.unmodifiableSet(hashSet2);
        Object[] objArr2 = {"youtube.com", "www.youtube.com", "m.youtube.com", "music.youtube.com"};
        HashSet hashSet3 = new HashSet(4);
        int i4 = 0;
        while (i4 < 4) {
            Object obj2 = objArr2[i4];
            Objects.requireNonNull(obj2);
            if (hashSet3.add(obj2)) {
                i4++;
            } else {
                throw new IllegalArgumentException("duplicate element: " + obj2);
            }
        }
        o = Collections.unmodifiableSet(hashSet3);
    }

    public static void a() throws IOException, ExtractionException {
        if (!c) {
            try {
                a = Utils.getStringResultFromRegexArray(NewPipe.getDownloader().get("https://www.youtube.com/sw.js", getOriginReferrerHeaders("https://www.youtube.com")).responseBody(), e, 1);
                c = true;
            } catch (Parser.RegexException e2) {
                throw new ParsingException("Could not extract YouTube WEB InnerTube client version from sw.js", e2);
            }
        }
    }

    public static String b(Stream<JsonObject> stream, String str, String str2) {
        Stream<R> flatMap = stream.filter(new y5(str, 7)).flatMap(new p8(13));
        Class<JsonObject> cls = JsonObject.class;
        return (String) y2.z(cls, 23, y2.s(cls, 23, flatMap)).filter(new y5(str2, 8)).map(new p8(14)).filter(new bz(13)).findFirst().orElse(null);
    }

    public static void defaultAlertsCheck(JsonObject jsonObject) throws ParsingException {
        JsonArray array = jsonObject.getArray("alerts");
        if (!Utils.isNullOrEmpty((Collection<?>) array)) {
            JsonObject object = array.getObject(0).getObject("alertRenderer");
            String textFromObject = getTextFromObject(object.getObject("text"));
            if (!object.getString("type", "").equalsIgnoreCase("ERROR")) {
                return;
            }
            if (textFromObject == null || (!textFromObject.contains("This account has been terminated") && !textFromObject.contains("This channel was removed"))) {
                throw new ContentNotAvailableException(y2.j("Got error: \"", textFromObject, "\""));
            } else if (textFromObject.matches(".*violat(ed|ion|ing).*") || textFromObject.contains("infringement")) {
                throw new AccountTerminatedException(textFromObject, AccountTerminatedException.Reason.VIOLATION);
            } else {
                throw new AccountTerminatedException(textFromObject);
            }
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0077, code lost:
        if (r9.equals("descriptive") == false) goto L_0x0043;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.schabi.newpipe.extractor.stream.AudioTrackType extractAudioTrackType(java.lang.String r9) {
        /*
            r0 = 0
            java.net.URL r1 = new java.net.URL     // Catch:{ MalformedURLException -> 0x008a }
            r1.<init>(r9)     // Catch:{ MalformedURLException -> 0x008a }
            java.lang.String r9 = "xtags"
            java.lang.String r9 = org.schabi.newpipe.extractor.utils.Utils.getQueryValue(r1, r9)     // Catch:{ MalformedURLException -> 0x008a }
            if (r9 != 0) goto L_0x000f
            return r0
        L_0x000f:
            java.lang.String r1 = ":"
            java.lang.String[] r9 = r9.split(r1)
            int r1 = r9.length
            r2 = 0
            r3 = 0
        L_0x0018:
            r4 = 2
            r5 = 1
            if (r3 >= r1) goto L_0x0037
            r6 = r9[r3]
            java.lang.String r7 = "="
            java.lang.String[] r6 = r6.split(r7, r4)
            int r7 = r6.length
            if (r7 <= r5) goto L_0x0034
            r7 = r6[r2]
            java.lang.String r8 = "acont"
            boolean r7 = r7.equals(r8)
            if (r7 == 0) goto L_0x0034
            r9 = r6[r5]
            goto L_0x0038
        L_0x0034:
            int r3 = r3 + 1
            goto L_0x0018
        L_0x0037:
            r9 = r0
        L_0x0038:
            if (r9 != 0) goto L_0x003b
            return r0
        L_0x003b:
            int r1 = r9.hashCode()
            r3 = -1
            switch(r1) {
                case -1724545844: goto L_0x0071;
                case -1320983312: goto L_0x0066;
                case -817598092: goto L_0x005b;
                case -512872340: goto L_0x0050;
                case 1379043793: goto L_0x0045;
                default: goto L_0x0043;
            }
        L_0x0043:
            r2 = -1
            goto L_0x007a
        L_0x0045:
            java.lang.String r1 = "original"
            boolean r9 = r9.equals(r1)
            if (r9 != 0) goto L_0x004e
            goto L_0x0043
        L_0x004e:
            r2 = 4
            goto L_0x007a
        L_0x0050:
            java.lang.String r1 = "dubbed-auto"
            boolean r9 = r9.equals(r1)
            if (r9 != 0) goto L_0x0059
            goto L_0x0043
        L_0x0059:
            r2 = 3
            goto L_0x007a
        L_0x005b:
            java.lang.String r1 = "secondary"
            boolean r9 = r9.equals(r1)
            if (r9 != 0) goto L_0x0064
            goto L_0x0043
        L_0x0064:
            r2 = 2
            goto L_0x007a
        L_0x0066:
            java.lang.String r1 = "dubbed"
            boolean r9 = r9.equals(r1)
            if (r9 != 0) goto L_0x006f
            goto L_0x0043
        L_0x006f:
            r2 = 1
            goto L_0x007a
        L_0x0071:
            java.lang.String r1 = "descriptive"
            boolean r9 = r9.equals(r1)
            if (r9 != 0) goto L_0x007a
            goto L_0x0043
        L_0x007a:
            switch(r2) {
                case 0: goto L_0x0087;
                case 1: goto L_0x0084;
                case 2: goto L_0x0081;
                case 3: goto L_0x0084;
                case 4: goto L_0x007e;
                default: goto L_0x007d;
            }
        L_0x007d:
            return r0
        L_0x007e:
            org.schabi.newpipe.extractor.stream.AudioTrackType r9 = org.schabi.newpipe.extractor.stream.AudioTrackType.ORIGINAL
            return r9
        L_0x0081:
            org.schabi.newpipe.extractor.stream.AudioTrackType r9 = org.schabi.newpipe.extractor.stream.AudioTrackType.SECONDARY
            return r9
        L_0x0084:
            org.schabi.newpipe.extractor.stream.AudioTrackType r9 = org.schabi.newpipe.extractor.stream.AudioTrackType.DUBBED
            return r9
        L_0x0087:
            org.schabi.newpipe.extractor.stream.AudioTrackType r9 = org.schabi.newpipe.extractor.stream.AudioTrackType.DESCRIPTIVE
            return r9
        L_0x008a:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.schabi.newpipe.extractor.services.youtube.YoutubeParsingHelper.extractAudioTrackType(java.lang.String):org.schabi.newpipe.extractor.stream.AudioTrackType");
    }

    public static String extractCachedUrlIfNeeded(String str) {
        if (str == null) {
            return null;
        }
        if (str.contains("webcache.googleusercontent.com")) {
            return str.split("cache:")[1];
        }
        return str;
    }

    public static String extractCookieValue(String str, Response response) {
        List<String> list = response.responseHeaders().get("set-cookie");
        String str2 = "";
        if (list == null) {
            return str2;
        }
        for (String str3 : list) {
            int indexOf = str3.indexOf(str);
            if (indexOf != -1) {
                str2 = str3.substring(str.length() + indexOf + 1, str3.indexOf(";", indexOf));
            }
        }
        return str2;
    }

    public static PlaylistInfo.PlaylistType extractPlaylistTypeFromPlaylistId(String str) throws ParsingException {
        if (Utils.isNullOrEmpty(str)) {
            throw new ParsingException("Could not extract playlist type from empty playlist id");
        } else if (isYoutubeMusicMixId(str)) {
            return PlaylistInfo.PlaylistType.MIX_MUSIC;
        } else {
            if (isYoutubeGenreMixId(str)) {
                return PlaylistInfo.PlaylistType.MIX_GENRE;
            }
            if (isYoutubeMixId(str)) {
                return PlaylistInfo.PlaylistType.MIX_STREAM;
            }
            return PlaylistInfo.PlaylistType.NORMAL;
        }
    }

    public static PlaylistInfo.PlaylistType extractPlaylistTypeFromPlaylistUrl(String str) throws ParsingException {
        try {
            return extractPlaylistTypeFromPlaylistId(Utils.getQueryValue(Utils.stringToURL(str), "list"));
        } catch (MalformedURLException e2) {
            throw new ParsingException("Could not extract playlist type from malformed url", e2);
        }
    }

    public static String extractVideoIdFromMixId(String str) throws ParsingException {
        if (Utils.isNullOrEmpty(str)) {
            throw new ParsingException("Video id could not be determined from empty playlist id");
        } else if (isYoutubeMyMixId(str)) {
            return str.substring(4);
        } else {
            if (isYoutubeMusicMixId(str)) {
                return str.substring(6);
            }
            if (isYoutubeGenreMixId(str)) {
                throw new ParsingException(y2.i("Video id could not be determined from genre mix id: ", str));
            } else if (!isYoutubeMixId(str)) {
                throw new ParsingException(y2.i("Video id could not be determined from playlist id: ", str));
            } else if (str.length() == 13) {
                return str.substring(2);
            } else {
                throw new ParsingException("Video id could not be determined from mix id: ".concat(str));
            }
        }
    }

    public static String fixThumbnailUrl(String str) {
        if (str.startsWith("//")) {
            str = str.substring(2);
        }
        if (str.startsWith("http://")) {
            return Utils.replaceHttpWithHttps(str);
        }
        if (!str.startsWith("https://")) {
            return "https://".concat(str);
        }
        return str;
    }

    public static String generateConsentCookie() {
        String str;
        if (isConsentAccepted()) {
            str = "CAISAiAD";
        } else {
            str = "CAE=";
        }
        return "SOCS=".concat(str);
    }

    public static String generateContentPlaybackNonce() {
        return RandomStringFromAlphabetGenerator.generate("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_", 16, g);
    }

    public static String generateTParameter() {
        return RandomStringFromAlphabetGenerator.generate("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_", 12, g);
    }

    public static String getAndroidUserAgent(Localization localization) {
        StringBuilder sb = new StringBuilder("com.google.android.youtube/19.28.35 (Linux; U; Android 15; ");
        if (localization == null) {
            localization = Localization.g;
        }
        sb.append(localization.getCountryCode());
        sb.append(") gzip");
        return sb.toString();
    }

    public static Map<String, List<String>> getClientHeaders(String str, String str2) {
        int i2 = 0;
        Object[] objArr = {str};
        ArrayList arrayList = new ArrayList(1);
        Object obj = objArr[0];
        List p2 = y2.p(obj, arrayList, obj, arrayList);
        Object[] objArr2 = {str2};
        ArrayList arrayList2 = new ArrayList(1);
        Object obj2 = objArr2[0];
        Map.Entry[] entryArr = {new AbstractMap.SimpleEntry("X-YouTube-Client-Name", p2), new AbstractMap.SimpleEntry("X-YouTube-Client-Version", y2.p(obj2, arrayList2, obj2, arrayList2))};
        HashMap hashMap = new HashMap(2);
        while (i2 < 2) {
            Map.Entry entry = entryArr[i2];
            Object key = entry.getKey();
            Objects.requireNonNull(key);
            Object value = entry.getValue();
            Objects.requireNonNull(value);
            if (hashMap.put(key, value) == null) {
                i2++;
            } else {
                throw new IllegalArgumentException("duplicate key: " + key);
            }
        }
        return Collections.unmodifiableMap(hashMap);
    }

    public static Map<String, List<String>> getClientInfoHeaders() throws ExtractionException, IOException {
        HashMap hashMap = new HashMap(getOriginReferrerHeaders("https://www.youtube.com"));
        hashMap.putAll(getClientHeaders("1", getClientVersion()));
        return hashMap;
    }

    public static String getClientVersion() throws IOException, ExtractionException {
        if (!Utils.isNullOrEmpty(a)) {
            return a;
        }
        try {
            a();
        } catch (Exception unused) {
            if (!c) {
                String responseBody = NewPipe.getDownloader().get("https://www.youtube.com/results?search_query=&ucbcb=1", getCookieHeader()).responseBody();
                try {
                    Class<JsonObject> cls = JsonObject.class;
                    Stream z = y2.z(cls, 21, y2.s(cls, 21, Collection$EL.stream(JsonParser.object().from(Utils.getStringResultFromRegexArray(responseBody, f, 1)).getObject("responseContext").getArray("serviceTrackingParams"))));
                    String b2 = b(z, "CSI", "cver");
                    a = b2;
                    if (b2 == null) {
                        try {
                            a = Utils.getStringResultFromRegexArray(responseBody, e, 1);
                        } catch (Parser.RegexException unused2) {
                        }
                    }
                    if (Utils.isNullOrEmpty(a)) {
                        a = b(z, "ECATCHER", "client.version");
                    }
                    if (a != null) {
                        c = true;
                    } else {
                        throw new ParsingException("Could not extract YouTube WEB InnerTube client version from HTML search results page");
                    }
                } catch (JsonParserException | Parser.RegexException e2) {
                    throw new ParsingException("Could not get ytInitialData", e2);
                }
            }
        }
        if (c) {
            return a;
        }
        if (isHardcodedClientVersionValid()) {
            a = "2.20250122.04.00";
            return "2.20250122.04.00";
        }
        throw new ExtractionException("Could not get YouTube WEB client version");
    }

    public static Map<String, List<String>> getCookieHeader() {
        Object[] objArr = {generateConsentCookie()};
        ArrayList arrayList = new ArrayList(1);
        Object obj = objArr[0];
        Map.Entry[] entryArr = {new AbstractMap.SimpleEntry("Cookie", y2.p(obj, arrayList, obj, arrayList))};
        HashMap hashMap = new HashMap(1);
        Map.Entry entry = entryArr[0];
        Object key = entry.getKey();
        Objects.requireNonNull(key);
        Object value = entry.getValue();
        Objects.requireNonNull(value);
        if (hashMap.put(key, value) == null) {
            return Collections.unmodifiableMap(hashMap);
        }
        throw new IllegalArgumentException("duplicate key: " + key);
    }

    public static String getFeedUrlFrom(String str) {
        if (str.startsWith("user/")) {
            return "https://www.youtube.com/feeds/videos.xml?user=" + str.replace("user/", "");
        } else if (!str.startsWith("channel/")) {
            return "https://www.youtube.com/feeds/videos.xml?channel_id=".concat(str);
        } else {
            return "https://www.youtube.com/feeds/videos.xml?channel_id=" + str.replace("channel/", "");
        }
    }

    public static JsonObject getFirstCollaborator(JsonObject jsonObject) throws ParsingException {
        try {
            return JsonUtils.getArray(jsonObject, "showDialogCommand.panelLoadingStrategy.inlineContent.dialogViewModel.customContent.listViewModel.listItems").getObject(0).getObject("listItemViewModel");
        } catch (ParsingException unused) {
            return null;
        }
    }

    public static List<Image> getImagesFromThumbnailsArray(JsonArray jsonArray) {
        Class<JsonObject> cls = JsonObject.class;
        return (List) y2.z(cls, 20, y2.s(cls, 20, Collection$EL.stream(jsonArray))).filter(new bz(11)).map(new p8(12)).collect(Collectors.toUnmodifiableList());
    }

    public static String getIosUserAgent(Localization localization) {
        StringBuilder sb = new StringBuilder("com.google.ios.youtube/20.03.02(iPhone16,2; U; CPU iOS 18_2_1 like Mac OS X; ");
        if (localization == null) {
            localization = Localization.g;
        }
        sb.append(localization.getCountryCode());
        sb.append(")");
        return sb.toString();
    }

    public static JsonObject getJsonPostResponse(String str, byte[] bArr, Localization localization) throws IOException, ExtractionException {
        return JsonUtils.toJsonObject(getValidJsonResponseBody(NewPipe.getDownloader().postWithContentTypeJson(y2.j("https://www.youtube.com/youtubei/v1/", str, "?prettyPrint=false"), getYouTubeHeaders(), bArr, localization)));
    }

    public static Map<String, List<String>> getOriginReferrerHeaders(String str) {
        int i2 = 0;
        Object[] objArr = {str};
        ArrayList arrayList = new ArrayList(1);
        Object obj = objArr[0];
        List p2 = y2.p(obj, arrayList, obj, arrayList);
        Map.Entry[] entryArr = {new AbstractMap.SimpleEntry("Origin", p2), new AbstractMap.SimpleEntry("Referer", p2)};
        HashMap hashMap = new HashMap(2);
        while (i2 < 2) {
            Map.Entry entry = entryArr[i2];
            Object key = entry.getKey();
            Objects.requireNonNull(key);
            Object value = entry.getValue();
            Objects.requireNonNull(value);
            if (hashMap.put(key, value) == null) {
                i2++;
            } else {
                throw new IllegalArgumentException("duplicate key: " + key);
            }
        }
        return Collections.unmodifiableMap(hashMap);
    }

    public static String getTextAtKey(JsonObject jsonObject, String str) {
        if (jsonObject.isString(str)) {
            return jsonObject.getString(str);
        }
        return getTextFromObject(jsonObject.getObject(str));
    }

    public static String getTextFromObject(JsonObject jsonObject, boolean z) {
        if (Utils.isNullOrEmpty(jsonObject)) {
            return null;
        }
        if (jsonObject.has("simpleText")) {
            return jsonObject.getString("simpleText");
        }
        JsonArray array = jsonObject.getArray("runs");
        if (array.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        Iterator it = array.iterator();
        while (it.hasNext()) {
            JsonObject jsonObject2 = (JsonObject) it.next();
            String string = jsonObject2.getString("text");
            if (z) {
                if (jsonObject2.has("navigationEndpoint")) {
                    String urlFromNavigationEndpoint = getUrlFromNavigationEndpoint(jsonObject2.getObject("navigationEndpoint"));
                    if (!Utils.isNullOrEmpty(urlFromNavigationEndpoint)) {
                        string = "<a href=\"" + Entities.escape(urlFromNavigationEndpoint) + "\">" + Entities.escape(string) + "</a>";
                    }
                }
                boolean z2 = true;
                boolean z3 = jsonObject2.has("bold") && jsonObject2.getBoolean("bold");
                boolean z4 = jsonObject2.has("italics") && jsonObject2.getBoolean("italics");
                if (!jsonObject2.has("strikethrough") || !jsonObject2.getBoolean("strikethrough")) {
                    z2 = false;
                }
                if (z3) {
                    sb.append("<b>");
                }
                if (z4) {
                    sb.append("<i>");
                }
                if (z2) {
                    sb.append("<s>");
                }
                sb.append(string);
                if (z2) {
                    sb.append("</s>");
                }
                if (z4) {
                    sb.append("</i>");
                }
                if (z3) {
                    sb.append("</b>");
                }
            } else {
                sb.append(string);
            }
        }
        String sb2 = sb.toString();
        return z ? sb2.replaceAll("\\n", "<br>").replaceAll(" {2}", " &nbsp;") : sb2;
    }

    public static String getTextFromObjectOrThrow(JsonObject jsonObject, String str) throws ParsingException {
        String textFromObject = getTextFromObject(jsonObject);
        if (textFromObject != null) {
            return textFromObject;
        }
        throw new ParsingException(y2.i("Could not extract text: ", str));
    }

    public static List<Image> getThumbnailsFromInfoItem(JsonObject jsonObject) throws ParsingException {
        try {
            return getImagesFromThumbnailsArray(jsonObject.getObject("thumbnail").getArray("thumbnails"));
        } catch (Exception e2) {
            throw new ParsingException("Could not get thumbnails from InfoItem", e2);
        }
    }

    public static String getTvHtml5UserAgent() {
        return "Mozilla/5.0 (PlayStation; PlayStation 4/12.00) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.4 Safari/605.1.15";
    }

    public static String getUrlFromNavigationEndpoint(JsonObject jsonObject) {
        if (jsonObject.has("urlEndpoint")) {
            String string = jsonObject.getObject("urlEndpoint").getString("url");
            if (string.startsWith("https://www.youtube.com/redirect?")) {
                string = string.substring(23);
            }
            if (string.startsWith("/redirect?")) {
                for (String str : string.substring(10).split("&")) {
                    if (str.split("=")[0].equals("q")) {
                        return Utils.decodeUrlUtf8(str.split("=")[1]);
                    }
                }
            } else if (string.startsWith("http")) {
                return string;
            } else {
                if (string.startsWith("/channel") || string.startsWith("/user") || string.startsWith("/watch")) {
                    return "https://www.youtube.com".concat(string);
                }
            }
        }
        if (jsonObject.has("browseEndpoint")) {
            JsonObject object = jsonObject.getObject("browseEndpoint");
            String string2 = object.getString("canonicalBaseUrl");
            String string3 = object.getString("browseId");
            if (string3 != null) {
                if (string3.startsWith("UC")) {
                    return "https://www.youtube.com/channel/".concat(string3);
                }
                if (string3.startsWith("VL")) {
                    return "https://www.youtube.com/playlist?list=" + string3.substring(2);
                }
            }
            if (!Utils.isNullOrEmpty(string2)) {
                return y2.i("https://www.youtube.com", string2);
            }
        }
        if (jsonObject.has("watchEndpoint")) {
            StringBuilder sb = new StringBuilder("https://www.youtube.com/watch?v=");
            sb.append(jsonObject.getObject("watchEndpoint").getString("videoId"));
            if (jsonObject.getObject("watchEndpoint").has("playlistId")) {
                sb.append("&list=");
                sb.append(jsonObject.getObject("watchEndpoint").getString("playlistId"));
            }
            if (jsonObject.getObject("watchEndpoint").has("startTimeSeconds")) {
                sb.append("&t=");
                sb.append(jsonObject.getObject("watchEndpoint").getInt("startTimeSeconds"));
            }
            return sb.toString();
        } else if (jsonObject.has("watchPlaylistEndpoint")) {
            return "https://www.youtube.com/playlist?list=" + jsonObject.getObject("watchPlaylistEndpoint").getString("playlistId");
        } else {
            if (jsonObject.has("showDialogCommand")) {
                try {
                    return getUrlFromNavigationEndpoint(JsonUtils.getObject(JsonUtils.getArray(jsonObject, "showDialogCommand.panelLoadingStrategy.inlineContent.dialogViewModel.customContent.listViewModel.listItems").getObject(0), "listItemViewModel.rendererContext.commandContext.onTap.innertubeCommand"));
                } catch (ParsingException unused) {
                }
            }
            if (!jsonObject.has("commandMetadata")) {
                return null;
            }
            JsonObject object2 = jsonObject.getObject("commandMetadata").getObject("webCommandMetadata");
            if (!object2.has("url")) {
                return null;
            }
            return "https://www.youtube.com" + object2.getString("url");
        }
    }

    public static String getUrlFromObject(JsonObject jsonObject) {
        if (Utils.isNullOrEmpty(jsonObject)) {
            return null;
        }
        JsonArray array = jsonObject.getArray("runs");
        if (array.isEmpty()) {
            return null;
        }
        Iterator it = array.iterator();
        while (it.hasNext()) {
            String urlFromNavigationEndpoint = getUrlFromNavigationEndpoint(((JsonObject) it.next()).getObject("navigationEndpoint"));
            if (!Utils.isNullOrEmpty(urlFromNavigationEndpoint)) {
                return urlFromNavigationEndpoint;
            }
        }
        return null;
    }

    public static String getValidJsonResponseBody(Response response) throws ParsingException, MalformedURLException {
        if (response.responseCode() != 404) {
            String responseBody = response.responseBody();
            if (responseBody.length() >= 50) {
                URL url = new URL(response.latestUrl());
                if (url.getHost().equalsIgnoreCase("www.youtube.com")) {
                    String path = url.getPath();
                    if (path.equalsIgnoreCase("/oops") || path.equalsIgnoreCase("/error")) {
                        throw new ContentNotAvailableException("Content unavailable");
                    }
                }
                String header = response.getHeader("Content-Type");
                if (header == null || !header.toLowerCase().contains(NanoHTTPD.MIME_HTML)) {
                    return responseBody;
                }
                throw new ParsingException("Got HTML document, expected JSON response (latest url was: \"" + response.latestUrl() + "\")");
            }
            throw new ParsingException("JSON response is too short");
        }
        throw new ContentNotAvailableException("Not found (\"" + response.responseCode() + " " + response.responseMessage() + "\")");
    }

    public static String getVisitorDataFromInnertube(InnertubeClientRequestInfo innertubeClientRequestInfo, Localization localization, ContentCountry contentCountry, Map<String, List<String>> map, String str, String str2, boolean z) throws IOException, ExtractionException {
        String str3;
        byte[] bytes = JsonWriter.string(prepareJsonBuilder(localization, contentCountry, innertubeClientRequestInfo, str2).done()).getBytes(StandardCharsets.UTF_8);
        Downloader downloader = NewPipe.getDownloader();
        StringBuilder m2 = y2.m(str);
        if (z) {
            str3 = "guide";
        } else {
            str3 = "visitor_id";
        }
        String string = JsonUtils.toJsonObject(getValidJsonResponseBody(downloader.postWithContentTypeJson(y2.k(m2, str3, "?prettyPrint=false"), map, bytes))).getObject("responseContext").getString("visitorData");
        if (!Utils.isNullOrEmpty(string)) {
            return string;
        }
        throw new ParsingException("Could not get visitorData");
    }

    public static Map<String, List<String>> getYouTubeHeaders() throws ExtractionException, IOException {
        Map<String, List<String>> clientInfoHeaders = getClientInfoHeaders();
        Object[] objArr = {generateConsentCookie()};
        ArrayList arrayList = new ArrayList(1);
        Object obj = objArr[0];
        Objects.requireNonNull(obj);
        arrayList.add(obj);
        clientInfoHeaders.put("Cookie", Collections.unmodifiableList(arrayList));
        return clientInfoHeaders;
    }

    public static String getYoutubeMusicClientVersion() throws IOException, ReCaptchaException, Parser.RegexException {
        String[] strArr = e;
        if (!Utils.isNullOrEmpty(b)) {
            return b;
        }
        if (isHardcodedYoutubeMusicClientVersionValid()) {
            b = "1.20250122.01.00";
            return "1.20250122.01.00";
        }
        try {
            b = Utils.getStringResultFromRegexArray(NewPipe.getDownloader().get("https://music.youtube.com/sw.js", getOriginReferrerHeaders("https://music.youtube.com")).responseBody(), strArr, 1);
        } catch (Exception unused) {
            b = Utils.getStringResultFromRegexArray(NewPipe.getDownloader().get("https://music.youtube.com/?ucbcb=1", getCookieHeader()).responseBody(), strArr, 1);
        }
        return b;
    }

    public static Map<String, List<String>> getYoutubeMusicHeaders() {
        HashMap hashMap = new HashMap(getOriginReferrerHeaders("https://music.youtube.com"));
        hashMap.putAll(getClientHeaders("67", b));
        return hashMap;
    }

    public static boolean hasArtistOrVerifiedIconBadgeAttachment(JsonArray jsonArray) {
        Class<JsonObject> cls = JsonObject.class;
        return y2.z(cls, 22, y2.s(cls, 22, Collection$EL.stream(jsonArray))).anyMatch(new bz(12));
    }

    public static boolean isAndroidStreamingUrl(String str) {
        return Parser.isMatch(k, str);
    }

    public static boolean isConsentAccepted() {
        return p;
    }

    public static boolean isGoogleURL(String str) {
        try {
            return Collection$EL.stream(m).anyMatch(new o8(new URL(extractCachedUrlIfNeeded(str)), 1));
        } catch (MalformedURLException unused) {
            return false;
        }
    }

    public static boolean isHardcodedClientVersionValid() throws IOException, ExtractionException {
        if (d.isPresent()) {
            return d.get().booleanValue();
        }
        boolean z = false;
        byte[] bytes = ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) JsonWriter.string().object()).object("context")).object("client")).value("hl", "en-GB")).value("gl", "GB")).value("clientName", "WEB")).value("clientVersion", "2.20250122.04.00")).value("platform", "DESKTOP")).value("utcOffsetMinutes", 0)).end()).object("request")).array("internalExperimentFlags")).end()).value("useSsl", true)).end()).object("user")).value("lockedSafetyMode", false)).end()).end()).value("fetchLiveState", true)).end()).done().getBytes(StandardCharsets.UTF_8);
        Response postWithContentTypeJson = NewPipe.getDownloader().postWithContentTypeJson("https://www.youtube.com/youtubei/v1/guide?prettyPrint=false", getClientHeaders("1", "2.20250122.04.00"), bytes);
        String responseBody = postWithContentTypeJson.responseBody();
        int responseCode = postWithContentTypeJson.responseCode();
        if (responseBody.length() > 5000 && responseCode == 200) {
            z = true;
        }
        Optional<Boolean> of = Optional.of(Boolean.valueOf(z));
        d = of;
        return of.get().booleanValue();
    }

    public static boolean isHardcodedYoutubeMusicClientVersionValid() throws IOException, ReCaptchaException {
        byte[] bytes = ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) JsonWriter.string().object()).object("context")).object("client")).value("clientName", "WEB_REMIX")).value("clientVersion", "1.20250122.01.00")).value("hl", "en-GB")).value("gl", "GB")).value("platform", "DESKTOP")).value("utcOffsetMinutes", 0)).end()).object("request")).array("internalExperimentFlags")).end()).value("useSsl", true)).end()).object("user")).value("lockedSafetyMode", false)).end()).end()).value("input", "")).end()).done().getBytes(StandardCharsets.UTF_8);
        HashMap hashMap = new HashMap(getOriginReferrerHeaders("https://music.youtube.com"));
        hashMap.putAll(getClientHeaders("67", "2.20250122.04.00"));
        Response postWithContentTypeJson = NewPipe.getDownloader().postWithContentTypeJson("https://music.youtube.com/youtubei/v1/music/get_search_suggestions?prettyPrint=false", hashMap, bytes);
        if (postWithContentTypeJson.responseBody().length() <= 500 || postWithContentTypeJson.responseCode() != 200) {
            return false;
        }
        return true;
    }

    public static boolean isHooktubeURL(URL url) {
        return url.getHost().equalsIgnoreCase("hooktube.com");
    }

    public static boolean isInvidiousURL(URL url) {
        return n.contains(url.getHost().toLowerCase(Locale.ROOT));
    }

    public static boolean isIosStreamingUrl(String str) {
        return Parser.isMatch(l, str);
    }

    public static boolean isTvHtml5StreamingUrl(String str) {
        return Parser.isMatch(j, str);
    }

    public static boolean isVerified(JsonArray jsonArray) {
        if (Utils.isNullOrEmpty((Collection<?>) jsonArray)) {
            return false;
        }
        Iterator it = jsonArray.iterator();
        while (it.hasNext()) {
            String string = ((JsonObject) it.next()).getObject("metadataBadgeRenderer").getString("style");
            if (string != null && (string.equals("BADGE_STYLE_TYPE_VERIFIED") || string.equals("BADGE_STYLE_TYPE_VERIFIED_ARTIST"))) {
                return true;
            }
        }
        return false;
    }

    public static boolean isWebEmbeddedPlayerStreamingUrl(String str) {
        return Parser.isMatch(i, str);
    }

    public static boolean isWebStreamingUrl(String str) {
        return Parser.isMatch(h, str);
    }

    public static boolean isY2ubeURL(URL url) {
        return url.getHost().equalsIgnoreCase("y2u.be");
    }

    public static boolean isYoutubeGenreMixId(String str) {
        return str.startsWith("RDGMEM");
    }

    public static boolean isYoutubeMixId(String str) {
        return str.startsWith("RD");
    }

    public static boolean isYoutubeMusicMixId(String str) {
        if (str.startsWith("RDAMVM") || str.startsWith("RDCLAK")) {
            return true;
        }
        return false;
    }

    public static boolean isYoutubeMyMixId(String str) {
        return str.startsWith("RDMM");
    }

    public static boolean isYoutubeServiceURL(URL url) {
        String host = url.getHost();
        if (host.equalsIgnoreCase("www.youtube-nocookie.com") || host.equalsIgnoreCase("youtu.be")) {
            return true;
        }
        return false;
    }

    public static boolean isYoutubeURL(URL url) {
        return o.contains(url.getHost().toLowerCase(Locale.ROOT));
    }

    public static int parseDurationString(String str) throws ParsingException, NumberFormatException {
        String[] strArr;
        int i2;
        if (str.contains(":")) {
            strArr = str.split(":");
        } else {
            strArr = str.split("\\.");
        }
        int[] iArr = {24, 60, 60, 1};
        int length = 4 - strArr.length;
        if (length >= 0) {
            int i3 = 0;
            for (int i4 = 0; i4 < strArr.length; i4++) {
                int i5 = iArr[i4 + length];
                String str2 = strArr[i4];
                if (str2 != null && !str2.isEmpty()) {
                    try {
                        i2 = Integer.parseInt(Utils.removeNonDigitCharacters(str2));
                    } catch (NumberFormatException unused) {
                    }
                    i3 = (i3 + i2) * i5;
                }
                i2 = 0;
                i3 = (i3 + i2) * i5;
            }
            return i3;
        }
        throw new ParsingException("Error duration string with unknown format: ".concat(str));
    }

    public static JsonBuilder<JsonObject> prepareDesktopJsonBuilder(Localization localization, ContentCountry contentCountry) throws IOException, ExtractionException {
        return JsonObject.builder().object("context").object("client").value("hl", localization.getLocalizationCode()).value("gl", contentCountry.getCountryCode()).value("clientName", "WEB").value("clientVersion", getClientVersion()).value("originalUrl", "https://www.youtube.com").value("platform", "DESKTOP").value("utcOffsetMinutes", 0).end().object("request").array("internalExperimentFlags").end().value("useSsl", true).end().object("user").value("lockedSafetyMode", false).end().end();
    }

    public static JsonBuilder<JsonObject> prepareJsonBuilder(Localization localization, ContentCountry contentCountry, InnertubeClientRequestInfo innertubeClientRequestInfo, String str) {
        JsonBuilder<JsonObject> value = JsonObject.builder().object("context").object("client").value("clientName", innertubeClientRequestInfo.a.a);
        InnertubeClientRequestInfo.ClientInfo clientInfo = innertubeClientRequestInfo.a;
        JsonBuilder<JsonObject> value2 = value.value("clientVersion", clientInfo.b);
        String str2 = clientInfo.d;
        if (str2 != null) {
            value2.value("clientScreen", str2);
        }
        InnertubeClientRequestInfo.DeviceInfo deviceInfo = innertubeClientRequestInfo.b;
        String str3 = deviceInfo.a;
        if (str3 != null) {
            value2.value("platform", str3);
        }
        String str4 = clientInfo.e;
        if (str4 != null) {
            value2.value("visitorData", str4);
        }
        String str5 = deviceInfo.b;
        if (str5 != null) {
            value2.value("deviceMake", str5);
        }
        String str6 = deviceInfo.c;
        if (str6 != null) {
            value2.value("deviceModel", str6);
        }
        String str7 = deviceInfo.d;
        if (str7 != null) {
            value2.value("osName", str7);
        }
        String str8 = deviceInfo.e;
        if (str8 != null) {
            value2.value("osVersion", str8);
        }
        int i2 = deviceInfo.f;
        if (i2 > 0) {
            value2.value("androidSdkVersion", i2);
        }
        value2.value("hl", localization.getLocalizationCode()).value("gl", contentCountry.getCountryCode()).value("utcOffsetMinutes", 0).end();
        if (str != null) {
            value2.object("thirdParty").value("embedUrl", str).end();
        }
        value2.object("request").array("internalExperimentFlags").end().value("useSsl", true).end().object("user").value("lockedSafetyMode", false).end().end();
        return value2;
    }

    public static void resetClientVersion() {
        a = null;
        c = false;
    }

    public static void setConsentAccepted(boolean z) {
        p = z;
    }

    public static void setNumberGenerator(Random random) {
        g = random;
    }

    public static JsonObject getJsonPostResponse(String str, List<String> list, byte[] bArr, Localization localization) throws IOException, ExtractionException {
        String str2;
        Map<String, List<String>> youTubeHeaders = getYouTubeHeaders();
        if (list.isEmpty()) {
            str2 = "?prettyPrint=false";
        } else {
            StringBuilder sb = new StringBuilder("?");
            StringBuilder sb2 = new StringBuilder();
            Iterator<T> it = list.iterator();
            if (it.hasNext()) {
                while (true) {
                    sb2.append((CharSequence) it.next());
                    if (!it.hasNext()) {
                        break;
                    }
                    sb2.append("&");
                }
            }
            sb.append(sb2.toString());
            sb.append("&prettyPrint=false");
            str2 = sb.toString();
        }
        return JsonUtils.toJsonObject(getValidJsonResponseBody(NewPipe.getDownloader().postWithContentTypeJson(y2.j("https://www.youtube.com/youtubei/v1/", str, str2), youTubeHeaders, bArr, localization)));
    }

    public static String getTextFromObject(JsonObject jsonObject) {
        return getTextFromObject(jsonObject, false);
    }
}
