package org.schabi.newpipe.extractor.services.peertube.extractors;

import com.grack.nanojson.JsonObject;
import java.util.List;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.ServiceList;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandlerFactory;
import org.schabi.newpipe.extractor.localization.DateWrapper;
import org.schabi.newpipe.extractor.services.peertube.PeertubeParsingHelper;
import org.schabi.newpipe.extractor.stream.ContentAvailability;
import org.schabi.newpipe.extractor.stream.StreamInfoItemExtractor;
import org.schabi.newpipe.extractor.stream.StreamType;
import org.schabi.newpipe.extractor.utils.JsonUtils;

public class PeertubeStreamInfoItemExtractor implements StreamInfoItemExtractor {
    public final JsonObject a;
    public String b;

    public PeertubeStreamInfoItemExtractor(JsonObject jsonObject, String str) {
        this.a = jsonObject;
        this.b = str;
    }

    public final /* synthetic */ ContentAvailability getContentAvailability() {
        return ob.a(this);
    }

    public long getDuration() {
        return this.a.getLong("duration");
    }

    public String getName() throws ParsingException {
        return JsonUtils.getString(this.a, "name");
    }

    public final /* synthetic */ String getShortDescription() {
        return ob.b(this);
    }

    public StreamType getStreamType() {
        return this.a.getBoolean("isLive") ? StreamType.LIVE_STREAM : StreamType.VIDEO_STREAM;
    }

    public String getTextualUploadDate() throws ParsingException {
        return JsonUtils.getString(this.a, "publishedAt");
    }

    public List<Image> getThumbnails() throws ParsingException {
        return PeertubeParsingHelper.getThumbnailsFromPlaylistOrVideoItem(this.b, this.a);
    }

    public DateWrapper getUploadDate() throws ParsingException {
        return DateWrapper.fromInstant(getTextualUploadDate());
    }

    public List<Image> getUploaderAvatars() {
        return PeertubeParsingHelper.getAvatarsFromOwnerAccountOrVideoChannelObject(this.b, this.a.getObject("account"));
    }

    public String getUploaderName() throws ParsingException {
        return JsonUtils.getString(this.a, "account.displayName");
    }

    public String getUploaderUrl() throws ParsingException {
        JsonObject jsonObject = this.a;
        String string = JsonUtils.getString(jsonObject, "account.name");
        String string2 = JsonUtils.getString(jsonObject, "account.host");
        ListLinkHandlerFactory channelLHFactory = ServiceList.c.getChannelLHFactory();
        return channelLHFactory.fromId("accounts/" + string + "@" + string2, this.b).getUrl();
    }

    public String getUrl() throws ParsingException {
        return ServiceList.c.getStreamLHFactory().fromId(JsonUtils.getString(this.a, "uuid"), this.b).getUrl();
    }

    public long getViewCount() {
        return this.a.getLong("views");
    }

    public boolean isAd() {
        return false;
    }

    public final /* synthetic */ boolean isShortFormContent() {
        return ob.d(this);
    }

    public boolean isUploaderVerified() throws ParsingException {
        return false;
    }
}
