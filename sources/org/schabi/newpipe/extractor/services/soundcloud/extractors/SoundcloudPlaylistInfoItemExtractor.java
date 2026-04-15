package org.schabi.newpipe.extractor.services.soundcloud.extractors;

import com.grack.nanojson.JsonObject;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.playlist.PlaylistInfo;
import org.schabi.newpipe.extractor.playlist.PlaylistInfoItemExtractor;
import org.schabi.newpipe.extractor.stream.Description;
import org.schabi.newpipe.extractor.utils.Utils;

public class SoundcloudPlaylistInfoItemExtractor implements PlaylistInfoItemExtractor {
    public final JsonObject a;

    public SoundcloudPlaylistInfoItemExtractor(JsonObject jsonObject) {
        this.a = jsonObject;
    }

    public final /* synthetic */ Description getDescription() {
        return y8.a(this);
    }

    public String getName() {
        return this.a.getString("title");
    }

    public final /* synthetic */ PlaylistInfo.PlaylistType getPlaylistType() {
        return y8.b(this);
    }

    public long getStreamCount() {
        return this.a.getLong("track_count");
    }

    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:21:0x005b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<org.schabi.newpipe.extractor.Image> getThumbnails() throws org.schabi.newpipe.extractor.exceptions.ParsingException {
        /*
            r8 = this;
            java.lang.String r0 = "avatar_url"
            java.lang.String r1 = "user"
            com.grack.nanojson.JsonObject r2 = r8.a
            java.lang.String r3 = "artwork_url"
            boolean r4 = r2.isString(r3)
            if (r4 == 0) goto L_0x001d
            java.lang.String r4 = r2.getString(r3)
            boolean r5 = org.schabi.newpipe.extractor.utils.Utils.isNullOrEmpty((java.lang.String) r4)
            if (r5 != 0) goto L_0x001d
            java.util.List r0 = org.schabi.newpipe.extractor.services.soundcloud.SoundcloudParsingHelper.getAllImagesFromArtworkOrAvatarUrl(r4)
            return r0
        L_0x001d:
            java.lang.String r4 = "tracks"
            com.grack.nanojson.JsonArray r4 = r2.getArray(r4)     // Catch:{ Exception -> 0x005b }
            java.util.Iterator r4 = r4.iterator()     // Catch:{ Exception -> 0x005b }
        L_0x0027:
            boolean r5 = r4.hasNext()     // Catch:{ Exception -> 0x005b }
            if (r5 == 0) goto L_0x005b
            java.lang.Object r5 = r4.next()     // Catch:{ Exception -> 0x005b }
            com.grack.nanojson.JsonObject r5 = (com.grack.nanojson.JsonObject) r5     // Catch:{ Exception -> 0x005b }
            boolean r6 = r5.isString(r3)     // Catch:{ Exception -> 0x005b }
            if (r6 == 0) goto L_0x0048
            java.lang.String r6 = r5.getString(r3)     // Catch:{ Exception -> 0x005b }
            boolean r7 = org.schabi.newpipe.extractor.utils.Utils.isNullOrEmpty((java.lang.String) r6)     // Catch:{ Exception -> 0x005b }
            if (r7 != 0) goto L_0x0048
            java.util.List r0 = org.schabi.newpipe.extractor.services.soundcloud.SoundcloudParsingHelper.getAllImagesFromArtworkOrAvatarUrl(r6)     // Catch:{ Exception -> 0x005b }
            return r0
        L_0x0048:
            com.grack.nanojson.JsonObject r5 = r5.getObject(r1)     // Catch:{ Exception -> 0x005b }
            java.lang.String r5 = r5.getString(r0)     // Catch:{ Exception -> 0x005b }
            boolean r6 = org.schabi.newpipe.extractor.utils.Utils.isNullOrEmpty((java.lang.String) r5)     // Catch:{ Exception -> 0x005b }
            if (r6 != 0) goto L_0x0027
            java.util.List r0 = org.schabi.newpipe.extractor.services.soundcloud.SoundcloudParsingHelper.getAllImagesFromArtworkOrAvatarUrl(r5)     // Catch:{ Exception -> 0x005b }
            return r0
        L_0x005b:
            com.grack.nanojson.JsonObject r1 = r2.getObject(r1)     // Catch:{ Exception -> 0x0068 }
            java.lang.String r0 = r1.getString(r0)     // Catch:{ Exception -> 0x0068 }
            java.util.List r0 = org.schabi.newpipe.extractor.services.soundcloud.SoundcloudParsingHelper.getAllImagesFromArtworkOrAvatarUrl(r0)     // Catch:{ Exception -> 0x0068 }
            return r0
        L_0x0068:
            r0 = move-exception
            org.schabi.newpipe.extractor.exceptions.ParsingException r1 = new org.schabi.newpipe.extractor.exceptions.ParsingException
            java.lang.String r2 = "Failed to extract playlist thumbnails"
            r1.<init>(r2, r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.schabi.newpipe.extractor.services.soundcloud.extractors.SoundcloudPlaylistInfoItemExtractor.getThumbnails():java.util.List");
    }

    public String getUploaderName() throws ParsingException {
        try {
            return this.a.getObject("user").getString("username");
        } catch (Exception e) {
            throw new ParsingException("Failed to extract playlist uploader", e);
        }
    }

    public String getUploaderUrl() {
        return this.a.getObject("user").getString("permalink_url");
    }

    public String getUrl() {
        return Utils.replaceHttpWithHttps(this.a.getString("permalink_url"));
    }

    public boolean isUploaderVerified() {
        return this.a.getObject("user").getBoolean("verified");
    }
}
