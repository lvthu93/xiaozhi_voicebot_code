package j$.util.concurrent;

import java.security.PrivilegedAction;

final class w implements PrivilegedAction {
    w() {
    }

    public final Object run() {
        return Boolean.valueOf(Boolean.getBoolean("java.util.secureRandomSeed"));
    }
}
