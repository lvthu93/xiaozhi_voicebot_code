package org.schabi.newpipe.extractor.services.youtube.dashmanifestcreators;

import fi.iki.elonen.NanoHTTPD;
import j$.util.Objects;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.mozilla.javascript.ES6Iterator;
import org.schabi.newpipe.extractor.MediaFormat;
import org.schabi.newpipe.extractor.NewPipe;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.downloader.Response;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.localization.Localization;
import org.schabi.newpipe.extractor.services.youtube.DeliveryType;
import org.schabi.newpipe.extractor.services.youtube.ItagItem;
import org.schabi.newpipe.extractor.services.youtube.YoutubeParsingHelper;
import org.schabi.newpipe.extractor.stream.AudioTrackType;
import org.schabi.newpipe.extractor.utils.ManifestCreatorCache;
import org.schabi.newpipe.extractor.utils.Utils;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public final class YoutubeDashManifestCreatorsUtils {
    public static String a(Document document) throws TransformerException {
        TransformerFactory newInstance = TransformerFactory.newInstance();
        try {
            newInstance.setAttribute("http://javax.xml.XMLConstants/property/accessExternalDTD", "");
            newInstance.setAttribute("http://javax.xml.XMLConstants/property/accessExternalSchema", "");
        } catch (Exception unused) {
        }
        Transformer newTransformer = newInstance.newTransformer();
        newTransformer.setOutputProperty("version", "1.0");
        newTransformer.setOutputProperty("encoding", "UTF-8");
        newTransformer.setOutputProperty("standalone", "no");
        StringWriter stringWriter = new StringWriter();
        newTransformer.transform(new DOMSource(document), new StreamResult(stringWriter));
        return stringWriter.toString();
    }

    public static String buildAndCacheResult(String str, Document document, ManifestCreatorCache<String, String> manifestCreatorCache) throws CreationException {
        try {
            String a = a(document);
            manifestCreatorCache.put(str, a);
            return a;
        } catch (Exception e) {
            throw new CreationException("Could not convert the DASH manifest generated to a string", e);
        }
    }

    public static void generateAdaptationSetElement(Document document, ItagItem itagItem) throws CreationException {
        Locale audioLocale;
        try {
            Element element = (Element) document.getElementsByTagName("Period").item(0);
            Element createElement = document.createElement("AdaptationSet");
            setAttribute(createElement, document, "id", "0");
            MediaFormat mediaFormat = itagItem.getMediaFormat();
            if (mediaFormat == null || Utils.isNullOrEmpty(mediaFormat.getMimeType())) {
                throw CreationException.couldNotAddElement("AdaptationSet", "the MediaFormat or its mime type is null or empty");
            }
            if (itagItem.g == ItagItem.ItagType.AUDIO && (audioLocale = itagItem.getAudioLocale()) != null) {
                String language = audioLocale.getLanguage();
                if (!language.isEmpty()) {
                    setAttribute(createElement, document, "lang", language);
                }
            }
            setAttribute(createElement, document, "mimeType", mediaFormat.getMimeType());
            setAttribute(createElement, document, "subsegmentAlignment", "true");
            element.appendChild(createElement);
        } catch (DOMException e) {
            throw CreationException.couldNotAddElement("AdaptationSet", (Exception) e);
        }
    }

    public static void generateAudioChannelConfigurationElement(Document document, ItagItem itagItem) throws CreationException {
        try {
            Element element = (Element) document.getElementsByTagName("Representation").item(0);
            Element createElement = document.createElement("AudioChannelConfiguration");
            setAttribute(createElement, document, "schemeIdUri", "urn:mpeg:dash:23003:3:audio_channel_configuration:2011");
            if (itagItem.getAudioChannels() > 0) {
                setAttribute(createElement, document, ES6Iterator.VALUE_PROPERTY, String.valueOf(itagItem.getAudioChannels()));
                element.appendChild(createElement);
                return;
            }
            throw new CreationException("the number of audioChannels in the ItagItem is <= 0: " + itagItem.getAudioChannels());
        } catch (DOMException e) {
            throw CreationException.couldNotAddElement("AudioChannelConfiguration", (Exception) e);
        }
    }

    public static Document generateDocumentAndDoCommonElementsGeneration(ItagItem itagItem, long j) throws CreationException {
        Document generateDocumentAndMpdElement = generateDocumentAndMpdElement(j);
        generatePeriodElement(generateDocumentAndMpdElement);
        generateAdaptationSetElement(generateDocumentAndMpdElement, itagItem);
        generateRoleElement(generateDocumentAndMpdElement, itagItem);
        generateRepresentationElement(generateDocumentAndMpdElement, itagItem);
        if (itagItem.g == ItagItem.ItagType.AUDIO) {
            generateAudioChannelConfigurationElement(generateDocumentAndMpdElement, itagItem);
        }
        return generateDocumentAndMpdElement;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(9:0|1|2|3|4|5|6|7|8) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:6:0x0010 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.w3c.dom.Document generateDocumentAndMpdElement(long r8) throws org.schabi.newpipe.extractor.services.youtube.dashmanifestcreators.CreationException {
        /*
            java.lang.String r0 = ""
            javax.xml.parsers.DocumentBuilderFactory r1 = javax.xml.parsers.DocumentBuilderFactory.newInstance()     // Catch:{ Exception -> 0x006a }
            java.lang.String r2 = "http://javax.xml.XMLConstants/property/accessExternalDTD"
            r1.setAttribute(r2, r0)     // Catch:{ Exception -> 0x0010 }
            java.lang.String r2 = "http://javax.xml.XMLConstants/property/accessExternalSchema"
            r1.setAttribute(r2, r0)     // Catch:{ Exception -> 0x0010 }
        L_0x0010:
            javax.xml.parsers.DocumentBuilder r0 = r1.newDocumentBuilder()     // Catch:{ Exception -> 0x006a }
            org.w3c.dom.Document r0 = r0.newDocument()     // Catch:{ Exception -> 0x006a }
            java.lang.String r1 = "MPD"
            org.w3c.dom.Element r1 = r0.createElement(r1)     // Catch:{ Exception -> 0x006a }
            r0.appendChild(r1)     // Catch:{ Exception -> 0x006a }
            java.lang.String r2 = "xmlns:xsi"
            java.lang.String r3 = "http://www.w3.org/2001/XMLSchema-instance"
            setAttribute(r1, r0, r2, r3)     // Catch:{ Exception -> 0x006a }
            java.lang.String r2 = "xmlns"
            java.lang.String r3 = "urn:mpeg:DASH:schema:MPD:2011"
            setAttribute(r1, r0, r2, r3)     // Catch:{ Exception -> 0x006a }
            java.lang.String r2 = "xsi:schemaLocation"
            java.lang.String r3 = "urn:mpeg:DASH:schema:MPD:2011 DASH-MPD.xsd"
            setAttribute(r1, r0, r2, r3)     // Catch:{ Exception -> 0x006a }
            java.lang.String r2 = "minBufferTime"
            java.lang.String r3 = "PT1.500S"
            setAttribute(r1, r0, r2, r3)     // Catch:{ Exception -> 0x006a }
            java.lang.String r2 = "profiles"
            java.lang.String r3 = "urn:mpeg:dash:profile:full:2011"
            setAttribute(r1, r0, r2, r3)     // Catch:{ Exception -> 0x006a }
            java.lang.String r2 = "type"
            java.lang.String r3 = "static"
            setAttribute(r1, r0, r2, r3)     // Catch:{ Exception -> 0x006a }
            java.lang.String r2 = "mediaPresentationDuration"
            java.util.Locale r3 = java.util.Locale.ENGLISH     // Catch:{ Exception -> 0x006a }
            java.lang.String r4 = "PT%.3fS"
            r5 = 1
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ Exception -> 0x006a }
            double r8 = (double) r8     // Catch:{ Exception -> 0x006a }
            r6 = 4652007308841189376(0x408f400000000000, double:1000.0)
            double r8 = r8 / r6
            java.lang.Double r8 = java.lang.Double.valueOf(r8)     // Catch:{ Exception -> 0x006a }
            r9 = 0
            r5[r9] = r8     // Catch:{ Exception -> 0x006a }
            java.lang.String r8 = java.lang.String.format(r3, r4, r5)     // Catch:{ Exception -> 0x006a }
            setAttribute(r1, r0, r2, r8)     // Catch:{ Exception -> 0x006a }
            return r0
        L_0x006a:
            r8 = move-exception
            org.schabi.newpipe.extractor.services.youtube.dashmanifestcreators.CreationException r9 = new org.schabi.newpipe.extractor.services.youtube.dashmanifestcreators.CreationException
            java.lang.String r0 = "Could not generate the DASH manifest or append the MPD doc to it"
            r9.<init>(r0, r8)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: org.schabi.newpipe.extractor.services.youtube.dashmanifestcreators.YoutubeDashManifestCreatorsUtils.generateDocumentAndMpdElement(long):org.w3c.dom.Document");
    }

    public static void generatePeriodElement(Document document) throws CreationException {
        try {
            ((Element) document.getElementsByTagName("MPD").item(0)).appendChild(document.createElement("Period"));
        } catch (DOMException e) {
            throw CreationException.couldNotAddElement("Period", (Exception) e);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0052, code lost:
        if (r1 == org.schabi.newpipe.extractor.services.youtube.ItagItem.ItagType.VIDEO_ONLY) goto L_0x0054;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void generateRepresentationElement(org.w3c.dom.Document r7, org.schabi.newpipe.extractor.services.youtube.ItagItem r8) throws org.schabi.newpipe.extractor.services.youtube.dashmanifestcreators.CreationException {
        /*
            java.lang.String r0 = "1"
            java.lang.String r1 = "AdaptationSet"
            java.lang.String r2 = "Representation"
            org.w3c.dom.NodeList r3 = r7.getElementsByTagName(r1)     // Catch:{ DOMException -> 0x00c3 }
            r4 = 0
            org.w3c.dom.Node r3 = r3.item(r4)     // Catch:{ DOMException -> 0x00c3 }
            org.w3c.dom.Element r3 = (org.w3c.dom.Element) r3     // Catch:{ DOMException -> 0x00c3 }
            org.w3c.dom.Element r4 = r7.createElement(r2)     // Catch:{ DOMException -> 0x00c3 }
            int r5 = r8.f     // Catch:{ DOMException -> 0x00c3 }
            if (r5 <= 0) goto L_0x00bc
            java.lang.String r6 = "id"
            java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ DOMException -> 0x00c3 }
            setAttribute(r4, r7, r6, r5)     // Catch:{ DOMException -> 0x00c3 }
            java.lang.String r5 = r8.getCodec()     // Catch:{ DOMException -> 0x00c3 }
            boolean r6 = org.schabi.newpipe.extractor.utils.Utils.isNullOrEmpty((java.lang.String) r5)     // Catch:{ DOMException -> 0x00c3 }
            if (r6 != 0) goto L_0x00b5
            java.lang.String r1 = "codecs"
            setAttribute(r4, r7, r1, r5)     // Catch:{ DOMException -> 0x00c3 }
            java.lang.String r1 = "startWithSAP"
            setAttribute(r4, r7, r1, r0)     // Catch:{ DOMException -> 0x00c3 }
            java.lang.String r1 = "maxPlayoutRate"
            setAttribute(r4, r7, r1, r0)     // Catch:{ DOMException -> 0x00c3 }
            int r0 = r8.getBitrate()     // Catch:{ DOMException -> 0x00c3 }
            if (r0 <= 0) goto L_0x00ae
            java.lang.String r1 = "bandwidth"
            java.lang.String r0 = java.lang.String.valueOf(r0)     // Catch:{ DOMException -> 0x00c3 }
            setAttribute(r4, r7, r1, r0)     // Catch:{ DOMException -> 0x00c3 }
            org.schabi.newpipe.extractor.services.youtube.ItagItem$ItagType r0 = org.schabi.newpipe.extractor.services.youtube.ItagItem.ItagType.VIDEO     // Catch:{ DOMException -> 0x00c3 }
            org.schabi.newpipe.extractor.services.youtube.ItagItem$ItagType r1 = r8.g
            if (r1 == r0) goto L_0x0054
            org.schabi.newpipe.extractor.services.youtube.ItagItem$ItagType r0 = org.schabi.newpipe.extractor.services.youtube.ItagItem.ItagType.VIDEO_ONLY     // Catch:{ DOMException -> 0x00c3 }
            if (r1 != r0) goto L_0x008f
        L_0x0054:
            int r0 = r8.getHeight()     // Catch:{ DOMException -> 0x00c3 }
            int r5 = r8.getWidth()     // Catch:{ DOMException -> 0x00c3 }
            if (r0 > 0) goto L_0x0068
            if (r5 <= 0) goto L_0x0061
            goto L_0x0068
        L_0x0061:
            java.lang.String r7 = "both width and height of the ItagItem are <= 0"
            org.schabi.newpipe.extractor.services.youtube.dashmanifestcreators.CreationException r7 = org.schabi.newpipe.extractor.services.youtube.dashmanifestcreators.CreationException.couldNotAddElement((java.lang.String) r2, (java.lang.String) r7)     // Catch:{ DOMException -> 0x00c3 }
            throw r7     // Catch:{ DOMException -> 0x00c3 }
        L_0x0068:
            if (r5 <= 0) goto L_0x0073
            java.lang.String r0 = "width"
            java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ DOMException -> 0x00c3 }
            setAttribute(r4, r7, r0, r5)     // Catch:{ DOMException -> 0x00c3 }
        L_0x0073:
            java.lang.String r0 = "height"
            int r5 = r8.getHeight()     // Catch:{ DOMException -> 0x00c3 }
            java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ DOMException -> 0x00c3 }
            setAttribute(r4, r7, r0, r5)     // Catch:{ DOMException -> 0x00c3 }
            int r0 = r8.getFps()     // Catch:{ DOMException -> 0x00c3 }
            if (r0 <= 0) goto L_0x008f
            java.lang.String r5 = "frameRate"
            java.lang.String r0 = java.lang.String.valueOf(r0)     // Catch:{ DOMException -> 0x00c3 }
            setAttribute(r4, r7, r5, r0)     // Catch:{ DOMException -> 0x00c3 }
        L_0x008f:
            org.schabi.newpipe.extractor.services.youtube.ItagItem$ItagType r0 = org.schabi.newpipe.extractor.services.youtube.ItagItem.ItagType.AUDIO     // Catch:{ DOMException -> 0x00c3 }
            if (r1 != r0) goto L_0x00aa
            int r0 = r8.getSampleRate()     // Catch:{ DOMException -> 0x00c3 }
            if (r0 <= 0) goto L_0x00aa
            java.lang.String r0 = "audioSamplingRate"
            org.w3c.dom.Attr r7 = r7.createAttribute(r0)     // Catch:{ DOMException -> 0x00c3 }
            int r8 = r8.getSampleRate()     // Catch:{ DOMException -> 0x00c3 }
            java.lang.String r8 = java.lang.String.valueOf(r8)     // Catch:{ DOMException -> 0x00c3 }
            r7.setValue(r8)     // Catch:{ DOMException -> 0x00c3 }
        L_0x00aa:
            r3.appendChild(r4)     // Catch:{ DOMException -> 0x00c3 }
            return
        L_0x00ae:
            java.lang.String r7 = "the bitrate of the ItagItem is <= 0"
            org.schabi.newpipe.extractor.services.youtube.dashmanifestcreators.CreationException r7 = org.schabi.newpipe.extractor.services.youtube.dashmanifestcreators.CreationException.couldNotAddElement((java.lang.String) r2, (java.lang.String) r7)     // Catch:{ DOMException -> 0x00c3 }
            throw r7     // Catch:{ DOMException -> 0x00c3 }
        L_0x00b5:
            java.lang.String r7 = "the codec value of the ItagItem is null or empty"
            org.schabi.newpipe.extractor.services.youtube.dashmanifestcreators.CreationException r7 = org.schabi.newpipe.extractor.services.youtube.dashmanifestcreators.CreationException.couldNotAddElement((java.lang.String) r1, (java.lang.String) r7)     // Catch:{ DOMException -> 0x00c3 }
            throw r7     // Catch:{ DOMException -> 0x00c3 }
        L_0x00bc:
            java.lang.String r7 = "the id of the ItagItem is <= 0"
            org.schabi.newpipe.extractor.services.youtube.dashmanifestcreators.CreationException r7 = org.schabi.newpipe.extractor.services.youtube.dashmanifestcreators.CreationException.couldNotAddElement((java.lang.String) r2, (java.lang.String) r7)     // Catch:{ DOMException -> 0x00c3 }
            throw r7     // Catch:{ DOMException -> 0x00c3 }
        L_0x00c3:
            r7 = move-exception
            org.schabi.newpipe.extractor.services.youtube.dashmanifestcreators.CreationException r7 = org.schabi.newpipe.extractor.services.youtube.dashmanifestcreators.CreationException.couldNotAddElement((java.lang.String) r2, (java.lang.Exception) r7)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: org.schabi.newpipe.extractor.services.youtube.dashmanifestcreators.YoutubeDashManifestCreatorsUtils.generateRepresentationElement(org.w3c.dom.Document, org.schabi.newpipe.extractor.services.youtube.ItagItem):void");
    }

    public static void generateRoleElement(Document document, ItagItem itagItem) throws CreationException {
        int ordinal;
        try {
            Element element = (Element) document.getElementsByTagName("AdaptationSet").item(0);
            Element createElement = document.createElement("Role");
            setAttribute(createElement, document, "schemeIdUri", "urn:mpeg:DASH:role:2011");
            AudioTrackType audioTrackType = itagItem.getAudioTrackType();
            String str = "main";
            if (!(audioTrackType == null || (ordinal = audioTrackType.ordinal()) == 0)) {
                str = ordinal != 1 ? ordinal != 2 ? "alternate" : "description" : "dub";
            }
            setAttribute(createElement, document, ES6Iterator.VALUE_PROPERTY, str);
            element.appendChild(createElement);
        } catch (DOMException e) {
            throw CreationException.couldNotAddElement("Role", (Exception) e);
        }
    }

    public static void generateSegmentTemplateElement(Document document, String str, DeliveryType deliveryType) throws CreationException {
        String str2;
        DeliveryType deliveryType2 = DeliveryType.OTF;
        DeliveryType deliveryType3 = DeliveryType.LIVE;
        if (deliveryType == deliveryType2 || deliveryType == deliveryType3) {
            try {
                Element element = (Element) document.getElementsByTagName("Representation").item(0);
                Element createElement = document.createElement("SegmentTemplate");
                if (deliveryType == deliveryType3) {
                    str2 = "0";
                } else {
                    str2 = "1";
                }
                setAttribute(createElement, document, "startNumber", str2);
                setAttribute(createElement, document, "timescale", "1000");
                if (deliveryType != deliveryType3) {
                    setAttribute(createElement, document, "initialization", str + "&sq=0");
                }
                setAttribute(createElement, document, "media", str + "&sq=$Number$");
                element.appendChild(createElement);
            } catch (DOMException e) {
                throw CreationException.couldNotAddElement("SegmentTemplate", (Exception) e);
            }
        } else {
            throw CreationException.couldNotAddElement("SegmentTemplate", "invalid delivery type: " + deliveryType);
        }
    }

    public static void generateSegmentTimelineElement(Document document) throws CreationException {
        try {
            ((Element) document.getElementsByTagName("SegmentTemplate").item(0)).appendChild(document.createElement("SegmentTimeline"));
        } catch (DOMException e) {
            throw CreationException.couldNotAddElement("SegmentTimeline", (Exception) e);
        }
    }

    public static Response getInitializationResponse(String str, ItagItem itagItem, DeliveryType deliveryType) throws CreationException {
        boolean z;
        String str2;
        String str3;
        String str4;
        boolean isTvHtml5StreamingUrl = YoutubeParsingHelper.isTvHtml5StreamingUrl(str);
        if (YoutubeParsingHelper.isWebStreamingUrl(str) || isTvHtml5StreamingUrl || YoutubeParsingHelper.isWebEmbeddedPlayerStreamingUrl(str)) {
            z = true;
        } else {
            z = false;
        }
        boolean isAndroidStreamingUrl = YoutubeParsingHelper.isAndroidStreamingUrl(str);
        boolean isIosStreamingUrl = YoutubeParsingHelper.isIosStreamingUrl(str);
        if (z) {
            str = y2.x(str, "&alr=yes");
        }
        StringBuilder m = y2.m(str);
        String str5 = "";
        if (deliveryType == DeliveryType.PROGRESSIVE) {
            str2 = str5;
        } else {
            str2 = "&sq=0";
        }
        String k = y2.k(m, str2, "&rn=0");
        Downloader downloader = NewPipe.getDownloader();
        if (z) {
            String mimeType = itagItem.getMediaFormat().getMimeType();
            if (!Utils.isNullOrEmpty(mimeType)) {
                try {
                    HashMap hashMap = new HashMap(YoutubeParsingHelper.getOriginReferrerHeaders("https://www.youtube.com"));
                    if (isTvHtml5StreamingUrl) {
                        hashMap.put("User-Agent", y2.q(YoutubeParsingHelper.getTvHtml5UserAgent()));
                    }
                    int i = 0;
                    while (!str5.equals(mimeType) && i < 20) {
                        Response post = downloader.post(k, hashMap, new byte[]{120, 0});
                        int responseCode = post.responseCode();
                        if (responseCode == 200) {
                            str5 = post.getHeader("Content-Type");
                            Objects.requireNonNull(str5, "Could not get the Content-Type header from the response headers");
                            if (!str5.equals(NanoHTTPD.MIME_PLAINTEXT)) {
                                return post;
                            }
                            k = post.responseBody();
                            i++;
                        } else {
                            throw new CreationException("Could not get the initialization URL: HTTP response code " + responseCode);
                        }
                    }
                    if (i >= 20) {
                        throw new CreationException("Too many redirects when trying to get the the streaming URL response of a HTML5 client");
                    }
                    throw new CreationException("Could not get the streaming URL response of a HTML5 client: unreachable code reached!");
                } catch (IOException | ExtractionException e) {
                    throw new CreationException("Could not get the streaming URL response of a HTML5 client", e);
                }
            }
        } else if (isAndroidStreamingUrl || isIosStreamingUrl) {
            if (isAndroidStreamingUrl) {
                try {
                    str3 = YoutubeParsingHelper.getAndroidUserAgent((Localization) null);
                } catch (IOException | ExtractionException e2) {
                    StringBuilder sb = new StringBuilder("Could not get the ");
                    if (isIosStreamingUrl) {
                        str4 = "ANDROID";
                    } else {
                        str4 = "IOS";
                    }
                    throw new CreationException(y2.k(sb, str4, " streaming URL response"), e2);
                }
            } else {
                str3 = YoutubeParsingHelper.getIosUserAgent((Localization) null);
            }
            return downloader.post(k, y2.y(y2.q(str3)), str5.getBytes(StandardCharsets.UTF_8));
        }
        try {
            return downloader.get(k);
        } catch (IOException | ExtractionException e3) {
            throw new CreationException("Could not get the streaming URL response", e3);
        }
    }

    public static void setAttribute(Element element, Document document, String str, String str2) {
        Attr createAttribute = document.createAttribute(str);
        createAttribute.setValue(str2);
        element.setAttributeNode(createAttribute);
    }
}
