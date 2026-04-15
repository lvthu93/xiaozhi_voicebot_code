package defpackage;

import j$.util.function.Consumer$CC;
import java.util.function.Consumer;

/* renamed from: hb  reason: default package */
public final /* synthetic */ class hb implements Consumer {
    public final /* synthetic */ int a;
    public final /* synthetic */ Object b;
    public final /* synthetic */ Object c;

    public /* synthetic */ hb(int i, Object obj, Object obj2) {
        this.a = i;
        this.c = obj;
        this.b = obj2;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x0173, code lost:
        if (r4.equals("music_playlists") == false) goto L_0x0175;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void accept(java.lang.Object r10) {
        /*
            r9 = this;
            int r0 = r9.a
            java.lang.String r1 = ""
            r2 = 0
            java.lang.Object r3 = r9.b
            java.lang.Object r4 = r9.c
            r5 = 1
            switch(r0) {
                case 0: goto L_0x00d2;
                case 1: goto L_0x003b;
                case 2: goto L_0x000f;
                default: goto L_0x000d;
            }
        L_0x000d:
            goto L_0x0101
        L_0x000f:
            org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeChannelExtractor r4 = (org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeChannelExtractor) r4
            java.util.List r3 = (java.util.List) r3
            java.lang.String r10 = (java.lang.String) r10
            r4.getClass()
            org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeChannelTabLinkHandlerFactory r0 = org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeChannelTabLinkHandlerFactory.getInstance()     // Catch:{ ParsingException -> 0x003a }
            java.lang.String r4 = r4.i     // Catch:{ ParsingException -> 0x003a }
            java.lang.Object[] r6 = new java.lang.Object[r5]     // Catch:{ ParsingException -> 0x003a }
            r6[r2] = r10     // Catch:{ ParsingException -> 0x003a }
            java.util.ArrayList r10 = new java.util.ArrayList     // Catch:{ ParsingException -> 0x003a }
            r10.<init>(r5)     // Catch:{ ParsingException -> 0x003a }
            r2 = r6[r2]     // Catch:{ ParsingException -> 0x003a }
            j$.util.Objects.requireNonNull(r2)     // Catch:{ ParsingException -> 0x003a }
            r10.add(r2)     // Catch:{ ParsingException -> 0x003a }
            java.util.List r10 = java.util.Collections.unmodifiableList(r10)     // Catch:{ ParsingException -> 0x003a }
            org.schabi.newpipe.extractor.linkhandler.ListLinkHandler r10 = r0.fromQuery(r4, r10, r1)     // Catch:{ ParsingException -> 0x003a }
            r3.add(r10)     // Catch:{ ParsingException -> 0x003a }
        L_0x003a:
            return
        L_0x003b:
            org.schabi.newpipe.extractor.services.soundcloud.extractors.SoundcloudStreamExtractor r4 = (org.schabi.newpipe.extractor.services.soundcloud.extractors.SoundcloudStreamExtractor) r4
            java.util.List r3 = (java.util.List) r3
            com.grack.nanojson.JsonObject r10 = (com.grack.nanojson.JsonObject) r10
            r4.getClass()
            java.lang.String r0 = "url"
            java.lang.String r0 = r10.getString(r0)
            boolean r1 = org.schabi.newpipe.extractor.utils.Utils.isNullOrEmpty((java.lang.String) r0)
            if (r1 == 0) goto L_0x0052
            goto L_0x00d1
        L_0x0052:
            java.lang.String r1 = "preset"
            java.lang.String r2 = " "
            java.lang.String r1 = r10.getString(r1, r2)     // Catch:{ IOException | ExtractionException -> 0x00d1 }
            java.lang.String r2 = "format"
            com.grack.nanojson.JsonObject r10 = r10.getObject(r2)     // Catch:{ IOException | ExtractionException -> 0x00d1 }
            java.lang.String r2 = "protocol"
            java.lang.String r10 = r10.getString(r2)     // Catch:{ IOException | ExtractionException -> 0x00d1 }
            java.lang.String r2 = "encrypted"
            boolean r2 = r10.contains(r2)     // Catch:{ IOException | ExtractionException -> 0x00d1 }
            if (r2 == 0) goto L_0x006f
            goto L_0x00d1
        L_0x006f:
            org.schabi.newpipe.extractor.stream.AudioStream$Builder r2 = new org.schabi.newpipe.extractor.stream.AudioStream$Builder     // Catch:{ IOException | ExtractionException -> 0x00d1 }
            r2.<init>()     // Catch:{ IOException | ExtractionException -> 0x00d1 }
            org.schabi.newpipe.extractor.stream.AudioStream$Builder r2 = r2.setId(r1)     // Catch:{ IOException | ExtractionException -> 0x00d1 }
            java.lang.String r6 = "hls"
            boolean r10 = r10.equals(r6)     // Catch:{ IOException | ExtractionException -> 0x00d1 }
            if (r10 == 0) goto L_0x0085
            org.schabi.newpipe.extractor.stream.DeliveryMethod r10 = org.schabi.newpipe.extractor.stream.DeliveryMethod.HLS     // Catch:{ IOException | ExtractionException -> 0x00d1 }
            r2.setDeliveryMethod(r10)     // Catch:{ IOException | ExtractionException -> 0x00d1 }
        L_0x0085:
            java.lang.String r10 = r4.c(r0)     // Catch:{ IOException | ExtractionException -> 0x00d1 }
            r2.setContent(r10, r5)     // Catch:{ IOException | ExtractionException -> 0x00d1 }
            java.lang.String r10 = "mp3"
            boolean r10 = r1.contains(r10)     // Catch:{ IOException | ExtractionException -> 0x00d1 }
            if (r10 == 0) goto L_0x009f
            org.schabi.newpipe.extractor.MediaFormat r10 = org.schabi.newpipe.extractor.MediaFormat.MP3     // Catch:{ IOException | ExtractionException -> 0x00d1 }
            r2.setMediaFormat(r10)     // Catch:{ IOException | ExtractionException -> 0x00d1 }
            r10 = 128(0x80, float:1.794E-43)
            r2.setAverageBitrate(r10)     // Catch:{ IOException | ExtractionException -> 0x00d1 }
            goto L_0x00c4
        L_0x009f:
            java.lang.String r10 = "opus"
            boolean r10 = r1.contains(r10)     // Catch:{ IOException | ExtractionException -> 0x00d1 }
            if (r10 == 0) goto L_0x00b2
            org.schabi.newpipe.extractor.MediaFormat r10 = org.schabi.newpipe.extractor.MediaFormat.OPUS     // Catch:{ IOException | ExtractionException -> 0x00d1 }
            r2.setMediaFormat(r10)     // Catch:{ IOException | ExtractionException -> 0x00d1 }
            r10 = 64
            r2.setAverageBitrate(r10)     // Catch:{ IOException | ExtractionException -> 0x00d1 }
            goto L_0x00c4
        L_0x00b2:
            java.lang.String r10 = "aac_160k"
            boolean r10 = r1.contains(r10)     // Catch:{ IOException | ExtractionException -> 0x00d1 }
            if (r10 == 0) goto L_0x00d1
            org.schabi.newpipe.extractor.MediaFormat r10 = org.schabi.newpipe.extractor.MediaFormat.M4A     // Catch:{ IOException | ExtractionException -> 0x00d1 }
            r2.setMediaFormat(r10)     // Catch:{ IOException | ExtractionException -> 0x00d1 }
            r10 = 160(0xa0, float:2.24E-43)
            r2.setAverageBitrate(r10)     // Catch:{ IOException | ExtractionException -> 0x00d1 }
        L_0x00c4:
            org.schabi.newpipe.extractor.stream.AudioStream r10 = r2.build()     // Catch:{ IOException | ExtractionException -> 0x00d1 }
            boolean r0 = org.schabi.newpipe.extractor.stream.Stream.containSimilarStream(r10, r3)     // Catch:{ IOException | ExtractionException -> 0x00d1 }
            if (r0 != 0) goto L_0x00d1
            r3.add(r10)     // Catch:{ IOException | ExtractionException -> 0x00d1 }
        L_0x00d1:
            return
        L_0x00d2:
            org.schabi.newpipe.extractor.stream.StreamInfoItemsCollector r4 = (org.schabi.newpipe.extractor.stream.StreamInfoItemsCollector) r4
            java.util.List r3 = (java.util.List) r3
            com.grack.nanojson.JsonObject r10 = (com.grack.nanojson.JsonObject) r10
            java.lang.String r0 = "title"
            boolean r0 = r10.has(r0)
            if (r0 == 0) goto L_0x00e9
            org.schabi.newpipe.extractor.services.soundcloud.extractors.SoundcloudStreamInfoItemExtractor r0 = new org.schabi.newpipe.extractor.services.soundcloud.extractors.SoundcloudStreamInfoItemExtractor
            r0.<init>(r10)
            r4.commit((org.schabi.newpipe.extractor.stream.StreamInfoItemExtractor) r0)
            goto L_0x0100
        L_0x00e9:
            java.lang.Object[] r0 = new java.lang.Object[r5]
            java.lang.String r1 = "id"
            int r10 = r10.getInt(r1)
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)
            r0[r2] = r10
            java.lang.String r10 = "%010d"
            java.lang.String r10 = java.lang.String.format(r10, r0)
            r3.add(r10)
        L_0x0100:
            return
        L_0x0101:
            java.lang.String r4 = (java.lang.String) r4
            org.schabi.newpipe.extractor.MultiInfoItemsCollector r3 = (org.schabi.newpipe.extractor.MultiInfoItemsCollector) r3
            com.grack.nanojson.JsonObject r10 = (com.grack.nanojson.JsonObject) r10
            java.lang.String r0 = "musicItemRendererDisplayPolicy"
            java.lang.String r0 = r10.getString(r0, r1)
            java.lang.String r1 = "MUSIC_ITEM_RENDERER_DISPLAY_POLICY_GREY_OUT"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0117
            goto L_0x019b
        L_0x0117:
            java.lang.String r0 = "flexColumns"
            com.grack.nanojson.JsonArray r0 = r10.getArray(r0)
            com.grack.nanojson.JsonObject r0 = r0.getObject(r5)
            java.lang.String r1 = "musicResponsiveListItemFlexColumnRenderer"
            com.grack.nanojson.JsonObject r0 = r0.getObject(r1)
            java.lang.String r1 = "text"
            com.grack.nanojson.JsonObject r0 = r0.getObject(r1)
            java.lang.String r1 = "runs"
            com.grack.nanojson.JsonArray r0 = r0.getArray(r1)
            r4.getClass()
            int r1 = r4.hashCode()
            r6 = 2
            r7 = 3
            r8 = 4
            switch(r1) {
                case -1778518201: goto L_0x016d;
                case -566908430: goto L_0x0162;
                case 1499667262: goto L_0x0157;
                case 1589120868: goto L_0x014c;
                case 2098153138: goto L_0x0141;
                default: goto L_0x0140;
            }
        L_0x0140:
            goto L_0x0175
        L_0x0141:
            java.lang.String r1 = "music_videos"
            boolean r1 = r4.equals(r1)
            if (r1 != 0) goto L_0x014a
            goto L_0x0175
        L_0x014a:
            r2 = 4
            goto L_0x0176
        L_0x014c:
            java.lang.String r1 = "music_songs"
            boolean r1 = r4.equals(r1)
            if (r1 != 0) goto L_0x0155
            goto L_0x0175
        L_0x0155:
            r2 = 3
            goto L_0x0176
        L_0x0157:
            java.lang.String r1 = "music_albums"
            boolean r1 = r4.equals(r1)
            if (r1 != 0) goto L_0x0160
            goto L_0x0175
        L_0x0160:
            r2 = 2
            goto L_0x0176
        L_0x0162:
            java.lang.String r1 = "music_artists"
            boolean r1 = r4.equals(r1)
            if (r1 != 0) goto L_0x016b
            goto L_0x0175
        L_0x016b:
            r2 = 1
            goto L_0x0176
        L_0x016d:
            java.lang.String r1 = "music_playlists"
            boolean r1 = r4.equals(r1)
            if (r1 != 0) goto L_0x0176
        L_0x0175:
            r2 = -1
        L_0x0176:
            if (r2 == 0) goto L_0x0193
            if (r2 == r5) goto L_0x018a
            if (r2 == r6) goto L_0x0193
            if (r2 == r7) goto L_0x0181
            if (r2 == r8) goto L_0x0181
            goto L_0x019b
        L_0x0181:
            org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeMusicSongOrVideoInfoItemExtractor r1 = new org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeMusicSongOrVideoInfoItemExtractor
            r1.<init>(r10, r0, r4)
            r3.commit(r1)
            goto L_0x019b
        L_0x018a:
            org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeMusicArtistInfoItemExtractor r0 = new org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeMusicArtistInfoItemExtractor
            r0.<init>(r10)
            r3.commit(r0)
            goto L_0x019b
        L_0x0193:
            org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeMusicAlbumOrPlaylistInfoItemExtractor r1 = new org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeMusicAlbumOrPlaylistInfoItemExtractor
            r1.<init>(r10, r0, r4)
            r3.commit(r1)
        L_0x019b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.hb.accept(java.lang.Object):void");
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        switch (this.a) {
            case 0:
                return Consumer$CC.$default$andThen(this, consumer);
            case 1:
                return Consumer$CC.$default$andThen(this, consumer);
            case 2:
                return Consumer$CC.$default$andThen(this, consumer);
            default:
                return Consumer$CC.$default$andThen(this, consumer);
        }
    }
}
