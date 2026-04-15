package com.google.android.exoplayer2.drm;

import android.os.Handler;
import androidx.annotation.CheckResult;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public interface DrmSessionEventListener {

    public static class EventDispatcher {
        public final int a;
        @Nullable
        public final MediaSource.MediaPeriodId b;
        public final CopyOnWriteArrayList<a> c;

        public static final class a {
            public final Handler a;
            public final DrmSessionEventListener b;

            public a(Handler handler, DrmSessionEventListener drmSessionEventListener) {
                this.a = handler;
                this.b = drmSessionEventListener;
            }
        }

        public EventDispatcher() {
            this(new CopyOnWriteArrayList(), 0, (MediaSource.MediaPeriodId) null);
        }

        public void addEventListener(Handler handler, DrmSessionEventListener drmSessionEventListener) {
            Assertions.checkNotNull(handler);
            Assertions.checkNotNull(drmSessionEventListener);
            this.c.add(new a(handler, drmSessionEventListener));
        }

        public void drmKeysLoaded() {
            Iterator<a> it = this.c.iterator();
            while (it.hasNext()) {
                a next = it.next();
                Util.postOrRun(next.a, new u1(this, next.b, 2));
            }
        }

        public void drmKeysRemoved() {
            Iterator<a> it = this.c.iterator();
            while (it.hasNext()) {
                a next = it.next();
                Util.postOrRun(next.a, new u1(this, next.b, 1));
            }
        }

        public void drmKeysRestored() {
            Iterator<a> it = this.c.iterator();
            while (it.hasNext()) {
                a next = it.next();
                Util.postOrRun(next.a, new u1(this, next.b, 3));
            }
        }

        public void drmSessionAcquired(int i) {
            Iterator<a> it = this.c.iterator();
            while (it.hasNext()) {
                a next = it.next();
                Util.postOrRun(next.a, new v1(this, next.b, i));
            }
        }

        public void drmSessionManagerError(Exception exc) {
            Iterator<a> it = this.c.iterator();
            while (it.hasNext()) {
                a next = it.next();
                Util.postOrRun(next.a, new d6(2, this, next.b, exc));
            }
        }

        public void drmSessionReleased() {
            Iterator<a> it = this.c.iterator();
            while (it.hasNext()) {
                a next = it.next();
                Util.postOrRun(next.a, new u1(this, next.b, 0));
            }
        }

        public void removeEventListener(DrmSessionEventListener drmSessionEventListener) {
            CopyOnWriteArrayList<a> copyOnWriteArrayList = this.c;
            Iterator<a> it = copyOnWriteArrayList.iterator();
            while (it.hasNext()) {
                a next = it.next();
                if (next.b == drmSessionEventListener) {
                    copyOnWriteArrayList.remove(next);
                }
            }
        }

        @CheckResult
        public EventDispatcher withParameters(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
            return new EventDispatcher(this.c, i, mediaPeriodId);
        }

        public EventDispatcher(CopyOnWriteArrayList<a> copyOnWriteArrayList, int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
            this.c = copyOnWriteArrayList;
            this.a = i;
            this.b = mediaPeriodId;
        }
    }

    void onDrmKeysLoaded(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId);

    void onDrmKeysRemoved(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId);

    void onDrmKeysRestored(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId);

    @Deprecated
    void onDrmSessionAcquired(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId);

    void onDrmSessionAcquired(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, int i2);

    void onDrmSessionManagerError(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, Exception exc);

    void onDrmSessionReleased(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId);
}
