package defpackage;

/* renamed from: c7  reason: default package */
public final class c7 {
    public final String a;
    public final String b;
    public final String c;
    public final String d;
    public final String e;
    public final String f;

    public c7() {
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("MqttConfig{endpoint='");
        sb.append(this.b);
        sb.append("', clientId='");
        sb.append(this.a);
        sb.append("', username='");
        sb.append(this.f);
        sb.append("', publishTopic='");
        sb.append(this.d);
        sb.append("', subscribeTopic='");
        return y2.k(sb, this.e, "'}");
    }

    public c7(String str, String str2, String str3, String str4, String str5, String str6) {
        this.b = str;
        this.a = str2;
        this.f = str3;
        this.c = str4;
        this.d = str5;
        this.e = str6;
    }
}
