package com.google.android.exoplayer2.extractor.ogg;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import defpackage.d3;
import defpackage.pb;
import java.io.IOException;
import org.checkerframework.checker.nullness.qual.EnsuresNonNullIf;

public class OggExtractor implements Extractor {
    public ExtractorOutput a;
    public pb b;
    public boolean c;

    @EnsuresNonNullIf(expression = {"streamReader"}, result = true)
    public final boolean a(ExtractorInput extractorInput) throws IOException {
        m7 m7Var = new m7();
        if (m7Var.populate(extractorInput, true) && (m7Var.a & 2) == 2) {
            int min = Math.min(m7Var.e, 8);
            ParsableByteArray parsableByteArray = new ParsableByteArray(min);
            extractorInput.peekFully(parsableByteArray.getData(), 0, min);
            parsableByteArray.setPosition(0);
            if (d3.verifyBitstreamType(parsableByteArray)) {
                this.b = new d3();
            } else {
                parsableByteArray.setPosition(0);
                if (xd.verifyBitstreamType(parsableByteArray)) {
                    this.b = new xd();
                } else {
                    parsableByteArray.setPosition(0);
                    if (t7.verifyBitstreamType(parsableByteArray)) {
                        this.b = new t7();
                    }
                }
            }
            return true;
        }
        return false;
    }

    public void init(ExtractorOutput extractorOutput) {
        this.a = extractorOutput;
    }

    public int read(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
        boolean z;
        l7 l7Var;
        boolean z2;
        ExtractorInput extractorInput2 = extractorInput;
        Assertions.checkStateNotNull(this.a);
        if (this.b == null) {
            if (a(extractorInput)) {
                extractorInput.resetPeekPosition();
            } else {
                throw new ParserException("Failed to determine bitstream type");
            }
        }
        if (!this.c) {
            TrackOutput track = this.a.track(0, 1);
            this.a.endTracks();
            pb pbVar = this.b;
            pbVar.c = this.a;
            pbVar.b = track;
            pbVar.d(true);
            this.c = true;
        }
        pb pbVar2 = this.b;
        Assertions.checkStateNotNull(pbVar2.b);
        Util.castNonNull(pbVar2.c);
        int i = pbVar2.h;
        l7 l7Var2 = pbVar2.a;
        if (i == 0) {
            while (true) {
                if (l7Var2.populate(extractorInput2)) {
                    pbVar2.k = extractorInput.getPosition() - pbVar2.f;
                    if (!pbVar2.c(l7Var2.getPayload(), pbVar2.f, pbVar2.j)) {
                        z = true;
                        break;
                    }
                    pbVar2.f = extractorInput.getPosition();
                } else {
                    pbVar2.h = 3;
                    z = false;
                    break;
                }
            }
            if (z) {
                Format format = pbVar2.j.a;
                pbVar2.i = format.ad;
                if (!pbVar2.m) {
                    pbVar2.b.format(format);
                    pbVar2.m = true;
                }
                d3.a aVar = pbVar2.j.b;
                if (aVar != null) {
                    pbVar2.d = aVar;
                } else if (extractorInput.getLength() == -1) {
                    pbVar2.d = new pb.b();
                } else {
                    m7 pageHeader = l7Var2.getPageHeader();
                    if ((pageHeader.a & 4) != 0) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    l7Var = l7Var2;
                    pbVar2.d = new d1(pbVar2, pbVar2.f, extractorInput.getLength(), (long) (pageHeader.d + pageHeader.e), pageHeader.b, z2);
                    pbVar2.h = 2;
                    l7Var.trimPayload();
                }
                l7Var = l7Var2;
                pbVar2.h = 2;
                l7Var.trimPayload();
            }
            return -1;
        } else if (i != 1) {
            if (i == 2) {
                Util.castNonNull(pbVar2.d);
                long read = pbVar2.d.read(extractorInput2);
                if (read >= 0) {
                    positionHolder.a = read;
                    return 1;
                }
                if (read < -1) {
                    pbVar2.a(-(read + 2));
                }
                if (!pbVar2.l) {
                    pbVar2.c.seekMap((SeekMap) Assertions.checkStateNotNull(pbVar2.d.createSeekMap()));
                    pbVar2.l = true;
                }
                if (pbVar2.k > 0 || l7Var2.populate(extractorInput2)) {
                    pbVar2.k = 0;
                    ParsableByteArray payload = l7Var2.getPayload();
                    long b2 = pbVar2.b(payload);
                    if (b2 >= 0) {
                        long j = pbVar2.g;
                        if (j + b2 >= pbVar2.e) {
                            pbVar2.b.sampleData(payload, payload.limit());
                            pbVar2.b.sampleMetadata((j * 1000000) / ((long) pbVar2.i), 1, payload.limit(), 0, (TrackOutput.CryptoData) null);
                            pbVar2.e = -1;
                        }
                    }
                    pbVar2.g += b2;
                } else {
                    pbVar2.h = 3;
                }
            } else if (i != 3) {
                throw new IllegalStateException();
            }
            return -1;
        } else {
            extractorInput2.skipFully((int) pbVar2.f);
            pbVar2.h = 2;
        }
        return 0;
    }

    public void release() {
    }

    public void seek(long j, long j2) {
        pb pbVar = this.b;
        if (pbVar != null) {
            pbVar.a.reset();
            if (j == 0) {
                pbVar.d(!pbVar.l);
            } else if (pbVar.h != 0) {
                pbVar.e = (((long) pbVar.i) * j2) / 1000000;
                ((n7) Util.castNonNull(pbVar.d)).startSeek(pbVar.e);
                pbVar.h = 2;
            }
        }
    }

    public boolean sniff(ExtractorInput extractorInput) throws IOException {
        try {
            return a(extractorInput);
        } catch (ParserException unused) {
            return false;
        }
    }
}
