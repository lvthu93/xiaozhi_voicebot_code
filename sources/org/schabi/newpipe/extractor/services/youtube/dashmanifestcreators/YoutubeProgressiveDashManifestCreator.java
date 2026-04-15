package org.schabi.newpipe.extractor.services.youtube.dashmanifestcreators;

import j$.util.Objects;
import org.schabi.newpipe.extractor.services.youtube.ItagItem;
import org.schabi.newpipe.extractor.utils.ManifestCreatorCache;
import org.schabi.newpipe.extractor.utils.Pair;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public final class YoutubeProgressiveDashManifestCreator {
    public static final ManifestCreatorCache<String, String> a = new ManifestCreatorCache<>();

    public static String fromProgressiveStreamingUrl(String str, ItagItem itagItem, long j) throws CreationException {
        ManifestCreatorCache<String, String> manifestCreatorCache = a;
        if (manifestCreatorCache.containsKey(str)) {
            Pair<Integer, String> pair = manifestCreatorCache.get(str);
            Objects.requireNonNull(pair);
            return pair.getSecond();
        }
        long approxDurationMs = itagItem.getApproxDurationMs();
        if (approxDurationMs == -1) {
            if (j > 0) {
                approxDurationMs = 1000 * j;
            } else {
                throw CreationException.couldNotAddElement("MPD", "the duration of the stream could not be determined and durationSecondsFallback is <= 0");
            }
        }
        Document generateDocumentAndDoCommonElementsGeneration = YoutubeDashManifestCreatorsUtils.generateDocumentAndDoCommonElementsGeneration(itagItem, approxDurationMs);
        try {
            Element createElement = generateDocumentAndDoCommonElementsGeneration.createElement("BaseURL");
            createElement.setTextContent(str);
            ((Element) generateDocumentAndDoCommonElementsGeneration.getElementsByTagName("Representation").item(0)).appendChild(createElement);
            try {
                Element element = (Element) generateDocumentAndDoCommonElementsGeneration.getElementsByTagName("Representation").item(0);
                Element createElement2 = generateDocumentAndDoCommonElementsGeneration.createElement("SegmentBase");
                String str2 = itagItem.getIndexStart() + "-" + itagItem.getIndexEnd();
                if (itagItem.getIndexStart() < 0 || itagItem.getIndexEnd() < 0) {
                    throw CreationException.couldNotAddElement("SegmentBase", "ItagItem's indexStart or indexEnd are < 0: " + str2);
                }
                YoutubeDashManifestCreatorsUtils.setAttribute(createElement2, generateDocumentAndDoCommonElementsGeneration, "indexRange", str2);
                element.appendChild(createElement2);
                try {
                    Element element2 = (Element) generateDocumentAndDoCommonElementsGeneration.getElementsByTagName("SegmentBase").item(0);
                    Element createElement3 = generateDocumentAndDoCommonElementsGeneration.createElement("Initialization");
                    String str3 = itagItem.getInitStart() + "-" + itagItem.getInitEnd();
                    if (itagItem.getInitStart() < 0 || itagItem.getInitEnd() < 0) {
                        throw CreationException.couldNotAddElement("Initialization", "ItagItem's initStart and/or initEnd are/is < 0: " + str3);
                    }
                    YoutubeDashManifestCreatorsUtils.setAttribute(createElement3, generateDocumentAndDoCommonElementsGeneration, "range", str3);
                    element2.appendChild(createElement3);
                    return YoutubeDashManifestCreatorsUtils.buildAndCacheResult(str, generateDocumentAndDoCommonElementsGeneration, manifestCreatorCache);
                } catch (DOMException e) {
                    throw CreationException.couldNotAddElement("Initialization", (Exception) e);
                }
            } catch (DOMException e2) {
                throw CreationException.couldNotAddElement("SegmentBase", (Exception) e2);
            }
        } catch (DOMException e3) {
            throw CreationException.couldNotAddElement("BaseURL", (Exception) e3);
        }
    }

    public static ManifestCreatorCache<String, String> getCache() {
        return a;
    }
}
