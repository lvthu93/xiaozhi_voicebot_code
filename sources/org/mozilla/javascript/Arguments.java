package org.mozilla.javascript;

import org.mozilla.javascript.NativeArrayIterator;

final class Arguments extends IdScriptableObject {
    private static final String FTAG = "Arguments";
    private static final int Id_callee = 1;
    private static final int Id_caller = 3;
    private static final int Id_length = 2;
    private static final int MAX_INSTANCE_ID = 3;
    private static BaseFunction iteratorMethod = new BaseFunction() {
        private static final long serialVersionUID = 4239122318596177391L;

        public Object call(Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
            return new NativeArrayIterator(scriptable, scriptable2, NativeArrayIterator.ARRAY_ITERATOR_TYPE.VALUES);
        }
    };
    private static final long serialVersionUID = 4275508002492040609L;
    private NativeCall activation;
    private Object[] args;
    private int calleeAttr = 2;
    private Object calleeObj;
    private int callerAttr = 2;
    private Object callerObj;
    private int lengthAttr = 2;
    private Object lengthObj;

    public static class ThrowTypeError extends BaseFunction {
        private static final long serialVersionUID = -744615873947395749L;
        private String propertyName;

        public ThrowTypeError(String str) {
            this.propertyName = str;
        }

        public Object call(Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
            throw ScriptRuntime.typeError1("msg.arguments.not.access.strict", this.propertyName);
        }
    }

    public Arguments(NativeCall nativeCall) {
        this.activation = nativeCall;
        Scriptable parentScope = nativeCall.getParentScope();
        setParentScope(parentScope);
        setPrototype(ScriptableObject.getObjectPrototype(parentScope));
        Object[] objArr = nativeCall.originalArgs;
        this.args = objArr;
        this.lengthObj = Integer.valueOf(objArr.length);
        NativeFunction nativeFunction = nativeCall.function;
        this.calleeObj = nativeFunction;
        int languageVersion = nativeFunction.getLanguageVersion();
        if (languageVersion > 130 || languageVersion == 0) {
            this.callerObj = Scriptable.NOT_FOUND;
        } else {
            this.callerObj = null;
        }
        defineProperty((Symbol) SymbolKey.ITERATOR, (Object) iteratorMethod, 2);
    }

    private Object arg(int i) {
        if (i >= 0) {
            Object[] objArr = this.args;
            if (objArr.length > i) {
                return objArr[i];
            }
        }
        return Scriptable.NOT_FOUND;
    }

    private Object getFromActivation(int i) {
        String paramOrVarName = this.activation.function.getParamOrVarName(i);
        NativeCall nativeCall = this.activation;
        return nativeCall.get(paramOrVarName, (Scriptable) nativeCall);
    }

    private void putIntoActivation(int i, Object obj) {
        String paramOrVarName = this.activation.function.getParamOrVarName(i);
        NativeCall nativeCall = this.activation;
        nativeCall.put(paramOrVarName, (Scriptable) nativeCall, obj);
    }

    private void removeArg(int i) {
        synchronized (this) {
            Object[] objArr = this.args;
            Object obj = objArr[i];
            Object obj2 = Scriptable.NOT_FOUND;
            if (obj != obj2) {
                if (objArr == this.activation.originalArgs) {
                    this.args = (Object[]) objArr.clone();
                }
                this.args[i] = obj2;
            }
        }
    }

    private void replaceArg(int i, Object obj) {
        if (sharedWithActivation(i)) {
            putIntoActivation(i, obj);
        }
        synchronized (this) {
            Object[] objArr = this.args;
            if (objArr == this.activation.originalArgs) {
                this.args = (Object[]) objArr.clone();
            }
            this.args[i] = obj;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x000c, code lost:
        r0 = r6.activation.function;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean sharedWithActivation(int r7) {
        /*
            r6 = this;
            org.mozilla.javascript.Context r0 = org.mozilla.javascript.Context.getContext()
            boolean r0 = r0.isStrictMode()
            r1 = 0
            if (r0 == 0) goto L_0x000c
            return r1
        L_0x000c:
            org.mozilla.javascript.NativeCall r0 = r6.activation
            org.mozilla.javascript.NativeFunction r0 = r0.function
            int r2 = r0.getParamCount()
            if (r7 >= r2) goto L_0x0031
            int r3 = r2 + -1
            r4 = 1
            if (r7 >= r3) goto L_0x0030
            java.lang.String r3 = r0.getParamOrVarName(r7)
            int r7 = r7 + r4
        L_0x0020:
            if (r7 >= r2) goto L_0x0030
            java.lang.String r5 = r0.getParamOrVarName(r7)
            boolean r5 = r3.equals(r5)
            if (r5 == 0) goto L_0x002d
            return r1
        L_0x002d:
            int r7 = r7 + 1
            goto L_0x0020
        L_0x0030:
            return r4
        L_0x0031:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.Arguments.sharedWithActivation(int):boolean");
    }

    public void defineAttributesForStrictMode() {
        if (Context.getContext().isStrictMode()) {
            setGetterOrSetter("caller", 0, new ThrowTypeError("caller"), true);
            setGetterOrSetter("caller", 0, new ThrowTypeError("caller"), false);
            setGetterOrSetter("callee", 0, new ThrowTypeError("callee"), true);
            setGetterOrSetter("callee", 0, new ThrowTypeError("callee"), false);
            setAttributes("caller", 6);
            setAttributes("callee", 6);
            this.callerObj = null;
            this.calleeObj = null;
        }
    }

    public void defineOwnProperty(Context context, Object obj, ScriptableObject scriptableObject, boolean z) {
        Object obj2;
        super.defineOwnProperty(context, obj, scriptableObject, z);
        if (!ScriptRuntime.isSymbol(obj)) {
            double number = ScriptRuntime.toNumber(obj);
            int i = (int) number;
            if (number != ((double) i) || arg(i) == (obj2 = Scriptable.NOT_FOUND)) {
                return;
            }
            if (isAccessorDescriptor(scriptableObject)) {
                removeArg(i);
                return;
            }
            Object property = ScriptableObject.getProperty((Scriptable) scriptableObject, ES6Iterator.VALUE_PROPERTY);
            if (property != obj2) {
                replaceArg(i, property);
                if (ScriptableObject.isFalse(ScriptableObject.getProperty((Scriptable) scriptableObject, "writable"))) {
                    removeArg(i);
                }
            }
        }
    }

    public void delete(int i) {
        if (i >= 0 && i < this.args.length) {
            removeArg(i);
        }
        super.delete(i);
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x004b  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0050  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int findInstanceIdInfo(java.lang.String r7) {
        /*
            r6 = this;
            int r0 = r7.length()
            r1 = 6
            r2 = 2
            r3 = 3
            r4 = 1
            r5 = 0
            if (r0 != r1) goto L_0x0028
            r0 = 5
            char r0 = r7.charAt(r0)
            r1 = 101(0x65, float:1.42E-43)
            if (r0 != r1) goto L_0x0018
            java.lang.String r0 = "callee"
            r1 = 1
            goto L_0x002a
        L_0x0018:
            r1 = 104(0x68, float:1.46E-43)
            if (r0 != r1) goto L_0x0020
            java.lang.String r0 = "length"
            r1 = 2
            goto L_0x002a
        L_0x0020:
            r1 = 114(0x72, float:1.6E-43)
            if (r0 != r1) goto L_0x0028
            java.lang.String r0 = "caller"
            r1 = 3
            goto L_0x002a
        L_0x0028:
            r0 = 0
            r1 = 0
        L_0x002a:
            if (r0 == 0) goto L_0x0035
            if (r0 == r7) goto L_0x0035
            boolean r0 = r0.equals(r7)
            if (r0 != 0) goto L_0x0035
            goto L_0x0036
        L_0x0035:
            r5 = r1
        L_0x0036:
            org.mozilla.javascript.Context r0 = org.mozilla.javascript.Context.getContext()
            boolean r0 = r0.isStrictMode()
            if (r0 == 0) goto L_0x0049
            if (r5 == r4) goto L_0x0044
            if (r5 != r3) goto L_0x0049
        L_0x0044:
            int r7 = super.findInstanceIdInfo((java.lang.String) r7)
            return r7
        L_0x0049:
            if (r5 != 0) goto L_0x0050
            int r7 = super.findInstanceIdInfo((java.lang.String) r7)
            return r7
        L_0x0050:
            if (r5 == r4) goto L_0x0062
            if (r5 == r2) goto L_0x005f
            if (r5 != r3) goto L_0x0059
            int r7 = r6.callerAttr
            goto L_0x0064
        L_0x0059:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            r7.<init>()
            throw r7
        L_0x005f:
            int r7 = r6.lengthAttr
            goto L_0x0064
        L_0x0062:
            int r7 = r6.calleeAttr
        L_0x0064:
            int r7 = org.mozilla.javascript.IdScriptableObject.instanceIdInfo(r7, r5)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.Arguments.findInstanceIdInfo(java.lang.String):int");
    }

    public Object get(int i, Scriptable scriptable) {
        Object arg = arg(i);
        if (arg == Scriptable.NOT_FOUND) {
            return super.get(i, scriptable);
        }
        if (sharedWithActivation(i)) {
            return getFromActivation(i);
        }
        return arg;
    }

    public String getClassName() {
        return FTAG;
    }

    public Object[] getIds(boolean z, boolean z2) {
        int intValue;
        Object[] ids = super.getIds(z, z2);
        Object[] objArr = this.args;
        if (objArr.length == 0) {
            return ids;
        }
        int length = objArr.length;
        boolean[] zArr = new boolean[length];
        int length2 = objArr.length;
        for (int i = 0; i != ids.length; i++) {
            Object obj = ids[i];
            if ((obj instanceof Integer) && (intValue = ((Integer) obj).intValue()) >= 0 && intValue < this.args.length && !zArr[intValue]) {
                zArr[intValue] = true;
                length2--;
            }
        }
        if (!z) {
            for (int i2 = 0; i2 < length; i2++) {
                if (!zArr[i2] && super.has(i2, (Scriptable) this)) {
                    zArr[i2] = true;
                    length2--;
                }
            }
        }
        if (length2 == 0) {
            return ids;
        }
        Object[] objArr2 = new Object[(ids.length + length2)];
        System.arraycopy(ids, 0, objArr2, length2, ids.length);
        int i3 = 0;
        for (int i4 = 0; i4 != this.args.length; i4++) {
            if (!zArr[i4]) {
                objArr2[i3] = Integer.valueOf(i4);
                i3++;
            }
        }
        if (i3 != length2) {
            Kit.codeBug();
        }
        return objArr2;
    }

    public String getInstanceIdName(int i) {
        if (i == 1) {
            return "callee";
        }
        if (i == 2) {
            return "length";
        }
        if (i != 3) {
            return null;
        }
        return "caller";
    }

    public Object getInstanceIdValue(int i) {
        NativeCall nativeCall;
        if (i == 1) {
            return this.calleeObj;
        }
        if (i == 2) {
            return this.lengthObj;
        }
        if (i != 3) {
            return super.getInstanceIdValue(i);
        }
        Object obj = this.callerObj;
        if (obj == UniqueTag.NULL_VALUE) {
            return null;
        }
        if (obj != null || (nativeCall = this.activation.parentActivationCall) == null) {
            return obj;
        }
        return nativeCall.get("arguments", (Scriptable) nativeCall);
    }

    public int getMaxInstanceId() {
        return 3;
    }

    public ScriptableObject getOwnPropertyDescriptor(Context context, Object obj) {
        if (ScriptRuntime.isSymbol(obj) || (obj instanceof Scriptable)) {
            return super.getOwnPropertyDescriptor(context, obj);
        }
        double number = ScriptRuntime.toNumber(obj);
        int i = (int) number;
        if (number != ((double) i)) {
            return super.getOwnPropertyDescriptor(context, obj);
        }
        Object arg = arg(i);
        if (arg == Scriptable.NOT_FOUND) {
            return super.getOwnPropertyDescriptor(context, obj);
        }
        if (sharedWithActivation(i)) {
            arg = getFromActivation(i);
        }
        if (super.has(i, (Scriptable) this)) {
            ScriptableObject ownPropertyDescriptor = super.getOwnPropertyDescriptor(context, obj);
            ownPropertyDescriptor.put(ES6Iterator.VALUE_PROPERTY, (Scriptable) ownPropertyDescriptor, arg);
            return ownPropertyDescriptor;
        }
        Scriptable parentScope = getParentScope();
        if (parentScope == null) {
            parentScope = this;
        }
        return ScriptableObject.buildDataDescriptor(parentScope, arg, 0);
    }

    public boolean has(int i, Scriptable scriptable) {
        if (arg(i) != Scriptable.NOT_FOUND) {
            return true;
        }
        return super.has(i, scriptable);
    }

    public void put(int i, Scriptable scriptable, Object obj) {
        if (arg(i) == Scriptable.NOT_FOUND) {
            super.put(i, scriptable, obj);
        } else {
            replaceArg(i, obj);
        }
    }

    public void setInstanceIdAttributes(int i, int i2) {
        if (i == 1) {
            this.calleeAttr = i2;
        } else if (i == 2) {
            this.lengthAttr = i2;
        } else if (i != 3) {
            super.setInstanceIdAttributes(i, i2);
        } else {
            this.callerAttr = i2;
        }
    }

    public void setInstanceIdValue(int i, Object obj) {
        if (i == 1) {
            this.calleeObj = obj;
        } else if (i == 2) {
            this.lengthObj = obj;
        } else if (i != 3) {
            super.setInstanceIdValue(i, obj);
        } else {
            if (obj == null) {
                obj = UniqueTag.NULL_VALUE;
            }
            this.callerObj = obj;
        }
    }

    public void put(String str, Scriptable scriptable, Object obj) {
        super.put(str, scriptable, obj);
    }
}
