package info.dourok.voicebot.java.audio;

import android.os.Handler;
import android.os.Looper;
import info.dourok.voicebot.java.audio.MusicPlayer;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public final class j {
    public MusicPlayer.m a;
    public final AtomicBoolean b = new AtomicBoolean(false);
    public final AtomicBoolean c = new AtomicBoolean(false);
    public final HashMap d;
    public final Handler e;

    public class a implements Runnable {
        public a() {
        }

        public final void run() {
            j.this.d();
        }
    }

    public static class b {
        public final String a;
        public final String b;
        public final String c;

        public b(String str, String str2, String str3) {
            this.c = str;
            this.b = str2;
            this.a = str3;
        }
    }

    public j() {
        HashMap hashMap = new HashMap();
        this.d = hashMap;
        this.e = new Handler(Looper.getMainLooper());
        hashMap.put("VOV1", new b("VOV 1 - Đài Tiếng nói Việt Nam", "Kênh thông tin tổng hợp", "News/Talk"));
        hashMap.put("VOV2", new b("VOV 2 - Âm thanh Việt Nam", "Kênh văn hóa - văn nghệ", "Culture/Music"));
        hashMap.put("VOV3", new b("VOV 3 - Tiếng nói Việt Nam", "Kênh thông tin - giải trí", "Entertainment"));
        hashMap.put("VOV5", new b("VOV 5 - Tiếng nói người Việt", "Kênh dành cho người Việt ở nước ngoài", "Overseas Vietnamese"));
        hashMap.put("VOVGT", new b("VOV Giao thông Hà Nội", "Thông tin giao thông Hà Nội", "Traffic"));
        hashMap.put("VOVGT_HCM", new b("VOV Giao thông Hồ Chí Minh", "Thông tin giao thông TP. Hồ Chí Minh", "Traffic"));
        hashMap.put("VOV_ENGLISH", new b("VOV English Tiếng Anh", "VOV English Service", "International"));
        hashMap.put("VOV_MEKONG", new b("VOV Mê Kông mekong", "Kênh vùng Đồng bằng sông Cửu Long", "Regional"));
        hashMap.put("VOV_MIENTRUNG", new b("VOV Miền Trung", "Kênh vùng miền Trung", "Regional"));
        hashMap.put("VOV_TAYBAC", new b("VOV Tây Bắc", "Kênh vùng Tây Bắc", "Regional"));
        hashMap.put("VOV_DONGBAC", new b("VOV Đông Bắc", "Kênh vùng Đông Bắc", "Regional"));
        hashMap.put("VOV_TAYNGUYEN", new b("VOV Tây Nguyên", "Kênh vùng Tây Nguyên", "Regional"));
        hashMap.size();
    }

    /* JADX WARNING: Removed duplicated region for block: B:6:0x0020  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final info.dourok.voicebot.java.audio.j.b a(java.lang.String r12) {
        /*
            r11 = this;
            boolean r0 = r12.isEmpty()
            r1 = 0
            if (r0 == 0) goto L_0x0008
            return r1
        L_0x0008:
            java.lang.String r0 = r12.toLowerCase()
            java.lang.String r0 = r0.trim()
            java.util.HashMap r2 = r11.d
            java.util.Set r3 = r2.entrySet()
            java.util.Iterator r3 = r3.iterator()
        L_0x001a:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x004d
            java.lang.Object r4 = r3.next()
            java.util.Map$Entry r4 = (java.util.Map.Entry) r4
            java.lang.Object r5 = r4.getValue()
            info.dourok.voicebot.java.audio.j$b r5 = (info.dourok.voicebot.java.audio.j.b) r5
            java.lang.String r5 = r5.c
            java.lang.String r5 = r5.toLowerCase()
            boolean r6 = r5.contains(r0)
            if (r6 != 0) goto L_0x003e
            boolean r5 = r0.contains(r5)
            if (r5 == 0) goto L_0x001a
        L_0x003e:
            java.lang.Object r12 = r4.getValue()
            info.dourok.voicebot.java.audio.j$b r12 = (info.dourok.voicebot.java.audio.j.b) r12
            java.lang.String r12 = r12.c
            java.lang.Object r12 = r4.getValue()
            info.dourok.voicebot.java.audio.j$b r12 = (info.dourok.voicebot.java.audio.j.b) r12
            return r12
        L_0x004d:
            java.lang.Object r12 = r2.get(r12)
            info.dourok.voicebot.java.audio.j$b r12 = (info.dourok.voicebot.java.audio.j.b) r12
            if (r12 == 0) goto L_0x0056
            return r12
        L_0x0056:
            java.util.Set r12 = r2.entrySet()
            java.util.Iterator r12 = r12.iterator()
        L_0x005e:
            boolean r3 = r12.hasNext()
            if (r3 == 0) goto L_0x0089
            java.lang.Object r3 = r12.next()
            java.util.Map$Entry r3 = (java.util.Map.Entry) r3
            java.lang.Object r4 = r3.getKey()
            java.lang.String r4 = (java.lang.String) r4
            java.lang.String r4 = r4.toLowerCase()
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x005e
            java.lang.Object r12 = r3.getValue()
            info.dourok.voicebot.java.audio.j$b r12 = (info.dourok.voicebot.java.audio.j.b) r12
            java.lang.String r12 = r12.c
            java.lang.Object r12 = r3.getValue()
            info.dourok.voicebot.java.audio.j$b r12 = (info.dourok.voicebot.java.audio.j.b) r12
            return r12
        L_0x0089:
            java.lang.String r12 = "tây nguyên"
            boolean r12 = r0.contains(r12)
            if (r12 != 0) goto L_0x0262
            java.lang.String r12 = "tay nguyen"
            boolean r12 = r0.contains(r12)
            if (r12 != 0) goto L_0x0262
            java.lang.String r12 = "nguyên"
            boolean r12 = r0.contains(r12)
            if (r12 != 0) goto L_0x0262
            java.lang.String r12 = "nguyen"
            boolean r12 = r0.contains(r12)
            if (r12 == 0) goto L_0x00ab
            goto L_0x0262
        L_0x00ab:
            java.lang.String r12 = "vov"
            boolean r12 = r0.contains(r12)
            if (r12 == 0) goto L_0x0138
            java.lang.String r12 = "mộc"
            boolean r12 = r0.contains(r12)
            if (r12 != 0) goto L_0x012f
            java.lang.String r12 = "mốc"
            boolean r12 = r0.contains(r12)
            if (r12 != 0) goto L_0x012f
            java.lang.String r12 = "mốt"
            boolean r12 = r0.contains(r12)
            if (r12 != 0) goto L_0x012f
            java.lang.String r12 = "máu"
            boolean r12 = r0.contains(r12)
            if (r12 != 0) goto L_0x012f
            java.lang.String r12 = "một"
            boolean r12 = r0.contains(r12)
            if (r12 != 0) goto L_0x012f
            java.lang.String r12 = "mút"
            boolean r12 = r0.contains(r12)
            if (r12 != 0) goto L_0x012f
            java.lang.String r12 = "mót"
            boolean r12 = r0.contains(r12)
            if (r12 != 0) goto L_0x012f
            java.lang.String r12 = "mục"
            boolean r12 = r0.contains(r12)
            if (r12 != 0) goto L_0x012f
            java.lang.String r12 = "1"
            boolean r12 = r0.contains(r12)
            if (r12 == 0) goto L_0x00fc
            goto L_0x012f
        L_0x00fc:
            java.lang.String r12 = "2"
            boolean r12 = r0.contains(r12)
            if (r12 != 0) goto L_0x0126
            java.lang.String r12 = "hai"
            boolean r12 = r0.contains(r12)
            if (r12 == 0) goto L_0x010d
            goto L_0x0126
        L_0x010d:
            java.lang.String r12 = "3"
            boolean r12 = r0.contains(r12)
            if (r12 != 0) goto L_0x011d
            java.lang.String r12 = "ba"
            boolean r12 = r0.contains(r12)
            if (r12 == 0) goto L_0x0138
        L_0x011d:
            java.lang.String r12 = "VOV3"
            java.lang.Object r12 = r2.get(r12)
            info.dourok.voicebot.java.audio.j$b r12 = (info.dourok.voicebot.java.audio.j.b) r12
            return r12
        L_0x0126:
            java.lang.String r12 = "VOV2"
            java.lang.Object r12 = r2.get(r12)
            info.dourok.voicebot.java.audio.j$b r12 = (info.dourok.voicebot.java.audio.j.b) r12
            return r12
        L_0x012f:
            java.lang.String r12 = "VOV1"
            java.lang.Object r12 = r2.get(r12)
            info.dourok.voicebot.java.audio.j$b r12 = (info.dourok.voicebot.java.audio.j.b) r12
            return r12
        L_0x0138:
            java.lang.String r12 = "giao thông"
            boolean r12 = r0.contains(r12)
            if (r12 != 0) goto L_0x022f
            java.lang.String r12 = "traffic"
            boolean r12 = r0.contains(r12)
            if (r12 == 0) goto L_0x014a
            goto L_0x022f
        L_0x014a:
            java.lang.String r12 = "english"
            boolean r12 = r0.contains(r12)
            if (r12 != 0) goto L_0x0226
            java.lang.String r12 = "tiếng anh"
            boolean r12 = r0.contains(r12)
            if (r12 != 0) goto L_0x0226
            java.lang.String r12 = "quốc tế"
            boolean r12 = r0.contains(r12)
            if (r12 == 0) goto L_0x0164
            goto L_0x0226
        L_0x0164:
            java.lang.String r12 = "mê kông"
            boolean r12 = r0.contains(r12)
            if (r12 != 0) goto L_0x021d
            java.lang.String r12 = "mekong"
            boolean r12 = r0.contains(r12)
            if (r12 != 0) goto L_0x021d
            java.lang.String r12 = "đồng bằng"
            boolean r12 = r0.contains(r12)
            if (r12 != 0) goto L_0x021d
            java.lang.String r12 = "cửu long"
            boolean r12 = r0.contains(r12)
            if (r12 == 0) goto L_0x0186
            goto L_0x021d
        L_0x0186:
            java.lang.String r12 = "miền trung"
            boolean r12 = r0.contains(r12)
            if (r12 != 0) goto L_0x0214
            java.lang.String r12 = "trung bộ"
            boolean r12 = r0.contains(r12)
            if (r12 == 0) goto L_0x0198
            goto L_0x0214
        L_0x0198:
            java.lang.String r12 = "tây bắc"
            boolean r12 = r0.contains(r12)
            if (r12 == 0) goto L_0x01a9
            java.lang.String r12 = "VOV_TAYBAC"
            java.lang.Object r12 = r2.get(r12)
            info.dourok.voicebot.java.audio.j$b r12 = (info.dourok.voicebot.java.audio.j.b) r12
            return r12
        L_0x01a9:
            java.lang.String r12 = "đông bắc"
            boolean r12 = r0.contains(r12)
            if (r12 == 0) goto L_0x01ba
            java.lang.String r12 = "VOV_DONGBAC"
            java.lang.Object r12 = r2.get(r12)
            info.dourok.voicebot.java.audio.j$b r12 = (info.dourok.voicebot.java.audio.j.b) r12
            return r12
        L_0x01ba:
            java.lang.String r3 = "tiếng nói"
            java.lang.String r4 = "việt nam"
            java.lang.String r5 = "giao thông"
            java.lang.String r6 = "mê kông"
            java.lang.String r7 = "miền trung"
            java.lang.String r8 = "tây bắc"
            java.lang.String r9 = "đông bắc"
            java.lang.String r10 = "tây nguyên"
            java.lang.String[] r12 = new java.lang.String[]{r3, r4, r5, r6, r7, r8, r9, r10}
            r3 = 0
        L_0x01cf:
            r4 = 8
            if (r3 >= r4) goto L_0x0213
            r4 = r12[r3]
            boolean r5 = r0.contains(r4)
            if (r5 == 0) goto L_0x0210
            java.util.Set r5 = r2.entrySet()
            java.util.Iterator r5 = r5.iterator()
        L_0x01e3:
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto L_0x0210
            java.lang.Object r6 = r5.next()
            java.util.Map$Entry r6 = (java.util.Map.Entry) r6
            java.lang.Object r7 = r6.getValue()
            info.dourok.voicebot.java.audio.j$b r7 = (info.dourok.voicebot.java.audio.j.b) r7
            java.lang.String r7 = r7.c
            java.lang.String r7 = r7.toLowerCase()
            boolean r7 = r7.contains(r4)
            if (r7 == 0) goto L_0x01e3
            java.lang.Object r12 = r6.getValue()
            info.dourok.voicebot.java.audio.j$b r12 = (info.dourok.voicebot.java.audio.j.b) r12
            java.lang.String r12 = r12.c
            java.lang.Object r12 = r6.getValue()
            info.dourok.voicebot.java.audio.j$b r12 = (info.dourok.voicebot.java.audio.j.b) r12
            return r12
        L_0x0210:
            int r3 = r3 + 1
            goto L_0x01cf
        L_0x0213:
            return r1
        L_0x0214:
            java.lang.String r12 = "VOV_MIENTRUNG"
            java.lang.Object r12 = r2.get(r12)
            info.dourok.voicebot.java.audio.j$b r12 = (info.dourok.voicebot.java.audio.j.b) r12
            return r12
        L_0x021d:
            java.lang.String r12 = "VOV_MEKONG"
            java.lang.Object r12 = r2.get(r12)
            info.dourok.voicebot.java.audio.j$b r12 = (info.dourok.voicebot.java.audio.j.b) r12
            return r12
        L_0x0226:
            java.lang.String r12 = "VOV_ENGLISH"
            java.lang.Object r12 = r2.get(r12)
            info.dourok.voicebot.java.audio.j$b r12 = (info.dourok.voicebot.java.audio.j.b) r12
            return r12
        L_0x022f:
            java.lang.String r12 = "hồ chí minh"
            boolean r12 = r0.contains(r12)
            if (r12 != 0) goto L_0x0259
            java.lang.String r12 = "sài gòn"
            boolean r12 = r0.contains(r12)
            if (r12 != 0) goto L_0x0259
            java.lang.String r12 = "hcm"
            boolean r12 = r0.contains(r12)
            if (r12 != 0) goto L_0x0259
            java.lang.String r12 = "saigon"
            boolean r12 = r0.contains(r12)
            if (r12 == 0) goto L_0x0250
            goto L_0x0259
        L_0x0250:
            java.lang.String r12 = "VOVGT"
            java.lang.Object r12 = r2.get(r12)
            info.dourok.voicebot.java.audio.j$b r12 = (info.dourok.voicebot.java.audio.j.b) r12
            return r12
        L_0x0259:
            java.lang.String r12 = "VOVGT_HCM"
            java.lang.Object r12 = r2.get(r12)
            info.dourok.voicebot.java.audio.j$b r12 = (info.dourok.voicebot.java.audio.j.b) r12
            return r12
        L_0x0262:
            java.lang.String r12 = "VOV_TAYNGUYEN"
            java.lang.Object r12 = r2.get(r12)
            info.dourok.voicebot.java.audio.j$b r12 = (info.dourok.voicebot.java.audio.j.b) r12
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: info.dourok.voicebot.java.audio.j.a(java.lang.String):info.dourok.voicebot.java.audio.j$b");
    }

    public final void b(String str) {
        w9 w9Var = new w9(this, str);
        Handler handler = zd.a;
        new Thread(new yd(str, w9Var)).start();
    }

    public final void c() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            d();
        } else {
            this.e.post(new a());
        }
    }

    public final void d() {
        MusicPlayer.m mVar;
        AtomicBoolean atomicBoolean = this.b;
        boolean z = atomicBoolean.get();
        atomicBoolean.set(false);
        this.c.set(false);
        if (z && (mVar = this.a) != null) {
            ((g8) mVar).b();
        }
    }
}
