package org.mozilla.javascript;

public class ArrowFunction extends BaseFunction {
    private static final long serialVersionUID = -7377989503697220633L;
    private final Scriptable boundThis;
    private final Callable targetFunction;

    public ArrowFunction(Context context, Scriptable scriptable, Callable callable, Scriptable scriptable2) {
        this.targetFunction = callable;
        this.boundThis = scriptable2;
        ScriptRuntime.setFunctionProtoAndParent(this, scriptable);
        BaseFunction typeErrorThrower = ScriptRuntime.typeErrorThrower(context);
        NativeObject nativeObject = new NativeObject();
        nativeObject.put("get", (Scriptable) nativeObject, (Object) typeErrorThrower);
        nativeObject.put("set", (Scriptable) nativeObject, (Object) typeErrorThrower);
        Boolean bool = Boolean.FALSE;
        nativeObject.put("enumerable", (Scriptable) nativeObject, (Object) bool);
        nativeObject.put("configurable", (Scriptable) nativeObject, (Object) bool);
        nativeObject.preventExtensions();
        defineOwnProperty(context, "caller", nativeObject, false);
        defineOwnProperty(context, "arguments", nativeObject, false);
    }

    public static boolean equalObjectGraphs(ArrowFunction arrowFunction, ArrowFunction arrowFunction2, EqualObjectGraphs equalObjectGraphs) {
        return equalObjectGraphs.equalGraphs(arrowFunction.boundThis, arrowFunction2.boundThis) && equalObjectGraphs.equalGraphs(arrowFunction.targetFunction, arrowFunction2.targetFunction);
    }

    public Object call(Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        Scriptable scriptable3 = this.boundThis;
        if (scriptable3 == null) {
            scriptable3 = ScriptRuntime.getTopCallScope(context);
        }
        return this.targetFunction.call(context, scriptable, scriptable3, objArr);
    }

    public Scriptable construct(Context context, Scriptable scriptable, Object[] objArr) {
        throw ScriptRuntime.typeError1("msg.not.ctor", decompile(0, 0));
    }

    public String decompile(int i, int i2) {
        Callable callable = this.targetFunction;
        if (callable instanceof BaseFunction) {
            return ((BaseFunction) callable).decompile(i, i2);
        }
        return super.decompile(i, i2);
    }

    public int getArity() {
        return getLength();
    }

    public int getLength() {
        Callable callable = this.targetFunction;
        if (callable instanceof BaseFunction) {
            return ((BaseFunction) callable).getLength();
        }
        return 0;
    }

    public boolean hasInstance(Scriptable scriptable) {
        Callable callable = this.targetFunction;
        if (callable instanceof Function) {
            return ((Function) callable).hasInstance(scriptable);
        }
        throw ScriptRuntime.typeError0("msg.not.ctor");
    }
}
