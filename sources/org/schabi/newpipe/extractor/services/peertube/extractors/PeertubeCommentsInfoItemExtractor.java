package org.schabi.newpipe.extractor.services.peertube.extractors;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonWriter;
import j$.util.Objects;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.jsoup.Jsoup;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.ServiceList;
import org.schabi.newpipe.extractor.comments.CommentsInfoItemExtractor;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandlerFactory;
import org.schabi.newpipe.extractor.localization.DateWrapper;
import org.schabi.newpipe.extractor.services.peertube.PeertubeParsingHelper;
import org.schabi.newpipe.extractor.stream.Description;
import org.schabi.newpipe.extractor.utils.JsonUtils;

public class PeertubeCommentsInfoItemExtractor implements CommentsInfoItemExtractor {
    public final JsonObject a;
    public final JsonArray b;
    public final String c;
    public final String d;
    public final boolean e;
    public Integer f;

    public PeertubeCommentsInfoItemExtractor(JsonObject jsonObject, JsonArray jsonArray, String str, String str2, boolean z) {
        this.a = jsonObject;
        this.b = jsonArray;
        this.c = str;
        this.d = str2;
        this.e = z;
    }

    public String getCommentId() {
        return Objects.toString(Long.valueOf(this.a.getLong("id")), (String) null);
    }

    public Description getCommentText() throws ParsingException {
        String string = JsonUtils.getString(this.a, "text");
        try {
            return new Description(Jsoup.parse(string).body().text(), 3);
        } catch (Exception unused) {
            return new Description(string.replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", ""), 3);
        }
    }

    public final /* synthetic */ int getLikeCount() {
        return p0.c(this);
    }

    public String getName() throws ParsingException {
        return JsonUtils.getString(this.a, "account.displayName");
    }

    public Page getReplies() throws ParsingException {
        JsonArray jsonArray;
        if (getReplyCount() == 0) {
            return null;
        }
        String obj = JsonUtils.getNumber(this.a, "threadId").toString();
        String str = this.c + MqttTopic.TOPIC_LEVEL_SEPARATOR + obj;
        if (!this.e || (jsonArray = this.b) == null || jsonArray.isEmpty()) {
            return new Page(str, obj);
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.put("children", jsonArray);
        return new Page(str, obj, JsonWriter.string(jsonObject).getBytes(StandardCharsets.UTF_8));
    }

    public int getReplyCount() throws ParsingException {
        if (this.f == null) {
            JsonArray jsonArray = this.b;
            if (jsonArray == null || jsonArray.isEmpty()) {
                this.f = Integer.valueOf(JsonUtils.getNumber(this.a, "totalReplies").intValue());
            } else {
                this.f = Integer.valueOf(jsonArray.size());
            }
        }
        return this.f.intValue();
    }

    public final /* synthetic */ int getStreamPosition() {
        return p0.f(this);
    }

    public final /* synthetic */ String getTextualLikeCount() {
        return p0.g(this);
    }

    public String getTextualUploadDate() throws ParsingException {
        return JsonUtils.getString(this.a, "createdAt");
    }

    public List<Image> getThumbnails() {
        return getUploaderAvatars();
    }

    public DateWrapper getUploadDate() throws ParsingException {
        return DateWrapper.fromInstant(getTextualUploadDate());
    }

    public List<Image> getUploaderAvatars() {
        return PeertubeParsingHelper.getAvatarsFromOwnerAccountOrVideoChannelObject(this.d, this.a.getObject("account"));
    }

    public String getUploaderName() throws ParsingException {
        StringBuilder sb = new StringBuilder();
        JsonObject jsonObject = this.a;
        sb.append(JsonUtils.getString(jsonObject, "account.name"));
        sb.append("@");
        sb.append(JsonUtils.getString(jsonObject, "account.host"));
        return sb.toString();
    }

    public String getUploaderUrl() throws ParsingException {
        JsonObject jsonObject = this.a;
        String string = JsonUtils.getString(jsonObject, "account.name");
        String string2 = JsonUtils.getString(jsonObject, "account.host");
        ListLinkHandlerFactory channelLHFactory = ServiceList.c.getChannelLHFactory();
        return channelLHFactory.fromId("accounts/" + string + "@" + string2, this.d).getUrl();
    }

    public String getUrl() throws ParsingException {
        return this.c + MqttTopic.TOPIC_LEVEL_SEPARATOR + getCommentId();
    }

    public boolean hasCreatorReply() {
        JsonObject jsonObject = this.a;
        if (!jsonObject.has("totalRepliesFromVideoAuthor") || jsonObject.getInt("totalRepliesFromVideoAuthor") <= 0) {
            return false;
        }
        return true;
    }

    public final /* synthetic */ boolean isChannelOwner() {
        return p0.n(this);
    }

    public final /* synthetic */ boolean isHeartedByUploader() {
        return p0.o(this);
    }

    public final /* synthetic */ boolean isPinned() {
        return p0.p(this);
    }

    public final /* synthetic */ boolean isUploaderVerified() {
        return p0.q(this);
    }
}
