package org.schabi.newpipe.extractor.services.youtube.extractors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jsoup.nodes.Element;
import org.mozilla.javascript.Context;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.localization.DateWrapper;
import org.schabi.newpipe.extractor.stream.ContentAvailability;
import org.schabi.newpipe.extractor.stream.StreamInfoItemExtractor;
import org.schabi.newpipe.extractor.stream.StreamType;

public class YoutubeFeedInfoItemExtractor implements StreamInfoItemExtractor {
    public final Element a;

    public YoutubeFeedInfoItemExtractor(Element element) {
        this.a = element;
    }

    public final /* synthetic */ ContentAvailability getContentAvailability() {
        return ob.a(this);
    }

    public long getDuration() {
        return -1;
    }

    public String getName() {
        return this.a.getElementsByTag("title").first().text();
    }

    public final /* synthetic */ String getShortDescription() {
        return ob.b(this);
    }

    public StreamType getStreamType() {
        return StreamType.VIDEO_STREAM;
    }

    public String getTextualUploadDate() {
        return this.a.getElementsByTag("published").first().text();
    }

    public List<Image> getThumbnails() {
        int i;
        int i2;
        Element first = this.a.getElementsByTag("media:thumbnail").first();
        if (first == null) {
            return Collections.emptyList();
        }
        String attr = first.attr("url");
        if (attr.isEmpty()) {
            return Collections.emptyList();
        }
        String replace = attr.replace("hqdefault", "mqdefault");
        if (replace.equals(attr)) {
            i2 = -1;
            try {
                i = Integer.parseInt(first.attr("height"));
            } catch (NumberFormatException unused) {
                i = -1;
            }
            try {
                i2 = Integer.parseInt(first.attr("width"));
            } catch (NumberFormatException unused2) {
            }
        } else {
            i = 320;
            i2 = Context.VERSION_1_8;
        }
        Object[] objArr = {new Image(replace, i, i2, Image.ResolutionLevel.fromHeight(i))};
        ArrayList arrayList = new ArrayList(1);
        Object obj = objArr[0];
        return y2.p(obj, arrayList, obj, arrayList);
    }

    public DateWrapper getUploadDate() throws ParsingException {
        return DateWrapper.fromOffsetDateTime(getTextualUploadDate());
    }

    public final /* synthetic */ List getUploaderAvatars() {
        return ob.c(this);
    }

    public String getUploaderName() {
        return this.a.select("author > name").first().text();
    }

    public String getUploaderUrl() {
        return this.a.select("author > uri").first().text();
    }

    public String getUrl() {
        return this.a.getElementsByTag("link").first().attr("href");
    }

    public long getViewCount() {
        return Long.parseLong(this.a.getElementsByTag("media:statistics").first().attr("views"));
    }

    public boolean isAd() {
        return false;
    }

    public final /* synthetic */ boolean isShortFormContent() {
        return ob.d(this);
    }

    public boolean isUploaderVerified() throws ParsingException {
        return false;
    }
}
