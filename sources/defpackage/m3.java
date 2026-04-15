package defpackage;

import com.google.android.exoplayer2.drm.DummyExoMediaDrm;
import com.google.android.exoplayer2.drm.ExoMediaDrm;
import com.google.android.exoplayer2.drm.FrameworkMediaDrm;
import com.google.android.exoplayer2.drm.UnsupportedDrmException;
import com.google.android.exoplayer2.util.Log;
import java.util.UUID;

/* renamed from: m3  reason: default package */
public final /* synthetic */ class m3 implements ExoMediaDrm.Provider {
    public final ExoMediaDrm acquireExoMediaDrm(UUID uuid) {
        try {
            return FrameworkMediaDrm.newInstance(uuid);
        } catch (UnsupportedDrmException unused) {
            String valueOf = String.valueOf(uuid);
            StringBuilder sb = new StringBuilder(valueOf.length() + 53);
            sb.append("Failed to instantiate a FrameworkMediaDrm for uuid: ");
            sb.append(valueOf);
            sb.append(".");
            Log.e("FrameworkMediaDrm", sb.toString());
            return new DummyExoMediaDrm();
        }
    }
}
