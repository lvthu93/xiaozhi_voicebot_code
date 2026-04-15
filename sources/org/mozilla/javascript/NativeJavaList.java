package org.mozilla.javascript;

import java.util.List;

public class NativeJavaList extends NativeJavaObject {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private List<Object> list;

    public NativeJavaList(Scriptable scriptable, Object obj) {
        super(scriptable, obj, obj.getClass());
        this.list = (List) obj;
    }

    private boolean isWithValidIndex(int i) {
        return i >= 0 && i < this.list.size();
    }

    public Object get(String str, Scriptable scriptable) {
        if ("length".equals(str)) {
            return Integer.valueOf(this.list.size());
        }
        return super.get(str, scriptable);
    }

    public String getClassName() {
        return "JavaList";
    }

    public Object[] getIds() {
        List list2 = (List) this.javaObject;
        Object[] objArr = new Object[list2.size()];
        int size = list2.size();
        while (true) {
            size--;
            if (size < 0) {
                return objArr;
            }
            objArr[size] = Integer.valueOf(size);
        }
    }

    public boolean has(String str, Scriptable scriptable) {
        if (str.equals("length")) {
            return true;
        }
        return super.has(str, scriptable);
    }

    public void put(int i, Scriptable scriptable, Object obj) {
        if (isWithValidIndex(i)) {
            this.list.set(i, Context.jsToJava(obj, Object.class));
        } else {
            super.put(i, scriptable, obj);
        }
    }

    public boolean has(int i, Scriptable scriptable) {
        if (isWithValidIndex(i)) {
            return true;
        }
        return super.has(i, scriptable);
    }

    public Object get(int i, Scriptable scriptable) {
        if (!isWithValidIndex(i)) {
            return Undefined.instance;
        }
        Context context = Context.getContext();
        Object obj = this.list.get(i);
        return context.getWrapFactory().wrap(context, this, obj, obj.getClass());
    }

    public boolean has(Symbol symbol, Scriptable scriptable) {
        if (SymbolKey.IS_CONCAT_SPREADABLE.equals(symbol)) {
            return true;
        }
        return super.has(symbol, scriptable);
    }

    public Object get(Symbol symbol, Scriptable scriptable) {
        if (SymbolKey.IS_CONCAT_SPREADABLE.equals(symbol)) {
            return Boolean.TRUE;
        }
        return super.get(symbol, scriptable);
    }
}
