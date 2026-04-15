package com.google.android.exoplayer2.video;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Pair;
import android.view.Surface;
import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.RendererConfiguration;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.decoder.DecoderReuseEvaluation;
import com.google.android.exoplayer2.drm.ExoMediaCrypto;
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto;
import com.google.android.exoplayer2.mediacodec.MediaCodecAdapter;
import com.google.android.exoplayer2.mediacodec.MediaCodecDecoderException;
import com.google.android.exoplayer2.mediacodec.MediaCodecInfo;
import com.google.android.exoplayer2.mediacodec.MediaCodecRenderer;
import com.google.android.exoplayer2.mediacodec.MediaCodecSelector;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MediaFormatUtil;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.TraceUtil;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoRendererEventListener;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

public class MediaCodecVideoRenderer extends MediaCodecRenderer {
    public static final int[] W = {1920, 1600, 1440, 1280, 960, 854, 640, 540, 480};
    public static boolean X;
    public static boolean Y;
    public boolean A;
    public boolean B;
    public boolean C;
    public long D;
    public long E;
    public long F;
    public int G;
    public int H;
    public int I;
    public long J;
    public long K;
    public long L;
    public int M;
    public int N;
    public int O;
    public int P;
    public float Q;
    @Nullable
    public VideoSize R;
    public boolean S;
    public int T;
    @Nullable
    public a U;
    @Nullable
    public VideoFrameMetadataListener V;
    public final Context cn;
    public final VideoFrameReleaseHelper co;
    public final VideoRendererEventListener.EventDispatcher cp;
    public final long cq;
    public final int cr;
    public final boolean cs;
    public CodecMaxValues ct;
    public boolean cu;
    public boolean cv;
    @Nullable
    public Surface cw;
    @Nullable
    public DummySurface cx;
    public boolean cy;
    public int cz;

    public static final class CodecMaxValues {
        public final int a;
        public final int b;
        public final int c;

        public CodecMaxValues(int i, int i2, int i3) {
            this.a = i;
            this.b = i2;
            this.c = i3;
        }
    }

    @RequiresApi(23)
    public final class a implements MediaCodecAdapter.OnFrameRenderedListener, Handler.Callback {
        public final Handler c;

        public a(MediaCodecAdapter mediaCodecAdapter) {
            Handler createHandlerForCurrentLooper = Util.createHandlerForCurrentLooper(this);
            this.c = createHandlerForCurrentLooper;
            mediaCodecAdapter.setOnFrameRenderedListener(this, createHandlerForCurrentLooper);
        }

        public boolean handleMessage(Message message) {
            if (message.what != 0) {
                return false;
            }
            long j = Util.toLong(message.arg1, message.arg2);
            MediaCodecVideoRenderer mediaCodecVideoRenderer = MediaCodecVideoRenderer.this;
            if (this == mediaCodecVideoRenderer.U) {
                if (j == Long.MAX_VALUE) {
                    mediaCodecVideoRenderer.cc = true;
                } else {
                    try {
                        mediaCodecVideoRenderer.ay(j);
                        mediaCodecVideoRenderer.bg();
                        mediaCodecVideoRenderer.ci.b++;
                        mediaCodecVideoRenderer.bf();
                        mediaCodecVideoRenderer.ai(j);
                    } catch (ExoPlaybackException e) {
                        mediaCodecVideoRenderer.ch = e;
                    }
                }
            }
            return true;
        }

        public void onFrameRendered(MediaCodecAdapter mediaCodecAdapter, long j, long j2) {
            if (Util.a < 30) {
                Handler handler = this.c;
                handler.sendMessageAtFrontOfQueue(Message.obtain(handler, 0, (int) (j >> 32), (int) j));
                return;
            }
            MediaCodecVideoRenderer mediaCodecVideoRenderer = MediaCodecVideoRenderer.this;
            if (this == mediaCodecVideoRenderer.U) {
                if (j == Long.MAX_VALUE) {
                    mediaCodecVideoRenderer.cc = true;
                    return;
                }
                try {
                    mediaCodecVideoRenderer.ay(j);
                    mediaCodecVideoRenderer.bg();
                    mediaCodecVideoRenderer.ci.b++;
                    mediaCodecVideoRenderer.bf();
                    mediaCodecVideoRenderer.ai(j);
                } catch (ExoPlaybackException e) {
                    mediaCodecVideoRenderer.ch = e;
                }
            }
        }
    }

    public MediaCodecVideoRenderer(Context context, MediaCodecSelector mediaCodecSelector) {
        this(context, mediaCodecSelector, 0);
    }

    public static boolean ba(String str) {
        if (str.startsWith("OMX.google")) {
            return false;
        }
        synchronized (MediaCodecVideoRenderer.class) {
            if (!X) {
                Y = bb();
                X = true;
            }
        }
        return Y;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:438:0x07cf, code lost:
        if (r0.equals("NX573J") == false) goto L_0x008c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:462:0x0831, code lost:
        if (r0.equals("AFTN") == false) goto L_0x0829;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:470:0x084d, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean bb() {
        /*
            int r0 = com.google.android.exoplayer2.util.Util.a
            r1 = 6
            r2 = 5
            r3 = 4
            r4 = 3
            r5 = 2
            r6 = -1
            r7 = 0
            r8 = 1
            r9 = 28
            if (r0 > r9) goto L_0x006d
            java.lang.String r10 = com.google.android.exoplayer2.util.Util.b
            r10.getClass()
            int r11 = r10.hashCode()
            switch(r11) {
                case -1339091551: goto L_0x005e;
                case -1220081023: goto L_0x0053;
                case -1220066608: goto L_0x0048;
                case -1012436106: goto L_0x003d;
                case -64886864: goto L_0x0032;
                case 3415681: goto L_0x0027;
                case 825323514: goto L_0x001c;
                default: goto L_0x001a;
            }
        L_0x001a:
            r10 = -1
            goto L_0x0068
        L_0x001c:
            java.lang.String r11 = "machuca"
            boolean r10 = r10.equals(r11)
            if (r10 != 0) goto L_0x0025
            goto L_0x001a
        L_0x0025:
            r10 = 6
            goto L_0x0068
        L_0x0027:
            java.lang.String r11 = "once"
            boolean r10 = r10.equals(r11)
            if (r10 != 0) goto L_0x0030
            goto L_0x001a
        L_0x0030:
            r10 = 5
            goto L_0x0068
        L_0x0032:
            java.lang.String r11 = "magnolia"
            boolean r10 = r10.equals(r11)
            if (r10 != 0) goto L_0x003b
            goto L_0x001a
        L_0x003b:
            r10 = 4
            goto L_0x0068
        L_0x003d:
            java.lang.String r11 = "oneday"
            boolean r10 = r10.equals(r11)
            if (r10 != 0) goto L_0x0046
            goto L_0x001a
        L_0x0046:
            r10 = 3
            goto L_0x0068
        L_0x0048:
            java.lang.String r11 = "dangalUHD"
            boolean r10 = r10.equals(r11)
            if (r10 != 0) goto L_0x0051
            goto L_0x001a
        L_0x0051:
            r10 = 2
            goto L_0x0068
        L_0x0053:
            java.lang.String r11 = "dangalFHD"
            boolean r10 = r10.equals(r11)
            if (r10 != 0) goto L_0x005c
            goto L_0x001a
        L_0x005c:
            r10 = 1
            goto L_0x0068
        L_0x005e:
            java.lang.String r11 = "dangal"
            boolean r10 = r10.equals(r11)
            if (r10 != 0) goto L_0x0067
            goto L_0x001a
        L_0x0067:
            r10 = 0
        L_0x0068:
            switch(r10) {
                case 0: goto L_0x006c;
                case 1: goto L_0x006c;
                case 2: goto L_0x006c;
                case 3: goto L_0x006c;
                case 4: goto L_0x006c;
                case 5: goto L_0x006c;
                case 6: goto L_0x006c;
                default: goto L_0x006b;
            }
        L_0x006b:
            goto L_0x006d
        L_0x006c:
            return r8
        L_0x006d:
            r10 = 27
            if (r0 > r10) goto L_0x007c
            java.lang.String r11 = "HWEML"
            java.lang.String r12 = com.google.android.exoplayer2.util.Util.b
            boolean r11 = r11.equals(r12)
            if (r11 == 0) goto L_0x007c
            return r8
        L_0x007c:
            r11 = 26
            if (r0 > r11) goto L_0x084e
            java.lang.String r0 = com.google.android.exoplayer2.util.Util.b
            r0.getClass()
            int r12 = r0.hashCode()
            switch(r12) {
                case -2144781245: goto L_0x080f;
                case -2144781185: goto L_0x0803;
                case -2144781160: goto L_0x07f7;
                case -2097309513: goto L_0x07eb;
                case -2022874474: goto L_0x07df;
                case -1978993182: goto L_0x07d3;
                case -1978990237: goto L_0x07c9;
                case -1936688988: goto L_0x07bd;
                case -1936688066: goto L_0x07b0;
                case -1936688065: goto L_0x07a2;
                case -1931988508: goto L_0x0794;
                case -1885099851: goto L_0x0786;
                case -1696512866: goto L_0x0778;
                case -1680025915: goto L_0x076a;
                case -1615810839: goto L_0x075c;
                case -1600724499: goto L_0x074e;
                case -1554255044: goto L_0x0740;
                case -1481772737: goto L_0x0732;
                case -1481772730: goto L_0x0724;
                case -1481772729: goto L_0x0716;
                case -1320080169: goto L_0x0708;
                case -1217592143: goto L_0x06fa;
                case -1180384755: goto L_0x06ec;
                case -1139198265: goto L_0x06de;
                case -1052835013: goto L_0x06d0;
                case -993250464: goto L_0x06c2;
                case -993250458: goto L_0x06b4;
                case -965403638: goto L_0x06a6;
                case -958336948: goto L_0x0698;
                case -879245230: goto L_0x068a;
                case -842500323: goto L_0x067c;
                case -821392978: goto L_0x066e;
                case -797483286: goto L_0x0660;
                case -794946968: goto L_0x0652;
                case -788334647: goto L_0x0644;
                case -782144577: goto L_0x0636;
                case -575125681: goto L_0x0628;
                case -521118391: goto L_0x061a;
                case -430914369: goto L_0x060c;
                case -290434366: goto L_0x05fe;
                case -282781963: goto L_0x05f0;
                case -277133239: goto L_0x05e2;
                case -173639913: goto L_0x05d4;
                case -56598463: goto L_0x05c6;
                case 2126: goto L_0x05b8;
                case 2564: goto L_0x05aa;
                case 2715: goto L_0x059c;
                case 2719: goto L_0x058e;
                case 3091: goto L_0x0580;
                case 3483: goto L_0x0572;
                case 73405: goto L_0x0564;
                case 75537: goto L_0x0556;
                case 75739: goto L_0x0548;
                case 76779: goto L_0x053a;
                case 78669: goto L_0x052c;
                case 79305: goto L_0x051e;
                case 80618: goto L_0x0510;
                case 88274: goto L_0x0502;
                case 98846: goto L_0x04f4;
                case 98848: goto L_0x04e6;
                case 99329: goto L_0x04d8;
                case 101481: goto L_0x04ca;
                case 1513190: goto L_0x04bc;
                case 1514184: goto L_0x04ae;
                case 1514185: goto L_0x04a0;
                case 2133089: goto L_0x0492;
                case 2133091: goto L_0x0484;
                case 2133120: goto L_0x0476;
                case 2133151: goto L_0x0468;
                case 2133182: goto L_0x045a;
                case 2133184: goto L_0x044c;
                case 2436959: goto L_0x043e;
                case 2463773: goto L_0x0430;
                case 2464648: goto L_0x0422;
                case 2689555: goto L_0x0414;
                case 3154429: goto L_0x0406;
                case 3284551: goto L_0x03f8;
                case 3351335: goto L_0x03ea;
                case 3386211: goto L_0x03dc;
                case 41325051: goto L_0x03ce;
                case 51349633: goto L_0x03c0;
                case 51350594: goto L_0x03b2;
                case 55178625: goto L_0x03a4;
                case 61542055: goto L_0x0396;
                case 65355429: goto L_0x0388;
                case 66214468: goto L_0x037a;
                case 66214470: goto L_0x036c;
                case 66214473: goto L_0x035e;
                case 66215429: goto L_0x0350;
                case 66215431: goto L_0x0342;
                case 66215433: goto L_0x0334;
                case 66216390: goto L_0x0326;
                case 76402249: goto L_0x0318;
                case 76404105: goto L_0x030a;
                case 76404911: goto L_0x02fc;
                case 80963634: goto L_0x02ee;
                case 82882791: goto L_0x02e0;
                case 98715550: goto L_0x02d2;
                case 101370885: goto L_0x02c4;
                case 102844228: goto L_0x02b6;
                case 165221241: goto L_0x02a8;
                case 182191441: goto L_0x029a;
                case 245388979: goto L_0x028c;
                case 287431619: goto L_0x027e;
                case 307593612: goto L_0x0270;
                case 308517133: goto L_0x0262;
                case 316215098: goto L_0x0254;
                case 316215116: goto L_0x0246;
                case 316246811: goto L_0x0238;
                case 316246818: goto L_0x022a;
                case 407160593: goto L_0x021c;
                case 507412548: goto L_0x020e;
                case 793982701: goto L_0x0200;
                case 794038622: goto L_0x01f2;
                case 794040393: goto L_0x01e4;
                case 835649806: goto L_0x01d6;
                case 917340916: goto L_0x01c8;
                case 958008161: goto L_0x01ba;
                case 1060579533: goto L_0x01ac;
                case 1150207623: goto L_0x019e;
                case 1176899427: goto L_0x0190;
                case 1280332038: goto L_0x0182;
                case 1306947716: goto L_0x0174;
                case 1349174697: goto L_0x0166;
                case 1522194893: goto L_0x0158;
                case 1691543273: goto L_0x014a;
                case 1691544261: goto L_0x013c;
                case 1709443163: goto L_0x012e;
                case 1865889110: goto L_0x0120;
                case 1906253259: goto L_0x0112;
                case 1977196784: goto L_0x0104;
                case 2006372676: goto L_0x00f7;
                case 2019281702: goto L_0x00ea;
                case 2029784656: goto L_0x00dd;
                case 2030379515: goto L_0x00d0;
                case 2033393791: goto L_0x00c3;
                case 2047190025: goto L_0x00b6;
                case 2047252157: goto L_0x00a9;
                case 2048319463: goto L_0x009c;
                case 2048855701: goto L_0x008f;
                default: goto L_0x008c;
            }
        L_0x008c:
            r1 = -1
            goto L_0x081a
        L_0x008f:
            java.lang.String r1 = "HWWAS-H"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0098
            goto L_0x008c
        L_0x0098:
            r1 = 139(0x8b, float:1.95E-43)
            goto L_0x081a
        L_0x009c:
            java.lang.String r1 = "HWVNS-H"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x00a5
            goto L_0x008c
        L_0x00a5:
            r1 = 138(0x8a, float:1.93E-43)
            goto L_0x081a
        L_0x00a9:
            java.lang.String r1 = "ELUGA_Prim"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x00b2
            goto L_0x008c
        L_0x00b2:
            r1 = 137(0x89, float:1.92E-43)
            goto L_0x081a
        L_0x00b6:
            java.lang.String r1 = "ELUGA_Note"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x00bf
            goto L_0x008c
        L_0x00bf:
            r1 = 136(0x88, float:1.9E-43)
            goto L_0x081a
        L_0x00c3:
            java.lang.String r1 = "ASUS_X00AD_2"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x00cc
            goto L_0x008c
        L_0x00cc:
            r1 = 135(0x87, float:1.89E-43)
            goto L_0x081a
        L_0x00d0:
            java.lang.String r1 = "HWCAM-H"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x00d9
            goto L_0x008c
        L_0x00d9:
            r1 = 134(0x86, float:1.88E-43)
            goto L_0x081a
        L_0x00dd:
            java.lang.String r1 = "HWBLN-H"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x00e6
            goto L_0x008c
        L_0x00e6:
            r1 = 133(0x85, float:1.86E-43)
            goto L_0x081a
        L_0x00ea:
            java.lang.String r1 = "DM-01K"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x00f3
            goto L_0x008c
        L_0x00f3:
            r1 = 132(0x84, float:1.85E-43)
            goto L_0x081a
        L_0x00f7:
            java.lang.String r1 = "BRAVIA_ATV3_4K"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0100
            goto L_0x008c
        L_0x0100:
            r1 = 131(0x83, float:1.84E-43)
            goto L_0x081a
        L_0x0104:
            java.lang.String r1 = "Infinix-X572"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x010e
            goto L_0x008c
        L_0x010e:
            r1 = 130(0x82, float:1.82E-43)
            goto L_0x081a
        L_0x0112:
            java.lang.String r1 = "PB2-670M"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x011c
            goto L_0x008c
        L_0x011c:
            r1 = 129(0x81, float:1.81E-43)
            goto L_0x081a
        L_0x0120:
            java.lang.String r1 = "santoni"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x012a
            goto L_0x008c
        L_0x012a:
            r1 = 128(0x80, float:1.794E-43)
            goto L_0x081a
        L_0x012e:
            java.lang.String r1 = "iball8735_9806"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0138
            goto L_0x008c
        L_0x0138:
            r1 = 127(0x7f, float:1.78E-43)
            goto L_0x081a
        L_0x013c:
            java.lang.String r1 = "CPH1715"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0146
            goto L_0x008c
        L_0x0146:
            r1 = 126(0x7e, float:1.77E-43)
            goto L_0x081a
        L_0x014a:
            java.lang.String r1 = "CPH1609"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0154
            goto L_0x008c
        L_0x0154:
            r1 = 125(0x7d, float:1.75E-43)
            goto L_0x081a
        L_0x0158:
            java.lang.String r1 = "woods_f"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0162
            goto L_0x008c
        L_0x0162:
            r1 = 124(0x7c, float:1.74E-43)
            goto L_0x081a
        L_0x0166:
            java.lang.String r1 = "htc_e56ml_dtul"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0170
            goto L_0x008c
        L_0x0170:
            r1 = 123(0x7b, float:1.72E-43)
            goto L_0x081a
        L_0x0174:
            java.lang.String r1 = "EverStar_S"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x017e
            goto L_0x008c
        L_0x017e:
            r1 = 122(0x7a, float:1.71E-43)
            goto L_0x081a
        L_0x0182:
            java.lang.String r1 = "hwALE-H"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x018c
            goto L_0x008c
        L_0x018c:
            r1 = 121(0x79, float:1.7E-43)
            goto L_0x081a
        L_0x0190:
            java.lang.String r1 = "itel_S41"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x019a
            goto L_0x008c
        L_0x019a:
            r1 = 120(0x78, float:1.68E-43)
            goto L_0x081a
        L_0x019e:
            java.lang.String r1 = "LS-5017"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x01a8
            goto L_0x008c
        L_0x01a8:
            r1 = 119(0x77, float:1.67E-43)
            goto L_0x081a
        L_0x01ac:
            java.lang.String r1 = "panell_d"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x01b6
            goto L_0x008c
        L_0x01b6:
            r1 = 118(0x76, float:1.65E-43)
            goto L_0x081a
        L_0x01ba:
            java.lang.String r1 = "j2xlteins"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x01c4
            goto L_0x008c
        L_0x01c4:
            r1 = 117(0x75, float:1.64E-43)
            goto L_0x081a
        L_0x01c8:
            java.lang.String r1 = "A7000plus"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x01d2
            goto L_0x008c
        L_0x01d2:
            r1 = 116(0x74, float:1.63E-43)
            goto L_0x081a
        L_0x01d6:
            java.lang.String r1 = "manning"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x01e0
            goto L_0x008c
        L_0x01e0:
            r1 = 115(0x73, float:1.61E-43)
            goto L_0x081a
        L_0x01e4:
            java.lang.String r1 = "GIONEE_WBL7519"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x01ee
            goto L_0x008c
        L_0x01ee:
            r1 = 114(0x72, float:1.6E-43)
            goto L_0x081a
        L_0x01f2:
            java.lang.String r1 = "GIONEE_WBL7365"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x01fc
            goto L_0x008c
        L_0x01fc:
            r1 = 113(0x71, float:1.58E-43)
            goto L_0x081a
        L_0x0200:
            java.lang.String r1 = "GIONEE_WBL5708"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x020a
            goto L_0x008c
        L_0x020a:
            r1 = 112(0x70, float:1.57E-43)
            goto L_0x081a
        L_0x020e:
            java.lang.String r1 = "QM16XE_U"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0218
            goto L_0x008c
        L_0x0218:
            r1 = 111(0x6f, float:1.56E-43)
            goto L_0x081a
        L_0x021c:
            java.lang.String r1 = "Pixi5-10_4G"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0226
            goto L_0x008c
        L_0x0226:
            r1 = 110(0x6e, float:1.54E-43)
            goto L_0x081a
        L_0x022a:
            java.lang.String r1 = "TB3-850M"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0234
            goto L_0x008c
        L_0x0234:
            r1 = 109(0x6d, float:1.53E-43)
            goto L_0x081a
        L_0x0238:
            java.lang.String r1 = "TB3-850F"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0242
            goto L_0x008c
        L_0x0242:
            r1 = 108(0x6c, float:1.51E-43)
            goto L_0x081a
        L_0x0246:
            java.lang.String r1 = "TB3-730X"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0250
            goto L_0x008c
        L_0x0250:
            r1 = 107(0x6b, float:1.5E-43)
            goto L_0x081a
        L_0x0254:
            java.lang.String r1 = "TB3-730F"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x025e
            goto L_0x008c
        L_0x025e:
            r1 = 106(0x6a, float:1.49E-43)
            goto L_0x081a
        L_0x0262:
            java.lang.String r1 = "A7020a48"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x026c
            goto L_0x008c
        L_0x026c:
            r1 = 105(0x69, float:1.47E-43)
            goto L_0x081a
        L_0x0270:
            java.lang.String r1 = "A7010a48"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x027a
            goto L_0x008c
        L_0x027a:
            r1 = 104(0x68, float:1.46E-43)
            goto L_0x081a
        L_0x027e:
            java.lang.String r1 = "griffin"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0288
            goto L_0x008c
        L_0x0288:
            r1 = 103(0x67, float:1.44E-43)
            goto L_0x081a
        L_0x028c:
            java.lang.String r1 = "marino_f"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0296
            goto L_0x008c
        L_0x0296:
            r1 = 102(0x66, float:1.43E-43)
            goto L_0x081a
        L_0x029a:
            java.lang.String r1 = "CPY83_I00"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x02a4
            goto L_0x008c
        L_0x02a4:
            r1 = 101(0x65, float:1.42E-43)
            goto L_0x081a
        L_0x02a8:
            java.lang.String r1 = "A2016a40"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x02b2
            goto L_0x008c
        L_0x02b2:
            r1 = 100
            goto L_0x081a
        L_0x02b6:
            java.lang.String r1 = "le_x6"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x02c0
            goto L_0x008c
        L_0x02c0:
            r1 = 99
            goto L_0x081a
        L_0x02c4:
            java.lang.String r1 = "l5460"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x02ce
            goto L_0x008c
        L_0x02ce:
            r1 = 98
            goto L_0x081a
        L_0x02d2:
            java.lang.String r1 = "i9031"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x02dc
            goto L_0x008c
        L_0x02dc:
            r1 = 97
            goto L_0x081a
        L_0x02e0:
            java.lang.String r1 = "X3_HK"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x02ea
            goto L_0x008c
        L_0x02ea:
            r1 = 96
            goto L_0x081a
        L_0x02ee:
            java.lang.String r1 = "V23GB"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x02f8
            goto L_0x008c
        L_0x02f8:
            r1 = 95
            goto L_0x081a
        L_0x02fc:
            java.lang.String r1 = "Q4310"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0306
            goto L_0x008c
        L_0x0306:
            r1 = 94
            goto L_0x081a
        L_0x030a:
            java.lang.String r1 = "Q4260"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0314
            goto L_0x008c
        L_0x0314:
            r1 = 93
            goto L_0x081a
        L_0x0318:
            java.lang.String r1 = "PRO7S"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0322
            goto L_0x008c
        L_0x0322:
            r1 = 92
            goto L_0x081a
        L_0x0326:
            java.lang.String r1 = "F3311"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0330
            goto L_0x008c
        L_0x0330:
            r1 = 91
            goto L_0x081a
        L_0x0334:
            java.lang.String r1 = "F3215"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x033e
            goto L_0x008c
        L_0x033e:
            r1 = 90
            goto L_0x081a
        L_0x0342:
            java.lang.String r1 = "F3213"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x034c
            goto L_0x008c
        L_0x034c:
            r1 = 89
            goto L_0x081a
        L_0x0350:
            java.lang.String r1 = "F3211"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x035a
            goto L_0x008c
        L_0x035a:
            r1 = 88
            goto L_0x081a
        L_0x035e:
            java.lang.String r1 = "F3116"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0368
            goto L_0x008c
        L_0x0368:
            r1 = 87
            goto L_0x081a
        L_0x036c:
            java.lang.String r1 = "F3113"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0376
            goto L_0x008c
        L_0x0376:
            r1 = 86
            goto L_0x081a
        L_0x037a:
            java.lang.String r1 = "F3111"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0384
            goto L_0x008c
        L_0x0384:
            r1 = 85
            goto L_0x081a
        L_0x0388:
            java.lang.String r1 = "E5643"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0392
            goto L_0x008c
        L_0x0392:
            r1 = 84
            goto L_0x081a
        L_0x0396:
            java.lang.String r1 = "A1601"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x03a0
            goto L_0x008c
        L_0x03a0:
            r1 = 83
            goto L_0x081a
        L_0x03a4:
            java.lang.String r1 = "Aura_Note_2"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x03ae
            goto L_0x008c
        L_0x03ae:
            r1 = 82
            goto L_0x081a
        L_0x03b2:
            java.lang.String r1 = "602LV"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x03bc
            goto L_0x008c
        L_0x03bc:
            r1 = 81
            goto L_0x081a
        L_0x03c0:
            java.lang.String r1 = "601LV"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x03ca
            goto L_0x008c
        L_0x03ca:
            r1 = 80
            goto L_0x081a
        L_0x03ce:
            java.lang.String r1 = "MEIZU_M5"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x03d8
            goto L_0x008c
        L_0x03d8:
            r1 = 79
            goto L_0x081a
        L_0x03dc:
            java.lang.String r1 = "p212"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x03e6
            goto L_0x008c
        L_0x03e6:
            r1 = 78
            goto L_0x081a
        L_0x03ea:
            java.lang.String r1 = "mido"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x03f4
            goto L_0x008c
        L_0x03f4:
            r1 = 77
            goto L_0x081a
        L_0x03f8:
            java.lang.String r1 = "kate"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0402
            goto L_0x008c
        L_0x0402:
            r1 = 76
            goto L_0x081a
        L_0x0406:
            java.lang.String r1 = "fugu"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0410
            goto L_0x008c
        L_0x0410:
            r1 = 75
            goto L_0x081a
        L_0x0414:
            java.lang.String r1 = "XE2X"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x041e
            goto L_0x008c
        L_0x041e:
            r1 = 74
            goto L_0x081a
        L_0x0422:
            java.lang.String r1 = "Q427"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x042c
            goto L_0x008c
        L_0x042c:
            r1 = 73
            goto L_0x081a
        L_0x0430:
            java.lang.String r1 = "Q350"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x043a
            goto L_0x008c
        L_0x043a:
            r1 = 72
            goto L_0x081a
        L_0x043e:
            java.lang.String r1 = "P681"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0448
            goto L_0x008c
        L_0x0448:
            r1 = 71
            goto L_0x081a
        L_0x044c:
            java.lang.String r1 = "F04J"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0456
            goto L_0x008c
        L_0x0456:
            r1 = 70
            goto L_0x081a
        L_0x045a:
            java.lang.String r1 = "F04H"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0464
            goto L_0x008c
        L_0x0464:
            r1 = 69
            goto L_0x081a
        L_0x0468:
            java.lang.String r1 = "F03H"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0472
            goto L_0x008c
        L_0x0472:
            r1 = 68
            goto L_0x081a
        L_0x0476:
            java.lang.String r1 = "F02H"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0480
            goto L_0x008c
        L_0x0480:
            r1 = 67
            goto L_0x081a
        L_0x0484:
            java.lang.String r1 = "F01J"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x048e
            goto L_0x008c
        L_0x048e:
            r1 = 66
            goto L_0x081a
        L_0x0492:
            java.lang.String r1 = "F01H"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x049c
            goto L_0x008c
        L_0x049c:
            r1 = 65
            goto L_0x081a
        L_0x04a0:
            java.lang.String r1 = "1714"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x04aa
            goto L_0x008c
        L_0x04aa:
            r1 = 64
            goto L_0x081a
        L_0x04ae:
            java.lang.String r1 = "1713"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x04b8
            goto L_0x008c
        L_0x04b8:
            r1 = 63
            goto L_0x081a
        L_0x04bc:
            java.lang.String r1 = "1601"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x04c6
            goto L_0x008c
        L_0x04c6:
            r1 = 62
            goto L_0x081a
        L_0x04ca:
            java.lang.String r1 = "flo"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x04d4
            goto L_0x008c
        L_0x04d4:
            r1 = 61
            goto L_0x081a
        L_0x04d8:
            java.lang.String r1 = "deb"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x04e2
            goto L_0x008c
        L_0x04e2:
            r1 = 60
            goto L_0x081a
        L_0x04e6:
            java.lang.String r1 = "cv3"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x04f0
            goto L_0x008c
        L_0x04f0:
            r1 = 59
            goto L_0x081a
        L_0x04f4:
            java.lang.String r1 = "cv1"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x04fe
            goto L_0x008c
        L_0x04fe:
            r1 = 58
            goto L_0x081a
        L_0x0502:
            java.lang.String r1 = "Z80"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x050c
            goto L_0x008c
        L_0x050c:
            r1 = 57
            goto L_0x081a
        L_0x0510:
            java.lang.String r1 = "QX1"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x051a
            goto L_0x008c
        L_0x051a:
            r1 = 56
            goto L_0x081a
        L_0x051e:
            java.lang.String r1 = "PLE"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0528
            goto L_0x008c
        L_0x0528:
            r1 = 55
            goto L_0x081a
        L_0x052c:
            java.lang.String r1 = "P85"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0536
            goto L_0x008c
        L_0x0536:
            r1 = 54
            goto L_0x081a
        L_0x053a:
            java.lang.String r1 = "MX6"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0544
            goto L_0x008c
        L_0x0544:
            r1 = 53
            goto L_0x081a
        L_0x0548:
            java.lang.String r1 = "M5c"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0552
            goto L_0x008c
        L_0x0552:
            r1 = 52
            goto L_0x081a
        L_0x0556:
            java.lang.String r1 = "M04"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0560
            goto L_0x008c
        L_0x0560:
            r1 = 51
            goto L_0x081a
        L_0x0564:
            java.lang.String r1 = "JGZ"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x056e
            goto L_0x008c
        L_0x056e:
            r1 = 50
            goto L_0x081a
        L_0x0572:
            java.lang.String r1 = "mh"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x057c
            goto L_0x008c
        L_0x057c:
            r1 = 49
            goto L_0x081a
        L_0x0580:
            java.lang.String r1 = "b5"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x058a
            goto L_0x008c
        L_0x058a:
            r1 = 48
            goto L_0x081a
        L_0x058e:
            java.lang.String r1 = "V5"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0598
            goto L_0x008c
        L_0x0598:
            r1 = 47
            goto L_0x081a
        L_0x059c:
            java.lang.String r1 = "V1"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x05a6
            goto L_0x008c
        L_0x05a6:
            r1 = 46
            goto L_0x081a
        L_0x05aa:
            java.lang.String r1 = "Q5"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x05b4
            goto L_0x008c
        L_0x05b4:
            r1 = 45
            goto L_0x081a
        L_0x05b8:
            java.lang.String r1 = "C1"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x05c2
            goto L_0x008c
        L_0x05c2:
            r1 = 44
            goto L_0x081a
        L_0x05c6:
            java.lang.String r1 = "woods_fn"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x05d0
            goto L_0x008c
        L_0x05d0:
            r1 = 43
            goto L_0x081a
        L_0x05d4:
            java.lang.String r1 = "ELUGA_A3_Pro"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x05de
            goto L_0x008c
        L_0x05de:
            r1 = 42
            goto L_0x081a
        L_0x05e2:
            java.lang.String r1 = "Z12_PRO"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x05ec
            goto L_0x008c
        L_0x05ec:
            r1 = 41
            goto L_0x081a
        L_0x05f0:
            java.lang.String r1 = "BLACK-1X"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x05fa
            goto L_0x008c
        L_0x05fa:
            r1 = 40
            goto L_0x081a
        L_0x05fe:
            java.lang.String r1 = "taido_row"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0608
            goto L_0x008c
        L_0x0608:
            r1 = 39
            goto L_0x081a
        L_0x060c:
            java.lang.String r1 = "Pixi4-7_3G"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0616
            goto L_0x008c
        L_0x0616:
            r1 = 38
            goto L_0x081a
        L_0x061a:
            java.lang.String r1 = "GIONEE_GBL7360"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0624
            goto L_0x008c
        L_0x0624:
            r1 = 37
            goto L_0x081a
        L_0x0628:
            java.lang.String r1 = "GiONEE_CBL7513"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0632
            goto L_0x008c
        L_0x0632:
            r1 = 36
            goto L_0x081a
        L_0x0636:
            java.lang.String r1 = "OnePlus5T"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0640
            goto L_0x008c
        L_0x0640:
            r1 = 35
            goto L_0x081a
        L_0x0644:
            java.lang.String r1 = "whyred"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x064e
            goto L_0x008c
        L_0x064e:
            r1 = 34
            goto L_0x081a
        L_0x0652:
            java.lang.String r1 = "watson"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x065c
            goto L_0x008c
        L_0x065c:
            r1 = 33
            goto L_0x081a
        L_0x0660:
            java.lang.String r1 = "SVP-DTV15"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x066a
            goto L_0x008c
        L_0x066a:
            r1 = 32
            goto L_0x081a
        L_0x066e:
            java.lang.String r1 = "A7000-a"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0678
            goto L_0x008c
        L_0x0678:
            r1 = 31
            goto L_0x081a
        L_0x067c:
            java.lang.String r1 = "nicklaus_f"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0686
            goto L_0x008c
        L_0x0686:
            r1 = 30
            goto L_0x081a
        L_0x068a:
            java.lang.String r1 = "tcl_eu"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0694
            goto L_0x008c
        L_0x0694:
            r1 = 29
            goto L_0x081a
        L_0x0698:
            java.lang.String r1 = "ELUGA_Ray_X"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x06a2
            goto L_0x008c
        L_0x06a2:
            r1 = 28
            goto L_0x081a
        L_0x06a6:
            java.lang.String r1 = "s905x018"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x06b0
            goto L_0x008c
        L_0x06b0:
            r1 = 27
            goto L_0x081a
        L_0x06b4:
            java.lang.String r1 = "A10-70L"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x06be
            goto L_0x008c
        L_0x06be:
            r1 = 26
            goto L_0x081a
        L_0x06c2:
            java.lang.String r1 = "A10-70F"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x06cc
            goto L_0x008c
        L_0x06cc:
            r1 = 25
            goto L_0x081a
        L_0x06d0:
            java.lang.String r1 = "namath"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x06da
            goto L_0x008c
        L_0x06da:
            r1 = 24
            goto L_0x081a
        L_0x06de:
            java.lang.String r1 = "Slate_Pro"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x06e8
            goto L_0x008c
        L_0x06e8:
            r1 = 23
            goto L_0x081a
        L_0x06ec:
            java.lang.String r1 = "iris60"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x06f6
            goto L_0x008c
        L_0x06f6:
            r1 = 22
            goto L_0x081a
        L_0x06fa:
            java.lang.String r1 = "BRAVIA_ATV2"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0704
            goto L_0x008c
        L_0x0704:
            r1 = 21
            goto L_0x081a
        L_0x0708:
            java.lang.String r1 = "GiONEE_GBL7319"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0712
            goto L_0x008c
        L_0x0712:
            r1 = 20
            goto L_0x081a
        L_0x0716:
            java.lang.String r1 = "panell_dt"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0720
            goto L_0x008c
        L_0x0720:
            r1 = 19
            goto L_0x081a
        L_0x0724:
            java.lang.String r1 = "panell_ds"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x072e
            goto L_0x008c
        L_0x072e:
            r1 = 18
            goto L_0x081a
        L_0x0732:
            java.lang.String r1 = "panell_dl"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x073c
            goto L_0x008c
        L_0x073c:
            r1 = 17
            goto L_0x081a
        L_0x0740:
            java.lang.String r1 = "vernee_M5"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x074a
            goto L_0x008c
        L_0x074a:
            r1 = 16
            goto L_0x081a
        L_0x074e:
            java.lang.String r1 = "pacificrim"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0758
            goto L_0x008c
        L_0x0758:
            r1 = 15
            goto L_0x081a
        L_0x075c:
            java.lang.String r1 = "Phantom6"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0766
            goto L_0x008c
        L_0x0766:
            r1 = 14
            goto L_0x081a
        L_0x076a:
            java.lang.String r1 = "ComioS1"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0774
            goto L_0x008c
        L_0x0774:
            r1 = 13
            goto L_0x081a
        L_0x0778:
            java.lang.String r1 = "XT1663"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0782
            goto L_0x008c
        L_0x0782:
            r1 = 12
            goto L_0x081a
        L_0x0786:
            java.lang.String r1 = "RAIJIN"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0790
            goto L_0x008c
        L_0x0790:
            r1 = 11
            goto L_0x081a
        L_0x0794:
            java.lang.String r1 = "AquaPowerM"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x079e
            goto L_0x008c
        L_0x079e:
            r1 = 10
            goto L_0x081a
        L_0x07a2:
            java.lang.String r1 = "PGN611"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x07ac
            goto L_0x008c
        L_0x07ac:
            r1 = 9
            goto L_0x081a
        L_0x07b0:
            java.lang.String r1 = "PGN610"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x07ba
            goto L_0x008c
        L_0x07ba:
            r1 = 8
            goto L_0x081a
        L_0x07bd:
            java.lang.String r1 = "PGN528"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x07c7
            goto L_0x008c
        L_0x07c7:
            r1 = 7
            goto L_0x081a
        L_0x07c9:
            java.lang.String r2 = "NX573J"
            boolean r0 = r0.equals(r2)
            if (r0 != 0) goto L_0x081a
            goto L_0x008c
        L_0x07d3:
            java.lang.String r1 = "NX541J"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x07dd
            goto L_0x008c
        L_0x07dd:
            r1 = 5
            goto L_0x081a
        L_0x07df:
            java.lang.String r1 = "CP8676_I02"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x07e9
            goto L_0x008c
        L_0x07e9:
            r1 = 4
            goto L_0x081a
        L_0x07eb:
            java.lang.String r1 = "K50a40"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x07f5
            goto L_0x008c
        L_0x07f5:
            r1 = 3
            goto L_0x081a
        L_0x07f7:
            java.lang.String r1 = "GIONEE_SWW1631"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0801
            goto L_0x008c
        L_0x0801:
            r1 = 2
            goto L_0x081a
        L_0x0803:
            java.lang.String r1 = "GIONEE_SWW1627"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x080d
            goto L_0x008c
        L_0x080d:
            r1 = 1
            goto L_0x081a
        L_0x080f:
            java.lang.String r1 = "GIONEE_SWW1609"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0819
            goto L_0x008c
        L_0x0819:
            r1 = 0
        L_0x081a:
            switch(r1) {
                case 0: goto L_0x084d;
                case 1: goto L_0x084d;
                case 2: goto L_0x084d;
                case 3: goto L_0x084d;
                case 4: goto L_0x084d;
                case 5: goto L_0x084d;
                case 6: goto L_0x084d;
                case 7: goto L_0x084d;
                case 8: goto L_0x084d;
                case 9: goto L_0x084d;
                case 10: goto L_0x084d;
                case 11: goto L_0x084d;
                case 12: goto L_0x084d;
                case 13: goto L_0x084d;
                case 14: goto L_0x084d;
                case 15: goto L_0x084d;
                case 16: goto L_0x084d;
                case 17: goto L_0x084d;
                case 18: goto L_0x084d;
                case 19: goto L_0x084d;
                case 20: goto L_0x084d;
                case 21: goto L_0x084d;
                case 22: goto L_0x084d;
                case 23: goto L_0x084d;
                case 24: goto L_0x084d;
                case 25: goto L_0x084d;
                case 26: goto L_0x084d;
                case 27: goto L_0x084d;
                case 28: goto L_0x084d;
                case 29: goto L_0x084d;
                case 30: goto L_0x084d;
                case 31: goto L_0x084d;
                case 32: goto L_0x084d;
                case 33: goto L_0x084d;
                case 34: goto L_0x084d;
                case 35: goto L_0x084d;
                case 36: goto L_0x084d;
                case 37: goto L_0x084d;
                case 38: goto L_0x084d;
                case 39: goto L_0x084d;
                case 40: goto L_0x084d;
                case 41: goto L_0x084d;
                case 42: goto L_0x084d;
                case 43: goto L_0x084d;
                case 44: goto L_0x084d;
                case 45: goto L_0x084d;
                case 46: goto L_0x084d;
                case 47: goto L_0x084d;
                case 48: goto L_0x084d;
                case 49: goto L_0x084d;
                case 50: goto L_0x084d;
                case 51: goto L_0x084d;
                case 52: goto L_0x084d;
                case 53: goto L_0x084d;
                case 54: goto L_0x084d;
                case 55: goto L_0x084d;
                case 56: goto L_0x084d;
                case 57: goto L_0x084d;
                case 58: goto L_0x084d;
                case 59: goto L_0x084d;
                case 60: goto L_0x084d;
                case 61: goto L_0x084d;
                case 62: goto L_0x084d;
                case 63: goto L_0x084d;
                case 64: goto L_0x084d;
                case 65: goto L_0x084d;
                case 66: goto L_0x084d;
                case 67: goto L_0x084d;
                case 68: goto L_0x084d;
                case 69: goto L_0x084d;
                case 70: goto L_0x084d;
                case 71: goto L_0x084d;
                case 72: goto L_0x084d;
                case 73: goto L_0x084d;
                case 74: goto L_0x084d;
                case 75: goto L_0x084d;
                case 76: goto L_0x084d;
                case 77: goto L_0x084d;
                case 78: goto L_0x084d;
                case 79: goto L_0x084d;
                case 80: goto L_0x084d;
                case 81: goto L_0x084d;
                case 82: goto L_0x084d;
                case 83: goto L_0x084d;
                case 84: goto L_0x084d;
                case 85: goto L_0x084d;
                case 86: goto L_0x084d;
                case 87: goto L_0x084d;
                case 88: goto L_0x084d;
                case 89: goto L_0x084d;
                case 90: goto L_0x084d;
                case 91: goto L_0x084d;
                case 92: goto L_0x084d;
                case 93: goto L_0x084d;
                case 94: goto L_0x084d;
                case 95: goto L_0x084d;
                case 96: goto L_0x084d;
                case 97: goto L_0x084d;
                case 98: goto L_0x084d;
                case 99: goto L_0x084d;
                case 100: goto L_0x084d;
                case 101: goto L_0x084d;
                case 102: goto L_0x084d;
                case 103: goto L_0x084d;
                case 104: goto L_0x084d;
                case 105: goto L_0x084d;
                case 106: goto L_0x084d;
                case 107: goto L_0x084d;
                case 108: goto L_0x084d;
                case 109: goto L_0x084d;
                case 110: goto L_0x084d;
                case 111: goto L_0x084d;
                case 112: goto L_0x084d;
                case 113: goto L_0x084d;
                case 114: goto L_0x084d;
                case 115: goto L_0x084d;
                case 116: goto L_0x084d;
                case 117: goto L_0x084d;
                case 118: goto L_0x084d;
                case 119: goto L_0x084d;
                case 120: goto L_0x084d;
                case 121: goto L_0x084d;
                case 122: goto L_0x084d;
                case 123: goto L_0x084d;
                case 124: goto L_0x084d;
                case 125: goto L_0x084d;
                case 126: goto L_0x084d;
                case 127: goto L_0x084d;
                case 128: goto L_0x084d;
                case 129: goto L_0x084d;
                case 130: goto L_0x084d;
                case 131: goto L_0x084d;
                case 132: goto L_0x084d;
                case 133: goto L_0x084d;
                case 134: goto L_0x084d;
                case 135: goto L_0x084d;
                case 136: goto L_0x084d;
                case 137: goto L_0x084d;
                case 138: goto L_0x084d;
                case 139: goto L_0x084d;
                default: goto L_0x081d;
            }
        L_0x081d:
            java.lang.String r0 = com.google.android.exoplayer2.util.Util.d
            r0.getClass()
            int r1 = r0.hashCode()
            switch(r1) {
                case -594534941: goto L_0x083f;
                case 2006354: goto L_0x0834;
                case 2006367: goto L_0x082b;
                default: goto L_0x0829;
            }
        L_0x0829:
            r5 = -1
            goto L_0x0849
        L_0x082b:
            java.lang.String r1 = "AFTN"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0849
            goto L_0x0829
        L_0x0834:
            java.lang.String r1 = "AFTA"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x083d
            goto L_0x0829
        L_0x083d:
            r5 = 1
            goto L_0x0849
        L_0x083f:
            java.lang.String r1 = "JSN-L21"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0848
            goto L_0x0829
        L_0x0848:
            r5 = 0
        L_0x0849:
            switch(r5) {
                case 0: goto L_0x084d;
                case 1: goto L_0x084d;
                case 2: goto L_0x084d;
                default: goto L_0x084c;
            }
        L_0x084c:
            goto L_0x084e
        L_0x084d:
            return r8
        L_0x084e:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.video.MediaCodecVideoRenderer.bb():boolean");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x006b, code lost:
        r2 = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00ac, code lost:
        return (r7 * 3) / (r2 * 2);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int bc(com.google.android.exoplayer2.mediacodec.MediaCodecInfo r5, java.lang.String r6, int r7, int r8) {
        /*
            r0 = -1
            if (r7 == r0) goto L_0x00ad
            if (r8 != r0) goto L_0x0007
            goto L_0x00ad
        L_0x0007:
            r6.getClass()
            int r1 = r6.hashCode()
            r2 = 4
            r3 = 3
            r4 = 2
            switch(r1) {
                case -1851077871: goto L_0x0058;
                case -1664118616: goto L_0x004d;
                case -1662541442: goto L_0x0042;
                case 1187890754: goto L_0x0037;
                case 1331836730: goto L_0x002c;
                case 1599127256: goto L_0x0021;
                case 1599127257: goto L_0x0016;
                default: goto L_0x0014;
            }
        L_0x0014:
            r6 = -1
            goto L_0x0062
        L_0x0016:
            java.lang.String r1 = "video/x-vnd.on2.vp9"
            boolean r6 = r6.equals(r1)
            if (r6 != 0) goto L_0x001f
            goto L_0x0014
        L_0x001f:
            r6 = 6
            goto L_0x0062
        L_0x0021:
            java.lang.String r1 = "video/x-vnd.on2.vp8"
            boolean r6 = r6.equals(r1)
            if (r6 != 0) goto L_0x002a
            goto L_0x0014
        L_0x002a:
            r6 = 5
            goto L_0x0062
        L_0x002c:
            java.lang.String r1 = "video/avc"
            boolean r6 = r6.equals(r1)
            if (r6 != 0) goto L_0x0035
            goto L_0x0014
        L_0x0035:
            r6 = 4
            goto L_0x0062
        L_0x0037:
            java.lang.String r1 = "video/mp4v-es"
            boolean r6 = r6.equals(r1)
            if (r6 != 0) goto L_0x0040
            goto L_0x0014
        L_0x0040:
            r6 = 3
            goto L_0x0062
        L_0x0042:
            java.lang.String r1 = "video/hevc"
            boolean r6 = r6.equals(r1)
            if (r6 != 0) goto L_0x004b
            goto L_0x0014
        L_0x004b:
            r6 = 2
            goto L_0x0062
        L_0x004d:
            java.lang.String r1 = "video/3gpp"
            boolean r6 = r6.equals(r1)
            if (r6 != 0) goto L_0x0056
            goto L_0x0014
        L_0x0056:
            r6 = 1
            goto L_0x0062
        L_0x0058:
            java.lang.String r1 = "video/dolby-vision"
            boolean r6 = r6.equals(r1)
            if (r6 != 0) goto L_0x0061
            goto L_0x0014
        L_0x0061:
            r6 = 0
        L_0x0062:
            switch(r6) {
                case 0: goto L_0x006d;
                case 1: goto L_0x0069;
                case 2: goto L_0x0066;
                case 3: goto L_0x0069;
                case 4: goto L_0x006d;
                case 5: goto L_0x0069;
                case 6: goto L_0x0066;
                default: goto L_0x0065;
            }
        L_0x0065:
            return r0
        L_0x0066:
            int r7 = r7 * r8
            goto L_0x00a7
        L_0x0069:
            int r7 = r7 * r8
        L_0x006b:
            r2 = 2
            goto L_0x00a7
        L_0x006d:
            java.lang.String r6 = com.google.android.exoplayer2.util.Util.d
            java.lang.String r1 = "BRAVIA 4K 2015"
            boolean r1 = r1.equals(r6)
            if (r1 != 0) goto L_0x00ad
            java.lang.String r1 = "Amazon"
            java.lang.String r2 = com.google.android.exoplayer2.util.Util.c
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0096
            java.lang.String r1 = "KFSOWI"
            boolean r1 = r1.equals(r6)
            if (r1 != 0) goto L_0x00ad
            java.lang.String r1 = "AFTS"
            boolean r6 = r1.equals(r6)
            if (r6 == 0) goto L_0x0096
            boolean r5 = r5.f
            if (r5 == 0) goto L_0x0096
            goto L_0x00ad
        L_0x0096:
            r5 = 16
            int r6 = com.google.android.exoplayer2.util.Util.ceilDivide((int) r7, (int) r5)
            int r7 = com.google.android.exoplayer2.util.Util.ceilDivide((int) r8, (int) r5)
            int r7 = r7 * r6
            int r7 = r7 * 16
            int r7 = r7 * 16
            goto L_0x006b
        L_0x00a7:
            int r7 = r7 * 3
            int r2 = r2 * 2
            int r7 = r7 / r2
            return r7
        L_0x00ad:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.video.MediaCodecVideoRenderer.bc(com.google.android.exoplayer2.mediacodec.MediaCodecInfo, java.lang.String, int, int):int");
    }

    public static List<MediaCodecInfo> bd(MediaCodecSelector mediaCodecSelector, Format format, boolean z, boolean z2) throws MediaCodecUtil.DecoderQueryException {
        Pair<Integer, Integer> codecProfileAndLevel;
        String str = format.p;
        if (str == null) {
            return Collections.emptyList();
        }
        List<MediaCodecInfo> decoderInfosSortedByFormatSupport = MediaCodecUtil.getDecoderInfosSortedByFormatSupport(mediaCodecSelector.getDecoderInfos(str, z, z2), format);
        if ("video/dolby-vision".equals(str) && (codecProfileAndLevel = MediaCodecUtil.getCodecProfileAndLevel(format)) != null) {
            int intValue = ((Integer) codecProfileAndLevel.first).intValue();
            if (intValue == 16 || intValue == 256) {
                decoderInfosSortedByFormatSupport.addAll(mediaCodecSelector.getDecoderInfos("video/hevc", z, z2));
            } else if (intValue == 512) {
                decoderInfosSortedByFormatSupport.addAll(mediaCodecSelector.getDecoderInfos("video/avc", z, z2));
            }
        }
        return Collections.unmodifiableList(decoderInfosSortedByFormatSupport);
    }

    public static int be(Format format, MediaCodecInfo mediaCodecInfo) {
        if (format.q != -1) {
            List<byte[]> list = format.r;
            int size = list.size();
            int i = 0;
            for (int i2 = 0; i2 < size; i2++) {
                i += list.get(i2).length;
            }
            return format.q + i;
        }
        return bc(mediaCodecInfo, format.p, format.u, format.v);
    }

    public final void ad(IllegalStateException illegalStateException) {
        Log.e("MediaCodecVideoRenderer", "Video codec error", illegalStateException);
        this.cp.videoCodecError(illegalStateException);
    }

    public final void ae(long j, long j2, String str) {
        this.cp.decoderInitialized(str, j, j2);
        this.cu = ba(str);
        this.cv = ((MediaCodecInfo) Assertions.checkNotNull(this.at)).isHdr10PlusOutOfBandMetadataSupported();
        if (Util.a >= 23 && this.S) {
            this.U = new a((MediaCodecAdapter) Assertions.checkNotNull(this.am));
        }
    }

    public final void af(String str) {
        this.cp.decoderReleased(str);
    }

    @Nullable
    public final DecoderReuseEvaluation ag(FormatHolder formatHolder) throws ExoPlaybackException {
        DecoderReuseEvaluation ag = super.ag(formatHolder);
        this.cp.inputFormatChanged(formatHolder.b, ag);
        return ag;
    }

    public final void ah(Format format, @Nullable MediaFormat mediaFormat) {
        boolean z;
        int i;
        int i2;
        MediaCodecAdapter mediaCodecAdapter = this.am;
        if (mediaCodecAdapter != null) {
            mediaCodecAdapter.setVideoScalingMode(this.cz);
        }
        if (this.S) {
            this.N = format.u;
            this.O = format.v;
        } else {
            Assertions.checkNotNull(mediaFormat);
            if (!mediaFormat.containsKey("crop-right") || !mediaFormat.containsKey("crop-left") || !mediaFormat.containsKey("crop-bottom") || !mediaFormat.containsKey("crop-top")) {
                z = false;
            } else {
                z = true;
            }
            if (z) {
                i = (mediaFormat.getInteger("crop-right") - mediaFormat.getInteger("crop-left")) + 1;
            } else {
                i = mediaFormat.getInteger("width");
            }
            this.N = i;
            if (z) {
                i2 = (mediaFormat.getInteger("crop-bottom") - mediaFormat.getInteger("crop-top")) + 1;
            } else {
                i2 = mediaFormat.getInteger("height");
            }
            this.O = i2;
        }
        float f = format.y;
        this.Q = f;
        int i3 = Util.a;
        int i4 = format.x;
        if (i3 < 21) {
            this.P = i4;
        } else if (i4 == 90 || i4 == 270) {
            int i5 = this.N;
            this.N = this.O;
            this.O = i5;
            this.Q = 1.0f / f;
        }
        this.co.onFormatChanged(format.w);
    }

    @CallSuper
    public final void ai(long j) {
        super.ai(j);
        if (!this.S) {
            this.I--;
        }
    }

    public final void aj() {
        az();
    }

    @CallSuper
    public final void ak(DecoderInputBuffer decoderInputBuffer) throws ExoPlaybackException {
        boolean z = this.S;
        if (!z) {
            this.I++;
        }
        if (Util.a < 23 && z) {
            long j = decoderInputBuffer.i;
            ay(j);
            bg();
            this.ci.b++;
            bf();
            ai(j);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00af, code lost:
        if ((((r5 > -30000 ? 1 : (r5 == -30000 ? 0 : -1)) < 0) && r17 > 100000) != false) goto L_0x00b1;
     */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00b8  */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x00dc  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean am(long r26, long r28, @androidx.annotation.Nullable com.google.android.exoplayer2.mediacodec.MediaCodecAdapter r30, @androidx.annotation.Nullable java.nio.ByteBuffer r31, int r32, int r33, int r34, long r35, boolean r37, boolean r38, com.google.android.exoplayer2.Format r39) throws com.google.android.exoplayer2.ExoPlaybackException {
        /*
            r25 = this;
            r0 = r25
            r1 = r26
            r3 = r30
            r4 = r32
            r5 = r35
            com.google.android.exoplayer2.util.Assertions.checkNotNull(r30)
            long r7 = r0.D
            r9 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            int r11 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r11 != 0) goto L_0x001a
            r0.D = r1
        L_0x001a:
            long r7 = r0.J
            com.google.android.exoplayer2.video.VideoFrameReleaseHelper r11 = r0.co
            int r12 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r12 == 0) goto L_0x0027
            r11.onNextFrame(r5)
            r0.J = r5
        L_0x0027:
            long r7 = r0.ck
            long r13 = r5 - r7
            r15 = 1
            if (r37 == 0) goto L_0x0034
            if (r38 != 0) goto L_0x0034
            r0.bk(r3, r4)
            return r15
        L_0x0034:
            float r12 = r0.ak
            double r9 = (double) r12
            int r12 = r25.getState()
            r15 = 2
            r16 = r11
            r11 = 0
            if (r12 != r15) goto L_0x0043
            r12 = 1
            goto L_0x0044
        L_0x0043:
            r12 = 0
        L_0x0044:
            long r17 = android.os.SystemClock.elapsedRealtime()
            r19 = 1000(0x3e8, double:4.94E-321)
            long r17 = r17 * r19
            long r5 = r5 - r1
            double r5 = (double) r5
            double r5 = r5 / r9
            long r5 = (long) r5
            if (r12 == 0) goto L_0x0055
            long r9 = r17 - r28
            long r5 = r5 - r9
        L_0x0055:
            android.view.Surface r9 = r0.cw
            com.google.android.exoplayer2.video.DummySurface r10 = r0.cx
            r21 = -30000(0xffffffffffff8ad0, double:NaN)
            if (r9 != r10) goto L_0x006f
            int r1 = (r5 > r21 ? 1 : (r5 == r21 ? 0 : -1))
            if (r1 >= 0) goto L_0x0063
            r1 = 1
            goto L_0x0064
        L_0x0063:
            r1 = 0
        L_0x0064:
            if (r1 == 0) goto L_0x006e
            r0.bk(r3, r4)
            r0.bm(r5)
            r9 = 1
            return r9
        L_0x006e:
            return r11
        L_0x006f:
            long r9 = r0.K
            long r17 = r17 - r9
            boolean r9 = r0.C
            if (r9 != 0) goto L_0x007e
            if (r12 != 0) goto L_0x0082
            boolean r9 = r0.B
            if (r9 == 0) goto L_0x0086
            goto L_0x0082
        L_0x007e:
            boolean r9 = r0.A
            if (r9 != 0) goto L_0x0086
        L_0x0082:
            r37 = r12
            r9 = 1
            goto L_0x0089
        L_0x0086:
            r37 = r12
            r9 = 0
        L_0x0089:
            long r11 = r0.E
            r23 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            int r15 = (r11 > r23 ? 1 : (r11 == r23 ? 0 : -1))
            if (r15 != 0) goto L_0x00b3
            int r11 = (r1 > r7 ? 1 : (r1 == r7 ? 0 : -1))
            if (r11 < 0) goto L_0x00b3
            if (r9 != 0) goto L_0x00b1
            if (r37 == 0) goto L_0x00b3
            int r7 = (r5 > r21 ? 1 : (r5 == r21 ? 0 : -1))
            if (r7 >= 0) goto L_0x00a2
            r7 = 1
            goto L_0x00a3
        L_0x00a2:
            r7 = 0
        L_0x00a3:
            if (r7 == 0) goto L_0x00ae
            r7 = 100000(0x186a0, double:4.94066E-319)
            int r9 = (r17 > r7 ? 1 : (r17 == r7 ? 0 : -1))
            if (r9 <= 0) goto L_0x00ae
            r7 = 1
            goto L_0x00af
        L_0x00ae:
            r7 = 0
        L_0x00af:
            if (r7 == 0) goto L_0x00b3
        L_0x00b1:
            r7 = 1
            goto L_0x00b4
        L_0x00b3:
            r7 = 0
        L_0x00b4:
            r8 = 21
            if (r7 == 0) goto L_0x00dc
            long r1 = java.lang.System.nanoTime()
            com.google.android.exoplayer2.video.VideoFrameMetadataListener r12 = r0.V
            if (r12 == 0) goto L_0x00cc
            android.media.MediaFormat r7 = r0.ao
            r9 = 1
            r15 = r1
            r17 = r39
            r18 = r7
            r12.onVideoFrameAboutToBeRendered(r13, r15, r17, r18)
            goto L_0x00cd
        L_0x00cc:
            r9 = 1
        L_0x00cd:
            int r7 = com.google.android.exoplayer2.util.Util.a
            if (r7 < r8) goto L_0x00d5
            r0.bi(r3, r4, r1)
            goto L_0x00d8
        L_0x00d5:
            r0.bh(r3, r4)
        L_0x00d8:
            r0.bm(r5)
            return r9
        L_0x00dc:
            r9 = 1
            if (r37 == 0) goto L_0x01da
            long r11 = r0.D
            int r7 = (r1 > r11 ? 1 : (r1 == r11 ? 0 : -1))
            if (r7 != 0) goto L_0x00e7
            goto L_0x01da
        L_0x00e7:
            long r11 = java.lang.System.nanoTime()
            long r5 = r5 * r19
            long r5 = r5 + r11
            r7 = r16
            long r5 = r7.adjustReleaseTime(r5)
            long r11 = r5 - r11
            long r11 = r11 / r19
            long r8 = r0.E
            r15 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            int r7 = (r8 > r15 ? 1 : (r8 == r15 ? 0 : -1))
            if (r7 == 0) goto L_0x0105
            r15 = 1
            goto L_0x0106
        L_0x0105:
            r15 = 0
        L_0x0106:
            r7 = -500000(0xfffffffffff85ee0, double:NaN)
            int r9 = (r11 > r7 ? 1 : (r11 == r7 ? 0 : -1))
            if (r9 >= 0) goto L_0x010f
            r7 = 1
            goto L_0x0110
        L_0x010f:
            r7 = 0
        L_0x0110:
            if (r7 == 0) goto L_0x0116
            if (r38 != 0) goto L_0x0116
            r7 = 1
            goto L_0x0117
        L_0x0116:
            r7 = 0
        L_0x0117:
            if (r7 == 0) goto L_0x0150
            com.google.android.exoplayer2.source.SampleStream r7 = r0.j
            java.lang.Object r7 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r7)
            com.google.android.exoplayer2.source.SampleStream r7 = (com.google.android.exoplayer2.source.SampleStream) r7
            long r8 = r0.l
            long r1 = r1 - r8
            int r1 = r7.skipData(r1)
            if (r1 != 0) goto L_0x012c
            r1 = 0
            goto L_0x014c
        L_0x012c:
            com.google.android.exoplayer2.decoder.DecoderCounters r2 = r0.ci
            int r7 = r2.f
            r8 = 1
            int r7 = r7 + r8
            r2.f = r7
            int r7 = r0.I
            int r7 = r7 + r1
            if (r15 == 0) goto L_0x013f
            int r1 = r2.c
            int r1 = r1 + r7
            r2.c = r1
            goto L_0x0142
        L_0x013f:
            r0.bl(r7)
        L_0x0142:
            boolean r1 = r25.s()
            if (r1 == 0) goto L_0x014b
            r25.ab()
        L_0x014b:
            r1 = 1
        L_0x014c:
            if (r1 == 0) goto L_0x0150
            r1 = 0
            return r1
        L_0x0150:
            int r1 = (r11 > r21 ? 1 : (r11 == r21 ? 0 : -1))
            if (r1 >= 0) goto L_0x0156
            r1 = 1
            goto L_0x0157
        L_0x0156:
            r1 = 0
        L_0x0157:
            if (r1 == 0) goto L_0x015d
            if (r38 != 0) goto L_0x015d
            r1 = 1
            goto L_0x015e
        L_0x015d:
            r1 = 0
        L_0x015e:
            if (r1 == 0) goto L_0x017b
            if (r15 == 0) goto L_0x0167
            r0.bk(r3, r4)
            r1 = 1
            goto L_0x0177
        L_0x0167:
            java.lang.String r1 = "dropVideoBuffer"
            com.google.android.exoplayer2.util.TraceUtil.beginSection(r1)
            r1 = 0
            r3.releaseOutputBuffer((int) r4, (boolean) r1)
            com.google.android.exoplayer2.util.TraceUtil.endSection()
            r1 = 1
            r0.bl(r1)
        L_0x0177:
            r0.bm(r11)
            return r1
        L_0x017b:
            int r1 = com.google.android.exoplayer2.util.Util.a
            r2 = 21
            if (r1 < r2) goto L_0x01a2
            r1 = 50000(0xc350, double:2.47033E-319)
            int r7 = (r11 > r1 ? 1 : (r11 == r1 ? 0 : -1))
            if (r7 >= 0) goto L_0x01d8
            com.google.android.exoplayer2.video.VideoFrameMetadataListener r1 = r0.V
            if (r1 == 0) goto L_0x0199
            android.media.MediaFormat r2 = r0.ao
            r7 = r11
            r12 = r1
            r15 = r5
            r17 = r39
            r18 = r2
            r12.onVideoFrameAboutToBeRendered(r13, r15, r17, r18)
            goto L_0x019a
        L_0x0199:
            r7 = r11
        L_0x019a:
            r0.bi(r3, r4, r5)
            r0.bm(r7)
            r1 = 1
            return r1
        L_0x01a2:
            r7 = r11
            r1 = 30000(0x7530, double:1.4822E-319)
            int r9 = (r7 > r1 ? 1 : (r7 == r1 ? 0 : -1))
            if (r9 >= 0) goto L_0x01d8
            r1 = 11000(0x2af8, double:5.4347E-320)
            int r9 = (r7 > r1 ? 1 : (r7 == r1 ? 0 : -1))
            if (r9 <= 0) goto L_0x01c2
            r1 = 10000(0x2710, double:4.9407E-320)
            long r11 = r7 - r1
            long r11 = r11 / r19
            java.lang.Thread.sleep(r11)     // Catch:{ InterruptedException -> 0x01b9 }
            goto L_0x01c2
        L_0x01b9:
            java.lang.Thread r1 = java.lang.Thread.currentThread()
            r1.interrupt()
            r1 = 0
            return r1
        L_0x01c2:
            com.google.android.exoplayer2.video.VideoFrameMetadataListener r12 = r0.V
            if (r12 == 0) goto L_0x01d0
            android.media.MediaFormat r1 = r0.ao
            r15 = r5
            r17 = r39
            r18 = r1
            r12.onVideoFrameAboutToBeRendered(r13, r15, r17, r18)
        L_0x01d0:
            r0.bh(r3, r4)
            r0.bm(r7)
            r1 = 1
            return r1
        L_0x01d8:
            r1 = 0
            return r1
        L_0x01da:
            r1 = 0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.video.MediaCodecVideoRenderer.am(long, long, com.google.android.exoplayer2.mediacodec.MediaCodecAdapter, java.nio.ByteBuffer, int, int, int, long, boolean, boolean, com.google.android.exoplayer2.Format):boolean");
    }

    @CallSuper
    public final void aq() {
        super.aq();
        this.I = 0;
    }

    public final boolean at(MediaCodecInfo mediaCodecInfo) {
        return this.cw != null || bj(mediaCodecInfo);
    }

    public final int av(MediaCodecSelector mediaCodecSelector, Format format) throws MediaCodecUtil.DecoderQueryException {
        boolean z;
        boolean z2;
        int i;
        int i2;
        int i3 = 0;
        if (!MimeTypes.isVideo(format.p)) {
            return ha.a(0);
        }
        if (format.s != null) {
            z = true;
        } else {
            z = false;
        }
        List<MediaCodecInfo> bd = bd(mediaCodecSelector, format, z, false);
        if (z && bd.isEmpty()) {
            bd = bd(mediaCodecSelector, format, false, false);
        }
        if (bd.isEmpty()) {
            return ha.a(1);
        }
        Class<? extends ExoMediaCrypto> cls = format.ai;
        if (cls == null || FrameworkMediaCrypto.class.equals(cls)) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (!z2) {
            return ha.a(2);
        }
        MediaCodecInfo mediaCodecInfo = bd.get(0);
        boolean isFormatSupported = mediaCodecInfo.isFormatSupported(format);
        if (mediaCodecInfo.isSeamlessAdaptationSupported(format)) {
            i = 16;
        } else {
            i = 8;
        }
        if (isFormatSupported) {
            List<MediaCodecInfo> bd2 = bd(mediaCodecSelector, format, z, true);
            if (!bd2.isEmpty()) {
                MediaCodecInfo mediaCodecInfo2 = bd2.get(0);
                if (mediaCodecInfo2.isFormatSupported(format) && mediaCodecInfo2.isSeamlessAdaptationSupported(format)) {
                    i3 = 32;
                }
            }
        }
        if (isFormatSupported) {
            i2 = 4;
        } else {
            i2 = 3;
        }
        return ha.b(i2, i, i3);
    }

    public final void az() {
        MediaCodecAdapter mediaCodecAdapter;
        this.A = false;
        if (Util.a >= 23 && this.S && (mediaCodecAdapter = this.am) != null) {
            this.U = new a(mediaCodecAdapter);
        }
    }

    public final void bf() {
        this.C = true;
        if (!this.A) {
            this.A = true;
            this.cp.renderedFirstFrame(this.cw);
            this.cy = true;
        }
    }

    public final void bg() {
        int i = this.N;
        if (i != -1 || this.O != -1) {
            VideoSize videoSize = this.R;
            if (videoSize == null || videoSize.c != i || videoSize.f != this.O || videoSize.g != this.P || videoSize.h != this.Q) {
                VideoSize videoSize2 = new VideoSize(this.N, this.O, this.P, this.Q);
                this.R = videoSize2;
                this.cp.videoSizeChanged(videoSize2);
            }
        }
    }

    public final void bh(MediaCodecAdapter mediaCodecAdapter, int i) {
        bg();
        TraceUtil.beginSection("releaseOutputBuffer");
        mediaCodecAdapter.releaseOutputBuffer(i, true);
        TraceUtil.endSection();
        this.K = SystemClock.elapsedRealtime() * 1000;
        this.ci.b++;
        this.H = 0;
        bf();
    }

    @RequiresApi(21)
    public final void bi(MediaCodecAdapter mediaCodecAdapter, int i, long j) {
        bg();
        TraceUtil.beginSection("releaseOutputBuffer");
        mediaCodecAdapter.releaseOutputBuffer(i, j);
        TraceUtil.endSection();
        this.K = SystemClock.elapsedRealtime() * 1000;
        this.ci.b++;
        this.H = 0;
        bf();
    }

    public final boolean bj(MediaCodecInfo mediaCodecInfo) {
        if (Util.a < 23 || this.S || ba(mediaCodecInfo.a) || (mediaCodecInfo.f && !DummySurface.isSecureSupported(this.cn))) {
            return false;
        }
        return true;
    }

    public final void bk(MediaCodecAdapter mediaCodecAdapter, int i) {
        TraceUtil.beginSection("skipVideoBuffer");
        mediaCodecAdapter.releaseOutputBuffer(i, false);
        TraceUtil.endSection();
        this.ci.c++;
    }

    public final void bl(int i) {
        int i2;
        DecoderCounters decoderCounters = this.ci;
        decoderCounters.d += i;
        this.G += i;
        int i3 = this.H + i;
        this.H = i3;
        decoderCounters.e = Math.max(i3, decoderCounters.e);
        int i4 = this.cr;
        if (i4 > 0 && (i2 = this.G) >= i4 && i2 > 0) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            this.cp.droppedFrames(this.G, elapsedRealtime - this.F);
            this.G = 0;
            this.F = elapsedRealtime;
        }
    }

    public final void bm(long j) {
        this.ci.addVideoFrameProcessingOffset(j);
        this.L += j;
        this.M++;
    }

    public final void c() {
        VideoRendererEventListener.EventDispatcher eventDispatcher = this.cp;
        this.R = null;
        az();
        this.cy = false;
        this.co.onDisabled();
        this.U = null;
        try {
            super.c();
        } finally {
            eventDispatcher.disabled(this.ci);
        }
    }

    public final void d(boolean z, boolean z2) throws ExoPlaybackException {
        boolean z3;
        super.d(z, z2);
        boolean z4 = ((RendererConfiguration) Assertions.checkNotNull(this.g)).a;
        if (!z4 || this.T != 0) {
            z3 = true;
        } else {
            z3 = false;
        }
        Assertions.checkState(z3);
        if (this.S != z4) {
            this.S = z4;
            ao();
        }
        this.cp.enabled(this.ci);
        this.co.onEnabled();
        this.B = z2;
        this.C = false;
    }

    public final void e(long j, boolean z) throws ExoPlaybackException {
        super.e(j, z);
        az();
        this.co.onPositionReset();
        long j2 = -9223372036854775807L;
        this.J = -9223372036854775807L;
        this.D = -9223372036854775807L;
        this.H = 0;
        if (z) {
            long j3 = this.cq;
            if (j3 > 0) {
                j2 = SystemClock.elapsedRealtime() + j3;
            }
            this.E = j2;
            return;
        }
        this.E = -9223372036854775807L;
    }

    @TargetApi(17)
    public final void f() {
        try {
            super.f();
            DummySurface dummySurface = this.cx;
            if (dummySurface != null) {
                if (this.cw == dummySurface) {
                    this.cw = null;
                }
                dummySurface.release();
                this.cx = null;
            }
        } catch (Throwable th) {
            if (this.cx != null) {
                Surface surface = this.cw;
                DummySurface dummySurface2 = this.cx;
                if (surface == dummySurface2) {
                    this.cw = null;
                }
                dummySurface2.release();
                this.cx = null;
            }
            throw th;
        }
    }

    public final void g() {
        this.G = 0;
        this.F = SystemClock.elapsedRealtime();
        this.K = SystemClock.elapsedRealtime() * 1000;
        this.L = 0;
        this.M = 0;
        this.co.onStarted();
    }

    public String getName() {
        return "MediaCodecVideoRenderer";
    }

    public final void h() {
        this.E = -9223372036854775807L;
        int i = this.G;
        VideoRendererEventListener.EventDispatcher eventDispatcher = this.cp;
        if (i > 0) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            eventDispatcher.droppedFrames(this.G, elapsedRealtime - this.F);
            this.G = 0;
            this.F = elapsedRealtime;
        }
        int i2 = this.M;
        if (i2 != 0) {
            eventDispatcher.reportVideoFrameProcessingOffset(this.L, i2);
            this.L = 0;
            this.M = 0;
        }
        this.co.onStopped();
    }

    /* JADX WARNING: Failed to insert additional move for type inference */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handleMessage(int r6, @androidx.annotation.Nullable java.lang.Object r7) throws com.google.android.exoplayer2.ExoPlaybackException {
        /*
            r5 = this;
            r0 = 1
            if (r6 == r0) goto L_0x003e
            r0 = 4
            if (r6 == r0) goto L_0x002d
            r0 = 6
            if (r6 == r0) goto L_0x0027
            r0 = 102(0x66, float:1.43E-43)
            if (r6 == r0) goto L_0x0012
            super.handleMessage(r6, r7)
            goto L_0x00d6
        L_0x0012:
            java.lang.Integer r7 = (java.lang.Integer) r7
            int r6 = r7.intValue()
            int r7 = r5.T
            if (r7 == r6) goto L_0x00d6
            r5.T = r6
            boolean r6 = r5.S
            if (r6 == 0) goto L_0x00d6
            r5.ao()
            goto L_0x00d6
        L_0x0027:
            com.google.android.exoplayer2.video.VideoFrameMetadataListener r7 = (com.google.android.exoplayer2.video.VideoFrameMetadataListener) r7
            r5.V = r7
            goto L_0x00d6
        L_0x002d:
            java.lang.Integer r7 = (java.lang.Integer) r7
            int r6 = r7.intValue()
            r5.cz = r6
            com.google.android.exoplayer2.mediacodec.MediaCodecAdapter r7 = r5.am
            if (r7 == 0) goto L_0x00d6
            r7.setVideoScalingMode(r6)
            goto L_0x00d6
        L_0x003e:
            boolean r6 = r7 instanceof android.view.Surface
            r0 = 0
            if (r6 == 0) goto L_0x0046
            android.view.Surface r7 = (android.view.Surface) r7
            goto L_0x0047
        L_0x0046:
            r7 = r0
        L_0x0047:
            if (r7 != 0) goto L_0x0063
            com.google.android.exoplayer2.video.DummySurface r6 = r5.cx
            if (r6 == 0) goto L_0x004f
            r7 = r6
            goto L_0x0063
        L_0x004f:
            com.google.android.exoplayer2.mediacodec.MediaCodecInfo r6 = r5.at
            if (r6 == 0) goto L_0x0063
            boolean r1 = r5.bj(r6)
            if (r1 == 0) goto L_0x0063
            android.content.Context r7 = r5.cn
            boolean r6 = r6.f
            com.google.android.exoplayer2.video.DummySurface r7 = com.google.android.exoplayer2.video.DummySurface.newInstanceV17(r7, r6)
            r5.cx = r7
        L_0x0063:
            android.view.Surface r6 = r5.cw
            com.google.android.exoplayer2.video.VideoRendererEventListener$EventDispatcher r1 = r5.cp
            if (r6 == r7) goto L_0x00c0
            r5.cw = r7
            com.google.android.exoplayer2.video.VideoFrameReleaseHelper r6 = r5.co
            r6.onSurfaceChanged(r7)
            r6 = 0
            r5.cy = r6
            int r6 = r5.getState()
            com.google.android.exoplayer2.mediacodec.MediaCodecAdapter r2 = r5.am
            if (r2 == 0) goto L_0x0091
            int r3 = com.google.android.exoplayer2.util.Util.a
            r4 = 23
            if (r3 < r4) goto L_0x008b
            if (r7 == 0) goto L_0x008b
            boolean r3 = r5.cu
            if (r3 != 0) goto L_0x008b
            r2.setOutputSurface(r7)
            goto L_0x0091
        L_0x008b:
            r5.ao()
            r5.ab()
        L_0x0091:
            if (r7 == 0) goto L_0x00ba
            com.google.android.exoplayer2.video.DummySurface r2 = r5.cx
            if (r7 == r2) goto L_0x00ba
            com.google.android.exoplayer2.video.VideoSize r7 = r5.R
            if (r7 == 0) goto L_0x009e
            r1.videoSizeChanged(r7)
        L_0x009e:
            r5.az()
            r7 = 2
            if (r6 != r7) goto L_0x00d6
            r6 = 0
            long r0 = r5.cq
            int r2 = (r0 > r6 ? 1 : (r0 == r6 ? 0 : -1))
            if (r2 <= 0) goto L_0x00b2
            long r6 = android.os.SystemClock.elapsedRealtime()
            long r6 = r6 + r0
            goto L_0x00b7
        L_0x00b2:
            r6 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
        L_0x00b7:
            r5.E = r6
            goto L_0x00d6
        L_0x00ba:
            r5.R = r0
            r5.az()
            goto L_0x00d6
        L_0x00c0:
            if (r7 == 0) goto L_0x00d6
            com.google.android.exoplayer2.video.DummySurface r6 = r5.cx
            if (r7 == r6) goto L_0x00d6
            com.google.android.exoplayer2.video.VideoSize r6 = r5.R
            if (r6 == 0) goto L_0x00cd
            r1.videoSizeChanged(r6)
        L_0x00cd:
            boolean r6 = r5.cy
            if (r6 == 0) goto L_0x00d6
            android.view.Surface r6 = r5.cw
            r1.renderedFirstFrame(r6)
        L_0x00d6:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.video.MediaCodecVideoRenderer.handleMessage(int, java.lang.Object):void");
    }

    public boolean isReady() {
        DummySurface dummySurface;
        if (super.isReady() && (this.A || (((dummySurface = this.cx) != null && this.cw == dummySurface) || this.am == null || this.S))) {
            this.E = -9223372036854775807L;
            return true;
        } else if (this.E == -9223372036854775807L) {
            return false;
        } else {
            if (SystemClock.elapsedRealtime() < this.E) {
                return true;
            }
            this.E = -9223372036854775807L;
            return false;
        }
    }

    public final DecoderReuseEvaluation l(MediaCodecInfo mediaCodecInfo, Format format, Format format2) {
        int i;
        DecoderReuseEvaluation canReuseCodec = mediaCodecInfo.canReuseCodec(format, format2);
        int i2 = canReuseCodec.e;
        CodecMaxValues codecMaxValues = this.ct;
        if (format2.u > codecMaxValues.a || format2.v > codecMaxValues.b) {
            i2 |= 256;
        }
        if (be(format2, mediaCodecInfo) > this.ct.c) {
            i2 |= 64;
        }
        int i3 = i2;
        String str = mediaCodecInfo.a;
        if (i3 != 0) {
            i = 0;
        } else {
            i = canReuseCodec.d;
        }
        return new DecoderReuseEvaluation(str, format, format2, i, i3);
    }

    public final MediaCodecDecoderException m(IllegalStateException illegalStateException, @Nullable MediaCodecInfo mediaCodecInfo) {
        return new MediaCodecVideoDecoderException(illegalStateException, mediaCodecInfo, this.cw);
    }

    public void setPlaybackSpeed(float f, float f2) throws ExoPlaybackException {
        super.setPlaybackSpeed(f, f2);
        this.co.onPlaybackSpeed(f);
    }

    public final boolean u() {
        return this.S && Util.a < 23;
    }

    public final float v(float f, Format[] formatArr) {
        float f2 = -1.0f;
        for (Format format : formatArr) {
            float f3 = format.w;
            if (f3 != -1.0f) {
                f2 = Math.max(f2, f3);
            }
        }
        if (f2 == -1.0f) {
            return -1.0f;
        }
        return f2 * f;
    }

    public final List<MediaCodecInfo> w(MediaCodecSelector mediaCodecSelector, Format format, boolean z) throws MediaCodecUtil.DecoderQueryException {
        return bd(mediaCodecSelector, format, z, this.S);
    }

    @TargetApi(17)
    public final MediaCodecAdapter.Configuration y(MediaCodecInfo mediaCodecInfo, Format format, @Nullable MediaCrypto mediaCrypto, float f) {
        String str;
        int i;
        int i2;
        ColorInfo colorInfo;
        CodecMaxValues codecMaxValues;
        int i3;
        boolean z;
        Pair<Integer, Integer> codecProfileAndLevel;
        int i4;
        boolean z2;
        int i5;
        int i6;
        Point point;
        int i7;
        int i8;
        int i9;
        int i10;
        boolean z3;
        int bc;
        MediaCodecInfo mediaCodecInfo2 = mediaCodecInfo;
        Format format2 = format;
        float f2 = f;
        DummySurface dummySurface = this.cx;
        if (!(dummySurface == null || dummySurface.c == mediaCodecInfo2.f)) {
            dummySurface.release();
            this.cx = null;
        }
        String str2 = mediaCodecInfo2.c;
        Format[] formatArr = (Format[]) Assertions.checkNotNull(this.k);
        int i11 = format2.u;
        int be = be(format2, mediaCodecInfo2);
        int length = formatArr.length;
        float f3 = format2.w;
        ColorInfo colorInfo2 = format2.ab;
        int i12 = format2.v;
        String str3 = format2.p;
        int i13 = format2.u;
        if (length == 1) {
            if (!(be == -1 || (bc = bc(mediaCodecInfo2, str3, i13, i12)) == -1)) {
                be = Math.min((int) (((float) be) * 1.5f), bc);
            }
            codecMaxValues = new CodecMaxValues(i11, i12, be);
            str = str2;
            i = i13;
            colorInfo = colorInfo2;
            i2 = i12;
        } else {
            int length2 = formatArr.length;
            int i14 = i12;
            int i15 = 0;
            boolean z4 = false;
            while (i15 < length2) {
                Format format3 = formatArr[i15];
                Format[] formatArr2 = formatArr;
                if (colorInfo2 != null && format3.ab == null) {
                    format3 = format3.buildUpon().setColorInfo(colorInfo2).build();
                }
                if (mediaCodecInfo2.canReuseCodec(format2, format3).d != 0) {
                    int i16 = format3.u;
                    i10 = length2;
                    int i17 = format3.v;
                    if (i16 == -1 || i17 == -1) {
                        z3 = true;
                    } else {
                        z3 = false;
                    }
                    z4 |= z3;
                    int max = Math.max(i11, i16);
                    i14 = Math.max(i14, i17);
                    be = Math.max(be, be(format3, mediaCodecInfo2));
                    i11 = max;
                } else {
                    i10 = length2;
                    int i18 = i14;
                }
                i15++;
                float f4 = f;
                formatArr = formatArr2;
                length2 = i10;
            }
            int i19 = i14;
            if (z4) {
                StringBuilder sb = new StringBuilder(66);
                sb.append("Resolutions unknown. Codec max resolution: ");
                sb.append(i11);
                sb.append("x");
                sb.append(i19);
                Log.w("MediaCodecVideoRenderer", sb.toString());
                if (i12 > i13) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (z2) {
                    i5 = i12;
                } else {
                    i5 = i13;
                }
                colorInfo = colorInfo2;
                if (z2) {
                    i6 = i13;
                } else {
                    i6 = i12;
                }
                i2 = i12;
                float f5 = ((float) i6) / ((float) i5);
                int[] iArr = W;
                str = str2;
                i = i13;
                int i20 = 0;
                while (true) {
                    if (i20 >= 9) {
                        break;
                    }
                    int i21 = iArr[i20];
                    int[] iArr2 = iArr;
                    int i22 = (int) (((float) i21) * f5);
                    if (i21 <= i5 || i22 <= i6) {
                        break;
                    }
                    float f6 = f5;
                    int i23 = i5;
                    if (Util.a >= 21) {
                        if (z2) {
                            i9 = i22;
                        } else {
                            i9 = i21;
                        }
                        if (!z2) {
                            i21 = i22;
                        }
                        point = mediaCodecInfo2.alignVideoSizeV21(i9, i21);
                        i7 = i6;
                        if (mediaCodecInfo2.isVideoSizeAndRateSupportedV21(point.x, point.y, (double) f3)) {
                            break;
                        }
                    } else {
                        i7 = i6;
                        try {
                            int ceilDivide = Util.ceilDivide(i21, 16) * 16;
                            int ceilDivide2 = Util.ceilDivide(i22, 16) * 16;
                            if (ceilDivide * ceilDivide2 <= MediaCodecUtil.maxH264DecodableFrameSize()) {
                                if (z2) {
                                    i8 = ceilDivide2;
                                } else {
                                    i8 = ceilDivide;
                                }
                                if (!z2) {
                                    ceilDivide = ceilDivide2;
                                }
                                point = new Point(i8, ceilDivide);
                            }
                        } catch (MediaCodecUtil.DecoderQueryException unused) {
                        }
                    }
                    i20++;
                    iArr = iArr2;
                    f5 = f6;
                    i5 = i23;
                    i6 = i7;
                }
                point = null;
                if (point != null) {
                    i11 = Math.max(i11, point.x);
                    i4 = Math.max(i19, point.y);
                    be = Math.max(be, bc(mediaCodecInfo2, str3, i11, i4));
                    StringBuilder sb2 = new StringBuilder(57);
                    sb2.append("Codec max resolution adjusted to: ");
                    sb2.append(i11);
                    sb2.append("x");
                    sb2.append(i4);
                    Log.w("MediaCodecVideoRenderer", sb2.toString());
                    codecMaxValues = new CodecMaxValues(i11, i4, be);
                }
            } else {
                str = str2;
                i = i13;
                colorInfo = colorInfo2;
                i2 = i12;
            }
            i4 = i19;
            codecMaxValues = new CodecMaxValues(i11, i4, be);
        }
        this.ct = codecMaxValues;
        if (this.S) {
            i3 = this.T;
        } else {
            i3 = 0;
        }
        MediaFormat mediaFormat = new MediaFormat();
        mediaFormat.setString("mime", str);
        mediaFormat.setInteger("width", i);
        mediaFormat.setInteger("height", i2);
        Format format4 = format;
        MediaFormatUtil.setCsdBuffers(mediaFormat, format4.r);
        MediaFormatUtil.maybeSetFloat(mediaFormat, "frame-rate", f3);
        MediaFormatUtil.maybeSetInteger(mediaFormat, "rotation-degrees", format4.x);
        MediaFormatUtil.maybeSetColorInfo(mediaFormat, colorInfo);
        if ("video/dolby-vision".equals(str3) && (codecProfileAndLevel = MediaCodecUtil.getCodecProfileAndLevel(format)) != null) {
            MediaFormatUtil.maybeSetInteger(mediaFormat, "profile", ((Integer) codecProfileAndLevel.first).intValue());
        }
        mediaFormat.setInteger("max-width", codecMaxValues.a);
        mediaFormat.setInteger("max-height", codecMaxValues.b);
        MediaFormatUtil.maybeSetInteger(mediaFormat, "max-input-size", codecMaxValues.c);
        if (Util.a >= 23) {
            mediaFormat.setInteger("priority", 0);
            float f7 = f;
            if (f7 != -1.0f) {
                mediaFormat.setFloat("operating-rate", f7);
            }
        }
        if (this.cs) {
            z = true;
            mediaFormat.setInteger("no-post-process", 1);
            mediaFormat.setInteger("auto-frc", 0);
        } else {
            z = true;
        }
        if (i3 != 0) {
            mediaFormat.setFeatureEnabled("tunneled-playback", z);
            mediaFormat.setInteger("audio-session-id", i3);
        }
        if (this.cw == null) {
            if (bj(mediaCodecInfo)) {
                if (this.cx == null) {
                    this.cx = DummySurface.newInstanceV17(this.cn, mediaCodecInfo2.f);
                }
                this.cw = this.cx;
            } else {
                throw new IllegalStateException();
            }
        }
        return new MediaCodecAdapter.Configuration(mediaCodecInfo, mediaFormat, format, this.cw, mediaCrypto, 0);
    }

    @TargetApi(29)
    public final void z(DecoderInputBuffer decoderInputBuffer) throws ExoPlaybackException {
        if (this.cv) {
            ByteBuffer byteBuffer = (ByteBuffer) Assertions.checkNotNull(decoderInputBuffer.j);
            if (byteBuffer.remaining() >= 7) {
                byte b = byteBuffer.get();
                short s = byteBuffer.getShort();
                short s2 = byteBuffer.getShort();
                byte b2 = byteBuffer.get();
                byte b3 = byteBuffer.get();
                byteBuffer.position(0);
                if (b == -75 && s == 60 && s2 == 1 && b2 == 4 && b3 == 0) {
                    byte[] bArr = new byte[byteBuffer.remaining()];
                    byteBuffer.get(bArr);
                    byteBuffer.position(0);
                    MediaCodecAdapter mediaCodecAdapter = this.am;
                    Bundle bundle = new Bundle();
                    bundle.putByteArray("hdr10-plus-info", bArr);
                    mediaCodecAdapter.setParameters(bundle);
                }
            }
        }
    }

    public MediaCodecVideoRenderer(Context context, MediaCodecSelector mediaCodecSelector, long j) {
        this(context, mediaCodecSelector, j, (Handler) null, (VideoRendererEventListener) null, 0);
    }

    public MediaCodecVideoRenderer(Context context, MediaCodecSelector mediaCodecSelector, long j, @Nullable Handler handler, @Nullable VideoRendererEventListener videoRendererEventListener, int i) {
        this(context, MediaCodecAdapter.Factory.a, mediaCodecSelector, j, false, handler, videoRendererEventListener, i);
    }

    public MediaCodecVideoRenderer(Context context, MediaCodecSelector mediaCodecSelector, long j, boolean z, @Nullable Handler handler, @Nullable VideoRendererEventListener videoRendererEventListener, int i) {
        this(context, MediaCodecAdapter.Factory.a, mediaCodecSelector, j, z, handler, videoRendererEventListener, i);
    }

    public MediaCodecVideoRenderer(Context context, MediaCodecAdapter.Factory factory, MediaCodecSelector mediaCodecSelector, long j, boolean z, @Nullable Handler handler, @Nullable VideoRendererEventListener videoRendererEventListener, int i) {
        super(2, factory, mediaCodecSelector, z, 30.0f);
        this.cq = j;
        this.cr = i;
        Context applicationContext = context.getApplicationContext();
        this.cn = applicationContext;
        this.co = new VideoFrameReleaseHelper(applicationContext);
        this.cp = new VideoRendererEventListener.EventDispatcher(handler, videoRendererEventListener);
        this.cs = "NVIDIA".equals(Util.c);
        this.E = -9223372036854775807L;
        this.N = -1;
        this.O = -1;
        this.Q = -1.0f;
        this.cz = 1;
        this.T = 0;
        this.R = null;
    }
}
