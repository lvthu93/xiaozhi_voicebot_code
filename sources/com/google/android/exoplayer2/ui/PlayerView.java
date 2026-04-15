package com.google.android.exoplayer2.ui;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.opengl.GLSurfaceView;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.google.android.exoplayer2.ControlDispatcher;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.PlaybackPreparer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.device.DeviceInfo;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.flac.PictureFrame;
import com.google.android.exoplayer2.metadata.id3.ApicFrame;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionUtil;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ErrorMessageProvider;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoDecoderGLSurfaceView;
import com.google.android.exoplayer2.video.VideoSize;
import com.google.android.exoplayer2.video.spherical.SphericalGLSurfaceView;
import com.google.common.collect.ImmutableList;
import info.dourok.voicebot.R;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import org.checkerframework.checker.nullness.qual.EnsuresNonNullIf;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

public class PlayerView extends FrameLayout implements AdViewProvider {
    public static final /* synthetic */ int af = 0;
    public boolean aa;
    public boolean ab;
    public boolean ac;
    public int ad;
    public boolean ae;
    public final a c;
    @Nullable
    public final AspectRatioFrameLayout f;
    @Nullable
    public final View g;
    @Nullable
    public final View h;
    public final boolean i;
    @Nullable
    public final ImageView j;
    @Nullable
    public final SubtitleView k;
    @Nullable
    public final View l;
    @Nullable
    public final TextView m;
    @Nullable
    public final PlayerControlView n;
    @Nullable
    public final FrameLayout o;
    @Nullable
    public final FrameLayout p;
    @Nullable
    public Player q;
    public boolean r;
    @Nullable
    public PlayerControlView.VisibilityListener s;
    public boolean t;
    @Nullable
    public Drawable u;
    public int v;
    public boolean w;
    @Nullable
    public ErrorMessageProvider<? super ExoPlaybackException> x;
    @Nullable
    public CharSequence y;
    public int z;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface ShowBuffering {
    }

    public final class a implements Player.Listener, View.OnLayoutChangeListener, View.OnClickListener, PlayerControlView.VisibilityListener {
        public final Timeline.Period c = new Timeline.Period();
        @Nullable
        public Object f;

        public a() {
        }

        public /* bridge */ /* synthetic */ void onAudioAttributesChanged(AudioAttributes audioAttributes) {
            u8.a(this, audioAttributes);
        }

        public /* bridge */ /* synthetic */ void onAudioSessionIdChanged(int i) {
            u8.b(this, i);
        }

        public /* bridge */ /* synthetic */ void onAvailableCommandsChanged(Player.Commands commands) {
            u8.c(this, commands);
        }

        public void onClick(View view) {
            int i = PlayerView.af;
            PlayerView.this.f();
        }

        public void onCues(List<Cue> list) {
            SubtitleView subtitleView = PlayerView.this.k;
            if (subtitleView != null) {
                subtitleView.onCues(list);
            }
        }

        public /* bridge */ /* synthetic */ void onDeviceInfoChanged(DeviceInfo deviceInfo) {
            u8.e(this, deviceInfo);
        }

        public /* bridge */ /* synthetic */ void onDeviceVolumeChanged(int i, boolean z) {
            u8.f(this, i, z);
        }

        public /* bridge */ /* synthetic */ void onEvents(Player player, Player.Events events) {
            u8.g(this, player, events);
        }

        public /* bridge */ /* synthetic */ void onIsLoadingChanged(boolean z) {
            u8.h(this, z);
        }

        public /* bridge */ /* synthetic */ void onIsPlayingChanged(boolean z) {
            u8.i(this, z);
        }

        public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            PlayerView.a((TextureView) view, PlayerView.this.ad);
        }

        @Deprecated
        public /* bridge */ /* synthetic */ void onLoadingChanged(boolean z) {
            t8.e(this, z);
        }

        public /* bridge */ /* synthetic */ void onMediaItemTransition(@Nullable MediaItem mediaItem, int i) {
            u8.k(this, mediaItem, i);
        }

        public /* bridge */ /* synthetic */ void onMediaMetadataChanged(MediaMetadata mediaMetadata) {
            u8.l(this, mediaMetadata);
        }

        public /* bridge */ /* synthetic */ void onMetadata(Metadata metadata) {
            u8.m(this, metadata);
        }

        public void onPlayWhenReadyChanged(boolean z, int i) {
            int i2 = PlayerView.af;
            PlayerView playerView = PlayerView.this;
            playerView.g();
            if (!playerView.b() || !playerView.ab) {
                playerView.c(false);
            } else {
                playerView.hideController();
            }
        }

        public /* bridge */ /* synthetic */ void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
            u8.o(this, playbackParameters);
        }

        public void onPlaybackStateChanged(int i) {
            int i2 = PlayerView.af;
            PlayerView playerView = PlayerView.this;
            playerView.g();
            playerView.i();
            if (!playerView.b() || !playerView.ab) {
                playerView.c(false);
            } else {
                playerView.hideController();
            }
        }

        public /* bridge */ /* synthetic */ void onPlaybackSuppressionReasonChanged(int i) {
            u8.q(this, i);
        }

        public /* bridge */ /* synthetic */ void onPlayerError(ExoPlaybackException exoPlaybackException) {
            u8.r(this, exoPlaybackException);
        }

        @Deprecated
        public /* bridge */ /* synthetic */ void onPlayerStateChanged(boolean z, int i) {
            t8.m(this, z, i);
        }

        @Deprecated
        public /* bridge */ /* synthetic */ void onPositionDiscontinuity(int i) {
            t8.n(this, i);
        }

        public void onPositionDiscontinuity(Player.PositionInfo positionInfo, Player.PositionInfo positionInfo2, int i) {
            int i2 = PlayerView.af;
            PlayerView playerView = PlayerView.this;
            if (playerView.b() && playerView.ab) {
                playerView.hideController();
            }
        }

        public void onRenderedFirstFrame() {
            View view = PlayerView.this.g;
            if (view != null) {
                view.setVisibility(4);
            }
        }

        public /* bridge */ /* synthetic */ void onRepeatModeChanged(int i) {
            u8.w(this, i);
        }

        @Deprecated
        public /* bridge */ /* synthetic */ void onSeekProcessed() {
            t8.q(this);
        }

        public /* bridge */ /* synthetic */ void onShuffleModeEnabledChanged(boolean z) {
            u8.y(this, z);
        }

        public /* bridge */ /* synthetic */ void onSkipSilenceEnabledChanged(boolean z) {
            u8.z(this, z);
        }

        public /* bridge */ /* synthetic */ void onStaticMetadataChanged(List list) {
            u8.aa(this, list);
        }

        public /* bridge */ /* synthetic */ void onSurfaceSizeChanged(int i, int i2) {
            u8.ab(this, i, i2);
        }

        public /* bridge */ /* synthetic */ void onTimelineChanged(Timeline timeline, int i) {
            u8.ac(this, timeline, i);
        }

        @Deprecated
        public /* bridge */ /* synthetic */ void onTimelineChanged(Timeline timeline, @Nullable Object obj, int i) {
            t8.u(this, timeline, obj, i);
        }

        public void onTracksChanged(TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {
            PlayerView playerView = PlayerView.this;
            Player player = (Player) Assertions.checkNotNull(playerView.q);
            Timeline currentTimeline = player.getCurrentTimeline();
            if (currentTimeline.isEmpty()) {
                this.f = null;
            } else {
                boolean isEmpty = player.getCurrentTrackGroups().isEmpty();
                Timeline.Period period = this.c;
                if (!isEmpty) {
                    this.f = currentTimeline.getPeriod(player.getCurrentPeriodIndex(), period, true).f;
                } else {
                    Object obj = this.f;
                    if (obj != null) {
                        int indexOfPeriod = currentTimeline.getIndexOfPeriod(obj);
                        if (indexOfPeriod == -1 || player.getCurrentWindowIndex() != currentTimeline.getPeriod(indexOfPeriod, period).g) {
                            this.f = null;
                        } else {
                            return;
                        }
                    }
                }
            }
            playerView.j(false);
        }

        public void onVideoSizeChanged(int i, int i2, int i3, float f2) {
            float f3 = (i2 == 0 || i == 0) ? 1.0f : (((float) i) * f2) / ((float) i2);
            PlayerView playerView = PlayerView.this;
            View view = playerView.h;
            if (view instanceof TextureView) {
                if (i3 == 90 || i3 == 270) {
                    f3 = 1.0f / f3;
                }
                if (playerView.ad != 0) {
                    view.removeOnLayoutChangeListener(this);
                }
                playerView.ad = i3;
                View view2 = playerView.h;
                if (i3 != 0) {
                    view2.addOnLayoutChangeListener(this);
                }
                PlayerView.a((TextureView) view2, playerView.ad);
            }
            if (playerView.i) {
                f3 = 0.0f;
            }
            AspectRatioFrameLayout aspectRatioFrameLayout = playerView.f;
            if (aspectRatioFrameLayout != null) {
                aspectRatioFrameLayout.setAspectRatio(f3);
            }
        }

        public /* bridge */ /* synthetic */ void onVideoSizeChanged(VideoSize videoSize) {
            u8.ag(this, videoSize);
        }

        public void onVisibilityChange(int i) {
            int i2 = PlayerView.af;
            PlayerView.this.h();
        }

        public /* bridge */ /* synthetic */ void onVolumeChanged(float f2) {
            u8.ah(this, f2);
        }
    }

    public PlayerView(Context context) {
        this(context, (AttributeSet) null);
    }

    public static void a(TextureView textureView, int i2) {
        Matrix matrix = new Matrix();
        float width = (float) textureView.getWidth();
        float height = (float) textureView.getHeight();
        if (!(width == 0.0f || height == 0.0f || i2 == 0)) {
            float f2 = width / 2.0f;
            float f3 = height / 2.0f;
            matrix.postRotate((float) i2, f2, f3);
            RectF rectF = new RectF(0.0f, 0.0f, width, height);
            RectF rectF2 = new RectF();
            matrix.mapRect(rectF2, rectF);
            matrix.postScale(width / rectF2.width(), height / rectF2.height(), f2, f3);
        }
        textureView.setTransform(matrix);
    }

    public static void switchTargetView(Player player, @Nullable PlayerView playerView, @Nullable PlayerView playerView2) {
        if (playerView != playerView2) {
            if (playerView2 != null) {
                playerView2.setPlayer(player);
            }
            if (playerView != null) {
                playerView.setPlayer((Player) null);
            }
        }
    }

    public final boolean b() {
        Player player = this.q;
        return player != null && player.isPlayingAd() && this.q.getPlayWhenReady();
    }

    public final void c(boolean z2) {
        boolean z3;
        if ((!b() || !this.ab) && k()) {
            PlayerControlView playerControlView = this.n;
            int i2 = 0;
            if (!playerControlView.isVisible() || playerControlView.getShowTimeoutMs() > 0) {
                z3 = false;
            } else {
                z3 = true;
            }
            boolean e = e();
            if ((z2 || z3 || e) && k()) {
                if (!e) {
                    i2 = this.z;
                }
                playerControlView.setShowTimeoutMs(i2);
                playerControlView.show();
            }
        }
    }

    @RequiresNonNull({"artworkView"})
    public final boolean d(@Nullable Drawable drawable) {
        if (drawable != null) {
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();
            if (intrinsicWidth > 0 && intrinsicHeight > 0) {
                float f2 = ((float) intrinsicWidth) / ((float) intrinsicHeight);
                AspectRatioFrameLayout aspectRatioFrameLayout = this.f;
                if (aspectRatioFrameLayout != null) {
                    aspectRatioFrameLayout.setAspectRatio(f2);
                }
                ImageView imageView = this.j;
                imageView.setImageDrawable(drawable);
                imageView.setVisibility(0);
                return true;
            }
        }
        return false;
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        boolean z2;
        Player player = this.q;
        if (player != null && player.isPlayingAd()) {
            return super.dispatchKeyEvent(keyEvent);
        }
        int keyCode = keyEvent.getKeyCode();
        if (keyCode == 19 || keyCode == 270 || keyCode == 22 || keyCode == 271 || keyCode == 20 || keyCode == 269 || keyCode == 21 || keyCode == 268 || keyCode == 23) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2 && k() && !this.n.isVisible()) {
            c(true);
        } else if (dispatchMediaKeyEvent(keyEvent) || super.dispatchKeyEvent(keyEvent)) {
            c(true);
        } else if (!z2 || !k()) {
            return false;
        } else {
            c(true);
            return false;
        }
        return true;
    }

    public boolean dispatchMediaKeyEvent(KeyEvent keyEvent) {
        return k() && this.n.dispatchMediaKeyEvent(keyEvent);
    }

    public final boolean e() {
        Player player = this.q;
        if (player == null) {
            return true;
        }
        int playbackState = player.getPlaybackState();
        if (!this.aa || (playbackState != 1 && playbackState != 4 && this.q.getPlayWhenReady())) {
            return false;
        }
        return true;
    }

    public final boolean f() {
        if (!k() || this.q == null) {
            return false;
        }
        PlayerControlView playerControlView = this.n;
        if (!playerControlView.isVisible()) {
            c(true);
        } else if (this.ac) {
            playerControlView.hide();
        }
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001d, code lost:
        if (r5.q.getPlayWhenReady() == false) goto L_0x0020;
     */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void g() {
        /*
            r5 = this;
            android.view.View r0 = r5.l
            if (r0 == 0) goto L_0x0029
            com.google.android.exoplayer2.Player r1 = r5.q
            r2 = 0
            if (r1 == 0) goto L_0x0020
            int r1 = r1.getPlaybackState()
            r3 = 2
            if (r1 != r3) goto L_0x0020
            int r1 = r5.v
            r4 = 1
            if (r1 == r3) goto L_0x0021
            if (r1 != r4) goto L_0x0020
            com.google.android.exoplayer2.Player r1 = r5.q
            boolean r1 = r1.getPlayWhenReady()
            if (r1 == 0) goto L_0x0020
            goto L_0x0021
        L_0x0020:
            r4 = 0
        L_0x0021:
            if (r4 == 0) goto L_0x0024
            goto L_0x0026
        L_0x0024:
            r2 = 8
        L_0x0026:
            r0.setVisibility(r2)
        L_0x0029:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ui.PlayerView.g():void");
    }

    public List<AdOverlayInfo> getAdOverlayInfos() {
        ArrayList arrayList = new ArrayList();
        FrameLayout frameLayout = this.p;
        if (frameLayout != null) {
            arrayList.add(new AdOverlayInfo(frameLayout, 3, "Transparent overlay does not impact viewability"));
        }
        PlayerControlView playerControlView = this.n;
        if (playerControlView != null) {
            arrayList.add(new AdOverlayInfo(playerControlView, 0));
        }
        return ImmutableList.copyOf(arrayList);
    }

    public ViewGroup getAdViewGroup() {
        return (ViewGroup) Assertions.checkStateNotNull(this.o, "exo_ad_overlay must be present for ad playback");
    }

    public boolean getControllerAutoShow() {
        return this.aa;
    }

    public boolean getControllerHideOnTouch() {
        return this.ac;
    }

    public int getControllerShowTimeoutMs() {
        return this.z;
    }

    @Nullable
    public Drawable getDefaultArtwork() {
        return this.u;
    }

    @Nullable
    public FrameLayout getOverlayFrameLayout() {
        return this.p;
    }

    @Nullable
    public Player getPlayer() {
        return this.q;
    }

    public int getResizeMode() {
        AspectRatioFrameLayout aspectRatioFrameLayout = this.f;
        Assertions.checkStateNotNull(aspectRatioFrameLayout);
        return aspectRatioFrameLayout.getResizeMode();
    }

    @Nullable
    public SubtitleView getSubtitleView() {
        return this.k;
    }

    public boolean getUseArtwork() {
        return this.t;
    }

    public boolean getUseController() {
        return this.r;
    }

    @Nullable
    public View getVideoSurfaceView() {
        return this.h;
    }

    public final void h() {
        String str = null;
        PlayerControlView playerControlView = this.n;
        if (playerControlView == null || !this.r) {
            setContentDescription((CharSequence) null);
        } else if (playerControlView.getVisibility() == 0) {
            if (this.ac) {
                str = getResources().getString(R.string.exo_controls_hide);
            }
            setContentDescription(str);
        } else {
            setContentDescription(getResources().getString(R.string.exo_controls_show));
        }
    }

    public void hideController() {
        PlayerControlView playerControlView = this.n;
        if (playerControlView != null) {
            playerControlView.hide();
        }
    }

    public final void i() {
        ExoPlaybackException exoPlaybackException;
        ErrorMessageProvider<? super ExoPlaybackException> errorMessageProvider;
        TextView textView = this.m;
        if (textView != null) {
            CharSequence charSequence = this.y;
            if (charSequence != null) {
                textView.setText(charSequence);
                textView.setVisibility(0);
                return;
            }
            Player player = this.q;
            if (player != null) {
                exoPlaybackException = player.getPlayerError();
            } else {
                exoPlaybackException = null;
            }
            if (exoPlaybackException == null || (errorMessageProvider = this.x) == null) {
                textView.setVisibility(8);
                return;
            }
            textView.setText((CharSequence) errorMessageProvider.getErrorMessage(exoPlaybackException).second);
            textView.setVisibility(0);
        }
    }

    public boolean isControllerVisible() {
        PlayerControlView playerControlView = this.n;
        return playerControlView != null && playerControlView.isVisible();
    }

    public final void j(boolean z2) {
        boolean z3;
        byte[] bArr;
        int i2;
        Player player = this.q;
        View view = this.g;
        ImageView imageView = this.j;
        if (player != null && !player.getCurrentTrackGroups().isEmpty()) {
            if (z2 && !this.w && view != null) {
                view.setVisibility(0);
            }
            if (!TrackSelectionUtil.hasTrackOfType(player.getCurrentTrackSelections(), 2)) {
                if (view != null) {
                    view.setVisibility(0);
                }
                if (this.t) {
                    Assertions.checkStateNotNull(imageView);
                    z3 = true;
                } else {
                    z3 = false;
                }
                if (z3) {
                    for (Metadata next : player.getCurrentStaticMetadata()) {
                        int i3 = 0;
                        int i4 = -1;
                        boolean z4 = false;
                        while (i3 < next.length()) {
                            Metadata.Entry entry = next.get(i3);
                            if (entry instanceof ApicFrame) {
                                ApicFrame apicFrame = (ApicFrame) entry;
                                bArr = apicFrame.i;
                                i2 = apicFrame.h;
                            } else if (entry instanceof PictureFrame) {
                                PictureFrame pictureFrame = (PictureFrame) entry;
                                bArr = pictureFrame.l;
                                i2 = pictureFrame.c;
                            } else {
                                continue;
                                i3++;
                            }
                            if (i4 == -1 || i2 == 3) {
                                z4 = d(new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(bArr, 0, bArr.length)));
                                if (i2 == 3) {
                                    continue;
                                    break;
                                } else {
                                    i4 = i2;
                                    i3++;
                                }
                            } else {
                                i3++;
                            }
                        }
                        if (z4) {
                            return;
                        }
                    }
                    if (d(this.u)) {
                        return;
                    }
                }
                if (imageView != null) {
                    imageView.setImageResource(17170445);
                    imageView.setVisibility(4);
                }
            } else if (imageView != null) {
                imageView.setImageResource(17170445);
                imageView.setVisibility(4);
            }
        } else if (!this.w) {
            if (imageView != null) {
                imageView.setImageResource(17170445);
                imageView.setVisibility(4);
            }
            if (view != null) {
                view.setVisibility(0);
            }
        }
    }

    @EnsuresNonNullIf(expression = {"controller"}, result = true)
    public final boolean k() {
        if (!this.r) {
            return false;
        }
        Assertions.checkStateNotNull(this.n);
        return true;
    }

    public void onPause() {
        View view = this.h;
        if (view instanceof GLSurfaceView) {
            ((GLSurfaceView) view).onPause();
        }
    }

    public void onResume() {
        View view = this.h;
        if (view instanceof GLSurfaceView) {
            ((GLSurfaceView) view).onResume();
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!k() || this.q == null) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action == 0) {
            this.ae = true;
            return true;
        } else if (action != 1 || !this.ae) {
            return false;
        } else {
            this.ae = false;
            performClick();
            return true;
        }
    }

    public boolean onTrackballEvent(MotionEvent motionEvent) {
        if (!k() || this.q == null) {
            return false;
        }
        c(true);
        return true;
    }

    public boolean performClick() {
        super.performClick();
        return f();
    }

    public void setAspectRatioListener(@Nullable AspectRatioFrameLayout.AspectRatioListener aspectRatioListener) {
        AspectRatioFrameLayout aspectRatioFrameLayout = this.f;
        Assertions.checkStateNotNull(aspectRatioFrameLayout);
        aspectRatioFrameLayout.setAspectRatioListener(aspectRatioListener);
    }

    public void setControlDispatcher(ControlDispatcher controlDispatcher) {
        PlayerControlView playerControlView = this.n;
        Assertions.checkStateNotNull(playerControlView);
        playerControlView.setControlDispatcher(controlDispatcher);
    }

    public void setControllerAutoShow(boolean z2) {
        this.aa = z2;
    }

    public void setControllerHideDuringAds(boolean z2) {
        this.ab = z2;
    }

    public void setControllerHideOnTouch(boolean z2) {
        Assertions.checkStateNotNull(this.n);
        this.ac = z2;
        h();
    }

    public void setControllerShowTimeoutMs(int i2) {
        PlayerControlView playerControlView = this.n;
        Assertions.checkStateNotNull(playerControlView);
        this.z = i2;
        if (playerControlView.isVisible()) {
            showController();
        }
    }

    public void setControllerVisibilityListener(@Nullable PlayerControlView.VisibilityListener visibilityListener) {
        PlayerControlView playerControlView = this.n;
        Assertions.checkStateNotNull(playerControlView);
        PlayerControlView.VisibilityListener visibilityListener2 = this.s;
        if (visibilityListener2 != visibilityListener) {
            if (visibilityListener2 != null) {
                playerControlView.removeVisibilityListener(visibilityListener2);
            }
            this.s = visibilityListener;
            if (visibilityListener != null) {
                playerControlView.addVisibilityListener(visibilityListener);
            }
        }
    }

    public void setCustomErrorMessage(@Nullable CharSequence charSequence) {
        boolean z2;
        if (this.m != null) {
            z2 = true;
        } else {
            z2 = false;
        }
        Assertions.checkState(z2);
        this.y = charSequence;
        i();
    }

    public void setDefaultArtwork(@Nullable Drawable drawable) {
        if (this.u != drawable) {
            this.u = drawable;
            j(false);
        }
    }

    public void setErrorMessageProvider(@Nullable ErrorMessageProvider<? super ExoPlaybackException> errorMessageProvider) {
        if (this.x != errorMessageProvider) {
            this.x = errorMessageProvider;
            i();
        }
    }

    public void setExtraAdGroupMarkers(@Nullable long[] jArr, @Nullable boolean[] zArr) {
        PlayerControlView playerControlView = this.n;
        Assertions.checkStateNotNull(playerControlView);
        playerControlView.setExtraAdGroupMarkers(jArr, zArr);
    }

    @Deprecated
    public void setFastForwardIncrementMs(int i2) {
        PlayerControlView playerControlView = this.n;
        Assertions.checkStateNotNull(playerControlView);
        playerControlView.setFastForwardIncrementMs(i2);
    }

    public void setKeepContentOnPlayerReset(boolean z2) {
        if (this.w != z2) {
            this.w = z2;
            j(false);
        }
    }

    @Deprecated
    public void setPlaybackPreparer(@Nullable PlaybackPreparer playbackPreparer) {
        PlayerControlView playerControlView = this.n;
        Assertions.checkStateNotNull(playerControlView);
        playerControlView.setPlaybackPreparer(playbackPreparer);
    }

    public void setPlayer(@Nullable Player player) {
        boolean z2;
        boolean z3;
        if (Looper.myLooper() == Looper.getMainLooper()) {
            z2 = true;
        } else {
            z2 = false;
        }
        Assertions.checkState(z2);
        if (player == null || player.getApplicationLooper() == Looper.getMainLooper()) {
            z3 = true;
        } else {
            z3 = false;
        }
        Assertions.checkArgument(z3);
        Player player2 = this.q;
        if (player2 != player) {
            View view = this.h;
            a aVar = this.c;
            if (player2 != null) {
                player2.removeListener((Player.Listener) aVar);
                if (player2.isCommandAvailable(21)) {
                    if (view instanceof TextureView) {
                        player2.clearVideoTextureView((TextureView) view);
                    } else if (view instanceof SurfaceView) {
                        player2.clearVideoSurfaceView((SurfaceView) view);
                    }
                }
            }
            SubtitleView subtitleView = this.k;
            if (subtitleView != null) {
                subtitleView.setCues((List<Cue>) null);
            }
            this.q = player;
            if (k()) {
                this.n.setPlayer(player);
            }
            g();
            i();
            j(true);
            if (player != null) {
                if (player.isCommandAvailable(21)) {
                    if (view instanceof TextureView) {
                        player.setVideoTextureView((TextureView) view);
                    } else if (view instanceof SurfaceView) {
                        player.setVideoSurfaceView((SurfaceView) view);
                    }
                }
                if (subtitleView != null && player.isCommandAvailable(22)) {
                    subtitleView.setCues(player.getCurrentCues());
                }
                player.addListener((Player.Listener) aVar);
                c(false);
                return;
            }
            hideController();
        }
    }

    public void setRepeatToggleModes(int i2) {
        PlayerControlView playerControlView = this.n;
        Assertions.checkStateNotNull(playerControlView);
        playerControlView.setRepeatToggleModes(i2);
    }

    public void setResizeMode(int i2) {
        AspectRatioFrameLayout aspectRatioFrameLayout = this.f;
        Assertions.checkStateNotNull(aspectRatioFrameLayout);
        aspectRatioFrameLayout.setResizeMode(i2);
    }

    @Deprecated
    public void setRewindIncrementMs(int i2) {
        PlayerControlView playerControlView = this.n;
        Assertions.checkStateNotNull(playerControlView);
        playerControlView.setRewindIncrementMs(i2);
    }

    public void setShowBuffering(int i2) {
        if (this.v != i2) {
            this.v = i2;
            g();
        }
    }

    public void setShowFastForwardButton(boolean z2) {
        PlayerControlView playerControlView = this.n;
        Assertions.checkStateNotNull(playerControlView);
        playerControlView.setShowFastForwardButton(z2);
    }

    public void setShowMultiWindowTimeBar(boolean z2) {
        PlayerControlView playerControlView = this.n;
        Assertions.checkStateNotNull(playerControlView);
        playerControlView.setShowMultiWindowTimeBar(z2);
    }

    public void setShowNextButton(boolean z2) {
        PlayerControlView playerControlView = this.n;
        Assertions.checkStateNotNull(playerControlView);
        playerControlView.setShowNextButton(z2);
    }

    public void setShowPreviousButton(boolean z2) {
        PlayerControlView playerControlView = this.n;
        Assertions.checkStateNotNull(playerControlView);
        playerControlView.setShowPreviousButton(z2);
    }

    public void setShowRewindButton(boolean z2) {
        PlayerControlView playerControlView = this.n;
        Assertions.checkStateNotNull(playerControlView);
        playerControlView.setShowRewindButton(z2);
    }

    public void setShowShuffleButton(boolean z2) {
        PlayerControlView playerControlView = this.n;
        Assertions.checkStateNotNull(playerControlView);
        playerControlView.setShowShuffleButton(z2);
    }

    public void setShutterBackgroundColor(int i2) {
        View view = this.g;
        if (view != null) {
            view.setBackgroundColor(i2);
        }
    }

    public void setUseArtwork(boolean z2) {
        boolean z3;
        if (!z2 || this.j != null) {
            z3 = true;
        } else {
            z3 = false;
        }
        Assertions.checkState(z3);
        if (this.t != z2) {
            this.t = z2;
            j(false);
        }
    }

    public void setUseController(boolean z2) {
        boolean z3;
        PlayerControlView playerControlView = this.n;
        if (!z2 || playerControlView != null) {
            z3 = true;
        } else {
            z3 = false;
        }
        Assertions.checkState(z3);
        if (this.r != z2) {
            this.r = z2;
            if (k()) {
                playerControlView.setPlayer(this.q);
            } else if (playerControlView != null) {
                playerControlView.hide();
                playerControlView.setPlayer((Player) null);
            }
            h();
        }
    }

    public void setVisibility(int i2) {
        super.setVisibility(i2);
        View view = this.h;
        if (view instanceof SurfaceView) {
            view.setVisibility(i2);
        }
    }

    public void showController() {
        int i2;
        boolean e = e();
        if (k()) {
            if (e) {
                i2 = 0;
            } else {
                i2 = this.z;
            }
            PlayerControlView playerControlView = this.n;
            playerControlView.setShowTimeoutMs(i2);
            playerControlView.show();
        }
    }

    public PlayerView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    /* JADX INFO: finally extract failed */
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public PlayerView(Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        boolean z2;
        int i3;
        int i4;
        boolean z3;
        boolean z4;
        int i5;
        int i6;
        boolean z5;
        int i7;
        boolean z6;
        int i8;
        boolean z7;
        Context context2 = context;
        AttributeSet attributeSet2 = attributeSet;
        a aVar = new a();
        this.c = aVar;
        if (isInEditMode()) {
            this.f = null;
            this.g = null;
            this.h = null;
            this.i = false;
            this.j = null;
            this.k = null;
            this.l = null;
            this.m = null;
            this.n = null;
            this.o = null;
            this.p = null;
            ImageView imageView = new ImageView(context2);
            if (Util.a >= 23) {
                Resources resources = getResources();
                imageView.setImageDrawable(resources.getDrawable(R.drawable.exo_edit_mode_logo, (Resources.Theme) null));
                imageView.setBackgroundColor(resources.getColor(R.color.exo_edit_mode_background_color, (Resources.Theme) null));
            } else {
                Resources resources2 = getResources();
                imageView.setImageDrawable(resources2.getDrawable(R.drawable.exo_edit_mode_logo));
                imageView.setBackgroundColor(resources2.getColor(R.color.exo_edit_mode_background_color));
            }
            addView(imageView);
            return;
        }
        int i9 = R.layout.exo_player_view;
        boolean z8 = true;
        if (attributeSet2 != null) {
            TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet2, R.styleable.d, 0, 0);
            try {
                z4 = obtainStyledAttributes.hasValue(25);
                i5 = obtainStyledAttributes.getColor(25, 0);
                i9 = obtainStyledAttributes.getResourceId(13, R.layout.exo_player_view);
                z3 = obtainStyledAttributes.getBoolean(30, true);
                i4 = obtainStyledAttributes.getResourceId(6, 0);
                boolean z9 = obtainStyledAttributes.getBoolean(31, true);
                int i10 = obtainStyledAttributes.getInt(26, 1);
                i7 = obtainStyledAttributes.getInt(15, 0);
                int i11 = obtainStyledAttributes.getInt(24, 5000);
                z6 = obtainStyledAttributes.getBoolean(9, true);
                int i12 = i10;
                boolean z10 = obtainStyledAttributes.getBoolean(2, true);
                int integer = obtainStyledAttributes.getInteger(22, 0);
                this.w = obtainStyledAttributes.getBoolean(10, this.w);
                boolean z11 = obtainStyledAttributes.getBoolean(8, true);
                obtainStyledAttributes.recycle();
                i8 = i12;
                z8 = z10;
                i6 = integer;
                z2 = z9;
                i3 = i11;
                z5 = z11;
            } catch (Throwable th) {
                obtainStyledAttributes.recycle();
                throw th;
            }
        } else {
            i8 = 1;
            z6 = true;
            i7 = 0;
            z5 = true;
            i6 = 0;
            i5 = 0;
            z4 = false;
            z3 = true;
            i4 = 0;
            i3 = 5000;
            z2 = true;
        }
        LayoutInflater.from(context).inflate(i9, this);
        setDescendantFocusability(262144);
        AspectRatioFrameLayout aspectRatioFrameLayout = (AspectRatioFrameLayout) findViewById(R.id.exo_content_frame);
        this.f = aspectRatioFrameLayout;
        if (aspectRatioFrameLayout != null) {
            aspectRatioFrameLayout.setResizeMode(i7);
        }
        View findViewById = findViewById(R.id.exo_shutter);
        this.g = findViewById;
        if (findViewById != null && z4) {
            findViewById.setBackgroundColor(i5);
        }
        if (aspectRatioFrameLayout == null || i8 == 0) {
            this.h = null;
            z7 = false;
        } else {
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -1);
            if (i8 == 2) {
                this.h = new TextureView(context2);
            } else if (i8 == 3) {
                this.h = new SphericalGLSurfaceView(context2);
                z7 = true;
                this.h.setLayoutParams(layoutParams);
                this.h.setOnClickListener(aVar);
                this.h.setClickable(false);
                aspectRatioFrameLayout.addView(this.h, 0);
            } else if (i8 != 4) {
                this.h = new SurfaceView(context2);
            } else {
                this.h = new VideoDecoderGLSurfaceView(context2);
            }
            z7 = false;
            this.h.setLayoutParams(layoutParams);
            this.h.setOnClickListener(aVar);
            this.h.setClickable(false);
            aspectRatioFrameLayout.addView(this.h, 0);
        }
        this.i = z7;
        this.o = (FrameLayout) findViewById(R.id.exo_ad_overlay);
        this.p = (FrameLayout) findViewById(R.id.exo_overlay);
        ImageView imageView2 = (ImageView) findViewById(R.id.exo_artwork);
        this.j = imageView2;
        this.t = z3 && imageView2 != null;
        if (i4 != 0) {
            this.u = ContextCompat.getDrawable(getContext(), i4);
        }
        SubtitleView subtitleView = (SubtitleView) findViewById(R.id.exo_subtitles);
        this.k = subtitleView;
        if (subtitleView != null) {
            subtitleView.setUserDefaultStyle();
            subtitleView.setUserDefaultTextSize();
        }
        View findViewById2 = findViewById(R.id.exo_buffering);
        this.l = findViewById2;
        if (findViewById2 != null) {
            findViewById2.setVisibility(8);
        }
        this.v = i6;
        TextView textView = (TextView) findViewById(R.id.exo_error_message);
        this.m = textView;
        if (textView != null) {
            textView.setVisibility(8);
        }
        PlayerControlView playerControlView = (PlayerControlView) findViewById(R.id.exo_controller);
        View findViewById3 = findViewById(R.id.exo_controller_placeholder);
        if (playerControlView != null) {
            this.n = playerControlView;
        } else if (findViewById3 != null) {
            PlayerControlView playerControlView2 = new PlayerControlView(context2, (AttributeSet) null, 0, attributeSet2);
            this.n = playerControlView2;
            playerControlView2.setId(R.id.exo_controller);
            playerControlView2.setLayoutParams(findViewById3.getLayoutParams());
            ViewGroup viewGroup = (ViewGroup) findViewById3.getParent();
            int indexOfChild = viewGroup.indexOfChild(findViewById3);
            viewGroup.removeView(findViewById3);
            viewGroup.addView(playerControlView2, indexOfChild);
        } else {
            this.n = null;
        }
        PlayerControlView playerControlView3 = this.n;
        this.z = playerControlView3 != null ? i3 : 0;
        this.ac = z6;
        this.aa = z8;
        this.ab = z5;
        this.r = z2 && playerControlView3 != null;
        hideController();
        h();
        PlayerControlView playerControlView4 = this.n;
        if (playerControlView4 != null) {
            playerControlView4.addVisibilityListener(aVar);
        }
    }
}
