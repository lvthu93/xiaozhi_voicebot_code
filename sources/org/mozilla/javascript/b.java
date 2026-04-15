package org.mozilla.javascript;

import org.mozilla.javascript.Interpreter;

public final /* synthetic */ class b implements Callable {
    public final /* synthetic */ Interpreter.CallFrame c;
    public final /* synthetic */ Object f;

    public /* synthetic */ b(Interpreter.CallFrame callFrame, Object obj) {
        this.c = callFrame;
        this.f = obj;
    }

    public final Object call(Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        return this.c.lambda$equals$0(this.f, context, scriptable, scriptable2, objArr);
    }
}
