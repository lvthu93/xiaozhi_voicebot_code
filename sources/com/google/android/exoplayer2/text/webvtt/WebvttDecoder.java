package com.google.android.exoplayer2.text.webvtt;

import android.text.TextUtils;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.text.SimpleSubtitleDecoder;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.text.SubtitleDecoderException;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.util.ArrayList;

public final class WebvttDecoder extends SimpleSubtitleDecoder {
    public final ParsableByteArray o = new ParsableByteArray();
    public final x0 p = new x0();

    public WebvttDecoder() {
        super("WebvttDecoder");
    }

    public final Subtitle e(byte[] bArr, int i, boolean z) throws SubtitleDecoderException {
        WebvttCueInfo parseCue;
        ParsableByteArray parsableByteArray = this.o;
        parsableByteArray.reset(bArr, i);
        ArrayList arrayList = new ArrayList();
        try {
            WebvttParserUtil.validateWebvttHeaderLine(parsableByteArray);
            do {
            } while (!TextUtils.isEmpty(parsableByteArray.readLine()));
            ArrayList arrayList2 = new ArrayList();
            while (true) {
                char c = 65535;
                int i2 = 0;
                while (c == 65535) {
                    i2 = parsableByteArray.getPosition();
                    String readLine = parsableByteArray.readLine();
                    if (readLine == null) {
                        c = 0;
                    } else if ("STYLE".equals(readLine)) {
                        c = 2;
                    } else if (readLine.startsWith("NOTE")) {
                        c = 1;
                    } else {
                        c = 3;
                    }
                }
                parsableByteArray.setPosition(i2);
                if (c == 0) {
                    return new df(arrayList2);
                }
                if (c == 1) {
                    do {
                    } while (!TextUtils.isEmpty(parsableByteArray.readLine()));
                } else if (c == 2) {
                    if (arrayList2.isEmpty()) {
                        parsableByteArray.readLine();
                        arrayList.addAll(this.p.parseBlock(parsableByteArray));
                    } else {
                        throw new SubtitleDecoderException("A style block was found after the first cue.");
                    }
                } else if (c == 3 && (parseCue = WebvttCueParser.parseCue(parsableByteArray, arrayList)) != null) {
                    arrayList2.add(parseCue);
                }
            }
        } catch (ParserException e) {
            throw new SubtitleDecoderException((Throwable) e);
        }
    }
}
