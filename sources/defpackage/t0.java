package defpackage;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.upstream.cache.ContentMetadata;

/* renamed from: t0  reason: default package */
public final /* synthetic */ class t0 {
    public static long a(ContentMetadata contentMetadata) {
        return contentMetadata.get("exo_len", -1);
    }

    @Nullable
    public static Uri b(ContentMetadata contentMetadata) {
        String str = contentMetadata.get("exo_redir", (String) null);
        if (str == null) {
            return null;
        }
        return Uri.parse(str);
    }
}
