package defpackage;

import info.dourok.voicebot.java.audio.MusicPlayer;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: g8  reason: default package */
public final class g8 implements MusicPlayer.m {
    public final /* synthetic */ x7 a;

    /* renamed from: g8$a */
    public class a implements Runnable {
        public a() {
        }

        public final void run() {
            g8.this.a.t(m1.IDLE);
        }
    }

    public g8(x7 x7Var) {
        this.a = x7Var;
    }

    public final void a() {
        this.a.r = false;
        if (this.a.k == m1.LISTENING || this.a.k == m1.SPEAKING) {
            m1 m1Var = this.a.k;
            this.a.q = false;
            x7 x7Var = this.a;
            x7Var.getClass();
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("session_id", x7Var.ag);
                jSONObject.put("type", "listen");
                jSONObject.put("state", "stop");
                x7Var.q(jSONObject);
            } catch (JSONException e) {
                e.getMessage();
            }
            this.a.v.post(new a());
        }
    }

    public final void b() {
        if (this.a.k == m1.IDLE) {
            this.a.v();
        }
    }
}
