package com.google.protobuf;

import com.google.protobuf.i;
import com.google.protobuf.n;
import com.google.protobuf.p;
import java.io.IOException;
import java.util.Arrays;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;
import org.mozilla.javascript.Token;

public final class d {

    public static final class a {
        public int a;
        public long b;
        public Object c;
        public final i d;
        public int e;

        public a(i iVar) {
            iVar.getClass();
            this.d = iVar;
        }
    }

    public static int a(int i, byte[] bArr, int i2, int i3, p.i<?> iVar, a aVar) {
        boolean z;
        boolean z2;
        e eVar = (e) iVar;
        int ak = ak(bArr, i2, aVar);
        if (aVar.b != 0) {
            z = true;
        } else {
            z = false;
        }
        eVar.b(z);
        while (ak < i3) {
            int ai = ai(bArr, ak, aVar);
            if (i != aVar.a) {
                break;
            }
            ak = ak(bArr, ai, aVar);
            if (aVar.b != 0) {
                z2 = true;
            } else {
                z2 = false;
            }
            eVar.b(z2);
        }
        return ak;
    }

    public static int aa(int i, byte[] bArr, int i2, int i3, p.i<?> iVar, a aVar) {
        o oVar = (o) iVar;
        int ai = ai(bArr, i2, aVar);
        oVar.b(f.b(aVar.a));
        while (ai < i3) {
            int ai2 = ai(bArr, ai, aVar);
            if (i != aVar.a) {
                break;
            }
            ai = ai(bArr, ai2, aVar);
            oVar.b(f.b(aVar.a));
        }
        return ai;
    }

    public static int ab(int i, byte[] bArr, int i2, int i3, p.i<?> iVar, a aVar) {
        u uVar = (u) iVar;
        int ak = ak(bArr, i2, aVar);
        uVar.b(f.c(aVar.b));
        while (ak < i3) {
            int ai = ai(bArr, ak, aVar);
            if (i != aVar.a) {
                break;
            }
            ak = ak(bArr, ai, aVar);
            uVar.b(f.c(aVar.b));
        }
        return ak;
    }

    public static int ac(byte[] bArr, int i, a aVar) throws q {
        int ai = ai(bArr, i, aVar);
        int i2 = aVar.a;
        if (i2 < 0) {
            throw q.f();
        } else if (i2 == 0) {
            aVar.c = "";
            return ai;
        } else {
            aVar.c = new String(bArr, ai, i2, p.a);
            return ai + i2;
        }
    }

    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0044 A[EDGE_INSN: B:21:0x0044->B:17:0x0044 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x001d  */
    public static int ad(int r4, byte[] r5, int r6, int r7, com.google.protobuf.p.i<?> r8, com.google.protobuf.d.a r9) throws com.google.protobuf.q {
        /*
            int r6 = ai(r5, r6, r9)
            int r0 = r9.a
            if (r0 < 0) goto L_0x0045
            java.lang.String r1 = ""
            if (r0 != 0) goto L_0x0010
            r8.add(r1)
            goto L_0x001b
        L_0x0010:
            java.lang.String r2 = new java.lang.String
            java.nio.charset.Charset r3 = com.google.protobuf.p.a
            r2.<init>(r5, r6, r0, r3)
            r8.add(r2)
        L_0x001a:
            int r6 = r6 + r0
        L_0x001b:
            if (r6 >= r7) goto L_0x0044
            int r0 = ai(r5, r6, r9)
            int r2 = r9.a
            if (r4 == r2) goto L_0x0026
            goto L_0x0044
        L_0x0026:
            int r6 = ai(r5, r0, r9)
            int r0 = r9.a
            if (r0 < 0) goto L_0x003f
            if (r0 != 0) goto L_0x0034
            r8.add(r1)
            goto L_0x001b
        L_0x0034:
            java.lang.String r2 = new java.lang.String
            java.nio.charset.Charset r3 = com.google.protobuf.p.a
            r2.<init>(r5, r6, r0, r3)
            r8.add(r2)
            goto L_0x001a
        L_0x003f:
            com.google.protobuf.q r4 = com.google.protobuf.q.f()
            throw r4
        L_0x0044:
            return r6
        L_0x0045:
            com.google.protobuf.q r4 = com.google.protobuf.q.f()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.d.ad(int, byte[], int, int, com.google.protobuf.p$i, com.google.protobuf.d$a):int");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x003c, code lost:
        r2 = r7 + r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0042, code lost:
        if (defpackage.gd.f(r6, r7, r2) == false) goto L_0x004f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0044, code lost:
        r9.add(new java.lang.String(r6, r7, r0, com.google.protobuf.p.a));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0053, code lost:
        throw com.google.protobuf.q.c();
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0025  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0059 A[EDGE_INSN: B:28:0x0059->B:23:0x0059 ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int ae(int r5, byte[] r6, int r7, int r8, com.google.protobuf.p.i<?> r9, com.google.protobuf.d.a r10) throws com.google.protobuf.q {
        /*
            int r7 = ai(r6, r7, r10)
            int r0 = r10.a
            if (r0 < 0) goto L_0x005f
            java.lang.String r1 = ""
            if (r0 != 0) goto L_0x0010
            r9.add(r1)
            goto L_0x0023
        L_0x0010:
            int r2 = r7 + r0
            boolean r3 = defpackage.gd.f(r6, r7, r2)
            if (r3 == 0) goto L_0x005a
            java.lang.String r3 = new java.lang.String
            java.nio.charset.Charset r4 = com.google.protobuf.p.a
            r3.<init>(r6, r7, r0, r4)
            r9.add(r3)
        L_0x0022:
            r7 = r2
        L_0x0023:
            if (r7 >= r8) goto L_0x0059
            int r0 = ai(r6, r7, r10)
            int r2 = r10.a
            if (r5 == r2) goto L_0x002e
            goto L_0x0059
        L_0x002e:
            int r7 = ai(r6, r0, r10)
            int r0 = r10.a
            if (r0 < 0) goto L_0x0054
            if (r0 != 0) goto L_0x003c
            r9.add(r1)
            goto L_0x0023
        L_0x003c:
            int r2 = r7 + r0
            boolean r3 = defpackage.gd.f(r6, r7, r2)
            if (r3 == 0) goto L_0x004f
            java.lang.String r3 = new java.lang.String
            java.nio.charset.Charset r4 = com.google.protobuf.p.a
            r3.<init>(r6, r7, r0, r4)
            r9.add(r3)
            goto L_0x0022
        L_0x004f:
            com.google.protobuf.q r5 = com.google.protobuf.q.c()
            throw r5
        L_0x0054:
            com.google.protobuf.q r5 = com.google.protobuf.q.f()
            throw r5
        L_0x0059:
            return r7
        L_0x005a:
            com.google.protobuf.q r5 = com.google.protobuf.q.c()
            throw r5
        L_0x005f:
            com.google.protobuf.q r5 = com.google.protobuf.q.f()
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.d.ae(int, byte[], int, int, com.google.protobuf.p$i, com.google.protobuf.d$a):int");
    }

    public static int af(byte[] bArr, int i, a aVar) throws q {
        int ai = ai(bArr, i, aVar);
        int i2 = aVar.a;
        if (i2 < 0) {
            throw q.f();
        } else if (i2 == 0) {
            aVar.c = "";
            return ai;
        } else {
            aVar.c = gd.a.a(bArr, ai, i2);
            return ai + i2;
        }
    }

    public static int ag(int i, byte[] bArr, int i2, int i3, ah ahVar, a aVar) throws q {
        if ((i >>> 3) != 0) {
            int i4 = i & 7;
            if (i4 == 0) {
                int ak = ak(bArr, i2, aVar);
                ahVar.f(i, Long.valueOf(aVar.b));
                return ak;
            } else if (i4 == 1) {
                ahVar.f(i, Long.valueOf(j(bArr, i2)));
                return i2 + 8;
            } else if (i4 == 2) {
                int ai = ai(bArr, i2, aVar);
                int i5 = aVar.a;
                if (i5 < 0) {
                    throw q.f();
                } else if (i5 <= bArr.length - ai) {
                    if (i5 == 0) {
                        ahVar.f(i, cp.f);
                    } else {
                        ahVar.f(i, cp.f(bArr, ai, i5));
                    }
                    return ai + i5;
                } else {
                    throw q.h();
                }
            } else if (i4 == 3) {
                ah ahVar2 = new ah();
                int i6 = (i & -8) | 4;
                int i7 = aVar.e + 1;
                aVar.e = i7;
                if (i7 < 100) {
                    int i8 = 0;
                    while (true) {
                        if (i2 >= i3) {
                            break;
                        }
                        int ai2 = ai(bArr, i2, aVar);
                        int i9 = aVar.a;
                        if (i9 == i6) {
                            i8 = i9;
                            i2 = ai2;
                            break;
                        }
                        i8 = i9;
                        i2 = ag(i9, bArr, ai2, i3, ahVar2, aVar);
                    }
                    aVar.e--;
                    if (i2 > i3 || i8 != i6) {
                        throw q.g();
                    }
                    ahVar.f(i, ahVar2);
                    return i2;
                }
                throw new q("Protocol message had too many levels of nesting.  May be malicious.  Use setRecursionLimit() to increase the recursion depth limit.");
            } else if (i4 == 5) {
                ahVar.f(i, Integer.valueOf(h(bArr, i2)));
                return i2 + 4;
            } else {
                throw q.b();
            }
        } else {
            throw q.b();
        }
    }

    public static int ah(int i, byte[] bArr, int i2, a aVar) {
        int i3 = i & Token.VOID;
        int i4 = i2 + 1;
        byte b = bArr[i2];
        if (b >= 0) {
            aVar.a = i3 | (b << 7);
            return i4;
        }
        int i5 = i3 | ((b & Byte.MAX_VALUE) << 7);
        int i6 = i4 + 1;
        byte b2 = bArr[i4];
        if (b2 >= 0) {
            aVar.a = i5 | (b2 << MqttWireMessage.MESSAGE_TYPE_DISCONNECT);
            return i6;
        }
        int i7 = i5 | ((b2 & Byte.MAX_VALUE) << MqttWireMessage.MESSAGE_TYPE_DISCONNECT);
        int i8 = i6 + 1;
        byte b3 = bArr[i6];
        if (b3 >= 0) {
            aVar.a = i7 | (b3 << 21);
            return i8;
        }
        int i9 = i7 | ((b3 & Byte.MAX_VALUE) << 21);
        int i10 = i8 + 1;
        byte b4 = bArr[i8];
        if (b4 >= 0) {
            aVar.a = i9 | (b4 << 28);
            return i10;
        }
        int i11 = i9 | ((b4 & Byte.MAX_VALUE) << 28);
        while (true) {
            int i12 = i10 + 1;
            if (bArr[i10] < 0) {
                i10 = i12;
            } else {
                aVar.a = i11;
                return i12;
            }
        }
    }

    public static int ai(byte[] bArr, int i, a aVar) {
        int i2 = i + 1;
        byte b = bArr[i];
        if (b < 0) {
            return ah(b, bArr, i2, aVar);
        }
        aVar.a = b;
        return i2;
    }

    public static int aj(int i, byte[] bArr, int i2, int i3, p.i<?> iVar, a aVar) {
        o oVar = (o) iVar;
        int ai = ai(bArr, i2, aVar);
        oVar.b(aVar.a);
        while (ai < i3) {
            int ai2 = ai(bArr, ai, aVar);
            if (i != aVar.a) {
                break;
            }
            ai = ai(bArr, ai2, aVar);
            oVar.b(aVar.a);
        }
        return ai;
    }

    public static int ak(byte[] bArr, int i, a aVar) {
        int i2 = i + 1;
        long j = (long) bArr[i];
        if (j >= 0) {
            aVar.b = j;
            return i2;
        }
        int i3 = i2 + 1;
        byte b = bArr[i2];
        long j2 = (j & 127) | (((long) (b & Byte.MAX_VALUE)) << 7);
        int i4 = 7;
        while (b < 0) {
            int i5 = i3 + 1;
            byte b2 = bArr[i3];
            i4 += 7;
            j2 |= ((long) (b2 & Byte.MAX_VALUE)) << i4;
            int i6 = i5;
            b = b2;
            i3 = i6;
        }
        aVar.b = j2;
        return i3;
    }

    public static int al(int i, byte[] bArr, int i2, int i3, p.i<?> iVar, a aVar) {
        u uVar = (u) iVar;
        int ak = ak(bArr, i2, aVar);
        uVar.b(aVar.b);
        while (ak < i3) {
            int ai = ai(bArr, ak, aVar);
            if (i != aVar.a) {
                break;
            }
            ak = ak(bArr, ai, aVar);
            uVar.b(aVar.b);
        }
        return ak;
    }

    public static <T> int am(Object obj, ac<T> acVar, byte[] bArr, int i, int i2, int i3, a aVar) throws IOException {
        z zVar = (z) acVar;
        int i4 = aVar.e + 1;
        aVar.e = i4;
        if (i4 < 100) {
            int af = zVar.af(obj, bArr, i, i2, i3, aVar);
            aVar.e--;
            aVar.c = obj;
            return af;
        }
        throw new q("Protocol message had too many levels of nesting.  May be malicious.  Use setRecursionLimit() to increase the recursion depth limit.");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v2, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v0, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v5, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v6, resolved type: byte} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static <T> int an(java.lang.Object r6, com.google.protobuf.ac<T> r7, byte[] r8, int r9, int r10, com.google.protobuf.d.a r11) throws java.io.IOException {
        /*
            int r0 = r9 + 1
            byte r9 = r8[r9]
            if (r9 >= 0) goto L_0x000c
            int r0 = ah(r9, r8, r0, r11)
            int r9 = r11.a
        L_0x000c:
            r3 = r0
            if (r9 < 0) goto L_0x0036
            int r10 = r10 - r3
            if (r9 > r10) goto L_0x0036
            int r10 = r11.e
            int r10 = r10 + 1
            r11.e = r10
            r0 = 100
            if (r10 >= r0) goto L_0x002e
            int r9 = r9 + r3
            r0 = r7
            r1 = r6
            r2 = r8
            r4 = r9
            r5 = r11
            r0.d(r1, r2, r3, r4, r5)
            int r7 = r11.e
            int r7 = r7 + -1
            r11.e = r7
            r11.c = r6
            return r9
        L_0x002e:
            com.google.protobuf.q r6 = new com.google.protobuf.q
            java.lang.String r7 = "Protocol message had too many levels of nesting.  May be malicious.  Use setRecursionLimit() to increase the recursion depth limit."
            r6.<init>((java.lang.String) r7)
            throw r6
        L_0x0036:
            com.google.protobuf.q r6 = com.google.protobuf.q.h()
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.d.an(java.lang.Object, com.google.protobuf.ac, byte[], int, int, com.google.protobuf.d$a):int");
    }

    public static int ao(int i, byte[] bArr, int i2, int i3, a aVar) throws q {
        if ((i >>> 3) != 0) {
            int i4 = i & 7;
            if (i4 == 0) {
                return ak(bArr, i2, aVar);
            }
            if (i4 == 1) {
                return i2 + 8;
            }
            if (i4 == 2) {
                return ai(bArr, i2, aVar) + aVar.a;
            }
            if (i4 == 3) {
                int i5 = (i & -8) | 4;
                int i6 = 0;
                while (i2 < i3) {
                    i2 = ai(bArr, i2, aVar);
                    i6 = aVar.a;
                    if (i6 == i5) {
                        break;
                    }
                    i2 = ao(i6, bArr, i2, i3, aVar);
                }
                if (i2 <= i3 && i6 == i5) {
                    return i2;
                }
                throw q.g();
            } else if (i4 == 5) {
                return i2 + 4;
            } else {
                throw q.b();
            }
        } else {
            throw q.b();
        }
    }

    public static int b(byte[] bArr, int i, a aVar) throws q {
        int ai = ai(bArr, i, aVar);
        int i2 = aVar.a;
        if (i2 < 0) {
            throw q.f();
        } else if (i2 > bArr.length - ai) {
            throw q.h();
        } else if (i2 == 0) {
            aVar.c = cp.f;
            return ai;
        } else {
            aVar.c = cp.f(bArr, ai, i2);
            return ai + i2;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x004d A[EDGE_INSN: B:30:0x004d->B:22:0x004d ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x001e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int c(int r2, byte[] r3, int r4, int r5, com.google.protobuf.p.i<?> r6, com.google.protobuf.d.a r7) throws com.google.protobuf.q {
        /*
            int r4 = ai(r3, r4, r7)
            int r0 = r7.a
            if (r0 < 0) goto L_0x0053
            int r1 = r3.length
            int r1 = r1 - r4
            if (r0 > r1) goto L_0x004e
            if (r0 != 0) goto L_0x0014
            cp$h r0 = defpackage.cp.f
            r6.add(r0)
            goto L_0x001c
        L_0x0014:
            cp$h r1 = defpackage.cp.f(r3, r4, r0)
            r6.add(r1)
        L_0x001b:
            int r4 = r4 + r0
        L_0x001c:
            if (r4 >= r5) goto L_0x004d
            int r0 = ai(r3, r4, r7)
            int r1 = r7.a
            if (r2 == r1) goto L_0x0027
            goto L_0x004d
        L_0x0027:
            int r4 = ai(r3, r0, r7)
            int r0 = r7.a
            if (r0 < 0) goto L_0x0048
            int r1 = r3.length
            int r1 = r1 - r4
            if (r0 > r1) goto L_0x0043
            if (r0 != 0) goto L_0x003b
            cp$h r0 = defpackage.cp.f
            r6.add(r0)
            goto L_0x001c
        L_0x003b:
            cp$h r1 = defpackage.cp.f(r3, r4, r0)
            r6.add(r1)
            goto L_0x001b
        L_0x0043:
            com.google.protobuf.q r2 = com.google.protobuf.q.h()
            throw r2
        L_0x0048:
            com.google.protobuf.q r2 = com.google.protobuf.q.f()
            throw r2
        L_0x004d:
            return r4
        L_0x004e:
            com.google.protobuf.q r2 = com.google.protobuf.q.h()
            throw r2
        L_0x0053:
            com.google.protobuf.q r2 = com.google.protobuf.q.f()
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.d.c(int, byte[], int, int, com.google.protobuf.p$i, com.google.protobuf.d$a):int");
    }

    public static double d(byte[] bArr, int i) {
        return Double.longBitsToDouble(j(bArr, i));
    }

    public static int e(int i, byte[] bArr, int i2, int i3, p.i<?> iVar, a aVar) {
        h hVar = (h) iVar;
        hVar.b(Double.longBitsToDouble(j(bArr, i2)));
        int i4 = i2 + 8;
        while (i4 < i3) {
            int ai = ai(bArr, i4, aVar);
            if (i != aVar.a) {
                break;
            }
            hVar.b(Double.longBitsToDouble(j(bArr, ai)));
            i4 = ai + 8;
        }
        return i4;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:53:0x01b7, code lost:
        r3 = r8;
        r10 = r10 + 4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x01c7, code lost:
        r3 = r8;
        r10 = r10 + 8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x01cc, code lost:
        if (r0.g == false) goto L_0x01d2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x01ce, code lost:
        r12.a(r0, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x01d2, code lost:
        r12.n(r0, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:?, code lost:
        return r10;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int f(int r8, byte[] r9, int r10, int r11, com.google.protobuf.n.c r12, com.google.protobuf.n.e r13, com.google.protobuf.d.a r14) throws java.io.IOException {
        /*
            com.google.protobuf.l<com.google.protobuf.n$d> r12 = r12.c
            int r8 = r8 >>> 3
            com.google.protobuf.n$d r0 = r13.d
            boolean r1 = r0.g
            hf r2 = r0.f
            if (r1 == 0) goto L_0x00c0
            boolean r1 = r0.h
            if (r1 == 0) goto L_0x00c0
            int r8 = r2.ordinal()
            switch(r8) {
                case 0: goto L_0x00a5;
                case 1: goto L_0x0097;
                case 2: goto L_0x0089;
                case 3: goto L_0x0089;
                case 4: goto L_0x007b;
                case 5: goto L_0x006d;
                case 6: goto L_0x005f;
                case 7: goto L_0x0051;
                case 8: goto L_0x0017;
                case 9: goto L_0x0017;
                case 10: goto L_0x0017;
                case 11: goto L_0x0017;
                case 12: goto L_0x007b;
                case 13: goto L_0x003e;
                case 14: goto L_0x005f;
                case 15: goto L_0x006d;
                case 16: goto L_0x0030;
                case 17: goto L_0x0022;
                default: goto L_0x0017;
            }
        L_0x0017:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            java.lang.String r10 = "Type cannot be packed: "
            r9.<init>(r10)
            goto L_0x00b3
        L_0x0022:
            com.google.protobuf.u r8 = new com.google.protobuf.u
            r8.<init>()
            int r9 = x(r9, r10, r8, r14)
            r12.n(r0, r8)
            goto L_0x01d6
        L_0x0030:
            com.google.protobuf.o r8 = new com.google.protobuf.o
            r8.<init>()
            int r9 = w(r9, r10, r8, r14)
            r12.n(r0, r8)
            goto L_0x01d6
        L_0x003e:
            com.google.protobuf.o r8 = new com.google.protobuf.o
            r8.<init>()
            int r9 = y(r9, r10, r8, r14)
            r0.getClass()
            java.lang.Class<?> r10 = com.google.protobuf.ad.a
            r12.n(r0, r8)
            goto L_0x01d6
        L_0x0051:
            com.google.protobuf.e r8 = new com.google.protobuf.e
            r8.<init>()
            int r9 = r(r9, r10, r8, r14)
            r12.n(r0, r8)
            goto L_0x01d6
        L_0x005f:
            com.google.protobuf.o r8 = new com.google.protobuf.o
            r8.<init>()
            int r9 = t(r9, r10, r8, r14)
            r12.n(r0, r8)
            goto L_0x01d6
        L_0x006d:
            com.google.protobuf.u r8 = new com.google.protobuf.u
            r8.<init>()
            int r9 = u(r9, r10, r8, r14)
            r12.n(r0, r8)
            goto L_0x01d6
        L_0x007b:
            com.google.protobuf.o r8 = new com.google.protobuf.o
            r8.<init>()
            int r9 = y(r9, r10, r8, r14)
            r12.n(r0, r8)
            goto L_0x01d6
        L_0x0089:
            com.google.protobuf.u r8 = new com.google.protobuf.u
            r8.<init>()
            int r9 = z(r9, r10, r8, r14)
            r12.n(r0, r8)
            goto L_0x01d6
        L_0x0097:
            com.google.protobuf.m r8 = new com.google.protobuf.m
            r8.<init>()
            int r9 = v(r9, r10, r8, r14)
            r12.n(r0, r8)
            goto L_0x01d6
        L_0x00a5:
            com.google.protobuf.h r8 = new com.google.protobuf.h
            r8.<init>()
            int r9 = s(r9, r10, r8, r14)
            r12.n(r0, r8)
            goto L_0x01d6
        L_0x00b3:
            hf r10 = r0.f
            r9.append(r10)
            java.lang.String r9 = r9.toString()
            r8.<init>(r9)
            throw r8
        L_0x00c0:
            hf r1 = defpackage.hf.ENUM
            r3 = 0
            if (r2 == r1) goto L_0x01d7
            int r1 = r2.ordinal()
            com.google.protobuf.x r13 = r13.c
            switch(r1) {
                case 0: goto L_0x01bb;
                case 1: goto L_0x01ab;
                case 2: goto L_0x01a0;
                case 3: goto L_0x01a0;
                case 4: goto L_0x0195;
                case 5: goto L_0x018c;
                case 6: goto L_0x0183;
                case 7: goto L_0x016f;
                case 8: goto L_0x0168;
                case 9: goto L_0x012f;
                case 10: goto L_0x0100;
                case 11: goto L_0x00f8;
                case 12: goto L_0x0195;
                case 13: goto L_0x00f0;
                case 14: goto L_0x0183;
                case 15: goto L_0x018c;
                case 16: goto L_0x00e0;
                case 17: goto L_0x00d0;
                default: goto L_0x00ce;
            }
        L_0x00ce:
            goto L_0x01ca
        L_0x00d0:
            int r10 = ak(r9, r10, r14)
            long r8 = r14.b
            long r8 = com.google.protobuf.f.c(r8)
            java.lang.Long r3 = java.lang.Long.valueOf(r8)
            goto L_0x01ca
        L_0x00e0:
            int r10 = ai(r9, r10, r14)
            int r8 = r14.a
            int r8 = com.google.protobuf.f.b(r8)
            java.lang.Integer r3 = java.lang.Integer.valueOf(r8)
            goto L_0x01ca
        L_0x00f0:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "Shouldn't reach here."
            r8.<init>(r9)
            throw r8
        L_0x00f8:
            int r10 = b(r9, r10, r14)
            java.lang.Object r3 = r14.c
            goto L_0x01ca
        L_0x0100:
            f9 r8 = defpackage.f9.c
            java.lang.Class r13 = r13.getClass()
            com.google.protobuf.ac r2 = r8.a(r13)
            boolean r8 = r0.g
            if (r8 == 0) goto L_0x0118
            int r8 = p(r2, r9, r10, r11, r14)
            java.lang.Object r9 = r14.c
            r12.a(r0, r9)
            goto L_0x012e
        L_0x0118:
            java.lang.Object r8 = r12.e(r0)
            if (r8 != 0) goto L_0x0125
            java.lang.Object r8 = r2.i()
            r12.n(r0, r8)
        L_0x0125:
            r1 = r8
            r3 = r9
            r4 = r10
            r5 = r11
            r6 = r14
            int r8 = an(r1, r2, r3, r4, r5, r6)
        L_0x012e:
            return r8
        L_0x012f:
            int r8 = r8 << 3
            r6 = r8 | 4
            f9 r8 = defpackage.f9.c
            java.lang.Class r13 = r13.getClass()
            com.google.protobuf.ac r2 = r8.a(r13)
            boolean r8 = r0.g
            if (r8 == 0) goto L_0x0151
            r1 = r2
            r2 = r9
            r3 = r10
            r4 = r11
            r5 = r6
            r6 = r14
            int r8 = n(r1, r2, r3, r4, r5, r6)
            java.lang.Object r9 = r14.c
            r12.a(r0, r9)
            goto L_0x0167
        L_0x0151:
            java.lang.Object r8 = r12.e(r0)
            if (r8 != 0) goto L_0x015e
            java.lang.Object r8 = r2.i()
            r12.n(r0, r8)
        L_0x015e:
            r1 = r8
            r3 = r9
            r4 = r10
            r5 = r11
            r7 = r14
            int r8 = am(r1, r2, r3, r4, r5, r6, r7)
        L_0x0167:
            return r8
        L_0x0168:
            int r10 = ac(r9, r10, r14)
            java.lang.Object r3 = r14.c
            goto L_0x01ca
        L_0x016f:
            int r10 = ak(r9, r10, r14)
            long r8 = r14.b
            r13 = 0
            int r11 = (r8 > r13 ? 1 : (r8 == r13 ? 0 : -1))
            if (r11 == 0) goto L_0x017d
            r8 = 1
            goto L_0x017e
        L_0x017d:
            r8 = 0
        L_0x017e:
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r8)
            goto L_0x01ca
        L_0x0183:
            int r8 = h(r9, r10)
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)
            goto L_0x01b7
        L_0x018c:
            long r8 = j(r9, r10)
            java.lang.Long r8 = java.lang.Long.valueOf(r8)
            goto L_0x01c7
        L_0x0195:
            int r10 = ai(r9, r10, r14)
            int r8 = r14.a
            java.lang.Integer r3 = java.lang.Integer.valueOf(r8)
            goto L_0x01ca
        L_0x01a0:
            int r10 = ak(r9, r10, r14)
            long r8 = r14.b
            java.lang.Long r3 = java.lang.Long.valueOf(r8)
            goto L_0x01ca
        L_0x01ab:
            int r8 = h(r9, r10)
            float r8 = java.lang.Float.intBitsToFloat(r8)
            java.lang.Float r8 = java.lang.Float.valueOf(r8)
        L_0x01b7:
            r3 = r8
            int r10 = r10 + 4
            goto L_0x01ca
        L_0x01bb:
            long r8 = j(r9, r10)
            double r8 = java.lang.Double.longBitsToDouble(r8)
            java.lang.Double r8 = java.lang.Double.valueOf(r8)
        L_0x01c7:
            r3 = r8
            int r10 = r10 + 8
        L_0x01ca:
            boolean r8 = r0.g
            if (r8 == 0) goto L_0x01d2
            r12.a(r0, r3)
            goto L_0x01d5
        L_0x01d2:
            r12.n(r0, r3)
        L_0x01d5:
            r9 = r10
        L_0x01d6:
            return r9
        L_0x01d7:
            ai(r9, r10, r14)
            r0.getClass()
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.d.f(int, byte[], int, int, com.google.protobuf.n$c, com.google.protobuf.n$e, com.google.protobuf.d$a):int");
    }

    public static int g(int i, byte[] bArr, int i2, int i3, Object obj, x xVar, a aVar) throws IOException {
        i iVar = aVar.d;
        iVar.getClass();
        n.e eVar = iVar.a.get(new i.a(i >>> 3, xVar));
        if (eVar == null) {
            return ag(i, bArr, i2, i3, z.q(obj), aVar);
        }
        n.c cVar = (n.c) obj;
        l<n.d> lVar = cVar.c;
        if (lVar.b) {
            cVar.c = lVar.clone();
        }
        return f(i, bArr, i2, i3, cVar, eVar, aVar);
    }

    public static int h(byte[] bArr, int i) {
        return ((bArr[i + 3] & 255) << 24) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16);
    }

    public static int i(int i, byte[] bArr, int i2, int i3, p.i<?> iVar, a aVar) {
        o oVar = (o) iVar;
        oVar.b(h(bArr, i2));
        int i4 = i2 + 4;
        while (i4 < i3) {
            int ai = ai(bArr, i4, aVar);
            if (i != aVar.a) {
                break;
            }
            oVar.b(h(bArr, ai));
            i4 = ai + 4;
        }
        return i4;
    }

    public static long j(byte[] bArr, int i) {
        return ((((long) bArr[i + 7]) & 255) << 56) | (((long) bArr[i]) & 255) | ((((long) bArr[i + 1]) & 255) << 8) | ((((long) bArr[i + 2]) & 255) << 16) | ((((long) bArr[i + 3]) & 255) << 24) | ((((long) bArr[i + 4]) & 255) << 32) | ((((long) bArr[i + 5]) & 255) << 40) | ((((long) bArr[i + 6]) & 255) << 48);
    }

    public static int k(int i, byte[] bArr, int i2, int i3, p.i<?> iVar, a aVar) {
        u uVar = (u) iVar;
        uVar.b(j(bArr, i2));
        int i4 = i2 + 8;
        while (i4 < i3) {
            int ai = ai(bArr, i4, aVar);
            if (i != aVar.a) {
                break;
            }
            uVar.b(j(bArr, ai));
            i4 = ai + 8;
        }
        return i4;
    }

    public static float l(byte[] bArr, int i) {
        return Float.intBitsToFloat(h(bArr, i));
    }

    public static int m(int i, byte[] bArr, int i2, int i3, p.i<?> iVar, a aVar) {
        m mVar = (m) iVar;
        mVar.b(Float.intBitsToFloat(h(bArr, i2)));
        int i4 = i2 + 4;
        while (i4 < i3) {
            int ai = ai(bArr, i4, aVar);
            if (i != aVar.a) {
                break;
            }
            mVar.b(Float.intBitsToFloat(h(bArr, ai)));
            i4 = ai + 4;
        }
        return i4;
    }

    public static <T> int n(ac<T> acVar, byte[] bArr, int i, int i2, int i3, a aVar) throws IOException {
        T i4 = acVar.i();
        int am = am(i4, acVar, bArr, i, i2, i3, aVar);
        acVar.b(i4);
        aVar.c = i4;
        return am;
    }

    public static int o(ac<?> acVar, int i, byte[] bArr, int i2, int i3, p.i<Object> iVar, a aVar) throws IOException {
        int i4 = (i & -8) | 4;
        int n = n(acVar, bArr, i2, i3, i4, aVar);
        iVar.add(aVar.c);
        while (n < i3) {
            int ai = ai(bArr, n, aVar);
            if (i != aVar.a) {
                break;
            }
            n = n(acVar, bArr, ai, i3, i4, aVar);
            iVar.add(aVar.c);
        }
        return n;
    }

    public static <T> int p(ac<T> acVar, byte[] bArr, int i, int i2, a aVar) throws IOException {
        T i3 = acVar.i();
        int an = an(i3, acVar, bArr, i, i2, aVar);
        acVar.b(i3);
        aVar.c = i3;
        return an;
    }

    public static int q(ac<?> acVar, int i, byte[] bArr, int i2, int i3, p.i<?> iVar, a aVar) throws IOException {
        int p = p(acVar, bArr, i2, i3, aVar);
        iVar.add(aVar.c);
        while (p < i3) {
            int ai = ai(bArr, p, aVar);
            if (i != aVar.a) {
                break;
            }
            p = p(acVar, bArr, ai, i3, aVar);
            iVar.add(aVar.c);
        }
        return p;
    }

    public static int r(byte[] bArr, int i, p.i<?> iVar, a aVar) throws q {
        boolean z;
        e eVar = (e) iVar;
        int ai = ai(bArr, i, aVar);
        int i2 = aVar.a + ai;
        while (ai < i2) {
            ai = ak(bArr, ai, aVar);
            if (aVar.b != 0) {
                z = true;
            } else {
                z = false;
            }
            eVar.b(z);
        }
        if (ai == i2) {
            return ai;
        }
        throw q.h();
    }

    public static int s(byte[] bArr, int i, p.i<?> iVar, a aVar) throws q {
        h hVar = (h) iVar;
        int ai = ai(bArr, i, aVar);
        int i2 = aVar.a;
        int i3 = ai + i2;
        if (i3 <= bArr.length) {
            int i4 = (i2 / 8) + hVar.g;
            double[] dArr = hVar.f;
            if (i4 > dArr.length) {
                if (dArr.length == 0) {
                    hVar.f = new double[Math.max(i4, 10)];
                } else {
                    int length = dArr.length;
                    while (length < i4) {
                        length = y2.b(length, 3, 2, 1, 10);
                    }
                    hVar.f = Arrays.copyOf(hVar.f, length);
                }
            }
            while (ai < i3) {
                hVar.b(Double.longBitsToDouble(j(bArr, ai)));
                ai += 8;
            }
            if (ai == i3) {
                return ai;
            }
            throw q.h();
        }
        throw q.h();
    }

    public static int t(byte[] bArr, int i, p.i<?> iVar, a aVar) throws q {
        o oVar = (o) iVar;
        int ai = ai(bArr, i, aVar);
        int i2 = aVar.a;
        int i3 = ai + i2;
        if (i3 <= bArr.length) {
            int i4 = (i2 / 4) + oVar.g;
            int[] iArr = oVar.f;
            if (i4 > iArr.length) {
                if (iArr.length == 0) {
                    oVar.f = new int[Math.max(i4, 10)];
                } else {
                    int length = iArr.length;
                    while (length < i4) {
                        length = y2.b(length, 3, 2, 1, 10);
                    }
                    oVar.f = Arrays.copyOf(oVar.f, length);
                }
            }
            while (ai < i3) {
                oVar.b(h(bArr, ai));
                ai += 4;
            }
            if (ai == i3) {
                return ai;
            }
            throw q.h();
        }
        throw q.h();
    }

    public static int u(byte[] bArr, int i, p.i<?> iVar, a aVar) throws q {
        u uVar = (u) iVar;
        int ai = ai(bArr, i, aVar);
        int i2 = aVar.a;
        int i3 = ai + i2;
        if (i3 <= bArr.length) {
            int i4 = (i2 / 8) + uVar.g;
            long[] jArr = uVar.f;
            if (i4 > jArr.length) {
                if (jArr.length == 0) {
                    uVar.f = new long[Math.max(i4, 10)];
                } else {
                    int length = jArr.length;
                    while (length < i4) {
                        length = y2.b(length, 3, 2, 1, 10);
                    }
                    uVar.f = Arrays.copyOf(uVar.f, length);
                }
            }
            while (ai < i3) {
                uVar.b(j(bArr, ai));
                ai += 8;
            }
            if (ai == i3) {
                return ai;
            }
            throw q.h();
        }
        throw q.h();
    }

    public static int v(byte[] bArr, int i, p.i<?> iVar, a aVar) throws q {
        m mVar = (m) iVar;
        int ai = ai(bArr, i, aVar);
        int i2 = aVar.a;
        int i3 = ai + i2;
        if (i3 <= bArr.length) {
            int i4 = (i2 / 4) + mVar.g;
            float[] fArr = mVar.f;
            if (i4 > fArr.length) {
                if (fArr.length == 0) {
                    mVar.f = new float[Math.max(i4, 10)];
                } else {
                    int length = fArr.length;
                    while (length < i4) {
                        length = y2.b(length, 3, 2, 1, 10);
                    }
                    mVar.f = Arrays.copyOf(mVar.f, length);
                }
            }
            while (ai < i3) {
                mVar.b(Float.intBitsToFloat(h(bArr, ai)));
                ai += 4;
            }
            if (ai == i3) {
                return ai;
            }
            throw q.h();
        }
        throw q.h();
    }

    public static int w(byte[] bArr, int i, p.i<?> iVar, a aVar) throws q {
        o oVar = (o) iVar;
        int ai = ai(bArr, i, aVar);
        int i2 = aVar.a + ai;
        while (ai < i2) {
            ai = ai(bArr, ai, aVar);
            oVar.b(f.b(aVar.a));
        }
        if (ai == i2) {
            return ai;
        }
        throw q.h();
    }

    public static int x(byte[] bArr, int i, p.i<?> iVar, a aVar) throws q {
        u uVar = (u) iVar;
        int ai = ai(bArr, i, aVar);
        int i2 = aVar.a + ai;
        while (ai < i2) {
            ai = ak(bArr, ai, aVar);
            uVar.b(f.c(aVar.b));
        }
        if (ai == i2) {
            return ai;
        }
        throw q.h();
    }

    public static int y(byte[] bArr, int i, p.i<?> iVar, a aVar) throws IOException {
        o oVar = (o) iVar;
        int ai = ai(bArr, i, aVar);
        int i2 = aVar.a + ai;
        while (ai < i2) {
            ai = ai(bArr, ai, aVar);
            oVar.b(aVar.a);
        }
        if (ai == i2) {
            return ai;
        }
        throw q.h();
    }

    public static int z(byte[] bArr, int i, p.i<?> iVar, a aVar) throws IOException {
        u uVar = (u) iVar;
        int ai = ai(bArr, i, aVar);
        int i2 = aVar.a + ai;
        while (ai < i2) {
            ai = ak(bArr, ai, aVar);
            uVar.b(aVar.b);
        }
        if (ai == i2) {
            return ai;
        }
        throw q.h();
    }
}
