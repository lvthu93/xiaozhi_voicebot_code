package org.mozilla.javascript;

import java.io.Serializable;

public class NativeWith implements Scriptable, SymbolScriptable, IdFunctionCall, Serializable {
    private static final Object FTAG = "With";
    private static final int Id_constructor = 1;
    private static final long serialVersionUID = 1;
    protected Scriptable parent;
    protected Scriptable prototype;

    private NativeWith() {
    }

    public static void init(Scriptable scriptable, boolean z) {
        NativeWith nativeWith = new NativeWith();
        nativeWith.setParentScope(scriptable);
        nativeWith.setPrototype(ScriptableObject.getObjectPrototype(scriptable));
        IdFunctionObject idFunctionObject = new IdFunctionObject(nativeWith, FTAG, 1, "With", 0, scriptable);
        idFunctionObject.markAsConstructor(nativeWith);
        if (z) {
            idFunctionObject.sealObject();
        }
        idFunctionObject.exportAsScopeProperty();
    }

    public static boolean isWithFunction(Object obj) {
        if (!(obj instanceof IdFunctionObject)) {
            return false;
        }
        IdFunctionObject idFunctionObject = (IdFunctionObject) obj;
        if (!idFunctionObject.hasTag(FTAG) || idFunctionObject.methodId() != 1) {
            return false;
        }
        return true;
    }

    public static Object newWithSpecial(Context context, Scriptable scriptable, Object[] objArr) {
        Scriptable scriptable2;
        ScriptRuntime.checkDeprecated(context, "With");
        Scriptable topLevelScope = ScriptableObject.getTopLevelScope(scriptable);
        NativeWith nativeWith = new NativeWith();
        if (objArr.length == 0) {
            scriptable2 = ScriptableObject.getObjectPrototype(topLevelScope);
        } else {
            scriptable2 = ScriptRuntime.toObject(context, topLevelScope, objArr[0]);
        }
        nativeWith.setPrototype(scriptable2);
        nativeWith.setParentScope(topLevelScope);
        return nativeWith;
    }

    public void delete(String str) {
        this.prototype.delete(str);
    }

    public Object execIdCall(IdFunctionObject idFunctionObject, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        if (!idFunctionObject.hasTag(FTAG) || idFunctionObject.methodId() != 1) {
            throw idFunctionObject.unknown();
        }
        throw Context.reportRuntimeError1("msg.cant.call.indirect", "With");
    }

    public Object get(String str, Scriptable scriptable) {
        if (scriptable == this) {
            scriptable = this.prototype;
        }
        return this.prototype.get(str, scriptable);
    }

    public String getClassName() {
        return "With";
    }

    public Object getDefaultValue(Class<?> cls) {
        return this.prototype.getDefaultValue(cls);
    }

    public Object[] getIds() {
        return this.prototype.getIds();
    }

    public Scriptable getParentScope() {
        return this.parent;
    }

    public Scriptable getPrototype() {
        return this.prototype;
    }

    public boolean has(String str, Scriptable scriptable) {
        Scriptable scriptable2 = this.prototype;
        return scriptable2.has(str, scriptable2);
    }

    public boolean hasInstance(Scriptable scriptable) {
        return this.prototype.hasInstance(scriptable);
    }

    public void put(String str, Scriptable scriptable, Object obj) {
        if (scriptable == this) {
            scriptable = this.prototype;
        }
        this.prototype.put(str, scriptable, obj);
    }

    public void setParentScope(Scriptable scriptable) {
        this.parent = scriptable;
    }

    public void setPrototype(Scriptable scriptable) {
        this.prototype = scriptable;
    }

    public Object updateDotQuery(boolean z) {
        throw new IllegalStateException();
    }

    public NativeWith(Scriptable scriptable, Scriptable scriptable2) {
        this.parent = scriptable;
        this.prototype = scriptable2;
    }

    public void delete(Symbol symbol) {
        Scriptable scriptable = this.prototype;
        if (scriptable instanceof SymbolScriptable) {
            ((SymbolScriptable) scriptable).delete(symbol);
        }
    }

    public boolean has(Symbol symbol, Scriptable scriptable) {
        Scriptable scriptable2 = this.prototype;
        if (scriptable2 instanceof SymbolScriptable) {
            return ((SymbolScriptable) scriptable2).has(symbol, scriptable2);
        }
        return false;
    }

    public Object get(Symbol symbol, Scriptable scriptable) {
        if (scriptable == this) {
            scriptable = this.prototype;
        }
        Scriptable scriptable2 = this.prototype;
        if (scriptable2 instanceof SymbolScriptable) {
            return ((SymbolScriptable) scriptable2).get(symbol, scriptable);
        }
        return Scriptable.NOT_FOUND;
    }

    public void put(Symbol symbol, Scriptable scriptable, Object obj) {
        if (scriptable == this) {
            scriptable = this.prototype;
        }
        Scriptable scriptable2 = this.prototype;
        if (scriptable2 instanceof SymbolScriptable) {
            ((SymbolScriptable) scriptable2).put(symbol, scriptable, obj);
        }
    }

    public void delete(int i) {
        this.prototype.delete(i);
    }

    public boolean has(int i, Scriptable scriptable) {
        Scriptable scriptable2 = this.prototype;
        return scriptable2.has(i, scriptable2);
    }

    public void put(int i, Scriptable scriptable, Object obj) {
        if (scriptable == this) {
            scriptable = this.prototype;
        }
        this.prototype.put(i, scriptable, obj);
    }

    public Object get(int i, Scriptable scriptable) {
        if (scriptable == this) {
            scriptable = this.prototype;
        }
        return this.prototype.get(i, scriptable);
    }
}
