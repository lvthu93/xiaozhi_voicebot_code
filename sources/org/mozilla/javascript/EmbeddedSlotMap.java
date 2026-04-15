package org.mozilla.javascript;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.mozilla.javascript.ScriptableObject;

public class EmbeddedSlotMap implements SlotMap {
    private static final int INITIAL_SLOT_SIZE = 4;
    private int count;
    private ScriptableObject.Slot firstAdded;
    private ScriptableObject.Slot lastAdded;
    private ScriptableObject.Slot[] slots;

    /* renamed from: org.mozilla.javascript.EmbeddedSlotMap$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$mozilla$javascript$ScriptableObject$SlotAccess;

        /* JADX WARNING: Can't wrap try/catch for region: R(12:0|1|2|3|4|5|6|7|8|9|10|12) */
        /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0028 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0033 */
        static {
            /*
                org.mozilla.javascript.ScriptableObject$SlotAccess[] r0 = org.mozilla.javascript.ScriptableObject.SlotAccess.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$org$mozilla$javascript$ScriptableObject$SlotAccess = r0
                org.mozilla.javascript.ScriptableObject$SlotAccess r1 = org.mozilla.javascript.ScriptableObject.SlotAccess.QUERY     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$org$mozilla$javascript$ScriptableObject$SlotAccess     // Catch:{ NoSuchFieldError -> 0x001d }
                org.mozilla.javascript.ScriptableObject$SlotAccess r1 = org.mozilla.javascript.ScriptableObject.SlotAccess.MODIFY     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$org$mozilla$javascript$ScriptableObject$SlotAccess     // Catch:{ NoSuchFieldError -> 0x0028 }
                org.mozilla.javascript.ScriptableObject$SlotAccess r1 = org.mozilla.javascript.ScriptableObject.SlotAccess.MODIFY_CONST     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r0 = $SwitchMap$org$mozilla$javascript$ScriptableObject$SlotAccess     // Catch:{ NoSuchFieldError -> 0x0033 }
                org.mozilla.javascript.ScriptableObject$SlotAccess r1 = org.mozilla.javascript.ScriptableObject.SlotAccess.MODIFY_GETTER_SETTER     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                int[] r0 = $SwitchMap$org$mozilla$javascript$ScriptableObject$SlotAccess     // Catch:{ NoSuchFieldError -> 0x003e }
                org.mozilla.javascript.ScriptableObject$SlotAccess r1 = org.mozilla.javascript.ScriptableObject.SlotAccess.CONVERT_ACCESSOR_TO_DATA     // Catch:{ NoSuchFieldError -> 0x003e }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x003e }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x003e }
            L_0x003e:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.EmbeddedSlotMap.AnonymousClass1.<clinit>():void");
        }
    }

    public static final class Iter implements Iterator<ScriptableObject.Slot> {
        private ScriptableObject.Slot next;

        public Iter(ScriptableObject.Slot slot) {
            this.next = slot;
        }

        public boolean hasNext() {
            return this.next != null;
        }

        public ScriptableObject.Slot next() {
            ScriptableObject.Slot slot = this.next;
            if (slot != null) {
                this.next = slot.orderedNext;
                return slot;
            }
            throw new NoSuchElementException();
        }
    }

    private static void addKnownAbsentSlot(ScriptableObject.Slot[] slotArr, ScriptableObject.Slot slot) {
        int slotIndex = getSlotIndex(slotArr.length, slot.indexOrHash);
        ScriptableObject.Slot slot2 = slotArr[slotIndex];
        slotArr[slotIndex] = slot;
        slot.next = slot2;
    }

    private static void copyTable(ScriptableObject.Slot[] slotArr, ScriptableObject.Slot[] slotArr2) {
        for (ScriptableObject.Slot slot : slotArr) {
            while (slot != null) {
                ScriptableObject.Slot slot2 = slot.next;
                slot.next = null;
                addKnownAbsentSlot(slotArr2, slot);
                slot = slot2;
            }
        }
    }

    private ScriptableObject.Slot createSlot(Object obj, int i, ScriptableObject.SlotAccess slotAccess, ScriptableObject.Slot slot) {
        ScriptableObject.Slot slot2;
        ScriptableObject.Slot slot3;
        if (this.count == 0) {
            this.slots = new ScriptableObject.Slot[4];
        } else if (slot != null) {
            int slotIndex = getSlotIndex(this.slots.length, i);
            ScriptableObject.Slot slot4 = this.slots[slotIndex];
            ScriptableObject.Slot slot5 = slot4;
            while (slot4 != null && (slot4.indexOrHash != i || ((r3 = slot4.name) != obj && (obj == null || !obj.equals(r3))))) {
                slot5 = slot4;
                slot4 = slot4.next;
            }
            if (slot4 != null) {
                if (slotAccess == ScriptableObject.SlotAccess.MODIFY_GETTER_SETTER && !(slot4 instanceof ScriptableObject.GetterSlot)) {
                    slot3 = new ScriptableObject.GetterSlot(obj, i, slot4.getAttributes());
                } else if (slotAccess == ScriptableObject.SlotAccess.CONVERT_ACCESSOR_TO_DATA && (slot4 instanceof ScriptableObject.GetterSlot)) {
                    slot3 = new ScriptableObject.Slot(obj, i, slot4.getAttributes());
                } else if (slotAccess == ScriptableObject.SlotAccess.MODIFY_CONST) {
                    return null;
                } else {
                    return slot4;
                }
                slot3.value = slot4.value;
                slot3.next = slot4.next;
                ScriptableObject.Slot slot6 = this.firstAdded;
                if (slot4 == slot6) {
                    this.firstAdded = slot3;
                } else {
                    while (slot6 != null) {
                        ScriptableObject.Slot slot7 = slot6.orderedNext;
                        if (slot7 == slot4) {
                            break;
                        }
                        slot6 = slot7;
                    }
                    if (slot6 != null) {
                        slot6.orderedNext = slot3;
                    }
                }
                slot3.orderedNext = slot4.orderedNext;
                if (slot4 == this.lastAdded) {
                    this.lastAdded = slot3;
                }
                if (slot5 == slot4) {
                    this.slots[slotIndex] = slot3;
                } else {
                    slot5.next = slot3;
                }
                return slot3;
            }
        }
        int i2 = (this.count + 1) * 4;
        ScriptableObject.Slot[] slotArr = this.slots;
        if (i2 > slotArr.length * 3) {
            ScriptableObject.Slot[] slotArr2 = new ScriptableObject.Slot[(slotArr.length * 2)];
            copyTable(slotArr, slotArr2);
            this.slots = slotArr2;
        }
        if (slotAccess == ScriptableObject.SlotAccess.MODIFY_GETTER_SETTER) {
            slot2 = new ScriptableObject.GetterSlot(obj, i, 0);
        } else {
            slot2 = new ScriptableObject.Slot(obj, i, 0);
        }
        if (slotAccess == ScriptableObject.SlotAccess.MODIFY_CONST) {
            slot2.setAttributes(13);
        }
        insertNewSlot(slot2);
        return slot2;
    }

    private static int getSlotIndex(int i, int i2) {
        return (i - 1) & i2;
    }

    private void insertNewSlot(ScriptableObject.Slot slot) {
        this.count++;
        ScriptableObject.Slot slot2 = this.lastAdded;
        if (slot2 != null) {
            slot2.orderedNext = slot;
        }
        if (this.firstAdded == null) {
            this.firstAdded = slot;
        }
        this.lastAdded = slot;
        addKnownAbsentSlot(this.slots, slot);
    }

    public void addSlot(ScriptableObject.Slot slot) {
        if (this.slots == null) {
            this.slots = new ScriptableObject.Slot[4];
        }
        insertNewSlot(slot);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0056, code lost:
        if (r1 != null) goto L_0x0058;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.mozilla.javascript.ScriptableObject.Slot get(java.lang.Object r4, int r5, org.mozilla.javascript.ScriptableObject.SlotAccess r6) {
        /*
            r3 = this;
            org.mozilla.javascript.ScriptableObject$Slot[] r0 = r3.slots
            r1 = 0
            if (r0 != 0) goto L_0x000a
            org.mozilla.javascript.ScriptableObject$SlotAccess r0 = org.mozilla.javascript.ScriptableObject.SlotAccess.QUERY
            if (r6 != r0) goto L_0x000a
            return r1
        L_0x000a:
            if (r4 == 0) goto L_0x0010
            int r5 = r4.hashCode()
        L_0x0010:
            org.mozilla.javascript.ScriptableObject$Slot[] r0 = r3.slots
            if (r0 == 0) goto L_0x0059
            int r0 = r0.length
            int r0 = getSlotIndex(r0, r5)
            org.mozilla.javascript.ScriptableObject$Slot[] r1 = r3.slots
            r0 = r1[r0]
            r1 = r0
        L_0x001e:
            if (r1 == 0) goto L_0x0034
            java.lang.Object r0 = r1.name
            int r2 = r1.indexOrHash
            if (r5 != r2) goto L_0x0031
            if (r0 == r4) goto L_0x0034
            if (r4 == 0) goto L_0x0031
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0031
            goto L_0x0034
        L_0x0031:
            org.mozilla.javascript.ScriptableObject$Slot r1 = r1.next
            goto L_0x001e
        L_0x0034:
            int[] r0 = org.mozilla.javascript.EmbeddedSlotMap.AnonymousClass1.$SwitchMap$org$mozilla$javascript$ScriptableObject$SlotAccess
            int r2 = r6.ordinal()
            r0 = r0[r2]
            r2 = 1
            if (r0 == r2) goto L_0x0058
            r2 = 2
            if (r0 == r2) goto L_0x0056
            r2 = 3
            if (r0 == r2) goto L_0x0056
            r2 = 4
            if (r0 == r2) goto L_0x0051
            r2 = 5
            if (r0 == r2) goto L_0x004c
            goto L_0x0059
        L_0x004c:
            boolean r0 = r1 instanceof org.mozilla.javascript.ScriptableObject.GetterSlot
            if (r0 != 0) goto L_0x0059
            return r1
        L_0x0051:
            boolean r0 = r1 instanceof org.mozilla.javascript.ScriptableObject.GetterSlot
            if (r0 == 0) goto L_0x0059
            return r1
        L_0x0056:
            if (r1 == 0) goto L_0x0059
        L_0x0058:
            return r1
        L_0x0059:
            org.mozilla.javascript.ScriptableObject$Slot r4 = r3.createSlot(r4, r5, r6, r1)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.EmbeddedSlotMap.get(java.lang.Object, int, org.mozilla.javascript.ScriptableObject$SlotAccess):org.mozilla.javascript.ScriptableObject$Slot");
    }

    public boolean isEmpty() {
        return this.count == 0;
    }

    public Iterator<ScriptableObject.Slot> iterator() {
        return new Iter(this.firstAdded);
    }

    public ScriptableObject.Slot query(Object obj, int i) {
        if (this.slots == null) {
            return null;
        }
        if (obj != null) {
            i = obj.hashCode();
        }
        for (ScriptableObject.Slot slot = this.slots[getSlotIndex(this.slots.length, i)]; slot != null; slot = slot.next) {
            Object obj2 = slot.name;
            if (i == slot.indexOrHash && (obj2 == obj || (obj != null && obj.equals(obj2)))) {
                return slot;
            }
        }
        return null;
    }

    public void remove(Object obj, int i) {
        if (obj != null) {
            i = obj.hashCode();
        }
        if (this.count != 0) {
            int slotIndex = getSlotIndex(this.slots.length, i);
            ScriptableObject.Slot slot = this.slots[slotIndex];
            ScriptableObject.Slot slot2 = slot;
            while (slot != null && (slot.indexOrHash != i || ((r3 = slot.name) != obj && (obj == null || !obj.equals(r3))))) {
                slot2 = slot;
                slot = slot.next;
            }
            if (slot == null) {
                return;
            }
            if ((slot.getAttributes() & 4) == 0) {
                this.count--;
                if (slot2 == slot) {
                    this.slots[slotIndex] = slot.next;
                } else {
                    slot2.next = slot.next;
                }
                ScriptableObject.Slot slot3 = this.firstAdded;
                if (slot == slot3) {
                    this.firstAdded = slot.orderedNext;
                    slot3 = null;
                } else {
                    while (true) {
                        ScriptableObject.Slot slot4 = slot3.orderedNext;
                        if (slot4 == slot) {
                            break;
                        }
                        slot3 = slot4;
                    }
                    slot3.orderedNext = slot.orderedNext;
                }
                if (slot == this.lastAdded) {
                    this.lastAdded = slot3;
                }
            } else if (Context.getContext().isStrictMode()) {
                throw ScriptRuntime.typeError1("msg.delete.property.with.configurable.false", obj);
            }
        }
    }

    public int size() {
        return this.count;
    }
}
