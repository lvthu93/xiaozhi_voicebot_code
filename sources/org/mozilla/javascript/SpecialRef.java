package org.mozilla.javascript;

class SpecialRef extends Ref {
    private static final int SPECIAL_NONE = 0;
    private static final int SPECIAL_PARENT = 2;
    private static final int SPECIAL_PROTO = 1;
    private static final long serialVersionUID = -7521596632456797847L;
    private String name;
    private Scriptable target;
    private int type;

    private SpecialRef(Scriptable scriptable, int i, String str) {
        this.target = scriptable;
        this.type = i;
        this.name = str;
    }

    public static Ref createSpecial(Context context, Scriptable scriptable, Object obj, String str) {
        int i;
        Scriptable objectOrNull = ScriptRuntime.toObjectOrNull(context, obj, scriptable);
        if (objectOrNull != null) {
            if (str.equals("__proto__")) {
                i = 1;
            } else if (str.equals("__parent__")) {
                i = 2;
            } else {
                throw new IllegalArgumentException(str);
            }
            if (!context.hasFeature(5)) {
                i = 0;
            }
            return new SpecialRef(objectOrNull, i, str);
        }
        throw ScriptRuntime.undefReadError(obj, str);
    }

    public boolean delete(Context context) {
        if (this.type == 0) {
            return ScriptRuntime.deleteObjectElem(this.target, this.name, context);
        }
        return false;
    }

    public Object get(Context context) {
        int i = this.type;
        if (i == 0) {
            return ScriptRuntime.getObjectProp(this.target, this.name, context);
        }
        if (i == 1) {
            return this.target.getPrototype();
        }
        if (i == 2) {
            return this.target.getParentScope();
        }
        throw Kit.codeBug();
    }

    public boolean has(Context context) {
        if (this.type == 0) {
            return ScriptRuntime.hasObjectElem(this.target, this.name, context);
        }
        return true;
    }

    @Deprecated
    public Object set(Context context, Object obj) {
        throw new IllegalStateException();
    }

    public Object set(Context context, Scriptable scriptable, Object obj) {
        int i = this.type;
        if (i == 0) {
            return ScriptRuntime.setObjectProp(this.target, this.name, obj, context);
        }
        if (i == 1 || i == 2) {
            Scriptable objectOrNull = ScriptRuntime.toObjectOrNull(context, obj, scriptable);
            if (objectOrNull != null) {
                Scriptable scriptable2 = objectOrNull;
                while (scriptable2 != this.target) {
                    if (this.type == 1) {
                        scriptable2 = scriptable2.getPrototype();
                        continue;
                    } else {
                        scriptable2 = scriptable2.getParentScope();
                        continue;
                    }
                    if (scriptable2 == null) {
                    }
                }
                throw Context.reportRuntimeError1("msg.cyclic.value", this.name);
            }
            if (this.type != 1) {
                this.target.setParentScope(objectOrNull);
            } else if (context.getLanguageVersion() < 200) {
                this.target.setPrototype(objectOrNull);
            } else if ((obj != null && !"object".equals(ScriptRuntime.typeof(obj))) || !"object".equals(ScriptRuntime.typeof(this.target))) {
                return Undefined.instance;
            } else {
                this.target.setPrototype(objectOrNull);
            }
            return objectOrNull;
        }
        throw Kit.codeBug();
    }
}
