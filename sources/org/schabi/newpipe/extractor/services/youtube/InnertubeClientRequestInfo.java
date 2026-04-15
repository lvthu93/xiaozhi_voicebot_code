package org.schabi.newpipe.extractor.services.youtube;

public final class InnertubeClientRequestInfo {
    public final ClientInfo a;
    public final DeviceInfo b;

    public static final class ClientInfo {
        public final String a;
        public String b;
        public final String c;
        public final String d;
        public String e = null;

        public ClientInfo(String str, String str2, String str3, String str4) {
            this.a = str;
            this.b = str2;
            this.c = str3;
            this.d = str4;
        }
    }

    public static final class DeviceInfo {
        public final String a;
        public final String b;
        public final String c;
        public final String d;
        public final String e;
        public final int f;

        public DeviceInfo(String str, String str2, String str3, String str4, String str5, int i) {
            this.a = str;
            this.b = str2;
            this.c = str3;
            this.d = str4;
            this.e = str5;
            this.f = i;
        }
    }

    public InnertubeClientRequestInfo(ClientInfo clientInfo, DeviceInfo deviceInfo) {
        this.a = clientInfo;
        this.b = deviceInfo;
    }

    public static InnertubeClientRequestInfo ofAndroidClient() {
        return new InnertubeClientRequestInfo(new ClientInfo("ANDROID", "19.28.35", "3", "WATCH"), new DeviceInfo("MOBILE", (String) null, (String) null, "Android", "15", 35));
    }

    public static InnertubeClientRequestInfo ofIosClient() {
        return new InnertubeClientRequestInfo(new ClientInfo("IOS", "20.03.02", "5", "WATCH"), new DeviceInfo("MOBILE", "Apple", "iPhone16,2", "iOS", "18.2.1.22C161", -1));
    }

    public static InnertubeClientRequestInfo ofWebClient() {
        return new InnertubeClientRequestInfo(new ClientInfo("WEB", "2.20250122.04.00", "1", "WATCH"), new DeviceInfo("DESKTOP", (String) null, (String) null, (String) null, (String) null, -1));
    }

    public static InnertubeClientRequestInfo ofWebEmbeddedPlayerClient() {
        return new InnertubeClientRequestInfo(new ClientInfo("WEB_EMBEDDED_PLAYER", "1.20250121.00.00", "56", "EMBED"), new DeviceInfo("DESKTOP", (String) null, (String) null, (String) null, (String) null, -1));
    }

    public static InnertubeClientRequestInfo ofWebMusicAnalyticsChartsClient() {
        return new InnertubeClientRequestInfo(new ClientInfo("WEB_MUSIC_ANALYTICS", "2.0", "31", (String) null), new DeviceInfo((String) null, (String) null, (String) null, (String) null, (String) null, -1));
    }
}
