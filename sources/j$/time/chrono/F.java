package j$.time.chrono;

import j$.time.LocalDate;
import j$.time.ZoneId;
import j$.time.ZoneOffset;
import j$.time.j;
import j$.time.temporal.a;
import j$.time.temporal.p;
import java.io.Externalizable;
import java.io.InvalidClassException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.StreamCorruptedException;

final class F implements Externalizable {
    private static final long serialVersionUID = -6103370247208168577L;
    private byte a;
    private Object b;

    public F() {
    }

    F(byte b2, Object obj) {
        this.a = b2;
        this.b = obj;
    }

    private Object readResolve() {
        return this.b;
    }

    public final void readExternal(ObjectInput objectInput) {
        Object obj;
        byte readByte = objectInput.readByte();
        this.a = readByte;
        switch (readByte) {
            case 1:
                int i = C0037a.c;
                obj = C0037a.p(objectInput.readUTF());
                break;
            case 2:
                obj = ((C0039c) objectInput.readObject()).G((j) objectInput.readObject());
                break;
            case 3:
                obj = ((C0042f) objectInput.readObject()).A((ZoneOffset) objectInput.readObject()).y((ZoneId) objectInput.readObject());
                break;
            case 4:
                LocalDate localDate = y.d;
                int readInt = objectInput.readInt();
                byte readByte2 = objectInput.readByte();
                byte readByte3 = objectInput.readByte();
                w.d.getClass();
                obj = new y(LocalDate.of(readInt, readByte2, readByte3));
                break;
            case 5:
                z zVar = z.d;
                obj = z.s(objectInput.readByte());
                break;
            case 6:
                p pVar = (p) objectInput.readObject();
                int readInt2 = objectInput.readInt();
                byte readByte4 = objectInput.readByte();
                byte readByte5 = objectInput.readByte();
                pVar.getClass();
                obj = r.V(pVar, readInt2, readByte4, readByte5);
                break;
            case 7:
                int readInt3 = objectInput.readInt();
                byte readByte6 = objectInput.readByte();
                byte readByte7 = objectInput.readByte();
                B.d.getClass();
                obj = new D(LocalDate.of(readInt3 + 1911, readByte6, readByte7));
                break;
            case 8:
                int readInt4 = objectInput.readInt();
                byte readByte8 = objectInput.readByte();
                byte readByte9 = objectInput.readByte();
                H.d.getClass();
                obj = new J(LocalDate.of(readInt4 - 543, readByte8, readByte9));
                break;
            case 9:
                int i2 = C0045i.e;
                obj = new C0045i(C0037a.p(objectInput.readUTF()), objectInput.readInt(), objectInput.readInt(), objectInput.readInt());
                break;
            default:
                throw new StreamCorruptedException("Unknown serialized type");
        }
        this.b = obj;
    }

    public final void writeExternal(ObjectOutput objectOutput) {
        byte b2 = this.a;
        Object obj = this.b;
        objectOutput.writeByte(b2);
        switch (b2) {
            case 1:
                objectOutput.writeUTF(((C0037a) obj).j());
                return;
            case 2:
                ((C0044h) obj).writeExternal(objectOutput);
                return;
            case 3:
                ((l) obj).writeExternal(objectOutput);
                return;
            case 4:
                y yVar = (y) obj;
                yVar.getClass();
                objectOutput.writeInt(p.a(yVar, a.YEAR));
                objectOutput.writeByte(p.a(yVar, a.MONTH_OF_YEAR));
                objectOutput.writeByte(p.a(yVar, a.DAY_OF_MONTH));
                return;
            case 5:
                ((z) obj).w(objectOutput);
                return;
            case 6:
                ((r) obj).writeExternal(objectOutput);
                return;
            case 7:
                D d = (D) obj;
                d.getClass();
                objectOutput.writeInt(p.a(d, a.YEAR));
                objectOutput.writeByte(p.a(d, a.MONTH_OF_YEAR));
                objectOutput.writeByte(p.a(d, a.DAY_OF_MONTH));
                return;
            case 8:
                J j = (J) obj;
                j.getClass();
                objectOutput.writeInt(p.a(j, a.YEAR));
                objectOutput.writeByte(p.a(j, a.MONTH_OF_YEAR));
                objectOutput.writeByte(p.a(j, a.DAY_OF_MONTH));
                return;
            case 9:
                ((C0045i) obj).a(objectOutput);
                return;
            default:
                throw new InvalidClassException("Unknown serialized type");
        }
    }
}
