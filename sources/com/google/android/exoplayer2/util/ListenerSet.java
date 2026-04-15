package com.google.android.exoplayer2.util;

import android.os.Looper;
import androidx.annotation.CheckResult;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.ExoFlags;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;

public final class ListenerSet<T> {
    public final Clock a;
    public final HandlerWrapper b;
    public final IterationFinishedEvent<T> c;
    public final CopyOnWriteArraySet<a<T>> d;
    public final ArrayDeque<Runnable> e;
    public final ArrayDeque<Runnable> f;
    public boolean g;

    public interface Event<T> {
        void invoke(T t);
    }

    public interface IterationFinishedEvent<T> {
        void invoke(T t, ExoFlags exoFlags);
    }

    public static final class a<T> {
        public final T a;
        public ExoFlags.Builder b = new ExoFlags.Builder();
        public boolean c;
        public boolean d;

        public a(T t) {
            this.a = t;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || a.class != obj.getClass()) {
                return false;
            }
            return this.a.equals(((a) obj).a);
        }

        public int hashCode() {
            return this.a.hashCode();
        }

        public void invoke(int i, Event<T> event) {
            if (!this.d) {
                if (i != -1) {
                    this.b.add(i);
                }
                this.c = true;
                event.invoke(this.a);
            }
        }

        public void iterationFinished(IterationFinishedEvent<T> iterationFinishedEvent) {
            if (!this.d && this.c) {
                ExoFlags build = this.b.build();
                this.b = new ExoFlags.Builder();
                this.c = false;
                iterationFinishedEvent.invoke(this.a, build);
            }
        }

        public void release(IterationFinishedEvent<T> iterationFinishedEvent) {
            this.d = true;
            if (this.c) {
                iterationFinishedEvent.invoke(this.a, this.b.build());
            }
        }
    }

    public ListenerSet(Looper looper, Clock clock, IterationFinishedEvent<T> iterationFinishedEvent) {
        this(new CopyOnWriteArraySet(), looper, clock, iterationFinishedEvent);
    }

    public void add(T t) {
        if (!this.g) {
            Assertions.checkNotNull(t);
            this.d.add(new a(t));
        }
    }

    @CheckResult
    public ListenerSet<T> copy(Looper looper, IterationFinishedEvent<T> iterationFinishedEvent) {
        return new ListenerSet<>(this.d, looper, this.a, iterationFinishedEvent);
    }

    public void flushEvents() {
        ArrayDeque<Runnable> arrayDeque = this.f;
        if (!arrayDeque.isEmpty()) {
            HandlerWrapper handlerWrapper = this.b;
            if (!handlerWrapper.hasMessages(0)) {
                handlerWrapper.obtainMessage(0).sendToTarget();
            }
            ArrayDeque<Runnable> arrayDeque2 = this.e;
            boolean z = !arrayDeque2.isEmpty();
            arrayDeque2.addAll(arrayDeque);
            arrayDeque.clear();
            if (!z) {
                while (!arrayDeque2.isEmpty()) {
                    arrayDeque2.peekFirst().run();
                    arrayDeque2.removeFirst();
                }
            }
        }
    }

    public void lazyRelease(int i, Event<T> event) {
        this.b.obtainMessage(1, i, 0, event).sendToTarget();
    }

    public void queueEvent(int i, Event<T> event) {
        this.f.add(new v1(i, new CopyOnWriteArraySet(this.d), event, 1));
    }

    public void release() {
        CopyOnWriteArraySet<a<T>> copyOnWriteArraySet = this.d;
        Iterator<a<T>> it = copyOnWriteArraySet.iterator();
        while (it.hasNext()) {
            it.next().release(this.c);
        }
        copyOnWriteArraySet.clear();
        this.g = true;
    }

    public void remove(T t) {
        CopyOnWriteArraySet<a<T>> copyOnWriteArraySet = this.d;
        Iterator<a<T>> it = copyOnWriteArraySet.iterator();
        while (it.hasNext()) {
            a next = it.next();
            if (next.a.equals(t)) {
                next.release(this.c);
                copyOnWriteArraySet.remove(next);
            }
        }
    }

    public void sendEvent(int i, Event<T> event) {
        queueEvent(i, event);
        flushEvents();
    }

    public ListenerSet(CopyOnWriteArraySet<a<T>> copyOnWriteArraySet, Looper looper, Clock clock, IterationFinishedEvent<T> iterationFinishedEvent) {
        this.a = clock;
        this.d = copyOnWriteArraySet;
        this.c = iterationFinishedEvent;
        this.e = new ArrayDeque<>();
        this.f = new ArrayDeque<>();
        this.b = clock.createHandler(looper, new q1(4, this));
    }
}
