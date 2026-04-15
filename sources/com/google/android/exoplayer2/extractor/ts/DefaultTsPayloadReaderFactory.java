package com.google.android.exoplayer2.extractor.ts;

import android.util.SparseArray;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.CodecSpecificDataUtil;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.common.collect.ImmutableList;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

public final class DefaultTsPayloadReaderFactory implements TsPayloadReader.Factory {
    public final int a;
    public final List<Format> b;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    public DefaultTsPayloadReaderFactory() {
        this(0);
    }

    public final List<Format> a(TsPayloadReader.EsInfo esInfo) {
        boolean z;
        String str;
        int i;
        List<byte[]> list;
        boolean b2 = b(32);
        List<Format> list2 = this.b;
        if (b2) {
            return list2;
        }
        ParsableByteArray parsableByteArray = new ParsableByteArray(esInfo.d);
        while (parsableByteArray.bytesLeft() > 0) {
            int readUnsignedByte = parsableByteArray.readUnsignedByte();
            int position = parsableByteArray.getPosition() + parsableByteArray.readUnsignedByte();
            if (readUnsignedByte == 134) {
                ArrayList arrayList = new ArrayList();
                int readUnsignedByte2 = parsableByteArray.readUnsignedByte() & 31;
                for (int i2 = 0; i2 < readUnsignedByte2; i2++) {
                    String readString = parsableByteArray.readString(3);
                    int readUnsignedByte3 = parsableByteArray.readUnsignedByte();
                    boolean z2 = true;
                    if ((readUnsignedByte3 & 128) != 0) {
                        z = true;
                    } else {
                        z = false;
                    }
                    if (z) {
                        i = readUnsignedByte3 & 63;
                        str = "application/cea-708";
                    } else {
                        str = "application/cea-608";
                        i = 1;
                    }
                    byte readUnsignedByte4 = (byte) parsableByteArray.readUnsignedByte();
                    parsableByteArray.skipBytes(1);
                    if (z) {
                        if ((readUnsignedByte4 & 64) == 0) {
                            z2 = false;
                        }
                        list = CodecSpecificDataUtil.buildCea708InitializationData(z2);
                    } else {
                        list = null;
                    }
                    arrayList.add(new Format.Builder().setSampleMimeType(str).setLanguage(readString).setAccessibilityChannel(i).setInitializationData(list).build());
                }
                list2 = arrayList;
            }
            parsableByteArray.setPosition(position);
        }
        return list2;
    }

    public final boolean b(int i) {
        return (i & this.a) != 0;
    }

    public SparseArray<TsPayloadReader> createInitialPayloadReaders() {
        return new SparseArray<>();
    }

    @Nullable
    public TsPayloadReader createPayloadReader(int i, TsPayloadReader.EsInfo esInfo) {
        if (i == 2) {
            return new PesReader(new H262Reader(new a(a(esInfo))));
        }
        if (i == 3 || i == 4) {
            return new PesReader(new MpegAudioReader(esInfo.b));
        }
        if (i == 21) {
            return new PesReader(new Id3Reader());
        }
        if (i != 27) {
            if (i == 36) {
                return new PesReader(new H265Reader(new SeiReader(a(esInfo))));
            }
            if (i == 89) {
                return new PesReader(new DvbSubtitleReader(esInfo.c));
            }
            if (i != 138) {
                if (i == 172) {
                    return new PesReader(new Ac4Reader(esInfo.b));
                }
                if (i == 257) {
                    return new SectionReader(new PassthroughSectionPayloadReader("application/vnd.dvb.ait"));
                }
                if (i != 129) {
                    if (i != 130) {
                        if (i != 134) {
                            if (i != 135) {
                                switch (i) {
                                    case 15:
                                        if (b(2)) {
                                            return null;
                                        }
                                        return new PesReader(new AdtsReader(false, esInfo.b));
                                    case 16:
                                        return new PesReader(new H263Reader(new a(a(esInfo))));
                                    case 17:
                                        if (b(2)) {
                                            return null;
                                        }
                                        return new PesReader(new LatmReader(esInfo.b));
                                    default:
                                        return null;
                                }
                            }
                        } else if (b(16)) {
                            return null;
                        } else {
                            return new SectionReader(new PassthroughSectionPayloadReader("application/x-scte35"));
                        }
                    } else if (!b(64)) {
                        return null;
                    }
                }
                return new PesReader(new Ac3Reader(esInfo.b));
            }
            return new PesReader(new DtsReader(esInfo.b));
        } else if (b(4)) {
            return null;
        } else {
            return new PesReader(new H264Reader(new SeiReader(a(esInfo)), b(1), b(8)));
        }
    }

    public DefaultTsPayloadReaderFactory(int i) {
        this(i, ImmutableList.of());
    }

    public DefaultTsPayloadReaderFactory(int i, List<Format> list) {
        this.a = i;
        this.b = list;
    }
}
