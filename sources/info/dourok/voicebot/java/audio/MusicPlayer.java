package info.dourok.voicebot.java.audio;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.device.DeviceInfo;
import com.google.android.exoplayer2.ext.okhttp.OkHttpDataSource;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.video.VideoSize;
import info.dourok.voicebot.java.services.ZingMp3Service;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;
import okhttp3.OkHttpClient;

public class MusicPlayer {
    public k a;
    public final Context b;
    public SimpleExoPlayer c;
    public m d;
    public final AtomicBoolean e = new AtomicBoolean(false);
    public final AtomicBoolean f = new AtomicBoolean(false);
    public String g = "";
    public String h = "";
    public long i = 0;
    public long j = 0;
    public long k = 0;
    public final ExecutorService l = Executors.newSingleThreadExecutor(new b());
    public final Handler m = new Handler(Looper.getMainLooper());
    public final OkHttpClient n = oa.c();
    public final ZingMp3Service o = new ZingMp3Service();

    public class a implements Runnable {
        public final /* synthetic */ String c;

        public a(String str) {
            this.c = str;
        }

        public final void run() {
            MusicPlayer.this.lambda$notifyError$8(this.c);
        }
    }

    public class b implements ThreadFactory {
        public final Thread newThread(Runnable runnable) {
            return MusicPlayer.lambda$new$0(runnable);
        }
    }

    public class c implements Runnable {
        public final /* synthetic */ String c;

        public c(String str) {
            this.c = str;
        }

        public final void run() {
            MusicPlayer.this.lambda$playFromUrl$1(this.c);
        }
    }

    public class d implements Runnable {
        public final /* synthetic */ String c;
        public final /* synthetic */ String f;

        public d(String str, String str2) {
            this.c = str;
            this.f = str2;
        }

        public final void run() {
            Thread.currentThread().getName();
            MusicPlayer.this.lambda$play$6(this.c, this.f, (l) null);
        }
    }

    public class e implements Runnable {
        public final void run() {
            throw null;
        }
    }

    public class f implements Runnable {
        public final /* synthetic */ String c;

        public f(String str) {
            this.c = str;
        }

        public final void run() {
            MusicPlayer.this.lambda$play$4(this.c);
        }
    }

    public class g implements Runnable {
        public final void run() {
            throw null;
        }
    }

    public class h implements Runnable {
        public final /* synthetic */ Exception c;

        public h(Exception exc) {
            this.c = exc;
        }

        public final void run() {
            new StringBuilder("播放失败: ").append(this.c.getMessage());
            throw null;
        }
    }

    public class i implements Player.Listener {
        public i() {
        }

        public final void onAudioAttributesChanged(AudioAttributes audioAttributes) {
        }

        public final void onAudioSessionIdChanged(int i) {
        }

        public final /* synthetic */ void onAvailableCommandsChanged(Player.Commands commands) {
            u8.c(this, commands);
        }

        public final void onCues(List<Cue> list) {
        }

        public final void onDeviceInfoChanged(DeviceInfo deviceInfo) {
        }

        public final void onDeviceVolumeChanged(int i, boolean z) {
        }

        public final /* synthetic */ void onEvents(Player player, Player.Events events) {
            u8.g(this, player, events);
        }

        public final void onIsLoadingChanged(boolean z) {
        }

        public final void onIsPlayingChanged(boolean z) {
            MusicPlayer.this.e.set(z);
        }

        public final void onLoadingChanged(boolean z) {
        }

        public final void onMediaItemTransition(MediaItem mediaItem, int i) {
        }

        public final void onMediaMetadataChanged(MediaMetadata mediaMetadata) {
        }

        public final void onMetadata(Metadata metadata) {
        }

        public final void onPlayWhenReadyChanged(boolean z, int i) {
            MusicPlayer musicPlayer = MusicPlayer.this;
            if (z) {
                musicPlayer.e.set(true);
            } else {
                musicPlayer.e.set(false);
            }
        }

        public final void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
        }

        public final void onPlaybackStateChanged(int i) {
            if (!(i == 1 || i == 2 || i == 3 || i == 4)) {
                StringBuilder sb = new StringBuilder("UNKNOWN(");
                sb.append(i);
                sb.append(")");
            }
            MusicPlayer musicPlayer = MusicPlayer.this;
            musicPlayer.f.get();
            musicPlayer.e.get();
            if (i == 3 && musicPlayer.f.get()) {
                musicPlayer.f.set(false);
                musicPlayer.e.set(true);
                musicPlayer.i = System.currentTimeMillis();
                try {
                    SimpleExoPlayer simpleExoPlayer = musicPlayer.c;
                    if (simpleExoPlayer != null) {
                        Object invoke = simpleExoPlayer.getClass().getMethod("getDuration", new Class[0]).invoke(musicPlayer.c, new Object[0]);
                        if (invoke instanceof Long) {
                            musicPlayer.k = ((Long) invoke).longValue();
                        }
                    }
                } catch (Exception e) {
                    e.getMessage();
                }
                k kVar = musicPlayer.a;
                if (kVar != null) {
                    String str = musicPlayer.g;
                    String str2 = musicPlayer.h;
                    kVar.getClass();
                }
            }
            if (i == 4) {
                musicPlayer.e.set(false);
                musicPlayer.i = 0;
                musicPlayer.j = 0;
                k kVar2 = musicPlayer.a;
                if (kVar2 != null) {
                    x xVar = x.this;
                    if (xVar.p) {
                        new Handler(Looper.getMainLooper()).post(new qb(10, xVar));
                    }
                }
            }
        }

        public final void onPlaybackSuppressionReasonChanged(int i) {
        }

        public final void onPlayerError(ExoPlaybackException exoPlaybackException) {
            exoPlaybackException.getMessage();
            if (exoPlaybackException.getCause() != null) {
                exoPlaybackException.getCause().getMessage();
                exoPlaybackException.getCause();
            }
            exoPlaybackException.toString();
            MusicPlayer musicPlayer = MusicPlayer.this;
            musicPlayer.e.set(false);
            musicPlayer.f.set(false);
            m mVar = musicPlayer.d;
            if (mVar != null) {
                ((g8) mVar).b();
            }
            musicPlayer.notifyError("播放错误: " + exoPlaybackException.getMessage());
        }

        public final void onPlayerStateChanged(boolean z, int i) {
        }

        public final void onPositionDiscontinuity(int i) {
        }

        public final /* synthetic */ void onPositionDiscontinuity(Player.PositionInfo positionInfo, Player.PositionInfo positionInfo2, int i) {
            u8.u(this, positionInfo, positionInfo2, i);
        }

        public final void onRenderedFirstFrame() {
        }

        public final void onRepeatModeChanged(int i) {
        }

        public final void onSeekProcessed() {
        }

        public final void onShuffleModeEnabledChanged(boolean z) {
        }

        public final void onSkipSilenceEnabledChanged(boolean z) {
        }

        public final void onStaticMetadataChanged(List<Metadata> list) {
        }

        public final void onSurfaceSizeChanged(int i, int i2) {
        }

        public final void onTimelineChanged(Timeline timeline, int i) {
        }

        public final void onTimelineChanged(Timeline timeline, Object obj, int i) {
        }

        public final void onTracksChanged(TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {
        }

        public final void onVideoSizeChanged(int i, int i2, int i3, float f) {
        }

        public final void onVideoSizeChanged(VideoSize videoSize) {
        }

        public final void onVolumeChanged(float f) {
        }
    }

    public class j implements Runnable {
        public j() {
        }

        public final void run() {
            MusicPlayer.this.lambda$stop$7();
        }
    }

    public interface k {
    }

    public interface l {
    }

    public interface m {
    }

    public MusicPlayer(Context context) {
        this.b = context;
    }

    public static Thread lambda$new$0(Runnable runnable) {
        Thread thread = new Thread(runnable, "MusicPlayer");
        thread.setPriority(5);
        return thread;
    }

    public final String a(String str, String str2) {
        StringBuilder m2 = y2.m(str);
        if (str2 != null && !str2.isEmpty()) {
            m2.append(" ");
            m2.append(str2);
        }
        String sb = m2.toString();
        ZingMp3Service zingMp3Service = this.o;
        List<ZingMp3Service.ZingMp3Song> searchSongs = zingMp3Service.searchSongs(sb);
        if (searchSongs != null) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(searchSongs.size());
            sb2.append(" results");
        }
        if (searchSongs == null || searchSongs.isEmpty()) {
            return null;
        }
        String streamLink = zingMp3Service.getStreamLink(searchSongs.get(0).id);
        if (streamLink != null && !streamLink.isEmpty()) {
            StringBuilder sb3 = new StringBuilder("URL (");
            sb3.append(streamLink.length());
            sb3.append(" chars)");
        }
        return streamLink;
    }

    public final void b(String str) {
        boolean z;
        if (Looper.myLooper() == Looper.getMainLooper()) {
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            Thread.currentThread().getName();
        }
        Context context = this.b;
        AtomicBoolean atomicBoolean = this.f;
        if (context == null) {
            atomicBoolean.set(false);
            notifyError("播放器上下文不可用");
        } else if (str == null || str.isEmpty()) {
            atomicBoolean.set(false);
            notifyError("流媒体URL无效");
        } else {
            try {
                atomicBoolean.set(true);
                m mVar = this.d;
                if (mVar != null) {
                    ((g8) mVar).a();
                }
                SimpleExoPlayer simpleExoPlayer = this.c;
                if (simpleExoPlayer != null) {
                    try {
                        simpleExoPlayer.release();
                    } catch (Exception e2) {
                        e2.getMessage();
                    }
                    this.c = null;
                }
                context.getClass();
                SimpleExoPlayer build = new SimpleExoPlayer.Builder(context).build();
                this.c = build;
                if (build == null) {
                    atomicBoolean.set(false);
                    notifyError("无法创建播放器");
                    return;
                }
                OkHttpClient okHttpClient = this.n;
                if (okHttpClient == null) {
                    atomicBoolean.set(false);
                    notifyError("HTTP客户端不可用");
                    return;
                }
                OkHttpDataSource.Factory userAgent = new OkHttpDataSource.Factory(okHttpClient).setUserAgent("Mozilla/5.0 (Linux; Android 5.1.1) AppleWebKit/537.36");
                Uri parse = Uri.parse(str);
                if (parse == null) {
                    atomicBoolean.set(false);
                    notifyError("无效的流媒体URL");
                    return;
                }
                ProgressiveMediaSource createMediaSource = new ProgressiveMediaSource.Factory(userAgent).createMediaSource(MediaItem.fromUri(parse));
                parse.toString();
                this.c.addListener((Player.Listener) new i());
                this.c.setMediaSource(createMediaSource);
                this.c.prepare();
                this.c.setPlayWhenReady(true);
            } catch (Exception e3) {
                e3.getMessage();
                throw e3;
            } catch (Exception e4) {
                e4.getMessage();
                throw e4;
            } catch (Exception e5) {
                e5.getMessage();
                throw e5;
            } catch (Exception e6) {
                e6.getMessage();
                atomicBoolean.set(false);
                m mVar2 = this.d;
                if (mVar2 != null) {
                    ((g8) mVar2).b();
                }
                notifyError("无法播放: " + e6.getMessage());
            }
        }
    }

    public final void c() {
        AtomicBoolean atomicBoolean = this.e;
        atomicBoolean.get();
        AtomicBoolean atomicBoolean2 = this.f;
        atomicBoolean2.get();
        atomicBoolean.set(false);
        atomicBoolean2.set(false);
        SimpleExoPlayer simpleExoPlayer = this.c;
        if (simpleExoPlayer != null) {
            try {
                simpleExoPlayer.stop();
                this.c.release();
            } catch (Exception e2) {
                e2.getMessage();
            }
            this.c = null;
        }
    }

    public String getCurrentArtist() {
        return this.h;
    }

    public long getCurrentPosition() {
        SimpleExoPlayer simpleExoPlayer = this.c;
        if (simpleExoPlayer != null) {
            try {
                Object invoke = simpleExoPlayer.getClass().getMethod("getCurrentPosition", new Class[0]).invoke(this.c, new Object[0]);
                if (invoke instanceof Long) {
                    long longValue = ((Long) invoke).longValue();
                    if (longValue > 0) {
                        return longValue;
                    }
                }
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
        if (!this.e.get() || this.i <= 0) {
            return this.j;
        }
        return this.j + (System.currentTimeMillis() - this.i);
    }

    public String getCurrentSongName() {
        return this.g;
    }

    public long getDuration() {
        SimpleExoPlayer simpleExoPlayer = this.c;
        if (simpleExoPlayer != null) {
            try {
                Object invoke = simpleExoPlayer.getClass().getMethod("getDuration", new Class[0]).invoke(this.c, new Object[0]);
                if (invoke instanceof Long) {
                    long longValue = ((Long) invoke).longValue();
                    if (longValue > 0) {
                        return longValue;
                    }
                }
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
        return this.k;
    }

    public boolean isCurrentlyPlaying() {
        return this.e.get();
    }

    public boolean isPlaying() {
        return this.e.get();
    }

    public void lambda$notifyError$8(String str) {
        k kVar = this.a;
        if (kVar != null) {
            kVar.getClass();
        }
    }

    public void lambda$play$4(String str) {
        Thread.currentThread().getName();
        Looper.myLooper();
        Looper.getMainLooper();
        c();
        b(str);
    }

    public void lambda$play$6(String str, String str2, l lVar) {
        Handler handler = this.m;
        Thread.currentThread().getName();
        Looper.myLooper();
        Looper.getMainLooper();
        try {
            String a2 = a(str, str2);
            if (a2 == null || a2.isEmpty()) {
                notifyError("没有找到歌曲 '" + str + "'");
                if (lVar != null) {
                    handler.post(new g());
                    return;
                }
                return;
            }
            if (lVar != null) {
                handler.post(new e());
            }
            handler.post(new f(a2));
        } catch (Exception e2) {
            e2.getMessage();
            notifyError("播放失败: " + e2.getMessage());
            if (lVar != null) {
                handler.post(new h(e2));
            }
        }
    }

    public void lambda$playFromUrl$1(String str) {
        boolean z;
        if (Looper.myLooper() == Looper.getMainLooper()) {
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            Thread.currentThread().getName();
        }
        c();
        b(str);
    }

    public void lambda$stop$7() {
        c();
    }

    public void notifyError(String str) {
        this.m.post(new a(str));
    }

    public void pause() {
        SimpleExoPlayer simpleExoPlayer = this.c;
        if (simpleExoPlayer != null) {
            try {
                simpleExoPlayer.setPlayWhenReady(false);
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
    }

    public void play(String str, String str2, l lVar) {
        String str3;
        Thread.currentThread().getName();
        this.g = str;
        if (str2 != null) {
            str3 = str2;
        } else {
            str3 = "";
        }
        this.h = str3;
        this.l.execute(new d(str, str2));
    }

    public void playFromUrl(String str, String str2, String str3) {
        Thread.currentThread().getName();
        Looper.myLooper();
        Looper.getMainLooper();
        if (str != null && !str.isEmpty()) {
            this.g = str2;
            if (str3 == null) {
                str3 = "";
            }
            this.h = str3;
            this.m.post(new c(str));
        }
    }

    public void release() {
        stop();
        this.l.shutdown();
    }

    public void resume() {
        SimpleExoPlayer simpleExoPlayer = this.c;
        if (simpleExoPlayer != null) {
            try {
                simpleExoPlayer.setPlayWhenReady(true);
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
    }

    public String searchMusic(String str, String str2) {
        String a2 = a(str, str2);
        if (a2 != null && !a2.isEmpty()) {
            StringBuilder sb = new StringBuilder("Found URL (");
            sb.append(a2.length());
            sb.append(" chars)");
        }
        return a2;
    }

    public void seekTo(long j2) {
        SimpleExoPlayer simpleExoPlayer = this.c;
        if (simpleExoPlayer != null) {
            try {
                simpleExoPlayer.getClass().getMethod("seekTo", new Class[]{Long.TYPE}).invoke(this.c, new Object[]{Long.valueOf(j2)});
                this.j = j2;
            } catch (Exception unused) {
            }
        }
    }

    public void setCallback(k kVar) {
        this.a = kVar;
    }

    public void setStateController(m mVar) {
        this.d = mVar;
    }

    public void stop() {
        this.e.get();
        this.m.post(new j());
    }
}
