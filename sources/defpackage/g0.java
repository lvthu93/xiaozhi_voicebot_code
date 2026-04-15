package defpackage;

import info.dourok.voicebot.java.activities.ChatActivity;

/* renamed from: g0  reason: default package */
public final /* synthetic */ class g0 implements Runnable {
    public final /* synthetic */ int c;
    public final /* synthetic */ ChatActivity f;

    public /* synthetic */ g0(ChatActivity chatActivity, int i) {
        this.c = i;
        this.f = chatActivity;
    }

    public final void run() {
        int i = this.c;
        ChatActivity chatActivity = this.f;
        switch (i) {
            case 0:
                chatActivity.lambda$registerChatBroadcastReceiver$0();
                return;
            default:
                chatActivity.lambda$toggleBluetoothAndPlaySound$1();
                return;
        }
    }
}
