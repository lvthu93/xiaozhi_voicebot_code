package defpackage;

import android.net.Uri;
import android.os.Bundle;
import com.google.android.exoplayer2.Bundleable;
import com.google.android.exoplayer2.HeartRating;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.PercentageRating;
import com.google.android.exoplayer2.Rating;
import com.google.android.exoplayer2.StarRating;
import com.google.android.exoplayer2.ThumbRating;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.device.DeviceInfo;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor;
import com.google.android.exoplayer2.metadata.id3.Id3Decoder;
import com.google.android.exoplayer2.source.ads.AdPlaybackState;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Consumer;
import com.google.android.exoplayer2.util.ExoFlags;
import com.google.android.exoplayer2.util.ListenerSet;
import java.util.Map;

/* renamed from: f2  reason: default package */
public final /* synthetic */ class f2 implements Bundleable.Creator, ListenerSet.IterationFinishedEvent, Consumer, DrmSessionManager.DrmSessionReference, ExtractorsFactory, Id3Decoder.FramePredicate {
    public final /* synthetic */ int c;

    public /* synthetic */ f2(int i) {
        this.c = i;
    }

    public final void accept(Object obj) {
        ((DrmSessionEventListener.EventDispatcher) obj).drmKeysRestored();
    }

    public final Extractor[] createExtractors() {
        switch (this.c) {
            case 22:
                return new Extractor[0];
            default:
                return new Extractor[]{new FragmentedMp4Extractor()};
        }
    }

    public final /* synthetic */ Extractor[] createExtractors(Uri uri, Map map) {
        switch (this.c) {
            case 22:
                return w2.a(this, uri, map);
            default:
                return w2.a(this, uri, map);
        }
    }

    public final boolean evaluate(int i, int i2, int i3, int i4, int i5) {
        return (i2 == 67 && i3 == 79 && i4 == 77 && (i5 == 77 || i == 2)) || (i2 == 77 && i3 == 76 && i4 == 76 && (i5 == 84 || i == 2));
    }

    public final Bundleable fromBundle(Bundle bundle) {
        boolean z;
        MediaItem.LiveConfiguration liveConfiguration;
        MediaMetadata mediaMetadata;
        MediaItem.ClippingProperties clippingProperties;
        Bundle bundle2;
        Bundle bundle3;
        boolean z2;
        AdPlaybackState adPlaybackState;
        MediaItem mediaItem;
        MediaItem.LiveConfiguration liveConfiguration2;
        Bundle bundle4 = bundle;
        boolean z3 = false;
        switch (this.c) {
            case 1:
                if (bundle4.getInt(HeartRating.a(0), -1) == 0) {
                    z = true;
                } else {
                    z = false;
                }
                Assertions.checkArgument(z);
                if (bundle4.getBoolean(HeartRating.a(1), false)) {
                    return new HeartRating(bundle4.getBoolean(HeartRating.a(2), false));
                }
                return new HeartRating();
            case 2:
                String str = (String) Assertions.checkNotNull(bundle4.getString(MediaItem.a(0), ""));
                Bundle bundle5 = bundle4.getBundle(MediaItem.a(1));
                if (bundle5 == null) {
                    liveConfiguration = MediaItem.LiveConfiguration.j;
                } else {
                    liveConfiguration = (MediaItem.LiveConfiguration) MediaItem.LiveConfiguration.k.fromBundle(bundle5);
                }
                MediaItem.LiveConfiguration liveConfiguration3 = liveConfiguration;
                Bundle bundle6 = bundle4.getBundle(MediaItem.a(2));
                if (bundle6 == null) {
                    mediaMetadata = MediaMetadata.w;
                } else {
                    mediaMetadata = (MediaMetadata) MediaMetadata.x.fromBundle(bundle6);
                }
                MediaMetadata mediaMetadata2 = mediaMetadata;
                Bundle bundle7 = bundle4.getBundle(MediaItem.a(3));
                if (bundle7 == null) {
                    clippingProperties = new MediaItem.ClippingProperties(0, Long.MIN_VALUE, false, false, false);
                } else {
                    try {
                        clippingProperties = (MediaItem.ClippingProperties) MediaItem.ClippingProperties.j.fromBundle(bundle7);
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                return new MediaItem(str, clippingProperties, (MediaItem.PlaybackProperties) null, liveConfiguration3, mediaMetadata2);
            case 3:
                return new MediaItem.ClippingProperties(bundle4.getLong(MediaItem.ClippingProperties.a(0), 0), bundle4.getLong(MediaItem.ClippingProperties.a(1), Long.MIN_VALUE), bundle4.getBoolean(MediaItem.ClippingProperties.a(2), false), bundle4.getBoolean(MediaItem.ClippingProperties.a(3), false), bundle4.getBoolean(MediaItem.ClippingProperties.a(4), false));
            case 4:
                return new MediaItem.LiveConfiguration(bundle4.getLong(MediaItem.LiveConfiguration.a(0), -9223372036854775807L), bundle4.getLong(MediaItem.LiveConfiguration.a(1), -9223372036854775807L), bundle4.getLong(MediaItem.LiveConfiguration.a(2), -9223372036854775807L), bundle4.getFloat(MediaItem.LiveConfiguration.a(3), -3.4028235E38f), bundle4.getFloat(MediaItem.LiveConfiguration.a(4), -3.4028235E38f));
            case 5:
                MediaMetadata mediaMetadata3 = MediaMetadata.w;
                MediaMetadata.Builder builder = new MediaMetadata.Builder();
                builder.setTitle(bundle4.getCharSequence(MediaMetadata.a(0))).setArtist(bundle4.getCharSequence(MediaMetadata.a(1))).setAlbumTitle(bundle4.getCharSequence(MediaMetadata.a(2))).setAlbumArtist(bundle4.getCharSequence(MediaMetadata.a(3))).setDisplayTitle(bundle4.getCharSequence(MediaMetadata.a(4))).setSubtitle(bundle4.getCharSequence(MediaMetadata.a(5))).setDescription(bundle4.getCharSequence(MediaMetadata.a(6))).setMediaUri((Uri) bundle4.getParcelable(MediaMetadata.a(7))).setArtworkData(bundle4.getByteArray(MediaMetadata.a(10))).setArtworkUri((Uri) bundle4.getParcelable(MediaMetadata.a(11))).setExtras(bundle4.getBundle(MediaMetadata.a(1000)));
                boolean containsKey = bundle4.containsKey(MediaMetadata.a(8));
                f2 f2Var = Rating.c;
                if (containsKey && (bundle3 = bundle4.getBundle(MediaMetadata.a(8))) != null) {
                    builder.setUserRating((Rating) f2Var.fromBundle(bundle3));
                }
                if (bundle4.containsKey(MediaMetadata.a(9)) && (bundle2 = bundle4.getBundle(MediaMetadata.a(9))) != null) {
                    builder.setOverallRating((Rating) f2Var.fromBundle(bundle2));
                }
                if (bundle4.containsKey(MediaMetadata.a(12))) {
                    builder.setTrackNumber(Integer.valueOf(bundle4.getInt(MediaMetadata.a(12))));
                }
                if (bundle4.containsKey(MediaMetadata.a(13))) {
                    builder.setTotalTrackCount(Integer.valueOf(bundle4.getInt(MediaMetadata.a(13))));
                }
                if (bundle4.containsKey(MediaMetadata.a(14))) {
                    builder.setFolderType(Integer.valueOf(bundle4.getInt(MediaMetadata.a(14))));
                }
                if (bundle4.containsKey(MediaMetadata.a(15))) {
                    builder.setIsPlayable(Boolean.valueOf(bundle4.getBoolean(MediaMetadata.a(15))));
                }
                if (bundle4.containsKey(MediaMetadata.a(16))) {
                    builder.setYear(Integer.valueOf(bundle4.getInt(MediaMetadata.a(16))));
                }
                return builder.build();
            case 6:
                if (bundle4.getInt(Integer.toString(0, 36), -1) == 1) {
                    z3 = true;
                }
                Assertions.checkArgument(z3);
                float f = bundle4.getFloat(Integer.toString(1, 36), -1.0f);
                if (f == -1.0f) {
                    return new PercentageRating();
                }
                return new PercentageRating(f);
            case 9:
                int i = bundle4.getInt(Integer.toString(0, 36), -1);
                if (i == 0) {
                    return (Rating) HeartRating.h.fromBundle(bundle4);
                }
                if (i == 1) {
                    return (Rating) PercentageRating.g.fromBundle(bundle4);
                }
                if (i == 2) {
                    return (Rating) StarRating.h.fromBundle(bundle4);
                }
                if (i == 3) {
                    return (Rating) ThumbRating.h.fromBundle(bundle4);
                }
                throw new IllegalArgumentException(y2.d(44, "Encountered unknown rating type: ", i));
            case 10:
                if (bundle4.getInt(StarRating.a(0), -1) == 2) {
                    z3 = true;
                }
                Assertions.checkArgument(z3);
                int i2 = bundle4.getInt(StarRating.a(1), 5);
                float f2 = bundle4.getFloat(StarRating.a(2), -1.0f);
                if (f2 == -1.0f) {
                    return new StarRating(i2);
                }
                return new StarRating(i2, f2);
            case 11:
                if (bundle4.getInt(ThumbRating.a(0), -1) == 3) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                Assertions.checkArgument(z2);
                if (bundle4.getBoolean(ThumbRating.a(1), false)) {
                    return new ThumbRating(bundle4.getBoolean(ThumbRating.a(2), false));
                }
                return new ThumbRating();
            case 13:
                int i3 = bundle4.getInt(Timeline.Period.a(0), 0);
                long j = bundle4.getLong(Timeline.Period.a(1), -9223372036854775807L);
                long j2 = bundle4.getLong(Timeline.Period.a(2), 0);
                boolean z4 = bundle4.getBoolean(Timeline.Period.a(3));
                Bundle bundle8 = bundle4.getBundle(Timeline.Period.a(4));
                if (bundle8 != null) {
                    adPlaybackState = (AdPlaybackState) AdPlaybackState.l.fromBundle(bundle8);
                } else {
                    adPlaybackState = AdPlaybackState.k;
                }
                AdPlaybackState adPlaybackState2 = adPlaybackState;
                Timeline.Period period = new Timeline.Period();
                period.set((Object) null, (Object) null, i3, j, j2, adPlaybackState2, z4);
                return period;
            case 14:
                Bundle bundle9 = bundle4.getBundle(Timeline.Window.a(1));
                if (bundle9 != null) {
                    mediaItem = (MediaItem) MediaItem.j.fromBundle(bundle9);
                } else {
                    mediaItem = null;
                }
                MediaItem mediaItem2 = mediaItem;
                long j3 = bundle4.getLong(Timeline.Window.a(2), -9223372036854775807L);
                long j4 = bundle4.getLong(Timeline.Window.a(3), -9223372036854775807L);
                long j5 = bundle4.getLong(Timeline.Window.a(4), -9223372036854775807L);
                boolean z5 = bundle4.getBoolean(Timeline.Window.a(5), false);
                boolean z6 = bundle4.getBoolean(Timeline.Window.a(6), false);
                Bundle bundle10 = bundle4.getBundle(Timeline.Window.a(7));
                if (bundle10 != null) {
                    liveConfiguration2 = (MediaItem.LiveConfiguration) MediaItem.LiveConfiguration.k.fromBundle(bundle10);
                } else {
                    liveConfiguration2 = null;
                }
                MediaItem.LiveConfiguration liveConfiguration4 = liveConfiguration2;
                boolean z7 = bundle4.getBoolean(Timeline.Window.a(8), false);
                long j6 = bundle4.getLong(Timeline.Window.a(9), 0);
                long j7 = bundle4.getLong(Timeline.Window.a(10), -9223372036854775807L);
                int i4 = bundle4.getInt(Timeline.Window.a(11), 0);
                int i5 = bundle4.getInt(Timeline.Window.a(12), 0);
                long j8 = bundle4.getLong(Timeline.Window.a(13), 0);
                Timeline.Window window = r0;
                Timeline.Window window2 = new Timeline.Window();
                window.set(Timeline.Window.w, mediaItem2, (Object) null, j3, j4, j5, z5, z6, liveConfiguration4, j6, j7, i4, i5, j8);
                window2.p = z7;
                return window2;
            default:
                return new DeviceInfo(bundle4.getInt(DeviceInfo.a(0), 0), bundle4.getInt(DeviceInfo.a(1), 0), bundle4.getInt(DeviceInfo.a(2), 0));
        }
    }

    public final void invoke(Object obj, ExoFlags exoFlags) {
        AnalyticsListener analyticsListener = (AnalyticsListener) obj;
    }

    public final void release() {
    }
}
