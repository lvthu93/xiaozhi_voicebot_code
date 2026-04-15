package org.mozilla.javascript;

import java.util.List;
import java.util.Map;

public class WrapFactory {
    private boolean javaPrimitiveWrap = true;

    public final boolean isJavaPrimitiveWrap() {
        return this.javaPrimitiveWrap;
    }

    public final void setJavaPrimitiveWrap(boolean z) {
        Context currentContext = Context.getCurrentContext();
        if (currentContext != null && currentContext.isSealed()) {
            Context.onSealedMutation();
        }
        this.javaPrimitiveWrap = z;
    }

    public Object wrap(Context context, Scriptable scriptable, Object obj, Class<?> cls) {
        Object obj2;
        if (obj == null || obj == (obj2 = Undefined.instance) || (obj instanceof Scriptable)) {
            return obj;
        }
        if (cls == null || !cls.isPrimitive()) {
            if (!isJavaPrimitiveWrap()) {
                if ((obj instanceof String) || (obj instanceof Boolean) || (obj instanceof Integer) || (obj instanceof Short) || (obj instanceof Long) || (obj instanceof Float) || (obj instanceof Double)) {
                    return obj;
                }
                if (obj instanceof Character) {
                    return String.valueOf(((Character) obj).charValue());
                }
            }
            if (obj.getClass().isArray()) {
                return NativeJavaArray.wrap(scriptable, obj);
            }
            return wrapAsJavaObject(context, scriptable, obj, cls);
        } else if (cls == Void.TYPE) {
            return obj2;
        } else {
            if (cls == Character.TYPE) {
                return Integer.valueOf(((Character) obj).charValue());
            }
            return obj;
        }
    }

    public Scriptable wrapAsJavaObject(Context context, Scriptable scriptable, Object obj, Class<?> cls) {
        if (List.class.isAssignableFrom(obj.getClass())) {
            return new NativeJavaList(scriptable, obj);
        }
        if (Map.class.isAssignableFrom(obj.getClass())) {
            return new NativeJavaMap(scriptable, obj);
        }
        return new NativeJavaObject(scriptable, obj, cls);
    }

    public Scriptable wrapJavaClass(Context context, Scriptable scriptable, Class<?> cls) {
        return new NativeJavaClass(scriptable, cls);
    }

    public Scriptable wrapNewObject(Context context, Scriptable scriptable, Object obj) {
        if (obj instanceof Scriptable) {
            return (Scriptable) obj;
        }
        if (obj.getClass().isArray()) {
            return NativeJavaArray.wrap(scriptable, obj);
        }
        return wrapAsJavaObject(context, scriptable, obj, (Class<?>) null);
    }
}
