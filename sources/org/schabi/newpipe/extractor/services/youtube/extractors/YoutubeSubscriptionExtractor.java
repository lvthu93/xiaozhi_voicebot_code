package org.schabi.newpipe.extractor.services.youtube.extractors;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonParser;
import com.grack.nanojson.JsonParserException;
import j$.io.BufferedReaderRetargetClass;
import j$.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.services.youtube.YoutubeService;
import org.schabi.newpipe.extractor.subscription.SubscriptionExtractor;
import org.schabi.newpipe.extractor.subscription.SubscriptionItem;

public class YoutubeSubscriptionExtractor extends SubscriptionExtractor {
    public YoutubeSubscriptionExtractor(YoutubeService youtubeService) {
        super(youtubeService, Collections.singletonList(SubscriptionExtractor.ContentSource.INPUT_STREAM));
    }

    public List<SubscriptionItem> fromCsvInputStream(InputStream inputStream) throws ExtractionException {
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            List<SubscriptionItem> list = (List) BufferedReaderRetargetClass.lines(bufferedReader).skip(1).map(new fg(22)).filter(new mg(10)).map(new s5(2, this)).filter(new mg(11)).collect(Collectors.toUnmodifiableList());
            bufferedReader.close();
            return list;
        } catch (IOException | UncheckedIOException e) {
            throw new SubscriptionExtractor.InvalidSourceException("Error reading CSV file", e);
        } catch (Throwable th) {
            th.addSuppressed(th);
        }
        throw th;
    }

    public List<SubscriptionItem> fromInputStream(InputStream inputStream) throws ExtractionException {
        return fromJsonInputStream(inputStream);
    }

    public List<SubscriptionItem> fromJsonInputStream(InputStream inputStream) throws ExtractionException {
        try {
            JsonArray from = JsonParser.array().from(inputStream);
            ArrayList arrayList = new ArrayList();
            Iterator it = from.iterator();
            boolean z = false;
            while (it.hasNext()) {
                Object next = it.next();
                if (next instanceof JsonObject) {
                    JsonObject object = ((JsonObject) next).getObject("snippet");
                    String string = object.getObject("resourceId").getString("channelId", "");
                    if (string.length() == 24) {
                        arrayList.add(new SubscriptionItem(this.b.getServiceId(), "https://www.youtube.com/channel/".concat(string), object.getString("title", "")));
                    }
                }
                z = true;
            }
            if (!z || !arrayList.isEmpty()) {
                return arrayList;
            }
            throw new SubscriptionExtractor.InvalidSourceException("Found only invalid channel ids");
        } catch (JsonParserException e) {
            throw new SubscriptionExtractor.InvalidSourceException("Invalid json input stream", e);
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(3:2|3|(3:5|(3:7|8|(3:10|11|12)(1:29))(1:28)|26)(4:27|13|14|15)) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:2:0x0005 */
    /* JADX WARNING: Removed duplicated region for block: B:2:0x0005 A[LOOP:0: B:2:0x0005->B:26:0x0005, LOOP_START, SYNTHETIC, Splitter:B:2:0x0005] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<org.schabi.newpipe.extractor.subscription.SubscriptionItem> fromZipInputStream(java.io.InputStream r3) throws org.schabi.newpipe.extractor.exceptions.ExtractionException {
        /*
            r2 = this;
            java.util.zip.ZipInputStream r0 = new java.util.zip.ZipInputStream     // Catch:{ IOException -> 0x003e }
            r0.<init>(r3)     // Catch:{ IOException -> 0x003e }
        L_0x0005:
            java.util.zip.ZipEntry r3 = r0.getNextEntry()     // Catch:{ all -> 0x0034 }
            if (r3 == 0) goto L_0x0029
            java.lang.String r3 = r3.getName()     // Catch:{ all -> 0x0034 }
            java.lang.String r3 = r3.toLowerCase()     // Catch:{ all -> 0x0034 }
            java.lang.String r1 = ".csv"
            boolean r3 = r3.endsWith(r1)     // Catch:{ all -> 0x0034 }
            if (r3 == 0) goto L_0x0005
            java.util.List r3 = r2.fromCsvInputStream(r0)     // Catch:{ ExtractionException -> 0x0005 }
            boolean r1 = r3.isEmpty()     // Catch:{ ExtractionException -> 0x0005 }
            if (r1 != 0) goto L_0x0005
            r0.close()     // Catch:{ IOException -> 0x003e }
            return r3
        L_0x0029:
            r0.close()     // Catch:{ IOException -> 0x003e }
            org.schabi.newpipe.extractor.subscription.SubscriptionExtractor$InvalidSourceException r3 = new org.schabi.newpipe.extractor.subscription.SubscriptionExtractor$InvalidSourceException
            java.lang.String r0 = "Unable to find a valid subscriptions.csv file (try extracting and selecting the csv file)"
            r3.<init>((java.lang.String) r0)
            throw r3
        L_0x0034:
            r3 = move-exception
            r0.close()     // Catch:{ all -> 0x0039 }
            goto L_0x003d
        L_0x0039:
            r0 = move-exception
            r3.addSuppressed(r0)     // Catch:{ IOException -> 0x003e }
        L_0x003d:
            throw r3     // Catch:{ IOException -> 0x003e }
        L_0x003e:
            r3 = move-exception
            org.schabi.newpipe.extractor.subscription.SubscriptionExtractor$InvalidSourceException r0 = new org.schabi.newpipe.extractor.subscription.SubscriptionExtractor$InvalidSourceException
            java.lang.String r1 = "Error reading contents of zip file"
            r0.<init>(r1, r3)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeSubscriptionExtractor.fromZipInputStream(java.io.InputStream):java.util.List");
    }

    public String getRelatedUrl() {
        return "https://takeout.google.com/takeout/custom/youtube";
    }

    public List<SubscriptionItem> fromInputStream(InputStream inputStream, String str) throws ExtractionException {
        str.getClass();
        char c = 65535;
        switch (str.hashCode()) {
            case -2135895576:
                if (str.equals("text/comma-separated-values")) {
                    c = 0;
                    break;
                }
                break;
            case -1248325150:
                if (str.equals("application/zip")) {
                    c = 1;
                    break;
                }
                break;
            case -1004747228:
                if (str.equals("text/csv")) {
                    c = 2;
                    break;
                }
                break;
            case -43840953:
                if (str.equals("application/json")) {
                    c = 3;
                    break;
                }
                break;
            case 98822:
                if (str.equals("csv")) {
                    c = 4;
                    break;
                }
                break;
            case 120609:
                if (str.equals("zip")) {
                    c = 5;
                    break;
                }
                break;
            case 3271912:
                if (str.equals("json")) {
                    c = 6;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
            case 2:
            case 4:
                return fromCsvInputStream(inputStream);
            case 1:
            case 5:
                return fromZipInputStream(inputStream);
            case 3:
            case 6:
                return fromJsonInputStream(inputStream);
            default:
                throw new SubscriptionExtractor.InvalidSourceException("Unsupported content type: ".concat(str));
        }
    }
}
