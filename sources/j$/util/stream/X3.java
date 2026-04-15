package j$.util.stream;

import java.security.AccessController;

abstract class X3 {
    static final boolean a = ((Boolean) AccessController.doPrivileged(new W3())).booleanValue();

    static void a(Class cls, String str) {
        throw new UnsupportedOperationException(cls + " tripwire tripped but logging not supported: " + str);
    }
}
