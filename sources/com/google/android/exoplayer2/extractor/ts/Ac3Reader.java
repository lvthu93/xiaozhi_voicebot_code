package com.google.android.exoplayer2.extractor.ts;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.audio.Ac3Util;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;

public final class Ac3Reader implements ElementaryStreamReader {
    public final ParsableBitArray a;
    public final ParsableByteArray b;
    @Nullable
    public final String c;
    public String d;
    public TrackOutput e;
    public int f;
    public int g;
    public boolean h;
    public long i;
    public Format j;
    public int k;
    public long l;

    public Ac3Reader() {
        this((String) null);
    }

    public void consume(ParsableByteArray parsableByteArray) {
        boolean z;
        boolean z2;
        boolean z3;
        Assertions.checkStateNotNull(this.e);
        while (parsableByteArray.bytesLeft() > 0) {
            int i2 = this.f;
            ParsableByteArray parsableByteArray2 = this.b;
            boolean z4 = true;
            if (i2 == 0) {
                while (true) {
                    if (parsableByteArray.bytesLeft() <= 0) {
                        z = false;
                        break;
                    } else if (!this.h) {
                        if (parsableByteArray.readUnsignedByte() == 11) {
                            z3 = true;
                        } else {
                            z3 = false;
                        }
                        this.h = z3;
                    } else {
                        int readUnsignedByte = parsableByteArray.readUnsignedByte();
                        if (readUnsignedByte == 119) {
                            this.h = false;
                            z = true;
                            break;
                        }
                        if (readUnsignedByte == 11) {
                            z2 = true;
                        } else {
                            z2 = false;
                        }
                        this.h = z2;
                    }
                }
                if (z) {
                    this.f = 1;
                    parsableByteArray2.getData()[0] = MqttWireMessage.MESSAGE_TYPE_UNSUBACK;
                    parsableByteArray2.getData()[1] = 119;
                    this.g = 2;
                }
            } else if (i2 == 1) {
                byte[] data = parsableByteArray2.getData();
                int min = Math.min(parsableByteArray.bytesLeft(), 128 - this.g);
                parsableByteArray.readBytes(data, this.g, min);
                int i3 = this.g + min;
                this.g = i3;
                if (i3 != 128) {
                    z4 = false;
                }
                if (z4) {
                    ParsableBitArray parsableBitArray = this.a;
                    parsableBitArray.setPosition(0);
                    Ac3Util.SyncFrameInfo parseAc3SyncframeInfo = Ac3Util.parseAc3SyncframeInfo(parsableBitArray);
                    Format format = this.j;
                    if (format == null || parseAc3SyncframeInfo.c != format.ac || parseAc3SyncframeInfo.b != format.ad || !Util.areEqual(parseAc3SyncframeInfo.a, format.p)) {
                        Format build = new Format.Builder().setId(this.d).setSampleMimeType(parseAc3SyncframeInfo.a).setChannelCount(parseAc3SyncframeInfo.c).setSampleRate(parseAc3SyncframeInfo.b).setLanguage(this.c).build();
                        this.j = build;
                        this.e.format(build);
                    }
                    this.k = parseAc3SyncframeInfo.d;
                    this.i = (((long) parseAc3SyncframeInfo.e) * 1000000) / ((long) this.j.ad);
                    parsableByteArray2.setPosition(0);
                    this.e.sampleData(parsableByteArray2, 128);
                    this.f = 2;
                }
            } else if (i2 == 2) {
                int min2 = Math.min(parsableByteArray.bytesLeft(), this.k - this.g);
                this.e.sampleData(parsableByteArray, min2);
                int i4 = this.g + min2;
                this.g = i4;
                int i5 = this.k;
                if (i4 == i5) {
                    this.e.sampleMetadata(this.l, 1, i5, 0, (TrackOutput.CryptoData) null);
                    this.l += this.i;
                    this.f = 0;
                }
            }
        }
    }

    public void createTracks(ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator trackIdGenerator) {
        trackIdGenerator.generateNewId();
        this.d = trackIdGenerator.getFormatId();
        this.e = extractorOutput.track(trackIdGenerator.getTrackId(), 1);
    }

    public void packetFinished() {
    }

    public void packetStarted(long j2, int i2) {
        this.l = j2;
    }

    public void seek() {
        this.f = 0;
        this.g = 0;
        this.h = false;
    }

    public Ac3Reader(@Nullable String str) {
        ParsableBitArray parsableBitArray = new ParsableBitArray(new byte[128]);
        this.a = parsableBitArray;
        this.b = new ParsableByteArray(parsableBitArray.a);
        this.f = 0;
        this.c = str;
    }
}
