package defpackage;

import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.mkv.EbmlProcessor;
import com.google.android.exoplayer2.util.Assertions;
import java.io.IOException;
import java.util.ArrayDeque;

/* renamed from: c1  reason: default package */
public final class c1 implements z1 {
    public final byte[] a = new byte[8];
    public final ArrayDeque<a> b = new ArrayDeque<>();
    public final ld c = new ld();
    public EbmlProcessor d;
    public int e;
    public int f;
    public long g;

    /* renamed from: c1$a */
    public static final class a {
        public final int a;
        public final long b;

        public a(int i, long j) {
            this.a = i;
            this.b = j;
        }
    }

    public void init(EbmlProcessor ebmlProcessor) {
        this.d = ebmlProcessor;
    }

    public boolean read(ExtractorInput extractorInput) throws IOException {
        String str;
        double d2;
        int parseUnsignedVarintLength;
        int assembleVarint;
        ExtractorInput extractorInput2 = extractorInput;
        Assertions.checkStateNotNull(this.d);
        while (true) {
            ArrayDeque<a> arrayDeque = this.b;
            a peek = arrayDeque.peek();
            if (peek == null || extractorInput.getPosition() < peek.b) {
                int i = this.e;
                byte[] bArr = this.a;
                ld ldVar = this.c;
                if (i == 0) {
                    long readUnsignedVarint = ldVar.readUnsignedVarint(extractorInput2, true, false, 4);
                    if (readUnsignedVarint == -2) {
                        extractorInput.resetPeekPosition();
                        while (true) {
                            extractorInput2.peekFully(bArr, 0, 4);
                            parseUnsignedVarintLength = ld.parseUnsignedVarintLength(bArr[0]);
                            if (parseUnsignedVarintLength != -1 && parseUnsignedVarintLength <= 4) {
                                assembleVarint = (int) ld.assembleVarint(bArr, parseUnsignedVarintLength, false);
                                if (this.d.isLevel1Element(assembleVarint)) {
                                    break;
                                }
                            }
                            extractorInput2.skipFully(1);
                        }
                        extractorInput2.skipFully(parseUnsignedVarintLength);
                        readUnsignedVarint = (long) assembleVarint;
                    }
                    if (readUnsignedVarint == -1) {
                        return false;
                    }
                    this.f = (int) readUnsignedVarint;
                    this.e = 1;
                }
                if (this.e == 1) {
                    this.g = ldVar.readUnsignedVarint(extractorInput2, false, true, 8);
                    this.e = 2;
                }
                int elementType = this.d.getElementType(this.f);
                if (elementType == 0) {
                    extractorInput2.skipFully((int) this.g);
                    this.e = 0;
                } else if (elementType != 1) {
                    long j = 0;
                    if (elementType == 2) {
                        long j2 = this.g;
                        if (j2 <= 8) {
                            EbmlProcessor ebmlProcessor = this.d;
                            int i2 = this.f;
                            int i3 = (int) j2;
                            extractorInput2.readFully(bArr, 0, i3);
                            for (int i4 = 0; i4 < i3; i4++) {
                                j = (j << 8) | ((long) (bArr[i4] & 255));
                            }
                            ebmlProcessor.integerElement(i2, j);
                            this.e = 0;
                            return true;
                        }
                        long j3 = this.g;
                        StringBuilder sb = new StringBuilder(42);
                        sb.append("Invalid integer size: ");
                        sb.append(j3);
                        throw new ParserException(sb.toString());
                    } else if (elementType == 3) {
                        long j4 = this.g;
                        if (j4 <= 2147483647L) {
                            EbmlProcessor ebmlProcessor2 = this.d;
                            int i5 = this.f;
                            int i6 = (int) j4;
                            if (i6 == 0) {
                                str = "";
                            } else {
                                byte[] bArr2 = new byte[i6];
                                extractorInput2.readFully(bArr2, 0, i6);
                                while (i6 > 0) {
                                    int i7 = i6 - 1;
                                    if (bArr2[i7] != 0) {
                                        break;
                                    }
                                    i6 = i7;
                                }
                                str = new String(bArr2, 0, i6);
                            }
                            ebmlProcessor2.stringElement(i5, str);
                            this.e = 0;
                            return true;
                        }
                        long j5 = this.g;
                        StringBuilder sb2 = new StringBuilder(41);
                        sb2.append("String element size: ");
                        sb2.append(j5);
                        throw new ParserException(sb2.toString());
                    } else if (elementType == 4) {
                        this.d.binaryElement(this.f, (int) this.g, extractorInput2);
                        this.e = 0;
                        return true;
                    } else if (elementType == 5) {
                        long j6 = this.g;
                        if (j6 == 4 || j6 == 8) {
                            EbmlProcessor ebmlProcessor3 = this.d;
                            int i8 = this.f;
                            int i9 = (int) j6;
                            extractorInput2.readFully(bArr, 0, i9);
                            for (int i10 = 0; i10 < i9; i10++) {
                                j = (j << 8) | ((long) (bArr[i10] & 255));
                            }
                            if (i9 == 4) {
                                d2 = (double) Float.intBitsToFloat((int) j);
                            } else {
                                d2 = Double.longBitsToDouble(j);
                            }
                            ebmlProcessor3.floatElement(i8, d2);
                            this.e = 0;
                            return true;
                        }
                        long j7 = this.g;
                        StringBuilder sb3 = new StringBuilder(40);
                        sb3.append("Invalid float size: ");
                        sb3.append(j7);
                        throw new ParserException(sb3.toString());
                    } else {
                        throw new ParserException(y2.d(32, "Invalid element type ", elementType));
                    }
                } else {
                    long position = extractorInput.getPosition();
                    arrayDeque.push(new a(this.f, this.g + position));
                    this.d.startMasterElement(this.f, position, this.g);
                    this.e = 0;
                    return true;
                }
            } else {
                this.d.endMasterElement(arrayDeque.pop().a);
                return true;
            }
        }
    }

    public void reset() {
        this.e = 0;
        this.b.clear();
        this.c.reset();
    }
}
