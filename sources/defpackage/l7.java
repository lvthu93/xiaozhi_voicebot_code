package defpackage;

import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorUtil;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.io.IOException;
import java.util.Arrays;

/* renamed from: l7  reason: default package */
public final class l7 {
    public final m7 a = new m7();
    public final ParsableByteArray b = new ParsableByteArray(new byte[65025], 0);
    public int c = -1;
    public int d;
    public boolean e;

    public m7 getPageHeader() {
        return this.a;
    }

    public ParsableByteArray getPayload() {
        return this.b;
    }

    public boolean populate(ExtractorInput extractorInput) throws IOException {
        boolean z;
        boolean z2;
        int i;
        int i2;
        int i3;
        if (extractorInput != null) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkState(z);
        boolean z3 = this.e;
        ParsableByteArray parsableByteArray = this.b;
        if (z3) {
            this.e = false;
            parsableByteArray.reset(0);
        }
        while (!this.e) {
            int i4 = this.c;
            m7 m7Var = this.a;
            if (i4 < 0) {
                if (!m7Var.skipToNextPage(extractorInput) || !m7Var.populate(extractorInput, true)) {
                    return false;
                }
                int i5 = m7Var.d;
                if ((m7Var.a & 1) == 1 && parsableByteArray.limit() == 0) {
                    this.d = 0;
                    int i6 = 0;
                    do {
                        int i7 = this.d;
                        int i8 = 0 + i7;
                        if (i8 >= m7Var.c) {
                            break;
                        }
                        this.d = i7 + 1;
                        i3 = m7Var.f[i8];
                        i6 += i3;
                    } while (i3 == 255);
                    i5 += i6;
                    i2 = this.d + 0;
                } else {
                    i2 = 0;
                }
                if (!ExtractorUtil.skipFullyQuietly(extractorInput, i5)) {
                    return false;
                }
                this.c = i2;
            }
            int i9 = this.c;
            this.d = 0;
            int i10 = 0;
            do {
                int i11 = this.d;
                int i12 = i9 + i11;
                if (i12 >= m7Var.c) {
                    break;
                }
                this.d = i11 + 1;
                i = m7Var.f[i12];
                i10 += i;
            } while (i == 255);
            int i13 = this.c + this.d;
            if (i10 > 0) {
                parsableByteArray.ensureCapacity(parsableByteArray.limit() + i10);
                if (!ExtractorUtil.readFullyQuietly(extractorInput, parsableByteArray.getData(), parsableByteArray.limit(), i10)) {
                    return false;
                }
                parsableByteArray.setLimit(parsableByteArray.limit() + i10);
                if (m7Var.f[i13 - 1] != 255) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                this.e = z2;
            }
            if (i13 == m7Var.c) {
                i13 = -1;
            }
            this.c = i13;
        }
        return true;
    }

    public void reset() {
        this.a.reset();
        this.b.reset(0);
        this.c = -1;
        this.e = false;
    }

    public void trimPayload() {
        ParsableByteArray parsableByteArray = this.b;
        if (parsableByteArray.getData().length != 65025) {
            parsableByteArray.reset(Arrays.copyOf(parsableByteArray.getData(), Math.max(65025, parsableByteArray.limit())), parsableByteArray.limit());
        }
    }
}
