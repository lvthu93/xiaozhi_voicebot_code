package j$.util;

import java.security.AccessController;

abstract class l0 {
    static final boolean a = ((Boolean) AccessController.doPrivileged(new k0())).booleanValue();

    static void a(Class cls, String str) {
        throw new UnsupportedOperationException(cls + " tripwire tripped but logging not supported: " + str);
    }
}
