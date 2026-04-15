package defpackage;

import j$.util.function.Consumer$CC;
import java.util.function.Consumer;
import org.schabi.newpipe.extractor.MultiInfoItemsCollector;

/* renamed from: q5  reason: default package */
public final /* synthetic */ class q5 implements Consumer {
    public final /* synthetic */ int a;
    public final /* synthetic */ MultiInfoItemsCollector b;

    public /* synthetic */ q5(MultiInfoItemsCollector multiInfoItemsCollector, int i) {
        this.a = i;
        this.b = multiInfoItemsCollector;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void accept(java.lang.Object r8) {
        /*
            r7 = this;
            int r0 = r7.a
            org.schabi.newpipe.extractor.MultiInfoItemsCollector r1 = r7.b
            switch(r0) {
                case 0: goto L_0x008b;
                case 1: goto L_0x0009;
                default: goto L_0x0007;
            }
        L_0x0007:
            goto L_0x0096
        L_0x0009:
            com.grack.nanojson.JsonObject r8 = (com.grack.nanojson.JsonObject) r8
            java.util.List<org.schabi.newpipe.extractor.utils.ImageSuffix> r0 = org.schabi.newpipe.extractor.services.soundcloud.SoundcloudParsingHelper.a
            java.lang.String r0 = "kind"
            java.lang.String r2 = ""
            java.lang.String r0 = r8.getString(r0, r2)
            r0.getClass()
            int r2 = r0.hashCode()
            r3 = 1
            r4 = 2
            r5 = 3
            java.lang.String r6 = "playlist"
            switch(r2) {
                case 3321751: goto L_0x0044;
                case 3599307: goto L_0x0039;
                case 110621003: goto L_0x002e;
                case 1879474642: goto L_0x0025;
                default: goto L_0x0024;
            }
        L_0x0024:
            goto L_0x004f
        L_0x0025:
            boolean r0 = r0.equals(r6)
            if (r0 != 0) goto L_0x002c
            goto L_0x004f
        L_0x002c:
            r0 = 3
            goto L_0x0050
        L_0x002e:
            java.lang.String r2 = "track"
            boolean r0 = r0.equals(r2)
            if (r0 != 0) goto L_0x0037
            goto L_0x004f
        L_0x0037:
            r0 = 2
            goto L_0x0050
        L_0x0039:
            java.lang.String r2 = "user"
            boolean r0 = r0.equals(r2)
            if (r0 != 0) goto L_0x0042
            goto L_0x004f
        L_0x0042:
            r0 = 1
            goto L_0x0050
        L_0x0044:
            java.lang.String r2 = "like"
            boolean r0 = r0.equals(r2)
            if (r0 != 0) goto L_0x004d
            goto L_0x004f
        L_0x004d:
            r0 = 0
            goto L_0x0050
        L_0x004f:
            r0 = -1
        L_0x0050:
            if (r0 == 0) goto L_0x0074
            if (r0 == r3) goto L_0x006b
            if (r0 == r4) goto L_0x0062
            if (r0 == r5) goto L_0x0059
            goto L_0x008a
        L_0x0059:
            org.schabi.newpipe.extractor.services.soundcloud.extractors.SoundcloudPlaylistInfoItemExtractor r0 = new org.schabi.newpipe.extractor.services.soundcloud.extractors.SoundcloudPlaylistInfoItemExtractor
            r0.<init>(r8)
            r1.commit(r0)
            goto L_0x008a
        L_0x0062:
            org.schabi.newpipe.extractor.services.soundcloud.extractors.SoundcloudStreamInfoItemExtractor r0 = new org.schabi.newpipe.extractor.services.soundcloud.extractors.SoundcloudStreamInfoItemExtractor
            r0.<init>(r8)
            r1.commit(r0)
            goto L_0x008a
        L_0x006b:
            org.schabi.newpipe.extractor.services.soundcloud.extractors.SoundcloudChannelInfoItemExtractor r0 = new org.schabi.newpipe.extractor.services.soundcloud.extractors.SoundcloudChannelInfoItemExtractor
            r0.<init>(r8)
            r1.commit(r0)
            goto L_0x008a
        L_0x0074:
            r0 = 0
            com.grack.nanojson.JsonObject r0 = r8.getObject(r6, r0)
            if (r0 != 0) goto L_0x0081
            org.schabi.newpipe.extractor.services.soundcloud.extractors.SoundcloudLikesInfoItemExtractor r0 = new org.schabi.newpipe.extractor.services.soundcloud.extractors.SoundcloudLikesInfoItemExtractor
            r0.<init>(r8)
            goto L_0x0087
        L_0x0081:
            org.schabi.newpipe.extractor.services.soundcloud.extractors.SoundcloudPlaylistInfoItemExtractor r8 = new org.schabi.newpipe.extractor.services.soundcloud.extractors.SoundcloudPlaylistInfoItemExtractor
            r8.<init>(r0)
            r0 = r8
        L_0x0087:
            r1.commit(r0)
        L_0x008a:
            return
        L_0x008b:
            com.grack.nanojson.JsonObject r8 = (com.grack.nanojson.JsonObject) r8
            org.schabi.newpipe.extractor.services.media_ccc.extractors.infoItems.MediaCCCStreamInfoItemExtractor r0 = new org.schabi.newpipe.extractor.services.media_ccc.extractors.infoItems.MediaCCCStreamInfoItemExtractor
            r0.<init>(r8)
            r1.commit(r0)
            return
        L_0x0096:
            org.schabi.newpipe.extractor.InfoItemExtractor r8 = (org.schabi.newpipe.extractor.InfoItemExtractor) r8
            r1.commit(r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.q5.accept(java.lang.Object):void");
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        switch (this.a) {
            case 0:
                return Consumer$CC.$default$andThen(this, consumer);
            case 1:
                return Consumer$CC.$default$andThen(this, consumer);
            default:
                return Consumer$CC.$default$andThen(this, consumer);
        }
    }
}
