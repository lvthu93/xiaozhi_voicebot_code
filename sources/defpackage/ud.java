package defpackage;

import info.dourok.voicebot.java.VoiceBotApplication;

/* renamed from: ud  reason: default package */
public final /* synthetic */ class ud implements Runnable {
    public final /* synthetic */ int c;
    public final /* synthetic */ VoiceBotApplication f;

    public /* synthetic */ ud(VoiceBotApplication voiceBotApplication, int i) {
        this.c = i;
        this.f = voiceBotApplication;
    }

    public final void run() {
        int i = this.c;
        VoiceBotApplication voiceBotApplication = this.f;
        switch (i) {
            case 0:
                voiceBotApplication.lambda$startChatQueueWorkerIfNeeded$4();
                return;
            case 1:
                voiceBotApplication.lambda$checkVersionAndDownload$1();
                return;
            case 2:
                voiceBotApplication.lambda$startZaloMqtt$3();
                return;
            default:
                voiceBotApplication.lambda$startAiboxPlusWebSocketServer$0();
                return;
        }
    }
}
