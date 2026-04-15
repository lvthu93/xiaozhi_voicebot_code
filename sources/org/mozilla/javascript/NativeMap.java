package org.mozilla.javascript;

import java.util.Iterator;
import org.mozilla.javascript.Hashtable;
import org.mozilla.javascript.NativeCollectionIterator;

public class NativeMap extends IdScriptableObject {
    static final String ITERATOR_TAG = "Map Iterator";
    private static final int Id_clear = 6;
    private static final int Id_constructor = 1;
    private static final int Id_delete = 4;
    private static final int Id_entries = 9;
    private static final int Id_forEach = 10;
    private static final int Id_get = 3;
    private static final int Id_has = 5;
    private static final int Id_keys = 7;
    private static final int Id_set = 2;
    private static final int Id_values = 8;
    private static final Object MAP_TAG = "Map";
    private static final int MAX_PROTOTYPE_ID = 12;
    private static final Object NULL_VALUE = new Object();
    private static final int SymbolId_getSize = 11;
    private static final int SymbolId_toStringTag = 12;
    private static final long serialVersionUID = 1171922614280016891L;
    private final Hashtable entries = new Hashtable();
    private boolean instanceOfMap = false;

    public static void init(Context context, Scriptable scriptable, boolean z) {
        NativeMap nativeMap = new NativeMap();
        nativeMap.exportAsJSClass(12, scriptable, false);
        ScriptableObject scriptableObject = (ScriptableObject) context.newObject(scriptable);
        scriptableObject.put("enumerable", (Scriptable) scriptableObject, (Object) Boolean.FALSE);
        scriptableObject.put("configurable", (Scriptable) scriptableObject, (Object) Boolean.TRUE);
        scriptableObject.put("get", (Scriptable) scriptableObject, nativeMap.get((Symbol) NativeSet.GETSIZE, (Scriptable) nativeMap));
        nativeMap.defineOwnProperty(context, "size", scriptableObject);
        if (z) {
            nativeMap.sealObject();
        }
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
                Hashtable.Entry next = it.next();
                Object obj3 = next.value;
                if (obj3 == NULL_VALUE) {
                    obj3 = null;
                }
                callable.call(context, scriptable, objectOrNull, new Object[]{obj3, next.key, this});
            }
            return Undefined.instance;
        }
        throw ScriptRuntime.typeError2("msg.isnt.function", obj, ScriptRuntime.typeof(obj));
    }

    private Object js_get(Object obj) {
        Object obj2 = this.entries.get(obj);
        if (obj2 == null) {
            return Undefined.instance;
        }
        if (obj2 == NULL_VALUE) {
            return null;
        }
        return obj2;
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

    private Object js_set(Object obj, Object obj2) {
        if (obj2 == null) {
            obj2 = NULL_VALUE;
        }
        if ((obj instanceof Number) && ((Number) obj).doubleValue() == ScriptRuntime.negativeZero) {
            obj = ScriptRuntime.zeroObj;
        }
        this.entries.put(obj, obj2);
        return this;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:27:0x007b, code lost:
        r8 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0080, code lost:
        r9 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0081, code lost:
        r7.addSuppressed(r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0084, code lost:
        throw r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void loadFromIterable(org.mozilla.javascript.Context r7, org.mozilla.javascript.Scriptable r8, org.mozilla.javascript.ScriptableObject r9, java.lang.Object r10) {
        /*
            if (r10 == 0) goto L_0x0085
            java.lang.Object r0 = org.mozilla.javascript.Undefined.instance
            boolean r1 = r0.equals(r10)
            if (r1 == 0) goto L_0x000c
            goto L_0x0085
        L_0x000c:
            java.lang.Object r10 = org.mozilla.javascript.ScriptRuntime.callIterator(r10, r7, r8)
            boolean r0 = r0.equals(r10)
            if (r0 == 0) goto L_0x0017
            return
        L_0x0017:
            java.lang.String r0 = r9.getClassName()
            org.mozilla.javascript.Scriptable r0 = r7.newObject(r8, r0)
            org.mozilla.javascript.ScriptableObject r0 = org.mozilla.javascript.ScriptableObject.ensureScriptableObject(r0)
            org.mozilla.javascript.Scriptable r0 = r0.getPrototype()
            java.lang.String r1 = "set"
            org.mozilla.javascript.Callable r0 = org.mozilla.javascript.ScriptRuntime.getPropFunctionAndThis(r0, r1, r7, r8)
            org.mozilla.javascript.ScriptRuntime.lastStoredScriptable(r7)
            org.mozilla.javascript.IteratorLikeIterable r1 = new org.mozilla.javascript.IteratorLikeIterable
            r1.<init>(r7, r8, r10)
            org.mozilla.javascript.IteratorLikeIterable$Itr r10 = r1.iterator()     // Catch:{ all -> 0x0079 }
        L_0x0039:
            boolean r2 = r10.hasNext()     // Catch:{ all -> 0x0079 }
            if (r2 == 0) goto L_0x0075
            java.lang.Object r2 = r10.next()     // Catch:{ all -> 0x0079 }
            org.mozilla.javascript.Scriptable r2 = org.mozilla.javascript.ScriptableObject.ensureScriptable(r2)     // Catch:{ all -> 0x0079 }
            boolean r3 = r2 instanceof org.mozilla.javascript.Symbol     // Catch:{ all -> 0x0079 }
            if (r3 != 0) goto L_0x006a
            r3 = 0
            java.lang.Object r4 = r2.get((int) r3, (org.mozilla.javascript.Scriptable) r2)     // Catch:{ all -> 0x0079 }
            java.lang.Object r5 = org.mozilla.javascript.Scriptable.NOT_FOUND     // Catch:{ all -> 0x0079 }
            if (r4 != r5) goto L_0x0056
            java.lang.Object r4 = org.mozilla.javascript.Undefined.instance     // Catch:{ all -> 0x0079 }
        L_0x0056:
            r6 = 1
            java.lang.Object r2 = r2.get((int) r6, (org.mozilla.javascript.Scriptable) r2)     // Catch:{ all -> 0x0079 }
            if (r2 != r5) goto L_0x005f
            java.lang.Object r2 = org.mozilla.javascript.Undefined.instance     // Catch:{ all -> 0x0079 }
        L_0x005f:
            r5 = 2
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ all -> 0x0079 }
            r5[r3] = r4     // Catch:{ all -> 0x0079 }
            r5[r6] = r2     // Catch:{ all -> 0x0079 }
            r0.call(r7, r8, r9, r5)     // Catch:{ all -> 0x0079 }
            goto L_0x0039
        L_0x006a:
            java.lang.String r7 = "msg.arg.not.object"
            java.lang.String r8 = org.mozilla.javascript.ScriptRuntime.typeof(r2)     // Catch:{ all -> 0x0079 }
            org.mozilla.javascript.EcmaError r7 = org.mozilla.javascript.ScriptRuntime.typeError1(r7, r8)     // Catch:{ all -> 0x0079 }
            throw r7     // Catch:{ all -> 0x0079 }
        L_0x0075:
            r1.close()
            return
        L_0x0079:
            r7 = move-exception
            throw r7     // Catch:{ all -> 0x007b }
        L_0x007b:
            r8 = move-exception
            r1.close()     // Catch:{ all -> 0x0080 }
            goto L_0x0084
        L_0x0080:
            r9 = move-exception
            r7.addSuppressed(r9)
        L_0x0084:
            throw r8
        L_0x0085:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NativeMap.loadFromIterable(org.mozilla.javascript.Context, org.mozilla.javascript.Scriptable, org.mozilla.javascript.ScriptableObject, java.lang.Object):void");
    }

    private static NativeMap realThis(Scriptable scriptable, IdFunctionObject idFunctionObject) {
        if (scriptable != null) {
            try {
                NativeMap nativeMap = (NativeMap) scriptable;
                if (nativeMap.instanceOfMap) {
                    return nativeMap;
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
        Object obj6;
        Object obj7;
        if (!idFunctionObject.hasTag(MAP_TAG)) {
            return super.execIdCall(idFunctionObject, context, scriptable, scriptable2, objArr);
        }
        switch (idFunctionObject.methodId()) {
            case 1:
                if (scriptable2 == null) {
                    NativeMap nativeMap = new NativeMap();
                    nativeMap.instanceOfMap = true;
                    if (objArr.length > 0) {
                        loadFromIterable(context, scriptable, nativeMap, objArr[0]);
                    }
                    return nativeMap;
                }
                throw ScriptRuntime.typeError1("msg.no.new", "Map");
            case 2:
                NativeMap realThis = realThis(scriptable2, idFunctionObject);
                if (objArr.length > 0) {
                    obj = objArr[0];
                } else {
                    obj = Undefined.instance;
                }
                if (objArr.length > 1) {
                    obj2 = objArr[1];
                } else {
                    obj2 = Undefined.instance;
                }
                return realThis.js_set(obj, obj2);
            case 3:
                NativeMap realThis2 = realThis(scriptable2, idFunctionObject);
                if (objArr.length > 0) {
                    obj3 = objArr[0];
                } else {
                    obj3 = Undefined.instance;
                }
                return realThis2.js_get(obj3);
            case 4:
                NativeMap realThis3 = realThis(scriptable2, idFunctionObject);
                if (objArr.length > 0) {
                    obj4 = objArr[0];
                } else {
                    obj4 = Undefined.instance;
                }
                return realThis3.js_delete(obj4);
            case 5:
                NativeMap realThis4 = realThis(scriptable2, idFunctionObject);
                if (objArr.length > 0) {
                    obj5 = objArr[0];
                } else {
                    obj5 = Undefined.instance;
                }
                return realThis4.js_has(obj5);
            case 6:
                return realThis(scriptable2, idFunctionObject).js_clear();
            case 7:
                return realThis(scriptable2, idFunctionObject).js_iterator(scriptable, NativeCollectionIterator.Type.KEYS);
            case 8:
                return realThis(scriptable2, idFunctionObject).js_iterator(scriptable, NativeCollectionIterator.Type.VALUES);
            case 9:
                return realThis(scriptable2, idFunctionObject).js_iterator(scriptable, NativeCollectionIterator.Type.BOTH);
            case 10:
                NativeMap realThis5 = realThis(scriptable2, idFunctionObject);
                if (objArr.length > 0) {
                    obj6 = objArr[0];
                } else {
                    obj6 = Undefined.instance;
                }
                if (objArr.length > 1) {
                    obj7 = objArr[1];
                } else {
                    obj7 = Undefined.instance;
                }
                return realThis5.js_forEach(context, scriptable, obj6, obj7);
            case 11:
                return realThis(scriptable2, idFunctionObject).js_getSize();
            default:
                throw new IllegalArgumentException("Map.prototype has no method: " + idFunctionObject.getFunctionName());
        }
    }

    public int findPrototypeId(Symbol symbol) {
        if (NativeSet.GETSIZE.equals(symbol)) {
            return 11;
        }
        if (SymbolKey.ITERATOR.equals(symbol)) {
            return 9;
        }
        return SymbolKey.TO_STRING_TAG.equals(symbol) ? 12 : 0;
    }

    public String getClassName() {
        return "Map";
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x002f, code lost:
        r4 = r1;
        r6 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x003a, code lost:
        r4 = r0;
        r6 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x003c, code lost:
        initPrototypeMethod(MAP_TAG, r10, r4, (java.lang.String) null, r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0043, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void initPrototypeId(int r10) {
        /*
            r9 = this;
            r0 = 11
            if (r10 == r0) goto L_0x004f
            r0 = 12
            if (r10 == r0) goto L_0x0044
            r5 = 0
            r0 = 1
            r1 = 0
            switch(r10) {
                case 1: goto L_0x0038;
                case 2: goto L_0x0032;
                case 3: goto L_0x002d;
                case 4: goto L_0x002a;
                case 5: goto L_0x0027;
                case 6: goto L_0x0024;
                case 7: goto L_0x0021;
                case 8: goto L_0x001e;
                case 9: goto L_0x001b;
                case 10: goto L_0x0018;
                default: goto L_0x000e;
            }
        L_0x000e:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r10 = java.lang.String.valueOf(r10)
            r0.<init>(r10)
            throw r0
        L_0x0018:
            java.lang.String r1 = "forEach"
            goto L_0x002f
        L_0x001b:
            java.lang.String r0 = "entries"
            goto L_0x003a
        L_0x001e:
            java.lang.String r0 = "values"
            goto L_0x003a
        L_0x0021:
            java.lang.String r0 = "keys"
            goto L_0x003a
        L_0x0024:
            java.lang.String r0 = "clear"
            goto L_0x003a
        L_0x0027:
            java.lang.String r1 = "has"
            goto L_0x002f
        L_0x002a:
            java.lang.String r1 = "delete"
            goto L_0x002f
        L_0x002d:
            java.lang.String r1 = "get"
        L_0x002f:
            r4 = r1
            r6 = 1
            goto L_0x003c
        L_0x0032:
            r0 = 2
            java.lang.String r1 = "set"
            r4 = r1
            r6 = 2
            goto L_0x003c
        L_0x0038:
            java.lang.String r0 = "constructor"
        L_0x003a:
            r4 = r0
            r6 = 0
        L_0x003c:
            java.lang.Object r2 = MAP_TAG
            r1 = r9
            r3 = r10
            r1.initPrototypeMethod((java.lang.Object) r2, (int) r3, (java.lang.String) r4, (java.lang.String) r5, (int) r6)
            return
        L_0x0044:
            org.mozilla.javascript.SymbolKey r10 = org.mozilla.javascript.SymbolKey.TO_STRING_TAG
            java.lang.String r1 = r9.getClassName()
            r2 = 3
            r9.initPrototypeValue((int) r0, (org.mozilla.javascript.Symbol) r10, (java.lang.Object) r1, (int) r2)
            return
        L_0x004f:
            java.lang.Object r4 = MAP_TAG
            org.mozilla.javascript.SymbolKey r6 = org.mozilla.javascript.NativeSet.GETSIZE
            java.lang.String r7 = "get size"
            r8 = 0
            r3 = r9
            r5 = r10
            r3.initPrototypeMethod((java.lang.Object) r4, (int) r5, (org.mozilla.javascript.Symbol) r6, (java.lang.String) r7, (int) r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NativeMap.initPrototypeId(int):void");
    }

    public int findPrototypeId(String str) {
        int i;
        String str2;
        int length = str.length();
        if (length != 3) {
            if (length == 4) {
                str2 = "keys";
                i = 7;
            } else if (length == 5) {
                str2 = "clear";
                i = 6;
            } else if (length == 6) {
                char charAt = str.charAt(0);
                if (charAt == 'd') {
                    str2 = "delete";
                    i = 4;
                } else if (charAt == 'v') {
                    str2 = "values";
                    i = 8;
                }
            } else if (length == 7) {
                char charAt2 = str.charAt(0);
                if (charAt2 == 'e') {
                    str2 = "entries";
                    i = 9;
                } else if (charAt2 == 'f') {
                    str2 = "forEach";
                    i = 10;
                }
            } else if (length == 11) {
                str2 = "constructor";
                i = 1;
            }
            if (str2 != null || str2 == str || str2.equals(str)) {
                return i;
            }
            return 0;
        }
        char charAt3 = str.charAt(0);
        if (charAt3 == 'g') {
            if (str.charAt(2) == 't' && str.charAt(1) == 'e') {
                return 3;
            }
        } else if (charAt3 == 'h') {
            if (str.charAt(2) == 's' && str.charAt(1) == 'a') {
                return 5;
            }
        } else if (charAt3 == 's' && str.charAt(2) == 't' && str.charAt(1) == 'e') {
            return 2;
        }
        str2 = null;
        i = 0;
        return str2 != null ? i : i;
    }
}
