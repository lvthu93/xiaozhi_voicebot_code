package org.schabi.newpipe.extractor.services.youtube;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonObject;
import j$.util.Collection$EL;
import j$.util.Objects;
import j$.util.stream.Collectors;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.schabi.newpipe.extractor.MetaInfo;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.stream.Description;
import org.schabi.newpipe.extractor.utils.Utils;

public final class YoutubeMetaInfoHelper {
    public static List<MetaInfo> getMetaInfo(JsonArray jsonArray) throws ParsingException {
        String str;
        String urlFromNavigationEndpoint;
        ArrayList arrayList = new ArrayList();
        Iterator it = jsonArray.iterator();
        while (it.hasNext()) {
            JsonObject jsonObject = (JsonObject) it.next();
            if (jsonObject.has("itemSectionRenderer")) {
                Iterator it2 = jsonObject.getObject("itemSectionRenderer").getArray("contents").iterator();
                while (it2.hasNext()) {
                    JsonObject jsonObject2 = (JsonObject) it2.next();
                    if (jsonObject2.has("infoPanelContentRenderer")) {
                        JsonObject object = jsonObject2.getObject("infoPanelContentRenderer");
                        MetaInfo metaInfo = new MetaInfo();
                        StringBuilder sb = new StringBuilder();
                        Iterator it3 = object.getArray("paragraphs").iterator();
                        while (it3.hasNext()) {
                            Object next = it3.next();
                            if (sb.length() != 0) {
                                sb.append("<br>");
                            }
                            sb.append(YoutubeParsingHelper.getTextFromObject((JsonObject) next));
                        }
                        metaInfo.setContent(new Description(sb.toString(), 1));
                        if (object.has("sourceEndpoint")) {
                            try {
                                String extractCachedUrlIfNeeded = YoutubeParsingHelper.extractCachedUrlIfNeeded(YoutubeParsingHelper.getUrlFromNavigationEndpoint(object.getObject("sourceEndpoint")));
                                Objects.requireNonNull(extractCachedUrlIfNeeded);
                                metaInfo.addUrl(new URL(extractCachedUrlIfNeeded));
                                String textFromObject = YoutubeParsingHelper.getTextFromObject(object.getObject("inlineSource"));
                                if (!Utils.isNullOrEmpty(textFromObject)) {
                                    metaInfo.addUrlText(textFromObject);
                                } else {
                                    throw new ParsingException("Could not get metadata info link text.");
                                }
                            } catch (NullPointerException | MalformedURLException e) {
                                throw new ParsingException("Could not get metadata info URL", e);
                            }
                        }
                        arrayList.add(metaInfo);
                    }
                    if (jsonObject2.has("clarificationRenderer")) {
                        JsonObject object2 = jsonObject2.getObject("clarificationRenderer");
                        MetaInfo metaInfo2 = new MetaInfo();
                        String textFromObject2 = YoutubeParsingHelper.getTextFromObject(object2.getObject("contentTitle"));
                        String textFromObject3 = YoutubeParsingHelper.getTextFromObject(object2.getObject("text"));
                        if (textFromObject2 == null || textFromObject3 == null) {
                            throw new ParsingException("Could not extract clarification renderer content");
                        }
                        metaInfo2.setTitle(textFromObject2);
                        metaInfo2.setContent(new Description(textFromObject3, 3));
                        if (object2.has("actionButton")) {
                            JsonObject object3 = object2.getObject("actionButton").getObject("buttonRenderer");
                            try {
                                String extractCachedUrlIfNeeded2 = YoutubeParsingHelper.extractCachedUrlIfNeeded(YoutubeParsingHelper.getUrlFromNavigationEndpoint(object3.getObject("command")));
                                Objects.requireNonNull(extractCachedUrlIfNeeded2);
                                metaInfo2.addUrl(new URL(extractCachedUrlIfNeeded2));
                                String textFromObject4 = YoutubeParsingHelper.getTextFromObject(object3.getObject("text"));
                                if (!Utils.isNullOrEmpty(textFromObject4)) {
                                    metaInfo2.addUrlText(textFromObject4);
                                } else {
                                    throw new ParsingException("Could not get metadata info link text.");
                                }
                            } catch (NullPointerException | MalformedURLException e2) {
                                throw new ParsingException("Could not get metadata info URL", e2);
                            }
                        }
                        if (object2.has("secondaryEndpoint") && object2.has("secondarySource") && (urlFromNavigationEndpoint = YoutubeParsingHelper.getUrlFromNavigationEndpoint(object2.getObject("secondaryEndpoint"))) != null && !YoutubeParsingHelper.isGoogleURL(urlFromNavigationEndpoint)) {
                            try {
                                metaInfo2.addUrl(new URL(urlFromNavigationEndpoint));
                                String textFromObject5 = YoutubeParsingHelper.getTextFromObject(object2.getObject("secondarySource"));
                                if (textFromObject5 != null) {
                                    urlFromNavigationEndpoint = textFromObject5;
                                }
                                metaInfo2.addUrlText(urlFromNavigationEndpoint);
                            } catch (MalformedURLException e3) {
                                throw new ParsingException("Could not get metadata info secondary URL", e3);
                            }
                        }
                        arrayList.add(metaInfo2);
                    }
                    if (jsonObject2.has("emergencyOneboxRenderer")) {
                        List<JsonObject> list = (List) Collection$EL.stream(jsonObject2.getObject("emergencyOneboxRenderer").values()).filter(new bz(10)).map(new p8(11)).collect(Collectors.toList());
                        if (!list.isEmpty()) {
                            for (JsonObject jsonObject3 : list) {
                                MetaInfo metaInfo3 = new MetaInfo();
                                String textFromObjectOrThrow = YoutubeParsingHelper.getTextFromObjectOrThrow(jsonObject3.getObject("title"), "title");
                                if (jsonObject3.has("actionText")) {
                                    str = "\n" + YoutubeParsingHelper.getTextFromObjectOrThrow(jsonObject3.getObject("actionText"), "action");
                                } else if (jsonObject3.has("contacts")) {
                                    JsonArray array = jsonObject3.getArray("contacts");
                                    StringBuilder sb2 = new StringBuilder();
                                    for (int i = 0; i < array.size(); i++) {
                                        sb2.append("\n");
                                        sb2.append(YoutubeParsingHelper.getTextFromObjectOrThrow(array.getObject(i).getObject("actionText"), "contacts.actionText"));
                                    }
                                    str = sb2.toString();
                                } else {
                                    str = "";
                                }
                                String textFromObjectOrThrow2 = YoutubeParsingHelper.getTextFromObjectOrThrow(jsonObject3.getObject("detailsText"), "details");
                                String textFromObjectOrThrow3 = YoutubeParsingHelper.getTextFromObjectOrThrow(jsonObject3.getObject("navigationText"), "urlText");
                                metaInfo3.setTitle(textFromObjectOrThrow);
                                metaInfo3.setContent(new Description(y2.x(textFromObjectOrThrow2, str), 3));
                                metaInfo3.addUrlText(textFromObjectOrThrow3);
                                String urlFromNavigationEndpoint2 = YoutubeParsingHelper.getUrlFromNavigationEndpoint(jsonObject3.getObject("navigationEndpoint"));
                                if (urlFromNavigationEndpoint2 != null) {
                                    try {
                                        metaInfo3.addUrl(new URL(Utils.replaceHttpWithHttps(urlFromNavigationEndpoint2)));
                                        arrayList.add(metaInfo3);
                                    } catch (MalformedURLException e4) {
                                        throw new ParsingException("Could not parse emergency renderer url", e4);
                                    }
                                } else {
                                    throw new ParsingException("Could not extract emergency renderer url");
                                }
                            }
                            continue;
                        } else {
                            throw new ParsingException("Could not extract any meta info from emergency renderer");
                        }
                    }
                }
                continue;
            }
        }
        return arrayList;
    }
}
