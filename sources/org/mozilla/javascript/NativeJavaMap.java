package org.mozilla.javascript;

import java.util.ArrayList;
import java.util.Map;

public class NativeJavaMap extends NativeJavaObject {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private Map<Object, Object> map;

    public NativeJavaMap(Scriptable scriptable, Object obj) {
        super(scriptable, obj, obj.getClass());
        this.map = (Map) obj;
    }

    public Object get(String str, Scriptable scriptable) {
        if (!this.map.containsKey(str)) {
            return super.get(str, scriptable);
        }
        Context context = Context.getContext();
        Object obj = this.map.get(str);
        return context.getWrapFactory().wrap(context, this, obj, obj.getClass());
    }

    public String getClassName() {
        return "JavaMap";
    }

    public Object[] getIds() {
        ArrayList arrayList = new ArrayList(this.map.size());
        for (Object next : this.map.keySet()) {
            if (next instanceof Integer) {
                arrayList.add((Integer) next);
            } else {
                arrayList.add(ScriptRuntime.toString(next));
            }
        }
        return arrayList.toArray();
    }

    public boolean has(String str, Scriptable scriptable) {
        if (this.map.containsKey(str)) {
            return true;
        }
        return super.has(str, scriptable);
    }

    public void put(String str, Scriptable scriptable, Object obj) {
        this.map.put(str, Context.jsToJava(obj, Object.class));
    }

    public void put(int i, Scriptable scriptable, Object obj) {
        this.map.put(Integer.valueOf(i), Context.jsToJava(obj, Object.class));
    }

    public boolean has(int i, Scriptable scriptable) {
        if (this.map.containsKey(Integer.valueOf(i))) {
            return true;
        }
        return super.has(i, scriptable);
    }

    public Object get(int i, Scriptable scriptable) {
        if (!this.map.containsKey(Integer.valueOf(i))) {
            return super.get(i, scriptable);
        }
        Context context = Context.getContext();
        Object obj = this.map.get(Integer.valueOf(i));
        return context.getWrapFactory().wrap(context, this, obj, obj.getClass());
    }
}
