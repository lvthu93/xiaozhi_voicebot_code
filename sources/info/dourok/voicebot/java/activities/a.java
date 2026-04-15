package info.dourok.voicebot.java.activities;

import info.dourok.voicebot.java.activities.ChatActivity;

public final /* synthetic */ class a implements Runnable {
    public final /* synthetic */ int c;
    public final /* synthetic */ ChatActivity.AnonymousClass2 f;

    public /* synthetic */ a(ChatActivity.AnonymousClass2 r1, int i) {
        this.c = i;
        this.f = r1;
    }

    public final void run() {
        int i = this.c;
        ChatActivity.AnonymousClass2 r1 = this.f;
        switch (i) {
            case 0:
                r1.lambda$onReceive$0();
                return;
            default:
                r1.lambda$onReceive$1();
                return;
        }
    }
}
