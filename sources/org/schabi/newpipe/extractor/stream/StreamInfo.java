package org.schabi.newpipe.extractor.stream;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.Info;
import org.schabi.newpipe.extractor.InfoItem;
import org.schabi.newpipe.extractor.MetaInfo;
import org.schabi.newpipe.extractor.NewPipe;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.exceptions.ContentNotAvailableException;
import org.schabi.newpipe.extractor.exceptions.ContentNotSupportedException;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.localization.DateWrapper;
import org.schabi.newpipe.extractor.stream.StreamExtractor;
import org.schabi.newpipe.extractor.utils.ExtractorHelper;
import org.schabi.newpipe.extractor.utils.Utils;

public class StreamInfo extends Info {
    public String aa = "";
    public List<Image> ab = Collections.emptyList();
    public List<VideoStream> ac = Collections.emptyList();
    public List<AudioStream> ad = Collections.emptyList();
    public List<VideoStream> ae = Collections.emptyList();
    public String af = "";
    public String ag = "";
    public List<InfoItem> ah = Collections.emptyList();
    public long ai = 0;
    public List<SubtitlesStream> aj = Collections.emptyList();
    public String ak = "";
    public StreamExtractor.Privacy al;
    public String am = "";
    public String an = "";
    public String ao = "";
    public Locale ap = null;
    public List<String> aq = Collections.emptyList();
    public List<StreamSegment> ar = Collections.emptyList();
    public List<MetaInfo> as = Collections.emptyList();
    public boolean at = false;
    public ContentAvailability au = ContentAvailability.AVAILABLE;
    public List<Frameset> av = Collections.emptyList();
    public StreamType k;
    public List<Image> l = Collections.emptyList();
    public String m;
    public DateWrapper n;
    public long o = -1;
    public int p;
    public Description q;
    public long r = -1;
    public long s = -1;
    public long t = -1;
    public String u = "";
    public String v = "";
    public List<Image> w = Collections.emptyList();
    public boolean x = false;
    public long y = -1;
    public String z = "";

    public static class StreamExtractException extends ExtractionException {
        public StreamExtractException() {
            super("Could not get any stream. See error variable to get further details.");
        }
    }

    public StreamInfo(int i, String str, String str2, StreamType streamType, String str3, String str4, int i2) {
        super(i, str3, str, str2, str4);
        this.k = streamType;
        this.p = i2;
    }

    public static StreamInfo a(StreamExtractor streamExtractor) throws ExtractionException {
        String url = streamExtractor.getUrl();
        StreamType streamType = streamExtractor.getStreamType();
        String id = streamExtractor.getId();
        String name = streamExtractor.getName();
        int ageLimit = streamExtractor.getAgeLimit();
        if (streamType != StreamType.NONE && !Utils.isNullOrEmpty(url) && !Utils.isNullOrEmpty(id) && name != null && ageLimit != -1) {
            return new StreamInfo(streamExtractor.getServiceId(), url, streamExtractor.getOriginalUrl(), streamType, id, name, ageLimit);
        }
        throw new ExtractionException("Some important stream information was not given.");
    }

    public static void b(StreamInfo streamInfo, StreamExtractor streamExtractor) {
        try {
            streamInfo.setThumbnails(streamExtractor.getThumbnails());
        } catch (Exception e) {
            streamInfo.addError(e);
        }
        try {
            streamInfo.setDuration(streamExtractor.getLength());
        } catch (Exception e2) {
            streamInfo.addError(e2);
        }
        try {
            streamInfo.setUploaderName(streamExtractor.getUploaderName());
        } catch (Exception e3) {
            streamInfo.addError(e3);
        }
        try {
            streamInfo.setUploaderUrl(streamExtractor.getUploaderUrl());
        } catch (Exception e4) {
            streamInfo.addError(e4);
        }
        try {
            streamInfo.setUploaderAvatars(streamExtractor.getUploaderAvatars());
        } catch (Exception e5) {
            streamInfo.addError(e5);
        }
        try {
            streamInfo.setUploaderVerified(streamExtractor.isUploaderVerified());
        } catch (Exception e6) {
            streamInfo.addError(e6);
        }
        try {
            streamInfo.setUploaderSubscriberCount(streamExtractor.getUploaderSubscriberCount());
        } catch (Exception e7) {
            streamInfo.addError(e7);
        }
        try {
            streamInfo.setSubChannelName(streamExtractor.getSubChannelName());
        } catch (Exception e8) {
            streamInfo.addError(e8);
        }
        try {
            streamInfo.setSubChannelUrl(streamExtractor.getSubChannelUrl());
        } catch (Exception e9) {
            streamInfo.addError(e9);
        }
        try {
            streamInfo.setSubChannelAvatars(streamExtractor.getSubChannelAvatars());
        } catch (Exception e10) {
            streamInfo.addError(e10);
        }
        try {
            streamInfo.setDescription(streamExtractor.getDescription());
        } catch (Exception e11) {
            streamInfo.addError(e11);
        }
        try {
            streamInfo.setViewCount(streamExtractor.getViewCount());
        } catch (Exception e12) {
            streamInfo.addError(e12);
        }
        try {
            streamInfo.setTextualUploadDate(streamExtractor.getTextualUploadDate());
        } catch (Exception e13) {
            streamInfo.addError(e13);
        }
        try {
            streamInfo.setUploadDate(streamExtractor.getUploadDate());
        } catch (Exception e14) {
            streamInfo.addError(e14);
        }
        try {
            streamInfo.setStartPosition(streamExtractor.getTimeStamp());
        } catch (Exception e15) {
            streamInfo.addError(e15);
        }
        try {
            streamInfo.setLikeCount(streamExtractor.getLikeCount());
        } catch (Exception e16) {
            streamInfo.addError(e16);
        }
        try {
            streamInfo.setDislikeCount(streamExtractor.getDislikeCount());
        } catch (Exception e17) {
            streamInfo.addError(e17);
        }
        try {
            streamInfo.setSubtitles(streamExtractor.getSubtitlesDefault());
        } catch (Exception e18) {
            streamInfo.addError(e18);
        }
        try {
            streamInfo.setHost(streamExtractor.getHost());
        } catch (Exception e19) {
            streamInfo.addError(e19);
        }
        try {
            streamInfo.setPrivacy(streamExtractor.getPrivacy());
        } catch (Exception e20) {
            streamInfo.addError(e20);
        }
        try {
            streamInfo.setCategory(streamExtractor.getCategory());
        } catch (Exception e21) {
            streamInfo.addError(e21);
        }
        try {
            streamInfo.setLicence(streamExtractor.getLicence());
        } catch (Exception e22) {
            streamInfo.addError(e22);
        }
        try {
            streamInfo.setLanguageInfo(streamExtractor.getLanguageInfo());
        } catch (Exception e23) {
            streamInfo.addError(e23);
        }
        try {
            streamInfo.setTags(streamExtractor.getTags());
        } catch (Exception e24) {
            streamInfo.addError(e24);
        }
        try {
            streamInfo.setSupportInfo(streamExtractor.getSupportInfo());
        } catch (Exception e25) {
            streamInfo.addError(e25);
        }
        try {
            streamInfo.setStreamSegments(streamExtractor.getStreamSegments());
        } catch (Exception e26) {
            streamInfo.addError(e26);
        }
        try {
            streamInfo.setMetaInfo(streamExtractor.getMetaInfo());
        } catch (Exception e27) {
            streamInfo.addError(e27);
        }
        try {
            streamInfo.setPreviewFrames(streamExtractor.getFrames());
        } catch (Exception e28) {
            streamInfo.addError(e28);
        }
        try {
            streamInfo.setShortFormContent(streamExtractor.isShortFormContent());
        } catch (Exception e29) {
            streamInfo.addError(e29);
        }
        try {
            streamInfo.setContentAvailability(streamExtractor.getContentAvailability());
        } catch (Exception e30) {
            streamInfo.addError(e30);
        }
        streamInfo.setRelatedItems(ExtractorHelper.getRelatedItemsOrLogError(streamInfo, streamExtractor));
    }

    public static void c(StreamInfo streamInfo, StreamExtractor streamExtractor) throws ExtractionException {
        try {
            streamInfo.setDashMpdUrl(streamExtractor.getDashMpdUrl());
        } catch (Exception e) {
            streamInfo.addError(new ExtractionException("Couldn't get DASH manifest", e));
        }
        try {
            streamInfo.setHlsUrl(streamExtractor.getHlsUrl());
        } catch (Exception e2) {
            streamInfo.addError(new ExtractionException("Couldn't get HLS manifest", e2));
        }
        try {
            streamInfo.setAudioStreams(streamExtractor.getAudioStreams());
        } catch (ContentNotSupportedException e3) {
            throw e3;
        } catch (Exception e4) {
            streamInfo.addError(new ExtractionException("Couldn't get audio streams", e4));
        }
        try {
            streamInfo.setVideoStreams(streamExtractor.getVideoStreams());
        } catch (Exception e5) {
            streamInfo.addError(new ExtractionException("Couldn't get video streams", e5));
        }
        try {
            streamInfo.setVideoOnlyStreams(streamExtractor.getVideoOnlyStreams());
        } catch (Exception e6) {
            streamInfo.addError(new ExtractionException("Couldn't get video only streams", e6));
        }
        if (streamInfo.ac.isEmpty() && streamInfo.ad.isEmpty()) {
            throw new StreamExtractException();
        }
    }

    public static StreamInfo getInfo(String str) throws IOException, ExtractionException {
        return getInfo(NewPipe.getServiceByUrl(str), str);
    }

    public int getAgeLimit() {
        return this.p;
    }

    public List<AudioStream> getAudioStreams() {
        return this.ad;
    }

    public String getCategory() {
        return this.am;
    }

    public ContentAvailability getContentAvailability() {
        return this.au;
    }

    public String getDashMpdUrl() {
        return this.af;
    }

    public Description getDescription() {
        return this.q;
    }

    public long getDislikeCount() {
        return this.t;
    }

    public long getDuration() {
        return this.o;
    }

    public String getHlsUrl() {
        return this.ag;
    }

    public String getHost() {
        return this.ak;
    }

    public Locale getLanguageInfo() {
        return this.ap;
    }

    public String getLicence() {
        return this.an;
    }

    public long getLikeCount() {
        return this.s;
    }

    public List<MetaInfo> getMetaInfo() {
        return this.as;
    }

    public List<Frameset> getPreviewFrames() {
        return this.av;
    }

    public StreamExtractor.Privacy getPrivacy() {
        return this.al;
    }

    public List<InfoItem> getRelatedItems() {
        return this.ah;
    }

    @Deprecated
    public List<InfoItem> getRelatedStreams() {
        return getRelatedItems();
    }

    public long getStartPosition() {
        return this.ai;
    }

    public List<StreamSegment> getStreamSegments() {
        return this.ar;
    }

    public StreamType getStreamType() {
        return this.k;
    }

    public List<Image> getSubChannelAvatars() {
        return this.ab;
    }

    public String getSubChannelName() {
        return this.z;
    }

    public String getSubChannelUrl() {
        return this.aa;
    }

    public List<SubtitlesStream> getSubtitles() {
        return this.aj;
    }

    public String getSupportInfo() {
        return this.ao;
    }

    public List<String> getTags() {
        return this.aq;
    }

    public String getTextualUploadDate() {
        return this.m;
    }

    public List<Image> getThumbnails() {
        return this.l;
    }

    public DateWrapper getUploadDate() {
        return this.n;
    }

    public List<Image> getUploaderAvatars() {
        return this.w;
    }

    public String getUploaderName() {
        return this.u;
    }

    public long getUploaderSubscriberCount() {
        return this.y;
    }

    public String getUploaderUrl() {
        return this.v;
    }

    public List<VideoStream> getVideoOnlyStreams() {
        return this.ae;
    }

    public List<VideoStream> getVideoStreams() {
        return this.ac;
    }

    public long getViewCount() {
        return this.r;
    }

    public boolean isShortFormContent() {
        return this.at;
    }

    public boolean isUploaderVerified() {
        return this.x;
    }

    public void setAgeLimit(int i) {
        this.p = i;
    }

    public void setAudioStreams(List<AudioStream> list) {
        this.ad = list;
    }

    public void setCategory(String str) {
        this.am = str;
    }

    public void setContentAvailability(ContentAvailability contentAvailability) {
        this.au = contentAvailability;
    }

    public void setDashMpdUrl(String str) {
        this.af = str;
    }

    public void setDescription(Description description) {
        this.q = description;
    }

    public void setDislikeCount(long j) {
        this.t = j;
    }

    public void setDuration(long j) {
        this.o = j;
    }

    public void setHlsUrl(String str) {
        this.ag = str;
    }

    public void setHost(String str) {
        this.ak = str;
    }

    public void setLanguageInfo(Locale locale) {
        this.ap = locale;
    }

    public void setLicence(String str) {
        this.an = str;
    }

    public void setLikeCount(long j) {
        this.s = j;
    }

    public void setMetaInfo(List<MetaInfo> list) {
        this.as = list;
    }

    public void setPreviewFrames(List<Frameset> list) {
        this.av = list;
    }

    public void setPrivacy(StreamExtractor.Privacy privacy) {
        this.al = privacy;
    }

    public void setRelatedItems(List<InfoItem> list) {
        this.ah = list;
    }

    @Deprecated
    public void setRelatedStreams(List<InfoItem> list) {
        setRelatedItems(list);
    }

    public void setShortFormContent(boolean z2) {
        this.at = z2;
    }

    public void setStartPosition(long j) {
        this.ai = j;
    }

    public void setStreamSegments(List<StreamSegment> list) {
        this.ar = list;
    }

    public void setStreamType(StreamType streamType) {
        this.k = streamType;
    }

    public void setSubChannelAvatars(List<Image> list) {
        this.ab = list;
    }

    public void setSubChannelName(String str) {
        this.z = str;
    }

    public void setSubChannelUrl(String str) {
        this.aa = str;
    }

    public void setSubtitles(List<SubtitlesStream> list) {
        this.aj = list;
    }

    public void setSupportInfo(String str) {
        this.ao = str;
    }

    public void setTags(List<String> list) {
        this.aq = list;
    }

    public void setTextualUploadDate(String str) {
        this.m = str;
    }

    public void setThumbnails(List<Image> list) {
        this.l = list;
    }

    public void setUploadDate(DateWrapper dateWrapper) {
        this.n = dateWrapper;
    }

    public void setUploaderAvatars(List<Image> list) {
        this.w = list;
    }

    public void setUploaderName(String str) {
        this.u = str;
    }

    public void setUploaderSubscriberCount(long j) {
        this.y = j;
    }

    public void setUploaderUrl(String str) {
        this.v = str;
    }

    public void setUploaderVerified(boolean z2) {
        this.x = z2;
    }

    public void setVideoOnlyStreams(List<VideoStream> list) {
        this.ae = list;
    }

    public void setVideoStreams(List<VideoStream> list) {
        this.ac = list;
    }

    public void setViewCount(long j) {
        this.r = j;
    }

    public static StreamInfo getInfo(StreamingService streamingService, String str) throws IOException, ExtractionException {
        return getInfo(streamingService.getStreamExtractor(str));
    }

    public static StreamInfo getInfo(StreamExtractor streamExtractor) throws ExtractionException, IOException {
        streamExtractor.fetchPage();
        try {
            StreamInfo a = a(streamExtractor);
            c(a, streamExtractor);
            b(a, streamExtractor);
            return a;
        } catch (ExtractionException e) {
            String errorMessage = streamExtractor.getErrorMessage();
            if (Utils.isNullOrEmpty(errorMessage)) {
                throw e;
            }
            throw new ContentNotAvailableException(errorMessage, e);
        }
    }
}
