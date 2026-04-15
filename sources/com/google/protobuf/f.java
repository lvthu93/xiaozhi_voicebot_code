package com.google.protobuf;

import androidx.appcompat.widget.ActivityChooserView;
import com.google.protobuf.q;
import defpackage.cp;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;
import org.mozilla.javascript.Token;

public abstract class f {
    public int a;
    public int b;
    public final int c = 100;
    public final int d = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
    public g e;

    public static final class a extends f {
        public final byte[] f;
        public int g;
        public int h;
        public int i;
        public final int j;
        public int k;
        public int l = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;

        public a(byte[] bArr, int i2, int i3, boolean z) {
            this.f = bArr;
            this.g = i3 + i2;
            this.i = i2;
            this.j = i2;
        }

        public final void a(int i2) throws q {
            if (this.k != i2) {
                throw q.a();
            }
        }

        public final int aa() throws IOException {
            if (e()) {
                this.k = 0;
                return 0;
            }
            int ah = ah();
            this.k = ah;
            if ((ah >>> 3) != 0) {
                return ah;
            }
            throw q.b();
        }

        public final int ab() throws IOException {
            return ah();
        }

        public final long ac() throws IOException {
            return ai();
        }

        public final boolean ad(int i2) throws IOException {
            int i3 = i2 & 7;
            int i4 = 0;
            if (i3 == 0) {
                int i5 = this.g - this.i;
                byte[] bArr = this.f;
                if (i5 >= 10) {
                    while (i4 < 10) {
                        int i6 = this.i;
                        this.i = i6 + 1;
                        if (bArr[i6] < 0) {
                            i4++;
                        }
                    }
                    throw q.e();
                }
                while (i4 < 10) {
                    int i7 = this.i;
                    if (i7 != this.g) {
                        this.i = i7 + 1;
                        if (bArr[i7] < 0) {
                            i4++;
                        }
                    } else {
                        throw q.h();
                    }
                }
                throw q.e();
                return true;
            } else if (i3 == 1) {
                ak(8);
                return true;
            } else if (i3 == 2) {
                ak(ah());
                return true;
            } else if (i3 == 3) {
                ae();
                a(((i2 >>> 3) << 3) | 4);
                return true;
            } else if (i3 == 4) {
                if (this.b == 0) {
                    a(0);
                }
                return false;
            } else if (i3 == 5) {
                ak(4);
                return true;
            } else {
                int i8 = q.f;
                throw new q.a();
            }
        }

        public final int af() throws IOException {
            int i2 = this.i;
            if (this.g - i2 >= 4) {
                this.i = i2 + 4;
                byte[] bArr = this.f;
                return ((bArr[i2 + 3] & 255) << 24) | (bArr[i2] & 255) | ((bArr[i2 + 1] & 255) << 8) | ((bArr[i2 + 2] & 255) << 16);
            }
            throw q.h();
        }

        public final long ag() throws IOException {
            int i2 = this.i;
            if (this.g - i2 >= 8) {
                this.i = i2 + 8;
                byte[] bArr = this.f;
                return ((((long) bArr[i2 + 7]) & 255) << 56) | (((long) bArr[i2]) & 255) | ((((long) bArr[i2 + 1]) & 255) << 8) | ((((long) bArr[i2 + 2]) & 255) << 16) | ((((long) bArr[i2 + 3]) & 255) << 24) | ((((long) bArr[i2 + 4]) & 255) << 32) | ((((long) bArr[i2 + 5]) & 255) << 40) | ((((long) bArr[i2 + 6]) & 255) << 48);
            }
            throw q.h();
        }

        /* JADX WARNING: Code restructure failed: missing block: B:29:0x0068, code lost:
            if (r3[r2] < 0) goto L_0x006a;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final int ah() throws java.io.IOException {
            /*
                r5 = this;
                int r0 = r5.i
                int r1 = r5.g
                if (r1 != r0) goto L_0x0007
                goto L_0x006a
            L_0x0007:
                int r2 = r0 + 1
                byte[] r3 = r5.f
                byte r0 = r3[r0]
                if (r0 < 0) goto L_0x0012
                r5.i = r2
                return r0
            L_0x0012:
                int r1 = r1 - r2
                r4 = 9
                if (r1 >= r4) goto L_0x0018
                goto L_0x006a
            L_0x0018:
                int r1 = r2 + 1
                byte r2 = r3[r2]
                int r2 = r2 << 7
                r0 = r0 ^ r2
                if (r0 >= 0) goto L_0x0024
                r0 = r0 ^ -128(0xffffffffffffff80, float:NaN)
                goto L_0x0070
            L_0x0024:
                int r2 = r1 + 1
                byte r1 = r3[r1]
                int r1 = r1 << 14
                r0 = r0 ^ r1
                if (r0 < 0) goto L_0x0031
                r0 = r0 ^ 16256(0x3f80, float:2.278E-41)
            L_0x002f:
                r1 = r2
                goto L_0x0070
            L_0x0031:
                int r1 = r2 + 1
                byte r2 = r3[r2]
                int r2 = r2 << 21
                r0 = r0 ^ r2
                if (r0 >= 0) goto L_0x003f
                r2 = -2080896(0xffffffffffe03f80, float:NaN)
                r0 = r0 ^ r2
                goto L_0x0070
            L_0x003f:
                int r2 = r1 + 1
                byte r1 = r3[r1]
                int r4 = r1 << 28
                r0 = r0 ^ r4
                r4 = 266354560(0xfe03f80, float:2.2112565E-29)
                r0 = r0 ^ r4
                if (r1 >= 0) goto L_0x002f
                int r1 = r2 + 1
                byte r2 = r3[r2]
                if (r2 >= 0) goto L_0x0070
                int r2 = r1 + 1
                byte r1 = r3[r1]
                if (r1 >= 0) goto L_0x002f
                int r1 = r2 + 1
                byte r2 = r3[r2]
                if (r2 >= 0) goto L_0x0070
                int r2 = r1 + 1
                byte r1 = r3[r1]
                if (r1 >= 0) goto L_0x002f
                int r1 = r2 + 1
                byte r2 = r3[r2]
                if (r2 >= 0) goto L_0x0070
            L_0x006a:
                long r0 = r5.aj()
                int r1 = (int) r0
                return r1
            L_0x0070:
                r5.i = r1
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.f.a.ah():int");
        }

        public final long ai() throws IOException {
            long j2;
            long j3;
            long j4;
            byte b;
            int i2 = this.i;
            int i3 = this.g;
            if (i3 != i2) {
                int i4 = i2 + 1;
                byte[] bArr = this.f;
                byte b2 = bArr[i2];
                if (b2 >= 0) {
                    this.i = i4;
                    return (long) b2;
                } else if (i3 - i4 >= 9) {
                    int i5 = i4 + 1;
                    byte b3 = b2 ^ (bArr[i4] << 7);
                    if (b3 < 0) {
                        b = b3 ^ Byte.MIN_VALUE;
                    } else {
                        int i6 = i5 + 1;
                        byte b4 = b3 ^ (bArr[i5] << MqttWireMessage.MESSAGE_TYPE_DISCONNECT);
                        if (b4 >= 0) {
                            j3 = (long) (b4 ^ 16256);
                        } else {
                            i5 = i6 + 1;
                            byte b5 = b4 ^ (bArr[i6] << 21);
                            if (b5 < 0) {
                                b = b5 ^ -2080896;
                            } else {
                                long j5 = (long) b5;
                                int i7 = i5 + 1;
                                long j6 = (((long) bArr[i5]) << 28) ^ j5;
                                if (j6 >= 0) {
                                    j2 = j6 ^ 266354560;
                                    i5 = i7;
                                } else {
                                    int i8 = i7 + 1;
                                    long j7 = j6 ^ (((long) bArr[i7]) << 35);
                                    if (j7 < 0) {
                                        j4 = -34093383808L;
                                    } else {
                                        i6 = i8 + 1;
                                        long j8 = j7 ^ (((long) bArr[i8]) << 42);
                                        if (j8 >= 0) {
                                            j3 = j8 ^ 4363953127296L;
                                        } else {
                                            i8 = i6 + 1;
                                            j7 = j8 ^ (((long) bArr[i6]) << 49);
                                            if (j7 < 0) {
                                                j4 = -558586000294016L;
                                            } else {
                                                i6 = i8 + 1;
                                                j3 = (j7 ^ (((long) bArr[i8]) << 56)) ^ 71499008037633920L;
                                                if (j3 < 0) {
                                                    i8 = i6 + 1;
                                                    if (((long) bArr[i6]) >= 0) {
                                                        j2 = j3;
                                                        i5 = i8;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    j2 = j4 ^ j7;
                                    i5 = i8;
                                }
                                this.i = i5;
                                return j2;
                            }
                        }
                        i5 = i6;
                        j2 = j3;
                        this.i = i5;
                        return j2;
                    }
                    j2 = (long) b;
                    this.i = i5;
                    return j2;
                }
            }
            return aj();
        }

        public final long aj() throws IOException {
            long j2 = 0;
            int i2 = 0;
            while (i2 < 64) {
                int i3 = this.i;
                if (i3 != this.g) {
                    this.i = i3 + 1;
                    byte b = this.f[i3];
                    j2 |= ((long) (b & Byte.MAX_VALUE)) << i2;
                    if ((b & 128) == 0) {
                        return j2;
                    }
                    i2 += 7;
                } else {
                    throw q.h();
                }
            }
            throw q.e();
        }

        public final void ak(int i2) throws IOException {
            if (i2 >= 0) {
                int i3 = this.g;
                int i4 = this.i;
                if (i2 <= i3 - i4) {
                    this.i = i4 + i2;
                    return;
                }
            }
            if (i2 < 0) {
                throw q.f();
            }
            throw q.h();
        }

        public final int d() {
            return this.i - this.j;
        }

        public final boolean e() throws IOException {
            return this.i == this.g;
        }

        public final void i(int i2) {
            this.l = i2;
            int i3 = this.g + this.h;
            this.g = i3;
            int i4 = i3 - this.j;
            if (i4 > i2) {
                int i5 = i4 - i2;
                this.h = i5;
                this.g = i3 - i5;
                return;
            }
            this.h = 0;
        }

        public final int j(int i2) throws q {
            if (i2 >= 0) {
                int i3 = this.i;
                int i4 = this.j;
                int i5 = (i3 - i4) + i2;
                if (i5 >= 0) {
                    int i6 = this.l;
                    if (i5 <= i6) {
                        this.l = i5;
                        int i7 = this.g + this.h;
                        this.g = i7;
                        int i8 = i7 - i4;
                        if (i8 > i5) {
                            int i9 = i8 - i5;
                            this.h = i9;
                            this.g = i7 - i9;
                        } else {
                            this.h = 0;
                        }
                        return i6;
                    }
                    throw q.h();
                }
                throw new q("Protocol message was too large.  May be malicious.  Use CodedInputStream.setSizeLimit() to increase the size limit. If reading multiple messages, consider resetting the counter between each message using CodedInputStream.resetSizeCounter().");
            }
            throw q.f();
        }

        public final boolean k() throws IOException {
            return ai() != 0;
        }

        public final cp.h l() throws IOException {
            byte[] bArr;
            int ah = ah();
            byte[] bArr2 = this.f;
            if (ah > 0) {
                int i2 = this.g;
                int i3 = this.i;
                if (ah <= i2 - i3) {
                    cp.h f2 = cp.f(bArr2, i3, ah);
                    this.i += ah;
                    return f2;
                }
            }
            if (ah == 0) {
                return cp.f;
            }
            if (ah > 0) {
                int i4 = this.g;
                int i5 = this.i;
                if (ah <= i4 - i5) {
                    int i6 = ah + i5;
                    this.i = i6;
                    bArr = Arrays.copyOfRange(bArr2, i5, i6);
                    cp.h hVar = cp.f;
                    return new cp.h(bArr);
                }
            }
            if (ah > 0) {
                throw q.h();
            } else if (ah == 0) {
                bArr = p.b;
                cp.h hVar2 = cp.f;
                return new cp.h(bArr);
            } else {
                throw q.f();
            }
        }

        public final double m() throws IOException {
            return Double.longBitsToDouble(ag());
        }

        public final int n() throws IOException {
            return ah();
        }

        public final int o() throws IOException {
            return af();
        }

        public final long p() throws IOException {
            return ag();
        }

        public final float q() throws IOException {
            return Float.intBitsToFloat(af());
        }

        public final int r() throws IOException {
            return ah();
        }

        public final long s() throws IOException {
            return ai();
        }

        public final int u() throws IOException {
            return af();
        }

        public final long v() throws IOException {
            return ag();
        }

        public final int w() throws IOException {
            return f.b(ah());
        }

        public final long x() throws IOException {
            return f.c(ai());
        }

        public final String y() throws IOException {
            int ah = ah();
            if (ah > 0) {
                int i2 = this.g;
                int i3 = this.i;
                if (ah <= i2 - i3) {
                    String str = new String(this.f, i3, ah, p.a);
                    this.i += ah;
                    return str;
                }
            }
            if (ah == 0) {
                return "";
            }
            if (ah < 0) {
                throw q.f();
            }
            throw q.h();
        }

        public final String z() throws IOException {
            int ah = ah();
            if (ah > 0) {
                int i2 = this.g;
                int i3 = this.i;
                if (ah <= i2 - i3) {
                    String a = gd.a.a(this.f, i3, ah);
                    this.i += ah;
                    return a;
                }
            }
            if (ah == 0) {
                return "";
            }
            if (ah <= 0) {
                throw q.f();
            }
            throw q.h();
        }
    }

    public static final class b extends f {
        public final InputStream f;
        public final byte[] g;
        public int h;
        public int i;
        public int j;
        public int k;
        public int l;
        public int m = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;

        public b(InputStream inputStream) {
            Charset charset = p.a;
            if (inputStream != null) {
                this.f = inputStream;
                this.g = new byte[4096];
                this.h = 0;
                this.j = 0;
                this.l = 0;
                return;
            }
            throw new NullPointerException("input");
        }

        public final void a(int i2) throws q {
            if (this.k != i2) {
                throw q.a();
            }
        }

        public final int aa() throws IOException {
            if (e()) {
                this.k = 0;
                return 0;
            }
            int ak = ak();
            this.k = ak;
            if ((ak >>> 3) != 0) {
                return ak;
            }
            throw q.b();
        }

        public final int ab() throws IOException {
            return ak();
        }

        public final long ac() throws IOException {
            return al();
        }

        public final boolean ad(int i2) throws IOException {
            int i3 = i2 & 7;
            int i4 = 0;
            if (i3 == 0) {
                int i5 = this.h - this.j;
                byte[] bArr = this.g;
                if (i5 >= 10) {
                    while (i4 < 10) {
                        int i6 = this.j;
                        this.j = i6 + 1;
                        if (bArr[i6] < 0) {
                            i4++;
                        }
                    }
                    throw q.e();
                }
                while (i4 < 10) {
                    if (this.j == this.h) {
                        ao(1);
                    }
                    int i7 = this.j;
                    this.j = i7 + 1;
                    if (bArr[i7] < 0) {
                        i4++;
                    }
                }
                throw q.e();
                return true;
            } else if (i3 == 1) {
                ap(8);
                return true;
            } else if (i3 == 2) {
                ap(ak());
                return true;
            } else if (i3 == 3) {
                ae();
                a(((i2 >>> 3) << 3) | 4);
                return true;
            } else if (i3 == 4) {
                if (this.b == 0) {
                    a(0);
                }
                return false;
            } else if (i3 == 5) {
                ap(4);
                return true;
            } else {
                int i8 = q.f;
                throw new q.a();
            }
        }

        public final byte[] af(int i2) throws IOException {
            byte[] ag = ag(i2);
            if (ag != null) {
                return ag;
            }
            int i3 = this.j;
            int i4 = this.h;
            int i5 = i4 - i3;
            this.l += i4;
            this.j = 0;
            this.h = 0;
            ArrayList ah = ah(i2 - i5);
            byte[] bArr = new byte[i2];
            System.arraycopy(this.g, i3, bArr, 0, i5);
            Iterator it = ah.iterator();
            while (it.hasNext()) {
                byte[] bArr2 = (byte[]) it.next();
                System.arraycopy(bArr2, 0, bArr, i5, bArr2.length);
                i5 += bArr2.length;
            }
            return bArr;
        }

        public final byte[] ag(int i2) throws IOException {
            if (i2 == 0) {
                return p.b;
            }
            if (i2 >= 0) {
                int i3 = this.l;
                int i4 = this.j;
                int i5 = i3 + i4 + i2;
                if (i5 - this.d <= 0) {
                    int i6 = this.m;
                    if (i5 <= i6) {
                        int i7 = this.h - i4;
                        int i8 = i2 - i7;
                        InputStream inputStream = this.f;
                        if (i8 >= 4096) {
                            try {
                                if (i8 > inputStream.available()) {
                                    return null;
                                }
                            } catch (q e) {
                                e.c = true;
                                throw e;
                            }
                        }
                        byte[] bArr = new byte[i2];
                        System.arraycopy(this.g, this.j, bArr, 0, i7);
                        this.l += this.h;
                        this.j = 0;
                        this.h = 0;
                        while (i7 < i2) {
                            try {
                                int read = inputStream.read(bArr, i7, i2 - i7);
                                if (read != -1) {
                                    this.l += read;
                                    i7 += read;
                                } else {
                                    throw q.h();
                                }
                            } catch (q e2) {
                                e2.c = true;
                                throw e2;
                            }
                        }
                        return bArr;
                    }
                    ap((i6 - i3) - i4);
                    throw q.h();
                }
                throw new q("Protocol message was too large.  May be malicious.  Use CodedInputStream.setSizeLimit() to increase the size limit. If reading multiple messages, consider resetting the counter between each message using CodedInputStream.resetSizeCounter().");
            }
            throw q.f();
        }

        public final ArrayList ah(int i2) throws IOException {
            ArrayList arrayList = new ArrayList();
            while (i2 > 0) {
                int min = Math.min(i2, 4096);
                byte[] bArr = new byte[min];
                int i3 = 0;
                while (i3 < min) {
                    int read = this.f.read(bArr, i3, min - i3);
                    if (read != -1) {
                        this.l += read;
                        i3 += read;
                    } else {
                        throw q.h();
                    }
                }
                i2 -= min;
                arrayList.add(bArr);
            }
            return arrayList;
        }

        public final int ai() throws IOException {
            int i2 = this.j;
            if (this.h - i2 < 4) {
                ao(4);
                i2 = this.j;
            }
            this.j = i2 + 4;
            byte[] bArr = this.g;
            return ((bArr[i2 + 3] & 255) << 24) | (bArr[i2] & 255) | ((bArr[i2 + 1] & 255) << 8) | ((bArr[i2 + 2] & 255) << 16);
        }

        public final long aj() throws IOException {
            int i2 = this.j;
            if (this.h - i2 < 8) {
                ao(8);
                i2 = this.j;
            }
            this.j = i2 + 8;
            byte[] bArr = this.g;
            return ((((long) bArr[i2 + 7]) & 255) << 56) | (((long) bArr[i2]) & 255) | ((((long) bArr[i2 + 1]) & 255) << 8) | ((((long) bArr[i2 + 2]) & 255) << 16) | ((((long) bArr[i2 + 3]) & 255) << 24) | ((((long) bArr[i2 + 4]) & 255) << 32) | ((((long) bArr[i2 + 5]) & 255) << 40) | ((((long) bArr[i2 + 6]) & 255) << 48);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:29:0x0068, code lost:
            if (r3[r2] < 0) goto L_0x006a;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final int ak() throws java.io.IOException {
            /*
                r5 = this;
                int r0 = r5.j
                int r1 = r5.h
                if (r1 != r0) goto L_0x0007
                goto L_0x006a
            L_0x0007:
                int r2 = r0 + 1
                byte[] r3 = r5.g
                byte r0 = r3[r0]
                if (r0 < 0) goto L_0x0012
                r5.j = r2
                return r0
            L_0x0012:
                int r1 = r1 - r2
                r4 = 9
                if (r1 >= r4) goto L_0x0018
                goto L_0x006a
            L_0x0018:
                int r1 = r2 + 1
                byte r2 = r3[r2]
                int r2 = r2 << 7
                r0 = r0 ^ r2
                if (r0 >= 0) goto L_0x0024
                r0 = r0 ^ -128(0xffffffffffffff80, float:NaN)
                goto L_0x0070
            L_0x0024:
                int r2 = r1 + 1
                byte r1 = r3[r1]
                int r1 = r1 << 14
                r0 = r0 ^ r1
                if (r0 < 0) goto L_0x0031
                r0 = r0 ^ 16256(0x3f80, float:2.278E-41)
            L_0x002f:
                r1 = r2
                goto L_0x0070
            L_0x0031:
                int r1 = r2 + 1
                byte r2 = r3[r2]
                int r2 = r2 << 21
                r0 = r0 ^ r2
                if (r0 >= 0) goto L_0x003f
                r2 = -2080896(0xffffffffffe03f80, float:NaN)
                r0 = r0 ^ r2
                goto L_0x0070
            L_0x003f:
                int r2 = r1 + 1
                byte r1 = r3[r1]
                int r4 = r1 << 28
                r0 = r0 ^ r4
                r4 = 266354560(0xfe03f80, float:2.2112565E-29)
                r0 = r0 ^ r4
                if (r1 >= 0) goto L_0x002f
                int r1 = r2 + 1
                byte r2 = r3[r2]
                if (r2 >= 0) goto L_0x0070
                int r2 = r1 + 1
                byte r1 = r3[r1]
                if (r1 >= 0) goto L_0x002f
                int r1 = r2 + 1
                byte r2 = r3[r2]
                if (r2 >= 0) goto L_0x0070
                int r2 = r1 + 1
                byte r1 = r3[r1]
                if (r1 >= 0) goto L_0x002f
                int r1 = r2 + 1
                byte r2 = r3[r2]
                if (r2 >= 0) goto L_0x0070
            L_0x006a:
                long r0 = r5.am()
                int r1 = (int) r0
                return r1
            L_0x0070:
                r5.j = r1
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.f.b.ak():int");
        }

        public final long al() throws IOException {
            long j2;
            long j3;
            long j4;
            byte b;
            int i2 = this.j;
            int i3 = this.h;
            if (i3 != i2) {
                int i4 = i2 + 1;
                byte[] bArr = this.g;
                byte b2 = bArr[i2];
                if (b2 >= 0) {
                    this.j = i4;
                    return (long) b2;
                } else if (i3 - i4 >= 9) {
                    int i5 = i4 + 1;
                    byte b3 = b2 ^ (bArr[i4] << 7);
                    if (b3 < 0) {
                        b = b3 ^ Byte.MIN_VALUE;
                    } else {
                        int i6 = i5 + 1;
                        byte b4 = b3 ^ (bArr[i5] << MqttWireMessage.MESSAGE_TYPE_DISCONNECT);
                        if (b4 >= 0) {
                            j3 = (long) (b4 ^ 16256);
                        } else {
                            i5 = i6 + 1;
                            byte b5 = b4 ^ (bArr[i6] << 21);
                            if (b5 < 0) {
                                b = b5 ^ -2080896;
                            } else {
                                long j5 = (long) b5;
                                int i7 = i5 + 1;
                                long j6 = (((long) bArr[i5]) << 28) ^ j5;
                                if (j6 >= 0) {
                                    j2 = j6 ^ 266354560;
                                    i5 = i7;
                                } else {
                                    int i8 = i7 + 1;
                                    long j7 = j6 ^ (((long) bArr[i7]) << 35);
                                    if (j7 < 0) {
                                        j4 = -34093383808L;
                                    } else {
                                        i6 = i8 + 1;
                                        long j8 = j7 ^ (((long) bArr[i8]) << 42);
                                        if (j8 >= 0) {
                                            j3 = j8 ^ 4363953127296L;
                                        } else {
                                            i8 = i6 + 1;
                                            j7 = j8 ^ (((long) bArr[i6]) << 49);
                                            if (j7 < 0) {
                                                j4 = -558586000294016L;
                                            } else {
                                                i6 = i8 + 1;
                                                j3 = (j7 ^ (((long) bArr[i8]) << 56)) ^ 71499008037633920L;
                                                if (j3 < 0) {
                                                    i8 = i6 + 1;
                                                    if (((long) bArr[i6]) >= 0) {
                                                        j2 = j3;
                                                        i5 = i8;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    j2 = j4 ^ j7;
                                    i5 = i8;
                                }
                                this.j = i5;
                                return j2;
                            }
                        }
                        i5 = i6;
                        j2 = j3;
                        this.j = i5;
                        return j2;
                    }
                    j2 = (long) b;
                    this.j = i5;
                    return j2;
                }
            }
            return am();
        }

        public final long am() throws IOException {
            long j2 = 0;
            for (int i2 = 0; i2 < 64; i2 += 7) {
                if (this.j == this.h) {
                    ao(1);
                }
                int i3 = this.j;
                this.j = i3 + 1;
                byte b = this.g[i3];
                j2 |= ((long) (b & Byte.MAX_VALUE)) << i2;
                if ((b & 128) == 0) {
                    return j2;
                }
            }
            throw q.e();
        }

        public final void an() {
            int i2 = this.h + this.i;
            this.h = i2;
            int i3 = this.l + i2;
            int i4 = this.m;
            if (i3 > i4) {
                int i5 = i3 - i4;
                this.i = i5;
                this.h = i2 - i5;
                return;
            }
            this.i = 0;
        }

        public final void ao(int i2) throws IOException {
            if (aq(i2)) {
                return;
            }
            if (i2 > (this.d - this.l) - this.j) {
                throw new q("Protocol message was too large.  May be malicious.  Use CodedInputStream.setSizeLimit() to increase the size limit. If reading multiple messages, consider resetting the counter between each message using CodedInputStream.resetSizeCounter().");
            }
            throw q.h();
        }

        public final void ap(int i2) throws IOException {
            int i3 = this.h;
            int i4 = this.j;
            if (i2 > i3 - i4 || i2 < 0) {
                InputStream inputStream = this.f;
                if (i2 >= 0) {
                    int i5 = this.l;
                    int i6 = i5 + i4;
                    int i7 = i6 + i2;
                    int i8 = this.m;
                    if (i7 <= i8) {
                        this.l = i6;
                        int i9 = i3 - i4;
                        this.h = 0;
                        this.j = 0;
                        while (i9 < i2) {
                            long j2 = (long) (i2 - i9);
                            try {
                                long skip = inputStream.skip(j2);
                                int i10 = (skip > 0 ? 1 : (skip == 0 ? 0 : -1));
                                if (i10 < 0 || skip > j2) {
                                    throw new IllegalStateException(inputStream.getClass() + "#skip returned invalid result: " + skip + "\nThe InputStream implementation is buggy.");
                                } else if (i10 == 0) {
                                    break;
                                } else {
                                    i9 += (int) skip;
                                }
                            } catch (q e) {
                                e.c = true;
                                throw e;
                            } catch (Throwable th) {
                                this.l += i9;
                                an();
                                throw th;
                            }
                        }
                        this.l += i9;
                        an();
                        if (i9 < i2) {
                            int i11 = this.h;
                            int i12 = i11 - this.j;
                            this.j = i11;
                            ao(1);
                            while (true) {
                                int i13 = i2 - i12;
                                int i14 = this.h;
                                if (i13 > i14) {
                                    i12 += i14;
                                    this.j = i14;
                                    ao(1);
                                } else {
                                    this.j = i13;
                                    return;
                                }
                            }
                        }
                    } else {
                        ap((i8 - i5) - i4);
                        throw q.h();
                    }
                } else {
                    throw q.f();
                }
            } else {
                this.j = i4 + i2;
            }
        }

        public final boolean aq(int i2) throws IOException {
            int i3 = this.j;
            int i4 = i3 + i2;
            int i5 = this.h;
            if (i4 > i5) {
                int i6 = this.l;
                int i7 = this.d;
                if (i2 > (i7 - i6) - i3 || i6 + i3 + i2 > this.m) {
                    return false;
                }
                byte[] bArr = this.g;
                if (i3 > 0) {
                    if (i5 > i3) {
                        System.arraycopy(bArr, i3, bArr, 0, i5 - i3);
                    }
                    this.l += i3;
                    this.h -= i3;
                    this.j = 0;
                }
                int i8 = this.h;
                int min = Math.min(bArr.length - i8, (i7 - this.l) - i8);
                InputStream inputStream = this.f;
                try {
                    int read = inputStream.read(bArr, i8, min);
                    if (read == 0 || read < -1 || read > bArr.length) {
                        throw new IllegalStateException(inputStream.getClass() + "#read(byte[]) returned invalid result: " + read + "\nThe InputStream implementation is buggy.");
                    } else if (read <= 0) {
                        return false;
                    } else {
                        this.h += read;
                        an();
                        if (this.h >= i2 || aq(i2)) {
                            return true;
                        }
                        return false;
                    }
                } catch (q e) {
                    e.c = true;
                    throw e;
                }
            } else {
                throw new IllegalStateException("refillBuffer() called when " + i2 + " bytes were already available in buffer");
            }
        }

        public final int d() {
            return this.l + this.j;
        }

        public final boolean e() throws IOException {
            return this.j == this.h && !aq(1);
        }

        public final void i(int i2) {
            this.m = i2;
            an();
        }

        public final int j(int i2) throws q {
            if (i2 >= 0) {
                int i3 = this.l + this.j + i2;
                if (i3 >= 0) {
                    int i4 = this.m;
                    if (i3 <= i4) {
                        this.m = i3;
                        an();
                        return i4;
                    }
                    throw q.h();
                }
                throw new q("Protocol message was too large.  May be malicious.  Use CodedInputStream.setSizeLimit() to increase the size limit. If reading multiple messages, consider resetting the counter between each message using CodedInputStream.resetSizeCounter().");
            }
            throw q.f();
        }

        public final boolean k() throws IOException {
            return al() != 0;
        }

        public final cp.h l() throws IOException {
            int ak = ak();
            int i2 = this.h;
            int i3 = this.j;
            int i4 = i2 - i3;
            byte[] bArr = this.g;
            if (ak <= i4 && ak > 0) {
                cp.h f2 = cp.f(bArr, i3, ak);
                this.j += ak;
                return f2;
            } else if (ak == 0) {
                return cp.f;
            } else {
                if (ak >= 0) {
                    byte[] ag = ag(ak);
                    if (ag != null) {
                        return cp.f(ag, 0, ag.length);
                    }
                    int i5 = this.j;
                    int i6 = this.h;
                    int i7 = i6 - i5;
                    this.l += i6;
                    this.j = 0;
                    this.h = 0;
                    ArrayList ah = ah(ak - i7);
                    byte[] bArr2 = new byte[ak];
                    System.arraycopy(bArr, i5, bArr2, 0, i7);
                    Iterator it = ah.iterator();
                    while (it.hasNext()) {
                        byte[] bArr3 = (byte[]) it.next();
                        System.arraycopy(bArr3, 0, bArr2, i7, bArr3.length);
                        i7 += bArr3.length;
                    }
                    cp.h hVar = cp.f;
                    return new cp.h(bArr2);
                }
                throw q.f();
            }
        }

        public final double m() throws IOException {
            return Double.longBitsToDouble(aj());
        }

        public final int n() throws IOException {
            return ak();
        }

        public final int o() throws IOException {
            return ai();
        }

        public final long p() throws IOException {
            return aj();
        }

        public final float q() throws IOException {
            return Float.intBitsToFloat(ai());
        }

        public final int r() throws IOException {
            return ak();
        }

        public final long s() throws IOException {
            return al();
        }

        public final int u() throws IOException {
            return ai();
        }

        public final long v() throws IOException {
            return aj();
        }

        public final int w() throws IOException {
            return f.b(ak());
        }

        public final long x() throws IOException {
            return f.c(al());
        }

        public final String y() throws IOException {
            int ak = ak();
            byte[] bArr = this.g;
            if (ak > 0) {
                int i2 = this.h;
                int i3 = this.j;
                if (ak <= i2 - i3) {
                    String str = new String(bArr, i3, ak, p.a);
                    this.j += ak;
                    return str;
                }
            }
            if (ak == 0) {
                return "";
            }
            if (ak < 0) {
                throw q.f();
            } else if (ak > this.h) {
                return new String(af(ak), p.a);
            } else {
                ao(ak);
                String str2 = new String(bArr, this.j, ak, p.a);
                this.j += ak;
                return str2;
            }
        }

        public final String z() throws IOException {
            int ak = ak();
            int i2 = this.j;
            int i3 = this.h;
            int i4 = i3 - i2;
            byte[] bArr = this.g;
            if (ak <= i4 && ak > 0) {
                this.j = i2 + ak;
            } else if (ak == 0) {
                return "";
            } else {
                if (ak >= 0) {
                    i2 = 0;
                    if (ak <= i3) {
                        ao(ak);
                        this.j = ak + 0;
                    } else {
                        bArr = af(ak);
                    }
                } else {
                    throw q.f();
                }
            }
            return gd.a.a(bArr, i2, ak);
        }
    }

    public static final class c extends f {
        public final ByteBuffer f;
        public final long g;
        public long h;
        public long i;
        public final long j;
        public int k;
        public int l;
        public int m = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;

        public c(ByteBuffer byteBuffer, boolean z) {
            this.f = byteBuffer.duplicate();
            long j2 = fd.c.j(fd.g, byteBuffer);
            this.g = j2;
            this.h = ((long) byteBuffer.limit()) + j2;
            long position = j2 + ((long) byteBuffer.position());
            this.i = position;
            this.j = position;
        }

        public final void a(int i2) throws q {
            if (this.l != i2) {
                throw q.a();
            }
        }

        public final int aa() throws IOException {
            if (e()) {
                this.l = 0;
                return 0;
            }
            int ah = ah();
            this.l = ah;
            if ((ah >>> 3) != 0) {
                return ah;
            }
            throw q.b();
        }

        public final int ab() throws IOException {
            return ah();
        }

        public final long ac() throws IOException {
            return ai();
        }

        public final boolean ad(int i2) throws IOException {
            int i3 = i2 & 7;
            int i4 = 0;
            if (i3 == 0) {
                if (((int) (this.h - this.i)) >= 10) {
                    while (i4 < 10) {
                        long j2 = this.i;
                        this.i = j2 + 1;
                        if (fd.h(j2) < 0) {
                            i4++;
                        }
                    }
                    throw q.e();
                }
                while (i4 < 10) {
                    long j3 = this.i;
                    if (j3 != this.h) {
                        this.i = j3 + 1;
                        if (fd.h(j3) < 0) {
                            i4++;
                        }
                    } else {
                        throw q.h();
                    }
                }
                throw q.e();
                return true;
            } else if (i3 == 1) {
                al(8);
                return true;
            } else if (i3 == 2) {
                al(ah());
                return true;
            } else if (i3 == 3) {
                ae();
                a(((i2 >>> 3) << 3) | 4);
                return true;
            } else if (i3 == 4) {
                if (this.b == 0) {
                    a(0);
                }
                return false;
            } else if (i3 == 5) {
                al(4);
                return true;
            } else {
                int i5 = q.f;
                throw new q.a();
            }
        }

        public final int af() throws IOException {
            long j2 = this.i;
            if (this.h - j2 >= 4) {
                this.i = 4 + j2;
                return ((fd.h(j2 + 3) & 255) << 24) | (fd.h(j2) & 255) | ((fd.h(1 + j2) & 255) << 8) | ((fd.h(2 + j2) & 255) << 16);
            }
            throw q.h();
        }

        public final long ag() throws IOException {
            long j2 = this.i;
            if (this.h - j2 >= 8) {
                this.i = 8 + j2;
                return ((((long) fd.h(j2 + 7)) & 255) << 56) | (((long) fd.h(j2)) & 255) | ((((long) fd.h(1 + j2)) & 255) << 8) | ((((long) fd.h(2 + j2)) & 255) << 16) | ((((long) fd.h(3 + j2)) & 255) << 24) | ((((long) fd.h(4 + j2)) & 255) << 32) | ((((long) fd.h(5 + j2)) & 255) << 40) | ((((long) fd.h(6 + j2)) & 255) << 48);
            }
            throw q.h();
        }

        /* JADX WARNING: Code restructure failed: missing block: B:29:0x0083, code lost:
            if (defpackage.fd.h(r4) < 0) goto L_0x0085;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final int ah() throws java.io.IOException {
            /*
                r10 = this;
                long r0 = r10.i
                long r2 = r10.h
                int r4 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
                if (r4 != 0) goto L_0x000a
                goto L_0x0085
            L_0x000a:
                r2 = 1
                long r4 = r0 + r2
                byte r0 = defpackage.fd.h(r0)
                if (r0 < 0) goto L_0x0017
                r10.i = r4
                return r0
            L_0x0017:
                long r6 = r10.h
                long r6 = r6 - r4
                r8 = 9
                int r1 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
                if (r1 >= 0) goto L_0x0021
                goto L_0x0085
            L_0x0021:
                long r6 = r4 + r2
                byte r1 = defpackage.fd.h(r4)
                int r1 = r1 << 7
                r0 = r0 ^ r1
                if (r0 >= 0) goto L_0x002f
                r0 = r0 ^ -128(0xffffffffffffff80, float:NaN)
                goto L_0x008b
            L_0x002f:
                long r4 = r6 + r2
                byte r1 = defpackage.fd.h(r6)
                int r1 = r1 << 14
                r0 = r0 ^ r1
                if (r0 < 0) goto L_0x003e
                r0 = r0 ^ 16256(0x3f80, float:2.278E-41)
            L_0x003c:
                r6 = r4
                goto L_0x008b
            L_0x003e:
                long r6 = r4 + r2
                byte r1 = defpackage.fd.h(r4)
                int r1 = r1 << 21
                r0 = r0 ^ r1
                if (r0 >= 0) goto L_0x004e
                r1 = -2080896(0xffffffffffe03f80, float:NaN)
                r0 = r0 ^ r1
                goto L_0x008b
            L_0x004e:
                long r4 = r6 + r2
                byte r1 = defpackage.fd.h(r6)
                int r6 = r1 << 28
                r0 = r0 ^ r6
                r6 = 266354560(0xfe03f80, float:2.2112565E-29)
                r0 = r0 ^ r6
                if (r1 >= 0) goto L_0x003c
                long r6 = r4 + r2
                byte r1 = defpackage.fd.h(r4)
                if (r1 >= 0) goto L_0x008b
                long r4 = r6 + r2
                byte r1 = defpackage.fd.h(r6)
                if (r1 >= 0) goto L_0x003c
                long r6 = r4 + r2
                byte r1 = defpackage.fd.h(r4)
                if (r1 >= 0) goto L_0x008b
                long r4 = r6 + r2
                byte r1 = defpackage.fd.h(r6)
                if (r1 >= 0) goto L_0x003c
                long r6 = r4 + r2
                byte r1 = defpackage.fd.h(r4)
                if (r1 >= 0) goto L_0x008b
            L_0x0085:
                long r0 = r10.aj()
                int r1 = (int) r0
                return r1
            L_0x008b:
                r10.i = r6
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.f.c.ah():int");
        }

        public final long ai() throws IOException {
            long j2;
            long j3;
            long j4;
            byte b;
            long j5 = this.i;
            if (this.h != j5) {
                long j6 = j5 + 1;
                byte h2 = fd.h(j5);
                if (h2 >= 0) {
                    this.i = j6;
                    return (long) h2;
                } else if (this.h - j6 >= 9) {
                    long j7 = j6 + 1;
                    byte h3 = h2 ^ (fd.h(j6) << 7);
                    if (h3 < 0) {
                        b = h3 ^ Byte.MIN_VALUE;
                    } else {
                        long j8 = j7 + 1;
                        byte h4 = h3 ^ (fd.h(j7) << MqttWireMessage.MESSAGE_TYPE_DISCONNECT);
                        if (h4 >= 0) {
                            j2 = (long) (h4 ^ 16256);
                        } else {
                            j7 = j8 + 1;
                            byte h5 = h4 ^ (fd.h(j8) << 21);
                            if (h5 < 0) {
                                b = h5 ^ -2080896;
                            } else {
                                j8 = j7 + 1;
                                long h6 = ((long) h5) ^ (((long) fd.h(j7)) << 28);
                                if (h6 >= 0) {
                                    j4 = 266354560;
                                } else {
                                    long j9 = j8 + 1;
                                    long h7 = h6 ^ (((long) fd.h(j8)) << 35);
                                    if (h7 < 0) {
                                        j3 = -34093383808L;
                                    } else {
                                        j8 = j9 + 1;
                                        h6 = h7 ^ (((long) fd.h(j9)) << 42);
                                        if (h6 >= 0) {
                                            j4 = 4363953127296L;
                                        } else {
                                            j9 = j8 + 1;
                                            h7 = h6 ^ (((long) fd.h(j8)) << 49);
                                            if (h7 < 0) {
                                                j3 = -558586000294016L;
                                            } else {
                                                j8 = j9 + 1;
                                                j2 = (h7 ^ (((long) fd.h(j9)) << 56)) ^ 71499008037633920L;
                                                if (j2 < 0) {
                                                    long j10 = 1 + j8;
                                                    if (((long) fd.h(j8)) >= 0) {
                                                        j7 = j10;
                                                        this.i = j7;
                                                        return j2;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    j2 = h7 ^ j3;
                                    j7 = j9;
                                    this.i = j7;
                                    return j2;
                                }
                                j2 = h6 ^ j4;
                            }
                        }
                        j7 = j8;
                        this.i = j7;
                        return j2;
                    }
                    j2 = (long) b;
                    this.i = j7;
                    return j2;
                }
            }
            return aj();
        }

        public final long aj() throws IOException {
            long j2 = 0;
            int i2 = 0;
            while (i2 < 64) {
                long j3 = this.i;
                if (j3 != this.h) {
                    this.i = 1 + j3;
                    byte h2 = fd.h(j3);
                    j2 |= ((long) (h2 & Byte.MAX_VALUE)) << i2;
                    if ((h2 & 128) == 0) {
                        return j2;
                    }
                    i2 += 7;
                } else {
                    throw q.h();
                }
            }
            throw q.e();
        }

        public final void ak() {
            long j2 = this.h + ((long) this.k);
            this.h = j2;
            int i2 = (int) (j2 - this.j);
            int i3 = this.m;
            if (i2 > i3) {
                int i4 = i2 - i3;
                this.k = i4;
                this.h = j2 - ((long) i4);
                return;
            }
            this.k = 0;
        }

        public final void al(int i2) throws IOException {
            if (i2 >= 0) {
                long j2 = this.h;
                long j3 = this.i;
                if (i2 <= ((int) (j2 - j3))) {
                    this.i = j3 + ((long) i2);
                    return;
                }
            }
            if (i2 < 0) {
                throw q.f();
            }
            throw q.h();
        }

        public final int d() {
            return (int) (this.i - this.j);
        }

        public final boolean e() throws IOException {
            return this.i == this.h;
        }

        public final void i(int i2) {
            this.m = i2;
            ak();
        }

        public final int j(int i2) throws q {
            if (i2 >= 0) {
                int d = d() + i2;
                int i3 = this.m;
                if (d <= i3) {
                    this.m = d;
                    ak();
                    return i3;
                }
                throw q.h();
            }
            throw q.f();
        }

        public final boolean k() throws IOException {
            return ai() != 0;
        }

        public final cp.h l() throws IOException {
            int ah = ah();
            if (ah > 0) {
                long j2 = this.h;
                long j3 = this.i;
                if (ah <= ((int) (j2 - j3))) {
                    byte[] bArr = new byte[ah];
                    long j4 = (long) ah;
                    fd.c.c(j3, bArr, 0, j4);
                    this.i += j4;
                    cp.h hVar = cp.f;
                    return new cp.h(bArr);
                }
            }
            if (ah == 0) {
                return cp.f;
            }
            if (ah < 0) {
                throw q.f();
            }
            throw q.h();
        }

        public final double m() throws IOException {
            return Double.longBitsToDouble(ag());
        }

        public final int n() throws IOException {
            return ah();
        }

        public final int o() throws IOException {
            return af();
        }

        public final long p() throws IOException {
            return ag();
        }

        public final float q() throws IOException {
            return Float.intBitsToFloat(af());
        }

        public final int r() throws IOException {
            return ah();
        }

        public final long s() throws IOException {
            return ai();
        }

        public final int u() throws IOException {
            return af();
        }

        public final long v() throws IOException {
            return ag();
        }

        public final int w() throws IOException {
            return f.b(ah());
        }

        public final long x() throws IOException {
            return f.c(ai());
        }

        public final String y() throws IOException {
            int ah = ah();
            if (ah > 0) {
                long j2 = this.h;
                long j3 = this.i;
                if (ah <= ((int) (j2 - j3))) {
                    byte[] bArr = new byte[ah];
                    long j4 = (long) ah;
                    fd.c.c(j3, bArr, 0, j4);
                    String str = new String(bArr, p.a);
                    this.i += j4;
                    return str;
                }
            }
            if (ah == 0) {
                return "";
            }
            if (ah < 0) {
                throw q.f();
            }
            throw q.h();
        }

        public final String z() throws IOException {
            int ah = ah();
            if (ah > 0) {
                long j2 = this.h;
                long j3 = this.i;
                if (ah <= ((int) (j2 - j3))) {
                    String b = gd.b(this.f, (int) (j3 - this.g), ah);
                    this.i += (long) ah;
                    return b;
                }
            }
            if (ah == 0) {
                return "";
            }
            if (ah <= 0) {
                throw q.f();
            }
            throw q.h();
        }
    }

    public static int b(int i) {
        return (-(i & 1)) ^ (i >>> 1);
    }

    public static long c(long j) {
        return (-(j & 1)) ^ (j >>> 1);
    }

    public static a f(byte[] bArr, int i, int i2, boolean z) {
        a aVar = new a(bArr, i, i2, z);
        try {
            aVar.j(i2);
            return aVar;
        } catch (q e2) {
            throw new IllegalArgumentException(e2);
        }
    }

    public static f g(InputStream inputStream) {
        if (inputStream != null) {
            return new b(inputStream);
        }
        byte[] bArr = p.b;
        return f(bArr, 0, bArr.length, false);
    }

    public static f h(ByteBuffer byteBuffer, boolean z) {
        if (byteBuffer.hasArray()) {
            return f(byteBuffer.array(), byteBuffer.position() + byteBuffer.arrayOffset(), byteBuffer.remaining(), z);
        } else if (byteBuffer.isDirect() && fd.d) {
            return new c(byteBuffer, z);
        } else {
            int remaining = byteBuffer.remaining();
            byte[] bArr = new byte[remaining];
            byteBuffer.duplicate().get(bArr);
            return f(bArr, 0, remaining, true);
        }
    }

    public static int t(InputStream inputStream, int i) throws IOException {
        if ((i & 128) == 0) {
            return i;
        }
        int i2 = i & Token.VOID;
        int i3 = 7;
        while (i3 < 32) {
            int read = inputStream.read();
            if (read != -1) {
                i2 |= (read & Token.VOID) << i3;
                if ((read & 128) == 0) {
                    return i2;
                }
                i3 += 7;
            } else {
                throw q.h();
            }
        }
        while (i3 < 64) {
            int read2 = inputStream.read();
            if (read2 == -1) {
                throw q.h();
            } else if ((read2 & 128) == 0) {
                return i2;
            } else {
                i3 += 7;
            }
        }
        throw q.e();
    }

    public abstract void a(int i) throws q;

    public abstract int aa() throws IOException;

    public abstract int ab() throws IOException;

    public abstract long ac() throws IOException;

    public abstract boolean ad(int i) throws IOException;

    public final void ae() throws IOException {
        int aa;
        do {
            aa = aa();
            if (aa != 0) {
                int i = this.a;
                int i2 = this.b;
                if (i + i2 < this.c) {
                    this.b = i2 + 1;
                    this.b--;
                } else {
                    throw new q("Protocol message had too many levels of nesting.  May be malicious.  Use setRecursionLimit() to increase the recursion depth limit.");
                }
            } else {
                return;
            }
        } while (ad(aa));
    }

    public abstract int d();

    public abstract boolean e() throws IOException;

    public abstract void i(int i);

    public abstract int j(int i) throws q;

    public abstract boolean k() throws IOException;

    public abstract cp.h l() throws IOException;

    public abstract double m() throws IOException;

    public abstract int n() throws IOException;

    public abstract int o() throws IOException;

    public abstract long p() throws IOException;

    public abstract float q() throws IOException;

    public abstract int r() throws IOException;

    public abstract long s() throws IOException;

    public abstract int u() throws IOException;

    public abstract long v() throws IOException;

    public abstract int w() throws IOException;

    public abstract long x() throws IOException;

    public abstract String y() throws IOException;

    public abstract String z() throws IOException;
}
