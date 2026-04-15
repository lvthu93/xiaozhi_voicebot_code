package com.google.android.exoplayer2;

import android.content.Context;
import com.google.android.exoplayer2.mediacodec.MediaCodecSelector;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class DefaultRenderersFactory implements RenderersFactory {
    public final Context a;
    public int b;
    public long c;
    public boolean d;
    public MediaCodecSelector e;
    public boolean f;
    public boolean g;
    public boolean h;
    public boolean i;
    public boolean j;
    public boolean k;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface ExtensionRendererMode {
    }

    public DefaultRenderersFactory(Context context) {
        this.a = context;
        this.b = 0;
        this.c = 5000;
        this.e = MediaCodecSelector.a;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0087, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x008f, code lost:
        throw new java.lang.RuntimeException("Error instantiating VP9 extension", r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x015d, code lost:
        r3 = r23;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x0164, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x016c, code lost:
        throw new java.lang.RuntimeException("Error instantiating Opus extension", r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x016d, code lost:
        r3 = r23;
        r6 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x019f, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x01a7, code lost:
        throw new java.lang.RuntimeException("Error instantiating FLAC extension", r0);
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0087 A[ExcHandler: Exception (r0v7 'e' java.lang.Exception A[CUSTOM_DECLARE]), Splitter:B:6:0x004c] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0123  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0126  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0164 A[ExcHandler: Exception (r0v5 'e' java.lang.Exception A[CUSTOM_DECLARE]), Splitter:B:32:0x0130] */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x019f A[ExcHandler: Exception (r0v4 'e' java.lang.Exception A[CUSTOM_DECLARE]), Splitter:B:56:0x0173] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.android.exoplayer2.Renderer[] createRenderers(android.os.Handler r25, com.google.android.exoplayer2.video.VideoRendererEventListener r26, com.google.android.exoplayer2.audio.AudioRendererEventListener r27, com.google.android.exoplayer2.text.TextOutput r28, com.google.android.exoplayer2.metadata.MetadataOutput r29) {
        /*
            r24 = this;
            r1 = r24
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            android.content.Context r3 = r1.a
            int r11 = r1.b
            com.google.android.exoplayer2.mediacodec.MediaCodecSelector r4 = r1.e
            boolean r7 = r1.d
            long r12 = r1.c
            java.lang.Class<com.google.android.exoplayer2.video.VideoRendererEventListener> r14 = com.google.android.exoplayer2.video.VideoRendererEventListener.class
            com.google.android.exoplayer2.video.MediaCodecVideoRenderer r15 = new com.google.android.exoplayer2.video.MediaCodecVideoRenderer
            r10 = 50
            r2 = r15
            r5 = r12
            r8 = r25
            r9 = r26
            r2.<init>(r3, r4, r5, r7, r8, r9, r10)
            boolean r2 = r1.f
            r15.experimentalSetAsynchronousBufferQueueingEnabled(r2)
            boolean r2 = r1.g
            r15.experimentalSetForceAsyncQueueingSynchronizationWorkaround(r2)
            boolean r2 = r1.h
            r15.experimentalSetSynchronizeCodecInteractionsWithQueueingEnabled(r2)
            r0.add(r15)
            r9 = 3
            r10 = 2
            java.lang.Class<android.os.Handler> r15 = android.os.Handler.class
            java.lang.String r8 = "DefaultRenderersFactory"
            r7 = 0
            r16 = 1
            if (r11 != 0) goto L_0x003f
            goto L_0x00d3
        L_0x003f:
            int r2 = r0.size()
            if (r11 != r10) goto L_0x0047
            int r2 = r2 + -1
        L_0x0047:
            r3 = 50
            r4 = 4
            java.lang.String r5 = "com.google.android.exoplayer2.ext.vp9.LibvpxVideoRenderer"
            java.lang.Class r5 = java.lang.Class.forName(r5)     // Catch:{ ClassNotFoundException -> 0x0090, Exception -> 0x0087 }
            java.lang.Class[] r6 = new java.lang.Class[r4]     // Catch:{ ClassNotFoundException -> 0x0090, Exception -> 0x0087 }
            java.lang.Class r11 = java.lang.Long.TYPE     // Catch:{ ClassNotFoundException -> 0x0090, Exception -> 0x0087 }
            r6[r7] = r11     // Catch:{ ClassNotFoundException -> 0x0090, Exception -> 0x0087 }
            r6[r16] = r15     // Catch:{ ClassNotFoundException -> 0x0090, Exception -> 0x0087 }
            r6[r10] = r14     // Catch:{ ClassNotFoundException -> 0x0090, Exception -> 0x0087 }
            java.lang.Class r11 = java.lang.Integer.TYPE     // Catch:{ ClassNotFoundException -> 0x0090, Exception -> 0x0087 }
            r6[r9] = r11     // Catch:{ ClassNotFoundException -> 0x0090, Exception -> 0x0087 }
            java.lang.reflect.Constructor r5 = r5.getConstructor(r6)     // Catch:{ ClassNotFoundException -> 0x0090, Exception -> 0x0087 }
            java.lang.Object[] r6 = new java.lang.Object[r4]     // Catch:{ ClassNotFoundException -> 0x0090, Exception -> 0x0087 }
            java.lang.Long r11 = java.lang.Long.valueOf(r12)     // Catch:{ ClassNotFoundException -> 0x0090, Exception -> 0x0087 }
            r6[r7] = r11     // Catch:{ ClassNotFoundException -> 0x0090, Exception -> 0x0087 }
            r6[r16] = r25     // Catch:{ ClassNotFoundException -> 0x0090, Exception -> 0x0087 }
            r6[r10] = r26     // Catch:{ ClassNotFoundException -> 0x0090, Exception -> 0x0087 }
            java.lang.Integer r11 = java.lang.Integer.valueOf(r3)     // Catch:{ ClassNotFoundException -> 0x0090, Exception -> 0x0087 }
            r6[r9] = r11     // Catch:{ ClassNotFoundException -> 0x0090, Exception -> 0x0087 }
            java.lang.Object r5 = r5.newInstance(r6)     // Catch:{ ClassNotFoundException -> 0x0090, Exception -> 0x0087 }
            com.google.android.exoplayer2.Renderer r5 = (com.google.android.exoplayer2.Renderer) r5     // Catch:{ ClassNotFoundException -> 0x0090, Exception -> 0x0087 }
            int r6 = r2 + 1
            r0.add(r2, r5)     // Catch:{ ClassNotFoundException -> 0x0085, Exception -> 0x0087 }
            java.lang.String r2 = "Loaded LibvpxVideoRenderer."
            com.google.android.exoplayer2.util.Log.i(r8, r2)     // Catch:{ ClassNotFoundException -> 0x0085, Exception -> 0x0087 }
            goto L_0x0091
        L_0x0085:
            r2 = r6
            goto L_0x0090
        L_0x0087:
            r0 = move-exception
            java.lang.RuntimeException r2 = new java.lang.RuntimeException
            java.lang.String r3 = "Error instantiating VP9 extension"
            r2.<init>(r3, r0)
            throw r2
        L_0x0090:
            r6 = r2
        L_0x0091:
            java.lang.String r2 = "com.google.android.exoplayer2.ext.av1.Libgav1VideoRenderer"
            java.lang.Class r2 = java.lang.Class.forName(r2)     // Catch:{ ClassNotFoundException -> 0x00d3, Exception -> 0x00ca }
            java.lang.Class[] r5 = new java.lang.Class[r4]     // Catch:{ ClassNotFoundException -> 0x00d3, Exception -> 0x00ca }
            java.lang.Class r11 = java.lang.Long.TYPE     // Catch:{ ClassNotFoundException -> 0x00d3, Exception -> 0x00ca }
            r5[r7] = r11     // Catch:{ ClassNotFoundException -> 0x00d3, Exception -> 0x00ca }
            r5[r16] = r15     // Catch:{ ClassNotFoundException -> 0x00d3, Exception -> 0x00ca }
            r5[r10] = r14     // Catch:{ ClassNotFoundException -> 0x00d3, Exception -> 0x00ca }
            java.lang.Class r11 = java.lang.Integer.TYPE     // Catch:{ ClassNotFoundException -> 0x00d3, Exception -> 0x00ca }
            r5[r9] = r11     // Catch:{ ClassNotFoundException -> 0x00d3, Exception -> 0x00ca }
            java.lang.reflect.Constructor r2 = r2.getConstructor(r5)     // Catch:{ ClassNotFoundException -> 0x00d3, Exception -> 0x00ca }
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ ClassNotFoundException -> 0x00d3, Exception -> 0x00ca }
            java.lang.Long r5 = java.lang.Long.valueOf(r12)     // Catch:{ ClassNotFoundException -> 0x00d3, Exception -> 0x00ca }
            r4[r7] = r5     // Catch:{ ClassNotFoundException -> 0x00d3, Exception -> 0x00ca }
            r4[r16] = r25     // Catch:{ ClassNotFoundException -> 0x00d3, Exception -> 0x00ca }
            r4[r10] = r26     // Catch:{ ClassNotFoundException -> 0x00d3, Exception -> 0x00ca }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ ClassNotFoundException -> 0x00d3, Exception -> 0x00ca }
            r4[r9] = r3     // Catch:{ ClassNotFoundException -> 0x00d3, Exception -> 0x00ca }
            java.lang.Object r2 = r2.newInstance(r4)     // Catch:{ ClassNotFoundException -> 0x00d3, Exception -> 0x00ca }
            com.google.android.exoplayer2.Renderer r2 = (com.google.android.exoplayer2.Renderer) r2     // Catch:{ ClassNotFoundException -> 0x00d3, Exception -> 0x00ca }
            r0.add(r6, r2)     // Catch:{ ClassNotFoundException -> 0x00d3, Exception -> 0x00ca }
            java.lang.String r2 = "Loaded Libgav1VideoRenderer."
            com.google.android.exoplayer2.util.Log.i(r8, r2)     // Catch:{ ClassNotFoundException -> 0x00d3, Exception -> 0x00ca }
            goto L_0x00d3
        L_0x00ca:
            r0 = move-exception
            java.lang.RuntimeException r2 = new java.lang.RuntimeException
            java.lang.String r3 = "Error instantiating AV1 extension"
            r2.<init>(r3, r0)
            throw r2
        L_0x00d3:
            boolean r2 = r1.i
            boolean r3 = r1.j
            boolean r4 = r1.k
            com.google.android.exoplayer2.audio.DefaultAudioSink r11 = new com.google.android.exoplayer2.audio.DefaultAudioSink
            android.content.Context r5 = r1.a
            com.google.android.exoplayer2.audio.AudioCapabilities r18 = com.google.android.exoplayer2.audio.AudioCapabilities.getCapabilities(r5)
            com.google.android.exoplayer2.audio.DefaultAudioSink$DefaultAudioProcessorChain r5 = new com.google.android.exoplayer2.audio.DefaultAudioSink$DefaultAudioProcessorChain
            com.google.android.exoplayer2.audio.AudioProcessor[] r6 = new com.google.android.exoplayer2.audio.AudioProcessor[r7]
            r5.<init>(r6)
            r17 = r11
            r19 = r5
            r20 = r2
            r21 = r3
            r22 = r4
            r17.<init>(r18, r19, r20, r21, r22)
            android.content.Context r3 = r1.a
            int r12 = r1.b
            com.google.android.exoplayer2.mediacodec.MediaCodecSelector r4 = r1.e
            boolean r5 = r1.d
            java.lang.Class<com.google.android.exoplayer2.audio.AudioSink> r13 = com.google.android.exoplayer2.audio.AudioSink.class
            java.lang.Class<com.google.android.exoplayer2.audio.AudioRendererEventListener> r14 = com.google.android.exoplayer2.audio.AudioRendererEventListener.class
            com.google.android.exoplayer2.audio.MediaCodecAudioRenderer r6 = new com.google.android.exoplayer2.audio.MediaCodecAudioRenderer
            r2 = r6
            r9 = r6
            r6 = r25
            r7 = r27
            r23 = r8
            r8 = r11
            r2.<init>((android.content.Context) r3, (com.google.android.exoplayer2.mediacodec.MediaCodecSelector) r4, (boolean) r5, (android.os.Handler) r6, (com.google.android.exoplayer2.audio.AudioRendererEventListener) r7, (com.google.android.exoplayer2.audio.AudioSink) r8)
            boolean r2 = r1.f
            r9.experimentalSetAsynchronousBufferQueueingEnabled(r2)
            boolean r2 = r1.g
            r9.experimentalSetForceAsyncQueueingSynchronizationWorkaround(r2)
            boolean r2 = r1.h
            r9.experimentalSetSynchronizeCodecInteractionsWithQueueingEnabled(r2)
            r0.add(r9)
            if (r12 != 0) goto L_0x0126
            r6 = 0
            goto L_0x01dc
        L_0x0126:
            int r2 = r0.size()
            if (r12 != r10) goto L_0x012e
            int r2 = r2 + -1
        L_0x012e:
            java.lang.String r3 = "com.google.android.exoplayer2.ext.opus.LibopusAudioRenderer"
            java.lang.Class r3 = java.lang.Class.forName(r3)     // Catch:{ ClassNotFoundException -> 0x016d, Exception -> 0x0164 }
            r4 = 3
            java.lang.Class[] r5 = new java.lang.Class[r4]     // Catch:{ ClassNotFoundException -> 0x016d, Exception -> 0x0164 }
            r6 = 0
            r5[r6] = r15     // Catch:{ ClassNotFoundException -> 0x0161, Exception -> 0x0164 }
            r5[r16] = r14     // Catch:{ ClassNotFoundException -> 0x0161, Exception -> 0x0164 }
            r5[r10] = r13     // Catch:{ ClassNotFoundException -> 0x0161, Exception -> 0x0164 }
            java.lang.reflect.Constructor r3 = r3.getConstructor(r5)     // Catch:{ ClassNotFoundException -> 0x0161, Exception -> 0x0164 }
            java.lang.Object[] r5 = new java.lang.Object[r4]     // Catch:{ ClassNotFoundException -> 0x0161, Exception -> 0x0164 }
            r5[r6] = r25     // Catch:{ ClassNotFoundException -> 0x0161, Exception -> 0x0164 }
            r5[r16] = r27     // Catch:{ ClassNotFoundException -> 0x0161, Exception -> 0x0164 }
            r5[r10] = r11     // Catch:{ ClassNotFoundException -> 0x0161, Exception -> 0x0164 }
            java.lang.Object r3 = r3.newInstance(r5)     // Catch:{ ClassNotFoundException -> 0x0161, Exception -> 0x0164 }
            com.google.android.exoplayer2.Renderer r3 = (com.google.android.exoplayer2.Renderer) r3     // Catch:{ ClassNotFoundException -> 0x0161, Exception -> 0x0164 }
            int r4 = r2 + 1
            r0.add(r2, r3)     // Catch:{ ClassNotFoundException -> 0x015d, Exception -> 0x0164 }
            java.lang.String r2 = "Loaded LibopusAudioRenderer."
            r3 = r23
            com.google.android.exoplayer2.util.Log.i(r3, r2)     // Catch:{ ClassNotFoundException -> 0x015f, Exception -> 0x0164 }
            goto L_0x0171
        L_0x015d:
            r3 = r23
        L_0x015f:
            r2 = r4
            goto L_0x0170
        L_0x0161:
            r3 = r23
            goto L_0x0170
        L_0x0164:
            r0 = move-exception
            java.lang.RuntimeException r2 = new java.lang.RuntimeException
            java.lang.String r3 = "Error instantiating Opus extension"
            r2.<init>(r3, r0)
            throw r2
        L_0x016d:
            r3 = r23
            r6 = 0
        L_0x0170:
            r4 = r2
        L_0x0171:
            java.lang.String r2 = "com.google.android.exoplayer2.ext.flac.LibflacAudioRenderer"
            java.lang.Class r2 = java.lang.Class.forName(r2)     // Catch:{ ClassNotFoundException -> 0x01a8, Exception -> 0x019f }
            r5 = 3
            java.lang.Class[] r7 = new java.lang.Class[r5]     // Catch:{ ClassNotFoundException -> 0x01a8, Exception -> 0x019f }
            r7[r6] = r15     // Catch:{ ClassNotFoundException -> 0x01a8, Exception -> 0x019f }
            r7[r16] = r14     // Catch:{ ClassNotFoundException -> 0x01a8, Exception -> 0x019f }
            r7[r10] = r13     // Catch:{ ClassNotFoundException -> 0x01a8, Exception -> 0x019f }
            java.lang.reflect.Constructor r2 = r2.getConstructor(r7)     // Catch:{ ClassNotFoundException -> 0x01a8, Exception -> 0x019f }
            java.lang.Object[] r7 = new java.lang.Object[r5]     // Catch:{ ClassNotFoundException -> 0x01a8, Exception -> 0x019f }
            r7[r6] = r25     // Catch:{ ClassNotFoundException -> 0x01a8, Exception -> 0x019f }
            r7[r16] = r27     // Catch:{ ClassNotFoundException -> 0x01a8, Exception -> 0x019f }
            r7[r10] = r11     // Catch:{ ClassNotFoundException -> 0x01a8, Exception -> 0x019f }
            java.lang.Object r2 = r2.newInstance(r7)     // Catch:{ ClassNotFoundException -> 0x01a8, Exception -> 0x019f }
            com.google.android.exoplayer2.Renderer r2 = (com.google.android.exoplayer2.Renderer) r2     // Catch:{ ClassNotFoundException -> 0x01a8, Exception -> 0x019f }
            int r5 = r4 + 1
            r0.add(r4, r2)     // Catch:{ ClassNotFoundException -> 0x019d, Exception -> 0x019f }
            java.lang.String r2 = "Loaded LibflacAudioRenderer."
            com.google.android.exoplayer2.util.Log.i(r3, r2)     // Catch:{ ClassNotFoundException -> 0x019d, Exception -> 0x019f }
            goto L_0x01a9
        L_0x019d:
            r4 = r5
            goto L_0x01a8
        L_0x019f:
            r0 = move-exception
            java.lang.RuntimeException r2 = new java.lang.RuntimeException
            java.lang.String r3 = "Error instantiating FLAC extension"
            r2.<init>(r3, r0)
            throw r2
        L_0x01a8:
            r5 = r4
        L_0x01a9:
            java.lang.String r2 = "com.google.android.exoplayer2.ext.ffmpeg.FfmpegAudioRenderer"
            java.lang.Class r2 = java.lang.Class.forName(r2)     // Catch:{ ClassNotFoundException -> 0x01dc, Exception -> 0x01d3 }
            r4 = 3
            java.lang.Class[] r7 = new java.lang.Class[r4]     // Catch:{ ClassNotFoundException -> 0x01dc, Exception -> 0x01d3 }
            r7[r6] = r15     // Catch:{ ClassNotFoundException -> 0x01dc, Exception -> 0x01d3 }
            r7[r16] = r14     // Catch:{ ClassNotFoundException -> 0x01dc, Exception -> 0x01d3 }
            r7[r10] = r13     // Catch:{ ClassNotFoundException -> 0x01dc, Exception -> 0x01d3 }
            java.lang.reflect.Constructor r2 = r2.getConstructor(r7)     // Catch:{ ClassNotFoundException -> 0x01dc, Exception -> 0x01d3 }
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ ClassNotFoundException -> 0x01dc, Exception -> 0x01d3 }
            r4[r6] = r25     // Catch:{ ClassNotFoundException -> 0x01dc, Exception -> 0x01d3 }
            r4[r16] = r27     // Catch:{ ClassNotFoundException -> 0x01dc, Exception -> 0x01d3 }
            r4[r10] = r11     // Catch:{ ClassNotFoundException -> 0x01dc, Exception -> 0x01d3 }
            java.lang.Object r2 = r2.newInstance(r4)     // Catch:{ ClassNotFoundException -> 0x01dc, Exception -> 0x01d3 }
            com.google.android.exoplayer2.Renderer r2 = (com.google.android.exoplayer2.Renderer) r2     // Catch:{ ClassNotFoundException -> 0x01dc, Exception -> 0x01d3 }
            r0.add(r5, r2)     // Catch:{ ClassNotFoundException -> 0x01dc, Exception -> 0x01d3 }
            java.lang.String r2 = "Loaded FfmpegAudioRenderer."
            com.google.android.exoplayer2.util.Log.i(r3, r2)     // Catch:{ ClassNotFoundException -> 0x01dc, Exception -> 0x01d3 }
            goto L_0x01dc
        L_0x01d3:
            r0 = move-exception
            java.lang.RuntimeException r2 = new java.lang.RuntimeException
            java.lang.String r3 = "Error instantiating FFmpeg extension"
            r2.<init>(r3, r0)
            throw r2
        L_0x01dc:
            android.os.Looper r2 = r25.getLooper()
            com.google.android.exoplayer2.text.TextRenderer r3 = new com.google.android.exoplayer2.text.TextRenderer
            r4 = r28
            r3.<init>(r4, r2)
            r0.add(r3)
            android.os.Looper r2 = r25.getLooper()
            com.google.android.exoplayer2.metadata.MetadataRenderer r3 = new com.google.android.exoplayer2.metadata.MetadataRenderer
            r4 = r29
            r3.<init>(r4, r2)
            r0.add(r3)
            com.google.android.exoplayer2.video.spherical.CameraMotionRenderer r2 = new com.google.android.exoplayer2.video.spherical.CameraMotionRenderer
            r2.<init>()
            r0.add(r2)
            com.google.android.exoplayer2.Renderer[] r2 = new com.google.android.exoplayer2.Renderer[r6]
            java.lang.Object[] r0 = r0.toArray(r2)
            com.google.android.exoplayer2.Renderer[] r0 = (com.google.android.exoplayer2.Renderer[]) r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.DefaultRenderersFactory.createRenderers(android.os.Handler, com.google.android.exoplayer2.video.VideoRendererEventListener, com.google.android.exoplayer2.audio.AudioRendererEventListener, com.google.android.exoplayer2.text.TextOutput, com.google.android.exoplayer2.metadata.MetadataOutput):com.google.android.exoplayer2.Renderer[]");
    }

    public DefaultRenderersFactory experimentalSetAsynchronousBufferQueueingEnabled(boolean z) {
        this.f = z;
        return this;
    }

    public DefaultRenderersFactory experimentalSetForceAsyncQueueingSynchronizationWorkaround(boolean z) {
        this.g = z;
        return this;
    }

    public DefaultRenderersFactory experimentalSetSynchronizeCodecInteractionsWithQueueingEnabled(boolean z) {
        this.h = z;
        return this;
    }

    public DefaultRenderersFactory setAllowedVideoJoiningTimeMs(long j2) {
        this.c = j2;
        return this;
    }

    public DefaultRenderersFactory setEnableAudioFloatOutput(boolean z) {
        this.i = z;
        return this;
    }

    public DefaultRenderersFactory setEnableAudioOffload(boolean z) {
        this.k = z;
        return this;
    }

    public DefaultRenderersFactory setEnableAudioTrackPlaybackParams(boolean z) {
        this.j = z;
        return this;
    }

    public DefaultRenderersFactory setEnableDecoderFallback(boolean z) {
        this.d = z;
        return this;
    }

    public DefaultRenderersFactory setExtensionRendererMode(int i2) {
        this.b = i2;
        return this;
    }

    public DefaultRenderersFactory setMediaCodecSelector(MediaCodecSelector mediaCodecSelector) {
        this.e = mediaCodecSelector;
        return this;
    }

    @Deprecated
    public DefaultRenderersFactory(Context context, int i2) {
        this(context, i2, 5000);
    }

    @Deprecated
    public DefaultRenderersFactory(Context context, int i2, long j2) {
        this.a = context;
        this.b = i2;
        this.c = j2;
        this.e = MediaCodecSelector.a;
    }
}
