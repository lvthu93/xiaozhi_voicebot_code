package com.google.protobuf;

import androidx.appcompat.widget.ActivityChooserView;
import com.google.protobuf.a;
import com.google.protobuf.d;
import com.google.protobuf.l;
import com.google.protobuf.n;
import com.google.protobuf.n.a;
import com.google.protobuf.p;
import com.google.protobuf.x;
import j$.util.concurrent.ConcurrentHashMap;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.Map;

public abstract class n<MessageType extends n<MessageType, BuilderType>, BuilderType extends a<MessageType, BuilderType>> extends a<MessageType, BuilderType> {
    private static final int MEMOIZED_SERIALIZED_SIZE_MASK = Integer.MAX_VALUE;
    private static final int MUTABLE_FLAG_MASK = Integer.MIN_VALUE;
    static final int UNINITIALIZED_HASH_CODE = 0;
    static final int UNINITIALIZED_SERIALIZED_SIZE = Integer.MAX_VALUE;
    private static Map<Class<?>, n<?, ?>> defaultInstanceMap = new ConcurrentHashMap();
    private int memoizedSerializedSize = -1;
    protected ah unknownFields = ah.f;

    public static abstract class a<MessageType extends n<MessageType, BuilderType>, BuilderType extends a<MessageType, BuilderType>> extends a.C0022a<MessageType, BuilderType> {
        private final MessageType defaultInstance;
        protected MessageType instance;

        public a(MessageType messagetype) {
            this.defaultInstance = messagetype;
            if (!messagetype.isMutable()) {
                this.instance = newMutableInstance();
                return;
            }
            throw new IllegalArgumentException("Default instance must be immutable.");
        }

        private static <MessageType> void mergeFromInstance(MessageType messagetype, MessageType messagetype2) {
            f9 f9Var = f9.c;
            f9Var.getClass();
            f9Var.a(messagetype.getClass()).a(messagetype, messagetype2);
        }

        private MessageType newMutableInstance() {
            return this.defaultInstance.newMutableInstance();
        }

        public final void copyOnWrite() {
            if (!this.instance.isMutable()) {
                copyOnWriteInternal();
            }
        }

        public void copyOnWriteInternal() {
            MessageType newMutableInstance = newMutableInstance();
            mergeFromInstance(newMutableInstance, this.instance);
            this.instance = newMutableInstance;
        }

        public final boolean isInitialized() {
            return n.isInitialized(this.instance, false);
        }

        public final MessageType build() {
            MessageType buildPartial = buildPartial();
            if (buildPartial.isInitialized()) {
                return buildPartial;
            }
            throw a.C0022a.newUninitializedMessageException(buildPartial);
        }

        public MessageType buildPartial() {
            if (!this.instance.isMutable()) {
                return this.instance;
            }
            this.instance.makeImmutable();
            return this.instance;
        }

        public final BuilderType clear() {
            if (!this.defaultInstance.isMutable()) {
                this.instance = newMutableInstance();
                return this;
            }
            throw new IllegalArgumentException("Default instance must be immutable.");
        }

        public MessageType getDefaultInstanceForType() {
            return this.defaultInstance;
        }

        public BuilderType internalMergeFrom(MessageType messagetype) {
            return mergeFrom(messagetype);
        }

        public BuilderType clone() {
            BuilderType newBuilderForType = getDefaultInstanceForType().newBuilderForType();
            newBuilderForType.instance = buildPartial();
            return newBuilderForType;
        }

        public BuilderType mergeFrom(MessageType messagetype) {
            if (getDefaultInstanceForType().equals(messagetype)) {
                return this;
            }
            copyOnWrite();
            mergeFromInstance(this.instance, messagetype);
            return this;
        }

        public BuilderType mergeFrom(byte[] bArr, int i, int i2, i iVar) throws q {
            copyOnWrite();
            try {
                f9.c.b(this.instance).d(this.instance, bArr, i, i + i2, new d.a(iVar));
                return this;
            } catch (q e) {
                throw e;
            } catch (IndexOutOfBoundsException unused) {
                throw q.h();
            } catch (IOException e2) {
                throw new RuntimeException("Reading from byte array should not throw IOException.", e2);
            }
        }

        public BuilderType mergeFrom(byte[] bArr, int i, int i2) throws q {
            return mergeFrom(bArr, i, i2, i.a());
        }

        public BuilderType mergeFrom(f fVar, i iVar) throws IOException {
            copyOnWrite();
            try {
                ac b = f9.c.b(this.instance);
                MessageType messagetype = this.instance;
                g gVar = fVar.e;
                if (gVar == null) {
                    gVar = new g(fVar);
                }
                b.e(messagetype, gVar, iVar);
                return this;
            } catch (RuntimeException e) {
                if (e.getCause() instanceof IOException) {
                    throw ((IOException) e.getCause());
                }
                throw e;
            }
        }
    }

    public static class b<T extends n<T, ?>> extends b<T> {
        public b(T t) {
        }
    }

    public static abstract class c<MessageType extends c<MessageType, BuilderType>, BuilderType> extends n<MessageType, BuilderType> implements p6 {
        public l<d> c = l.d;

        public final /* bridge */ /* synthetic */ x getDefaultInstanceForType() {
            return n.super.getDefaultInstanceForType();
        }

        public final /* bridge */ /* synthetic */ x.a newBuilderForType() {
            return n.super.newBuilderForType();
        }

        public final /* bridge */ /* synthetic */ x.a toBuilder() {
            return n.super.toBuilder();
        }
    }

    public static final class d implements l.a<d> {
        public final int c;
        public final hf f;
        public final boolean g;
        public final boolean h;

        public d(int i, hf hfVar, boolean z, boolean z2) {
            this.c = i;
            this.f = hfVar;
            this.g = z;
            this.h = z2;
        }

        public final int a() {
            return this.c;
        }

        public final boolean c() {
            return this.g;
        }

        public final int compareTo(Object obj) {
            return this.c - ((d) obj).c;
        }

        public final void f(Object obj, Object obj2) {
            ((a) obj).mergeFrom((n) obj2);
        }

        public final hf g() {
            return this.f;
        }

        public final boolean j(Object obj) {
            return obj instanceof x;
        }

        public final Cif k() {
            return this.f.c;
        }

        public final boolean l() {
            return this.h;
        }
    }

    public static class e<ContainingType extends x, Type> extends t2<ContainingType, Type> {
        public final ContainingType a;
        public final Type b;
        public final x c;
        public final d d;

        public e(x xVar, Object obj, x xVar2, d dVar) {
            if (xVar == null) {
                throw new IllegalArgumentException("Null containingTypeDefaultInstance");
            } else if (dVar.f == hf.g && xVar2 == null) {
                throw new IllegalArgumentException("Null messageDefaultInstance");
            } else {
                this.a = xVar;
                this.b = obj;
                this.c = xVar2;
                this.d = dVar;
            }
        }
    }

    public enum f {
        GET_MEMOIZED_IS_INITIALIZED,
        SET_MEMOIZED_IS_INITIALIZED,
        BUILD_MESSAGE_INFO,
        NEW_MUTABLE_INSTANCE,
        NEW_BUILDER,
        GET_DEFAULT_INSTANCE,
        GET_PARSER
    }

    /* access modifiers changed from: private */
    public static <MessageType extends c<MessageType, BuilderType>, BuilderType, T> e<MessageType, T> checkIsLite(t2<MessageType, T> t2Var) {
        t2Var.getClass();
        return (e) t2Var;
    }

    private static <T extends n<T, ?>> T checkMessageInitialized(T t) throws q {
        if (t == null || t.isInitialized()) {
            return t;
        }
        ed newUninitializedMessageException = t.newUninitializedMessageException();
        newUninitializedMessageException.getClass();
        throw new q(newUninitializedMessageException.getMessage());
    }

    private int computeSerializedSize(ac<?> acVar) {
        if (acVar != null) {
            return acVar.h(this);
        }
        f9 f9Var = f9.c;
        f9Var.getClass();
        return f9Var.a(getClass()).h(this);
    }

    public static p.a emptyBooleanList() {
        return e.i;
    }

    public static p.b emptyDoubleList() {
        return h.i;
    }

    public static p.f emptyFloatList() {
        return m.i;
    }

    public static p.g emptyIntList() {
        return o.i;
    }

    public static p.h emptyLongList() {
        return u.i;
    }

    public static <E> p.i<E> emptyProtobufList() {
        return ab.i;
    }

    private void ensureUnknownFieldsInitialized() {
        if (this.unknownFields == ah.f) {
            this.unknownFields = new ah();
        }
    }

    public static <T extends n<?, ?>> T getDefaultInstance(Class<T> cls) {
        T t = (n) defaultInstanceMap.get(cls);
        if (t == null) {
            try {
                Class.forName(cls.getName(), true, cls.getClassLoader());
                t = (n) defaultInstanceMap.get(cls);
            } catch (ClassNotFoundException e2) {
                throw new IllegalStateException("Class initialization cannot fail.", e2);
            }
        }
        if (t == null) {
            t = ((n) fd.b(cls)).getDefaultInstanceForType();
            if (t != null) {
                defaultInstanceMap.put(cls, t);
            } else {
                throw new IllegalStateException();
            }
        }
        return t;
    }

    public static Method getMethodOrDie(Class cls, String str, Class... clsArr) {
        try {
            return cls.getMethod(str, clsArr);
        } catch (NoSuchMethodException e2) {
            throw new RuntimeException("Generated message class \"" + cls.getName() + "\" missing method \"" + str + "\".", e2);
        }
    }

    public static Object invokeOrDie(Method method, Object obj, Object... objArr) {
        try {
            return method.invoke(obj, objArr);
        } catch (IllegalAccessException e2) {
            throw new RuntimeException("Couldn't use Java reflection to implement protocol message reflection.", e2);
        } catch (InvocationTargetException e3) {
            Throwable cause = e3.getCause();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            } else if (cause instanceof Error) {
                throw ((Error) cause);
            } else {
                throw new RuntimeException("Unexpected exception thrown by generated accessor method.", cause);
            }
        }
    }

    public static p.g mutableCopy(p.g gVar) {
        o oVar = (o) gVar;
        return oVar.h(oVar.g * 2);
    }

    public static Object newMessageInfo(x xVar, String str, Object[] objArr) {
        return new x9(xVar, str, objArr);
    }

    public static <ContainingType extends x, Type> e<ContainingType, Type> newRepeatedGeneratedExtension(ContainingType containingtype, x xVar, p.d<?> dVar, int i, hf hfVar, boolean z, Class cls) {
        return new e<>(containingtype, ab.i, xVar, new d(i, hfVar, true, z));
    }

    public static <ContainingType extends x, Type> e<ContainingType, Type> newSingularGeneratedExtension(ContainingType containingtype, Type type, x xVar, p.d<?> dVar, int i, hf hfVar, Class cls) {
        return new e<>(containingtype, type, xVar, new d(i, hfVar, false, false));
    }

    public static <T extends n<T, ?>> T parseDelimitedFrom(T t, InputStream inputStream) throws q {
        return checkMessageInitialized(parsePartialDelimitedFrom(t, inputStream, i.a()));
    }

    public static <T extends n<T, ?>> T parseFrom(T t, ByteBuffer byteBuffer, i iVar) throws q {
        return checkMessageInitialized(parseFrom(t, f.h(byteBuffer, false), iVar));
    }

    private static <T extends n<T, ?>> T parsePartialDelimitedFrom(T t, InputStream inputStream, i iVar) throws q {
        try {
            int read = inputStream.read();
            if (read == -1) {
                return null;
            }
            f g = f.g(new a.C0022a.C0023a(inputStream, f.t(inputStream, read)));
            T parsePartialFrom = parsePartialFrom(t, g, iVar);
            try {
                g.a(0);
                return parsePartialFrom;
            } catch (q e2) {
                throw e2;
            }
        } catch (q e3) {
            e = e3;
            if (e.c) {
                e = new q((IOException) e);
            }
            throw e;
        } catch (IOException e4) {
            throw new q(e4);
        }
    }

    public static <T extends n<T, ?>> T parsePartialFrom(T t, f fVar, i iVar) throws q {
        T newMutableInstance = t.newMutableInstance();
        try {
            ac b2 = f9.c.b(newMutableInstance);
            g gVar = fVar.e;
            if (gVar == null) {
                gVar = new g(fVar);
            }
            b2.e(newMutableInstance, gVar, iVar);
            b2.b(newMutableInstance);
            return newMutableInstance;
        } catch (q e2) {
            e = e2;
            if (e.c) {
                e = new q((IOException) e);
            }
            throw e;
        } catch (ed e3) {
            throw new q(e3.getMessage());
        } catch (IOException e4) {
            if (e4.getCause() instanceof q) {
                throw ((q) e4.getCause());
            }
            throw new q(e4);
        } catch (RuntimeException e5) {
            if (e5.getCause() instanceof q) {
                throw ((q) e5.getCause());
            }
            throw e5;
        }
    }

    public static <T extends n<?, ?>> void registerDefaultInstance(Class<T> cls, T t) {
        t.markImmutable();
        defaultInstanceMap.put(cls, t);
    }

    public Object buildMessageInfo() throws Exception {
        return dynamicMethod(f.BUILD_MESSAGE_INFO, (Object) null, (Object) null);
    }

    public void clearMemoizedHashCode() {
        this.memoizedHashCode = 0;
    }

    public void clearMemoizedSerializedSize() {
        setMemoizedSerializedSize(ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
    }

    public int computeHashCode() {
        f9 f9Var = f9.c;
        f9Var.getClass();
        return f9Var.a(getClass()).j(this);
    }

    public final <MessageType2 extends n<MessageType2, BuilderType2>, BuilderType2 extends a<MessageType2, BuilderType2>> BuilderType2 createBuilder() {
        return (a) dynamicMethod(f.NEW_BUILDER, (Object) null, (Object) null);
    }

    public abstract Object dynamicMethod(f fVar, Object obj, Object obj2);

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        f9 f9Var = f9.c;
        f9Var.getClass();
        return f9Var.a(getClass()).f(this, (n) obj);
    }

    public int getMemoizedHashCode() {
        return this.memoizedHashCode;
    }

    public int getMemoizedSerializedSize() {
        return this.memoizedSerializedSize & ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
    }

    public final n8<MessageType> getParserForType() {
        return (n8) dynamicMethod(f.GET_PARSER, (Object) null, (Object) null);
    }

    public int getSerializedSize(ac acVar) {
        if (isMutable()) {
            int computeSerializedSize = computeSerializedSize(acVar);
            if (computeSerializedSize >= 0) {
                return computeSerializedSize;
            }
            throw new IllegalStateException(y2.f("serialized size must be non-negative, was ", computeSerializedSize));
        } else if (getMemoizedSerializedSize() != Integer.MAX_VALUE) {
            return getMemoizedSerializedSize();
        } else {
            int computeSerializedSize2 = computeSerializedSize(acVar);
            setMemoizedSerializedSize(computeSerializedSize2);
            return computeSerializedSize2;
        }
    }

    public int hashCode() {
        if (isMutable()) {
            return computeHashCode();
        }
        if (hashCodeIsNotMemoized()) {
            setMemoizedHashCode(computeHashCode());
        }
        return getMemoizedHashCode();
    }

    public boolean hashCodeIsNotMemoized() {
        return getMemoizedHashCode() == 0;
    }

    public final boolean isInitialized() {
        return isInitialized(this, true);
    }

    public boolean isMutable() {
        return (this.memoizedSerializedSize & Integer.MIN_VALUE) != 0;
    }

    public void makeImmutable() {
        f9 f9Var = f9.c;
        f9Var.getClass();
        f9Var.a(getClass()).b(this);
        markImmutable();
    }

    public void markImmutable() {
        this.memoizedSerializedSize &= ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
    }

    public void mergeLengthDelimitedField(int i, cp cpVar) {
        ensureUnknownFieldsInitialized();
        ah ahVar = this.unknownFields;
        ahVar.a();
        if (i != 0) {
            ahVar.f((i << 3) | 2, cpVar);
            return;
        }
        throw new IllegalArgumentException("Zero is not a valid field number.");
    }

    public final void mergeUnknownFields(ah ahVar) {
        this.unknownFields = ah.e(this.unknownFields, ahVar);
    }

    public void mergeVarintField(int i, int i2) {
        ensureUnknownFieldsInitialized();
        ah ahVar = this.unknownFields;
        ahVar.a();
        if (i != 0) {
            ahVar.f((i << 3) | 0, Long.valueOf((long) i2));
            return;
        }
        throw new IllegalArgumentException("Zero is not a valid field number.");
    }

    public MessageType newMutableInstance() {
        return (n) dynamicMethod(f.NEW_MUTABLE_INSTANCE, (Object) null, (Object) null);
    }

    public boolean parseUnknownField(int i, f fVar) throws IOException {
        if ((i & 7) == 4) {
            return false;
        }
        ensureUnknownFieldsInitialized();
        return this.unknownFields.d(i, fVar);
    }

    public void setMemoizedHashCode(int i) {
        this.memoizedHashCode = i;
    }

    public void setMemoizedSerializedSize(int i) {
        if (i >= 0) {
            this.memoizedSerializedSize = (i & ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED) | (this.memoizedSerializedSize & Integer.MIN_VALUE);
            return;
        }
        throw new IllegalStateException(y2.f("serialized size must be non-negative, was ", i));
    }

    public String toString() {
        String obj = super.toString();
        char[] cArr = y.a;
        StringBuilder sb = new StringBuilder();
        sb.append("# ");
        sb.append(obj);
        y.c(this, sb, 0);
        return sb.toString();
    }

    public void writeTo(n0 n0Var) throws IOException {
        f9 f9Var = f9.c;
        f9Var.getClass();
        ac<?> a2 = f9Var.a(getClass());
        o0 o0Var = n0Var.c;
        if (o0Var == null) {
            o0Var = new o0(n0Var);
        }
        a2.g(this, o0Var);
    }

    /* access modifiers changed from: private */
    public static final <T extends n<T, ?>> boolean isInitialized(T t, boolean z) {
        byte byteValue = ((Byte) t.dynamicMethod(f.GET_MEMOIZED_IS_INITIALIZED, (Object) null, (Object) null)).byteValue();
        if (byteValue == 1) {
            return true;
        }
        if (byteValue == 0) {
            return false;
        }
        f9 f9Var = f9.c;
        f9Var.getClass();
        boolean c2 = f9Var.a(t.getClass()).c(t);
        if (z) {
            t.dynamicMethod(f.SET_MEMOIZED_IS_INITIALIZED, c2 ? t : null, (Object) null);
        }
        return c2;
    }

    public final <MessageType2 extends n<MessageType2, BuilderType2>, BuilderType2 extends a<MessageType2, BuilderType2>> BuilderType2 createBuilder(MessageType2 messagetype2) {
        return createBuilder().mergeFrom(messagetype2);
    }

    public final MessageType getDefaultInstanceForType() {
        return (n) dynamicMethod(f.GET_DEFAULT_INSTANCE, (Object) null, (Object) null);
    }

    public final BuilderType newBuilderForType() {
        return (a) dynamicMethod(f.NEW_BUILDER, (Object) null, (Object) null);
    }

    public final BuilderType toBuilder() {
        return ((a) dynamicMethod(f.NEW_BUILDER, (Object) null, (Object) null)).mergeFrom(this);
    }

    public static p.h mutableCopy(p.h hVar) {
        u uVar = (u) hVar;
        return uVar.h(uVar.g * 2);
    }

    public static <T extends n<T, ?>> T parseDelimitedFrom(T t, InputStream inputStream, i iVar) throws q {
        return checkMessageInitialized(parsePartialDelimitedFrom(t, inputStream, iVar));
    }

    public static <T extends n<T, ?>> T parseFrom(T t, ByteBuffer byteBuffer) throws q {
        return parseFrom(t, byteBuffer, i.a());
    }

    public static <T extends n<T, ?>> T parseFrom(T t, cp cpVar) throws q {
        return checkMessageInitialized(parseFrom(t, cpVar, i.a()));
    }

    public static p.f mutableCopy(p.f fVar) {
        m mVar = (m) fVar;
        return mVar.h(mVar.g * 2);
    }

    public static <T extends n<T, ?>> T parseFrom(T t, cp cpVar, i iVar) throws q {
        return checkMessageInitialized(parsePartialFrom(t, cpVar, iVar));
    }

    public static <T extends n<T, ?>> T parseFrom(T t, byte[] bArr) throws q {
        return checkMessageInitialized(parsePartialFrom(t, bArr, 0, bArr.length, i.a()));
    }

    public static p.b mutableCopy(p.b bVar) {
        h hVar = (h) bVar;
        return hVar.h(hVar.g * 2);
    }

    public static <T extends n<T, ?>> T parseFrom(T t, byte[] bArr, i iVar) throws q {
        return checkMessageInitialized(parsePartialFrom(t, bArr, 0, bArr.length, iVar));
    }

    public static p.a mutableCopy(p.a aVar) {
        e eVar = (e) aVar;
        return eVar.h(eVar.g * 2);
    }

    public int getSerializedSize() {
        return getSerializedSize((ac) null);
    }

    public static <T extends n<T, ?>> T parseFrom(T t, InputStream inputStream) throws q {
        return checkMessageInitialized(parsePartialFrom(t, f.g(inputStream), i.a()));
    }

    public static <E> p.i<E> mutableCopy(p.i<E> iVar) {
        return iVar.h(iVar.size() * 2);
    }

    public static <T extends n<T, ?>> T parseFrom(T t, InputStream inputStream, i iVar) throws q {
        return checkMessageInitialized(parsePartialFrom(t, f.g(inputStream), iVar));
    }

    /* access modifiers changed from: private */
    public static <T extends n<T, ?>> T parsePartialFrom(T t, byte[] bArr, int i, int i2, i iVar) throws q {
        if (i2 == 0) {
            return t;
        }
        T newMutableInstance = t.newMutableInstance();
        try {
            ac b2 = f9.c.b(newMutableInstance);
            b2.d(newMutableInstance, bArr, i, i + i2, new d.a(iVar));
            b2.b(newMutableInstance);
            return newMutableInstance;
        } catch (q e2) {
            e = e2;
            if (e.c) {
                e = new q((IOException) e);
            }
            throw e;
        } catch (ed e3) {
            throw new q(e3.getMessage());
        } catch (IOException e4) {
            if (e4.getCause() instanceof q) {
                throw ((q) e4.getCause());
            }
            throw new q(e4);
        } catch (IndexOutOfBoundsException unused) {
            throw q.h();
        }
    }

    public static <T extends n<T, ?>> T parseFrom(T t, f fVar) throws q {
        return parseFrom(t, fVar, i.a());
    }

    public static <T extends n<T, ?>> T parseFrom(T t, f fVar, i iVar) throws q {
        return checkMessageInitialized(parsePartialFrom(t, fVar, iVar));
    }

    public static <T extends n<T, ?>> T parsePartialFrom(T t, f fVar) throws q {
        return parsePartialFrom(t, fVar, i.a());
    }

    private static <T extends n<T, ?>> T parsePartialFrom(T t, cp cpVar, i iVar) throws q {
        f m = cpVar.m();
        T parsePartialFrom = parsePartialFrom(t, m, iVar);
        try {
            m.a(0);
            return parsePartialFrom;
        } catch (q e2) {
            throw e2;
        }
    }
}
