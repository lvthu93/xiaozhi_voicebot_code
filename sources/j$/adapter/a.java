package j$.adapter;

public abstract class a {
    public static final boolean a;

    static {
        boolean z;
        try {
            Class.forName("java.util.StringJoiner");
            z = true;
        } catch (ClassNotFoundException unused) {
            z = false;
        }
        a = z;
        try {
            Class.forName("java.nio.file.FileSystems");
        } catch (ClassNotFoundException unused2) {
        }
        try {
            Class.forName("android.os.Build");
        } catch (ClassNotFoundException unused3) {
        }
    }
}
