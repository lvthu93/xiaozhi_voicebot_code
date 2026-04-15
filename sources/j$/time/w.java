package j$.time;

import j$.time.zone.f;
import j$.time.zone.g;
import j$.time.zone.j;
import j$.util.Objects;
import java.io.DataOutput;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;

final class w extends ZoneId {
    public static final /* synthetic */ int d = 0;
    private static final long serialVersionUID = 8386373296231747096L;
    private final String b;
    private final transient f c;

    w(String str, f fVar) {
        this.b = str;
        this.c = fVar;
    }

    static w Y(String str, boolean z) {
        f fVar;
        Objects.requireNonNull(str, "zoneId");
        int length = str.length();
        if (length >= 2) {
            for (int i = 0; i < length; i++) {
                char charAt = str.charAt(i);
                if ((charAt < 'a' || charAt > 'z') && ((charAt < 'A' || charAt > 'Z') && ((charAt != '/' || i == 0) && ((charAt < '0' || charAt > '9' || i == 0) && ((charAt != '~' || i == 0) && ((charAt != '.' || i == 0) && ((charAt != '_' || i == 0) && ((charAt != '+' || i == 0) && (charAt != '-' || i == 0))))))))) {
                    throw new DateTimeException("Invalid ID for region-based ZoneId, invalid format: ".concat(str));
                }
            }
            try {
                fVar = j.b(str, true);
            } catch (g e) {
                if (!z) {
                    fVar = null;
                } else {
                    throw e;
                }
            }
            return new w(str, fVar);
        }
        throw new DateTimeException("Invalid ID for region-based ZoneId, invalid format: ".concat(str));
    }

    private void readObject(ObjectInputStream objectInputStream) {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new r((byte) 7, this);
    }

    public final f R() {
        f fVar = this.c;
        return fVar != null ? fVar : j.b(this.b, false);
    }

    /* access modifiers changed from: package-private */
    public final void X(DataOutput dataOutput) {
        dataOutput.writeByte(7);
        dataOutput.writeUTF(this.b);
    }

    /* access modifiers changed from: package-private */
    public final void Z(DataOutput dataOutput) {
        dataOutput.writeUTF(this.b);
    }

    public final String j() {
        return this.b;
    }
}
