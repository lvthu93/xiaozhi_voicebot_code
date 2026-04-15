package org.mozilla.javascript;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.WeakHashMap;

public class NativeWeakMap extends IdScriptableObject {
    private static final int Id_constructor = 1;
    private static final int Id_delete = 2;
    private static final int Id_get = 3;
    private static final int Id_has = 4;
    private static final int Id_set = 5;
    private static final Object MAP_TAG = "WeakMap";
    private static final int MAX_PROTOTYPE_ID = 6;
    private static final Object NULL_VALUE = new Object();
    private static final int SymbolId_toStringTag = 6;
    private static final long serialVersionUID = 8670434366883930453L;
    private boolean instanceOfWeakMap = false;
    private transient WeakHashMap<Scriptable, Object> map = new WeakHashMap<>();

    public static void init(Scriptable scriptable, boolean z) {
        new NativeWeakMap().exportAsJSClass(6, scriptable, z);
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

    private Object js_get(Object obj) {
        if (!ScriptRuntime.isObject(obj)) {
            return Undefined.instance;
        }
        Object obj2 = this.map.get(obj);
        if (obj2 == null) {
            return Undefined.instance;
        }
        if (obj2 == NULL_VALUE) {
            return null;
        }
        return obj2;
    }

    private Object js_has(Object obj) {
        if (!ScriptRuntime.isObject(obj)) {
            return Boolean.FALSE;
        }
        return Boolean.valueOf(this.map.containsKey(obj));
    }

    private Object js_set(Object obj, Object obj2) {
        if (ScriptRuntime.isObject(obj)) {
            if (obj2 == null) {
                obj2 = NULL_VALUE;
            }
            this.map.put((Scriptable) obj, obj2);
            return this;
        }
        throw ScriptRuntime.typeError1("msg.arg.not.object", ScriptRuntime.typeof(obj));
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.map = new WeakHashMap<>();
    }

    private static NativeWeakMap realThis(Scriptable scriptable, IdFunctionObject idFunctionObject) {
        if (scriptable != null) {
            try {
                NativeWeakMap nativeWeakMap = (NativeWeakMap) scriptable;
                if (nativeWeakMap.instanceOfWeakMap) {
                    return nativeWeakMap;
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
        Object obj4;
        Object obj5;
        if (!idFunctionObject.hasTag(MAP_TAG)) {
            return super.execIdCall(idFunctionObject, context, scriptable, scriptable2, objArr);
        }
        int methodId = idFunctionObject.methodId();
        if (methodId != 1) {
            if (methodId == 2) {
                NativeWeakMap realThis = realThis(scriptable2, idFunctionObject);
                if (objArr.length > 0) {
                    obj = objArr[0];
                } else {
                    obj = Undefined.instance;
                }
                return realThis.js_delete(obj);
            } else if (methodId == 3) {
                NativeWeakMap realThis2 = realThis(scriptable2, idFunctionObject);
                if (objArr.length > 0) {
                    obj2 = objArr[0];
                } else {
                    obj2 = Undefined.instance;
                }
                return realThis2.js_get(obj2);
            } else if (methodId == 4) {
                NativeWeakMap realThis3 = realThis(scriptable2, idFunctionObject);
                if (objArr.length > 0) {
                    obj3 = objArr[0];
                } else {
                    obj3 = Undefined.instance;
                }
                return realThis3.js_has(obj3);
            } else if (methodId == 5) {
                NativeWeakMap realThis4 = realThis(scriptable2, idFunctionObject);
                if (objArr.length > 0) {
                    obj4 = objArr[0];
                } else {
                    obj4 = Undefined.instance;
                }
                if (objArr.length > 1) {
                    obj5 = objArr[1];
                } else {
                    obj5 = Undefined.instance;
                }
                return realThis4.js_set(obj4, obj5);
            } else {
                throw new IllegalArgumentException("WeakMap.prototype has no method: " + idFunctionObject.getFunctionName());
            }
        } else if (scriptable2 == null) {
            NativeWeakMap nativeWeakMap = new NativeWeakMap();
            nativeWeakMap.instanceOfWeakMap = true;
            if (objArr.length > 0) {
                NativeMap.loadFromIterable(context, scriptable, nativeWeakMap, objArr[0]);
            }
            return nativeWeakMap;
        } else {
            throw ScriptRuntime.typeError1("msg.no.new", "WeakMap");
        }
    }

    public int findPrototypeId(Symbol symbol) {
        return SymbolKey.TO_STRING_TAG.equals(symbol) ? 6 : 0;
    }

    public String getClassName() {
        return "WeakMap";
    }

    public void initPrototypeId(int i) {
        int i2;
        String str;
        String str2;
        if (i == 6) {
            initPrototypeValue(6, (Symbol) SymbolKey.TO_STRING_TAG, (Object) getClassName(), 3);
            return;
        }
        if (i != 1) {
            if (i == 2) {
                str2 = "delete";
            } else if (i == 3) {
                str2 = "get";
            } else if (i == 4) {
                str2 = "has";
            } else if (i == 5) {
                str = "set";
                i2 = 2;
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
            if (charAt == 'g') {
                if (str.charAt(2) == 't' && str.charAt(1) == 'e') {
                    return 3;
                }
            } else if (charAt == 'h') {
                if (str.charAt(2) == 's' && str.charAt(1) == 'a') {
                    return 4;
                }
            } else if (charAt == 's' && str.charAt(2) == 't' && str.charAt(1) == 'e') {
                return 5;
            }
        } else {
            if (length == 6) {
                str2 = "delete";
                i = 2;
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
