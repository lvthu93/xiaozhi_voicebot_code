package org.schabi.newpipe.extractor.utils;

import j$.util.Objects;
import java.io.Serializable;
import org.schabi.newpipe.extractor.Image;

public final class ImageSuffix implements Serializable {
    public final String c;
    public final int f;
    public final int g;
    public final Image.ResolutionLevel h;

    public ImageSuffix(String str, int i, int i2, Image.ResolutionLevel resolutionLevel) throws NullPointerException {
        this.c = str;
        this.f = i;
        this.g = i2;
        Objects.requireNonNull(resolutionLevel, "estimatedResolutionLevel is null");
        this.h = resolutionLevel;
    }

    public int getHeight() {
        return this.f;
    }

    public Image.ResolutionLevel getResolutionLevel() {
        return this.h;
    }

    public String getSuffix() {
        return this.c;
    }

    public int getWidth() {
        return this.g;
    }

    public String toString() {
        return "ImageSuffix {suffix=" + this.c + ", height=" + this.f + ", width=" + this.g + ", resolutionLevel=" + this.h + "}";
    }
}
