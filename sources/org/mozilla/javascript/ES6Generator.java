package org.mozilla.javascript;

public final class ES6Generator extends IdScriptableObject {
    private static final Object GENERATOR_TAG = "Generator";
    private static final int Id_next = 1;
    private static final int Id_return = 2;
    private static final int Id_throw = 3;
    private static final int MAX_PROTOTYPE_ID = 4;
    private static final int SymbolId_iterator = 4;
    private static final long serialVersionUID = 1645892441041347273L;
    private Object delegee;
    private NativeFunction function;
    private int lineNumber;
    private String lineSource;
    private Object savedState;
    private State state = State.SUSPENDED_START;

    public enum State {
        SUSPENDED_START,
        SUSPENDED_YIELD,
        EXECUTING,
        COMPLETED
    }

    public static final class YieldStarResult {
        private Object result;

        public YieldStarResult(Object obj) {
            this.result = obj;
        }

        public Object getResult() {
            return this.result;
        }
    }

    private ES6Generator() {
    }

    private Object callReturnOptionally(Context context, Scriptable scriptable, Object obj) {
        Object[] objArr;
        Object obj2 = Undefined.instance;
        if (obj2.equals(obj)) {
            objArr = ScriptRuntime.emptyArgs;
        } else {
            objArr = new Object[]{obj};
        }
        Object objectPropNoWarn = ScriptRuntime.getObjectPropNoWarn(this.delegee, "return", context, scriptable);
        if (obj2.equals(objectPropNoWarn)) {
            return null;
        }
        if (objectPropNoWarn instanceof Callable) {
            return ((Callable) objectPropNoWarn).call(context, scriptable, ScriptableObject.ensureScriptable(this.delegee), objArr);
        }
        throw ScriptRuntime.typeError2("msg.isnt.function", "return", ScriptRuntime.typeof(objectPropNoWarn));
    }

    public static ES6Generator init(ScriptableObject scriptableObject, boolean z) {
        ES6Generator eS6Generator = new ES6Generator();
        if (scriptableObject != null) {
            eS6Generator.setParentScope(scriptableObject);
            eS6Generator.setPrototype(ScriptableObject.getObjectPrototype(scriptableObject));
        }
        eS6Generator.activatePrototypeMap(4);
        if (z) {
            eS6Generator.sealObject();
        }
        if (scriptableObject != null) {
            scriptableObject.associateValue(GENERATOR_TAG, eS6Generator);
        }
        return eS6Generator;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0071, code lost:
        if (r0 == r10) goto L_0x00c7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00a4, code lost:
        if (r1.state == r2) goto L_0x00c7;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private org.mozilla.javascript.Scriptable resumeAbruptLocal(org.mozilla.javascript.Context r14, org.mozilla.javascript.Scriptable r15, int r16, java.lang.Object r17) {
        /*
            r13 = this;
            r1 = r13
            r0 = r14
            r4 = r15
            r5 = r16
            r2 = r17
            java.lang.String r8 = "value"
            org.mozilla.javascript.ES6Generator$State r3 = r1.state
            org.mozilla.javascript.ES6Generator$State r6 = org.mozilla.javascript.ES6Generator.State.EXECUTING
            if (r3 == r6) goto L_0x00dd
            org.mozilla.javascript.ES6Generator$State r7 = org.mozilla.javascript.ES6Generator.State.SUSPENDED_START
            if (r3 != r7) goto L_0x0017
            org.mozilla.javascript.ES6Generator$State r3 = org.mozilla.javascript.ES6Generator.State.COMPLETED
            r1.state = r3
        L_0x0017:
            java.lang.Boolean r3 = java.lang.Boolean.FALSE
            org.mozilla.javascript.Scriptable r9 = org.mozilla.javascript.ES6Iterator.makeIteratorResult(r14, r15, r3)
            org.mozilla.javascript.ES6Generator$State r3 = r1.state
            org.mozilla.javascript.ES6Generator$State r10 = org.mozilla.javascript.ES6Generator.State.COMPLETED
            java.lang.String r11 = "done"
            if (r3 != r10) goto L_0x0038
            r0 = 1
            if (r5 == r0) goto L_0x002e
            java.lang.Boolean r0 = java.lang.Boolean.TRUE
            org.mozilla.javascript.ScriptableObject.putProperty((org.mozilla.javascript.Scriptable) r9, (java.lang.String) r11, (java.lang.Object) r0)
            return r9
        L_0x002e:
            org.mozilla.javascript.JavaScriptException r0 = new org.mozilla.javascript.JavaScriptException
            java.lang.String r3 = r1.lineSource
            int r4 = r1.lineNumber
            r0.<init>(r2, r3, r4)
            throw r0
        L_0x0038:
            r1.state = r6
            r3 = 2
            if (r5 != r3) goto L_0x0047
            boolean r3 = r2 instanceof org.mozilla.javascript.NativeGenerator.GeneratorClosedException
            if (r3 != 0) goto L_0x005c
            org.mozilla.javascript.NativeGenerator$GeneratorClosedException r2 = new org.mozilla.javascript.NativeGenerator$GeneratorClosedException
            r2.<init>()
            goto L_0x005c
        L_0x0047:
            boolean r3 = r2 instanceof org.mozilla.javascript.JavaScriptException
            if (r3 == 0) goto L_0x0052
            org.mozilla.javascript.JavaScriptException r2 = (org.mozilla.javascript.JavaScriptException) r2
            java.lang.Object r2 = r2.getValue()
            goto L_0x005c
        L_0x0052:
            boolean r3 = r2 instanceof org.mozilla.javascript.RhinoException
            if (r3 == 0) goto L_0x005c
            java.lang.Throwable r2 = (java.lang.Throwable) r2
            org.mozilla.javascript.Scriptable r2 = org.mozilla.javascript.ScriptRuntime.wrapException(r2, r15, r14)
        L_0x005c:
            r7 = r2
            r12 = 0
            org.mozilla.javascript.NativeFunction r2 = r1.function     // Catch:{ GeneratorClosedException -> 0x00c3, JavaScriptException -> 0x0088, RhinoException -> 0x0076 }
            java.lang.Object r6 = r1.savedState     // Catch:{ GeneratorClosedException -> 0x00c3, JavaScriptException -> 0x0088, RhinoException -> 0x0076 }
            r3 = r14
            r4 = r15
            r5 = r16
            java.lang.Object r0 = r2.resumeGenerator(r3, r4, r5, r6, r7)     // Catch:{ GeneratorClosedException -> 0x00c3, JavaScriptException -> 0x0088, RhinoException -> 0x0076 }
            org.mozilla.javascript.ScriptableObject.putProperty((org.mozilla.javascript.Scriptable) r9, (java.lang.String) r8, (java.lang.Object) r0)     // Catch:{ GeneratorClosedException -> 0x00c3, JavaScriptException -> 0x0088, RhinoException -> 0x0076 }
            org.mozilla.javascript.ES6Generator$State r0 = org.mozilla.javascript.ES6Generator.State.SUSPENDED_YIELD     // Catch:{ GeneratorClosedException -> 0x00c3, JavaScriptException -> 0x0088, RhinoException -> 0x0076 }
            r1.state = r0     // Catch:{ GeneratorClosedException -> 0x00c3, JavaScriptException -> 0x0088, RhinoException -> 0x0076 }
            if (r0 != r10) goto L_0x00ce
            goto L_0x00c7
        L_0x0074:
            r0 = move-exception
            goto L_0x00cf
        L_0x0076:
            r0 = move-exception
            org.mozilla.javascript.ES6Generator$State r2 = org.mozilla.javascript.ES6Generator.State.COMPLETED     // Catch:{ all -> 0x0074 }
            r1.state = r2     // Catch:{ all -> 0x0074 }
            int r2 = r0.lineNumber()     // Catch:{ all -> 0x0074 }
            r1.lineNumber = r2     // Catch:{ all -> 0x0074 }
            java.lang.String r2 = r0.lineSource()     // Catch:{ all -> 0x0074 }
            r1.lineSource = r2     // Catch:{ all -> 0x0074 }
            throw r0     // Catch:{ all -> 0x0074 }
        L_0x0088:
            r0 = move-exception
            org.mozilla.javascript.ES6Generator$State r2 = org.mozilla.javascript.ES6Generator.State.COMPLETED     // Catch:{ all -> 0x0074 }
            r1.state = r2     // Catch:{ all -> 0x0074 }
            java.lang.Object r3 = r0.getValue()     // Catch:{ all -> 0x0074 }
            boolean r3 = r3 instanceof org.mozilla.javascript.NativeIterator.StopIteration     // Catch:{ all -> 0x0074 }
            if (r3 == 0) goto L_0x00a7
            java.lang.Object r0 = r0.getValue()     // Catch:{ all -> 0x0074 }
            org.mozilla.javascript.NativeIterator$StopIteration r0 = (org.mozilla.javascript.NativeIterator.StopIteration) r0     // Catch:{ all -> 0x0074 }
            java.lang.Object r0 = r0.getValue()     // Catch:{ all -> 0x0074 }
            org.mozilla.javascript.ScriptableObject.putProperty((org.mozilla.javascript.Scriptable) r9, (java.lang.String) r8, (java.lang.Object) r0)     // Catch:{ all -> 0x0074 }
            org.mozilla.javascript.ES6Generator$State r0 = r1.state
            if (r0 != r2) goto L_0x00ce
            goto L_0x00c7
        L_0x00a7:
            int r2 = r0.lineNumber()     // Catch:{ all -> 0x0074 }
            r1.lineNumber = r2     // Catch:{ all -> 0x0074 }
            java.lang.String r2 = r0.lineSource()     // Catch:{ all -> 0x0074 }
            r1.lineSource = r2     // Catch:{ all -> 0x0074 }
            java.lang.Object r2 = r0.getValue()     // Catch:{ all -> 0x0074 }
            boolean r2 = r2 instanceof org.mozilla.javascript.RhinoException     // Catch:{ all -> 0x0074 }
            if (r2 == 0) goto L_0x00c2
            java.lang.Object r0 = r0.getValue()     // Catch:{ all -> 0x0074 }
            org.mozilla.javascript.RhinoException r0 = (org.mozilla.javascript.RhinoException) r0     // Catch:{ all -> 0x0074 }
            throw r0     // Catch:{ all -> 0x0074 }
        L_0x00c2:
            throw r0     // Catch:{ all -> 0x0074 }
        L_0x00c3:
            org.mozilla.javascript.ES6Generator$State r0 = org.mozilla.javascript.ES6Generator.State.COMPLETED     // Catch:{ all -> 0x0074 }
            r1.state = r0     // Catch:{ all -> 0x0074 }
        L_0x00c7:
            r1.delegee = r12
            java.lang.Boolean r0 = java.lang.Boolean.TRUE
            org.mozilla.javascript.ScriptableObject.putProperty((org.mozilla.javascript.Scriptable) r9, (java.lang.String) r11, (java.lang.Object) r0)
        L_0x00ce:
            return r9
        L_0x00cf:
            org.mozilla.javascript.ES6Generator$State r2 = r1.state
            org.mozilla.javascript.ES6Generator$State r3 = org.mozilla.javascript.ES6Generator.State.COMPLETED
            if (r2 != r3) goto L_0x00dc
            r1.delegee = r12
            java.lang.Boolean r2 = java.lang.Boolean.TRUE
            org.mozilla.javascript.ScriptableObject.putProperty((org.mozilla.javascript.Scriptable) r9, (java.lang.String) r11, (java.lang.Object) r2)
        L_0x00dc:
            throw r0
        L_0x00dd:
            java.lang.String r0 = "msg.generator.executing"
            org.mozilla.javascript.EcmaError r0 = org.mozilla.javascript.ScriptRuntime.typeError0(r0)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.ES6Generator.resumeAbruptLocal(org.mozilla.javascript.Context, org.mozilla.javascript.Scriptable, int, java.lang.Object):org.mozilla.javascript.Scriptable");
    }

    private Scriptable resumeDelegee(Context context, Scriptable scriptable, Object obj) {
        Object[] objArr;
        try {
            if (Undefined.instance.equals(obj)) {
                objArr = ScriptRuntime.emptyArgs;
            } else {
                objArr = new Object[]{obj};
            }
            Scriptable ensureScriptable = ScriptableObject.ensureScriptable(ScriptRuntime.getPropFunctionAndThis(this.delegee, ES6Iterator.NEXT_METHOD, context, scriptable).call(context, scriptable, ScriptRuntime.lastStoredScriptable(context), objArr));
            if (!ScriptRuntime.isIteratorDone(context, ensureScriptable)) {
                return ensureScriptable;
            }
            this.delegee = null;
            return resumeLocal(context, scriptable, ScriptableObject.getProperty(ensureScriptable, ES6Iterator.VALUE_PROPERTY));
        } catch (RhinoException e) {
            this.delegee = null;
            return resumeAbruptLocal(context, scriptable, 1, e);
        }
    }

    private Scriptable resumeDelegeeReturn(Context context, Scriptable scriptable, Object obj) {
        try {
            Object callReturnOptionally = callReturnOptionally(context, scriptable, obj);
            if (callReturnOptionally == null) {
                this.delegee = null;
                return resumeAbruptLocal(context, scriptable, 2, obj);
            } else if (!ScriptRuntime.isIteratorDone(context, callReturnOptionally)) {
                return ScriptableObject.ensureScriptable(callReturnOptionally);
            } else {
                this.delegee = null;
                return resumeAbruptLocal(context, scriptable, 2, ScriptRuntime.getObjectPropNoWarn(callReturnOptionally, ES6Iterator.VALUE_PROPERTY, context, scriptable));
            }
        } catch (RhinoException e) {
            this.delegee = null;
            return resumeAbruptLocal(context, scriptable, 1, e);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x003e A[SYNTHETIC, Splitter:B:19:0x003e] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private org.mozilla.javascript.Scriptable resumeDelegeeThrow(org.mozilla.javascript.Context r7, org.mozilla.javascript.Scriptable r8, java.lang.Object r9) {
        /*
            r6 = this;
            r0 = 1
            r1 = 0
            r2 = 0
            java.lang.Object r3 = r6.delegee     // Catch:{ RhinoException -> 0x003b }
            java.lang.String r4 = "throw"
            org.mozilla.javascript.Callable r3 = org.mozilla.javascript.ScriptRuntime.getPropFunctionAndThis(r3, r4, r7, r8)     // Catch:{ RhinoException -> 0x003b }
            org.mozilla.javascript.Scriptable r4 = org.mozilla.javascript.ScriptRuntime.lastStoredScriptable(r7)     // Catch:{ RhinoException -> 0x003b }
            java.lang.Object[] r5 = new java.lang.Object[r0]     // Catch:{ RhinoException -> 0x003b }
            r5[r2] = r9     // Catch:{ RhinoException -> 0x003b }
            java.lang.Object r9 = r3.call(r7, r8, r4, r5)     // Catch:{ RhinoException -> 0x003b }
            boolean r3 = org.mozilla.javascript.ScriptRuntime.isIteratorDone(r7, r9)     // Catch:{ RhinoException -> 0x003b }
            if (r3 == 0) goto L_0x0036
            java.lang.Object r2 = org.mozilla.javascript.Undefined.instance     // Catch:{ all -> 0x002f }
            r6.callReturnOptionally(r7, r8, r2)     // Catch:{ all -> 0x002f }
            r6.delegee = r1     // Catch:{ RhinoException -> 0x0033 }
            java.lang.String r2 = "value"
            java.lang.Object r9 = org.mozilla.javascript.ScriptRuntime.getObjectProp(r9, r2, r7, r8)     // Catch:{ RhinoException -> 0x0033 }
            org.mozilla.javascript.Scriptable r7 = r6.resumeLocal(r7, r8, r9)     // Catch:{ RhinoException -> 0x0033 }
            return r7
        L_0x002f:
            r9 = move-exception
            r6.delegee = r1     // Catch:{ RhinoException -> 0x0033 }
            throw r9     // Catch:{ RhinoException -> 0x0033 }
        L_0x0033:
            r9 = move-exception
            r2 = 1
            goto L_0x003c
        L_0x0036:
            org.mozilla.javascript.Scriptable r7 = org.mozilla.javascript.ScriptableObject.ensureScriptable(r9)     // Catch:{ RhinoException -> 0x003b }
            return r7
        L_0x003b:
            r9 = move-exception
        L_0x003c:
            if (r2 != 0) goto L_0x0051
            java.lang.Object r2 = org.mozilla.javascript.Undefined.instance     // Catch:{ RhinoException -> 0x0046 }
            r6.callReturnOptionally(r7, r8, r2)     // Catch:{ RhinoException -> 0x0046 }
            goto L_0x0051
        L_0x0044:
            r7 = move-exception
            goto L_0x004e
        L_0x0046:
            r9 = move-exception
            org.mozilla.javascript.Scriptable r7 = r6.resumeAbruptLocal(r7, r8, r0, r9)     // Catch:{ all -> 0x0044 }
            r6.delegee = r1
            return r7
        L_0x004e:
            r6.delegee = r1
            throw r7
        L_0x0051:
            r6.delegee = r1
            org.mozilla.javascript.Scriptable r7 = r6.resumeAbruptLocal(r7, r8, r0, r9)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.ES6Generator.resumeDelegeeThrow(org.mozilla.javascript.Context, org.mozilla.javascript.Scriptable, java.lang.Object):org.mozilla.javascript.Scriptable");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x005a, code lost:
        r12 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:?, code lost:
        r11.state = org.mozilla.javascript.ES6Generator.State.EXECUTING;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x005f, code lost:
        throw r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x007c, code lost:
        if (r11.state == r3) goto L_0x00d3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x007f, code lost:
        r11.state = org.mozilla.javascript.ES6Generator.State.SUSPENDED_YIELD;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0086, code lost:
        r12 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:?, code lost:
        r11.lineNumber = r12.lineNumber();
        r11.lineSource = r12.lineSource();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x0093, code lost:
        throw r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x0094, code lost:
        r12 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x0095, code lost:
        r13 = org.mozilla.javascript.ES6Generator.State.COMPLETED;
        r11.state = r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x009f, code lost:
        if ((r12.getValue() instanceof org.mozilla.javascript.NativeIterator.StopIteration) != false) goto L_0x00a1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00a1, code lost:
        org.mozilla.javascript.ScriptableObject.putProperty(r2, org.mozilla.javascript.ES6Iterator.VALUE_PROPERTY, ((org.mozilla.javascript.NativeIterator.StopIteration) r12.getValue()).getValue());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00b0, code lost:
        if (r11.state == r13) goto L_0x00d3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:?, code lost:
        r11.lineNumber = r12.lineNumber();
        r11.lineSource = r12.lineSource();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00c5, code lost:
        if ((r12.getValue() instanceof org.mozilla.javascript.RhinoException) != false) goto L_0x00c7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00cd, code lost:
        throw ((org.mozilla.javascript.RhinoException) r12.getValue());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00ce, code lost:
        throw r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00cf, code lost:
        r11.state = org.mozilla.javascript.ES6Generator.State.COMPLETED;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x00d3, code lost:
        org.mozilla.javascript.ScriptableObject.putProperty(r2, org.mozilla.javascript.ES6Iterator.DONE_PROPERTY, (java.lang.Object) java.lang.Boolean.TRUE);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x00d8, code lost:
        return r2;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:58:? A[ExcHandler: GeneratorClosedException (unused org.mozilla.javascript.NativeGenerator$GeneratorClosedException), SYNTHETIC, Splitter:B:7:0x001d] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private org.mozilla.javascript.Scriptable resumeLocal(org.mozilla.javascript.Context r12, org.mozilla.javascript.Scriptable r13, java.lang.Object r14) {
        /*
            r11 = this;
            java.lang.String r0 = "value"
            java.lang.String r1 = "done"
            org.mozilla.javascript.ES6Generator$State r2 = r11.state
            org.mozilla.javascript.ES6Generator$State r3 = org.mozilla.javascript.ES6Generator.State.COMPLETED
            if (r2 != r3) goto L_0x0011
            java.lang.Boolean r14 = java.lang.Boolean.TRUE
            org.mozilla.javascript.Scriptable r12 = org.mozilla.javascript.ES6Iterator.makeIteratorResult(r12, r13, r14)
            return r12
        L_0x0011:
            org.mozilla.javascript.ES6Generator$State r4 = org.mozilla.javascript.ES6Generator.State.EXECUTING
            if (r2 == r4) goto L_0x00ea
            java.lang.Boolean r2 = java.lang.Boolean.FALSE
            org.mozilla.javascript.Scriptable r2 = org.mozilla.javascript.ES6Iterator.makeIteratorResult(r12, r13, r2)
            r11.state = r4
            org.mozilla.javascript.NativeFunction r5 = r11.function     // Catch:{ GeneratorClosedException -> 0x00cf, JavaScriptException -> 0x0094, RhinoException -> 0x0086 }
            r8 = 0
            java.lang.Object r9 = r11.savedState     // Catch:{ GeneratorClosedException -> 0x00cf, JavaScriptException -> 0x0094, RhinoException -> 0x0086 }
            r6 = r12
            r7 = r13
            r10 = r14
            java.lang.Object r14 = r5.resumeGenerator(r6, r7, r8, r9, r10)     // Catch:{ GeneratorClosedException -> 0x00cf, JavaScriptException -> 0x0094, RhinoException -> 0x0086 }
            boolean r5 = r14 instanceof org.mozilla.javascript.ES6Generator.YieldStarResult     // Catch:{ GeneratorClosedException -> 0x00cf, JavaScriptException -> 0x0094, RhinoException -> 0x0086 }
            if (r5 == 0) goto L_0x0077
            org.mozilla.javascript.ES6Generator$State r5 = org.mozilla.javascript.ES6Generator.State.SUSPENDED_YIELD     // Catch:{ GeneratorClosedException -> 0x00cf, JavaScriptException -> 0x0094, RhinoException -> 0x0086 }
            r11.state = r5     // Catch:{ GeneratorClosedException -> 0x00cf, JavaScriptException -> 0x0094, RhinoException -> 0x0086 }
            org.mozilla.javascript.ES6Generator$YieldStarResult r14 = (org.mozilla.javascript.ES6Generator.YieldStarResult) r14     // Catch:{ GeneratorClosedException -> 0x00cf, JavaScriptException -> 0x0094, RhinoException -> 0x0086 }
            java.lang.Object r14 = r14.getResult()     // Catch:{ RhinoException -> 0x0060, GeneratorClosedException -> 0x00cf }
            java.lang.Object r14 = org.mozilla.javascript.ScriptRuntime.callIterator(r14, r12, r13)     // Catch:{ RhinoException -> 0x0060, GeneratorClosedException -> 0x00cf }
            r11.delegee = r14     // Catch:{ RhinoException -> 0x0060, GeneratorClosedException -> 0x00cf }
            java.lang.Object r14 = org.mozilla.javascript.Undefined.instance     // Catch:{ all -> 0x005a }
            org.mozilla.javascript.Scriptable r13 = r11.resumeDelegee(r12, r13, r14)     // Catch:{ all -> 0x005a }
            r11.state = r4     // Catch:{ GeneratorClosedException -> 0x00cf, JavaScriptException -> 0x0094, RhinoException -> 0x0086 }
            boolean r12 = org.mozilla.javascript.ScriptRuntime.isIteratorDone(r12, r13)     // Catch:{ GeneratorClosedException -> 0x00cf, JavaScriptException -> 0x0094, RhinoException -> 0x0086 }
            if (r12 == 0) goto L_0x004d
            r11.state = r3     // Catch:{ GeneratorClosedException -> 0x00cf, JavaScriptException -> 0x0094, RhinoException -> 0x0086 }
        L_0x004d:
            org.mozilla.javascript.ES6Generator$State r12 = r11.state
            if (r12 != r3) goto L_0x0057
            java.lang.Boolean r12 = java.lang.Boolean.TRUE
            org.mozilla.javascript.ScriptableObject.putProperty((org.mozilla.javascript.Scriptable) r2, (java.lang.String) r1, (java.lang.Object) r12)
            goto L_0x0059
        L_0x0057:
            r11.state = r5
        L_0x0059:
            return r13
        L_0x005a:
            r12 = move-exception
            org.mozilla.javascript.ES6Generator$State r13 = org.mozilla.javascript.ES6Generator.State.EXECUTING     // Catch:{ GeneratorClosedException -> 0x00cf, JavaScriptException -> 0x0094, RhinoException -> 0x0086 }
            r11.state = r13     // Catch:{ GeneratorClosedException -> 0x00cf, JavaScriptException -> 0x0094, RhinoException -> 0x0086 }
            throw r12     // Catch:{ GeneratorClosedException -> 0x00cf, JavaScriptException -> 0x0094, RhinoException -> 0x0086 }
        L_0x0060:
            r14 = move-exception
            r3 = 1
            org.mozilla.javascript.Scriptable r12 = r11.resumeAbruptLocal(r12, r13, r3, r14)     // Catch:{ GeneratorClosedException -> 0x00cf, JavaScriptException -> 0x0094, RhinoException -> 0x0086 }
            org.mozilla.javascript.ES6Generator$State r13 = r11.state
            org.mozilla.javascript.ES6Generator$State r14 = org.mozilla.javascript.ES6Generator.State.COMPLETED
            if (r13 != r14) goto L_0x0072
            java.lang.Boolean r13 = java.lang.Boolean.TRUE
            org.mozilla.javascript.ScriptableObject.putProperty((org.mozilla.javascript.Scriptable) r2, (java.lang.String) r1, (java.lang.Object) r13)
            goto L_0x0076
        L_0x0072:
            org.mozilla.javascript.ES6Generator$State r13 = org.mozilla.javascript.ES6Generator.State.SUSPENDED_YIELD
            r11.state = r13
        L_0x0076:
            return r12
        L_0x0077:
            org.mozilla.javascript.ScriptableObject.putProperty((org.mozilla.javascript.Scriptable) r2, (java.lang.String) r0, (java.lang.Object) r14)     // Catch:{ GeneratorClosedException -> 0x00cf, JavaScriptException -> 0x0094, RhinoException -> 0x0086 }
            org.mozilla.javascript.ES6Generator$State r12 = r11.state
            if (r12 != r3) goto L_0x007f
            goto L_0x00d3
        L_0x007f:
            org.mozilla.javascript.ES6Generator$State r12 = org.mozilla.javascript.ES6Generator.State.SUSPENDED_YIELD
            r11.state = r12
            goto L_0x00d8
        L_0x0084:
            r12 = move-exception
            goto L_0x00d9
        L_0x0086:
            r12 = move-exception
            int r13 = r12.lineNumber()     // Catch:{ all -> 0x0084 }
            r11.lineNumber = r13     // Catch:{ all -> 0x0084 }
            java.lang.String r13 = r12.lineSource()     // Catch:{ all -> 0x0084 }
            r11.lineSource = r13     // Catch:{ all -> 0x0084 }
            throw r12     // Catch:{ all -> 0x0084 }
        L_0x0094:
            r12 = move-exception
            org.mozilla.javascript.ES6Generator$State r13 = org.mozilla.javascript.ES6Generator.State.COMPLETED     // Catch:{ all -> 0x0084 }
            r11.state = r13     // Catch:{ all -> 0x0084 }
            java.lang.Object r14 = r12.getValue()     // Catch:{ all -> 0x0084 }
            boolean r14 = r14 instanceof org.mozilla.javascript.NativeIterator.StopIteration     // Catch:{ all -> 0x0084 }
            if (r14 == 0) goto L_0x00b3
            java.lang.Object r12 = r12.getValue()     // Catch:{ all -> 0x0084 }
            org.mozilla.javascript.NativeIterator$StopIteration r12 = (org.mozilla.javascript.NativeIterator.StopIteration) r12     // Catch:{ all -> 0x0084 }
            java.lang.Object r12 = r12.getValue()     // Catch:{ all -> 0x0084 }
            org.mozilla.javascript.ScriptableObject.putProperty((org.mozilla.javascript.Scriptable) r2, (java.lang.String) r0, (java.lang.Object) r12)     // Catch:{ all -> 0x0084 }
            org.mozilla.javascript.ES6Generator$State r12 = r11.state
            if (r12 != r13) goto L_0x007f
            goto L_0x00d3
        L_0x00b3:
            int r13 = r12.lineNumber()     // Catch:{ all -> 0x0084 }
            r11.lineNumber = r13     // Catch:{ all -> 0x0084 }
            java.lang.String r13 = r12.lineSource()     // Catch:{ all -> 0x0084 }
            r11.lineSource = r13     // Catch:{ all -> 0x0084 }
            java.lang.Object r13 = r12.getValue()     // Catch:{ all -> 0x0084 }
            boolean r13 = r13 instanceof org.mozilla.javascript.RhinoException     // Catch:{ all -> 0x0084 }
            if (r13 == 0) goto L_0x00ce
            java.lang.Object r12 = r12.getValue()     // Catch:{ all -> 0x0084 }
            org.mozilla.javascript.RhinoException r12 = (org.mozilla.javascript.RhinoException) r12     // Catch:{ all -> 0x0084 }
            throw r12     // Catch:{ all -> 0x0084 }
        L_0x00ce:
            throw r12     // Catch:{ all -> 0x0084 }
        L_0x00cf:
            org.mozilla.javascript.ES6Generator$State r12 = org.mozilla.javascript.ES6Generator.State.COMPLETED     // Catch:{ all -> 0x0084 }
            r11.state = r12     // Catch:{ all -> 0x0084 }
        L_0x00d3:
            java.lang.Boolean r12 = java.lang.Boolean.TRUE
            org.mozilla.javascript.ScriptableObject.putProperty((org.mozilla.javascript.Scriptable) r2, (java.lang.String) r1, (java.lang.Object) r12)
        L_0x00d8:
            return r2
        L_0x00d9:
            org.mozilla.javascript.ES6Generator$State r13 = r11.state
            org.mozilla.javascript.ES6Generator$State r14 = org.mozilla.javascript.ES6Generator.State.COMPLETED
            if (r13 != r14) goto L_0x00e5
            java.lang.Boolean r13 = java.lang.Boolean.TRUE
            org.mozilla.javascript.ScriptableObject.putProperty((org.mozilla.javascript.Scriptable) r2, (java.lang.String) r1, (java.lang.Object) r13)
            goto L_0x00e9
        L_0x00e5:
            org.mozilla.javascript.ES6Generator$State r13 = org.mozilla.javascript.ES6Generator.State.SUSPENDED_YIELD
            r11.state = r13
        L_0x00e9:
            throw r12
        L_0x00ea:
            java.lang.String r12 = "msg.generator.executing"
            org.mozilla.javascript.EcmaError r12 = org.mozilla.javascript.ScriptRuntime.typeError0(r12)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.ES6Generator.resumeLocal(org.mozilla.javascript.Context, org.mozilla.javascript.Scriptable, java.lang.Object):org.mozilla.javascript.Scriptable");
    }

    public Object execIdCall(IdFunctionObject idFunctionObject, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        Object obj;
        if (!idFunctionObject.hasTag(GENERATOR_TAG)) {
            return super.execIdCall(idFunctionObject, context, scriptable, scriptable2, objArr);
        }
        int methodId = idFunctionObject.methodId();
        if (scriptable2 instanceof ES6Generator) {
            ES6Generator eS6Generator = (ES6Generator) scriptable2;
            if (objArr.length >= 1) {
                obj = objArr[0];
            } else {
                obj = Undefined.instance;
            }
            if (methodId != 1) {
                if (methodId != 2) {
                    if (methodId != 3) {
                        if (methodId == 4) {
                            return scriptable2;
                        }
                        throw new IllegalArgumentException(String.valueOf(methodId));
                    } else if (eS6Generator.delegee == null) {
                        return eS6Generator.resumeAbruptLocal(context, scriptable, 1, obj);
                    } else {
                        return eS6Generator.resumeDelegeeThrow(context, scriptable, obj);
                    }
                } else if (eS6Generator.delegee == null) {
                    return eS6Generator.resumeAbruptLocal(context, scriptable, 2, obj);
                } else {
                    return eS6Generator.resumeDelegeeReturn(context, scriptable, obj);
                }
            } else if (eS6Generator.delegee == null) {
                return eS6Generator.resumeLocal(context, scriptable, obj);
            } else {
                return eS6Generator.resumeDelegee(context, scriptable, obj);
            }
        } else {
            throw IdScriptableObject.incompatibleCallError(idFunctionObject);
        }
    }

    public int findPrototypeId(Symbol symbol) {
        return SymbolKey.ITERATOR.equals(symbol) ? 4 : 0;
    }

    public String getClassName() {
        return "Generator";
    }

    public void initPrototypeId(int i) {
        String str;
        if (i == 4) {
            initPrototypeMethod(GENERATOR_TAG, i, (Symbol) SymbolKey.ITERATOR, "[Symbol.iterator]", 0);
            return;
        }
        if (i == 1) {
            str = ES6Iterator.NEXT_METHOD;
        } else if (i == 2) {
            str = "return";
        } else if (i == 3) {
            str = "throw";
        } else {
            throw new IllegalArgumentException(String.valueOf(i));
        }
        initPrototypeMethod(GENERATOR_TAG, i, str, 1);
    }

    public int findPrototypeId(String str) {
        int i;
        String str2;
        int length = str.length();
        if (length == 4) {
            str2 = ES6Iterator.NEXT_METHOD;
            i = 1;
        } else if (length == 5) {
            str2 = "throw";
            i = 3;
        } else if (length == 6) {
            str2 = "return";
            i = 2;
        } else {
            str2 = null;
            i = 0;
        }
        if (str2 == null || str2 == str || str2.equals(str)) {
            return i;
        }
        return 0;
    }

    public ES6Generator(Scriptable scriptable, NativeFunction nativeFunction, Object obj) {
        this.function = nativeFunction;
        this.savedState = obj;
        Scriptable topLevelScope = ScriptableObject.getTopLevelScope(scriptable);
        setParentScope(topLevelScope);
        setPrototype((ES6Generator) ScriptableObject.getTopScopeValue(topLevelScope, GENERATOR_TAG));
    }
}
