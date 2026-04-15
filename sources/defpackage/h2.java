package defpackage;

/* renamed from: h2  reason: default package */
public final /* synthetic */ class h2 implements Runnable {
    public final /* synthetic */ int c;
    public final /* synthetic */ Object f;
    public final /* synthetic */ Object g;

    public /* synthetic */ h2(int i, Object obj, Object obj2) {
        this.c = i;
        this.f = obj;
        this.g = obj2;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v6, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v9, resolved type: boolean} */
    /* JADX WARNING: type inference failed for: r6v0 */
    /* JADX WARNING: type inference failed for: r6v1 */
    /* JADX WARNING: type inference failed for: r6v2 */
    /* JADX WARNING: type inference failed for: r6v8 */
    /* JADX WARNING: type inference failed for: r6v10 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r18 = this;
            r1 = r18
            int r0 = r1.c
            r2 = 0
            r4 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            r6 = 1
            r7 = 0
            switch(r0) {
                case 0: goto L_0x01fa;
                case 1: goto L_0x01dc;
                case 2: goto L_0x01c8;
                case 3: goto L_0x019f;
                case 4: goto L_0x015b;
                case 5: goto L_0x014a;
                case 6: goto L_0x0136;
                case 7: goto L_0x0123;
                case 8: goto L_0x00db;
                case 9: goto L_0x00c7;
                case 10: goto L_0x0094;
                case 11: goto L_0x0084;
                case 12: goto L_0x0070;
                case 13: goto L_0x005c;
                case 14: goto L_0x0048;
                case 15: goto L_0x0012;
                default: goto L_0x0010;
            }
        L_0x0010:
            goto L_0x02d2
        L_0x0012:
            java.lang.Object r0 = r1.f
            com.google.android.exoplayer2.video.spherical.SphericalGLSurfaceView r0 = (com.google.android.exoplayer2.video.spherical.SphericalGLSurfaceView) r0
            java.lang.Object r2 = r1.g
            android.graphics.SurfaceTexture r2 = (android.graphics.SurfaceTexture) r2
            android.graphics.SurfaceTexture r3 = r0.k
            android.view.Surface r4 = r0.l
            android.view.Surface r5 = new android.view.Surface
            r5.<init>(r2)
            r0.k = r2
            r0.l = r5
            java.util.concurrent.CopyOnWriteArrayList<com.google.android.exoplayer2.video.spherical.SphericalGLSurfaceView$VideoSurfaceListener> r0 = r0.c
            java.util.Iterator r0 = r0.iterator()
        L_0x002d:
            boolean r2 = r0.hasNext()
            if (r2 == 0) goto L_0x003d
            java.lang.Object r2 = r0.next()
            com.google.android.exoplayer2.video.spherical.SphericalGLSurfaceView$VideoSurfaceListener r2 = (com.google.android.exoplayer2.video.spherical.SphericalGLSurfaceView.VideoSurfaceListener) r2
            r2.onVideoSurfaceCreated(r5)
            goto L_0x002d
        L_0x003d:
            if (r3 == 0) goto L_0x0042
            r3.release()
        L_0x0042:
            if (r4 == 0) goto L_0x0047
            r4.release()
        L_0x0047:
            return
        L_0x0048:
            java.lang.Object r0 = r1.f
            com.google.android.exoplayer2.video.VideoRendererEventListener$EventDispatcher r0 = (com.google.android.exoplayer2.video.VideoRendererEventListener.EventDispatcher) r0
            java.lang.Object r2 = r1.g
            java.lang.Exception r2 = (java.lang.Exception) r2
            com.google.android.exoplayer2.video.VideoRendererEventListener r0 = r0.b
            java.lang.Object r0 = com.google.android.exoplayer2.util.Util.castNonNull(r0)
            com.google.android.exoplayer2.video.VideoRendererEventListener r0 = (com.google.android.exoplayer2.video.VideoRendererEventListener) r0
            r0.onVideoCodecError(r2)
            return
        L_0x005c:
            java.lang.Object r0 = r1.f
            com.google.android.exoplayer2.video.VideoRendererEventListener$EventDispatcher r0 = (com.google.android.exoplayer2.video.VideoRendererEventListener.EventDispatcher) r0
            java.lang.Object r2 = r1.g
            com.google.android.exoplayer2.video.VideoSize r2 = (com.google.android.exoplayer2.video.VideoSize) r2
            com.google.android.exoplayer2.video.VideoRendererEventListener r0 = r0.b
            java.lang.Object r0 = com.google.android.exoplayer2.util.Util.castNonNull(r0)
            com.google.android.exoplayer2.video.VideoRendererEventListener r0 = (com.google.android.exoplayer2.video.VideoRendererEventListener) r0
            r0.onVideoSizeChanged(r2)
            return
        L_0x0070:
            java.lang.Object r0 = r1.f
            com.google.android.exoplayer2.video.VideoRendererEventListener$EventDispatcher r0 = (com.google.android.exoplayer2.video.VideoRendererEventListener.EventDispatcher) r0
            java.lang.Object r2 = r1.g
            java.lang.String r2 = (java.lang.String) r2
            com.google.android.exoplayer2.video.VideoRendererEventListener r0 = r0.b
            java.lang.Object r0 = com.google.android.exoplayer2.util.Util.castNonNull(r0)
            com.google.android.exoplayer2.video.VideoRendererEventListener r0 = (com.google.android.exoplayer2.video.VideoRendererEventListener) r0
            r0.onVideoDecoderReleased(r2)
            return
        L_0x0084:
            java.lang.Object r0 = r1.f
            com.google.android.exoplayer2.util.NetworkTypeObserver r0 = (com.google.android.exoplayer2.util.NetworkTypeObserver) r0
            java.lang.Object r2 = r1.g
            com.google.android.exoplayer2.util.NetworkTypeObserver$Listener r2 = (com.google.android.exoplayer2.util.NetworkTypeObserver.Listener) r2
            int r0 = r0.getNetworkType()
            r2.onNetworkTypeChanged(r0)
            return
        L_0x0094:
            java.lang.Object r0 = r1.f
            com.google.android.exoplayer2.source.ads.AdsMediaSource$c r0 = (com.google.android.exoplayer2.source.ads.AdsMediaSource.c) r0
            java.lang.Object r2 = r1.g
            com.google.android.exoplayer2.source.ads.AdPlaybackState r2 = (com.google.android.exoplayer2.source.ads.AdPlaybackState) r2
            boolean r3 = r0.b
            if (r3 == 0) goto L_0x00a1
            goto L_0x00c6
        L_0x00a1:
            com.google.android.exoplayer2.source.ads.AdsMediaSource r0 = com.google.android.exoplayer2.source.ads.AdsMediaSource.this
            com.google.android.exoplayer2.source.ads.AdPlaybackState r3 = r0.t
            if (r3 != 0) goto L_0x00b3
            int r3 = r2.f
            com.google.android.exoplayer2.source.ads.AdsMediaSource$a[][] r3 = new com.google.android.exoplayer2.source.ads.AdsMediaSource.a[r3][]
            r0.u = r3
            com.google.android.exoplayer2.source.ads.AdsMediaSource$a[] r4 = new com.google.android.exoplayer2.source.ads.AdsMediaSource.a[r7]
            java.util.Arrays.fill(r3, r4)
            goto L_0x00be
        L_0x00b3:
            int r4 = r2.f
            int r3 = r3.f
            if (r4 != r3) goto L_0x00ba
            goto L_0x00bb
        L_0x00ba:
            r6 = 0
        L_0x00bb:
            com.google.android.exoplayer2.util.Assertions.checkState(r6)
        L_0x00be:
            r0.t = r2
            r0.i()
            r0.j()
        L_0x00c6:
            return
        L_0x00c7:
            java.lang.Object r0 = r1.f
            com.google.android.exoplayer2.source.ads.AdsMediaSource$b r0 = (com.google.android.exoplayer2.source.ads.AdsMediaSource.b) r0
            java.lang.Object r2 = r1.g
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r2 = (com.google.android.exoplayer2.source.MediaSource.MediaPeriodId) r2
            com.google.android.exoplayer2.source.ads.AdsMediaSource r0 = com.google.android.exoplayer2.source.ads.AdsMediaSource.this
            com.google.android.exoplayer2.source.ads.AdsLoader r3 = r0.l
            int r4 = r2.b
            int r2 = r2.c
            r3.handlePrepareComplete(r0, r4, r2)
            return
        L_0x00db:
            java.lang.Object r0 = r1.f
            com.google.android.exoplayer2.source.b r0 = (com.google.android.exoplayer2.source.b) r0
            java.lang.Object r2 = r1.g
            com.google.android.exoplayer2.extractor.SeekMap r2 = (com.google.android.exoplayer2.extractor.SeekMap) r2
            com.google.android.exoplayer2.metadata.icy.IcyHeaders r3 = r0.v
            if (r3 != 0) goto L_0x00e9
            r3 = r2
            goto L_0x00ee
        L_0x00e9:
            com.google.android.exoplayer2.extractor.SeekMap$Unseekable r3 = new com.google.android.exoplayer2.extractor.SeekMap$Unseekable
            r3.<init>(r4)
        L_0x00ee:
            r0.ac = r3
            long r8 = r2.getDurationUs()
            r0.ad = r8
            long r8 = r0.aj
            r10 = -1
            int r3 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r3 != 0) goto L_0x0107
            long r8 = r2.getDurationUs()
            int r3 = (r8 > r4 ? 1 : (r8 == r4 ? 0 : -1))
            if (r3 != 0) goto L_0x0107
            r7 = 1
        L_0x0107:
            r0.ae = r7
            if (r7 == 0) goto L_0x010c
            r6 = 7
        L_0x010c:
            r0.af = r6
            long r3 = r0.ad
            boolean r2 = r2.isSeekable()
            boolean r5 = r0.ae
            com.google.android.exoplayer2.source.b$b r6 = r0.k
            r6.onSourceInfoRefreshed(r3, r2, r5)
            boolean r2 = r0.z
            if (r2 != 0) goto L_0x0122
            r0.e()
        L_0x0122:
            return
        L_0x0123:
            java.lang.Object r0 = r1.f
            com.google.android.exoplayer2.offline.DownloadService$a r0 = (com.google.android.exoplayer2.offline.DownloadService.a) r0
            java.lang.Object r2 = r1.g
            com.google.android.exoplayer2.offline.DownloadService r2 = (com.google.android.exoplayer2.offline.DownloadService) r2
            com.google.android.exoplayer2.offline.DownloadManager r0 = r0.b
            r0.getCurrentDownloads()
            java.util.HashMap<java.lang.Class<? extends com.google.android.exoplayer2.offline.DownloadService>, com.google.android.exoplayer2.offline.DownloadService$a> r0 = com.google.android.exoplayer2.offline.DownloadService.j
            r2.getClass()
            return
        L_0x0136:
            java.lang.Object r0 = r1.f
            com.google.android.exoplayer2.offline.DownloadHelper r0 = (com.google.android.exoplayer2.offline.DownloadHelper) r0
            java.lang.Object r2 = r1.g
            java.io.IOException r2 = (java.io.IOException) r2
            com.google.android.exoplayer2.offline.DownloadHelper$Callback r3 = r0.i
            java.lang.Object r3 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r3)
            com.google.android.exoplayer2.offline.DownloadHelper$Callback r3 = (com.google.android.exoplayer2.offline.DownloadHelper.Callback) r3
            r3.onPrepareError(r0, r2)
            return
        L_0x014a:
            java.lang.Object r0 = r1.f
            com.google.android.exoplayer2.offline.DownloadHelper r0 = (com.google.android.exoplayer2.offline.DownloadHelper) r0
            java.lang.Object r2 = r1.g
            com.google.android.exoplayer2.offline.DownloadHelper$Callback r2 = (com.google.android.exoplayer2.offline.DownloadHelper.Callback) r2
            com.google.android.exoplayer2.trackselection.DefaultTrackSelector$Parameters r3 = com.google.android.exoplayer2.offline.DownloadHelper.o
            r0.getClass()
            r2.onPrepared(r0)
            return
        L_0x015b:
            java.lang.Object r0 = r1.f
            r4 = r0
            bh r4 = (defpackage.bh) r4
            java.lang.Object r0 = r1.g
            java.lang.Runnable r0 = (java.lang.Runnable) r0
            java.lang.Object r5 = r4.a
            monitor-enter(r5)
            boolean r6 = r4.l     // Catch:{ all -> 0x019c }
            if (r6 == 0) goto L_0x016c
            goto L_0x019a
        L_0x016c:
            long r6 = r4.k     // Catch:{ all -> 0x019c }
            r8 = 1
            long r6 = r6 - r8
            r4.k = r6     // Catch:{ all -> 0x019c }
            int r8 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r8 <= 0) goto L_0x0178
            goto L_0x019a
        L_0x0178:
            if (r8 >= 0) goto L_0x0183
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch:{ all -> 0x019c }
            r0.<init>()     // Catch:{ all -> 0x019c }
            r4.b(r0)     // Catch:{ all -> 0x019c }
            goto L_0x019a
        L_0x0183:
            r4.a()     // Catch:{ all -> 0x019c }
            r0.run()     // Catch:{ IllegalStateException -> 0x0195, Exception -> 0x018a }
            goto L_0x019a
        L_0x018a:
            r0 = move-exception
            r2 = r0
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch:{ all -> 0x019c }
            r0.<init>(r2)     // Catch:{ all -> 0x019c }
            r4.b(r0)     // Catch:{ all -> 0x019c }
            goto L_0x019a
        L_0x0195:
            r0 = move-exception
            r2 = r0
            r4.b(r2)     // Catch:{ all -> 0x019c }
        L_0x019a:
            monitor-exit(r5)     // Catch:{ all -> 0x019c }
            return
        L_0x019c:
            r0 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x019c }
            throw r0
        L_0x019f:
            java.lang.Object r0 = r1.f
            com.google.android.exoplayer2.drm.DefaultDrmSessionManager$c r0 = (com.google.android.exoplayer2.drm.DefaultDrmSessionManager.c) r0
            java.lang.Object r2 = r1.g
            com.google.android.exoplayer2.Format r2 = (com.google.android.exoplayer2.Format) r2
            com.google.android.exoplayer2.drm.DefaultDrmSessionManager r3 = com.google.android.exoplayer2.drm.DefaultDrmSessionManager.this
            int r4 = r3.p
            if (r4 == 0) goto L_0x01c7
            boolean r4 = r0.g
            if (r4 == 0) goto L_0x01b2
            goto L_0x01c7
        L_0x01b2:
            android.os.Looper r4 = r3.t
            java.lang.Object r4 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r4)
            android.os.Looper r4 = (android.os.Looper) r4
            com.google.android.exoplayer2.drm.DrmSessionEventListener$EventDispatcher r5 = r0.c
            com.google.android.exoplayer2.drm.DrmSession r2 = r3.a(r4, r5, r2, r7)
            r0.f = r2
            java.util.Set<com.google.android.exoplayer2.drm.DefaultDrmSessionManager$c> r2 = r3.n
            r2.add(r0)
        L_0x01c7:
            return
        L_0x01c8:
            java.lang.Object r0 = r1.f
            com.google.android.exoplayer2.audio.AudioRendererEventListener$EventDispatcher r0 = (com.google.android.exoplayer2.audio.AudioRendererEventListener.EventDispatcher) r0
            java.lang.Object r2 = r1.g
            java.lang.String r2 = (java.lang.String) r2
            com.google.android.exoplayer2.audio.AudioRendererEventListener r0 = r0.b
            java.lang.Object r0 = com.google.android.exoplayer2.util.Util.castNonNull(r0)
            com.google.android.exoplayer2.audio.AudioRendererEventListener r0 = (com.google.android.exoplayer2.audio.AudioRendererEventListener) r0
            r0.onAudioDecoderReleased(r2)
            return
        L_0x01dc:
            java.lang.Object r0 = r1.f
            com.google.android.exoplayer2.ExoPlayerImplInternal r0 = (com.google.android.exoplayer2.ExoPlayerImplInternal) r0
            java.lang.Object r2 = r1.g
            com.google.android.exoplayer2.PlayerMessage r2 = (com.google.android.exoplayer2.PlayerMessage) r2
            r0.getClass()
            com.google.android.exoplayer2.ExoPlayerImplInternal.b(r2)     // Catch:{ ExoPlaybackException -> 0x01eb }
            return
        L_0x01eb:
            r0 = move-exception
            r2 = r0
            java.lang.String r0 = "ExoPlayerImplInternal"
            java.lang.String r3 = "Unexpected error delivering message on external thread."
            com.google.android.exoplayer2.util.Log.e(r0, r3, r2)
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            r0.<init>(r2)
            throw r0
        L_0x01fa:
            java.lang.Object r0 = r1.f
            r8 = r0
            com.google.android.exoplayer2.a r8 = (com.google.android.exoplayer2.a) r8
            java.lang.Object r0 = r1.g
            com.google.android.exoplayer2.ExoPlayerImplInternal$PlaybackInfoUpdate r0 = (com.google.android.exoplayer2.ExoPlayerImplInternal.PlaybackInfoUpdate) r0
            int r9 = r8.u
            int r10 = r0.c
            int r9 = r9 - r10
            r8.u = r9
            boolean r10 = r0.d
            if (r10 == 0) goto L_0x0214
            int r10 = r0.e
            r8.v = r10
            r8.w = r6
        L_0x0214:
            boolean r10 = r0.f
            if (r10 == 0) goto L_0x021c
            int r10 = r0.g
            r8.x = r10
        L_0x021c:
            if (r9 != 0) goto L_0x02d1
            s8 r9 = r0.b
            com.google.android.exoplayer2.Timeline r9 = r9.a
            s8 r10 = r8.ae
            com.google.android.exoplayer2.Timeline r10 = r10.a
            boolean r10 = r10.isEmpty()
            if (r10 != 0) goto L_0x0237
            boolean r10 = r9.isEmpty()
            if (r10 == 0) goto L_0x0237
            r10 = -1
            r8.af = r10
            r8.ag = r2
        L_0x0237:
            boolean r2 = r9.isEmpty()
            if (r2 != 0) goto L_0x0272
            r2 = r9
            z8 r2 = (defpackage.z8) r2
            com.google.android.exoplayer2.Timeline[] r2 = r2.m
            java.util.List r2 = java.util.Arrays.asList(r2)
            int r3 = r2.size()
            java.util.ArrayList r10 = r8.l
            int r10 = r10.size()
            if (r3 != r10) goto L_0x0254
            r3 = 1
            goto L_0x0255
        L_0x0254:
            r3 = 0
        L_0x0255:
            com.google.android.exoplayer2.util.Assertions.checkState(r3)
            r3 = 0
        L_0x0259:
            int r10 = r2.size()
            if (r3 >= r10) goto L_0x0272
            java.util.ArrayList r10 = r8.l
            java.lang.Object r10 = r10.get(r3)
            com.google.android.exoplayer2.a$a r10 = (com.google.android.exoplayer2.a.C0013a) r10
            java.lang.Object r11 = r2.get(r3)
            com.google.android.exoplayer2.Timeline r11 = (com.google.android.exoplayer2.Timeline) r11
            r10.b = r11
            int r3 = r3 + 1
            goto L_0x0259
        L_0x0272:
            boolean r2 = r8.w
            if (r2 == 0) goto L_0x02c0
            s8 r2 = r0.b
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r2 = r2.b
            s8 r3 = r8.ae
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r3 = r3.b
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x0292
            s8 r2 = r0.b
            long r2 = r2.d
            s8 r10 = r8.ae
            long r10 = r10.s
            int r12 = (r2 > r10 ? 1 : (r2 == r10 ? 0 : -1))
            if (r12 == 0) goto L_0x0291
            goto L_0x0292
        L_0x0291:
            r6 = 0
        L_0x0292:
            if (r6 == 0) goto L_0x02bd
            boolean r2 = r9.isEmpty()
            if (r2 != 0) goto L_0x02b8
            s8 r2 = r0.b
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r2 = r2.b
            boolean r2 = r2.isAd()
            if (r2 == 0) goto L_0x02a5
            goto L_0x02b8
        L_0x02a5:
            s8 r2 = r0.b
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r3 = r2.b
            long r4 = r2.d
            java.lang.Object r2 = r3.a
            com.google.android.exoplayer2.Timeline$Period r3 = r8.k
            r9.getPeriodByUid(r2, r3)
            long r2 = r3.getPositionInWindowUs()
            long r2 = r2 + r4
            goto L_0x02bc
        L_0x02b8:
            s8 r2 = r0.b
            long r2 = r2.d
        L_0x02bc:
            r4 = r2
        L_0x02bd:
            r15 = r4
            r13 = r6
            goto L_0x02c2
        L_0x02c0:
            r15 = r4
            r13 = 0
        L_0x02c2:
            r8.w = r7
            s8 r9 = r0.b
            r10 = 1
            int r11 = r8.x
            r12 = 0
            int r14 = r8.v
            r17 = -1
            r8.m(r9, r10, r11, r12, r13, r14, r15, r17)
        L_0x02d1:
            return
        L_0x02d2:
            java.lang.Object r0 = r1.f
            x r0 = (defpackage.x) r0
            java.lang.Object r2 = r1.g
            lf$a r2 = (defpackage.lf.a) r2
            int r3 = defpackage.x.aa
            r0.getClass()
            info.dourok.voicebot.java.audio.MusicPlayer r0 = r0.g     // Catch:{ Exception -> 0x02ea }
            java.lang.String r3 = r2.a     // Catch:{ Exception -> 0x02ea }
            java.lang.String r4 = r2.e     // Catch:{ Exception -> 0x02ea }
            java.lang.String r2 = r2.b     // Catch:{ Exception -> 0x02ea }
            r0.playFromUrl(r3, r4, r2)     // Catch:{ Exception -> 0x02ea }
        L_0x02ea:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.h2.run():void");
    }
}
