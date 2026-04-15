package org.schabi.newpipe.extractor;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public abstract class InfoItem implements Serializable {
    public final InfoType c;
    public final int f;
    public final String g;
    public final String h;
    public List<Image> i = Collections.emptyList();

    public enum InfoType {
        STREAM,
        PLAYLIST,
        CHANNEL,
        COMMENT
    }

    public InfoItem(InfoType infoType, int i2, String str, String str2) {
        this.c = infoType;
        this.f = i2;
        this.g = str;
        this.h = str2;
    }

    public InfoType getInfoType() {
        return this.c;
    }

    public String getName() {
        return this.h;
    }

    public int getServiceId() {
        return this.f;
    }

    public List<Image> getThumbnails() {
        return this.i;
    }

    public String getUrl() {
        return this.g;
    }

    public void setThumbnails(List<Image> list) {
        this.i = list;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append("[url=\"");
        sb.append(this.g);
        sb.append("\", name=\"");
        return y2.k(sb, this.h, "\"]");
    }
}
