package defpackage;

import java.io.ObjectStreamException;
import java.io.Serializable;

/* renamed from: f7  reason: default package */
public abstract class f7 implements k4, Serializable {
    private static final long serialVersionUID = 7535258609338176893L;

    public String getName() {
        return null;
    }

    public Object readResolve() throws ObjectStreamException {
        return l4.f(getName());
    }
}
