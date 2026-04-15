package defpackage;

import java.io.IOException;
import java.io.OutputStream;

/* renamed from: o7  reason: default package */
public final class o7 implements za {
    public final /* synthetic */ lc c;
    public final /* synthetic */ OutputStream f;

    public o7(OutputStream outputStream, lc lcVar) {
        this.c = lcVar;
        this.f = outputStream;
    }

    public final void close() throws IOException {
        this.f.close();
    }

    public final void flush() throws IOException {
        this.f.flush();
    }

    public final lc timeout() {
        return this.c;
    }

    public final String toString() {
        return "sink(" + this.f + ")";
    }

    public final void write(ck ckVar, long j) throws IOException {
        jd.a(ckVar.f, 0, j);
        while (j > 0) {
            this.c.throwIfReached();
            qa qaVar = ckVar.c;
            int min = (int) Math.min(j, (long) (qaVar.c - qaVar.b));
            this.f.write(qaVar.a, qaVar.b, min);
            int i = qaVar.b + min;
            qaVar.b = i;
            long j2 = (long) min;
            j -= j2;
            ckVar.f -= j2;
            if (i == qaVar.c) {
                ckVar.c = qaVar.a();
                sa.a(qaVar);
            }
        }
    }
}
