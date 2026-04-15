package com.google.protobuf;

import com.google.protobuf.a;
import com.google.protobuf.a.C0022a;
import com.google.protobuf.f;
import com.google.protobuf.x;
import defpackage.cp;
import defpackage.n0;
import j$.io.DesugarInputStream;
import j$.io.InputStreamRetargetInterface;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;
import java.util.logging.Logger;

public abstract class a<MessageType extends a<MessageType, BuilderType>, BuilderType extends C0022a<MessageType, BuilderType>> implements x {
    protected int memoizedHashCode = 0;

    /* renamed from: com.google.protobuf.a$a  reason: collision with other inner class name */
    public static abstract class C0022a<MessageType extends a<MessageType, BuilderType>, BuilderType extends C0022a<MessageType, BuilderType>> implements x.a {
        @Deprecated
        public static <T> void addAll(Iterable<T> iterable, Collection<? super T> collection) {
            addAll(iterable, (List) collection);
        }

        private static <T> void addAllCheckingNulls(Iterable<T> iterable, List<? super T> list) {
            if (iterable instanceof Collection) {
                int size = ((Collection) iterable).size();
                if (list instanceof ArrayList) {
                    ((ArrayList) list).ensureCapacity(list.size() + size);
                } else if (list instanceof ab) {
                    ab abVar = (ab) list;
                    int size2 = list.size() + size;
                    E[] eArr = abVar.f;
                    if (size2 > eArr.length) {
                        if (eArr.length == 0) {
                            abVar.f = new Object[Math.max(size2, 10)];
                        } else {
                            int length = eArr.length;
                            while (length < size2) {
                                length = y2.b(length, 3, 2, 1, 10);
                            }
                            abVar.f = Arrays.copyOf(abVar.f, length);
                        }
                    }
                }
            }
            int size3 = list.size();
            if (!(iterable instanceof List) || !(iterable instanceof RandomAccess)) {
                for (T next : iterable) {
                    if (next == null) {
                        resetListAndThrow(list, size3);
                    }
                    list.add(next);
                }
                return;
            }
            List list2 = (List) iterable;
            int size4 = list2.size();
            for (int i = 0; i < size4; i++) {
                Object obj = list2.get(i);
                if (obj == null) {
                    resetListAndThrow(list, size3);
                }
                list.add(obj);
            }
        }

        private String getReadingExceptionMessage(String str) {
            return "Reading " + getClass().getName() + " from a " + str + " threw an IOException (should never happen).";
        }

        public static ed newUninitializedMessageException(x xVar) {
            return new ed();
        }

        private static void resetListAndThrow(List<?> list, int i) {
            String str = "Element at index " + (list.size() - i) + " is null.";
            for (int size = list.size() - 1; size >= i; size--) {
                list.remove(size);
            }
            throw new NullPointerException(str);
        }

        public abstract BuilderType clone();

        public abstract BuilderType internalMergeFrom(MessageType messagetype);

        public boolean mergeDelimitedFrom(InputStream inputStream, i iVar) throws IOException {
            int read = inputStream.read();
            if (read == -1) {
                return false;
            }
            mergeFrom((InputStream) new C0023a(inputStream, f.t(inputStream, read)), iVar);
            return true;
        }

        public abstract BuilderType mergeFrom(f fVar, i iVar) throws IOException;

        public BuilderType mergeFrom(byte[] bArr, int i, int i2) throws q {
            try {
                f.a f = f.f(bArr, i, i2, false);
                mergeFrom((f) f);
                f.a(0);
                return this;
            } catch (q e) {
                throw e;
            } catch (IOException e2) {
                throw new RuntimeException(getReadingExceptionMessage("byte array"), e2);
            }
        }

        public static <T> void addAll(Iterable<T> iterable, List<? super T> list) {
            Charset charset = p.a;
            iterable.getClass();
            if (iterable instanceof e4) {
                List<?> n = ((e4) iterable).n();
                e4 e4Var = (e4) list;
                int size = list.size();
                for (Object next : n) {
                    if (next == null) {
                        String str = "Element at index " + (e4Var.size() - size) + " is null.";
                        int size2 = e4Var.size();
                        while (true) {
                            size2--;
                            if (size2 >= size) {
                                e4Var.remove(size2);
                            } else {
                                throw new NullPointerException(str);
                            }
                        }
                    } else if (next instanceof cp) {
                        cp cpVar = (cp) next;
                        e4Var.i();
                    } else if (next instanceof byte[]) {
                        byte[] bArr = (byte[]) next;
                        cp.f(bArr, 0, bArr.length);
                        e4Var.i();
                    } else {
                        e4Var.add((String) next);
                    }
                }
            } else if (iterable instanceof a9) {
                list.addAll((Collection) iterable);
            } else {
                addAllCheckingNulls(iterable, list);
            }
        }

        /* renamed from: com.google.protobuf.a$a$a  reason: collision with other inner class name */
        public static final class C0023a extends FilterInputStream implements InputStreamRetargetInterface {
            public int c;

            public C0023a(InputStream inputStream, int i) {
                super(inputStream);
                this.c = i;
            }

            public final int available() throws IOException {
                return Math.min(super.available(), this.c);
            }

            public final int read() throws IOException {
                if (this.c <= 0) {
                    return -1;
                }
                int read = super.read();
                if (read >= 0) {
                    this.c--;
                }
                return read;
            }

            public final long skip(long j) throws IOException {
                int skip = (int) super.skip(Math.min(j, (long) this.c));
                if (skip >= 0) {
                    this.c -= skip;
                }
                return (long) skip;
            }

            public final /* synthetic */ long transferTo(OutputStream outputStream) {
                return DesugarInputStream.transferTo(this, outputStream);
            }

            public final int read(byte[] bArr, int i, int i2) throws IOException {
                int i3 = this.c;
                if (i3 <= 0) {
                    return -1;
                }
                int read = super.read(bArr, i, Math.min(i2, i3));
                if (read >= 0) {
                    this.c -= read;
                }
                return read;
            }
        }

        public boolean mergeDelimitedFrom(InputStream inputStream) throws IOException {
            return mergeDelimitedFrom(inputStream, i.a());
        }

        public BuilderType mergeFrom(byte[] bArr, int i, int i2, i iVar) throws q {
            try {
                f.a f = f.f(bArr, i, i2, false);
                mergeFrom((f) f, iVar);
                f.a(0);
                return this;
            } catch (q e) {
                throw e;
            } catch (IOException e2) {
                throw new RuntimeException(getReadingExceptionMessage("byte array"), e2);
            }
        }

        public BuilderType mergeFrom(f fVar) throws IOException {
            return mergeFrom(fVar, i.a());
        }

        public BuilderType mergeFrom(cp cpVar) throws q {
            try {
                f m = cpVar.m();
                mergeFrom(m);
                m.a(0);
                return this;
            } catch (q e) {
                throw e;
            } catch (IOException e2) {
                throw new RuntimeException(getReadingExceptionMessage("ByteString"), e2);
            }
        }

        public BuilderType mergeFrom(cp cpVar, i iVar) throws q {
            try {
                f m = cpVar.m();
                mergeFrom(m, iVar);
                m.a(0);
                return this;
            } catch (q e) {
                throw e;
            } catch (IOException e2) {
                throw new RuntimeException(getReadingExceptionMessage("ByteString"), e2);
            }
        }

        public BuilderType mergeFrom(byte[] bArr) throws q {
            return mergeFrom(bArr, 0, bArr.length);
        }

        public BuilderType mergeFrom(byte[] bArr, i iVar) throws q {
            return mergeFrom(bArr, 0, bArr.length, iVar);
        }

        public BuilderType mergeFrom(InputStream inputStream) throws IOException {
            f g = f.g(inputStream);
            mergeFrom(g);
            g.a(0);
            return this;
        }

        public BuilderType mergeFrom(InputStream inputStream, i iVar) throws IOException {
            f g = f.g(inputStream);
            mergeFrom(g, iVar);
            g.a(0);
            return this;
        }

        public BuilderType mergeFrom(x xVar) {
            if (getDefaultInstanceForType().getClass().isInstance(xVar)) {
                return internalMergeFrom((a) xVar);
            }
            throw new IllegalArgumentException("mergeFrom(MessageLite) can only merge messages of the same type.");
        }
    }

    public static <T> void addAll(Iterable<T> iterable, List<? super T> list) {
        C0022a.addAll(iterable, list);
    }

    public static void checkByteStringIsUtf8(cp cpVar) throws IllegalArgumentException {
        if (!cpVar.k()) {
            throw new IllegalArgumentException("Byte string is not UTF-8.");
        }
    }

    private String getSerializingExceptionMessage(String str) {
        return "Serializing " + getClass().getName() + " to a " + str + " threw an IOException (should never happen).";
    }

    public int getMemoizedSerializedSize() {
        throw new UnsupportedOperationException();
    }

    public int getSerializedSize(ac acVar) {
        int memoizedSerializedSize = getMemoizedSerializedSize();
        if (memoizedSerializedSize != -1) {
            return memoizedSerializedSize;
        }
        int h = acVar.h(this);
        setMemoizedSerializedSize(h);
        return h;
    }

    public ed newUninitializedMessageException() {
        return new ed();
    }

    public void setMemoizedSerializedSize(int i) {
        throw new UnsupportedOperationException();
    }

    public byte[] toByteArray() {
        try {
            int serializedSize = getSerializedSize();
            byte[] bArr = new byte[serializedSize];
            Logger logger = n0.f;
            n0.b bVar = new n0.b(bArr, serializedSize);
            writeTo(bVar);
            if (bVar.ap() == 0) {
                return bArr;
            }
            throw new IllegalStateException("Did not write as much data as expected.");
        } catch (IOException e) {
            throw new RuntimeException(getSerializingExceptionMessage("byte array"), e);
        }
    }

    public cp toByteString() {
        try {
            int serializedSize = getSerializedSize();
            cp.h hVar = cp.f;
            byte[] bArr = new byte[serializedSize];
            Logger logger = n0.f;
            n0.b bVar = new n0.b(bArr, serializedSize);
            writeTo(bVar);
            if (bVar.ap() == 0) {
                return new cp.h(bArr);
            }
            throw new IllegalStateException("Did not write as much data as expected.");
        } catch (IOException e) {
            throw new RuntimeException(getSerializingExceptionMessage("ByteString"), e);
        }
    }

    public void writeDelimitedTo(OutputStream outputStream) throws IOException {
        int serializedSize = getSerializedSize();
        int u = n0.u(serializedSize) + serializedSize;
        if (u > 4096) {
            u = 4096;
        }
        n0.d dVar = new n0.d(outputStream, u);
        dVar.am(serializedSize);
        writeTo(dVar);
        if (dVar.j > 0) {
            dVar.au();
        }
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        int serializedSize = getSerializedSize();
        Logger logger = n0.f;
        if (serializedSize > 4096) {
            serializedSize = 4096;
        }
        n0.d dVar = new n0.d(outputStream, serializedSize);
        writeTo(dVar);
        if (dVar.j > 0) {
            dVar.au();
        }
    }
}
