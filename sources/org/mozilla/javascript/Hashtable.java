package org.mozilla.javascript;

import j$.util.Iterator;
import j$.util.Map;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Hashtable implements Serializable, Iterable<Entry> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long serialVersionUID = -7151554912419543747L;
    private Entry first = null;
    private Entry last = null;
    private final HashMap<Object, Entry> map = new HashMap<>();

    public static final class Iter implements Iterator<Entry> {
        private Entry pos;

        public Iter(Entry entry) {
            Entry access$000 = Hashtable.makeDummy();
            access$000.next = entry;
            this.pos = access$000;
        }

        private void skipDeleted() {
            while (true) {
                Entry entry = this.pos.next;
                if (entry != null && entry.deleted) {
                    this.pos = entry;
                } else {
                    return;
                }
            }
        }

        public boolean hasNext() {
            skipDeleted();
            Entry entry = this.pos;
            if (entry == null || entry.next == null) {
                return false;
            }
            return true;
        }

        public Entry next() {
            Entry entry;
            skipDeleted();
            Entry entry2 = this.pos;
            if (entry2 == null || (entry = entry2.next) == null) {
                throw new NoSuchElementException();
            }
            this.pos = entry;
            return entry;
        }
    }

    /* access modifiers changed from: private */
    public static Entry makeDummy() {
        Entry entry = new Entry();
        entry.clear();
        return entry;
    }

    public void clear() {
        Iterator.EL.forEachRemaining(iterator(), new q3());
        if (this.first != null) {
            Entry makeDummy = makeDummy();
            this.last.next = makeDummy;
            this.last = makeDummy;
            this.first = makeDummy;
        }
        this.map.clear();
    }

    public Object delete(Object obj) {
        Entry remove = this.map.remove(new Entry(obj, (Object) null));
        if (remove == null) {
            return null;
        }
        if (remove != this.first) {
            Entry entry = remove.prev;
            entry.next = remove.next;
            remove.prev = null;
            Entry entry2 = remove.next;
            if (entry2 != null) {
                entry2.prev = entry;
            } else {
                this.last = entry;
            }
        } else if (remove == this.last) {
            remove.clear();
            remove.prev = null;
        } else {
            Entry entry3 = remove.next;
            this.first = entry3;
            entry3.prev = null;
            Entry entry4 = entry3.next;
            if (entry4 != null) {
                entry4.prev = entry3;
            }
        }
        return remove.clear();
    }

    public Object get(Object obj) {
        Entry entry = this.map.get(new Entry(obj, (Object) null));
        if (entry == null) {
            return null;
        }
        return entry.value;
    }

    public boolean has(Object obj) {
        return this.map.containsKey(new Entry(obj, (Object) null));
    }

    public java.util.Iterator<Entry> iterator() {
        return new Iter(this.first);
    }

    public void put(Object obj, Object obj2) {
        Entry entry = new Entry(obj, obj2);
        Entry entry2 = (Entry) Map.EL.putIfAbsent(this.map, entry, entry);
        if (entry2 != null) {
            entry2.value = obj2;
        } else if (this.first == null) {
            this.last = entry;
            this.first = entry;
        } else {
            Entry entry3 = this.last;
            entry3.next = entry;
            entry.prev = entry3;
            this.last = entry;
        }
    }

    public int size() {
        return this.map.size();
    }

    public static final class Entry implements Serializable {
        private static final long serialVersionUID = 4086572107122965503L;
        protected boolean deleted;
        private final int hashCode;
        protected Object key;
        protected Entry next;
        protected Entry prev;
        protected Object value;

        public Entry() {
            this.hashCode = 0;
        }

        public Object clear() {
            Object obj = this.value;
            Object obj2 = Undefined.instance;
            this.key = obj2;
            this.value = obj2;
            this.deleted = true;
            return obj;
        }

        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            try {
                return ScriptRuntime.sameZero(this.key, ((Entry) obj).key);
            } catch (ClassCastException unused) {
                return false;
            }
        }

        public int hashCode() {
            return this.hashCode;
        }

        public Object key() {
            return this.key;
        }

        public Object value() {
            return this.value;
        }

        public Entry(Object obj, Object obj2) {
            if ((obj instanceof Number) && !(obj instanceof Double)) {
                this.key = Double.valueOf(((Number) obj).doubleValue());
            } else if (obj instanceof ConsString) {
                this.key = obj.toString();
            } else {
                this.key = obj;
            }
            if (this.key == null) {
                this.hashCode = 0;
            } else if (obj.equals(ScriptRuntime.negativeZeroObj)) {
                this.hashCode = 0;
            } else {
                this.hashCode = this.key.hashCode();
            }
            this.value = obj2;
        }
    }
}
