package org.mozilla.javascript;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.WeakHashMap;

public class NativeWeakSet extends IdScriptableObject {
    private static final int Id_add = 2;
    private static final int Id_constructor = 1;
    private static final int Id_delete = 3;
    private static final int Id_has = 4;
    private static final Object MAP_TAG = "WeakSet";
    private static final int MAX_PROTOTYPE_ID = 5;
    private static final int SymbolId_toStringTag = 5;
    private static final long serialVersionUID = 2065753364224029534L;
    private boolean instanceOfWeakSet = false;
    private transient WeakHashMap<Scriptable, Boolean> map = new WeakHashMap<>();

    public static void init(Scriptable scriptable, boolean z) {
        new NativeWeakSet().exportAsJSClass(5, scriptable, z);
    }

    private Object js_add(Object obj) {
        if (ScriptRuntime.isObject(obj)) {
            this.map.put((Scriptable) obj, Boolean.TRUE);
            return this;
        }
        throw ScriptRuntime.typeError1("msg.arg.not.object", ScriptRuntime.typeof(obj));
    }

    private Object js_delete(Object obj) {
        boolean z;
        if (!ScriptRuntime.isObject(obj)) {
            return Boolean.FALSE;
        }
        if (this.map.remove(obj) != null) {
            z = true;
        } else {
            z = false;
        }
        return Boolean.valueOf(z);
    }

    private Object js_has(Object obj) {
        if (!ScriptRuntime.isObject(obj)) {
            return Boolean.FALSE;
        }
        return Boolean.valueOf(this.map.containsKey(obj));
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.map = new WeakHashMap<>();
    }

    private static NativeWeakSet realThis(Scriptable scriptable, IdFunctionObject idFunctionObject) {
        if (scriptable != null) {
            try {
                NativeWeakSet nativeWeakSet = (NativeWeakSet) scriptable;
                if (nativeWeakSet.instanceOfWeakSet) {
                    return nativeWeakSet;
                }
                throw IdScriptableObject.incompatibleCallError(idFunctionObject);
            } catch (ClassCastException unused) {
                throw IdScriptableObject.incompatibleCallError(idFunctionObject);
            }
        } else {
            throw IdScriptableObject.incompatibleCallError(idFunctionObject);
        }
    }

    public Object execIdCall(IdFunctionObject idFunctionObject, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        Object obj;
        Object obj2;
        Object obj3;
        if (!idFunctionObject.hasTag(MAP_TAG)) {
            return super.execIdCall(idFunctionObject, context, scriptable, scriptable2, objArr);
        }
        int methodId = idFunctionObject.methodId();
        if (methodId != 1) {
            if (methodId == 2) {
                NativeWeakSet realThis = realThis(scriptable2, idFunctionObject);
                if (objArr.length > 0) {
                    obj = objArr[0];
                } else {
                    obj = Undefined.instance;
                }
                return realThis.js_add(obj);
            } else if (methodId == 3) {
                NativeWeakSet realThis2 = realThis(scriptable2, idFunctionObject);
                if (objArr.length > 0) {
                    obj2 = objArr[0];
                } else {
                    obj2 = Undefined.instance;
                }
                return realThis2.js_delete(obj2);
            } else if (methodId == 4) {
                NativeWeakSet realThis3 = realThis(scriptable2, idFunctionObject);
                if (objArr.length > 0) {
                    obj3 = objArr[0];
                } else {
                    obj3 = Undefined.instance;
                }
                return realThis3.js_has(obj3);
            } else {
                throw new IllegalArgumentException("WeakMap.prototype has no method: " + idFunctionObject.getFunctionName());
            }
        } else if (scriptable2 == null) {
            NativeWeakSet nativeWeakSet = new NativeWeakSet();
            nativeWeakSet.instanceOfWeakSet = true;
            if (objArr.length > 0) {
                NativeSet.loadFromIterable(context, scriptable, nativeWeakSet, objArr[0]);
            }
            return nativeWeakSet;
        } else {
            throw ScriptRuntime.typeError1("msg.no.new", "WeakSet");
        }
    }

    public int findPrototypeId(Symbol symbol) {
        return SymbolKey.TO_STRING_TAG.equals(symbol) ? 5 : 0;
    }

    public String getClassName() {
        return "WeakSet";
    }

    public void initPrototypeId(int i) {
        int i2;
        String str;
        String str2;
        if (i == 5) {
            initPrototypeValue(5, (Symbol) SymbolKey.TO_STRING_TAG, (Object) getClassName(), 3);
            return;
        }
        if (i != 1) {
            if (i == 2) {
                str2 = "add";
            } else if (i == 3) {
                str2 = "delete";
            } else if (i == 4) {
                str2 = "has";
            } else {
                throw new IllegalArgumentException(String.valueOf(i));
            }
            str = str2;
            i2 = 1;
        } else {
            str = "constructor";
            i2 = 0;
        }
        initPrototypeMethod(MAP_TAG, i, str, (String) null, i2);
    }

    public int findPrototypeId(String str) {
        String str2;
        int length = str.length();
        int i = 1;
        if (length == 3) {
            char charAt = str.charAt(0);
            if (charAt == 'a') {
                if (str.charAt(2) == 'd' && str.charAt(1) == 'd') {
                    return 2;
                }
            } else if (charAt == 'h' && str.charAt(2) == 's' && str.charAt(1) == 'a') {
                return 4;
            }
        } else {
            if (length == 6) {
                str2 = "delete";
                i = 3;
            } else if (length == 11) {
                str2 = "constructor";
            }
            if (str2 != null || str2 == str || str2.equals(str)) {
                return i;
            }
            return 0;
        }
        str2 = null;
        i = 0;
        if (str2 != null) {
        }
        return i;
    }
}
