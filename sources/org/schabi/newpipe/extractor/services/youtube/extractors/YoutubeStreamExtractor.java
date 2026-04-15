package org.schabi.newpipe.extractor.services.youtube.extractors;

import androidx.core.app.NotificationCompat;
import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonWriter;
import j$.util.Collection$EL;
import j$.util.Map;
import j$.util.Objects;
import j$.util.stream.Collectors;
import j$.util.stream.Stream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.mozilla.javascript.ES6Iterator;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.MediaFormat;
import org.schabi.newpipe.extractor.MetaInfo;
import org.schabi.newpipe.extractor.MultiInfoItemsCollector;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.AgeRestrictedContentException;
import org.schabi.newpipe.extractor.exceptions.ContentNotAvailableException;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.GeographicRestrictionException;
import org.schabi.newpipe.extractor.exceptions.PaidContentException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.exceptions.PrivateContentException;
import org.schabi.newpipe.extractor.exceptions.SignInConfirmNotBotException;
import org.schabi.newpipe.extractor.exceptions.YoutubeMusicPremiumContentException;
import org.schabi.newpipe.extractor.linkhandler.LinkHandler;
import org.schabi.newpipe.extractor.localization.ContentCountry;
import org.schabi.newpipe.extractor.localization.Localization;
import org.schabi.newpipe.extractor.services.youtube.ItagItem;
import org.schabi.newpipe.extractor.services.youtube.PoTokenProvider;
import org.schabi.newpipe.extractor.services.youtube.PoTokenResult;
import org.schabi.newpipe.extractor.services.youtube.YoutubeDescriptionHelper;
import org.schabi.newpipe.extractor.services.youtube.YoutubeJavaScriptPlayerManager;
import org.schabi.newpipe.extractor.services.youtube.YoutubeMetaInfoHelper;
import org.schabi.newpipe.extractor.services.youtube.YoutubeParsingHelper;
import org.schabi.newpipe.extractor.services.youtube.YoutubeStreamHelper;
import org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeChannelLinkHandlerFactory;
import org.schabi.newpipe.extractor.stream.AudioStream;
import org.schabi.newpipe.extractor.stream.Description;
import org.schabi.newpipe.extractor.stream.Frameset;
import org.schabi.newpipe.extractor.stream.StreamExtractor;
import org.schabi.newpipe.extractor.stream.StreamSegment;
import org.schabi.newpipe.extractor.stream.StreamType;
import org.schabi.newpipe.extractor.stream.SubtitlesStream;
import org.schabi.newpipe.extractor.stream.VideoStream;
import org.schabi.newpipe.extractor.utils.JsonUtils;
import org.schabi.newpipe.extractor.utils.LocaleCompat;
import org.schabi.newpipe.extractor.utils.Pair;
import org.schabi.newpipe.extractor.utils.Parser;
import org.schabi.newpipe.extractor.utils.Utils;

public class YoutubeStreamExtractor extends StreamExtractor {
    public static PoTokenProvider x;
    public static boolean y;
    public JsonObject g;
    public JsonObject h;
    public JsonObject i;
    public JsonObject j;
    public JsonObject k;
    public JsonObject l;
    public JsonObject m;
    public JsonObject n;
    public JsonObject o;
    public int p = -1;
    public StreamType q;
    public String r;
    public String s;
    public String t;
    public String u;
    public String v;
    public String w;

    public YoutubeStreamExtractor(StreamingService streamingService, LinkHandler linkHandler) {
        super(streamingService, linkHandler);
    }

    public static void d(JsonObject jsonObject) throws ParsingException {
        String string = jsonObject.getString(NotificationCompat.CATEGORY_STATUS);
        if (string != null && !string.equalsIgnoreCase("ok")) {
            String string2 = jsonObject.getString("reason");
            if (string.equalsIgnoreCase("login_required")) {
                if (string2 == null) {
                    String string3 = jsonObject.getArray("messages").getString(0);
                    if (string3 != null && string3.contains("private")) {
                        throw new PrivateContentException("This video is private");
                    }
                } else if (string2.contains("age")) {
                    throw new AgeRestrictedContentException("This age-restricted video cannot be watched anonymously");
                }
            }
            if ((string.equalsIgnoreCase("unplayable") || string.equalsIgnoreCase(MqttServiceConstants.TRACE_ERROR)) && string2 != null) {
                if (string2.contains("Music Premium")) {
                    throw new YoutubeMusicPremiumContentException();
                } else if (string2.contains("payment")) {
                    throw new PaidContentException("This video is a paid video");
                } else if (string2.contains("members-only")) {
                    throw new PaidContentException("This video is only available for members of the channel of this video");
                } else if (string2.contains("unavailable")) {
                    String textFromObject = YoutubeParsingHelper.getTextFromObject(jsonObject.getObject("errorScreen").getObject("playerErrorMessageRenderer").getObject("subreason"));
                    if (textFromObject == null || !textFromObject.contains("country")) {
                        throw new ContentNotAvailableException((String) Objects.requireNonNullElse(textFromObject, string2));
                    }
                    throw new GeographicRestrictionException("This video is not available in client's country.");
                } else if (string2.contains("age-restricted")) {
                    throw new AgeRestrictedContentException("This age-restricted video cannot be watched anonymously");
                }
            }
            if (string2 == null || !string2.contains("a bot")) {
                throw new ContentNotAvailableException("Got error " + string + ": \"" + string2 + "\"");
            }
            throw new SignInConfirmNotBotException("YouTube probably temporarily blocked this IP, got error " + string + ": \"" + string2 + "\"");
        }
    }

    public static String f(String str, List<Pair<JsonObject, String>> list, String str2) {
        String concat = str.concat("ManifestUrl");
        for (Pair next : list) {
            if (next.getFirst() != null) {
                String string = ((JsonObject) next.getFirst()).getString(concat);
                if (!Utils.isNullOrEmpty(string)) {
                    if (next.getSecond() == null) {
                        return string + "?" + str2;
                    }
                    StringBuilder o2 = y2.o(string, "?pot=");
                    o2.append((String) next.getSecond());
                    o2.append("&");
                    o2.append(str2);
                    return o2.toString();
                }
            }
        }
        return "";
    }

    public static boolean j(JsonObject jsonObject, String str) {
        return !str.equals(jsonObject.getObject("videoDetails").getString("videoId"));
    }

    public static long k(JsonArray jsonArray) throws ParsingException {
        Class<JsonObject> cls = JsonObject.class;
        String str = null;
        JsonObject jsonObject = (JsonObject) y2.ab(cls, 16, y2.aa(cls, 16, Collection$EL.stream(jsonArray))).map(new fg(8)).filter(new mg(3)).findFirst().orElse(null);
        if (jsonObject != null) {
            String string = jsonObject.getObject("accessibilityData").getObject("accessibilityData").getString("label");
            if (string == null) {
                string = jsonObject.getObject("accessibility").getString("label");
            }
            if (string == null) {
                str = jsonObject.getObject("defaultText").getObject("accessibility").getObject("accessibilityData").getString("label");
            } else {
                str = string;
            }
            if (str != null && str.toLowerCase().contains("no likes")) {
                return 0;
            }
        }
        if (str != null) {
            try {
                return Long.parseLong(Utils.removeNonDigitCharacters(str));
            } catch (NumberFormatException e) {
                throw new ParsingException(y2.j("Could not parse \"", str, "\" as a long"), e);
            }
        } else {
            throw new ParsingException("Could not get like count from accessibility data");
        }
    }

    public static long l(JsonArray jsonArray) throws ParsingException {
        Class<JsonObject> cls = JsonObject.class;
        JsonObject jsonObject = (JsonObject) y2.ab(cls, 17, y2.aa(cls, 17, Collection$EL.stream(jsonArray))).map(new fg(9)).filter(new mg(4)).findFirst().orElse(null);
        if (jsonObject != null) {
            String string = jsonObject.getString("accessibilityText");
            if (string != null) {
                try {
                    return Long.parseLong(Utils.removeNonDigitCharacters(string));
                } catch (NumberFormatException e) {
                    throw new ParsingException(y2.j("Could not parse \"", string, "\" as a long"), e);
                }
            } else {
                throw new ParsingException("Could not find buttonViewModel's accessibilityText string");
            }
        } else {
            throw new ParsingException("Could not find buttonViewModel object");
        }
    }

    public static void setFetchIosClient(boolean z) {
        y = z;
    }

    public static void setPoTokenProvider(PoTokenProvider poTokenProvider) {
        x = poTokenProvider;
    }

    public final z3 c(String str, JsonObject jsonObject, ItagItem itagItem, ItagItem.ItagType itagType, String str2, String str3) throws ExtractionException {
        String str4;
        String str5;
        if (jsonObject.has("url")) {
            str4 = jsonObject.getString("url");
        } else {
            String string = jsonObject.getString("cipher", jsonObject.getString("signatureCipher"));
            if (Utils.isNullOrEmpty(string)) {
                return null;
            }
            Map<String, String> compatParseMap = Parser.compatParseMap(string);
            str4 = compatParseMap.get("url") + "&" + compatParseMap.get("sp") + "=" + YoutubeJavaScriptPlayerManager.deobfuscateSignature(str, (String) Map.EL.getOrDefault(compatParseMap, "s", ""));
        }
        String str6 = YoutubeJavaScriptPlayerManager.getUrlWithThrottlingParameterDeobfuscated(str, str4) + "&cpn=" + str2;
        if (str3 != null) {
            str6 = str6 + "&pot=" + str3;
        }
        JsonObject object = jsonObject.getObject("initRange");
        JsonObject object2 = jsonObject.getObject("indexRange");
        String string2 = jsonObject.getString("mimeType", "");
        boolean z = true;
        if (string2.contains("codecs")) {
            str5 = string2.split("\"")[1];
        } else {
            str5 = "";
        }
        itagItem.setBitrate(jsonObject.getInt("bitrate"));
        itagItem.setWidth(jsonObject.getInt("width"));
        itagItem.setHeight(jsonObject.getInt("height"));
        itagItem.setInitStart(Integer.parseInt(object.getString("start", "-1")));
        itagItem.setInitEnd(Integer.parseInt(object.getString("end", "-1")));
        itagItem.setIndexStart(Integer.parseInt(object2.getString("start", "-1")));
        itagItem.setIndexEnd(Integer.parseInt(object2.getString("end", "-1")));
        itagItem.setQuality(jsonObject.getString("quality"));
        itagItem.setCodec(str5);
        itagItem.setIsDrc(Boolean.valueOf(jsonObject.getBoolean("isDrc", Boolean.FALSE)));
        itagItem.setLastModified(Long.parseLong(jsonObject.getString("lastModified", "-1")));
        itagItem.setXtags(jsonObject.getString("xtags"));
        StreamType streamType = this.q;
        StreamType streamType2 = StreamType.LIVE_STREAM;
        StreamType streamType3 = StreamType.POST_LIVE_STREAM;
        if (streamType == streamType2 || streamType == streamType3) {
            itagItem.setTargetDurationSec(jsonObject.getInt("targetDurationSec"));
        }
        if (itagType == ItagItem.ItagType.VIDEO || itagType == ItagItem.ItagType.VIDEO_ONLY) {
            itagItem.setFps(jsonObject.getInt("fps"));
        } else if (itagType == ItagItem.ItagType.AUDIO) {
            itagItem.setSampleRate(Integer.parseInt(jsonObject.getString("audioSampleRate")));
            itagItem.setAudioChannels(jsonObject.getInt("audioChannels", 2));
            String string3 = jsonObject.getObject("audioTrack").getString("id");
            if (!Utils.isNullOrEmpty(string3)) {
                itagItem.setAudioTrackId(string3);
                int indexOf = string3.indexOf(".");
                if (indexOf != -1) {
                    LocaleCompat.forLanguageTag(string3.substring(0, indexOf)).ifPresent(new ce(1, itagItem));
                }
                itagItem.setAudioTrackType(YoutubeParsingHelper.extractAudioTrackType(str6));
            }
            itagItem.setAudioTrackName(jsonObject.getObject("audioTrack").getString("displayName"));
        }
        itagItem.setContentLength(Long.parseLong(jsonObject.getString("contentLength", String.valueOf(-1))));
        itagItem.setApproxDurationMs(Long.parseLong(jsonObject.getString("approxDurationMs", String.valueOf(-1))));
        z3 z3Var = new z3(str6, itagItem);
        StreamType streamType4 = this.q;
        if (streamType4 == StreamType.VIDEO_STREAM) {
            z3Var.g = !jsonObject.getString("type", "").equalsIgnoreCase("FORMAT_STREAM_TYPE_OTF");
        } else {
            if (streamType4 == streamType3) {
                z = false;
            }
            z3Var.g = z;
        }
        return z3Var;
    }

    public final ArrayList e(String str, ItagItem.ItagType itagType, Function function, String str2) throws ParsingException {
        try {
            String id = getId();
            ArrayList arrayList = new ArrayList();
            Stream.CC.of((T[]) new Pair[]{new Pair(this.k, new Pair(this.t, this.u)), new Pair(this.j, new Pair(this.s, this.v)), new Pair(this.i, new Pair(this.r, this.w))}).flatMap(new og(this, id, str, itagType)).map(function).forEachOrdered(new q8(arrayList, 2));
            return arrayList;
        } catch (Exception e) {
            throw new ParsingException(y2.j("Could not get ", str2, " streams"), e);
        }
    }

    public final JsonObject g(String str) {
        Class<JsonObject> cls = JsonObject.class;
        return (JsonObject) y2.ab(cls, 20, y2.aa(cls, 20, Collection$EL.stream(this.h.getObject("contents").getObject("twoColumnWatchNextResults").getObject("results").getObject("results").getArray("contents")))).filter(new y5(str, 11)).map(new cc(str, 6)).findFirst().orElse(new JsonObject());
    }

    public int getAgeLimit() throws ParsingException {
        int i2 = this.p;
        if (i2 != -1) {
            return i2;
        }
        Class<JsonObject> cls = JsonObject.class;
        int i3 = 0;
        if (y2.ab(cls, 12, y2.aa(cls, 12, Collection$EL.stream(i().getObject("metadataRowContainer").getObject("metadataRowContainerRenderer").getArray("rows")))).flatMap(new fg(3)).flatMap(new fg(4)).map(new fg(5)).anyMatch(new mg(0))) {
            i3 = 18;
        }
        this.p = i3;
        return i3;
    }

    public List<AudioStream> getAudioStreams() throws ExtractionException {
        a();
        return e("adaptiveFormats", ItagItem.ItagType.AUDIO, new s5(1, this), "audio");
    }

    public String getCategory() {
        return this.n.getString("category", "");
    }

    public String getDashMpdUrl() throws ParsingException {
        a();
        return f("dash", Arrays.asList(new Pair[]{new Pair(this.j, this.v), new Pair(this.k, this.u)}), "mpd_version=7");
    }

    public Description getDescription() throws ParsingException {
        a();
        String textFromObject = YoutubeParsingHelper.getTextFromObject(i().getObject("description"), true);
        if (!Utils.isNullOrEmpty(textFromObject)) {
            return new Description(textFromObject, 1);
        }
        String attributedDescriptionToHtml = YoutubeDescriptionHelper.attributedDescriptionToHtml(i().getObject("attributedDescription"));
        if (!Utils.isNullOrEmpty(attributedDescriptionToHtml)) {
            return new Description(attributedDescriptionToHtml, 1);
        }
        String string = this.g.getObject("videoDetails").getString("shortDescription");
        if (string == null) {
            string = YoutubeParsingHelper.getTextFromObject(this.n.getObject("description"));
        }
        return new Description(string, 3);
    }

    public String getErrorMessage() {
        try {
            return YoutubeParsingHelper.getTextFromObject(this.g.getObject("playabilityStatus").getObject("errorScreen").getObject("playerErrorMessageRenderer").getObject("reason"));
        } catch (NullPointerException unused) {
            return null;
        }
    }

    public List<Frameset> getFrames() throws ExtractionException {
        ArrayList arrayList;
        String str = "playerLiveStoryboardSpecRenderer";
        try {
            JsonObject object = this.g.getObject("storyboards");
            if (!object.has(str)) {
                str = "playerStoryboardSpecRenderer";
            }
            JsonObject object2 = object.getObject(str);
            if (object2 == null) {
                return Collections.emptyList();
            }
            String string = object2.getString("spec");
            if (string == null) {
                return Collections.emptyList();
            }
            String[] split = string.split("\\|");
            String str2 = split[0];
            ArrayList arrayList2 = new ArrayList(split.length - 1);
            for (int i2 = 1; i2 < split.length; i2++) {
                String[] split2 = split[i2].split(MqttTopic.MULTI_LEVEL_WILDCARD);
                if (split2.length == 8) {
                    if (Integer.parseInt(split2[5]) != 0) {
                        int parseInt = Integer.parseInt(split2[2]);
                        int parseInt2 = Integer.parseInt(split2[3]);
                        int parseInt3 = Integer.parseInt(split2[4]);
                        String str3 = str2.replace("$L", String.valueOf(i2 - 1)).replace("$N", split2[6]) + "&sigh=" + split2[7];
                        if (str3.contains("$M")) {
                            int ceil = (int) Math.ceil(((double) parseInt) / ((double) (parseInt2 * parseInt3)));
                            ArrayList arrayList3 = new ArrayList(ceil);
                            for (int i3 = 0; i3 < ceil; i3++) {
                                arrayList3.add(str3.replace("$M", String.valueOf(i3)));
                            }
                            arrayList = arrayList3;
                        } else {
                            arrayList = Collections.singletonList(str3);
                        }
                        arrayList2.add(new Frameset(arrayList, Integer.parseInt(split2[0]), Integer.parseInt(split2[1]), parseInt, Integer.parseInt(split2[5]), parseInt2, parseInt3));
                    }
                }
            }
            return arrayList2;
        } catch (Exception e) {
            throw new ExtractionException("Could not get frames", e);
        }
    }

    public String getHlsUrl() throws ParsingException {
        a();
        return f("hls", Arrays.asList(new Pair[]{new Pair(this.i, this.w), new Pair(this.j, this.v), new Pair(this.k, this.u)}), "");
    }

    public Locale getLanguageInfo() {
        return null;
    }

    public long getLength() throws ParsingException {
        a();
        try {
            return Long.parseLong(this.g.getObject("videoDetails").getString("lengthSeconds"));
        } catch (Exception unused) {
            for (JsonObject array : Arrays.asList(new JsonObject[]{this.k, this.j, this.i})) {
                JsonArray array2 = array.getArray("adaptiveFormats");
                if (!array2.isEmpty()) {
                    try {
                        return (long) Math.round(((float) Long.parseLong(array2.getObject(0).getString("approxDurationMs"))) / 1000.0f);
                    } catch (NumberFormatException unused2) {
                    }
                }
            }
            throw new ParsingException("Could not get duration");
        }
    }

    public String getLicence() throws ParsingException {
        JsonObject object = i().getObject("metadataRowContainer").getObject("metadataRowContainerRenderer").getArray("rows").getObject(0).getObject("metadataRowRenderer");
        String textFromObject = YoutubeParsingHelper.getTextFromObject(object.getArray("contents").getObject(0));
        if (textFromObject == null || !"Licence".equals(YoutubeParsingHelper.getTextFromObject(object.getObject("title")))) {
            return "YouTube licence";
        }
        return textFromObject;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0036, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x003e, code lost:
        throw new org.schabi.newpipe.extractor.exceptions.ParsingException("Could not get like count", r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0035, code lost:
        return k(r0);
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0031 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long getLikeCount() throws org.schabi.newpipe.extractor.exceptions.ParsingException {
        /*
            r3 = this;
            r3.a()
            com.grack.nanojson.JsonObject r0 = r3.g
            java.lang.String r1 = "videoDetails"
            com.grack.nanojson.JsonObject r0 = r0.getObject(r1)
            java.lang.String r1 = "allowRatings"
            boolean r0 = r0.getBoolean(r1)
            if (r0 != 0) goto L_0x0016
            r0 = -1
            return r0
        L_0x0016:
            com.grack.nanojson.JsonObject r0 = r3.h()
            java.lang.String r1 = "videoActions"
            com.grack.nanojson.JsonObject r0 = r0.getObject(r1)
            java.lang.String r1 = "menuRenderer"
            com.grack.nanojson.JsonObject r0 = r0.getObject(r1)
            java.lang.String r1 = "topLevelButtons"
            com.grack.nanojson.JsonArray r0 = r0.getArray(r1)
            long r0 = l(r0)     // Catch:{ ParsingException -> 0x0031 }
            return r0
        L_0x0031:
            long r0 = k(r0)     // Catch:{ ParsingException -> 0x0036 }
            return r0
        L_0x0036:
            r0 = move-exception
            org.schabi.newpipe.extractor.exceptions.ParsingException r1 = new org.schabi.newpipe.extractor.exceptions.ParsingException
            java.lang.String r2 = "Could not get like count"
            r1.<init>(r2, r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeStreamExtractor.getLikeCount():long");
    }

    public List<MetaInfo> getMetaInfo() throws ParsingException {
        return YoutubeMetaInfoHelper.getMetaInfo(this.h.getObject("contents").getObject("twoColumnWatchNextResults").getObject("results").getObject("results").getArray("contents"));
    }

    public String getName() throws ParsingException {
        a();
        String string = this.g.getObject("videoDetails").getString("title");
        if (Utils.isNullOrEmpty(string)) {
            string = YoutubeParsingHelper.getTextFromObject(h().getObject("title"));
            if (Utils.isNullOrEmpty(string)) {
                throw new ParsingException("Could not get name");
            }
        }
        return string;
    }

    public StreamExtractor.Privacy getPrivacy() {
        if (this.n.getBoolean("isUnlisted")) {
            return StreamExtractor.Privacy.UNLISTED;
        }
        return StreamExtractor.Privacy.c;
    }

    public List<StreamSegment> getStreamSegments() throws ParsingException {
        if (!this.h.has("engagementPanels")) {
            return Collections.emptyList();
        }
        Class<JsonObject> cls = JsonObject.class;
        JsonArray jsonArray = (JsonArray) y2.ab(cls, 14, y2.aa(cls, 14, Collection$EL.stream(this.h.getArray("engagementPanels")))).filter(new mg(2)).map(new fg(6)).findFirst().orElse(null);
        if (jsonArray == null) {
            return Collections.emptyList();
        }
        long length = getLength();
        ArrayList arrayList = new ArrayList();
        for (JsonObject jsonObject : (List) y2.ab(cls, 15, y2.aa(cls, 15, Collection$EL.stream(jsonArray))).map(new fg(7)).collect(Collectors.toList())) {
            int i2 = jsonObject.getObject("onTap").getObject("watchEndpoint").getInt("startTimeSeconds", -1);
            if (i2 == -1) {
                throw new ParsingException("Could not get stream segment start time.");
            } else if (((long) i2) > length) {
                break;
            } else {
                String textFromObject = YoutubeParsingHelper.getTextFromObject(jsonObject.getObject("title"));
                if (!Utils.isNullOrEmpty(textFromObject)) {
                    StreamSegment streamSegment = new StreamSegment(textFromObject, i2);
                    streamSegment.setUrl(getUrl() + "?t=" + i2);
                    if (jsonObject.has("thumbnail")) {
                        JsonArray array = jsonObject.getObject("thumbnail").getArray("thumbnails");
                        if (!array.isEmpty()) {
                            streamSegment.setPreviewUrl(YoutubeParsingHelper.fixThumbnailUrl(array.getObject(array.size() - 1).getString("url")));
                        }
                    }
                    arrayList.add(streamSegment);
                } else {
                    throw new ParsingException("Could not get stream segment title.");
                }
            }
        }
        return arrayList;
    }

    public StreamType getStreamType() {
        a();
        return this.q;
    }

    public List<SubtitlesStream> getSubtitles(MediaFormat mediaFormat) throws ParsingException {
        a();
        ArrayList arrayList = new ArrayList();
        JsonArray array = this.o.getArray("captionTracks");
        for (int i2 = 0; i2 < array.size(); i2++) {
            String string = array.getObject(i2).getString("languageCode");
            String string2 = array.getObject(i2).getString("baseUrl");
            String string3 = array.getObject(i2).getString("vssId");
            if (!(string == null || string2 == null || string3 == null)) {
                boolean startsWith = string3.startsWith("a.");
                String replaceAll = string2.replaceAll("&fmt=[^&]*", "").replaceAll("&tlang=[^&]*", "");
                SubtitlesStream.Builder builder = new SubtitlesStream.Builder();
                StringBuilder o2 = y2.o(replaceAll, "&fmt=");
                o2.append(mediaFormat.getSuffix());
                arrayList.add(builder.setContent(o2.toString(), true).setMediaFormat(mediaFormat).setLanguageCode(string).setAutoGenerated(startsWith).build());
            }
        }
        return arrayList;
    }

    public List<SubtitlesStream> getSubtitlesDefault() throws ParsingException {
        return getSubtitles(MediaFormat.TTML);
    }

    public List<String> getTags() {
        return JsonUtils.getStringListFromJsonArray(this.g.getObject("videoDetails").getArray("keywords"));
    }

    public String getTextualUploadDate() {
        String textFromObject;
        String string = this.n.getString("uploadDate", "");
        if (string.isEmpty()) {
            string = this.n.getString("publishDate", "");
        }
        if (!string.isEmpty()) {
            return string;
        }
        JsonObject object = this.n.getObject("liveBroadcastDetails");
        String string2 = object.getString("endTimestamp", "");
        if (string2.isEmpty()) {
            string2 = object.getString("startTimestamp", "");
        }
        if (!string2.isEmpty()) {
            return string2;
        }
        if (getStreamType() == StreamType.LIVE_STREAM || (textFromObject = YoutubeParsingHelper.getTextFromObject(h().getObject("dateText"))) == null) {
            return null;
        }
        if (textFromObject.startsWith("Premiered on ")) {
            return textFromObject.substring(13);
        }
        if (textFromObject.startsWith("Premiered ")) {
            return textFromObject.substring(10);
        }
        return textFromObject;
    }

    public List<Image> getThumbnails() throws ParsingException {
        a();
        try {
            return YoutubeParsingHelper.getImagesFromThumbnailsArray(this.g.getObject("videoDetails").getObject("thumbnail").getArray("thumbnails"));
        } catch (Exception unused) {
            throw new ParsingException("Could not get thumbnails");
        }
    }

    public long getTimeStamp() throws ParsingException {
        long b = b("((#|&|\\?)t=\\d*h?\\d*m?\\d+s?)");
        if (b == -2) {
            return 0;
        }
        return b;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:?, code lost:
        r1 = j$.util.Optional.of(j$.time.LocalDate.parse(r0, j$.time.format.DateTimeFormatter.ofPattern("MMM dd, yyyy", java.util.Locale.ENGLISH)));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x002a, code lost:
        r1 = j$.util.Optional.empty();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x004d, code lost:
        return (org.schabi.newpipe.extractor.localization.DateWrapper) r1.or(new defpackage.lg(r4, r0)).map(new defpackage.fg(2)).orElseThrow(new defpackage.j7(r0, 4));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0018, code lost:
        return org.schabi.newpipe.extractor.localization.TimeAgoPatternsManager.getTimeAgoParserFor(new org.schabi.newpipe.extractor.localization.Localization("en")).parse(r0);
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:4:0x0009 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.schabi.newpipe.extractor.localization.DateWrapper getUploadDate() throws org.schabi.newpipe.extractor.exceptions.ParsingException {
        /*
            r4 = this;
            java.lang.String r0 = r4.getTextualUploadDate()
            org.schabi.newpipe.extractor.localization.DateWrapper r0 = org.schabi.newpipe.extractor.localization.DateWrapper.fromOffsetDateTime(r0)     // Catch:{ ParsingException -> 0x0009 }
            return r0
        L_0x0009:
            org.schabi.newpipe.extractor.localization.Localization r1 = new org.schabi.newpipe.extractor.localization.Localization     // Catch:{ ParsingException -> 0x0019 }
            java.lang.String r2 = "en"
            r1.<init>(r2)     // Catch:{ ParsingException -> 0x0019 }
            org.schabi.newpipe.extractor.localization.TimeAgoParser r1 = org.schabi.newpipe.extractor.localization.TimeAgoPatternsManager.getTimeAgoParserFor(r1)     // Catch:{ ParsingException -> 0x0019 }
            org.schabi.newpipe.extractor.localization.DateWrapper r0 = r1.parse(r0)     // Catch:{ ParsingException -> 0x0019 }
            return r0
        L_0x0019:
            java.lang.String r1 = "MMM dd, yyyy"
            java.util.Locale r2 = java.util.Locale.ENGLISH     // Catch:{ DateTimeParseException -> 0x002a }
            j$.time.format.DateTimeFormatter r1 = j$.time.format.DateTimeFormatter.ofPattern(r1, r2)     // Catch:{ DateTimeParseException -> 0x002a }
            j$.time.LocalDate r1 = j$.time.LocalDate.parse(r0, r1)     // Catch:{ DateTimeParseException -> 0x002a }
            j$.util.Optional r1 = j$.util.Optional.of(r1)     // Catch:{ DateTimeParseException -> 0x002a }
            goto L_0x002e
        L_0x002a:
            j$.util.Optional r1 = j$.util.Optional.empty()
        L_0x002e:
            lg r2 = new lg
            r2.<init>(r4, r0)
            j$.util.Optional r1 = r1.or(r2)
            fg r2 = new fg
            r3 = 2
            r2.<init>(r3)
            j$.util.Optional r1 = r1.map(r2)
            j7 r2 = new j7
            r3 = 4
            r2.<init>(r0, r3)
            java.lang.Object r0 = r1.orElseThrow(r2)
            org.schabi.newpipe.extractor.localization.DateWrapper r0 = (org.schabi.newpipe.extractor.localization.DateWrapper) r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeStreamExtractor.getUploadDate():org.schabi.newpipe.extractor.localization.DateWrapper");
    }

    public List<Image> getUploaderAvatars() throws ParsingException {
        List<Image> list;
        a();
        JsonObject object = i().getObject("owner").getObject("videoOwnerRenderer");
        if (object.has("avatarStack")) {
            list = YoutubeParsingHelper.getImagesFromThumbnailsArray(object.getObject("avatarStack").getObject("avatarStackViewModel").getArray("avatars").getObject(0).getObject("avatarViewModel").getObject("image").getArray("sources"));
        } else {
            list = YoutubeParsingHelper.getImagesFromThumbnailsArray(object.getObject("thumbnail").getArray("thumbnails"));
        }
        if (!list.isEmpty() || this.p != 0) {
            return list;
        }
        throw new ParsingException("Could not get uploader avatars");
    }

    public String getUploaderName() throws ParsingException {
        a();
        String string = this.g.getObject("videoDetails").getString("author");
        if (!Utils.isNullOrEmpty(string)) {
            return string;
        }
        throw new ParsingException("Could not get uploader name");
    }

    public long getUploaderSubscriberCount() throws ParsingException {
        String str;
        JsonObject object = JsonUtils.getObject(this.m, "owner.videoOwnerRenderer");
        if (object.has("subscriberCountText")) {
            str = YoutubeParsingHelper.getTextFromObject(object.getObject("subscriberCountText"));
        } else {
            str = YoutubeParsingHelper.getFirstCollaborator(object.getObject("navigationEndpoint")).getObject("subtitle").getString("content").split("•")[1];
        }
        if (Utils.isNullOrEmpty(str)) {
            return -1;
        }
        try {
            return Utils.mixedNumberWordToLong(str);
        } catch (NumberFormatException e) {
            throw new ParsingException("Could not get uploader subscriber count", e);
        }
    }

    public String getUploaderUrl() throws ParsingException {
        a();
        String string = this.g.getObject("videoDetails").getString("channelId");
        if (!Utils.isNullOrEmpty(string)) {
            YoutubeChannelLinkHandlerFactory instance = YoutubeChannelLinkHandlerFactory.getInstance();
            return instance.getUrl("channel/" + string);
        }
        throw new ParsingException("Could not get uploader url");
    }

    public List<VideoStream> getVideoOnlyStreams() throws ExtractionException {
        a();
        return e("adaptiveFormats", ItagItem.ItagType.VIDEO_ONLY, new ng(this, true), "video-only");
    }

    public List<VideoStream> getVideoStreams() throws ExtractionException {
        a();
        return e("formats", ItagItem.ItagType.VIDEO, new ng(this, false), "video");
    }

    public long getViewCount() throws ParsingException {
        String textFromObject = YoutubeParsingHelper.getTextFromObject(h().getObject("viewCount").getObject("videoViewCountRenderer").getObject("viewCount"));
        if (Utils.isNullOrEmpty(textFromObject)) {
            textFromObject = this.g.getObject("videoDetails").getString("viewCount");
            if (Utils.isNullOrEmpty(textFromObject)) {
                throw new ParsingException("Could not get view count");
            }
        }
        if (textFromObject.toLowerCase().contains("no views")) {
            return 0;
        }
        return Long.parseLong(Utils.removeNonDigitCharacters(textFromObject));
    }

    public final JsonObject h() {
        JsonObject jsonObject = this.l;
        if (jsonObject != null) {
            return jsonObject;
        }
        JsonObject g2 = g("videoPrimaryInfoRenderer");
        this.l = g2;
        return g2;
    }

    public final JsonObject i() {
        JsonObject jsonObject = this.m;
        if (jsonObject != null) {
            return jsonObject;
        }
        JsonObject g2 = g("videoSecondaryInfoRenderer");
        this.m = g2;
        return g2;
    }

    public boolean isUploaderVerified() throws ParsingException {
        JsonObject object = i().getObject("owner").getObject("videoOwnerRenderer");
        if (object.has("badges")) {
            return YoutubeParsingHelper.isVerified(object.getArray("badges"));
        }
        JsonObject firstCollaborator = YoutubeParsingHelper.getFirstCollaborator(object.getObject("navigationEndpoint"));
        if (firstCollaborator == null) {
            return false;
        }
        return YoutubeParsingHelper.hasArtistOrVerifiedIconBadgeAttachment(firstCollaborator.getObject("title").getArray("attachmentRuns"));
    }

    public void onFetchPage(Downloader downloader) throws IOException, ExtractionException {
        boolean z;
        String str;
        PoTokenResult poTokenResult;
        JsonObject jsonObject;
        PoTokenResult poTokenResult2;
        String id = getId();
        Localization extractorLocalization = getExtractorLocalization();
        ContentCountry extractorContentCountry = getExtractorContentCountry();
        PoTokenProvider poTokenProvider = x;
        boolean z2 = false;
        if (poTokenProvider == null) {
            z = true;
        } else {
            z = false;
        }
        this.t = YoutubeParsingHelper.generateContentPlaybackNonce();
        JsonObject webMetadataPlayerResponse = YoutubeStreamHelper.getWebMetadataPlayerResponse(extractorLocalization, extractorContentCountry, id);
        if (!j(webMetadataPlayerResponse, id)) {
            this.g = webMetadataPlayerResponse;
            this.n = webMetadataPlayerResponse.getObject("microformat").getObject("playerMicroformatRenderer");
            JsonObject object = webMetadataPlayerResponse.getObject("playabilityStatus");
            if ("login_required".equalsIgnoreCase(object.getString(NotificationCompat.CATEGORY_STATUS)) && object.getString("reason", "").contains("age")) {
                z2 = true;
            }
            PoTokenResult poTokenResult3 = null;
            if (z2) {
                if (poTokenProvider == null) {
                    poTokenResult2 = null;
                } else {
                    poTokenResult2 = poTokenProvider.getWebEmbedClientPoToken(id);
                }
                String generateContentPlaybackNonce = YoutubeParsingHelper.generateContentPlaybackNonce();
                this.t = generateContentPlaybackNonce;
                PoTokenResult poTokenResult4 = poTokenResult2;
                str = "streamingData";
                JsonObject webEmbeddedPlayerResponse = YoutubeStreamHelper.getWebEmbeddedPlayerResponse(extractorLocalization, extractorContentCountry, id, generateContentPlaybackNonce, poTokenResult4, YoutubeJavaScriptPlayerManager.getSignatureTimestamp(id).intValue());
                this.g = webEmbeddedPlayerResponse;
                d(webEmbeddedPlayerResponse.getObject("playabilityStatus"));
                if (!j(webEmbeddedPlayerResponse, id)) {
                    this.k = webEmbeddedPlayerResponse.getObject(str);
                    this.o = webEmbeddedPlayerResponse.getObject("captions").getObject("playerCaptionsTracklistRenderer");
                    PoTokenResult poTokenResult5 = poTokenResult4;
                    if (poTokenResult5 != null) {
                        this.u = poTokenResult5.c;
                    }
                } else {
                    throw new ExtractionException("WEB_EMBEDDED_PLAYER player response is not valid");
                }
            } else {
                str = "streamingData";
                d(object);
            }
            if (this.g.getObject("playabilityStatus").has("liveStreamability")) {
                this.q = StreamType.LIVE_STREAM;
            } else if (this.g.getObject("videoDetails").getBoolean("isPostLiveDvr", Boolean.FALSE)) {
                this.q = StreamType.POST_LIVE_STREAM;
            } else {
                this.q = StreamType.VIDEO_STREAM;
            }
            if (z) {
                poTokenResult = null;
            } else {
                poTokenResult = poTokenProvider.getAndroidClientPoToken(id);
            }
            try {
                String generateContentPlaybackNonce2 = YoutubeParsingHelper.generateContentPlaybackNonce();
                this.s = generateContentPlaybackNonce2;
                if (poTokenResult == null) {
                    jsonObject = YoutubeStreamHelper.getAndroidReelPlayerResponse(extractorContentCountry, extractorLocalization, id, generateContentPlaybackNonce2);
                } else {
                    jsonObject = YoutubeStreamHelper.getAndroidPlayerResponse(extractorContentCountry, extractorLocalization, id, generateContentPlaybackNonce2, poTokenResult);
                }
                if (!j(jsonObject, id)) {
                    this.j = jsonObject.getObject(str);
                    if (Utils.isNullOrEmpty(this.o)) {
                        this.o = jsonObject.getObject("captions").getObject("playerCaptionsTracklistRenderer");
                    }
                    if (poTokenResult != null) {
                        this.v = poTokenResult.c;
                    }
                }
            } catch (Exception unused) {
            }
            if (y) {
                if (!z) {
                    poTokenResult3 = poTokenProvider.getIosClientPoToken(id);
                }
                PoTokenResult poTokenResult6 = poTokenResult3;
                try {
                    String generateContentPlaybackNonce3 = YoutubeParsingHelper.generateContentPlaybackNonce();
                    this.r = generateContentPlaybackNonce3;
                    JsonObject iosPlayerResponse = YoutubeStreamHelper.getIosPlayerResponse(extractorContentCountry, extractorLocalization, id, generateContentPlaybackNonce3, poTokenResult6);
                    if (!j(iosPlayerResponse, id)) {
                        this.i = iosPlayerResponse.getObject(str);
                        if (Utils.isNullOrEmpty(this.o)) {
                            this.o = iosPlayerResponse.getObject("captions").getObject("playerCaptionsTracklistRenderer");
                        }
                        if (poTokenResult6 != null) {
                            this.w = poTokenResult6.c;
                        }
                    }
                } catch (Exception unused2) {
                }
            }
            this.h = YoutubeParsingHelper.getJsonPostResponse(ES6Iterator.NEXT_METHOD, JsonWriter.string(YoutubeParsingHelper.prepareDesktopJsonBuilder(extractorLocalization, extractorContentCountry).value("videoId", id).value("contentCheckOk", true).value("racyCheckOk", true).done()).getBytes(StandardCharsets.UTF_8), extractorLocalization);
            return;
        }
        d(webMetadataPlayerResponse.getObject("playabilityStatus"));
        throw new ExtractionException("WEB player response is not valid");
    }

    public MultiInfoItemsCollector getRelatedItems() throws ExtractionException {
        Class<JsonObject> cls = JsonObject.class;
        a();
        if (getAgeLimit() != 0) {
            return null;
        }
        try {
            MultiInfoItemsCollector multiInfoItemsCollector = new MultiInfoItemsCollector(getServiceId());
            Collection$EL.stream(this.h.getObject("contents").getObject("twoColumnWatchNextResults").getObject("secondaryResults").getObject("secondaryResults").getArray("results")).filter(new uf(cls, 13)).map(new vf(cls, 13)).map(new eg(getTimeAgoParser(), 1)).filter(new mg(1)).forEach(new q5(multiInfoItemsCollector, 2));
            return multiInfoItemsCollector;
        } catch (Exception e) {
            throw new ParsingException("Could not get related videos", e);
        }
    }
}
