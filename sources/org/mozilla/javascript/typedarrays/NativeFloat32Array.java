package org.mozilla.javascript.typedarrays;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.IdFunctionObject;
import org.mozilla.javascript.IdScriptableObject;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Undefined;

public class NativeFloat32Array extends NativeTypedArrayView<Float> {
    private static final int BYTES_PER_ELEMENT = 4;
    private static final String CLASS_NAME = "Float32Array";
    private static final long serialVersionUID = -8963461831950499340L;

    public NativeFloat32Array() {
    }

    public static void init(Context context, Scriptable scriptable, boolean z) {
        new NativeFloat32Array().exportAsJSClass(6, scriptable, z);
    }

    public int getBytesPerElement() {
        return 4;
    }

    public String getClassName() {
        return CLASS_NAME;
    }

    public Object js_get(int i) {
        if (checkIndex(i)) {
            return Undefined.instance;
        }
        return ByteIo.readFloat32(this.arrayBuffer.buffer, (i * 4) + this.offset, NativeArrayBufferView.useLittleEndian());
    }

    public Object js_set(int i, Object obj) {
        if (checkIndex(i)) {
            return Undefined.instance;
        }
        ByteIo.writeFloat32(this.arrayBuffer.buffer, (i * 4) + this.offset, ScriptRuntime.toNumber(obj), NativeArrayBufferView.useLittleEndian());
        return null;
    }

    public NativeFloat32Array(NativeArrayBuffer nativeArrayBuffer, int i, int i2) {
        super(nativeArrayBuffer, i, i2, i2 * 4);
    }

    public NativeFloat32Array construct(NativeArrayBuffer nativeArrayBuffer, int i, int i2) {
        return new NativeFloat32Array(nativeArrayBuffer, i, i2);
    }

    public Float get(int i) {
        if (!checkIndex(i)) {
            return (Float) js_get(i);
        }
        throw new IndexOutOfBoundsException();
    }

    public NativeFloat32Array realThis(Scriptable scriptable, IdFunctionObject idFunctionObject) {
        if (scriptable instanceof NativeFloat32Array) {
            return (NativeFloat32Array) scriptable;
        }
        throw IdScriptableObject.incompatibleCallError(idFunctionObject);
    }

    public Float set(int i, Float f) {
        if (!checkIndex(i)) {
            return (Float) js_set(i, f);
        }
        throw new IndexOutOfBoundsException();
    }

    public NativeFloat32Array(int i) {
        this(new NativeArrayBuffer(((double) i) * 4.0d), 0, i);
    }
}
