package info.dourok.voicebot.java.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;

public class ServerFormData implements Parcelable {
    public static final Parcelable.Creator<ServerFormData> CREATOR = new a();
    public SelfHostConfig c;
    public int f;
    public XiaoZhiConfig g;

    public class a implements Parcelable.Creator<ServerFormData> {
        public final Object createFromParcel(Parcel parcel) {
            return new ServerFormData(parcel);
        }

        public final Object[] newArray(int i) {
            return new ServerFormData[i];
        }
    }

    static {
        Pattern.compile("^wss?://[\\w.-]+(?::\\d+)?(?:/.*)?$");
        Pattern.compile("^https?://[\\w.-]+(?::\\d+)?(?:/.*)?$");
    }

    public ServerFormData() {
        this.f = 1;
        this.g = new XiaoZhiConfig();
        this.c = new SelfHostConfig();
    }

    public static ServerFormData a(JSONObject jSONObject) throws JSONException {
        ServerFormData serverFormData = new ServerFormData();
        if (jSONObject.has("serverType")) {
            serverFormData.f = y2.ah(jSONObject.getString("serverType"));
        }
        if (jSONObject.has("xiaoZhiConfig")) {
            JSONObject jSONObject2 = jSONObject.getJSONObject("xiaoZhiConfig");
            XiaoZhiConfig xiaoZhiConfig = new XiaoZhiConfig();
            if (jSONObject2.has("webSocketUrl")) {
                xiaoZhiConfig.g = jSONObject2.getString("webSocketUrl");
            }
            if (jSONObject2.has("otaUrl")) {
                xiaoZhiConfig.c = jSONObject2.getString("otaUrl");
            }
            if (jSONObject2.has("transportType")) {
                xiaoZhiConfig.f = y2.ai(jSONObject2.getString("transportType"));
            }
            serverFormData.g = xiaoZhiConfig;
        }
        if (jSONObject.has("selfHostConfig")) {
            JSONObject jSONObject3 = jSONObject.getJSONObject("selfHostConfig");
            SelfHostConfig selfHostConfig = new SelfHostConfig();
            if (jSONObject3.has("webSocketUrl")) {
                selfHostConfig.f = jSONObject3.getString("webSocketUrl");
            }
            serverFormData.c = selfHostConfig;
        }
        return serverFormData;
    }

    public final int describeContents() {
        return 0;
    }

    public final String toString() {
        return "ServerFormData{serverType=" + y2.af(this.f) + ", xiaoZhiConfig=" + this.g + ", selfHostConfig=" + this.c + '}';
    }

    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(y2.ac(this.f));
        parcel.writeParcelable(this.g, i);
        parcel.writeParcelable(this.c, i);
    }

    public static class SelfHostConfig implements Parcelable {
        public static final Parcelable.Creator<SelfHostConfig> CREATOR = new a();
        public final int c;
        public String f;

        public class a implements Parcelable.Creator<SelfHostConfig> {
            public final Object createFromParcel(Parcel parcel) {
                return new SelfHostConfig(parcel);
            }

            public final Object[] newArray(int i) {
                return new SelfHostConfig[i];
            }
        }

        public SelfHostConfig() {
            this.f = "ws://192.168.1.246:8000";
            this.c = 2;
        }

        public final int describeContents() {
            return 0;
        }

        public final String toString() {
            return "SelfHostConfig{webSocketUrl='" + this.f + "', transportType=" + y2.ag(this.c) + '}';
        }

        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.f);
            parcel.writeString(y2.ad(this.c));
        }

        public SelfHostConfig(Parcel parcel) {
            this.f = parcel.readString();
            this.c = y2.ai(parcel.readString());
        }
    }

    public static class XiaoZhiConfig implements Parcelable {
        public static final Parcelable.Creator<XiaoZhiConfig> CREATOR = new a();
        public String c;
        public int f;
        public String g;

        public class a implements Parcelable.Creator<XiaoZhiConfig> {
            public final Object createFromParcel(Parcel parcel) {
                return new XiaoZhiConfig(parcel);
            }

            public final Object[] newArray(int i) {
                return new XiaoZhiConfig[i];
            }
        }

        public XiaoZhiConfig() {
            this.g = "wss://api.tenclass.net/xiaozhi/v1/";
            this.c = "https://api.tenclass.net/xiaozhi/ota/";
            this.f = 1;
        }

        public final int describeContents() {
            return 0;
        }

        public final String toString() {
            return "XiaoZhiConfig{webSocketUrl='" + this.g + "', otaUrl='" + this.c + "', transportType=" + y2.ag(this.f) + '}';
        }

        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.g);
            parcel.writeString(this.c);
            parcel.writeString(y2.ad(this.f));
        }

        public XiaoZhiConfig(Parcel parcel) {
            this.g = parcel.readString();
            this.c = parcel.readString();
            this.f = y2.ai(parcel.readString());
        }
    }

    public ServerFormData(Parcel parcel) {
        this.f = y2.ah(parcel.readString());
        this.g = (XiaoZhiConfig) parcel.readParcelable(XiaoZhiConfig.class.getClassLoader());
        this.c = (SelfHostConfig) parcel.readParcelable(SelfHostConfig.class.getClassLoader());
    }
}
