package org.mozilla.javascript.typedarrays;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.IdFunctionObject;
import org.mozilla.javascript.IdScriptableObject;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Undefined;

public class NativeDataView extends NativeArrayBufferView {
    public static final String CLASS_NAME = "DataView";
    private static final int Id_constructor = 1;
    private static final int Id_getFloat32 = 8;
    private static final int Id_getFloat64 = 9;
    private static final int Id_getInt16 = 4;
    private static final int Id_getInt32 = 6;
    private static final int Id_getInt8 = 2;
    private static final int Id_getUint16 = 5;
    private static final int Id_getUint32 = 7;
    private static final int Id_getUint8 = 3;
    private static final int Id_setFloat32 = 16;
    private static final int Id_setFloat64 = 17;
    private static final int Id_setInt16 = 12;
    private static final int Id_setInt32 = 14;
    private static final int Id_setInt8 = 10;
    private static final int Id_setUint16 = 13;
    private static final int Id_setUint32 = 15;
    private static final int Id_setUint8 = 11;
    private static final int MAX_PROTOTYPE_ID = 17;
    private static final long serialVersionUID = 1427967607557438968L;

    public NativeDataView() {
    }

    private static int determinePos(Object[] objArr) {
        if (!NativeArrayBufferView.isArg(objArr, 0)) {
            return 0;
        }
        double number = ScriptRuntime.toNumber(objArr[0]);
        if (!Double.isInfinite(number)) {
            return ScriptRuntime.toInt32(number);
        }
        throw ScriptRuntime.rangeError("offset out of range");
    }

    public static void init(Context context, Scriptable scriptable, boolean z) {
        new NativeDataView().exportAsJSClass(17, scriptable, z);
    }

    private static NativeDataView js_constructor(Object[] objArr) {
        int i;
        int i2 = 0;
        if (NativeArrayBufferView.isArg(objArr, 0)) {
            NativeArrayBuffer nativeArrayBuffer = objArr[0];
            if (nativeArrayBuffer instanceof NativeArrayBuffer) {
                NativeArrayBuffer nativeArrayBuffer2 = nativeArrayBuffer;
                if (NativeArrayBufferView.isArg(objArr, 1)) {
                    double number = ScriptRuntime.toNumber(objArr[1]);
                    if (!Double.isInfinite(number)) {
                        i2 = ScriptRuntime.toInt32(number);
                    } else {
                        throw ScriptRuntime.rangeError("offset out of range");
                    }
                }
                if (NativeArrayBufferView.isArg(objArr, 2)) {
                    double number2 = ScriptRuntime.toNumber(objArr[2]);
                    if (!Double.isInfinite(number2)) {
                        i = ScriptRuntime.toInt32(number2);
                    } else {
                        throw ScriptRuntime.rangeError("offset out of range");
                    }
                } else {
                    i = nativeArrayBuffer2.getLength() - i2;
                }
                if (i < 0) {
                    throw ScriptRuntime.rangeError("length out of range");
                } else if (i2 >= 0 && i2 + i <= nativeArrayBuffer2.getLength()) {
                    return new NativeDataView(nativeArrayBuffer2, i2, i);
                } else {
                    throw ScriptRuntime.rangeError("offset out of range");
                }
            }
        }
        throw ScriptRuntime.constructError("TypeError", "Missing parameters");
    }

    private Object js_getFloat(int i, Object[] objArr) {
        int determinePos = determinePos(objArr);
        rangeCheck(determinePos, i);
        boolean z = true;
        if (!NativeArrayBufferView.isArg(objArr, 1) || i <= 1 || !ScriptRuntime.toBoolean(objArr[1])) {
            z = false;
        }
        if (i == 4) {
            return ByteIo.readFloat32(this.arrayBuffer.buffer, this.offset + determinePos, z);
        }
        if (i == 8) {
            return ByteIo.readFloat64(this.arrayBuffer.buffer, this.offset + determinePos, z);
        }
        throw new AssertionError();
    }

    private Object js_getInt(int i, boolean z, Object[] objArr) {
        boolean z2;
        int determinePos = determinePos(objArr);
        rangeCheck(determinePos, i);
        if (!NativeArrayBufferView.isArg(objArr, 1) || i <= 1 || !ScriptRuntime.toBoolean(objArr[1])) {
            z2 = false;
        } else {
            z2 = true;
        }
        if (i != 1) {
            if (i != 2) {
                if (i == 4) {
                    byte[] bArr = this.arrayBuffer.buffer;
                    if (z) {
                        return ByteIo.readInt32(bArr, this.offset + determinePos, z2);
                    }
                    return ByteIo.readUint32(bArr, this.offset + determinePos, z2);
                }
                throw new AssertionError();
            } else if (z) {
                return ByteIo.readInt16(this.arrayBuffer.buffer, this.offset + determinePos, z2);
            } else {
                return ByteIo.readUint16(this.arrayBuffer.buffer, this.offset + determinePos, z2);
            }
        } else if (z) {
            return ByteIo.readInt8(this.arrayBuffer.buffer, this.offset + determinePos);
        } else {
            return ByteIo.readUint8(this.arrayBuffer.buffer, this.offset + determinePos);
        }
    }

    private void js_setFloat(int i, Object[] objArr) {
        boolean z;
        double d;
        int determinePos = determinePos(objArr);
        if (determinePos >= 0) {
            if (!NativeArrayBufferView.isArg(objArr, 2) || i <= 1 || !ScriptRuntime.toBoolean(objArr[2])) {
                z = false;
            } else {
                z = true;
            }
            if (objArr.length > 1) {
                d = ScriptRuntime.toNumber(objArr[1]);
            } else {
                d = Double.NaN;
            }
            if (determinePos + i > this.byteLength) {
                throw ScriptRuntime.rangeError("offset out of range");
            } else if (i == 4) {
                ByteIo.writeFloat32(this.arrayBuffer.buffer, this.offset + determinePos, d, z);
            } else if (i == 8) {
                ByteIo.writeFloat64(this.arrayBuffer.buffer, this.offset + determinePos, d, z);
            } else {
                throw new AssertionError();
            }
        } else {
            throw ScriptRuntime.rangeError("offset out of range");
        }
    }

    private void js_setInt(int i, boolean z, Object[] objArr) {
        boolean z2;
        int determinePos = determinePos(objArr);
        if (determinePos >= 0) {
            if (!NativeArrayBufferView.isArg(objArr, 2) || i <= 1 || !ScriptRuntime.toBoolean(objArr[2])) {
                z2 = false;
            } else {
                z2 = true;
            }
            Double d = ScriptRuntime.zeroObj;
            if (objArr.length > 1) {
                d = objArr[1];
            }
            if (i != 1) {
                if (i != 2) {
                    if (i != 4) {
                        throw new AssertionError();
                    } else if (z) {
                        int int32 = Conversions.toInt32(d);
                        if (i + determinePos <= this.byteLength) {
                            ByteIo.writeInt32(this.arrayBuffer.buffer, this.offset + determinePos, int32, z2);
                            return;
                        }
                        throw ScriptRuntime.rangeError("offset out of range");
                    } else {
                        long uint32 = Conversions.toUint32(d);
                        if (i + determinePos <= this.byteLength) {
                            ByteIo.writeUint32(this.arrayBuffer.buffer, this.offset + determinePos, uint32, z2);
                            return;
                        }
                        throw ScriptRuntime.rangeError("offset out of range");
                    }
                } else if (z) {
                    int int16 = Conversions.toInt16(d);
                    if (i + determinePos <= this.byteLength) {
                        ByteIo.writeInt16(this.arrayBuffer.buffer, this.offset + determinePos, int16, z2);
                        return;
                    }
                    throw ScriptRuntime.rangeError("offset out of range");
                } else {
                    int uint16 = Conversions.toUint16(d);
                    if (i + determinePos <= this.byteLength) {
                        ByteIo.writeUint16(this.arrayBuffer.buffer, this.offset + determinePos, uint16, z2);
                        return;
                    }
                    throw ScriptRuntime.rangeError("offset out of range");
                }
            } else if (z) {
                int int8 = Conversions.toInt8(d);
                if (i + determinePos <= this.byteLength) {
                    ByteIo.writeInt8(this.arrayBuffer.buffer, this.offset + determinePos, int8);
                    return;
                }
                throw ScriptRuntime.rangeError("offset out of range");
            } else {
                int uint8 = Conversions.toUint8(d);
                if (i + determinePos <= this.byteLength) {
                    ByteIo.writeUint8(this.arrayBuffer.buffer, this.offset + determinePos, uint8);
                    return;
                }
                throw ScriptRuntime.rangeError("offset out of range");
            }
        } else {
            throw ScriptRuntime.rangeError("offset out of range");
        }
    }

    private void rangeCheck(int i, int i2) {
        if (i < 0 || i + i2 > this.byteLength) {
            throw ScriptRuntime.rangeError("offset out of range");
        }
    }

    private static NativeDataView realThis(Scriptable scriptable, IdFunctionObject idFunctionObject) {
        if (scriptable instanceof NativeDataView) {
            return (NativeDataView) scriptable;
        }
        throw IdScriptableObject.incompatibleCallError(idFunctionObject);
    }

    public Object execIdCall(IdFunctionObject idFunctionObject, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        if (!idFunctionObject.hasTag(getClassName())) {
            return super.execIdCall(idFunctionObject, context, scriptable, scriptable2, objArr);
        }
        int methodId = idFunctionObject.methodId();
        switch (methodId) {
            case 1:
                return js_constructor(objArr);
            case 2:
                return realThis(scriptable2, idFunctionObject).js_getInt(1, true, objArr);
            case 3:
                return realThis(scriptable2, idFunctionObject).js_getInt(1, false, objArr);
            case 4:
                return realThis(scriptable2, idFunctionObject).js_getInt(2, true, objArr);
            case 5:
                return realThis(scriptable2, idFunctionObject).js_getInt(2, false, objArr);
            case 6:
                return realThis(scriptable2, idFunctionObject).js_getInt(4, true, objArr);
            case 7:
                return realThis(scriptable2, idFunctionObject).js_getInt(4, false, objArr);
            case 8:
                return realThis(scriptable2, idFunctionObject).js_getFloat(4, objArr);
            case 9:
                return realThis(scriptable2, idFunctionObject).js_getFloat(8, objArr);
            case 10:
                realThis(scriptable2, idFunctionObject).js_setInt(1, true, objArr);
                return Undefined.instance;
            case 11:
                realThis(scriptable2, idFunctionObject).js_setInt(1, false, objArr);
                return Undefined.instance;
            case 12:
                realThis(scriptable2, idFunctionObject).js_setInt(2, true, objArr);
                return Undefined.instance;
            case 13:
                realThis(scriptable2, idFunctionObject).js_setInt(2, false, objArr);
                return Undefined.instance;
            case 14:
                realThis(scriptable2, idFunctionObject).js_setInt(4, true, objArr);
                return Undefined.instance;
            case 15:
                realThis(scriptable2, idFunctionObject).js_setInt(4, false, objArr);
                return Undefined.instance;
            case 16:
                realThis(scriptable2, idFunctionObject).js_setFloat(4, objArr);
                return Undefined.instance;
            case 17:
                realThis(scriptable2, idFunctionObject).js_setFloat(8, objArr);
                return Undefined.instance;
            default:
                throw new IllegalArgumentException(String.valueOf(methodId));
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int findPrototypeId(java.lang.String r9) {
        /*
            r8 = this;
            int r0 = r9.length()
            r1 = 8
            r2 = 50
            r3 = 115(0x73, float:1.61E-43)
            r4 = 103(0x67, float:1.44E-43)
            r5 = 0
            switch(r0) {
                case 7: goto L_0x00ba;
                case 8: goto L_0x0077;
                case 9: goto L_0x0049;
                case 10: goto L_0x0017;
                case 11: goto L_0x0012;
                default: goto L_0x0010;
            }
        L_0x0010:
            goto L_0x00cb
        L_0x0012:
            java.lang.String r0 = "constructor"
            r1 = 1
            goto L_0x00cd
        L_0x0017:
            char r0 = r9.charAt(r5)
            r6 = 52
            r7 = 9
            if (r0 != r4) goto L_0x0033
            char r0 = r9.charAt(r7)
            if (r0 != r2) goto L_0x002b
            java.lang.String r0 = "getFloat32"
            goto L_0x00cd
        L_0x002b:
            if (r0 != r6) goto L_0x00cb
            java.lang.String r0 = "getFloat64"
            r1 = 9
            goto L_0x00cd
        L_0x0033:
            if (r0 != r3) goto L_0x00cb
            char r0 = r9.charAt(r7)
            if (r0 != r2) goto L_0x0041
            java.lang.String r0 = "setFloat32"
            r1 = 16
            goto L_0x00cd
        L_0x0041:
            if (r0 != r6) goto L_0x00cb
            java.lang.String r0 = "setFloat64"
            r1 = 17
            goto L_0x00cd
        L_0x0049:
            char r0 = r9.charAt(r5)
            r6 = 54
            if (r0 != r4) goto L_0x0063
            char r0 = r9.charAt(r1)
            if (r0 != r2) goto L_0x005c
            java.lang.String r0 = "getUint32"
            r1 = 7
            goto L_0x00cd
        L_0x005c:
            if (r0 != r6) goto L_0x00cb
            java.lang.String r0 = "getUint16"
            r1 = 5
            goto L_0x00cd
        L_0x0063:
            if (r0 != r3) goto L_0x00cb
            char r0 = r9.charAt(r1)
            if (r0 != r2) goto L_0x0070
            java.lang.String r0 = "setUint32"
            r1 = 15
            goto L_0x00cd
        L_0x0070:
            if (r0 != r6) goto L_0x00cb
            java.lang.String r0 = "setUint16"
            r1 = 13
            goto L_0x00cd
        L_0x0077:
            r1 = 6
            char r0 = r9.charAt(r1)
            r2 = 49
            if (r0 != r2) goto L_0x0091
            char r0 = r9.charAt(r5)
            if (r0 != r4) goto L_0x008a
            java.lang.String r0 = "getInt16"
            r1 = 4
            goto L_0x00cd
        L_0x008a:
            if (r0 != r3) goto L_0x00cb
            java.lang.String r0 = "setInt16"
            r1 = 12
            goto L_0x00cd
        L_0x0091:
            r2 = 51
            if (r0 != r2) goto L_0x00a5
            char r0 = r9.charAt(r5)
            if (r0 != r4) goto L_0x009e
            java.lang.String r0 = "getInt32"
            goto L_0x00cd
        L_0x009e:
            if (r0 != r3) goto L_0x00cb
            java.lang.String r0 = "setInt32"
            r1 = 14
            goto L_0x00cd
        L_0x00a5:
            r1 = 116(0x74, float:1.63E-43)
            if (r0 != r1) goto L_0x00cb
            char r0 = r9.charAt(r5)
            if (r0 != r4) goto L_0x00b3
            java.lang.String r0 = "getUint8"
            r1 = 3
            goto L_0x00cd
        L_0x00b3:
            if (r0 != r3) goto L_0x00cb
            java.lang.String r0 = "setUint8"
            r1 = 11
            goto L_0x00cd
        L_0x00ba:
            char r0 = r9.charAt(r5)
            if (r0 != r4) goto L_0x00c4
            java.lang.String r0 = "getInt8"
            r1 = 2
            goto L_0x00cd
        L_0x00c4:
            if (r0 != r3) goto L_0x00cb
            java.lang.String r0 = "setInt8"
            r1 = 10
            goto L_0x00cd
        L_0x00cb:
            r0 = 0
            r1 = 0
        L_0x00cd:
            if (r0 == 0) goto L_0x00d8
            if (r0 == r9) goto L_0x00d8
            boolean r9 = r0.equals(r9)
            if (r9 != 0) goto L_0x00d8
            goto L_0x00d9
        L_0x00d8:
            r5 = r1
        L_0x00d9:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.typedarrays.NativeDataView.findPrototypeId(java.lang.String):int");
    }

    public String getClassName() {
        return CLASS_NAME;
    }

    public void initPrototypeId(int i) {
        String str;
        String str2;
        int i2 = 2;
        switch (i) {
            case 1:
                i2 = 3;
                str = "constructor";
                break;
            case 2:
                str2 = "getInt8";
                break;
            case 3:
                str2 = "getUint8";
                break;
            case 4:
                str2 = "getInt16";
                break;
            case 5:
                str2 = "getUint16";
                break;
            case 6:
                str2 = "getInt32";
                break;
            case 7:
                str2 = "getUint32";
                break;
            case 8:
                str2 = "getFloat32";
                break;
            case 9:
                str2 = "getFloat64";
                break;
            case 10:
                str = "setInt8";
                break;
            case 11:
                str = "setUint8";
                break;
            case 12:
                str = "setInt16";
                break;
            case 13:
                str = "setUint16";
                break;
            case 14:
                str = "setInt32";
                break;
            case 15:
                str = "setUint32";
                break;
            case 16:
                str = "setFloat32";
                break;
            case 17:
                str = "setFloat64";
                break;
            default:
                throw new IllegalArgumentException(String.valueOf(i));
        }
        str = str2;
        i2 = 1;
        initPrototypeMethod(getClassName(), i, str, i2);
    }

    public NativeDataView(NativeArrayBuffer nativeArrayBuffer, int i, int i2) {
        super(nativeArrayBuffer, i, i2);
    }
}
