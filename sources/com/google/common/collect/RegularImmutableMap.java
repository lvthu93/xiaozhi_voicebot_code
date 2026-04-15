package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.util.AbstractMap;
import java.util.Map;

final class RegularImmutableMap<K, V> extends ImmutableMap<K, V> {
    public static final ImmutableMap<Object, Object> m = new RegularImmutableMap((int[]) null, new Object[0], 0);
    private static final long serialVersionUID = 0;
    public final transient int[] j;
    public final transient Object[] k;
    public final transient int l;

    public static class EntrySet<K, V> extends ImmutableSet<Map.Entry<K, V>> {
        public final transient ImmutableMap<K, V> g;
        public final transient Object[] h;
        public final transient int i;
        public final transient int j;

        public EntrySet(ImmutableMap<K, V> immutableMap, Object[] objArr, int i2, int i3) {
            this.g = immutableMap;
            this.h = objArr;
            this.i = i2;
            this.j = i3;
        }

        public final int a(int i2, Object[] objArr) {
            return asList().a(i2, objArr);
        }

        public boolean contains(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            Object value = entry.getValue();
            if (value == null || !value.equals(this.g.get(key))) {
                return false;
            }
            return true;
        }

        public final boolean isPartialView() {
            return true;
        }

        public final ImmutableList<Map.Entry<K, V>> k() {
            return new ImmutableList<Map.Entry<K, V>>() {
                public Map.Entry<K, V> get(int i) {
                    EntrySet entrySet = EntrySet.this;
                    Preconditions.checkElementIndex(i, entrySet.j);
                    Object[] objArr = entrySet.h;
                    int i2 = i * 2;
                    int i3 = entrySet.i;
                    return new AbstractMap.SimpleImmutableEntry(objArr[i2 + i3], objArr[i2 + (i3 ^ 1)]);
                }

                public boolean isPartialView() {
                    return true;
                }

                public int size() {
                    return EntrySet.this.j;
                }
            };
        }

        public int size() {
            return this.j;
        }

        public UnmodifiableIterator<Map.Entry<K, V>> iterator() {
            return asList().iterator();
        }
    }

    public static final class KeySet<K> extends ImmutableSet<K> {
        public final transient ImmutableMap<K, ?> g;
        public final transient ImmutableList<K> h;

        public KeySet(ImmutableMap<K, ?> immutableMap, ImmutableList<K> immutableList) {
            this.g = immutableMap;
            this.h = immutableList;
        }

        public final int a(int i, Object[] objArr) {
            return asList().a(i, objArr);
        }

        public ImmutableList<K> asList() {
            return this.h;
        }

        public boolean contains(Object obj) {
            return this.g.get(obj) != null;
        }

        public final boolean isPartialView() {
            return true;
        }

        public int size() {
            return this.g.size();
        }

        public UnmodifiableIterator<K> iterator() {
            return asList().iterator();
        }
    }

    public static final class KeysOrValuesAsList extends ImmutableList<Object> {
        public final transient Object[] g;
        public final transient int h;
        public final transient int i;

        public KeysOrValuesAsList(Object[] objArr, int i2, int i3) {
            this.g = objArr;
            this.h = i2;
            this.i = i3;
        }

        public Object get(int i2) {
            Preconditions.checkElementIndex(i2, this.i);
            return this.g[(i2 * 2) + this.h];
        }

        public final boolean isPartialView() {
            return true;
        }

        public int size() {
            return this.i;
        }
    }

    public RegularImmutableMap(int[] iArr, Object[] objArr, int i) {
        this.j = iArr;
        this.k = objArr;
        this.l = i;
    }

    public static <K, V> RegularImmutableMap<K, V> h(int i, Object[] objArr) {
        if (i == 0) {
            return (RegularImmutableMap) m;
        }
        if (i == 1) {
            CollectPreconditions.a(objArr[0], objArr[1]);
            return new RegularImmutableMap<>((int[]) null, objArr, 1);
        }
        Preconditions.checkPositionIndex(i, objArr.length >> 1);
        return new RegularImmutableMap<>(i(objArr, i, ImmutableSet.g(i), 0), objArr, i);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0034, code lost:
        r12[r7] = r5;
        r3 = r3 + 1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int[] i(java.lang.Object[] r10, int r11, int r12, int r13) {
        /*
            r0 = 1
            if (r11 != r0) goto L_0x000e
            r11 = r10[r13]
            r12 = r13 ^ 1
            r10 = r10[r12]
            com.google.common.collect.CollectPreconditions.a(r11, r10)
            r10 = 0
            return r10
        L_0x000e:
            int r1 = r12 + -1
            int[] r12 = new int[r12]
            r2 = -1
            java.util.Arrays.fill(r12, r2)
            r3 = 0
        L_0x0017:
            if (r3 >= r11) goto L_0x0074
            int r4 = r3 * 2
            int r5 = r4 + r13
            r6 = r10[r5]
            r7 = r13 ^ 1
            int r4 = r4 + r7
            r4 = r10[r4]
            com.google.common.collect.CollectPreconditions.a(r6, r4)
            int r7 = r6.hashCode()
            int r7 = com.google.common.collect.Hashing.b(r7)
        L_0x002f:
            r7 = r7 & r1
            r8 = r12[r7]
            if (r8 != r2) goto L_0x0039
            r12[r7] = r5
            int r3 = r3 + 1
            goto L_0x0017
        L_0x0039:
            r9 = r10[r8]
            boolean r9 = r9.equals(r6)
            if (r9 != 0) goto L_0x0044
            int r7 = r7 + 1
            goto L_0x002f
        L_0x0044:
            java.lang.IllegalArgumentException r11 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            java.lang.String r13 = "Multiple entries with same key: "
            r12.<init>(r13)
            r12.append(r6)
            java.lang.String r13 = "="
            r12.append(r13)
            r12.append(r4)
            java.lang.String r1 = " and "
            r12.append(r1)
            r1 = r10[r8]
            r12.append(r1)
            r12.append(r13)
            r13 = r8 ^ 1
            r10 = r10[r13]
            r12.append(r10)
            java.lang.String r10 = r12.toString()
            r11.<init>(r10)
            throw r11
        L_0x0074:
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.RegularImmutableMap.i(java.lang.Object[], int, int, int):int[]");
    }

    public static Object j(int[] iArr, Object[] objArr, int i, int i2, Object obj) {
        if (obj == null) {
            return null;
        }
        if (i == 1) {
            if (objArr[i2].equals(obj)) {
                return objArr[i2 ^ 1];
            }
            return null;
        } else if (iArr == null) {
            return null;
        } else {
            int length = iArr.length - 1;
            int b = Hashing.b(obj.hashCode());
            while (true) {
                int i3 = b & length;
                int i4 = iArr[i3];
                if (i4 == -1) {
                    return null;
                }
                if (objArr[i4].equals(obj)) {
                    return objArr[i4 ^ 1];
                }
                b = i3 + 1;
            }
        }
    }

    public final ImmutableSet<Map.Entry<K, V>> a() {
        return new EntrySet(this, this.k, 0, this.l);
    }

    public final ImmutableSet<K> b() {
        return new KeySet(this, new KeysOrValuesAsList(this.k, 0, this.l));
    }

    public final ImmutableCollection<V> c() {
        return new KeysOrValuesAsList(this.k, 1, this.l);
    }

    public final boolean f() {
        return false;
    }

    public V get(Object obj) {
        return j(this.j, this.k, this.l, 0, obj);
    }

    public int size() {
        return this.l;
    }
}
