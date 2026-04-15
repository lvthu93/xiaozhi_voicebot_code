package org.mozilla.javascript;

import j$.util.Iterator;
import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Consumer;

public class NativeObject extends IdScriptableObject implements Map {
    private static final int ConstructorId_assign = -15;
    private static final int ConstructorId_create = -9;
    private static final int ConstructorId_defineProperties = -8;
    private static final int ConstructorId_defineProperty = -5;
    private static final int ConstructorId_freeze = -13;
    private static final int ConstructorId_getOwnPropertyDescriptor = -4;
    private static final int ConstructorId_getOwnPropertyNames = -3;
    private static final int ConstructorId_getOwnPropertySymbols = -14;
    private static final int ConstructorId_getPrototypeOf = -1;
    private static final int ConstructorId_is = -16;
    private static final int ConstructorId_isExtensible = -6;
    private static final int ConstructorId_isFrozen = -11;
    private static final int ConstructorId_isSealed = -10;
    private static final int ConstructorId_keys = -2;
    private static final int ConstructorId_preventExtensions = -7;
    private static final int ConstructorId_seal = -12;
    private static final int ConstructorId_setPrototypeOf = -17;
    private static final int Id___defineGetter__ = 9;
    private static final int Id___defineSetter__ = 10;
    private static final int Id___lookupGetter__ = 11;
    private static final int Id___lookupSetter__ = 12;
    private static final int Id_constructor = 1;
    private static final int Id_hasOwnProperty = 5;
    private static final int Id_isPrototypeOf = 7;
    private static final int Id_propertyIsEnumerable = 6;
    private static final int Id_toLocaleString = 3;
    private static final int Id_toSource = 8;
    private static final int Id_toString = 2;
    private static final int Id_valueOf = 4;
    private static final int MAX_PROTOTYPE_ID = 12;
    private static final Object OBJECT_TAG = "Object";
    private static final long serialVersionUID = -6345305608474346996L;

    public class EntrySet extends AbstractSet<Map.Entry<Object, Object>> {
        public EntrySet() {
        }

        public Iterator<Map.Entry<Object, Object>> iterator() {
            return new Object() {
                Object[] ids;
                int index = 0;
                Object key = null;

                {
                    this.ids = NativeObject.this.getIds();
                }

                public final /* synthetic */ void forEachRemaining(Consumer consumer) {
                    Iterator.CC.$default$forEachRemaining(this, consumer);
                }

                public boolean hasNext() {
                    return this.index < this.ids.length;
                }

                public void remove() {
                    Object obj = this.key;
                    if (obj != null) {
                        NativeObject.this.remove(obj);
                        this.key = null;
                        return;
                    }
                    throw new IllegalStateException();
                }

                public Map.Entry<Object, Object> next() {
                    Object[] objArr = this.ids;
                    int i = this.index;
                    this.index = i + 1;
                    final Object obj = objArr[i];
                    this.key = obj;
                    final Object obj2 = NativeObject.this.get(obj);
                    return new Map.Entry<Object, Object>() {
                        /* JADX WARNING: Removed duplicated region for block: B:15:0x0032 A[ORIG_RETURN, RETURN, SYNTHETIC] */
                        /* Code decompiled incorrectly, please refer to instructions dump. */
                        public boolean equals(java.lang.Object r4) {
                            /*
                                r3 = this;
                                boolean r0 = r4 instanceof java.util.Map.Entry
                                r1 = 0
                                if (r0 != 0) goto L_0x0006
                                return r1
                            L_0x0006:
                                java.util.Map$Entry r4 = (java.util.Map.Entry) r4
                                java.lang.Object r0 = r0
                                if (r0 != 0) goto L_0x0013
                                java.lang.Object r0 = r4.getKey()
                                if (r0 != 0) goto L_0x0033
                                goto L_0x001d
                            L_0x0013:
                                java.lang.Object r2 = r4.getKey()
                                boolean r0 = r0.equals(r2)
                                if (r0 == 0) goto L_0x0033
                            L_0x001d:
                                java.lang.Object r0 = r1
                                if (r0 != 0) goto L_0x0028
                                java.lang.Object r4 = r4.getValue()
                                if (r4 != 0) goto L_0x0033
                                goto L_0x0032
                            L_0x0028:
                                java.lang.Object r4 = r4.getValue()
                                boolean r4 = r0.equals(r4)
                                if (r4 == 0) goto L_0x0033
                            L_0x0032:
                                r1 = 1
                            L_0x0033:
                                return r1
                            */
                            throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NativeObject.EntrySet.AnonymousClass1.AnonymousClass1.equals(java.lang.Object):boolean");
                        }

                        public Object getKey() {
                            return obj;
                        }

                        public Object getValue() {
                            return obj2;
                        }

                        public int hashCode() {
                            int i;
                            Object obj = obj;
                            int i2 = 0;
                            if (obj == null) {
                                i = 0;
                            } else {
                                i = obj.hashCode();
                            }
                            Object obj2 = obj2;
                            if (obj2 != null) {
                                i2 = obj2.hashCode();
                            }
                            return i ^ i2;
                        }

                        public Object setValue(Object obj) {
                            throw new UnsupportedOperationException();
                        }

                        public String toString() {
                            return obj + "=" + obj2;
                        }
                    };
                }
            };
        }

        public int size() {
            return NativeObject.this.size();
        }
    }

    public class KeySet extends AbstractSet<Object> {
        public KeySet() {
        }

        public boolean contains(Object obj) {
            return NativeObject.this.containsKey(obj);
        }

        public java.util.Iterator<Object> iterator() {
            return new Object() {
                Object[] ids;
                int index = 0;
                Object key;

                {
                    this.ids = NativeObject.this.getIds();
                }

                public final /* synthetic */ void forEachRemaining(Consumer consumer) {
                    Iterator.CC.$default$forEachRemaining(this, consumer);
                }

                public boolean hasNext() {
                    return this.index < this.ids.length;
                }

                public Object next() {
                    try {
                        Object[] objArr = this.ids;
                        int i = this.index;
                        this.index = i + 1;
                        Object obj = objArr[i];
                        this.key = obj;
                        return obj;
                    } catch (ArrayIndexOutOfBoundsException unused) {
                        this.key = null;
                        throw new NoSuchElementException();
                    }
                }

                public void remove() {
                    Object obj = this.key;
                    if (obj != null) {
                        NativeObject.this.remove(obj);
                        this.key = null;
                        return;
                    }
                    throw new IllegalStateException();
                }
            };
        }

        public int size() {
            return NativeObject.this.size();
        }
    }

    public class ValueCollection extends AbstractCollection<Object> {
        public ValueCollection() {
        }

        public java.util.Iterator<Object> iterator() {
            return new Object() {
                Object[] ids;
                int index = 0;
                Object key;

                {
                    this.ids = NativeObject.this.getIds();
                }

                public final /* synthetic */ void forEachRemaining(Consumer consumer) {
                    Iterator.CC.$default$forEachRemaining(this, consumer);
                }

                public boolean hasNext() {
                    return this.index < this.ids.length;
                }

                public Object next() {
                    NativeObject nativeObject = NativeObject.this;
                    Object[] objArr = this.ids;
                    int i = this.index;
                    this.index = i + 1;
                    Object obj = objArr[i];
                    this.key = obj;
                    return nativeObject.get(obj);
                }

                public void remove() {
                    Object obj = this.key;
                    if (obj != null) {
                        NativeObject.this.remove(obj);
                        this.key = null;
                        return;
                    }
                    throw new IllegalStateException();
                }
            };
        }

        public int size() {
            return NativeObject.this.size();
        }
    }

    private static Scriptable getCompatibleObject(Context context, Scriptable scriptable, Object obj) {
        if (context.getLanguageVersion() >= 200) {
            return ScriptableObject.ensureScriptable(ScriptRuntime.toObject(context, scriptable, obj));
        }
        return ScriptableObject.ensureScriptable(obj);
    }

    public static void init(Scriptable scriptable, boolean z) {
        new NativeObject().exportAsJSClass(12, scriptable, z);
    }

    public void clear() {
        throw new UnsupportedOperationException();
    }

    public boolean containsKey(Object obj) {
        if (obj instanceof String) {
            return has((String) obj, (Scriptable) this);
        }
        if (obj instanceof Number) {
            return has(((Number) obj).intValue(), (Scriptable) this);
        }
        return false;
    }

    public boolean containsValue(Object obj) {
        for (Object next : values()) {
            if (obj == next) {
                return true;
            }
            if (obj != null && obj.equals(next)) {
                return true;
            }
        }
        return false;
    }

    public Set<Map.Entry<Object, Object>> entrySet() {
        return new EntrySet();
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v11, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v15, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v12, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v17, resolved type: boolean} */
    /* JADX WARNING: type inference failed for: r13v0 */
    /* JADX WARNING: type inference failed for: r7v0 */
    /* JADX WARNING: type inference failed for: r7v4, types: [int] */
    /* JADX WARNING: type inference failed for: r13v1, types: [int] */
    /* JADX WARNING: type inference failed for: r13v3, types: [int] */
    /* JADX WARNING: type inference failed for: r13v5, types: [int] */
    /* JADX WARNING: type inference failed for: r13v7, types: [int] */
    /* JADX WARNING: type inference failed for: r13v9, types: [int] */
    /* JADX WARNING: type inference failed for: r13v12 */
    /* JADX WARNING: type inference failed for: r13v13 */
    /* JADX WARNING: type inference failed for: r13v14 */
    /* JADX WARNING: type inference failed for: r13v16 */
    /* JADX WARNING: type inference failed for: r7v13 */
    /* JADX WARNING: type inference failed for: r13v18 */
    /* JADX WARNING: type inference failed for: r7v14 */
    /* JADX WARNING: type inference failed for: r13v19 */
    /* JADX WARNING: type inference failed for: r13v20 */
    /* JADX WARNING: type inference failed for: r13v21 */
    /* JADX WARNING: type inference failed for: r13v22 */
    /* JADX WARNING: type inference failed for: r13v23 */
    /* JADX WARNING: Code restructure failed: missing block: B:104:0x0168, code lost:
        if ((((org.mozilla.javascript.ScriptableObject) r4).getAttributes(r0) & 2) == 0) goto L_0x016a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:124:0x01a5, code lost:
        if ((((org.mozilla.javascript.ScriptableObject) r4).getAttributes(r2.stringId) & 2) == 0) goto L_0x016a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:420:0x0596, code lost:
        r10 = org.mozilla.javascript.ScriptRuntime.toInt32(r10);
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object execIdCall(org.mozilla.javascript.IdFunctionObject r17, org.mozilla.javascript.Context r18, org.mozilla.javascript.Scriptable r19, org.mozilla.javascript.Scriptable r20, java.lang.Object[] r21) {
        /*
            r16 = this;
            r1 = r16
            r0 = r17
            r2 = r18
            r3 = r19
            r4 = r20
            r5 = r21
            java.lang.Object r6 = OBJECT_TAG
            boolean r6 = r0.hasTag(r6)
            if (r6 != 0) goto L_0x0019
            java.lang.Object r0 = super.execIdCall(r17, r18, r19, r20, r21)
            return r0
        L_0x0019:
            int r6 = r17.methodId()
            java.lang.String r8 = "msg.incompat.call"
            java.lang.String r9 = "writable"
            java.lang.String r10 = ".to.object"
            java.lang.String r11 = "undef"
            java.lang.String r12 = "msg."
            java.lang.String r14 = "null"
            java.lang.String r15 = "configurable"
            r13 = 0
            r7 = 1
            switch(r6) {
                case -17: goto L_0x05d3;
                case -16: goto L_0x05b9;
                case -15: goto L_0x0553;
                case -14: goto L_0x0522;
                case -13: goto L_0x04c9;
                case -12: goto L_0x0487;
                case -11: goto L_0x042e;
                case -10: goto L_0x03e8;
                case -9: goto L_0x03b4;
                case -8: goto L_0x0393;
                case -7: goto L_0x0376;
                case -6: goto L_0x0352;
                case -5: goto L_0x032c;
                case -4: goto L_0x030a;
                case -3: goto L_0x02e3;
                case -2: goto L_0x02c0;
                case -1: goto L_0x02af;
                case 0: goto L_0x0030;
                case 1: goto L_0x028d;
                case 2: goto L_0x0261;
                case 3: goto L_0x0249;
                case 4: goto L_0x0221;
                case 5: goto L_0x01cb;
                case 6: goto L_0x011d;
                case 7: goto L_0x00dc;
                case 8: goto L_0x00d7;
                case 9: goto L_0x0078;
                case 10: goto L_0x0078;
                case 11: goto L_0x003a;
                case 12: goto L_0x003a;
                default: goto L_0x0030;
            }
        L_0x0030:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r2 = java.lang.String.valueOf(r6)
            r0.<init>(r2)
            throw r0
        L_0x003a:
            int r0 = r5.length
            if (r0 < r7) goto L_0x0075
            boolean r0 = r4 instanceof org.mozilla.javascript.ScriptableObject
            if (r0 != 0) goto L_0x0042
            goto L_0x0075
        L_0x0042:
            r0 = r4
            org.mozilla.javascript.ScriptableObject r0 = (org.mozilla.javascript.ScriptableObject) r0
            r3 = r5[r13]
            org.mozilla.javascript.ScriptRuntime$StringIdOrIndex r2 = org.mozilla.javascript.ScriptRuntime.toStringIdOrIndex(r2, r3)
            java.lang.String r3 = r2.stringId
            if (r3 == 0) goto L_0x0051
            r3 = 0
            goto L_0x0053
        L_0x0051:
            int r3 = r2.index
        L_0x0053:
            r4 = 12
            if (r6 != r4) goto L_0x0058
            r13 = 1
        L_0x0058:
            java.lang.String r4 = r2.stringId
            java.lang.Object r4 = r0.getGetterOrSetter(r4, r3, r13)
            if (r4 == 0) goto L_0x0061
            goto L_0x006f
        L_0x0061:
            org.mozilla.javascript.Scriptable r0 = r0.getPrototype()
            if (r0 != 0) goto L_0x0068
            goto L_0x006f
        L_0x0068:
            boolean r5 = r0 instanceof org.mozilla.javascript.ScriptableObject
            if (r5 == 0) goto L_0x006f
            org.mozilla.javascript.ScriptableObject r0 = (org.mozilla.javascript.ScriptableObject) r0
            goto L_0x0058
        L_0x006f:
            if (r4 == 0) goto L_0x0072
            return r4
        L_0x0072:
            java.lang.Object r0 = org.mozilla.javascript.Undefined.instance
            return r0
        L_0x0075:
            java.lang.Object r0 = org.mozilla.javascript.Undefined.instance
            return r0
        L_0x0078:
            int r0 = r5.length
            r3 = 2
            if (r0 < r3) goto L_0x00c9
            r0 = r5[r7]
            boolean r0 = r0 instanceof org.mozilla.javascript.Callable
            if (r0 != 0) goto L_0x0083
            goto L_0x00c9
        L_0x0083:
            boolean r0 = r4 instanceof org.mozilla.javascript.ScriptableObject
            if (r0 != 0) goto L_0x009f
            if (r4 != 0) goto L_0x008a
            goto L_0x0092
        L_0x008a:
            java.lang.Class r0 = r20.getClass()
            java.lang.String r14 = r0.getName()
        L_0x0092:
            r0 = r5[r13]
            java.lang.String r0 = java.lang.String.valueOf(r0)
            java.lang.String r2 = "msg.extend.scriptable"
            org.mozilla.javascript.EvaluatorException r0 = org.mozilla.javascript.Context.reportRuntimeError2(r2, r14, r0)
            throw r0
        L_0x009f:
            r0 = r4
            org.mozilla.javascript.ScriptableObject r0 = (org.mozilla.javascript.ScriptableObject) r0
            r3 = r5[r13]
            org.mozilla.javascript.ScriptRuntime$StringIdOrIndex r2 = org.mozilla.javascript.ScriptRuntime.toStringIdOrIndex(r2, r3)
            java.lang.String r3 = r2.stringId
            if (r3 == 0) goto L_0x00ae
            r2 = 0
            goto L_0x00b0
        L_0x00ae:
            int r2 = r2.index
        L_0x00b0:
            r4 = r5[r7]
            org.mozilla.javascript.Callable r4 = (org.mozilla.javascript.Callable) r4
            r5 = 10
            if (r6 != r5) goto L_0x00b9
            goto L_0x00ba
        L_0x00b9:
            r7 = 0
        L_0x00ba:
            r0.setGetterOrSetter(r3, r2, r4, r7)
            boolean r2 = r0 instanceof org.mozilla.javascript.NativeArray
            if (r2 == 0) goto L_0x00c6
            org.mozilla.javascript.NativeArray r0 = (org.mozilla.javascript.NativeArray) r0
            r0.setDenseOnly(r13)
        L_0x00c6:
            java.lang.Object r0 = org.mozilla.javascript.Undefined.instance
            return r0
        L_0x00c9:
            int r0 = r5.length
            r2 = 2
            if (r0 < r2) goto L_0x00d0
            r0 = r5[r7]
            goto L_0x00d2
        L_0x00d0:
            java.lang.Object r0 = org.mozilla.javascript.Undefined.instance
        L_0x00d2:
            java.lang.RuntimeException r0 = org.mozilla.javascript.ScriptRuntime.notFunctionError(r0)
            throw r0
        L_0x00d7:
            java.lang.String r0 = org.mozilla.javascript.ScriptRuntime.defaultObjectToSource(r18, r19, r20, r21)
            return r0
        L_0x00dc:
            int r0 = r18.getLanguageVersion()
            r2 = 180(0xb4, float:2.52E-43)
            if (r0 < r2) goto L_0x0103
            if (r4 == 0) goto L_0x00ec
            boolean r0 = org.mozilla.javascript.Undefined.isUndefined(r20)
            if (r0 == 0) goto L_0x0103
        L_0x00ec:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>(r12)
            if (r4 != 0) goto L_0x00f4
            r11 = r14
        L_0x00f4:
            r0.append(r11)
            r0.append(r10)
            java.lang.String r0 = r0.toString()
            org.mozilla.javascript.EcmaError r0 = org.mozilla.javascript.ScriptRuntime.typeError0(r0)
            throw r0
        L_0x0103:
            int r0 = r5.length
            if (r0 == 0) goto L_0x0118
            r0 = r5[r13]
            boolean r2 = r0 instanceof org.mozilla.javascript.Scriptable
            if (r2 == 0) goto L_0x0118
            org.mozilla.javascript.Scriptable r0 = (org.mozilla.javascript.Scriptable) r0
        L_0x010e:
            org.mozilla.javascript.Scriptable r0 = r0.getPrototype()
            if (r0 != r4) goto L_0x0116
            r13 = 1
            goto L_0x0118
        L_0x0116:
            if (r0 != 0) goto L_0x010e
        L_0x0118:
            java.lang.Boolean r0 = org.mozilla.javascript.ScriptRuntime.wrapBoolean(r13)
            return r0
        L_0x011d:
            int r0 = r18.getLanguageVersion()
            r3 = 180(0xb4, float:2.52E-43)
            if (r0 < r3) goto L_0x0144
            if (r4 == 0) goto L_0x012d
            boolean r0 = org.mozilla.javascript.Undefined.isUndefined(r20)
            if (r0 == 0) goto L_0x0144
        L_0x012d:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>(r12)
            if (r4 != 0) goto L_0x0135
            r11 = r14
        L_0x0135:
            r0.append(r11)
            r0.append(r10)
            java.lang.String r0 = r0.toString()
            org.mozilla.javascript.EcmaError r0 = org.mozilla.javascript.ScriptRuntime.typeError0(r0)
            throw r0
        L_0x0144:
            int r0 = r5.length
            if (r0 >= r7) goto L_0x014a
            java.lang.Object r0 = org.mozilla.javascript.Undefined.instance
            goto L_0x014c
        L_0x014a:
            r0 = r5[r13]
        L_0x014c:
            boolean r3 = r0 instanceof org.mozilla.javascript.Symbol
            if (r3 == 0) goto L_0x016e
            r2 = r4
            org.mozilla.javascript.SymbolScriptable r2 = (org.mozilla.javascript.SymbolScriptable) r2
            org.mozilla.javascript.Symbol r0 = (org.mozilla.javascript.Symbol) r0
            boolean r2 = r2.has(r0, r4)
            if (r2 == 0) goto L_0x016c
            boolean r3 = r4 instanceof org.mozilla.javascript.ScriptableObject
            if (r3 == 0) goto L_0x016c
            r2 = r4
            org.mozilla.javascript.ScriptableObject r2 = (org.mozilla.javascript.ScriptableObject) r2
            int r0 = r2.getAttributes((org.mozilla.javascript.Symbol) r0)
            r2 = 2
            r0 = r0 & r2
            if (r0 != 0) goto L_0x01c5
        L_0x016a:
            r13 = 1
            goto L_0x01c5
        L_0x016c:
            r13 = r2
            goto L_0x01c5
        L_0x016e:
            org.mozilla.javascript.ScriptRuntime$StringIdOrIndex r2 = org.mozilla.javascript.ScriptRuntime.toStringIdOrIndex(r2, r0)
            java.lang.String r0 = r2.stringId     // Catch:{ EvaluatorException -> 0x01aa }
            if (r0 != 0) goto L_0x0190
            int r0 = r2.index     // Catch:{ EvaluatorException -> 0x01aa }
            boolean r0 = r4.has((int) r0, (org.mozilla.javascript.Scriptable) r4)     // Catch:{ EvaluatorException -> 0x01aa }
            if (r0 == 0) goto L_0x01a8
            boolean r3 = r4 instanceof org.mozilla.javascript.ScriptableObject     // Catch:{ EvaluatorException -> 0x01aa }
            if (r3 == 0) goto L_0x01a8
            r0 = r4
            org.mozilla.javascript.ScriptableObject r0 = (org.mozilla.javascript.ScriptableObject) r0     // Catch:{ EvaluatorException -> 0x01aa }
            int r3 = r2.index     // Catch:{ EvaluatorException -> 0x01aa }
            int r0 = r0.getAttributes((int) r3)     // Catch:{ EvaluatorException -> 0x01aa }
            r2 = 2
            r0 = r0 & r2
            if (r0 != 0) goto L_0x01c5
            goto L_0x016a
        L_0x0190:
            boolean r0 = r4.has((java.lang.String) r0, (org.mozilla.javascript.Scriptable) r4)     // Catch:{ EvaluatorException -> 0x01aa }
            if (r0 == 0) goto L_0x01a8
            boolean r3 = r4 instanceof org.mozilla.javascript.ScriptableObject     // Catch:{ EvaluatorException -> 0x01aa }
            if (r3 == 0) goto L_0x01a8
            r0 = r4
            org.mozilla.javascript.ScriptableObject r0 = (org.mozilla.javascript.ScriptableObject) r0     // Catch:{ EvaluatorException -> 0x01aa }
            java.lang.String r3 = r2.stringId     // Catch:{ EvaluatorException -> 0x01aa }
            int r0 = r0.getAttributes((java.lang.String) r3)     // Catch:{ EvaluatorException -> 0x01aa }
            r2 = 2
            r0 = r0 & r2
            if (r0 != 0) goto L_0x01c5
            goto L_0x016a
        L_0x01a8:
            r13 = r0
            goto L_0x01c5
        L_0x01aa:
            r0 = move-exception
            java.lang.String r3 = r0.getMessage()
            java.lang.String r4 = r2.stringId
            if (r4 != 0) goto L_0x01b9
            int r2 = r2.index
            java.lang.String r4 = java.lang.Integer.toString(r2)
        L_0x01b9:
            java.lang.String r2 = "msg.prop.not.found"
            java.lang.String r2 = org.mozilla.javascript.ScriptRuntime.getMessage1(r2, r4)
            boolean r2 = r3.startsWith(r2)
            if (r2 == 0) goto L_0x01ca
        L_0x01c5:
            java.lang.Boolean r0 = org.mozilla.javascript.ScriptRuntime.wrapBoolean(r13)
            return r0
        L_0x01ca:
            throw r0
        L_0x01cb:
            int r0 = r18.getLanguageVersion()
            r3 = 180(0xb4, float:2.52E-43)
            if (r0 < r3) goto L_0x01f2
            if (r4 == 0) goto L_0x01db
            boolean r0 = org.mozilla.javascript.Undefined.isUndefined(r20)
            if (r0 == 0) goto L_0x01f2
        L_0x01db:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>(r12)
            if (r4 != 0) goto L_0x01e3
            r11 = r14
        L_0x01e3:
            r0.append(r11)
            r0.append(r10)
            java.lang.String r0 = r0.toString()
            org.mozilla.javascript.EcmaError r0 = org.mozilla.javascript.ScriptRuntime.typeError0(r0)
            throw r0
        L_0x01f2:
            int r0 = r5.length
            if (r0 >= r7) goto L_0x01f8
            java.lang.Object r0 = org.mozilla.javascript.Undefined.instance
            goto L_0x01fa
        L_0x01f8:
            r0 = r5[r13]
        L_0x01fa:
            boolean r3 = r0 instanceof org.mozilla.javascript.Symbol
            if (r3 == 0) goto L_0x0209
            org.mozilla.javascript.SymbolScriptable r2 = org.mozilla.javascript.ScriptableObject.ensureSymbolScriptable(r20)
            org.mozilla.javascript.Symbol r0 = (org.mozilla.javascript.Symbol) r0
            boolean r0 = r2.has(r0, r4)
            goto L_0x021c
        L_0x0209:
            org.mozilla.javascript.ScriptRuntime$StringIdOrIndex r0 = org.mozilla.javascript.ScriptRuntime.toStringIdOrIndex(r2, r0)
            java.lang.String r2 = r0.stringId
            if (r2 != 0) goto L_0x0218
            int r0 = r0.index
            boolean r0 = r4.has((int) r0, (org.mozilla.javascript.Scriptable) r4)
            goto L_0x021c
        L_0x0218:
            boolean r0 = r4.has((java.lang.String) r2, (org.mozilla.javascript.Scriptable) r4)
        L_0x021c:
            java.lang.Boolean r0 = org.mozilla.javascript.ScriptRuntime.wrapBoolean(r0)
            return r0
        L_0x0221:
            int r0 = r18.getLanguageVersion()
            r2 = 180(0xb4, float:2.52E-43)
            if (r0 < r2) goto L_0x0248
            if (r4 == 0) goto L_0x0231
            boolean r0 = org.mozilla.javascript.Undefined.isUndefined(r20)
            if (r0 == 0) goto L_0x0248
        L_0x0231:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>(r12)
            if (r4 != 0) goto L_0x0239
            r11 = r14
        L_0x0239:
            r0.append(r11)
            r0.append(r10)
            java.lang.String r0 = r0.toString()
            org.mozilla.javascript.EcmaError r0 = org.mozilla.javascript.ScriptRuntime.typeError0(r0)
            throw r0
        L_0x0248:
            return r4
        L_0x0249:
            java.lang.String r0 = "toString"
            java.lang.Object r0 = org.mozilla.javascript.ScriptableObject.getProperty((org.mozilla.javascript.Scriptable) r4, (java.lang.String) r0)
            boolean r5 = r0 instanceof org.mozilla.javascript.Callable
            if (r5 == 0) goto L_0x025c
            org.mozilla.javascript.Callable r0 = (org.mozilla.javascript.Callable) r0
            java.lang.Object[] r5 = org.mozilla.javascript.ScriptRuntime.emptyArgs
            java.lang.Object r0 = r0.call(r2, r3, r4, r5)
            return r0
        L_0x025c:
            java.lang.RuntimeException r0 = org.mozilla.javascript.ScriptRuntime.notFunctionError(r0)
            throw r0
        L_0x0261:
            r0 = 4
            boolean r0 = r2.hasFeature(r0)
            if (r0 == 0) goto L_0x0288
            java.lang.String r0 = org.mozilla.javascript.ScriptRuntime.defaultObjectToSource(r18, r19, r20, r21)
            int r2 = r0.length()
            if (r2 == 0) goto L_0x0287
            char r3 = r0.charAt(r13)
            r4 = 40
            if (r3 != r4) goto L_0x0287
            int r2 = r2 - r7
            char r3 = r0.charAt(r2)
            r4 = 41
            if (r3 != r4) goto L_0x0287
            java.lang.String r0 = r0.substring(r7, r2)
        L_0x0287:
            return r0
        L_0x0288:
            java.lang.String r0 = org.mozilla.javascript.ScriptRuntime.defaultObjectToString(r20)
            return r0
        L_0x028d:
            if (r4 == 0) goto L_0x0294
            org.mozilla.javascript.Scriptable r0 = r0.construct(r2, r3, r5)
            return r0
        L_0x0294:
            int r0 = r5.length
            if (r0 == 0) goto L_0x02a9
            r0 = r5[r13]
            if (r0 == 0) goto L_0x02a9
            boolean r0 = org.mozilla.javascript.Undefined.isUndefined(r0)
            if (r0 == 0) goto L_0x02a2
            goto L_0x02a9
        L_0x02a2:
            r0 = r5[r13]
            org.mozilla.javascript.Scriptable r0 = org.mozilla.javascript.ScriptRuntime.toObject((org.mozilla.javascript.Context) r2, (org.mozilla.javascript.Scriptable) r3, (java.lang.Object) r0)
            return r0
        L_0x02a9:
            org.mozilla.javascript.NativeObject r0 = new org.mozilla.javascript.NativeObject
            r0.<init>()
            return r0
        L_0x02af:
            int r0 = r5.length
            if (r0 >= r7) goto L_0x02b5
            java.lang.Object r0 = org.mozilla.javascript.Undefined.instance
            goto L_0x02b7
        L_0x02b5:
            r0 = r5[r13]
        L_0x02b7:
            org.mozilla.javascript.Scriptable r0 = getCompatibleObject(r2, r3, r0)
            org.mozilla.javascript.Scriptable r0 = r0.getPrototype()
            return r0
        L_0x02c0:
            int r0 = r5.length
            if (r0 >= r7) goto L_0x02c6
            java.lang.Object r0 = org.mozilla.javascript.Undefined.instance
            goto L_0x02c8
        L_0x02c6:
            r0 = r5[r13]
        L_0x02c8:
            org.mozilla.javascript.Scriptable r0 = getCompatibleObject(r2, r3, r0)
            java.lang.Object[] r0 = r0.getIds()
        L_0x02d0:
            int r4 = r0.length
            if (r13 >= r4) goto L_0x02de
            r4 = r0[r13]
            java.lang.String r4 = org.mozilla.javascript.ScriptRuntime.toString((java.lang.Object) r4)
            r0[r13] = r4
            int r13 = r13 + 1
            goto L_0x02d0
        L_0x02de:
            org.mozilla.javascript.Scriptable r0 = r2.newArray((org.mozilla.javascript.Scriptable) r3, (java.lang.Object[]) r0)
            return r0
        L_0x02e3:
            int r0 = r5.length
            if (r0 >= r7) goto L_0x02e9
            java.lang.Object r0 = org.mozilla.javascript.Undefined.instance
            goto L_0x02eb
        L_0x02e9:
            r0 = r5[r13]
        L_0x02eb:
            org.mozilla.javascript.Scriptable r0 = getCompatibleObject(r2, r3, r0)
            org.mozilla.javascript.ScriptableObject r0 = org.mozilla.javascript.ScriptableObject.ensureScriptableObject(r0)
            java.lang.Object[] r0 = r0.getIds(r7, r13)
        L_0x02f7:
            int r4 = r0.length
            if (r13 >= r4) goto L_0x0305
            r4 = r0[r13]
            java.lang.String r4 = org.mozilla.javascript.ScriptRuntime.toString((java.lang.Object) r4)
            r0[r13] = r4
            int r13 = r13 + 1
            goto L_0x02f7
        L_0x0305:
            org.mozilla.javascript.Scriptable r0 = r2.newArray((org.mozilla.javascript.Scriptable) r3, (java.lang.Object[]) r0)
            return r0
        L_0x030a:
            int r0 = r5.length
            if (r0 >= r7) goto L_0x0310
            java.lang.Object r0 = org.mozilla.javascript.Undefined.instance
            goto L_0x0312
        L_0x0310:
            r0 = r5[r13]
        L_0x0312:
            org.mozilla.javascript.Scriptable r0 = getCompatibleObject(r2, r3, r0)
            org.mozilla.javascript.ScriptableObject r0 = org.mozilla.javascript.ScriptableObject.ensureScriptableObject(r0)
            int r3 = r5.length
            r4 = 2
            if (r3 >= r4) goto L_0x0321
            java.lang.Object r3 = org.mozilla.javascript.Undefined.instance
            goto L_0x0323
        L_0x0321:
            r3 = r5[r7]
        L_0x0323:
            org.mozilla.javascript.ScriptableObject r0 = r0.getOwnPropertyDescriptor(r2, r3)
            if (r0 != 0) goto L_0x032b
            java.lang.Object r0 = org.mozilla.javascript.Undefined.instance
        L_0x032b:
            return r0
        L_0x032c:
            int r0 = r5.length
            if (r0 >= r7) goto L_0x0332
            java.lang.Object r0 = org.mozilla.javascript.Undefined.instance
            goto L_0x0334
        L_0x0332:
            r0 = r5[r13]
        L_0x0334:
            org.mozilla.javascript.ScriptableObject r0 = org.mozilla.javascript.ScriptableObject.ensureScriptableObject(r0)
            int r3 = r5.length
            r4 = 2
            if (r3 >= r4) goto L_0x033f
            java.lang.Object r3 = org.mozilla.javascript.Undefined.instance
            goto L_0x0341
        L_0x033f:
            r3 = r5[r7]
        L_0x0341:
            int r6 = r5.length
            r7 = 3
            if (r6 >= r7) goto L_0x0348
            java.lang.Object r4 = org.mozilla.javascript.Undefined.instance
            goto L_0x034a
        L_0x0348:
            r4 = r5[r4]
        L_0x034a:
            org.mozilla.javascript.ScriptableObject r4 = org.mozilla.javascript.ScriptableObject.ensureScriptableObject(r4)
            r0.defineOwnProperty(r2, r3, r4)
            return r0
        L_0x0352:
            int r0 = r5.length
            if (r0 >= r7) goto L_0x0358
            java.lang.Object r0 = org.mozilla.javascript.Undefined.instance
            goto L_0x035a
        L_0x0358:
            r0 = r5[r13]
        L_0x035a:
            int r2 = r18.getLanguageVersion()
            r3 = 200(0xc8, float:2.8E-43)
            if (r2 < r3) goto L_0x0369
            boolean r2 = r0 instanceof org.mozilla.javascript.ScriptableObject
            if (r2 != 0) goto L_0x0369
            java.lang.Boolean r0 = java.lang.Boolean.FALSE
            return r0
        L_0x0369:
            org.mozilla.javascript.ScriptableObject r0 = org.mozilla.javascript.ScriptableObject.ensureScriptableObject(r0)
            boolean r0 = r0.isExtensible()
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)
            return r0
        L_0x0376:
            int r0 = r5.length
            if (r0 >= r7) goto L_0x037c
            java.lang.Object r0 = org.mozilla.javascript.Undefined.instance
            goto L_0x037e
        L_0x037c:
            r0 = r5[r13]
        L_0x037e:
            int r2 = r18.getLanguageVersion()
            r3 = 200(0xc8, float:2.8E-43)
            if (r2 < r3) goto L_0x038b
            boolean r2 = r0 instanceof org.mozilla.javascript.ScriptableObject
            if (r2 != 0) goto L_0x038b
            return r0
        L_0x038b:
            org.mozilla.javascript.ScriptableObject r0 = org.mozilla.javascript.ScriptableObject.ensureScriptableObject(r0)
            r0.preventExtensions()
            return r0
        L_0x0393:
            int r0 = r5.length
            if (r0 >= r7) goto L_0x0399
            java.lang.Object r0 = org.mozilla.javascript.Undefined.instance
            goto L_0x039b
        L_0x0399:
            r0 = r5[r13]
        L_0x039b:
            org.mozilla.javascript.ScriptableObject r0 = org.mozilla.javascript.ScriptableObject.ensureScriptableObject(r0)
            int r4 = r5.length
            r6 = 2
            if (r4 >= r6) goto L_0x03a6
            java.lang.Object r4 = org.mozilla.javascript.Undefined.instance
            goto L_0x03a8
        L_0x03a6:
            r4 = r5[r7]
        L_0x03a8:
            org.mozilla.javascript.Scriptable r3 = org.mozilla.javascript.Context.toObject(r4, r3)
            org.mozilla.javascript.ScriptableObject r3 = org.mozilla.javascript.ScriptableObject.ensureScriptableObject(r3)
            r0.defineOwnProperties(r2, r3)
            return r0
        L_0x03b4:
            int r0 = r5.length
            if (r0 >= r7) goto L_0x03ba
            java.lang.Object r0 = org.mozilla.javascript.Undefined.instance
            goto L_0x03bc
        L_0x03ba:
            r0 = r5[r13]
        L_0x03bc:
            if (r0 != 0) goto L_0x03c0
            r0 = 0
            goto L_0x03c4
        L_0x03c0:
            org.mozilla.javascript.Scriptable r0 = org.mozilla.javascript.ScriptableObject.ensureScriptable(r0)
        L_0x03c4:
            org.mozilla.javascript.NativeObject r4 = new org.mozilla.javascript.NativeObject
            r4.<init>()
            r4.setParentScope(r3)
            r4.setPrototype(r0)
            int r0 = r5.length
            if (r0 <= r7) goto L_0x03e7
            r0 = r5[r7]
            boolean r0 = org.mozilla.javascript.Undefined.isUndefined(r0)
            if (r0 != 0) goto L_0x03e7
            r0 = r5[r7]
            org.mozilla.javascript.Scriptable r0 = org.mozilla.javascript.Context.toObject(r0, r3)
            org.mozilla.javascript.ScriptableObject r0 = org.mozilla.javascript.ScriptableObject.ensureScriptableObject(r0)
            r4.defineOwnProperties(r2, r0)
        L_0x03e7:
            return r4
        L_0x03e8:
            int r0 = r5.length
            if (r0 >= r7) goto L_0x03ee
            java.lang.Object r0 = org.mozilla.javascript.Undefined.instance
            goto L_0x03f0
        L_0x03ee:
            r0 = r5[r13]
        L_0x03f0:
            int r3 = r18.getLanguageVersion()
            r4 = 200(0xc8, float:2.8E-43)
            if (r3 < r4) goto L_0x03ff
            boolean r3 = r0 instanceof org.mozilla.javascript.ScriptableObject
            if (r3 != 0) goto L_0x03ff
            java.lang.Boolean r0 = java.lang.Boolean.TRUE
            return r0
        L_0x03ff:
            org.mozilla.javascript.ScriptableObject r0 = org.mozilla.javascript.ScriptableObject.ensureScriptableObject(r0)
            boolean r3 = r0.isExtensible()
            if (r3 == 0) goto L_0x040c
            java.lang.Boolean r0 = java.lang.Boolean.FALSE
            return r0
        L_0x040c:
            java.lang.Object[] r3 = r0.getAllIds()
            int r4 = r3.length
        L_0x0411:
            if (r13 >= r4) goto L_0x042b
            r5 = r3[r13]
            org.mozilla.javascript.ScriptableObject r5 = r0.getOwnPropertyDescriptor(r2, r5)
            java.lang.Object r5 = r5.get(r15)
            java.lang.Boolean r6 = java.lang.Boolean.TRUE
            boolean r5 = r6.equals(r5)
            if (r5 == 0) goto L_0x0428
            java.lang.Boolean r0 = java.lang.Boolean.FALSE
            return r0
        L_0x0428:
            int r13 = r13 + 1
            goto L_0x0411
        L_0x042b:
            java.lang.Boolean r0 = java.lang.Boolean.TRUE
            return r0
        L_0x042e:
            int r0 = r5.length
            if (r0 >= r7) goto L_0x0434
            java.lang.Object r0 = org.mozilla.javascript.Undefined.instance
            goto L_0x0436
        L_0x0434:
            r0 = r5[r13]
        L_0x0436:
            int r3 = r18.getLanguageVersion()
            r4 = 200(0xc8, float:2.8E-43)
            if (r3 < r4) goto L_0x0445
            boolean r3 = r0 instanceof org.mozilla.javascript.ScriptableObject
            if (r3 != 0) goto L_0x0445
            java.lang.Boolean r0 = java.lang.Boolean.TRUE
            return r0
        L_0x0445:
            org.mozilla.javascript.ScriptableObject r0 = org.mozilla.javascript.ScriptableObject.ensureScriptableObject(r0)
            boolean r3 = r0.isExtensible()
            if (r3 == 0) goto L_0x0452
            java.lang.Boolean r0 = java.lang.Boolean.FALSE
            return r0
        L_0x0452:
            java.lang.Object[] r3 = r0.getAllIds()
            int r4 = r3.length
        L_0x0457:
            if (r13 >= r4) goto L_0x0484
            r5 = r3[r13]
            org.mozilla.javascript.ScriptableObject r5 = r0.getOwnPropertyDescriptor(r2, r5)
            java.lang.Boolean r6 = java.lang.Boolean.TRUE
            java.lang.Object r7 = r5.get(r15)
            boolean r7 = r6.equals(r7)
            if (r7 == 0) goto L_0x046e
            java.lang.Boolean r0 = java.lang.Boolean.FALSE
            return r0
        L_0x046e:
            boolean r7 = r1.isDataDescriptor(r5)
            if (r7 == 0) goto L_0x0481
            java.lang.Object r5 = r5.get(r9)
            boolean r5 = r6.equals(r5)
            if (r5 == 0) goto L_0x0481
            java.lang.Boolean r0 = java.lang.Boolean.FALSE
            return r0
        L_0x0481:
            int r13 = r13 + 1
            goto L_0x0457
        L_0x0484:
            java.lang.Boolean r0 = java.lang.Boolean.TRUE
            return r0
        L_0x0487:
            int r0 = r5.length
            if (r0 >= r7) goto L_0x048d
            java.lang.Object r0 = org.mozilla.javascript.Undefined.instance
            goto L_0x048f
        L_0x048d:
            r0 = r5[r13]
        L_0x048f:
            int r3 = r18.getLanguageVersion()
            r4 = 200(0xc8, float:2.8E-43)
            if (r3 < r4) goto L_0x049c
            boolean r3 = r0 instanceof org.mozilla.javascript.ScriptableObject
            if (r3 != 0) goto L_0x049c
            return r0
        L_0x049c:
            org.mozilla.javascript.ScriptableObject r0 = org.mozilla.javascript.ScriptableObject.ensureScriptableObject(r0)
            java.lang.Object[] r3 = r0.getAllIds()
            int r4 = r3.length
            r5 = 0
        L_0x04a6:
            if (r5 >= r4) goto L_0x04c5
            r6 = r3[r5]
            org.mozilla.javascript.ScriptableObject r7 = r0.getOwnPropertyDescriptor(r2, r6)
            java.lang.Boolean r8 = java.lang.Boolean.TRUE
            java.lang.Object r9 = r7.get(r15)
            boolean r8 = r8.equals(r9)
            if (r8 == 0) goto L_0x04c2
            java.lang.Boolean r8 = java.lang.Boolean.FALSE
            r7.put((java.lang.String) r15, (org.mozilla.javascript.Scriptable) r7, (java.lang.Object) r8)
            r0.defineOwnProperty(r2, r6, r7, r13)
        L_0x04c2:
            int r5 = r5 + 1
            goto L_0x04a6
        L_0x04c5:
            r0.preventExtensions()
            return r0
        L_0x04c9:
            int r0 = r5.length
            if (r0 >= r7) goto L_0x04cf
            java.lang.Object r0 = org.mozilla.javascript.Undefined.instance
            goto L_0x04d1
        L_0x04cf:
            r0 = r5[r13]
        L_0x04d1:
            int r3 = r18.getLanguageVersion()
            r4 = 200(0xc8, float:2.8E-43)
            if (r3 < r4) goto L_0x04de
            boolean r3 = r0 instanceof org.mozilla.javascript.ScriptableObject
            if (r3 != 0) goto L_0x04de
            return r0
        L_0x04de:
            org.mozilla.javascript.ScriptableObject r0 = org.mozilla.javascript.ScriptableObject.ensureScriptableObject(r0)
            java.lang.Object[] r3 = r0.getIds(r7, r7)
            int r4 = r3.length
            r5 = 0
        L_0x04e8:
            if (r5 >= r4) goto L_0x051e
            r6 = r3[r5]
            org.mozilla.javascript.ScriptableObject r7 = r0.getOwnPropertyDescriptor(r2, r6)
            boolean r8 = r1.isDataDescriptor(r7)
            if (r8 == 0) goto L_0x0507
            java.lang.Boolean r8 = java.lang.Boolean.TRUE
            java.lang.Object r10 = r7.get(r9)
            boolean r8 = r8.equals(r10)
            if (r8 == 0) goto L_0x0507
            java.lang.Boolean r8 = java.lang.Boolean.FALSE
            r7.put((java.lang.String) r9, (org.mozilla.javascript.Scriptable) r7, (java.lang.Object) r8)
        L_0x0507:
            java.lang.Boolean r8 = java.lang.Boolean.TRUE
            java.lang.Object r10 = r7.get(r15)
            boolean r8 = r8.equals(r10)
            if (r8 == 0) goto L_0x0518
            java.lang.Boolean r8 = java.lang.Boolean.FALSE
            r7.put((java.lang.String) r15, (org.mozilla.javascript.Scriptable) r7, (java.lang.Object) r8)
        L_0x0518:
            r0.defineOwnProperty(r2, r6, r7, r13)
            int r5 = r5 + 1
            goto L_0x04e8
        L_0x051e:
            r0.preventExtensions()
            return r0
        L_0x0522:
            int r0 = r5.length
            if (r0 >= r7) goto L_0x0528
            java.lang.Object r0 = org.mozilla.javascript.Undefined.instance
            goto L_0x052a
        L_0x0528:
            r0 = r5[r13]
        L_0x052a:
            org.mozilla.javascript.Scriptable r0 = getCompatibleObject(r2, r3, r0)
            org.mozilla.javascript.ScriptableObject r0 = org.mozilla.javascript.ScriptableObject.ensureScriptableObject(r0)
            java.lang.Object[] r0 = r0.getIds(r7, r7)
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
        L_0x053b:
            int r5 = r0.length
            if (r13 >= r5) goto L_0x054a
            r5 = r0[r13]
            boolean r6 = r5 instanceof org.mozilla.javascript.Symbol
            if (r6 == 0) goto L_0x0547
            r4.add(r5)
        L_0x0547:
            int r13 = r13 + 1
            goto L_0x053b
        L_0x054a:
            java.lang.Object[] r0 = r4.toArray()
            org.mozilla.javascript.Scriptable r0 = r2.newArray((org.mozilla.javascript.Scriptable) r3, (java.lang.Object[]) r0)
            return r0
        L_0x0553:
            int r0 = r5.length
            if (r0 < r7) goto L_0x05b2
            r0 = r5[r13]
            org.mozilla.javascript.Scriptable r0 = org.mozilla.javascript.ScriptRuntime.toObject((org.mozilla.javascript.Context) r2, (org.mozilla.javascript.Scriptable) r4, (java.lang.Object) r0)
        L_0x055c:
            int r3 = r5.length
            if (r7 >= r3) goto L_0x05b1
            r3 = r5[r7]
            if (r3 == 0) goto L_0x05ae
            boolean r3 = org.mozilla.javascript.Undefined.isUndefined(r3)
            if (r3 == 0) goto L_0x056a
            goto L_0x05ae
        L_0x056a:
            r3 = r5[r7]
            org.mozilla.javascript.Scriptable r3 = org.mozilla.javascript.ScriptRuntime.toObject((org.mozilla.javascript.Context) r2, (org.mozilla.javascript.Scriptable) r4, (java.lang.Object) r3)
            java.lang.Object[] r6 = r3.getIds()
            int r8 = r6.length
            r9 = 0
        L_0x0576:
            if (r9 >= r8) goto L_0x05ae
            r10 = r6[r9]
            boolean r11 = r10 instanceof java.lang.String
            if (r11 == 0) goto L_0x0592
            java.lang.String r10 = (java.lang.String) r10
            java.lang.Object r11 = r3.get((java.lang.String) r10, (org.mozilla.javascript.Scriptable) r3)
            java.lang.Object r12 = org.mozilla.javascript.Scriptable.NOT_FOUND
            if (r11 == r12) goto L_0x05ab
            boolean r12 = org.mozilla.javascript.Undefined.isUndefined(r11)
            if (r12 != 0) goto L_0x05ab
            r0.put((java.lang.String) r10, (org.mozilla.javascript.Scriptable) r0, (java.lang.Object) r11)
            goto L_0x05ab
        L_0x0592:
            boolean r11 = r10 instanceof java.lang.Number
            if (r11 == 0) goto L_0x05ab
            int r10 = org.mozilla.javascript.ScriptRuntime.toInt32((java.lang.Object) r10)
            java.lang.Object r11 = r3.get((int) r10, (org.mozilla.javascript.Scriptable) r3)
            java.lang.Object r12 = org.mozilla.javascript.Scriptable.NOT_FOUND
            if (r11 == r12) goto L_0x05ab
            boolean r12 = org.mozilla.javascript.Undefined.isUndefined(r11)
            if (r12 != 0) goto L_0x05ab
            r0.put((int) r10, (org.mozilla.javascript.Scriptable) r0, (java.lang.Object) r11)
        L_0x05ab:
            int r9 = r9 + 1
            goto L_0x0576
        L_0x05ae:
            int r7 = r7 + 1
            goto L_0x055c
        L_0x05b1:
            return r0
        L_0x05b2:
            java.lang.String r0 = "assign"
            org.mozilla.javascript.EcmaError r0 = org.mozilla.javascript.ScriptRuntime.typeError1(r8, r0)
            throw r0
        L_0x05b9:
            int r0 = r5.length
            if (r0 >= r7) goto L_0x05bf
            java.lang.Object r0 = org.mozilla.javascript.Undefined.instance
            goto L_0x05c1
        L_0x05bf:
            r0 = r5[r13]
        L_0x05c1:
            int r2 = r5.length
            r3 = 2
            if (r2 >= r3) goto L_0x05c8
            java.lang.Object r2 = org.mozilla.javascript.Undefined.instance
            goto L_0x05ca
        L_0x05c8:
            r2 = r5[r7]
        L_0x05ca:
            boolean r0 = org.mozilla.javascript.ScriptRuntime.same(r0, r2)
            java.lang.Boolean r0 = org.mozilla.javascript.ScriptRuntime.wrapBoolean(r0)
            return r0
        L_0x05d3:
            r3 = 2
            int r4 = r5.length
            if (r4 < r3) goto L_0x062e
            r3 = r5[r7]
            if (r3 != 0) goto L_0x05dd
            r7 = 0
            goto L_0x05e1
        L_0x05dd:
            org.mozilla.javascript.Scriptable r7 = org.mozilla.javascript.ScriptableObject.ensureScriptable(r3)
        L_0x05e1:
            boolean r3 = r7 instanceof org.mozilla.javascript.Symbol
            if (r3 != 0) goto L_0x0623
            r3 = r5[r13]
            int r4 = r18.getLanguageVersion()
            r5 = 200(0xc8, float:2.8E-43)
            if (r4 < r5) goto L_0x05f2
            org.mozilla.javascript.ScriptRuntimeES6.requireObjectCoercible(r2, r3, r0)
        L_0x05f2:
            boolean r0 = r3 instanceof org.mozilla.javascript.ScriptableObject
            if (r0 != 0) goto L_0x05f7
            return r3
        L_0x05f7:
            org.mozilla.javascript.ScriptableObject r3 = (org.mozilla.javascript.ScriptableObject) r3
            boolean r0 = r3.isExtensible()
            if (r0 == 0) goto L_0x061c
            r0 = r7
        L_0x0600:
            if (r0 == 0) goto L_0x0618
            if (r0 == r3) goto L_0x0609
            org.mozilla.javascript.Scriptable r0 = r0.getPrototype()
            goto L_0x0600
        L_0x0609:
            java.lang.Class r0 = r3.getClass()
            java.lang.String r0 = r0.getSimpleName()
            java.lang.String r2 = "msg.object.cyclic.prototype"
            org.mozilla.javascript.EcmaError r0 = org.mozilla.javascript.ScriptRuntime.typeError1(r2, r0)
            throw r0
        L_0x0618:
            r3.setPrototype(r7)
            return r3
        L_0x061c:
            java.lang.String r0 = "msg.not.extensible"
            org.mozilla.javascript.EcmaError r0 = org.mozilla.javascript.ScriptRuntime.typeError0(r0)
            throw r0
        L_0x0623:
            java.lang.String r0 = "msg.arg.not.object"
            java.lang.String r2 = org.mozilla.javascript.ScriptRuntime.typeof(r7)
            org.mozilla.javascript.EcmaError r0 = org.mozilla.javascript.ScriptRuntime.typeError1(r0, r2)
            throw r0
        L_0x062e:
            java.lang.String r0 = "setPrototypeOf"
            org.mozilla.javascript.EcmaError r0 = org.mozilla.javascript.ScriptRuntime.typeError1(r8, r0)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NativeObject.execIdCall(org.mozilla.javascript.IdFunctionObject, org.mozilla.javascript.Context, org.mozilla.javascript.Scriptable, org.mozilla.javascript.Scriptable, java.lang.Object[]):java.lang.Object");
    }

    public void fillConstructorProperties(IdFunctionObject idFunctionObject) {
        Object obj = OBJECT_TAG;
        addIdFunctionProperty(idFunctionObject, obj, -1, "getPrototypeOf", 1);
        if (Context.getCurrentContext().version >= 200) {
            addIdFunctionProperty(idFunctionObject, obj, ConstructorId_setPrototypeOf, "setPrototypeOf", 2);
        }
        IdFunctionObject idFunctionObject2 = idFunctionObject;
        Object obj2 = obj;
        addIdFunctionProperty(idFunctionObject2, obj2, -2, "keys", 1);
        addIdFunctionProperty(idFunctionObject2, obj2, -3, "getOwnPropertyNames", 1);
        addIdFunctionProperty(idFunctionObject2, obj2, ConstructorId_getOwnPropertySymbols, "getOwnPropertySymbols", 1);
        addIdFunctionProperty(idFunctionObject2, obj2, -4, "getOwnPropertyDescriptor", 2);
        addIdFunctionProperty(idFunctionObject2, obj2, ConstructorId_defineProperty, "defineProperty", 3);
        addIdFunctionProperty(idFunctionObject2, obj2, ConstructorId_isExtensible, "isExtensible", 1);
        addIdFunctionProperty(idFunctionObject2, obj2, ConstructorId_preventExtensions, "preventExtensions", 1);
        addIdFunctionProperty(idFunctionObject2, obj2, ConstructorId_defineProperties, "defineProperties", 2);
        addIdFunctionProperty(idFunctionObject2, obj2, ConstructorId_create, "create", 2);
        addIdFunctionProperty(idFunctionObject2, obj2, ConstructorId_isSealed, "isSealed", 1);
        addIdFunctionProperty(idFunctionObject2, obj2, ConstructorId_isFrozen, "isFrozen", 1);
        addIdFunctionProperty(idFunctionObject2, obj2, ConstructorId_seal, "seal", 1);
        addIdFunctionProperty(idFunctionObject2, obj2, ConstructorId_freeze, "freeze", 1);
        addIdFunctionProperty(idFunctionObject2, obj2, ConstructorId_assign, "assign", 2);
        addIdFunctionProperty(idFunctionObject2, obj2, ConstructorId_is, "is", 2);
        super.fillConstructorProperties(idFunctionObject);
    }

    public int findPrototypeId(String str) {
        String str2;
        int length = str.length();
        int i = 7;
        if (length != 7) {
            if (length == 8) {
                char charAt = str.charAt(3);
                if (charAt == 'o') {
                    str2 = "toSource";
                    i = 8;
                } else if (charAt == 't') {
                    str2 = "toString";
                    i = 2;
                }
            } else if (length == 11) {
                str2 = "constructor";
                i = 1;
            } else if (length == 16) {
                char charAt2 = str.charAt(2);
                if (charAt2 == 'd') {
                    char charAt3 = str.charAt(8);
                    if (charAt3 == 'G') {
                        str2 = "__defineGetter__";
                        i = 9;
                    } else if (charAt3 == 'S') {
                        str2 = "__defineSetter__";
                        i = 10;
                    }
                } else if (charAt2 == 'l') {
                    char charAt4 = str.charAt(8);
                    if (charAt4 == 'G') {
                        str2 = "__lookupGetter__";
                        i = 11;
                    } else if (charAt4 == 'S') {
                        str2 = "__lookupSetter__";
                        i = 12;
                    }
                }
            } else if (length == 20) {
                str2 = "propertyIsEnumerable";
                i = 6;
            } else if (length == 13) {
                str2 = "isPrototypeOf";
            } else if (length == 14) {
                char charAt5 = str.charAt(0);
                if (charAt5 == 'h') {
                    str2 = "hasOwnProperty";
                    i = 5;
                } else if (charAt5 == 't') {
                    str2 = "toLocaleString";
                    i = 3;
                }
            }
            str2 = null;
            i = 0;
        } else {
            str2 = "valueOf";
            i = 4;
        }
        if (str2 == null || str2 == str || str2.equals(str)) {
            return i;
        }
        return 0;
    }

    public String getClassName() {
        return "Object";
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0032, code lost:
        r2 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0036, code lost:
        initPrototypeMethod(OBJECT_TAG, r4, r0, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x003b, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x001b, code lost:
        r0 = r1;
        r2 = 2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void initPrototypeId(int r4) {
        /*
            r3 = this;
            r0 = 2
            r1 = 0
            r2 = 1
            switch(r4) {
                case 1: goto L_0x0034;
                case 2: goto L_0x0030;
                case 3: goto L_0x002d;
                case 4: goto L_0x002a;
                case 5: goto L_0x0027;
                case 6: goto L_0x0024;
                case 7: goto L_0x0021;
                case 8: goto L_0x001e;
                case 9: goto L_0x0019;
                case 10: goto L_0x0016;
                case 11: goto L_0x0013;
                case 12: goto L_0x0010;
                default: goto L_0x0006;
            }
        L_0x0006:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r4 = java.lang.String.valueOf(r4)
            r0.<init>(r4)
            throw r0
        L_0x0010:
            java.lang.String r0 = "__lookupSetter__"
            goto L_0x0036
        L_0x0013:
            java.lang.String r0 = "__lookupGetter__"
            goto L_0x0036
        L_0x0016:
            java.lang.String r1 = "__defineSetter__"
            goto L_0x001b
        L_0x0019:
            java.lang.String r1 = "__defineGetter__"
        L_0x001b:
            r0 = r1
            r2 = 2
            goto L_0x0036
        L_0x001e:
            java.lang.String r0 = "toSource"
            goto L_0x0032
        L_0x0021:
            java.lang.String r0 = "isPrototypeOf"
            goto L_0x0036
        L_0x0024:
            java.lang.String r0 = "propertyIsEnumerable"
            goto L_0x0036
        L_0x0027:
            java.lang.String r0 = "hasOwnProperty"
            goto L_0x0036
        L_0x002a:
            java.lang.String r0 = "valueOf"
            goto L_0x0032
        L_0x002d:
            java.lang.String r0 = "toLocaleString"
            goto L_0x0032
        L_0x0030:
            java.lang.String r0 = "toString"
        L_0x0032:
            r2 = 0
            goto L_0x0036
        L_0x0034:
            java.lang.String r0 = "constructor"
        L_0x0036:
            java.lang.Object r1 = OBJECT_TAG
            r3.initPrototypeMethod(r1, r4, r0, r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NativeObject.initPrototypeId(int):void");
    }

    public Set<Object> keySet() {
        return new KeySet();
    }

    public Object put(Object obj, Object obj2) {
        throw new UnsupportedOperationException();
    }

    public void putAll(Map map) {
        throw new UnsupportedOperationException();
    }

    public Object remove(Object obj) {
        Object obj2 = get(obj);
        if (obj instanceof String) {
            delete((String) obj);
        } else if (obj instanceof Number) {
            delete(((Number) obj).intValue());
        }
        return obj2;
    }

    public String toString() {
        return ScriptRuntime.defaultObjectToString(this);
    }

    public Collection<Object> values() {
        return new ValueCollection();
    }
}
