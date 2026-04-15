package defpackage;

import java.io.IOException;
import java.nio.channels.WritableByteChannel;

/* renamed from: cl  reason: default package */
public interface cl extends za, WritableByteChannel {
    ck a();

    cl c() throws IOException;

    void flush() throws IOException;

    long j(jb jbVar) throws IOException;

    cl k() throws IOException;

    cl o(String str) throws IOException;

    cl r(cq cqVar) throws IOException;

    cl s(long j) throws IOException;

    cl write(byte[] bArr) throws IOException;

    cl write(byte[] bArr, int i, int i2) throws IOException;

    cl writeByte(int i) throws IOException;

    cl writeInt(int i) throws IOException;

    cl writeShort(int i) throws IOException;

    cl x(long j) throws IOException;
}
