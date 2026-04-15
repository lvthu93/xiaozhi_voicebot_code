package j$.time.zone;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TimeZone;

final class i extends j {
    private final Set d;

    i() {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (String add : TimeZone.getAvailableIDs()) {
            linkedHashSet.add(add);
        }
        this.d = Collections.unmodifiableSet(linkedHashSet);
    }

    /* access modifiers changed from: protected */
    public final f c(String str) {
        if (this.d.contains(str)) {
            return new f(TimeZone.getTimeZone(str));
        }
        throw new g("Not a built-in time zone: " + str);
    }

    /* access modifiers changed from: protected */
    public final Set d() {
        return this.d;
    }
}
