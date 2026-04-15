package org.schabi.newpipe.extractor.services.youtube;

import androidx.recyclerview.widget.ItemTouchHelper;
import java.io.Serializable;
import java.util.Locale;
import okhttp3.internal.http.StatusLine;
import org.mozilla.javascript.Token;
import org.schabi.newpipe.extractor.MediaFormat;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.stream.AudioTrackType;

public class ItagItem implements Serializable {
    public static final ItagItem[] af;
    public AudioTrackType aa;
    public Locale ab;
    public boolean ac;
    public long ad;
    public String ae;
    public final MediaFormat c;
    public final int f;
    public final ItagType g;
    @Deprecated
    public final int h;
    public int i;
    public int j;
    @Deprecated
    public final String k;
    @Deprecated
    public int l;
    public int m;
    public int n;
    public int o;
    public int p;
    public int q;
    public int r;
    public int s;
    public String t;
    public String u;
    public int v;
    public long w;
    public long x;
    public String y;
    public String z;

    public enum ItagType {
        AUDIO,
        VIDEO,
        VIDEO_ONLY
    }

    static {
        ItagType itagType = ItagType.VIDEO;
        MediaFormat mediaFormat = MediaFormat.v3GPP;
        MediaFormat mediaFormat2 = MediaFormat.MPEG_4;
        MediaFormat mediaFormat3 = MediaFormat.WEBM;
        ItagType itagType2 = ItagType.AUDIO;
        MediaFormat mediaFormat4 = MediaFormat.WEBMA;
        MediaFormat mediaFormat5 = MediaFormat.M4A;
        MediaFormat mediaFormat6 = MediaFormat.WEBMA_OPUS;
        ItagType itagType3 = ItagType.VIDEO_ONLY;
        ItagType itagType4 = itagType3;
        MediaFormat mediaFormat7 = mediaFormat3;
        MediaFormat mediaFormat8 = mediaFormat2;
        String str = "480p";
        ItagType itagType5 = itagType3;
        MediaFormat mediaFormat9 = mediaFormat7;
        af = new ItagItem[]{new ItagItem(17, itagType, mediaFormat, "144p"), new ItagItem(36, itagType, mediaFormat, "240p"), new ItagItem(18, itagType, mediaFormat2, "360p"), new ItagItem(34, itagType, mediaFormat2, "360p"), new ItagItem(35, itagType, mediaFormat2, "480p"), new ItagItem(59, itagType, mediaFormat2, "480p"), new ItagItem(78, itagType, mediaFormat2, "480p"), new ItagItem(22, itagType, mediaFormat2, "720p"), new ItagItem(37, itagType, mediaFormat2, "1080p"), new ItagItem(38, itagType, mediaFormat2, "1080p"), new ItagItem(43, itagType, mediaFormat3, "360p"), new ItagItem(44, itagType, mediaFormat3, "480p"), new ItagItem(45, itagType, mediaFormat3, "720p"), new ItagItem(46, itagType, mediaFormat3, "1080p"), new ItagItem(171, itagType2, mediaFormat4, 128), new ItagItem(172, itagType2, mediaFormat4, 256), new ItagItem(599, itagType2, mediaFormat5, 32), new ItagItem((int) Token.USE_STACK, itagType2, mediaFormat5, 48), new ItagItem(140, itagType2, mediaFormat5, 128), new ItagItem((int) Token.SETELEM_OP, itagType2, mediaFormat5, 256), new ItagItem(600, itagType2, mediaFormat6, 35), new ItagItem(249, itagType2, mediaFormat6, 50), new ItagItem((int) ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, itagType2, mediaFormat6, 70), new ItagItem(251, itagType2, mediaFormat6, 160), new ItagItem(160, itagType3, mediaFormat2, "144p"), new ItagItem(394, itagType3, mediaFormat2, "144p"), new ItagItem((int) Token.LOOP, itagType3, mediaFormat2, "240p"), new ItagItem(395, itagType3, mediaFormat2, "240p"), new ItagItem((int) Token.EXPR_VOID, itagType3, mediaFormat2, "360p"), new ItagItem(396, itagType3, mediaFormat2, "360p"), new ItagItem((int) Token.EXPR_RESULT, itagType3, mediaFormat2, "480p"), new ItagItem(212, itagType3, mediaFormat2, "480p"), new ItagItem(397, itagType3, mediaFormat2, "480p"), new ItagItem((int) Token.JSR, itagType3, mediaFormat2, "720p"), new ItagItem(398, itagType3, mediaFormat2, "720p"), new ItagItem(298, itagType4, mediaFormat8, "720p60", 60), new ItagItem((int) Token.SCRIPT, itagType3, mediaFormat2, "1080p"), new ItagItem(399, itagType3, mediaFormat2, "1080p"), new ItagItem(299, itagType4, mediaFormat8, "1080p60", 60), new ItagItem(400, itagType3, mediaFormat2, "1440p"), new ItagItem(266, itagType3, mediaFormat2, "2160p"), new ItagItem(401, itagType3, mediaFormat2, "2160p"), new ItagItem(278, itagType3, mediaFormat7, "144p"), new ItagItem(242, itagType3, mediaFormat7, "240p"), new ItagItem(243, itagType3, mediaFormat7, "360p"), new ItagItem(244, itagType3, mediaFormat7, str), new ItagItem(245, itagType3, mediaFormat7, str), new ItagItem(246, itagType3, mediaFormat7, str), new ItagItem(247, itagType3, mediaFormat7, "720p"), new ItagItem(248, itagType3, mediaFormat7, "1080p"), new ItagItem(271, itagType3, mediaFormat7, "1440p"), new ItagItem(272, itagType3, mediaFormat7, "2160p"), new ItagItem(302, itagType5, mediaFormat9, "720p60", 60), new ItagItem(303, itagType5, mediaFormat9, "1080p60", 60), new ItagItem(StatusLine.HTTP_PERM_REDIRECT, itagType5, mediaFormat9, "1440p60", 60), new ItagItem(313, itagType3, mediaFormat7, "2160p"), new ItagItem(315, itagType5, mediaFormat9, "2160p60", 60)};
    }

    public ItagItem(int i2, ItagType itagType, MediaFormat mediaFormat, String str) {
        this.h = -1;
        this.i = -1;
        this.j = -1;
        this.v = -1;
        this.w = -1;
        this.x = -1;
        this.f = i2;
        this.g = itagType;
        this.c = mediaFormat;
        this.k = str;
        this.l = 30;
    }

    public static ItagItem getItag(int i2) throws ParsingException {
        for (ItagItem itagItem : af) {
            if (i2 == itagItem.f) {
                return new ItagItem(itagItem);
            }
        }
        throw new ParsingException("itag " + i2 + " is not supported");
    }

    public static boolean isSupported(int i2) {
        for (ItagItem itagItem : af) {
            if (i2 == itagItem.f) {
                return true;
            }
        }
        return false;
    }

    public long getApproxDurationMs() {
        return this.w;
    }

    public int getAudioChannels() {
        return this.j;
    }

    public Locale getAudioLocale() {
        return this.ab;
    }

    public String getAudioTrackId() {
        return this.y;
    }

    public String getAudioTrackName() {
        return this.z;
    }

    public AudioTrackType getAudioTrackType() {
        return this.aa;
    }

    public int getAverageBitrate() {
        return this.h;
    }

    public int getBitrate() {
        return this.m;
    }

    public String getCodec() {
        return this.u;
    }

    public long getContentLength() {
        return this.x;
    }

    public int getFps() {
        return this.l;
    }

    public int getHeight() {
        return this.o;
    }

    public int getIndexEnd() {
        return this.s;
    }

    public int getIndexStart() {
        return this.r;
    }

    public int getInitEnd() {
        return this.q;
    }

    public int getInitStart() {
        return this.p;
    }

    public long getLastModified() {
        return this.ad;
    }

    public MediaFormat getMediaFormat() {
        return this.c;
    }

    public String getQuality() {
        return this.t;
    }

    public String getResolutionString() {
        return this.k;
    }

    public int getSampleRate() {
        return this.i;
    }

    public int getTargetDurationSec() {
        return this.v;
    }

    public int getWidth() {
        return this.n;
    }

    public String getXtags() {
        return this.ae;
    }

    public Boolean isDrc() {
        return Boolean.valueOf(this.ac);
    }

    public void setApproxDurationMs(long j2) {
        if (j2 <= 0) {
            j2 = -1;
        }
        this.w = j2;
    }

    public void setAudioChannels(int i2) {
        if (i2 <= 0) {
            i2 = -1;
        }
        this.j = i2;
    }

    public void setAudioLocale(Locale locale) {
        this.ab = locale;
    }

    public void setAudioTrackId(String str) {
        this.y = str;
    }

    public void setAudioTrackName(String str) {
        this.z = str;
    }

    public void setAudioTrackType(AudioTrackType audioTrackType) {
        this.aa = audioTrackType;
    }

    public void setBitrate(int i2) {
        this.m = i2;
    }

    public void setCodec(String str) {
        this.u = str;
    }

    public void setContentLength(long j2) {
        if (j2 <= 0) {
            j2 = -1;
        }
        this.x = j2;
    }

    public void setFps(int i2) {
        if (i2 <= 0) {
            i2 = -1;
        }
        this.l = i2;
    }

    public void setHeight(int i2) {
        this.o = i2;
    }

    public void setIndexEnd(int i2) {
        this.s = i2;
    }

    public void setIndexStart(int i2) {
        this.r = i2;
    }

    public void setInitEnd(int i2) {
        this.q = i2;
    }

    public void setInitStart(int i2) {
        this.p = i2;
    }

    public void setIsDrc(Boolean bool) {
        this.ac = bool.booleanValue();
    }

    public void setLastModified(long j2) {
        this.ad = j2;
    }

    public void setQuality(String str) {
        this.t = str;
    }

    public void setSampleRate(int i2) {
        if (i2 <= 0) {
            i2 = -1;
        }
        this.i = i2;
    }

    public void setTargetDurationSec(int i2) {
        if (i2 <= 0) {
            i2 = -1;
        }
        this.v = i2;
    }

    public void setWidth(int i2) {
        this.n = i2;
    }

    public void setXtags(String str) {
        this.ae = str;
    }

    public ItagItem(int i2, ItagType itagType, MediaFormat mediaFormat, String str, int i3) {
        this.h = -1;
        this.i = -1;
        this.j = -1;
        this.v = -1;
        this.w = -1;
        this.x = -1;
        this.f = i2;
        this.g = itagType;
        this.c = mediaFormat;
        this.k = str;
        this.l = i3;
    }

    public ItagItem(int i2, ItagType itagType, MediaFormat mediaFormat, int i3) {
        this.h = -1;
        this.i = -1;
        this.j = -1;
        this.l = -1;
        this.v = -1;
        this.w = -1;
        this.x = -1;
        this.f = i2;
        this.g = itagType;
        this.c = mediaFormat;
        this.h = i3;
    }

    public ItagItem(ItagItem itagItem) {
        this.h = -1;
        this.i = -1;
        this.j = -1;
        this.l = -1;
        this.v = -1;
        this.w = -1;
        this.x = -1;
        this.c = itagItem.c;
        this.f = itagItem.f;
        this.g = itagItem.g;
        this.h = itagItem.h;
        this.i = itagItem.i;
        this.j = itagItem.j;
        this.k = itagItem.k;
        this.l = itagItem.l;
        this.m = itagItem.m;
        this.n = itagItem.n;
        this.o = itagItem.o;
        this.p = itagItem.p;
        this.q = itagItem.q;
        this.r = itagItem.r;
        this.s = itagItem.s;
        this.t = itagItem.t;
        this.u = itagItem.u;
        this.v = itagItem.v;
        this.w = itagItem.w;
        this.x = itagItem.x;
        this.y = itagItem.y;
        this.z = itagItem.z;
        this.aa = itagItem.aa;
        this.ab = itagItem.ab;
    }
}
