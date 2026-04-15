package org.schabi.newpipe.extractor.services.bandcamp.linkHandler;

import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonParserException;
import java.io.IOException;
import java.util.List;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.schabi.newpipe.extractor.NewPipe;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.exceptions.ReCaptchaException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandlerFactory;
import org.schabi.newpipe.extractor.services.bandcamp.extractors.BandcampExtractorHelper;
import org.schabi.newpipe.extractor.utils.JsonUtils;
import org.schabi.newpipe.extractor.utils.Utils;

public final class BandcampChannelLinkHandlerFactory extends ListLinkHandlerFactory {
    public static final BandcampChannelLinkHandlerFactory a = new BandcampChannelLinkHandlerFactory();

    public static BandcampChannelLinkHandlerFactory getInstance() {
        return a;
    }

    public String getId(String str) throws ParsingException, UnsupportedOperationException {
        try {
            return String.valueOf(JsonUtils.getJsonData(NewPipe.getDownloader().get(Utils.replaceHttpWithHttps(str)).responseBody(), "data-band").getLong("id"));
        } catch (JsonParserException | IOException | ArrayIndexOutOfBoundsException | ReCaptchaException e) {
            throw new ParsingException("Download failed", e);
        }
    }

    public String getUrl(String str, List<String> list, String str2) throws ParsingException, UnsupportedOperationException {
        JsonObject artistDetails = BandcampExtractorHelper.getArtistDetails(str);
        if (!artistDetails.getBoolean(MqttServiceConstants.TRACE_ERROR)) {
            return Utils.replaceHttpWithHttps(artistDetails.getString("bandcamp_url"));
        }
        throw new ParsingException("JSON does not contain a channel URL (invalid id?) or is otherwise invalid");
    }

    public boolean onAcceptUrl(String str) throws ParsingException {
        String lowerCase = str.toLowerCase();
        String[] split = lowerCase.split(MqttTopic.TOPIC_LEVEL_SEPARATOR);
        if (split.length != 3 && split.length != 4) {
            return false;
        }
        if ((split.length != 4 || split[3].equals("releases") || split[3].equals("music") || split[3].equals("album") || split[3].equals("track")) && !split[2].equals("daily.bandcamp.com")) {
            return BandcampExtractorHelper.isArtistDomain(lowerCase);
        }
        return false;
    }
}
