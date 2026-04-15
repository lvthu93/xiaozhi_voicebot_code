package org.schabi.newpipe.extractor.utils;

import androidx.appcompat.widget.ActivityChooserView;
import j$.lang.Iterable$EL;
import j$.util.concurrent.ConcurrentHashMap;
import java.io.Serializable;
import java.util.ArrayList;

public final class ManifestCreatorCache<K extends Serializable, V extends Serializable> implements Serializable {
    public final ConcurrentHashMap<K, Pair<Integer, V>> c = new ConcurrentHashMap<>();
    public int f = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
    public double g = 0.75d;

    public final void a(int i) {
        ConcurrentHashMap<K, Pair<Integer, V>> concurrentHashMap = this.c;
        int size = concurrentHashMap.size() - i;
        ArrayList arrayList = new ArrayList();
        Iterable$EL.forEach(concurrentHashMap.entrySet(), new o4(arrayList, size));
        Iterable$EL.forEach(arrayList, new ce(2, this));
    }

    public void clear() {
        this.c.clear();
    }

    public boolean containsKey(K k) {
        return this.c.containsKey(k);
    }

    public Pair<Integer, V> get(K k) {
        return this.c.get(k);
    }

    public double getClearFactor() {
        return this.g;
    }

    public long getMaximumSize() {
        return (long) this.f;
    }

    public V put(K k, V v) {
        int i;
        ConcurrentHashMap<K, Pair<Integer, V>> concurrentHashMap = this.c;
        if (!concurrentHashMap.containsKey(k) && concurrentHashMap.size() == (i = this.f)) {
            int round = (int) Math.round(((double) i) * this.g);
            if (round == 0) {
                round = 1;
            }
            a(round);
        }
        Pair put = concurrentHashMap.put(k, new Pair(Integer.valueOf(concurrentHashMap.size()), v));
        if (put == null) {
            return null;
        }
        return put.getSecond();
    }

    public void reset() {
        clear();
        resetClearFactor();
        resetMaximumSize();
    }

    public void resetClearFactor() {
        this.g = 0.75d;
    }

    public void resetMaximumSize() {
        this.f = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
    }

    public void setClearFactor(double d) {
        if (d <= 0.0d || d >= 1.0d) {
            throw new IllegalArgumentException("Invalid clear factor");
        }
        this.g = d;
    }

    public void setMaximumSize(int i) {
        if (i > 0) {
            if (i < this.f && !this.c.isEmpty()) {
                int round = (int) Math.round(((double) i) * this.g);
                if (round == 0) {
                    round = 1;
                }
                a(round);
            }
            this.f = i;
            return;
        }
        throw new IllegalArgumentException("Invalid maximum size");
    }

    public int size() {
        return this.c.size();
    }

    public String toString() {
        return "ManifestCreatorCache[clearFactor=" + this.g + ", maximumSize=" + this.f + ", concurrentHashMap=" + this.c + "]";
    }
}
