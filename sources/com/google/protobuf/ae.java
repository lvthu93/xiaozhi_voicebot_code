package com.google.protobuf;

import com.google.protobuf.af;
import com.google.protobuf.l;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class ae extends af<l.a<Object>, Object> {
    public final void f() {
        if (!this.h) {
            for (int i = 0; i < this.f; i++) {
                af.a c = c(i);
                if (((l.a) c.c).c()) {
                    c.setValue(Collections.unmodifiableList((List) c.f));
                }
            }
            for (Map.Entry entry : d()) {
                if (((l.a) entry.getKey()).c()) {
                    entry.setValue(Collections.unmodifiableList((List) entry.getValue()));
                }
            }
        }
        super.f();
    }

    public final /* bridge */ /* synthetic */ Object put(Object obj, Object obj2) {
        return put((Comparable) obj, obj2);
    }
}
