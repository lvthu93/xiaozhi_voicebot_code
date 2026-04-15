package defpackage;

import java.lang.reflect.Method;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextAction;
import org.mozilla.javascript.InterfaceAdapter;
import org.mozilla.javascript.Scriptable;

/* renamed from: x3  reason: default package */
public final /* synthetic */ class x3 implements ContextAction {
    public final /* synthetic */ InterfaceAdapter c;
    public final /* synthetic */ Object f;
    public final /* synthetic */ Scriptable g;
    public final /* synthetic */ Object h;
    public final /* synthetic */ Method i;
    public final /* synthetic */ Object[] j;

    public /* synthetic */ x3(InterfaceAdapter interfaceAdapter, Object obj, Scriptable scriptable, Object obj2, Method method, Object[] objArr) {
        this.c = interfaceAdapter;
        this.f = obj;
        this.g = scriptable;
        this.h = obj2;
        this.i = method;
        this.j = objArr;
    }

    public final Object run(Context context) {
        return this.c.lambda$invoke$0(this.f, this.g, this.h, this.i, this.j, context);
    }
}
