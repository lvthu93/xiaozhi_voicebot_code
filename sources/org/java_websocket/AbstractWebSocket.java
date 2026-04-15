package org.java_websocket;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.java_websocket.util.NamedThreadFactory;

public abstract class AbstractWebSocket extends WebSocketAdapter {
    private ScheduledFuture<?> connectionLostCheckerFuture;
    private ScheduledExecutorService connectionLostCheckerService;
    /* access modifiers changed from: private */
    public long connectionLostTimeout = TimeUnit.SECONDS.toNanos(60);
    private final k4 log = l4.e(AbstractWebSocket.class);
    private boolean reuseAddr;
    /* access modifiers changed from: private */
    public final Object syncConnectionLost = new Object();
    private boolean tcpNoDelay;
    private boolean websocketRunning = false;

    private void cancelConnectionLostTimer() {
        ScheduledExecutorService scheduledExecutorService = this.connectionLostCheckerService;
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdownNow();
            this.connectionLostCheckerService = null;
        }
        ScheduledFuture<?> scheduledFuture = this.connectionLostCheckerFuture;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
            this.connectionLostCheckerFuture = null;
        }
    }

    /* access modifiers changed from: private */
    public void executeConnectionLostDetection(WebSocket webSocket, long j) {
        if (webSocket instanceof WebSocketImpl) {
            WebSocketImpl webSocketImpl = (WebSocketImpl) webSocket;
            if (webSocketImpl.getLastPong() < j) {
                this.log.d(webSocketImpl, "Closing connection due to no pong received: {}");
                webSocketImpl.closeConnection(1006, "The connection was closed because the other endpoint did not respond with a pong in time. For more information check: https://github.com/TooTallNate/Java-WebSocket/wiki/Lost-connection-detection");
            } else if (webSocketImpl.isOpen()) {
                webSocketImpl.sendPing();
            } else {
                this.log.d(webSocketImpl, "Trying to ping a non open connection: {}");
            }
        }
    }

    private void restartConnectionLostTimer() {
        cancelConnectionLostTimer();
        this.connectionLostCheckerService = Executors.newSingleThreadScheduledExecutor(new NamedThreadFactory("connectionLostChecker"));
        AnonymousClass1 r2 = new Runnable() {
            private ArrayList<WebSocket> connections = new ArrayList<>();

            public void run() {
                long nanoTime;
                this.connections.clear();
                try {
                    this.connections.addAll(AbstractWebSocket.this.getConnections());
                    synchronized (AbstractWebSocket.this.syncConnectionLost) {
                        nanoTime = (long) (((double) System.nanoTime()) - (((double) AbstractWebSocket.this.connectionLostTimeout) * 1.5d));
                    }
                    Iterator<WebSocket> it = this.connections.iterator();
                    while (it.hasNext()) {
                        AbstractWebSocket.this.executeConnectionLostDetection(it.next(), nanoTime);
                    }
                } catch (Exception unused) {
                }
                this.connections.clear();
            }
        };
        ScheduledExecutorService scheduledExecutorService = this.connectionLostCheckerService;
        long j = this.connectionLostTimeout;
        this.connectionLostCheckerFuture = scheduledExecutorService.scheduleAtFixedRate(r2, j, j, TimeUnit.NANOSECONDS);
    }

    public int getConnectionLostTimeout() {
        int seconds;
        synchronized (this.syncConnectionLost) {
            seconds = (int) TimeUnit.NANOSECONDS.toSeconds(this.connectionLostTimeout);
        }
        return seconds;
    }

    public abstract Collection<WebSocket> getConnections();

    public boolean isReuseAddr() {
        return this.reuseAddr;
    }

    public boolean isTcpNoDelay() {
        return this.tcpNoDelay;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0058, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setConnectionLostTimeout(int r6) {
        /*
            r5 = this;
            java.lang.Object r0 = r5.syncConnectionLost
            monitor-enter(r0)
            java.util.concurrent.TimeUnit r1 = java.util.concurrent.TimeUnit.SECONDS     // Catch:{ all -> 0x0059 }
            long r2 = (long) r6     // Catch:{ all -> 0x0059 }
            long r1 = r1.toNanos(r2)     // Catch:{ all -> 0x0059 }
            r5.connectionLostTimeout = r1     // Catch:{ all -> 0x0059 }
            r3 = 0
            int r6 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r6 > 0) goto L_0x001e
            k4 r6 = r5.log     // Catch:{ all -> 0x0059 }
            java.lang.String r1 = "Connection lost timer stopped"
            r6.i(r1)     // Catch:{ all -> 0x0059 }
            r5.cancelConnectionLostTimer()     // Catch:{ all -> 0x0059 }
            monitor-exit(r0)     // Catch:{ all -> 0x0059 }
            return
        L_0x001e:
            boolean r6 = r5.websocketRunning     // Catch:{ all -> 0x0059 }
            if (r6 == 0) goto L_0x0057
            k4 r6 = r5.log     // Catch:{ all -> 0x0059 }
            java.lang.String r1 = "Connection lost timer restarted"
            r6.i(r1)     // Catch:{ all -> 0x0059 }
            java.util.ArrayList r6 = new java.util.ArrayList     // Catch:{ Exception -> 0x004c }
            java.util.Collection r1 = r5.getConnections()     // Catch:{ Exception -> 0x004c }
            r6.<init>(r1)     // Catch:{ Exception -> 0x004c }
            java.util.Iterator r6 = r6.iterator()     // Catch:{ Exception -> 0x004c }
        L_0x0036:
            boolean r1 = r6.hasNext()     // Catch:{ Exception -> 0x004c }
            if (r1 == 0) goto L_0x0054
            java.lang.Object r1 = r6.next()     // Catch:{ Exception -> 0x004c }
            org.java_websocket.WebSocket r1 = (org.java_websocket.WebSocket) r1     // Catch:{ Exception -> 0x004c }
            boolean r2 = r1 instanceof org.java_websocket.WebSocketImpl     // Catch:{ Exception -> 0x004c }
            if (r2 == 0) goto L_0x0036
            org.java_websocket.WebSocketImpl r1 = (org.java_websocket.WebSocketImpl) r1     // Catch:{ Exception -> 0x004c }
            r1.updateLastPong()     // Catch:{ Exception -> 0x004c }
            goto L_0x0036
        L_0x004c:
            r6 = move-exception
            k4 r1 = r5.log     // Catch:{ all -> 0x0059 }
            java.lang.String r2 = "Exception during connection lost restart"
            r1.c(r2, r6)     // Catch:{ all -> 0x0059 }
        L_0x0054:
            r5.restartConnectionLostTimer()     // Catch:{ all -> 0x0059 }
        L_0x0057:
            monitor-exit(r0)     // Catch:{ all -> 0x0059 }
            return
        L_0x0059:
            r6 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0059 }
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.java_websocket.AbstractWebSocket.setConnectionLostTimeout(int):void");
    }

    public void setReuseAddr(boolean z) {
        this.reuseAddr = z;
    }

    public void setTcpNoDelay(boolean z) {
        this.tcpNoDelay = z;
    }

    public void startConnectionLostTimer() {
        synchronized (this.syncConnectionLost) {
            if (this.connectionLostTimeout <= 0) {
                this.log.i("Connection lost timer deactivated");
                return;
            }
            this.log.i("Connection lost timer started");
            this.websocketRunning = true;
            restartConnectionLostTimer();
        }
    }

    public void stopConnectionLostTimer() {
        synchronized (this.syncConnectionLost) {
            if (!(this.connectionLostCheckerService == null && this.connectionLostCheckerFuture == null)) {
                this.websocketRunning = false;
                this.log.i("Connection lost timer stopped");
                cancelConnectionLostTimer();
            }
        }
    }
}
