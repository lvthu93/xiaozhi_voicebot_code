package org.mozilla.javascript;

import java.lang.reflect.Array;

public class NativeJavaArray extends NativeJavaObject {
    private static final long serialVersionUID = -924022554283675333L;
    Object array;
    Class<?> cls;
    int length;

    public NativeJavaArray(Scriptable scriptable, Object obj) {
        super(scriptable, (Object) null, ScriptRuntime.ObjectClass);
        Class<?> cls2 = obj.getClass();
        if (cls2.isArray()) {
            this.array = obj;
            this.length = Array.getLength(obj);
            this.cls = cls2.getComponentType();
            return;
        }
        throw new RuntimeException("Array expected");
    }

    public static NativeJavaArray wrap(Scriptable scriptable, Object obj) {
        return new NativeJavaArray(scriptable, obj);
    }

    public void delete(Symbol symbol) {
    }

    public Object get(String str, Scriptable scriptable) {
        if (str.equals("length")) {
            return Integer.valueOf(this.length);
        }
        Object obj = super.get(str, scriptable);
        if (obj != Scriptable.NOT_FOUND || ScriptableObject.hasProperty(getPrototype(), str)) {
            return obj;
        }
        throw Context.reportRuntimeError2("msg.java.member.not.found", this.array.getClass().getName(), str);
    }

    public String getClassName() {
        return "JavaArray";
    }

    public Object getDefaultValue(Class<?> cls2) {
        if (cls2 == null || cls2 == ScriptRuntime.StringClass) {
            return this.array.toString();
        }
        if (cls2 == ScriptRuntime.BooleanClass) {
            return Boolean.TRUE;
        }
        if (cls2 == ScriptRuntime.NumberClass) {
            return ScriptRuntime.NaNobj;
        }
        return this;
    }

    public Object[] getIds() {
        int i = this.length;
        Object[] objArr = new Object[i];
        while (true) {
            i--;
            if (i < 0) {
                return objArr;
            }
            objArr[i] = Integer.valueOf(i);
        }
    }

    public Scriptable getPrototype() {
        if (this.prototype == null) {
            this.prototype = ScriptableObject.getArrayPrototype(getParentScope());
        }
        return this.prototype;
    }

    public boolean has(String str, Scriptable scriptable) {
        return str.equals("length") || super.has(str, scriptable);
    }

    public boolean hasInstance(Scriptable scriptable) {
        if (!(scriptable instanceof Wrapper)) {
            return false;
        }
        return this.cls.isInstance(((Wrapper) scriptable).unwrap());
    }

    public void put(String str, Scriptable scriptable, Object obj) {
        if (!str.equals("length")) {
            throw Context.reportRuntimeError1("msg.java.array.member.not.found", str);
        }
    }

    public Object unwrap() {
        return this.array;
    }

    public boolean has(int i, Scriptable scriptable) {
        return i >= 0 && i < this.length;
    }

    public boolean has(Symbol symbol, Scriptable scriptable) {
        return SymbolKey.IS_CONCAT_SPREADABLE.equals(symbol);
    }

    public void put(int i, Scriptable scriptable, Object obj) {
        if (i < 0 || i >= this.length) {
            throw Context.reportRuntimeError2("msg.java.array.index.out.of.bounds", String.valueOf(i), String.valueOf(this.length - 1));
        }
        Array.set(this.array, i, Context.jsToJava(obj, this.cls));
    }

    public Object get(int i, Scriptable scriptable) {
        if (i < 0 || i >= this.length) {
            return Undefined.instance;
        }
        Context context = Context.getContext();
        return context.getWrapFactory().wrap(context, this, Array.get(this.array, i), this.cls);
    }

    public Object get(Symbol symbol, Scriptable scriptable) {
        if (SymbolKey.IS_CONCAT_SPREADABLE.equals(symbol)) {
            return Boolean.TRUE;
        }
        return Scriptable.NOT_FOUND;
    }
}
