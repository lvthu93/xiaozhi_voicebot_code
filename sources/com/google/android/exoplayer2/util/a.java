package com.google.android.exoplayer2.util;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.HandlerWrapper;
import java.util.ArrayList;

public final class a implements HandlerWrapper {
    @GuardedBy("messagePool")
    public static final ArrayList b = new ArrayList(50);
    public final Handler a;

    /* renamed from: com.google.android.exoplayer2.util.a$a  reason: collision with other inner class name */
    public static final class C0021a implements HandlerWrapper.Message {
        @Nullable
        public Message a;
        @Nullable
        public a b;

        public final void a() {
            this.a = null;
            this.b = null;
            ArrayList arrayList = a.b;
            synchronized (arrayList) {
                if (arrayList.size() < 50) {
                    arrayList.add(this);
                }
            }
        }

        public HandlerWrapper getTarget() {
            return (HandlerWrapper) Assertions.checkNotNull(this.b);
        }

        public boolean sendAtFrontOfQueue(Handler handler) {
            boolean sendMessageAtFrontOfQueue = handler.sendMessageAtFrontOfQueue((Message) Assertions.checkNotNull(this.a));
            a();
            return sendMessageAtFrontOfQueue;
        }

        public void sendToTarget() {
            ((Message) Assertions.checkNotNull(this.a)).sendToTarget();
            a();
        }

        public C0021a setMessage(Message message, a aVar) {
            this.a = message;
            this.b = aVar;
            return this;
        }
    }

    public a(Handler handler) {
        this.a = handler;
    }

    public static C0021a a() {
        C0021a aVar;
        ArrayList arrayList = b;
        synchronized (arrayList) {
            if (arrayList.isEmpty()) {
                aVar = new C0021a();
            } else {
                aVar = (C0021a) arrayList.remove(arrayList.size() - 1);
            }
        }
        return aVar;
    }

    public Looper getLooper() {
        return this.a.getLooper();
    }

    public boolean hasMessages(int i) {
        return this.a.hasMessages(i);
    }

    public HandlerWrapper.Message obtainMessage(int i) {
        return a().setMessage(this.a.obtainMessage(i), this);
    }

    public boolean post(Runnable runnable) {
        return this.a.post(runnable);
    }

    public boolean postAtFrontOfQueue(Runnable runnable) {
        return this.a.postAtFrontOfQueue(runnable);
    }

    public boolean postDelayed(Runnable runnable, long j) {
        return this.a.postDelayed(runnable, j);
    }

    public void removeCallbacksAndMessages(@Nullable Object obj) {
        this.a.removeCallbacksAndMessages(obj);
    }

    public void removeMessages(int i) {
        this.a.removeMessages(i);
    }

    public boolean sendEmptyMessage(int i) {
        return this.a.sendEmptyMessage(i);
    }

    public boolean sendEmptyMessageAtTime(int i, long j) {
        return this.a.sendEmptyMessageAtTime(i, j);
    }

    public boolean sendEmptyMessageDelayed(int i, int i2) {
        return this.a.sendEmptyMessageDelayed(i, (long) i2);
    }

    public boolean sendMessageAtFrontOfQueue(HandlerWrapper.Message message) {
        return ((C0021a) message).sendAtFrontOfQueue(this.a);
    }

    public HandlerWrapper.Message obtainMessage(int i, @Nullable Object obj) {
        return a().setMessage(this.a.obtainMessage(i, obj), this);
    }

    public HandlerWrapper.Message obtainMessage(int i, int i2, int i3) {
        return a().setMessage(this.a.obtainMessage(i, i2, i3), this);
    }

    public HandlerWrapper.Message obtainMessage(int i, int i2, int i3, @Nullable Object obj) {
        return a().setMessage(this.a.obtainMessage(i, i2, i3, obj), this);
    }
}
