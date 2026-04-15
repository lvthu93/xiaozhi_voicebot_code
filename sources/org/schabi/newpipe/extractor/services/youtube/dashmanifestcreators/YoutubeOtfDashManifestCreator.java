package org.schabi.newpipe.extractor.services.youtube.dashmanifestcreators;

import j$.util.Objects;
import java.util.Arrays;
import org.schabi.newpipe.extractor.downloader.Response;
import org.schabi.newpipe.extractor.services.youtube.DeliveryType;
import org.schabi.newpipe.extractor.services.youtube.ItagItem;
import org.schabi.newpipe.extractor.utils.ManifestCreatorCache;
import org.schabi.newpipe.extractor.utils.Pair;
import org.schabi.newpipe.extractor.utils.Utils;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public final class YoutubeOtfDashManifestCreator {
    public static final ManifestCreatorCache<String, String> a = new ManifestCreatorCache<>();

    public static String fromOtfStreamingUrl(String str, ItagItem itagItem, long j) throws CreationException {
        long j2;
        long j3;
        String str2 = str;
        ItagItem itagItem2 = itagItem;
        ManifestCreatorCache<String, String> manifestCreatorCache = a;
        if (manifestCreatorCache.containsKey(str2)) {
            Pair<Integer, String> pair = manifestCreatorCache.get(str2);
            Objects.requireNonNull(pair);
            return pair.getSecond();
        }
        DeliveryType deliveryType = DeliveryType.OTF;
        Response initializationResponse = YoutubeDashManifestCreatorsUtils.getInitializationResponse(str2, itagItem2, deliveryType);
        String replace = initializationResponse.latestUrl().replace("&sq=0", "").replace("&rn=0", "").replace("&alr=yes", "");
        int responseCode = initializationResponse.responseCode();
        if (responseCode == 200) {
            try {
                int i = 1;
                char c = 0;
                String[] split = initializationResponse.responseBody().split("Segment-Durations-Ms: ")[1].split("\n")[0].split(",");
                int length = split.length - 1;
                if (Utils.isBlank(split[length])) {
                    split = (String[]) Arrays.copyOf(split, length);
                }
                String[] strArr = split;
                try {
                    int length2 = strArr.length;
                    int i2 = 0;
                    j2 = 0;
                    while (i2 < length2) {
                        String[] split2 = strArr[i2].split("\\(r=");
                        if (split2.length > i) {
                            j3 = Long.parseLong(Utils.removeNonDigitCharacters(split2[i]));
                        } else {
                            j3 = 0;
                        }
                        long parseInt = (long) Integer.parseInt(split2[c]);
                        j2 += (j3 * parseInt) + parseInt;
                        i2++;
                        i = 1;
                        c = 0;
                    }
                } catch (NumberFormatException e) {
                    throw new CreationException("Could not get stream length from sequences list", e);
                } catch (CreationException unused) {
                    j2 = j * 1000;
                }
                Document generateDocumentAndDoCommonElementsGeneration = YoutubeDashManifestCreatorsUtils.generateDocumentAndDoCommonElementsGeneration(itagItem2, j2);
                YoutubeDashManifestCreatorsUtils.generateSegmentTemplateElement(generateDocumentAndDoCommonElementsGeneration, replace, deliveryType);
                YoutubeDashManifestCreatorsUtils.generateSegmentTimelineElement(generateDocumentAndDoCommonElementsGeneration);
                try {
                    Element element = (Element) generateDocumentAndDoCommonElementsGeneration.getElementsByTagName("SegmentTimeline").item(0);
                    for (String split3 : strArr) {
                        Element createElement = generateDocumentAndDoCommonElementsGeneration.createElement("S");
                        String[] split4 = split3.split("\\(r=");
                        Integer.parseInt(split4[0]);
                        if (split4.length > 1) {
                            YoutubeDashManifestCreatorsUtils.setAttribute(createElement, generateDocumentAndDoCommonElementsGeneration, "r", String.valueOf(Integer.parseInt(Utils.removeNonDigitCharacters(split4[1]))));
                        }
                        YoutubeDashManifestCreatorsUtils.setAttribute(createElement, generateDocumentAndDoCommonElementsGeneration, "d", split4[0]);
                        element.appendChild(createElement);
                    }
                    return YoutubeDashManifestCreatorsUtils.buildAndCacheResult(str2, generateDocumentAndDoCommonElementsGeneration, manifestCreatorCache);
                } catch (IllegalStateException | IndexOutOfBoundsException | NumberFormatException | DOMException e2) {
                    throw CreationException.couldNotAddElement("segment (S)", (Exception) e2);
                }
            } catch (Exception e3) {
                throw new CreationException("Could not get segment durations", e3);
            }
        } else {
            throw new CreationException(y2.f("Could not get the initialization URL: response code ", responseCode));
        }
    }

    public static ManifestCreatorCache<String, String> getCache() {
        return a;
    }
}
