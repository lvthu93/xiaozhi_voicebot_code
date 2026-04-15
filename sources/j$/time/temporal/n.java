package j$.time.temporal;

public final /* synthetic */ class n implements m {
    public final /* synthetic */ int a;
    public final /* synthetic */ int b;

    public /* synthetic */ n(int i, int i2) {
        this.a = i2;
        this.b = i;
    }

    public final l p(l lVar) {
        int i = this.a;
        int i2 = this.b;
        switch (i) {
            case 0:
                int k = lVar.k(a.DAY_OF_WEEK);
                if (k == i2) {
                    return lVar;
                }
                int i3 = k - i2;
                return lVar.d((long) (i3 >= 0 ? 7 - i3 : -i3), ChronoUnit.DAYS);
            default:
                int k2 = lVar.k(a.DAY_OF_WEEK);
                if (k2 == i2) {
                    return lVar;
                }
                int i4 = i2 - k2;
                return lVar.g((long) (i4 >= 0 ? 7 - i4 : -i4), ChronoUnit.DAYS);
        }
    }
}
