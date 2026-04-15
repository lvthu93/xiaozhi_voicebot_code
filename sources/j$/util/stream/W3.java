package j$.util.stream;

import java.security.PrivilegedAction;

public final /* synthetic */ class W3 implements PrivilegedAction {
    public final Object run() {
        return Boolean.valueOf(Boolean.getBoolean("org.openjdk.java.util.stream.tripwire"));
    }
}
