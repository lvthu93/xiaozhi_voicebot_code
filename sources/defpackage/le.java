package defpackage;

import android.os.Handler;
import defpackage.ye;
import info.dourok.voicebot.java.data.model.DeviceInfo;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import okhttp3.Headers;
import okhttp3.Request;

/* renamed from: le  reason: default package */
public final class le implements Runnable {
    public final /* synthetic */ d0 c;
    public final /* synthetic */ ye f;

    public le(ye yeVar, cg cgVar) {
        this.f = yeVar;
        this.c = cgVar;
    }

    public final void run() {
        d0 d0Var = this.c;
        ye yeVar = this.f;
        Handler handler = yeVar.l;
        try {
            String str = yeVar.m;
            if (str == null || str.isEmpty()) {
                if (d0Var != null) {
                    handler.post(new qe(d0Var));
                }
            } else if (yeVar.i != null) {
                yeVar.a();
                DeviceInfo deviceInfo = yeVar.i;
                String str2 = deviceInfo.l;
                String str3 = deviceInfo.q;
                if (str2 == null || str2.isEmpty()) {
                    str2 = "unknown-device";
                }
                if (str3 == null || str3.isEmpty()) {
                    str3 = "unknown-client-" + System.currentTimeMillis();
                }
                Request.Builder url = new Request.Builder().url(yeVar.m);
                StringBuilder sb = new StringBuilder("Bearer ");
                String str4 = yeVar.g;
                if (str4 == null) {
                    str4 = "";
                }
                sb.append(str4);
                Request build = url.addHeader("Authorization", sb.toString()).addHeader("Protocol-Version", "1").addHeader("Device-Id", str2).addHeader("Client-Id", str3).build();
                Headers headers = build.headers();
                for (int i = 0; i < headers.size(); i++) {
                    headers.name(i);
                    headers.value(i);
                }
                yeVar.j = new CountDownLatch(1);
                yeVar.n = yeVar.h.newWebSocket(build, new ye.b());
                try {
                    if (!yeVar.j.await(10, TimeUnit.SECONDS)) {
                        yeVar.e("Server timeout");
                        yeVar.a();
                        if (d0Var != null) {
                            handler.post(new oe(d0Var));
                        }
                    } else if (d0Var != null) {
                        handler.post(new pe(d0Var));
                    }
                } catch (InterruptedException e) {
                    yeVar.e("Connection interrupted");
                    yeVar.a();
                    if (d0Var != null) {
                        handler.post(new ne(d0Var, e));
                    }
                }
            } else if (d0Var != null) {
                handler.post(new me(d0Var));
            }
        } catch (Exception e2) {
            yeVar.a();
            if (d0Var != null) {
                handler.post(new re(d0Var, e2));
            }
        }
    }
}
