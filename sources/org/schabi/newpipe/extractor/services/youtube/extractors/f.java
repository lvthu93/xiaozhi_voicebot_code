package org.schabi.newpipe.extractor.services.youtube.extractors;

import com.grack.nanojson.JsonObject;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeChannelTabExtractor;
import org.schabi.newpipe.extractor.utils.Utils;

public final class f extends YoutubePlaylistInfoItemExtractor {
    public final /* synthetic */ String b;
    public final /* synthetic */ String c;
    public final /* synthetic */ YoutubeChannelTabExtractor.a d;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public f(JsonObject jsonObject, String str, String str2, YoutubeChannelTabExtractor.a aVar) {
        super(jsonObject);
        this.b = str;
        this.c = str2;
        this.d = aVar;
    }

    public String getUploaderName() throws ParsingException {
        String str = this.b;
        return Utils.isNullOrEmpty(str) ? super.getUploaderName() : str;
    }

    public String getUploaderUrl() throws ParsingException {
        String str = this.c;
        return Utils.isNullOrEmpty(str) ? super.getUploaderName() : str;
    }

    public boolean isUploaderVerified() throws ParsingException {
        int ordinal = this.d.ordinal();
        if (ordinal == 0) {
            return true;
        }
        if (ordinal != 1) {
            return super.isUploaderVerified();
        }
        return false;
    }
}
