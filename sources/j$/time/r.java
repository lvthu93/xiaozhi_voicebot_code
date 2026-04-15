package j$.time;

import java.io.Externalizable;
import java.io.InvalidClassException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.io.StreamCorruptedException;

final class r implements Externalizable {
    private static final long serialVersionUID = -7683839454370182990L;
    private byte a;
    private Object b;

    public r() {
    }

    r(byte b2, Object obj) {
        this.a = b2;
        this.b = obj;
    }

    static Serializable a(ObjectInput objectInput) {
        return b(objectInput.readByte(), objectInput);
    }

    private static Serializable b(byte b2, ObjectInput objectInput) {
        switch (b2) {
            case 1:
                Duration duration = Duration.c;
                return Duration.E(objectInput.readLong(), (long) objectInput.readInt());
            case 2:
                Instant instant = Instant.c;
                return Instant.W(objectInput.readLong(), (long) objectInput.readInt());
            case 3:
                LocalDate localDate = LocalDate.d;
                return LocalDate.of(objectInput.readInt(), objectInput.readByte(), objectInput.readByte());
            case 4:
                return j.g0(objectInput);
            case 5:
                LocalDateTime localDateTime = LocalDateTime.c;
                LocalDate localDate2 = LocalDate.d;
                return LocalDateTime.Z(LocalDate.of(objectInput.readInt(), objectInput.readByte(), objectInput.readByte()), j.g0(objectInput));
            case 6:
                return ZonedDateTime.V(objectInput);
            case 7:
                int i = w.d;
                return ZoneId.U(objectInput.readUTF(), false);
            case 8:
                return ZoneOffset.f0(objectInput);
            case 9:
                return p.S(objectInput);
            case 10:
                return OffsetDateTime.U(objectInput);
            case 11:
                int i2 = t.b;
                return t.Q(objectInput.readInt());
            case 12:
                return v.T(objectInput);
            case 13:
                return n.Q(objectInput);
            case 14:
                return q.d(objectInput);
            default:
                throw new StreamCorruptedException("Unknown serialized type");
        }
    }

    private Object readResolve() {
        return this.b;
    }

    public final void readExternal(ObjectInput objectInput) {
        byte readByte = objectInput.readByte();
        this.a = readByte;
        this.b = b(readByte, objectInput);
    }

    public final void writeExternal(ObjectOutput objectOutput) {
        byte b2 = this.a;
        Object obj = this.b;
        objectOutput.writeByte(b2);
        switch (b2) {
            case 1:
                ((Duration) obj).Q(objectOutput);
                return;
            case 2:
                ((Instant) obj).a0(objectOutput);
                return;
            case 3:
                ((LocalDate) obj).p0(objectOutput);
                return;
            case 4:
                ((j) obj).l0(objectOutput);
                return;
            case 5:
                ((LocalDateTime) obj).k0(objectOutput);
                return;
            case 6:
                ((ZonedDateTime) obj).Z(objectOutput);
                return;
            case 7:
                ((w) obj).Z(objectOutput);
                return;
            case 8:
                ((ZoneOffset) obj).g0(objectOutput);
                return;
            case 9:
                ((p) obj).writeExternal(objectOutput);
                return;
            case 10:
                ((OffsetDateTime) obj).writeExternal(objectOutput);
                return;
            case 11:
                ((t) obj).U(objectOutput);
                return;
            case 12:
                ((v) obj).W(objectOutput);
                return;
            case 13:
                ((n) obj).R(objectOutput);
                return;
            case 14:
                ((q) obj).f(objectOutput);
                return;
            default:
                throw new InvalidClassException("Unknown serialized type");
        }
    }
}
