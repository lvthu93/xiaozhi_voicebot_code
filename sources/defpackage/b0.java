package defpackage;

import info.dourok.voicebot.java.data.model.DeviceInfo;

/* renamed from: b0  reason: default package */
public interface b0 {
    void onDeviceInfoReady(DeviceInfo deviceInfo);

    void onError(String str);

    void onPermissionRequired(String str);
}
