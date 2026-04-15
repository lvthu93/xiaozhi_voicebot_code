package com.google.android.exoplayer2.text.webvtt;

import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.SimpleSubtitleDecoder;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.text.SubtitleDecoderException;
import com.google.android.exoplayer2.text.webvtt.WebvttCueParser;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;

public final class Mp4WebvttDecoder extends SimpleSubtitleDecoder {
    public final ParsableByteArray o = new ParsableByteArray();

    public Mp4WebvttDecoder() {
        super("Mp4WebvttDecoder");
    }

    public final Subtitle e(byte[] bArr, int i, boolean z) throws SubtitleDecoderException {
        Cue cue;
        ParsableByteArray parsableByteArray = this.o;
        parsableByteArray.reset(bArr, i);
        ArrayList arrayList = new ArrayList();
        while (parsableByteArray.bytesLeft() > 0) {
            if (parsableByteArray.bytesLeft() >= 8) {
                int readInt = parsableByteArray.readInt();
                if (parsableByteArray.readInt() == 1987343459) {
                    int i2 = readInt - 8;
                    CharSequence charSequence = null;
                    Cue.Builder builder = null;
                    while (i2 > 0) {
                        if (i2 >= 8) {
                            int readInt2 = parsableByteArray.readInt();
                            int readInt3 = parsableByteArray.readInt();
                            int i3 = readInt2 - 8;
                            String fromUtf8Bytes = Util.fromUtf8Bytes(parsableByteArray.getData(), parsableByteArray.getPosition(), i3);
                            parsableByteArray.skipBytes(i3);
                            i2 = (i2 - 8) - i3;
                            if (readInt3 == 1937011815) {
                                Pattern pattern = WebvttCueParser.a;
                                WebvttCueParser.d dVar = new WebvttCueParser.d();
                                WebvttCueParser.e(fromUtf8Bytes, dVar);
                                builder = dVar.toCueBuilder();
                            } else if (readInt3 == 1885436268) {
                                charSequence = WebvttCueParser.f((String) null, Collections.emptyList(), fromUtf8Bytes.trim());
                            }
                        } else {
                            throw new SubtitleDecoderException("Incomplete vtt cue box header found.");
                        }
                    }
                    if (charSequence == null) {
                        charSequence = "";
                    }
                    if (builder != null) {
                        cue = builder.setText(charSequence).build();
                    } else {
                        Pattern pattern2 = WebvttCueParser.a;
                        WebvttCueParser.d dVar2 = new WebvttCueParser.d();
                        dVar2.c = charSequence;
                        cue = dVar2.toCueBuilder().build();
                    }
                    arrayList.add(cue);
                } else {
                    parsableByteArray.skipBytes(readInt - 8);
                }
            } else {
                throw new SubtitleDecoderException("Incomplete Mp4Webvtt Top Level box header found.");
            }
        }
        return new b7(arrayList);
    }
}
