package j$.time.zone;

import j$.time.ZoneOffset;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.Externalizable;
import java.io.InvalidClassException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.StreamCorruptedException;
import java.util.TimeZone;
import org.mozilla.javascript.Token;

final class a implements Externalizable {
    private static final long serialVersionUID = -8885321777449118786L;
    private byte a;
    private Object b;

    public a() {
    }

    a(byte b2, Object obj) {
        this.a = b2;
        this.b = obj;
    }

    static long a(DataInput dataInput) {
        byte readByte = dataInput.readByte() & 255;
        if (readByte == 255) {
            return dataInput.readLong();
        }
        return (((long) (((readByte << 16) + ((dataInput.readByte() & 255) << 8)) + (dataInput.readByte() & 255))) * 900) - 4575744000L;
    }

    static ZoneOffset b(DataInput dataInput) {
        byte readByte = dataInput.readByte();
        return readByte == Byte.MAX_VALUE ? ZoneOffset.d0(dataInput.readInt()) : ZoneOffset.d0(readByte * 900);
    }

    static void c(long j, DataOutput dataOutput) {
        if (j < -4575744000L || j >= 10413792000L || j % 900 != 0) {
            dataOutput.writeByte(255);
            dataOutput.writeLong(j);
            return;
        }
        int i = (int) ((j + 4575744000L) / 900);
        dataOutput.writeByte((i >>> 16) & 255);
        dataOutput.writeByte((i >>> 8) & 255);
        dataOutput.writeByte(i & 255);
    }

    static void d(ZoneOffset zoneOffset, DataOutput dataOutput) {
        int a0 = zoneOffset.a0();
        int i = a0 % 900 == 0 ? a0 / 900 : Token.VOID;
        dataOutput.writeByte(i);
        if (i == 127) {
            dataOutput.writeInt(a0);
        }
    }

    private Object readResolve() {
        return this.b;
    }

    public final void readExternal(ObjectInput objectInput) {
        Object obj;
        byte readByte = objectInput.readByte();
        this.a = readByte;
        if (readByte == 1) {
            obj = f.l(objectInput);
        } else if (readByte == 2) {
            long a2 = a(objectInput);
            ZoneOffset b2 = b(objectInput);
            ZoneOffset b3 = b(objectInput);
            if (!b2.equals(b3)) {
                obj = new b(a2, b2, b3);
            } else {
                throw new IllegalArgumentException("Offsets must not be equal");
            }
        } else if (readByte == 3) {
            obj = e.b(objectInput);
        } else if (readByte == 100) {
            obj = new f(TimeZone.getTimeZone(objectInput.readUTF()));
        } else {
            throw new StreamCorruptedException("Unknown serialized type");
        }
        this.b = obj;
    }

    public final void writeExternal(ObjectOutput objectOutput) {
        byte b2 = this.a;
        Object obj = this.b;
        objectOutput.writeByte(b2);
        if (b2 == 1) {
            ((f) obj).m(objectOutput);
        } else if (b2 == 2) {
            ((b) obj).R(objectOutput);
        } else if (b2 == 3) {
            ((e) obj).c(objectOutput);
        } else if (b2 == 100) {
            ((f) obj).n(objectOutput);
        } else {
            throw new InvalidClassException("Unknown serialized type");
        }
    }
}
