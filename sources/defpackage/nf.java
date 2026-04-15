package defpackage;

import j$.util.function.Consumer$CC;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeChannelExtractor;

/* renamed from: nf  reason: default package */
public final /* synthetic */ class nf implements Consumer {
    public final /* synthetic */ YoutubeChannelExtractor a;
    public final /* synthetic */ List b;
    public final /* synthetic */ String c;
    public final /* synthetic */ String d;
    public final /* synthetic */ String e;
    public final /* synthetic */ Consumer f;

    public /* synthetic */ nf(YoutubeChannelExtractor youtubeChannelExtractor, ArrayList arrayList, String str, String str2, String str3, hb hbVar) {
        this.a = youtubeChannelExtractor;
        this.b = arrayList;
        this.c = str;
        this.d = str2;
        this.e = str3;
        this.f = hbVar;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void accept(java.lang.Object r17) {
        /*
            r16 = this;
            r0 = r16
            java.lang.String r4 = r0.c
            java.lang.String r5 = r0.d
            java.lang.String r6 = r0.e
            r2 = r17
            com.grack.nanojson.JsonObject r2 = (com.grack.nanojson.JsonObject) r2
            org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeChannelExtractor r1 = r0.a
            r1.getClass()
            java.lang.String r3 = "endpoint"
            com.grack.nanojson.JsonObject r3 = r2.getObject(r3)
            java.lang.String r7 = "commandMetadata"
            com.grack.nanojson.JsonObject r3 = r3.getObject(r7)
            java.lang.String r7 = "webCommandMetadata"
            com.grack.nanojson.JsonObject r3 = r3.getObject(r7)
            java.lang.String r7 = "url"
            java.lang.String r7 = r3.getString(r7)
            if (r7 == 0) goto L_0x00c6
            java.lang.String r3 = "/"
            java.lang.String[] r3 = r7.split(r3)
            int r8 = r3.length
            if (r8 != 0) goto L_0x0036
            goto L_0x00c6
        L_0x0036:
            int r8 = r3.length
            r9 = 1
            int r8 = r8 - r9
            r3 = r3[r8]
            org.schabi.newpipe.extractor.services.youtube.YoutubeChannelHelper$ChannelHeader r8 = r1.h
            if (r8 != 0) goto L_0x0041
            r8 = 0
            goto L_0x004b
        L_0x0041:
            org.schabi.newpipe.extractor.services.youtube.YoutubeChannelHelper$ChannelHeader r10 = new org.schabi.newpipe.extractor.services.youtube.YoutubeChannelHelper$ChannelHeader
            com.grack.nanojson.JsonObject r11 = r8.c
            org.schabi.newpipe.extractor.services.youtube.YoutubeChannelHelper$ChannelHeader$HeaderType r8 = r8.f
            r10.<init>(r11, r8)
            r8 = r10
        L_0x004b:
            r3.getClass()
            int r10 = r3.hashCode()
            r12 = 2
            r13 = 3
            java.lang.String r15 = "videos"
            java.lang.String r11 = "shorts"
            java.lang.String r14 = "playlists"
            switch(r10) {
                case -1881890573: goto L_0x0084;
                case -1865828127: goto L_0x007b;
                case -903148681: goto L_0x0072;
                case -816678056: goto L_0x0069;
                case -551298740: goto L_0x005e;
                default: goto L_0x005d;
            }
        L_0x005d:
            goto L_0x008f
        L_0x005e:
            java.lang.String r10 = "releases"
            boolean r3 = r3.equals(r10)
            if (r3 != 0) goto L_0x0067
            goto L_0x008f
        L_0x0067:
            r3 = 4
            goto L_0x0090
        L_0x0069:
            boolean r3 = r3.equals(r15)
            if (r3 != 0) goto L_0x0070
            goto L_0x008f
        L_0x0070:
            r3 = 3
            goto L_0x0090
        L_0x0072:
            boolean r3 = r3.equals(r11)
            if (r3 != 0) goto L_0x0079
            goto L_0x008f
        L_0x0079:
            r3 = 2
            goto L_0x0090
        L_0x007b:
            boolean r3 = r3.equals(r14)
            if (r3 != 0) goto L_0x0082
            goto L_0x008f
        L_0x0082:
            r3 = 1
            goto L_0x0090
        L_0x0084:
            java.lang.String r10 = "streams"
            boolean r3 = r3.equals(r10)
            if (r3 != 0) goto L_0x008d
            goto L_0x008f
        L_0x008d:
            r3 = 0
            goto L_0x0090
        L_0x008f:
            r3 = -1
        L_0x0090:
            java.util.function.Consumer r10 = r0.f
            if (r3 == 0) goto L_0x00c1
            if (r3 == r9) goto L_0x00bd
            if (r3 == r12) goto L_0x00b9
            if (r3 == r13) goto L_0x00a4
            r9 = 4
            if (r3 == r9) goto L_0x009e
            goto L_0x00c6
        L_0x009e:
            java.lang.String r1 = "albums"
            r10.accept(r1)
            goto L_0x00c6
        L_0x00a4:
            org.schabi.newpipe.extractor.linkhandler.ReadyChannelTabListLinkHandler r9 = new org.schabi.newpipe.extractor.linkhandler.ReadyChannelTabListLinkHandler
            java.lang.String r10 = r1.i
            pf r11 = new pf
            r1 = r11
            r3 = r8
            r1.<init>(r2, r3, r4, r5, r6)
            r9.<init>(r7, r10, r15, r11)
            java.util.List r1 = r0.b
            r2 = 0
            r1.add(r2, r9)
            goto L_0x00c6
        L_0x00b9:
            r10.accept(r11)
            goto L_0x00c6
        L_0x00bd:
            r10.accept(r14)
            goto L_0x00c6
        L_0x00c1:
            java.lang.String r1 = "livestreams"
            r10.accept(r1)
        L_0x00c6:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.nf.accept(java.lang.Object):void");
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }
}
