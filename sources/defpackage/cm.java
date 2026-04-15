package defpackage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;

/* renamed from: cm  reason: default package */
public interface cm extends jb, ReadableByteChannel {
    ck a();

    boolean aa(cq cqVar) throws IOException;

    cq b(long j) throws IOException;

    byte[] f() throws IOException;

    boolean g() throws IOException;

    InputStream inputStream();

    long l() throws IOException;

    String m(long j) throws IOException;

    void p(ck ckVar, long j) throws IOException;

    String q(Charset charset) throws IOException;

    byte readByte() throws IOException;

    void readFully(byte[] bArr) throws IOException;

    int readInt() throws IOException;

    long readLong() throws IOException;

    short readShort() throws IOException;

    void skip(long j) throws IOException;

    boolean t(long j) throws IOException;

    String u() throws IOException;

    byte[] v(long j) throws IOException;

    void w(long j) throws IOException;

    long z() throws IOException;
}
