package org.schabi.newpipe.extractor.services.youtube.extractors;

import com.grack.nanojson.JsonObject;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.localization.TimeAgoParser;
import org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeChannelTabExtractor;
import org.schabi.newpipe.extractor.utils.Utils;

public final class c extends YoutubeStreamInfoItemLockupExtractor {
    public final /* synthetic */ String i;
    public final /* synthetic */ String j;
    public final /* synthetic */ YoutubeChannelTabExtractor.a k;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public c(JsonObject jsonObject, TimeAgoParser timeAgoParser, String str, String str2, YoutubeChannelTabExtractor.a aVar) {
        super(jsonObject, timeAgoParser);
        this.i = str;
        this.j = str2;
        this.k = aVar;
    }

    public String getUploaderName() throws ParsingException {
        String str = this.i;
        return Utils.isNullOrEmpty(str) ? super.getUploaderName() : str;
    }

    public String getUploaderUrl() throws ParsingException {
        String str = this.j;
        return Utils.isNullOrEmpty(str) ? super.getUploaderName() : str;
    }

    public boolean isUploaderVerified() {
        return this.k == YoutubeChannelTabExtractor.a.VERIFIED;
    }
}
