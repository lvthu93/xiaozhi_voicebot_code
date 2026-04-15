package defpackage;

/* renamed from: qa  reason: default package */
public final class qa {
    public final byte[] a;
    public int b;
    public int c;
    public boolean d;
    public final boolean e;
    public qa f;
    public qa g;

    public qa() {
        this.a = new byte[8192];
        this.e = true;
        this.d = false;
    }

    public final qa a() {
        qa qaVar;
        qa qaVar2 = this.f;
        if (qaVar2 != this) {
            qaVar = qaVar2;
        } else {
            qaVar = null;
        }
        qa qaVar3 = this.g;
        qaVar3.f = qaVar2;
        this.f.g = qaVar3;
        this.f = null;
        this.g = null;
        return qaVar;
    }

    public final void b(qa qaVar) {
        qaVar.g = this;
        qaVar.f = this.f;
        this.f.g = qaVar;
        this.f = qaVar;
    }

    public final qa c() {
        this.d = true;
        return new qa(this.a, this.b, this.c, true, false);
    }

    public final void d(qa qaVar, int i) {
        if (qaVar.e) {
            int i2 = qaVar.c;
            int i3 = i2 + i;
            byte[] bArr = qaVar.a;
            if (i3 > 8192) {
                if (!qaVar.d) {
                    int i4 = qaVar.b;
                    if ((i2 + i) - i4 <= 8192) {
                        System.arraycopy(bArr, i4, bArr, 0, i2 - i4);
                        qaVar.c -= qaVar.b;
                        qaVar.b = 0;
                    } else {
                        throw new IllegalArgumentException();
                    }
                } else {
                    throw new IllegalArgumentException();
                }
            }
            System.arraycopy(this.a, this.b, bArr, qaVar.c, i);
            qaVar.c += i;
            this.b += i;
            return;
        }
        throw new IllegalArgumentException();
    }

    public qa(byte[] bArr, int i, int i2, boolean z, boolean z2) {
        this.a = bArr;
        this.b = i;
        this.c = i2;
        this.d = z;
        this.e = z2;
    }
}
