package org.mozilla.javascript;

import java.util.Iterator;
import java.util.concurrent.locks.StampedLock;
import org.mozilla.javascript.ScriptableObject;

class ThreadSafeSlotMapContainer extends SlotMapContainer {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final StampedLock lock = new StampedLock();

    public ThreadSafeSlotMapContainer(int i) {
        super(i);
    }

    public void addSlot(ScriptableObject.Slot slot) {
        long a = this.lock.writeLock();
        try {
            checkMapSize();
            this.map.addSlot(slot);
        } finally {
            this.lock.unlockWrite(a);
        }
    }

    public void checkMapSize() {
        super.checkMapSize();
    }

    public int dirtySize() {
        return this.map.size();
    }

    public ScriptableObject.Slot get(Object obj, int i, ScriptableObject.SlotAccess slotAccess) {
        long a = this.lock.writeLock();
        try {
            if (slotAccess != ScriptableObject.SlotAccess.QUERY) {
                checkMapSize();
            }
            return this.map.get(obj, i, slotAccess);
        } finally {
            this.lock.unlockWrite(a);
        }
    }

    public boolean isEmpty() {
        long u = this.lock.tryOptimisticRead();
        boolean isEmpty = this.map.isEmpty();
        if (this.lock.validate(u)) {
            return isEmpty;
        }
        long y = this.lock.readLock();
        try {
            return this.map.isEmpty();
        } finally {
            this.lock.unlockRead(y);
        }
    }

    public Iterator<ScriptableObject.Slot> iterator() {
        return this.map.iterator();
    }

    public ScriptableObject.Slot query(Object obj, int i) {
        long u = this.lock.tryOptimisticRead();
        ScriptableObject.Slot query = this.map.query(obj, i);
        if (this.lock.validate(u)) {
            return query;
        }
        long y = this.lock.readLock();
        try {
            return this.map.query(obj, i);
        } finally {
            this.lock.unlockRead(y);
        }
    }

    public long readLock() {
        return this.lock.readLock();
    }

    public void remove(Object obj, int i) {
        long a = this.lock.writeLock();
        try {
            this.map.remove(obj, i);
        } finally {
            this.lock.unlockWrite(a);
        }
    }

    public int size() {
        long u = this.lock.tryOptimisticRead();
        int size = this.map.size();
        if (this.lock.validate(u)) {
            return size;
        }
        long y = this.lock.readLock();
        try {
            return this.map.size();
        } finally {
            this.lock.unlockRead(y);
        }
    }

    public void unlockRead(long j) {
        this.lock.unlockRead(j);
    }
}
