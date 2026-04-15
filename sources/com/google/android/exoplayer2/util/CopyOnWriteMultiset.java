package com.google.android.exoplayer2.util;

import androidx.annotation.GuardedBy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class CopyOnWriteMultiset<E> implements Iterable<E> {
    public final Object c = new Object();
    @GuardedBy("lock")
    public final HashMap f = new HashMap();
    @GuardedBy("lock")
    public Set<E> g = Collections.emptySet();
    @GuardedBy("lock")
    public List<E> h = Collections.emptyList();

    public void add(E e) {
        synchronized (this.c) {
            ArrayList arrayList = new ArrayList(this.h);
            arrayList.add(e);
            this.h = Collections.unmodifiableList(arrayList);
            Integer num = (Integer) this.f.get(e);
            if (num == null) {
                HashSet hashSet = new HashSet(this.g);
                hashSet.add(e);
                this.g = Collections.unmodifiableSet(hashSet);
            }
            HashMap hashMap = this.f;
            int i = 1;
            if (num != null) {
                i = 1 + num.intValue();
            }
            hashMap.put(e, Integer.valueOf(i));
        }
    }

    public int count(E e) {
        int i;
        synchronized (this.c) {
            if (this.f.containsKey(e)) {
                i = ((Integer) this.f.get(e)).intValue();
            } else {
                i = 0;
            }
        }
        return i;
    }

    public Set<E> elementSet() {
        Set<E> set;
        synchronized (this.c) {
            set = this.g;
        }
        return set;
    }

    public Iterator<E> iterator() {
        Iterator<E> it;
        synchronized (this.c) {
            it = this.h.iterator();
        }
        return it;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x004b, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void remove(E r5) {
        /*
            r4 = this;
            java.lang.Object r0 = r4.c
            monitor-enter(r0)
            java.util.HashMap r1 = r4.f     // Catch:{ all -> 0x004c }
            java.lang.Object r1 = r1.get(r5)     // Catch:{ all -> 0x004c }
            java.lang.Integer r1 = (java.lang.Integer) r1     // Catch:{ all -> 0x004c }
            if (r1 != 0) goto L_0x000f
            monitor-exit(r0)     // Catch:{ all -> 0x004c }
            return
        L_0x000f:
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch:{ all -> 0x004c }
            java.util.List<E> r3 = r4.h     // Catch:{ all -> 0x004c }
            r2.<init>(r3)     // Catch:{ all -> 0x004c }
            r2.remove(r5)     // Catch:{ all -> 0x004c }
            java.util.List r2 = java.util.Collections.unmodifiableList(r2)     // Catch:{ all -> 0x004c }
            r4.h = r2     // Catch:{ all -> 0x004c }
            int r2 = r1.intValue()     // Catch:{ all -> 0x004c }
            r3 = 1
            if (r2 != r3) goto L_0x003c
            java.util.HashMap r1 = r4.f     // Catch:{ all -> 0x004c }
            r1.remove(r5)     // Catch:{ all -> 0x004c }
            java.util.HashSet r1 = new java.util.HashSet     // Catch:{ all -> 0x004c }
            java.util.Set<E> r2 = r4.g     // Catch:{ all -> 0x004c }
            r1.<init>(r2)     // Catch:{ all -> 0x004c }
            r1.remove(r5)     // Catch:{ all -> 0x004c }
            java.util.Set r5 = java.util.Collections.unmodifiableSet(r1)     // Catch:{ all -> 0x004c }
            r4.g = r5     // Catch:{ all -> 0x004c }
            goto L_0x004a
        L_0x003c:
            java.util.HashMap r2 = r4.f     // Catch:{ all -> 0x004c }
            int r1 = r1.intValue()     // Catch:{ all -> 0x004c }
            int r1 = r1 - r3
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ all -> 0x004c }
            r2.put(r5, r1)     // Catch:{ all -> 0x004c }
        L_0x004a:
            monitor-exit(r0)     // Catch:{ all -> 0x004c }
            return
        L_0x004c:
            r5 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x004c }
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.util.CopyOnWriteMultiset.remove(java.lang.Object):void");
    }
}
