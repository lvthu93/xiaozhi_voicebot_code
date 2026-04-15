package org.schabi.newpipe.extractor.stream;

import org.schabi.newpipe.extractor.MediaFormat;
import org.schabi.newpipe.extractor.services.youtube.ItagItem;

public final class VideoStream extends Stream {
    @Deprecated
    public final String k;
    @Deprecated
    public final boolean l;
    public final int m = -1;
    public final int n;
    public final int o;
    public final int p;
    public final int q;
    public final int r;
    public final int s;
    public final int t;
    public final int u;
    public final String v;
    public final String w;
    public final ItagItem x;

    public static final class Builder {
        public String a;
        public String b;
        public boolean c;
        public DeliveryMethod d = DeliveryMethod.PROGRESSIVE_HTTP;
        public MediaFormat e;
        public String f;
        public Boolean g;
        public String h;
        public ItagItem i;

        public VideoStream build() {
            String str = this.a;
            if (str != null) {
                String str2 = this.b;
                if (str2 != null) {
                    DeliveryMethod deliveryMethod = this.d;
                    if (deliveryMethod != null) {
                        Boolean bool = this.g;
                        if (bool != null) {
                            String str3 = this.h;
                            if (str3 != null) {
                                return new VideoStream(str, str2, this.c, this.e, deliveryMethod, str3, bool.booleanValue(), this.f, this.i);
                            }
                            throw new IllegalStateException("The resolution of the video stream has been not set. Please specify it with setResolution (use an empty string if you are not able to get it).");
                        }
                        throw new IllegalStateException("The video stream has been not set as a video-only stream or as a video stream with embedded audio. Please specify this information with setIsVideoOnly.");
                    }
                    throw new IllegalStateException("The delivery method of the video stream has been set as null, which is not allowed. Pass a valid one instead with setDeliveryMethod.");
                }
                throw new IllegalStateException("The content of the video stream has been not set or is null. Please specify a non-null one with setContent.");
            }
            throw new IllegalStateException("The identifier of the video stream has been not set or is null. If you are not able to get an identifier, use the static constant ID_UNKNOWN of the Stream class.");
        }

        public Builder setContent(String str, boolean z) {
            this.b = str;
            this.c = z;
            return this;
        }

        public Builder setDeliveryMethod(DeliveryMethod deliveryMethod) {
            this.d = deliveryMethod;
            return this;
        }

        public Builder setId(String str) {
            this.a = str;
            return this;
        }

        public Builder setIsVideoOnly(boolean z) {
            this.g = Boolean.valueOf(z);
            return this;
        }

        public Builder setItagItem(ItagItem itagItem) {
            this.i = itagItem;
            return this;
        }

        public Builder setManifestUrl(String str) {
            this.f = str;
            return this;
        }

        public Builder setMediaFormat(MediaFormat mediaFormat) {
            this.e = mediaFormat;
            return this;
        }

        public Builder setResolution(String str) {
            this.h = str;
            return this;
        }
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public VideoStream(String str, String str2, boolean z, MediaFormat mediaFormat, DeliveryMethod deliveryMethod, String str3, boolean z2, String str4, ItagItem itagItem) {
        super(str, str2, z, mediaFormat, deliveryMethod, str4);
        ItagItem itagItem2 = itagItem;
        if (itagItem2 != null) {
            this.x = itagItem2;
            this.m = itagItem2.f;
            this.n = itagItem.getBitrate();
            this.o = itagItem.getInitStart();
            this.p = itagItem.getInitEnd();
            this.q = itagItem.getIndexStart();
            this.r = itagItem.getIndexEnd();
            this.w = itagItem.getCodec();
            this.t = itagItem.getHeight();
            this.s = itagItem.getWidth();
            this.v = itagItem.getQuality();
            this.u = itagItem.getFps();
        }
        this.k = str3;
        this.l = z2;
    }

    public boolean equalStats(Stream stream) {
        if (super.equalStats(stream) && (stream instanceof VideoStream)) {
            VideoStream videoStream = (VideoStream) stream;
            if (!this.k.equals(videoStream.k) || this.l != videoStream.l) {
                return false;
            }
            return true;
        }
        return false;
    }

    public int getBitrate() {
        return this.n;
    }

    public String getCodec() {
        return this.w;
    }

    public int getFps() {
        return this.u;
    }

    public int getHeight() {
        return this.t;
    }

    public int getIndexEnd() {
        return this.r;
    }

    public int getIndexStart() {
        return this.q;
    }

    public int getInitEnd() {
        return this.p;
    }

    public int getInitStart() {
        return this.o;
    }

    public int getItag() {
        return this.m;
    }

    public ItagItem getItagItem() {
        return this.x;
    }

    public String getQuality() {
        return this.v;
    }

    public String getResolution() {
        return this.k;
    }

    public int getWidth() {
        return this.s;
    }

    public boolean isVideoOnly() {
        return this.l;
    }
}
