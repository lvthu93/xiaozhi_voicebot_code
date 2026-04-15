package defpackage;

import org.mozilla.javascript.Callable;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextAction;
import org.mozilla.javascript.Scriptable;

/* renamed from: u0  reason: default package */
public final /* synthetic */ class u0 implements ContextAction {
    public final /* synthetic */ Callable c;
    public final /* synthetic */ Scriptable f;
    public final /* synthetic */ Scriptable g;
    public final /* synthetic */ Object[] h;

    public /* synthetic */ u0(Callable callable, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        this.c = callable;
        this.f = scriptable;
        this.g = scriptable2;
        this.h = objArr;
    }

    public final Object run(Context context) {
        return this.c.call(context, this.f, this.g, this.h);
    }
}
