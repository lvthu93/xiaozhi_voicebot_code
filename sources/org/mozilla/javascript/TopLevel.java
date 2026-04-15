package org.mozilla.javascript;

import java.util.EnumMap;

public class TopLevel extends IdScriptableObject {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long serialVersionUID = -4648046356662472260L;
    private EnumMap<Builtins, BaseFunction> ctors;
    private EnumMap<NativeErrors, BaseFunction> errors;

    public enum Builtins {
        Object,
        Array,
        Function,
        String,
        Number,
        Boolean,
        RegExp,
        Error,
        Symbol,
        GeneratorFunction
    }

    public enum NativeErrors {
        Error,
        EvalError,
        RangeError,
        ReferenceError,
        SyntaxError,
        TypeError,
        URIError,
        InternalError,
        JavaException
    }

    public static Function getBuiltinCtor(Context context, Scriptable scriptable, Builtins builtins) {
        String str;
        BaseFunction builtinCtor;
        if ((scriptable instanceof TopLevel) && (builtinCtor = ((TopLevel) scriptable).getBuiltinCtor(builtins)) != null) {
            return builtinCtor;
        }
        if (builtins == Builtins.GeneratorFunction) {
            str = "__GeneratorFunction";
        } else {
            str = builtins.name();
        }
        return ScriptRuntime.getExistingCtor(context, scriptable, str);
    }

    public static Scriptable getBuiltinPrototype(Scriptable scriptable, Builtins builtins) {
        String str;
        Scriptable builtinPrototype;
        if ((scriptable instanceof TopLevel) && (builtinPrototype = ((TopLevel) scriptable).getBuiltinPrototype(builtins)) != null) {
            return builtinPrototype;
        }
        if (builtins == Builtins.GeneratorFunction) {
            str = "__GeneratorFunction";
        } else {
            str = builtins.name();
        }
        return ScriptableObject.getClassPrototype(scriptable, str);
    }

    public static Function getNativeErrorCtor(Context context, Scriptable scriptable, NativeErrors nativeErrors) {
        BaseFunction nativeErrorCtor;
        if (!(scriptable instanceof TopLevel) || (nativeErrorCtor = ((TopLevel) scriptable).getNativeErrorCtor(nativeErrors)) == null) {
            return ScriptRuntime.getExistingCtor(context, scriptable, nativeErrors.name());
        }
        return nativeErrorCtor;
    }

    public void cacheBuiltins(Scriptable scriptable, boolean z) {
        this.ctors = new EnumMap<>(Builtins.class);
        for (Builtins builtins : Builtins.values()) {
            Object property = ScriptableObject.getProperty((Scriptable) this, builtins.name());
            if (property instanceof BaseFunction) {
                this.ctors.put(builtins, (BaseFunction) property);
            } else if (builtins == Builtins.GeneratorFunction) {
                this.ctors.put(builtins, (BaseFunction) BaseFunction.initAsGeneratorFunction(scriptable, z));
            }
        }
        this.errors = new EnumMap<>(NativeErrors.class);
        for (NativeErrors nativeErrors : NativeErrors.values()) {
            Object property2 = ScriptableObject.getProperty((Scriptable) this, nativeErrors.name());
            if (property2 instanceof BaseFunction) {
                this.errors.put(nativeErrors, (BaseFunction) property2);
            }
        }
    }

    public String getClassName() {
        return "global";
    }

    public BaseFunction getNativeErrorCtor(NativeErrors nativeErrors) {
        EnumMap<NativeErrors, BaseFunction> enumMap = this.errors;
        if (enumMap != null) {
            return enumMap.get(nativeErrors);
        }
        return null;
    }

    public BaseFunction getBuiltinCtor(Builtins builtins) {
        EnumMap<Builtins, BaseFunction> enumMap = this.ctors;
        if (enumMap != null) {
            return enumMap.get(builtins);
        }
        return null;
    }

    public Scriptable getBuiltinPrototype(Builtins builtins) {
        BaseFunction builtinCtor = getBuiltinCtor(builtins);
        Object prototypeProperty = builtinCtor != null ? builtinCtor.getPrototypeProperty() : null;
        if (prototypeProperty instanceof Scriptable) {
            return (Scriptable) prototypeProperty;
        }
        return null;
    }
}
