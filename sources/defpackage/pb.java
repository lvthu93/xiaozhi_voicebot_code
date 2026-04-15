package defpackage;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.util.ParsableByteArray;
import defpackage.d3;
import java.io.IOException;
import org.checkerframework.checker.nullness.qual.EnsuresNonNullIf;

/* renamed from: pb  reason: default package */
public abstract class pb {
    public final l7 a = new l7();
    public TrackOutput b;
    public ExtractorOutput c;
    public n7 d;
    public long e;
    public long f;
    public long g;
    public int h;
    public int i;
    public a j = new a();
    public long k;
    public boolean l;
    public boolean m;

    /* renamed from: pb$a */
    public static class a {
        public Format a;
        public d3.a b;
    }

    /* renamed from: pb$b */
    public static final class b implements n7 {
        public SeekMap createSeekMap() {
            return new SeekMap.Unseekable(-9223372036854775807L);
        }

        public long read(ExtractorInput extractorInput) {
            return -1;
        }

        public void startSeek(long j) {
        }
    }

    public void a(long j2) {
        this.g = j2;
    }

    public abstract long b(ParsableByteArray parsableByteArray);

    @EnsuresNonNullIf(expression = {"#3.format"}, result = false)
    public abstract boolean c(ParsableByteArray parsableByteArray, long j2, a aVar) throws IOException;

    public void d(boolean z) {
        if (z) {
            this.j = new a();
            this.f = 0;
            this.h = 0;
        } else {
            this.h = 1;
        }
        this.e = -1;
        this.g = 0;
    }
}
