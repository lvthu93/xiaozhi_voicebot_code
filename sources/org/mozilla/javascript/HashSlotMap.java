package org.mozilla.javascript;

import java.util.Iterator;
import java.util.LinkedHashMap;
import org.mozilla.javascript.ScriptableObject;

public class HashSlotMap implements SlotMap {
    private final LinkedHashMap<Object, ScriptableObject.Slot> map = new LinkedHashMap<>();

    /* renamed from: org.mozilla.javascript.HashSlotMap$1  reason: invalid class name */
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
            throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.HashSlotMap.AnonymousClass1.<clinit>():void");
        }
    }

    private ScriptableObject.Slot createSlot(Object obj, int i, Object obj2, ScriptableObject.SlotAccess slotAccess) {
        ScriptableObject.Slot slot;
        ScriptableObject.Slot slot2;
        ScriptableObject.Slot slot3 = this.map.get(obj2);
        if (slot3 != null) {
            if (slotAccess == ScriptableObject.SlotAccess.MODIFY_GETTER_SETTER && !(slot3 instanceof ScriptableObject.GetterSlot)) {
                slot2 = new ScriptableObject.GetterSlot(obj2, slot3.indexOrHash, slot3.getAttributes());
            } else if (slotAccess == ScriptableObject.SlotAccess.CONVERT_ACCESSOR_TO_DATA && (slot3 instanceof ScriptableObject.GetterSlot)) {
                slot2 = new ScriptableObject.Slot(obj2, slot3.indexOrHash, slot3.getAttributes());
            } else if (slotAccess == ScriptableObject.SlotAccess.MODIFY_CONST) {
                return null;
            } else {
                return slot3;
            }
            slot2.value = slot3.value;
            this.map.put(obj2, slot2);
            return slot2;
        }
        if (slotAccess == ScriptableObject.SlotAccess.MODIFY_GETTER_SETTER) {
            slot = new ScriptableObject.GetterSlot(obj, i, 0);
        } else {
            slot = new ScriptableObject.Slot(obj, i, 0);
        }
        if (slotAccess == ScriptableObject.SlotAccess.MODIFY_CONST) {
            slot.setAttributes(13);
        }
        addSlot(slot);
        return slot;
    }

    public void addSlot(ScriptableObject.Slot slot) {
        Object obj = slot.name;
        if (obj == null) {
            obj = String.valueOf(slot.indexOrHash);
        }
        this.map.put(obj, slot);
    }

    public ScriptableObject.Slot get(Object obj, int i, ScriptableObject.SlotAccess slotAccess) {
        Object obj2;
        if (obj == null) {
            obj2 = String.valueOf(i);
        } else {
            obj2 = obj;
        }
        ScriptableObject.Slot slot = this.map.get(obj2);
        int i2 = AnonymousClass1.$SwitchMap$org$mozilla$javascript$ScriptableObject$SlotAccess[slotAccess.ordinal()];
        if (i2 == 1) {
            return slot;
        }
        if (i2 == 2 || i2 == 3) {
            if (slot != null) {
                return slot;
            }
        } else if (i2 != 4) {
            if (i2 == 5 && !(slot instanceof ScriptableObject.GetterSlot)) {
                return slot;
            }
        } else if (slot instanceof ScriptableObject.GetterSlot) {
            return slot;
        }
        return createSlot(obj, i, obj2, slotAccess);
    }

    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    public Iterator<ScriptableObject.Slot> iterator() {
        return this.map.values().iterator();
    }

    public ScriptableObject.Slot query(Object obj, int i) {
        if (obj == null) {
            obj = String.valueOf(i);
        }
        return this.map.get(obj);
    }

    public void remove(Object obj, int i) {
        Object obj2;
        if (obj == null) {
            obj2 = String.valueOf(i);
        } else {
            obj2 = obj;
        }
        ScriptableObject.Slot slot = this.map.get(obj2);
        if (slot == null) {
            return;
        }
        if ((slot.getAttributes() & 4) == 0) {
            this.map.remove(obj2);
        } else if (Context.getContext().isStrictMode()) {
            throw ScriptRuntime.typeError1("msg.delete.property.with.configurable.false", obj);
        }
    }

    public int size() {
        return this.map.size();
    }
}
