package org.mozilla.javascript;

import j$.util.Iterator;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import org.mozilla.javascript.IteratorLikeIterable;
import org.mozilla.javascript.NativeArrayIterator;
import org.mozilla.javascript.TopLevel;
import org.mozilla.javascript.regexp.NativeRegExp;

public class NativeArray extends IdScriptableObject implements List {
    private static final Object ARRAY_TAG = "Array";
    private static final int ConstructorId_concat = -13;
    private static final int ConstructorId_every = -17;
    private static final int ConstructorId_filter = -18;
    private static final int ConstructorId_find = -22;
    private static final int ConstructorId_findIndex = -23;
    private static final int ConstructorId_forEach = -19;
    private static final int ConstructorId_from = -28;
    private static final int ConstructorId_indexOf = -15;
    private static final int ConstructorId_isArray = -26;
    private static final int ConstructorId_join = -5;
    private static final int ConstructorId_lastIndexOf = -16;
    private static final int ConstructorId_map = -20;
    private static final int ConstructorId_of = -27;
    private static final int ConstructorId_pop = -9;
    private static final int ConstructorId_push = -8;
    private static final int ConstructorId_reduce = -24;
    private static final int ConstructorId_reduceRight = -25;
    private static final int ConstructorId_reverse = -6;
    private static final int ConstructorId_shift = -10;
    private static final int ConstructorId_slice = -14;
    private static final int ConstructorId_some = -21;
    private static final int ConstructorId_sort = -7;
    private static final int ConstructorId_splice = -12;
    private static final int ConstructorId_unshift = -11;
    private static final Comparator<Object> DEFAULT_COMPARATOR = new ElementComparator();
    private static final int DEFAULT_INITIAL_CAPACITY = 10;
    private static final double GROW_FACTOR = 1.5d;
    private static final int Id_concat = 13;
    private static final int Id_constructor = 1;
    private static final int Id_copyWithin = 31;
    private static final int Id_entries = 29;
    private static final int Id_every = 17;
    private static final int Id_fill = 26;
    private static final int Id_filter = 18;
    private static final int Id_find = 22;
    private static final int Id_findIndex = 23;
    private static final int Id_forEach = 19;
    private static final int Id_includes = 30;
    private static final int Id_indexOf = 15;
    private static final int Id_join = 5;
    private static final int Id_keys = 27;
    private static final int Id_lastIndexOf = 16;
    private static final int Id_length = 1;
    private static final int Id_map = 20;
    private static final int Id_pop = 9;
    private static final int Id_push = 8;
    private static final int Id_reduce = 24;
    private static final int Id_reduceRight = 25;
    private static final int Id_reverse = 6;
    private static final int Id_shift = 10;
    private static final int Id_slice = 14;
    private static final int Id_some = 21;
    private static final int Id_sort = 7;
    private static final int Id_splice = 12;
    private static final int Id_toLocaleString = 3;
    private static final int Id_toSource = 4;
    private static final int Id_toString = 2;
    private static final int Id_unshift = 11;
    private static final int Id_values = 28;
    private static final int MAX_INSTANCE_ID = 1;
    private static final int MAX_PRE_GROW_SIZE = 1431655764;
    private static final int MAX_PROTOTYPE_ID = 32;
    private static final Long NEGATIVE_ONE = -1L;
    /* access modifiers changed from: private */
    public static final Comparator<Object> STRING_COMPARATOR = new StringLikeComparator();
    private static final int SymbolId_iterator = 32;
    private static int maximumInitialCapacity = 10000;
    private static final long serialVersionUID = 7331366857676127338L;
    private Object[] dense;
    private boolean denseOnly;
    private long length;
    private int lengthAttr;

    public static final class StringLikeComparator implements Comparator<Object>, Serializable {
        private static final long serialVersionUID = 5299017659728190979L;

        public int compare(Object obj, Object obj2) {
            return ScriptRuntime.toString(obj).compareTo(ScriptRuntime.toString(obj2));
        }
    }

    public NativeArray(long j) {
        this.lengthAttr = 6;
        boolean z = j <= ((long) maximumInitialCapacity);
        this.denseOnly = z;
        if (z) {
            int i = (int) j;
            Object[] objArr = new Object[(i < 10 ? 10 : i)];
            this.dense = objArr;
            Arrays.fill(objArr, Scriptable.NOT_FOUND);
        }
        this.length = j;
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0033  */
    /* JADX WARNING: Removed duplicated region for block: B:20:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static org.mozilla.javascript.Scriptable callConstructorOrCreateArray(org.mozilla.javascript.Context r4, org.mozilla.javascript.Scriptable r5, org.mozilla.javascript.Scriptable r6, long r7, boolean r9) {
        /*
            boolean r0 = r6 instanceof org.mozilla.javascript.Function
            r1 = 0
            if (r0 == 0) goto L_0x0030
            if (r9 != 0) goto L_0x0011
            r2 = 0
            int r9 = (r7 > r2 ? 1 : (r7 == r2 ? 0 : -1))
            if (r9 <= 0) goto L_0x000e
            goto L_0x0011
        L_0x000e:
            java.lang.Object[] r9 = org.mozilla.javascript.ScriptRuntime.emptyArgs     // Catch:{ EcmaError -> 0x0021 }
            goto L_0x001a
        L_0x0011:
            r9 = 1
            java.lang.Object[] r9 = new java.lang.Object[r9]     // Catch:{ EcmaError -> 0x0021 }
            java.lang.Long r0 = java.lang.Long.valueOf(r7)     // Catch:{ EcmaError -> 0x0021 }
            r9[r1] = r0     // Catch:{ EcmaError -> 0x0021 }
        L_0x001a:
            org.mozilla.javascript.Function r6 = (org.mozilla.javascript.Function) r6     // Catch:{ EcmaError -> 0x0021 }
            org.mozilla.javascript.Scriptable r6 = r6.construct(r4, r5, r9)     // Catch:{ EcmaError -> 0x0021 }
            goto L_0x0031
        L_0x0021:
            r6 = move-exception
            java.lang.String r9 = "TypeError"
            java.lang.String r0 = r6.getName()
            boolean r9 = r9.equals(r0)
            if (r9 == 0) goto L_0x002f
            goto L_0x0030
        L_0x002f:
            throw r6
        L_0x0030:
            r6 = 0
        L_0x0031:
            if (r6 != 0) goto L_0x0040
            r2 = 2147483647(0x7fffffff, double:1.060997895E-314)
            int r6 = (r7 > r2 ? 1 : (r7 == r2 ? 0 : -1))
            if (r6 <= 0) goto L_0x003b
            goto L_0x003c
        L_0x003b:
            int r1 = (int) r7
        L_0x003c:
            org.mozilla.javascript.Scriptable r6 = r4.newArray((org.mozilla.javascript.Scriptable) r5, (int) r1)
        L_0x0040:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NativeArray.callConstructorOrCreateArray(org.mozilla.javascript.Context, org.mozilla.javascript.Scriptable, org.mozilla.javascript.Scriptable, long, boolean):org.mozilla.javascript.Scriptable");
    }

    private static long concatSpreadArg(Context context, Scriptable scriptable, Scriptable scriptable2, long j) {
        long lengthProperty = getLengthProperty(context, scriptable2, false);
        long j2 = lengthProperty + j;
        if (j2 <= 2147483647L && (scriptable instanceof NativeArray)) {
            NativeArray nativeArray = (NativeArray) scriptable;
            if (nativeArray.denseOnly && (scriptable2 instanceof NativeArray)) {
                NativeArray nativeArray2 = (NativeArray) scriptable2;
                if (nativeArray2.denseOnly) {
                    nativeArray.ensureCapacity((int) j2);
                    System.arraycopy(nativeArray2.dense, 0, nativeArray.dense, (int) j, (int) lengthProperty);
                    return j2;
                }
            }
        }
        long j3 = 0;
        while (j3 < lengthProperty) {
            Object rawElem = getRawElem(scriptable2, j3);
            if (rawElem != Scriptable.NOT_FOUND) {
                defineElem(context, scriptable, j, rawElem);
            }
            j3++;
            j++;
        }
        return j2;
    }

    private ScriptableObject defaultIndexPropertyDescriptor(Object obj) {
        Scriptable parentScope = getParentScope();
        if (parentScope == null) {
            parentScope = this;
        }
        NativeObject nativeObject = new NativeObject();
        ScriptRuntime.setBuiltinProtoAndParent(nativeObject, parentScope, TopLevel.Builtins.Object);
        nativeObject.defineProperty(ES6Iterator.VALUE_PROPERTY, obj, 0);
        Boolean bool = Boolean.TRUE;
        nativeObject.defineProperty("writable", (Object) bool, 0);
        nativeObject.defineProperty("enumerable", (Object) bool, 0);
        nativeObject.defineProperty("configurable", (Object) bool, 0);
        return nativeObject;
    }

    private static void defineElem(Context context, Scriptable scriptable, long j, Object obj) {
        if (j > 2147483647L) {
            scriptable.put(Long.toString(j), scriptable, obj);
        } else {
            scriptable.put((int) j, scriptable, obj);
        }
    }

    private static void deleteElem(Scriptable scriptable, long j) {
        int i = (int) j;
        if (((long) i) == j) {
            scriptable.delete(i);
        } else {
            scriptable.delete(Long.toString(j));
        }
    }

    private static long doConcat(Context context, Scriptable scriptable, Scriptable scriptable2, Object obj, long j) {
        if (isConcatSpreadable(context, scriptable, obj)) {
            return concatSpreadArg(context, scriptable2, (Scriptable) obj, j);
        }
        defineElem(context, scriptable2, j, obj);
        return j + 1;
    }

    private boolean ensureCapacity(int i) {
        Object[] objArr = this.dense;
        if (i <= objArr.length) {
            return true;
        }
        if (i > MAX_PRE_GROW_SIZE) {
            this.denseOnly = false;
            return false;
        }
        int max = Math.max(i, (int) (((double) objArr.length) * GROW_FACTOR));
        Object[] objArr2 = new Object[max];
        Object[] objArr3 = this.dense;
        System.arraycopy(objArr3, 0, objArr2, 0, objArr3.length);
        Arrays.fill(objArr2, this.dense.length, max, Scriptable.NOT_FOUND);
        this.dense = objArr2;
        return true;
    }

    private static Object getElem(Context context, Scriptable scriptable, long j) {
        Object rawElem = getRawElem(scriptable, j);
        if (rawElem != Scriptable.NOT_FOUND) {
            return rawElem;
        }
        return Undefined.instance;
    }

    public static long getLengthProperty(Context context, Scriptable scriptable, boolean z) {
        if (scriptable instanceof NativeString) {
            return (long) ((NativeString) scriptable).getLength();
        }
        if (scriptable instanceof NativeArray) {
            return ((NativeArray) scriptable).getLength();
        }
        Object property = ScriptableObject.getProperty(scriptable, "length");
        if (property == Scriptable.NOT_FOUND) {
            return 0;
        }
        double number = ScriptRuntime.toNumber(property);
        if (number > 9.007199254740991E15d) {
            if (!z) {
                return 2147483647L;
            }
            throw ScriptRuntime.rangeError(ScriptRuntime.getMessage0("msg.arraylength.bad"));
        } else if (number < 0.0d) {
            return 0;
        } else {
            return ScriptRuntime.toUint32(property);
        }
    }

    public static int getMaximumInitialCapacity() {
        return maximumInitialCapacity;
    }

    private static Object getRawElem(Scriptable scriptable, long j) {
        if (j > 2147483647L) {
            return ScriptableObject.getProperty(scriptable, Long.toString(j));
        }
        return ScriptableObject.getProperty(scriptable, (int) j);
    }

    public static void init(Scriptable scriptable, boolean z) {
        new NativeArray(0).exportAsJSClass(32, scriptable, z);
    }

    private static boolean isConcatSpreadable(Context context, Scriptable scriptable, Object obj) {
        Object property;
        if ((obj instanceof Scriptable) && (property = ScriptableObject.getProperty((Scriptable) obj, (Symbol) SymbolKey.IS_CONCAT_SPREADABLE)) != Scriptable.NOT_FOUND && !Undefined.isUndefined(property)) {
            return ScriptRuntime.toBoolean(property);
        }
        if (context.getLanguageVersion() >= 200 || !ScriptRuntime.instanceOf(obj, ScriptRuntime.getExistingCtor(context, scriptable, "Array"), context)) {
            return js_isArray(obj);
        }
        return true;
    }

    private static Object iterativeMethod(Context context, IdFunctionObject idFunctionObject, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        boolean z;
        Object obj;
        Scriptable scriptable3;
        Scriptable scriptable4;
        long j;
        long j2;
        int i;
        Object obj2;
        Context context2 = context;
        Scriptable scriptable5 = scriptable;
        Object[] objArr2 = objArr;
        Scriptable object = ScriptRuntime.toObject(context2, scriptable5, (Object) scriptable2);
        int abs = Math.abs(idFunctionObject.methodId());
        int i2 = 23;
        if (22 == abs || 23 == abs) {
            ScriptRuntimeES6.requireObjectCoercible(context2, object, idFunctionObject);
        }
        if (abs == 20) {
            z = true;
        } else {
            z = false;
        }
        long lengthProperty = getLengthProperty(context2, object, z);
        if (objArr2.length > 0) {
            obj = objArr2[0];
        } else {
            obj = Undefined.instance;
        }
        if (obj == null || !(obj instanceof Function)) {
            throw ScriptRuntime.notFunctionError(obj);
        } else if (context.getLanguageVersion() < 200 || !(obj instanceof NativeRegExp)) {
            Function function = (Function) obj;
            Scriptable topLevelScope = ScriptableObject.getTopLevelScope(function);
            if (objArr2.length < 2 || (obj2 = objArr2[1]) == null || obj2 == Undefined.instance) {
                scriptable3 = topLevelScope;
            } else {
                scriptable3 = ScriptRuntime.toObject(context2, scriptable5, obj2);
            }
            if (abs == 18 || abs == 20) {
                if (abs == 20) {
                    i = (int) lengthProperty;
                } else {
                    i = 0;
                }
                scriptable4 = context2.newArray(scriptable5, i);
            } else {
                scriptable4 = null;
            }
            long j3 = 0;
            long j4 = 0;
            while (j3 < lengthProperty) {
                Object[] objArr3 = new Object[3];
                Object rawElem = getRawElem(object, j3);
                if (rawElem == Scriptable.NOT_FOUND) {
                    if (abs == 22 || abs == i2) {
                        rawElem = Undefined.instance;
                    } else {
                        j = lengthProperty;
                        j2 = j4;
                        j4 = j2;
                        j3++;
                        lengthProperty = j;
                        i2 = 23;
                    }
                }
                objArr3[0] = rawElem;
                objArr3[1] = Long.valueOf(j3);
                objArr3[2] = object;
                Object call = function.call(context2, topLevelScope, scriptable3, objArr3);
                switch (abs) {
                    case 17:
                        j = lengthProperty;
                        j2 = j4;
                        if (!ScriptRuntime.toBoolean(call)) {
                            return Boolean.FALSE;
                        }
                        break;
                    case 18:
                        if (ScriptRuntime.toBoolean(call)) {
                            j = lengthProperty;
                            long j5 = j4;
                            j4 = j5 + 1;
                            defineElem(context2, scriptable4, j5, objArr3[0]);
                            continue;
                        }
                        break;
                    case 20:
                        defineElem(context2, scriptable4, j3, call);
                        break;
                    case 21:
                        if (ScriptRuntime.toBoolean(call)) {
                            return Boolean.TRUE;
                        }
                        break;
                    case 22:
                        if (ScriptRuntime.toBoolean(call)) {
                            return rawElem;
                        }
                        break;
                    case 23:
                        if (ScriptRuntime.toBoolean(call)) {
                            return ScriptRuntime.wrapNumber((double) j3);
                        }
                        break;
                }
                j = lengthProperty;
                j2 = j4;
                j4 = j2;
                j3++;
                lengthProperty = j;
                i2 = 23;
            }
            switch (abs) {
                case 17:
                    return Boolean.TRUE;
                case 18:
                case 20:
                    return scriptable4;
                case 21:
                    return Boolean.FALSE;
                case 23:
                    return ScriptRuntime.wrapNumber(-1.0d);
                default:
                    return Undefined.instance;
            }
        } else {
            throw ScriptRuntime.notFunctionError(obj);
        }
    }

    private static Object jsConstructor(Context context, Scriptable scriptable, Object[] objArr) {
        if (objArr.length == 0) {
            return new NativeArray(0);
        }
        if (context.getLanguageVersion() == 120) {
            return new NativeArray(objArr);
        }
        Number number = objArr[0];
        if (objArr.length > 1 || !(number instanceof Number)) {
            return new NativeArray(objArr);
        }
        long uint32 = ScriptRuntime.toUint32((Object) number);
        if (((double) uint32) == number.doubleValue()) {
            return new NativeArray(uint32);
        }
        throw ScriptRuntime.rangeError(ScriptRuntime.getMessage0("msg.arraylength.bad"));
    }

    private static Scriptable js_concat(Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        Scriptable object = ScriptRuntime.toObject(context, scriptable, (Object) scriptable2);
        Scriptable topLevelScope = ScriptableObject.getTopLevelScope(scriptable);
        Scriptable newArray = context.newArray(topLevelScope, 0);
        long doConcat = doConcat(context, topLevelScope, newArray, object, 0);
        long j = doConcat;
        for (Object doConcat2 : objArr) {
            j = doConcat(context, topLevelScope, newArray, doConcat2, j);
        }
        setLengthProperty(context, newArray, j);
        return newArray;
    }

    private static Object js_copyWithin(Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        Object obj;
        long j;
        Object obj2;
        long j2;
        long j3;
        long j4;
        Context context2 = context;
        Object[] objArr2 = objArr;
        Scriptable object = ScriptRuntime.toObject(context, scriptable, (Object) scriptable2);
        long lengthProperty = getLengthProperty(context2, object, false);
        int i = 1;
        if (objArr2.length >= 1) {
            obj = objArr2[0];
        } else {
            obj = Undefined.instance;
        }
        long integer = (long) ScriptRuntime.toInteger(obj);
        if (integer < 0) {
            j = Math.max(integer + lengthProperty, 0);
        } else {
            j = Math.min(integer, lengthProperty);
        }
        if (objArr2.length >= 2) {
            obj2 = objArr2[1];
        } else {
            obj2 = Undefined.instance;
        }
        long integer2 = (long) ScriptRuntime.toInteger(obj2);
        if (integer2 < 0) {
            j2 = Math.max(integer2 + lengthProperty, 0);
        } else {
            j2 = Math.min(integer2, lengthProperty);
        }
        if (objArr2.length < 3 || Undefined.isUndefined(objArr2[2])) {
            j3 = lengthProperty;
        } else {
            j3 = (long) ScriptRuntime.toInteger(objArr2[2]);
        }
        if (j3 < 0) {
            j4 = Math.max(j3 + lengthProperty, 0);
        } else {
            j4 = Math.min(j3, lengthProperty);
        }
        long min = Math.min(j4 - j2, lengthProperty - j);
        if (j2 < j) {
            long j5 = j2 + min;
            if (j < j5) {
                j2 = j5 - 1;
                j = (j + min) - 1;
                i = -1;
            }
        }
        if ((object instanceof NativeArray) && min <= 2147483647L) {
            NativeArray nativeArray = (NativeArray) object;
            if (nativeArray.denseOnly) {
                while (min > 0) {
                    Object[] objArr3 = nativeArray.dense;
                    objArr3[(int) j] = objArr3[(int) j2];
                    long j6 = (long) i;
                    j2 += j6;
                    j += j6;
                    min--;
                }
                return scriptable2;
            }
        }
        while (min > 0) {
            Object rawElem = getRawElem(object, j2);
            if (rawElem == Scriptable.NOT_FOUND || Undefined.isUndefined(rawElem)) {
                deleteElem(object, j);
            } else {
                setElem(context2, object, j, rawElem);
            }
            long j7 = (long) i;
            j2 += j7;
            j += j7;
            min--;
        }
        return scriptable2;
    }

    private static Object js_fill(Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        long j;
        long j2;
        long j3;
        long j4;
        Object obj;
        long lengthProperty = getLengthProperty(context, ScriptRuntime.toObject(context, scriptable, (Object) scriptable2), false);
        if (objArr.length >= 2) {
            j = (long) ScriptRuntime.toInteger(objArr[1]);
        } else {
            j = 0;
        }
        if (j < 0) {
            j2 = Math.max(j + lengthProperty, 0);
        } else {
            j2 = Math.min(j, lengthProperty);
        }
        if (objArr.length < 3 || Undefined.isUndefined(objArr[2])) {
            j3 = lengthProperty;
        } else {
            j3 = (long) ScriptRuntime.toInteger(objArr[2]);
        }
        if (j3 < 0) {
            j4 = Math.max(lengthProperty + j3, 0);
        } else {
            j4 = Math.min(j3, lengthProperty);
        }
        if (objArr.length > 0) {
            obj = objArr[0];
        } else {
            obj = Undefined.instance;
        }
        while (j2 < j4) {
            setRawElem(context, scriptable2, j2, obj);
            j2++;
        }
        return scriptable2;
    }

    private static Object js_from(Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        Object obj;
        Object obj2;
        Function function;
        Throwable th;
        Context context2 = context;
        Scriptable scriptable3 = scriptable;
        Object[] objArr2 = objArr;
        if (objArr2.length >= 1) {
            obj = objArr2[0];
        } else {
            obj = Undefined.instance;
        }
        Scriptable object = ScriptRuntime.toObject(scriptable3, obj);
        if (objArr2.length >= 2) {
            obj2 = objArr2[1];
        } else {
            obj2 = Undefined.instance;
        }
        Scriptable scriptable4 = Undefined.SCRIPTABLE_UNDEFINED;
        boolean z = !Undefined.isUndefined(obj2);
        if (!z) {
            function = null;
        } else if (obj2 instanceof Function) {
            function = (Function) obj2;
            if (objArr2.length >= 3) {
                scriptable4 = ScriptableObject.ensureScriptable(objArr2[2]);
            }
        } else {
            throw ScriptRuntime.typeError0("msg.map.function.not");
        }
        Function function2 = function;
        Scriptable scriptable5 = scriptable4;
        Object property = ScriptableObject.getProperty(object, (Symbol) SymbolKey.ITERATOR);
        if (!(object instanceof NativeArray) && property != Scriptable.NOT_FOUND && !Undefined.isUndefined(property)) {
            Object callIterator = ScriptRuntime.callIterator(object, context2, scriptable3);
            if (!Undefined.isUndefined(callIterator)) {
                Scriptable callConstructorOrCreateArray = callConstructorOrCreateArray(context, scriptable, scriptable2, 0, false);
                IteratorLikeIterable iteratorLikeIterable = new IteratorLikeIterable(context2, scriptable3, callIterator);
                try {
                    IteratorLikeIterable.Itr it = iteratorLikeIterable.iterator();
                    long j = 0;
                    while (it.hasNext()) {
                        Object next = it.next();
                        if (z) {
                            next = function2.call(context2, scriptable3, scriptable5, new Object[]{next, Long.valueOf(j)});
                        }
                        defineElem(context2, callConstructorOrCreateArray, j, next);
                        j++;
                    }
                    iteratorLikeIterable.close();
                    setLengthProperty(context2, callConstructorOrCreateArray, j);
                    return callConstructorOrCreateArray;
                } catch (Throwable th2) {
                    Throwable th3 = th2;
                    try {
                        iteratorLikeIterable.close();
                    } catch (Throwable th4) {
                        th.addSuppressed(th4);
                    }
                    throw th3;
                }
            }
        }
        long lengthProperty = getLengthProperty(context2, object, false);
        Scriptable scriptable6 = scriptable2;
        long j2 = lengthProperty;
        Scriptable callConstructorOrCreateArray2 = callConstructorOrCreateArray(context, scriptable, scriptable6, lengthProperty, true);
        for (long j3 = 0; j3 < lengthProperty; j3++) {
            Object rawElem = getRawElem(object, j3);
            if (rawElem != Scriptable.NOT_FOUND) {
                if (z) {
                    rawElem = function2.call(context2, scriptable3, scriptable5, new Object[]{rawElem, Long.valueOf(j3)});
                }
                defineElem(context2, callConstructorOrCreateArray2, j3, rawElem);
            }
        }
        setLengthProperty(context2, callConstructorOrCreateArray2, lengthProperty);
        return callConstructorOrCreateArray2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x003a, code lost:
        if (r9 < 0) goto L_0x003e;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.Boolean js_includes(org.mozilla.javascript.Context r8, org.mozilla.javascript.Scriptable r9, org.mozilla.javascript.Scriptable r10, java.lang.Object[] r11) {
        /*
            int r0 = r11.length
            r1 = 0
            if (r0 <= 0) goto L_0x0007
            r0 = r11[r1]
            goto L_0x0009
        L_0x0007:
            java.lang.Object r0 = org.mozilla.javascript.Undefined.instance
        L_0x0009:
            org.mozilla.javascript.Scriptable r8 = org.mozilla.javascript.ScriptRuntime.toObject((org.mozilla.javascript.Context) r8, (org.mozilla.javascript.Scriptable) r9, (java.lang.Object) r10)
            r9 = 1
            java.lang.Object[] r2 = new java.lang.Object[r9]
            java.lang.String r3 = "length"
            java.lang.Object r10 = org.mozilla.javascript.ScriptableObject.getProperty((org.mozilla.javascript.Scriptable) r10, (java.lang.String) r3)
            r2[r1] = r10
            long r1 = org.mozilla.javascript.ScriptRuntime.toLength(r2, r1)
            r3 = 0
            int r10 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r10 != 0) goto L_0x0025
            java.lang.Boolean r8 = java.lang.Boolean.FALSE
            return r8
        L_0x0025:
            int r10 = r11.length
            r5 = 2
            r6 = 1
            if (r10 >= r5) goto L_0x002c
            goto L_0x0047
        L_0x002c:
            r9 = r11[r9]
            double r9 = org.mozilla.javascript.ScriptRuntime.toInteger((java.lang.Object) r9)
            long r9 = (long) r9
            int r11 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1))
            if (r11 >= 0) goto L_0x003d
            long r9 = r9 + r1
            int r11 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1))
            if (r11 >= 0) goto L_0x003d
            goto L_0x003e
        L_0x003d:
            r3 = r9
        L_0x003e:
            long r9 = r1 - r6
            int r11 = (r3 > r9 ? 1 : (r3 == r9 ? 0 : -1))
            if (r11 <= 0) goto L_0x0047
            java.lang.Boolean r8 = java.lang.Boolean.FALSE
            return r8
        L_0x0047:
            boolean r9 = r8 instanceof org.mozilla.javascript.NativeArray
            if (r9 == 0) goto L_0x007d
            r9 = r8
            org.mozilla.javascript.NativeArray r9 = (org.mozilla.javascript.NativeArray) r9
            boolean r10 = r9.denseOnly
            if (r10 == 0) goto L_0x007d
            org.mozilla.javascript.Scriptable r8 = r9.getPrototype()
            int r10 = (int) r3
        L_0x0057:
            long r3 = (long) r10
            int r11 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r11 >= 0) goto L_0x007a
            java.lang.Object[] r11 = r9.dense
            r11 = r11[r10]
            java.lang.Object r3 = org.mozilla.javascript.Scriptable.NOT_FOUND
            if (r11 != r3) goto L_0x006a
            if (r8 == 0) goto L_0x006a
            java.lang.Object r11 = org.mozilla.javascript.ScriptableObject.getProperty((org.mozilla.javascript.Scriptable) r8, (int) r10)
        L_0x006a:
            if (r11 != r3) goto L_0x006e
            java.lang.Object r11 = org.mozilla.javascript.Undefined.instance
        L_0x006e:
            boolean r11 = org.mozilla.javascript.ScriptRuntime.sameZero(r11, r0)
            if (r11 == 0) goto L_0x0077
            java.lang.Boolean r8 = java.lang.Boolean.TRUE
            return r8
        L_0x0077:
            int r10 = r10 + 1
            goto L_0x0057
        L_0x007a:
            java.lang.Boolean r8 = java.lang.Boolean.FALSE
            return r8
        L_0x007d:
            int r9 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r9 >= 0) goto L_0x0096
            java.lang.Object r9 = getRawElem(r8, r3)
            java.lang.Object r10 = org.mozilla.javascript.Scriptable.NOT_FOUND
            if (r9 != r10) goto L_0x008b
            java.lang.Object r9 = org.mozilla.javascript.Undefined.instance
        L_0x008b:
            boolean r9 = org.mozilla.javascript.ScriptRuntime.sameZero(r9, r0)
            if (r9 == 0) goto L_0x0094
            java.lang.Boolean r8 = java.lang.Boolean.TRUE
            return r8
        L_0x0094:
            long r3 = r3 + r6
            goto L_0x007d
        L_0x0096:
            java.lang.Boolean r8 = java.lang.Boolean.FALSE
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NativeArray.js_includes(org.mozilla.javascript.Context, org.mozilla.javascript.Scriptable, org.mozilla.javascript.Scriptable, java.lang.Object[]):java.lang.Boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0029, code lost:
        if (r9 < 0) goto L_0x002d;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.Object js_indexOf(org.mozilla.javascript.Context r7, org.mozilla.javascript.Scriptable r8, org.mozilla.javascript.Scriptable r9, java.lang.Object[] r10) {
        /*
            int r0 = r10.length
            r1 = 0
            if (r0 <= 0) goto L_0x0007
            r0 = r10[r1]
            goto L_0x0009
        L_0x0007:
            java.lang.Object r0 = org.mozilla.javascript.Undefined.instance
        L_0x0009:
            org.mozilla.javascript.Scriptable r8 = org.mozilla.javascript.ScriptRuntime.toObject((org.mozilla.javascript.Context) r7, (org.mozilla.javascript.Scriptable) r8, (java.lang.Object) r9)
            long r1 = getLengthProperty(r7, r8, r1)
            int r7 = r10.length
            r9 = 2
            r3 = 1
            r5 = 0
            if (r7 >= r9) goto L_0x001a
            goto L_0x0036
        L_0x001a:
            r7 = 1
            r7 = r10[r7]
            double r9 = org.mozilla.javascript.ScriptRuntime.toInteger((java.lang.Object) r7)
            long r9 = (long) r9
            int r7 = (r9 > r5 ? 1 : (r9 == r5 ? 0 : -1))
            if (r7 >= 0) goto L_0x002c
            long r9 = r9 + r1
            int r7 = (r9 > r5 ? 1 : (r9 == r5 ? 0 : -1))
            if (r7 >= 0) goto L_0x002c
            goto L_0x002d
        L_0x002c:
            r5 = r9
        L_0x002d:
            long r9 = r1 - r3
            int r7 = (r5 > r9 ? 1 : (r5 == r9 ? 0 : -1))
            if (r7 <= 0) goto L_0x0036
            java.lang.Long r7 = NEGATIVE_ONE
            return r7
        L_0x0036:
            boolean r7 = r8 instanceof org.mozilla.javascript.NativeArray
            if (r7 == 0) goto L_0x006c
            r7 = r8
            org.mozilla.javascript.NativeArray r7 = (org.mozilla.javascript.NativeArray) r7
            boolean r9 = r7.denseOnly
            if (r9 == 0) goto L_0x006c
            org.mozilla.javascript.Scriptable r8 = r7.getPrototype()
            int r9 = (int) r5
        L_0x0046:
            long r3 = (long) r9
            int r10 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r10 >= 0) goto L_0x0069
            java.lang.Object[] r10 = r7.dense
            r10 = r10[r9]
            java.lang.Object r5 = org.mozilla.javascript.Scriptable.NOT_FOUND
            if (r10 != r5) goto L_0x0059
            if (r8 == 0) goto L_0x0059
            java.lang.Object r10 = org.mozilla.javascript.ScriptableObject.getProperty((org.mozilla.javascript.Scriptable) r8, (int) r9)
        L_0x0059:
            if (r10 == r5) goto L_0x0066
            boolean r10 = org.mozilla.javascript.ScriptRuntime.shallowEq(r10, r0)
            if (r10 == 0) goto L_0x0066
            java.lang.Long r7 = java.lang.Long.valueOf(r3)
            return r7
        L_0x0066:
            int r9 = r9 + 1
            goto L_0x0046
        L_0x0069:
            java.lang.Long r7 = NEGATIVE_ONE
            return r7
        L_0x006c:
            int r7 = (r5 > r1 ? 1 : (r5 == r1 ? 0 : -1))
            if (r7 >= 0) goto L_0x0085
            java.lang.Object r7 = getRawElem(r8, r5)
            java.lang.Object r9 = org.mozilla.javascript.Scriptable.NOT_FOUND
            if (r7 == r9) goto L_0x0083
            boolean r7 = org.mozilla.javascript.ScriptRuntime.shallowEq(r7, r0)
            if (r7 == 0) goto L_0x0083
            java.lang.Long r7 = java.lang.Long.valueOf(r5)
            return r7
        L_0x0083:
            long r5 = r5 + r3
            goto L_0x006c
        L_0x0085:
            java.lang.Long r7 = NEGATIVE_ONE
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NativeArray.js_indexOf(org.mozilla.javascript.Context, org.mozilla.javascript.Scriptable, org.mozilla.javascript.Scriptable, java.lang.Object[]):java.lang.Object");
    }

    private static boolean js_isArray(Object obj) {
        if (!(obj instanceof Scriptable)) {
            return false;
        }
        return "Array".equals(((Scriptable) obj).getClassName());
    }

    private static String js_join(Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        String str;
        Object obj;
        Object obj2;
        Scriptable object = ScriptRuntime.toObject(context, scriptable, (Object) scriptable2);
        int i = 0;
        long lengthProperty = getLengthProperty(context, object, false);
        int i2 = (int) lengthProperty;
        if (lengthProperty == ((long) i2)) {
            if (objArr.length < 1 || (obj2 = objArr[0]) == Undefined.instance) {
                str = ",";
            } else {
                str = ScriptRuntime.toString(obj2);
            }
            if (object instanceof NativeArray) {
                NativeArray nativeArray = (NativeArray) object;
                if (nativeArray.denseOnly) {
                    StringBuilder sb = new StringBuilder();
                    while (i < i2) {
                        if (i != 0) {
                            sb.append(str);
                        }
                        Object[] objArr2 = nativeArray.dense;
                        if (!(i >= objArr2.length || (obj = objArr2[i]) == null || obj == Undefined.instance || obj == Scriptable.NOT_FOUND)) {
                            sb.append(ScriptRuntime.toString(obj));
                        }
                        i++;
                    }
                    return sb.toString();
                }
            }
            if (i2 == 0) {
                return "";
            }
            String[] strArr = new String[i2];
            int i3 = 0;
            for (int i4 = 0; i4 != i2; i4++) {
                Object elem = getElem(context, object, (long) i4);
                if (!(elem == null || elem == Undefined.instance)) {
                    String scriptRuntime = ScriptRuntime.toString(elem);
                    i3 += scriptRuntime.length();
                    strArr[i4] = scriptRuntime;
                }
            }
            StringBuilder sb2 = new StringBuilder((str.length() * (i2 - 1)) + i3);
            while (i != i2) {
                if (i != 0) {
                    sb2.append(str);
                }
                String str2 = strArr[i];
                if (str2 != null) {
                    sb2.append(str2);
                }
                i++;
            }
            return sb2.toString();
        }
        throw Context.reportRuntimeError1("msg.arraylength.too.big", String.valueOf(lengthProperty));
    }

    private static Object js_lastIndexOf(Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        Object obj;
        long j;
        if (objArr.length > 0) {
            obj = objArr[0];
        } else {
            obj = Undefined.instance;
        }
        Scriptable object = ScriptRuntime.toObject(context, scriptable, (Object) scriptable2);
        long lengthProperty = getLengthProperty(context, object, false);
        if (objArr.length < 2) {
            j = lengthProperty - 1;
        } else {
            long integer = (long) ScriptRuntime.toInteger(objArr[1]);
            if (integer >= lengthProperty) {
                j = lengthProperty - 1;
            } else {
                if (integer < 0) {
                    integer += lengthProperty;
                }
                j = integer;
            }
            if (j < 0) {
                return NEGATIVE_ONE;
            }
        }
        if (object instanceof NativeArray) {
            NativeArray nativeArray = (NativeArray) object;
            if (nativeArray.denseOnly) {
                Scriptable prototype = nativeArray.getPrototype();
                for (int i = (int) j; i >= 0; i--) {
                    Object obj2 = nativeArray.dense[i];
                    Object obj3 = Scriptable.NOT_FOUND;
                    if (obj2 == obj3 && prototype != null) {
                        obj2 = ScriptableObject.getProperty(prototype, i);
                    }
                    if (obj2 != obj3 && ScriptRuntime.shallowEq(obj2, obj)) {
                        return Long.valueOf((long) i);
                    }
                }
                return NEGATIVE_ONE;
            }
        }
        while (j >= 0) {
            Object rawElem = getRawElem(object, j);
            if (rawElem != Scriptable.NOT_FOUND && ScriptRuntime.shallowEq(rawElem, obj)) {
                return Long.valueOf(j);
            }
            j--;
        }
        return NEGATIVE_ONE;
    }

    private static Object js_of(Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        Scriptable callConstructorOrCreateArray = callConstructorOrCreateArray(context, scriptable, scriptable2, (long) objArr.length, true);
        for (int i = 0; i < objArr.length; i++) {
            defineElem(context, callConstructorOrCreateArray, (long) i, objArr[i]);
        }
        setLengthProperty(context, callConstructorOrCreateArray, (long) objArr.length);
        return callConstructorOrCreateArray;
    }

    private static Object js_pop(Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        Object obj;
        Scriptable object = ScriptRuntime.toObject(context, scriptable, (Object) scriptable2);
        if (object instanceof NativeArray) {
            NativeArray nativeArray = (NativeArray) object;
            if (nativeArray.denseOnly) {
                long j = nativeArray.length;
                if (j > 0) {
                    long j2 = j - 1;
                    nativeArray.length = j2;
                    Object[] objArr2 = nativeArray.dense;
                    Object obj2 = objArr2[(int) j2];
                    objArr2[(int) j2] = Scriptable.NOT_FOUND;
                    return obj2;
                }
            }
        }
        long lengthProperty = getLengthProperty(context, object, false);
        if (lengthProperty > 0) {
            lengthProperty--;
            obj = getElem(context, object, lengthProperty);
            deleteElem(object, lengthProperty);
        } else {
            obj = Undefined.instance;
        }
        setLengthProperty(context, object, lengthProperty);
        return obj;
    }

    private static Object js_push(Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        Scriptable object = ScriptRuntime.toObject(context, scriptable, (Object) scriptable2);
        int i = 0;
        if (object instanceof NativeArray) {
            NativeArray nativeArray = (NativeArray) object;
            if (nativeArray.denseOnly && nativeArray.ensureCapacity(((int) nativeArray.length) + objArr.length)) {
                while (i < objArr.length) {
                    Object[] objArr2 = nativeArray.dense;
                    long j = nativeArray.length;
                    nativeArray.length = 1 + j;
                    objArr2[(int) j] = objArr[i];
                    i++;
                }
                return ScriptRuntime.wrapNumber((double) nativeArray.length);
            }
        }
        long lengthProperty = getLengthProperty(context, object, false);
        while (i < objArr.length) {
            setElem(context, object, ((long) i) + lengthProperty, objArr[i]);
            i++;
        }
        Object lengthProperty2 = setLengthProperty(context, object, lengthProperty + ((long) objArr.length));
        if (context.getLanguageVersion() != 120) {
            return lengthProperty2;
        }
        if (objArr.length == 0) {
            return Undefined.instance;
        }
        return objArr[objArr.length - 1];
    }

    private static Scriptable js_reverse(Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        Scriptable object = ScriptRuntime.toObject(context, scriptable, (Object) scriptable2);
        int i = 0;
        if (object instanceof NativeArray) {
            NativeArray nativeArray = (NativeArray) object;
            if (nativeArray.denseOnly) {
                for (int i2 = ((int) nativeArray.length) - 1; i < i2; i2--) {
                    Object[] objArr2 = nativeArray.dense;
                    Object obj = objArr2[i];
                    objArr2[i] = objArr2[i2];
                    objArr2[i2] = obj;
                    i++;
                }
                return object;
            }
        }
        long lengthProperty = getLengthProperty(context, object, false);
        long j = lengthProperty / 2;
        for (long j2 = 0; j2 < j; j2++) {
            long j3 = (lengthProperty - j2) - 1;
            Object rawElem = getRawElem(object, j2);
            setRawElem(context, object, j2, getRawElem(object, j3));
            setRawElem(context, object, j3, rawElem);
        }
        return object;
    }

    private static Object js_shift(Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        Object obj;
        Scriptable object = ScriptRuntime.toObject(context, scriptable, (Object) scriptable2);
        if (object instanceof NativeArray) {
            NativeArray nativeArray = (NativeArray) object;
            if (nativeArray.denseOnly) {
                long j = nativeArray.length;
                if (j > 0) {
                    long j2 = j - 1;
                    nativeArray.length = j2;
                    Object[] objArr2 = nativeArray.dense;
                    Object obj2 = objArr2[0];
                    System.arraycopy(objArr2, 1, objArr2, 0, (int) j2);
                    Object[] objArr3 = nativeArray.dense;
                    int i = (int) nativeArray.length;
                    Object obj3 = Scriptable.NOT_FOUND;
                    objArr3[i] = obj3;
                    if (obj2 == obj3) {
                        return Undefined.instance;
                    }
                    return obj2;
                }
            }
        }
        long lengthProperty = getLengthProperty(context, object, false);
        if (lengthProperty > 0) {
            lengthProperty--;
            obj = getElem(context, object, 0);
            if (lengthProperty > 0) {
                for (long j3 = 1; j3 <= lengthProperty; j3++) {
                    setRawElem(context, object, j3 - 1, getRawElem(object, j3));
                }
            }
            deleteElem(object, lengthProperty);
        } else {
            obj = Undefined.instance;
        }
        setLengthProperty(context, object, lengthProperty);
        return obj;
    }

    private static Scriptable js_slice(Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        long j;
        Object obj;
        Scriptable object = ScriptRuntime.toObject(context, scriptable, (Object) scriptable2);
        Scriptable newArray = context.newArray(scriptable, 0);
        long lengthProperty = getLengthProperty(context, object, false);
        if (objArr.length == 0) {
            j = 0;
        } else {
            j = toSliceIndex(ScriptRuntime.toInteger(objArr[0]), lengthProperty);
            if (!(objArr.length == 1 || (obj = objArr[1]) == Undefined.instance)) {
                lengthProperty = toSliceIndex(ScriptRuntime.toInteger(obj), lengthProperty);
            }
        }
        for (long j2 = j; j2 < lengthProperty; j2++) {
            Object rawElem = getRawElem(object, j2);
            if (rawElem != Scriptable.NOT_FOUND) {
                defineElem(context, newArray, j2 - j, rawElem);
            }
        }
        setLengthProperty(context, newArray, Math.max(0, lengthProperty - j));
        return newArray;
    }

    private static Scriptable js_sort(Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        Comparator comparator;
        Object obj;
        Scriptable object = ScriptRuntime.toObject(context, scriptable, (Object) scriptable2);
        if (objArr.length <= 0 || Undefined.instance == (obj = objArr[0])) {
            comparator = DEFAULT_COMPARATOR;
        } else {
            final Callable valueFunctionAndThis = ScriptRuntime.getValueFunctionAndThis(obj, context);
            final Scriptable lastStoredScriptable = ScriptRuntime.lastStoredScriptable(context);
            final Object[] objArr2 = new Object[2];
            final Context context2 = context;
            final Scriptable scriptable3 = scriptable;
            comparator = new ElementComparator(new Comparator<Object>() {
                public int compare(Object obj, Object obj2) {
                    Object[] objArr = objArr2;
                    objArr[0] = obj;
                    objArr[1] = obj2;
                    int compare = Double.compare(ScriptRuntime.toNumber(valueFunctionAndThis.call(context2, scriptable3, lastStoredScriptable, objArr)), 0.0d);
                    if (compare < 0) {
                        return -1;
                    }
                    if (compare > 0) {
                        return 1;
                    }
                    return 0;
                }
            });
        }
        long lengthProperty = getLengthProperty(context, object, false);
        int i = (int) lengthProperty;
        if (lengthProperty == ((long) i)) {
            Object[] objArr3 = new Object[i];
            for (int i2 = 0; i2 != i; i2++) {
                objArr3[i2] = getRawElem(object, (long) i2);
            }
            Sorting.get().hybridSort(objArr3, comparator);
            for (int i3 = 0; i3 < i; i3++) {
                setRawElem(context, object, (long) i3, objArr3[i3]);
            }
            return object;
        }
        throw Context.reportRuntimeError1("msg.arraylength.too.big", String.valueOf(lengthProperty));
    }

    /* JADX WARNING: Removed duplicated region for block: B:43:0x00cb  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x0110  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x012a  */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x0155 A[LOOP:4: B:72:0x0153->B:73:0x0155, LOOP_END] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.Object js_splice(org.mozilla.javascript.Context r26, org.mozilla.javascript.Scriptable r27, org.mozilla.javascript.Scriptable r28, java.lang.Object[] r29) {
        /*
            r0 = r26
            r1 = r29
            org.mozilla.javascript.Scriptable r2 = org.mozilla.javascript.ScriptRuntime.toObject((org.mozilla.javascript.Context) r26, (org.mozilla.javascript.Scriptable) r27, (java.lang.Object) r28)
            boolean r3 = r2 instanceof org.mozilla.javascript.NativeArray
            r4 = 0
            if (r3 == 0) goto L_0x0013
            r3 = r2
            org.mozilla.javascript.NativeArray r3 = (org.mozilla.javascript.NativeArray) r3
            boolean r5 = r3.denseOnly
            goto L_0x0015
        L_0x0013:
            r3 = 0
            r5 = 0
        L_0x0015:
            org.mozilla.javascript.Scriptable r6 = org.mozilla.javascript.ScriptableObject.getTopLevelScope(r27)
            int r7 = r1.length
            if (r7 != 0) goto L_0x0021
            org.mozilla.javascript.Scriptable r0 = r0.newArray((org.mozilla.javascript.Scriptable) r6, (int) r4)
            return r0
        L_0x0021:
            long r8 = getLengthProperty(r0, r2, r4)
            r10 = r1[r4]
            double r10 = org.mozilla.javascript.ScriptRuntime.toInteger((java.lang.Object) r10)
            long r10 = toSliceIndex(r10, r8)
            int r7 = r7 + -1
            int r12 = r1.length
            r13 = 1
            if (r12 != r13) goto L_0x003a
            long r12 = r8 - r10
            r16 = r5
            goto L_0x0059
        L_0x003a:
            r12 = r1[r13]
            double r12 = org.mozilla.javascript.ScriptRuntime.toInteger((java.lang.Object) r12)
            r16 = 0
            int r18 = (r12 > r16 ? 1 : (r12 == r16 ? 0 : -1))
            if (r18 >= 0) goto L_0x004b
            r16 = r5
            r12 = 0
            goto L_0x0057
        L_0x004b:
            r16 = r5
            long r4 = r8 - r10
            double r14 = (double) r4
            int r19 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1))
            if (r19 <= 0) goto L_0x0055
            goto L_0x0056
        L_0x0055:
            long r4 = (long) r12
        L_0x0056:
            r12 = r4
        L_0x0057:
            int r7 = r7 + -1
        L_0x0059:
            long r4 = r10 + r12
            r14 = 120(0x78, float:1.68E-43)
            r19 = 1
            r17 = 0
            int r15 = (r12 > r17 ? 1 : (r12 == r17 ? 0 : -1))
            if (r15 == 0) goto L_0x00b3
            int r15 = (r12 > r19 ? 1 : (r12 == r19 ? 0 : -1))
            if (r15 != 0) goto L_0x0078
            int r15 = r26.getLanguageVersion()
            if (r15 != r14) goto L_0x0078
            java.lang.Object r6 = getElem(r0, r2, r10)
            r21 = r3
            r22 = r8
            goto L_0x00bf
        L_0x0078:
            if (r16 == 0) goto L_0x008f
            long r14 = r4 - r10
            int r15 = (int) r14
            java.lang.Object[] r14 = new java.lang.Object[r15]
            java.lang.Object[] r1 = r3.dense
            r21 = r3
            int r3 = (int) r10
            r22 = r8
            r8 = 0
            java.lang.System.arraycopy(r1, r3, r14, r8, r15)
            org.mozilla.javascript.Scriptable r6 = r0.newArray((org.mozilla.javascript.Scriptable) r6, (java.lang.Object[]) r14)
            goto L_0x00bf
        L_0x008f:
            r21 = r3
            r22 = r8
            r8 = 0
            org.mozilla.javascript.Scriptable r6 = r0.newArray((org.mozilla.javascript.Scriptable) r6, (int) r8)
            r8 = r10
        L_0x0099:
            int r1 = (r8 > r4 ? 1 : (r8 == r4 ? 0 : -1))
            if (r1 == 0) goto L_0x00ad
            java.lang.Object r1 = getRawElem(r2, r8)
            java.lang.Object r3 = org.mozilla.javascript.Scriptable.NOT_FOUND
            if (r1 == r3) goto L_0x00aa
            long r14 = r8 - r10
            setElem(r0, r6, r14, r1)
        L_0x00aa:
            long r8 = r8 + r19
            goto L_0x0099
        L_0x00ad:
            long r8 = r4 - r10
            setLengthProperty(r0, r6, r8)
            goto L_0x00bf
        L_0x00b3:
            r21 = r3
            r22 = r8
            int r1 = r26.getLanguageVersion()
            if (r1 != r14) goto L_0x00c1
            java.lang.Object r6 = org.mozilla.javascript.Undefined.instance
        L_0x00bf:
            r1 = 0
            goto L_0x00c6
        L_0x00c1:
            r1 = 0
            org.mozilla.javascript.Scriptable r6 = r0.newArray((org.mozilla.javascript.Scriptable) r6, (int) r1)
        L_0x00c6:
            long r8 = (long) r7
            long r12 = r8 - r12
            if (r16 == 0) goto L_0x0106
            long r14 = r22 + r12
            r24 = 2147483647(0x7fffffff, double:1.060997895E-314)
            int r3 = (r14 > r24 ? 1 : (r14 == r24 ? 0 : -1))
            if (r3 >= 0) goto L_0x0106
            int r3 = (int) r14
            r1 = r21
            boolean r16 = r1.ensureCapacity(r3)
            if (r16 == 0) goto L_0x0106
            java.lang.Object[] r0 = r1.dense
            int r2 = (int) r4
            long r8 = r8 + r10
            int r9 = (int) r8
            long r4 = r22 - r4
            int r5 = (int) r4
            java.lang.System.arraycopy(r0, r2, r0, r9, r5)
            if (r7 <= 0) goto L_0x00f3
            java.lang.Object[] r0 = r1.dense
            int r2 = (int) r10
            r4 = 2
            r8 = r29
            java.lang.System.arraycopy(r8, r4, r0, r2, r7)
        L_0x00f3:
            r4 = 0
            int r0 = (r12 > r4 ? 1 : (r12 == r4 ? 0 : -1))
            if (r0 >= 0) goto L_0x0103
            java.lang.Object[] r0 = r1.dense
            r4 = r22
            int r2 = (int) r4
            java.lang.Object r4 = org.mozilla.javascript.Scriptable.NOT_FOUND
            java.util.Arrays.fill(r0, r3, r2, r4)
        L_0x0103:
            r1.length = r14
            return r6
        L_0x0106:
            r8 = r29
            r14 = r22
            r16 = 0
            int r1 = (r12 > r16 ? 1 : (r12 == r16 ? 0 : -1))
            if (r1 <= 0) goto L_0x012a
            long r16 = r14 - r19
            r21 = r10
            r9 = r16
        L_0x0116:
            int r1 = (r9 > r4 ? 1 : (r9 == r4 ? 0 : -1))
            if (r1 < 0) goto L_0x0150
            java.lang.Object r1 = getRawElem(r2, r9)
            r16 = r4
            long r3 = r9 + r12
            setRawElem(r0, r2, r3, r1)
            long r9 = r9 - r19
            r4 = r16
            goto L_0x0116
        L_0x012a:
            r16 = r4
            r21 = r10
            if (r1 >= 0) goto L_0x0150
            r4 = r16
        L_0x0132:
            int r1 = (r4 > r14 ? 1 : (r4 == r14 ? 0 : -1))
            if (r1 >= 0) goto L_0x0142
            java.lang.Object r1 = getRawElem(r2, r4)
            long r9 = r4 + r12
            setRawElem(r0, r2, r9, r1)
            long r4 = r4 + r19
            goto L_0x0132
        L_0x0142:
            long r3 = r14 - r19
        L_0x0144:
            long r9 = r14 + r12
            int r1 = (r3 > r9 ? 1 : (r3 == r9 ? 0 : -1))
            if (r1 < 0) goto L_0x0150
            deleteElem(r2, r3)
            long r3 = r3 - r19
            goto L_0x0144
        L_0x0150:
            int r1 = r8.length
            int r1 = r1 - r7
            r4 = 0
        L_0x0153:
            if (r4 >= r7) goto L_0x0162
            long r9 = (long) r4
            long r10 = r21 + r9
            int r3 = r4 + r1
            r3 = r8[r3]
            setElem(r0, r2, r10, r3)
            int r4 = r4 + 1
            goto L_0x0153
        L_0x0162:
            long r8 = r14 + r12
            setLengthProperty(r0, r2, r8)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NativeArray.js_splice(org.mozilla.javascript.Context, org.mozilla.javascript.Scriptable, org.mozilla.javascript.Scriptable, java.lang.Object[]):java.lang.Object");
    }

    private static Object js_unshift(Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        Scriptable object = ScriptRuntime.toObject(context, scriptable, (Object) scriptable2);
        int i = 0;
        if (object instanceof NativeArray) {
            NativeArray nativeArray = (NativeArray) object;
            if (nativeArray.denseOnly && nativeArray.ensureCapacity(((int) nativeArray.length) + objArr.length)) {
                Object[] objArr2 = nativeArray.dense;
                System.arraycopy(objArr2, 0, objArr2, objArr.length, (int) nativeArray.length);
                while (i < objArr.length) {
                    nativeArray.dense[i] = objArr[i];
                    i++;
                }
                long length2 = nativeArray.length + ((long) objArr.length);
                nativeArray.length = length2;
                return ScriptRuntime.wrapNumber((double) length2);
            }
        }
        long lengthProperty = getLengthProperty(context, object, false);
        int length3 = objArr.length;
        if (objArr.length > 0) {
            if (lengthProperty > 0) {
                for (long j = lengthProperty - 1; j >= 0; j--) {
                    setRawElem(context, object, ((long) length3) + j, getRawElem(object, j));
                }
            }
            while (i < objArr.length) {
                setElem(context, object, (long) i, objArr[i]);
                i++;
            }
        }
        return setLengthProperty(context, object, lengthProperty + ((long) objArr.length));
    }

    private static Object reduceMethod(Context context, int i, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        Object obj;
        boolean z;
        Object obj2;
        long j;
        Context context2 = context;
        Object[] objArr2 = objArr;
        Scriptable object = ScriptRuntime.toObject(context2, scriptable, (Object) scriptable2);
        long lengthProperty = getLengthProperty(context2, object, false);
        if (objArr2.length > 0) {
            obj = objArr2[0];
        } else {
            obj = Undefined.instance;
        }
        if (obj == null || !(obj instanceof Function)) {
            throw ScriptRuntime.notFunctionError(obj);
        }
        Function function = (Function) obj;
        Scriptable topLevelScope = ScriptableObject.getTopLevelScope(function);
        if (i == 24) {
            z = true;
        } else {
            z = false;
        }
        if (objArr2.length > 1) {
            obj2 = objArr2[1];
        } else {
            obj2 = Scriptable.NOT_FOUND;
        }
        for (long j2 = 0; j2 < lengthProperty; j2++) {
            if (z) {
                j = j2;
            } else {
                j = (lengthProperty - 1) - j2;
            }
            Object rawElem = getRawElem(object, j);
            Object obj3 = Scriptable.NOT_FOUND;
            if (rawElem != obj3) {
                if (obj2 == obj3) {
                    obj2 = rawElem;
                } else {
                    obj2 = function.call(context2, topLevelScope, topLevelScope, new Object[]{obj2, rawElem, Long.valueOf(j), object});
                }
            }
        }
        if (obj2 != Scriptable.NOT_FOUND) {
            return obj2;
        }
        throw ScriptRuntime.typeError0("msg.empty.array.reduce");
    }

    private static void setElem(Context context, Scriptable scriptable, long j, Object obj) {
        if (j > 2147483647L) {
            ScriptableObject.putProperty(scriptable, Long.toString(j), obj);
        } else {
            ScriptableObject.putProperty(scriptable, (int) j, obj);
        }
    }

    private void setLength(Object obj) {
        if ((this.lengthAttr & 1) == 0) {
            double number = ScriptRuntime.toNumber(obj);
            long uint32 = ScriptRuntime.toUint32(number);
            double d = (double) uint32;
            if (d == number) {
                if (this.denseOnly) {
                    long j = this.length;
                    if (uint32 < j) {
                        Object[] objArr = this.dense;
                        Arrays.fill(objArr, (int) uint32, objArr.length, Scriptable.NOT_FOUND);
                        this.length = uint32;
                        return;
                    } else if (uint32 >= 1431655764 || d >= ((double) j) * GROW_FACTOR || !ensureCapacity((int) uint32)) {
                        this.denseOnly = false;
                    } else {
                        this.length = uint32;
                        return;
                    }
                }
                long j2 = this.length;
                if (uint32 < j2) {
                    if (j2 - uint32 > 4096) {
                        Object[] ids = getIds();
                        for (Object obj2 : ids) {
                            if (obj2 instanceof String) {
                                String str = (String) obj2;
                                if (toArrayIndex(str) >= uint32) {
                                    delete(str);
                                }
                            } else {
                                int intValue = ((Integer) obj2).intValue();
                                if (((long) intValue) >= uint32) {
                                    delete(intValue);
                                }
                            }
                        }
                    } else {
                        for (long j3 = uint32; j3 < this.length; j3++) {
                            deleteElem(this, j3);
                        }
                    }
                }
                this.length = uint32;
                return;
            }
            throw ScriptRuntime.rangeError(ScriptRuntime.getMessage0("msg.arraylength.bad"));
        }
    }

    private static Object setLengthProperty(Context context, Scriptable scriptable, long j) {
        Number wrapNumber = ScriptRuntime.wrapNumber((double) j);
        ScriptableObject.putProperty(scriptable, "length", (Object) wrapNumber);
        return wrapNumber;
    }

    public static void setMaximumInitialCapacity(int i) {
        maximumInitialCapacity = i;
    }

    private static void setRawElem(Context context, Scriptable scriptable, long j, Object obj) {
        if (obj == Scriptable.NOT_FOUND) {
            deleteElem(scriptable, j);
        } else {
            setElem(context, scriptable, j, obj);
        }
    }

    private static long toArrayIndex(Object obj) {
        if (obj instanceof String) {
            return toArrayIndex((String) obj);
        }
        if (obj instanceof Number) {
            return toArrayIndex(((Number) obj).doubleValue());
        }
        return -1;
    }

    private static int toDenseIndex(Object obj) {
        long arrayIndex = toArrayIndex(obj);
        if (0 > arrayIndex || arrayIndex >= 2147483647L) {
            return -1;
        }
        return (int) arrayIndex;
    }

    private static long toSliceIndex(double d, long j) {
        if (d < 0.0d) {
            d += (double) j;
            if (d < 0.0d) {
                return 0;
            }
        } else if (d > ((double) j)) {
            return j;
        }
        return (long) d;
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0054 A[Catch:{ all -> 0x00ad }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String toStringHelper(org.mozilla.javascript.Context r18, org.mozilla.javascript.Scriptable r19, org.mozilla.javascript.Scriptable r20, boolean r21, boolean r22) {
        /*
            r1 = r18
            r0 = r19
            org.mozilla.javascript.Scriptable r2 = org.mozilla.javascript.ScriptRuntime.toObject((org.mozilla.javascript.Context) r18, (org.mozilla.javascript.Scriptable) r19, (java.lang.Object) r20)
            r3 = 0
            long r4 = getLengthProperty(r1, r2, r3)
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r7 = 256(0x100, float:3.59E-43)
            r6.<init>(r7)
            if (r21 == 0) goto L_0x001e
            r7 = 91
            r6.append(r7)
            java.lang.String r7 = ", "
            goto L_0x0020
        L_0x001e:
            java.lang.String r7 = ","
        L_0x0020:
            org.mozilla.javascript.ObjToIntMap r8 = r1.iterating
            if (r8 != 0) goto L_0x0030
            org.mozilla.javascript.ObjToIntMap r8 = new org.mozilla.javascript.ObjToIntMap
            r10 = 31
            r8.<init>(r10)
            r1.iterating = r8
            r8 = 0
            r10 = 1
            goto L_0x0035
        L_0x0030:
            boolean r8 = r8.has(r2)
            r10 = 0
        L_0x0035:
            r11 = 0
            r12 = 0
            if (r8 != 0) goto L_0x00b3
            org.mozilla.javascript.ObjToIntMap r8 = r1.iterating     // Catch:{ all -> 0x00ad }
            r8.put(r2, r3)     // Catch:{ all -> 0x00ad }
            if (r21 == 0) goto L_0x004c
            int r8 = r18.getLanguageVersion()     // Catch:{ all -> 0x00ad }
            r14 = 150(0x96, float:2.1E-43)
            if (r8 >= r14) goto L_0x004a
            goto L_0x004c
        L_0x004a:
            r8 = 0
            goto L_0x004d
        L_0x004c:
            r8 = 1
        L_0x004d:
            r14 = r12
            r16 = 0
        L_0x0050:
            int r17 = (r14 > r4 ? 1 : (r14 == r4 ? 0 : -1))
            if (r17 >= 0) goto L_0x00a5
            int r16 = (r14 > r12 ? 1 : (r14 == r12 ? 0 : -1))
            if (r16 <= 0) goto L_0x005b
            r6.append(r7)     // Catch:{ all -> 0x00ad }
        L_0x005b:
            java.lang.Object r3 = getRawElem(r2, r14)     // Catch:{ all -> 0x00ad }
            java.lang.Object r9 = org.mozilla.javascript.Scriptable.NOT_FOUND     // Catch:{ all -> 0x00ad }
            if (r3 == r9) goto L_0x009c
            if (r8 == 0) goto L_0x006c
            if (r3 == 0) goto L_0x009c
            java.lang.Object r9 = org.mozilla.javascript.Undefined.instance     // Catch:{ all -> 0x00ad }
            if (r3 != r9) goto L_0x006c
            goto L_0x009c
        L_0x006c:
            if (r21 == 0) goto L_0x0076
            java.lang.String r3 = org.mozilla.javascript.ScriptRuntime.uneval(r1, r0, r3)     // Catch:{ all -> 0x00ad }
            r6.append(r3)     // Catch:{ all -> 0x00ad }
            goto L_0x0099
        L_0x0076:
            boolean r9 = r3 instanceof java.lang.String     // Catch:{ all -> 0x00ad }
            if (r9 == 0) goto L_0x0080
            java.lang.String r3 = (java.lang.String) r3     // Catch:{ all -> 0x00ad }
            r6.append(r3)     // Catch:{ all -> 0x00ad }
            goto L_0x0099
        L_0x0080:
            if (r22 == 0) goto L_0x0092
            java.lang.String r9 = "toLocaleString"
            org.mozilla.javascript.Callable r3 = org.mozilla.javascript.ScriptRuntime.getPropFunctionAndThis(r3, r9, r1, r0)     // Catch:{ all -> 0x00ad }
            org.mozilla.javascript.Scriptable r9 = org.mozilla.javascript.ScriptRuntime.lastStoredScriptable(r18)     // Catch:{ all -> 0x00ad }
            java.lang.Object[] r12 = org.mozilla.javascript.ScriptRuntime.emptyArgs     // Catch:{ all -> 0x00ad }
            java.lang.Object r3 = r3.call(r1, r0, r9, r12)     // Catch:{ all -> 0x00ad }
        L_0x0092:
            java.lang.String r3 = org.mozilla.javascript.ScriptRuntime.toString((java.lang.Object) r3)     // Catch:{ all -> 0x00ad }
            r6.append(r3)     // Catch:{ all -> 0x00ad }
        L_0x0099:
            r16 = 1
            goto L_0x009e
        L_0x009c:
            r16 = 0
        L_0x009e:
            r12 = 1
            long r14 = r14 + r12
            r3 = 0
            r12 = 0
            goto L_0x0050
        L_0x00a5:
            org.mozilla.javascript.ObjToIntMap r0 = r1.iterating     // Catch:{ all -> 0x00ad }
            r0.remove(r2)     // Catch:{ all -> 0x00ad }
            r3 = r16
            goto L_0x00b6
        L_0x00ad:
            r0 = move-exception
            if (r10 == 0) goto L_0x00b2
            r1.iterating = r11
        L_0x00b2:
            throw r0
        L_0x00b3:
            r3 = 0
            r14 = 0
        L_0x00b6:
            if (r10 == 0) goto L_0x00ba
            r1.iterating = r11
        L_0x00ba:
            if (r21 == 0) goto L_0x00cf
            if (r3 != 0) goto L_0x00ca
            r0 = 0
            int r2 = (r14 > r0 ? 1 : (r14 == r0 ? 0 : -1))
            if (r2 <= 0) goto L_0x00ca
            java.lang.String r0 = ", ]"
            r6.append(r0)
            goto L_0x00cf
        L_0x00ca:
            r0 = 93
            r6.append(r0)
        L_0x00cf:
            java.lang.String r0 = r6.toString()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NativeArray.toStringHelper(org.mozilla.javascript.Context, org.mozilla.javascript.Scriptable, org.mozilla.javascript.Scriptable, boolean, boolean):java.lang.String");
    }

    public boolean add(Object obj) {
        throw new UnsupportedOperationException();
    }

    public boolean addAll(Collection collection) {
        throw new UnsupportedOperationException();
    }

    public void clear() {
        throw new UnsupportedOperationException();
    }

    public boolean contains(Object obj) {
        return indexOf(obj) > -1;
    }

    public boolean containsAll(Collection collection) {
        for (Object contains : collection) {
            if (!contains(contains)) {
                return false;
            }
        }
        return true;
    }

    public void defineOwnProperty(Context context, Object obj, ScriptableObject scriptableObject, boolean z) {
        Object[] objArr = this.dense;
        if (objArr != null) {
            this.dense = null;
            this.denseOnly = false;
            for (int i = 0; i < objArr.length; i++) {
                Object obj2 = objArr[i];
                if (obj2 != Scriptable.NOT_FOUND) {
                    put(i, (Scriptable) this, obj2);
                }
            }
        }
        long arrayIndex = toArrayIndex(obj);
        if (arrayIndex >= this.length) {
            this.length = arrayIndex + 1;
        }
        super.defineOwnProperty(context, obj, scriptableObject, z);
    }

    public void delete(int i) {
        Object[] objArr = this.dense;
        if (objArr == null || i < 0 || i >= objArr.length || isSealed() || (!this.denseOnly && isGetterOrSetter((String) null, i, true))) {
            super.delete(i);
        } else {
            this.dense[i] = Scriptable.NOT_FOUND;
        }
    }

    public Object execIdCall(IdFunctionObject idFunctionObject, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        if (!idFunctionObject.hasTag(ARRAY_TAG)) {
            return super.execIdCall(idFunctionObject, context, scriptable, scriptable2, objArr);
        }
        int methodId = idFunctionObject.methodId();
        while (true) {
            boolean z = true;
            int i = 0;
            switch (methodId) {
                case ConstructorId_from /*-28*/:
                    return js_from(context, scriptable, scriptable2, objArr);
                case ConstructorId_of /*-27*/:
                    return js_of(context, scriptable, scriptable2, objArr);
                case ConstructorId_isArray /*-26*/:
                    if (objArr.length <= 0 || !js_isArray(objArr[0])) {
                        z = false;
                    }
                    return Boolean.valueOf(z);
                case ConstructorId_reduceRight /*-25*/:
                case ConstructorId_reduce /*-24*/:
                case ConstructorId_findIndex /*-23*/:
                case ConstructorId_find /*-22*/:
                case ConstructorId_some /*-21*/:
                case ConstructorId_map /*-20*/:
                case ConstructorId_forEach /*-19*/:
                case ConstructorId_filter /*-18*/:
                case ConstructorId_every /*-17*/:
                case ConstructorId_lastIndexOf /*-16*/:
                case ConstructorId_indexOf /*-15*/:
                case ConstructorId_slice /*-14*/:
                case ConstructorId_concat /*-13*/:
                case ConstructorId_splice /*-12*/:
                case ConstructorId_unshift /*-11*/:
                case ConstructorId_shift /*-10*/:
                case ConstructorId_pop /*-9*/:
                case ConstructorId_push /*-8*/:
                case ConstructorId_sort /*-7*/:
                case ConstructorId_reverse /*-6*/:
                case ConstructorId_join /*-5*/:
                    if (objArr.length > 0) {
                        scriptable2 = ScriptRuntime.toObject(context, scriptable, objArr[0]);
                        int length2 = objArr.length - 1;
                        Object[] objArr2 = new Object[length2];
                        while (i < length2) {
                            int i2 = i + 1;
                            objArr2[i] = objArr[i2];
                            i = i2;
                        }
                        objArr = objArr2;
                    }
                    methodId = -methodId;
                default:
                    switch (methodId) {
                        case 1:
                            if (scriptable2 != null) {
                                z = false;
                            }
                            if (!z) {
                                return idFunctionObject.construct(context, scriptable, objArr);
                            }
                            return jsConstructor(context, scriptable, objArr);
                        case 2:
                            return toStringHelper(context, scriptable, scriptable2, context.hasFeature(4), false);
                        case 3:
                            return toStringHelper(context, scriptable, scriptable2, false, true);
                        case 4:
                            return toStringHelper(context, scriptable, scriptable2, true, false);
                        case 5:
                            return js_join(context, scriptable, scriptable2, objArr);
                        case 6:
                            return js_reverse(context, scriptable, scriptable2, objArr);
                        case 7:
                            return js_sort(context, scriptable, scriptable2, objArr);
                        case 8:
                            return js_push(context, scriptable, scriptable2, objArr);
                        case 9:
                            return js_pop(context, scriptable, scriptable2, objArr);
                        case 10:
                            return js_shift(context, scriptable, scriptable2, objArr);
                        case 11:
                            return js_unshift(context, scriptable, scriptable2, objArr);
                        case 12:
                            return js_splice(context, scriptable, scriptable2, objArr);
                        case 13:
                            return js_concat(context, scriptable, scriptable2, objArr);
                        case 14:
                            return js_slice(context, scriptable, scriptable2, objArr);
                        case 15:
                            return js_indexOf(context, scriptable, scriptable2, objArr);
                        case 16:
                            return js_lastIndexOf(context, scriptable, scriptable2, objArr);
                        case 17:
                        case 18:
                        case 19:
                        case 20:
                        case 21:
                        case 22:
                        case 23:
                            return iterativeMethod(context, idFunctionObject, scriptable, scriptable2, objArr);
                        case 24:
                        case 25:
                            return reduceMethod(context, methodId, scriptable, scriptable2, objArr);
                        case 26:
                            return js_fill(context, scriptable, scriptable2, objArr);
                        case 27:
                            return new NativeArrayIterator(scriptable, ScriptRuntime.toObject(context, scriptable, (Object) scriptable2), NativeArrayIterator.ARRAY_ITERATOR_TYPE.KEYS);
                        case 28:
                        case 32:
                            return new NativeArrayIterator(scriptable, ScriptRuntime.toObject(context, scriptable, (Object) scriptable2), NativeArrayIterator.ARRAY_ITERATOR_TYPE.VALUES);
                        case 29:
                            return new NativeArrayIterator(scriptable, ScriptRuntime.toObject(context, scriptable, (Object) scriptable2), NativeArrayIterator.ARRAY_ITERATOR_TYPE.ENTRIES);
                        case 30:
                            return js_includes(context, scriptable, scriptable2, objArr);
                        case 31:
                            return js_copyWithin(context, scriptable, scriptable2, objArr);
                        default:
                            throw new IllegalArgumentException("Array.prototype has no method: " + idFunctionObject.getFunctionName());
                    }
            }
        }
    }

    public void fillConstructorProperties(IdFunctionObject idFunctionObject) {
        IdFunctionObject idFunctionObject2 = idFunctionObject;
        Object obj = ARRAY_TAG;
        addIdFunctionProperty(idFunctionObject2, obj, ConstructorId_join, "join", 1);
        addIdFunctionProperty(idFunctionObject2, obj, ConstructorId_reverse, "reverse", 0);
        addIdFunctionProperty(idFunctionObject2, obj, ConstructorId_sort, "sort", 1);
        addIdFunctionProperty(idFunctionObject2, obj, ConstructorId_push, "push", 1);
        addIdFunctionProperty(idFunctionObject2, obj, ConstructorId_pop, "pop", 0);
        addIdFunctionProperty(idFunctionObject2, obj, ConstructorId_shift, "shift", 0);
        addIdFunctionProperty(idFunctionObject2, obj, ConstructorId_unshift, "unshift", 1);
        addIdFunctionProperty(idFunctionObject2, obj, ConstructorId_splice, "splice", 2);
        addIdFunctionProperty(idFunctionObject2, obj, ConstructorId_concat, "concat", 1);
        addIdFunctionProperty(idFunctionObject2, obj, ConstructorId_slice, "slice", 2);
        addIdFunctionProperty(idFunctionObject2, obj, ConstructorId_indexOf, "indexOf", 1);
        addIdFunctionProperty(idFunctionObject2, obj, ConstructorId_lastIndexOf, "lastIndexOf", 1);
        addIdFunctionProperty(idFunctionObject2, obj, ConstructorId_every, "every", 1);
        addIdFunctionProperty(idFunctionObject2, obj, ConstructorId_filter, "filter", 1);
        addIdFunctionProperty(idFunctionObject2, obj, ConstructorId_forEach, "forEach", 1);
        addIdFunctionProperty(idFunctionObject2, obj, ConstructorId_map, "map", 1);
        addIdFunctionProperty(idFunctionObject2, obj, ConstructorId_some, "some", 1);
        addIdFunctionProperty(idFunctionObject2, obj, ConstructorId_find, "find", 1);
        addIdFunctionProperty(idFunctionObject2, obj, ConstructorId_findIndex, "findIndex", 1);
        addIdFunctionProperty(idFunctionObject2, obj, ConstructorId_reduce, "reduce", 1);
        addIdFunctionProperty(idFunctionObject2, obj, ConstructorId_reduceRight, "reduceRight", 1);
        addIdFunctionProperty(idFunctionObject2, obj, ConstructorId_isArray, "isArray", 1);
        addIdFunctionProperty(idFunctionObject2, obj, ConstructorId_of, "of", 0);
        addIdFunctionProperty(idFunctionObject2, obj, ConstructorId_from, "from", 1);
        super.fillConstructorProperties(idFunctionObject);
    }

    public int findInstanceIdInfo(String str) {
        if (str.equals("length")) {
            return IdScriptableObject.instanceIdInfo(this.lengthAttr, 1);
        }
        return super.findInstanceIdInfo(str);
    }

    public int findPrototypeId(Symbol symbol) {
        return SymbolKey.ITERATOR.equals(symbol) ? 32 : 0;
    }

    public Object get(int i, Scriptable scriptable) {
        if (!this.denseOnly && isGetterOrSetter((String) null, i, false)) {
            return super.get(i, scriptable);
        }
        Object[] objArr = this.dense;
        if (objArr == null || i < 0 || i >= objArr.length) {
            return super.get(i, scriptable);
        }
        return objArr[i];
    }

    public int getAttributes(int i) {
        Object[] objArr = this.dense;
        if (objArr == null || i < 0 || i >= objArr.length || objArr[i] == Scriptable.NOT_FOUND) {
            return super.getAttributes(i);
        }
        return 0;
    }

    public String getClassName() {
        return "Array";
    }

    public Object getDefaultValue(Class<?> cls) {
        if (cls == ScriptRuntime.NumberClass && Context.getContext().getLanguageVersion() == 120) {
            return Long.valueOf(this.length);
        }
        return super.getDefaultValue(cls);
    }

    public Object[] getIds(boolean z, boolean z2) {
        Object[] ids = super.getIds(z, z2);
        Object[] objArr = this.dense;
        if (objArr == null) {
            return ids;
        }
        int length2 = objArr.length;
        long j = this.length;
        if (((long) length2) > j) {
            length2 = (int) j;
        }
        if (length2 == 0) {
            return ids;
        }
        int length3 = ids.length;
        Object[] objArr2 = new Object[(length2 + length3)];
        int i = 0;
        for (int i2 = 0; i2 != length2; i2++) {
            if (this.dense[i2] != Scriptable.NOT_FOUND) {
                objArr2[i] = Integer.valueOf(i2);
                i++;
            }
        }
        if (i != length2) {
            Object[] objArr3 = new Object[(i + length3)];
            System.arraycopy(objArr2, 0, objArr3, 0, i);
            objArr2 = objArr3;
        }
        System.arraycopy(ids, 0, objArr2, i, length3);
        return objArr2;
    }

    public List<Integer> getIndexIds() {
        Object[] ids = getIds();
        ArrayList arrayList = new ArrayList(ids.length);
        for (Object obj : ids) {
            int int32 = ScriptRuntime.toInt32(obj);
            if (int32 >= 0 && ScriptRuntime.toString((double) int32).equals(ScriptRuntime.toString(obj))) {
                arrayList.add(Integer.valueOf(int32));
            }
        }
        return arrayList;
    }

    public String getInstanceIdName(int i) {
        if (i == 1) {
            return "length";
        }
        return super.getInstanceIdName(i);
    }

    public Object getInstanceIdValue(int i) {
        if (i == 1) {
            return ScriptRuntime.wrapNumber((double) this.length);
        }
        return super.getInstanceIdValue(i);
    }

    public long getLength() {
        return this.length;
    }

    public int getMaxInstanceId() {
        return 1;
    }

    public ScriptableObject getOwnPropertyDescriptor(Context context, Object obj) {
        int denseIndex;
        Object obj2;
        if (this.dense != null && (denseIndex = toDenseIndex(obj)) >= 0) {
            Object[] objArr = this.dense;
            if (denseIndex < objArr.length && (obj2 = objArr[denseIndex]) != Scriptable.NOT_FOUND) {
                return defaultIndexPropertyDescriptor(obj2);
            }
        }
        return super.getOwnPropertyDescriptor(context, obj);
    }

    public boolean has(int i, Scriptable scriptable) {
        if (!this.denseOnly && isGetterOrSetter((String) null, i, false)) {
            return super.has(i, scriptable);
        }
        Object[] objArr = this.dense;
        if (objArr == null || i < 0 || i >= objArr.length) {
            return super.has(i, scriptable);
        }
        if (objArr[i] != Scriptable.NOT_FOUND) {
            return true;
        }
        return false;
    }

    public int indexOf(Object obj) {
        long j = this.length;
        if (j <= 2147483647L) {
            int i = (int) j;
            int i2 = 0;
            if (obj == null) {
                while (i2 < i) {
                    if (get(i2) == null) {
                        return i2;
                    }
                    i2++;
                }
                return -1;
            }
            while (i2 < i) {
                if (obj.equals(get(i2))) {
                    return i2;
                }
                i2++;
            }
            return -1;
        }
        throw new IllegalStateException();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0063, code lost:
        r10 = r1;
        r12 = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0083, code lost:
        r10 = r0;
        r12 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0088, code lost:
        r10 = r0;
        r12 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x008a, code lost:
        initPrototypeMethod(ARRAY_TAG, r14, r10, (java.lang.String) null, r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0091, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void initPrototypeId(int r14) {
        /*
            r13 = this;
            r0 = 32
            if (r14 != r0) goto L_0x0011
            java.lang.Object r2 = ARRAY_TAG
            org.mozilla.javascript.SymbolKey r4 = org.mozilla.javascript.SymbolKey.ITERATOR
            java.lang.String r5 = "[Symbol.iterator]"
            r6 = 0
            r1 = r13
            r3 = r14
            r1.initPrototypeMethod((java.lang.Object) r2, (int) r3, (org.mozilla.javascript.Symbol) r4, (java.lang.String) r5, (int) r6)
            return
        L_0x0011:
            r11 = 0
            r0 = 2
            r1 = 0
            r2 = 1
            switch(r14) {
                case 1: goto L_0x0086;
                case 2: goto L_0x0081;
                case 3: goto L_0x007e;
                case 4: goto L_0x007b;
                case 5: goto L_0x0078;
                case 6: goto L_0x0075;
                case 7: goto L_0x0072;
                case 8: goto L_0x006f;
                case 9: goto L_0x006c;
                case 10: goto L_0x0069;
                case 11: goto L_0x0066;
                case 12: goto L_0x0061;
                case 13: goto L_0x005e;
                case 14: goto L_0x005b;
                case 15: goto L_0x0058;
                case 16: goto L_0x0055;
                case 17: goto L_0x0052;
                case 18: goto L_0x004f;
                case 19: goto L_0x004c;
                case 20: goto L_0x0049;
                case 21: goto L_0x0046;
                case 22: goto L_0x0043;
                case 23: goto L_0x0040;
                case 24: goto L_0x003d;
                case 25: goto L_0x0039;
                case 26: goto L_0x0035;
                case 27: goto L_0x0031;
                case 28: goto L_0x002d;
                case 29: goto L_0x0029;
                case 30: goto L_0x0025;
                case 31: goto L_0x0022;
                default: goto L_0x0018;
            }
        L_0x0018:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r14 = java.lang.String.valueOf(r14)
            r0.<init>(r14)
            throw r0
        L_0x0022:
            java.lang.String r1 = "copyWithin"
            goto L_0x0063
        L_0x0025:
            java.lang.String r0 = "includes"
            goto L_0x0088
        L_0x0029:
            java.lang.String r0 = "entries"
            goto L_0x0083
        L_0x002d:
            java.lang.String r0 = "values"
            goto L_0x0083
        L_0x0031:
            java.lang.String r0 = "keys"
            goto L_0x0083
        L_0x0035:
            java.lang.String r0 = "fill"
            goto L_0x0088
        L_0x0039:
            java.lang.String r0 = "reduceRight"
            goto L_0x0088
        L_0x003d:
            java.lang.String r0 = "reduce"
            goto L_0x0088
        L_0x0040:
            java.lang.String r0 = "findIndex"
            goto L_0x0088
        L_0x0043:
            java.lang.String r0 = "find"
            goto L_0x0088
        L_0x0046:
            java.lang.String r0 = "some"
            goto L_0x0088
        L_0x0049:
            java.lang.String r0 = "map"
            goto L_0x0088
        L_0x004c:
            java.lang.String r0 = "forEach"
            goto L_0x0088
        L_0x004f:
            java.lang.String r0 = "filter"
            goto L_0x0088
        L_0x0052:
            java.lang.String r0 = "every"
            goto L_0x0088
        L_0x0055:
            java.lang.String r0 = "lastIndexOf"
            goto L_0x0088
        L_0x0058:
            java.lang.String r0 = "indexOf"
            goto L_0x0088
        L_0x005b:
            java.lang.String r1 = "slice"
            goto L_0x0063
        L_0x005e:
            java.lang.String r0 = "concat"
            goto L_0x0088
        L_0x0061:
            java.lang.String r1 = "splice"
        L_0x0063:
            r10 = r1
            r12 = 2
            goto L_0x008a
        L_0x0066:
            java.lang.String r0 = "unshift"
            goto L_0x0088
        L_0x0069:
            java.lang.String r0 = "shift"
            goto L_0x0083
        L_0x006c:
            java.lang.String r0 = "pop"
            goto L_0x0083
        L_0x006f:
            java.lang.String r0 = "push"
            goto L_0x0088
        L_0x0072:
            java.lang.String r0 = "sort"
            goto L_0x0088
        L_0x0075:
            java.lang.String r0 = "reverse"
            goto L_0x0083
        L_0x0078:
            java.lang.String r0 = "join"
            goto L_0x0088
        L_0x007b:
            java.lang.String r0 = "toSource"
            goto L_0x0083
        L_0x007e:
            java.lang.String r0 = "toLocaleString"
            goto L_0x0083
        L_0x0081:
            java.lang.String r0 = "toString"
        L_0x0083:
            r10 = r0
            r12 = 0
            goto L_0x008a
        L_0x0086:
            java.lang.String r0 = "constructor"
        L_0x0088:
            r10 = r0
            r12 = 1
        L_0x008a:
            java.lang.Object r8 = ARRAY_TAG
            r7 = r13
            r9 = r14
            r7.initPrototypeMethod((java.lang.Object) r8, (int) r9, (java.lang.String) r10, (java.lang.String) r11, (int) r12)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NativeArray.initPrototypeId(int):void");
    }

    public boolean isEmpty() {
        return this.length == 0;
    }

    public Iterator iterator() {
        return listIterator(0);
    }

    @Deprecated
    public long jsGet_length() {
        return getLength();
    }

    public int lastIndexOf(Object obj) {
        long j = this.length;
        if (j <= 2147483647L) {
            int i = (int) j;
            if (obj == null) {
                for (int i2 = i - 1; i2 >= 0; i2--) {
                    if (get(i2) == null) {
                        return i2;
                    }
                }
                return -1;
            }
            for (int i3 = i - 1; i3 >= 0; i3--) {
                if (obj.equals(get(i3))) {
                    return i3;
                }
            }
            return -1;
        }
        throw new IllegalStateException();
    }

    public ListIterator listIterator() {
        return listIterator(0);
    }

    public void put(String str, Scriptable scriptable, Object obj) {
        super.put(str, scriptable, obj);
        if (scriptable == this) {
            long arrayIndex = toArrayIndex(str);
            if (arrayIndex >= this.length) {
                this.length = arrayIndex + 1;
                this.denseOnly = false;
            }
        }
    }

    public boolean remove(Object obj) {
        throw new UnsupportedOperationException();
    }

    public boolean removeAll(Collection collection) {
        throw new UnsupportedOperationException();
    }

    public boolean retainAll(Collection collection) {
        throw new UnsupportedOperationException();
    }

    public Object set(int i, Object obj) {
        throw new UnsupportedOperationException();
    }

    public void setDenseOnly(boolean z) {
        if (!z || this.denseOnly) {
            this.denseOnly = z;
            return;
        }
        throw new IllegalArgumentException();
    }

    public void setInstanceIdAttributes(int i, int i2) {
        if (i == 1) {
            this.lengthAttr = i2;
        }
    }

    public void setInstanceIdValue(int i, Object obj) {
        if (i == 1) {
            setLength(obj);
        } else {
            super.setInstanceIdValue(i, obj);
        }
    }

    public int size() {
        long j = this.length;
        if (j <= 2147483647L) {
            return (int) j;
        }
        throw new IllegalStateException();
    }

    public List subList(int i, int i2) {
        throw new UnsupportedOperationException();
    }

    public Object[] toArray() {
        return toArray(ScriptRuntime.emptyArgs);
    }

    public static final class ElementComparator implements Comparator<Object>, Serializable {
        private static final long serialVersionUID = -1189948017688708858L;
        private final Comparator<Object> child;

        public ElementComparator() {
            this.child = NativeArray.STRING_COMPARATOR;
        }

        public int compare(Object obj, Object obj2) {
            Object obj3 = Undefined.instance;
            if (obj != obj3) {
                Object obj4 = Scriptable.NOT_FOUND;
                if (obj == obj4) {
                    if (obj2 == obj4) {
                        return 0;
                    }
                    return 1;
                } else if (obj2 == obj4 || obj2 == obj3) {
                    return -1;
                } else {
                    return this.child.compare(obj, obj2);
                }
            } else if (obj2 == obj3) {
                return 0;
            } else {
                if (obj2 == Scriptable.NOT_FOUND) {
                    return -1;
                }
                return 1;
            }
        }

        public ElementComparator(Comparator<Object> comparator) {
            this.child = comparator;
        }
    }

    public void add(int i, Object obj) {
        throw new UnsupportedOperationException();
    }

    public boolean addAll(int i, Collection collection) {
        throw new UnsupportedOperationException();
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int findPrototypeId(java.lang.String r15) {
        /*
            r14 = this;
            int r0 = r15.length()
            r1 = 3
            r2 = 14
            r3 = 0
            if (r0 == r2) goto L_0x0143
            r4 = 111(0x6f, float:1.56E-43)
            r5 = 118(0x76, float:1.65E-43)
            r6 = 102(0x66, float:1.43E-43)
            r7 = 99
            r8 = 115(0x73, float:1.61E-43)
            r9 = 105(0x69, float:1.47E-43)
            r10 = 108(0x6c, float:1.51E-43)
            r11 = 1
            r12 = 114(0x72, float:1.6E-43)
            r13 = 2
            switch(r0) {
                case 3: goto L_0x0114;
                case 4: goto L_0x00e1;
                case 5: goto L_0x00c3;
                case 6: goto L_0x0095;
                case 7: goto L_0x0064;
                case 8: goto L_0x0048;
                case 9: goto L_0x0042;
                case 10: goto L_0x003c;
                case 11: goto L_0x0021;
                default: goto L_0x001f;
            }
        L_0x001f:
            goto L_0x0140
        L_0x0021:
            char r0 = r15.charAt(r3)
            if (r0 != r7) goto L_0x002c
            java.lang.String r0 = "constructor"
            r1 = 1
            goto L_0x0145
        L_0x002c:
            if (r0 != r10) goto L_0x0034
            java.lang.String r0 = "lastIndexOf"
            r1 = 16
            goto L_0x0145
        L_0x0034:
            if (r0 != r12) goto L_0x0140
            java.lang.String r0 = "reduceRight"
            r1 = 25
            goto L_0x0145
        L_0x003c:
            java.lang.String r0 = "copyWithin"
            r1 = 31
            goto L_0x0145
        L_0x0042:
            java.lang.String r0 = "findIndex"
            r1 = 23
            goto L_0x0145
        L_0x0048:
            char r0 = r15.charAt(r1)
            if (r0 != r10) goto L_0x0054
            java.lang.String r0 = "includes"
            r1 = 30
            goto L_0x0145
        L_0x0054:
            if (r0 != r4) goto L_0x005b
            java.lang.String r0 = "toSource"
            r1 = 4
            goto L_0x0145
        L_0x005b:
            r1 = 116(0x74, float:1.63E-43)
            if (r0 != r1) goto L_0x0140
            java.lang.String r0 = "toString"
            r1 = 2
            goto L_0x0145
        L_0x0064:
            char r0 = r15.charAt(r3)
            r1 = 101(0x65, float:1.42E-43)
            if (r0 == r1) goto L_0x008f
            if (r0 == r6) goto L_0x0089
            if (r0 == r9) goto L_0x0083
            if (r0 == r12) goto L_0x007e
            r1 = 117(0x75, float:1.64E-43)
            if (r0 == r1) goto L_0x0078
            goto L_0x0140
        L_0x0078:
            java.lang.String r0 = "unshift"
            r1 = 11
            goto L_0x0145
        L_0x007e:
            java.lang.String r0 = "reverse"
            r1 = 6
            goto L_0x0145
        L_0x0083:
            java.lang.String r0 = "indexOf"
            r1 = 15
            goto L_0x0145
        L_0x0089:
            java.lang.String r0 = "forEach"
            r1 = 19
            goto L_0x0145
        L_0x008f:
            java.lang.String r0 = "entries"
            r1 = 29
            goto L_0x0145
        L_0x0095:
            char r0 = r15.charAt(r3)
            if (r0 == r7) goto L_0x00bd
            if (r0 == r6) goto L_0x00b7
            if (r0 == r5) goto L_0x00b1
            if (r0 == r12) goto L_0x00ab
            if (r0 == r8) goto L_0x00a5
            goto L_0x0140
        L_0x00a5:
            java.lang.String r0 = "splice"
            r1 = 12
            goto L_0x0145
        L_0x00ab:
            java.lang.String r0 = "reduce"
            r1 = 24
            goto L_0x0145
        L_0x00b1:
            java.lang.String r0 = "values"
            r1 = 28
            goto L_0x0145
        L_0x00b7:
            java.lang.String r0 = "filter"
            r1 = 18
            goto L_0x0145
        L_0x00bd:
            java.lang.String r0 = "concat"
            r1 = 13
            goto L_0x0145
        L_0x00c3:
            char r0 = r15.charAt(r11)
            r1 = 104(0x68, float:1.46E-43)
            if (r0 != r1) goto L_0x00d1
            java.lang.String r0 = "shift"
            r1 = 10
            goto L_0x0145
        L_0x00d1:
            if (r0 != r10) goto L_0x00d9
            java.lang.String r0 = "slice"
            r1 = 14
            goto L_0x0145
        L_0x00d9:
            if (r0 != r5) goto L_0x0140
            java.lang.String r0 = "every"
            r1 = 17
            goto L_0x0145
        L_0x00e1:
            char r0 = r15.charAt(r13)
            if (r0 == r9) goto L_0x0110
            r1 = 121(0x79, float:1.7E-43)
            if (r0 == r1) goto L_0x010b
            if (r0 == r12) goto L_0x0107
            if (r0 == r8) goto L_0x0102
            switch(r0) {
                case 108: goto L_0x00fd;
                case 109: goto L_0x00f8;
                case 110: goto L_0x00f3;
                default: goto L_0x00f2;
            }
        L_0x00f2:
            goto L_0x0140
        L_0x00f3:
            java.lang.String r0 = "find"
            r1 = 22
            goto L_0x0145
        L_0x00f8:
            java.lang.String r0 = "some"
            r1 = 21
            goto L_0x0145
        L_0x00fd:
            java.lang.String r0 = "fill"
            r1 = 26
            goto L_0x0145
        L_0x0102:
            java.lang.String r0 = "push"
            r1 = 8
            goto L_0x0145
        L_0x0107:
            java.lang.String r0 = "sort"
            r1 = 7
            goto L_0x0145
        L_0x010b:
            java.lang.String r0 = "keys"
            r1 = 27
            goto L_0x0145
        L_0x0110:
            java.lang.String r0 = "join"
            r1 = 5
            goto L_0x0145
        L_0x0114:
            char r0 = r15.charAt(r3)
            r1 = 109(0x6d, float:1.53E-43)
            r2 = 112(0x70, float:1.57E-43)
            if (r0 != r1) goto L_0x012f
            char r0 = r15.charAt(r13)
            if (r0 != r2) goto L_0x0140
            char r0 = r15.charAt(r11)
            r1 = 97
            if (r0 != r1) goto L_0x0140
            r3 = 20
            goto L_0x0151
        L_0x012f:
            if (r0 != r2) goto L_0x0140
            char r0 = r15.charAt(r13)
            if (r0 != r2) goto L_0x0140
            char r0 = r15.charAt(r11)
            if (r0 != r4) goto L_0x0140
            r3 = 9
            goto L_0x0151
        L_0x0140:
            r0 = 0
            r1 = 0
            goto L_0x0145
        L_0x0143:
            java.lang.String r0 = "toLocaleString"
        L_0x0145:
            if (r0 == 0) goto L_0x0150
            if (r0 == r15) goto L_0x0150
            boolean r15 = r0.equals(r15)
            if (r15 != 0) goto L_0x0150
            goto L_0x0151
        L_0x0150:
            r3 = r1
        L_0x0151:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NativeArray.findPrototypeId(java.lang.String):int");
    }

    public ListIterator listIterator(int i) {
        long j = this.length;
        if (j <= 2147483647L) {
            int i2 = (int) j;
            if (i >= 0 && i <= i2) {
                return new Object(i, i2) {
                    int cursor;
                    final /* synthetic */ int val$len;
                    final /* synthetic */ int val$start;

                    {
                        this.val$start = r2;
                        this.val$len = r3;
                        this.cursor = r2;
                    }

                    public void add(Object obj) {
                        throw new UnsupportedOperationException();
                    }

                    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
                        Iterator.CC.$default$forEachRemaining(this, consumer);
                    }

                    public boolean hasNext() {
                        return this.cursor < this.val$len;
                    }

                    public boolean hasPrevious() {
                        return this.cursor > 0;
                    }

                    public Object next() {
                        int i = this.cursor;
                        if (i != this.val$len) {
                            NativeArray nativeArray = NativeArray.this;
                            this.cursor = i + 1;
                            return nativeArray.get(i);
                        }
                        throw new NoSuchElementException();
                    }

                    public int nextIndex() {
                        return this.cursor;
                    }

                    public Object previous() {
                        int i = this.cursor;
                        if (i != 0) {
                            NativeArray nativeArray = NativeArray.this;
                            int i2 = i - 1;
                            this.cursor = i2;
                            return nativeArray.get(i2);
                        }
                        throw new NoSuchElementException();
                    }

                    public int previousIndex() {
                        return this.cursor - 1;
                    }

                    public void remove() {
                        throw new UnsupportedOperationException();
                    }

                    public void set(Object obj) {
                        throw new UnsupportedOperationException();
                    }
                };
            }
            throw new IndexOutOfBoundsException(y2.f("Index: ", i));
        }
        throw new IllegalStateException();
    }

    public Object remove(int i) {
        throw new UnsupportedOperationException();
    }

    public Object[] toArray(Object[] objArr) {
        long j = this.length;
        if (j <= 2147483647L) {
            int i = (int) j;
            if (objArr.length < i) {
                objArr = (Object[]) Array.newInstance(objArr.getClass().getComponentType(), i);
            }
            for (int i2 = 0; i2 < i; i2++) {
                objArr[i2] = get(i2);
            }
            return objArr;
        }
        throw new IllegalStateException();
    }

    private static long toArrayIndex(String str) {
        long arrayIndex = toArrayIndex(ScriptRuntime.toNumber(str));
        if (Long.toString(arrayIndex).equals(str)) {
            return arrayIndex;
        }
        return -1;
    }

    public Object get(long j) {
        if (j < 0 || j >= this.length) {
            throw new IndexOutOfBoundsException();
        }
        Object rawElem = getRawElem(this, j);
        if (rawElem == Scriptable.NOT_FOUND || rawElem == Undefined.instance) {
            return null;
        }
        return rawElem instanceof Wrapper ? ((Wrapper) rawElem).unwrap() : rawElem;
    }

    public void put(int i, Scriptable scriptable, Object obj) {
        if (scriptable == this && !isSealed() && this.dense != null && i >= 0 && (this.denseOnly || !isGetterOrSetter((String) null, i, true))) {
            if (isExtensible() || this.length > ((long) i)) {
                Object[] objArr = this.dense;
                if (i < objArr.length) {
                    objArr[i] = obj;
                    long j = (long) i;
                    if (this.length <= j) {
                        this.length = j + 1;
                        return;
                    }
                    return;
                } else if (!this.denseOnly || ((double) i) >= ((double) objArr.length) * GROW_FACTOR || !ensureCapacity(i + 1)) {
                    this.denseOnly = false;
                } else {
                    this.dense[i] = obj;
                    this.length = ((long) i) + 1;
                    return;
                }
            } else {
                return;
            }
        }
        super.put(i, scriptable, obj);
        if (scriptable == this && (this.lengthAttr & 1) == 0) {
            long j2 = (long) i;
            if (this.length <= j2) {
                this.length = j2 + 1;
            }
        }
    }

    public NativeArray(Object[] objArr) {
        this.lengthAttr = 6;
        this.denseOnly = true;
        this.dense = objArr;
        this.length = (long) objArr.length;
    }

    private static long toArrayIndex(double d) {
        if (Double.isNaN(d)) {
            return -1;
        }
        long uint32 = ScriptRuntime.toUint32(d);
        if (((double) uint32) != d || uint32 == 4294967295L) {
            return -1;
        }
        return uint32;
    }

    public Object get(int i) {
        return get((long) i);
    }
}
