package defpackage;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.decoder.OutputBuffer;
import com.google.android.exoplayer2.text.SubtitleDecoder;
import com.google.android.exoplayer2.text.SubtitleDecoderException;
import com.google.android.exoplayer2.text.SubtitleInputBuffer;
import com.google.android.exoplayer2.text.SubtitleOutputBuffer;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayDeque;
import java.util.PriorityQueue;

/* renamed from: e0  reason: default package */
public abstract class e0 implements SubtitleDecoder {
    public final ArrayDeque<a> a = new ArrayDeque<>();
    public final ArrayDeque<SubtitleOutputBuffer> b;
    public final PriorityQueue<a> c;
    @Nullable
    public a d;
    public long e;
    public long f;

    /* renamed from: e0$a */
    public static final class a extends SubtitleInputBuffer implements Comparable<a> {
        public long n;

        public int compareTo(a aVar) {
            if (isEndOfStream() == aVar.isEndOfStream()) {
                long j = this.i - aVar.i;
                if (j == 0) {
                    j = this.n - aVar.n;
                    if (j == 0) {
                        return 0;
                    }
                }
                if (j > 0) {
                    return 1;
                }
                return -1;
            } else if (isEndOfStream()) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    /* renamed from: e0$b */
    public static final class b extends SubtitleOutputBuffer {
        public final OutputBuffer.Owner<b> j;

        public b(OutputBuffer.Owner<b> owner) {
            this.j = owner;
        }

        public final void release() {
            this.j.releaseOutputBuffer(this);
        }
    }

    public e0() {
        for (int i = 0; i < 10; i++) {
            this.a.add(new a());
        }
        this.b = new ArrayDeque<>();
        for (int i2 = 0; i2 < 2; i2++) {
            this.b.add(new b(new i2(9, this)));
        }
        this.c = new PriorityQueue<>();
    }

    public abstract f0 a();

    public abstract void b(a aVar);

    public abstract boolean c();

    public void flush() {
        ArrayDeque<a> arrayDeque;
        this.f = 0;
        this.e = 0;
        while (true) {
            PriorityQueue<a> priorityQueue = this.c;
            boolean isEmpty = priorityQueue.isEmpty();
            arrayDeque = this.a;
            if (isEmpty) {
                break;
            }
            a aVar = (a) Util.castNonNull(priorityQueue.poll());
            aVar.clear();
            arrayDeque.add(aVar);
        }
        a aVar2 = this.d;
        if (aVar2 != null) {
            aVar2.clear();
            arrayDeque.add(aVar2);
            this.d = null;
        }
    }

    public abstract String getName();

    public void release() {
    }

    public void setPositionUs(long j) {
        this.e = j;
    }

    @Nullable
    public SubtitleInputBuffer dequeueInputBuffer() throws SubtitleDecoderException {
        Assertions.checkState(this.d == null);
        ArrayDeque<a> arrayDeque = this.a;
        if (arrayDeque.isEmpty()) {
            return null;
        }
        a pollFirst = arrayDeque.pollFirst();
        this.d = pollFirst;
        return pollFirst;
    }

    @Nullable
    public SubtitleOutputBuffer dequeueOutputBuffer() throws SubtitleDecoderException {
        ArrayDeque<SubtitleOutputBuffer> arrayDeque = this.b;
        if (arrayDeque.isEmpty()) {
            return null;
        }
        while (true) {
            PriorityQueue<a> priorityQueue = this.c;
            if (priorityQueue.isEmpty() || ((a) Util.castNonNull(priorityQueue.peek())).i > this.e) {
                return null;
            }
            a aVar = (a) Util.castNonNull(priorityQueue.poll());
            boolean isEndOfStream = aVar.isEndOfStream();
            ArrayDeque<a> arrayDeque2 = this.a;
            if (isEndOfStream) {
                SubtitleOutputBuffer subtitleOutputBuffer = (SubtitleOutputBuffer) Util.castNonNull(arrayDeque.pollFirst());
                subtitleOutputBuffer.addFlag(4);
                aVar.clear();
                arrayDeque2.add(aVar);
                return subtitleOutputBuffer;
            }
            b(aVar);
            if (c()) {
                f0 a2 = a();
                SubtitleOutputBuffer subtitleOutputBuffer2 = (SubtitleOutputBuffer) Util.castNonNull(arrayDeque.pollFirst());
                subtitleOutputBuffer2.setContent(aVar.i, a2, Long.MAX_VALUE);
                aVar.clear();
                arrayDeque2.add(aVar);
                return subtitleOutputBuffer2;
            }
            aVar.clear();
            arrayDeque2.add(aVar);
        }
        return null;
    }

    public void queueInputBuffer(SubtitleInputBuffer subtitleInputBuffer) throws SubtitleDecoderException {
        Assertions.checkArgument(subtitleInputBuffer == this.d);
        a aVar = (a) subtitleInputBuffer;
        if (aVar.isDecodeOnly()) {
            aVar.clear();
            this.a.add(aVar);
        } else {
            long j = this.f;
            this.f = 1 + j;
            aVar.n = j;
            this.c.add(aVar);
        }
        this.d = null;
    }
}
