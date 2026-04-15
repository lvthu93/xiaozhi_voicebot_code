package com.google.android.exoplayer2.mediacodec;

import com.google.android.exoplayer2.decoder.DecoderException;

public class MediaCodecDecoderException extends DecoderException {
    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public MediaCodecDecoderException(java.lang.Throwable r3, @androidx.annotation.Nullable com.google.android.exoplayer2.mediacodec.MediaCodecInfo r4) {
        /*
            r2 = this;
            if (r4 != 0) goto L_0x0004
            r4 = 0
            goto L_0x0006
        L_0x0004:
            java.lang.String r4 = r4.a
        L_0x0006:
            java.lang.String r4 = java.lang.String.valueOf(r4)
            int r0 = r4.length()
            java.lang.String r1 = "Decoder failed: "
            if (r0 == 0) goto L_0x0017
            java.lang.String r4 = r1.concat(r4)
            goto L_0x001c
        L_0x0017:
            java.lang.String r4 = new java.lang.String
            r4.<init>(r1)
        L_0x001c:
            r2.<init>(r4, r3)
            int r4 = com.google.android.exoplayer2.util.Util.a
            r0 = 21
            if (r4 < r0) goto L_0x002e
            boolean r4 = r3 instanceof android.media.MediaCodec.CodecException
            if (r4 == 0) goto L_0x002e
            android.media.MediaCodec$CodecException r3 = (android.media.MediaCodec.CodecException) r3
            r3.getDiagnosticInfo()
        L_0x002e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.mediacodec.MediaCodecDecoderException.<init>(java.lang.Throwable, com.google.android.exoplayer2.mediacodec.MediaCodecInfo):void");
    }
}
