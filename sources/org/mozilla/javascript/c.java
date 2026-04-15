package org.mozilla.javascript;

import j$.util.function.Function$CC;
import java.util.function.Function;
import org.mozilla.javascript.Interpreter;

public final /* synthetic */ class c implements Function {
    public final /* synthetic */ Interpreter.CallFrame a;
    public final /* synthetic */ Object b;

    public /* synthetic */ c(Interpreter.CallFrame callFrame, Object obj) {
        this.a = callFrame;
        this.b = obj;
    }

    public final /* synthetic */ Function andThen(Function function) {
        return Function$CC.$default$andThen(this, function);
    }

    public final Object apply(Object obj) {
        return this.a.lambda$equalsInTopScope$1(this.b, (EqualObjectGraphs) obj);
    }

    public final /* synthetic */ Function compose(Function function) {
        return Function$CC.$default$compose(this, function);
    }
}
