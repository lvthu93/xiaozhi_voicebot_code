package defpackage;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextAction;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.JavaAdapter;
import org.mozilla.javascript.Scriptable;

/* renamed from: a4  reason: default package */
public final /* synthetic */ class a4 implements ContextAction {
    public final /* synthetic */ Scriptable c;
    public final /* synthetic */ Scriptable f;
    public final /* synthetic */ Function g;
    public final /* synthetic */ Object[] h;
    public final /* synthetic */ long i;

    public /* synthetic */ a4(Scriptable scriptable, Scriptable scriptable2, Function function, Object[] objArr, long j) {
        this.c = scriptable;
        this.f = scriptable2;
        this.g = function;
        this.h = objArr;
        this.i = j;
    }

    public final Object run(Context context) {
        return JavaAdapter.doCall(context, this.c, this.f, this.g, this.h, this.i);
    }
}
