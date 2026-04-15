package defpackage;

import android.net.Uri;
import android.os.Bundle;
import com.google.android.exoplayer2.Bundleable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.wav.WavExtractor;
import com.google.android.exoplayer2.mediacodec.MediaCodecInfo;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil;
import com.google.android.exoplayer2.metadata.id3.Id3Decoder;
import com.google.android.exoplayer2.offline.DownloadHelper;
import com.google.android.exoplayer2.source.MediaParserExtractorAdapter;
import com.google.android.exoplayer2.source.ProgressiveMediaExtractor;
import com.google.android.exoplayer2.source.SampleQueue;
import com.google.android.exoplayer2.source.ads.AdPlaybackState;
import com.google.android.exoplayer2.source.chunk.ChunkExtractor;
import com.google.android.exoplayer2.source.chunk.MediaParserChunkExtractor;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.cache.CacheKeyFactory;
import com.google.android.exoplayer2.util.Consumer;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoSize;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/* renamed from: z6  reason: default package */
public final /* synthetic */ class z6 implements ExtractorsFactory, MediaCodecUtil.e, Id3Decoder.FramePredicate, TrackSelector.InvalidationListener, ProgressiveMediaExtractor.Factory, Consumer, Bundleable.Creator, ChunkExtractor.Factory, CacheKeyFactory {
    public final /* synthetic */ int c;

    public /* synthetic */ z6(int i) {
        this.c = i;
    }

    public final void accept(Object obj) {
        switch (this.c) {
            case 13:
                ((SampleQueue.b) obj).b.release();
                return;
            default:
                return;
        }
    }

    public final String buildCacheKey(DataSpec dataSpec) {
        String str = dataSpec.h;
        return str != null ? str : dataSpec.a.toString();
    }

    public final Extractor[] createExtractors() {
        return new Extractor[]{new WavExtractor()};
    }

    public final /* synthetic */ Extractor[] createExtractors(Uri uri, Map map) {
        return w2.a(this, uri, map);
    }

    public final ProgressiveMediaExtractor createProgressiveMediaExtractor() {
        return new MediaParserExtractorAdapter();
    }

    public final ChunkExtractor createProgressiveMediaExtractor(int i, Format format, boolean z, List list, TrackOutput trackOutput) {
        if (!MimeTypes.isText(format.o)) {
            return new MediaParserChunkExtractor(i, format, list);
        }
        Log.w("MediaPrsrChunkExtractor", "Ignoring an unsupported text track.");
        return null;
    }

    public final boolean evaluate(int i, int i2, int i3, int i4, int i5) {
        return false;
    }

    public final Bundleable fromBundle(Bundle bundle) {
        AdPlaybackState.AdGroup[] adGroupArr;
        Uri[] uriArr;
        switch (this.c) {
            case 15:
                long[] longArray = bundle.getLongArray(AdPlaybackState.a(1));
                ArrayList parcelableArrayList = bundle.getParcelableArrayList(AdPlaybackState.a(2));
                if (parcelableArrayList == null) {
                    adGroupArr = null;
                } else {
                    AdPlaybackState.AdGroup[] adGroupArr2 = new AdPlaybackState.AdGroup[parcelableArrayList.size()];
                    for (int i = 0; i < parcelableArrayList.size(); i++) {
                        adGroupArr2[i] = (AdPlaybackState.AdGroup) AdPlaybackState.AdGroup.i.fromBundle((Bundle) parcelableArrayList.get(i));
                    }
                    adGroupArr = adGroupArr2;
                }
                long j = bundle.getLong(AdPlaybackState.a(3), 0);
                long j2 = bundle.getLong(AdPlaybackState.a(4), -9223372036854775807L);
                if (longArray == null) {
                    longArray = new long[0];
                }
                return new AdPlaybackState((Object) null, longArray, adGroupArr, j, j2);
            case 16:
                int i2 = bundle.getInt(AdPlaybackState.AdGroup.b(0), -1);
                ArrayList parcelableArrayList2 = bundle.getParcelableArrayList(AdPlaybackState.AdGroup.b(1));
                int[] intArray = bundle.getIntArray(AdPlaybackState.AdGroup.b(2));
                long[] longArray2 = bundle.getLongArray(AdPlaybackState.AdGroup.b(3));
                if (intArray == null) {
                    intArray = new int[0];
                }
                if (parcelableArrayList2 == null) {
                    uriArr = new Uri[0];
                } else {
                    uriArr = (Uri[]) parcelableArrayList2.toArray(new Uri[0]);
                }
                if (longArray2 == null) {
                    longArray2 = new long[0];
                }
                return new AdPlaybackState.AdGroup(i2, intArray, uriArr, longArray2);
            default:
                return new VideoSize(bundle.getInt(VideoSize.a(0), 0), bundle.getInt(VideoSize.a(1), 0), bundle.getInt(VideoSize.a(2), 0), bundle.getFloat(VideoSize.a(3), 1.0f));
        }
    }

    public final int getScore(Object obj) {
        switch (this.c) {
            case 8:
                Pattern pattern = MediaCodecUtil.a;
                String str = ((MediaCodecInfo) obj).a;
                if (str.startsWith("OMX.google") || str.startsWith("c2.android")) {
                    return 1;
                }
                if (Util.a >= 26 || !str.equals("OMX.MTK.AUDIO.DECODER.RAW")) {
                    return 0;
                }
                return -1;
            default:
                Pattern pattern2 = MediaCodecUtil.a;
                return ((MediaCodecInfo) obj).a.startsWith("OMX.google") ? 1 : 0;
        }
    }

    public final void onTrackSelectionsInvalidated() {
        DefaultTrackSelector.Parameters parameters = DownloadHelper.o;
    }
}
