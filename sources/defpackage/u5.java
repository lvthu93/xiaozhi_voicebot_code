package defpackage;

import java.util.List;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.channel.ChannelInfoItem;
import org.schabi.newpipe.extractor.channel.ChannelInfoItemExtractor;

/* renamed from: u5  reason: default package */
public final class u5 implements ChannelInfoItemExtractor {
    public final /* synthetic */ ChannelInfoItem a;

    public u5(ChannelInfoItem channelInfoItem) {
        this.a = channelInfoItem;
    }

    public String getDescription() {
        return this.a.getDescription();
    }

    public String getName() {
        return this.a.getName();
    }

    public long getStreamCount() {
        return this.a.getStreamCount();
    }

    public long getSubscriberCount() {
        return this.a.getSubscriberCount();
    }

    public List<Image> getThumbnails() {
        return this.a.getThumbnails();
    }

    public String getUrl() {
        return this.a.getUrl();
    }

    public boolean isVerified() {
        return false;
    }
}
