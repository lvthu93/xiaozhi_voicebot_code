package defpackage;

import android.content.Context;
import info.dourok.voicebot.java.data.model.DeviceInfo;
import info.dourok.voicebot.java.data.model.a;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* renamed from: k1  reason: default package */
public final class k1 {
    public static final String[] d = {"android.permission.ACCESS_WIFI_STATE", "android.permission.ACCESS_NETWORK_STATE", "android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"};
    public final Context a;
    public final c2 b;
    public final ExecutorService c = Executors.newSingleThreadExecutor();

    public k1(Context context) {
        this.a = context.getApplicationContext();
        this.b = new c2(context);
    }

    public final DeviceInfo a() {
        try {
            return this.b.c();
        } catch (Exception unused) {
            if (a.a != null) {
                Random random = new Random();
                DeviceInfo.ChipInfo chipInfo = new DeviceInfo.ChipInfo();
                chipInfo.g = 3;
                chipInfo.c = 2;
                chipInfo.h = 1;
                chipInfo.f = 5;
                DeviceInfo.Application application = new DeviceInfo.Application();
                application.i = "sensor-hub";
                application.j = "1.3.0";
                application.f = "2025-02-28T12:34:56Z";
                application.h = "5.1-beta";
                StringBuilder sb = new StringBuilder();
                Random random2 = new Random();
                for (int i = 0; i < 64; i++) {
                    sb.append("0123456789abcdef".charAt(random2.nextInt(16)));
                }
                application.g = sb.toString();
                ArrayList arrayList = new ArrayList();
                DeviceInfo.Partition partition = new DeviceInfo.Partition();
                partition.g = "app";
                partition.k = 1;
                partition.i = 2;
                partition.c = 65536;
                partition.h = 2097152;
                arrayList.add(partition);
                DeviceInfo.Partition partition2 = new DeviceInfo.Partition();
                partition2.g = "nvs";
                partition2.k = 1;
                partition2.i = 1;
                partition2.c = 32768;
                partition2.h = 65536;
                arrayList.add(partition2);
                DeviceInfo.Partition partition3 = new DeviceInfo.Partition();
                partition3.g = "phy_init";
                partition3.k = 1;
                partition3.i = 3;
                partition3.c = 98304;
                partition3.h = 8192;
                arrayList.add(partition3);
                DeviceInfo.Ota ota = new DeviceInfo.Ota();
                ota.f = "ota_1";
                DeviceInfo.Board board = new DeviceInfo.Board();
                board.l = "ESP32S3-DevKitM-1";
                board.m = "v1.2";
                ArrayList arrayList2 = new ArrayList();
                arrayList2.add("WiFi");
                arrayList2.add("Bluetooth");
                arrayList2.add("USB-OTG");
                arrayList2.add("LCD");
                board.f = arrayList2;
                board.j = "Espressif";
                board.o = "ESP32S3-" + (random.nextInt(8999) + 1000);
                DeviceInfo deviceInfo = new DeviceInfo();
                deviceInfo.r = 2;
                deviceInfo.j = 8388608;
                deviceInfo.p = 4194304;
                deviceInfo.m = String.valueOf(random.nextInt(100000) + 200000);
                deviceInfo.l = a.b;
                deviceInfo.q = UUID.randomUUID().toString();
                deviceInfo.h = "esp32s3";
                deviceInfo.g = chipInfo;
                deviceInfo.c = application;
                deviceInfo.o = arrayList;
                deviceInfo.n = ota;
                deviceInfo.f = board;
                return deviceInfo;
            }
            throw new RuntimeException("DummyDataGenerator not initialized. Call initialize() first.");
        }
    }
}
