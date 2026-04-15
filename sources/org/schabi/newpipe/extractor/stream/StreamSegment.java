package org.schabi.newpipe.extractor.stream;

import java.io.Serializable;

public class StreamSegment implements Serializable {
    public String c;
    public String f;
    public int g;
    public String h;
    public String i = null;

    public StreamSegment(String str, int i2) {
        this.c = str;
        this.g = i2;
    }

    public String getChannelName() {
        return this.f;
    }

    public String getPreviewUrl() {
        return this.i;
    }

    public int getStartTimeSeconds() {
        return this.g;
    }

    public String getTitle() {
        return this.c;
    }

    public String getUrl() {
        return this.h;
    }

    public void setChannelName(String str) {
        this.f = str;
    }

    public void setPreviewUrl(String str) {
        this.i = str;
    }

    public void setStartTimeSeconds(int i2) {
        this.g = i2;
    }

    public void setTitle(String str) {
        this.c = str;
    }

    public void setUrl(String str) {
        this.h = str;
    }
}
