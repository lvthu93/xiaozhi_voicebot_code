package defpackage;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.mp4.TrackEncryptionBox;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.io.IOException;
import org.mozilla.javascript.Token;

/* renamed from: nc  reason: default package */
public final class nc {
    public f1 a;
    public long b;
    public long c;
    public int d;
    public int e;
    public long[] f = new long[0];
    public int[] g = new int[0];
    public int[] h = new int[0];
    public int[] i = new int[0];
    public long[] j = new long[0];
    public boolean[] k = new boolean[0];
    public boolean l;
    public boolean[] m = new boolean[0];
    @Nullable
    public TrackEncryptionBox n;
    public final ParsableByteArray o = new ParsableByteArray();
    public boolean p;
    public long q;
    public boolean r;

    public void fillEncryptionData(ExtractorInput extractorInput) throws IOException {
        ParsableByteArray parsableByteArray = this.o;
        extractorInput.readFully(parsableByteArray.getData(), 0, parsableByteArray.limit());
        parsableByteArray.setPosition(0);
        this.p = false;
    }

    public long getSamplePresentationTimeUs(int i2) {
        return this.j[i2] + ((long) this.i[i2]);
    }

    public void initEncryptionData(int i2) {
        this.o.reset(i2);
        this.l = true;
        this.p = true;
    }

    public void initTables(int i2, int i3) {
        this.d = i2;
        this.e = i3;
        if (this.g.length < i2) {
            this.f = new long[i2];
            this.g = new int[i2];
        }
        if (this.h.length < i3) {
            int i4 = (i3 * Token.CATCH) / 100;
            this.h = new int[i4];
            this.i = new int[i4];
            this.j = new long[i4];
            this.k = new boolean[i4];
            this.m = new boolean[i4];
        }
    }

    public void reset() {
        this.d = 0;
        this.q = 0;
        this.r = false;
        this.l = false;
        this.p = false;
        this.n = null;
    }

    public boolean sampleHasSubsampleEncryptionTable(int i2) {
        return this.l && this.m[i2];
    }

    public void fillEncryptionData(ParsableByteArray parsableByteArray) {
        ParsableByteArray parsableByteArray2 = this.o;
        parsableByteArray.readBytes(parsableByteArray2.getData(), 0, parsableByteArray2.limit());
        parsableByteArray2.setPosition(0);
        this.p = false;
    }
}
