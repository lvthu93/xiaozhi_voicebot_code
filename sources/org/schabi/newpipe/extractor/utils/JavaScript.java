package org.schabi.newpipe.extractor.utils;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public final class JavaScript {
    public static void compileOrThrow(String str) {
        try {
            Context.enter().compileString(str, (String) null, 1, (Object) null);
        } finally {
            Context.exit();
        }
    }

    public static String run(String str, String str2, String... strArr) {
        String str3;
        Context enter = Context.enter();
        try {
            ScriptableObject initSafeStandardObjects = enter.initSafeStandardObjects();
            enter.evaluateString(initSafeStandardObjects, str, str2, 1, (Object) null);
            Object call = ((Function) initSafeStandardObjects.get(str2, (Scriptable) initSafeStandardObjects)).call(enter, initSafeStandardObjects, initSafeStandardObjects, strArr);
            if (call == null) {
                str3 = null;
            } else {
                str3 = call.toString();
            }
            return str3;
        } finally {
            Context.exit();
        }
    }
}
