package org.schabi.newpipe.extractor.services.youtube.dashmanifestcreators;

import j$.util.Objects;
import java.util.List;
import java.util.Map;
import org.schabi.newpipe.extractor.downloader.Response;
import org.schabi.newpipe.extractor.services.youtube.DeliveryType;
import org.schabi.newpipe.extractor.services.youtube.ItagItem;
import org.schabi.newpipe.extractor.utils.ManifestCreatorCache;
import org.schabi.newpipe.extractor.utils.Pair;
import org.schabi.newpipe.extractor.utils.Utils;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public final class YoutubePostLiveStreamDvrDashManifestCreator {
    public static final ManifestCreatorCache<String, String> a = new ManifestCreatorCache<>();

    public static String fromPostLiveStreamDvrStreamingUrl(String str, ItagItem itagItem, int i, long j) throws CreationException {
        ManifestCreatorCache<String, String> manifestCreatorCache = a;
        if (manifestCreatorCache.containsKey(str)) {
            Pair<Integer, String> pair = manifestCreatorCache.get(str);
            Objects.requireNonNull(pair);
            return pair.getSecond();
        } else if (i > 0) {
            DeliveryType deliveryType = DeliveryType.LIVE;
            try {
                Response initializationResponse = YoutubeDashManifestCreatorsUtils.getInitializationResponse(str, itagItem, deliveryType);
                String replace = initializationResponse.latestUrl().replace("&sq=0", "").replace("&rn=0", "").replace("&alr=yes", "");
                int responseCode = initializationResponse.responseCode();
                if (responseCode == 200) {
                    Map<String, List<String>> responseHeaders = initializationResponse.responseHeaders();
                    String str2 = (String) responseHeaders.get("X-Head-Time-Millis").get(0);
                    String str3 = (String) responseHeaders.get("X-Head-Seqnum").get(0);
                    if (!Utils.isNullOrEmpty(str3)) {
                        try {
                            j = Long.parseLong(str2);
                        } catch (NumberFormatException unused) {
                        }
                        Document generateDocumentAndDoCommonElementsGeneration = YoutubeDashManifestCreatorsUtils.generateDocumentAndDoCommonElementsGeneration(itagItem, j);
                        YoutubeDashManifestCreatorsUtils.generateSegmentTemplateElement(generateDocumentAndDoCommonElementsGeneration, replace, deliveryType);
                        YoutubeDashManifestCreatorsUtils.generateSegmentTimelineElement(generateDocumentAndDoCommonElementsGeneration);
                        try {
                            Element createElement = generateDocumentAndDoCommonElementsGeneration.createElement("S");
                            YoutubeDashManifestCreatorsUtils.setAttribute(createElement, generateDocumentAndDoCommonElementsGeneration, "d", String.valueOf(i * 1000));
                            YoutubeDashManifestCreatorsUtils.setAttribute(createElement, generateDocumentAndDoCommonElementsGeneration, "r", str3);
                            ((Element) generateDocumentAndDoCommonElementsGeneration.getElementsByTagName("SegmentTimeline").item(0)).appendChild(createElement);
                            return YoutubeDashManifestCreatorsUtils.buildAndCacheResult(str, generateDocumentAndDoCommonElementsGeneration, manifestCreatorCache);
                        } catch (DOMException e) {
                            throw CreationException.couldNotAddElement("segment (S)", (Exception) e);
                        }
                    } else {
                        throw new CreationException("Could not get the number of segments");
                    }
                } else {
                    throw new CreationException("Could not get the initialization sequence: response code " + responseCode);
                }
            } catch (IndexOutOfBoundsException e2) {
                throw new CreationException("Could not get the value of the X-Head-Time-Millis or the X-Head-Seqnum header", e2);
            }
        } else {
            throw new CreationException(y2.f("targetDurationSec value is <= 0: ", i));
        }
    }

    public static ManifestCreatorCache<String, String> getCache() {
        return a;
    }
}
