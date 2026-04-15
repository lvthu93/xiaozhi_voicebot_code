package defpackage;

import android.os.ConditionVariable;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;

/* renamed from: wa  reason: default package */
public final class wa extends Thread {
    public final /* synthetic */ ConditionVariable c;
    public final /* synthetic */ SimpleCache f;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public wa(SimpleCache simpleCache, ConditionVariable conditionVariable) {
        super("ExoPlayer:SimpleCacheInit");
        this.f = simpleCache;
        this.c = conditionVariable;
    }

    public void run() {
        synchronized (this.f) {
            this.c.open();
            SimpleCache.a(this.f);
            this.f.b.onCacheInitialized();
        }
    }
}
