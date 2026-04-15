package org.schabi.newpipe.extractor.services.youtube.extractors;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonWriter;
import j$.util.Collection$EL;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import org.mozilla.javascript.ES6Iterator;
import org.schabi.newpipe.extractor.ListExtractor;
import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.comments.CommentsExtractor;
import org.schabi.newpipe.extractor.comments.CommentsInfoItem;
import org.schabi.newpipe.extractor.comments.CommentsInfoItemExtractor;
import org.schabi.newpipe.extractor.comments.CommentsInfoItemsCollector;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.schabi.newpipe.extractor.localization.Localization;
import org.schabi.newpipe.extractor.localization.TimeAgoParser;
import org.schabi.newpipe.extractor.services.youtube.YoutubeParsingHelper;
import org.schabi.newpipe.extractor.utils.JsonUtils;
import org.schabi.newpipe.extractor.utils.Utils;

public class YoutubeCommentsExtractor extends CommentsExtractor {
    public boolean g;
    public JsonObject h;

    public YoutubeCommentsExtractor(StreamingService streamingService, ListLinkHandler listLinkHandler) {
        super(streamingService, listLinkHandler);
    }

    public static JsonObject c(JsonArray jsonArray, String str) throws ParsingException {
        Class<JsonObject> cls = JsonObject.class;
        return ((JsonObject) y2.ab(cls, 2, y2.aa(cls, 2, Collection$EL.stream(jsonArray))).filter(new y5(str, 10)).findFirst().orElseThrow(new cb(8))).getObject(MqttServiceConstants.PAYLOAD);
    }

    public final ListExtractor.InfoItemsPage<CommentsInfoItem> b(JsonObject jsonObject) throws ExtractionException {
        String str;
        String str2;
        String str3;
        Page page;
        Page page2;
        String str4;
        String str5;
        String str6;
        TimeAgoParser timeAgoParser;
        TimeAgoParser timeAgoParser2;
        JsonObject jsonObject2 = jsonObject;
        CommentsInfoItemsCollector commentsInfoItemsCollector = new CommentsInfoItemsCollector(getServiceId());
        JsonArray array = jsonObject2.getArray("onResponseReceivedEndpoints");
        String str7 = "continuationItemRenderer";
        String str8 = "appendContinuationItemsAction";
        String str9 = "reloadContinuationItemsCommand";
        if (!array.isEmpty()) {
            JsonObject object = array.getObject(array.size() - 1);
            if (object.has(str9)) {
                str5 = "reloadContinuationItemsCommand.continuationItems";
            } else if (object.has(str8)) {
                str5 = "appendContinuationItemsAction.continuationItems";
            }
            try {
                JsonArray jsonArray = new JsonArray((Collection<?>) JsonUtils.getArray(object, str5));
                int size = jsonArray.size() - 1;
                if (!jsonArray.isEmpty() && jsonArray.getObject(size).has(str7)) {
                    jsonArray.remove(size);
                }
                JsonArray array2 = jsonObject2.getObject("frameworkUpdates").getObject("entityBatchUpdate").getArray("mutations");
                String url = getUrl();
                TimeAgoParser timeAgoParser3 = getTimeAgoParser();
                Iterator it = jsonArray.iterator();
                while (it.hasNext()) {
                    Object next = it.next();
                    if (next instanceof JsonObject) {
                        JsonObject jsonObject3 = (JsonObject) next;
                        Iterator it2 = it;
                        TimeAgoParser timeAgoParser4 = timeAgoParser3;
                        String str10 = str7;
                        String str11 = str9;
                        if (jsonObject3.has("commentThreadRenderer")) {
                            JsonObject object2 = jsonObject3.getObject("commentThreadRenderer");
                            str6 = str8;
                            if (object2.has("commentViewModel")) {
                                JsonObject object3 = object2.getObject("commentViewModel").getObject("commentViewModel");
                                timeAgoParser2 = timeAgoParser4;
                                commentsInfoItemsCollector.commit((CommentsInfoItemExtractor) new wf(object3, object2.getObject("replies").getObject("commentRepliesRenderer"), c(array2, object3.getString("commentKey", "")).getObject("commentEntityPayload"), c(array2, object3.getString("toolbarStateKey", "")).getObject("engagementToolbarStateEntityPayload"), url, timeAgoParser4));
                            } else {
                                timeAgoParser2 = timeAgoParser4;
                                if (object2.has("comment")) {
                                    commentsInfoItemsCollector.commit((CommentsInfoItemExtractor) new YoutubeCommentsInfoItemExtractor(object2.getObject("comment").getObject("commentRenderer"), object2.getObject("replies").getObject("commentRepliesRenderer"), url, timeAgoParser2));
                                }
                            }
                            timeAgoParser = timeAgoParser2;
                        } else {
                            str6 = str8;
                            timeAgoParser = timeAgoParser4;
                            if (jsonObject3.has("commentViewModel")) {
                                JsonObject object4 = jsonObject3.getObject("commentViewModel");
                                commentsInfoItemsCollector.commit((CommentsInfoItemExtractor) new wf(object4, (JsonObject) null, c(array2, object4.getString("commentKey", "")).getObject("commentEntityPayload"), c(array2, object4.getString("toolbarStateKey", "")).getObject("engagementToolbarStateEntityPayload"), url, timeAgoParser));
                            } else if (jsonObject3.has("commentRenderer")) {
                                commentsInfoItemsCollector.commit((CommentsInfoItemExtractor) new YoutubeCommentsInfoItemExtractor(jsonObject3.getObject("commentRenderer"), (JsonObject) null, url, timeAgoParser));
                                timeAgoParser3 = timeAgoParser;
                                it = it2;
                                str7 = str10;
                                str9 = str11;
                                str8 = str6;
                            }
                        }
                        timeAgoParser3 = timeAgoParser;
                        it = it2;
                        str7 = str10;
                        str9 = str11;
                        str8 = str6;
                    }
                }
            } catch (Exception unused) {
                str3 = str7;
                str = str8;
                str2 = str9;
                page = null;
            }
        }
        str3 = str7;
        str = str8;
        str2 = str9;
        page = null;
        JsonArray array3 = jsonObject2.getArray("onResponseReceivedEndpoints");
        if (!array3.isEmpty()) {
            try {
                JsonObject object5 = array3.getObject(array3.size() - 1);
                JsonArray array4 = object5.getObject(str2, object5.getObject(str)).getArray("continuationItems");
                if (!array4.isEmpty()) {
                    JsonObject object6 = array4.getObject(array4.size() - 1).getObject(str3);
                    if (object6.has("button")) {
                        str4 = "button.buttonRenderer.command.continuationCommand.token";
                    } else {
                        str4 = "continuationEndpoint.continuationCommand.token";
                    }
                    page2 = new Page(getUrl(), JsonUtils.getString(object6, str4));
                    return new ListExtractor.InfoItemsPage<>(commentsInfoItemsCollector, page2);
                }
            } catch (Exception unused2) {
            }
        }
        page2 = page;
        return new ListExtractor.InfoItemsPage<>(commentsInfoItemsCollector, page2);
    }

    public int getCommentsCount() throws ExtractionException {
        a();
        if (this.g) {
            return -1;
        }
        try {
            return Integer.parseInt(Utils.removeNonDigitCharacters(YoutubeParsingHelper.getTextFromObject(this.h.getArray("onResponseReceivedEndpoints").getObject(0).getObject("reloadContinuationItemsCommand").getArray("continuationItems").getObject(0).getObject("commentsHeaderRenderer").getObject("countText"))));
        } catch (Exception e) {
            throw new ExtractionException("Unable to get comments count", e);
        }
    }

    public ListExtractor.InfoItemsPage<CommentsInfoItem> getInitialPage() throws IOException, ExtractionException {
        if (this.g) {
            return new ListExtractor.InfoItemsPage<>(Collections.emptyList(), (Page) null, Collections.emptyList());
        }
        return b(this.h);
    }

    public ListExtractor.InfoItemsPage<CommentsInfoItem> getPage(Page page) throws IOException, ExtractionException {
        if (this.g) {
            return new ListExtractor.InfoItemsPage<>(Collections.emptyList(), (Page) null, Collections.emptyList());
        }
        if (page == null || Utils.isNullOrEmpty(page.getId())) {
            throw new IllegalArgumentException("Page doesn't have the continuation.");
        }
        Localization extractorLocalization = getExtractorLocalization();
        return b(YoutubeParsingHelper.getJsonPostResponse(ES6Iterator.NEXT_METHOD, JsonWriter.string(YoutubeParsingHelper.prepareDesktopJsonBuilder(extractorLocalization, getExtractorContentCountry()).value("continuation", page.getId()).done()).getBytes(StandardCharsets.UTF_8), extractorLocalization));
    }

    public boolean isCommentsDisabled() {
        return this.g;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v20, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v4, resolved type: java.lang.String} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onFetchPage(org.schabi.newpipe.extractor.downloader.Downloader r7) throws java.io.IOException, org.schabi.newpipe.extractor.exceptions.ExtractionException {
        /*
            r6 = this;
            org.schabi.newpipe.extractor.localization.Localization r7 = r6.getExtractorLocalization()
            org.schabi.newpipe.extractor.localization.ContentCountry r0 = r6.getExtractorContentCountry()
            com.grack.nanojson.JsonBuilder r0 = org.schabi.newpipe.extractor.services.youtube.YoutubeParsingHelper.prepareDesktopJsonBuilder(r7, r0)
            java.lang.String r1 = "videoId"
            java.lang.String r2 = r6.getId()
            com.grack.nanojson.JsonBuilder r0 = r0.value((java.lang.String) r1, (java.lang.String) r2)
            java.lang.Object r0 = r0.done()
            java.lang.String r0 = com.grack.nanojson.JsonWriter.string(r0)
            java.nio.charset.Charset r1 = java.nio.charset.StandardCharsets.UTF_8
            byte[] r0 = r0.getBytes(r1)
            java.lang.String r1 = "next"
            com.grack.nanojson.JsonObject r0 = org.schabi.newpipe.extractor.services.youtube.YoutubeParsingHelper.getJsonPostResponse(r1, r0, r7)
            r2 = 0
            java.lang.String r3 = "contents.twoColumnWatchNextResults.results.results.contents"
            com.grack.nanojson.JsonArray r0 = org.schabi.newpipe.extractor.utils.JsonUtils.getArray(r0, r3)     // Catch:{ ParsingException -> 0x0032 }
            goto L_0x0034
        L_0x0032:
            r0 = r2
        L_0x0034:
            if (r0 != 0) goto L_0x0037
            goto L_0x006d
        L_0x0037:
            j$.util.stream.Stream r0 = j$.util.Collection$EL.stream(r0)
            java.lang.Class<com.grack.nanojson.JsonObject> r3 = com.grack.nanojson.JsonObject.class
            r4 = 1
            j$.util.stream.Stream r0 = defpackage.y2.aa(r3, r4, r0)
            j$.util.stream.Stream r0 = defpackage.y2.ab(r3, r4, r0)
            bz r3 = new bz
            r5 = 20
            r3.<init>(r5)
            j$.util.stream.Stream r0 = r0.filter(r3)
            j$.util.Optional r0 = r0.findFirst()
            p8 r3 = new p8
            r5 = 25
            r3.<init>(r5)
            j$.util.Optional r0 = r0.map(r3)
            java.lang.Object r0 = r0.orElse(r2)
            r2 = r0
            java.lang.String r2 = (java.lang.String) r2
            if (r2 != 0) goto L_0x006a
            goto L_0x006b
        L_0x006a:
            r4 = 0
        L_0x006b:
            r6.g = r4
        L_0x006d:
            if (r2 != 0) goto L_0x0070
            return
        L_0x0070:
            org.schabi.newpipe.extractor.localization.ContentCountry r0 = r6.getExtractorContentCountry()
            com.grack.nanojson.JsonBuilder r0 = org.schabi.newpipe.extractor.services.youtube.YoutubeParsingHelper.prepareDesktopJsonBuilder(r7, r0)
            java.lang.String r3 = "continuation"
            com.grack.nanojson.JsonBuilder r0 = r0.value((java.lang.String) r3, (java.lang.String) r2)
            java.lang.Object r0 = r0.done()
            java.lang.String r0 = com.grack.nanojson.JsonWriter.string(r0)
            java.nio.charset.Charset r2 = java.nio.charset.StandardCharsets.UTF_8
            byte[] r0 = r0.getBytes(r2)
            com.grack.nanojson.JsonObject r7 = org.schabi.newpipe.extractor.services.youtube.YoutubeParsingHelper.getJsonPostResponse(r1, r0, r7)
            r6.h = r7
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeCommentsExtractor.onFetchPage(org.schabi.newpipe.extractor.downloader.Downloader):void");
    }
}
