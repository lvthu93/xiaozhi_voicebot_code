package com.google.protobuf;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.List;
import java.util.RandomAccess;

public final class p {
    public static final Charset a = Charset.forName("UTF-8");
    public static final byte[] b;

    public interface a extends i<Boolean> {
    }

    public interface b extends i<Double> {
    }

    public interface c {
        int a();
    }

    public interface d<T extends c> {
    }

    public interface e {
        boolean a();
    }

    public interface f extends i<Float> {
    }

    public interface g extends i<Integer> {
    }

    public interface h extends i<Long> {
    }

    public interface i<E> extends List<E>, RandomAccess {
        void e();

        i<E> h(int i);

        boolean y();
    }

    static {
        Charset.forName("ISO-8859-1");
        byte[] bArr = new byte[0];
        b = bArr;
        ByteBuffer.wrap(bArr);
        f.f(bArr, 0, 0, false);
    }

    public static int a(long j) {
        return (int) (j ^ (j >>> 32));
    }
}
