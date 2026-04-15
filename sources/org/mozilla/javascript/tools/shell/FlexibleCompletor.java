package org.mozilla.javascript.tools.shell;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

class FlexibleCompletor implements InvocationHandler {
    private Method completeMethod;
    private Scriptable global;

    public FlexibleCompletor(Class<?> cls, Scriptable scriptable) throws NoSuchMethodException {
        this.global = scriptable;
        this.completeMethod = cls.getMethod("complete", new Class[]{String.class, Integer.TYPE, List.class});
    }

    public int complete(String str, int i, List<String> list) {
        Object[] objArr;
        int i2 = i - 1;
        while (i2 >= 0) {
            char charAt = str.charAt(i2);
            if (!Character.isJavaIdentifierPart(charAt) && charAt != '.') {
                break;
            }
            i2--;
        }
        String[] split = str.substring(i2 + 1, i).split("\\.", -1);
        Scriptable scriptable = this.global;
        for (int i3 = 0; i3 < split.length - 1; i3++) {
            Object obj = scriptable.get(split[i3], this.global);
            if (!(obj instanceof Scriptable)) {
                return str.length();
            }
            scriptable = (Scriptable) obj;
        }
        if (scriptable instanceof ScriptableObject) {
            objArr = ((ScriptableObject) scriptable).getAllIds();
        } else {
            objArr = scriptable.getIds();
        }
        String str2 = split[split.length - 1];
        for (Object obj2 : objArr) {
            if (obj2 instanceof String) {
                String str3 = (String) obj2;
                if (str3.startsWith(str2)) {
                    if (scriptable.get(str3, scriptable) instanceof Function) {
                        str3 = str3.concat("(");
                    }
                    list.add(str3);
                }
            }
        }
        return str.length() - str2.length();
    }

    public Object invoke(Object obj, Method method, Object[] objArr) {
        if (method.equals(this.completeMethod)) {
            return Integer.valueOf(complete(objArr[0], objArr[1].intValue(), objArr[2]));
        }
        throw new NoSuchMethodError(method.toString());
    }
}
