package defpackage;

import java.io.Serializable;

/* renamed from: b4  reason: default package */
public enum b4 {
    VOID(Void.class, (int) null),
    INT(r7, 0),
    LONG(Long.class, 0L),
    FLOAT(Float.class, Float.valueOf(0.0f)),
    DOUBLE(Double.class, Double.valueOf(0.0d)),
    BOOLEAN(Boolean.class, Boolean.FALSE),
    STRING(String.class, ""),
    BYTE_STRING(cp.class, cp.f),
    ENUM(r7, (int) null),
    MESSAGE(Object.class, (int) null);
    
    public final Object c;

    /* access modifiers changed from: public */
    b4(Class cls, Serializable serializable) {
        this.c = serializable;
    }
}
