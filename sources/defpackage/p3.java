package defpackage;

import androidx.core.view.ViewCompat;
import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.logging.Logger;
import java.util.zip.CRC32;
import java.util.zip.Inflater;

/* renamed from: p3  reason: default package */
public final class p3 implements jb {
    public int c = 0;
    public final da f;
    public final Inflater g;
    public final u3 h;
    public final CRC32 i = new CRC32();

    public p3(jb jbVar) {
        if (jbVar != null) {
            Inflater inflater = new Inflater(true);
            this.g = inflater;
            Logger logger = s7.a;
            da daVar = new da(jbVar);
            this.f = daVar;
            this.h = new u3(daVar, inflater);
            return;
        }
        throw new IllegalArgumentException("source == null");
    }

    public static void d(int i2, int i3, String str) throws IOException {
        if (i3 != i2) {
            throw new IOException(String.format("%s: actual 0x%08x != expected 0x%08x", new Object[]{str, Integer.valueOf(i3), Integer.valueOf(i2)}));
        }
    }

    public final void close() throws IOException {
        this.h.close();
    }

    public final void e(long j, ck ckVar, long j2) {
        qa qaVar = ckVar.c;
        while (true) {
            int i2 = qaVar.c;
            int i3 = qaVar.b;
            if (j < ((long) (i2 - i3))) {
                break;
            }
            j -= (long) (i2 - i3);
            qaVar = qaVar.f;
        }
        while (j2 > 0) {
            int i4 = (int) (((long) qaVar.b) + j);
            int min = (int) Math.min((long) (qaVar.c - i4), j2);
            this.i.update(qaVar.a, i4, min);
            j2 -= (long) min;
            qaVar = qaVar.f;
            j = 0;
        }
    }

    public final long read(ck ckVar, long j) throws IOException {
        da daVar;
        boolean z;
        ck ckVar2;
        long j2;
        ck ckVar3 = ckVar;
        long j3 = j;
        int i2 = (j3 > 0 ? 1 : (j3 == 0 ? 0 : -1));
        if (i2 < 0) {
            throw new IllegalArgumentException(y2.g("byteCount < 0: ", j3));
        } else if (i2 == 0) {
            return 0;
        } else {
            int i3 = this.c;
            CRC32 crc32 = this.i;
            da daVar2 = this.f;
            if (i3 == 0) {
                daVar2.w(10);
                ck ckVar4 = daVar2.c;
                byte i4 = ckVar4.i(3);
                if (((i4 >> 1) & 1) == 1) {
                    z = true;
                } else {
                    z = false;
                }
                if (z) {
                    ckVar2 = ckVar4;
                    e(0, daVar2.c, 10);
                } else {
                    ckVar2 = ckVar4;
                }
                d(8075, daVar2.readShort(), "ID1ID2");
                daVar2.skip(8);
                if (((i4 >> 2) & 1) == 1) {
                    daVar2.w(2);
                    if (z) {
                        e(0, daVar2.c, 2);
                    }
                    short readShort = ckVar2.readShort();
                    Charset charset = jd.a;
                    short s = readShort & 65535;
                    long j4 = (long) ((short) (((s & 255) << 8) | ((s & 65280) >>> 8)));
                    daVar2.w(j4);
                    if (z) {
                        e(0, daVar2.c, j4);
                        j2 = j4;
                    } else {
                        j2 = j4;
                    }
                    daVar2.skip(j2);
                }
                if (((i4 >> 3) & 1) == 1) {
                    daVar = daVar2;
                    long d = daVar2.d((byte) 0, 0, Long.MAX_VALUE);
                    if (d != -1) {
                        if (z) {
                            e(0, daVar.c, d + 1);
                        }
                        daVar.skip(d + 1);
                    } else {
                        throw new EOFException();
                    }
                } else {
                    daVar = daVar2;
                }
                if (((i4 >> 4) & 1) == 1) {
                    long d2 = daVar.d((byte) 0, 0, Long.MAX_VALUE);
                    if (d2 != -1) {
                        if (z) {
                            e(0, daVar.c, d2 + 1);
                        }
                        daVar.skip(d2 + 1);
                    } else {
                        throw new EOFException();
                    }
                }
                if (z) {
                    daVar.w(2);
                    short readShort2 = ckVar2.readShort();
                    Charset charset2 = jd.a;
                    short s2 = readShort2 & 65535;
                    d((short) (((s2 & 255) << 8) | ((65280 & s2) >>> 8)), (short) ((int) crc32.getValue()), "FHCRC");
                    crc32.reset();
                }
                this.c = 1;
            } else {
                daVar = daVar2;
            }
            if (this.c == 1) {
                long j5 = ckVar3.f;
                long read = this.h.read(ckVar3, j3);
                if (read != -1) {
                    e(j5, ckVar, read);
                    return read;
                }
                this.c = 2;
            }
            if (this.c == 2) {
                daVar.w(4);
                int readInt = daVar.c.readInt();
                Charset charset3 = jd.a;
                d(((readInt & 255) << 24) | ((readInt & ViewCompat.MEASURED_STATE_MASK) >>> 24) | ((readInt & 16711680) >>> 8) | ((65280 & readInt) << 8), (int) crc32.getValue(), "CRC");
                daVar.w(4);
                int readInt2 = daVar.c.readInt();
                d(((readInt2 & 255) << 24) | ((readInt2 & ViewCompat.MEASURED_STATE_MASK) >>> 24) | ((readInt2 & 16711680) >>> 8) | ((65280 & readInt2) << 8), (int) this.g.getBytesWritten(), "ISIZE");
                this.c = 3;
                if (!daVar.g()) {
                    throw new IOException("gzip finished without exhausting source");
                }
            }
            return -1;
        }
    }

    public final lc timeout() {
        return this.f.timeout();
    }
}
