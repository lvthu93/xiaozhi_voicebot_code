package org.mozilla.javascript;

public abstract class ES6Iterator extends IdScriptableObject {
    public static final String DONE_PROPERTY = "done";
    private static final int Id_next = 1;
    private static final int MAX_PROTOTYPE_ID = 3;
    public static final String NEXT_METHOD = "next";
    public static final String RETURN_METHOD = "return";
    public static final String RETURN_PROPERTY = "return";
    private static final int SymbolId_iterator = 2;
    private static final int SymbolId_toStringTag = 3;
    public static final String VALUE_PROPERTY = "value";
    private static final long serialVersionUID = 2438373029140003950L;
    protected boolean exhausted = false;
    private String tag;

    public ES6Iterator() {
    }

    public static void init(ScriptableObject scriptableObject, boolean z, IdScriptableObject idScriptableObject, String str) {
        if (scriptableObject != null) {
            idScriptableObject.setParentScope(scriptableObject);
            idScriptableObject.setPrototype(ScriptableObject.getObjectPrototype(scriptableObject));
        }
        idScriptableObject.activatePrototypeMap(3);
        if (z) {
            idScriptableObject.sealObject();
        }
        if (scriptableObject != null) {
            scriptableObject.associateValue(str, idScriptableObject);
        }
    }

    public static Scriptable makeIteratorResult(Context context, Scriptable scriptable, Boolean bool) {
        return makeIteratorResult(context, scriptable, bool, Undefined.instance);
    }

    public Object execIdCall(IdFunctionObject idFunctionObject, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        if (!idFunctionObject.hasTag(getTag())) {
            return super.execIdCall(idFunctionObject, context, scriptable, scriptable2, objArr);
        }
        int methodId = idFunctionObject.methodId();
        if (scriptable2 instanceof ES6Iterator) {
            ES6Iterator eS6Iterator = (ES6Iterator) scriptable2;
            if (methodId == 1) {
                return eS6Iterator.next(context, scriptable);
            }
            if (methodId == 2) {
                return eS6Iterator;
            }
            throw new IllegalArgumentException(String.valueOf(methodId));
        }
        throw IdScriptableObject.incompatibleCallError(idFunctionObject);
    }

    public int findPrototypeId(Symbol symbol) {
        if (SymbolKey.ITERATOR.equals(symbol)) {
            return 2;
        }
        return SymbolKey.TO_STRING_TAG.equals(symbol) ? 3 : 0;
    }

    public String getTag() {
        return this.tag;
    }

    public void initPrototypeId(int i) {
        if (i == 1) {
            initPrototypeMethod(getTag(), i, NEXT_METHOD, 0);
        } else if (i == 2) {
            initPrototypeMethod((Object) getTag(), i, (Symbol) SymbolKey.ITERATOR, "[Symbol.iterator]", 3);
        } else if (i == 3) {
            initPrototypeValue(3, (Symbol) SymbolKey.TO_STRING_TAG, (Object) getClassName(), 3);
        } else {
            throw new IllegalArgumentException(String.valueOf(i));
        }
    }

    public abstract boolean isDone(Context context, Scriptable scriptable);

    public Object next(Context context, Scriptable scriptable) {
        boolean z;
        Object obj = Undefined.instance;
        if (isDone(context, scriptable) || this.exhausted) {
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            obj = nextValue(context, scriptable);
        } else {
            this.exhausted = true;
        }
        return makeIteratorResult(context, scriptable, Boolean.valueOf(z), obj);
    }

    public abstract Object nextValue(Context context, Scriptable scriptable);

    public static Scriptable makeIteratorResult(Context context, Scriptable scriptable, Boolean bool, Object obj) {
        Scriptable newObject = context.newObject(scriptable);
        ScriptableObject.putProperty(newObject, VALUE_PROPERTY, obj);
        ScriptableObject.putProperty(newObject, DONE_PROPERTY, (Object) bool);
        return newObject;
    }

    public ES6Iterator(Scriptable scriptable, String str) {
        this.tag = str;
        Scriptable topLevelScope = ScriptableObject.getTopLevelScope(scriptable);
        setParentScope(topLevelScope);
        setPrototype((IdScriptableObject) ScriptableObject.getTopScopeValue(topLevelScope, str));
    }

    public int findPrototypeId(String str) {
        return NEXT_METHOD.equals(str) ? 1 : 0;
    }
}
