package com.google.android.exoplayer2;

import android.os.Handler;
import android.os.Looper;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Clock;

public final class PlayerMessage {
    public final Target a;
    public final Sender b;
    public final Clock c;
    public final Timeline d;
    public int e;
    @Nullable
    public Object f;
    public Looper g;
    public int h;
    public long i = -9223372036854775807L;
    public boolean j = true;
    public boolean k;
    public boolean l;
    public boolean m;
    public boolean n;

    public interface Sender {
        void sendMessage(PlayerMessage playerMessage);
    }

    public interface Target {
        void handleMessage(int i, @Nullable Object obj) throws ExoPlaybackException;
    }

    public PlayerMessage(Sender sender, Target target, Timeline timeline, int i2, Clock clock, Looper looper) {
        this.b = sender;
        this.a = target;
        this.d = timeline;
        this.g = looper;
        this.c = clock;
        this.h = i2;
    }

    public synchronized boolean blockUntilDelivered() throws InterruptedException {
        Assertions.checkState(this.k);
        Assertions.checkState(this.g.getThread() != Thread.currentThread());
        while (!this.m) {
            wait();
        }
        return this.l;
    }

    public synchronized PlayerMessage cancel() {
        Assertions.checkState(this.k);
        this.n = true;
        markAsProcessed(false);
        return this;
    }

    public boolean getDeleteAfterDelivery() {
        return this.j;
    }

    public Looper getLooper() {
        return this.g;
    }

    @Nullable
    public Object getPayload() {
        return this.f;
    }

    public long getPositionMs() {
        return this.i;
    }

    public Target getTarget() {
        return this.a;
    }

    public Timeline getTimeline() {
        return this.d;
    }

    public int getType() {
        return this.e;
    }

    public int getWindowIndex() {
        return this.h;
    }

    public synchronized boolean isCanceled() {
        return this.n;
    }

    public synchronized void markAsProcessed(boolean z) {
        this.l = z | this.l;
        this.m = true;
        notifyAll();
    }

    public PlayerMessage send() {
        Assertions.checkState(!this.k);
        if (this.i == -9223372036854775807L) {
            Assertions.checkArgument(this.j);
        }
        this.k = true;
        this.b.sendMessage(this);
        return this;
    }

    public PlayerMessage setDeleteAfterDelivery(boolean z) {
        Assertions.checkState(!this.k);
        this.j = z;
        return this;
    }

    @Deprecated
    public PlayerMessage setHandler(Handler handler) {
        return setLooper(handler.getLooper());
    }

    public PlayerMessage setLooper(Looper looper) {
        Assertions.checkState(!this.k);
        this.g = looper;
        return this;
    }

    public PlayerMessage setPayload(@Nullable Object obj) {
        Assertions.checkState(!this.k);
        this.f = obj;
        return this;
    }

    public PlayerMessage setPosition(long j2) {
        Assertions.checkState(!this.k);
        this.i = j2;
        return this;
    }

    public PlayerMessage setType(int i2) {
        Assertions.checkState(!this.k);
        this.e = i2;
        return this;
    }

    public PlayerMessage setPosition(int i2, long j2) {
        boolean z = true;
        Assertions.checkState(!this.k);
        if (j2 == -9223372036854775807L) {
            z = false;
        }
        Assertions.checkArgument(z);
        Timeline timeline = this.d;
        if (i2 < 0 || (!timeline.isEmpty() && i2 >= timeline.getWindowCount())) {
            throw new IllegalSeekPositionException(timeline, i2, j2);
        }
        this.h = i2;
        this.i = j2;
        return this;
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x003c  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0040 A[SYNTHETIC, Splitter:B:16:0x0040] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized boolean blockUntilDelivered(long r7) throws java.lang.InterruptedException, java.util.concurrent.TimeoutException {
        /*
            r6 = this;
            monitor-enter(r6)
            boolean r0 = r6.k     // Catch:{ all -> 0x0048 }
            com.google.android.exoplayer2.util.Assertions.checkState(r0)     // Catch:{ all -> 0x0048 }
            android.os.Looper r0 = r6.g     // Catch:{ all -> 0x0048 }
            java.lang.Thread r0 = r0.getThread()     // Catch:{ all -> 0x0048 }
            java.lang.Thread r1 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x0048 }
            if (r0 == r1) goto L_0x0014
            r0 = 1
            goto L_0x0015
        L_0x0014:
            r0 = 0
        L_0x0015:
            com.google.android.exoplayer2.util.Assertions.checkState(r0)     // Catch:{ all -> 0x0048 }
            com.google.android.exoplayer2.util.Clock r0 = r6.c     // Catch:{ all -> 0x0048 }
            long r0 = r0.elapsedRealtime()     // Catch:{ all -> 0x0048 }
            long r0 = r0 + r7
        L_0x001f:
            boolean r2 = r6.m     // Catch:{ all -> 0x0048 }
            if (r2 != 0) goto L_0x003a
            r3 = 0
            int r5 = (r7 > r3 ? 1 : (r7 == r3 ? 0 : -1))
            if (r5 <= 0) goto L_0x003a
            com.google.android.exoplayer2.util.Clock r2 = r6.c     // Catch:{ all -> 0x0048 }
            r2.onThreadBlocked()     // Catch:{ all -> 0x0048 }
            r6.wait(r7)     // Catch:{ all -> 0x0048 }
            com.google.android.exoplayer2.util.Clock r7 = r6.c     // Catch:{ all -> 0x0048 }
            long r7 = r7.elapsedRealtime()     // Catch:{ all -> 0x0048 }
            long r7 = r0 - r7
            goto L_0x001f
        L_0x003a:
            if (r2 == 0) goto L_0x0040
            boolean r7 = r6.l     // Catch:{ all -> 0x0048 }
            monitor-exit(r6)
            return r7
        L_0x0040:
            java.util.concurrent.TimeoutException r7 = new java.util.concurrent.TimeoutException     // Catch:{ all -> 0x0048 }
            java.lang.String r8 = "Message delivery timed out."
            r7.<init>(r8)     // Catch:{ all -> 0x0048 }
            throw r7     // Catch:{ all -> 0x0048 }
        L_0x0048:
            r7 = move-exception
            monitor-exit(r6)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.PlayerMessage.blockUntilDelivered(long):boolean");
    }
}
