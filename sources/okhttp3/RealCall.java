package okhttp3;

import androidx.core.app.NotificationCompat;
import java.io.Closeable;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import okhttp3.internal.NamedRunnable;
import okhttp3.internal.Util;
import okhttp3.internal.cache.CacheInterceptor;
import okhttp3.internal.connection.ConnectInterceptor;
import okhttp3.internal.connection.RealConnection;
import okhttp3.internal.connection.StreamAllocation;
import okhttp3.internal.http.BridgeInterceptor;
import okhttp3.internal.http.CallServerInterceptor;
import okhttp3.internal.http.HttpCodec;
import okhttp3.internal.http.RealInterceptorChain;
import okhttp3.internal.http.RetryAndFollowUpInterceptor;
import okhttp3.internal.platform.Platform;
import okio.a;

final class RealCall implements Call {
    final OkHttpClient client;
    /* access modifiers changed from: private */
    public EventListener eventListener;
    private boolean executed;
    final boolean forWebSocket;
    final Request originalRequest;
    final RetryAndFollowUpInterceptor retryAndFollowUpInterceptor;
    final a timeout;

    public final class AsyncCall extends NamedRunnable {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final Callback responseCallback;

        public AsyncCall(Callback callback) {
            super("OkHttp %s", RealCall.this.redactedUrl());
            this.responseCallback = callback;
        }

        /* JADX WARNING: Removed duplicated region for block: B:15:0x0034 A[Catch:{ IOException -> 0x004d, all -> 0x002c, all -> 0x0085 }] */
        /* JADX WARNING: Removed duplicated region for block: B:20:0x0056 A[Catch:{ IOException -> 0x004d, all -> 0x002c, all -> 0x0085 }] */
        /* JADX WARNING: Removed duplicated region for block: B:21:0x0071 A[Catch:{ IOException -> 0x004d, all -> 0x002c, all -> 0x0085 }] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void execute() {
            /*
                r6 = this;
                java.lang.String r0 = "canceled due to "
                java.lang.String r1 = "Callback failure for "
                okhttp3.RealCall r2 = okhttp3.RealCall.this
                okio.a r2 = r2.timeout
                r2.enter()
                r2 = 0
                okhttp3.RealCall r3 = okhttp3.RealCall.this     // Catch:{ IOException -> 0x004d, all -> 0x002c }
                okhttp3.Response r2 = r3.getResponseWithInterceptorChain()     // Catch:{ IOException -> 0x004d, all -> 0x002c }
                r3 = 1
                okhttp3.Callback r4 = r6.responseCallback     // Catch:{ IOException -> 0x0029, all -> 0x0026 }
                okhttp3.RealCall r5 = okhttp3.RealCall.this     // Catch:{ IOException -> 0x0029, all -> 0x0026 }
                r4.onResponse(r5, r2)     // Catch:{ IOException -> 0x0029, all -> 0x0026 }
            L_0x001a:
                okhttp3.RealCall r0 = okhttp3.RealCall.this
                okhttp3.OkHttpClient r0 = r0.client
                okhttp3.Dispatcher r0 = r0.dispatcher()
                r0.finished((okhttp3.RealCall.AsyncCall) r6)
                goto L_0x0084
            L_0x0026:
                r1 = move-exception
                r2 = 1
                goto L_0x002d
            L_0x0029:
                r0 = move-exception
                r2 = 1
                goto L_0x004e
            L_0x002c:
                r1 = move-exception
            L_0x002d:
                okhttp3.RealCall r3 = okhttp3.RealCall.this     // Catch:{ all -> 0x0085 }
                r3.cancel()     // Catch:{ all -> 0x0085 }
                if (r2 != 0) goto L_0x004c
                java.io.IOException r2 = new java.io.IOException     // Catch:{ all -> 0x0085 }
                java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0085 }
                r3.<init>(r0)     // Catch:{ all -> 0x0085 }
                r3.append(r1)     // Catch:{ all -> 0x0085 }
                java.lang.String r0 = r3.toString()     // Catch:{ all -> 0x0085 }
                r2.<init>(r0)     // Catch:{ all -> 0x0085 }
                okhttp3.Callback r0 = r6.responseCallback     // Catch:{ all -> 0x0085 }
                okhttp3.RealCall r3 = okhttp3.RealCall.this     // Catch:{ all -> 0x0085 }
                r0.onFailure(r3, r2)     // Catch:{ all -> 0x0085 }
            L_0x004c:
                throw r1     // Catch:{ all -> 0x0085 }
            L_0x004d:
                r0 = move-exception
            L_0x004e:
                okhttp3.RealCall r3 = okhttp3.RealCall.this     // Catch:{ all -> 0x0085 }
                java.io.IOException r0 = r3.timeoutExit(r0)     // Catch:{ all -> 0x0085 }
                if (r2 == 0) goto L_0x0071
                okhttp3.internal.platform.Platform r2 = okhttp3.internal.platform.Platform.get()     // Catch:{ all -> 0x0085 }
                java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0085 }
                r3.<init>(r1)     // Catch:{ all -> 0x0085 }
                okhttp3.RealCall r1 = okhttp3.RealCall.this     // Catch:{ all -> 0x0085 }
                java.lang.String r1 = r1.toLoggableString()     // Catch:{ all -> 0x0085 }
                r3.append(r1)     // Catch:{ all -> 0x0085 }
                java.lang.String r1 = r3.toString()     // Catch:{ all -> 0x0085 }
                r3 = 4
                r2.log(r3, r1, r0)     // Catch:{ all -> 0x0085 }
                goto L_0x001a
            L_0x0071:
                okhttp3.RealCall r1 = okhttp3.RealCall.this     // Catch:{ all -> 0x0085 }
                okhttp3.EventListener r1 = r1.eventListener     // Catch:{ all -> 0x0085 }
                okhttp3.RealCall r2 = okhttp3.RealCall.this     // Catch:{ all -> 0x0085 }
                r1.callFailed(r2, r0)     // Catch:{ all -> 0x0085 }
                okhttp3.Callback r1 = r6.responseCallback     // Catch:{ all -> 0x0085 }
                okhttp3.RealCall r2 = okhttp3.RealCall.this     // Catch:{ all -> 0x0085 }
                r1.onFailure(r2, r0)     // Catch:{ all -> 0x0085 }
                goto L_0x001a
            L_0x0084:
                return
            L_0x0085:
                r0 = move-exception
                okhttp3.RealCall r1 = okhttp3.RealCall.this
                okhttp3.OkHttpClient r1 = r1.client
                okhttp3.Dispatcher r1 = r1.dispatcher()
                r1.finished((okhttp3.RealCall.AsyncCall) r6)
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: okhttp3.RealCall.AsyncCall.execute():void");
        }

        public void executeOn(ExecutorService executorService) {
            try {
                executorService.execute(this);
            } catch (RejectedExecutionException e) {
                InterruptedIOException interruptedIOException = new InterruptedIOException("executor rejected");
                interruptedIOException.initCause(e);
                RealCall.this.eventListener.callFailed(RealCall.this, interruptedIOException);
                this.responseCallback.onFailure(RealCall.this, interruptedIOException);
                RealCall.this.client.dispatcher().finished(this);
            } catch (Throwable th) {
                RealCall.this.client.dispatcher().finished(this);
                throw th;
            }
        }

        public RealCall get() {
            return RealCall.this;
        }

        public String host() {
            return RealCall.this.originalRequest.url().host();
        }

        public Request request() {
            return RealCall.this.originalRequest;
        }
    }

    private RealCall(OkHttpClient okHttpClient, Request request, boolean z) {
        this.client = okHttpClient;
        this.originalRequest = request;
        this.forWebSocket = z;
        this.retryAndFollowUpInterceptor = new RetryAndFollowUpInterceptor(okHttpClient, z);
        AnonymousClass1 r4 = new a() {
            public void timedOut() {
                RealCall.this.cancel();
            }
        };
        this.timeout = r4;
        r4.timeout((long) okHttpClient.callTimeoutMillis(), TimeUnit.MILLISECONDS);
    }

    private void captureCallStackTrace() {
        this.retryAndFollowUpInterceptor.setCallStackTrace(Platform.get().getStackTraceForCloseable("response.body().close()"));
    }

    public static RealCall newRealCall(OkHttpClient okHttpClient, Request request, boolean z) {
        RealCall realCall = new RealCall(okHttpClient, request, z);
        realCall.eventListener = okHttpClient.eventListenerFactory().create(realCall);
        return realCall;
    }

    public void cancel() {
        this.retryAndFollowUpInterceptor.cancel();
    }

    public void enqueue(Callback callback) {
        synchronized (this) {
            if (!this.executed) {
                this.executed = true;
            } else {
                throw new IllegalStateException("Already Executed");
            }
        }
        captureCallStackTrace();
        this.eventListener.callStart(this);
        this.client.dispatcher().enqueue(new AsyncCall(callback));
    }

    public Response execute() throws IOException {
        synchronized (this) {
            if (!this.executed) {
                this.executed = true;
            } else {
                throw new IllegalStateException("Already Executed");
            }
        }
        captureCallStackTrace();
        this.timeout.enter();
        this.eventListener.callStart(this);
        try {
            this.client.dispatcher().executed(this);
            Response responseWithInterceptorChain = getResponseWithInterceptorChain();
            if (responseWithInterceptorChain != null) {
                this.client.dispatcher().finished(this);
                return responseWithInterceptorChain;
            }
            throw new IOException("Canceled");
        } catch (IOException e) {
            IOException timeoutExit = timeoutExit(e);
            this.eventListener.callFailed(this, timeoutExit);
            throw timeoutExit;
        } catch (Throwable th) {
            this.client.dispatcher().finished(this);
            throw th;
        }
    }

    public Response getResponseWithInterceptorChain() throws IOException {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.client.interceptors());
        arrayList.add(this.retryAndFollowUpInterceptor);
        arrayList.add(new BridgeInterceptor(this.client.cookieJar()));
        arrayList.add(new CacheInterceptor(this.client.internalCache()));
        arrayList.add(new ConnectInterceptor(this.client));
        if (!this.forWebSocket) {
            arrayList.addAll(this.client.networkInterceptors());
        }
        arrayList.add(new CallServerInterceptor(this.forWebSocket));
        Response proceed = new RealInterceptorChain(arrayList, (StreamAllocation) null, (HttpCodec) null, (RealConnection) null, 0, this.originalRequest, this, this.eventListener, this.client.connectTimeoutMillis(), this.client.readTimeoutMillis(), this.client.writeTimeoutMillis()).proceed(this.originalRequest);
        if (!this.retryAndFollowUpInterceptor.isCanceled()) {
            return proceed;
        }
        Util.closeQuietly((Closeable) proceed);
        throw new IOException("Canceled");
    }

    public boolean isCanceled() {
        return this.retryAndFollowUpInterceptor.isCanceled();
    }

    public synchronized boolean isExecuted() {
        return this.executed;
    }

    public String redactedUrl() {
        return this.originalRequest.url().redact();
    }

    public Request request() {
        return this.originalRequest;
    }

    public StreamAllocation streamAllocation() {
        return this.retryAndFollowUpInterceptor.streamAllocation();
    }

    public lc timeout() {
        return this.timeout;
    }

    public IOException timeoutExit(IOException iOException) {
        if (!this.timeout.exit()) {
            return iOException;
        }
        InterruptedIOException interruptedIOException = new InterruptedIOException("timeout");
        if (iOException != null) {
            interruptedIOException.initCause(iOException);
        }
        return interruptedIOException;
    }

    public String toLoggableString() {
        String str;
        String str2;
        StringBuilder sb = new StringBuilder();
        if (isCanceled()) {
            str = "canceled ";
        } else {
            str = "";
        }
        sb.append(str);
        if (this.forWebSocket) {
            str2 = "web socket";
        } else {
            str2 = NotificationCompat.CATEGORY_CALL;
        }
        sb.append(str2);
        sb.append(" to ");
        sb.append(redactedUrl());
        return sb.toString();
    }

    public RealCall clone() {
        return newRealCall(this.client, this.originalRequest, this.forWebSocket);
    }
}
