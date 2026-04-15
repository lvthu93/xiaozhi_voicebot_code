package org.schabi.newpipe.extractor.services.youtube.linkHandler;

import java.net.URL;
import java.util.List;
import java.util.regex.Pattern;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandlerFactory;
import org.schabi.newpipe.extractor.services.youtube.YoutubeParsingHelper;
import org.schabi.newpipe.extractor.utils.Utils;

public final class YoutubeChannelLinkHandlerFactory extends ListLinkHandlerFactory {
    public static final YoutubeChannelLinkHandlerFactory a = new YoutubeChannelLinkHandlerFactory();
    public static final Pattern b = Pattern.compile("playlist|watch|attribution_link|watch_popup|embed|feed|select_site|account|reporthistory|redirect");

    public static YoutubeChannelLinkHandlerFactory getInstance() {
        return a;
    }

    public String getId(String str) throws ParsingException, UnsupportedOperationException {
        boolean z;
        boolean z2;
        try {
            URL stringToURL = Utils.stringToURL(str);
            String path = stringToURL.getPath();
            if (!Utils.isHTTP(stringToURL) || (!YoutubeParsingHelper.isYoutubeURL(stringToURL) && !YoutubeParsingHelper.isInvidiousURL(stringToURL) && !YoutubeParsingHelper.isHooktubeURL(stringToURL))) {
                throw new ParsingException("The URL given is not a YouTube URL");
            }
            String substring = path.substring(1);
            String[] split = substring.split(MqttTopic.TOPIC_LEVEL_SEPARATOR);
            if (split.length <= 0 || !split[0].startsWith("@")) {
                z = false;
            } else {
                z = true;
            }
            if (z) {
                return split[0];
            }
            if (split.length != 1 || b.matcher(split[0]).matches()) {
                z2 = false;
            } else {
                z2 = true;
            }
            if (z2) {
                substring = "c/".concat(substring);
                split = substring.split(MqttTopic.TOPIC_LEVEL_SEPARATOR);
            }
            if (!substring.startsWith("user/") && !substring.startsWith("channel/")) {
                if (!substring.startsWith("c/")) {
                    throw new ParsingException("The given URL is not a channel, a user or a handle URL");
                }
            }
            String str2 = split[1];
            if (!Utils.isBlank(str2)) {
                return split[0] + MqttTopic.TOPIC_LEVEL_SEPARATOR + str2;
            }
            throw new ParsingException("The given ID is not a YouTube channel or user ID");
        } catch (Exception e) {
            throw new ParsingException("Could not parse URL :" + e.getMessage(), e);
        }
    }

    public String getUrl(String str, List<String> list, String str2) throws ParsingException, UnsupportedOperationException {
        return y2.i("https://www.youtube.com/", str);
    }

    public boolean onAcceptUrl(String str) {
        try {
            getId(str);
            return true;
        } catch (ParsingException unused) {
            return false;
        }
    }
}
