package org.schabi.newpipe.extractor.services.soundcloud;

import androidx.recyclerview.widget.ItemTouchHelper;
import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonParser;
import com.grack.nanojson.JsonParserException;
import j$.time.OffsetDateTime;
import j$.time.format.DateTimeFormatter;
import j$.time.format.DateTimeParseException;
import j$.util.Collection$EL;
import j$.util.Objects;
import j$.util.stream.Collectors;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.MultiInfoItemsCollector;
import org.schabi.newpipe.extractor.NewPipe;
import org.schabi.newpipe.extractor.ServiceList;
import org.schabi.newpipe.extractor.channel.ChannelInfoItemsCollector;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.downloader.Response;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.exceptions.ReCaptchaException;
import org.schabi.newpipe.extractor.localization.DateWrapper;
import org.schabi.newpipe.extractor.services.soundcloud.extractors.SoundcloudChannelInfoItemExtractor;
import org.schabi.newpipe.extractor.services.soundcloud.extractors.SoundcloudStreamInfoItemExtractor;
import org.schabi.newpipe.extractor.stream.StreamInfoItemExtractor;
import org.schabi.newpipe.extractor.stream.StreamInfoItemsCollector;
import org.schabi.newpipe.extractor.utils.ImageSuffix;
import org.schabi.newpipe.extractor.utils.JsonUtils;
import org.schabi.newpipe.extractor.utils.Parser;
import org.schabi.newpipe.extractor.utils.Utils;

public final class SoundcloudParsingHelper {
    public static final List<ImageSuffix> a;
    public static final List<ImageSuffix> b;
    public static String c;
    public static final Pattern d = Pattern.compile("^https?://on.soundcloud.com/[0-9a-zA-Z]+$");

    static {
        Image.ResolutionLevel resolutionLevel = Image.ResolutionLevel.LOW;
        Image.ResolutionLevel resolutionLevel2 = Image.ResolutionLevel.MEDIUM;
        ImageSuffix[] imageSuffixArr = {new ImageSuffix("mini", 16, 16, resolutionLevel), new ImageSuffix("t20x20", 20, 20, resolutionLevel), new ImageSuffix("small", 32, 32, resolutionLevel), new ImageSuffix("badge", 47, 47, resolutionLevel), new ImageSuffix("t50x50", 50, 50, resolutionLevel), new ImageSuffix("t60x60", 60, 60, resolutionLevel), new ImageSuffix("t67x67", 67, 67, resolutionLevel), new ImageSuffix("t80x80", 80, 80, resolutionLevel), new ImageSuffix("large", 100, 100, resolutionLevel), new ImageSuffix("t120x120", 120, 120, resolutionLevel), new ImageSuffix("t200x200", 200, 200, resolutionLevel2), new ImageSuffix("t240x240", 240, 240, resolutionLevel2), new ImageSuffix("t250x250", ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, resolutionLevel2), new ImageSuffix("t300x300", 300, 300, resolutionLevel2), new ImageSuffix("t500x500", 500, 500, resolutionLevel2)};
        ArrayList arrayList = new ArrayList(15);
        for (int i = 0; i < 15; i++) {
            ImageSuffix imageSuffix = imageSuffixArr[i];
            Objects.requireNonNull(imageSuffix);
            arrayList.add(imageSuffix);
        }
        a = Collections.unmodifiableList(arrayList);
        Object[] objArr = {new ImageSuffix("t1240x260", 1240, 260, resolutionLevel2), new ImageSuffix("t2480x520", 2480, 520, resolutionLevel2)};
        ArrayList arrayList2 = new ArrayList(2);
        for (int i2 = 0; i2 < 2; i2++) {
            Object obj = objArr[i2];
            Objects.requireNonNull(obj);
            arrayList2.add(obj);
        }
        b = Collections.unmodifiableList(arrayList2);
    }

    public static String a(JsonObject jsonObject) {
        try {
            String string = jsonObject.getString("next_href");
            if (string.contains("client_id=")) {
                return string;
            }
            return string + "&client_id=" + clientId();
        } catch (Exception unused) {
            return "";
        }
    }

    public static synchronized String clientId() throws ExtractionException, IOException {
        synchronized (SoundcloudParsingHelper.class) {
            if (!Utils.isNullOrEmpty(c)) {
                String str = c;
                return str;
            }
            Downloader downloader = NewPipe.getDownloader();
            Elements select = Jsoup.parse(downloader.get("https://soundcloud.com").responseBody()).select("script[src*=\"sndcdn.com/assets/\"][src$=\".js\"]");
            Collections.reverse(select);
            ArrayList arrayList = new ArrayList(1);
            Object obj = new Object[]{"bytes=0-50000"}[0];
            Objects.requireNonNull(obj);
            arrayList.add(obj);
            Map r = y2.r(Collections.unmodifiableList(arrayList));
            Iterator it = select.iterator();
            while (it.hasNext()) {
                String attr = ((Element) it.next()).attr("src");
                if (!Utils.isNullOrEmpty(attr)) {
                    try {
                        String matchGroup1 = Parser.matchGroup1(",client_id:\"(.*?)\"", downloader.get(attr, (Map<String, List<String>>) r).responseBody());
                        c = matchGroup1;
                        return matchGroup1;
                    } catch (Parser.RegexException unused) {
                        continue;
                    }
                }
            }
            throw new ExtractionException("Couldn't extract client id");
        }
    }

    public static List<Image> getAllImagesFromArtworkOrAvatarUrl(String str) {
        if (Utils.isNullOrEmpty(str)) {
            return Collections.emptyList();
        }
        return (List) Collection$EL.stream(a).map(new cc(str.replace("-large.", "-%s."), 3)).collect(Collectors.toUnmodifiableList());
    }

    public static List<Image> getAllImagesFromTrackObject(JsonObject jsonObject) throws ParsingException {
        String string = jsonObject.getString("artwork_url");
        if (string != null) {
            return getAllImagesFromArtworkOrAvatarUrl(string);
        }
        String string2 = jsonObject.getObject("user").getString("avatar_url");
        if (string2 != null) {
            return getAllImagesFromArtworkOrAvatarUrl(string2);
        }
        throw new ParsingException("Could not get track or track user's thumbnails");
    }

    public static List<Image> getAllImagesFromVisualUrl(String str) {
        if (Utils.isNullOrEmpty(str)) {
            return Collections.emptyList();
        }
        return (List) Collection$EL.stream(b).map(new cc(str.replace("-original.", "-%s."), 3)).collect(Collectors.toUnmodifiableList());
    }

    public static String getAvatarUrl(JsonObject jsonObject) {
        return Utils.replaceHttpWithHttps(jsonObject.getObject("user").getString("avatar_url", ""));
    }

    public static String getInfoItemsFromApi(MultiInfoItemsCollector multiInfoItemsCollector, String str) throws ReCaptchaException, ParsingException, IOException {
        Response response = NewPipe.getDownloader().get(str, ServiceList.b.getLocalization());
        if (response.responseCode() < 400) {
            try {
                JsonObject from = JsonParser.object().from(response.responseBody());
                Class<JsonObject> cls = JsonObject.class;
                y2.z(cls, 11, y2.s(cls, 11, Collection$EL.stream(from.getArray("collection")))).forEach(new q5(multiInfoItemsCollector, 1));
                try {
                    String string = from.getString("next_href");
                    if (string.contains("client_id=")) {
                        return string;
                    }
                    return string + "&client_id=" + clientId();
                } catch (Exception unused) {
                    return "";
                }
            } catch (JsonParserException e) {
                throw new ParsingException("Could not parse json response", e);
            }
        } else {
            throw new IOException("Could not get streams from API, HTTP " + response.responseCode());
        }
    }

    public static String getStreamsFromApi(StreamInfoItemsCollector streamInfoItemsCollector, String str, boolean z) throws IOException, ReCaptchaException, ParsingException {
        Response response = NewPipe.getDownloader().get(str, ServiceList.b.getLocalization());
        if (response.responseCode() < 400) {
            try {
                JsonObject from = JsonParser.object().from(response.responseBody());
                Iterator it = from.getArray("collection").iterator();
                while (it.hasNext()) {
                    Object next = it.next();
                    if (next instanceof JsonObject) {
                        JsonObject jsonObject = (JsonObject) next;
                        if (z) {
                            jsonObject = jsonObject.getObject("track");
                        }
                        streamInfoItemsCollector.commit((StreamInfoItemExtractor) new SoundcloudStreamInfoItemExtractor(jsonObject));
                    }
                }
                return a(from);
            } catch (JsonParserException e) {
                throw new ParsingException("Could not parse json response", e);
            }
        } else {
            throw new IOException("Could not get streams from API, HTTP " + response.responseCode());
        }
    }

    public static String getStreamsFromApiMinItems(int i, StreamInfoItemsCollector streamInfoItemsCollector, String str) throws IOException, ReCaptchaException, ParsingException {
        String streamsFromApi = getStreamsFromApi(streamInfoItemsCollector, str);
        while (!streamsFromApi.isEmpty() && streamInfoItemsCollector.getItems().size() < i) {
            streamsFromApi = getStreamsFromApi(streamInfoItemsCollector, streamsFromApi);
        }
        return streamsFromApi;
    }

    public static String getUploaderName(JsonObject jsonObject) {
        return jsonObject.getObject("user").getString("username", "");
    }

    public static String getUploaderUrl(JsonObject jsonObject) {
        return Utils.replaceHttpWithHttps(jsonObject.getObject("user").getString("permalink_url", ""));
    }

    public static String getUsersFromApi(ChannelInfoItemsCollector channelInfoItemsCollector, String str) throws IOException, ReCaptchaException, ParsingException {
        try {
            JsonObject from = JsonParser.object().from(NewPipe.getDownloader().get(str, ServiceList.b.getLocalization()).responseBody());
            Iterator it = from.getArray("collection").iterator();
            while (it.hasNext()) {
                Object next = it.next();
                if (next instanceof JsonObject) {
                    channelInfoItemsCollector.commit(new SoundcloudChannelInfoItemExtractor((JsonObject) next));
                }
            }
            return a(from);
        } catch (JsonParserException e) {
            throw new ParsingException("Could not parse json response", e);
        }
    }

    public static String getUsersFromApiMinItems(int i, ChannelInfoItemsCollector channelInfoItemsCollector, String str) throws IOException, ReCaptchaException, ParsingException {
        String usersFromApi = getUsersFromApi(channelInfoItemsCollector, str);
        while (!usersFromApi.isEmpty() && channelInfoItemsCollector.getItems().size() < i) {
            usersFromApi = getUsersFromApi(channelInfoItemsCollector, usersFromApi);
        }
        return usersFromApi;
    }

    public static DateWrapper parseDate(String str) throws ParsingException {
        try {
            return DateWrapper.fromInstant(str);
        } catch (DateTimeParseException e) {
            try {
                return new DateWrapper(OffsetDateTime.parse(str, DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss +0000")));
            } catch (DateTimeParseException e2) {
                e2.addSuppressed(e);
                throw new ParsingException(y2.j("Could not parse date: \"", str, "\""), e2);
            }
        }
    }

    public static JsonObject resolveFor(Downloader downloader, String str) throws IOException, ExtractionException {
        try {
            return JsonParser.object().from(downloader.get("https://api-v2.soundcloud.com/resolve?url=" + Utils.encodeUrlUtf8(str) + "&client_id=" + clientId(), ServiceList.b.getLocalization()).responseBody());
        } catch (JsonParserException e) {
            throw new ParsingException("Could not parse json response", e);
        }
    }

    public static String resolveIdWithWidgetApi(String str) throws IOException, ParsingException {
        if (d.matcher(str).find()) {
            try {
                str = NewPipe.getDownloader().head(str).latestUrl().split("\\?")[0];
            } catch (ExtractionException e) {
                throw new ParsingException("Could not follow on.soundcloud.com redirect", e);
            }
        }
        if (str.charAt(str.length() - 1) == '/') {
            str = str.substring(0, str.length() - 1);
        }
        try {
            URL stringToURL = Utils.stringToURL(Utils.removeMAndWWWFromUrl(str.toLowerCase()));
            try {
                return String.valueOf(JsonUtils.getValue(JsonParser.object().from(NewPipe.getDownloader().get("https://api-widget.soundcloud.com/resolve?url=" + Utils.encodeUrlUtf8(stringToURL.toString()) + "&format=json&client_id=" + clientId(), ServiceList.b.getLocalization()).responseBody()), "id"));
            } catch (JsonParserException e2) {
                throw new ParsingException("Could not parse JSON response", e2);
            } catch (ExtractionException e3) {
                throw new ParsingException("Could not resolve id with embedded player. ClientId not extracted", e3);
            }
        } catch (MalformedURLException unused) {
            throw new IllegalArgumentException("The given URL is not valid");
        }
    }

    public static String resolveUrlWithEmbedPlayer(String str) throws IOException, ReCaptchaException {
        Downloader downloader = NewPipe.getDownloader();
        return Jsoup.parse(downloader.get("https://w.soundcloud.com/player/?url=" + Utils.encodeUrlUtf8(str), ServiceList.b.getLocalization()).responseBody()).select("link[rel=\"canonical\"]").first().attr("abs:href");
    }

    public static String getStreamsFromApi(StreamInfoItemsCollector streamInfoItemsCollector, String str) throws ReCaptchaException, ParsingException, IOException {
        return getStreamsFromApi(streamInfoItemsCollector, str, false);
    }
}
