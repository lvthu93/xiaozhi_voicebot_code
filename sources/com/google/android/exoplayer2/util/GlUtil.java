package com.google.android.exoplayer2.util;

import android.content.Context;
import android.opengl.EGL14;
import android.opengl.GLES20;
import android.opengl.GLU;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public final class GlUtil {

    public static final class Attribute {
        public final int a;
        public final int b;
        @Nullable
        public FloatBuffer c;
        public int d;

        public Attribute(int i, int i2) {
            int i3 = i;
            int[] iArr = new int[1];
            GLES20.glGetProgramiv(i3, 35722, iArr, 0);
            int i4 = iArr[0];
            byte[] bArr = new byte[i4];
            int[] iArr2 = new int[1];
            int i5 = i;
            int i6 = i2;
            int i7 = i4;
            GLES20.glGetActiveAttrib(i5, i6, i7, iArr2, 0, new int[1], 0, new int[1], 0, bArr, 0);
            int i8 = 0;
            while (true) {
                if (i8 >= i4) {
                    break;
                } else if (bArr[i8] == 0) {
                    i4 = i8;
                    break;
                } else {
                    i8++;
                }
            }
            this.b = GLES20.glGetAttribLocation(i3, new String(bArr, 0, i4));
            this.a = i2;
        }

        public void bind() {
            GLES20.glBindBuffer(34962, 0);
            GLES20.glVertexAttribPointer(this.b, this.d, 5126, false, 0, (Buffer) Assertions.checkNotNull(this.c, "call setBuffer before bind"));
            GLES20.glEnableVertexAttribArray(this.a);
            GlUtil.checkGlError();
        }

        public void setBuffer(float[] fArr, int i) {
            this.c = GlUtil.createBuffer(fArr);
            this.d = i;
        }
    }

    public static final class Uniform {
        public final int a;
        public final int b;
        public final float[] c;
        public int d;
        public int e;

        public Uniform(int i, int i2) {
            int i3 = i;
            int[] iArr = new int[1];
            GLES20.glGetProgramiv(i3, 35719, iArr, 0);
            int[] iArr2 = new int[1];
            int i4 = iArr[0];
            byte[] bArr = new byte[i4];
            int i5 = i;
            int i6 = i2;
            int i7 = i4;
            byte[] bArr2 = bArr;
            GLES20.glGetActiveUniform(i5, i6, i7, new int[1], 0, new int[1], 0, iArr2, 0, bArr, 0);
            int i8 = 0;
            while (true) {
                if (i8 >= i4) {
                    break;
                } else if (bArr2[i8] == 0) {
                    i4 = i8;
                    break;
                } else {
                    i8++;
                }
            }
            this.a = GLES20.glGetUniformLocation(i3, new String(bArr2, 0, i4));
            this.b = iArr2[0];
            this.c = new float[16];
        }

        public void bind() {
            float[] fArr = this.c;
            int i = this.a;
            int i2 = this.b;
            if (i2 == 5126) {
                GLES20.glUniform1fv(i, 1, fArr, 0);
                GlUtil.checkGlError();
            } else if (i2 == 35676) {
                GLES20.glUniformMatrix4fv(i, 1, false, fArr, 0);
                GlUtil.checkGlError();
            } else if (this.d != 0) {
                GLES20.glActiveTexture(this.e + 33984);
                if (i2 == 36198) {
                    GLES20.glBindTexture(36197, this.d);
                } else if (i2 == 35678) {
                    GLES20.glBindTexture(3553, this.d);
                } else {
                    throw new IllegalStateException(y2.d(36, "unexpected uniform type: ", i2));
                }
                GLES20.glUniform1i(i, this.e);
                GLES20.glTexParameteri(3553, 10240, 9729);
                GLES20.glTexParameteri(3553, 10241, 9729);
                GLES20.glTexParameteri(3553, 10242, 33071);
                GLES20.glTexParameteri(3553, 10243, 33071);
                GlUtil.checkGlError();
            } else {
                throw new IllegalStateException("call setSamplerTexId before bind");
            }
        }

        public void setFloat(float f) {
            this.c[0] = f;
        }

        public void setFloats(float[] fArr) {
            System.arraycopy(fArr, 0, this.c, 0, fArr.length);
        }

        public void setSamplerTexId(int i, int i2) {
            this.d = i;
            this.e = i2;
        }
    }

    public static void a(int i, int i2, String str) {
        int glCreateShader = GLES20.glCreateShader(i);
        GLES20.glShaderSource(glCreateShader, str);
        GLES20.glCompileShader(glCreateShader);
        int[] iArr = {0};
        GLES20.glGetShaderiv(glCreateShader, 35713, iArr, 0);
        if (iArr[0] != 1) {
            String glGetShaderInfoLog = GLES20.glGetShaderInfoLog(glCreateShader);
            StringBuilder sb = new StringBuilder(y2.c(str, y2.c(glGetShaderInfoLog, 10)));
            sb.append(glGetShaderInfoLog);
            sb.append(", source: ");
            sb.append(str);
            Log.e("GlUtil", sb.toString());
        }
        GLES20.glAttachShader(i2, glCreateShader);
        GLES20.glDeleteShader(glCreateShader);
        checkGlError();
    }

    public static void checkGlError() {
        String str;
        while (true) {
            int glGetError = GLES20.glGetError();
            if (glGetError != 0) {
                String valueOf = String.valueOf(GLU.gluErrorString(glGetError));
                if (valueOf.length() != 0) {
                    str = "glError ".concat(valueOf);
                } else {
                    str = new String("glError ");
                }
                Log.e("GlUtil", str);
            } else {
                return;
            }
        }
    }

    public static int compileProgram(String[] strArr, String[] strArr2) {
        return compileProgram(TextUtils.join("\n", strArr), TextUtils.join("\n", strArr2));
    }

    public static FloatBuffer createBuffer(float[] fArr) {
        return (FloatBuffer) createBuffer(fArr.length).put(fArr).flip();
    }

    public static int createExternalTexture() {
        int[] iArr = new int[1];
        GLES20.glGenTextures(1, IntBuffer.wrap(iArr));
        GLES20.glBindTexture(36197, iArr[0]);
        GLES20.glTexParameteri(36197, 10241, 9729);
        GLES20.glTexParameteri(36197, 10240, 9729);
        GLES20.glTexParameteri(36197, 10242, 33071);
        GLES20.glTexParameteri(36197, 10243, 33071);
        checkGlError();
        return iArr[0];
    }

    public static Attribute[] getAttributes(int i) {
        int[] iArr = new int[1];
        GLES20.glGetProgramiv(i, 35721, iArr, 0);
        int i2 = iArr[0];
        if (i2 == 2) {
            Attribute[] attributeArr = new Attribute[i2];
            for (int i3 = 0; i3 < iArr[0]; i3++) {
                attributeArr[i3] = new Attribute(i, i3);
            }
            return attributeArr;
        }
        throw new IllegalStateException("expected two attributes");
    }

    public static Uniform[] getUniforms(int i) {
        int[] iArr = new int[1];
        GLES20.glGetProgramiv(i, 35718, iArr, 0);
        Uniform[] uniformArr = new Uniform[iArr[0]];
        for (int i2 = 0; i2 < iArr[0]; i2++) {
            uniformArr[i2] = new Uniform(i, i2);
        }
        return uniformArr;
    }

    public static boolean isProtectedContentExtensionSupported(Context context) {
        String eglQueryString;
        int i = Util.a;
        if (i < 24) {
            return false;
        }
        if (i < 26 && ("samsung".equals(Util.c) || "XT1650".equals(Util.d))) {
            return false;
        }
        if ((i >= 26 || context.getPackageManager().hasSystemFeature("android.hardware.vr.high_performance")) && (eglQueryString = EGL14.eglQueryString(EGL14.eglGetDisplay(0), 12373)) != null && eglQueryString.contains("EGL_EXT_protected_content")) {
            return true;
        }
        return false;
    }

    public static boolean isSurfacelessContextExtensionSupported() {
        String eglQueryString;
        if (Util.a >= 17 && (eglQueryString = EGL14.eglQueryString(EGL14.eglGetDisplay(0), 12373)) != null && eglQueryString.contains("EGL_KHR_surfaceless_context")) {
            return true;
        }
        return false;
    }

    public static int compileProgram(String str, String str2) {
        int glCreateProgram = GLES20.glCreateProgram();
        checkGlError();
        a(35633, glCreateProgram, str);
        a(35632, glCreateProgram, str2);
        GLES20.glLinkProgram(glCreateProgram);
        int[] iArr = {0};
        GLES20.glGetProgramiv(glCreateProgram, 35714, iArr, 0);
        if (iArr[0] != 1) {
            String valueOf = String.valueOf(GLES20.glGetProgramInfoLog(glCreateProgram));
            Log.e("GlUtil", valueOf.length() != 0 ? "Unable to link shader program: \n".concat(valueOf) : new String("Unable to link shader program: \n"));
        }
        checkGlError();
        return glCreateProgram;
    }

    public static FloatBuffer createBuffer(int i) {
        return ByteBuffer.allocateDirect(i * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
    }
}
