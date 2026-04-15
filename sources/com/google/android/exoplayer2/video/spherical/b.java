package com.google.android.exoplayer2.video.spherical;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.GlUtil;
import com.google.android.exoplayer2.video.spherical.Projection;
import java.nio.FloatBuffer;

public final class b {
    public static final String[] j = {"uniform mat4 uMvpMatrix;", "uniform mat3 uTexMatrix;", "attribute vec4 aPosition;", "attribute vec2 aTexCoords;", "varying vec2 vTexCoords;", "void main() {", "  gl_Position = uMvpMatrix * aPosition;", "  vTexCoords = (uTexMatrix * vec3(aTexCoords, 1)).xy;", "}"};
    public static final String[] k = {"#extension GL_OES_EGL_image_external : require", "precision mediump float;", "uniform samplerExternalOES uTexture;", "varying vec2 vTexCoords;", "void main() {", "  gl_FragColor = texture2D(uTexture, vTexCoords);", "}"};
    public static final float[] l = {1.0f, 0.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 1.0f, 1.0f};
    public static final float[] m = {1.0f, 0.0f, 0.0f, 0.0f, -0.5f, 0.0f, 0.0f, 0.5f, 1.0f};
    public static final float[] n = {1.0f, 0.0f, 0.0f, 0.0f, -0.5f, 0.0f, 0.0f, 1.0f, 1.0f};
    public static final float[] o = {0.5f, 0.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 1.0f, 1.0f};
    public static final float[] p = {0.5f, 0.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.5f, 1.0f, 1.0f};
    public int a;
    @Nullable
    public a b;
    @Nullable
    public a c;
    public int d;
    public int e;
    public int f;
    public int g;
    public int h;
    public int i;

    public static class a {
        public final int a;
        public final FloatBuffer b;
        public final FloatBuffer c;
        public final int d;

        public a(Projection.SubMesh subMesh) {
            this.a = subMesh.getVertexCount();
            this.b = GlUtil.createBuffer(subMesh.c);
            this.c = GlUtil.createBuffer(subMesh.d);
            int i = subMesh.b;
            if (i == 1) {
                this.d = 5;
            } else if (i != 2) {
                this.d = 4;
            } else {
                this.d = 6;
            }
        }
    }

    public static boolean isSupported(Projection projection) {
        Projection.Mesh mesh = projection.a;
        if (mesh.getSubMeshCount() != 1 || mesh.getSubMesh(0).a != 0) {
            return false;
        }
        Projection.Mesh mesh2 = projection.b;
        if (mesh2.getSubMeshCount() == 1 && mesh2.getSubMesh(0).a == 0) {
            return true;
        }
        return false;
    }

    public void setProjection(Projection projection) {
        if (isSupported(projection)) {
            this.a = projection.c;
            a aVar = new a(projection.a.getSubMesh(0));
            this.b = aVar;
            if (!projection.d) {
                aVar = new a(projection.b.getSubMesh(0));
            }
            this.c = aVar;
        }
    }
}
