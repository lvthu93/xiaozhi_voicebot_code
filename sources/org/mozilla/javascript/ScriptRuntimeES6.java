package org.mozilla.javascript;

public class ScriptRuntimeES6 {
    public static Object requireObjectCoercible(Context context, Object obj, IdFunctionObject idFunctionObject) {
        if (obj != null && !Undefined.isUndefined(obj)) {
            return obj;
        }
        throw ScriptRuntime.typeError2("msg.called.null.or.undefined", idFunctionObject.getTag(), idFunctionObject.getFunctionName());
    }
}
