package com.google.android.exoplayer2.source;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.decoder.CryptoInfo;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.source.SampleQueue;
import com.google.android.exoplayer2.upstream.Allocation;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DataReader;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public final class c {
    public final Allocator a;
    public final int b;
    public final ParsableByteArray c = new ParsableByteArray(32);
    public a d;
    public a e;
    public a f;
    public long g;

    public static final class a {
        public final long a;
        public final long b;
        public boolean c;
        @Nullable
        public Allocation d;
        @Nullable
        public a e;

        public a(long j, int i) {
            this.a = j;
            this.b = j + ((long) i);
        }

        public a clear() {
            this.d = null;
            a aVar = this.e;
            this.e = null;
            return aVar;
        }

        public void initialize(Allocation allocation, a aVar) {
            this.d = allocation;
            this.e = aVar;
            this.c = true;
        }

        public int translateOffset(long j) {
            return ((int) (j - this.a)) + this.d.b;
        }
    }

    public c(Allocator allocator) {
        this.a = allocator;
        int individualAllocationLength = allocator.getIndividualAllocationLength();
        this.b = individualAllocationLength;
        a aVar = new a(0, individualAllocationLength);
        this.d = aVar;
        this.e = aVar;
        this.f = aVar;
    }

    public static a c(a aVar, long j, ByteBuffer byteBuffer, int i) {
        while (j >= aVar.b) {
            aVar = aVar.e;
        }
        while (i > 0) {
            int min = Math.min(i, (int) (aVar.b - j));
            byteBuffer.put(aVar.d.a, aVar.translateOffset(j), min);
            i -= min;
            j += (long) min;
            if (j == aVar.b) {
                aVar = aVar.e;
            }
        }
        return aVar;
    }

    public static a d(a aVar, long j, byte[] bArr, int i) {
        while (j >= aVar.b) {
            aVar = aVar.e;
        }
        int i2 = i;
        while (i2 > 0) {
            int min = Math.min(i2, (int) (aVar.b - j));
            System.arraycopy(aVar.d.a, aVar.translateOffset(j), bArr, i - i2, min);
            i2 -= min;
            j += (long) min;
            if (j == aVar.b) {
                aVar = aVar.e;
            }
        }
        return aVar;
    }

    public static a e(a aVar, DecoderInputBuffer decoderInputBuffer, SampleQueue.a aVar2, ParsableByteArray parsableByteArray) {
        a aVar3;
        boolean z;
        int i;
        DecoderInputBuffer decoderInputBuffer2 = decoderInputBuffer;
        SampleQueue.a aVar4 = aVar2;
        ParsableByteArray parsableByteArray2 = parsableByteArray;
        if (decoderInputBuffer.isEncrypted()) {
            long j = aVar4.b;
            parsableByteArray2.reset(1);
            a d2 = d(aVar, j, parsableByteArray.getData(), 1);
            long j2 = j + 1;
            byte b2 = parsableByteArray.getData()[0];
            if ((b2 & 128) != 0) {
                z = true;
            } else {
                z = false;
            }
            byte b3 = b2 & Byte.MAX_VALUE;
            CryptoInfo cryptoInfo = decoderInputBuffer2.f;
            byte[] bArr = cryptoInfo.a;
            if (bArr == null) {
                cryptoInfo.a = new byte[16];
            } else {
                Arrays.fill(bArr, (byte) 0);
            }
            aVar3 = d(d2, j2, cryptoInfo.a, b3);
            long j3 = j2 + ((long) b3);
            if (z) {
                parsableByteArray2.reset(2);
                aVar3 = d(aVar3, j3, parsableByteArray.getData(), 2);
                j3 += 2;
                i = parsableByteArray.readUnsignedShort();
            } else {
                i = 1;
            }
            int[] iArr = cryptoInfo.d;
            if (iArr == null || iArr.length < i) {
                iArr = new int[i];
            }
            int[] iArr2 = iArr;
            int[] iArr3 = cryptoInfo.e;
            if (iArr3 == null || iArr3.length < i) {
                iArr3 = new int[i];
            }
            int[] iArr4 = iArr3;
            if (z) {
                int i2 = i * 6;
                parsableByteArray2.reset(i2);
                aVar3 = d(aVar3, j3, parsableByteArray.getData(), i2);
                j3 += (long) i2;
                parsableByteArray2.setPosition(0);
                for (int i3 = 0; i3 < i; i3++) {
                    iArr2[i3] = parsableByteArray.readUnsignedShort();
                    iArr4[i3] = parsableByteArray.readUnsignedIntToInt();
                }
            } else {
                iArr2[0] = 0;
                iArr4[0] = aVar4.a - ((int) (j3 - aVar4.b));
            }
            TrackOutput.CryptoData cryptoData = (TrackOutput.CryptoData) Util.castNonNull(aVar4.c);
            cryptoInfo.set(i, iArr2, iArr4, cryptoData.b, cryptoInfo.a, cryptoData.a, cryptoData.c, cryptoData.d);
            long j4 = aVar4.b;
            int i4 = (int) (j3 - j4);
            aVar4.b = j4 + ((long) i4);
            aVar4.a -= i4;
        } else {
            aVar3 = aVar;
        }
        if (decoderInputBuffer.hasSupplementalData()) {
            parsableByteArray2.reset(4);
            a d3 = d(aVar3, aVar4.b, parsableByteArray.getData(), 4);
            int readUnsignedIntToInt = parsableByteArray.readUnsignedIntToInt();
            aVar4.b += 4;
            aVar4.a -= 4;
            decoderInputBuffer2.ensureSpaceForWrite(readUnsignedIntToInt);
            a c2 = c(d3, aVar4.b, decoderInputBuffer2.g, readUnsignedIntToInt);
            aVar4.b += (long) readUnsignedIntToInt;
            int i5 = aVar4.a - readUnsignedIntToInt;
            aVar4.a = i5;
            decoderInputBuffer2.resetSupplementalData(i5);
            return c(c2, aVar4.b, decoderInputBuffer2.j, aVar4.a);
        }
        decoderInputBuffer2.ensureSpaceForWrite(aVar4.a);
        return c(aVar3, aVar4.b, decoderInputBuffer2.g, aVar4.a);
    }

    public final void a(a aVar) {
        if (aVar.c) {
            a aVar2 = this.f;
            int i = (((int) (aVar2.a - aVar.a)) / this.b) + (aVar2.c ? 1 : 0);
            Allocation[] allocationArr = new Allocation[i];
            for (int i2 = 0; i2 < i; i2++) {
                allocationArr[i2] = aVar.d;
                aVar = aVar.clear();
            }
            this.a.release(allocationArr);
        }
    }

    public final int b(int i) {
        a aVar = this.f;
        if (!aVar.c) {
            aVar.initialize(this.a.allocate(), new a(this.f.b, this.b));
        }
        return Math.min(i, (int) (this.f.b - this.g));
    }

    public void discardDownstreamTo(long j) {
        a aVar;
        if (j != -1) {
            while (true) {
                aVar = this.d;
                if (j < aVar.b) {
                    break;
                }
                this.a.release(aVar.d);
                this.d = this.d.clear();
            }
            if (this.e.a < aVar.a) {
                this.e = aVar;
            }
        }
    }

    public void discardUpstreamSampleBytes(long j) {
        this.g = j;
        int i = this.b;
        if (j != 0) {
            a aVar = this.d;
            if (j != aVar.a) {
                while (this.g > aVar.b) {
                    aVar = aVar.e;
                }
                a aVar2 = aVar.e;
                a(aVar2);
                long j2 = aVar.b;
                a aVar3 = new a(j2, i);
                aVar.e = aVar3;
                if (this.g == j2) {
                    aVar = aVar3;
                }
                this.f = aVar;
                if (this.e == aVar2) {
                    this.e = aVar3;
                    return;
                }
                return;
            }
        }
        a(this.d);
        a aVar4 = new a(this.g, i);
        this.d = aVar4;
        this.e = aVar4;
        this.f = aVar4;
    }

    public long getTotalBytesWritten() {
        return this.g;
    }

    public void peekToBuffer(DecoderInputBuffer decoderInputBuffer, SampleQueue.a aVar) {
        e(this.e, decoderInputBuffer, aVar, this.c);
    }

    public void readToBuffer(DecoderInputBuffer decoderInputBuffer, SampleQueue.a aVar) {
        this.e = e(this.e, decoderInputBuffer, aVar, this.c);
    }

    public void reset() {
        a(this.d);
        a aVar = new a(0, this.b);
        this.d = aVar;
        this.e = aVar;
        this.f = aVar;
        this.g = 0;
        this.a.trim();
    }

    public void rewind() {
        this.e = this.d;
    }

    public int sampleData(DataReader dataReader, int i, boolean z) throws IOException {
        int b2 = b(i);
        a aVar = this.f;
        int read = dataReader.read(aVar.d.a, aVar.translateOffset(this.g), b2);
        if (read != -1) {
            long j = this.g + ((long) read);
            this.g = j;
            a aVar2 = this.f;
            if (j == aVar2.b) {
                this.f = aVar2.e;
            }
            return read;
        } else if (z) {
            return -1;
        } else {
            throw new EOFException();
        }
    }

    public void sampleData(ParsableByteArray parsableByteArray, int i) {
        while (i > 0) {
            int b2 = b(i);
            a aVar = this.f;
            parsableByteArray.readBytes(aVar.d.a, aVar.translateOffset(this.g), b2);
            i -= b2;
            long j = this.g + ((long) b2);
            this.g = j;
            a aVar2 = this.f;
            if (j == aVar2.b) {
                this.f = aVar2.e;
            }
        }
    }
}
