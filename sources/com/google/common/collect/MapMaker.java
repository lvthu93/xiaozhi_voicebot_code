package com.google.common.collect;

import com.google.common.base.Ascii;
import com.google.common.base.Equivalence;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.MapMakerInternalMap;
import j$.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class MapMaker {
    public boolean a;
    public int b = -1;
    public int c = -1;
    public MapMakerInternalMap.Strength d;
    public MapMakerInternalMap.Strength e;
    public Equivalence<Object> f;

    public enum Dummy {
        ;

        /* access modifiers changed from: public */
        Dummy() {
        }
    }

    public final MapMakerInternalMap.Strength a() {
        return (MapMakerInternalMap.Strength) MoreObjects.firstNonNull(this.d, MapMakerInternalMap.Strength.c);
    }

    public final MapMakerInternalMap.Strength b() {
        return (MapMakerInternalMap.Strength) MoreObjects.firstNonNull(this.e, MapMakerInternalMap.Strength.c);
    }

    public MapMaker concurrencyLevel(int i) {
        boolean z;
        int i2 = this.c;
        boolean z2 = true;
        if (i2 == -1) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkState(z, "concurrency level was already set to %s", i2);
        if (i <= 0) {
            z2 = false;
        }
        Preconditions.checkArgument(z2);
        this.c = i;
        return this;
    }

    public MapMaker initialCapacity(int i) {
        boolean z;
        int i2 = this.b;
        boolean z2 = true;
        if (i2 == -1) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkState(z, "initial capacity was already set to %s", i2);
        if (i < 0) {
            z2 = false;
        }
        Preconditions.checkArgument(z2);
        this.b = i;
        return this;
    }

    public <K, V> ConcurrentMap<K, V> makeMap() {
        if (!this.a) {
            int i = this.b;
            if (i == -1) {
                i = 16;
            }
            int i2 = this.c;
            if (i2 == -1) {
                i2 = 4;
            }
            return new ConcurrentHashMap(i, 0.75f, i2);
        }
        MapMakerInternalMap.AnonymousClass1 r0 = MapMakerInternalMap.n;
        MapMakerInternalMap.Strength a2 = a();
        MapMakerInternalMap.Strength.AnonymousClass1 r1 = MapMakerInternalMap.Strength.c;
        if (a2 == r1 && b() == r1) {
            return new MapMakerInternalMap(this, MapMakerInternalMap.StrongKeyStrongValueEntry.Helper.a);
        }
        MapMakerInternalMap.Strength a3 = a();
        MapMakerInternalMap.Strength.AnonymousClass2 r2 = MapMakerInternalMap.Strength.f;
        if (a3 == r1 && b() == r2) {
            return new MapMakerInternalMap(this, MapMakerInternalMap.StrongKeyWeakValueEntry.Helper.a);
        }
        if (a() == r2 && b() == r1) {
            return new MapMakerInternalMap(this, MapMakerInternalMap.WeakKeyStrongValueEntry.Helper.a);
        }
        if (a() == r2 && b() == r2) {
            return new MapMakerInternalMap(this, MapMakerInternalMap.WeakKeyWeakValueEntry.Helper.a);
        }
        throw new AssertionError();
    }

    public String toString() {
        MoreObjects.ToStringHelper stringHelper = MoreObjects.toStringHelper((Object) this);
        int i = this.b;
        if (i != -1) {
            stringHelper.add("initialCapacity", i);
        }
        int i2 = this.c;
        if (i2 != -1) {
            stringHelper.add("concurrencyLevel", i2);
        }
        MapMakerInternalMap.Strength strength = this.d;
        if (strength != null) {
            stringHelper.add("keyStrength", (Object) Ascii.toLowerCase(strength.toString()));
        }
        MapMakerInternalMap.Strength strength2 = this.e;
        if (strength2 != null) {
            stringHelper.add("valueStrength", (Object) Ascii.toLowerCase(strength2.toString()));
        }
        if (this.f != null) {
            stringHelper.addValue((Object) "keyEquivalence");
        }
        return stringHelper.toString();
    }

    public MapMaker weakKeys() {
        boolean z;
        MapMakerInternalMap.Strength.AnonymousClass2 r0 = MapMakerInternalMap.Strength.f;
        MapMakerInternalMap.Strength strength = this.d;
        if (strength == null) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkState(z, "Key strength was already set to %s", (Object) strength);
        this.d = (MapMakerInternalMap.Strength) Preconditions.checkNotNull(r0);
        this.a = true;
        return this;
    }

    public MapMaker weakValues() {
        boolean z;
        MapMakerInternalMap.Strength.AnonymousClass2 r0 = MapMakerInternalMap.Strength.f;
        MapMakerInternalMap.Strength strength = this.e;
        if (strength == null) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkState(z, "Value strength was already set to %s", (Object) strength);
        this.e = (MapMakerInternalMap.Strength) Preconditions.checkNotNull(r0);
        this.a = true;
        return this;
    }
}
