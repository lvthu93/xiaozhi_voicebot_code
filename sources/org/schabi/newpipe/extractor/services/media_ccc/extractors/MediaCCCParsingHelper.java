package org.schabi.newpipe.extractor.services.media_ccc.extractors;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonParser;
import com.grack.nanojson.JsonParserException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ReCaptchaException;
import org.schabi.newpipe.extractor.localization.Localization;
import org.schabi.newpipe.extractor.utils.Utils;

public final class MediaCCCParsingHelper {
    public static final Pattern a = Pattern.compile("\\w+/\\w+");
    public static JsonArray b = null;

    public static List<Image> a(JsonObject jsonObject, String str, String str2) {
        ArrayList arrayList = new ArrayList(2);
        String string = jsonObject.getString(str);
        if (!Utils.isNullOrEmpty(string)) {
            arrayList.add(new Image(string, -1, -1, Image.ResolutionLevel.MEDIUM));
        }
        String string2 = jsonObject.getString(str2);
        if (!Utils.isNullOrEmpty(string2)) {
            arrayList.add(new Image(string2, -1, -1, Image.ResolutionLevel.HIGH));
        }
        return Collections.unmodifiableList(arrayList);
    }

    public static List<Image> getImageListFromLogoImageUrl(String str) {
        if (Utils.isNullOrEmpty(str)) {
            return Collections.emptyList();
        }
        Object[] objArr = {new Image(str, -1, -1, Image.ResolutionLevel.UNKNOWN)};
        ArrayList arrayList = new ArrayList(1);
        Object obj = objArr[0];
        return y2.p(obj, arrayList, obj, arrayList);
    }

    public static JsonArray getLiveStreams(Downloader downloader, Localization localization) throws ExtractionException {
        if (b == null) {
            try {
                b = JsonParser.array().from(downloader.get("https://streaming.media.ccc.de/streams/v2.json", localization).responseBody());
            } catch (IOException | ReCaptchaException e) {
                throw new ExtractionException("Could not get live stream JSON.", e);
            } catch (JsonParserException e2) {
                throw new ExtractionException("Could not parse JSON.", e2);
            }
        }
        return b;
    }

    public static List<Image> getThumbnailsFromLiveStreamItem(JsonObject jsonObject) {
        return a(jsonObject, "thumb", "poster");
    }

    public static List<Image> getThumbnailsFromStreamItem(JsonObject jsonObject) {
        return a(jsonObject, "thumb_url", "poster_url");
    }

    public static boolean isLiveStreamId(String str) {
        return a.matcher(str).find();
    }
}
