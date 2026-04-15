package org.mozilla.javascript;

import java.util.Iterator;

public final class NativeIterator extends IdScriptableObject {
    public static final String ITERATOR_PROPERTY_NAME = "__iterator__";
    private static final Object ITERATOR_TAG = "Iterator";
    private static final int Id___iterator__ = 3;
    private static final int Id_constructor = 1;
    private static final int Id_next = 2;
    private static final int MAX_PROTOTYPE_ID = 3;
    private static final String STOP_ITERATION = "StopIteration";
    private static final long serialVersionUID = -4136968203581667681L;
    private Object objectIterator;

    public static class WrappedJavaIterator {
        private Iterator<?> iterator;
        private Scriptable scope;

        public WrappedJavaIterator(Iterator<?> it, Scriptable scriptable) {
            this.iterator = it;
            this.scope = scriptable;
        }

        public Object __iterator__(boolean z) {
            return this;
        }

        public Object next() {
            if (this.iterator.hasNext()) {
                return this.iterator.next();
            }
            throw new JavaScriptException(NativeIterator.getStopIterationObject(this.scope), (String) null, 0);
        }
    }

    private NativeIterator() {
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: java.util.Iterator<?>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.util.Iterator<?> getJavaIterator(java.lang.Object r2) {
        /*
            boolean r0 = r2 instanceof org.mozilla.javascript.Wrapper
            r1 = 0
            if (r0 == 0) goto L_0x001c
            org.mozilla.javascript.Wrapper r2 = (org.mozilla.javascript.Wrapper) r2
            java.lang.Object r2 = r2.unwrap()
            boolean r0 = r2 instanceof java.util.Iterator
            if (r0 == 0) goto L_0x0012
            r1 = r2
            java.util.Iterator r1 = (java.util.Iterator) r1
        L_0x0012:
            boolean r0 = r2 instanceof java.lang.Iterable
            if (r0 == 0) goto L_0x001c
            java.lang.Iterable r2 = (java.lang.Iterable) r2
            java.util.Iterator r1 = r2.iterator()
        L_0x001c:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NativeIterator.getJavaIterator(java.lang.Object):java.util.Iterator");
    }

    public static Object getStopIterationObject(Scriptable scriptable) {
        return ScriptableObject.getTopScopeValue(ScriptableObject.getTopLevelScope(scriptable), ITERATOR_TAG);
    }

    public static void init(Context context, ScriptableObject scriptableObject, boolean z) {
        new NativeIterator().exportAsJSClass(3, scriptableObject, z);
        if (context.getLanguageVersion() >= 200) {
            ES6Generator.init(scriptableObject, z);
        } else {
            NativeGenerator.init(scriptableObject, z);
        }
        StopIteration stopIteration = new StopIteration();
        stopIteration.setPrototype(ScriptableObject.getObjectPrototype(scriptableObject));
        stopIteration.setParentScope(scriptableObject);
        if (z) {
            stopIteration.sealObject();
        }
        ScriptableObject.defineProperty(scriptableObject, STOP_ITERATION, stopIteration, 2);
        scriptableObject.associateValue(ITERATOR_TAG, stopIteration);
    }

    private static Object jsConstructor(Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        Object obj;
        Object obj2;
        int i;
        boolean z = false;
        if (objArr.length == 0 || (obj2 = objArr[0]) == null || obj2 == Undefined.instance) {
            if (objArr.length == 0) {
                obj = Undefined.instance;
            } else {
                obj = objArr[0];
            }
            throw ScriptRuntime.typeError1("msg.no.properties", ScriptRuntime.toString(obj));
        }
        Scriptable object = ScriptRuntime.toObject(context, scriptable, obj2);
        if (objArr.length > 1 && ScriptRuntime.toBoolean(objArr[1])) {
            z = true;
        }
        if (scriptable2 != null) {
            Iterator<?> javaIterator = getJavaIterator(object);
            if (javaIterator != null) {
                Scriptable topLevelScope = ScriptableObject.getTopLevelScope(scriptable);
                return context.getWrapFactory().wrap(context, topLevelScope, new WrappedJavaIterator(javaIterator, topLevelScope), WrappedJavaIterator.class);
            }
            Scriptable iterator = ScriptRuntime.toIterator(context, scriptable, object, z);
            if (iterator != null) {
                return iterator;
            }
        }
        if (z) {
            i = 3;
        } else {
            i = 5;
        }
        Object enumInit = ScriptRuntime.enumInit(object, context, scriptable, i);
        ScriptRuntime.setEnumNumbers(enumInit, true);
        NativeIterator nativeIterator = new NativeIterator(enumInit);
        nativeIterator.setPrototype(ScriptableObject.getClassPrototype(scriptable, nativeIterator.getClassName()));
        nativeIterator.setParentScope(scriptable);
        return nativeIterator;
    }

    private Object next(Context context, Scriptable scriptable) {
        if (ScriptRuntime.enumNext(this.objectIterator).booleanValue()) {
            return ScriptRuntime.enumId(this.objectIterator, context);
        }
        throw new JavaScriptException(getStopIterationObject(scriptable), (String) null, 0);
    }

    public Object execIdCall(IdFunctionObject idFunctionObject, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        if (!idFunctionObject.hasTag(ITERATOR_TAG)) {
            return super.execIdCall(idFunctionObject, context, scriptable, scriptable2, objArr);
        }
        int methodId = idFunctionObject.methodId();
        if (methodId == 1) {
            return jsConstructor(context, scriptable, scriptable2, objArr);
        }
        if (scriptable2 instanceof NativeIterator) {
            NativeIterator nativeIterator = (NativeIterator) scriptable2;
            if (methodId == 2) {
                return nativeIterator.next(context, scriptable);
            }
            if (methodId == 3) {
                return scriptable2;
            }
            throw new IllegalArgumentException(String.valueOf(methodId));
        }
        throw IdScriptableObject.incompatibleCallError(idFunctionObject);
    }

    public int findPrototypeId(String str) {
        int i;
        String str2;
        int length = str.length();
        if (length == 4) {
            str2 = ES6Iterator.NEXT_METHOD;
            i = 2;
        } else if (length == 11) {
            str2 = "constructor";
            i = 1;
        } else if (length == 12) {
            str2 = ITERATOR_PROPERTY_NAME;
            i = 3;
        } else {
            str2 = null;
            i = 0;
        }
        if (str2 == null || str2 == str || str2.equals(str)) {
            return i;
        }
        return 0;
    }

    public String getClassName() {
        return "Iterator";
    }

    public void initPrototypeId(int i) {
        String str;
        int i2 = 1;
        if (i == 1) {
            str = "constructor";
            i2 = 2;
        } else if (i == 2) {
            str = ES6Iterator.NEXT_METHOD;
            i2 = 0;
        } else if (i == 3) {
            str = ITERATOR_PROPERTY_NAME;
        } else {
            throw new IllegalArgumentException(String.valueOf(i));
        }
        initPrototypeMethod(ITERATOR_TAG, i, str, i2);
    }

    public static class StopIteration extends NativeObject {
        private static final long serialVersionUID = 2485151085722377663L;
        private Object value;

        public StopIteration() {
            this.value = Undefined.instance;
        }

        public String getClassName() {
            return NativeIterator.STOP_ITERATION;
        }

        public Object getValue() {
            return this.value;
        }

        public boolean hasInstance(Scriptable scriptable) {
            return scriptable instanceof StopIteration;
        }

        public StopIteration(Object obj) {
            Object obj2 = Undefined.instance;
            this.value = obj;
        }
    }

    private NativeIterator(Object obj) {
        this.objectIterator = obj;
    }
}
