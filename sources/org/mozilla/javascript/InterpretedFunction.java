package org.mozilla.javascript;

import org.mozilla.javascript.debug.DebuggableScript;

final class InterpretedFunction extends NativeFunction implements Script {
    private static final long serialVersionUID = 541475680333911468L;
    InterpreterData idata;
    SecurityController securityController;
    Object securityDomain;

    private InterpretedFunction(InterpreterData interpreterData, Object obj) {
        Object obj2;
        this.idata = interpreterData;
        SecurityController securityController2 = Context.getContext().getSecurityController();
        if (securityController2 != null) {
            obj2 = securityController2.getDynamicSecurityDomain(obj);
        } else if (obj == null) {
            obj2 = null;
        } else {
            throw new IllegalArgumentException();
        }
        this.securityController = securityController2;
        this.securityDomain = obj2;
    }

    public static InterpretedFunction createFunction(Context context, Scriptable scriptable, InterpreterData interpreterData, Object obj) {
        InterpretedFunction interpretedFunction = new InterpretedFunction(interpreterData, obj);
        interpretedFunction.initScriptFunction(context, scriptable, interpretedFunction.idata.isES6Generator);
        return interpretedFunction;
    }

    public static InterpretedFunction createScript(InterpreterData interpreterData, Object obj) {
        return new InterpretedFunction(interpreterData, obj);
    }

    public Object call(Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        if (ScriptRuntime.hasTopCall(context)) {
            return Interpreter.interpret(this, context, scriptable, scriptable2, objArr);
        }
        return ScriptRuntime.doTopCall(this, context, scriptable, scriptable2, objArr, this.idata.isStrict);
    }

    public Object exec(Context context, Scriptable scriptable) {
        if (!isScript()) {
            throw new IllegalStateException();
        } else if (ScriptRuntime.hasTopCall(context)) {
            return Interpreter.interpret(this, context, scriptable, scriptable, ScriptRuntime.emptyArgs);
        } else {
            return ScriptRuntime.doTopCall(this, context, scriptable, scriptable, ScriptRuntime.emptyArgs, this.idata.isStrict);
        }
    }

    public DebuggableScript getDebuggableView() {
        return this.idata;
    }

    public String getEncodedSource() {
        return Interpreter.getEncodedSource(this.idata);
    }

    public String getFunctionName() {
        String str = this.idata.itsName;
        return str == null ? "" : str;
    }

    public int getLanguageVersion() {
        return this.idata.languageVersion;
    }

    public int getParamAndVarCount() {
        return this.idata.argNames.length;
    }

    public int getParamCount() {
        return this.idata.argCount;
    }

    public boolean getParamOrVarConst(int i) {
        return this.idata.argIsConst[i];
    }

    public String getParamOrVarName(int i) {
        return this.idata.argNames[i];
    }

    public boolean hasFunctionNamed(String str) {
        for (int i = 0; i < this.idata.getFunctionCount(); i++) {
            InterpreterData interpreterData = (InterpreterData) this.idata.getFunction(i);
            if (!interpreterData.declaredAsFunctionExpression && str.equals(interpreterData.getFunctionName())) {
                return false;
            }
        }
        return true;
    }

    public boolean isScript() {
        return this.idata.itsFunctionType == 0;
    }

    public Object resumeGenerator(Context context, Scriptable scriptable, int i, Object obj, Object obj2) {
        return Interpreter.resumeGenerator(context, scriptable, i, obj, obj2);
    }

    public static InterpretedFunction createFunction(Context context, Scriptable scriptable, InterpretedFunction interpretedFunction, int i) {
        InterpretedFunction interpretedFunction2 = new InterpretedFunction(interpretedFunction, i);
        interpretedFunction2.initScriptFunction(context, scriptable, interpretedFunction2.idata.isES6Generator);
        return interpretedFunction2;
    }

    private InterpretedFunction(InterpretedFunction interpretedFunction, int i) {
        this.idata = interpretedFunction.idata.itsNestedFunctions[i];
        this.securityController = interpretedFunction.securityController;
        this.securityDomain = interpretedFunction.securityDomain;
    }
}
