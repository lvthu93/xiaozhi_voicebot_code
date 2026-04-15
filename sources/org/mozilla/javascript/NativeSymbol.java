package org.mozilla.javascript;

import java.util.HashMap;
import java.util.Map;

public class NativeSymbol extends IdScriptableObject implements Symbol {
    public static final String CLASS_NAME = "Symbol";
    private static final Object CONSTRUCTOR_SLOT = new Object();
    private static final int ConstructorId_for = -1;
    private static final int ConstructorId_keyFor = -2;
    private static final Object GLOBAL_TABLE_KEY = new Object();
    private static final int Id_constructor = 1;
    private static final int Id_toString = 2;
    private static final int Id_valueOf = 4;
    private static final int MAX_PROTOTYPE_ID = 5;
    private static final int SymbolId_toPrimitive = 5;
    private static final int SymbolId_toStringTag = 3;
    public static final String TYPE_NAME = "symbol";
    private static final long serialVersionUID = -589539749749830003L;
    private final SymbolKey key;
    private final NativeSymbol symbolData;

    private NativeSymbol(String str) {
        this.key = new SymbolKey(str);
        this.symbolData = null;
    }

    public static NativeSymbol construct(Context context, Scriptable scriptable, Object[] objArr) {
        Object obj = CONSTRUCTOR_SLOT;
        context.putThreadLocal(obj, Boolean.TRUE);
        try {
            NativeSymbol nativeSymbol = (NativeSymbol) context.newObject(scriptable, CLASS_NAME, objArr);
            context.removeThreadLocal(obj);
            return nativeSymbol;
        } catch (Throwable th) {
            context.removeThreadLocal(CONSTRUCTOR_SLOT);
            throw th;
        }
    }

    private static void createStandardSymbol(Context context, Scriptable scriptable, ScriptableObject scriptableObject, String str, SymbolKey symbolKey) {
        scriptableObject.defineProperty(str, (Object) context.newObject(scriptable, CLASS_NAME, new Object[]{str, symbolKey}), 7);
    }

    private Map<String, NativeSymbol> getGlobalMap() {
        ScriptableObject scriptableObject = (ScriptableObject) ScriptableObject.getTopLevelScope(this);
        Object obj = GLOBAL_TABLE_KEY;
        Map<String, NativeSymbol> map = (Map) scriptableObject.getAssociatedValue(obj);
        if (map != null) {
            return map;
        }
        HashMap hashMap = new HashMap();
        scriptableObject.associateValue(obj, hashMap);
        return hashMap;
    }

    private static NativeSymbol getSelf(Object obj) {
        try {
            return (NativeSymbol) obj;
        } catch (ClassCastException unused) {
            throw ScriptRuntime.typeError1("msg.invalid.type", obj.getClass().getName());
        }
    }

    public static void init(Context context, Scriptable scriptable, boolean z) {
        IdFunctionObject exportAsJSClass = new NativeSymbol("").exportAsJSClass(5, scriptable, false);
        Object obj = CONSTRUCTOR_SLOT;
        context.putThreadLocal(obj, Boolean.TRUE);
        try {
            createStandardSymbol(context, scriptable, exportAsJSClass, "iterator", SymbolKey.ITERATOR);
            createStandardSymbol(context, scriptable, exportAsJSClass, "species", SymbolKey.SPECIES);
            createStandardSymbol(context, scriptable, exportAsJSClass, "toStringTag", SymbolKey.TO_STRING_TAG);
            createStandardSymbol(context, scriptable, exportAsJSClass, "hasInstance", SymbolKey.HAS_INSTANCE);
            createStandardSymbol(context, scriptable, exportAsJSClass, "isConcatSpreadable", SymbolKey.IS_CONCAT_SPREADABLE);
            createStandardSymbol(context, scriptable, exportAsJSClass, "isRegExp", SymbolKey.IS_REGEXP);
            createStandardSymbol(context, scriptable, exportAsJSClass, "toPrimitive", SymbolKey.TO_PRIMITIVE);
            createStandardSymbol(context, scriptable, exportAsJSClass, "match", SymbolKey.MATCH);
            createStandardSymbol(context, scriptable, exportAsJSClass, "replace", SymbolKey.REPLACE);
            createStandardSymbol(context, scriptable, exportAsJSClass, "search", SymbolKey.SEARCH);
            createStandardSymbol(context, scriptable, exportAsJSClass, "split", SymbolKey.SPLIT);
            createStandardSymbol(context, scriptable, exportAsJSClass, "unscopables", SymbolKey.UNSCOPABLES);
            context.removeThreadLocal(obj);
            if (z) {
                exportAsJSClass.sealObject();
            }
        } catch (Throwable th) {
            context.removeThreadLocal(CONSTRUCTOR_SLOT);
            throw th;
        }
    }

    private static boolean isStrictMode() {
        Context currentContext = Context.getCurrentContext();
        if (currentContext == null || !currentContext.isStrictMode()) {
            return false;
        }
        return true;
    }

    private static NativeSymbol js_constructor(Object[] objArr) {
        String str = "";
        if (objArr.length > 0 && !Undefined.instance.equals(objArr[0])) {
            str = ScriptRuntime.toString(objArr[0]);
        }
        if (objArr.length > 1) {
            return new NativeSymbol(objArr[1]);
        }
        return new NativeSymbol(new SymbolKey(str));
    }

    private Object js_for(Context context, Scriptable scriptable, Object[] objArr) {
        Object obj;
        if (objArr.length > 0) {
            obj = objArr[0];
        } else {
            obj = Undefined.instance;
        }
        String scriptRuntime = ScriptRuntime.toString(obj);
        Map<String, NativeSymbol> globalMap = getGlobalMap();
        NativeSymbol nativeSymbol = globalMap.get(scriptRuntime);
        if (nativeSymbol != null) {
            return nativeSymbol;
        }
        NativeSymbol construct = construct(context, scriptable, new Object[]{scriptRuntime});
        globalMap.put(scriptRuntime, construct);
        return construct;
    }

    private Object js_keyFor(Context context, Scriptable scriptable, Object[] objArr) {
        Object obj;
        if (objArr.length > 0) {
            obj = objArr[0];
        } else {
            obj = Undefined.instance;
        }
        if (obj instanceof NativeSymbol) {
            NativeSymbol nativeSymbol = (NativeSymbol) obj;
            for (Map.Entry next : getGlobalMap().entrySet()) {
                if (((NativeSymbol) next.getValue()).key == nativeSymbol.key) {
                    return next.getKey();
                }
            }
            return Undefined.instance;
        }
        throw ScriptRuntime.throwCustomError(context, scriptable, "TypeError", "Not a Symbol");
    }

    private Object js_valueOf() {
        return this.symbolData;
    }

    public boolean equals(Object obj) {
        return this.key.equals(obj);
    }

    public Object execIdCall(IdFunctionObject idFunctionObject, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        if (!idFunctionObject.hasTag(CLASS_NAME)) {
            return super.execIdCall(idFunctionObject, context, scriptable, scriptable2, objArr);
        }
        int methodId = idFunctionObject.methodId();
        if (methodId == -2) {
            return js_keyFor(context, scriptable, objArr);
        }
        if (methodId == -1) {
            return js_for(context, scriptable, objArr);
        }
        if (methodId != 1) {
            if (methodId == 2) {
                return getSelf(scriptable2).toString();
            }
            if (methodId == 4 || methodId == 5) {
                return getSelf(scriptable2).js_valueOf();
            }
            return super.execIdCall(idFunctionObject, context, scriptable, scriptable2, objArr);
        } else if (scriptable2 != null) {
            return construct(context, scriptable, objArr);
        } else {
            if (context.getThreadLocal(CONSTRUCTOR_SLOT) != null) {
                return js_constructor(objArr);
            }
            throw ScriptRuntime.typeError0("msg.no.symbol.new");
        }
    }

    public void fillConstructorProperties(IdFunctionObject idFunctionObject) {
        super.fillConstructorProperties(idFunctionObject);
        addIdFunctionProperty(idFunctionObject, CLASS_NAME, -1, "for", 1);
        addIdFunctionProperty(idFunctionObject, CLASS_NAME, -2, "keyFor", 1);
    }

    public int findPrototypeId(String str) {
        int i;
        String str2;
        int length = str.length();
        if (length == 7) {
            str2 = "valueOf";
            i = 4;
        } else if (length == 8) {
            str2 = "toString";
            i = 2;
        } else if (length == 11) {
            str2 = "constructor";
            i = 1;
        } else {
            str2 = null;
            i = 0;
        }
        if (str2 == null || str2 == str || str2.equals(str)) {
            return i;
        }
        return 0;
    }

    public String getClassName() {
        return CLASS_NAME;
    }

    public SymbolKey getKey() {
        return this.key;
    }

    public String getTypeOf() {
        return isSymbol() ? TYPE_NAME : super.getTypeOf();
    }

    public int hashCode() {
        return this.key.hashCode();
    }

    public void initPrototypeId(int i) {
        if (i == 1) {
            initPrototypeMethod(CLASS_NAME, i, "constructor", 0);
        } else if (i == 2) {
            initPrototypeMethod(CLASS_NAME, i, "toString", 0);
        } else if (i == 3) {
            initPrototypeValue(i, (Symbol) SymbolKey.TO_STRING_TAG, (Object) CLASS_NAME, 3);
        } else if (i == 4) {
            initPrototypeMethod(CLASS_NAME, i, "valueOf", 0);
        } else if (i != 5) {
            super.initPrototypeId(i);
        } else {
            initPrototypeMethod((Object) CLASS_NAME, i, (Symbol) SymbolKey.TO_PRIMITIVE, "Symbol.toPrimitive", 1);
        }
    }

    public boolean isSymbol() {
        return this.symbolData == this;
    }

    public void put(String str, Scriptable scriptable, Object obj) {
        if (!isSymbol()) {
            super.put(str, scriptable, obj);
        } else if (isStrictMode()) {
            throw ScriptRuntime.typeError0("msg.no.assign.symbol.strict");
        }
    }

    public String toString() {
        return this.key.toString();
    }

    public int findPrototypeId(Symbol symbol) {
        if (SymbolKey.TO_STRING_TAG.equals(symbol)) {
            return 3;
        }
        return SymbolKey.TO_PRIMITIVE.equals(symbol) ? 5 : 0;
    }

    private NativeSymbol(SymbolKey symbolKey) {
        this.key = symbolKey;
        this.symbolData = this;
    }

    public void put(int i, Scriptable scriptable, Object obj) {
        if (!isSymbol()) {
            super.put(i, scriptable, obj);
        } else if (isStrictMode()) {
            throw ScriptRuntime.typeError0("msg.no.assign.symbol.strict");
        }
    }

    public NativeSymbol(NativeSymbol nativeSymbol) {
        this.key = nativeSymbol.key;
        this.symbolData = nativeSymbol.symbolData;
    }

    public void put(Symbol symbol, Scriptable scriptable, Object obj) {
        if (!isSymbol()) {
            super.put(symbol, scriptable, obj);
        } else if (isStrictMode()) {
            throw ScriptRuntime.typeError0("msg.no.assign.symbol.strict");
        }
    }
}
