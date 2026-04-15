package org.mozilla.javascript.typedarrays;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ExternalArrayData;
import org.mozilla.javascript.IdFunctionObject;
import org.mozilla.javascript.IdScriptableObject;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeArrayIterator;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Symbol;
import org.mozilla.javascript.SymbolKey;
import org.mozilla.javascript.Undefined;
import org.mozilla.javascript.Wrapper;

public abstract class NativeTypedArrayView<T> extends NativeArrayBufferView implements List<T>, RandomAccess, ExternalArrayData {
    private static final int Id_BYTES_PER_ELEMENT = 5;
    private static final int Id_constructor = 1;
    private static final int Id_get = 3;
    private static final int Id_length = 4;
    private static final int Id_set = 4;
    private static final int Id_subarray = 5;
    private static final int Id_toString = 2;
    private static final int MAX_INSTANCE_ID = 5;
    protected static final int MAX_PROTOTYPE_ID = 6;
    private static final int SymbolId_iterator = 6;
    private static final long serialVersionUID = -4963053773152251274L;
    protected final int length;

    public NativeTypedArrayView() {
        this.length = 0;
    }

    private NativeTypedArrayView<T> js_constructor(Context context, Scriptable scriptable, Object[] objArr) {
        int i;
        int i2 = 0;
        if (!NativeArrayBufferView.isArg(objArr, 0)) {
            return construct(new NativeArrayBuffer(), 0, 0);
        }
        NativeTypedArrayView nativeTypedArrayView = objArr[0];
        if (nativeTypedArrayView == null) {
            return construct(new NativeArrayBuffer(), 0, 0);
        }
        if ((nativeTypedArrayView instanceof Number) || (nativeTypedArrayView instanceof String)) {
            int int32 = ScriptRuntime.toInt32((Object) nativeTypedArrayView);
            return construct(makeArrayBuffer(context, scriptable, int32), 0, int32);
        } else if (nativeTypedArrayView instanceof NativeTypedArrayView) {
            NativeTypedArrayView nativeTypedArrayView2 = nativeTypedArrayView;
            NativeTypedArrayView<T> construct = construct(makeArrayBuffer(context, scriptable, nativeTypedArrayView2.length), 0, nativeTypedArrayView2.length);
            while (i2 < nativeTypedArrayView2.length) {
                construct.js_set(i2, nativeTypedArrayView2.js_get(i2));
                i2++;
            }
            return construct;
        } else if (nativeTypedArrayView instanceof NativeArrayBuffer) {
            NativeArrayBuffer nativeArrayBuffer = (NativeArrayBuffer) nativeTypedArrayView;
            if (NativeArrayBufferView.isArg(objArr, 1)) {
                i2 = ScriptRuntime.toInt32(objArr[1]);
            }
            if (NativeArrayBufferView.isArg(objArr, 2)) {
                i = getBytesPerElement() * ScriptRuntime.toInt32(objArr[2]);
            } else {
                i = nativeArrayBuffer.getLength() - i2;
            }
            if (i2 >= 0) {
                byte[] bArr = nativeArrayBuffer.buffer;
                if (i2 <= bArr.length) {
                    if (i < 0 || i2 + i > bArr.length) {
                        throw ScriptRuntime.rangeError("length out of range");
                    } else if (i2 % getBytesPerElement() != 0) {
                        throw ScriptRuntime.rangeError("offset must be a multiple of the byte size");
                    } else if (i % getBytesPerElement() == 0) {
                        return construct(nativeArrayBuffer, i2, i / getBytesPerElement());
                    } else {
                        throw ScriptRuntime.rangeError("offset and buffer must be a multiple of the byte size");
                    }
                }
            }
            throw ScriptRuntime.rangeError("offset out of range");
        } else if (nativeTypedArrayView instanceof NativeArray) {
            NativeArray nativeArray = (NativeArray) nativeTypedArrayView;
            NativeTypedArrayView<T> construct2 = construct(makeArrayBuffer(context, scriptable, nativeArray.size()), 0, nativeArray.size());
            while (i2 < nativeArray.size()) {
                Object obj = nativeArray.get(i2, nativeArray);
                if (obj == Scriptable.NOT_FOUND || obj == Undefined.instance) {
                    construct2.js_set(i2, ScriptRuntime.NaNobj);
                } else if (obj instanceof Wrapper) {
                    construct2.js_set(i2, ((Wrapper) obj).unwrap());
                } else {
                    construct2.js_set(i2, obj);
                }
                i2++;
            }
            return construct2;
        } else if (ScriptRuntime.isArrayObject(nativeTypedArrayView)) {
            Object[] arrayElements = ScriptRuntime.getArrayElements(nativeTypedArrayView);
            NativeTypedArrayView<T> construct3 = construct(makeArrayBuffer(context, scriptable, arrayElements.length), 0, arrayElements.length);
            while (i2 < arrayElements.length) {
                construct3.js_set(i2, arrayElements[i2]);
                i2++;
            }
            return construct3;
        } else {
            throw ScriptRuntime.constructError("Error", "invalid argument");
        }
    }

    private Object js_subarray(Context context, Scriptable scriptable, int i, int i2) {
        if (i < 0) {
            i += this.length;
        }
        if (i2 < 0) {
            i2 += this.length;
        }
        int max = Math.max(0, i);
        int max2 = Math.max(0, Math.min(this.length, i2) - max);
        int min = Math.min(getBytesPerElement() * max, this.arrayBuffer.getLength());
        return context.newObject(scriptable, getClassName(), new Object[]{this.arrayBuffer, Integer.valueOf(min), Integer.valueOf(max2)});
    }

    private NativeArrayBuffer makeArrayBuffer(Context context, Scriptable scriptable, int i) {
        return (NativeArrayBuffer) context.newObject(scriptable, NativeArrayBuffer.CLASS_NAME, new Object[]{Double.valueOf(((double) i) * ((double) getBytesPerElement()))});
    }

    private void setRange(NativeTypedArrayView<T> nativeTypedArrayView, int i) {
        int i2 = this.length;
        if (i < i2) {
            int i3 = nativeTypedArrayView.length;
            if (i3 <= i2 - i) {
                int i4 = 0;
                if (nativeTypedArrayView.arrayBuffer == this.arrayBuffer) {
                    Object[] objArr = new Object[i3];
                    for (int i5 = 0; i5 < nativeTypedArrayView.length; i5++) {
                        objArr[i5] = nativeTypedArrayView.js_get(i5);
                    }
                    while (i4 < nativeTypedArrayView.length) {
                        js_set(i4 + i, objArr[i4]);
                        i4++;
                    }
                    return;
                }
                while (i4 < nativeTypedArrayView.length) {
                    js_set(i4 + i, nativeTypedArrayView.js_get(i4));
                    i4++;
                }
                return;
            }
            throw ScriptRuntime.rangeError("source array too long");
        }
        throw ScriptRuntime.rangeError("offset out of range");
    }

    public boolean add(T t) {
        throw new UnsupportedOperationException();
    }

    public boolean addAll(Collection<? extends T> collection) {
        throw new UnsupportedOperationException();
    }

    public boolean checkIndex(int i) {
        return i < 0 || i >= this.length;
    }

    public void clear() {
        throw new UnsupportedOperationException();
    }

    public abstract NativeTypedArrayView<T> construct(NativeArrayBuffer nativeArrayBuffer, int i, int i2);

    public boolean contains(Object obj) {
        return indexOf(obj) >= 0;
    }

    public boolean containsAll(Collection<?> collection) {
        for (Object contains : collection) {
            if (!contains(contains)) {
                return false;
            }
        }
        return true;
    }

    public void delete(int i) {
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        try {
            NativeTypedArrayView nativeTypedArrayView = (NativeTypedArrayView) obj;
            if (this.length != nativeTypedArrayView.length) {
                return false;
            }
            for (int i = 0; i < this.length; i++) {
                if (!js_get(i).equals(nativeTypedArrayView.js_get(i))) {
                    return false;
                }
            }
            return true;
        } catch (ClassCastException unused) {
            return false;
        }
    }

    public Object execIdCall(IdFunctionObject idFunctionObject, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        int i;
        int i2;
        int i3;
        if (!idFunctionObject.hasTag(getClassName())) {
            return super.execIdCall(idFunctionObject, context, scriptable, scriptable2, objArr);
        }
        int methodId = idFunctionObject.methodId();
        switch (methodId) {
            case 1:
                if (scriptable2 == null || context.getLanguageVersion() < 200) {
                    return js_constructor(context, scriptable, objArr);
                }
                throw ScriptRuntime.typeError1("msg.only.from.new", getClassName());
            case 2:
                NativeTypedArrayView realThis = realThis(scriptable2, idFunctionObject);
                int arrayLength = realThis.getArrayLength();
                StringBuilder sb = new StringBuilder();
                if (arrayLength > 0) {
                    sb.append(ScriptRuntime.toString(realThis.js_get(0)));
                }
                for (int i4 = 1; i4 < arrayLength; i4++) {
                    sb.append(',');
                    sb.append(ScriptRuntime.toString(realThis.js_get(i4)));
                }
                return sb.toString();
            case 3:
                if (objArr.length > 0) {
                    return realThis(scriptable2, idFunctionObject).js_get(ScriptRuntime.toInt32(objArr[0]));
                }
                throw ScriptRuntime.constructError("Error", "invalid arguments");
            case 4:
                if (objArr.length > 0) {
                    NativeTypedArrayView realThis2 = realThis(scriptable2, idFunctionObject);
                    Object obj = objArr[0];
                    if (obj instanceof NativeTypedArrayView) {
                        if (NativeArrayBufferView.isArg(objArr, 1)) {
                            i2 = ScriptRuntime.toInt32(objArr[1]);
                        } else {
                            i2 = 0;
                        }
                        realThis2.setRange(objArr[0], i2);
                        return Undefined.instance;
                    } else if (obj instanceof NativeArray) {
                        if (NativeArrayBufferView.isArg(objArr, 1)) {
                            i = ScriptRuntime.toInt32(objArr[1]);
                        } else {
                            i = 0;
                        }
                        realThis2.setRange(objArr[0], i);
                        return Undefined.instance;
                    } else if (obj instanceof Scriptable) {
                        return Undefined.instance;
                    } else {
                        if (NativeArrayBufferView.isArg(objArr, 2)) {
                            return realThis2.js_set(ScriptRuntime.toInt32(objArr[0]), objArr[1]);
                        }
                    }
                }
                throw ScriptRuntime.constructError("Error", "invalid arguments");
            case 5:
                if (objArr.length > 0) {
                    NativeTypedArrayView realThis3 = realThis(scriptable2, idFunctionObject);
                    int int32 = ScriptRuntime.toInt32(objArr[0]);
                    if (NativeArrayBufferView.isArg(objArr, 1)) {
                        i3 = ScriptRuntime.toInt32(objArr[1]);
                    } else {
                        i3 = realThis3.length;
                    }
                    return realThis3.js_subarray(context, scriptable, int32, i3);
                }
                throw ScriptRuntime.constructError("Error", "invalid arguments");
            case 6:
                return new NativeArrayIterator(scriptable, scriptable2, NativeArrayIterator.ARRAY_ITERATOR_TYPE.VALUES);
            default:
                throw new IllegalArgumentException(String.valueOf(methodId));
        }
    }

    public void fillConstructorProperties(IdFunctionObject idFunctionObject) {
        idFunctionObject.defineProperty("BYTES_PER_ELEMENT", (Object) ScriptRuntime.wrapInt(getBytesPerElement()), 7);
        super.fillConstructorProperties(idFunctionObject);
    }

    public int findInstanceIdInfo(String str) {
        int i;
        String str2;
        int length2 = str.length();
        int i2 = 0;
        if (length2 == 6) {
            str2 = "length";
            i = 4;
        } else if (length2 == 17) {
            str2 = "BYTES_PER_ELEMENT";
            i = 5;
        } else {
            str2 = null;
            i = 0;
        }
        if (str2 == null || str2 == str || str2.equals(str)) {
            i2 = i;
        }
        if (i2 == 0) {
            return super.findInstanceIdInfo(str);
        }
        if (i2 == 5) {
            return IdScriptableObject.instanceIdInfo(7, i2);
        }
        return IdScriptableObject.instanceIdInfo(5, i2);
    }

    public int findPrototypeId(Symbol symbol) {
        return SymbolKey.ITERATOR.equals(symbol) ? 6 : 0;
    }

    public Object get(int i, Scriptable scriptable) {
        return js_get(i);
    }

    public Object getArrayElement(int i) {
        return js_get(i);
    }

    public int getArrayLength() {
        return this.length;
    }

    public abstract int getBytesPerElement();

    public Object[] getIds() {
        Object[] objArr = new Object[this.length];
        for (int i = 0; i < this.length; i++) {
            objArr[i] = Integer.valueOf(i);
        }
        return objArr;
    }

    public String getInstanceIdName(int i) {
        return i != 4 ? i != 5 ? super.getInstanceIdName(i) : "BYTES_PER_ELEMENT" : "length";
    }

    public Object getInstanceIdValue(int i) {
        if (i == 4) {
            return ScriptRuntime.wrapInt(this.length);
        }
        if (i != 5) {
            return super.getInstanceIdValue(i);
        }
        return ScriptRuntime.wrapInt(getBytesPerElement());
    }

    public int getMaxInstanceId() {
        return 5;
    }

    public boolean has(int i, Scriptable scriptable) {
        return !checkIndex(i);
    }

    public int hashCode() {
        int i = 0;
        for (int i2 = 0; i2 < this.length; i2++) {
            i += js_get(i2).hashCode();
        }
        return i;
    }

    public int indexOf(Object obj) {
        for (int i = 0; i < this.length; i++) {
            if (obj.equals(js_get(i))) {
                return i;
            }
        }
        return -1;
    }

    public void initPrototypeId(int i) {
        int i2;
        String str;
        String str2;
        if (i == 6) {
            initPrototypeMethod((Object) getClassName(), i, (Symbol) SymbolKey.ITERATOR, "[Symbol.iterator]", 0);
            return;
        }
        if (i == 1) {
            str = "constructor";
            i2 = 3;
        } else if (i == 2) {
            str = "toString";
            i2 = 0;
        } else if (i != 3) {
            if (i == 4) {
                str2 = "set";
            } else if (i == 5) {
                str2 = "subarray";
            } else {
                throw new IllegalArgumentException(String.valueOf(i));
            }
            str = str2;
            i2 = 2;
        } else {
            str = "get";
            i2 = 1;
        }
        initPrototypeMethod((Object) getClassName(), i, str, (String) null, i2);
    }

    public boolean isEmpty() {
        return this.length == 0;
    }

    public Iterator<T> iterator() {
        return new NativeTypedArrayIterator(this, 0);
    }

    public abstract Object js_get(int i);

    public abstract Object js_set(int i, Object obj);

    public int lastIndexOf(Object obj) {
        for (int i = this.length - 1; i >= 0; i--) {
            if (obj.equals(js_get(i))) {
                return i;
            }
        }
        return -1;
    }

    public ListIterator<T> listIterator() {
        return new NativeTypedArrayIterator(this, 0);
    }

    public void put(int i, Scriptable scriptable, Object obj) {
        js_set(i, obj);
    }

    public abstract NativeTypedArrayView<T> realThis(Scriptable scriptable, IdFunctionObject idFunctionObject);

    public T remove(int i) {
        throw new UnsupportedOperationException();
    }

    public boolean removeAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    public boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    public void setArrayElement(int i, Object obj) {
        js_set(i, obj);
    }

    public int size() {
        return this.length;
    }

    public List<T> subList(int i, int i2) {
        throw new UnsupportedOperationException();
    }

    public Object[] toArray() {
        Object[] objArr = new Object[this.length];
        for (int i = 0; i < this.length; i++) {
            objArr[i] = js_get(i);
        }
        return objArr;
    }

    public void add(int i, T t) {
        throw new UnsupportedOperationException();
    }

    public boolean addAll(int i, Collection<? extends T> collection) {
        throw new UnsupportedOperationException();
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x0055 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int findPrototypeId(java.lang.String r10) {
        /*
            r9 = this;
            int r0 = r10.length()
            r1 = 115(0x73, float:1.61E-43)
            r2 = 1
            r3 = 116(0x74, float:1.63E-43)
            r4 = 2
            r5 = 0
            r6 = 3
            if (r0 != r6) goto L_0x0036
            char r0 = r10.charAt(r5)
            r7 = 103(0x67, float:1.44E-43)
            r8 = 101(0x65, float:1.42E-43)
            if (r0 != r7) goto L_0x0026
            char r0 = r10.charAt(r4)
            if (r0 != r3) goto L_0x0051
            char r0 = r10.charAt(r2)
            if (r0 != r8) goto L_0x0051
            r5 = 3
            goto L_0x005f
        L_0x0026:
            if (r0 != r1) goto L_0x0051
            char r0 = r10.charAt(r4)
            if (r0 != r3) goto L_0x0051
            char r0 = r10.charAt(r2)
            if (r0 != r8) goto L_0x0051
            r5 = 4
            goto L_0x005f
        L_0x0036:
            r6 = 8
            if (r0 != r6) goto L_0x004a
            char r0 = r10.charAt(r5)
            if (r0 != r1) goto L_0x0044
            java.lang.String r0 = "subarray"
            r2 = 5
            goto L_0x0053
        L_0x0044:
            if (r0 != r3) goto L_0x0051
            java.lang.String r0 = "toString"
            r2 = 2
            goto L_0x0053
        L_0x004a:
            r1 = 11
            if (r0 != r1) goto L_0x0051
            java.lang.String r0 = "constructor"
            goto L_0x0053
        L_0x0051:
            r0 = 0
            r2 = 0
        L_0x0053:
            if (r0 == 0) goto L_0x005e
            if (r0 == r10) goto L_0x005e
            boolean r10 = r0.equals(r10)
            if (r10 != 0) goto L_0x005e
            goto L_0x005f
        L_0x005e:
            r5 = r2
        L_0x005f:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.typedarrays.NativeTypedArrayView.findPrototypeId(java.lang.String):int");
    }

    public ListIterator<T> listIterator(int i) {
        if (!checkIndex(i)) {
            return new NativeTypedArrayIterator(this, i);
        }
        throw new IndexOutOfBoundsException();
    }

    public boolean remove(Object obj) {
        throw new UnsupportedOperationException();
    }

    public NativeTypedArrayView(NativeArrayBuffer nativeArrayBuffer, int i, int i2, int i3) {
        super(nativeArrayBuffer, i, i3);
        this.length = i2;
    }

    public <U> U[] toArray(U[] uArr) {
        if (uArr.length < this.length) {
            uArr = (Object[]) Array.newInstance(uArr.getClass().getComponentType(), this.length);
        }
        int i = 0;
        while (i < this.length) {
            try {
                uArr[i] = js_get(i);
                i++;
            } catch (ClassCastException unused) {
                throw new ArrayStoreException();
            }
        }
        return uArr;
    }

    private void setRange(NativeArray nativeArray, int i) {
        if (i > this.length) {
            throw ScriptRuntime.rangeError("offset out of range");
        } else if (nativeArray.size() + i <= this.length) {
            Iterator it = nativeArray.iterator();
            while (it.hasNext()) {
                js_set(i, it.next());
                i++;
            }
        } else {
            throw ScriptRuntime.rangeError("offset + length out of range");
        }
    }
}
