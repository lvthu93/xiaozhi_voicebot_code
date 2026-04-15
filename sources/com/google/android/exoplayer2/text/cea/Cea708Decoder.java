package com.google.android.exoplayer2.text.cea;

import android.text.Layout;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.SubtitleDecoderException;
import com.google.android.exoplayer2.text.SubtitleInputBuffer;
import com.google.android.exoplayer2.text.SubtitleOutputBuffer;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.CodecSpecificDataUtil;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import defpackage.e0;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.mozilla.javascript.Token;

public final class Cea708Decoder extends e0 {
    public final ParsableByteArray g = new ParsableByteArray();
    public final ParsableBitArray h = new ParsableBitArray();
    public int i = -1;
    public final int j;
    public final b[] k;
    public b l;
    @Nullable
    public List<Cue> m;
    @Nullable
    public List<Cue> n;
    @Nullable
    public c o;
    public int p;

    public static final class a {
        public static final db c = new db(4);
        public final Cue a;
        public final int b;

        public a(CharSequence charSequence, Layout.Alignment alignment, float f, int i, int i2, float f2, int i3, float f3, boolean z, int i4, int i5) {
            Cue.Builder size = new Cue.Builder().setText(charSequence).setTextAlignment(alignment).setLine(f, i).setLineAnchor(i2).setPosition(f2).setPositionAnchor(i3).setSize(f3);
            if (z) {
                size.setWindowColor(i4);
            }
            this.a = size.build();
            this.b = i5;
        }
    }

    public static final class b {
        public static final int[] aa = {0, 0, 0, 0, 0, 0, 2};
        public static final int[] ab = {3, 3, 3, 3, 3, 3, 1};
        public static final boolean[] ac = {false, false, false, true, true, true, false};
        public static final int[] ad;
        public static final int[] ae = {0, 1, 2, 3, 4, 3, 4};
        public static final int[] af = {0, 0, 0, 0, 0, 3, 3};
        public static final int[] ag;
        public static final int w = getArgbColorFromCeaColor(2, 2, 2, 0);
        public static final int x;
        public static final int y;
        public static final int[] z = {0, 0, 0, 0, 0, 2, 0};
        public final ArrayList a = new ArrayList();
        public final SpannableStringBuilder b = new SpannableStringBuilder();
        public boolean c;
        public boolean d;
        public int e;
        public boolean f;
        public int g;
        public int h;
        public int i;
        public int j;
        public boolean k;
        public int l;
        public int m;
        public int n;
        public int o;
        public int p;
        public int q;
        public int r;
        public int s;
        public int t;
        public int u;
        public int v;

        static {
            int argbColorFromCeaColor = getArgbColorFromCeaColor(0, 0, 0, 0);
            x = argbColorFromCeaColor;
            int argbColorFromCeaColor2 = getArgbColorFromCeaColor(0, 0, 0, 3);
            y = argbColorFromCeaColor2;
            ad = new int[]{argbColorFromCeaColor, argbColorFromCeaColor2, argbColorFromCeaColor, argbColorFromCeaColor, argbColorFromCeaColor2, argbColorFromCeaColor, argbColorFromCeaColor};
            ag = new int[]{argbColorFromCeaColor, argbColorFromCeaColor, argbColorFromCeaColor, argbColorFromCeaColor, argbColorFromCeaColor, argbColorFromCeaColor2, argbColorFromCeaColor2};
        }

        public b() {
            reset();
        }

        public static int getArgbColorFromCeaColor(int i2, int i3, int i4) {
            return getArgbColorFromCeaColor(i2, i3, i4, 0);
        }

        public void append(char c2) {
            SpannableStringBuilder spannableStringBuilder = this.b;
            if (c2 == 10) {
                ArrayList arrayList = this.a;
                arrayList.add(buildSpannableString());
                spannableStringBuilder.clear();
                if (this.p != -1) {
                    this.p = 0;
                }
                if (this.q != -1) {
                    this.q = 0;
                }
                if (this.r != -1) {
                    this.r = 0;
                }
                if (this.t != -1) {
                    this.t = 0;
                }
                while (true) {
                    if ((this.k && arrayList.size() >= this.j) || arrayList.size() >= 15) {
                        arrayList.remove(0);
                    } else {
                        return;
                    }
                }
            } else {
                spannableStringBuilder.append(c2);
            }
        }

        public void backspace() {
            SpannableStringBuilder spannableStringBuilder = this.b;
            int length = spannableStringBuilder.length();
            if (length > 0) {
                spannableStringBuilder.delete(length - 1, length);
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:19:0x005a  */
        /* JADX WARNING: Removed duplicated region for block: B:20:0x0065  */
        /* JADX WARNING: Removed duplicated region for block: B:23:0x0086  */
        /* JADX WARNING: Removed duplicated region for block: B:24:0x0088  */
        /* JADX WARNING: Removed duplicated region for block: B:29:0x0090  */
        /* JADX WARNING: Removed duplicated region for block: B:30:0x0093  */
        /* JADX WARNING: Removed duplicated region for block: B:35:0x00a0  */
        /* JADX WARNING: Removed duplicated region for block: B:36:0x00a3  */
        @androidx.annotation.Nullable
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public com.google.android.exoplayer2.text.cea.Cea708Decoder.a build() {
            /*
                r14 = this;
                boolean r0 = r14.isEmpty()
                if (r0 == 0) goto L_0x0008
                r0 = 0
                return r0
            L_0x0008:
                android.text.SpannableStringBuilder r2 = new android.text.SpannableStringBuilder
                r2.<init>()
                r0 = 0
                r1 = 0
            L_0x000f:
                java.util.ArrayList r3 = r14.a
                int r4 = r3.size()
                if (r1 >= r4) goto L_0x0028
                java.lang.Object r3 = r3.get(r1)
                java.lang.CharSequence r3 = (java.lang.CharSequence) r3
                r2.append(r3)
                r3 = 10
                r2.append(r3)
                int r1 = r1 + 1
                goto L_0x000f
            L_0x0028:
                android.text.SpannableString r1 = r14.buildSpannableString()
                r2.append(r1)
                int r1 = r14.l
                r3 = 3
                r4 = 2
                r5 = 1
                if (r1 == 0) goto L_0x0053
                if (r1 == r5) goto L_0x0050
                if (r1 == r4) goto L_0x004d
                if (r1 != r3) goto L_0x003d
                goto L_0x0053
            L_0x003d:
                java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
                int r1 = r14.l
                r2 = 43
                java.lang.String r3 = "Unexpected justification value: "
                java.lang.String r1 = defpackage.y2.d(r2, r3, r1)
                r0.<init>(r1)
                throw r0
            L_0x004d:
                android.text.Layout$Alignment r1 = android.text.Layout.Alignment.ALIGN_CENTER
                goto L_0x0055
            L_0x0050:
                android.text.Layout$Alignment r1 = android.text.Layout.Alignment.ALIGN_OPPOSITE
                goto L_0x0055
            L_0x0053:
                android.text.Layout$Alignment r1 = android.text.Layout.Alignment.ALIGN_NORMAL
            L_0x0055:
                r4 = r1
                boolean r1 = r14.f
                if (r1 == 0) goto L_0x0065
                int r1 = r14.h
                float r1 = (float) r1
                r6 = 1120272384(0x42c60000, float:99.0)
                float r1 = r1 / r6
                int r7 = r14.g
                float r7 = (float) r7
                float r7 = r7 / r6
                goto L_0x0072
            L_0x0065:
                int r1 = r14.h
                float r1 = (float) r1
                r6 = 1129381888(0x43510000, float:209.0)
                float r1 = r1 / r6
                int r6 = r14.g
                float r6 = (float) r6
                r7 = 1116995584(0x42940000, float:74.0)
                float r7 = r6 / r7
            L_0x0072:
                r6 = 1063675494(0x3f666666, float:0.9)
                float r1 = r1 * r6
                r8 = 1028443341(0x3d4ccccd, float:0.05)
                float r9 = r1 + r8
                float r7 = r7 * r6
                float r6 = r7 + r8
                int r1 = r14.i
                int r7 = r1 / 3
                if (r7 != 0) goto L_0x0088
                r7 = 0
                goto L_0x008d
            L_0x0088:
                if (r7 != r5) goto L_0x008c
                r7 = 1
                goto L_0x008d
            L_0x008c:
                r7 = 2
            L_0x008d:
                int r1 = r1 % r3
                if (r1 != 0) goto L_0x0093
                r1 = 0
                r8 = 0
                goto L_0x009a
            L_0x0093:
                if (r1 != r5) goto L_0x0098
                r1 = 1
                r8 = 1
                goto L_0x009a
            L_0x0098:
                r1 = 2
                r8 = 2
            L_0x009a:
                int r11 = r14.o
                int r1 = x
                if (r11 == r1) goto L_0x00a3
                r0 = 1
                r10 = 1
                goto L_0x00a4
            L_0x00a3:
                r10 = 0
            L_0x00a4:
                com.google.android.exoplayer2.text.cea.Cea708Decoder$a r0 = new com.google.android.exoplayer2.text.cea.Cea708Decoder$a
                r5 = 0
                r12 = -8388609(0xffffffffff7fffff, float:-3.4028235E38)
                int r13 = r14.e
                r1 = r0
                r3 = r4
                r4 = r6
                r6 = r7
                r7 = r9
                r9 = r12
                r12 = r13
                r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12)
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.cea.Cea708Decoder.b.build():com.google.android.exoplayer2.text.cea.Cea708Decoder$a");
        }

        public SpannableString buildSpannableString() {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(this.b);
            int length = spannableStringBuilder.length();
            if (length > 0) {
                if (this.p != -1) {
                    spannableStringBuilder.setSpan(new StyleSpan(2), this.p, length, 33);
                }
                if (this.q != -1) {
                    spannableStringBuilder.setSpan(new UnderlineSpan(), this.q, length, 33);
                }
                if (this.r != -1) {
                    spannableStringBuilder.setSpan(new ForegroundColorSpan(this.s), this.r, length, 33);
                }
                if (this.t != -1) {
                    spannableStringBuilder.setSpan(new BackgroundColorSpan(this.u), this.t, length, 33);
                }
            }
            return new SpannableString(spannableStringBuilder);
        }

        public void clear() {
            this.a.clear();
            this.b.clear();
            this.p = -1;
            this.q = -1;
            this.r = -1;
            this.t = -1;
            this.v = 0;
        }

        public void defineWindow(boolean z2, boolean z3, boolean z4, int i2, boolean z5, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
            this.c = true;
            this.d = z2;
            this.k = z3;
            this.e = i2;
            this.f = z5;
            this.g = i3;
            this.h = i4;
            this.i = i7;
            int i10 = i5 + 1;
            if (this.j != i10) {
                this.j = i10;
                while (true) {
                    ArrayList arrayList = this.a;
                    if ((!z3 || arrayList.size() < this.j) && arrayList.size() < 15) {
                        break;
                    }
                    arrayList.remove(0);
                }
            }
            if (!(i8 == 0 || this.m == i8)) {
                this.m = i8;
                int i11 = i8 - 1;
                setWindowAttributes(ad[i11], y, ac[i11], 0, aa[i11], ab[i11], z[i11]);
            }
            if (i9 != 0 && this.n != i9) {
                this.n = i9;
                int i12 = i9 - 1;
                setPenAttributes(0, 1, 1, false, false, af[i12], ae[i12]);
                setPenColor(w, ag[i12], x);
            }
        }

        public boolean isDefined() {
            return this.c;
        }

        public boolean isEmpty() {
            return !isDefined() || (this.a.isEmpty() && this.b.length() == 0);
        }

        public boolean isVisible() {
            return this.d;
        }

        public void reset() {
            clear();
            this.c = false;
            this.d = false;
            this.e = 4;
            this.f = false;
            this.g = 0;
            this.h = 0;
            this.i = 0;
            this.j = 15;
            this.k = true;
            this.l = 0;
            this.m = 0;
            this.n = 0;
            int i2 = x;
            this.o = i2;
            this.s = w;
            this.u = i2;
        }

        public void setPenAttributes(int i2, int i3, int i4, boolean z2, boolean z3, int i5, int i6) {
            int i7 = this.p;
            SpannableStringBuilder spannableStringBuilder = this.b;
            if (i7 != -1) {
                if (!z2) {
                    spannableStringBuilder.setSpan(new StyleSpan(2), this.p, spannableStringBuilder.length(), 33);
                    this.p = -1;
                }
            } else if (z2) {
                this.p = spannableStringBuilder.length();
            }
            if (this.q != -1) {
                if (!z3) {
                    spannableStringBuilder.setSpan(new UnderlineSpan(), this.q, spannableStringBuilder.length(), 33);
                    this.q = -1;
                }
            } else if (z3) {
                this.q = spannableStringBuilder.length();
            }
        }

        public void setPenColor(int i2, int i3, int i4) {
            int i5 = this.r;
            SpannableStringBuilder spannableStringBuilder = this.b;
            if (!(i5 == -1 || this.s == i2)) {
                spannableStringBuilder.setSpan(new ForegroundColorSpan(this.s), this.r, spannableStringBuilder.length(), 33);
            }
            if (i2 != w) {
                this.r = spannableStringBuilder.length();
                this.s = i2;
            }
            if (!(this.t == -1 || this.u == i3)) {
                spannableStringBuilder.setSpan(new BackgroundColorSpan(this.u), this.t, spannableStringBuilder.length(), 33);
            }
            if (i3 != x) {
                this.t = spannableStringBuilder.length();
                this.u = i3;
            }
        }

        public void setPenLocation(int i2, int i3) {
            if (this.v != i2) {
                append(10);
            }
            this.v = i2;
        }

        public void setVisibility(boolean z2) {
            this.d = z2;
        }

        public void setWindowAttributes(int i2, int i3, boolean z2, int i4, int i5, int i6, int i7) {
            this.o = i2;
            this.l = i7;
        }

        /* JADX WARNING: Removed duplicated region for block: B:11:0x0025  */
        /* JADX WARNING: Removed duplicated region for block: B:12:0x0028  */
        /* JADX WARNING: Removed duplicated region for block: B:14:0x002b  */
        /* JADX WARNING: Removed duplicated region for block: B:15:0x002e  */
        /* JADX WARNING: Removed duplicated region for block: B:17:0x0031  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static int getArgbColorFromCeaColor(int r4, int r5, int r6, int r7) {
            /*
                r0 = 0
                r1 = 4
                com.google.android.exoplayer2.util.Assertions.checkIndex(r4, r0, r1)
                com.google.android.exoplayer2.util.Assertions.checkIndex(r5, r0, r1)
                com.google.android.exoplayer2.util.Assertions.checkIndex(r6, r0, r1)
                com.google.android.exoplayer2.util.Assertions.checkIndex(r7, r0, r1)
                r1 = 1
                r2 = 255(0xff, float:3.57E-43)
                if (r7 == 0) goto L_0x0021
                if (r7 == r1) goto L_0x0021
                r3 = 2
                if (r7 == r3) goto L_0x001e
                r3 = 3
                if (r7 == r3) goto L_0x001c
                goto L_0x0021
            L_0x001c:
                r7 = 0
                goto L_0x0023
            L_0x001e:
                r7 = 127(0x7f, float:1.78E-43)
                goto L_0x0023
            L_0x0021:
                r7 = 255(0xff, float:3.57E-43)
            L_0x0023:
                if (r4 <= r1) goto L_0x0028
                r4 = 255(0xff, float:3.57E-43)
                goto L_0x0029
            L_0x0028:
                r4 = 0
            L_0x0029:
                if (r5 <= r1) goto L_0x002e
                r5 = 255(0xff, float:3.57E-43)
                goto L_0x002f
            L_0x002e:
                r5 = 0
            L_0x002f:
                if (r6 <= r1) goto L_0x0033
                r0 = 255(0xff, float:3.57E-43)
            L_0x0033:
                int r4 = android.graphics.Color.argb(r7, r4, r5, r0)
                return r4
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.cea.Cea708Decoder.b.getArgbColorFromCeaColor(int, int, int, int):int");
        }
    }

    public static final class c {
        public final int a;
        public final int b;
        public final byte[] c;
        public int d = 0;

        public c(int i, int i2) {
            this.a = i;
            this.b = i2;
            this.c = new byte[((i2 * 2) - 1)];
        }
    }

    public Cea708Decoder(int i2, @Nullable List<byte[]> list) {
        this.j = i2 == -1 ? 1 : i2;
        if (list != null) {
            boolean parseCea708InitializationData = CodecSpecificDataUtil.parseCea708InitializationData(list);
        }
        this.k = new b[8];
        for (int i3 = 0; i3 < 8; i3++) {
            this.k[i3] = new b();
        }
        this.l = this.k[0];
    }

    public final f0 a() {
        List<Cue> list = this.m;
        this.n = list;
        return new f0((List) Assertions.checkNotNull(list));
    }

    public final void b(e0.a aVar) {
        boolean z;
        ByteBuffer byteBuffer = (ByteBuffer) Assertions.checkNotNull(aVar.g);
        byte[] array = byteBuffer.array();
        int limit = byteBuffer.limit();
        ParsableByteArray parsableByteArray = this.g;
        parsableByteArray.reset(array, limit);
        while (parsableByteArray.bytesLeft() >= 3) {
            int readUnsignedByte = parsableByteArray.readUnsignedByte() & 7;
            int i2 = readUnsignedByte & 3;
            boolean z2 = false;
            if ((readUnsignedByte & 4) == 4) {
                z = true;
            } else {
                z = false;
            }
            byte readUnsignedByte2 = (byte) parsableByteArray.readUnsignedByte();
            byte readUnsignedByte3 = (byte) parsableByteArray.readUnsignedByte();
            if ((i2 == 2 || i2 == 3) && z) {
                if (i2 == 3) {
                    d();
                    int i3 = (readUnsignedByte2 & 192) >> 6;
                    int i4 = this.i;
                    if (!(i4 == -1 || i3 == (i4 + 1) % 4)) {
                        f();
                        int i5 = this.i;
                        StringBuilder sb = new StringBuilder(71);
                        sb.append("Sequence number discontinuity. previous=");
                        sb.append(i5);
                        sb.append(" current=");
                        sb.append(i3);
                        Log.w("Cea708Decoder", sb.toString());
                    }
                    this.i = i3;
                    byte b2 = readUnsignedByte2 & 63;
                    if (b2 == 0) {
                        b2 = 64;
                    }
                    c cVar = new c(i3, b2);
                    this.o = cVar;
                    int i6 = cVar.d;
                    cVar.d = i6 + 1;
                    cVar.c[i6] = readUnsignedByte3;
                } else {
                    if (i2 == 2) {
                        z2 = true;
                    }
                    Assertions.checkArgument(z2);
                    c cVar2 = this.o;
                    if (cVar2 == null) {
                        Log.e("Cea708Decoder", "Encountered DTVCC_PACKET_DATA before DTVCC_PACKET_START");
                    } else {
                        int i7 = cVar2.d;
                        int i8 = i7 + 1;
                        byte[] bArr = cVar2.c;
                        bArr[i7] = readUnsignedByte2;
                        cVar2.d = i8 + 1;
                        bArr[i8] = readUnsignedByte3;
                    }
                }
                c cVar3 = this.o;
                if (cVar3.d == (cVar3.b * 2) - 1) {
                    d();
                }
            }
        }
    }

    public final boolean c() {
        return this.m != this.n;
    }

    public final void d() {
        c cVar = this.o;
        if (cVar != null) {
            int i2 = cVar.d;
            int i3 = (cVar.b * 2) - 1;
            if (i2 != i3) {
                StringBuilder sb = new StringBuilder(115);
                sb.append("DtvCcPacket ended prematurely; size is ");
                sb.append(i3);
                sb.append(", but current index is ");
                sb.append(i2);
                sb.append(" (sequence number ");
                sb.append(cVar.a);
                sb.append(");");
                Log.d("Cea708Decoder", sb.toString());
            }
            c cVar2 = this.o;
            byte[] bArr = cVar2.c;
            int i4 = cVar2.d;
            ParsableBitArray parsableBitArray = this.h;
            parsableBitArray.reset(bArr, i4);
            int i5 = 3;
            int readBits = parsableBitArray.readBits(3);
            int readBits2 = parsableBitArray.readBits(5);
            if (readBits == 7) {
                parsableBitArray.skipBits(2);
                readBits = parsableBitArray.readBits(6);
                if (readBits < 7) {
                    y2.t(44, "Invalid extended service number: ", readBits, "Cea708Decoder");
                }
            }
            if (readBits2 == 0) {
                if (readBits != 0) {
                    StringBuilder sb2 = new StringBuilder(59);
                    sb2.append("serviceNumber is non-zero (");
                    sb2.append(readBits);
                    sb2.append(") when blockSize is 0");
                    Log.w("Cea708Decoder", sb2.toString());
                }
            } else if (readBits == this.j) {
                boolean z = false;
                while (parsableBitArray.bitsLeft() > 0) {
                    int readBits3 = parsableBitArray.readBits(8);
                    if (readBits3 != 16) {
                        if (readBits3 <= 31) {
                            if (readBits3 != 0) {
                                if (readBits3 == i5) {
                                    this.m = e();
                                } else if (readBits3 != 8) {
                                    switch (readBits3) {
                                        case 12:
                                            f();
                                            break;
                                        case 13:
                                            this.l.append(10);
                                            break;
                                        case 14:
                                            break;
                                        default:
                                            if (readBits3 < 17 || readBits3 > 23) {
                                                if (readBits3 >= 24 && readBits3 <= 31) {
                                                    StringBuilder sb3 = new StringBuilder(54);
                                                    sb3.append("Currently unsupported COMMAND_P16 Command: ");
                                                    sb3.append(readBits3);
                                                    Log.w("Cea708Decoder", sb3.toString());
                                                    parsableBitArray.skipBits(16);
                                                    break;
                                                } else {
                                                    y2.t(31, "Invalid C0 command: ", readBits3, "Cea708Decoder");
                                                    break;
                                                }
                                            } else {
                                                StringBuilder sb4 = new StringBuilder(55);
                                                sb4.append("Currently unsupported COMMAND_EXT1 Command: ");
                                                sb4.append(readBits3);
                                                Log.w("Cea708Decoder", sb4.toString());
                                                parsableBitArray.skipBits(8);
                                                break;
                                            }
                                    }
                                } else {
                                    this.l.backspace();
                                }
                            }
                        } else if (readBits3 <= 127) {
                            if (readBits3 == 127) {
                                this.l.append(9835);
                            } else {
                                this.l.append((char) (readBits3 & 255));
                            }
                        } else if (readBits3 <= 159) {
                            b[] bVarArr = this.k;
                            switch (readBits3) {
                                case 128:
                                case Token.EMPTY /*129*/:
                                case 130:
                                case Token.LABEL /*131*/:
                                case Token.TARGET /*132*/:
                                case Token.LOOP /*133*/:
                                case Token.EXPR_VOID /*134*/:
                                case Token.EXPR_RESULT /*135*/:
                                    int i6 = readBits3 - 128;
                                    if (this.p != i6) {
                                        this.p = i6;
                                        this.l = bVarArr[i6];
                                        break;
                                    }
                                    break;
                                case Token.JSR /*136*/:
                                    for (int i7 = 1; i7 <= 8; i7++) {
                                        if (parsableBitArray.readBit()) {
                                            bVarArr[8 - i7].clear();
                                        }
                                    }
                                    break;
                                case Token.SCRIPT /*137*/:
                                    for (int i8 = 1; i8 <= 8; i8++) {
                                        if (parsableBitArray.readBit()) {
                                            bVarArr[8 - i8].setVisibility(true);
                                        }
                                    }
                                    break;
                                case Token.TYPEOFNAME /*138*/:
                                    for (int i9 = 1; i9 <= 8; i9++) {
                                        if (parsableBitArray.readBit()) {
                                            bVarArr[8 - i9].setVisibility(false);
                                        }
                                    }
                                    break;
                                case Token.USE_STACK /*139*/:
                                    for (int i10 = 1; i10 <= 8; i10++) {
                                        if (parsableBitArray.readBit()) {
                                            b bVar = bVarArr[8 - i10];
                                            bVar.setVisibility(!bVar.isVisible());
                                        }
                                    }
                                    break;
                                case 140:
                                    for (int i11 = 1; i11 <= 8; i11++) {
                                        if (parsableBitArray.readBit()) {
                                            bVarArr[8 - i11].reset();
                                        }
                                    }
                                    break;
                                case Token.SETELEM_OP /*141*/:
                                    parsableBitArray.skipBits(8);
                                    break;
                                case Token.LOCAL_BLOCK /*142*/:
                                    break;
                                case Token.SET_REF_OP /*143*/:
                                    f();
                                    break;
                                case Token.DOTDOT /*144*/:
                                    if (this.l.isDefined()) {
                                        this.l.setPenAttributes(parsableBitArray.readBits(4), parsableBitArray.readBits(2), parsableBitArray.readBits(2), parsableBitArray.readBit(), parsableBitArray.readBit(), parsableBitArray.readBits(i5), parsableBitArray.readBits(i5));
                                        break;
                                    } else {
                                        parsableBitArray.skipBits(16);
                                        break;
                                    }
                                case Token.COLONCOLON /*145*/:
                                    if (this.l.isDefined()) {
                                        int argbColorFromCeaColor = b.getArgbColorFromCeaColor(parsableBitArray.readBits(2), parsableBitArray.readBits(2), parsableBitArray.readBits(2), parsableBitArray.readBits(2));
                                        int argbColorFromCeaColor2 = b.getArgbColorFromCeaColor(parsableBitArray.readBits(2), parsableBitArray.readBits(2), parsableBitArray.readBits(2), parsableBitArray.readBits(2));
                                        parsableBitArray.skipBits(2);
                                        this.l.setPenColor(argbColorFromCeaColor, argbColorFromCeaColor2, b.getArgbColorFromCeaColor(parsableBitArray.readBits(2), parsableBitArray.readBits(2), parsableBitArray.readBits(2)));
                                        break;
                                    } else {
                                        parsableBitArray.skipBits(24);
                                        break;
                                    }
                                case Token.XML /*146*/:
                                    if (this.l.isDefined()) {
                                        parsableBitArray.skipBits(4);
                                        int readBits4 = parsableBitArray.readBits(4);
                                        parsableBitArray.skipBits(2);
                                        this.l.setPenLocation(readBits4, parsableBitArray.readBits(6));
                                        break;
                                    } else {
                                        parsableBitArray.skipBits(16);
                                        break;
                                    }
                                case Token.TO_DOUBLE /*151*/:
                                    if (this.l.isDefined()) {
                                        int argbColorFromCeaColor3 = b.getArgbColorFromCeaColor(parsableBitArray.readBits(2), parsableBitArray.readBits(2), parsableBitArray.readBits(2), parsableBitArray.readBits(2));
                                        int readBits5 = parsableBitArray.readBits(2);
                                        int argbColorFromCeaColor4 = b.getArgbColorFromCeaColor(parsableBitArray.readBits(2), parsableBitArray.readBits(2), parsableBitArray.readBits(2));
                                        if (parsableBitArray.readBit()) {
                                            readBits5 |= 4;
                                        }
                                        boolean readBit = parsableBitArray.readBit();
                                        int readBits6 = parsableBitArray.readBits(2);
                                        int readBits7 = parsableBitArray.readBits(2);
                                        int readBits8 = parsableBitArray.readBits(2);
                                        parsableBitArray.skipBits(8);
                                        this.l.setWindowAttributes(argbColorFromCeaColor3, argbColorFromCeaColor4, readBit, readBits5, readBits6, readBits7, readBits8);
                                        break;
                                    } else {
                                        parsableBitArray.skipBits(32);
                                        break;
                                    }
                                case Token.GET /*152*/:
                                case Token.SET /*153*/:
                                case Token.LET /*154*/:
                                case Token.CONST /*155*/:
                                case Token.SETCONST /*156*/:
                                case Token.SETCONSTVAR /*157*/:
                                case Token.ARRAYCOMP /*158*/:
                                case Token.LETEXPR /*159*/:
                                    int i12 = readBits3 - 152;
                                    b bVar2 = bVarArr[i12];
                                    parsableBitArray.skipBits(2);
                                    boolean readBit2 = parsableBitArray.readBit();
                                    boolean readBit3 = parsableBitArray.readBit();
                                    boolean readBit4 = parsableBitArray.readBit();
                                    int readBits9 = parsableBitArray.readBits(i5);
                                    boolean readBit5 = parsableBitArray.readBit();
                                    int readBits10 = parsableBitArray.readBits(7);
                                    int readBits11 = parsableBitArray.readBits(8);
                                    int readBits12 = parsableBitArray.readBits(4);
                                    int readBits13 = parsableBitArray.readBits(4);
                                    parsableBitArray.skipBits(2);
                                    int readBits14 = parsableBitArray.readBits(6);
                                    parsableBitArray.skipBits(2);
                                    bVar2.defineWindow(readBit2, readBit3, readBit4, readBits9, readBit5, readBits10, readBits11, readBits13, readBits14, readBits12, parsableBitArray.readBits(i5), parsableBitArray.readBits(i5));
                                    if (this.p != i12) {
                                        this.p = i12;
                                        this.l = bVarArr[i12];
                                        break;
                                    }
                                    break;
                                default:
                                    y2.t(31, "Invalid C1 command: ", readBits3, "Cea708Decoder");
                                    break;
                            }
                        } else if (readBits3 <= 255) {
                            this.l.append((char) (readBits3 & 255));
                        } else {
                            y2.t(33, "Invalid base command: ", readBits3, "Cea708Decoder");
                        }
                        i5 = 3;
                    } else {
                        int readBits15 = parsableBitArray.readBits(8);
                        if (readBits15 <= 31) {
                            if (readBits15 > 7) {
                                if (readBits15 <= 15) {
                                    parsableBitArray.skipBits(8);
                                } else if (readBits15 <= 23) {
                                    parsableBitArray.skipBits(16);
                                } else if (readBits15 <= 31) {
                                    parsableBitArray.skipBits(24);
                                }
                            }
                        } else if (readBits15 <= 127) {
                            if (readBits15 == 32) {
                                this.l.append(' ');
                            } else if (readBits15 == 33) {
                                this.l.append(160);
                            } else if (readBits15 == 37) {
                                this.l.append(8230);
                            } else if (readBits15 == 42) {
                                this.l.append(352);
                            } else if (readBits15 == 44) {
                                this.l.append(338);
                            } else if (readBits15 == 63) {
                                this.l.append(376);
                            } else if (readBits15 == 57) {
                                this.l.append(8482);
                            } else if (readBits15 == 58) {
                                this.l.append(353);
                            } else if (readBits15 == 60) {
                                this.l.append(339);
                            } else if (readBits15 != 61) {
                                switch (readBits15) {
                                    case 48:
                                        this.l.append(9608);
                                        break;
                                    case 49:
                                        this.l.append(8216);
                                        break;
                                    case 50:
                                        this.l.append(8217);
                                        break;
                                    case 51:
                                        this.l.append(8220);
                                        break;
                                    case 52:
                                        this.l.append(8221);
                                        break;
                                    case 53:
                                        this.l.append(8226);
                                        break;
                                    default:
                                        switch (readBits15) {
                                            case 118:
                                                this.l.append(8539);
                                                break;
                                            case 119:
                                                this.l.append(8540);
                                                break;
                                            case 120:
                                                this.l.append(8541);
                                                break;
                                            case 121:
                                                this.l.append(8542);
                                                break;
                                            case 122:
                                                this.l.append(9474);
                                                break;
                                            case 123:
                                                this.l.append(9488);
                                                break;
                                            case 124:
                                                this.l.append(9492);
                                                break;
                                            case Token.CATCH /*125*/:
                                                this.l.append(9472);
                                                break;
                                            case Token.FINALLY /*126*/:
                                                this.l.append(9496);
                                                break;
                                            case Token.VOID /*127*/:
                                                this.l.append(9484);
                                                break;
                                            default:
                                                y2.t(33, "Invalid G2 character: ", readBits15, "Cea708Decoder");
                                                break;
                                        }
                                }
                            } else {
                                this.l.append(8480);
                            }
                        } else if (readBits15 <= 159) {
                            if (readBits15 <= 135) {
                                parsableBitArray.skipBits(32);
                            } else if (readBits15 <= 143) {
                                parsableBitArray.skipBits(40);
                            } else if (readBits15 <= 159) {
                                parsableBitArray.skipBits(2);
                                parsableBitArray.skipBits(parsableBitArray.readBits(6) * 8);
                            }
                        } else if (readBits15 > 255) {
                            y2.t(37, "Invalid extended command: ", readBits15, "Cea708Decoder");
                        } else if (readBits15 == 160) {
                            this.l.append(13252);
                        } else {
                            y2.t(33, "Invalid G3 character: ", readBits15, "Cea708Decoder");
                            this.l.append('_');
                        }
                        i5 = 3;
                    }
                    z = true;
                    i5 = 3;
                }
                if (z) {
                    this.m = e();
                }
            }
            this.o = null;
        }
    }

    @Nullable
    public /* bridge */ /* synthetic */ SubtitleInputBuffer dequeueInputBuffer() throws SubtitleDecoderException {
        return super.dequeueInputBuffer();
    }

    @Nullable
    public /* bridge */ /* synthetic */ SubtitleOutputBuffer dequeueOutputBuffer() throws SubtitleDecoderException {
        return super.dequeueOutputBuffer();
    }

    public final List<Cue> e() {
        a build;
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < 8; i2++) {
            b[] bVarArr = this.k;
            if (!bVarArr[i2].isEmpty() && bVarArr[i2].isVisible() && (build = bVarArr[i2].build()) != null) {
                arrayList.add(build);
            }
        }
        Collections.sort(arrayList, a.c);
        ArrayList arrayList2 = new ArrayList(arrayList.size());
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            arrayList2.add(((a) arrayList.get(i3)).a);
        }
        return Collections.unmodifiableList(arrayList2);
    }

    public final void f() {
        for (int i2 = 0; i2 < 8; i2++) {
            this.k[i2].reset();
        }
    }

    public void flush() {
        super.flush();
        this.m = null;
        this.n = null;
        this.p = 0;
        this.l = this.k[0];
        f();
        this.o = null;
    }

    public String getName() {
        return "Cea708Decoder";
    }

    public /* bridge */ /* synthetic */ void queueInputBuffer(SubtitleInputBuffer subtitleInputBuffer) throws SubtitleDecoderException {
        super.queueInputBuffer(subtitleInputBuffer);
    }

    public /* bridge */ /* synthetic */ void release() {
        super.release();
    }

    public /* bridge */ /* synthetic */ void setPositionUs(long j2) {
        super.setPositionUs(j2);
    }
}
