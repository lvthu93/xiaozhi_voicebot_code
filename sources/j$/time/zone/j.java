package j$.time.zone;

import j$.util.Objects;
import j$.util.concurrent.ConcurrentHashMap;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class j {
    private static final CopyOnWriteArrayList a;
    private static final ConcurrentHashMap b = new ConcurrentHashMap(512, 0.75f, 2);
    private static volatile Set c;

    static {
        CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();
        a = copyOnWriteArrayList;
        ArrayList arrayList = new ArrayList();
        AccessController.doPrivileged(new h(arrayList));
        copyOnWriteArrayList.addAll(arrayList);
    }

    protected j() {
    }

    public static Set a() {
        return c;
    }

    public static f b(String str, boolean z) {
        Objects.requireNonNull(str, "zoneId");
        ConcurrentHashMap concurrentHashMap = b;
        j jVar = (j) concurrentHashMap.get(str);
        if (jVar != null) {
            return jVar.c(str);
        }
        if (concurrentHashMap.isEmpty()) {
            throw new g("No time-zone data files registered");
        }
        throw new g("Unknown time-zone ID: " + str);
    }

    public static void e(j jVar) {
        Objects.requireNonNull(jVar, "provider");
        synchronized (j.class) {
            for (String str : jVar.d()) {
                Objects.requireNonNull(str, "zoneId");
                if (((j) b.putIfAbsent(str, jVar)) != null) {
                    throw new g("Unable to register zone as one already registered with that ID: " + str + ", currently loading from provider: " + jVar);
                }
            }
            c = Collections.unmodifiableSet(new HashSet(b.keySet()));
        }
        a.add(jVar);
    }

    /* access modifiers changed from: protected */
    public abstract f c(String str);

    /* access modifiers changed from: protected */
    public abstract Set d();
}
