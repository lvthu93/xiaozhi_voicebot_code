package org.mozilla.javascript;

public final class NativeGenerator extends IdScriptableObject {
    public static final int GENERATOR_CLOSE = 2;
    public static final int GENERATOR_SEND = 0;
    private static final Object GENERATOR_TAG = "Generator";
    public static final int GENERATOR_THROW = 1;
    private static final int Id___iterator__ = 5;
    private static final int Id_close = 1;
    private static final int Id_next = 2;
    private static final int Id_send = 3;
    private static final int Id_throw = 4;
    private static final int MAX_PROTOTYPE_ID = 5;
    private static final long serialVersionUID = 1645892441041347273L;
    private boolean firstTime = true;
    private NativeFunction function;
    private int lineNumber;
    private String lineSource;
    private boolean locked;
    private Object savedState;

    public static class GeneratorClosedException extends RuntimeException {
        private static final long serialVersionUID = 2561315658662379681L;
    }

    private NativeGenerator() {
    }

    public static NativeGenerator init(ScriptableObject scriptableObject, boolean z) {
        NativeGenerator nativeGenerator = new NativeGenerator();
        if (scriptableObject != null) {
            nativeGenerator.setParentScope(scriptableObject);
            nativeGenerator.setPrototype(ScriptableObject.getObjectPrototype(scriptableObject));
        }
        nativeGenerator.activatePrototypeMap(5);
        if (z) {
            nativeGenerator.sealObject();
        }
        if (scriptableObject != null) {
            scriptableObject.associateValue(GENERATOR_TAG, nativeGenerator);
        }
        return nativeGenerator;
    }

    private Object resume(Context context, Scriptable scriptable, int i, Object obj) {
        if (this.savedState != null) {
            try {
                synchronized (this) {
                    if (!this.locked) {
                        this.locked = true;
                    } else {
                        throw ScriptRuntime.typeError0("msg.already.exec.gen");
                    }
                }
                Object resumeGenerator = this.function.resumeGenerator(context, scriptable, i, this.savedState, obj);
                synchronized (this) {
                    this.locked = false;
                }
                if (i == 2) {
                    this.savedState = null;
                }
                return resumeGenerator;
            } catch (GeneratorClosedException unused) {
                Object obj2 = Undefined.instance;
                synchronized (this) {
                    this.locked = false;
                    if (i == 2) {
                        this.savedState = null;
                    }
                    return obj2;
                }
            } catch (RhinoException e) {
                try {
                    this.lineNumber = e.lineNumber();
                    this.lineSource = e.lineSource();
                    this.savedState = null;
                    throw e;
                } catch (Throwable th) {
                    synchronized (this) {
                        this.locked = false;
                        if (i == 2) {
                            this.savedState = null;
                        }
                        throw th;
                    }
                }
            }
        } else if (i == 2) {
            return Undefined.instance;
        } else {
            if (i != 1) {
                obj = NativeIterator.getStopIterationObject(scriptable);
            }
            throw new JavaScriptException(obj, this.lineSource, this.lineNumber);
        }
    }

    public Object execIdCall(IdFunctionObject idFunctionObject, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        Object obj;
        Object obj2;
        if (!idFunctionObject.hasTag(GENERATOR_TAG)) {
            return super.execIdCall(idFunctionObject, context, scriptable, scriptable2, objArr);
        }
        int methodId = idFunctionObject.methodId();
        if (scriptable2 instanceof NativeGenerator) {
            NativeGenerator nativeGenerator = (NativeGenerator) scriptable2;
            if (methodId == 1) {
                return nativeGenerator.resume(context, scriptable, 2, new GeneratorClosedException());
            }
            if (methodId == 2) {
                nativeGenerator.firstTime = false;
                return nativeGenerator.resume(context, scriptable, 0, Undefined.instance);
            } else if (methodId == 3) {
                if (objArr.length > 0) {
                    obj = objArr[0];
                } else {
                    obj = Undefined.instance;
                }
                if (!nativeGenerator.firstTime || obj.equals(Undefined.instance)) {
                    return nativeGenerator.resume(context, scriptable, 0, obj);
                }
                throw ScriptRuntime.typeError0("msg.send.newborn");
            } else if (methodId == 4) {
                if (objArr.length > 0) {
                    obj2 = objArr[0];
                } else {
                    obj2 = Undefined.instance;
                }
                return nativeGenerator.resume(context, scriptable, 1, obj2);
            } else if (methodId == 5) {
                return scriptable2;
            } else {
                throw new IllegalArgumentException(String.valueOf(methodId));
            }
        } else {
            throw IdScriptableObject.incompatibleCallError(idFunctionObject);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x003e A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int findPrototypeId(java.lang.String r5) {
        /*
            r4 = this;
            int r0 = r5.length()
            r1 = 0
            r2 = 4
            if (r0 != r2) goto L_0x001c
            char r0 = r5.charAt(r1)
            r2 = 110(0x6e, float:1.54E-43)
            if (r0 != r2) goto L_0x0014
            java.lang.String r0 = "next"
            r2 = 2
            goto L_0x003c
        L_0x0014:
            r2 = 115(0x73, float:1.61E-43)
            if (r0 != r2) goto L_0x003a
            java.lang.String r0 = "send"
            r2 = 3
            goto L_0x003c
        L_0x001c:
            r3 = 5
            if (r0 != r3) goto L_0x0032
            char r0 = r5.charAt(r1)
            r3 = 99
            if (r0 != r3) goto L_0x002b
            java.lang.String r0 = "close"
            r2 = 1
            goto L_0x003c
        L_0x002b:
            r3 = 116(0x74, float:1.63E-43)
            if (r0 != r3) goto L_0x003a
            java.lang.String r0 = "throw"
            goto L_0x003c
        L_0x0032:
            r2 = 12
            if (r0 != r2) goto L_0x003a
            java.lang.String r0 = "__iterator__"
            r2 = 5
            goto L_0x003c
        L_0x003a:
            r0 = 0
            r2 = 0
        L_0x003c:
            if (r0 == 0) goto L_0x0047
            if (r0 == r5) goto L_0x0047
            boolean r5 = r0.equals(r5)
            if (r5 != 0) goto L_0x0047
            goto L_0x0048
        L_0x0047:
            r1 = r2
        L_0x0048:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NativeGenerator.findPrototypeId(java.lang.String):int");
    }

    public String getClassName() {
        return "Generator";
    }

    public void initPrototypeId(int i) {
        String str;
        int i2 = 1;
        if (i == 1) {
            str = "close";
        } else if (i != 2) {
            if (i == 3) {
                str = MqttServiceConstants.SEND_ACTION;
            } else if (i == 4) {
                str = "throw";
            } else if (i == 5) {
                str = NativeIterator.ITERATOR_PROPERTY_NAME;
            } else {
                throw new IllegalArgumentException(String.valueOf(i));
            }
            i2 = 0;
        } else {
            str = ES6Iterator.NEXT_METHOD;
        }
        initPrototypeMethod(GENERATOR_TAG, i, str, i2);
    }

    public NativeGenerator(Scriptable scriptable, NativeFunction nativeFunction, Object obj) {
        this.function = nativeFunction;
        this.savedState = obj;
        Scriptable topLevelScope = ScriptableObject.getTopLevelScope(scriptable);
        setParentScope(topLevelScope);
        setPrototype((NativeGenerator) ScriptableObject.getTopScopeValue(topLevelScope, GENERATOR_TAG));
    }
}
