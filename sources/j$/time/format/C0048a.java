package j$.time.format;

import j$.time.ZoneId;
import j$.time.ZoneOffset;
import j$.time.temporal.TemporalAccessor;
import j$.time.temporal.p;
import j$.time.temporal.s;

/* renamed from: j$.time.format.a  reason: case insensitive filesystem */
public final /* synthetic */ class C0048a implements s {
    public final Object a(TemporalAccessor temporalAccessor) {
        int i = w.j;
        ZoneId zoneId = (ZoneId) temporalAccessor.H(p.l());
        if (zoneId == null || (zoneId instanceof ZoneOffset)) {
            return null;
        }
        return zoneId;
    }
}
