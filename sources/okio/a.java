package okio;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.concurrent.TimeUnit;

public class a extends lc {
    private static final long IDLE_TIMEOUT_MILLIS;
    private static final long IDLE_TIMEOUT_NANOS;
    private static final int TIMEOUT_WRITE_SIZE = 65536;
    static a head;
    private boolean inQueue;
    private a next;
    private long timeoutAt;

    /* renamed from: okio.a$a  reason: collision with other inner class name */
    public class C0025a implements za {
        public final /* synthetic */ za c;

        public C0025a(za zaVar) {
            this.c = zaVar;
        }

        public final void close() throws IOException {
            a aVar = a.this;
            aVar.enter();
            try {
                this.c.close();
                aVar.exit(true);
            } catch (IOException e) {
                throw aVar.exit(e);
            } catch (Throwable th) {
                aVar.exit(false);
                throw th;
            }
        }

        public final void flush() throws IOException {
            a aVar = a.this;
            aVar.enter();
            try {
                this.c.flush();
                aVar.exit(true);
            } catch (IOException e) {
                throw aVar.exit(e);
            } catch (Throwable th) {
                aVar.exit(false);
                throw th;
            }
        }

        public final lc timeout() {
            return a.this;
        }

        public final String toString() {
            return "AsyncTimeout.sink(" + this.c + ")";
        }

        public final void write(ck ckVar, long j) throws IOException {
            jd.a(ckVar.f, 0, j);
            while (true) {
                long j2 = 0;
                if (j > 0) {
                    qa qaVar = ckVar.c;
                    while (true) {
                        if (j2 >= 65536) {
                            break;
                        }
                        j2 += (long) (qaVar.c - qaVar.b);
                        if (j2 >= j) {
                            j2 = j;
                            break;
                        }
                        qaVar = qaVar.f;
                    }
                    a aVar = a.this;
                    aVar.enter();
                    try {
                        this.c.write(ckVar, j2);
                        j -= j2;
                        aVar.exit(true);
                    } catch (IOException e) {
                        throw aVar.exit(e);
                    } catch (Throwable th) {
                        aVar.exit(false);
                        throw th;
                    }
                } else {
                    return;
                }
            }
        }
    }

    public class b implements jb {
        public final /* synthetic */ jb c;

        public b(jb jbVar) {
            this.c = jbVar;
        }

        public final void close() throws IOException {
            a aVar = a.this;
            try {
                this.c.close();
                aVar.exit(true);
            } catch (IOException e) {
                throw aVar.exit(e);
            } catch (Throwable th) {
                aVar.exit(false);
                throw th;
            }
        }

        public final long read(ck ckVar, long j) throws IOException {
            a aVar = a.this;
            aVar.enter();
            try {
                long read = this.c.read(ckVar, j);
                aVar.exit(true);
                return read;
            } catch (IOException e) {
                throw aVar.exit(e);
            } catch (Throwable th) {
                aVar.exit(false);
                throw th;
            }
        }

        public final lc timeout() {
            return a.this;
        }

        public final String toString() {
            return "AsyncTimeout.source(" + this.c + ")";
        }
    }

    public static final class c extends Thread {
        public c() {
            super("Okio Watchdog");
            setDaemon(true);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
            r1.timedOut();
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void run() {
            /*
                r3 = this;
            L_0x0000:
                java.lang.Class<okio.a> r0 = okio.a.class
                monitor-enter(r0)     // Catch:{ InterruptedException -> 0x0000 }
                okio.a r1 = okio.a.awaitTimeout()     // Catch:{ all -> 0x0019 }
                if (r1 != 0) goto L_0x000b
                monitor-exit(r0)     // Catch:{ all -> 0x0019 }
                goto L_0x0000
            L_0x000b:
                okio.a r2 = okio.a.head     // Catch:{ all -> 0x0019 }
                if (r1 != r2) goto L_0x0014
                r1 = 0
                okio.a.head = r1     // Catch:{ all -> 0x0019 }
                monitor-exit(r0)     // Catch:{ all -> 0x0019 }
                return
            L_0x0014:
                monitor-exit(r0)     // Catch:{ all -> 0x0019 }
                r1.timedOut()     // Catch:{ InterruptedException -> 0x0000 }
                goto L_0x0000
            L_0x0019:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0019 }
                throw r1     // Catch:{ InterruptedException -> 0x0000 }
            */
            throw new UnsupportedOperationException("Method not decompiled: okio.a.c.run():void");
        }
    }

    static {
        long millis = TimeUnit.SECONDS.toMillis(60);
        IDLE_TIMEOUT_MILLIS = millis;
        IDLE_TIMEOUT_NANOS = TimeUnit.MILLISECONDS.toNanos(millis);
    }

    public static a awaitTimeout() throws InterruptedException {
        a aVar = head.next;
        Class<a> cls = a.class;
        if (aVar == null) {
            long nanoTime = System.nanoTime();
            cls.wait(IDLE_TIMEOUT_MILLIS);
            if (head.next != null || System.nanoTime() - nanoTime < IDLE_TIMEOUT_NANOS) {
                return null;
            }
            return head;
        }
        long remainingNanos = aVar.remainingNanos(System.nanoTime());
        if (remainingNanos > 0) {
            long j = remainingNanos / 1000000;
            cls.wait(j, (int) (remainingNanos - (1000000 * j)));
            return null;
        }
        head.next = aVar.next;
        aVar.next = null;
        return aVar;
    }

    private static synchronized boolean cancelScheduledTimeout(a aVar) {
        synchronized (a.class) {
            a aVar2 = head;
            while (aVar2 != null) {
                a aVar3 = aVar2.next;
                if (aVar3 == aVar) {
                    aVar2.next = aVar.next;
                    aVar.next = null;
                    return false;
                }
                aVar2 = aVar3;
            }
            return true;
        }
    }

    private long remainingNanos(long j) {
        return this.timeoutAt - j;
    }

    private static synchronized void scheduleTimeout(a aVar, long j, boolean z) {
        synchronized (a.class) {
            if (head == null) {
                head = new a();
                new c().start();
            }
            long nanoTime = System.nanoTime();
            int i = (j > 0 ? 1 : (j == 0 ? 0 : -1));
            if (i != 0 && z) {
                aVar.timeoutAt = Math.min(j, aVar.deadlineNanoTime() - nanoTime) + nanoTime;
            } else if (i != 0) {
                aVar.timeoutAt = j + nanoTime;
            } else if (z) {
                aVar.timeoutAt = aVar.deadlineNanoTime();
            } else {
                throw new AssertionError();
            }
            long remainingNanos = aVar.remainingNanos(nanoTime);
            a aVar2 = head;
            while (true) {
                a aVar3 = aVar2.next;
                if (aVar3 == null) {
                    break;
                } else if (remainingNanos < aVar3.remainingNanos(nanoTime)) {
                    break;
                } else {
                    aVar2 = aVar2.next;
                }
            }
            aVar.next = aVar2.next;
            aVar2.next = aVar;
            if (aVar2 == head) {
                a.class.notify();
            }
        }
    }

    public final void enter() {
        if (!this.inQueue) {
            long timeoutNanos = timeoutNanos();
            boolean hasDeadline = hasDeadline();
            if (timeoutNanos != 0 || hasDeadline) {
                this.inQueue = true;
                scheduleTimeout(this, timeoutNanos, hasDeadline);
                return;
            }
            return;
        }
        throw new IllegalStateException("Unbalanced enter/exit");
    }

    public final boolean exit() {
        if (!this.inQueue) {
            return false;
        }
        this.inQueue = false;
        return cancelScheduledTimeout(this);
    }

    public IOException newTimeoutException(IOException iOException) {
        InterruptedIOException interruptedIOException = new InterruptedIOException("timeout");
        if (iOException != null) {
            interruptedIOException.initCause(iOException);
        }
        return interruptedIOException;
    }

    public final za sink(za zaVar) {
        return new C0025a(zaVar);
    }

    public final jb source(jb jbVar) {
        return new b(jbVar);
    }

    public void timedOut() {
    }

    public final void exit(boolean z) throws IOException {
        if (exit() && z) {
            throw newTimeoutException((IOException) null);
        }
    }

    public final IOException exit(IOException iOException) throws IOException {
        if (!exit()) {
            return iOException;
        }
        return newTimeoutException(iOException);
    }
}
