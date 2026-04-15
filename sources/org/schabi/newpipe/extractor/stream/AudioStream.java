package org.schabi.newpipe.extractor.stream;

import java.util.Locale;
import org.schabi.newpipe.extractor.MediaFormat;
import org.schabi.newpipe.extractor.services.youtube.ItagItem;

public final class AudioStream extends Stream {
    public final int k;
    public final int l = -1;
    public final int m;
    public final int n;
    public final int o;
    public final int p;
    public final int q;
    public final String r;
    public final String s;
    public final String t;
    public final String u;
    public final Locale v;
    public final AudioTrackType w;
    public final ItagItem x;

    public static final class Builder {
        public String a;
        public String b;
        public boolean c;
        public DeliveryMethod d = DeliveryMethod.PROGRESSIVE_HTTP;
        public MediaFormat e;
        public String f;
        public int g = -1;
        public String h;
        public String i;
        public Locale j;
        public AudioTrackType k;
        public ItagItem l;

        public AudioStream build() {
            if (this.a == null) {
                throw new IllegalStateException("The identifier of the audio stream has been not set or is null. If you are not able to get an identifier, use the static constant ID_UNKNOWN of the Stream class.");
            } else if (this.b == null) {
                throw new IllegalStateException("The content of the audio stream has been not set or is null. Please specify a non-null one with setContent.");
            } else if (this.d != null) {
                return new AudioStream(this);
            } else {
                throw new IllegalStateException("The delivery method of the audio stream has been set as null, which is not allowed. Pass a valid one instead with setDeliveryMethod.");
            }
        }

        public Builder setAudioLocale(Locale locale) {
            this.j = locale;
            return this;
        }

        public Builder setAudioTrackId(String str) {
            this.h = str;
            return this;
        }

        public Builder setAudioTrackName(String str) {
            this.i = str;
            return this;
        }

        public Builder setAudioTrackType(AudioTrackType audioTrackType) {
            this.k = audioTrackType;
            return this;
        }

        public Builder setAverageBitrate(int i2) {
            this.g = i2;
            return this;
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

        public Builder setItagItem(ItagItem itagItem) {
            this.l = itagItem;
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
    }

    public AudioStream(Builder builder) {
        super(builder.a, builder.b, builder.c, builder.e, builder.d, builder.f);
        ItagItem itagItem = builder.l;
        if (itagItem != null) {
            this.x = itagItem;
            this.l = itagItem.f;
            this.r = itagItem.getQuality();
            this.m = builder.l.getBitrate();
            this.n = builder.l.getInitStart();
            this.o = builder.l.getInitEnd();
            this.p = builder.l.getIndexStart();
            this.q = builder.l.getIndexEnd();
            this.s = builder.l.getCodec();
        }
        this.k = builder.g;
        this.t = builder.h;
        this.u = builder.i;
        this.v = builder.j;
        this.w = builder.k;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x000a, code lost:
        r3 = (org.schabi.newpipe.extractor.stream.AudioStream) r3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equalStats(org.schabi.newpipe.extractor.stream.Stream r3) {
        /*
            r2 = this;
            boolean r0 = super.equalStats(r3)
            if (r0 == 0) goto L_0x002e
            boolean r0 = r3 instanceof org.schabi.newpipe.extractor.stream.AudioStream
            if (r0 == 0) goto L_0x002e
            org.schabi.newpipe.extractor.stream.AudioStream r3 = (org.schabi.newpipe.extractor.stream.AudioStream) r3
            int r0 = r3.k
            int r1 = r2.k
            if (r1 != r0) goto L_0x002e
            java.lang.String r0 = r2.t
            java.lang.String r1 = r3.t
            boolean r0 = j$.util.Objects.equals(r0, r1)
            if (r0 == 0) goto L_0x002e
            org.schabi.newpipe.extractor.stream.AudioTrackType r0 = r2.w
            org.schabi.newpipe.extractor.stream.AudioTrackType r1 = r3.w
            if (r0 != r1) goto L_0x002e
            java.util.Locale r0 = r2.v
            java.util.Locale r3 = r3.v
            boolean r3 = j$.util.Objects.equals(r0, r3)
            if (r3 == 0) goto L_0x002e
            r3 = 1
            goto L_0x002f
        L_0x002e:
            r3 = 0
        L_0x002f:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.schabi.newpipe.extractor.stream.AudioStream.equalStats(org.schabi.newpipe.extractor.stream.Stream):boolean");
    }

    public Locale getAudioLocale() {
        return this.v;
    }

    public String getAudioTrackId() {
        return this.t;
    }

    public String getAudioTrackName() {
        return this.u;
    }

    public AudioTrackType getAudioTrackType() {
        return this.w;
    }

    public int getAverageBitrate() {
        return this.k;
    }

    public int getBitrate() {
        return this.m;
    }

    public String getCodec() {
        return this.s;
    }

    public int getIndexEnd() {
        return this.q;
    }

    public int getIndexStart() {
        return this.p;
    }

    public int getInitEnd() {
        return this.o;
    }

    public int getInitStart() {
        return this.n;
    }

    public int getItag() {
        return this.l;
    }

    public ItagItem getItagItem() {
        return this.x;
    }

    public String getQuality() {
        return this.r;
    }
}
