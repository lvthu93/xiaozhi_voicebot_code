package j$.time.temporal;

import j$.time.LocalDate;
import j$.time.ZoneId;
import j$.time.ZoneOffset;
import j$.time.chrono.m;
import j$.time.j;

final class r implements s {
    public final /* synthetic */ int a;

    public /* synthetic */ r(int i) {
        this.a = i;
    }

    public final Object a(TemporalAccessor temporalAccessor) {
        switch (this.a) {
            case 0:
                return b(temporalAccessor);
            case 1:
                return (m) temporalAccessor.H(p.b);
            case 2:
                return (TemporalUnit) temporalAccessor.H(p.c);
            case 3:
                a aVar = a.OFFSET_SECONDS;
                if (temporalAccessor.e(aVar)) {
                    return ZoneOffset.d0(temporalAccessor.k(aVar));
                }
                return null;
            case 4:
                return b(temporalAccessor);
            case 5:
                a aVar2 = a.EPOCH_DAY;
                if (temporalAccessor.e(aVar2)) {
                    return LocalDate.d0(temporalAccessor.E(aVar2));
                }
                return null;
            default:
                a aVar3 = a.NANO_OF_DAY;
                if (temporalAccessor.e(aVar3)) {
                    return j.Z(temporalAccessor.E(aVar3));
                }
                return null;
        }
    }

    public final ZoneId b(TemporalAccessor temporalAccessor) {
        s sVar = p.a;
        switch (this.a) {
            case 0:
                return (ZoneId) temporalAccessor.H(sVar);
            default:
                ZoneId zoneId = (ZoneId) temporalAccessor.H(sVar);
                return zoneId != null ? zoneId : (ZoneId) temporalAccessor.H(p.d);
        }
    }

    public final String toString() {
        switch (this.a) {
            case 0:
                return "ZoneId";
            case 1:
                return "Chronology";
            case 2:
                return "Precision";
            case 3:
                return "ZoneOffset";
            case 4:
                return "Zone";
            case 5:
                return "LocalDate";
            default:
                return "LocalTime";
        }
    }
}
