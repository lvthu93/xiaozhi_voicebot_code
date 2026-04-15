package defpackage;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/* renamed from: g3  reason: default package */
public final class g3 extends lc {
    public lc a;

    public g3(lc lcVar) {
        if (lcVar != null) {
            this.a = lcVar;
            return;
        }
        throw new IllegalArgumentException("delegate == null");
    }

    public final lc clearDeadline() {
        return this.a.clearDeadline();
    }

    public final lc clearTimeout() {
        return this.a.clearTimeout();
    }

    public final long deadlineNanoTime() {
        return this.a.deadlineNanoTime();
    }

    public final boolean hasDeadline() {
        return this.a.hasDeadline();
    }

    public final void throwIfReached() throws IOException {
        this.a.throwIfReached();
    }

    public final lc timeout(long j, TimeUnit timeUnit) {
        return this.a.timeout(j, timeUnit);
    }

    public final long timeoutNanos() {
        return this.a.timeoutNanos();
    }

    public final lc deadlineNanoTime(long j) {
        return this.a.deadlineNanoTime(j);
    }
}
