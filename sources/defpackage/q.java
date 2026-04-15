package defpackage;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import defpackage.lf;
import info.dourok.voicebot.java.audio.MusicPlayer;
import info.dourok.voicebot.java.services.ZingMp3Service;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.java_websocket.WebSocket;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: q  reason: default package */
public final /* synthetic */ class q implements Runnable {
    public final /* synthetic */ int c;
    public final /* synthetic */ x f;
    public final /* synthetic */ String g;
    public final /* synthetic */ WebSocket h;
    public final /* synthetic */ boolean i;

    public /* synthetic */ q(x xVar, String str, WebSocket webSocket, boolean z, int i2) {
        this.c = i2;
        this.f = xVar;
        this.g = str;
        this.h = webSocket;
        this.i = z;
    }

    public final void run() {
        Context context;
        Context context2;
        String str;
        Context context3;
        int i2 = this.c;
        String str2 = "channel";
        String str3 = "video_id";
        String str4 = "";
        boolean z = this.i;
        String str5 = "title";
        x xVar = this.f;
        WebSocket webSocket = this.h;
        String str6 = this.g;
        switch (i2) {
            case 0:
                String str7 = str2;
                String str8 = str3;
                String str9 = str4;
                xVar.getClass();
                try {
                    ArrayList c2 = lf.c(str6);
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("type", "playlist_result");
                    jSONObject.put("success", true);
                    JSONArray jSONArray = new JSONArray();
                    Iterator it = c2.iterator();
                    while (it.hasNext()) {
                        lf.c cVar = (lf.c) it.next();
                        JSONObject jSONObject2 = new JSONObject();
                        String str10 = str8;
                        jSONObject2.put(str10, cVar.a);
                        jSONObject2.put(str5, cVar.b);
                        Iterator it2 = it;
                        String str11 = str7;
                        jSONObject2.put(str11, cVar.c);
                        boolean z2 = z;
                        String str12 = str5;
                        jSONObject2.put("duration_seconds", cVar.d);
                        String str13 = cVar.e;
                        if (str13 == null) {
                            str13 = str9;
                        }
                        jSONObject2.put("thumbnail_url", str13);
                        jSONArray.put(jSONObject2);
                        str5 = str12;
                        z = z2;
                        str7 = str11;
                        str8 = str10;
                        it = it2;
                    }
                    boolean z3 = z;
                    jSONObject.put("songs", jSONArray);
                    jSONObject.put("count", c2.size());
                    xVar.h = c2;
                    x.bb(webSocket, jSONObject.toString());
                    c2.size();
                    if (z3 && !c2.isEmpty()) {
                        lf.c cVar2 = (lf.c) c2.get(0);
                        String str14 = cVar2.a;
                        a5 a5Var = xVar.f;
                        if (a5Var != null) {
                            xVar.g = a5Var.f;
                        }
                        if (!(xVar.g != null || (context = xVar.c) == null || a5Var == null)) {
                            MusicPlayer musicPlayer = new MusicPlayer(context);
                            xVar.g = musicPlayer;
                            xVar.f.h(musicPlayer);
                        }
                        if (xVar.g != null) {
                            try {
                                lf.a a = lf.a(str14);
                                if (a.d) {
                                    new Handler(Looper.getMainLooper()).post(new d6(9, xVar, a, cVar2));
                                    return;
                                }
                                return;
                            } catch (Exception unused) {
                                return;
                            }
                        } else {
                            return;
                        }
                    } else {
                        return;
                    }
                } catch (Exception e) {
                    y2.v(e, new StringBuilder("Playlist search failed: "), webSocket);
                    return;
                }
            case 1:
                xVar.getClass();
                try {
                    ArrayList e2 = lf.e(str6);
                    JSONObject jSONObject3 = new JSONObject();
                    jSONObject3.put("type", "search_result");
                    jSONObject3.put("success", true);
                    JSONArray jSONArray2 = new JSONArray();
                    Iterator it3 = e2.iterator();
                    while (it3.hasNext()) {
                        lf.c cVar3 = (lf.c) it3.next();
                        String str15 = str4;
                        JSONObject jSONObject4 = new JSONObject();
                        Iterator it4 = it3;
                        jSONObject4.put(str3, cVar3.a);
                        jSONObject4.put(str5, cVar3.b);
                        jSONObject4.put(str2, cVar3.c);
                        String str16 = str2;
                        String str17 = str3;
                        jSONObject4.put("duration_seconds", cVar3.d);
                        String str18 = cVar3.e;
                        if (str18 == null) {
                            str18 = str15;
                        }
                        jSONObject4.put("thumbnail_url", str18);
                        jSONArray2.put(jSONObject4);
                        str4 = str15;
                        it3 = it4;
                        str2 = str16;
                        str3 = str17;
                    }
                    jSONObject3.put("songs", jSONArray2);
                    jSONObject3.put("count", e2.size());
                    xVar.h = e2;
                    x.bb(webSocket, jSONObject3.toString());
                    e2.size();
                    if (z && !e2.isEmpty()) {
                        lf.c cVar4 = (lf.c) e2.get(0);
                        String str19 = cVar4.a;
                        a5 a5Var2 = xVar.f;
                        if (a5Var2 != null) {
                            xVar.g = a5Var2.f;
                        }
                        if (!(xVar.g != null || (context2 = xVar.c) == null || a5Var2 == null)) {
                            MusicPlayer musicPlayer2 = new MusicPlayer(context2);
                            xVar.g = musicPlayer2;
                            xVar.f.h(musicPlayer2);
                        }
                        xVar.f();
                        if (xVar.g != null) {
                            try {
                                lf.a a2 = lf.a(str19);
                                if (a2.d) {
                                    xVar.k = cVar4;
                                    new Handler(Looper.getMainLooper()).post(new h2(16, xVar, a2));
                                    return;
                                }
                                return;
                            } catch (Exception unused2) {
                                return;
                            }
                        } else {
                            return;
                        }
                    } else {
                        return;
                    }
                } catch (Exception e3) {
                    y2.v(e3, new StringBuilder("Search failed: "), webSocket);
                    return;
                }
            default:
                String str20 = str4;
                boolean z4 = z;
                String str21 = str5;
                xVar.getClass();
                try {
                    ZingMp3Service zingMp3Service = new ZingMp3Service();
                    List<ZingMp3Service.ZingMp3Song> searchSongs = zingMp3Service.searchSongs(str6);
                    JSONObject jSONObject5 = new JSONObject();
                    jSONObject5.put("type", "zing_result");
                    jSONObject5.put("success", true);
                    JSONArray jSONArray3 = new JSONArray();
                    for (ZingMp3Service.ZingMp3Song next : searchSongs) {
                        JSONObject jSONObject6 = new JSONObject();
                        jSONObject6.put("song_id", next.id);
                        String str22 = next.title;
                        if (str22 == null) {
                            str22 = str20;
                        }
                        String str23 = str21;
                        jSONObject6.put(str23, str22);
                        String str24 = next.artistName;
                        if (str24 == null) {
                            str24 = str20;
                        }
                        jSONObject6.put("artist", str24);
                        jSONObject6.put("duration_seconds", next.duration);
                        String str25 = next.thumb;
                        if (str25 == null) {
                            str25 = str20;
                        }
                        jSONObject6.put("thumbnail_url", str25);
                        jSONArray3.put(jSONObject6);
                        str21 = str23;
                    }
                    jSONObject5.put("songs", jSONArray3);
                    jSONObject5.put("count", searchSongs.size());
                    xVar.i = searchSongs;
                    x.bb(webSocket, jSONObject5.toString());
                    searchSongs.size();
                    if (z4 && !searchSongs.isEmpty()) {
                        ZingMp3Service.ZingMp3Song zingMp3Song = searchSongs.get(0);
                        String str26 = zingMp3Song.id;
                        a5 a5Var3 = xVar.f;
                        if (a5Var3 != null) {
                            xVar.g = a5Var3.f;
                        }
                        if (!(xVar.g != null || (context3 = xVar.c) == null || a5Var3 == null)) {
                            MusicPlayer musicPlayer3 = new MusicPlayer(context3);
                            xVar.g = musicPlayer3;
                            MusicPlayer.m mVar = xVar.f.i;
                            if (mVar != null) {
                                musicPlayer3.setStateController(mVar);
                            }
                            xVar.f.h(xVar.g);
                        }
                        xVar.f();
                        if (xVar.g != null) {
                            try {
                                String streamLink = zingMp3Service.getStreamLink(str26);
                                if (streamLink != null && !streamLink.isEmpty()) {
                                    String str27 = zingMp3Song.title;
                                    if (str27 == null) {
                                        str27 = str20;
                                    }
                                    String str28 = zingMp3Song.artistName;
                                    if (str28 == null) {
                                        str28 = str20;
                                    }
                                    xVar.l = str27;
                                    xVar.m = str28;
                                    String str29 = zingMp3Song.thumb;
                                    if (str29 != null) {
                                        str = str29;
                                    } else {
                                        str = str20;
                                    }
                                    xVar.n = str;
                                    xVar.o = str26;
                                    xVar.k = null;
                                    new Handler(Looper.getMainLooper()).post(new i6(xVar, streamLink, str27, str28, 1));
                                    return;
                                }
                                return;
                            } catch (Exception unused3) {
                                return;
                            }
                        } else {
                            return;
                        }
                    } else {
                        return;
                    }
                } catch (Exception e4) {
                    y2.v(e4, new StringBuilder("Zing MP3 search failed: "), webSocket);
                    return;
                }
        }
    }
}
