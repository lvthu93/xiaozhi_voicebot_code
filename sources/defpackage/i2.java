package defpackage;

import android.view.Display;
import com.google.android.exoplayer2.ExoPlayerImplInternal;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.a;
import com.google.android.exoplayer2.decoder.OutputBuffer;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.DrmSessionManagerProvider;
import com.google.android.exoplayer2.extractor.BinarySearchSeeker;
import com.google.android.exoplayer2.extractor.FlacStreamMetadata;
import com.google.android.exoplayer2.mediacodec.MediaCodecInfo;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil;
import com.google.android.exoplayer2.offline.DownloadManager;
import com.google.android.exoplayer2.offline.Downloader;
import com.google.android.exoplayer2.offline.ProgressiveDownloader;
import com.google.android.exoplayer2.scheduler.Requirements;
import com.google.android.exoplayer2.scheduler.RequirementsWatcher;
import com.google.android.exoplayer2.text.SimpleSubtitleDecoder;
import com.google.android.exoplayer2.text.SubtitleOutputBuffer;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.trackselection.RandomTrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionUtil;
import com.google.android.exoplayer2.upstream.cache.CacheWriter;
import com.google.android.exoplayer2.util.Consumer;
import com.google.android.exoplayer2.util.ExoFlags;
import com.google.android.exoplayer2.util.ListenerSet;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.video.VideoFrameReleaseHelper;
import defpackage.e0;
import info.dourok.voicebot.java.VoiceBotApplication;
import info.dourok.voicebot.java.services.ZaloMqttClient;
import java.util.regex.Pattern;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextAction;
import org.mozilla.javascript.JavaAdapter;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.tools.shell.Global;

/* renamed from: i2  reason: default package */
public final /* synthetic */ class i2 implements ListenerSet.IterationFinishedEvent, ExoPlayerImplInternal.PlaybackInfoUpdateListener, Consumer, BinarySearchSeeker.SeekTimestampConverter, MediaCodecUtil.e, RequirementsWatcher.Listener, CacheWriter.ProgressListener, DrmSessionManagerProvider, OutputBuffer.Owner, TrackSelectionUtil.AdaptiveTrackSelectionFactory, VideoFrameReleaseHelper.DisplayHelper.Listener, ZaloMqttClient.MessageCallback, ContextAction {
    public final /* synthetic */ int c;
    public final /* synthetic */ Object f;

    public /* synthetic */ i2(int i, Object obj) {
        this.c = i;
        this.f = obj;
    }

    public final void accept(Object obj) {
        ((DrmSessionEventListener.EventDispatcher) obj).drmSessionManagerError((Exception) this.f);
    }

    public final ExoTrackSelection createAdaptiveTrackSelection(ExoTrackSelection.Definition definition) {
        RandomTrackSelection.Factory factory = (RandomTrackSelection.Factory) this.f;
        factory.getClass();
        return new RandomTrackSelection(definition.a, definition.b, definition.c, factory.a);
    }

    public final DrmSessionManager get(MediaItem mediaItem) {
        return (DrmSessionManager) this.f;
    }

    public final int getScore(Object obj) {
        Format format = (Format) this.f;
        MediaCodecInfo mediaCodecInfo = (MediaCodecInfo) obj;
        Pattern pattern = MediaCodecUtil.a;
        try {
            return mediaCodecInfo.isFormatSupported(format) ? 1 : 0;
        } catch (MediaCodecUtil.DecoderQueryException unused) {
            return -1;
        }
    }

    public final void invoke(Object obj, ExoFlags exoFlags) {
        ((Player.EventListener) obj).onEvents((Player) this.f, new Player.Events(exoFlags));
    }

    public final void onDefaultDisplayChanged(Display display) {
        VideoFrameReleaseHelper videoFrameReleaseHelper = (VideoFrameReleaseHelper) this.f;
        videoFrameReleaseHelper.getClass();
        if (display != null) {
            long refreshRate = (long) (1.0E9d / ((double) display.getRefreshRate()));
            videoFrameReleaseHelper.j = refreshRate;
            videoFrameReleaseHelper.k = (refreshRate * 80) / 100;
            return;
        }
        Log.w("VideoFrameReleaseHelper", "Unable to query display refresh rate");
        videoFrameReleaseHelper.j = -9223372036854775807L;
        videoFrameReleaseHelper.k = -9223372036854775807L;
    }

    public final void onMessage(String str, String str2) {
        ((VoiceBotApplication) this.f).lambda$startZaloMqtt$2(str, str2);
    }

    public final void onPlaybackInfoUpdate(ExoPlayerImplInternal.PlaybackInfoUpdate playbackInfoUpdate) {
        a aVar = (a) this.f;
        aVar.getClass();
        aVar.f.post(new h2(0, aVar, playbackInfoUpdate));
    }

    public final void onProgress(long j, long j2, long j3) {
        float f2;
        Downloader.ProgressListener progressListener = ((ProgressiveDownloader) this.f).f;
        if (progressListener != null) {
            if (j == -1 || j == 0) {
                f2 = -1.0f;
            } else {
                f2 = (((float) j2) * 100.0f) / ((float) j);
            }
            progressListener.onProgress(j, j2, f2);
        }
    }

    public final void onRequirementsStateChanged(RequirementsWatcher requirementsWatcher, int i) {
        Requirements requirements = DownloadManager.q;
        ((DownloadManager) this.f).c(requirementsWatcher, i);
    }

    public final void releaseOutputBuffer(OutputBuffer outputBuffer) {
        boolean z;
        switch (this.c) {
            case 8:
                SimpleSubtitleDecoder simpleSubtitleDecoder = (SimpleSubtitleDecoder) this.f;
                O o = (SubtitleOutputBuffer) outputBuffer;
                synchronized (simpleSubtitleDecoder.b) {
                    o.clear();
                    int i = simpleSubtitleDecoder.h;
                    simpleSubtitleDecoder.h = i + 1;
                    simpleSubtitleDecoder.f[i] = o;
                    if (simpleSubtitleDecoder.c.isEmpty() || simpleSubtitleDecoder.h <= 0) {
                        z = false;
                    } else {
                        z = true;
                    }
                    if (z) {
                        simpleSubtitleDecoder.b.notify();
                    }
                }
                return;
            default:
                e0 e0Var = (e0) this.f;
                e0.b bVar = (e0.b) outputBuffer;
                e0Var.getClass();
                bVar.clear();
                e0Var.b.add(bVar);
                return;
        }
    }

    public final Object run(Context context) {
        int i = this.c;
        Object obj = this.f;
        switch (i) {
            case 13:
                return JavaAdapter.lambda$runScript$1((Script) obj, context);
            default:
                return ((Global) obj).lambda$init$0(context);
        }
    }

    public final long timeUsToTargetTime(long j) {
        return ((FlacStreamMetadata) this.f).getSampleNumber(j);
    }
}
