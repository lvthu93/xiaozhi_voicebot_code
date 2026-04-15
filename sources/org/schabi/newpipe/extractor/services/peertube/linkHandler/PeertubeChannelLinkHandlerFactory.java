package org.schabi.newpipe.extractor.services.peertube.linkHandler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.schabi.newpipe.extractor.ServiceList;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandlerFactory;
import org.schabi.newpipe.extractor.utils.Parser;

public final class PeertubeChannelLinkHandlerFactory extends ListLinkHandlerFactory {
    public static final PeertubeChannelLinkHandlerFactory a = new PeertubeChannelLinkHandlerFactory();

    public static String a(String str) {
        if (str.startsWith(MqttTopic.TOPIC_LEVEL_SEPARATOR)) {
            str = str.substring(1);
        }
        if (str.startsWith("a/")) {
            return "accounts" + str.substring(1);
        } else if (!str.startsWith("c/")) {
            return str;
        } else {
            return "video-channels" + str.substring(1);
        }
    }

    public static PeertubeChannelLinkHandlerFactory getInstance() {
        return a;
    }

    public String getId(String str) throws ParsingException, UnsupportedOperationException {
        return a(Parser.matchGroup("/((accounts|a)|(video-channels|c))/([^/?&#]*)", str, 0));
    }

    public String getUrl(String str, List<String> list, String str2) throws ParsingException, UnsupportedOperationException {
        return getUrl(str, list, str2, ServiceList.c.getBaseUrl());
    }

    public boolean onAcceptUrl(String str) {
        try {
            new URL(str);
            if (str.contains("/accounts/") || str.contains("/a/") || str.contains("/video-channels/") || str.contains("/c/")) {
                return true;
            }
            return false;
        } catch (MalformedURLException unused) {
            return false;
        }
    }

    public String getUrl(String str, List<String> list, String str2, String str3) throws ParsingException, UnsupportedOperationException {
        if (str.matches("((accounts|a)|(video-channels|c))/([^/?&#]*)")) {
            StringBuilder o = y2.o(str3, MqttTopic.TOPIC_LEVEL_SEPARATOR);
            o.append(a(str));
            return o.toString();
        }
        return str3 + "/accounts/" + str;
    }
}
