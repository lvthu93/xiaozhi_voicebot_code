package org.schabi.newpipe.extractor.stream;

import java.util.Comparator;
import org.schabi.newpipe.extractor.InfoItemsCollector;
import org.schabi.newpipe.extractor.exceptions.FoundAdException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;

public class StreamInfoItemsCollector extends InfoItemsCollector<StreamInfoItem, StreamInfoItemExtractor> {
    public StreamInfoItemsCollector(int i) {
        super(i);
    }

    public StreamInfoItemsCollector(int i, Comparator<StreamInfoItem> comparator) {
        super(i, comparator);
    }

    public StreamInfoItem extract(StreamInfoItemExtractor streamInfoItemExtractor) throws ParsingException {
        if (!streamInfoItemExtractor.isAd()) {
            StreamInfoItem streamInfoItem = new StreamInfoItem(getServiceId(), streamInfoItemExtractor.getUrl(), streamInfoItemExtractor.getName(), streamInfoItemExtractor.getStreamType());
            try {
                streamInfoItem.setDuration(streamInfoItemExtractor.getDuration());
            } catch (Exception e) {
                a(e);
            }
            try {
                streamInfoItem.setUploaderName(streamInfoItemExtractor.getUploaderName());
            } catch (Exception e2) {
                a(e2);
            }
            try {
                streamInfoItem.setTextualUploadDate(streamInfoItemExtractor.getTextualUploadDate());
            } catch (Exception e3) {
                a(e3);
            }
            try {
                streamInfoItem.setUploadDate(streamInfoItemExtractor.getUploadDate());
            } catch (ParsingException e4) {
                a(e4);
            }
            try {
                streamInfoItem.setViewCount(streamInfoItemExtractor.getViewCount());
            } catch (Exception e5) {
                a(e5);
            }
            try {
                streamInfoItem.setThumbnails(streamInfoItemExtractor.getThumbnails());
            } catch (Exception e6) {
                a(e6);
            }
            try {
                streamInfoItem.setUploaderUrl(streamInfoItemExtractor.getUploaderUrl());
            } catch (Exception e7) {
                a(e7);
            }
            try {
                streamInfoItem.setUploaderAvatars(streamInfoItemExtractor.getUploaderAvatars());
            } catch (Exception e8) {
                a(e8);
            }
            try {
                streamInfoItem.setUploaderVerified(streamInfoItemExtractor.isUploaderVerified());
            } catch (Exception e9) {
                a(e9);
            }
            try {
                streamInfoItem.setShortDescription(streamInfoItemExtractor.getShortDescription());
            } catch (Exception e10) {
                a(e10);
            }
            try {
                streamInfoItem.setShortFormContent(streamInfoItemExtractor.isShortFormContent());
            } catch (Exception e11) {
                a(e11);
            }
            try {
                streamInfoItem.setContentAvailability(streamInfoItemExtractor.getContentAvailability());
            } catch (Exception e12) {
                a(e12);
            }
            return streamInfoItem;
        }
        throw new FoundAdException("Found ad");
    }

    public void commit(StreamInfoItemExtractor streamInfoItemExtractor) {
        try {
            this.a.add(extract(streamInfoItemExtractor));
        } catch (FoundAdException unused) {
        } catch (Exception e) {
            a(e);
        }
    }
}
