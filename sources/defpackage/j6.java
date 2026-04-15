package defpackage;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import java.util.List;

/* renamed from: j6  reason: default package */
public final /* synthetic */ class j6 {
    @Deprecated
    public static MediaSource a(MediaSourceFactory mediaSourceFactory, Uri uri) {
        return mediaSourceFactory.createMediaSource(MediaItem.fromUri(uri));
    }

    @Deprecated
    public static MediaSourceFactory b(MediaSourceFactory mediaSourceFactory, @Nullable List list) {
        return mediaSourceFactory;
    }
}
