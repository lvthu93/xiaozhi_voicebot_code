package j$.time.zone;

import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;

final class h implements PrivilegedAction {
    final /* synthetic */ List a;

    h(ArrayList arrayList) {
        this.a = arrayList;
    }

    public final Object run() {
        Class<j> cls = j.class;
        String property = System.getProperty("java.time.zone.DefaultZoneRulesProvider");
        if (property != null) {
            try {
                j cast = cls.cast(Class.forName(property, true, cls.getClassLoader()).newInstance());
                j.e(cast);
                this.a.add(cast);
                return null;
            } catch (Exception e) {
                throw new Error(e);
            }
        } else {
            j.e(new i());
            return null;
        }
    }
}
