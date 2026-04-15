package com.google.android.exoplayer2.offline;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.SparseIntArray;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Renderer;
import com.google.android.exoplayer2.RendererCapabilities;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.decoder.DecoderReuseEvaluation;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.offline.DownloadRequest;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.chunk.Chunk;
import com.google.android.exoplayer2.source.chunk.MediaChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunkIterator;
import com.google.android.exoplayer2.trackselection.BaseTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoRendererEventListener;
import com.google.android.exoplayer2.video.VideoSize;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;

public final class DownloadHelper {
    public static final DefaultTrackSelector.Parameters o = DefaultTrackSelector.Parameters.an.buildUpon().setForceHighestSupportedBitrate(true).build();
    public final MediaItem.PlaybackProperties a;
    @Nullable
    public final MediaSource b;
    public final DefaultTrackSelector c;
    public final RendererCapabilities[] d;
    public final SparseIntArray e = new SparseIntArray();
    public final Handler f;
    public final Timeline.Window g;
    public boolean h;
    public Callback i;
    public e j;
    public TrackGroupArray[] k;
    public MappingTrackSelector.MappedTrackInfo[] l;
    public List<ExoTrackSelection>[][] m;
    public List<ExoTrackSelection>[][] n;

    public interface Callback {
        void onPrepareError(DownloadHelper downloadHelper, IOException iOException);

        void onPrepared(DownloadHelper downloadHelper);
    }

    public static class LiveContentUnsupportedException extends IOException {
    }

    public class a implements VideoRendererEventListener {
        public /* bridge */ /* synthetic */ void onDroppedFrames(int i, long j) {
            pd.a(this, i, j);
        }

        public /* bridge */ /* synthetic */ void onRenderedFirstFrame(Object obj, long j) {
            pd.b(this, obj, j);
        }

        public /* bridge */ /* synthetic */ void onVideoCodecError(Exception exc) {
            pd.c(this, exc);
        }

        public /* bridge */ /* synthetic */ void onVideoDecoderInitialized(String str, long j, long j2) {
            pd.d(this, str, j, j2);
        }

        public /* bridge */ /* synthetic */ void onVideoDecoderReleased(String str) {
            pd.e(this, str);
        }

        public /* bridge */ /* synthetic */ void onVideoDisabled(DecoderCounters decoderCounters) {
            pd.f(this, decoderCounters);
        }

        public /* bridge */ /* synthetic */ void onVideoEnabled(DecoderCounters decoderCounters) {
            pd.g(this, decoderCounters);
        }

        public /* bridge */ /* synthetic */ void onVideoFrameProcessingOffset(long j, int i) {
            pd.h(this, j, i);
        }

        @Deprecated
        public /* bridge */ /* synthetic */ void onVideoInputFormatChanged(Format format) {
            pd.i(this, format);
        }

        public /* bridge */ /* synthetic */ void onVideoInputFormatChanged(Format format, @Nullable DecoderReuseEvaluation decoderReuseEvaluation) {
            pd.j(this, format, decoderReuseEvaluation);
        }

        public /* bridge */ /* synthetic */ void onVideoSizeChanged(VideoSize videoSize) {
            pd.k(this, videoSize);
        }
    }

    public class b implements AudioRendererEventListener {
        public /* bridge */ /* synthetic */ void onAudioCodecError(Exception exc) {
            bp.a(this, exc);
        }

        public /* bridge */ /* synthetic */ void onAudioDecoderInitialized(String str, long j, long j2) {
            bp.b(this, str, j, j2);
        }

        public /* bridge */ /* synthetic */ void onAudioDecoderReleased(String str) {
            bp.c(this, str);
        }

        public /* bridge */ /* synthetic */ void onAudioDisabled(DecoderCounters decoderCounters) {
            bp.d(this, decoderCounters);
        }

        public /* bridge */ /* synthetic */ void onAudioEnabled(DecoderCounters decoderCounters) {
            bp.e(this, decoderCounters);
        }

        @Deprecated
        public /* bridge */ /* synthetic */ void onAudioInputFormatChanged(Format format) {
            bp.f(this, format);
        }

        public /* bridge */ /* synthetic */ void onAudioInputFormatChanged(Format format, @Nullable DecoderReuseEvaluation decoderReuseEvaluation) {
            bp.g(this, format, decoderReuseEvaluation);
        }

        public /* bridge */ /* synthetic */ void onAudioPositionAdvancing(long j) {
            bp.h(this, j);
        }

        public /* bridge */ /* synthetic */ void onAudioSinkError(Exception exc) {
            bp.i(this, exc);
        }

        public /* bridge */ /* synthetic */ void onAudioUnderrun(int i, long j, long j2) {
            bp.j(this, i, j, j2);
        }

        public /* bridge */ /* synthetic */ void onSkipSilenceEnabledChanged(boolean z) {
            bp.k(this, z);
        }
    }

    public static final class c extends BaseTrackSelection {

        public static final class a implements ExoTrackSelection.Factory {
            public ExoTrackSelection[] createTrackSelections(ExoTrackSelection.Definition[] definitionArr, BandwidthMeter bandwidthMeter, MediaSource.MediaPeriodId mediaPeriodId, Timeline timeline) {
                c cVar;
                ExoTrackSelection[] exoTrackSelectionArr = new ExoTrackSelection[definitionArr.length];
                for (int i = 0; i < definitionArr.length; i++) {
                    ExoTrackSelection.Definition definition = definitionArr[i];
                    if (definition == null) {
                        cVar = null;
                    } else {
                        cVar = new c(definition.a, definition.b);
                    }
                    exoTrackSelectionArr[i] = cVar;
                }
                return exoTrackSelectionArr;
            }
        }

        public c(TrackGroup trackGroup, int[] iArr) {
            super(trackGroup, iArr);
        }

        public int getSelectedIndex() {
            return 0;
        }

        @Nullable
        public Object getSelectionData() {
            return null;
        }

        public int getSelectionReason() {
            return 0;
        }

        public /* bridge */ /* synthetic */ void onDiscontinuity() {
            s2.a(this);
        }

        public /* bridge */ /* synthetic */ void onPlayWhenReadyChanged(boolean z) {
            s2.b(this, z);
        }

        public /* bridge */ /* synthetic */ void onRebuffer() {
            s2.c(this);
        }

        public /* bridge */ /* synthetic */ boolean shouldCancelChunkLoad(long j, Chunk chunk, List list) {
            return s2.d(this, j, chunk, list);
        }

        public void updateSelectedTrack(long j, long j2, long j3, List<? extends MediaChunk> list, MediaChunkIterator[] mediaChunkIteratorArr) {
        }
    }

    public static final class d implements BandwidthMeter {
        public void addEventListener(Handler handler, BandwidthMeter.EventListener eventListener) {
        }

        public long getBitrateEstimate() {
            return 0;
        }

        public /* bridge */ /* synthetic */ long getTimeToFirstByteEstimateUs() {
            return cf.a(this);
        }

        @Nullable
        public TransferListener getTransferListener() {
            return null;
        }

        public void removeEventListener(BandwidthMeter.EventListener eventListener) {
        }
    }

    public static final class e implements MediaSource.MediaSourceCaller, MediaPeriod.Callback, Handler.Callback {
        public final MediaSource c;
        public final DownloadHelper f;
        public final DefaultAllocator g = new DefaultAllocator(true, 65536);
        public final ArrayList<MediaPeriod> h = new ArrayList<>();
        public final Handler i = Util.createHandlerForCurrentOrMainLooper(new q1(0, this));
        public final HandlerThread j;
        public final Handler k;
        public Timeline l;
        public MediaPeriod[] m;
        public boolean n;

        public e(MediaSource mediaSource, DownloadHelper downloadHelper) {
            this.c = mediaSource;
            this.f = downloadHelper;
            HandlerThread handlerThread = new HandlerThread("ExoPlayer:DownloadHelper");
            this.j = handlerThread;
            handlerThread.start();
            Handler createHandler = Util.createHandler(handlerThread.getLooper(), this);
            this.k = createHandler;
            createHandler.sendEmptyMessage(0);
        }

        public boolean handleMessage(Message message) {
            int i2 = message.what;
            Handler handler = this.k;
            MediaSource mediaSource = this.c;
            if (i2 != 0) {
                int i3 = 0;
                ArrayList<MediaPeriod> arrayList = this.h;
                if (i2 == 1) {
                    try {
                        if (this.m == null) {
                            mediaSource.maybeThrowSourceInfoRefreshError();
                        } else {
                            while (i3 < arrayList.size()) {
                                arrayList.get(i3).maybeThrowPrepareError();
                                i3++;
                            }
                        }
                        handler.sendEmptyMessageDelayed(1, 100);
                    } catch (IOException e) {
                        this.i.obtainMessage(1, e).sendToTarget();
                    }
                    return true;
                } else if (i2 == 2) {
                    MediaPeriod mediaPeriod = (MediaPeriod) message.obj;
                    if (arrayList.contains(mediaPeriod)) {
                        mediaPeriod.continueLoading(0);
                    }
                    return true;
                } else if (i2 != 3) {
                    return false;
                } else {
                    MediaPeriod[] mediaPeriodArr = this.m;
                    if (mediaPeriodArr != null) {
                        int length = mediaPeriodArr.length;
                        while (i3 < length) {
                            mediaSource.releasePeriod(mediaPeriodArr[i3]);
                            i3++;
                        }
                    }
                    mediaSource.releaseSource(this);
                    handler.removeCallbacksAndMessages((Object) null);
                    this.j.quit();
                    return true;
                }
            } else {
                mediaSource.prepareSource(this, (TransferListener) null);
                handler.sendEmptyMessage(1);
                return true;
            }
        }

        public void onPrepared(MediaPeriod mediaPeriod) {
            ArrayList<MediaPeriod> arrayList = this.h;
            arrayList.remove(mediaPeriod);
            if (arrayList.isEmpty()) {
                this.k.removeMessages(1);
                this.i.sendEmptyMessage(0);
            }
        }

        public void onSourceInfoRefreshed(MediaSource mediaSource, Timeline timeline) {
            MediaPeriod[] mediaPeriodArr;
            if (this.l == null) {
                if (timeline.getWindow(0, new Timeline.Window()).isLive()) {
                    this.i.obtainMessage(1, new LiveContentUnsupportedException()).sendToTarget();
                    return;
                }
                this.l = timeline;
                this.m = new MediaPeriod[timeline.getPeriodCount()];
                int i2 = 0;
                while (true) {
                    mediaPeriodArr = this.m;
                    if (i2 >= mediaPeriodArr.length) {
                        break;
                    }
                    MediaPeriod createPeriod = this.c.createPeriod(new MediaSource.MediaPeriodId(timeline.getUidOfPeriod(i2)), this.g, 0);
                    this.m[i2] = createPeriod;
                    this.h.add(createPeriod);
                    i2++;
                }
                for (MediaPeriod prepare : mediaPeriodArr) {
                    prepare.prepare(this, 0);
                }
            }
        }

        public void release() {
            if (!this.n) {
                this.n = true;
                this.k.sendEmptyMessage(3);
            }
        }

        public void onContinueLoadingRequested(MediaPeriod mediaPeriod) {
            if (this.h.contains(mediaPeriod)) {
                this.k.obtainMessage(2, mediaPeriod).sendToTarget();
            }
        }
    }

    public DownloadHelper(MediaItem mediaItem, @Nullable MediaSource mediaSource, DefaultTrackSelector.Parameters parameters, RendererCapabilities[] rendererCapabilitiesArr) {
        this.a = (MediaItem.PlaybackProperties) Assertions.checkNotNull(mediaItem.f);
        this.b = mediaSource;
        DefaultTrackSelector defaultTrackSelector = new DefaultTrackSelector(parameters, (ExoTrackSelection.Factory) new c.a());
        this.c = defaultTrackSelector;
        this.d = rendererCapabilitiesArr;
        defaultTrackSelector.init(new z6(11), new d());
        this.f = Util.createHandlerForCurrentOrMainLooper();
        this.g = new Timeline.Window();
    }

    public static MediaSource createMediaSource(DownloadRequest downloadRequest, DataSource.Factory factory) {
        return createMediaSource(downloadRequest, factory, (DrmSessionManager) null);
    }

    @Deprecated
    public static DownloadHelper forDash(Context context, Uri uri, DataSource.Factory factory, RenderersFactory renderersFactory) {
        return forDash(uri, factory, renderersFactory, (DrmSessionManager) null, getDefaultTrackSelectorParameters(context));
    }

    @Deprecated
    public static DownloadHelper forHls(Context context, Uri uri, DataSource.Factory factory, RenderersFactory renderersFactory) {
        return forHls(uri, factory, renderersFactory, (DrmSessionManager) null, getDefaultTrackSelectorParameters(context));
    }

    public static DownloadHelper forMediaItem(Context context, MediaItem mediaItem) {
        MediaItem.PlaybackProperties playbackProperties = (MediaItem.PlaybackProperties) Assertions.checkNotNull(mediaItem.f);
        Assertions.checkArgument(Util.inferContentTypeForUriAndMimeType(playbackProperties.a, playbackProperties.b) == 4);
        return forMediaItem(mediaItem, getDefaultTrackSelectorParameters(context), (RenderersFactory) null, (DataSource.Factory) null, (DrmSessionManager) null);
    }

    @Deprecated
    public static DownloadHelper forProgressive(Context context, Uri uri) {
        return forMediaItem(context, new MediaItem.Builder().setUri(uri).build());
    }

    @Deprecated
    public static DownloadHelper forSmoothStreaming(Uri uri, DataSource.Factory factory, RenderersFactory renderersFactory) {
        return forSmoothStreaming(uri, factory, renderersFactory, (DrmSessionManager) null, o);
    }

    public static DefaultTrackSelector.Parameters getDefaultTrackSelectorParameters(Context context) {
        return DefaultTrackSelector.Parameters.getDefaults(context).buildUpon().setForceHighestSupportedBitrate(true).build();
    }

    public static RendererCapabilities[] getRendererCapabilities(RenderersFactory renderersFactory) {
        Renderer[] createRenderers = renderersFactory.createRenderers(Util.createHandlerForCurrentOrMainLooper(), new a(), new b(), new o1(), new p1());
        RendererCapabilities[] rendererCapabilitiesArr = new RendererCapabilities[createRenderers.length];
        for (int i2 = 0; i2 < createRenderers.length; i2++) {
            rendererCapabilitiesArr[i2] = createRenderers[i2].getCapabilities();
        }
        return rendererCapabilitiesArr;
    }

    @EnsuresNonNull({"trackGroupArrays", "mappedTrackInfos", "trackSelectionsByPeriodAndRenderer", "immutableTrackSelectionsByPeriodAndRenderer", "mediaPreparer", "mediaPreparer.timeline", "mediaPreparer.mediaPeriods"})
    public final void a() {
        Assertions.checkState(this.h);
    }

    public void addAudioLanguagesToSelection(String... strArr) {
        a();
        for (int i2 = 0; i2 < this.l.length; i2++) {
            DefaultTrackSelector.ParametersBuilder buildUpon = o.buildUpon();
            MappingTrackSelector.MappedTrackInfo mappedTrackInfo = this.l[i2];
            int rendererCount = mappedTrackInfo.getRendererCount();
            for (int i3 = 0; i3 < rendererCount; i3++) {
                if (mappedTrackInfo.getRendererType(i3) != 1) {
                    buildUpon.setRendererDisabled(i3, true);
                }
            }
            for (String preferredAudioLanguage : strArr) {
                buildUpon.setPreferredAudioLanguage(preferredAudioLanguage);
                addTrackSelection(i2, buildUpon.build());
            }
        }
    }

    public void addTextLanguagesToSelection(boolean z, String... strArr) {
        a();
        for (int i2 = 0; i2 < this.l.length; i2++) {
            DefaultTrackSelector.ParametersBuilder buildUpon = o.buildUpon();
            MappingTrackSelector.MappedTrackInfo mappedTrackInfo = this.l[i2];
            int rendererCount = mappedTrackInfo.getRendererCount();
            for (int i3 = 0; i3 < rendererCount; i3++) {
                if (mappedTrackInfo.getRendererType(i3) != 3) {
                    buildUpon.setRendererDisabled(i3, true);
                }
            }
            buildUpon.setSelectUndeterminedTextLanguage(z);
            for (String preferredTextLanguage : strArr) {
                buildUpon.setPreferredTextLanguage(preferredTextLanguage);
                addTrackSelection(i2, buildUpon.build());
            }
        }
    }

    public void addTrackSelection(int i2, DefaultTrackSelector.Parameters parameters) {
        a();
        this.c.setParameters(parameters);
        b(i2);
    }

    public void addTrackSelectionForSingleRenderer(int i2, int i3, DefaultTrackSelector.Parameters parameters, List<DefaultTrackSelector.SelectionOverride> list) {
        boolean z;
        a();
        DefaultTrackSelector.ParametersBuilder buildUpon = parameters.buildUpon();
        for (int i4 = 0; i4 < this.l[i2].getRendererCount(); i4++) {
            if (i4 != i3) {
                z = true;
            } else {
                z = false;
            }
            buildUpon.setRendererDisabled(i4, z);
        }
        if (list.isEmpty()) {
            addTrackSelection(i2, buildUpon.build());
            return;
        }
        TrackGroupArray trackGroups = this.l[i2].getTrackGroups(i3);
        for (int i5 = 0; i5 < list.size(); i5++) {
            buildUpon.setSelectionOverride(i3, trackGroups, list.get(i5));
            addTrackSelection(i2, buildUpon.build());
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0048, code lost:
        r7 = r11.e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:?, code lost:
        r7.clear();
        r8 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0052, code lost:
        if (r8 >= r6.length()) goto L_0x005e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0054, code lost:
        r7.put(r6.getIndexInTrackGroup(r8), 0);
        r8 = r8 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x005e, code lost:
        r8 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0063, code lost:
        if (r8 >= r3.length()) goto L_0x006f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0065, code lost:
        r7.put(r3.getIndexInTrackGroup(r8), 0);
        r8 = r8 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x006f, code lost:
        r8 = new int[r7.size()];
        r9 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x007a, code lost:
        if (r9 >= r7.size()) goto L_0x0085;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x007c, code lost:
        r8[r9] = r7.keyAt(r9);
        r9 = r9 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0085, code lost:
        r4.set(r5, new com.google.android.exoplayer2.offline.DownloadHelper.c(r6.getTrackGroup(), r8));
        r5 = true;
     */
    @org.checkerframework.checker.nullness.qual.RequiresNonNull({"trackGroupArrays", "trackSelectionsByPeriodAndRenderer", "mediaPreparer", "mediaPreparer.timeline"})
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.android.exoplayer2.trackselection.TrackSelectorResult b(int r12) {
        /*
            r11 = this;
            com.google.android.exoplayer2.trackselection.DefaultTrackSelector r0 = r11.c     // Catch:{ ExoPlaybackException -> 0x00a0 }
            com.google.android.exoplayer2.RendererCapabilities[] r1 = r11.d     // Catch:{ ExoPlaybackException -> 0x00a0 }
            com.google.android.exoplayer2.source.TrackGroupArray[] r2 = r11.k     // Catch:{ ExoPlaybackException -> 0x00a0 }
            r2 = r2[r12]     // Catch:{ ExoPlaybackException -> 0x00a0 }
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r3 = new com.google.android.exoplayer2.source.MediaSource$MediaPeriodId     // Catch:{ ExoPlaybackException -> 0x00a0 }
            com.google.android.exoplayer2.offline.DownloadHelper$e r4 = r11.j     // Catch:{ ExoPlaybackException -> 0x00a0 }
            com.google.android.exoplayer2.Timeline r4 = r4.l     // Catch:{ ExoPlaybackException -> 0x00a0 }
            java.lang.Object r4 = r4.getUidOfPeriod(r12)     // Catch:{ ExoPlaybackException -> 0x00a0 }
            r3.<init>((java.lang.Object) r4)     // Catch:{ ExoPlaybackException -> 0x00a0 }
            com.google.android.exoplayer2.offline.DownloadHelper$e r4 = r11.j     // Catch:{ ExoPlaybackException -> 0x00a0 }
            com.google.android.exoplayer2.Timeline r4 = r4.l     // Catch:{ ExoPlaybackException -> 0x00a0 }
            com.google.android.exoplayer2.trackselection.TrackSelectorResult r0 = r0.selectTracks(r1, r2, r3, r4)     // Catch:{ ExoPlaybackException -> 0x00a0 }
            r1 = 0
            r2 = 0
        L_0x001f:
            int r3 = r0.a     // Catch:{ ExoPlaybackException -> 0x00a0 }
            if (r2 >= r3) goto L_0x009f
            com.google.android.exoplayer2.trackselection.ExoTrackSelection[] r3 = r0.c     // Catch:{ ExoPlaybackException -> 0x00a0 }
            r3 = r3[r2]     // Catch:{ ExoPlaybackException -> 0x00a0 }
            if (r3 != 0) goto L_0x002b
            goto L_0x009c
        L_0x002b:
            java.util.List<com.google.android.exoplayer2.trackselection.ExoTrackSelection>[][] r4 = r11.m     // Catch:{ ExoPlaybackException -> 0x00a0 }
            r4 = r4[r12]     // Catch:{ ExoPlaybackException -> 0x00a0 }
            r4 = r4[r2]     // Catch:{ ExoPlaybackException -> 0x00a0 }
            r5 = 0
        L_0x0032:
            int r6 = r4.size()     // Catch:{ ExoPlaybackException -> 0x00a0 }
            if (r5 >= r6) goto L_0x0096
            java.lang.Object r6 = r4.get(r5)     // Catch:{ ExoPlaybackException -> 0x00a0 }
            com.google.android.exoplayer2.trackselection.ExoTrackSelection r6 = (com.google.android.exoplayer2.trackselection.ExoTrackSelection) r6     // Catch:{ ExoPlaybackException -> 0x00a0 }
            com.google.android.exoplayer2.source.TrackGroup r7 = r6.getTrackGroup()     // Catch:{ ExoPlaybackException -> 0x00a0 }
            com.google.android.exoplayer2.source.TrackGroup r8 = r3.getTrackGroup()     // Catch:{ ExoPlaybackException -> 0x00a0 }
            if (r7 != r8) goto L_0x0093
            android.util.SparseIntArray r7 = r11.e
            r7.clear()     // Catch:{ ExoPlaybackException -> 0x00a0 }
            r8 = 0
        L_0x004e:
            int r9 = r6.length()     // Catch:{ ExoPlaybackException -> 0x00a0 }
            if (r8 >= r9) goto L_0x005e
            int r9 = r6.getIndexInTrackGroup(r8)     // Catch:{ ExoPlaybackException -> 0x00a0 }
            r7.put(r9, r1)     // Catch:{ ExoPlaybackException -> 0x00a0 }
            int r8 = r8 + 1
            goto L_0x004e
        L_0x005e:
            r8 = 0
        L_0x005f:
            int r9 = r3.length()     // Catch:{ ExoPlaybackException -> 0x00a0 }
            if (r8 >= r9) goto L_0x006f
            int r9 = r3.getIndexInTrackGroup(r8)     // Catch:{ ExoPlaybackException -> 0x00a0 }
            r7.put(r9, r1)     // Catch:{ ExoPlaybackException -> 0x00a0 }
            int r8 = r8 + 1
            goto L_0x005f
        L_0x006f:
            int r8 = r7.size()     // Catch:{ ExoPlaybackException -> 0x00a0 }
            int[] r8 = new int[r8]     // Catch:{ ExoPlaybackException -> 0x00a0 }
            r9 = 0
        L_0x0076:
            int r10 = r7.size()     // Catch:{ ExoPlaybackException -> 0x00a0 }
            if (r9 >= r10) goto L_0x0085
            int r10 = r7.keyAt(r9)     // Catch:{ ExoPlaybackException -> 0x00a0 }
            r8[r9] = r10     // Catch:{ ExoPlaybackException -> 0x00a0 }
            int r9 = r9 + 1
            goto L_0x0076
        L_0x0085:
            com.google.android.exoplayer2.offline.DownloadHelper$c r7 = new com.google.android.exoplayer2.offline.DownloadHelper$c     // Catch:{ ExoPlaybackException -> 0x00a0 }
            com.google.android.exoplayer2.source.TrackGroup r6 = r6.getTrackGroup()     // Catch:{ ExoPlaybackException -> 0x00a0 }
            r7.<init>(r6, r8)     // Catch:{ ExoPlaybackException -> 0x00a0 }
            r4.set(r5, r7)     // Catch:{ ExoPlaybackException -> 0x00a0 }
            r5 = 1
            goto L_0x0097
        L_0x0093:
            int r5 = r5 + 1
            goto L_0x0032
        L_0x0096:
            r5 = 0
        L_0x0097:
            if (r5 != 0) goto L_0x009c
            r4.add(r3)     // Catch:{ ExoPlaybackException -> 0x00a0 }
        L_0x009c:
            int r2 = r2 + 1
            goto L_0x001f
        L_0x009f:
            return r0
        L_0x00a0:
            r12 = move-exception
            java.lang.UnsupportedOperationException r0 = new java.lang.UnsupportedOperationException
            r0.<init>(r12)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.offline.DownloadHelper.b(int):com.google.android.exoplayer2.trackselection.TrackSelectorResult");
    }

    public void clearTrackSelections(int i2) {
        a();
        for (int i3 = 0; i3 < this.d.length; i3++) {
            this.m[i2][i3].clear();
        }
    }

    public DownloadRequest getDownloadRequest(@Nullable byte[] bArr) {
        return getDownloadRequest(this.a.a.toString(), bArr);
    }

    @Nullable
    public Object getManifest() {
        if (this.b == null) {
            return null;
        }
        a();
        if (this.j.l.getWindowCount() > 0) {
            return this.j.l.getWindow(0, this.g).h;
        }
        return null;
    }

    public MappingTrackSelector.MappedTrackInfo getMappedTrackInfo(int i2) {
        a();
        return this.l[i2];
    }

    public int getPeriodCount() {
        if (this.b == null) {
            return 0;
        }
        a();
        return this.k.length;
    }

    public TrackGroupArray getTrackGroups(int i2) {
        a();
        return this.k[i2];
    }

    public List<ExoTrackSelection> getTrackSelections(int i2, int i3) {
        a();
        return this.n[i2][i3];
    }

    public void prepare(Callback callback) {
        boolean z;
        if (this.i == null) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkState(z);
        this.i = callback;
        MediaSource mediaSource = this.b;
        if (mediaSource != null) {
            this.j = new e(mediaSource, this);
            return;
        }
        this.f.post(new h2(5, this, callback));
    }

    public void release() {
        e eVar = this.j;
        if (eVar != null) {
            eVar.release();
        }
    }

    public void replaceTrackSelections(int i2, DefaultTrackSelector.Parameters parameters) {
        clearTrackSelections(i2);
        addTrackSelection(i2, parameters);
    }

    public static MediaSource createMediaSource(DownloadRequest downloadRequest, DataSource.Factory factory, @Nullable DrmSessionManager drmSessionManager) {
        return new DefaultMediaSourceFactory(factory, (ExtractorsFactory) ExtractorsFactory.d).setDrmSessionManager(drmSessionManager).createMediaSource(downloadRequest.toMediaItem());
    }

    @Deprecated
    public static DownloadHelper forProgressive(Context context, Uri uri, @Nullable String str) {
        return forMediaItem(context, new MediaItem.Builder().setUri(uri).setCustomCacheKey(str).build());
    }

    @Deprecated
    public static DownloadHelper forSmoothStreaming(Context context, Uri uri, DataSource.Factory factory, RenderersFactory renderersFactory) {
        return forSmoothStreaming(uri, factory, renderersFactory, (DrmSessionManager) null, getDefaultTrackSelectorParameters(context));
    }

    public DownloadRequest getDownloadRequest(String str, @Nullable byte[] bArr) {
        MediaItem.PlaybackProperties playbackProperties = this.a;
        DownloadRequest.Builder mimeType = new DownloadRequest.Builder(str, playbackProperties.a).setMimeType(playbackProperties.b);
        MediaItem.DrmConfiguration drmConfiguration = playbackProperties.c;
        DownloadRequest.Builder data = mimeType.setKeySetId(drmConfiguration != null ? drmConfiguration.getKeySetId() : null).setCustomCacheKey(playbackProperties.f).setData(bArr);
        if (this.b == null) {
            return data.build();
        }
        a();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        int length = this.m.length;
        for (int i2 = 0; i2 < length; i2++) {
            arrayList2.clear();
            for (List<ExoTrackSelection> addAll : this.m[i2]) {
                arrayList2.addAll(addAll);
            }
            arrayList.addAll(this.j.m[i2].getStreamKeys(arrayList2));
        }
        return data.setStreamKeys(arrayList).build();
    }

    @Deprecated
    public static DownloadHelper forDash(Uri uri, DataSource.Factory factory, RenderersFactory renderersFactory, @Nullable DrmSessionManager drmSessionManager, DefaultTrackSelector.Parameters parameters) {
        return forMediaItem(new MediaItem.Builder().setUri(uri).setMimeType("application/dash+xml").build(), parameters, renderersFactory, factory, drmSessionManager);
    }

    @Deprecated
    public static DownloadHelper forHls(Uri uri, DataSource.Factory factory, RenderersFactory renderersFactory, @Nullable DrmSessionManager drmSessionManager, DefaultTrackSelector.Parameters parameters) {
        return forMediaItem(new MediaItem.Builder().setUri(uri).setMimeType("application/x-mpegURL").build(), parameters, renderersFactory, factory, drmSessionManager);
    }

    @Deprecated
    public static DownloadHelper forSmoothStreaming(Uri uri, DataSource.Factory factory, RenderersFactory renderersFactory, @Nullable DrmSessionManager drmSessionManager, DefaultTrackSelector.Parameters parameters) {
        return forMediaItem(new MediaItem.Builder().setUri(uri).setMimeType("application/vnd.ms-sstr+xml").build(), parameters, renderersFactory, factory, drmSessionManager);
    }

    public static DownloadHelper forMediaItem(Context context, MediaItem mediaItem, @Nullable RenderersFactory renderersFactory, @Nullable DataSource.Factory factory) {
        return forMediaItem(mediaItem, getDefaultTrackSelectorParameters(context), renderersFactory, factory, (DrmSessionManager) null);
    }

    public static DownloadHelper forMediaItem(MediaItem mediaItem, DefaultTrackSelector.Parameters parameters, @Nullable RenderersFactory renderersFactory, @Nullable DataSource.Factory factory) {
        return forMediaItem(mediaItem, parameters, renderersFactory, factory, (DrmSessionManager) null);
    }

    public static DownloadHelper forMediaItem(MediaItem mediaItem, DefaultTrackSelector.Parameters parameters, @Nullable RenderersFactory renderersFactory, @Nullable DataSource.Factory factory, @Nullable DrmSessionManager drmSessionManager) {
        MediaSource mediaSource;
        MediaItem.PlaybackProperties playbackProperties = (MediaItem.PlaybackProperties) Assertions.checkNotNull(mediaItem.f);
        boolean z = true;
        boolean z2 = Util.inferContentTypeForUriAndMimeType(playbackProperties.a, playbackProperties.b) == 4;
        if (!z2 && factory == null) {
            z = false;
        }
        Assertions.checkArgument(z);
        if (z2) {
            mediaSource = null;
        } else {
            mediaSource = new DefaultMediaSourceFactory((DataSource.Factory) Util.castNonNull(factory), (ExtractorsFactory) ExtractorsFactory.d).setDrmSessionManager(drmSessionManager).createMediaSource(mediaItem);
        }
        return new DownloadHelper(mediaItem, mediaSource, parameters, renderersFactory != null ? getRendererCapabilities(renderersFactory) : new RendererCapabilities[0]);
    }
}
