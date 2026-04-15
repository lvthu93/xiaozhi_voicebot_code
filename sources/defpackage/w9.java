package defpackage;

import defpackage.zd;
import java.lang.reflect.Method;

/* renamed from: w9  reason: default package */
public final class w9 implements zd.a {
    public final /* synthetic */ Object a;
    public final /* synthetic */ String b;

    public w9(Object obj, String str) {
        this.a = obj;
        this.b = str;
    }

    public final void a() {
        String str;
        Object obj = this.a;
        String upperCase = this.b.toUpperCase();
        if (!upperCase.equals("VOV1")) {
            if (upperCase.equals("VOV2")) {
                str = "http://stream.vovmedia.vn/vov2";
            } else if (upperCase.equals("VOV3")) {
                str = "http://stream.vovmedia.vn/vov3";
            } else if (upperCase.equals("VOV4")) {
                str = "http://stream.vovmedia.vn/vov4";
            } else if (upperCase.equals("VOV5")) {
                str = "http://stream.vovmedia.vn/vov5";
            } else if (upperCase.equals("VOV_GT_HN")) {
                str = "http://stream.vovmedia.vn/vovgt-hn";
            } else if (upperCase.equals("VOV_GT_HCM")) {
                str = "http://stream.vovmedia.vn/vovgt-hcm";
            }
            Method declaredMethod = obj.getClass().getDeclaredMethod("startStreamingInternal", new Class[]{String.class});
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(obj, new Object[]{str});
        }
        str = "http://stream.vovmedia.vn/vov1";
        try {
            Method declaredMethod2 = obj.getClass().getDeclaredMethod("startStreamingInternal", new Class[]{String.class});
            declaredMethod2.setAccessible(true);
            declaredMethod2.invoke(obj, new Object[]{str});
        } catch (Exception unused) {
        }
    }
}
