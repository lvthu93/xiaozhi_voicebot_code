package defpackage;

import java.util.concurrent.ThreadFactory;

/* renamed from: k9  reason: default package */
public final class k9 implements ThreadFactory {
    public final Thread newThread(Runnable runnable) {
        Thread thread = new Thread(runnable, "Protocol-Network");
        thread.setDaemon(true);
        return thread;
    }
}
