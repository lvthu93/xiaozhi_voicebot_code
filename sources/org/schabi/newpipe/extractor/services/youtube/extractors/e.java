package org.schabi.newpipe.extractor.services.youtube.extractors;

import com.grack.nanojson.JsonObject;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.localization.TimeAgoParser;
import org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeChannelTabExtractor;
import org.schabi.newpipe.extractor.utils.Utils;

public final class e extends YoutubeStreamInfoItemExtractor {
    public final /* synthetic */ String f;
    public final /* synthetic */ String g;
    public final /* synthetic */ YoutubeChannelTabExtractor.a h;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public e(JsonObject jsonObject, TimeAgoParser timeAgoParser, String str, String str2, YoutubeChannelTabExtractor.a aVar) {
        super(jsonObject, timeAgoParser);
        this.f = str;
        this.g = str2;
        this.h = aVar;
    }

    public String getUploaderName() throws ParsingException {
        String str = this.f;
        return Utils.isNullOrEmpty(str) ? super.getUploaderName() : str;
    }

    public String getUploaderUrl() throws ParsingException {
        String str = this.g;
        return Utils.isNullOrEmpty(str) ? super.getUploaderName() : str;
    }

    public boolean isUploaderVerified() throws ParsingException {
        int ordinal = this.h.ordinal();
        if (ordinal == 0) {
            return true;
        }
        if (ordinal != 1) {
            return super.isUploaderVerified();
        }
        return false;
    }
}
