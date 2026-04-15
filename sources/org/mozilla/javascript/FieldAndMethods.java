package org.mozilla.javascript;

import java.lang.reflect.Field;

class FieldAndMethods extends NativeJavaMethod {
    private static final long serialVersionUID = -9222428244284796755L;
    Field field;
    Object javaObject;

    public FieldAndMethods(Scriptable scriptable, MemberBox[] memberBoxArr, Field field2) {
        super(memberBoxArr);
        this.field = field2;
        setParentScope(scriptable);
        setPrototype(ScriptableObject.getFunctionPrototype(scriptable));
    }

    public Object getDefaultValue(Class<?> cls) {
        if (cls == ScriptRuntime.FunctionClass) {
            return this;
        }
        try {
            Object obj = this.field.get(this.javaObject);
            Class<?> type = this.field.getType();
            Context context = Context.getContext();
            Object wrap = context.getWrapFactory().wrap(context, this, obj, type);
            if (wrap instanceof Scriptable) {
                return ((Scriptable) wrap).getDefaultValue(cls);
            }
            return wrap;
        } catch (IllegalAccessException unused) {
            throw Context.reportRuntimeError1("msg.java.internal.private", this.field.getName());
        }
    }
}
