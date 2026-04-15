package com.google.android.exoplayer2.ui;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.ControlDispatcher;
import com.google.android.exoplayer2.DefaultControlDispatcher;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerLibraryInfo;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.PlaybackPreparer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.TimeBar;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.RepeatModeUtil;
import com.google.android.exoplayer2.util.Util;
import info.dourok.voicebot.R;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;

public class PlayerControlView extends FrameLayout {
    public static final /* synthetic */ int bg = 0;
    public final Drawable aa;
    public final String ab;
    public final String ac;
    public final String ad;
    public final Drawable ae;
    public final Drawable af;
    public final float ag;
    public final float ah;
    public final String ai;
    public final String aj;
    @Nullable
    public Player ak;
    public ControlDispatcher al;
    @Nullable
    public ProgressUpdateListener am;
    @Nullable
    public PlaybackPreparer an;
    public boolean ao;
    public boolean ap;
    public boolean aq;
    public boolean ar;
    public int as;
    public int at;
    public int au;
    public boolean av;
    public boolean aw;
    public boolean ax;
    public boolean ay;
    public boolean az;
    public long ba;
    public long[] bb;
    public boolean[] bc;
    public long[] bd;
    public boolean[] be;
    public long bf;
    public final a c;
    public final CopyOnWriteArrayList<VisibilityListener> f;
    @Nullable
    public final View g;
    @Nullable
    public final View h;
    @Nullable
    public final View i;
    @Nullable
    public final View j;
    @Nullable
    public final View k;
    @Nullable
    public final View l;
    @Nullable
    public final ImageView m;
    @Nullable
    public final ImageView n;
    @Nullable
    public final View o;
    @Nullable
    public final TextView p;
    @Nullable
    public final TextView q;
    @Nullable
    public final TimeBar r;
    public final StringBuilder s;
    public final Formatter t;
    public final Timeline.Period u;
    public final Timeline.Window v;
    public final v8 w;
    public final v8 x;
    public final Drawable y;
    public final Drawable z;

    public interface ProgressUpdateListener {
        void onProgressUpdate(long j, long j2);
    }

    public interface VisibilityListener {
        void onVisibilityChange(int i);
    }

    public final class a implements Player.EventListener, TimeBar.OnScrubListener, View.OnClickListener {
        public a() {
        }

        public /* bridge */ /* synthetic */ void onAvailableCommandsChanged(Player.Commands commands) {
            t8.a(this, commands);
        }

        public void onClick(View view) {
            PlayerControlView playerControlView = PlayerControlView.this;
            Player player = playerControlView.ak;
            if (player != null) {
                if (playerControlView.h == view) {
                    playerControlView.al.dispatchNext(player);
                } else if (playerControlView.g == view) {
                    playerControlView.al.dispatchPrevious(player);
                } else if (playerControlView.k == view) {
                    if (player.getPlaybackState() != 4) {
                        playerControlView.al.dispatchFastForward(player);
                    }
                } else if (playerControlView.l == view) {
                    playerControlView.al.dispatchRewind(player);
                } else if (playerControlView.i == view) {
                    playerControlView.a(player);
                } else if (playerControlView.j == view) {
                    playerControlView.al.dispatchSetPlayWhenReady(player, false);
                } else if (playerControlView.m == view) {
                    playerControlView.al.dispatchSetRepeatMode(player, RepeatModeUtil.getNextRepeatMode(player.getRepeatMode(), playerControlView.au));
                } else if (playerControlView.n == view) {
                    playerControlView.al.dispatchSetShuffleModeEnabled(player, !player.getShuffleModeEnabled());
                }
            }
        }

        public void onEvents(Player player, Player.Events events) {
            boolean containsAny = events.containsAny(5, 6);
            PlayerControlView playerControlView = PlayerControlView.this;
            if (containsAny) {
                int i = PlayerControlView.bg;
                playerControlView.f();
            }
            if (events.containsAny(5, 6, 8)) {
                int i2 = PlayerControlView.bg;
                playerControlView.g();
            }
            if (events.contains(9)) {
                int i3 = PlayerControlView.bg;
                playerControlView.h();
            }
            if (events.contains(10)) {
                int i4 = PlayerControlView.bg;
                playerControlView.i();
            }
            if (events.containsAny(9, 10, 12, 0)) {
                int i5 = PlayerControlView.bg;
                playerControlView.e();
            }
            if (events.containsAny(12, 0)) {
                int i6 = PlayerControlView.bg;
                playerControlView.j();
            }
        }

        public /* bridge */ /* synthetic */ void onIsLoadingChanged(boolean z) {
            t8.c(this, z);
        }

        public /* bridge */ /* synthetic */ void onIsPlayingChanged(boolean z) {
            t8.d(this, z);
        }

        @Deprecated
        public /* bridge */ /* synthetic */ void onLoadingChanged(boolean z) {
            t8.e(this, z);
        }

        public /* bridge */ /* synthetic */ void onMediaItemTransition(@Nullable MediaItem mediaItem, int i) {
            t8.f(this, mediaItem, i);
        }

        public /* bridge */ /* synthetic */ void onMediaMetadataChanged(MediaMetadata mediaMetadata) {
            t8.g(this, mediaMetadata);
        }

        public /* bridge */ /* synthetic */ void onPlayWhenReadyChanged(boolean z, int i) {
            t8.h(this, z, i);
        }

        public /* bridge */ /* synthetic */ void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
            t8.i(this, playbackParameters);
        }

        public /* bridge */ /* synthetic */ void onPlaybackStateChanged(int i) {
            t8.j(this, i);
        }

        public /* bridge */ /* synthetic */ void onPlaybackSuppressionReasonChanged(int i) {
            t8.k(this, i);
        }

        public /* bridge */ /* synthetic */ void onPlayerError(ExoPlaybackException exoPlaybackException) {
            t8.l(this, exoPlaybackException);
        }

        @Deprecated
        public /* bridge */ /* synthetic */ void onPlayerStateChanged(boolean z, int i) {
            t8.m(this, z, i);
        }

        @Deprecated
        public /* bridge */ /* synthetic */ void onPositionDiscontinuity(int i) {
            t8.n(this, i);
        }

        public /* bridge */ /* synthetic */ void onPositionDiscontinuity(Player.PositionInfo positionInfo, Player.PositionInfo positionInfo2, int i) {
            t8.o(this, positionInfo, positionInfo2, i);
        }

        public /* bridge */ /* synthetic */ void onRepeatModeChanged(int i) {
            t8.p(this, i);
        }

        public void onScrubMove(TimeBar timeBar, long j) {
            PlayerControlView playerControlView = PlayerControlView.this;
            TextView textView = playerControlView.q;
            if (textView != null) {
                textView.setText(Util.getStringForTime(playerControlView.s, playerControlView.t, j));
            }
        }

        public void onScrubStart(TimeBar timeBar, long j) {
            PlayerControlView playerControlView = PlayerControlView.this;
            playerControlView.ar = true;
            TextView textView = playerControlView.q;
            if (textView != null) {
                textView.setText(Util.getStringForTime(playerControlView.s, playerControlView.t, j));
            }
        }

        public void onScrubStop(TimeBar timeBar, long j, boolean z) {
            Player player;
            PlayerControlView playerControlView = PlayerControlView.this;
            int i = 0;
            playerControlView.ar = false;
            if (!z && (player = playerControlView.ak) != null) {
                Timeline currentTimeline = player.getCurrentTimeline();
                if (playerControlView.aq && !currentTimeline.isEmpty()) {
                    int windowCount = currentTimeline.getWindowCount();
                    while (true) {
                        long durationMs = currentTimeline.getWindow(i, playerControlView.v).getDurationMs();
                        if (j < durationMs) {
                            break;
                        } else if (i == windowCount - 1) {
                            j = durationMs;
                            break;
                        } else {
                            j -= durationMs;
                            i++;
                        }
                    }
                } else {
                    i = player.getCurrentWindowIndex();
                }
                playerControlView.al.dispatchSeekTo(player, i, j);
                playerControlView.g();
            }
        }

        @Deprecated
        public /* bridge */ /* synthetic */ void onSeekProcessed() {
            t8.q(this);
        }

        public /* bridge */ /* synthetic */ void onShuffleModeEnabledChanged(boolean z) {
            t8.r(this, z);
        }

        public /* bridge */ /* synthetic */ void onStaticMetadataChanged(List list) {
            t8.s(this, list);
        }

        public /* bridge */ /* synthetic */ void onTimelineChanged(Timeline timeline, int i) {
            t8.t(this, timeline, i);
        }

        @Deprecated
        public /* bridge */ /* synthetic */ void onTimelineChanged(Timeline timeline, @Nullable Object obj, int i) {
            t8.u(this, timeline, obj, i);
        }

        public /* bridge */ /* synthetic */ void onTracksChanged(TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {
            t8.v(this, trackGroupArray, trackSelectionArray);
        }
    }

    static {
        ExoPlayerLibraryInfo.registerModule("goog.exo.ui");
    }

    public PlayerControlView(Context context) {
        this(context, (AttributeSet) null);
    }

    public final void a(Player player) {
        int playbackState = player.getPlaybackState();
        if (playbackState == 1) {
            PlaybackPreparer playbackPreparer = this.an;
            if (playbackPreparer != null) {
                playbackPreparer.preparePlayback();
            } else {
                this.al.dispatchPrepare(player);
            }
        } else if (playbackState == 4) {
            this.al.dispatchSeekTo(player, player.getCurrentWindowIndex(), -9223372036854775807L);
        }
        this.al.dispatchSetPlayWhenReady(player, true);
    }

    public void addVisibilityListener(VisibilityListener visibilityListener) {
        Assertions.checkNotNull(visibilityListener);
        this.f.add(visibilityListener);
    }

    public final void b() {
        v8 v8Var = this.x;
        removeCallbacks(v8Var);
        if (this.as > 0) {
            long uptimeMillis = SystemClock.uptimeMillis();
            long j2 = (long) this.as;
            this.ba = uptimeMillis + j2;
            if (this.ao) {
                postDelayed(v8Var, j2);
                return;
            }
            return;
        }
        this.ba = -9223372036854775807L;
    }

    public final boolean c() {
        Player player = this.ak;
        if (player == null || player.getPlaybackState() == 4 || this.ak.getPlaybackState() == 1 || !this.ak.getPlayWhenReady()) {
            return false;
        }
        return true;
    }

    public final void d(@Nullable View view, boolean z2, boolean z3) {
        float f2;
        int i2;
        if (view != null) {
            view.setEnabled(z3);
            if (z3) {
                f2 = this.ag;
            } else {
                f2 = this.ah;
            }
            view.setAlpha(f2);
            if (z2) {
                i2 = 0;
            } else {
                i2 = 8;
            }
            view.setVisibility(i2);
        }
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return dispatchMediaKeyEvent(keyEvent) || super.dispatchKeyEvent(keyEvent);
    }

    public boolean dispatchMediaKeyEvent(KeyEvent keyEvent) {
        boolean z2;
        int keyCode = keyEvent.getKeyCode();
        Player player = this.ak;
        if (player != null) {
            if (keyCode == 90 || keyCode == 89 || keyCode == 85 || keyCode == 79 || keyCode == 126 || keyCode == 127 || keyCode == 87 || keyCode == 88) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (z2) {
                if (keyEvent.getAction() == 0) {
                    if (keyCode == 90) {
                        if (player.getPlaybackState() != 4) {
                            this.al.dispatchFastForward(player);
                        }
                    } else if (keyCode == 89) {
                        this.al.dispatchRewind(player);
                    } else if (keyEvent.getRepeatCount() == 0) {
                        if (keyCode == 79 || keyCode == 85) {
                            int playbackState = player.getPlaybackState();
                            if (playbackState == 1 || playbackState == 4 || !player.getPlayWhenReady()) {
                                a(player);
                            } else {
                                this.al.dispatchSetPlayWhenReady(player, false);
                            }
                        } else if (keyCode == 87) {
                            this.al.dispatchNext(player);
                        } else if (keyCode == 88) {
                            this.al.dispatchPrevious(player);
                        } else if (keyCode == 126) {
                            a(player);
                        } else if (keyCode == 127) {
                            this.al.dispatchSetPlayWhenReady(player, false);
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            removeCallbacks(this.x);
        } else if (motionEvent.getAction() == 1) {
            b();
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    /* JADX WARNING: Removed duplicated region for block: B:39:0x0096  */
    /* JADX WARNING: Removed duplicated region for block: B:42:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void e() {
        /*
            r9 = this;
            boolean r0 = r9.isVisible()
            if (r0 == 0) goto L_0x0099
            boolean r0 = r9.ao
            if (r0 != 0) goto L_0x000c
            goto L_0x0099
        L_0x000c:
            com.google.android.exoplayer2.Player r0 = r9.ak
            r1 = 0
            if (r0 == 0) goto L_0x0072
            com.google.android.exoplayer2.Timeline r2 = r0.getCurrentTimeline()
            boolean r3 = r2.isEmpty()
            if (r3 != 0) goto L_0x0072
            boolean r3 = r0.isPlayingAd()
            if (r3 != 0) goto L_0x0072
            r3 = 4
            boolean r3 = r0.isCommandAvailable(r3)
            int r4 = r0.getCurrentWindowIndex()
            com.google.android.exoplayer2.Timeline$Window r5 = r9.v
            r2.getWindow(r4, r5)
            r2 = 1
            if (r3 != 0) goto L_0x0042
            boolean r4 = r5.isLive()
            if (r4 == 0) goto L_0x0042
            r4 = 6
            boolean r4 = r0.isCommandAvailable(r4)
            if (r4 == 0) goto L_0x0040
            goto L_0x0042
        L_0x0040:
            r4 = 0
            goto L_0x0043
        L_0x0042:
            r4 = 1
        L_0x0043:
            if (r3 == 0) goto L_0x004f
            com.google.android.exoplayer2.ControlDispatcher r6 = r9.al
            boolean r6 = r6.isRewindEnabled()
            if (r6 == 0) goto L_0x004f
            r6 = 1
            goto L_0x0050
        L_0x004f:
            r6 = 0
        L_0x0050:
            if (r3 == 0) goto L_0x005c
            com.google.android.exoplayer2.ControlDispatcher r7 = r9.al
            boolean r7 = r7.isFastForwardEnabled()
            if (r7 == 0) goto L_0x005c
            r7 = 1
            goto L_0x005d
        L_0x005c:
            r7 = 0
        L_0x005d:
            boolean r8 = r5.isLive()
            if (r8 == 0) goto L_0x0067
            boolean r5 = r5.m
            if (r5 != 0) goto L_0x006e
        L_0x0067:
            r5 = 5
            boolean r0 = r0.isCommandAvailable(r5)
            if (r0 == 0) goto L_0x006f
        L_0x006e:
            r1 = 1
        L_0x006f:
            r0 = r1
            r1 = r4
            goto L_0x0076
        L_0x0072:
            r0 = 0
            r3 = 0
            r6 = 0
            r7 = 0
        L_0x0076:
            boolean r2 = r9.ax
            android.view.View r4 = r9.g
            r9.d(r4, r2, r1)
            boolean r1 = r9.av
            android.view.View r2 = r9.l
            r9.d(r2, r1, r6)
            boolean r1 = r9.aw
            android.view.View r2 = r9.k
            r9.d(r2, r1, r7)
            boolean r1 = r9.ay
            android.view.View r2 = r9.h
            r9.d(r2, r1, r0)
            com.google.android.exoplayer2.ui.TimeBar r0 = r9.r
            if (r0 == 0) goto L_0x0099
            r0.setEnabled(r3)
        L_0x0099:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ui.PlayerControlView.e():void");
    }

    public final void f() {
        boolean z2;
        boolean z3;
        int i2;
        if (isVisible() && this.ao) {
            boolean c2 = c();
            View view = this.i;
            boolean z4 = true;
            int i3 = 8;
            if (view != null) {
                if (!c2 || !view.isFocused()) {
                    z3 = false;
                } else {
                    z3 = true;
                }
                z2 = z3 | false;
                if (c2) {
                    i2 = 8;
                } else {
                    i2 = 0;
                }
                view.setVisibility(i2);
            } else {
                z2 = false;
            }
            View view2 = this.j;
            if (view2 != null) {
                if (c2 || !view2.isFocused()) {
                    z4 = false;
                }
                z2 |= z4;
                if (c2) {
                    i3 = 0;
                }
                view2.setVisibility(i3);
            }
            if (z2) {
                boolean c3 = c();
                if (!c3 && view != null) {
                    view.requestFocus();
                } else if (c3 && view2 != null) {
                    view2.requestFocus();
                }
            }
        }
    }

    public final void g() {
        long j2;
        long j3;
        int i2;
        long j4;
        if (isVisible() && this.ao) {
            Player player = this.ak;
            if (player != null) {
                j3 = player.getContentPosition() + this.bf;
                j2 = player.getContentBufferedPosition() + this.bf;
            } else {
                j3 = 0;
                j2 = 0;
            }
            TextView textView = this.q;
            if (textView != null && !this.ar) {
                textView.setText(Util.getStringForTime(this.s, this.t, j3));
            }
            TimeBar timeBar = this.r;
            if (timeBar != null) {
                timeBar.setPosition(j3);
                timeBar.setBufferedPosition(j2);
            }
            ProgressUpdateListener progressUpdateListener = this.am;
            if (progressUpdateListener != null) {
                progressUpdateListener.onProgressUpdate(j3, j2);
            }
            v8 v8Var = this.w;
            removeCallbacks(v8Var);
            if (player == null) {
                i2 = 1;
            } else {
                i2 = player.getPlaybackState();
            }
            long j5 = 1000;
            if (player != null && player.isPlaying()) {
                if (timeBar != null) {
                    j4 = timeBar.getPreferredUpdateDelay();
                } else {
                    j4 = 1000;
                }
                long min = Math.min(j4, 1000 - (j3 % 1000));
                float f2 = player.getPlaybackParameters().c;
                if (f2 > 0.0f) {
                    j5 = (long) (((float) min) / f2);
                }
                postDelayed(v8Var, Util.constrainValue(j5, (long) this.at, 1000));
            } else if (i2 != 4 && i2 != 1) {
                postDelayed(v8Var, 1000);
            }
        }
    }

    @Nullable
    public Player getPlayer() {
        return this.ak;
    }

    public int getRepeatToggleModes() {
        return this.au;
    }

    public boolean getShowShuffleButton() {
        return this.az;
    }

    public int getShowTimeoutMs() {
        return this.as;
    }

    public boolean getShowVrButton() {
        View view = this.o;
        return view != null && view.getVisibility() == 0;
    }

    public final void h() {
        ImageView imageView;
        if (isVisible() && this.ao && (imageView = this.m) != null) {
            if (this.au == 0) {
                d(imageView, false, false);
                return;
            }
            Player player = this.ak;
            String str = this.ab;
            Drawable drawable = this.y;
            if (player == null) {
                d(imageView, true, false);
                imageView.setImageDrawable(drawable);
                imageView.setContentDescription(str);
                return;
            }
            d(imageView, true, true);
            int repeatMode = player.getRepeatMode();
            if (repeatMode == 0) {
                imageView.setImageDrawable(drawable);
                imageView.setContentDescription(str);
            } else if (repeatMode == 1) {
                imageView.setImageDrawable(this.z);
                imageView.setContentDescription(this.ac);
            } else if (repeatMode == 2) {
                imageView.setImageDrawable(this.aa);
                imageView.setContentDescription(this.ad);
            }
            imageView.setVisibility(0);
        }
    }

    public void hide() {
        if (isVisible()) {
            setVisibility(8);
            Iterator<VisibilityListener> it = this.f.iterator();
            while (it.hasNext()) {
                it.next().onVisibilityChange(getVisibility());
            }
            removeCallbacks(this.w);
            removeCallbacks(this.x);
            this.ba = -9223372036854775807L;
        }
    }

    public final void i() {
        ImageView imageView;
        if (isVisible() && this.ao && (imageView = this.n) != null) {
            Player player = this.ak;
            if (!this.az) {
                d(imageView, false, false);
                return;
            }
            String str = this.aj;
            Drawable drawable = this.af;
            if (player == null) {
                d(imageView, true, false);
                imageView.setImageDrawable(drawable);
                imageView.setContentDescription(str);
                return;
            }
            d(imageView, true, true);
            if (player.getShuffleModeEnabled()) {
                drawable = this.ae;
            }
            imageView.setImageDrawable(drawable);
            if (player.getShuffleModeEnabled()) {
                str = this.ai;
            }
            imageView.setContentDescription(str);
        }
    }

    public boolean isVisible() {
        return getVisibility() == 0;
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0039  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x004c  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x0108  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0113  */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x0122  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void j() {
        /*
            r24 = this;
            r0 = r24
            com.google.android.exoplayer2.Player r1 = r0.ak
            if (r1 != 0) goto L_0x0007
            return
        L_0x0007:
            boolean r2 = r0.ap
            r3 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            com.google.android.exoplayer2.Timeline$Window r5 = r0.v
            r7 = 1
            if (r2 == 0) goto L_0x003b
            com.google.android.exoplayer2.Timeline r2 = r1.getCurrentTimeline()
            int r8 = r2.getWindowCount()
            r9 = 100
            if (r8 <= r9) goto L_0x0021
        L_0x001f:
            r2 = 0
            goto L_0x0037
        L_0x0021:
            int r8 = r2.getWindowCount()
            r9 = 0
        L_0x0026:
            if (r9 >= r8) goto L_0x0036
            com.google.android.exoplayer2.Timeline$Window r10 = r2.getWindow(r9, r5)
            long r10 = r10.r
            int r12 = (r10 > r3 ? 1 : (r10 == r3 ? 0 : -1))
            if (r12 != 0) goto L_0x0033
            goto L_0x001f
        L_0x0033:
            int r9 = r9 + 1
            goto L_0x0026
        L_0x0036:
            r2 = 1
        L_0x0037:
            if (r2 == 0) goto L_0x003b
            r2 = 1
            goto L_0x003c
        L_0x003b:
            r2 = 0
        L_0x003c:
            r0.aq = r2
            r8 = 0
            r0.bf = r8
            com.google.android.exoplayer2.Timeline r2 = r1.getCurrentTimeline()
            boolean r10 = r2.isEmpty()
            if (r10 != 0) goto L_0x0108
            int r1 = r1.getCurrentWindowIndex()
            boolean r10 = r0.aq
            if (r10 == 0) goto L_0x0056
            r11 = 0
            goto L_0x0057
        L_0x0056:
            r11 = r1
        L_0x0057:
            if (r10 == 0) goto L_0x005f
            int r10 = r2.getWindowCount()
            int r10 = r10 - r7
            goto L_0x0060
        L_0x005f:
            r10 = r1
        L_0x0060:
            r12 = r8
            r14 = 0
        L_0x0062:
            if (r11 > r10) goto L_0x0106
            if (r11 != r1) goto L_0x006c
            long r8 = com.google.android.exoplayer2.C.usToMs(r12)
            r0.bf = r8
        L_0x006c:
            r2.getWindow(r11, r5)
            long r8 = r5.r
            int r17 = (r8 > r3 ? 1 : (r8 == r3 ? 0 : -1))
            if (r17 != 0) goto L_0x007d
            boolean r1 = r0.aq
            r1 = r1 ^ r7
            com.google.android.exoplayer2.util.Assertions.checkState(r1)
            goto L_0x0106
        L_0x007d:
            int r8 = r5.s
        L_0x007f:
            int r9 = r5.t
            if (r8 > r9) goto L_0x00f5
            com.google.android.exoplayer2.Timeline$Period r9 = r0.u
            r2.getPeriod(r8, r9)
            int r7 = r9.getAdGroupCount()
            r15 = 0
        L_0x008d:
            if (r15 >= r7) goto L_0x00ea
            long r20 = r9.getAdGroupTimeUs(r15)
            r22 = -9223372036854775808
            int r16 = (r20 > r22 ? 1 : (r20 == r22 ? 0 : -1))
            r22 = r7
            if (r16 != 0) goto L_0x00a6
            long r6 = r9.h
            int r20 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1))
            if (r20 != 0) goto L_0x00a4
            r18 = 0
            goto L_0x00e0
        L_0x00a4:
            r20 = r6
        L_0x00a6:
            long r6 = r9.getPositionInWindowUs()
            long r6 = r6 + r20
            r18 = 0
            int r20 = (r6 > r18 ? 1 : (r6 == r18 ? 0 : -1))
            if (r20 < 0) goto L_0x00e0
            long[] r3 = r0.bb
            int r4 = r3.length
            if (r14 != r4) goto L_0x00cd
            int r4 = r3.length
            if (r4 != 0) goto L_0x00bc
            r4 = 1
            goto L_0x00bf
        L_0x00bc:
            int r4 = r3.length
            int r4 = r4 * 2
        L_0x00bf:
            long[] r3 = java.util.Arrays.copyOf(r3, r4)
            r0.bb = r3
            boolean[] r3 = r0.bc
            boolean[] r3 = java.util.Arrays.copyOf(r3, r4)
            r0.bc = r3
        L_0x00cd:
            long[] r3 = r0.bb
            long r6 = r6 + r12
            long r6 = com.google.android.exoplayer2.C.usToMs(r6)
            r3[r14] = r6
            boolean[] r3 = r0.bc
            boolean r4 = r9.hasPlayedAdGroup(r15)
            r3[r14] = r4
            int r14 = r14 + 1
        L_0x00e0:
            int r15 = r15 + 1
            r7 = r22
            r3 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            goto L_0x008d
        L_0x00ea:
            r18 = 0
            int r8 = r8 + 1
            r3 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            r7 = 1
            goto L_0x007f
        L_0x00f5:
            r18 = 0
            long r3 = r5.r
            long r12 = r12 + r3
            int r11 = r11 + 1
            r8 = r18
            r3 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            r7 = 1
            goto L_0x0062
        L_0x0106:
            r8 = r12
            goto L_0x010b
        L_0x0108:
            r18 = r8
            r14 = 0
        L_0x010b:
            long r1 = com.google.android.exoplayer2.C.usToMs(r8)
            android.widget.TextView r3 = r0.p
            if (r3 == 0) goto L_0x011e
            java.lang.StringBuilder r4 = r0.s
            java.util.Formatter r5 = r0.t
            java.lang.String r4 = com.google.android.exoplayer2.util.Util.getStringForTime(r4, r5, r1)
            r3.setText(r4)
        L_0x011e:
            com.google.android.exoplayer2.ui.TimeBar r3 = r0.r
            if (r3 == 0) goto L_0x0153
            r3.setDuration(r1)
            long[] r1 = r0.bd
            int r1 = r1.length
            int r2 = r14 + r1
            long[] r4 = r0.bb
            int r5 = r4.length
            if (r2 <= r5) goto L_0x013d
            long[] r4 = java.util.Arrays.copyOf(r4, r2)
            r0.bb = r4
            boolean[] r4 = r0.bc
            boolean[] r4 = java.util.Arrays.copyOf(r4, r2)
            r0.bc = r4
        L_0x013d:
            long[] r4 = r0.bd
            long[] r5 = r0.bb
            r6 = 0
            java.lang.System.arraycopy(r4, r6, r5, r14, r1)
            boolean[] r4 = r0.be
            boolean[] r5 = r0.bc
            java.lang.System.arraycopy(r4, r6, r5, r14, r1)
            long[] r1 = r0.bb
            boolean[] r4 = r0.bc
            r3.setAdGroupTimesMs(r1, r4, r2)
        L_0x0153:
            r24.g()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ui.PlayerControlView.j():void");
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.ao = true;
        long j2 = this.ba;
        if (j2 != -9223372036854775807L) {
            long uptimeMillis = j2 - SystemClock.uptimeMillis();
            if (uptimeMillis <= 0) {
                hide();
            } else {
                postDelayed(this.x, uptimeMillis);
            }
        } else if (isVisible()) {
            b();
        }
        f();
        e();
        h();
        i();
        j();
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.ao = false;
        removeCallbacks(this.w);
        removeCallbacks(this.x);
    }

    public void removeVisibilityListener(VisibilityListener visibilityListener) {
        this.f.remove(visibilityListener);
    }

    public void setControlDispatcher(ControlDispatcher controlDispatcher) {
        if (this.al != controlDispatcher) {
            this.al = controlDispatcher;
            e();
        }
    }

    public void setExtraAdGroupMarkers(@Nullable long[] jArr, @Nullable boolean[] zArr) {
        boolean z2 = false;
        if (jArr == null) {
            this.bd = new long[0];
            this.be = new boolean[0];
        } else {
            boolean[] zArr2 = (boolean[]) Assertions.checkNotNull(zArr);
            if (jArr.length == zArr2.length) {
                z2 = true;
            }
            Assertions.checkArgument(z2);
            this.bd = jArr;
            this.be = zArr2;
        }
        j();
    }

    @Deprecated
    public void setFastForwardIncrementMs(int i2) {
        ControlDispatcher controlDispatcher = this.al;
        if (controlDispatcher instanceof DefaultControlDispatcher) {
            ((DefaultControlDispatcher) controlDispatcher).setFastForwardIncrementMs((long) i2);
            e();
        }
    }

    @Deprecated
    public void setPlaybackPreparer(@Nullable PlaybackPreparer playbackPreparer) {
        this.an = playbackPreparer;
    }

    public void setPlayer(@Nullable Player player) {
        boolean z2;
        boolean z3 = false;
        if (Looper.myLooper() == Looper.getMainLooper()) {
            z2 = true;
        } else {
            z2 = false;
        }
        Assertions.checkState(z2);
        if (player == null || player.getApplicationLooper() == Looper.getMainLooper()) {
            z3 = true;
        }
        Assertions.checkArgument(z3);
        Player player2 = this.ak;
        if (player2 != player) {
            a aVar = this.c;
            if (player2 != null) {
                player2.removeListener((Player.EventListener) aVar);
            }
            this.ak = player;
            if (player != null) {
                player.addListener((Player.EventListener) aVar);
            }
            f();
            e();
            h();
            i();
            j();
        }
    }

    public void setProgressUpdateListener(@Nullable ProgressUpdateListener progressUpdateListener) {
        this.am = progressUpdateListener;
    }

    public void setRepeatToggleModes(int i2) {
        this.au = i2;
        Player player = this.ak;
        if (player != null) {
            int repeatMode = player.getRepeatMode();
            if (i2 == 0 && repeatMode != 0) {
                this.al.dispatchSetRepeatMode(this.ak, 0);
            } else if (i2 == 1 && repeatMode == 2) {
                this.al.dispatchSetRepeatMode(this.ak, 1);
            } else if (i2 == 2 && repeatMode == 1) {
                this.al.dispatchSetRepeatMode(this.ak, 2);
            }
        }
        h();
    }

    @Deprecated
    public void setRewindIncrementMs(int i2) {
        ControlDispatcher controlDispatcher = this.al;
        if (controlDispatcher instanceof DefaultControlDispatcher) {
            ((DefaultControlDispatcher) controlDispatcher).setRewindIncrementMs((long) i2);
            e();
        }
    }

    public void setShowFastForwardButton(boolean z2) {
        this.aw = z2;
        e();
    }

    public void setShowMultiWindowTimeBar(boolean z2) {
        this.ap = z2;
        j();
    }

    public void setShowNextButton(boolean z2) {
        this.ay = z2;
        e();
    }

    public void setShowPreviousButton(boolean z2) {
        this.ax = z2;
        e();
    }

    public void setShowRewindButton(boolean z2) {
        this.av = z2;
        e();
    }

    public void setShowShuffleButton(boolean z2) {
        this.az = z2;
        i();
    }

    public void setShowTimeoutMs(int i2) {
        this.as = i2;
        if (isVisible()) {
            b();
        }
    }

    public void setShowVrButton(boolean z2) {
        int i2;
        View view = this.o;
        if (view != null) {
            if (z2) {
                i2 = 0;
            } else {
                i2 = 8;
            }
            view.setVisibility(i2);
        }
    }

    public void setTimeBarMinUpdateInterval(int i2) {
        this.at = Util.constrainValue(i2, 16, 1000);
    }

    public void setVrButtonListener(@Nullable View.OnClickListener onClickListener) {
        boolean z2;
        View view = this.o;
        if (view != null) {
            view.setOnClickListener(onClickListener);
            boolean showVrButton = getShowVrButton();
            if (onClickListener != null) {
                z2 = true;
            } else {
                z2 = false;
            }
            d(view, showVrButton, z2);
        }
    }

    public void show() {
        View view;
        View view2;
        if (!isVisible()) {
            setVisibility(0);
            Iterator<VisibilityListener> it = this.f.iterator();
            while (it.hasNext()) {
                it.next().onVisibilityChange(getVisibility());
            }
            f();
            e();
            h();
            i();
            j();
            boolean c2 = c();
            if (!c2 && (view2 = this.i) != null) {
                view2.requestFocus();
            } else if (c2 && (view = this.j) != null) {
                view.requestFocus();
            }
        }
        b();
    }

    public PlayerControlView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PlayerControlView(Context context, @Nullable AttributeSet attributeSet, int i2) {
        this(context, attributeSet, i2, attributeSet);
    }

    public PlayerControlView(Context context, @Nullable AttributeSet attributeSet, int i2, @Nullable AttributeSet attributeSet2) {
        super(context, attributeSet, i2);
        int i3 = 5000;
        this.as = 5000;
        this.au = 0;
        this.at = 200;
        this.ba = -9223372036854775807L;
        this.av = true;
        this.aw = true;
        this.ax = true;
        this.ay = true;
        this.az = false;
        int i4 = R.layout.exo_player_control_view;
        int i5 = 15000;
        if (attributeSet2 != null) {
            TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet2, R.styleable.c, 0, 0);
            try {
                i3 = obtainStyledAttributes.getInt(10, 5000);
                i5 = obtainStyledAttributes.getInt(6, 15000);
                this.as = obtainStyledAttributes.getInt(21, this.as);
                i4 = obtainStyledAttributes.getResourceId(5, R.layout.exo_player_control_view);
                this.au = obtainStyledAttributes.getInt(9, this.au);
                this.av = obtainStyledAttributes.getBoolean(19, this.av);
                this.aw = obtainStyledAttributes.getBoolean(16, this.aw);
                this.ax = obtainStyledAttributes.getBoolean(18, this.ax);
                this.ay = obtainStyledAttributes.getBoolean(17, this.ay);
                this.az = obtainStyledAttributes.getBoolean(20, this.az);
                setTimeBarMinUpdateInterval(obtainStyledAttributes.getInt(22, this.at));
            } finally {
                obtainStyledAttributes.recycle();
            }
        }
        this.f = new CopyOnWriteArrayList<>();
        this.u = new Timeline.Period();
        this.v = new Timeline.Window();
        StringBuilder sb = new StringBuilder();
        this.s = sb;
        this.t = new Formatter(sb, Locale.getDefault());
        this.bb = new long[0];
        this.bc = new boolean[0];
        this.bd = new long[0];
        this.be = new boolean[0];
        a aVar = new a();
        this.c = aVar;
        this.al = new DefaultControlDispatcher((long) i5, (long) i3);
        this.w = new v8(this, 0);
        this.x = new v8(this, 1);
        LayoutInflater.from(context).inflate(i4, this);
        setDescendantFocusability(262144);
        TimeBar timeBar = (TimeBar) findViewById(R.id.exo_progress);
        View findViewById = findViewById(R.id.exo_progress_placeholder);
        if (timeBar != null) {
            this.r = timeBar;
        } else if (findViewById != null) {
            DefaultTimeBar defaultTimeBar = new DefaultTimeBar(context, (AttributeSet) null, 0, attributeSet2);
            defaultTimeBar.setId(R.id.exo_progress);
            defaultTimeBar.setLayoutParams(findViewById.getLayoutParams());
            ViewGroup viewGroup = (ViewGroup) findViewById.getParent();
            int indexOfChild = viewGroup.indexOfChild(findViewById);
            viewGroup.removeView(findViewById);
            viewGroup.addView(defaultTimeBar, indexOfChild);
            this.r = defaultTimeBar;
        } else {
            this.r = null;
        }
        this.p = (TextView) findViewById(R.id.exo_duration);
        this.q = (TextView) findViewById(R.id.exo_position);
        TimeBar timeBar2 = this.r;
        if (timeBar2 != null) {
            timeBar2.addListener(aVar);
        }
        View findViewById2 = findViewById(R.id.exo_play);
        this.i = findViewById2;
        if (findViewById2 != null) {
            findViewById2.setOnClickListener(aVar);
        }
        View findViewById3 = findViewById(R.id.exo_pause);
        this.j = findViewById3;
        if (findViewById3 != null) {
            findViewById3.setOnClickListener(aVar);
        }
        View findViewById4 = findViewById(R.id.exo_prev);
        this.g = findViewById4;
        if (findViewById4 != null) {
            findViewById4.setOnClickListener(aVar);
        }
        View findViewById5 = findViewById(R.id.exo_next);
        this.h = findViewById5;
        if (findViewById5 != null) {
            findViewById5.setOnClickListener(aVar);
        }
        View findViewById6 = findViewById(R.id.exo_rew);
        this.l = findViewById6;
        if (findViewById6 != null) {
            findViewById6.setOnClickListener(aVar);
        }
        View findViewById7 = findViewById(R.id.exo_ffwd);
        this.k = findViewById7;
        if (findViewById7 != null) {
            findViewById7.setOnClickListener(aVar);
        }
        ImageView imageView = (ImageView) findViewById(R.id.exo_repeat_toggle);
        this.m = imageView;
        if (imageView != null) {
            imageView.setOnClickListener(aVar);
        }
        ImageView imageView2 = (ImageView) findViewById(R.id.exo_shuffle);
        this.n = imageView2;
        if (imageView2 != null) {
            imageView2.setOnClickListener(aVar);
        }
        View findViewById8 = findViewById(R.id.exo_vr);
        this.o = findViewById8;
        setShowVrButton(false);
        d(findViewById8, false, false);
        Resources resources = context.getResources();
        this.ag = ((float) resources.getInteger(R.integer.exo_media_button_opacity_percentage_enabled)) / 100.0f;
        this.ah = ((float) resources.getInteger(R.integer.exo_media_button_opacity_percentage_disabled)) / 100.0f;
        this.y = resources.getDrawable(R.drawable.exo_controls_repeat_off);
        this.z = resources.getDrawable(R.drawable.exo_controls_repeat_one);
        this.aa = resources.getDrawable(R.drawable.exo_controls_repeat_all);
        this.ae = resources.getDrawable(R.drawable.exo_controls_shuffle_on);
        this.af = resources.getDrawable(R.drawable.exo_controls_shuffle_off);
        this.ab = resources.getString(R.string.exo_controls_repeat_off_description);
        this.ac = resources.getString(R.string.exo_controls_repeat_one_description);
        this.ad = resources.getString(R.string.exo_controls_repeat_all_description);
        this.ai = resources.getString(R.string.exo_controls_shuffle_on_description);
        this.aj = resources.getString(R.string.exo_controls_shuffle_off_description);
    }
}
