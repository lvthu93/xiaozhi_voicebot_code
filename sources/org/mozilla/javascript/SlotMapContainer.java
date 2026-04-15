package org.mozilla.javascript;

import java.util.Iterator;
import org.mozilla.javascript.ScriptableObject;

class SlotMapContainer implements SlotMap {
    private static final int LARGE_HASH_SIZE = 2000;
    protected SlotMap map;

    public SlotMapContainer(int i) {
        if (i > LARGE_HASH_SIZE) {
            this.map = new HashSlotMap();
        } else {
            this.map = new EmbeddedSlotMap();
        }
    }

    public void addSlot(ScriptableObject.Slot slot) {
        checkMapSize();
        this.map.addSlot(slot);
    }

    public void checkMapSize() {
        SlotMap slotMap = this.map;
        if ((slotMap instanceof EmbeddedSlotMap) && slotMap.size() >= LARGE_HASH_SIZE) {
            HashSlotMap hashSlotMap = new HashSlotMap();
            for (ScriptableObject.Slot addSlot : this.map) {
                hashSlotMap.addSlot(addSlot);
            }
            this.map = hashSlotMap;
        }
    }

    public int dirtySize() {
        return this.map.size();
    }

    public ScriptableObject.Slot get(Object obj, int i, ScriptableObject.SlotAccess slotAccess) {
        if (slotAccess != ScriptableObject.SlotAccess.QUERY) {
            checkMapSize();
        }
        return this.map.get(obj, i, slotAccess);
    }

    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    public Iterator<ScriptableObject.Slot> iterator() {
        return this.map.iterator();
    }

    public ScriptableObject.Slot query(Object obj, int i) {
        return this.map.query(obj, i);
    }

    public long readLock() {
        return 0;
    }

    public void remove(Object obj, int i) {
        this.map.remove(obj, i);
    }

    public int size() {
        return this.map.size();
    }

    public void unlockRead(long j) {
    }
}
