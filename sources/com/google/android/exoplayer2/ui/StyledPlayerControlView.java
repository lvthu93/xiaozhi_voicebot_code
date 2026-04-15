package com.google.android.exoplayer2.ui;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.ActivityChooserView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.exoplayer2.ControlDispatcher;
import com.google.android.exoplayer2.DefaultControlDispatcher;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerLibraryInfo;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.PlaybackPreparer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.TimeBar;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.RepeatModeUtil;
import com.google.android.exoplayer2.util.Util;
import info.dourok.voicebot.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;

public class StyledPlayerControlView extends FrameLayout {
    public static final /* synthetic */ int cf = 0;
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
    public final Drawable ak;
    public final Drawable al;
    public final String am;
    public final String an;
    public final Drawable ao;
    public final Drawable ap;
    public final String aq;
    public final String ar;
    @Nullable
    public Player as;
    public ControlDispatcher at;
    @Nullable
    public ProgressUpdateListener au;
    @Nullable
    public PlaybackPreparer av;
    @Nullable
    public OnFullScreenModeChangedListener aw;
    public boolean ax;
    public boolean ay;
    public boolean az;
    public boolean ba;
    public boolean bb;
    public int bc;
    public int bd;
    public int be;
    public long[] bf;
    public boolean[] bg;
    public long[] bh;
    public boolean[] bi;
    public long bj;
    public long bk;
    public long bl;
    public final b bm;
    public final Resources bn;
    public final RecyclerView bo;
    public final e bp;
    public final c bq;
    public final PopupWindow br;
    public boolean bs;
    public final int bt;
    @Nullable
    public DefaultTrackSelector bu;
    public final g bv;
    public final a bw;
    public final DefaultTrackNameProvider bx;
    @Nullable
    public final ImageView bz;
    public final b c;
    @Nullable
    public final ImageView ca;
    @Nullable
    public final ImageView cb;
    @Nullable
    public final View cc;
    @Nullable
    public final View cd;
    @Nullable
    public final View ce;
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
    public final TextView l;
    @Nullable
    public final TextView m;
    @Nullable
    public final ImageView n;
    @Nullable
    public final ImageView o;
    @Nullable
    public final View p;
    @Nullable
    public final TextView q;
    @Nullable
    public final TextView r;
    @Nullable
    public final TimeBar s;
    public final StringBuilder t;
    public final Formatter u;
    public final Timeline.Period v;
    public final Timeline.Window w;
    public final qb x;
    public final Drawable y;
    public final Drawable z;

    public interface OnFullScreenModeChangedListener {
        void onFullScreenModeChanged(boolean z);
    }

    public interface ProgressUpdateListener {
        void onProgressUpdate(long j, long j2);
    }

    public interface VisibilityListener {
        void onVisibilityChange(int i);
    }

    public final class a extends i {
        public a() {
            super();
        }

        public void init(List<Integer> list, List<h> list2, MappingTrackSelector.MappedTrackInfo mappedTrackInfo) {
            StyledPlayerControlView styledPlayerControlView;
            boolean z;
            int i = 0;
            int i2 = 0;
            while (true) {
                int size = list.size();
                styledPlayerControlView = StyledPlayerControlView.this;
                if (i2 >= size) {
                    z = false;
                    break;
                }
                int intValue = list.get(i2).intValue();
                TrackGroupArray trackGroups = mappedTrackInfo.getTrackGroups(intValue);
                DefaultTrackSelector defaultTrackSelector = styledPlayerControlView.bu;
                if (defaultTrackSelector != null && defaultTrackSelector.getParameters().hasSelectionOverride(intValue, trackGroups)) {
                    z = true;
                    break;
                }
                i2++;
            }
            if (!list2.isEmpty()) {
                if (z) {
                    while (true) {
                        if (i >= list2.size()) {
                            break;
                        }
                        h hVar = list2.get(i);
                        if (hVar.e) {
                            styledPlayerControlView.bp.setSubTextAtPosition(1, hVar.d);
                            break;
                        }
                        i++;
                    }
                } else {
                    styledPlayerControlView.bp.setSubTextAtPosition(1, styledPlayerControlView.getResources().getString(R.string.exo_track_selection_auto));
                }
            } else {
                styledPlayerControlView.bp.setSubTextAtPosition(1, styledPlayerControlView.getResources().getString(R.string.exo_track_selection_none));
            }
            this.a = list;
            this.b = list2;
            this.c = mappedTrackInfo;
        }

        public void onBindViewHolderAtZeroPosition(f fVar) {
            boolean z;
            int i;
            fVar.a.setText(R.string.exo_track_selection_auto);
            DefaultTrackSelector.Parameters parameters = ((DefaultTrackSelector) Assertions.checkNotNull(StyledPlayerControlView.this.bu)).getParameters();
            int i2 = 0;
            while (true) {
                if (i2 >= this.a.size()) {
                    z = false;
                    break;
                }
                int intValue = this.a.get(i2).intValue();
                if (parameters.hasSelectionOverride(intValue, ((MappingTrackSelector.MappedTrackInfo) Assertions.checkNotNull(this.c)).getTrackGroups(intValue))) {
                    z = true;
                    break;
                }
                i2++;
            }
            if (z) {
                i = 4;
            } else {
                i = 0;
            }
            fVar.b.setVisibility(i);
            fVar.itemView.setOnClickListener(new ub(0, this));
        }

        public void onTrackSelection(String str) {
            StyledPlayerControlView.this.bp.setSubTextAtPosition(1, str);
        }
    }

    public final class b implements Player.EventListener, TimeBar.OnScrubListener, View.OnClickListener, PopupWindow.OnDismissListener {
        public b() {
        }

        public /* bridge */ /* synthetic */ void onAvailableCommandsChanged(Player.Commands commands) {
            t8.a(this, commands);
        }

        public void onClick(View view) {
            StyledPlayerControlView styledPlayerControlView = StyledPlayerControlView.this;
            Player player = styledPlayerControlView.as;
            if (player != null) {
                b bVar = styledPlayerControlView.bm;
                bVar.resetHideCallbacks();
                if (styledPlayerControlView.h == view) {
                    styledPlayerControlView.at.dispatchNext(player);
                } else if (styledPlayerControlView.g == view) {
                    styledPlayerControlView.at.dispatchPrevious(player);
                } else if (styledPlayerControlView.j == view) {
                    if (player.getPlaybackState() != 4) {
                        styledPlayerControlView.at.dispatchFastForward(player);
                    }
                } else if (styledPlayerControlView.k == view) {
                    styledPlayerControlView.at.dispatchRewind(player);
                } else if (styledPlayerControlView.i == view) {
                    int playbackState = player.getPlaybackState();
                    if (playbackState == 1 || playbackState == 4 || !player.getPlayWhenReady()) {
                        styledPlayerControlView.c(player);
                    } else {
                        styledPlayerControlView.at.dispatchSetPlayWhenReady(player, false);
                    }
                } else if (styledPlayerControlView.n == view) {
                    styledPlayerControlView.at.dispatchSetRepeatMode(player, RepeatModeUtil.getNextRepeatMode(player.getRepeatMode(), styledPlayerControlView.be));
                } else if (styledPlayerControlView.o == view) {
                    styledPlayerControlView.at.dispatchSetShuffleModeEnabled(player, !player.getShuffleModeEnabled());
                } else if (styledPlayerControlView.cc == view) {
                    bVar.removeHideCallbacks();
                    styledPlayerControlView.d(styledPlayerControlView.bp);
                } else if (styledPlayerControlView.cd == view) {
                    bVar.removeHideCallbacks();
                    styledPlayerControlView.d(styledPlayerControlView.bq);
                } else if (styledPlayerControlView.ce == view) {
                    bVar.removeHideCallbacks();
                    styledPlayerControlView.d(styledPlayerControlView.bw);
                } else if (styledPlayerControlView.bz == view) {
                    bVar.removeHideCallbacks();
                    styledPlayerControlView.d(styledPlayerControlView.bv);
                }
            }
        }

        public void onDismiss() {
            StyledPlayerControlView styledPlayerControlView = StyledPlayerControlView.this;
            if (styledPlayerControlView.bs) {
                styledPlayerControlView.bm.resetHideCallbacks();
            }
        }

        public void onEvents(Player player, Player.Events events) {
            boolean containsAny = events.containsAny(5, 6);
            StyledPlayerControlView styledPlayerControlView = StyledPlayerControlView.this;
            if (containsAny) {
                int i = StyledPlayerControlView.cf;
                styledPlayerControlView.i();
            }
            if (events.containsAny(5, 6, 8)) {
                int i2 = StyledPlayerControlView.cf;
                styledPlayerControlView.j();
            }
            if (events.contains(9)) {
                int i3 = StyledPlayerControlView.cf;
                styledPlayerControlView.k();
            }
            if (events.contains(10)) {
                int i4 = StyledPlayerControlView.cf;
                styledPlayerControlView.m();
            }
            if (events.containsAny(9, 10, 12, 0)) {
                int i5 = StyledPlayerControlView.cf;
                styledPlayerControlView.h();
            }
            if (events.containsAny(12, 0)) {
                int i6 = StyledPlayerControlView.cf;
                styledPlayerControlView.n();
            }
            if (events.contains(13)) {
                int i7 = StyledPlayerControlView.cf;
                Player player2 = styledPlayerControlView.as;
                if (player2 != null) {
                    float f = player2.getPlaybackParameters().c;
                    c cVar = styledPlayerControlView.bq;
                    cVar.updateSelectedIndex(f);
                    styledPlayerControlView.bp.setSubTextAtPosition(0, cVar.getSelectedText());
                }
            }
            if (events.contains(2)) {
                int i8 = StyledPlayerControlView.cf;
                styledPlayerControlView.o();
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
            StyledPlayerControlView styledPlayerControlView = StyledPlayerControlView.this;
            TextView textView = styledPlayerControlView.r;
            if (textView != null) {
                textView.setText(Util.getStringForTime(styledPlayerControlView.t, styledPlayerControlView.u, j));
            }
        }

        public void onScrubStart(TimeBar timeBar, long j) {
            StyledPlayerControlView styledPlayerControlView = StyledPlayerControlView.this;
            styledPlayerControlView.bb = true;
            TextView textView = styledPlayerControlView.r;
            if (textView != null) {
                textView.setText(Util.getStringForTime(styledPlayerControlView.t, styledPlayerControlView.u, j));
            }
            styledPlayerControlView.bm.removeHideCallbacks();
        }

        public void onScrubStop(TimeBar timeBar, long j, boolean z) {
            Player player;
            StyledPlayerControlView styledPlayerControlView = StyledPlayerControlView.this;
            int i = 0;
            styledPlayerControlView.bb = false;
            if (!z && (player = styledPlayerControlView.as) != null) {
                Timeline currentTimeline = player.getCurrentTimeline();
                if (styledPlayerControlView.ba && !currentTimeline.isEmpty()) {
                    int windowCount = currentTimeline.getWindowCount();
                    while (true) {
                        long durationMs = currentTimeline.getWindow(i, styledPlayerControlView.w).getDurationMs();
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
                styledPlayerControlView.at.dispatchSeekTo(player, i, j);
                styledPlayerControlView.j();
            }
            styledPlayerControlView.bm.resetHideCallbacks();
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

    public final class c extends RecyclerView.Adapter<f> {
        public final String[] a;
        public final int[] b;
        public int c;

        public c(String[] strArr, int[] iArr) {
            this.a = strArr;
            this.b = iArr;
        }

        public int getItemCount() {
            return this.a.length;
        }

        public String getSelectedText() {
            return this.a[this.c];
        }

        public void updateSelectedIndex(float f) {
            int round = Math.round(f * 100.0f);
            int i = 0;
            int i2 = 0;
            int i3 = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            while (true) {
                int[] iArr = this.b;
                if (i < iArr.length) {
                    int abs = Math.abs(round - iArr[i]);
                    if (abs < i3) {
                        i2 = i;
                        i3 = abs;
                    }
                    i++;
                } else {
                    this.c = i2;
                    return;
                }
            }
        }

        public void onBindViewHolder(f fVar, int i) {
            String[] strArr = this.a;
            if (i < strArr.length) {
                fVar.a.setText(strArr[i]);
            }
            fVar.b.setVisibility(i == this.c ? 0 : 4);
            fVar.itemView.setOnClickListener(new vb(this, i));
        }

        public f onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new f(LayoutInflater.from(StyledPlayerControlView.this.getContext()).inflate(R.layout.exo_styled_sub_settings_list_item, viewGroup, false));
        }
    }

    public final class d extends RecyclerView.ViewHolder {
        public static final /* synthetic */ int e = 0;
        public final TextView a;
        public final TextView b;
        public final ImageView c;

        public d(View view) {
            super(view);
            if (Util.a < 26) {
                view.setFocusable(true);
            }
            this.a = (TextView) view.findViewById(R.id.exo_main_text);
            this.b = (TextView) view.findViewById(R.id.exo_sub_text);
            this.c = (ImageView) view.findViewById(R.id.exo_icon);
            view.setOnClickListener(new ub(1, this));
        }
    }

    public class e extends RecyclerView.Adapter<d> {
        public final String[] a;
        public final String[] b;
        public final Drawable[] c;

        public e(String[] strArr, Drawable[] drawableArr) {
            this.a = strArr;
            this.b = new String[strArr.length];
            this.c = drawableArr;
        }

        public int getItemCount() {
            return this.a.length;
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public void setSubTextAtPosition(int i, String str) {
            this.b[i] = str;
        }

        public void onBindViewHolder(d dVar, int i) {
            dVar.a.setText(this.a[i]);
            String str = this.b[i];
            TextView textView = dVar.b;
            if (str == null) {
                textView.setVisibility(8);
            } else {
                textView.setText(str);
            }
            Drawable drawable = this.c[i];
            ImageView imageView = dVar.c;
            if (drawable == null) {
                imageView.setVisibility(8);
            } else {
                imageView.setImageDrawable(drawable);
            }
        }

        public d onCreateViewHolder(ViewGroup viewGroup, int i) {
            StyledPlayerControlView styledPlayerControlView = StyledPlayerControlView.this;
            return new d(LayoutInflater.from(styledPlayerControlView.getContext()).inflate(R.layout.exo_styled_settings_list_item, viewGroup, false));
        }
    }

    public static class f extends RecyclerView.ViewHolder {
        public final TextView a;
        public final View b;

        public f(View view) {
            super(view);
            if (Util.a < 26) {
                view.setFocusable(true);
            }
            this.a = (TextView) view.findViewById(R.id.exo_text);
            this.b = view.findViewById(R.id.exo_check);
        }
    }

    public final class g extends i {
        public g() {
            super();
        }

        public void init(List<Integer> list, List<h> list2, MappingTrackSelector.MappedTrackInfo mappedTrackInfo) {
            Drawable drawable;
            String str;
            boolean z = false;
            int i = 0;
            while (true) {
                if (i >= list2.size()) {
                    break;
                } else if (list2.get(i).e) {
                    z = true;
                    break;
                } else {
                    i++;
                }
            }
            StyledPlayerControlView styledPlayerControlView = StyledPlayerControlView.this;
            ImageView imageView = styledPlayerControlView.bz;
            if (imageView != null) {
                if (z) {
                    drawable = styledPlayerControlView.ak;
                } else {
                    drawable = styledPlayerControlView.al;
                }
                imageView.setImageDrawable(drawable);
                ImageView imageView2 = styledPlayerControlView.bz;
                if (z) {
                    str = styledPlayerControlView.am;
                } else {
                    str = styledPlayerControlView.an;
                }
                imageView2.setContentDescription(str);
            }
            this.a = list;
            this.b = list2;
            this.c = mappedTrackInfo;
        }

        public void onBindViewHolderAtZeroPosition(f fVar) {
            boolean z;
            fVar.a.setText(R.string.exo_track_selection_none);
            int i = 0;
            int i2 = 0;
            while (true) {
                if (i2 >= this.b.size()) {
                    z = true;
                    break;
                } else if (this.b.get(i2).e) {
                    z = false;
                    break;
                } else {
                    i2++;
                }
            }
            if (!z) {
                i = 4;
            }
            fVar.b.setVisibility(i);
            fVar.itemView.setOnClickListener(new ub(2, this));
        }

        public void onTrackSelection(String str) {
        }

        public void onBindViewHolder(f fVar, int i) {
            super.onBindViewHolder(fVar, i);
            if (i > 0) {
                fVar.b.setVisibility(this.b.get(i + -1).e ? 0 : 4);
            }
        }
    }

    public static final class h {
        public final int a;
        public final int b;
        public final int c;
        public final String d;
        public final boolean e;

        public h(int i, int i2, int i3, String str, boolean z) {
            this.a = i;
            this.b = i2;
            this.c = i3;
            this.d = str;
            this.e = z;
        }
    }

    public abstract class i extends RecyclerView.Adapter<f> {
        public List<Integer> a = new ArrayList();
        public List<h> b = new ArrayList();
        @Nullable
        public MappingTrackSelector.MappedTrackInfo c = null;

        public i() {
        }

        public void clear() {
            this.b = Collections.emptyList();
            this.c = null;
        }

        public int getItemCount() {
            if (this.b.isEmpty()) {
                return 0;
            }
            return this.b.size() + 1;
        }

        public abstract void init(List<Integer> list, List<h> list2, MappingTrackSelector.MappedTrackInfo mappedTrackInfo);

        public void onBindViewHolder(f fVar, int i) {
            StyledPlayerControlView styledPlayerControlView = StyledPlayerControlView.this;
            if (styledPlayerControlView.bu != null && this.c != null) {
                if (i == 0) {
                    onBindViewHolderAtZeroPosition(fVar);
                    return;
                }
                boolean z = true;
                h hVar = this.b.get(i - 1);
                int i2 = 0;
                if (!((DefaultTrackSelector) Assertions.checkNotNull(styledPlayerControlView.bu)).getParameters().hasSelectionOverride(hVar.a, this.c.getTrackGroups(hVar.a)) || !hVar.e) {
                    z = false;
                }
                fVar.a.setText(hVar.d);
                if (!z) {
                    i2 = 4;
                }
                fVar.b.setVisibility(i2);
                fVar.itemView.setOnClickListener(new wb(this, hVar));
            }
        }

        public abstract void onBindViewHolderAtZeroPosition(f fVar);

        public abstract void onTrackSelection(String str);

        public f onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new f(LayoutInflater.from(StyledPlayerControlView.this.getContext()).inflate(R.layout.exo_styled_sub_settings_list_item, viewGroup, false));
        }
    }

    static {
        ExoPlayerLibraryInfo.registerModule("goog.exo.ui");
    }

    public StyledPlayerControlView(Context context) {
        this(context, (AttributeSet) null);
    }

    public static void a(StyledPlayerControlView styledPlayerControlView) {
        if (styledPlayerControlView.aw != null) {
            boolean z2 = !styledPlayerControlView.ax;
            styledPlayerControlView.ax = z2;
            String str = styledPlayerControlView.aq;
            Drawable drawable = styledPlayerControlView.ao;
            String str2 = styledPlayerControlView.ar;
            Drawable drawable2 = styledPlayerControlView.ap;
            ImageView imageView = styledPlayerControlView.ca;
            if (imageView != null) {
                if (z2) {
                    imageView.setImageDrawable(drawable);
                    imageView.setContentDescription(str);
                } else {
                    imageView.setImageDrawable(drawable2);
                    imageView.setContentDescription(str2);
                }
            }
            boolean z3 = styledPlayerControlView.ax;
            ImageView imageView2 = styledPlayerControlView.cb;
            if (imageView2 != null) {
                if (z3) {
                    imageView2.setImageDrawable(drawable);
                    imageView2.setContentDescription(str);
                } else {
                    imageView2.setImageDrawable(drawable2);
                    imageView2.setContentDescription(str2);
                }
            }
            OnFullScreenModeChangedListener onFullScreenModeChangedListener = styledPlayerControlView.aw;
            if (onFullScreenModeChangedListener != null) {
                onFullScreenModeChangedListener.onFullScreenModeChanged(styledPlayerControlView.ax);
            }
        }
    }

    /* access modifiers changed from: private */
    public void setPlaybackSpeed(float f2) {
        Player player = this.as;
        if (player != null) {
            this.at.dispatchSetPlaybackParameters(player, player.getPlaybackParameters().withSpeed(f2));
        }
    }

    public void addVisibilityListener(VisibilityListener visibilityListener) {
        Assertions.checkNotNull(visibilityListener);
        this.f.add(visibilityListener);
    }

    public final void c(Player player) {
        int playbackState = player.getPlaybackState();
        if (playbackState == 1) {
            PlaybackPreparer playbackPreparer = this.av;
            if (playbackPreparer != null) {
                playbackPreparer.preparePlayback();
            } else {
                this.at.dispatchPrepare(player);
            }
        } else if (playbackState == 4) {
            this.at.dispatchSeekTo(player, player.getCurrentWindowIndex(), -9223372036854775807L);
        }
        this.at.dispatchSetPlayWhenReady(player, true);
    }

    public final void d(RecyclerView.Adapter<?> adapter) {
        this.bo.setAdapter(adapter);
        l();
        this.bs = false;
        PopupWindow popupWindow = this.br;
        popupWindow.dismiss();
        this.bs = true;
        int width = getWidth() - popupWindow.getWidth();
        int i2 = this.bt;
        popupWindow.showAsDropDown(this, width - i2, (-popupWindow.getHeight()) - i2);
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return dispatchMediaKeyEvent(keyEvent) || super.dispatchKeyEvent(keyEvent);
    }

    public boolean dispatchMediaKeyEvent(KeyEvent keyEvent) {
        boolean z2;
        int keyCode = keyEvent.getKeyCode();
        Player player = this.as;
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
                            this.at.dispatchFastForward(player);
                        }
                    } else if (keyCode == 89) {
                        this.at.dispatchRewind(player);
                    } else if (keyEvent.getRepeatCount() == 0) {
                        if (keyCode == 79 || keyCode == 85) {
                            int playbackState = player.getPlaybackState();
                            if (playbackState == 1 || playbackState == 4 || !player.getPlayWhenReady()) {
                                c(player);
                            } else {
                                this.at.dispatchSetPlayWhenReady(player, false);
                            }
                        } else if (keyCode == 87) {
                            this.at.dispatchNext(player);
                        } else if (keyCode == 88) {
                            this.at.dispatchPrevious(player);
                        } else if (keyCode == 126) {
                            c(player);
                        } else if (keyCode == 127) {
                            this.at.dispatchSetPlayWhenReady(player, false);
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    public final void e(MappingTrackSelector.MappedTrackInfo mappedTrackInfo, int i2, ArrayList arrayList) {
        boolean z2;
        int i3 = i2;
        TrackGroupArray trackGroups = mappedTrackInfo.getTrackGroups(i2);
        TrackSelection trackSelection = ((Player) Assertions.checkNotNull(this.as)).getCurrentTrackSelections().get(i3);
        for (int i4 = 0; i4 < trackGroups.c; i4++) {
            TrackGroup trackGroup = trackGroups.get(i4);
            for (int i5 = 0; i5 < trackGroup.c; i5++) {
                Format format = trackGroup.getFormat(i5);
                if (mappedTrackInfo.getTrackSupport(i3, i4, i5) == 4) {
                    if (trackSelection == null || trackSelection.indexOf(format) == -1) {
                        z2 = false;
                    } else {
                        z2 = true;
                    }
                    String trackName = this.bx.getTrackName(format);
                    arrayList.add(new h(i2, i4, i5, trackName, z2));
                } else {
                    ArrayList arrayList2 = arrayList;
                }
            }
            MappingTrackSelector.MappedTrackInfo mappedTrackInfo2 = mappedTrackInfo;
            ArrayList arrayList3 = arrayList;
        }
    }

    public final void f() {
        i();
        h();
        k();
        m();
        o();
        Player player = this.as;
        if (player != null) {
            float f2 = player.getPlaybackParameters().c;
            c cVar = this.bq;
            cVar.updateSelectedIndex(f2);
            this.bp.setSubTextAtPosition(0, cVar.getSelectedText());
        }
        n();
    }

    public final void g(@Nullable View view, boolean z2) {
        float f2;
        if (view != null) {
            view.setEnabled(z2);
            if (z2) {
                f2 = this.ag;
            } else {
                f2 = this.ah;
            }
            view.setAlpha(f2);
        }
    }

    @Nullable
    public Player getPlayer() {
        return this.as;
    }

    public int getRepeatToggleModes() {
        return this.be;
    }

    public boolean getShowShuffleButton() {
        return this.bm.getShowButton(this.o);
    }

    public boolean getShowSubtitleButton() {
        return this.bm.getShowButton(this.bz);
    }

    public int getShowTimeoutMs() {
        return this.bc;
    }

    public boolean getShowVrButton() {
        return this.bm.getShowButton(this.p);
    }

    /* JADX WARNING: Removed duplicated region for block: B:39:0x007f  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00b4  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00f8  */
    /* JADX WARNING: Removed duplicated region for block: B:62:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void h() {
        /*
            r14 = this;
            boolean r0 = r14.isVisible()
            if (r0 == 0) goto L_0x00fb
            boolean r0 = r14.ay
            if (r0 != 0) goto L_0x000c
            goto L_0x00fb
        L_0x000c:
            com.google.android.exoplayer2.Player r0 = r14.as
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L_0x0072
            com.google.android.exoplayer2.Timeline r3 = r0.getCurrentTimeline()
            boolean r4 = r3.isEmpty()
            if (r4 != 0) goto L_0x0072
            boolean r4 = r0.isPlayingAd()
            if (r4 != 0) goto L_0x0072
            r4 = 4
            boolean r4 = r0.isCommandAvailable(r4)
            int r5 = r0.getCurrentWindowIndex()
            com.google.android.exoplayer2.Timeline$Window r6 = r14.w
            r3.getWindow(r5, r6)
            if (r4 != 0) goto L_0x0042
            boolean r3 = r6.isLive()
            if (r3 == 0) goto L_0x0042
            r3 = 6
            boolean r3 = r0.isCommandAvailable(r3)
            if (r3 == 0) goto L_0x0040
            goto L_0x0042
        L_0x0040:
            r3 = 0
            goto L_0x0043
        L_0x0042:
            r3 = 1
        L_0x0043:
            if (r4 == 0) goto L_0x004f
            com.google.android.exoplayer2.ControlDispatcher r5 = r14.at
            boolean r5 = r5.isRewindEnabled()
            if (r5 == 0) goto L_0x004f
            r5 = 1
            goto L_0x0050
        L_0x004f:
            r5 = 0
        L_0x0050:
            if (r4 == 0) goto L_0x005c
            com.google.android.exoplayer2.ControlDispatcher r7 = r14.at
            boolean r7 = r7.isFastForwardEnabled()
            if (r7 == 0) goto L_0x005c
            r7 = 1
            goto L_0x005d
        L_0x005c:
            r7 = 0
        L_0x005d:
            boolean r8 = r6.isLive()
            if (r8 == 0) goto L_0x0067
            boolean r6 = r6.m
            if (r6 != 0) goto L_0x006e
        L_0x0067:
            r6 = 5
            boolean r0 = r0.isCommandAvailable(r6)
            if (r0 == 0) goto L_0x0070
        L_0x006e:
            r0 = 1
            goto L_0x0077
        L_0x0070:
            r0 = 0
            goto L_0x0077
        L_0x0072:
            r0 = 0
            r3 = 0
            r4 = 0
            r5 = 0
            r7 = 0
        L_0x0077:
            android.content.res.Resources r6 = r14.bn
            android.view.View r8 = r14.k
            r9 = 1000(0x3e8, double:4.94E-321)
            if (r5 == 0) goto L_0x00b0
            com.google.android.exoplayer2.ControlDispatcher r11 = r14.at
            boolean r12 = r11 instanceof com.google.android.exoplayer2.DefaultControlDispatcher
            if (r12 == 0) goto L_0x008d
            com.google.android.exoplayer2.DefaultControlDispatcher r11 = (com.google.android.exoplayer2.DefaultControlDispatcher) r11
            long r11 = r11.getRewindIncrementMs()
            r14.bk = r11
        L_0x008d:
            long r11 = r14.bk
            long r11 = r11 / r9
            int r12 = (int) r11
            android.widget.TextView r11 = r14.m
            if (r11 == 0) goto L_0x009c
            java.lang.String r13 = java.lang.String.valueOf(r12)
            r11.setText(r13)
        L_0x009c:
            if (r8 == 0) goto L_0x00b0
            java.lang.Object[] r11 = new java.lang.Object[r1]
            java.lang.Integer r13 = java.lang.Integer.valueOf(r12)
            r11[r2] = r13
            r13 = 2131623937(0x7f0e0001, float:1.887504E38)
            java.lang.String r11 = r6.getQuantityString(r13, r12, r11)
            r8.setContentDescription(r11)
        L_0x00b0:
            android.view.View r11 = r14.j
            if (r7 == 0) goto L_0x00e4
            com.google.android.exoplayer2.ControlDispatcher r12 = r14.at
            boolean r13 = r12 instanceof com.google.android.exoplayer2.DefaultControlDispatcher
            if (r13 == 0) goto L_0x00c2
            com.google.android.exoplayer2.DefaultControlDispatcher r12 = (com.google.android.exoplayer2.DefaultControlDispatcher) r12
            long r12 = r12.getFastForwardIncrementMs()
            r14.bl = r12
        L_0x00c2:
            long r12 = r14.bl
            long r12 = r12 / r9
            int r9 = (int) r12
            android.widget.TextView r10 = r14.l
            if (r10 == 0) goto L_0x00d1
            java.lang.String r12 = java.lang.String.valueOf(r9)
            r10.setText(r12)
        L_0x00d1:
            if (r11 == 0) goto L_0x00e4
            java.lang.Object[] r1 = new java.lang.Object[r1]
            java.lang.Integer r10 = java.lang.Integer.valueOf(r9)
            r1[r2] = r10
            r2 = 2131623936(0x7f0e0000, float:1.8875038E38)
            java.lang.String r1 = r6.getQuantityString(r2, r9, r1)
            r11.setContentDescription(r1)
        L_0x00e4:
            android.view.View r1 = r14.g
            r14.g(r1, r3)
            r14.g(r8, r5)
            r14.g(r11, r7)
            android.view.View r1 = r14.h
            r14.g(r1, r0)
            com.google.android.exoplayer2.ui.TimeBar r0 = r14.s
            if (r0 == 0) goto L_0x00fb
            r0.setEnabled(r4)
        L_0x00fb:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ui.StyledPlayerControlView.h():void");
    }

    public void hide() {
        this.bm.hide();
    }

    public void hideImmediately() {
        this.bm.hideImmediately();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0029, code lost:
        if (r4.as.getPlayWhenReady() != false) goto L_0x002d;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void i() {
        /*
            r4 = this;
            boolean r0 = r4.isVisible()
            if (r0 == 0) goto L_0x0060
            boolean r0 = r4.ay
            if (r0 != 0) goto L_0x000b
            goto L_0x0060
        L_0x000b:
            android.view.View r0 = r4.i
            if (r0 == 0) goto L_0x0060
            com.google.android.exoplayer2.Player r1 = r4.as
            if (r1 == 0) goto L_0x002c
            int r1 = r1.getPlaybackState()
            r2 = 4
            if (r1 == r2) goto L_0x002c
            com.google.android.exoplayer2.Player r1 = r4.as
            int r1 = r1.getPlaybackState()
            r2 = 1
            if (r1 == r2) goto L_0x002c
            com.google.android.exoplayer2.Player r1 = r4.as
            boolean r1 = r1.getPlayWhenReady()
            if (r1 == 0) goto L_0x002c
            goto L_0x002d
        L_0x002c:
            r2 = 0
        L_0x002d:
            android.content.res.Resources r1 = r4.bn
            if (r2 == 0) goto L_0x0049
            r2 = r0
            android.widget.ImageView r2 = (android.widget.ImageView) r2
            r3 = 2131165343(0x7f07009f, float:1.79449E38)
            android.graphics.drawable.Drawable r3 = r1.getDrawable(r3)
            r2.setImageDrawable(r3)
            r2 = 2131689510(0x7f0f0026, float:1.9008037E38)
            java.lang.String r1 = r1.getString(r2)
            r0.setContentDescription(r1)
            goto L_0x0060
        L_0x0049:
            r2 = r0
            android.widget.ImageView r2 = (android.widget.ImageView) r2
            r3 = 2131165344(0x7f0700a0, float:1.7944902E38)
            android.graphics.drawable.Drawable r3 = r1.getDrawable(r3)
            r2.setImageDrawable(r3)
            r2 = 2131689511(0x7f0f0027, float:1.900804E38)
            java.lang.String r1 = r1.getString(r2)
            r0.setContentDescription(r1)
        L_0x0060:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ui.StyledPlayerControlView.i():void");
    }

    public boolean isAnimationEnabled() {
        return this.bm.isAnimationEnabled();
    }

    public boolean isFullyVisible() {
        return this.bm.isFullyVisible();
    }

    public boolean isVisible() {
        return getVisibility() == 0;
    }

    public final void j() {
        long j2;
        long j3;
        int i2;
        long j4;
        if (isVisible() && this.ay) {
            Player player = this.as;
            if (player != null) {
                j3 = player.getContentPosition() + this.bj;
                j2 = player.getContentBufferedPosition() + this.bj;
            } else {
                j3 = 0;
                j2 = 0;
            }
            TextView textView = this.r;
            if (textView != null && !this.bb) {
                textView.setText(Util.getStringForTime(this.t, this.u, j3));
            }
            TimeBar timeBar = this.s;
            if (timeBar != null) {
                timeBar.setPosition(j3);
                timeBar.setBufferedPosition(j2);
            }
            ProgressUpdateListener progressUpdateListener = this.au;
            if (progressUpdateListener != null) {
                progressUpdateListener.onProgressUpdate(j3, j2);
            }
            qb qbVar = this.x;
            removeCallbacks(qbVar);
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
                postDelayed(qbVar, Util.constrainValue(j5, (long) this.bd, 1000));
            } else if (i2 != 4 && i2 != 1) {
                postDelayed(qbVar, 1000);
            }
        }
    }

    public final void k() {
        ImageView imageView;
        if (isVisible() && this.ay && (imageView = this.n) != null) {
            if (this.be == 0) {
                g(imageView, false);
                return;
            }
            Player player = this.as;
            String str = this.ab;
            Drawable drawable = this.y;
            if (player == null) {
                g(imageView, false);
                imageView.setImageDrawable(drawable);
                imageView.setContentDescription(str);
                return;
            }
            g(imageView, true);
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
        }
    }

    public final void l() {
        RecyclerView recyclerView = this.bo;
        recyclerView.measure(0, 0);
        int width = getWidth();
        int i2 = this.bt;
        int min = Math.min(recyclerView.getMeasuredWidth(), width - (i2 * 2));
        PopupWindow popupWindow = this.br;
        popupWindow.setWidth(min);
        popupWindow.setHeight(Math.min(getHeight() - (i2 * 2), recyclerView.getMeasuredHeight()));
    }

    public final void m() {
        ImageView imageView;
        if (isVisible() && this.ay && (imageView = this.o) != null) {
            Player player = this.as;
            if (!this.bm.getShowButton(imageView)) {
                g(imageView, false);
                return;
            }
            String str = this.aj;
            Drawable drawable = this.af;
            if (player == null) {
                g(imageView, false);
                imageView.setImageDrawable(drawable);
                imageView.setContentDescription(str);
                return;
            }
            g(imageView, true);
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

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0039  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x004c  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x0108  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0113  */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x0122  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void n() {
        /*
            r24 = this;
            r0 = r24
            com.google.android.exoplayer2.Player r1 = r0.as
            if (r1 != 0) goto L_0x0007
            return
        L_0x0007:
            boolean r2 = r0.az
            r3 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            com.google.android.exoplayer2.Timeline$Window r5 = r0.w
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
            r0.ba = r2
            r8 = 0
            r0.bj = r8
            com.google.android.exoplayer2.Timeline r2 = r1.getCurrentTimeline()
            boolean r10 = r2.isEmpty()
            if (r10 != 0) goto L_0x0108
            int r1 = r1.getCurrentWindowIndex()
            boolean r10 = r0.ba
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
            r0.bj = r8
        L_0x006c:
            r2.getWindow(r11, r5)
            long r8 = r5.r
            int r17 = (r8 > r3 ? 1 : (r8 == r3 ? 0 : -1))
            if (r17 != 0) goto L_0x007d
            boolean r1 = r0.ba
            r1 = r1 ^ r7
            com.google.android.exoplayer2.util.Assertions.checkState(r1)
            goto L_0x0106
        L_0x007d:
            int r8 = r5.s
        L_0x007f:
            int r9 = r5.t
            if (r8 > r9) goto L_0x00f5
            com.google.android.exoplayer2.Timeline$Period r9 = r0.v
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
            long[] r3 = r0.bf
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
            r0.bf = r3
            boolean[] r3 = r0.bg
            boolean[] r3 = java.util.Arrays.copyOf(r3, r4)
            r0.bg = r3
        L_0x00cd:
            long[] r3 = r0.bf
            long r6 = r6 + r12
            long r6 = com.google.android.exoplayer2.C.usToMs(r6)
            r3[r14] = r6
            boolean[] r3 = r0.bg
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
            android.widget.TextView r3 = r0.q
            if (r3 == 0) goto L_0x011e
            java.lang.StringBuilder r4 = r0.t
            java.util.Formatter r5 = r0.u
            java.lang.String r4 = com.google.android.exoplayer2.util.Util.getStringForTime(r4, r5, r1)
            r3.setText(r4)
        L_0x011e:
            com.google.android.exoplayer2.ui.TimeBar r3 = r0.s
            if (r3 == 0) goto L_0x0153
            r3.setDuration(r1)
            long[] r1 = r0.bh
            int r1 = r1.length
            int r2 = r14 + r1
            long[] r4 = r0.bf
            int r5 = r4.length
            if (r2 <= r5) goto L_0x013d
            long[] r4 = java.util.Arrays.copyOf(r4, r2)
            r0.bf = r4
            boolean[] r4 = r0.bg
            boolean[] r4 = java.util.Arrays.copyOf(r4, r2)
            r0.bg = r4
        L_0x013d:
            long[] r4 = r0.bh
            long[] r5 = r0.bf
            r6 = 0
            java.lang.System.arraycopy(r4, r6, r5, r14, r1)
            boolean[] r4 = r0.bi
            boolean[] r5 = r0.bg
            java.lang.System.arraycopy(r4, r6, r5, r14, r1)
            long[] r1 = r0.bf
            boolean[] r4 = r0.bg
            r3.setAdGroupTimesMs(r1, r4, r2)
        L_0x0153:
            r24.j()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ui.StyledPlayerControlView.n():void");
    }

    public final void o() {
        DefaultTrackSelector defaultTrackSelector;
        MappingTrackSelector.MappedTrackInfo currentMappedTrackInfo;
        g gVar = this.bv;
        gVar.clear();
        a aVar = this.bw;
        aVar.clear();
        Player player = this.as;
        ImageView imageView = this.bz;
        boolean z2 = false;
        if (!(player == null || (defaultTrackSelector = this.bu) == null || (currentMappedTrackInfo = defaultTrackSelector.getCurrentMappedTrackInfo()) == null)) {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = new ArrayList();
            ArrayList arrayList4 = new ArrayList();
            for (int i2 = 0; i2 < currentMappedTrackInfo.getRendererCount(); i2++) {
                if (currentMappedTrackInfo.getRendererType(i2) == 3 && this.bm.getShowButton(imageView)) {
                    e(currentMappedTrackInfo, i2, arrayList);
                    arrayList3.add(Integer.valueOf(i2));
                } else if (currentMappedTrackInfo.getRendererType(i2) == 1) {
                    e(currentMappedTrackInfo, i2, arrayList2);
                    arrayList4.add(Integer.valueOf(i2));
                }
            }
            gVar.init(arrayList3, arrayList, currentMappedTrackInfo);
            aVar.init(arrayList4, arrayList2, currentMappedTrackInfo);
        }
        if (gVar.getItemCount() > 0) {
            z2 = true;
        }
        g(imageView, z2);
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        b bVar = this.bm;
        bVar.onAttachedToWindow();
        this.ay = true;
        if (isFullyVisible()) {
            bVar.resetHideCallbacks();
        }
        f();
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        b bVar = this.bm;
        bVar.onDetachedFromWindow();
        this.ay = false;
        removeCallbacks(this.x);
        bVar.removeHideCallbacks();
    }

    public final void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        super.onLayout(z2, i2, i3, i4, i5);
        this.bm.onLayout(z2, i2, i3, i4, i5);
    }

    public void removeVisibilityListener(VisibilityListener visibilityListener) {
        this.f.remove(visibilityListener);
    }

    public void setAnimationEnabled(boolean z2) {
        this.bm.setAnimationEnabled(z2);
    }

    public void setControlDispatcher(ControlDispatcher controlDispatcher) {
        if (this.at != controlDispatcher) {
            this.at = controlDispatcher;
            h();
        }
    }

    public void setExtraAdGroupMarkers(@Nullable long[] jArr, @Nullable boolean[] zArr) {
        boolean z2 = false;
        if (jArr == null) {
            this.bh = new long[0];
            this.bi = new boolean[0];
        } else {
            boolean[] zArr2 = (boolean[]) Assertions.checkNotNull(zArr);
            if (jArr.length == zArr2.length) {
                z2 = true;
            }
            Assertions.checkArgument(z2);
            this.bh = jArr;
            this.bi = zArr2;
        }
        n();
    }

    public void setOnFullScreenModeChangedListener(@Nullable OnFullScreenModeChangedListener onFullScreenModeChangedListener) {
        boolean z2;
        this.aw = onFullScreenModeChangedListener;
        boolean z3 = true;
        if (onFullScreenModeChangedListener != null) {
            z2 = true;
        } else {
            z2 = false;
        }
        ImageView imageView = this.ca;
        if (imageView != null) {
            if (z2) {
                imageView.setVisibility(0);
            } else {
                imageView.setVisibility(8);
            }
        }
        if (onFullScreenModeChangedListener == null) {
            z3 = false;
        }
        ImageView imageView2 = this.cb;
        if (imageView2 != null) {
            if (z3) {
                imageView2.setVisibility(0);
            } else {
                imageView2.setVisibility(8);
            }
        }
    }

    @Deprecated
    public void setPlaybackPreparer(@Nullable PlaybackPreparer playbackPreparer) {
        this.av = playbackPreparer;
    }

    public void setPlayer(@Nullable Player player) {
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
        Player player2 = this.as;
        if (player2 != player) {
            b bVar = this.c;
            if (player2 != null) {
                player2.removeListener((Player.EventListener) bVar);
            }
            this.as = player;
            if (player != null) {
                player.addListener((Player.EventListener) bVar);
            }
            if (player instanceof ExoPlayer) {
                TrackSelector trackSelector = ((ExoPlayer) player).getTrackSelector();
                if (trackSelector instanceof DefaultTrackSelector) {
                    this.bu = (DefaultTrackSelector) trackSelector;
                }
            } else {
                this.bu = null;
            }
            f();
        }
    }

    public void setProgressUpdateListener(@Nullable ProgressUpdateListener progressUpdateListener) {
        this.au = progressUpdateListener;
    }

    public void setRepeatToggleModes(int i2) {
        this.be = i2;
        Player player = this.as;
        boolean z2 = false;
        if (player != null) {
            int repeatMode = player.getRepeatMode();
            if (i2 == 0 && repeatMode != 0) {
                this.at.dispatchSetRepeatMode(this.as, 0);
            } else if (i2 == 1 && repeatMode == 2) {
                this.at.dispatchSetRepeatMode(this.as, 1);
            } else if (i2 == 2 && repeatMode == 1) {
                this.at.dispatchSetRepeatMode(this.as, 2);
            }
        }
        if (i2 != 0) {
            z2 = true;
        }
        this.bm.setShowButton(this.n, z2);
        k();
    }

    public void setShowFastForwardButton(boolean z2) {
        this.bm.setShowButton(this.j, z2);
        h();
    }

    public void setShowMultiWindowTimeBar(boolean z2) {
        this.az = z2;
        n();
    }

    public void setShowNextButton(boolean z2) {
        this.bm.setShowButton(this.h, z2);
        h();
    }

    public void setShowPreviousButton(boolean z2) {
        this.bm.setShowButton(this.g, z2);
        h();
    }

    public void setShowRewindButton(boolean z2) {
        this.bm.setShowButton(this.k, z2);
        h();
    }

    public void setShowShuffleButton(boolean z2) {
        this.bm.setShowButton(this.o, z2);
        m();
    }

    public void setShowSubtitleButton(boolean z2) {
        this.bm.setShowButton(this.bz, z2);
    }

    public void setShowTimeoutMs(int i2) {
        this.bc = i2;
        if (isFullyVisible()) {
            this.bm.resetHideCallbacks();
        }
    }

    public void setShowVrButton(boolean z2) {
        this.bm.setShowButton(this.p, z2);
    }

    public void setTimeBarMinUpdateInterval(int i2) {
        this.bd = Util.constrainValue(i2, 16, 1000);
    }

    public void setVrButtonListener(@Nullable View.OnClickListener onClickListener) {
        boolean z2;
        View view = this.p;
        if (view != null) {
            view.setOnClickListener(onClickListener);
            if (onClickListener != null) {
                z2 = true;
            } else {
                z2 = false;
            }
            g(view, z2);
        }
    }

    public void show() {
        this.bm.show();
    }

    public StyledPlayerControlView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public StyledPlayerControlView(Context context, @Nullable AttributeSet attributeSet, int i2) {
        this(context, attributeSet, i2, attributeSet);
    }

    /* JADX INFO: finally extract failed */
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public StyledPlayerControlView(Context context, @Nullable AttributeSet attributeSet, int i2, @Nullable AttributeSet attributeSet2) {
        super(context, attributeSet, i2);
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        boolean z7;
        boolean z8;
        boolean z9;
        boolean z10;
        boolean z11;
        b bVar;
        boolean z12;
        AttributeSet attributeSet3 = attributeSet2;
        this.bk = 5000;
        this.bl = 15000;
        this.bc = 5000;
        this.be = 0;
        this.bd = 200;
        int i3 = R.layout.exo_styled_player_control_view;
        if (attributeSet3 != null) {
            TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet3, R.styleable.e, 0, 0);
            try {
                this.bk = (long) obtainStyledAttributes.getInt(11, (int) this.bk);
                this.bl = (long) obtainStyledAttributes.getInt(7, (int) this.bl);
                i3 = obtainStyledAttributes.getResourceId(6, R.layout.exo_styled_player_control_view);
                this.bc = obtainStyledAttributes.getInt(23, this.bc);
                this.be = obtainStyledAttributes.getInt(10, this.be);
                boolean z13 = obtainStyledAttributes.getBoolean(20, true);
                boolean z14 = obtainStyledAttributes.getBoolean(17, true);
                boolean z15 = obtainStyledAttributes.getBoolean(19, true);
                boolean z16 = obtainStyledAttributes.getBoolean(18, true);
                boolean z17 = obtainStyledAttributes.getBoolean(21, false);
                boolean z18 = obtainStyledAttributes.getBoolean(22, false);
                boolean z19 = obtainStyledAttributes.getBoolean(24, false);
                setTimeBarMinUpdateInterval(obtainStyledAttributes.getInt(25, this.bd));
                boolean z20 = obtainStyledAttributes.getBoolean(2, true);
                obtainStyledAttributes.recycle();
                z6 = z20;
                boolean z21 = z16;
                z5 = z13;
                z9 = z19;
                z2 = z21;
                boolean z22 = z17;
                z4 = z14;
                z8 = z18;
                z3 = z15;
                z7 = z22;
            } catch (Throwable th) {
                obtainStyledAttributes.recycle();
                throw th;
            }
        } else {
            z9 = false;
            z8 = false;
            z7 = false;
            z6 = true;
            z5 = true;
            z4 = true;
            z3 = true;
            z2 = true;
        }
        LayoutInflater.from(context).inflate(i3, this);
        setDescendantFocusability(262144);
        b bVar2 = new b();
        this.c = bVar2;
        this.f = new CopyOnWriteArrayList<>();
        this.v = new Timeline.Period();
        this.w = new Timeline.Window();
        StringBuilder sb = new StringBuilder();
        this.t = sb;
        this.u = new Formatter(sb, Locale.getDefault());
        this.bf = new long[0];
        this.bg = new boolean[0];
        this.bh = new long[0];
        this.bi = new boolean[0];
        boolean z23 = z5;
        boolean z24 = z6;
        this.at = new DefaultControlDispatcher(this.bl, this.bk);
        this.x = new qb(7, this);
        this.q = (TextView) findViewById(R.id.exo_duration);
        this.r = (TextView) findViewById(R.id.exo_position);
        ImageView imageView = (ImageView) findViewById(R.id.exo_subtitle);
        this.bz = imageView;
        if (imageView != null) {
            imageView.setOnClickListener(bVar2);
        }
        ImageView imageView2 = (ImageView) findViewById(R.id.exo_fullscreen);
        this.ca = imageView2;
        sb sbVar = new sb(this, 0);
        if (imageView2 != null) {
            imageView2.setVisibility(8);
            imageView2.setOnClickListener(sbVar);
        }
        ImageView imageView3 = (ImageView) findViewById(R.id.exo_minimal_fullscreen);
        this.cb = imageView3;
        sb sbVar2 = new sb(this, 1);
        if (imageView3 != null) {
            imageView3.setVisibility(8);
            imageView3.setOnClickListener(sbVar2);
        }
        View findViewById = findViewById(R.id.exo_settings);
        this.cc = findViewById;
        if (findViewById != null) {
            findViewById.setOnClickListener(bVar2);
        }
        View findViewById2 = findViewById(R.id.exo_playback_speed);
        this.cd = findViewById2;
        if (findViewById2 != null) {
            findViewById2.setOnClickListener(bVar2);
        }
        View findViewById3 = findViewById(R.id.exo_audio_track);
        this.ce = findViewById3;
        if (findViewById3 != null) {
            findViewById3.setOnClickListener(bVar2);
        }
        TimeBar timeBar = (TimeBar) findViewById(R.id.exo_progress);
        View findViewById4 = findViewById(R.id.exo_progress_placeholder);
        if (timeBar != null) {
            this.s = timeBar;
            bVar = bVar2;
            z11 = z9;
            z10 = z8;
            z12 = z7;
        } else if (findViewById4 != null) {
            DefaultTimeBar defaultTimeBar = r2;
            bVar = bVar2;
            z11 = z9;
            z10 = z8;
            z12 = z7;
            DefaultTimeBar defaultTimeBar2 = new DefaultTimeBar(context, (AttributeSet) null, 0, attributeSet2, 2131755197);
            defaultTimeBar.setId(R.id.exo_progress);
            defaultTimeBar.setLayoutParams(findViewById4.getLayoutParams());
            ViewGroup viewGroup = (ViewGroup) findViewById4.getParent();
            int indexOfChild = viewGroup.indexOfChild(findViewById4);
            viewGroup.removeView(findViewById4);
            viewGroup.addView(defaultTimeBar, indexOfChild);
            this.s = defaultTimeBar;
        } else {
            bVar = bVar2;
            z11 = z9;
            z10 = z8;
            z12 = z7;
            this.s = null;
        }
        TimeBar timeBar2 = this.s;
        b bVar3 = bVar;
        if (timeBar2 != null) {
            timeBar2.addListener(bVar3);
        }
        View findViewById5 = findViewById(R.id.exo_play_pause);
        this.i = findViewById5;
        if (findViewById5 != null) {
            findViewById5.setOnClickListener(bVar3);
        }
        View findViewById6 = findViewById(R.id.exo_prev);
        this.g = findViewById6;
        if (findViewById6 != null) {
            findViewById6.setOnClickListener(bVar3);
        }
        View findViewById7 = findViewById(R.id.exo_next);
        this.h = findViewById7;
        if (findViewById7 != null) {
            findViewById7.setOnClickListener(bVar3);
        }
        Typeface font = ResourcesCompat.getFont(context, R.font.roboto_medium_numbers);
        View findViewById8 = findViewById(R.id.exo_rew);
        TextView textView = findViewById8 == null ? (TextView) findViewById(R.id.exo_rew_with_amount) : null;
        this.m = textView;
        if (textView != null) {
            textView.setTypeface(font);
        }
        findViewById8 = findViewById8 == null ? textView : findViewById8;
        this.k = findViewById8;
        if (findViewById8 != null) {
            findViewById8.setOnClickListener(bVar3);
        }
        View findViewById9 = findViewById(R.id.exo_ffwd);
        TextView textView2 = findViewById9 == null ? (TextView) findViewById(R.id.exo_ffwd_with_amount) : null;
        this.l = textView2;
        if (textView2 != null) {
            textView2.setTypeface(font);
        }
        findViewById9 = findViewById9 == null ? textView2 : findViewById9;
        this.j = findViewById9;
        if (findViewById9 != null) {
            findViewById9.setOnClickListener(bVar3);
        }
        ImageView imageView4 = (ImageView) findViewById(R.id.exo_repeat_toggle);
        this.n = imageView4;
        if (imageView4 != null) {
            imageView4.setOnClickListener(bVar3);
        }
        ImageView imageView5 = (ImageView) findViewById(R.id.exo_shuffle);
        this.o = imageView5;
        if (imageView5 != null) {
            imageView5.setOnClickListener(bVar3);
        }
        Resources resources = context.getResources();
        this.bn = resources;
        this.ag = ((float) resources.getInteger(R.integer.exo_media_button_opacity_percentage_enabled)) / 100.0f;
        this.ah = ((float) resources.getInteger(R.integer.exo_media_button_opacity_percentage_disabled)) / 100.0f;
        View findViewById10 = findViewById(R.id.exo_vr);
        this.p = findViewById10;
        if (findViewById10 != null) {
            g(findViewById10, false);
        }
        b bVar4 = new b(this);
        this.bm = bVar4;
        ImageView imageView6 = imageView4;
        bVar4.setAnimationEnabled(z24);
        View view = findViewById10;
        ImageView imageView7 = imageView;
        Resources resources2 = resources;
        e eVar = new e(new String[]{resources.getString(R.string.exo_controls_playback_speed), resources.getString(R.string.exo_track_selection_title_audio)}, new Drawable[]{resources.getDrawable(R.drawable.exo_styled_controls_speed), resources.getDrawable(R.drawable.exo_styled_controls_audiotrack)});
        this.bp = eVar;
        this.bt = resources2.getDimensionPixelSize(R.dimen.exo_settings_offset);
        boolean z25 = z12;
        RecyclerView recyclerView = (RecyclerView) LayoutInflater.from(context).inflate(R.layout.exo_styled_settings_list, (ViewGroup) null);
        this.bo = recyclerView;
        recyclerView.setAdapter(eVar);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        PopupWindow popupWindow = new PopupWindow(recyclerView, -2, -2, true);
        this.br = popupWindow;
        if (Util.a < 23) {
            popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        }
        popupWindow.setOnDismissListener(bVar3);
        this.bs = true;
        this.bx = new DefaultTrackNameProvider(getResources());
        this.ak = resources2.getDrawable(R.drawable.exo_styled_controls_subtitle_on);
        this.al = resources2.getDrawable(R.drawable.exo_styled_controls_subtitle_off);
        this.am = resources2.getString(R.string.exo_controls_cc_enabled_description);
        this.an = resources2.getString(R.string.exo_controls_cc_disabled_description);
        this.bv = new g();
        this.bw = new a();
        this.bq = new c(resources2.getStringArray(R.array.exo_playback_speeds), resources2.getIntArray(R.array.exo_speed_multiplied_by_100));
        this.ao = resources2.getDrawable(R.drawable.exo_styled_controls_fullscreen_exit);
        this.ap = resources2.getDrawable(R.drawable.exo_styled_controls_fullscreen_enter);
        this.y = resources2.getDrawable(R.drawable.exo_styled_controls_repeat_off);
        this.z = resources2.getDrawable(R.drawable.exo_styled_controls_repeat_one);
        this.aa = resources2.getDrawable(R.drawable.exo_styled_controls_repeat_all);
        this.ae = resources2.getDrawable(R.drawable.exo_styled_controls_shuffle_on);
        this.af = resources2.getDrawable(R.drawable.exo_styled_controls_shuffle_off);
        this.aq = resources2.getString(R.string.exo_controls_fullscreen_exit_description);
        this.ar = resources2.getString(R.string.exo_controls_fullscreen_enter_description);
        this.ab = resources2.getString(R.string.exo_controls_repeat_off_description);
        this.ac = resources2.getString(R.string.exo_controls_repeat_one_description);
        this.ad = resources2.getString(R.string.exo_controls_repeat_all_description);
        this.ai = resources2.getString(R.string.exo_controls_shuffle_on_description);
        this.aj = resources2.getString(R.string.exo_controls_shuffle_off_description);
        bVar4.setShowButton((ViewGroup) findViewById(R.id.exo_bottom_bar), true);
        bVar4.setShowButton(findViewById9, z4);
        bVar4.setShowButton(findViewById8, z23);
        bVar4.setShowButton(findViewById6, z3);
        bVar4.setShowButton(findViewById7, z2);
        bVar4.setShowButton(imageView5, z25);
        bVar4.setShowButton(imageView7, z10);
        bVar4.setShowButton(view, z11);
        bVar4.setShowButton(imageView6, this.be != 0);
        addOnLayoutChangeListener(new tb(0, this));
    }
}
