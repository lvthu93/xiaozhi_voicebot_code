package j$.time.zone;

import j$.lang.a;
import j$.time.Instant;
import j$.time.LocalDate;
import j$.time.LocalDateTime;
import j$.time.ZoneOffset;
import j$.time.b;
import j$.time.chrono.C0038b;
import j$.util.Objects;
import j$.util.concurrent.ConcurrentHashMap;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

public final class f implements Serializable {
    private static final long[] i = new long[0];
    private static final e[] j = new e[0];
    private static final LocalDateTime[] k = new LocalDateTime[0];
    private static final b[] l = new b[0];
    private static final long serialVersionUID = 3044319355680032515L;
    private final long[] a;
    private final ZoneOffset[] b;
    private final long[] c;
    private final LocalDateTime[] d;
    private final ZoneOffset[] e;
    private final e[] f;
    private final TimeZone g;
    private final transient ConcurrentHashMap h = new ConcurrentHashMap();

    private f(ZoneOffset zoneOffset) {
        ZoneOffset[] zoneOffsetArr = new ZoneOffset[1];
        this.b = zoneOffsetArr;
        zoneOffsetArr[0] = zoneOffset;
        long[] jArr = i;
        this.a = jArr;
        this.c = jArr;
        this.d = k;
        this.e = zoneOffsetArr;
        this.f = j;
        this.g = null;
    }

    f(TimeZone timeZone) {
        ZoneOffset[] zoneOffsetArr = new ZoneOffset[1];
        this.b = zoneOffsetArr;
        zoneOffsetArr[0] = k(timeZone.getRawOffset());
        long[] jArr = i;
        this.a = jArr;
        this.c = jArr;
        this.d = k;
        this.e = zoneOffsetArr;
        this.f = j;
        this.g = timeZone;
    }

    private f(long[] jArr, ZoneOffset[] zoneOffsetArr, long[] jArr2, ZoneOffset[] zoneOffsetArr2, e[] eVarArr) {
        LocalDateTime localDateTime;
        this.a = jArr;
        this.b = zoneOffsetArr;
        this.c = jArr2;
        this.e = zoneOffsetArr2;
        this.f = eVarArr;
        if (jArr2.length == 0) {
            this.d = k;
        } else {
            ArrayList arrayList = new ArrayList();
            int i2 = 0;
            while (i2 < jArr2.length) {
                int i3 = i2 + 1;
                b bVar = new b(jArr2[i2], zoneOffsetArr2[i2], zoneOffsetArr2[i3]);
                if (bVar.Q()) {
                    arrayList.add(bVar.l());
                    localDateTime = bVar.k();
                } else {
                    arrayList.add(bVar.k());
                    localDateTime = bVar.l();
                }
                arrayList.add(localDateTime);
                i2 = i3;
            }
            this.d = (LocalDateTime[]) arrayList.toArray(new LocalDateTime[arrayList.size()]);
        }
        this.g = null;
    }

    private static Object a(LocalDateTime localDateTime, b bVar) {
        LocalDateTime l2 = bVar.l();
        boolean Q = bVar.Q();
        boolean W = localDateTime.W(l2);
        return Q ? W ? bVar.E() : localDateTime.W(bVar.k()) ? bVar : bVar.p() : !W ? bVar.p() : localDateTime.W(bVar.k()) ? bVar.E() : bVar;
    }

    private b[] b(int i2) {
        long j2;
        int i3 = i2;
        Integer valueOf = Integer.valueOf(i2);
        ConcurrentHashMap concurrentHashMap = this.h;
        b[] bVarArr = (b[]) concurrentHashMap.get(valueOf);
        if (bVarArr != null) {
            return bVarArr;
        }
        TimeZone timeZone = this.g;
        if (timeZone != null) {
            b[] bVarArr2 = l;
            if (i3 < 1800) {
                return bVarArr2;
            }
            long p = C0038b.p(LocalDateTime.X(i3 - 1), this.b[0]);
            int offset = timeZone.getOffset(p * 1000);
            long j3 = 31968000 + p;
            while (p < j3) {
                long j4 = 7776000 + p;
                long j5 = p;
                if (offset != timeZone.getOffset(j4 * 1000)) {
                    p = j5;
                    while (j4 - p > 1) {
                        long j6 = j3;
                        long i4 = a.i(j4 + p, 2);
                        int i5 = offset;
                        if (timeZone.getOffset(i4 * 1000) == i5) {
                            p = i4;
                        } else {
                            j4 = i4;
                        }
                        offset = i5;
                        j3 = j6;
                    }
                    j2 = j3;
                    int i6 = offset;
                    if (timeZone.getOffset(p * 1000) == i6) {
                        p = j4;
                    }
                    ZoneOffset k2 = k(i6);
                    offset = timeZone.getOffset(p * 1000);
                    ZoneOffset k3 = k(offset);
                    if (c(p, k3) == i3) {
                        bVarArr2 = (b[]) Arrays.copyOf(bVarArr2, bVarArr2.length + 1);
                        bVarArr2[bVarArr2.length - 1] = new b(p, k2, k3);
                    }
                } else {
                    j2 = j3;
                    int i7 = offset;
                    p = j4;
                }
                j3 = j2;
            }
            if (1916 <= i3 && i3 < 2100) {
                concurrentHashMap.putIfAbsent(valueOf, bVarArr2);
            }
            return bVarArr2;
        }
        e[] eVarArr = this.f;
        b[] bVarArr3 = new b[eVarArr.length];
        for (int i8 = 0; i8 < eVarArr.length; i8++) {
            bVarArr3[i8] = eVarArr[i8].a(i3);
        }
        if (i3 < 2100) {
            concurrentHashMap.putIfAbsent(valueOf, bVarArr3);
        }
        return bVarArr3;
    }

    private static int c(long j2, ZoneOffset zoneOffset) {
        return LocalDate.d0(a.i(j2 + ((long) zoneOffset.a0()), (long) 86400)).X();
    }

    private Object e(LocalDateTime localDateTime) {
        Object obj = null;
        ZoneOffset[] zoneOffsetArr = this.b;
        int i2 = 0;
        TimeZone timeZone = this.g;
        if (timeZone != null) {
            b[] b2 = b(localDateTime.U());
            if (b2.length == 0) {
                return k(timeZone.getOffset(C0038b.p(localDateTime, zoneOffsetArr[0]) * 1000));
            }
            int length = b2.length;
            while (i2 < length) {
                b bVar = b2[i2];
                Object a2 = a(localDateTime, bVar);
                if ((a2 instanceof b) || a2.equals(bVar.E())) {
                    return a2;
                }
                i2++;
                obj = a2;
            }
            return obj;
        } else if (this.c.length == 0) {
            return zoneOffsetArr[0];
        } else {
            int length2 = this.f.length;
            LocalDateTime[] localDateTimeArr = this.d;
            if (length2 <= 0 || !localDateTime.V(localDateTimeArr[localDateTimeArr.length - 1])) {
                int binarySearch = Arrays.binarySearch(localDateTimeArr, localDateTime);
                ZoneOffset[] zoneOffsetArr2 = this.e;
                if (binarySearch == -1) {
                    return zoneOffsetArr2[0];
                }
                if (binarySearch < 0) {
                    binarySearch = (-binarySearch) - 2;
                } else if (binarySearch < localDateTimeArr.length - 1) {
                    int i3 = binarySearch + 1;
                    if (localDateTimeArr[binarySearch].equals(localDateTimeArr[i3])) {
                        binarySearch = i3;
                    }
                }
                if ((binarySearch & 1) != 0) {
                    return zoneOffsetArr2[(binarySearch / 2) + 1];
                }
                LocalDateTime localDateTime2 = localDateTimeArr[binarySearch];
                LocalDateTime localDateTime3 = localDateTimeArr[binarySearch + 1];
                int i4 = binarySearch / 2;
                ZoneOffset zoneOffset = zoneOffsetArr2[i4];
                ZoneOffset zoneOffset2 = zoneOffsetArr2[i4 + 1];
                return zoneOffset2.a0() > zoneOffset.a0() ? new b(localDateTime2, zoneOffset, zoneOffset2) : new b(localDateTime3, zoneOffset, zoneOffset2);
            }
            b[] b3 = b(localDateTime.U());
            int length3 = b3.length;
            while (i2 < length3) {
                b bVar2 = b3[i2];
                Object a3 = a(localDateTime, bVar2);
                if ((a3 instanceof b) || a3.equals(bVar2.E())) {
                    return a3;
                }
                i2++;
                obj = a3;
            }
            return obj;
        }
    }

    public static f j(ZoneOffset zoneOffset) {
        Objects.requireNonNull(zoneOffset, "offset");
        return new f(zoneOffset);
    }

    private static ZoneOffset k(int i2) {
        return ZoneOffset.d0(i2 / 1000);
    }

    static f l(DataInput dataInput) {
        int readInt = dataInput.readInt();
        long[] jArr = i;
        long[] jArr2 = readInt == 0 ? jArr : new long[readInt];
        for (int i2 = 0; i2 < readInt; i2++) {
            jArr2[i2] = a.a(dataInput);
        }
        int i3 = readInt + 1;
        ZoneOffset[] zoneOffsetArr = new ZoneOffset[i3];
        for (int i4 = 0; i4 < i3; i4++) {
            zoneOffsetArr[i4] = a.b(dataInput);
        }
        int readInt2 = dataInput.readInt();
        if (readInt2 != 0) {
            jArr = new long[readInt2];
        }
        long[] jArr3 = jArr;
        for (int i5 = 0; i5 < readInt2; i5++) {
            jArr3[i5] = a.a(dataInput);
        }
        int i6 = readInt2 + 1;
        ZoneOffset[] zoneOffsetArr2 = new ZoneOffset[i6];
        for (int i7 = 0; i7 < i6; i7++) {
            zoneOffsetArr2[i7] = a.b(dataInput);
        }
        int readByte = dataInput.readByte();
        e[] eVarArr = readByte == 0 ? j : new e[readByte];
        for (int i8 = 0; i8 < readByte; i8++) {
            eVarArr[i8] = e.b(dataInput);
        }
        return new f(jArr2, zoneOffsetArr, jArr3, zoneOffsetArr2, eVarArr);
    }

    private void readObject(ObjectInputStream objectInputStream) {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new a(this.g != null ? (byte) 100 : 1, this);
    }

    public final ZoneOffset d(Instant instant) {
        TimeZone timeZone = this.g;
        if (timeZone != null) {
            return k(timeZone.getOffset(instant.Z()));
        }
        long[] jArr = this.c;
        if (jArr.length == 0) {
            return this.b[0];
        }
        long S = instant.S();
        int length = this.f.length;
        ZoneOffset[] zoneOffsetArr = this.e;
        if (length <= 0 || S <= jArr[jArr.length - 1]) {
            int binarySearch = Arrays.binarySearch(jArr, S);
            if (binarySearch < 0) {
                binarySearch = (-binarySearch) - 2;
            }
            return zoneOffsetArr[binarySearch + 1];
        }
        b[] b2 = b(c(S, zoneOffsetArr[zoneOffsetArr.length - 1]));
        b bVar = null;
        for (int i2 = 0; i2 < b2.length; i2++) {
            bVar = b2[i2];
            if (S < bVar.P()) {
                return bVar.E();
            }
        }
        return bVar.p();
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof f)) {
            return false;
        }
        f fVar = (f) obj;
        return Objects.equals(this.g, fVar.g) && Arrays.equals(this.a, fVar.a) && Arrays.equals(this.b, fVar.b) && Arrays.equals(this.c, fVar.c) && Arrays.equals(this.e, fVar.e) && Arrays.equals(this.f, fVar.f);
    }

    public final b f(LocalDateTime localDateTime) {
        Object e2 = e(localDateTime);
        if (e2 instanceof b) {
            return (b) e2;
        }
        return null;
    }

    public final List g(LocalDateTime localDateTime) {
        Object e2 = e(localDateTime);
        return e2 instanceof b ? ((b) e2).H() : Collections.singletonList((ZoneOffset) e2);
    }

    public final boolean h(Instant instant) {
        ZoneOffset zoneOffset;
        TimeZone timeZone = this.g;
        if (timeZone != null) {
            zoneOffset = k(timeZone.getRawOffset());
        } else {
            int length = this.c.length;
            ZoneOffset[] zoneOffsetArr = this.b;
            if (length == 0) {
                zoneOffset = zoneOffsetArr[0];
            } else {
                int binarySearch = Arrays.binarySearch(this.a, instant.S());
                if (binarySearch < 0) {
                    binarySearch = (-binarySearch) - 2;
                }
                zoneOffset = zoneOffsetArr[binarySearch + 1];
            }
        }
        return !zoneOffset.equals(d(instant));
    }

    public final int hashCode() {
        return ((((Objects.hashCode(this.g) ^ Arrays.hashCode(this.a)) ^ Arrays.hashCode(this.b)) ^ Arrays.hashCode(this.c)) ^ Arrays.hashCode(this.e)) ^ Arrays.hashCode(this.f);
    }

    public final boolean i() {
        b bVar;
        TimeZone timeZone = this.g;
        if (timeZone == null) {
            return this.c.length == 0;
        }
        if (timeZone.useDaylightTime() || timeZone.getDSTSavings() != 0) {
            return false;
        }
        Instant U = Instant.U();
        long S = U.S();
        if (U.T() > 0 && S < Long.MAX_VALUE) {
            S++;
        }
        int c2 = c(S, d(U));
        b[] b2 = b(c2);
        int length = b2.length - 1;
        while (true) {
            if (length < 0) {
                if (c2 > 1800) {
                    b[] b3 = b(c2 - 1);
                    int length2 = b3.length - 1;
                    while (true) {
                        if (length2 < 0) {
                            long min = Math.min(S - 31104000, (b.d().b() / 1000) + 31968000);
                            int offset = timeZone.getOffset((S - 1) * 1000);
                            long F = LocalDate.of(1800, 1, 1).F() * 86400;
                            while (true) {
                                if (F > min) {
                                    break;
                                }
                                int offset2 = timeZone.getOffset(min * 1000);
                                if (offset != offset2) {
                                    int c3 = c(min, k(offset2));
                                    b[] b4 = b(c3 + 1);
                                    int length3 = b4.length - 1;
                                    while (true) {
                                        if (length3 < 0) {
                                            b[] b5 = b(c3);
                                            bVar = b5[b5.length - 1];
                                            break;
                                        } else if (S > b4[length3].P()) {
                                            bVar = b4[length3];
                                            break;
                                        } else {
                                            length3--;
                                        }
                                    }
                                } else {
                                    min -= 7776000;
                                }
                            }
                        } else if (S > b3[length2].P()) {
                            bVar = b3[length2];
                            break;
                        } else {
                            length2--;
                        }
                    }
                }
                bVar = null;
            } else if (S > b2[length].P()) {
                bVar = b2[length];
                break;
            } else {
                length--;
            }
        }
        return bVar == null;
    }

    /* access modifiers changed from: package-private */
    public final void m(DataOutput dataOutput) {
        long[] jArr = this.a;
        dataOutput.writeInt(jArr.length);
        for (long c2 : jArr) {
            a.c(c2, dataOutput);
        }
        for (ZoneOffset d2 : this.b) {
            a.d(d2, dataOutput);
        }
        long[] jArr2 = this.c;
        dataOutput.writeInt(jArr2.length);
        for (long c3 : jArr2) {
            a.c(c3, dataOutput);
        }
        for (ZoneOffset d3 : this.e) {
            a.d(d3, dataOutput);
        }
        e[] eVarArr = this.f;
        dataOutput.writeByte(eVarArr.length);
        for (e c4 : eVarArr) {
            c4.c(dataOutput);
        }
    }

    /* access modifiers changed from: package-private */
    public final void n(DataOutput dataOutput) {
        dataOutput.writeUTF(this.g.getID());
    }

    public final String toString() {
        StringBuilder sb;
        TimeZone timeZone = this.g;
        if (timeZone != null) {
            String id = timeZone.getID();
            sb = new StringBuilder("ZoneRules[timeZone=");
            sb.append(id);
        } else {
            ZoneOffset[] zoneOffsetArr = this.b;
            ZoneOffset zoneOffset = zoneOffsetArr[zoneOffsetArr.length - 1];
            sb = new StringBuilder("ZoneRules[currentStandardOffset=");
            sb.append(zoneOffset);
        }
        sb.append("]");
        return sb.toString();
    }
}
