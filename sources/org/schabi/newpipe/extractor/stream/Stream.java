package org.schabi.newpipe.extractor.stream;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.schabi.newpipe.extractor.MediaFormat;
import org.schabi.newpipe.extractor.services.youtube.ItagItem;
import org.schabi.newpipe.extractor.utils.Utils;

public abstract class Stream implements Serializable {
    public final String c;
    public final MediaFormat f;
    public final String g;
    public final boolean h;
    public final DeliveryMethod i;
    public final String j;

    public Stream(String str, String str2, boolean z, MediaFormat mediaFormat, DeliveryMethod deliveryMethod, String str3) {
        this.c = str;
        this.g = str2;
        this.h = z;
        this.f = mediaFormat;
        this.i = deliveryMethod;
        this.j = str3;
    }

    public static boolean containSimilarStream(Stream stream, List<? extends Stream> list) {
        if (Utils.isNullOrEmpty((Collection<?>) list)) {
            return false;
        }
        for (Stream equalStats : list) {
            if (stream.equalStats(equalStats)) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:1:0x0002, code lost:
        r0 = r2.f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0006, code lost:
        r1 = r3.f;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equalStats(org.schabi.newpipe.extractor.stream.Stream r3) {
        /*
            r2 = this;
            if (r3 == 0) goto L_0x001e
            org.schabi.newpipe.extractor.MediaFormat r0 = r2.f
            if (r0 == 0) goto L_0x001e
            org.schabi.newpipe.extractor.MediaFormat r1 = r3.f
            if (r1 == 0) goto L_0x001e
            int r0 = r0.c
            int r1 = r1.c
            if (r0 != r1) goto L_0x001e
            org.schabi.newpipe.extractor.stream.DeliveryMethod r0 = r2.i
            org.schabi.newpipe.extractor.stream.DeliveryMethod r1 = r3.i
            if (r0 != r1) goto L_0x001e
            boolean r0 = r2.h
            boolean r3 = r3.h
            if (r0 != r3) goto L_0x001e
            r3 = 1
            goto L_0x001f
        L_0x001e:
            r3 = 0
        L_0x001f:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.schabi.newpipe.extractor.stream.Stream.equalStats(org.schabi.newpipe.extractor.stream.Stream):boolean");
    }

    public String getContent() {
        return this.g;
    }

    public DeliveryMethod getDeliveryMethod() {
        return this.i;
    }

    public MediaFormat getFormat() {
        return this.f;
    }

    public int getFormatId() {
        MediaFormat mediaFormat = this.f;
        if (mediaFormat != null) {
            return mediaFormat.c;
        }
        return -1;
    }

    public String getId() {
        return this.c;
    }

    public abstract ItagItem getItagItem();

    public String getManifestUrl() {
        return this.j;
    }

    @Deprecated
    public String getUrl() {
        if (this.h) {
            return this.g;
        }
        return null;
    }

    public boolean isUrl() {
        return this.h;
    }
}
