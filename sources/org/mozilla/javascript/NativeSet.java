package org.mozilla.javascript;

import java.util.Iterator;
import org.mozilla.javascript.Hashtable;
import org.mozilla.javascript.NativeCollectionIterator;

public class NativeSet extends IdScriptableObject {
    static final SymbolKey GETSIZE = new SymbolKey("[Symbol.getSize]");
    static final String ITERATOR_TAG = "Set Iterator";
    private static final int Id_add = 2;
    private static final int Id_clear = 5;
    private static final int Id_constructor = 1;
    private static final int Id_delete = 3;
    private static final int Id_entries = 7;
    private static final int Id_forEach = 8;
    private static final int Id_has = 4;
    private static final int Id_keys = 6;
    private static final int Id_values = 6;
    private static final int MAX_PROTOTYPE_ID = 10;
    private static final Object SET_TAG = "Set";
    private static final int SymbolId_getSize = 9;
    private static final int SymbolId_toStringTag = 10;
    private static final long serialVersionUID = -8442212766987072986L;
    private final Hashtable entries = new Hashtable();
    private boolean instanceOfSet = false;

    public static void init(Context context, Scriptable scriptable, boolean z) {
        NativeSet nativeSet = new NativeSet();
        nativeSet.exportAsJSClass(10, scriptable, false);
        ScriptableObject scriptableObject = (ScriptableObject) context.newObject(scriptable);
        scriptableObject.put("enumerable", (Scriptable) scriptableObject, (Object) Boolean.FALSE);
        scriptableObject.put("configurable", (Scriptable) scriptableObject, (Object) Boolean.TRUE);
        scriptableObject.put("get", (Scriptable) scriptableObject, nativeSet.get((Symbol) GETSIZE, (Scriptable) nativeSet));
        nativeSet.defineOwnProperty(context, "size", scriptableObject);
        if (z) {
            nativeSet.sealObject();
        }
    }

    private Object js_add(Object obj) {
        if ((obj instanceof Number) && ((Number) obj).doubleValue() == ScriptRuntime.negativeZero) {
            obj = ScriptRuntime.zeroObj;
        }
        this.entries.put(obj, obj);
        return this;
    }

    private Object js_clear() {
        this.entries.clear();
        return Undefined.instance;
    }

    private Object js_delete(Object obj) {
        boolean z;
        if (this.entries.delete(obj) != null) {
            z = true;
        } else {
            z = false;
        }
        return Boolean.valueOf(z);
    }

    private Object js_forEach(Context context, Scriptable scriptable, Object obj, Object obj2) {
        if (obj instanceof Callable) {
            Callable callable = (Callable) obj;
            boolean isStrictMode = context.isStrictMode();
            Iterator<Hashtable.Entry> it = this.entries.iterator();
            while (it.hasNext()) {
                Scriptable objectOrNull = ScriptRuntime.toObjectOrNull(context, obj2, scriptable);
                if (objectOrNull == null && !isStrictMode) {
                    objectOrNull = scriptable;
                }
                if (objectOrNull == null) {
                    objectOrNull = Undefined.SCRIPTABLE_UNDEFINED;
                }
                Object obj3 = it.next().value;
                callable.call(context, scriptable, objectOrNull, new Object[]{obj3, obj3, this});
            }
            return Undefined.instance;
        }
        throw ScriptRuntime.notFunctionError(obj);
    }

    private Object js_getSize() {
        return Integer.valueOf(this.entries.size());
    }

    private Object js_has(Object obj) {
        return Boolean.valueOf(this.entries.has(obj));
    }

    private Object js_iterator(Scriptable scriptable, NativeCollectionIterator.Type type) {
        return new NativeCollectionIterator(scriptable, ITERATOR_TAG, type, this.entries.iterator());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0058, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x005d, code lost:
        r7 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x005e, code lost:
        r5.addSuppressed(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0061, code lost:
        throw r6;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void loadFromIterable(org.mozilla.javascript.Context r5, org.mozilla.javascript.Scriptable r6, org.mozilla.javascript.ScriptableObject r7, java.lang.Object r8) {
        /*
            if (r8 == 0) goto L_0x0062
            java.lang.Object r0 = org.mozilla.javascript.Undefined.instance
            boolean r1 = r0.equals(r8)
            if (r1 == 0) goto L_0x000b
            goto L_0x0062
        L_0x000b:
            java.lang.Object r8 = org.mozilla.javascript.ScriptRuntime.callIterator(r8, r5, r6)
            boolean r0 = r0.equals(r8)
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            java.lang.String r0 = r7.getClassName()
            org.mozilla.javascript.Scriptable r0 = r5.newObject(r6, r0)
            org.mozilla.javascript.ScriptableObject r0 = org.mozilla.javascript.ScriptableObject.ensureScriptableObject(r0)
            org.mozilla.javascript.Scriptable r0 = r0.getPrototype()
            java.lang.String r1 = "add"
            org.mozilla.javascript.Callable r0 = org.mozilla.javascript.ScriptRuntime.getPropFunctionAndThis(r0, r1, r5, r6)
            org.mozilla.javascript.ScriptRuntime.lastStoredScriptable(r5)
            org.mozilla.javascript.IteratorLikeIterable r1 = new org.mozilla.javascript.IteratorLikeIterable
            r1.<init>(r5, r6, r8)
            org.mozilla.javascript.IteratorLikeIterable$Itr r8 = r1.iterator()     // Catch:{ all -> 0x0056 }
        L_0x0038:
            boolean r2 = r8.hasNext()     // Catch:{ all -> 0x0056 }
            if (r2 == 0) goto L_0x0052
            java.lang.Object r2 = r8.next()     // Catch:{ all -> 0x0056 }
            java.lang.Object r3 = org.mozilla.javascript.Scriptable.NOT_FOUND     // Catch:{ all -> 0x0056 }
            if (r2 != r3) goto L_0x0048
            java.lang.Object r2 = org.mozilla.javascript.Undefined.instance     // Catch:{ all -> 0x0056 }
        L_0x0048:
            r3 = 1
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x0056 }
            r4 = 0
            r3[r4] = r2     // Catch:{ all -> 0x0056 }
            r0.call(r5, r6, r7, r3)     // Catch:{ all -> 0x0056 }
            goto L_0x0038
        L_0x0052:
            r1.close()
            return
        L_0x0056:
            r5 = move-exception
            throw r5     // Catch:{ all -> 0x0058 }
        L_0x0058:
            r6 = move-exception
            r1.close()     // Catch:{ all -> 0x005d }
            goto L_0x0061
        L_0x005d:
            r7 = move-exception
            r5.addSuppressed(r7)
        L_0x0061:
            throw r6
        L_0x0062:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NativeSet.loadFromIterable(org.mozilla.javascript.Context, org.mozilla.javascript.Scriptable, org.mozilla.javascript.ScriptableObject, java.lang.Object):void");
    }

    private static NativeSet realThis(Scriptable scriptable, IdFunctionObject idFunctionObject) {
        if (scriptable != null) {
            try {
                NativeSet nativeSet = (NativeSet) scriptable;
                if (nativeSet.instanceOfSet) {
                    return nativeSet;
                }
                throw IdScriptableObject.incompatibleCallError(idFunctionObject);
            } catch (ClassCastException unused) {
                throw IdScriptableObject.incompatibleCallError(idFunctionObject);
            }
        } else {
            throw IdScriptableObject.incompatibleCallError(idFunctionObject);
        }
    }

    public Object execIdCall(IdFunctionObject idFunctionObject, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        Object obj;
        Object obj2;
        Object obj3;
        Object obj4;
        Object obj5;
        if (!idFunctionObject.hasTag(SET_TAG)) {
            return super.execIdCall(idFunctionObject, context, scriptable, scriptable2, objArr);
        }
        switch (idFunctionObject.methodId()) {
            case 1:
                if (scriptable2 == null) {
                    NativeSet nativeSet = new NativeSet();
                    nativeSet.instanceOfSet = true;
                    if (objArr.length > 0) {
                        loadFromIterable(context, scriptable, nativeSet, objArr[0]);
                    }
                    return nativeSet;
                }
                throw ScriptRuntime.typeError1("msg.no.new", "Set");
            case 2:
                NativeSet realThis = realThis(scriptable2, idFunctionObject);
                if (objArr.length > 0) {
                    obj = objArr[0];
                } else {
                    obj = Undefined.instance;
                }
                return realThis.js_add(obj);
            case 3:
                NativeSet realThis2 = realThis(scriptable2, idFunctionObject);
                if (objArr.length > 0) {
                    obj2 = objArr[0];
                } else {
                    obj2 = Undefined.instance;
                }
                return realThis2.js_delete(obj2);
            case 4:
                NativeSet realThis3 = realThis(scriptable2, idFunctionObject);
                if (objArr.length > 0) {
                    obj3 = objArr[0];
                } else {
                    obj3 = Undefined.instance;
                }
                return realThis3.js_has(obj3);
            case 5:
                return realThis(scriptable2, idFunctionObject).js_clear();
            case 6:
                return realThis(scriptable2, idFunctionObject).js_iterator(scriptable, NativeCollectionIterator.Type.VALUES);
            case 7:
                return realThis(scriptable2, idFunctionObject).js_iterator(scriptable, NativeCollectionIterator.Type.BOTH);
            case 8:
                NativeSet realThis4 = realThis(scriptable2, idFunctionObject);
                if (objArr.length > 0) {
                    obj4 = objArr[0];
                } else {
                    obj4 = Undefined.instance;
                }
                if (objArr.length > 1) {
                    obj5 = objArr[1];
                } else {
                    obj5 = Undefined.instance;
                }
                return realThis4.js_forEach(context, scriptable, obj4, obj5);
            case 9:
                return realThis(scriptable2, idFunctionObject).js_getSize();
            default:
                throw new IllegalArgumentException("Set.prototype has no method: " + idFunctionObject.getFunctionName());
        }
    }

    public int findPrototypeId(Symbol symbol) {
        if (GETSIZE.equals(symbol)) {
            return 9;
        }
        if (SymbolKey.ITERATOR.equals(symbol)) {
            return 6;
        }
        return SymbolKey.TO_STRING_TAG.equals(symbol) ? 10 : 0;
    }

    public String getClassName() {
        return "Set";
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x002c, code lost:
        r4 = r1;
        r6 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0031, code lost:
        r4 = r0;
        r6 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0033, code lost:
        initPrototypeMethod(SET_TAG, r10, r4, (java.lang.String) null, r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x003a, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void initPrototypeId(int r10) {
        /*
            r9 = this;
            r0 = 9
            if (r10 == r0) goto L_0x0046
            r0 = 10
            if (r10 == r0) goto L_0x003b
            r5 = 0
            r0 = 1
            r1 = 0
            switch(r10) {
                case 1: goto L_0x002f;
                case 2: goto L_0x002a;
                case 3: goto L_0x0027;
                case 4: goto L_0x0024;
                case 5: goto L_0x0021;
                case 6: goto L_0x001e;
                case 7: goto L_0x001b;
                case 8: goto L_0x0018;
                default: goto L_0x000e;
            }
        L_0x000e:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r10 = java.lang.String.valueOf(r10)
            r0.<init>(r10)
            throw r0
        L_0x0018:
            java.lang.String r1 = "forEach"
            goto L_0x002c
        L_0x001b:
            java.lang.String r0 = "entries"
            goto L_0x0031
        L_0x001e:
            java.lang.String r0 = "values"
            goto L_0x0031
        L_0x0021:
            java.lang.String r0 = "clear"
            goto L_0x0031
        L_0x0024:
            java.lang.String r1 = "has"
            goto L_0x002c
        L_0x0027:
            java.lang.String r1 = "delete"
            goto L_0x002c
        L_0x002a:
            java.lang.String r1 = "add"
        L_0x002c:
            r4 = r1
            r6 = 1
            goto L_0x0033
        L_0x002f:
            java.lang.String r0 = "constructor"
        L_0x0031:
            r4 = r0
            r6 = 0
        L_0x0033:
            java.lang.Object r2 = SET_TAG
            r1 = r9
            r3 = r10
            r1.initPrototypeMethod((java.lang.Object) r2, (int) r3, (java.lang.String) r4, (java.lang.String) r5, (int) r6)
            return
        L_0x003b:
            org.mozilla.javascript.SymbolKey r10 = org.mozilla.javascript.SymbolKey.TO_STRING_TAG
            java.lang.String r1 = r9.getClassName()
            r2 = 3
            r9.initPrototypeValue((int) r0, (org.mozilla.javascript.Symbol) r10, (java.lang.Object) r1, (int) r2)
            return
        L_0x0046:
            java.lang.Object r4 = SET_TAG
            org.mozilla.javascript.SymbolKey r6 = GETSIZE
            java.lang.String r7 = "get size"
            r8 = 0
            r3 = r9
            r5 = r10
            r3.initPrototypeMethod((java.lang.Object) r4, (int) r5, (org.mozilla.javascript.Symbol) r6, (java.lang.String) r7, (int) r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NativeSet.initPrototypeId(int):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:42:0x007a A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:46:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int findPrototypeId(java.lang.String r8) {
        /*
            r7 = this;
            int r0 = r8.length()
            r1 = 4
            r2 = 3
            r3 = 1
            r4 = 100
            r5 = 0
            if (r0 == r2) goto L_0x004c
            r6 = 6
            if (r0 == r1) goto L_0x0048
            r1 = 5
            if (r0 == r1) goto L_0x0045
            if (r0 == r6) goto L_0x0034
            r1 = 7
            if (r0 == r1) goto L_0x0020
            r1 = 11
            if (r0 == r1) goto L_0x001c
            goto L_0x0076
        L_0x001c:
            java.lang.String r0 = "constructor"
            r1 = 1
            goto L_0x0078
        L_0x0020:
            char r0 = r8.charAt(r5)
            r2 = 101(0x65, float:1.42E-43)
            if (r0 != r2) goto L_0x002b
            java.lang.String r0 = "entries"
            goto L_0x0078
        L_0x002b:
            r1 = 102(0x66, float:1.43E-43)
            if (r0 != r1) goto L_0x0076
            java.lang.String r0 = "forEach"
            r1 = 8
            goto L_0x0078
        L_0x0034:
            char r0 = r8.charAt(r5)
            if (r0 != r4) goto L_0x003e
            java.lang.String r0 = "delete"
            r1 = 3
            goto L_0x0078
        L_0x003e:
            r1 = 118(0x76, float:1.65E-43)
            if (r0 != r1) goto L_0x0076
            java.lang.String r0 = "values"
            goto L_0x004a
        L_0x0045:
            java.lang.String r0 = "clear"
            goto L_0x0078
        L_0x0048:
            java.lang.String r0 = "keys"
        L_0x004a:
            r1 = 6
            goto L_0x0078
        L_0x004c:
            char r0 = r8.charAt(r5)
            r2 = 97
            r6 = 2
            if (r0 != r2) goto L_0x0063
            char r0 = r8.charAt(r6)
            if (r0 != r4) goto L_0x0076
            char r0 = r8.charAt(r3)
            if (r0 != r4) goto L_0x0076
            r1 = 2
            goto L_0x0083
        L_0x0063:
            r4 = 104(0x68, float:1.46E-43)
            if (r0 != r4) goto L_0x0076
            char r0 = r8.charAt(r6)
            r4 = 115(0x73, float:1.61E-43)
            if (r0 != r4) goto L_0x0076
            char r0 = r8.charAt(r3)
            if (r0 != r2) goto L_0x0076
            goto L_0x0083
        L_0x0076:
            r0 = 0
            r1 = 0
        L_0x0078:
            if (r0 == 0) goto L_0x0083
            if (r0 == r8) goto L_0x0083
            boolean r8 = r0.equals(r8)
            if (r8 != 0) goto L_0x0083
            r1 = 0
        L_0x0083:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NativeSet.findPrototypeId(java.lang.String):int");
    }
}
