package org.mozilla.javascript.typedarrays;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.IdFunctionObject;
import org.mozilla.javascript.IdScriptableObject;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Undefined;

public class NativeArrayBuffer extends IdScriptableObject {
    public static final String CLASS_NAME = "ArrayBuffer";
    private static final int ConstructorId_isView = -1;
    private static final byte[] EMPTY_BUF = new byte[0];
    private static final int Id_byteLength = 1;
    private static final int Id_constructor = 1;
    private static final int Id_slice = 2;
    private static final int MAX_INSTANCE_ID = 1;
    private static final int MAX_PROTOTYPE_ID = 2;
    private static final long serialVersionUID = 3110411773054879549L;
    final byte[] buffer;

    public NativeArrayBuffer() {
        this.buffer = EMPTY_BUF;
    }

    public static void init(Context context, Scriptable scriptable, boolean z) {
        new NativeArrayBuffer().exportAsJSClass(2, scriptable, z);
    }

    private static boolean isArg(Object[] objArr, int i) {
        return objArr.length > i && !Undefined.instance.equals(objArr[i]);
    }

    private static NativeArrayBuffer realThis(Scriptable scriptable, IdFunctionObject idFunctionObject) {
        if (scriptable instanceof NativeArrayBuffer) {
            return (NativeArrayBuffer) scriptable;
        }
        throw IdScriptableObject.incompatibleCallError(idFunctionObject);
    }

    public Object execIdCall(IdFunctionObject idFunctionObject, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        double d;
        if (!idFunctionObject.hasTag(CLASS_NAME)) {
            return super.execIdCall(idFunctionObject, context, scriptable, scriptable2, objArr);
        }
        int methodId = idFunctionObject.methodId();
        boolean z = true;
        if (methodId != -1) {
            double d2 = 0.0d;
            if (methodId == 1) {
                if (isArg(objArr, 0)) {
                    d2 = ScriptRuntime.toNumber(objArr[0]);
                }
                return new NativeArrayBuffer(d2);
            } else if (methodId == 2) {
                NativeArrayBuffer realThis = realThis(scriptable2, idFunctionObject);
                if (isArg(objArr, 0)) {
                    d2 = ScriptRuntime.toNumber(objArr[0]);
                }
                if (isArg(objArr, 1)) {
                    d = ScriptRuntime.toNumber(objArr[1]);
                } else {
                    d = (double) realThis.buffer.length;
                }
                return realThis.slice(d2, d);
            } else {
                throw new IllegalArgumentException(String.valueOf(methodId));
            }
        } else {
            if (!isArg(objArr, 0) || !(objArr[0] instanceof NativeArrayBufferView)) {
                z = false;
            }
            return Boolean.valueOf(z);
        }
    }

    public void fillConstructorProperties(IdFunctionObject idFunctionObject) {
        addIdFunctionProperty(idFunctionObject, CLASS_NAME, -1, "isView", 1);
    }

    public int findInstanceIdInfo(String str) {
        if ("byteLength".equals(str)) {
            return IdScriptableObject.instanceIdInfo(5, 1);
        }
        return super.findInstanceIdInfo(str);
    }

    public int findPrototypeId(String str) {
        int i;
        String str2;
        int length = str.length();
        if (length == 5) {
            str2 = "slice";
            i = 2;
        } else if (length == 11) {
            str2 = "constructor";
            i = 1;
        } else {
            str2 = null;
            i = 0;
        }
        if (str2 == null || str2 == str || str2.equals(str)) {
            return i;
        }
        return 0;
    }

    public byte[] getBuffer() {
        return this.buffer;
    }

    public String getClassName() {
        return CLASS_NAME;
    }

    public String getInstanceIdName(int i) {
        if (i == 1) {
            return "byteLength";
        }
        return super.getInstanceIdName(i);
    }

    public Object getInstanceIdValue(int i) {
        if (i == 1) {
            return ScriptRuntime.wrapInt(this.buffer.length);
        }
        return super.getInstanceIdValue(i);
    }

    public int getLength() {
        return this.buffer.length;
    }

    public int getMaxInstanceId() {
        return 1;
    }

    public void initPrototypeId(int i) {
        String str;
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i == 2) {
                str = "slice";
            } else {
                throw new IllegalArgumentException(String.valueOf(i));
            }
        } else {
            str = "constructor";
        }
        initPrototypeMethod(CLASS_NAME, i, str, i2);
    }

    public NativeArrayBuffer slice(double d, double d2) {
        byte[] bArr = this.buffer;
        double length = (double) bArr.length;
        if (d2 < 0.0d) {
            d2 += (double) bArr.length;
        }
        int int32 = ScriptRuntime.toInt32(Math.max(0.0d, Math.min(length, d2)));
        double d3 = (double) int32;
        if (d < 0.0d) {
            d += (double) this.buffer.length;
        }
        int int322 = ScriptRuntime.toInt32(Math.min(d3, Math.max(0.0d, d)));
        int i = int32 - int322;
        NativeArrayBuffer nativeArrayBuffer = new NativeArrayBuffer((double) i);
        System.arraycopy(this.buffer, int322, nativeArrayBuffer.buffer, 0, i);
        return nativeArrayBuffer;
    }

    public NativeArrayBuffer(double d) {
        if (d >= 2.147483647E9d) {
            throw ScriptRuntime.rangeError("length parameter (" + d + ") is too large ");
        } else if (d == Double.NEGATIVE_INFINITY) {
            throw ScriptRuntime.rangeError("Negative array length " + d);
        } else if (d > -1.0d) {
            int int32 = ScriptRuntime.toInt32(d);
            if (int32 < 0) {
                throw ScriptRuntime.rangeError("Negative array length " + d);
            } else if (int32 == 0) {
                this.buffer = EMPTY_BUF;
            } else {
                this.buffer = new byte[int32];
            }
        } else {
            throw ScriptRuntime.rangeError("Negative array length " + d);
        }
    }
}
