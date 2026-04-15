package com.google.android.exoplayer2.ui;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.media.session.MediaSessionCompat;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.google.android.exoplayer2.ControlDispatcher;
import com.google.android.exoplayer2.DefaultControlDispatcher;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.PlaybackPreparer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.NotificationUtil;
import com.google.android.exoplayer2.util.Util;
import info.dourok.voicebot.R;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerNotificationManager {
    public static int an;
    public final Context a;
    public boolean aa;
    public boolean ab;
    public boolean ac;
    public boolean ad;
    public int ae;
    public boolean af;
    public int ag;
    public int ah;
    @DrawableRes
    public int ai;
    public int aj;
    public int ak;
    public boolean al;
    @Nullable
    public final String am;
    public final String b;
    public final int c;
    public final MediaDescriptionAdapter d;
    @Nullable
    public final NotificationListener e;
    @Nullable
    public final CustomActionReceiver f;
    public final Handler g;
    public final NotificationManagerCompat h;
    public final IntentFilter i;
    public final b j;
    public final a k;
    public final HashMap l;
    public final Map<String, NotificationCompat.Action> m;
    public final PendingIntent n;
    public final int o;
    public final Timeline.Window p;
    @Nullable
    public NotificationCompat.Builder q;
    @Nullable
    public ArrayList r;
    @Nullable
    public Player s;
    @Nullable
    public PlaybackPreparer t;
    public ControlDispatcher u;
    public boolean v;
    public int w;
    @Nullable
    public MediaSessionCompat.Token x;
    public boolean y;
    public boolean z;

    public final class BitmapCallback {
        public final int a;

        public BitmapCallback(int i) {
            this.a = i;
        }

        public void onBitmap(Bitmap bitmap) {
            if (bitmap != null) {
                PlayerNotificationManager.this.g.obtainMessage(1, this.a, -1, bitmap).sendToTarget();
            }
        }
    }

    public static class Builder {
        public final Context a;
        public final int b;
        public final String c;
        public final MediaDescriptionAdapter d;
        @Nullable
        public NotificationListener e;
        @Nullable
        public CustomActionReceiver f;
        public int g;
        public int h;
        public int i;
        public int j;
        public int k;
        public int l;
        public int m;
        public int n;
        public int o;
        public int p;
        public int q;
        @Nullable
        public String r;

        public Builder(Context context, int i2, String str, MediaDescriptionAdapter mediaDescriptionAdapter) {
            boolean z;
            if (i2 > 0) {
                z = true;
            } else {
                z = false;
            }
            Assertions.checkArgument(z);
            this.a = context;
            this.b = i2;
            this.c = str;
            this.d = mediaDescriptionAdapter;
            this.i = 2;
            this.j = R.drawable.exo_notification_small_icon;
            this.l = R.drawable.exo_notification_play;
            this.m = R.drawable.exo_notification_pause;
            this.n = R.drawable.exo_notification_stop;
            this.k = R.drawable.exo_notification_rewind;
            this.o = R.drawable.exo_notification_fastforward;
            this.p = R.drawable.exo_notification_previous;
            this.q = R.drawable.exo_notification_next;
        }

        public PlayerNotificationManager build() {
            int i2 = this.g;
            if (i2 != 0) {
                NotificationUtil.createNotificationChannel(this.a, this.c, i2, this.h, this.i);
            }
            Context context = this.a;
            String str = this.c;
            int i3 = this.b;
            MediaDescriptionAdapter mediaDescriptionAdapter = this.d;
            NotificationListener notificationListener = this.e;
            CustomActionReceiver customActionReceiver = this.f;
            int i4 = this.j;
            int i5 = this.l;
            int i6 = this.m;
            int i7 = this.n;
            int i8 = this.k;
            int i9 = this.o;
            return new PlayerNotificationManager(context, str, i3, mediaDescriptionAdapter, notificationListener, customActionReceiver, i4, i5, i6, i7, i8, i9, this.p, this.q, this.r);
        }

        public Builder setChannelDescriptionResourceId(int i2) {
            this.h = i2;
            return this;
        }

        public Builder setChannelImportance(int i2) {
            this.i = i2;
            return this;
        }

        public Builder setChannelNameResourceId(int i2) {
            this.g = i2;
            return this;
        }

        public Builder setCustomActionReceiver(CustomActionReceiver customActionReceiver) {
            this.f = customActionReceiver;
            return this;
        }

        public Builder setFastForwardActionIconResourceId(int i2) {
            this.o = i2;
            return this;
        }

        public Builder setGroup(String str) {
            this.r = str;
            return this;
        }

        public Builder setNextActionIconResourceId(int i2) {
            this.q = i2;
            return this;
        }

        public Builder setNotificationListener(NotificationListener notificationListener) {
            this.e = notificationListener;
            return this;
        }

        public Builder setPauseActionIconResourceId(int i2) {
            this.m = i2;
            return this;
        }

        public Builder setPlayActionIconResourceId(int i2) {
            this.l = i2;
            return this;
        }

        public Builder setPreviousActionIconResourceId(int i2) {
            this.p = i2;
            return this;
        }

        public Builder setRewindActionIconResourceId(int i2) {
            this.k = i2;
            return this;
        }

        public Builder setSmallIconResourceId(int i2) {
            this.j = i2;
            return this;
        }

        public Builder setStopActionIconResourceId(int i2) {
            this.n = i2;
            return this;
        }
    }

    public interface CustomActionReceiver {
        Map<String, NotificationCompat.Action> createCustomActions(Context context, int i);

        List<String> getCustomActions(Player player);

        void onCustomAction(Player player, String str, Intent intent);
    }

    public interface MediaDescriptionAdapter {
        @Nullable
        PendingIntent createCurrentContentIntent(Player player);

        @Nullable
        CharSequence getCurrentContentText(Player player);

        CharSequence getCurrentContentTitle(Player player);

        @Nullable
        Bitmap getCurrentLargeIcon(Player player, BitmapCallback bitmapCallback);

        @Nullable
        CharSequence getCurrentSubText(Player player);
    }

    public interface NotificationListener {
        void onNotificationCancelled(int i, boolean z);

        void onNotificationPosted(int i, Notification notification, boolean z);
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Priority {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Visibility {
    }

    public class a extends BroadcastReceiver {
        public a() {
        }

        public void onReceive(Context context, Intent intent) {
            PlayerNotificationManager playerNotificationManager = PlayerNotificationManager.this;
            Player player = playerNotificationManager.s;
            if (player != null && playerNotificationManager.v && intent.getIntExtra("INSTANCE_ID", playerNotificationManager.o) == playerNotificationManager.o) {
                String action = intent.getAction();
                if ("com.google.android.exoplayer.play".equals(action)) {
                    if (player.getPlaybackState() == 1) {
                        PlaybackPreparer playbackPreparer = playerNotificationManager.t;
                        if (playbackPreparer != null) {
                            playbackPreparer.preparePlayback();
                        } else {
                            playerNotificationManager.u.dispatchPrepare(player);
                        }
                    } else if (player.getPlaybackState() == 4) {
                        playerNotificationManager.u.dispatchSeekTo(player, player.getCurrentWindowIndex(), -9223372036854775807L);
                    }
                    playerNotificationManager.u.dispatchSetPlayWhenReady(player, true);
                } else if ("com.google.android.exoplayer.pause".equals(action)) {
                    playerNotificationManager.u.dispatchSetPlayWhenReady(player, false);
                } else if ("com.google.android.exoplayer.prev".equals(action)) {
                    playerNotificationManager.u.dispatchPrevious(player);
                } else if ("com.google.android.exoplayer.rewind".equals(action)) {
                    playerNotificationManager.u.dispatchRewind(player);
                } else if ("com.google.android.exoplayer.ffwd".equals(action)) {
                    playerNotificationManager.u.dispatchFastForward(player);
                } else if ("com.google.android.exoplayer.next".equals(action)) {
                    playerNotificationManager.u.dispatchNext(player);
                } else if ("com.google.android.exoplayer.stop".equals(action)) {
                    playerNotificationManager.u.dispatchStop(player, true);
                } else if ("com.google.android.exoplayer.dismiss".equals(action)) {
                    playerNotificationManager.c(true);
                } else if (action != null && playerNotificationManager.f != null && playerNotificationManager.m.containsKey(action)) {
                    playerNotificationManager.f.onCustomAction(player, action, intent);
                }
            }
        }
    }

    public class b implements Player.EventListener {
        public b() {
        }

        public /* bridge */ /* synthetic */ void onAvailableCommandsChanged(Player.Commands commands) {
            t8.a(this, commands);
        }

        public void onEvents(Player player, Player.Events events) {
            if (events.containsAny(5, 6, 8, 0, 13, 12, 9, 10)) {
                Handler handler = PlayerNotificationManager.this.g;
                if (!handler.hasMessages(0)) {
                    handler.sendEmptyMessage(0);
                }
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

    @Deprecated
    public PlayerNotificationManager(Context context, String str, int i2, MediaDescriptionAdapter mediaDescriptionAdapter) {
        this(context, str, i2, mediaDescriptionAdapter, (NotificationListener) null, (CustomActionReceiver) null);
    }

    public static PendingIntent a(String str, Context context, int i2) {
        int i3;
        Intent intent = new Intent(str).setPackage(context.getPackageName());
        intent.putExtra("INSTANCE_ID", i2);
        if (Util.a >= 23) {
            i3 = 201326592;
        } else {
            i3 = 134217728;
        }
        return PendingIntent.getBroadcast(context, i2, intent, i3);
    }

    @Deprecated
    public static PlayerNotificationManager createWithNotificationChannel(Context context, String str, @StringRes int i2, int i3, MediaDescriptionAdapter mediaDescriptionAdapter) {
        return createWithNotificationChannel(context, str, i2, 0, i3, mediaDescriptionAdapter);
    }

    /* JADX WARNING: Removed duplicated region for block: B:100:0x0176  */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x017b  */
    /* JADX WARNING: Removed duplicated region for block: B:104:0x0180  */
    /* JADX WARNING: Removed duplicated region for block: B:105:0x0184  */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x0199  */
    /* JADX WARNING: Removed duplicated region for block: B:113:0x019b  */
    /* JADX WARNING: Removed duplicated region for block: B:121:0x01b1  */
    /* JADX WARNING: Removed duplicated region for block: B:134:0x0222  */
    /* JADX WARNING: Removed duplicated region for block: B:135:0x0238  */
    /* JADX WARNING: Removed duplicated region for block: B:138:0x0258  */
    /* JADX WARNING: Removed duplicated region for block: B:139:0x0268  */
    /* JADX WARNING: Removed duplicated region for block: B:142:0x0278  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00a6  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00b3  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x00d3  */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x00e7  */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x00f2  */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x0107  */
    /* JADX WARNING: Removed duplicated region for block: B:90:0x0147 A[LOOP:1: B:88:0x0141->B:90:0x0147, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:93:0x015c  */
    /* JADX WARNING: Removed duplicated region for block: B:96:0x016c  */
    /* JADX WARNING: Removed duplicated region for block: B:97:0x0171  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void b(com.google.android.exoplayer2.Player r18, @androidx.annotation.Nullable android.graphics.Bitmap r19) {
        /*
            r17 = this;
            r0 = r17
            r1 = r18
            int r2 = r18.getPlaybackState()
            r3 = 2
            r4 = 3
            r5 = 0
            r6 = 1
            if (r2 == r3) goto L_0x0010
            if (r2 != r4) goto L_0x0018
        L_0x0010:
            boolean r2 = r18.getPlayWhenReady()
            if (r2 == 0) goto L_0x0018
            r2 = 1
            goto L_0x0019
        L_0x0018:
            r2 = 0
        L_0x0019:
            androidx.core.app.NotificationCompat$Builder r3 = r0.q
            int r7 = r18.getPlaybackState()
            android.content.Context r8 = r0.a
            if (r7 != r6) goto L_0x0032
            com.google.android.exoplayer2.Timeline r7 = r18.getCurrentTimeline()
            boolean r7 = r7.isEmpty()
            if (r7 == 0) goto L_0x0032
            r1 = 0
            r0.r = r1
            goto L_0x0280
        L_0x0032:
            com.google.android.exoplayer2.Timeline r7 = r18.getCurrentTimeline()
            boolean r9 = r7.isEmpty()
            r10 = 4
            if (r9 != 0) goto L_0x0090
            boolean r9 = r18.isPlayingAd()
            if (r9 != 0) goto L_0x0090
            boolean r9 = r1.isCommandAvailable(r10)
            int r11 = r18.getCurrentWindowIndex()
            com.google.android.exoplayer2.Timeline$Window r12 = r0.p
            r7.getWindow(r11, r12)
            if (r9 != 0) goto L_0x0062
            boolean r7 = r12.isLive()
            if (r7 == 0) goto L_0x0062
            r7 = 6
            boolean r7 = r1.isCommandAvailable(r7)
            if (r7 == 0) goto L_0x0060
            goto L_0x0062
        L_0x0060:
            r7 = 0
            goto L_0x0063
        L_0x0062:
            r7 = 1
        L_0x0063:
            if (r9 == 0) goto L_0x006f
            com.google.android.exoplayer2.ControlDispatcher r11 = r0.u
            boolean r11 = r11.isRewindEnabled()
            if (r11 == 0) goto L_0x006f
            r11 = 1
            goto L_0x0070
        L_0x006f:
            r11 = 0
        L_0x0070:
            if (r9 == 0) goto L_0x007c
            com.google.android.exoplayer2.ControlDispatcher r9 = r0.u
            boolean r9 = r9.isFastForwardEnabled()
            if (r9 == 0) goto L_0x007c
            r9 = 1
            goto L_0x007d
        L_0x007c:
            r9 = 0
        L_0x007d:
            boolean r13 = r12.isLive()
            if (r13 == 0) goto L_0x0087
            boolean r12 = r12.m
            if (r12 != 0) goto L_0x008e
        L_0x0087:
            r12 = 5
            boolean r12 = r1.isCommandAvailable(r12)
            if (r12 == 0) goto L_0x0093
        L_0x008e:
            r12 = 1
            goto L_0x0094
        L_0x0090:
            r7 = 0
            r9 = 0
            r11 = 0
        L_0x0093:
            r12 = 0
        L_0x0094:
            java.util.ArrayList r13 = new java.util.ArrayList
            r13.<init>()
            boolean r14 = r0.y
            java.lang.String r15 = "com.google.android.exoplayer.prev"
            if (r14 == 0) goto L_0x00a4
            if (r7 == 0) goto L_0x00a4
            r13.add(r15)
        L_0x00a4:
            if (r11 == 0) goto L_0x00ab
            java.lang.String r7 = "com.google.android.exoplayer.rewind"
            r13.add(r7)
        L_0x00ab:
            boolean r7 = r0.ac
            java.lang.String r11 = "com.google.android.exoplayer.pause"
            java.lang.String r14 = "com.google.android.exoplayer.play"
            if (r7 == 0) goto L_0x00d1
            int r7 = r18.getPlaybackState()
            if (r7 == r10) goto L_0x00c7
            int r7 = r18.getPlaybackState()
            if (r7 == r6) goto L_0x00c7
            boolean r7 = r18.getPlayWhenReady()
            if (r7 == 0) goto L_0x00c7
            r7 = 1
            goto L_0x00c8
        L_0x00c7:
            r7 = 0
        L_0x00c8:
            if (r7 == 0) goto L_0x00ce
            r13.add(r11)
            goto L_0x00d1
        L_0x00ce:
            r13.add(r14)
        L_0x00d1:
            if (r9 == 0) goto L_0x00d8
            java.lang.String r7 = "com.google.android.exoplayer.ffwd"
            r13.add(r7)
        L_0x00d8:
            boolean r7 = r0.z
            java.lang.String r9 = "com.google.android.exoplayer.next"
            if (r7 == 0) goto L_0x00e3
            if (r12 == 0) goto L_0x00e3
            r13.add(r9)
        L_0x00e3:
            com.google.android.exoplayer2.ui.PlayerNotificationManager$CustomActionReceiver r7 = r0.f
            if (r7 == 0) goto L_0x00ee
            java.util.List r7 = r7.getCustomActions(r1)
            r13.addAll(r7)
        L_0x00ee:
            boolean r7 = r0.ad
            if (r7 == 0) goto L_0x00f7
            java.lang.String r7 = "com.google.android.exoplayer.stop"
            r13.add(r7)
        L_0x00f7:
            java.util.ArrayList r7 = new java.util.ArrayList
            int r12 = r13.size()
            r7.<init>(r12)
            r12 = 0
        L_0x0101:
            int r6 = r13.size()
            if (r12 >= r6) goto L_0x012d
            java.lang.Object r6 = r13.get(r12)
            java.lang.String r6 = (java.lang.String) r6
            java.util.HashMap r10 = r0.l
            boolean r16 = r10.containsKey(r6)
            if (r16 == 0) goto L_0x011c
            java.lang.Object r6 = r10.get(r6)
            androidx.core.app.NotificationCompat$Action r6 = (androidx.core.app.NotificationCompat.Action) r6
            goto L_0x0124
        L_0x011c:
            java.util.Map<java.lang.String, androidx.core.app.NotificationCompat$Action> r10 = r0.m
            java.lang.Object r6 = r10.get(r6)
            androidx.core.app.NotificationCompat$Action r6 = (androidx.core.app.NotificationCompat.Action) r6
        L_0x0124:
            if (r6 == 0) goto L_0x0129
            r7.add(r6)
        L_0x0129:
            int r12 = r12 + 1
            r10 = 4
            goto L_0x0101
        L_0x012d:
            if (r3 == 0) goto L_0x0137
            java.util.ArrayList r6 = r0.r
            boolean r6 = r7.equals(r6)
            if (r6 != 0) goto L_0x0153
        L_0x0137:
            androidx.core.app.NotificationCompat$Builder r3 = new androidx.core.app.NotificationCompat$Builder
            java.lang.String r6 = r0.b
            r3.<init>(r8, r6)
            r0.r = r7
            r6 = 0
        L_0x0141:
            int r10 = r7.size()
            if (r6 >= r10) goto L_0x0153
            java.lang.Object r10 = r7.get(r6)
            androidx.core.app.NotificationCompat$Action r10 = (androidx.core.app.NotificationCompat.Action) r10
            r3.addAction(r10)
            int r6 = r6 + 1
            goto L_0x0141
        L_0x0153:
            androidx.media.app.NotificationCompat$MediaStyle r6 = new androidx.media.app.NotificationCompat$MediaStyle
            r6.<init>()
            android.support.v4.media.session.MediaSessionCompat$Token r7 = r0.x
            if (r7 == 0) goto L_0x015f
            r6.setMediaSession(r7)
        L_0x015f:
            int r7 = r13.indexOf(r11)
            int r10 = r13.indexOf(r14)
            boolean r11 = r0.aa
            r12 = -1
            if (r11 == 0) goto L_0x0171
            int r11 = r13.indexOf(r15)
            goto L_0x0172
        L_0x0171:
            r11 = -1
        L_0x0172:
            boolean r14 = r0.ab
            if (r14 == 0) goto L_0x017b
            int r9 = r13.indexOf(r9)
            goto L_0x017c
        L_0x017b:
            r9 = -1
        L_0x017c:
            int[] r4 = new int[r4]
            if (r11 == r12) goto L_0x0184
            r4[r5] = r11
            r11 = 1
            goto L_0x0185
        L_0x0184:
            r11 = 0
        L_0x0185:
            int r13 = r18.getPlaybackState()
            r14 = 4
            if (r13 == r14) goto L_0x019b
            int r13 = r18.getPlaybackState()
            r14 = 1
            if (r13 == r14) goto L_0x019b
            boolean r13 = r18.getPlayWhenReady()
            if (r13 == 0) goto L_0x019b
            r13 = 1
            goto L_0x019c
        L_0x019b:
            r13 = 0
        L_0x019c:
            if (r7 == r12) goto L_0x01a6
            if (r13 == 0) goto L_0x01a6
            int r10 = r11 + 1
            r4[r11] = r7
            r11 = r10
            goto L_0x01af
        L_0x01a6:
            if (r10 == r12) goto L_0x01af
            if (r13 != 0) goto L_0x01af
            int r7 = r11 + 1
            r4[r11] = r10
            r11 = r7
        L_0x01af:
            if (r9 == r12) goto L_0x01b6
            int r7 = r11 + 1
            r4[r11] = r9
            r11 = r7
        L_0x01b6:
            int[] r4 = java.util.Arrays.copyOf(r4, r11)
            r6.setShowActionsInCompactView(r4)
            r4 = r2 ^ 1
            r6.setShowCancelButton(r4)
            android.app.PendingIntent r4 = r0.n
            r6.setCancelButtonIntent(r4)
            r3.setStyle(r6)
            r3.setDeleteIntent(r4)
            int r4 = r0.ae
            androidx.core.app.NotificationCompat$Builder r4 = r3.setBadgeIconType(r4)
            androidx.core.app.NotificationCompat$Builder r4 = r4.setOngoing(r2)
            int r6 = r0.ah
            androidx.core.app.NotificationCompat$Builder r4 = r4.setColor(r6)
            boolean r6 = r0.af
            androidx.core.app.NotificationCompat$Builder r4 = r4.setColorized(r6)
            int r6 = r0.ai
            androidx.core.app.NotificationCompat$Builder r4 = r4.setSmallIcon(r6)
            int r6 = r0.aj
            androidx.core.app.NotificationCompat$Builder r4 = r4.setVisibility(r6)
            int r6 = r0.ak
            androidx.core.app.NotificationCompat$Builder r4 = r4.setPriority(r6)
            int r6 = r0.ag
            r4.setDefaults(r6)
            int r4 = com.google.android.exoplayer2.util.Util.a
            r6 = 21
            if (r4 < r6) goto L_0x0238
            boolean r4 = r0.al
            if (r4 == 0) goto L_0x0238
            boolean r4 = r18.isPlaying()
            if (r4 == 0) goto L_0x0238
            boolean r4 = r18.isPlayingAd()
            if (r4 != 0) goto L_0x0238
            boolean r4 = r18.isCurrentWindowDynamic()
            if (r4 != 0) goto L_0x0238
            com.google.android.exoplayer2.PlaybackParameters r4 = r18.getPlaybackParameters()
            float r4 = r4.c
            r6 = 1065353216(0x3f800000, float:1.0)
            int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r4 != 0) goto L_0x0238
            long r6 = java.lang.System.currentTimeMillis()
            long r9 = r18.getContentPosition()
            long r6 = r6 - r9
            androidx.core.app.NotificationCompat$Builder r4 = r3.setWhen(r6)
            r6 = 1
            androidx.core.app.NotificationCompat$Builder r4 = r4.setShowWhen(r6)
            r4.setUsesChronometer(r6)
            goto L_0x023f
        L_0x0238:
            androidx.core.app.NotificationCompat$Builder r4 = r3.setShowWhen(r5)
            r4.setUsesChronometer(r5)
        L_0x023f:
            com.google.android.exoplayer2.ui.PlayerNotificationManager$MediaDescriptionAdapter r4 = r0.d
            java.lang.CharSequence r6 = r4.getCurrentContentTitle(r1)
            r3.setContentTitle(r6)
            java.lang.CharSequence r6 = r4.getCurrentContentText(r1)
            r3.setContentText(r6)
            java.lang.CharSequence r6 = r4.getCurrentSubText(r1)
            r3.setSubText(r6)
            if (r19 != 0) goto L_0x0268
            com.google.android.exoplayer2.ui.PlayerNotificationManager$BitmapCallback r6 = new com.google.android.exoplayer2.ui.PlayerNotificationManager$BitmapCallback
            int r7 = r0.w
            r9 = 1
            int r7 = r7 + r9
            r0.w = r7
            r6.<init>(r7)
            android.graphics.Bitmap r6 = r4.getCurrentLargeIcon(r1, r6)
            goto L_0x026a
        L_0x0268:
            r6 = r19
        L_0x026a:
            r3.setLargeIcon(r6)
            android.app.PendingIntent r1 = r4.createCurrentContentIntent(r1)
            r3.setContentIntent(r1)
            java.lang.String r1 = r0.am
            if (r1 == 0) goto L_0x027b
            r3.setGroup(r1)
        L_0x027b:
            r1 = 1
            r3.setOnlyAlertOnce(r1)
            r1 = r3
        L_0x0280:
            r0.q = r1
            if (r1 != 0) goto L_0x0288
            r0.c(r5)
            return
        L_0x0288:
            android.app.Notification r1 = r1.build()
            androidx.core.app.NotificationManagerCompat r3 = r0.h
            int r4 = r0.c
            r3.notify(r4, r1)
            boolean r3 = r0.v
            if (r3 != 0) goto L_0x029e
            android.content.IntentFilter r3 = r0.i
            com.google.android.exoplayer2.ui.PlayerNotificationManager$a r6 = r0.k
            r8.registerReceiver(r6, r3)
        L_0x029e:
            com.google.android.exoplayer2.ui.PlayerNotificationManager$NotificationListener r3 = r0.e
            if (r3 == 0) goto L_0x02ac
            if (r2 != 0) goto L_0x02a8
            boolean r2 = r0.v
            if (r2 != 0) goto L_0x02a9
        L_0x02a8:
            r5 = 1
        L_0x02a9:
            r3.onNotificationPosted(r4, r1, r5)
        L_0x02ac:
            r1 = 1
            r0.v = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ui.PlayerNotificationManager.b(com.google.android.exoplayer2.Player, android.graphics.Bitmap):void");
    }

    public final void c(boolean z2) {
        if (this.v) {
            this.v = false;
            this.g.removeMessages(0);
            NotificationManagerCompat notificationManagerCompat = this.h;
            int i2 = this.c;
            notificationManagerCompat.cancel(i2);
            this.a.unregisterReceiver(this.k);
            NotificationListener notificationListener = this.e;
            if (notificationListener != null) {
                notificationListener.onNotificationCancelled(i2, z2);
            }
        }
    }

    public void invalidate() {
        if (this.v) {
            Handler handler = this.g;
            if (!handler.hasMessages(0)) {
                handler.sendEmptyMessage(0);
            }
        }
    }

    public final void setBadgeIconType(int i2) {
        if (this.ae != i2) {
            if (i2 == 0 || i2 == 1 || i2 == 2) {
                this.ae = i2;
                invalidate();
                return;
            }
            throw new IllegalArgumentException();
        }
    }

    public final void setColor(int i2) {
        if (this.ah != i2) {
            this.ah = i2;
            invalidate();
        }
    }

    public final void setColorized(boolean z2) {
        if (this.af != z2) {
            this.af = z2;
            invalidate();
        }
    }

    public final void setControlDispatcher(ControlDispatcher controlDispatcher) {
        if (this.u != controlDispatcher) {
            this.u = controlDispatcher;
            invalidate();
        }
    }

    public final void setDefaults(int i2) {
        if (this.ag != i2) {
            this.ag = i2;
            invalidate();
        }
    }

    @Deprecated
    public final void setFastForwardIncrementMs(long j2) {
        ControlDispatcher controlDispatcher = this.u;
        if (controlDispatcher instanceof DefaultControlDispatcher) {
            ((DefaultControlDispatcher) controlDispatcher).setFastForwardIncrementMs(j2);
            invalidate();
        }
    }

    public final void setMediaSessionToken(MediaSessionCompat.Token token) {
        if (!Util.areEqual(this.x, token)) {
            this.x = token;
            invalidate();
        }
    }

    @Deprecated
    public void setPlaybackPreparer(@Nullable PlaybackPreparer playbackPreparer) {
        this.t = playbackPreparer;
    }

    public final void setPlayer(@Nullable Player player) {
        boolean z2;
        boolean z3 = true;
        if (Looper.myLooper() == Looper.getMainLooper()) {
            z2 = true;
        } else {
            z2 = false;
        }
        Assertions.checkState(z2);
        if (!(player == null || player.getApplicationLooper() == Looper.getMainLooper())) {
            z3 = false;
        }
        Assertions.checkArgument(z3);
        Player player2 = this.s;
        if (player2 != player) {
            b bVar = this.j;
            if (player2 != null) {
                player2.removeListener((Player.EventListener) bVar);
                if (player == null) {
                    c(false);
                }
            }
            this.s = player;
            if (player != null) {
                player.addListener((Player.EventListener) bVar);
                Handler handler = this.g;
                if (!handler.hasMessages(0)) {
                    handler.sendEmptyMessage(0);
                }
            }
        }
    }

    public final void setPriority(int i2) {
        if (this.ak != i2) {
            if (i2 == -2 || i2 == -1 || i2 == 0 || i2 == 1 || i2 == 2) {
                this.ak = i2;
                invalidate();
                return;
            }
            throw new IllegalArgumentException();
        }
    }

    @Deprecated
    public final void setRewindIncrementMs(long j2) {
        ControlDispatcher controlDispatcher = this.u;
        if (controlDispatcher instanceof DefaultControlDispatcher) {
            ((DefaultControlDispatcher) controlDispatcher).setRewindIncrementMs(j2);
            invalidate();
        }
    }

    public final void setSmallIcon(@DrawableRes int i2) {
        if (this.ai != i2) {
            this.ai = i2;
            invalidate();
        }
    }

    public final void setUseChronometer(boolean z2) {
        if (this.al != z2) {
            this.al = z2;
            invalidate();
        }
    }

    @Deprecated
    public final void setUseNavigationActions(boolean z2) {
        setUseNextAction(z2);
        setUsePreviousAction(z2);
    }

    @Deprecated
    public final void setUseNavigationActionsInCompactView(boolean z2) {
        setUseNextActionInCompactView(z2);
        setUsePreviousActionInCompactView(z2);
    }

    public void setUseNextAction(boolean z2) {
        if (this.z != z2) {
            this.z = z2;
            invalidate();
        }
    }

    public void setUseNextActionInCompactView(boolean z2) {
        if (this.ab != z2) {
            this.ab = z2;
            invalidate();
        }
    }

    public final void setUsePlayPauseActions(boolean z2) {
        if (this.ac != z2) {
            this.ac = z2;
            invalidate();
        }
    }

    public void setUsePreviousAction(boolean z2) {
        if (this.y != z2) {
            this.y = z2;
            invalidate();
        }
    }

    public void setUsePreviousActionInCompactView(boolean z2) {
        if (this.aa != z2) {
            this.aa = z2;
            invalidate();
        }
    }

    public final void setUseStopAction(boolean z2) {
        if (this.ad != z2) {
            this.ad = z2;
            invalidate();
        }
    }

    public final void setVisibility(int i2) {
        if (this.aj != i2) {
            if (i2 == -1 || i2 == 0 || i2 == 1) {
                this.aj = i2;
                invalidate();
                return;
            }
            throw new IllegalStateException();
        }
    }

    @Deprecated
    public PlayerNotificationManager(Context context, String str, int i2, MediaDescriptionAdapter mediaDescriptionAdapter, @Nullable NotificationListener notificationListener) {
        this(context, str, i2, mediaDescriptionAdapter, notificationListener, (CustomActionReceiver) null);
    }

    @Deprecated
    public static PlayerNotificationManager createWithNotificationChannel(Context context, String str, @StringRes int i2, @StringRes int i3, int i4, MediaDescriptionAdapter mediaDescriptionAdapter) {
        NotificationUtil.createNotificationChannel(context, str, i2, i3, 2);
        return new PlayerNotificationManager(context, str, i4, mediaDescriptionAdapter);
    }

    @Deprecated
    public PlayerNotificationManager(Context context, String str, int i2, MediaDescriptionAdapter mediaDescriptionAdapter, @Nullable CustomActionReceiver customActionReceiver) {
        this(context, str, i2, mediaDescriptionAdapter, (NotificationListener) null, customActionReceiver);
    }

    @Deprecated
    public PlayerNotificationManager(Context context, String str, int i2, MediaDescriptionAdapter mediaDescriptionAdapter, @Nullable NotificationListener notificationListener, @Nullable CustomActionReceiver customActionReceiver) {
        this(context, str, i2, mediaDescriptionAdapter, notificationListener, customActionReceiver, R.drawable.exo_notification_small_icon, R.drawable.exo_notification_play, R.drawable.exo_notification_pause, R.drawable.exo_notification_stop, R.drawable.exo_notification_rewind, R.drawable.exo_notification_fastforward, R.drawable.exo_notification_previous, R.drawable.exo_notification_next, (String) null);
    }

    @Deprecated
    public static PlayerNotificationManager createWithNotificationChannel(Context context, String str, @StringRes int i2, int i3, MediaDescriptionAdapter mediaDescriptionAdapter, @Nullable NotificationListener notificationListener) {
        return createWithNotificationChannel(context, str, i2, 0, i3, mediaDescriptionAdapter, notificationListener);
    }

    public PlayerNotificationManager(Context context, String str, int i2, MediaDescriptionAdapter mediaDescriptionAdapter, @Nullable NotificationListener notificationListener, @Nullable CustomActionReceiver customActionReceiver, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, @Nullable String str2) {
        Map<String, NotificationCompat.Action> map;
        Context applicationContext = context.getApplicationContext();
        this.a = applicationContext;
        this.b = str;
        this.c = i2;
        this.d = mediaDescriptionAdapter;
        this.e = notificationListener;
        this.f = customActionReceiver;
        this.ai = i3;
        this.am = str2;
        this.u = new DefaultControlDispatcher();
        this.p = new Timeline.Window();
        int i11 = an;
        an = i11 + 1;
        this.o = i11;
        this.g = Util.createHandler(Looper.getMainLooper(), new q1(3, this));
        this.h = NotificationManagerCompat.from(applicationContext);
        this.j = new b();
        this.k = new a();
        this.i = new IntentFilter();
        this.y = true;
        this.z = true;
        this.ac = true;
        this.af = true;
        this.al = true;
        this.ah = 0;
        this.ag = 0;
        this.ak = -1;
        this.ae = 1;
        this.aj = 1;
        HashMap hashMap = new HashMap();
        hashMap.put("com.google.android.exoplayer.play", new NotificationCompat.Action(i4, (CharSequence) applicationContext.getString(R.string.exo_controls_play_description), a("com.google.android.exoplayer.play", applicationContext, i11)));
        hashMap.put("com.google.android.exoplayer.pause", new NotificationCompat.Action(i5, (CharSequence) applicationContext.getString(R.string.exo_controls_pause_description), a("com.google.android.exoplayer.pause", applicationContext, i11)));
        hashMap.put("com.google.android.exoplayer.stop", new NotificationCompat.Action(i6, (CharSequence) applicationContext.getString(R.string.exo_controls_stop_description), a("com.google.android.exoplayer.stop", applicationContext, i11)));
        hashMap.put("com.google.android.exoplayer.rewind", new NotificationCompat.Action(i7, (CharSequence) applicationContext.getString(R.string.exo_controls_rewind_description), a("com.google.android.exoplayer.rewind", applicationContext, i11)));
        hashMap.put("com.google.android.exoplayer.ffwd", new NotificationCompat.Action(i8, (CharSequence) applicationContext.getString(R.string.exo_controls_fastforward_description), a("com.google.android.exoplayer.ffwd", applicationContext, i11)));
        hashMap.put("com.google.android.exoplayer.prev", new NotificationCompat.Action(i9, (CharSequence) applicationContext.getString(R.string.exo_controls_previous_description), a("com.google.android.exoplayer.prev", applicationContext, i11)));
        hashMap.put("com.google.android.exoplayer.next", new NotificationCompat.Action(i10, (CharSequence) applicationContext.getString(R.string.exo_controls_next_description), a("com.google.android.exoplayer.next", applicationContext, i11)));
        this.l = hashMap;
        for (String addAction : hashMap.keySet()) {
            this.i.addAction(addAction);
        }
        if (customActionReceiver != null) {
            map = customActionReceiver.createCustomActions(applicationContext, this.o);
        } else {
            map = Collections.emptyMap();
        }
        this.m = map;
        for (String addAction2 : map.keySet()) {
            this.i.addAction(addAction2);
        }
        this.n = a("com.google.android.exoplayer.dismiss", applicationContext, this.o);
        this.i.addAction("com.google.android.exoplayer.dismiss");
    }

    @Deprecated
    public static PlayerNotificationManager createWithNotificationChannel(Context context, String str, @StringRes int i2, @StringRes int i3, int i4, MediaDescriptionAdapter mediaDescriptionAdapter, @Nullable NotificationListener notificationListener) {
        NotificationUtil.createNotificationChannel(context, str, i2, i3, 2);
        return new PlayerNotificationManager(context, str, i4, mediaDescriptionAdapter, notificationListener);
    }
}
