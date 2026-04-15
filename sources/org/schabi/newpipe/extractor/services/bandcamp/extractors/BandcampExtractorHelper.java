package org.schabi.newpipe.extractor.services.bandcamp.extractors;

import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonParser;
import com.grack.nanojson.JsonParserException;
import com.grack.nanojson.JsonStringWriter;
import com.grack.nanojson.JsonWriter;
import j$.time.DateTimeException;
import j$.time.ZonedDateTime;
import j$.time.format.DateTimeFormatter;
import j$.util.Collection$EL;
import j$.util.Objects;
import j$.util.stream.Collectors;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.mozilla.javascript.Context;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.NewPipe;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.exceptions.ReCaptchaException;
import org.schabi.newpipe.extractor.localization.DateWrapper;
import org.schabi.newpipe.extractor.utils.ImageSuffix;
import org.schabi.newpipe.extractor.utils.Utils;

public final class BandcampExtractorHelper {
    public static final List<ImageSuffix> a;

    static {
        Image.ResolutionLevel resolutionLevel = Image.ResolutionLevel.HIGH;
        Image.ResolutionLevel resolutionLevel2 = Image.ResolutionLevel.LOW;
        Image.ResolutionLevel resolutionLevel3 = Image.ResolutionLevel.MEDIUM;
        ImageSuffix[] imageSuffixArr = {new ImageSuffix("10.jpg", -1, 1200, resolutionLevel), new ImageSuffix("101.jpg", 90, -1, resolutionLevel2), new ImageSuffix("170.jpg", 422, -1, resolutionLevel3), new ImageSuffix("171.jpg", 646, -1, resolutionLevel3), new ImageSuffix("20.jpg", -1, 1024, resolutionLevel), new ImageSuffix("200.jpg", 420, -1, resolutionLevel3), new ImageSuffix("201.jpg", 280, -1, resolutionLevel3), new ImageSuffix("202.jpg", 140, -1, resolutionLevel2), new ImageSuffix("204.jpg", 360, -1, resolutionLevel3), new ImageSuffix("205.jpg", 240, -1, resolutionLevel3), new ImageSuffix("206.jpg", Context.VERSION_1_8, -1, resolutionLevel3), new ImageSuffix("207.jpg", 120, -1, resolutionLevel2), new ImageSuffix("43.jpg", 100, -1, resolutionLevel2), new ImageSuffix("44.jpg", 200, -1, resolutionLevel3)};
        ArrayList arrayList = new ArrayList(14);
        for (int i = 0; i < 14; i++) {
            ImageSuffix imageSuffix = imageSuffixArr[i];
            Objects.requireNonNull(imageSuffix);
            arrayList.add(imageSuffix);
        }
        a = Collections.unmodifiableList(arrayList);
    }

    public static JsonObject getArtistDetails(String str) throws ParsingException {
        try {
            return JsonParser.object().from(NewPipe.getDownloader().postWithContentTypeJson("https://bandcamp.com/api/mobile/22/band_details", Collections.emptyMap(), ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) JsonWriter.string().object()).value("band_id", str)).end()).done().getBytes(StandardCharsets.UTF_8)).responseBody());
        } catch (JsonParserException | IOException | ReCaptchaException e) {
            throw new ParsingException("Could not download band details", e);
        }
    }

    public static String getImageUrl(long j, boolean z) {
        StringBuilder sb = new StringBuilder("https://f4.bcbits.com/img/");
        sb.append(z ? 'a' : "");
        sb.append(j);
        sb.append("_10.jpg");
        return sb.toString();
    }

    public static List<Image> getImagesFromImageId(long j, boolean z) {
        char c;
        if (j == 0) {
            return Collections.emptyList();
        }
        StringBuilder sb = new StringBuilder("https://f4.bcbits.com/img/");
        if (z) {
            c = 'a';
        } else {
            c = "";
        }
        sb.append(c);
        sb.append(j);
        sb.append("_");
        return (List) Collection$EL.stream(a).map(new cc(sb.toString(), 0)).collect(Collectors.toUnmodifiableList());
    }

    public static List<Image> getImagesFromImageUrl(String str) {
        if (Utils.isNullOrEmpty(str)) {
            return Collections.emptyList();
        }
        return (List) Collection$EL.stream(a).map(new cc(str.replaceFirst("_\\d+\\.\\w+", "_"), 0)).collect(Collectors.toUnmodifiableList());
    }

    public static List<Image> getImagesFromSearchResult(Element element) {
        return getImagesFromImageUrl((String) Collection$EL.stream(element.getElementsByClass("art")).flatMap(new z5(10)).map(new z5(11)).filter(new bz(2)).findFirst().orElse(""));
    }

    public static String getStreamUrlFromIds(long j, long j2, String str) throws ParsingException {
        try {
            Downloader downloader = NewPipe.getDownloader();
            return Utils.replaceHttpWithHttps(JsonParser.object().from(downloader.get("https://bandcamp.com/api/mobile/22/tralbum_details?band_id=" + j + "&tralbum_id=" + j2 + "&tralbum_type=" + str.charAt(0)).responseBody()).getString("bandcamp_url"));
        } catch (JsonParserException | IOException | ReCaptchaException e) {
            throw new ParsingException("Ids could not be translated to URL", e);
        }
    }

    public static boolean isArtistDomain(String str) throws ParsingException {
        if (str.toLowerCase().matches("https?://.+\\.bandcamp\\.com(/.*)?")) {
            return true;
        }
        if (str.toLowerCase().matches("https?://bandcamp\\.com(/.*)?")) {
            return false;
        }
        try {
            return ((Element) ((Element) Jsoup.parse(NewPipe.getDownloader().get(Utils.replaceHttpWithHttps(str)).responseBody()).getElementsByClass("cart-wrapper").get(0)).getElementsByTag("a").get(0)).attr("href").equals("https://bandcamp.com/cart");
        } catch (IndexOutOfBoundsException | NullPointerException unused) {
            return false;
        } catch (IOException | ReCaptchaException unused2) {
            throw new ParsingException("Could not determine whether URL is custom domain (not available? network error?)");
        }
    }

    public static boolean isRadioUrl(String str) {
        return str.toLowerCase().matches("https?://bandcamp\\.com/\\?show=\\d+");
    }

    public static DateWrapper parseDate(String str) throws ParsingException {
        try {
            return new DateWrapper(ZonedDateTime.parse(str, DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH)).toInstant());
        } catch (DateTimeException e) {
            throw new ParsingException(y2.j("Could not parse date '", str, "'"), e);
        }
    }
}
