package org.schabi.newpipe.extractor.channel;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.Info;
import org.schabi.newpipe.extractor.NewPipe;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;

public class ChannelInfo extends Info {
    public String k;
    public String l;
    public String m;
    public long n = -1;
    public String o;
    public String[] p;
    public List<Image> q = Collections.emptyList();
    public List<Image> r = Collections.emptyList();
    public List<Image> s = Collections.emptyList();
    public boolean t;
    public List<ListLinkHandler> u = Collections.emptyList();
    public List<String> v = Collections.emptyList();

    public ChannelInfo(int i, String str, String str2, String str3, String str4) {
        super(i, str, str2, str3, str4);
    }

    public static ChannelInfo getInfo(String str) throws IOException, ExtractionException {
        return getInfo(NewPipe.getServiceByUrl(str), str);
    }

    public List<Image> getAvatars() {
        return this.q;
    }

    public List<Image> getBanners() {
        return this.r;
    }

    public String getDescription() {
        return this.o;
    }

    public String[] getDonationLinks() {
        return this.p;
    }

    public String getFeedUrl() {
        return this.m;
    }

    public List<Image> getParentChannelAvatars() {
        return this.s;
    }

    public String getParentChannelName() {
        return this.k;
    }

    public String getParentChannelUrl() {
        return this.l;
    }

    public long getSubscriberCount() {
        return this.n;
    }

    public List<ListLinkHandler> getTabs() {
        return this.u;
    }

    public List<String> getTags() {
        return this.v;
    }

    public boolean isVerified() {
        return this.t;
    }

    public void setAvatars(List<Image> list) {
        this.q = list;
    }

    public void setBanners(List<Image> list) {
        this.r = list;
    }

    public void setDescription(String str) {
        this.o = str;
    }

    public void setDonationLinks(String[] strArr) {
        this.p = strArr;
    }

    public void setFeedUrl(String str) {
        this.m = str;
    }

    public void setParentChannelAvatars(List<Image> list) {
        this.s = list;
    }

    public void setParentChannelName(String str) {
        this.k = str;
    }

    public void setParentChannelUrl(String str) {
        this.l = str;
    }

    public void setSubscriberCount(long j) {
        this.n = j;
    }

    public void setTabs(List<ListLinkHandler> list) {
        this.u = list;
    }

    public void setTags(List<String> list) {
        this.v = list;
    }

    public void setVerified(boolean z) {
        this.t = z;
    }

    public static ChannelInfo getInfo(StreamingService streamingService, String str) throws IOException, ExtractionException {
        ChannelExtractor channelExtractor = streamingService.getChannelExtractor(str);
        channelExtractor.fetchPage();
        return getInfo(channelExtractor);
    }

    public static ChannelInfo getInfo(ChannelExtractor channelExtractor) throws IOException, ExtractionException {
        ChannelInfo channelInfo = new ChannelInfo(channelExtractor.getServiceId(), channelExtractor.getId(), channelExtractor.getUrl(), channelExtractor.getOriginalUrl(), channelExtractor.getName());
        try {
            channelInfo.setAvatars(channelExtractor.getAvatars());
        } catch (Exception e) {
            channelInfo.addError(e);
        }
        try {
            channelInfo.setBanners(channelExtractor.getBanners());
        } catch (Exception e2) {
            channelInfo.addError(e2);
        }
        try {
            channelInfo.setFeedUrl(channelExtractor.getFeedUrl());
        } catch (Exception e3) {
            channelInfo.addError(e3);
        }
        try {
            channelInfo.setSubscriberCount(channelExtractor.getSubscriberCount());
        } catch (Exception e4) {
            channelInfo.addError(e4);
        }
        try {
            channelInfo.setDescription(channelExtractor.getDescription());
        } catch (Exception e5) {
            channelInfo.addError(e5);
        }
        try {
            channelInfo.setParentChannelName(channelExtractor.getParentChannelName());
        } catch (Exception e6) {
            channelInfo.addError(e6);
        }
        try {
            channelInfo.setParentChannelUrl(channelExtractor.getParentChannelUrl());
        } catch (Exception e7) {
            channelInfo.addError(e7);
        }
        try {
            channelInfo.setParentChannelAvatars(channelExtractor.getParentChannelAvatars());
        } catch (Exception e8) {
            channelInfo.addError(e8);
        }
        try {
            channelInfo.setVerified(channelExtractor.isVerified());
        } catch (Exception e9) {
            channelInfo.addError(e9);
        }
        try {
            channelInfo.setTabs(channelExtractor.getTabs());
        } catch (Exception e10) {
            channelInfo.addError(e10);
        }
        try {
            channelInfo.setTags(channelExtractor.getTags());
        } catch (Exception e11) {
            channelInfo.addError(e11);
        }
        return channelInfo;
    }
}
