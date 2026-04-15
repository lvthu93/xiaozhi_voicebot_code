package com.google.android.exoplayer2.text.cea;

import android.text.Layout;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import androidx.annotation.Nullable;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.InputDeviceCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.SubtitleDecoderException;
import com.google.android.exoplayer2.text.SubtitleInputBuffer;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.mozilla.javascript.Token;

public final class Cea608Decoder extends e0 {
    public static final int[] aa = {-1, -16711936, -16776961, -16711681, SupportMenu.CATEGORY_MASK, InputDeviceCompat.SOURCE_ANY, -65281};
    public static final int[] ab = {32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 225, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 233, 93, 237, 243, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 231, 247, 209, 241, 9632};
    public static final int[] ac = {174, 176, 189, 191, 8482, Token.COMMENT, Token.GENEXPR, 9834, 224, 32, 232, 226, 234, 238, 244, 251};
    public static final int[] ad = {193, 201, 211, 218, 220, 252, 8216, Token.DEBUGGER, 42, 39, 8212, 169, 8480, 8226, 8220, 8221, 192, 194, 199, 200, 202, 203, 235, 206, 207, 239, 212, 217, 249, 219, 171, 187};
    public static final int[] ae = {195, 227, 205, 204, 236, 210, 242, 213, 245, 123, Token.CATCH, 92, 94, 95, 124, Token.FINALLY, 196, 228, 214, 246, 223, Token.ARROW, Token.METHOD, 9474, 197, 229, 216, 248, 9484, 9488, 9492, 9496};
    public static final boolean[] af = {false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false};
    public static final int[] y = {11, 1, 3, 12, 14, 5, 7, 9};
    public static final int[] z = {0, 4, 8, 12, 16, 20, 24, 28};
    public final ParsableByteArray g = new ParsableByteArray();
    public final int h;
    public final int i;
    public final int j;
    public final long k;
    public final ArrayList<a> l = new ArrayList<>();
    public a m = new a(0, 4);
    @Nullable
    public List<Cue> n;
    @Nullable
    public List<Cue> o;
    public int p;
    public int q;
    public boolean r;
    public boolean s;
    public byte t;
    public byte u;
    public int v = 0;
    public boolean w;
    public long x;

    public static final class a {
        public final ArrayList a = new ArrayList();
        public final ArrayList b = new ArrayList();
        public final StringBuilder c = new StringBuilder();
        public int d;
        public int e;
        public int f;
        public int g;
        public int h;

        /* renamed from: com.google.android.exoplayer2.text.cea.Cea608Decoder$a$a  reason: collision with other inner class name */
        public static class C0019a {
            public final int a;
            public final boolean b;
            public int c;

            public C0019a(int i, boolean z, int i2) {
                this.a = i;
                this.b = z;
                this.c = i2;
            }
        }

        public a(int i, int i2) {
            reset(i);
            this.h = i2;
        }

        public final SpannableString a() {
            int i;
            boolean z;
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(this.c);
            int length = spannableStringBuilder.length();
            int i2 = 0;
            int i3 = -1;
            int i4 = -1;
            int i5 = 0;
            int i6 = -1;
            boolean z2 = false;
            int i7 = -1;
            while (true) {
                ArrayList arrayList = this.a;
                if (i2 >= arrayList.size()) {
                    break;
                }
                C0019a aVar = (C0019a) arrayList.get(i2);
                boolean z3 = aVar.b;
                int i8 = aVar.a;
                if (i8 != 8) {
                    if (i8 == 7) {
                        z = true;
                    } else {
                        z = false;
                    }
                    if (i8 != 7) {
                        i7 = Cea608Decoder.aa[i8];
                    }
                    z2 = z;
                }
                int i9 = aVar.c;
                i2++;
                if (i2 < arrayList.size()) {
                    i = ((C0019a) arrayList.get(i2)).c;
                } else {
                    i = length;
                }
                if (i9 != i) {
                    if (i3 != -1 && !z3) {
                        spannableStringBuilder.setSpan(new UnderlineSpan(), i3, i9, 33);
                        i3 = -1;
                    } else if (i3 == -1 && z3) {
                        i3 = i9;
                    }
                    if (i4 != -1 && !z2) {
                        spannableStringBuilder.setSpan(new StyleSpan(2), i4, i9, 33);
                        i4 = -1;
                    } else if (i4 == -1 && z2) {
                        i4 = i9;
                    }
                    if (i7 != i6) {
                        if (i6 != -1) {
                            spannableStringBuilder.setSpan(new ForegroundColorSpan(i6), i5, i9, 33);
                        }
                        i6 = i7;
                        i5 = i9;
                    }
                }
            }
            if (!(i3 == -1 || i3 == length)) {
                spannableStringBuilder.setSpan(new UnderlineSpan(), i3, length, 33);
            }
            if (!(i4 == -1 || i4 == length)) {
                spannableStringBuilder.setSpan(new StyleSpan(2), i4, length, 33);
            }
            if (!(i5 == length || i6 == -1)) {
                spannableStringBuilder.setSpan(new ForegroundColorSpan(i6), i5, length, 33);
            }
            return new SpannableString(spannableStringBuilder);
        }

        public void append(char c2) {
            StringBuilder sb = this.c;
            if (sb.length() < 32) {
                sb.append(c2);
            }
        }

        public void backspace() {
            C0019a aVar;
            int i;
            StringBuilder sb = this.c;
            int length = sb.length();
            if (length > 0) {
                sb.delete(length - 1, length);
                ArrayList arrayList = this.a;
                int size = arrayList.size();
                while (true) {
                    size--;
                    if (size >= 0 && (i = (aVar = (C0019a) arrayList.get(size)).c) == length) {
                        aVar.c = i - 1;
                    } else {
                        return;
                    }
                }
            }
        }

        @Nullable
        public Cue build(int i) {
            float f2;
            int i2 = this.e + this.f;
            int i3 = 32 - i2;
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            int i4 = 0;
            while (true) {
                ArrayList arrayList = this.b;
                if (i4 >= arrayList.size()) {
                    break;
                }
                spannableStringBuilder.append(Util.truncateAscii((CharSequence) arrayList.get(i4), i3));
                spannableStringBuilder.append(10);
                i4++;
            }
            spannableStringBuilder.append(Util.truncateAscii(a(), i3));
            if (spannableStringBuilder.length() == 0) {
                return null;
            }
            int length = i3 - spannableStringBuilder.length();
            int i5 = i2 - length;
            if (i == Integer.MIN_VALUE) {
                if (this.g == 2 && (Math.abs(i5) < 3 || length < 0)) {
                    i = 1;
                } else if (this.g != 2 || i5 <= 0) {
                    i = 0;
                } else {
                    i = 2;
                }
            }
            if (i != 1) {
                if (i == 2) {
                    i2 = 32 - length;
                }
                f2 = ((((float) i2) / 32.0f) * 0.8f) + 0.1f;
            } else {
                f2 = 0.5f;
            }
            int i6 = this.d;
            if (i6 > 7) {
                i6 = (i6 - 15) - 2;
            } else if (this.g == 1) {
                i6 -= this.h - 1;
            }
            return new Cue.Builder().setText(spannableStringBuilder).setTextAlignment(Layout.Alignment.ALIGN_NORMAL).setLine((float) i6, 1).setPosition(f2).setPositionAnchor(i).build();
        }

        public boolean isEmpty() {
            if (!this.a.isEmpty() || !this.b.isEmpty() || this.c.length() != 0) {
                return false;
            }
            return true;
        }

        public void reset(int i) {
            this.g = i;
            this.a.clear();
            this.b.clear();
            this.c.setLength(0);
            this.d = 15;
            this.e = 0;
            this.f = 0;
        }

        public void rollUp() {
            ArrayList arrayList = this.b;
            arrayList.add(a());
            this.c.setLength(0);
            this.a.clear();
            int min = Math.min(this.h, this.d);
            while (arrayList.size() >= min) {
                arrayList.remove(0);
            }
        }

        public void setCaptionMode(int i) {
            this.g = i;
        }

        public void setCaptionRowCount(int i) {
            this.h = i;
        }

        public void setStyle(int i, boolean z) {
            this.a.add(new C0019a(i, z, this.c.length()));
        }
    }

    public Cea608Decoder(String str, int i2, long j2) {
        long j3;
        int i3;
        if (j2 > 0) {
            j3 = j2 * 1000;
        } else {
            j3 = -9223372036854775807L;
        }
        this.k = j3;
        if ("application/x-mp4-cea-608".equals(str)) {
            i3 = 2;
        } else {
            i3 = 3;
        }
        this.h = i3;
        if (i2 == 1) {
            this.j = 0;
            this.i = 0;
        } else if (i2 == 2) {
            this.j = 1;
            this.i = 0;
        } else if (i2 == 3) {
            this.j = 0;
            this.i = 1;
        } else if (i2 != 4) {
            Log.w("Cea608Decoder", "Invalid channel. Defaulting to CC1.");
            this.j = 0;
            this.i = 0;
        } else {
            this.j = 1;
            this.i = 1;
        }
        f(0);
        e();
        this.w = true;
        this.x = -9223372036854775807L;
    }

    public final f0 a() {
        List<Cue> list = this.n;
        this.o = list;
        return new f0((List) Assertions.checkNotNull(list));
    }

    /* JADX WARNING: Removed duplicated region for block: B:200:0x0017 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0064  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x008a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void b(defpackage.e0.a r15) {
        /*
            r14 = this;
            java.nio.ByteBuffer r15 = r15.g
            java.lang.Object r15 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r15)
            java.nio.ByteBuffer r15 = (java.nio.ByteBuffer) r15
            byte[] r0 = r15.array()
            int r15 = r15.limit()
            com.google.android.exoplayer2.util.ParsableByteArray r1 = r14.g
            r1.reset(r0, r15)
            r15 = 0
            r0 = 0
        L_0x0017:
            int r2 = r1.bytesLeft()
            r3 = 3
            r4 = 1
            int r5 = r14.h
            if (r2 < r5) goto L_0x027c
            r2 = 2
            if (r5 != r2) goto L_0x0026
            r5 = -4
            goto L_0x002b
        L_0x0026:
            int r5 = r1.readUnsignedByte()
            byte r5 = (byte) r5
        L_0x002b:
            int r6 = r1.readUnsignedByte()
            int r7 = r1.readUnsignedByte()
            r8 = r5 & 2
            if (r8 == 0) goto L_0x0038
            goto L_0x0017
        L_0x0038:
            r8 = r5 & 1
            int r9 = r14.i
            if (r8 == r9) goto L_0x003f
            goto L_0x0017
        L_0x003f:
            r8 = r6 & 127(0x7f, float:1.78E-43)
            byte r8 = (byte) r8
            r9 = r7 & 127(0x7f, float:1.78E-43)
            byte r9 = (byte) r9
            if (r8 != 0) goto L_0x004a
            if (r9 != 0) goto L_0x004a
            goto L_0x0017
        L_0x004a:
            boolean r10 = r14.r
            r5 = r5 & 4
            r11 = 4
            if (r5 != r11) goto L_0x005d
            boolean[] r5 = af
            boolean r6 = r5[r6]
            if (r6 == 0) goto L_0x005d
            boolean r5 = r5[r7]
            if (r5 == 0) goto L_0x005d
            r5 = 1
            goto L_0x005e
        L_0x005d:
            r5 = 0
        L_0x005e:
            r14.r = r5
            r6 = 16
            if (r5 == 0) goto L_0x0084
            r7 = r8 & 240(0xf0, float:3.36E-43)
            if (r7 != r6) goto L_0x006a
            r7 = 1
            goto L_0x006b
        L_0x006a:
            r7 = 0
        L_0x006b:
            if (r7 == 0) goto L_0x0084
            boolean r7 = r14.s
            if (r7 == 0) goto L_0x007d
            byte r7 = r14.t
            if (r7 != r8) goto L_0x007d
            byte r7 = r14.u
            if (r7 != r9) goto L_0x007d
            r14.s = r15
            r7 = 1
            goto L_0x0087
        L_0x007d:
            r14.s = r4
            r14.t = r8
            r14.u = r9
            goto L_0x0086
        L_0x0084:
            r14.s = r15
        L_0x0086:
            r7 = 0
        L_0x0087:
            if (r7 == 0) goto L_0x008a
            goto L_0x0017
        L_0x008a:
            if (r5 != 0) goto L_0x0093
            if (r10 == 0) goto L_0x0017
            r14.e()
            goto L_0x0279
        L_0x0093:
            if (r4 > r8) goto L_0x009b
            r5 = 15
            if (r8 > r5) goto L_0x009b
            r5 = 1
            goto L_0x009c
        L_0x009b:
            r5 = 0
        L_0x009c:
            r7 = 32
            r10 = 20
            if (r5 == 0) goto L_0x00a5
            r14.w = r15
            goto L_0x00c0
        L_0x00a5:
            r5 = r8 & 247(0xf7, float:3.46E-43)
            if (r5 != r10) goto L_0x00ab
            r5 = 1
            goto L_0x00ac
        L_0x00ab:
            r5 = 0
        L_0x00ac:
            if (r5 == 0) goto L_0x00c0
            if (r9 == r7) goto L_0x00be
            r5 = 47
            if (r9 == r5) goto L_0x00be
            switch(r9) {
                case 37: goto L_0x00be;
                case 38: goto L_0x00be;
                case 39: goto L_0x00be;
                default: goto L_0x00b7;
            }
        L_0x00b7:
            switch(r9) {
                case 41: goto L_0x00be;
                case 42: goto L_0x00bb;
                case 43: goto L_0x00bb;
                default: goto L_0x00ba;
            }
        L_0x00ba:
            goto L_0x00c0
        L_0x00bb:
            r14.w = r15
            goto L_0x00c0
        L_0x00be:
            r14.w = r4
        L_0x00c0:
            boolean r5 = r14.w
            if (r5 != 0) goto L_0x00c6
            goto L_0x0017
        L_0x00c6:
            r5 = r8 & 224(0xe0, float:3.14E-43)
            if (r5 != 0) goto L_0x00cc
            r12 = 1
            goto L_0x00cd
        L_0x00cc:
            r12 = 0
        L_0x00cd:
            if (r12 == 0) goto L_0x00d4
            int r12 = r8 >> 3
            r12 = r12 & r4
            r14.v = r12
        L_0x00d4:
            int r12 = r14.v
            int r13 = r14.j
            if (r12 != r13) goto L_0x00dc
            r12 = 1
            goto L_0x00dd
        L_0x00dc:
            r12 = 0
        L_0x00dd:
            if (r12 != 0) goto L_0x00e1
            goto L_0x0017
        L_0x00e1:
            if (r5 != 0) goto L_0x00e5
            r0 = 1
            goto L_0x00e6
        L_0x00e5:
            r0 = 0
        L_0x00e6:
            if (r0 == 0) goto L_0x025b
            r0 = r8 & 247(0xf7, float:3.46E-43)
            r5 = 17
            if (r0 != r5) goto L_0x00f6
            r12 = r9 & 240(0xf0, float:3.36E-43)
            r13 = 48
            if (r12 != r13) goto L_0x00f6
            r12 = 1
            goto L_0x00f7
        L_0x00f6:
            r12 = 0
        L_0x00f7:
            if (r12 == 0) goto L_0x0107
            com.google.android.exoplayer2.text.cea.Cea608Decoder$a r0 = r14.m
            r2 = r9 & 15
            int[] r3 = ac
            r2 = r3[r2]
            char r2 = (char) r2
            r0.append(r2)
            goto L_0x0279
        L_0x0107:
            r12 = r8 & 246(0xf6, float:3.45E-43)
            r13 = 18
            if (r12 != r13) goto L_0x0113
            r13 = r9 & 224(0xe0, float:3.14E-43)
            if (r13 != r7) goto L_0x0113
            r13 = 1
            goto L_0x0114
        L_0x0113:
            r13 = 0
        L_0x0114:
            if (r13 == 0) goto L_0x0134
            com.google.android.exoplayer2.text.cea.Cea608Decoder$a r0 = r14.m
            r0.backspace()
            com.google.android.exoplayer2.text.cea.Cea608Decoder$a r0 = r14.m
            r2 = r8 & 1
            if (r2 != 0) goto L_0x0128
            r2 = r9 & 31
            int[] r3 = ad
            r2 = r3[r2]
            goto L_0x012e
        L_0x0128:
            r2 = r9 & 31
            int[] r3 = ae
            r2 = r3[r2]
        L_0x012e:
            char r2 = (char) r2
            r0.append(r2)
            goto L_0x0279
        L_0x0134:
            if (r0 != r5) goto L_0x013c
            r5 = r9 & 240(0xf0, float:3.36E-43)
            if (r5 != r7) goto L_0x013c
            r5 = 1
            goto L_0x013d
        L_0x013c:
            r5 = 0
        L_0x013d:
            if (r5 == 0) goto L_0x0156
            com.google.android.exoplayer2.text.cea.Cea608Decoder$a r0 = r14.m
            r0.append(r7)
            r0 = r9 & 1
            if (r0 != r4) goto L_0x014a
            r0 = 1
            goto L_0x014b
        L_0x014a:
            r0 = 0
        L_0x014b:
            int r2 = r9 >> 1
            r2 = r2 & 7
            com.google.android.exoplayer2.text.cea.Cea608Decoder$a r3 = r14.m
            r3.setStyle(r2, r0)
            goto L_0x0279
        L_0x0156:
            r5 = r8 & 240(0xf0, float:3.36E-43)
            if (r5 != r6) goto L_0x0162
            r5 = r9 & 192(0xc0, float:2.69E-43)
            r13 = 64
            if (r5 != r13) goto L_0x0162
            r5 = 1
            goto L_0x0163
        L_0x0162:
            r5 = 0
        L_0x0163:
            if (r5 == 0) goto L_0x01c3
            int[] r0 = y
            r2 = r8 & 7
            r0 = r0[r2]
            r2 = r9 & 32
            if (r2 == 0) goto L_0x0171
            r2 = 1
            goto L_0x0172
        L_0x0171:
            r2 = 0
        L_0x0172:
            if (r2 == 0) goto L_0x0176
            int r0 = r0 + 1
        L_0x0176:
            com.google.android.exoplayer2.text.cea.Cea608Decoder$a r2 = r14.m
            int r3 = r2.d
            if (r0 == r3) goto L_0x019a
            int r3 = r14.p
            if (r3 == r4) goto L_0x0196
            boolean r2 = r2.isEmpty()
            if (r2 != 0) goto L_0x0196
            com.google.android.exoplayer2.text.cea.Cea608Decoder$a r2 = new com.google.android.exoplayer2.text.cea.Cea608Decoder$a
            int r3 = r14.p
            int r5 = r14.q
            r2.<init>(r3, r5)
            r14.m = r2
            java.util.ArrayList<com.google.android.exoplayer2.text.cea.Cea608Decoder$a> r3 = r14.l
            r3.add(r2)
        L_0x0196:
            com.google.android.exoplayer2.text.cea.Cea608Decoder$a r2 = r14.m
            r2.d = r0
        L_0x019a:
            r0 = r9 & 16
            if (r0 != r6) goto L_0x01a0
            r0 = 1
            goto L_0x01a1
        L_0x01a0:
            r0 = 0
        L_0x01a1:
            r2 = r9 & 1
            if (r2 != r4) goto L_0x01a7
            r2 = 1
            goto L_0x01a8
        L_0x01a7:
            r2 = 0
        L_0x01a8:
            int r3 = r9 >> 1
            r3 = r3 & 7
            com.google.android.exoplayer2.text.cea.Cea608Decoder$a r5 = r14.m
            if (r0 == 0) goto L_0x01b3
            r6 = 8
            goto L_0x01b4
        L_0x01b3:
            r6 = r3
        L_0x01b4:
            r5.setStyle(r6, r2)
            if (r0 == 0) goto L_0x0279
            com.google.android.exoplayer2.text.cea.Cea608Decoder$a r0 = r14.m
            int[] r2 = z
            r2 = r2[r3]
            r0.e = r2
            goto L_0x0279
        L_0x01c3:
            r5 = 23
            r6 = 33
            if (r0 != r5) goto L_0x01d1
            if (r9 < r6) goto L_0x01d1
            r0 = 35
            if (r9 > r0) goto L_0x01d1
            r0 = 1
            goto L_0x01d2
        L_0x01d1:
            r0 = 0
        L_0x01d2:
            if (r0 == 0) goto L_0x01dc
            com.google.android.exoplayer2.text.cea.Cea608Decoder$a r0 = r14.m
            int r9 = r9 + -32
            r0.f = r9
            goto L_0x0279
        L_0x01dc:
            if (r12 != r10) goto L_0x01e4
            r0 = r9 & 240(0xf0, float:3.36E-43)
            if (r0 != r7) goto L_0x01e4
            r0 = 1
            goto L_0x01e5
        L_0x01e4:
            r0 = 0
        L_0x01e5:
            if (r0 == 0) goto L_0x0279
            if (r9 == r7) goto L_0x0257
            r0 = 41
            if (r9 == r0) goto L_0x0253
            switch(r9) {
                case 37: goto L_0x020e;
                case 38: goto L_0x0202;
                case 39: goto L_0x01f6;
                default: goto L_0x01f0;
            }
        L_0x01f0:
            int r0 = r14.p
            if (r0 != 0) goto L_0x0219
            goto L_0x0279
        L_0x01f6:
            r14.f(r4)
            r14.q = r11
            com.google.android.exoplayer2.text.cea.Cea608Decoder$a r0 = r14.m
            r0.setCaptionRowCount(r11)
            goto L_0x0279
        L_0x0202:
            r14.f(r4)
            r14.q = r3
            com.google.android.exoplayer2.text.cea.Cea608Decoder$a r0 = r14.m
            r0.setCaptionRowCount(r3)
            goto L_0x0279
        L_0x020e:
            r14.f(r4)
            r14.q = r2
            com.google.android.exoplayer2.text.cea.Cea608Decoder$a r0 = r14.m
            r0.setCaptionRowCount(r2)
            goto L_0x0279
        L_0x0219:
            if (r9 == r6) goto L_0x024d
            switch(r9) {
                case 44: goto L_0x023d;
                case 45: goto L_0x022d;
                case 46: goto L_0x0229;
                case 47: goto L_0x021f;
                default: goto L_0x021e;
            }
        L_0x021e:
            goto L_0x0279
        L_0x021f:
            java.util.List r0 = r14.d()
            r14.n = r0
            r14.e()
            goto L_0x0279
        L_0x0229:
            r14.e()
            goto L_0x0279
        L_0x022d:
            if (r0 != r4) goto L_0x0279
            com.google.android.exoplayer2.text.cea.Cea608Decoder$a r0 = r14.m
            boolean r0 = r0.isEmpty()
            if (r0 != 0) goto L_0x0279
            com.google.android.exoplayer2.text.cea.Cea608Decoder$a r0 = r14.m
            r0.rollUp()
            goto L_0x0279
        L_0x023d:
            java.util.List r0 = java.util.Collections.emptyList()
            r14.n = r0
            int r0 = r14.p
            if (r0 == r4) goto L_0x0249
            if (r0 != r3) goto L_0x0279
        L_0x0249:
            r14.e()
            goto L_0x0279
        L_0x024d:
            com.google.android.exoplayer2.text.cea.Cea608Decoder$a r0 = r14.m
            r0.backspace()
            goto L_0x0279
        L_0x0253:
            r14.f(r3)
            goto L_0x0279
        L_0x0257:
            r14.f(r2)
            goto L_0x0279
        L_0x025b:
            com.google.android.exoplayer2.text.cea.Cea608Decoder$a r0 = r14.m
            r2 = r8 & 127(0x7f, float:1.78E-43)
            int r2 = r2 + -32
            int[] r3 = ab
            r2 = r3[r2]
            char r2 = (char) r2
            r0.append(r2)
            r0 = r9 & 224(0xe0, float:3.14E-43)
            if (r0 == 0) goto L_0x0279
            com.google.android.exoplayer2.text.cea.Cea608Decoder$a r0 = r14.m
            r2 = r9 & 127(0x7f, float:1.78E-43)
            int r2 = r2 + -32
            r2 = r3[r2]
            char r2 = (char) r2
            r0.append(r2)
        L_0x0279:
            r0 = 1
            goto L_0x0017
        L_0x027c:
            if (r0 == 0) goto L_0x028e
            int r15 = r14.p
            if (r15 == r4) goto L_0x0284
            if (r15 != r3) goto L_0x028e
        L_0x0284:
            java.util.List r15 = r14.d()
            r14.n = r15
            long r0 = r14.e
            r14.x = r0
        L_0x028e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.cea.Cea608Decoder.b(e0$a):void");
    }

    public final boolean c() {
        return this.n != this.o;
    }

    public final List<Cue> d() {
        ArrayList<a> arrayList = this.l;
        int size = arrayList.size();
        ArrayList arrayList2 = new ArrayList(size);
        int i2 = 2;
        for (int i3 = 0; i3 < size; i3++) {
            Cue build = arrayList.get(i3).build(Integer.MIN_VALUE);
            arrayList2.add(build);
            if (build != null) {
                i2 = Math.min(i2, build.i);
            }
        }
        ArrayList arrayList3 = new ArrayList(size);
        for (int i4 = 0; i4 < size; i4++) {
            Cue cue = (Cue) arrayList2.get(i4);
            if (cue != null) {
                if (cue.i != i2) {
                    cue = (Cue) Assertions.checkNotNull(arrayList.get(i4).build(i2));
                }
                arrayList3.add(cue);
            }
        }
        return arrayList3;
    }

    @Nullable
    public /* bridge */ /* synthetic */ SubtitleInputBuffer dequeueInputBuffer() throws SubtitleDecoderException {
        return super.dequeueInputBuffer();
    }

    public final void e() {
        this.m.reset(this.p);
        ArrayList<a> arrayList = this.l;
        arrayList.clear();
        arrayList.add(this.m);
    }

    public final void f(int i2) {
        int i3 = this.p;
        if (i3 != i2) {
            this.p = i2;
            if (i2 == 3) {
                int i4 = 0;
                while (true) {
                    ArrayList<a> arrayList = this.l;
                    if (i4 < arrayList.size()) {
                        arrayList.get(i4).setCaptionMode(i2);
                        i4++;
                    } else {
                        return;
                    }
                }
            } else {
                e();
                if (i3 == 3 || i2 == 1 || i2 == 0) {
                    this.n = Collections.emptyList();
                }
            }
        }
    }

    public void flush() {
        super.flush();
        this.n = null;
        this.o = null;
        f(0);
        this.q = 4;
        this.m.setCaptionRowCount(4);
        e();
        this.r = false;
        this.s = false;
        this.t = 0;
        this.u = 0;
        this.v = 0;
        this.w = true;
        this.x = -9223372036854775807L;
    }

    public String getName() {
        return "Cea608Decoder";
    }

    public /* bridge */ /* synthetic */ void queueInputBuffer(SubtitleInputBuffer subtitleInputBuffer) throws SubtitleDecoderException {
        super.queueInputBuffer(subtitleInputBuffer);
    }

    public void release() {
    }

    public /* bridge */ /* synthetic */ void setPositionUs(long j2) {
        super.setPositionUs(j2);
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x002f  */
    /* JADX WARNING: Removed duplicated region for block: B:17:? A[RETURN, SYNTHETIC] */
    @androidx.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.android.exoplayer2.text.SubtitleOutputBuffer dequeueOutputBuffer() throws com.google.android.exoplayer2.text.SubtitleDecoderException {
        /*
            r9 = this;
            com.google.android.exoplayer2.text.SubtitleOutputBuffer r0 = super.dequeueOutputBuffer()
            if (r0 == 0) goto L_0x0007
            return r0
        L_0x0007:
            r0 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            long r2 = r9.k
            int r4 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r4 == 0) goto L_0x0022
            long r4 = r9.x
            int r6 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
            if (r6 != 0) goto L_0x0019
            goto L_0x0022
        L_0x0019:
            long r6 = r9.e
            long r6 = r6 - r4
            int r4 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r4 < 0) goto L_0x0022
            r2 = 1
            goto L_0x0023
        L_0x0022:
            r2 = 0
        L_0x0023:
            if (r2 == 0) goto L_0x0047
            java.util.ArrayDeque<com.google.android.exoplayer2.text.SubtitleOutputBuffer> r2 = r9.b
            java.lang.Object r2 = r2.pollFirst()
            com.google.android.exoplayer2.text.SubtitleOutputBuffer r2 = (com.google.android.exoplayer2.text.SubtitleOutputBuffer) r2
            if (r2 == 0) goto L_0x0047
            java.util.List r3 = java.util.Collections.emptyList()
            r9.n = r3
            r9.x = r0
            f0 r6 = r9.a()
            long r4 = r9.e
            r7 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            r3 = r2
            r3.setContent(r4, r6, r7)
            return r2
        L_0x0047:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.cea.Cea608Decoder.dequeueOutputBuffer():com.google.android.exoplayer2.text.SubtitleOutputBuffer");
    }
}
