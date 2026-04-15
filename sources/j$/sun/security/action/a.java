package j$.sun.security.action;

import java.security.PrivilegedAction;

public final class a implements PrivilegedAction {
    private String a = "file.encoding";

    public final Object run() {
        String property = System.getProperty(this.a);
        if (property == null) {
            return null;
        }
        return property;
    }
}
