package org.mozilla.javascript;

public class Delegator implements Function, SymbolScriptable {
    protected Scriptable obj;

    public Delegator() {
        this.obj = null;
    }

    public Object call(Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        return ((Function) getDelegee()).call(context, scriptable, scriptable2, objArr);
    }

    public Scriptable construct(Context context, Scriptable scriptable, Object[] objArr) {
        Scriptable scriptable2;
        Scriptable delegee = getDelegee();
        if (delegee != null) {
            return ((Function) delegee).construct(context, scriptable, objArr);
        }
        Delegator newInstance = newInstance();
        if (objArr.length == 0) {
            scriptable2 = new NativeObject();
        } else {
            scriptable2 = ScriptRuntime.toObject(context, scriptable, objArr[0]);
        }
        newInstance.setDelegee(scriptable2);
        return newInstance;
    }

    public void delete(String str) {
        getDelegee().delete(str);
    }

    public Object get(String str, Scriptable scriptable) {
        return getDelegee().get(str, scriptable);
    }

    public String getClassName() {
        return getDelegee().getClassName();
    }

    public Object getDefaultValue(Class<?> cls) {
        if (cls == null || cls == ScriptRuntime.ScriptableClass || cls == ScriptRuntime.FunctionClass) {
            return this;
        }
        return getDelegee().getDefaultValue(cls);
    }

    public Scriptable getDelegee() {
        return this.obj;
    }

    public Object[] getIds() {
        return getDelegee().getIds();
    }

    public Scriptable getParentScope() {
        return getDelegee().getParentScope();
    }

    public Scriptable getPrototype() {
        return getDelegee().getPrototype();
    }

    public boolean has(String str, Scriptable scriptable) {
        return getDelegee().has(str, scriptable);
    }

    public boolean hasInstance(Scriptable scriptable) {
        return getDelegee().hasInstance(scriptable);
    }

    public Delegator newInstance() {
        try {
            return (Delegator) getClass().newInstance();
        } catch (Exception e) {
            throw Context.throwAsScriptRuntimeEx(e);
        }
    }

    public void put(String str, Scriptable scriptable, Object obj2) {
        getDelegee().put(str, scriptable, obj2);
    }

    public void setDelegee(Scriptable scriptable) {
        this.obj = scriptable;
    }

    public void setParentScope(Scriptable scriptable) {
        getDelegee().setParentScope(scriptable);
    }

    public void setPrototype(Scriptable scriptable) {
        getDelegee().setPrototype(scriptable);
    }

    public void delete(Symbol symbol) {
        Scriptable delegee = getDelegee();
        if (delegee instanceof SymbolScriptable) {
            ((SymbolScriptable) delegee).delete(symbol);
        }
    }

    public Object get(Symbol symbol, Scriptable scriptable) {
        Scriptable delegee = getDelegee();
        if (delegee instanceof SymbolScriptable) {
            return ((SymbolScriptable) delegee).get(symbol, scriptable);
        }
        return Scriptable.NOT_FOUND;
    }

    public boolean has(Symbol symbol, Scriptable scriptable) {
        Scriptable delegee = getDelegee();
        if (delegee instanceof SymbolScriptable) {
            return ((SymbolScriptable) delegee).has(symbol, scriptable);
        }
        return false;
    }

    public void put(Symbol symbol, Scriptable scriptable, Object obj2) {
        Scriptable delegee = getDelegee();
        if (delegee instanceof SymbolScriptable) {
            ((SymbolScriptable) delegee).put(symbol, scriptable, obj2);
        }
    }

    public Delegator(Scriptable scriptable) {
        this.obj = scriptable;
    }

    public void delete(int i) {
        getDelegee().delete(i);
    }

    public boolean has(int i, Scriptable scriptable) {
        return getDelegee().has(i, scriptable);
    }

    public void put(int i, Scriptable scriptable, Object obj2) {
        getDelegee().put(i, scriptable, obj2);
    }

    public Object get(int i, Scriptable scriptable) {
        return getDelegee().get(i, scriptable);
    }
}
