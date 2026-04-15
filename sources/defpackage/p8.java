package defpackage;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonObject;
import j$.util.Collection$EL;
import j$.util.function.Function$CC;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import org.mozilla.javascript.ES6Iterator;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.MediaFormat;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.services.youtube.YoutubeChannelHelper;
import org.schabi.newpipe.extractor.services.youtube.YoutubeDescriptionHelper;
import org.schabi.newpipe.extractor.services.youtube.YoutubeParsingHelper;
import org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeMixPlaylistExtractor;
import org.schabi.newpipe.extractor.stream.DeliveryMethod;
import org.schabi.newpipe.extractor.stream.VideoStream;
import org.schabi.newpipe.extractor.utils.ImageSuffix;
import org.schabi.newpipe.extractor.utils.JsonUtils;

/* renamed from: p8  reason: default package */
public final /* synthetic */ class p8 implements Function {
    public final /* synthetic */ int a;

    public /* synthetic */ p8(int i) {
        this.a = i;
    }

    public final /* synthetic */ Function andThen(Function function) {
        switch (this.a) {
            case 0:
                return Function$CC.$default$andThen(this, function);
            case 1:
                return Function$CC.$default$andThen(this, function);
            case 2:
                return Function$CC.$default$andThen(this, function);
            case 3:
                return Function$CC.$default$andThen(this, function);
            case 4:
                return Function$CC.$default$andThen(this, function);
            case 5:
                return Function$CC.$default$andThen(this, function);
            case 6:
                return Function$CC.$default$andThen(this, function);
            case 7:
                return Function$CC.$default$andThen(this, function);
            case 8:
                return Function$CC.$default$andThen(this, function);
            case 9:
                return Function$CC.$default$andThen(this, function);
            case 10:
                return Function$CC.$default$andThen(this, function);
            case 11:
                return Function$CC.$default$andThen(this, function);
            case 12:
                return Function$CC.$default$andThen(this, function);
            case 13:
                return Function$CC.$default$andThen(this, function);
            case 14:
                return Function$CC.$default$andThen(this, function);
            case 15:
                return Function$CC.$default$andThen(this, function);
            case 16:
                return Function$CC.$default$andThen(this, function);
            case 17:
                return Function$CC.$default$andThen(this, function);
            case 18:
                return Function$CC.$default$andThen(this, function);
            case 19:
                return Function$CC.$default$andThen(this, function);
            case 20:
                return Function$CC.$default$andThen(this, function);
            case 21:
                return Function$CC.$default$andThen(this, function);
            case 22:
                return Function$CC.$default$andThen(this, function);
            case 23:
                return Function$CC.$default$andThen(this, function);
            case 24:
                return Function$CC.$default$andThen(this, function);
            case 25:
                return Function$CC.$default$andThen(this, function);
            case 26:
                return Function$CC.$default$andThen(this, function);
            case 27:
                return Function$CC.$default$andThen(this, function);
            case 28:
                return Function$CC.$default$andThen(this, function);
            default:
                return Function$CC.$default$andThen(this, function);
        }
    }

    public final Object apply(Object obj) {
        YoutubeChannelHelper.ChannelHeader.HeaderType headerType = YoutubeChannelHelper.ChannelHeader.HeaderType.PAGE;
        switch (this.a) {
            case 0:
                JsonObject jsonObject = (JsonObject) obj;
                return new VideoStream.Builder().setId(String.valueOf(jsonObject.getInt("id", -1))).setContent(jsonObject.getString("playlistUrl", ""), true).setIsVideoOnly(false).setResolution("").setMediaFormat(MediaFormat.MPEG_4).setDeliveryMethod(DeliveryMethod.HLS).build();
            case 1:
                return (String) ((Map.Entry) obj).getKey();
            case 2:
                return new YoutubeChannelHelper.ChannelHeader((JsonObject) obj, YoutubeChannelHelper.ChannelHeader.HeaderType.C4_TABBED);
            case 3:
                return ((JsonObject) obj).getObject("topicChannelDetailsRenderer");
            case 4:
                return new YoutubeChannelHelper.ChannelHeader((JsonObject) obj, YoutubeChannelHelper.ChannelHeader.HeaderType.CAROUSEL);
            case 5:
                return new YoutubeChannelHelper.ChannelHeader((JsonObject) obj, headerType);
            case 6:
                return new YoutubeChannelHelper.ChannelHeader((JsonObject) obj, YoutubeChannelHelper.ChannelHeader.HeaderType.INTERACTIVE_TABBED);
            case 7:
                Class<JsonObject> cls = JsonObject.class;
                return y2.z(cls, 17, y2.s(cls, 17, Collection$EL.stream(((JsonObject) obj).getObject("tabRenderer").getObject("content").getObject("sectionListRenderer").getArray("contents"))));
            case 8:
                return ((JsonObject) obj).getObject("channelAgeGateRenderer");
            case 9:
                YoutubeChannelHelper.ChannelHeader channelHeader = (YoutubeChannelHelper.ChannelHeader) obj;
                JsonObject jsonObject2 = channelHeader.c;
                int ordinal = channelHeader.f.ordinal();
                if (ordinal == 1 || ordinal == 2) {
                    return YoutubeParsingHelper.getTextFromObject(jsonObject2.getObject("title"));
                }
                if (ordinal != 3) {
                    return jsonObject2.getString("title");
                }
                return jsonObject2.getObject("content").getObject("pageHeaderViewModel").getObject("title").getObject("dynamicTextViewModel").getObject("text").getString("content", jsonObject2.getString("pageTitle"));
            case 10:
                String str = (String) obj;
                Matcher matcher = YoutubeDescriptionHelper.a.matcher(str);
                if (matcher.find()) {
                    return matcher.group(1);
                }
                return str;
            case 11:
                return ((JsonObject) obj).getObject("singleActionEmergencySupportRenderer");
            case 12:
                JsonObject jsonObject3 = (JsonObject) obj;
                String str2 = YoutubeParsingHelper.a;
                int i = jsonObject3.getInt("height", -1);
                return new Image(YoutubeParsingHelper.fixThumbnailUrl(jsonObject3.getString("url")), i, jsonObject3.getInt("width", -1), Image.ResolutionLevel.fromHeight(i));
            case 13:
                String str3 = YoutubeParsingHelper.a;
                return Collection$EL.stream(((JsonObject) obj).getArray("params"));
            case 14:
                String str4 = YoutubeParsingHelper.a;
                return ((JsonObject) obj).getString(ES6Iterator.VALUE_PROPERTY);
            case 15:
                YoutubeChannelHelper.ChannelHeader channelHeader2 = (YoutubeChannelHelper.ChannelHeader) obj;
                YoutubeChannelHelper.ChannelHeader.HeaderType headerType2 = channelHeader2.f;
                JsonObject jsonObject4 = channelHeader2.c;
                if (headerType2 != headerType) {
                    return jsonObject4.getObject("banner").getArray("thumbnails");
                }
                JsonObject object = jsonObject4.getObject("content").getObject("pageHeaderViewModel");
                if (object.has("banner")) {
                    return object.getObject("banner").getObject("imageBannerViewModel").getObject("image").getArray("sources");
                }
                return new JsonArray();
            case 16:
                return YoutubeParsingHelper.getImagesFromThumbnailsArray((JsonArray) obj);
            case 17:
                return YoutubeParsingHelper.getImagesFromThumbnailsArray((JsonArray) obj);
            case 18:
                YoutubeChannelHelper.ChannelHeader channelHeader3 = (YoutubeChannelHelper.ChannelHeader) obj;
                int ordinal2 = channelHeader3.f.ordinal();
                JsonObject jsonObject5 = channelHeader3.c;
                if (ordinal2 == 1) {
                    return jsonObject5.getObject("boxArt").getArray("thumbnails");
                }
                if (ordinal2 != 3) {
                    return jsonObject5.getObject("avatar").getArray("thumbnails");
                }
                JsonObject object2 = jsonObject5.getObject("content").getObject("pageHeaderViewModel").getObject("image");
                if (object2.has("contentPreviewImageViewModel")) {
                    return object2.getObject("contentPreviewImageViewModel").getObject("image").getArray("sources");
                }
                if (object2.has("decoratedAvatarViewModel")) {
                    return object2.getObject("decoratedAvatarViewModel").getObject("avatar").getObject("avatarViewModel").getObject("image").getArray("sources");
                }
                return new JsonArray();
            case 19:
                return YoutubeParsingHelper.getImagesFromThumbnailsArray((JsonArray) obj);
            case 20:
                return ((JsonObject) obj).getObject("tabRenderer");
            case 21:
                return ((JsonObject) obj).getObject("appendContinuationItemsAction");
            case 22:
                return ((JsonObject) obj).getObject("tabRenderer");
            case 23:
                return ((JsonObject) obj).getObject("continuationItemRenderer", (JsonObject) null);
            case 24:
                return ((JsonObject) obj).getObject("continuationEndpoint").getObject("continuationCommand").getString("token");
            case 25:
                try {
                    return JsonUtils.getString(((JsonObject) obj).getObject("itemSectionRenderer").getArray("contents").getObject(0), "continuationItemRenderer.continuationEndpoint.continuationCommand.token");
                } catch (ParsingException unused) {
                    return null;
                }
            case 26:
                List<ImageSuffix> list = YoutubeMixPlaylistExtractor.j;
                return ((JsonObject) obj).getObject("playlistPanelVideoRenderer");
            case 27:
                return ((JsonObject) obj).getObject("itemSectionRenderer");
            case 28:
                return ((JsonObject) obj).getArray("contents").getObject(0);
            default:
                return ((JsonObject) obj).getObject("musicResponsiveListItemRenderer", (JsonObject) null);
        }
    }

    public final /* synthetic */ Function compose(Function function) {
        switch (this.a) {
            case 0:
                return Function$CC.$default$compose(this, function);
            case 1:
                return Function$CC.$default$compose(this, function);
            case 2:
                return Function$CC.$default$compose(this, function);
            case 3:
                return Function$CC.$default$compose(this, function);
            case 4:
                return Function$CC.$default$compose(this, function);
            case 5:
                return Function$CC.$default$compose(this, function);
            case 6:
                return Function$CC.$default$compose(this, function);
            case 7:
                return Function$CC.$default$compose(this, function);
            case 8:
                return Function$CC.$default$compose(this, function);
            case 9:
                return Function$CC.$default$compose(this, function);
            case 10:
                return Function$CC.$default$compose(this, function);
            case 11:
                return Function$CC.$default$compose(this, function);
            case 12:
                return Function$CC.$default$compose(this, function);
            case 13:
                return Function$CC.$default$compose(this, function);
            case 14:
                return Function$CC.$default$compose(this, function);
            case 15:
                return Function$CC.$default$compose(this, function);
            case 16:
                return Function$CC.$default$compose(this, function);
            case 17:
                return Function$CC.$default$compose(this, function);
            case 18:
                return Function$CC.$default$compose(this, function);
            case 19:
                return Function$CC.$default$compose(this, function);
            case 20:
                return Function$CC.$default$compose(this, function);
            case 21:
                return Function$CC.$default$compose(this, function);
            case 22:
                return Function$CC.$default$compose(this, function);
            case 23:
                return Function$CC.$default$compose(this, function);
            case 24:
                return Function$CC.$default$compose(this, function);
            case 25:
                return Function$CC.$default$compose(this, function);
            case 26:
                return Function$CC.$default$compose(this, function);
            case 27:
                return Function$CC.$default$compose(this, function);
            case 28:
                return Function$CC.$default$compose(this, function);
            default:
                return Function$CC.$default$compose(this, function);
        }
    }
}
