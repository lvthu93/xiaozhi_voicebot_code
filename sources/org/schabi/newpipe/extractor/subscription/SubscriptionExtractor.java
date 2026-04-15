package org.schabi.newpipe.extractor.subscription;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;

public abstract class SubscriptionExtractor {
    public final List<ContentSource> a;
    public final StreamingService b;

    public enum ContentSource {
        CHANNEL_URL,
        INPUT_STREAM
    }

    public static class InvalidSourceException extends ParsingException {
        public InvalidSourceException() {
            this((String) null, (Throwable) null);
        }

        public InvalidSourceException(String str) {
            this(str, (Throwable) null);
        }

        public InvalidSourceException(Throwable th) {
            this((String) null, th);
        }

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public InvalidSourceException(java.lang.String r4, java.lang.Throwable r5) {
            /*
                r3 = this;
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                java.lang.String r1 = "Not a valid source"
                r0.<init>(r1)
                if (r4 != 0) goto L_0x000c
                java.lang.String r4 = ""
                goto L_0x0014
            L_0x000c:
                java.lang.String r1 = " ("
                java.lang.String r2 = ")"
                java.lang.String r4 = defpackage.y2.j(r1, r4, r2)
            L_0x0014:
                r0.append(r4)
                java.lang.String r4 = r0.toString()
                r3.<init>(r4, r5)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.schabi.newpipe.extractor.subscription.SubscriptionExtractor.InvalidSourceException.<init>(java.lang.String, java.lang.Throwable):void");
        }
    }

    public SubscriptionExtractor(StreamingService streamingService, List<ContentSource> list) {
        this.b = streamingService;
        this.a = Collections.unmodifiableList(list);
    }

    public List<SubscriptionItem> fromChannelUrl(String str) throws IOException, ExtractionException {
        throw new UnsupportedOperationException("Service " + this.b.getServiceInfo().getName() + " doesn't support extracting from a channel url");
    }

    public List<SubscriptionItem> fromInputStream(InputStream inputStream) throws ExtractionException {
        throw new UnsupportedOperationException("Service " + this.b.getServiceInfo().getName() + " doesn't support extracting from an InputStream");
    }

    public abstract String getRelatedUrl();

    public List<ContentSource> getSupportedSources() {
        return this.a;
    }

    public List<SubscriptionItem> fromInputStream(InputStream inputStream, String str) throws ExtractionException {
        throw new UnsupportedOperationException("Service " + this.b.getServiceInfo().getName() + " doesn't support extracting from an InputStream");
    }
}
