package org.mozilla.javascript.tools.shell;

import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class Environment extends ScriptableObject {
    static final long serialVersionUID = -430727378460177065L;
    private Environment thePrototypeInstance;

    public Environment() {
        this.thePrototypeInstance = null;
        this.thePrototypeInstance = this;
    }

    private Object[] collectIds() {
        return System.getProperties().keySet().toArray();
    }

    public static void defineClass(ScriptableObject scriptableObject) {
        try {
            ScriptableObject.defineClass(scriptableObject, Environment.class);
        } catch (Exception e) {
            throw new Error(e.getMessage());
        }
    }

    public Object get(String str, Scriptable scriptable) {
        if (this == this.thePrototypeInstance) {
            return super.get(str, scriptable);
        }
        String property = System.getProperty(str);
        if (property != null) {
            return ScriptRuntime.toObject(getParentScope(), property);
        }
        return Scriptable.NOT_FOUND;
    }

    public Object[] getAllIds() {
        if (this == this.thePrototypeInstance) {
            return super.getAllIds();
        }
        return collectIds();
    }

    public String getClassName() {
        return "Environment";
    }

    public Object[] getIds() {
        if (this == this.thePrototypeInstance) {
            return super.getIds();
        }
        return collectIds();
    }

    public boolean has(String str, Scriptable scriptable) {
        if (this == this.thePrototypeInstance) {
            return super.has(str, scriptable);
        }
        if (System.getProperty(str) != null) {
            return true;
        }
        return false;
    }

    public void put(String str, Scriptable scriptable, Object obj) {
        if (this == this.thePrototypeInstance) {
            super.put(str, scriptable, obj);
        } else {
            System.getProperties().put(str, ScriptRuntime.toString(obj));
        }
    }

    public Environment(ScriptableObject scriptableObject) {
        this.thePrototypeInstance = null;
        setParentScope(scriptableObject);
        Object topLevelProp = ScriptRuntime.getTopLevelProp(scriptableObject, "Environment");
        if (topLevelProp != null && (topLevelProp instanceof Scriptable)) {
            Scriptable scriptable = (Scriptable) topLevelProp;
            setPrototype((Scriptable) scriptable.get("prototype", scriptable));
        }
    }
}
