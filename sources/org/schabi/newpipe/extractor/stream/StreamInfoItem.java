package org.schabi.newpipe.extractor.stream;

import java.util.Collections;
import java.util.List;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.InfoItem;
import org.schabi.newpipe.extractor.localization.DateWrapper;

public class StreamInfoItem extends InfoItem {
    public final StreamType j;
    public String k;
    public String l;
    public String m;
    public DateWrapper n;
    public long o = -1;
    public long p = -1;
    public String q = null;
    public List<Image> r = Collections.emptyList();
    public boolean s = false;
    public boolean t = false;
    public ContentAvailability u = ContentAvailability.AVAILABLE;

    public StreamInfoItem(int i, String str, String str2, StreamType streamType) {
        super(InfoItem.InfoType.STREAM, i, str, str2);
        this.j = streamType;
    }

    public ContentAvailability getContentAvailability() {
        return this.u;
    }

    public long getDuration() {
        return this.p;
    }

    public String getShortDescription() {
        return this.l;
    }

    public StreamType getStreamType() {
        return this.j;
    }

    public String getTextualUploadDate() {
        return this.m;
    }

    public DateWrapper getUploadDate() {
        return this.n;
    }

    public List<Image> getUploaderAvatars() {
        return this.r;
    }

    public String getUploaderName() {
        return this.k;
    }

    public String getUploaderUrl() {
        return this.q;
    }

    public long getViewCount() {
        return this.o;
    }

    public boolean isShortFormContent() {
        return this.t;
    }

    public boolean isUploaderVerified() {
        return this.s;
    }

    public void setContentAvailability(ContentAvailability contentAvailability) {
        this.u = contentAvailability;
    }

    public void setDuration(long j2) {
        this.p = j2;
    }

    public void setShortDescription(String str) {
        this.l = str;
    }

    public void setShortFormContent(boolean z) {
        this.t = z;
    }

    public void setTextualUploadDate(String str) {
        this.m = str;
    }

    public void setUploadDate(DateWrapper dateWrapper) {
        this.n = dateWrapper;
    }

    public void setUploaderAvatars(List<Image> list) {
        this.r = list;
    }

    public void setUploaderName(String str) {
        this.k = str;
    }

    public void setUploaderUrl(String str) {
        this.q = str;
    }

    public void setUploaderVerified(boolean z) {
        this.s = z;
    }

    public void setViewCount(long j2) {
        this.o = j2;
    }

    public String toString() {
        return "StreamInfoItem{streamType=" + this.j + ", uploaderName='" + this.k + "', textualUploadDate='" + this.m + "', viewCount=" + this.o + ", duration=" + this.p + ", uploaderUrl='" + this.q + "', infoType=" + getInfoType() + ", serviceId=" + getServiceId() + ", url='" + getUrl() + "', name='" + getName() + "', thumbnails='" + getThumbnails() + "', uploaderVerified='" + isUploaderVerified() + "'}";
    }
}
