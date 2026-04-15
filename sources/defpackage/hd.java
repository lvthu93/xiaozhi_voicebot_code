package defpackage;

import com.google.android.exoplayer2.util.Util;
import java.util.concurrent.ThreadFactory;

/* renamed from: hd  reason: default package */
public final /* synthetic */ class hd implements ThreadFactory {
    public final /* synthetic */ String a;

    public /* synthetic */ hd(String str) {
        this.a = str;
    }

    public final Thread newThread(Runnable runnable) {
        int i = Util.a;
        return new Thread(runnable, this.a);
    }
}
