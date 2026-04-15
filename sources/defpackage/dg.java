package defpackage;

import java.util.regex.Pattern;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.schabi.newpipe.extractor.NewPipe;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.localization.Localization;
import org.schabi.newpipe.extractor.utils.Parser;

/* renamed from: dg  reason: default package */
public final class dg {
    public static final Pattern a = Pattern.compile("player\\\\/([a-z0-9]{8})\\\\/");
    public static final Pattern b = Pattern.compile("\"jsUrl\":\"(/s/player/[A-Za-z0-9]+/player_ias\\.vflset/[A-Za-z_-]+/base\\.js)\"");

    public static String a(String str) {
        if (str.startsWith("//")) {
            return "https:".concat(str);
        }
        if (str.startsWith(MqttTopic.TOPIC_LEVEL_SEPARATOR)) {
            return "https://www.youtube.com".concat(str);
        }
        return str;
    }

    public static String b() throws ParsingException {
        try {
            try {
                return String.format("https://www.youtube.com/s/player/%s/player_ias.vflset/en_GB/base.js", new Object[]{Parser.matchGroup1(a, NewPipe.getDownloader().get("https://www.youtube.com/iframe_api", Localization.g).responseBody())});
            } catch (Parser.RegexException e) {
                throw new ParsingException("IFrame resource didn't provide JavaScript base player's hash", e);
            }
        } catch (Exception e2) {
            throw new ParsingException("Could not fetch IFrame resource", e2);
        }
    }
}
