package defpackage;

import defpackage.zd;
import java.lang.reflect.Method;

/* renamed from: yd  reason: default package */
public final class yd implements Runnable {
    public final /* synthetic */ String c;
    public final /* synthetic */ zd.a f;

    /* renamed from: yd$a */
    public class a implements Runnable {
        public final /* synthetic */ String c;

        public a(String str) {
            this.c = str;
        }

        public final void run() {
            yd ydVar = yd.this;
            String str = this.c;
            if (str == null || str.isEmpty()) {
                ((w9) ydVar.f).a();
                return;
            }
            Object obj = ((w9) ydVar.f).a;
            try {
                Method declaredMethod = obj.getClass().getDeclaredMethod("startStreamingInternal", new Class[]{String.class});
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(obj, new Object[]{str});
            } catch (Exception unused) {
            }
        }
    }

    /* renamed from: yd$b */
    public class b implements Runnable {
        public final /* synthetic */ Exception c;

        public b(Exception exc) {
            this.c = exc;
        }

        public final void run() {
            zd.a aVar = yd.this.f;
            this.c.getMessage();
            ((w9) aVar).a();
        }
    }

    public yd(String str, w9 w9Var) {
        this.c = str;
        this.f = w9Var;
    }

    public final void run() {
        try {
            zd.a.post(new a(de.a(this.c)));
        } catch (Exception e) {
            zd.a.post(new b(e));
        }
    }
}
