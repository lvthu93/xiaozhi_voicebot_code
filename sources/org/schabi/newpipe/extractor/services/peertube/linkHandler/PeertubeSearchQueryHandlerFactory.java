package org.schabi.newpipe.extractor.services.peertube.linkHandler;

import java.util.List;
import org.schabi.newpipe.extractor.ServiceList;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.SearchQueryHandlerFactory;
import org.schabi.newpipe.extractor.utils.Utils;

public final class PeertubeSearchQueryHandlerFactory extends SearchQueryHandlerFactory {
    public static final PeertubeSearchQueryHandlerFactory a = new PeertubeSearchQueryHandlerFactory();

    public static PeertubeSearchQueryHandlerFactory getInstance() {
        return a;
    }

    public String[] getAvailableContentFilter() {
        return new String[]{"videos", "playlists", "channels", "sepia_videos"};
    }

    public String getUrl(String str, List<String> list, String str2) throws ParsingException, UnsupportedOperationException {
        String str3;
        if (list.isEmpty() || !list.get(0).startsWith("sepia_")) {
            str3 = ServiceList.c.getBaseUrl();
        } else {
            str3 = "https://sepiasearch.org";
        }
        return getUrl(str, list, str2, str3);
    }

    public String getUrl(String str, List<String> list, String str2, String str3) throws ParsingException, UnsupportedOperationException {
        String str4 = (list.isEmpty() || list.get(0).equals("videos") || list.get(0).equals("sepia_videos")) ? "/api/v1/search/videos" : list.get(0).equals("channels") ? "/api/v1/search/video-channels" : "/api/v1/search/video-playlists";
        return str3 + str4 + "?search=" + Utils.encodeUrlUtf8(str);
    }
}
