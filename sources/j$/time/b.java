package j$.time;

public abstract class b {
    protected b() {
    }

    public static b c() {
        return new a(ZoneId.systemDefault());
    }

    public static b d() {
        return a.b;
    }

    public abstract ZoneId a();

    public abstract long b();
}
