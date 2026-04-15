package org.schabi.newpipe.extractor;

import j$.util.Objects;
import java.io.Serializable;

public final class Image implements Serializable {
    public final String c;
    public final int f;
    public final int g;
    public final ResolutionLevel h;

    public enum ResolutionLevel {
        HIGH,
        MEDIUM,
        LOW,
        UNKNOWN;

        public static ResolutionLevel fromHeight(int i2) {
            if (i2 <= 0) {
                return UNKNOWN;
            }
            if (i2 < 175) {
                return LOW;
            }
            if (i2 < 720) {
                return MEDIUM;
            }
            return HIGH;
        }
    }

    public Image(String str, int i, int i2, ResolutionLevel resolutionLevel) throws NullPointerException {
        this.c = str;
        this.f = i;
        this.g = i2;
        Objects.requireNonNull(resolutionLevel, "estimatedResolutionLevel is null");
        this.h = resolutionLevel;
    }

    public ResolutionLevel getEstimatedResolutionLevel() {
        return this.h;
    }

    public int getHeight() {
        return this.f;
    }

    public String getUrl() {
        return this.c;
    }

    public int getWidth() {
        return this.g;
    }

    public String toString() {
        return "Image {url=" + this.c + ", height=" + this.f + ", width=" + this.g + ", estimatedResolutionLevel=" + this.h + "}";
    }
}
