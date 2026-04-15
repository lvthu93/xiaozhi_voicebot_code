package org.schabi.newpipe.extractor.playlist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.ListExtractor;
import org.schabi.newpipe.extractor.ListInfo;
import org.schabi.newpipe.extractor.NewPipe;
import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.schabi.newpipe.extractor.stream.Description;
import org.schabi.newpipe.extractor.stream.StreamInfoItem;
import org.schabi.newpipe.extractor.utils.ExtractorHelper;

public final class PlaylistInfo extends ListInfo<StreamInfoItem> {
    public String o = "";
    public String p = "";
    public String q;
    public String r;
    public Description s;
    public List<Image> t = Collections.emptyList();
    public List<Image> u = Collections.emptyList();
    public List<Image> v = Collections.emptyList();
    public List<Image> w = Collections.emptyList();
    public long x;
    public PlaylistType y;

    public enum PlaylistType {
        NORMAL,
        MIX_STREAM,
        MIX_MUSIC,
        MIX_GENRE
    }

    public PlaylistInfo(int i, ListLinkHandler listLinkHandler, String str) throws ParsingException {
        super(i, listLinkHandler, str);
    }

    public static PlaylistInfo getInfo(String str) throws IOException, ExtractionException {
        return getInfo(NewPipe.getServiceByUrl(str), str);
    }

    public static ListExtractor.InfoItemsPage<StreamInfoItem> getMoreItems(StreamingService streamingService, String str, Page page) throws IOException, ExtractionException {
        return streamingService.getPlaylistExtractor(str).getPage(page);
    }

    public List<Image> getBanners() {
        return this.t;
    }

    public Description getDescription() {
        return this.s;
    }

    public PlaylistType getPlaylistType() {
        return this.y;
    }

    public long getStreamCount() {
        return this.x;
    }

    public List<Image> getSubChannelAvatars() {
        return this.u;
    }

    public String getSubChannelName() {
        return this.r;
    }

    public String getSubChannelUrl() {
        return this.q;
    }

    public List<Image> getThumbnails() {
        return this.v;
    }

    public List<Image> getUploaderAvatars() {
        return this.w;
    }

    public String getUploaderName() {
        return this.p;
    }

    public String getUploaderUrl() {
        return this.o;
    }

    public void setBanners(List<Image> list) {
        this.t = list;
    }

    public void setDescription(Description description) {
        this.s = description;
    }

    public void setPlaylistType(PlaylistType playlistType) {
        this.y = playlistType;
    }

    public void setStreamCount(long j) {
        this.x = j;
    }

    public void setSubChannelAvatars(List<Image> list) {
        this.u = list;
    }

    public void setSubChannelName(String str) {
        this.r = str;
    }

    public void setSubChannelUrl(String str) {
        this.q = str;
    }

    public void setThumbnails(List<Image> list) {
        this.v = list;
    }

    public void setUploaderAvatars(List<Image> list) {
        this.w = list;
    }

    public void setUploaderName(String str) {
        this.p = str;
    }

    public void setUploaderUrl(String str) {
        this.o = str;
    }

    public static PlaylistInfo getInfo(StreamingService streamingService, String str) throws IOException, ExtractionException {
        PlaylistExtractor playlistExtractor = streamingService.getPlaylistExtractor(str);
        playlistExtractor.fetchPage();
        return getInfo(playlistExtractor);
    }

    public static PlaylistInfo getInfo(PlaylistExtractor playlistExtractor) throws ExtractionException {
        PlaylistInfo playlistInfo = new PlaylistInfo(playlistExtractor.getServiceId(), playlistExtractor.getLinkHandler(), playlistExtractor.getName());
        ArrayList arrayList = new ArrayList();
        try {
            playlistInfo.setOriginalUrl(playlistExtractor.getOriginalUrl());
        } catch (Exception e) {
            playlistInfo.addError(e);
        }
        try {
            playlistInfo.setStreamCount(playlistExtractor.getStreamCount());
        } catch (Exception e2) {
            playlistInfo.addError(e2);
        }
        try {
            playlistInfo.setDescription(playlistExtractor.getDescription());
        } catch (Exception e3) {
            playlistInfo.addError(e3);
        }
        try {
            playlistInfo.setThumbnails(playlistExtractor.getThumbnails());
        } catch (Exception e4) {
            playlistInfo.addError(e4);
        }
        try {
            playlistInfo.setUploaderUrl(playlistExtractor.getUploaderUrl());
        } catch (Exception e5) {
            arrayList.add(e5);
        }
        try {
            playlistInfo.setUploaderName(playlistExtractor.getUploaderName());
        } catch (Exception e6) {
            arrayList.add(e6);
        }
        try {
            playlistInfo.setUploaderAvatars(playlistExtractor.getUploaderAvatars());
        } catch (Exception e7) {
            arrayList.add(e7);
        }
        try {
            playlistInfo.setSubChannelUrl(playlistExtractor.getSubChannelUrl());
        } catch (Exception e8) {
            arrayList.add(e8);
        }
        try {
            playlistInfo.setSubChannelName(playlistExtractor.getSubChannelName());
        } catch (Exception e9) {
            arrayList.add(e9);
        }
        try {
            playlistInfo.setSubChannelAvatars(playlistExtractor.getSubChannelAvatars());
        } catch (Exception e10) {
            arrayList.add(e10);
        }
        try {
            playlistInfo.setBanners(playlistExtractor.getBanners());
        } catch (Exception e11) {
            playlistInfo.addError(e11);
        }
        try {
            playlistInfo.setPlaylistType(playlistExtractor.getPlaylistType());
        } catch (Exception e12) {
            playlistInfo.addError(e12);
        }
        if (!arrayList.isEmpty() && (!playlistInfo.getErrors().isEmpty() || arrayList.size() < 3)) {
            playlistInfo.addAllErrors(arrayList);
        }
        ListExtractor.InfoItemsPage itemsPageOrLogError = ExtractorHelper.getItemsPageOrLogError(playlistInfo, playlistExtractor);
        playlistInfo.setRelatedItems(itemsPageOrLogError.getItems());
        playlistInfo.setNextPage(itemsPageOrLogError.getNextPage());
        return playlistInfo;
    }
}
