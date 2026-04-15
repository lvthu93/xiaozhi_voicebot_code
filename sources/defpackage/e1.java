package defpackage;

import android.util.Base64;
import com.google.android.exoplayer2.analytics.DefaultPlaybackSessionManager;
import com.google.common.base.Supplier;

/* renamed from: e1  reason: default package */
public final /* synthetic */ class e1 implements Supplier {
    public final Object get() {
        byte[] bArr = new byte[12];
        DefaultPlaybackSessionManager.i.nextBytes(bArr);
        return Base64.encodeToString(bArr, 10);
    }
}
