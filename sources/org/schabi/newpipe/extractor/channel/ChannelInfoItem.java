package org.schabi.newpipe.extractor.channel;

import org.schabi.newpipe.extractor.InfoItem;

public class ChannelInfoItem extends InfoItem {
    public String j;
    public long k = -1;
    public long l = -1;
    public boolean m = false;

    public ChannelInfoItem(int i, String str, String str2) {
        super(InfoItem.InfoType.CHANNEL, i, str, str2);
    }

    public String getDescription() {
        return this.j;
    }

    public long getStreamCount() {
        return this.l;
    }

    public long getSubscriberCount() {
        return this.k;
    }

    public boolean isVerified() {
        return this.m;
    }

    public void setDescription(String str) {
        this.j = str;
    }

    public void setStreamCount(long j2) {
        this.l = j2;
    }

    public void setSubscriberCount(long j2) {
        this.k = j2;
    }

    public void setVerified(boolean z) {
        this.m = z;
    }
}
