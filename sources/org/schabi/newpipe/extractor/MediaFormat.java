package org.schabi.newpipe.extractor;

import j$.util.DesugarArrays;
import j$.util.stream.Collectors;
import java.util.List;

public enum MediaFormat {
    MPEG_4("mp4", 0, "video/mp4", 0),
    v3GPP("3gp", 1, "video/3gpp", 16),
    WEBM("webm", 2, "video/webm", 32),
    M4A("m4a", 3, "audio/mp4", 256),
    WEBMA("webm", 4, "audio/webm", 512),
    MP3("mp3", 5, "audio/mpeg", 768),
    OPUS("opus", 7, "audio/opus", 1024),
    OGG("ogg", 8, "audio/ogg", 1280),
    WEBMA_OPUS("webm", 9, "audio/webm", 512),
    TTML("ttml", 16, "application/ttml+xml", 8192);
    
    public final int c;
    public final String f;
    public final String g;
    public final String h;

    /* access modifiers changed from: public */
    MediaFormat(String str, int i, String str2, int i2) {
        this.c = i2;
        this.f = r2;
        this.g = str;
        this.h = str2;
    }

    public static Object b(int i, z5 z5Var, String str) {
        return DesugarArrays.stream(values()).filter(new af(i, 1)).map(z5Var).findFirst().orElse(str);
    }

    public static List<MediaFormat> getAllFromMimeType(String str) {
        return (List) DesugarArrays.stream(values()).filter(new y5(str, 0)).collect(Collectors.toList());
    }

    public static MediaFormat getFormatById(int i) {
        return (MediaFormat) b(i, new z5(1), (String) null);
    }

    public static MediaFormat getFromMimeType(String str) {
        return (MediaFormat) DesugarArrays.stream(values()).filter(new y5(str, 2)).findFirst().orElse(null);
    }

    public static MediaFormat getFromSuffix(String str) {
        return (MediaFormat) DesugarArrays.stream(values()).filter(new y5(str, 1)).findFirst().orElse(null);
    }

    public static String getMimeById(int i) {
        return (String) b(i, new z5(3), (String) null);
    }

    public static String getNameById(int i) {
        return (String) b(i, new z5(2), "");
    }

    public static String getSuffixById(int i) {
        return (String) b(i, new z5(0), "");
    }

    public String getMimeType() {
        return this.h;
    }

    public String getName() {
        return this.f;
    }

    public String getSuffix() {
        return this.g;
    }
}
