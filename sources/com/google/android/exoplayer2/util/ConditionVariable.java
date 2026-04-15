package com.google.android.exoplayer2.util;

public class ConditionVariable {
    public final Clock a;
    public boolean b;

    public ConditionVariable() {
        this(Clock.a);
    }

    public synchronized void block() throws InterruptedException {
        while (!this.b) {
            wait();
        }
    }

    public synchronized void blockUninterruptible() {
        boolean z = false;
        while (!this.b) {
            try {
                wait();
            } catch (InterruptedException unused) {
                z = true;
            }
        }
        if (z) {
            Thread.currentThread().interrupt();
        }
    }

    public synchronized boolean close() {
        boolean z;
        z = this.b;
        this.b = false;
        return z;
    }

    public synchronized boolean isOpen() {
        return this.b;
    }

    public synchronized boolean open() {
        if (this.b) {
            return false;
        }
        this.b = true;
        notifyAll();
        return true;
    }

    public ConditionVariable(Clock clock) {
        this.a = clock;
    }

    public synchronized boolean block(long j) throws InterruptedException {
        if (j <= 0) {
            return this.b;
        }
        long elapsedRealtime = this.a.elapsedRealtime();
        long j2 = j + elapsedRealtime;
        if (j2 < elapsedRealtime) {
            block();
        } else {
            while (!this.b && elapsedRealtime < j2) {
                wait(j2 - elapsedRealtime);
                elapsedRealtime = this.a.elapsedRealtime();
            }
        }
        return this.b;
    }
}
