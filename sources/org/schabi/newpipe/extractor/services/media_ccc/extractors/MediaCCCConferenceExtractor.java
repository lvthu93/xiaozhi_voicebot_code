package org.schabi.newpipe.extractor.services.media_ccc.extractors;

import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonParser;
import com.grack.nanojson.JsonParserException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.channel.ChannelExtractor;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.schabi.newpipe.extractor.linkhandler.ReadyChannelTabListLinkHandler;

public class MediaCCCConferenceExtractor extends ChannelExtractor {
    public JsonObject g;

    public MediaCCCConferenceExtractor(StreamingService streamingService, ListLinkHandler listLinkHandler) {
        super(streamingService, listLinkHandler);
    }

    public static JsonObject b(Downloader downloader, String str) throws IOException, ExtractionException {
        String i = y2.i("https://api.media.ccc.de/public/conferences/", str);
        try {
            return JsonParser.object().from(downloader.get(i).responseBody());
        } catch (JsonParserException unused) {
            throw new ExtractionException(y2.i("Could not parse json returned by URL: ", i));
        }
    }

    public List<Image> getAvatars() {
        return MediaCCCParsingHelper.getImageListFromLogoImageUrl(this.g.getString("logo_url"));
    }

    public List<Image> getBanners() {
        return Collections.emptyList();
    }

    public String getDescription() {
        return null;
    }

    public String getFeedUrl() {
        return null;
    }

    public String getName() throws ParsingException {
        return this.g.getString("title");
    }

    public List<Image> getParentChannelAvatars() {
        return Collections.emptyList();
    }

    public String getParentChannelName() {
        return "";
    }

    public String getParentChannelUrl() {
        return "";
    }

    public long getSubscriberCount() {
        return -1;
    }

    public List<ListLinkHandler> getTabs() throws ParsingException {
        Object[] objArr = {new ReadyChannelTabListLinkHandler(getUrl(), getId(), "videos", new r5(this.g))};
        ArrayList arrayList = new ArrayList(1);
        Object obj = objArr[0];
        return y2.p(obj, arrayList, obj, arrayList);
    }

    public boolean isVerified() {
        return false;
    }

    public void onFetchPage(Downloader downloader) throws IOException, ExtractionException {
        this.g = b(downloader, getId());
    }
}
