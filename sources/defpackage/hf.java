package defpackage;

/* renamed from: hf  reason: default package */
public enum hf {
    ENUM(Cif.m, 0);
    
    public final Cif c;

    /* 'enum' modifier removed */
    /* renamed from: hf$a */
    public final class a extends hf {
        public a() {
            super("STRING", 8, Cif.k, 2);
        }
    }

    /* 'enum' modifier removed */
    /* renamed from: hf$b */
    public final class b extends hf {
        public b(Cif ifVar) {
            super("GROUP", 9, ifVar, 3);
        }
    }

    /* 'enum' modifier removed */
    /* renamed from: hf$c */
    public final class c extends hf {
        public c(Cif ifVar) {
            super("MESSAGE", 10, ifVar, 2);
        }
    }

    /* 'enum' modifier removed */
    /* renamed from: hf$d */
    public final class d extends hf {
        public d(Cif ifVar) {
            super("BYTES", 11, ifVar, 2);
        }
    }

    /* access modifiers changed from: public */
    hf(Cif ifVar, int i2) {
        this.c = ifVar;
    }
}
