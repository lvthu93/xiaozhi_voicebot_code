package org.schabi.newpipe.extractor.services.soundcloud.extractors;

import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonParser;
import com.grack.nanojson.JsonParserException;
import j$.util.Collection$EL;
import j$.util.Objects;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.ListExtractor;
import org.schabi.newpipe.extractor.NewPipe;
import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.schabi.newpipe.extractor.playlist.PlaylistExtractor;
import org.schabi.newpipe.extractor.services.soundcloud.SoundcloudParsingHelper;
import org.schabi.newpipe.extractor.stream.Description;
import org.schabi.newpipe.extractor.stream.StreamInfoItem;
import org.schabi.newpipe.extractor.stream.StreamInfoItemExtractor;
import org.schabi.newpipe.extractor.stream.StreamInfoItemsCollector;
import org.schabi.newpipe.extractor.utils.Utils;

public class SoundcloudPlaylistExtractor extends PlaylistExtractor {
    public String g;
    public JsonObject h;

    public SoundcloudPlaylistExtractor(StreamingService streamingService, ListLinkHandler listLinkHandler) {
        super(streamingService, listLinkHandler);
    }

    public Description getDescription() throws ParsingException {
        String string = this.h.getString("description");
        if (Utils.isNullOrEmpty(string)) {
            return Description.g;
        }
        return new Description(string, 3);
    }

    public String getId() {
        return this.g;
    }

    public ListExtractor.InfoItemsPage<StreamInfoItem> getInitialPage() {
        StreamInfoItemsCollector streamInfoItemsCollector = new StreamInfoItemsCollector(getServiceId());
        ArrayList arrayList = new ArrayList();
        Class<JsonObject> cls = JsonObject.class;
        y2.z(cls, 12, y2.s(cls, 12, Collection$EL.stream(this.h.getArray("tracks")))).forEachOrdered(new hb(0, streamInfoItemsCollector, arrayList));
        return new ListExtractor.InfoItemsPage<>(streamInfoItemsCollector, new Page((List<String>) arrayList));
    }

    public String getName() {
        return this.h.getString("title");
    }

    public ListExtractor.InfoItemsPage<StreamInfoItem> getPage(Page page) throws IOException, ExtractionException {
        List<String> list;
        List<String> list2;
        if (page == null || Utils.isNullOrEmpty((Collection<?>) page.getIds())) {
            throw new IllegalArgumentException("Page doesn't contain IDs");
        }
        if (page.getIds().size() <= 15) {
            list = page.getIds();
            list2 = null;
        } else {
            List<String> subList = page.getIds().subList(0, 15);
            list2 = page.getIds().subList(15, page.getIds().size());
            list = subList;
        }
        StringBuilder sb = new StringBuilder("https://api-v2.soundcloud.com/tracks?client_id=");
        sb.append(SoundcloudParsingHelper.clientId());
        sb.append("&ids=");
        StringBuilder sb2 = new StringBuilder();
        Iterator<T> it = list.iterator();
        if (it.hasNext()) {
            while (true) {
                sb2.append((CharSequence) it.next());
                if (!it.hasNext()) {
                    break;
                }
                sb2.append(",");
            }
        }
        sb.append(sb2.toString());
        String sb3 = sb.toString();
        StreamInfoItemsCollector streamInfoItemsCollector = new StreamInfoItemsCollector(getServiceId());
        String responseBody = NewPipe.getDownloader().get(sb3, getExtractorLocalization()).responseBody();
        try {
            HashMap hashMap = new HashMap();
            Iterator it2 = JsonParser.array().from(responseBody).iterator();
            while (it2.hasNext()) {
                Object next = it2.next();
                if (next instanceof JsonObject) {
                    JsonObject jsonObject = (JsonObject) next;
                    hashMap.put(Integer.valueOf(jsonObject.getInt("id")), jsonObject);
                }
            }
            for (String parseInt : list) {
                int parseInt2 = Integer.parseInt(parseInt);
                JsonObject jsonObject2 = (JsonObject) hashMap.get(Integer.valueOf(parseInt2));
                Objects.requireNonNull(jsonObject2, "no track with id " + parseInt2 + " in response");
                streamInfoItemsCollector.commit((StreamInfoItemExtractor) new SoundcloudStreamInfoItemExtractor(jsonObject2));
            }
            return new ListExtractor.InfoItemsPage<>(streamInfoItemsCollector, new Page(list2));
        } catch (NullPointerException e) {
            throw new ParsingException("Could not parse json response", e);
        } catch (JsonParserException e2) {
            throw new ParsingException("Could not parse json response", e2);
        }
    }

    public long getStreamCount() {
        return this.h.getLong("track_count");
    }

    public List<Image> getThumbnails() {
        String string = this.h.getString("artwork_url");
        if (!Utils.isNullOrEmpty(string)) {
            return SoundcloudParsingHelper.getAllImagesFromArtworkOrAvatarUrl(string);
        }
        try {
            for (StreamInfoItem thumbnails : getInitialPage().getItems()) {
                List<Image> thumbnails2 = thumbnails.getThumbnails();
                if (!Utils.isNullOrEmpty((Collection<?>) thumbnails2)) {
                    return thumbnails2;
                }
            }
        } catch (Exception unused) {
        }
        return Collections.emptyList();
    }

    public List<Image> getUploaderAvatars() {
        return SoundcloudParsingHelper.getAllImagesFromArtworkOrAvatarUrl(SoundcloudParsingHelper.getAvatarUrl(this.h));
    }

    public String getUploaderName() {
        return SoundcloudParsingHelper.getUploaderName(this.h);
    }

    public String getUploaderUrl() {
        return SoundcloudParsingHelper.getUploaderUrl(this.h);
    }

    public boolean isUploaderVerified() throws ParsingException {
        return this.h.getObject("user").getBoolean("verified");
    }

    public void onFetchPage(Downloader downloader) throws IOException, ExtractionException {
        this.g = getLinkHandler().getId();
        try {
            this.h = JsonParser.object().from(downloader.get("https://api-v2.soundcloud.com/playlists/" + this.g + "?client_id=" + SoundcloudParsingHelper.clientId() + "&representation=compact", getExtractorLocalization()).responseBody());
        } catch (JsonParserException e) {
            throw new ParsingException("Could not parse json response", e);
        }
    }
}
