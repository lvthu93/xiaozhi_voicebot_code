package org.schabi.newpipe.extractor.stream;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import org.schabi.newpipe.extractor.Extractor;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.InfoItem;
import org.schabi.newpipe.extractor.InfoItemExtractor;
import org.schabi.newpipe.extractor.InfoItemsCollector;
import org.schabi.newpipe.extractor.MediaFormat;
import org.schabi.newpipe.extractor.MetaInfo;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.LinkHandler;
import org.schabi.newpipe.extractor.localization.DateWrapper;

public abstract class StreamExtractor extends Extractor {

    public enum Privacy {
        c,
        UNLISTED,
        PRIVATE,
        INTERNAL,
        OTHER
    }

    public StreamExtractor(StreamingService streamingService, LinkHandler linkHandler) {
        super(streamingService, linkHandler);
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(3:18|19|(1:23)) */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0033, code lost:
        r1 = org.schabi.newpipe.extractor.utils.Parser.matchGroup1("t=(\\d+)", r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x006b, code lost:
        r9 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0073, code lost:
        throw new org.schabi.newpipe.extractor.exceptions.ParsingException("Could not get timestamp.", r9);
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:18:0x0027 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final long b(java.lang.String r9) throws org.schabi.newpipe.extractor.exceptions.ParsingException {
        /*
            r8 = this;
            java.lang.String r0 = ""
            java.lang.String r1 = r8.getOriginalUrl()     // Catch:{ RegexException -> 0x0077 }
            java.lang.String r9 = org.schabi.newpipe.extractor.utils.Parser.matchGroup1((java.lang.String) r9, (java.lang.String) r1)     // Catch:{ RegexException -> 0x0077 }
            boolean r1 = r9.isEmpty()
            if (r1 != 0) goto L_0x0074
            java.lang.String r1 = "(\\d+)s"
            java.lang.String r1 = org.schabi.newpipe.extractor.utils.Parser.matchGroup1((java.lang.String) r1, (java.lang.String) r9)     // Catch:{ Exception -> 0x0025 }
            java.lang.String r2 = "(\\d+)m"
            java.lang.String r2 = org.schabi.newpipe.extractor.utils.Parser.matchGroup1((java.lang.String) r2, (java.lang.String) r9)     // Catch:{ Exception -> 0x0023 }
            java.lang.String r3 = "(\\d+)h"
            java.lang.String r0 = org.schabi.newpipe.extractor.utils.Parser.matchGroup1((java.lang.String) r3, (java.lang.String) r9)     // Catch:{ Exception -> 0x0027 }
            goto L_0x0039
        L_0x0023:
            r2 = r0
            goto L_0x0027
        L_0x0025:
            r1 = r0
            r2 = r1
        L_0x0027:
            boolean r3 = r1.isEmpty()     // Catch:{ ParsingException -> 0x006b }
            if (r3 == 0) goto L_0x0039
            boolean r3 = r2.isEmpty()     // Catch:{ ParsingException -> 0x006b }
            if (r3 == 0) goto L_0x0039
            java.lang.String r1 = "t=(\\d+)"
            java.lang.String r1 = org.schabi.newpipe.extractor.utils.Parser.matchGroup1((java.lang.String) r1, (java.lang.String) r9)     // Catch:{ ParsingException -> 0x006b }
        L_0x0039:
            boolean r9 = r1.isEmpty()     // Catch:{ ParsingException -> 0x006b }
            r3 = 0
            if (r9 == 0) goto L_0x0042
            r9 = 0
            goto L_0x0046
        L_0x0042:
            int r9 = java.lang.Integer.parseInt(r1)     // Catch:{ ParsingException -> 0x006b }
        L_0x0046:
            boolean r1 = r2.isEmpty()     // Catch:{ ParsingException -> 0x006b }
            if (r1 == 0) goto L_0x004e
            r1 = 0
            goto L_0x0052
        L_0x004e:
            int r1 = java.lang.Integer.parseInt(r2)     // Catch:{ ParsingException -> 0x006b }
        L_0x0052:
            boolean r2 = r0.isEmpty()     // Catch:{ ParsingException -> 0x006b }
            if (r2 == 0) goto L_0x0059
            goto L_0x005d
        L_0x0059:
            int r3 = java.lang.Integer.parseInt(r0)     // Catch:{ ParsingException -> 0x006b }
        L_0x005d:
            long r4 = (long) r9
            r6 = 60
            long r0 = (long) r1
            long r0 = r0 * r6
            long r0 = r0 + r4
            r4 = 3600(0xe10, double:1.7786E-320)
            long r2 = (long) r3
            long r2 = r2 * r4
            long r2 = r2 + r0
            return r2
        L_0x006b:
            r9 = move-exception
            org.schabi.newpipe.extractor.exceptions.ParsingException r0 = new org.schabi.newpipe.extractor.exceptions.ParsingException
            java.lang.String r1 = "Could not get timestamp."
            r0.<init>(r1, r9)
            throw r0
        L_0x0074:
            r0 = 0
            return r0
        L_0x0077:
            r0 = -2
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.schabi.newpipe.extractor.stream.StreamExtractor.b(java.lang.String):long");
    }

    public int getAgeLimit() throws ParsingException {
        return 0;
    }

    public abstract List<AudioStream> getAudioStreams() throws IOException, ExtractionException;

    public String getCategory() throws ParsingException {
        return "";
    }

    public ContentAvailability getContentAvailability() throws ParsingException {
        return ContentAvailability.UNKNOWN;
    }

    public String getDashMpdUrl() throws ParsingException {
        return "";
    }

    public Description getDescription() throws ParsingException {
        return Description.g;
    }

    public long getDislikeCount() throws ParsingException {
        return -1;
    }

    public String getErrorMessage() {
        return null;
    }

    public List<Frameset> getFrames() throws ExtractionException {
        return Collections.emptyList();
    }

    public String getHlsUrl() throws ParsingException {
        return "";
    }

    public String getHost() throws ParsingException {
        return "";
    }

    public Locale getLanguageInfo() throws ParsingException {
        return null;
    }

    public long getLength() throws ParsingException {
        return 0;
    }

    public String getLicence() throws ParsingException {
        return "";
    }

    public long getLikeCount() throws ParsingException {
        return -1;
    }

    public List<MetaInfo> getMetaInfo() throws ParsingException {
        return Collections.emptyList();
    }

    public Privacy getPrivacy() throws ParsingException {
        return Privacy.c;
    }

    public InfoItemsCollector<? extends InfoItem, ? extends InfoItemExtractor> getRelatedItems() throws IOException, ExtractionException {
        return null;
    }

    @Deprecated
    public StreamInfoItemsCollector getRelatedStreams() throws IOException, ExtractionException {
        InfoItemsCollector<? extends InfoItem, ? extends InfoItemExtractor> relatedItems = getRelatedItems();
        if (relatedItems instanceof StreamInfoItemsCollector) {
            return (StreamInfoItemsCollector) relatedItems;
        }
        return null;
    }

    public List<StreamSegment> getStreamSegments() throws ParsingException {
        return Collections.emptyList();
    }

    public abstract StreamType getStreamType() throws ParsingException;

    public List<Image> getSubChannelAvatars() throws ParsingException {
        return Collections.emptyList();
    }

    public String getSubChannelName() throws ParsingException {
        return "";
    }

    public String getSubChannelUrl() throws ParsingException {
        return "";
    }

    public List<SubtitlesStream> getSubtitles(MediaFormat mediaFormat) throws IOException, ExtractionException {
        return Collections.emptyList();
    }

    public List<SubtitlesStream> getSubtitlesDefault() throws IOException, ExtractionException {
        return Collections.emptyList();
    }

    public String getSupportInfo() throws ParsingException {
        return "";
    }

    public List<String> getTags() throws ParsingException {
        return Collections.emptyList();
    }

    public String getTextualUploadDate() throws ParsingException {
        return null;
    }

    public abstract List<Image> getThumbnails() throws ParsingException;

    public long getTimeStamp() throws ParsingException {
        return 0;
    }

    public DateWrapper getUploadDate() throws ParsingException {
        return null;
    }

    public List<Image> getUploaderAvatars() throws ParsingException {
        return Collections.emptyList();
    }

    public abstract String getUploaderName() throws ParsingException;

    public long getUploaderSubscriberCount() throws ParsingException {
        return -1;
    }

    public abstract String getUploaderUrl() throws ParsingException;

    public abstract List<VideoStream> getVideoOnlyStreams() throws IOException, ExtractionException;

    public abstract List<VideoStream> getVideoStreams() throws IOException, ExtractionException;

    public long getViewCount() throws ParsingException {
        return -1;
    }

    public boolean isShortFormContent() throws ParsingException {
        return false;
    }

    public boolean isUploaderVerified() throws ParsingException {
        return false;
    }
}
