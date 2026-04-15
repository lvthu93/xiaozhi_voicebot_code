package defpackage;

import android.os.Build;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.eclipse.paho.client.mqttv3.internal.security.SSLSocketFactoryFactory;

/* renamed from: oa  reason: default package */
public final class oa {
    public static OkHttpClient a;

    /* renamed from: oa$a */
    public class a implements HostnameVerifier {
        public final boolean verify(String str, SSLSession sSLSession) {
            return true;
        }
    }

    public static OkHttpClient a() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        TimeUnit timeUnit = TimeUnit.SECONDS;
        OkHttpClient.Builder retryOnConnectionFailure = builder.connectTimeout(15, timeUnit).readTimeout(30, timeUnit).writeTimeout(30, timeUnit).retryOnConnectionFailure(true);
        if (Build.VERSION.SDK_INT < 24) {
            try {
                na naVar = new na();
                SSLContext instance = SSLContext.getInstance(SSLSocketFactoryFactory.DEFAULT_PROTOCOL);
                instance.init((KeyManager[]) null, new TrustManager[]{naVar}, new SecureRandom());
                retryOnConnectionFailure.sslSocketFactory(instance.getSocketFactory(), naVar);
                retryOnConnectionFailure.hostnameVerifier(new a());
            } catch (Exception e) {
                e.getMessage();
            }
        }
        return retryOnConnectionFailure.build();
    }

    public static String b(String str) {
        try {
            Request.Builder addHeader = new Request.Builder().url(str).addHeader("Accept", "application/json, text/plain, */*");
            addHeader.addHeader("User-Agent", "VoiceBot-Android/4.0.0");
            Response execute = c().newCall(addHeader.build()).execute();
            if (execute == null || !execute.isSuccessful()) {
                if (execute != null) {
                    execute.code();
                    execute.close();
                }
                return null;
            } else if (execute.body() != null) {
                try {
                    String string = execute.body().string();
                    execute.close();
                    return string;
                } catch (IOException unused) {
                    execute.close();
                    return null;
                }
            } else {
                execute.close();
                return null;
            }
        } catch (Exception unused2) {
        }
    }

    public static synchronized OkHttpClient c() {
        OkHttpClient okHttpClient;
        synchronized (oa.class) {
            synchronized (oa.class) {
                if (a == null) {
                    a = a();
                }
                okHttpClient = a;
            }
        }
        return okHttpClient;
    }
}
