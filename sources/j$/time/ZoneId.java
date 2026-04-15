package j$.time;

import j$.lang.a;
import j$.time.temporal.TemporalAccessor;
import j$.time.temporal.p;
import j$.time.zone.f;
import j$.util.Objects;
import java.io.DataOutput;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import org.eclipse.paho.client.mqttv3.MqttTopic;

public abstract class ZoneId implements Serializable {
    public static final Map a;
    private static final long serialVersionUID = 8352817235686L;

    static {
        int i = 0;
        Map.Entry[] entryArr = {a.j("ACT", "Australia/Darwin"), a.j("AET", "Australia/Sydney"), a.j("AGT", "America/Argentina/Buenos_Aires"), a.j("ART", "Africa/Cairo"), a.j("AST", "America/Anchorage"), a.j("BET", "America/Sao_Paulo"), a.j("BST", "Asia/Dhaka"), a.j("CAT", "Africa/Harare"), a.j("CNT", "America/St_Johns"), a.j("CST", "America/Chicago"), a.j("CTT", "Asia/Shanghai"), a.j("EAT", "Africa/Addis_Ababa"), a.j("ECT", "Europe/Paris"), a.j("IET", "America/Indiana/Indianapolis"), a.j("IST", "Asia/Kolkata"), a.j("JST", "Asia/Tokyo"), a.j("MIT", "Pacific/Apia"), a.j("NET", "Asia/Yerevan"), a.j("NST", "Pacific/Auckland"), a.j("PLT", "Asia/Karachi"), a.j("PNT", "America/Phoenix"), a.j("PRT", "America/Puerto_Rico"), a.j("PST", "America/Los_Angeles"), a.j("SST", "Pacific/Guadalcanal"), a.j("VST", "Asia/Ho_Chi_Minh"), a.j("EST", "-05:00"), a.j("MST", "-07:00"), a.j("HST", "-10:00")};
        HashMap hashMap = new HashMap(28);
        while (i < 28) {
            Map.Entry entry = entryArr[i];
            Object requireNonNull = Objects.requireNonNull(entry.getKey());
            if (hashMap.put(requireNonNull, Objects.requireNonNull(entry.getValue())) == null) {
                i++;
            } else {
                throw new IllegalArgumentException("duplicate key: " + requireNonNull);
            }
        }
        a = Collections.unmodifiableMap(hashMap);
    }

    ZoneId() {
        if (getClass() != ZoneOffset.class && getClass() != w.class) {
            throw new AssertionError("Invalid subclass");
        }
    }

    public static ZoneId Q(TemporalAccessor temporalAccessor) {
        ZoneId zoneId = (ZoneId) temporalAccessor.H(p.k());
        if (zoneId != null) {
            return zoneId;
        }
        String name = temporalAccessor.getClass().getName();
        throw new DateTimeException("Unable to obtain ZoneId from TemporalAccessor: " + temporalAccessor + " of type " + name);
    }

    public static ZoneId S(String str) {
        return U(str, true);
    }

    public static ZoneId T(String str, Map map) {
        Objects.requireNonNull(str, "zoneId");
        Objects.requireNonNull(map, "aliasMap");
        return U((String) Objects.requireNonNullElse((String) map.get(str), str), true);
    }

    static ZoneId U(String str, boolean z) {
        int i;
        Objects.requireNonNull(str, "zoneId");
        if (str.length() <= 1 || str.startsWith(MqttTopic.SINGLE_LEVEL_WILDCARD) || str.startsWith("-")) {
            return ZoneOffset.b0(str);
        }
        if (str.startsWith("UTC") || str.startsWith("GMT")) {
            i = 3;
        } else if (!str.startsWith("UT")) {
            return w.Y(str, z);
        } else {
            i = 2;
        }
        return W(str, i, z);
    }

    public static ZoneId V(String str, ZoneOffset zoneOffset) {
        Objects.requireNonNull(str, "prefix");
        Objects.requireNonNull(zoneOffset, "offset");
        if (str.isEmpty()) {
            return zoneOffset;
        }
        if (str.equals("GMT") || str.equals("UTC") || str.equals("UT")) {
            if (zoneOffset.a0() != 0) {
                str = str.concat(zoneOffset.j());
            }
            return new w(str, f.j(zoneOffset));
        }
        throw new IllegalArgumentException("prefix should be GMT, UTC or UT, is: ".concat(str));
    }

    private static ZoneId W(String str, int i, boolean z) {
        String substring = str.substring(0, i);
        if (str.length() == i) {
            return V(substring, ZoneOffset.UTC);
        }
        if (str.charAt(i) != '+' && str.charAt(i) != '-') {
            return w.Y(str, z);
        }
        try {
            ZoneOffset b0 = ZoneOffset.b0(str.substring(i));
            return b0 == ZoneOffset.UTC ? V(substring, b0) : V(substring, b0);
        } catch (DateTimeException e) {
            throw new DateTimeException("Invalid ID for offset-based ZoneId: ".concat(str), e);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    public static ZoneId systemDefault() {
        return T(TimeZone.getDefault().getID(), a);
    }

    private Object writeReplace() {
        return new r((byte) 7, this);
    }

    public abstract f R();

    /* access modifiers changed from: package-private */
    public abstract void X(DataOutput dataOutput);

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ZoneId) {
            return j().equals(((ZoneId) obj).j());
        }
        return false;
    }

    public int hashCode() {
        return j().hashCode();
    }

    public abstract String j();

    public String toString() {
        return j();
    }
}
