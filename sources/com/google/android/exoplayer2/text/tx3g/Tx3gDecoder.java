package com.google.android.exoplayer2.text.tx3g;

import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.text.style.UnderlineSpan;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.SimpleSubtitleDecoder;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.text.SubtitleDecoderException;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Charsets;
import java.util.List;

public final class Tx3gDecoder extends SimpleSubtitleDecoder {
    public final ParsableByteArray o = new ParsableByteArray();
    public final boolean p;
    public final int q;
    public final int r;
    public final String s;
    public final float t;
    public final int u;

    public Tx3gDecoder(List<byte[]> list) {
        super("Tx3gDecoder");
        String str = "sans-serif";
        boolean z = false;
        if (list.size() == 1 && (list.get(0).length == 48 || list.get(0).length == 53)) {
            byte[] bArr = list.get(0);
            this.q = bArr[24];
            this.r = ((bArr[26] & 255) << 24) | ((bArr[27] & 255) << 16) | ((bArr[28] & 255) << 8) | (bArr[29] & 255);
            this.s = "Serif".equals(Util.fromUtf8Bytes(bArr, 43, bArr.length - 43)) ? "serif" : str;
            int i = bArr[25] * 20;
            this.u = i;
            z = (bArr[0] & 32) != 0 ? true : z;
            this.p = z;
            if (z) {
                this.t = Util.constrainValue(((float) ((bArr[11] & 255) | ((bArr[10] & 255) << 8))) / ((float) i), 0.0f, 0.95f);
            } else {
                this.t = 0.85f;
            }
        } else {
            this.q = 0;
            this.r = -1;
            this.s = str;
            this.p = false;
            this.t = 0.85f;
            this.u = -1;
        }
    }

    public static void f(SpannableStringBuilder spannableStringBuilder, int i, int i2, int i3, int i4, int i5) {
        boolean z;
        boolean z2;
        if (i != i2) {
            int i6 = i5 | 33;
            boolean z3 = true;
            if ((i & 1) != 0) {
                z = true;
            } else {
                z = false;
            }
            if ((i & 2) != 0) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (z) {
                if (z2) {
                    spannableStringBuilder.setSpan(new StyleSpan(3), i3, i4, i6);
                } else {
                    spannableStringBuilder.setSpan(new StyleSpan(1), i3, i4, i6);
                }
            } else if (z2) {
                spannableStringBuilder.setSpan(new StyleSpan(2), i3, i4, i6);
            }
            if ((i & 4) == 0) {
                z3 = false;
            }
            if (z3) {
                spannableStringBuilder.setSpan(new UnderlineSpan(), i3, i4, i6);
            }
            if (!z3 && !z && !z2) {
                spannableStringBuilder.setSpan(new StyleSpan(0), i3, i4, i6);
            }
        }
    }

    public final Subtitle e(byte[] bArr, int i, boolean z) throws SubtitleDecoderException {
        boolean z2;
        String str;
        boolean z3;
        boolean z4;
        boolean z5;
        int i2;
        int i3;
        int i4;
        float f;
        int i5;
        char peekChar;
        ParsableByteArray parsableByteArray = this.o;
        parsableByteArray.reset(bArr, i);
        int i6 = 1;
        int i7 = 2;
        if (parsableByteArray.bytesLeft() >= 2) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2) {
            int readUnsignedShort = parsableByteArray.readUnsignedShort();
            if (readUnsignedShort == 0) {
                str = "";
            } else if (parsableByteArray.bytesLeft() < 2 || !((peekChar = parsableByteArray.peekChar()) == 65279 || peekChar == 65534)) {
                str = parsableByteArray.readString(readUnsignedShort, Charsets.c);
            } else {
                str = parsableByteArray.readString(readUnsignedShort, Charsets.e);
            }
            if (str.isEmpty()) {
                return yc.f;
            }
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str);
            f(spannableStringBuilder, this.q, 0, 0, spannableStringBuilder.length(), 16711680);
            int length = spannableStringBuilder.length();
            int i8 = this.r;
            if (i8 != -1) {
                spannableStringBuilder.setSpan(new ForegroundColorSpan(((i8 & 255) << 24) | (i8 >>> 8)), 0, length, 16711713);
            }
            int length2 = spannableStringBuilder.length();
            String str2 = this.s;
            if (str2 != "sans-serif") {
                spannableStringBuilder.setSpan(new TypefaceSpan(str2), 0, length2, 16711713);
            }
            float f2 = this.t;
            while (parsableByteArray.bytesLeft() >= 8) {
                int position = parsableByteArray.getPosition();
                int readInt = parsableByteArray.readInt();
                int readInt2 = parsableByteArray.readInt();
                if (readInt2 == 1937013100) {
                    if (parsableByteArray.bytesLeft() >= i7) {
                        z4 = true;
                    } else {
                        z4 = false;
                    }
                    if (z4) {
                        int readUnsignedShort2 = parsableByteArray.readUnsignedShort();
                        int i9 = 0;
                        while (i9 < readUnsignedShort2) {
                            if (parsableByteArray.bytesLeft() >= 12) {
                                z5 = true;
                            } else {
                                z5 = false;
                            }
                            if (z5) {
                                int readUnsignedShort3 = parsableByteArray.readUnsignedShort();
                                int readUnsignedShort4 = parsableByteArray.readUnsignedShort();
                                parsableByteArray.skipBytes(i7);
                                int readUnsignedByte = parsableByteArray.readUnsignedByte();
                                parsableByteArray.skipBytes(i6);
                                int readInt3 = parsableByteArray.readInt();
                                if (readUnsignedShort4 > spannableStringBuilder.length()) {
                                    int length3 = spannableStringBuilder.length();
                                    i3 = readInt3;
                                    i2 = i9;
                                    StringBuilder sb = new StringBuilder(68);
                                    sb.append("Truncating styl end (");
                                    sb.append(readUnsignedShort4);
                                    sb.append(") to cueText.length() (");
                                    sb.append(length3);
                                    sb.append(").");
                                    Log.w("Tx3gDecoder", sb.toString());
                                    readUnsignedShort4 = spannableStringBuilder.length();
                                } else {
                                    i3 = readInt3;
                                    i2 = i9;
                                }
                                int i10 = readUnsignedShort4;
                                if (readUnsignedShort3 >= i10) {
                                    StringBuilder sb2 = new StringBuilder(60);
                                    sb2.append("Ignoring styl with start (");
                                    sb2.append(readUnsignedShort3);
                                    sb2.append(") >= end (");
                                    sb2.append(i10);
                                    sb2.append(").");
                                    Log.w("Tx3gDecoder", sb2.toString());
                                    i4 = readUnsignedShort2;
                                    i5 = i2;
                                    f = f2;
                                } else {
                                    int i11 = i3;
                                    i5 = i2;
                                    f = f2;
                                    int i12 = readUnsignedShort3;
                                    i4 = readUnsignedShort2;
                                    f(spannableStringBuilder, readUnsignedByte, this.q, readUnsignedShort3, i10, 0);
                                    if (i11 != i8) {
                                        spannableStringBuilder.setSpan(new ForegroundColorSpan(((i11 & 255) << 24) | (i11 >>> 8)), i12, i10, 33);
                                    }
                                }
                                i9 = i5 + 1;
                                f2 = f;
                                readUnsignedShort2 = i4;
                                i6 = 1;
                                i7 = 2;
                            } else {
                                throw new SubtitleDecoderException("Unexpected subtitle format.");
                            }
                        }
                        float f3 = f2;
                    } else {
                        throw new SubtitleDecoderException("Unexpected subtitle format.");
                    }
                } else {
                    float f4 = f2;
                    if (readInt2 != 1952608120 || !this.p) {
                        f2 = f4;
                    } else {
                        if (parsableByteArray.bytesLeft() >= 2) {
                            z3 = true;
                        } else {
                            z3 = false;
                        }
                        if (z3) {
                            f2 = Util.constrainValue(((float) parsableByteArray.readUnsignedShort()) / ((float) this.u), 0.0f, 0.95f);
                        } else {
                            throw new SubtitleDecoderException("Unexpected subtitle format.");
                        }
                    }
                }
                parsableByteArray.setPosition(position + readInt);
                i6 = 1;
                i7 = 2;
            }
            return new yc(new Cue.Builder().setText(spannableStringBuilder).setLine(f2, 0).setLineAnchor(0).build());
        }
        throw new SubtitleDecoderException("Unexpected subtitle format.");
    }
}
