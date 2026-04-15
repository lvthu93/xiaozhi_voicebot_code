package org.mozilla.javascript.tools.shell;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextAction;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;

class Runner implements Runnable, ContextAction<Object> {
    private Object[] args;
    private Function f;
    ContextFactory factory;
    private Script s;
    private Scriptable scope;

    public Runner(Scriptable scriptable, Function function, Object[] objArr) {
        this.scope = scriptable;
        this.f = function;
        this.args = objArr;
    }

    public void run() {
        this.factory.call(this);
    }

    public Object run(Context context) {
        Function function = this.f;
        if (function == null) {
            return this.s.exec(context, this.scope);
        }
        Scriptable scriptable = this.scope;
        return function.call(context, scriptable, scriptable, this.args);
    }

    public Runner(Scriptable scriptable, Script script) {
        this.scope = scriptable;
        this.s = script;
    }
}
