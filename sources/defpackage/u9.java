package defpackage;

import com.google.android.exoplayer2.extractor.BinarySearchSeeker;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

/* renamed from: u9  reason: default package */
public final class u9 extends BinarySearchSeeker {

    /* renamed from: u9$a */
    public static final class a implements BinarySearchSeeker.TimestampSeeker {
        public final TimestampAdjuster a;
        public final ParsableByteArray b = new ParsableByteArray();

        public a(TimestampAdjuster timestampAdjuster) {
            this.a = timestampAdjuster;
        }

        public void onSeekFinished() {
            this.b.reset(Util.f);
        }

        public BinarySearchSeeker.TimestampSearchResult searchForTimestamp(ExtractorInput extractorInput, long j) throws IOException {
            int b2;
            long position = extractorInput.getPosition();
            int min = (int) Math.min(20000, extractorInput.getLength() - position);
            ParsableByteArray parsableByteArray = this.b;
            parsableByteArray.reset(min);
            extractorInput.peekFully(parsableByteArray.getData(), 0, min);
            int i = -1;
            long j2 = -9223372036854775807L;
            int i2 = -1;
            while (parsableByteArray.bytesLeft() >= 4) {
                if (u9.b(parsableByteArray.getData(), parsableByteArray.getPosition()) != 442) {
                    parsableByteArray.skipBytes(1);
                } else {
                    parsableByteArray.skipBytes(4);
                    long readScrValueFromPack = v9.readScrValueFromPack(parsableByteArray);
                    if (readScrValueFromPack != -9223372036854775807L) {
                        long adjustTsTimestamp = this.a.adjustTsTimestamp(readScrValueFromPack);
                        if (adjustTsTimestamp > j) {
                            if (j2 == -9223372036854775807L) {
                                return BinarySearchSeeker.TimestampSearchResult.overestimatedResult(adjustTsTimestamp, position);
                            }
                            return BinarySearchSeeker.TimestampSearchResult.targetFoundResult(position + ((long) i2));
                        } else if (100000 + adjustTsTimestamp > j) {
                            return BinarySearchSeeker.TimestampSearchResult.targetFoundResult(position + ((long) parsableByteArray.getPosition()));
                        } else {
                            i2 = parsableByteArray.getPosition();
                            j2 = adjustTsTimestamp;
                        }
                    }
                    int limit = parsableByteArray.limit();
                    if (parsableByteArray.bytesLeft() >= 10) {
                        parsableByteArray.skipBytes(9);
                        int readUnsignedByte = parsableByteArray.readUnsignedByte() & 7;
                        if (parsableByteArray.bytesLeft() >= readUnsignedByte) {
                            parsableByteArray.skipBytes(readUnsignedByte);
                            if (parsableByteArray.bytesLeft() >= 4) {
                                if (u9.b(parsableByteArray.getData(), parsableByteArray.getPosition()) == 443) {
                                    parsableByteArray.skipBytes(4);
                                    int readUnsignedShort = parsableByteArray.readUnsignedShort();
                                    if (parsableByteArray.bytesLeft() < readUnsignedShort) {
                                        parsableByteArray.setPosition(limit);
                                    } else {
                                        parsableByteArray.skipBytes(readUnsignedShort);
                                    }
                                }
                                while (true) {
                                    if (parsableByteArray.bytesLeft() < 4 || (b2 = u9.b(parsableByteArray.getData(), parsableByteArray.getPosition())) == 442 || b2 == 441 || (b2 >>> 8) != 1) {
                                        break;
                                    }
                                    parsableByteArray.skipBytes(4);
                                    if (parsableByteArray.bytesLeft() < 2) {
                                        parsableByteArray.setPosition(limit);
                                        break;
                                    }
                                    parsableByteArray.setPosition(Math.min(parsableByteArray.limit(), parsableByteArray.getPosition() + parsableByteArray.readUnsignedShort()));
                                }
                            } else {
                                parsableByteArray.setPosition(limit);
                            }
                        } else {
                            parsableByteArray.setPosition(limit);
                        }
                    } else {
                        parsableByteArray.setPosition(limit);
                    }
                    i = parsableByteArray.getPosition();
                }
            }
            if (j2 != -9223372036854775807L) {
                return BinarySearchSeeker.TimestampSearchResult.underestimatedResult(j2, position + ((long) i));
            }
            return BinarySearchSeeker.TimestampSearchResult.d;
        }
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public u9(TimestampAdjuster timestampAdjuster, long j, long j2) {
        super(new BinarySearchSeeker.DefaultSeekTimestampConverter(), new a(timestampAdjuster), j, j + 1, 0, j2, 188, 1000);
        TimestampAdjuster timestampAdjuster2 = timestampAdjuster;
    }

    public static int b(byte[] bArr, int i) {
        return (bArr[i + 3] & 255) | ((bArr[i] & 255) << 24) | ((bArr[i + 1] & 255) << 16) | ((bArr[i + 2] & 255) << 8);
    }
}
