package defpackage;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.exoplayer2.util.Log;
import j$.util.Objects;
import j$.util.stream.Stream;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.java_websocket.WebSocket;

/* renamed from: y2  reason: default package */
public final /* synthetic */ class y2 {
    public static /* synthetic */ boolean a(int i, int i2) {
        if (i != 0) {
            return i == i2;
        }
        throw null;
    }

    public static Stream aa(Class cls, int i, Stream stream) {
        return stream.filter(new uf(cls, i));
    }

    public static Stream ab(Class cls, int i, Stream stream) {
        return stream.map(new vf(cls, i));
    }

    public static /* synthetic */ String ac(int i) {
        if (i == 1) {
            return "XIAO_ZHI";
        }
        if (i == 2) {
            return "SELF_HOST";
        }
        throw null;
    }

    public static /* synthetic */ String ad(int i) {
        if (i == 1) {
            return "MQTT";
        }
        if (i == 2) {
            return "WEBSOCKET";
        }
        throw null;
    }

    public static /* synthetic */ int ae(int i) {
        if (i != 0) {
            return i - 1;
        }
        throw null;
    }

    public static /* synthetic */ String af(int i) {
        return i == 1 ? "XIAO_ZHI" : i == 2 ? "SELF_HOST" : "null";
    }

    public static /* synthetic */ String ag(int i) {
        return i == 1 ? "MQTT" : i == 2 ? "WEBSOCKET" : "null";
    }

    public static /* synthetic */ int ah(String str) {
        if (str == null) {
            throw new NullPointerException("Name is null");
        } else if (str.equals("XIAO_ZHI")) {
            return 1;
        } else {
            if (str.equals("SELF_HOST")) {
                return 2;
            }
            throw new IllegalArgumentException("No enum constant info.dourok.voicebot.java.data.model.ServerFormData.ServerType.".concat(str));
        }
    }

    public static /* synthetic */ int ai(String str) {
        if (str == null) {
            throw new NullPointerException("Name is null");
        } else if (str.equals("MQTT")) {
            return 1;
        } else {
            if (str.equals("WEBSOCKET")) {
                return 2;
            }
            throw new IllegalArgumentException("No enum constant info.dourok.voicebot.java.data.model.ServerFormData.TransportType.".concat(str));
        }
    }

    public static int b(int i, int i2, int i3, int i4, int i5) {
        return Math.max(((i * i2) / i3) + i4, i5);
    }

    public static int c(String str, int i) {
        return String.valueOf(str).length() + i;
    }

    public static String d(int i, String str, int i2) {
        StringBuilder sb = new StringBuilder(i);
        sb.append(str);
        sb.append(i2);
        return sb.toString();
    }

    public static String e(RecyclerView recyclerView, StringBuilder sb) {
        sb.append(recyclerView.exceptionLabel());
        return sb.toString();
    }

    public static String f(String str, int i) {
        return str + i;
    }

    public static String g(String str, long j) {
        return str + j;
    }

    public static String h(String str, Fragment fragment, String str2) {
        return str + fragment + str2;
    }

    public static String i(String str, String str2) {
        return str + str2;
    }

    public static String j(String str, String str2, String str3) {
        return str + str2 + str3;
    }

    public static String k(StringBuilder sb, String str, String str2) {
        sb.append(str);
        sb.append(str2);
        return sb.toString();
    }

    public static StringBuilder l(int i, String str, String str2, String str3, String str4) {
        StringBuilder sb = new StringBuilder(i);
        sb.append(str);
        sb.append(str2);
        sb.append(str3);
        sb.append(str4);
        return sb;
    }

    public static StringBuilder m(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        return sb;
    }

    public static StringBuilder n(String str, int i, String str2) {
        StringBuilder sb = new StringBuilder(str);
        sb.append(i);
        sb.append(str2);
        return sb;
    }

    public static StringBuilder o(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(str2);
        return sb;
    }

    public static List p(Object obj, ArrayList arrayList, Object obj2, ArrayList arrayList2) {
        Objects.requireNonNull(obj);
        arrayList.add(obj2);
        return Collections.unmodifiableList(arrayList2);
    }

    public static /* synthetic */ List q(String str) {
        Object[] objArr = {str};
        ArrayList arrayList = new ArrayList(1);
        Object obj = objArr[0];
        return p(obj, arrayList, obj, arrayList);
    }

    public static /* synthetic */ Map r(List list) {
        Map.Entry[] entryArr = {new AbstractMap.SimpleEntry("Range", list)};
        HashMap hashMap = new HashMap(1);
        Map.Entry entry = entryArr[0];
        Object key = entry.getKey();
        Objects.requireNonNull(key);
        Object value = entry.getValue();
        Objects.requireNonNull(value);
        if (hashMap.put(key, value) == null) {
            return Collections.unmodifiableMap(hashMap);
        }
        throw new IllegalArgumentException("duplicate key: " + key);
    }

    public static Stream s(Class cls, int i, Stream stream) {
        return stream.filter(new c4(cls, i));
    }

    public static void t(int i, String str, int i2, String str2) {
        StringBuilder sb = new StringBuilder(i);
        sb.append(str);
        sb.append(i2);
        Log.w(str2, sb.toString());
    }

    public static void u(int i, HashMap hashMap, String str, int i2, String str2, int i3, String str3, int i4, String str4) {
        hashMap.put(str, Integer.valueOf(i));
        hashMap.put(str2, Integer.valueOf(i2));
        hashMap.put(str3, Integer.valueOf(i3));
        hashMap.put(str4, Integer.valueOf(i4));
    }

    public static void v(Exception exc, StringBuilder sb, WebSocket webSocket) {
        sb.append(exc.getMessage());
        x.bd(webSocket, sb.toString());
    }

    public static void w(String str, Fragment fragment, String str2) {
        android.util.Log.v(str2, str + fragment);
    }

    public static String x(String str, String str2) {
        return str + str2;
    }

    public static /* synthetic */ Map y(List list) {
        Map.Entry[] entryArr = {new AbstractMap.SimpleEntry("User-Agent", list)};
        HashMap hashMap = new HashMap(1);
        Map.Entry entry = entryArr[0];
        Object key = entry.getKey();
        Objects.requireNonNull(key);
        Object value = entry.getValue();
        Objects.requireNonNull(value);
        if (hashMap.put(key, value) == null) {
            return Collections.unmodifiableMap(hashMap);
        }
        throw new IllegalArgumentException("duplicate key: " + key);
    }

    public static Stream z(Class cls, int i, Stream stream) {
        return stream.map(new d4(cls, i));
    }
}
