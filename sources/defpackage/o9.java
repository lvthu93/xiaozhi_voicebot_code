package defpackage;

import java.util.concurrent.ThreadFactory;

/* renamed from: o9  reason: default package */
public final class o9 implements ThreadFactory {
    public final Thread newThread(Runnable runnable) {
        Thread thread = new Thread(runnable, "Protocol-Callback");
        thread.setDaemon(true);
        return thread;
    }
}
