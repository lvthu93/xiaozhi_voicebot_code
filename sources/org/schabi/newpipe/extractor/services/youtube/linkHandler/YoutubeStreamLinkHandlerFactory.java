package org.schabi.newpipe.extractor.services.youtube.linkHandler;

import j$.util.Objects;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import org.schabi.newpipe.extractor.exceptions.FoundAdException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.LinkHandlerFactory;

public final class YoutubeStreamLinkHandlerFactory extends LinkHandlerFactory {
    public static final Pattern a = Pattern.compile("^([a-zA-Z0-9_-]{11})");
    public static final YoutubeStreamLinkHandlerFactory b = new YoutubeStreamLinkHandlerFactory();
    public static final List<String> c;

    static {
        Object[] objArr = {"embed/", "live/", "shorts/", "watch/", "v/", "w/"};
        ArrayList arrayList = new ArrayList(6);
        for (int i = 0; i < 6; i++) {
            Object obj = objArr[i];
            Objects.requireNonNull(obj);
            arrayList.add(obj);
        }
        c = Collections.unmodifiableList(arrayList);
    }

    /* JADX WARNING: Removed duplicated region for block: B:6:0x0017 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:7:0x0018  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(java.lang.String r1) throws org.schabi.newpipe.extractor.exceptions.ParsingException {
        /*
            if (r1 == 0) goto L_0x0014
            java.util.regex.Pattern r0 = a
            java.util.regex.Matcher r1 = r0.matcher(r1)
            boolean r0 = r1.find()
            if (r0 == 0) goto L_0x0014
            r0 = 1
            java.lang.String r1 = r1.group(r0)
            goto L_0x0015
        L_0x0014:
            r1 = 0
        L_0x0015:
            if (r1 == 0) goto L_0x0018
            return r1
        L_0x0018:
            org.schabi.newpipe.extractor.exceptions.ParsingException r1 = new org.schabi.newpipe.extractor.exceptions.ParsingException
            java.lang.String r0 = "The given string is not a YouTube video ID"
            r1.<init>(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeStreamLinkHandlerFactory.a(java.lang.String):java.lang.String");
    }

    public static String b(String str) throws ParsingException {
        for (String next : c) {
            if (str.startsWith(next)) {
                return a(str.substring(next.length()));
            }
        }
        return null;
    }

    public static YoutubeStreamLinkHandlerFactory getInstance() {
        return b;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:145:0x027b, code lost:
        if (r3.equals("TUBUS.EDUVID.ORG") == false) goto L_0x0289;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:21:0x0052 */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0047 A[Catch:{ URISyntaxException -> 0x0052 }, RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0048 A[Catch:{ URISyntaxException -> 0x0052 }] */
    /* JADX WARNING: Removed duplicated region for block: B:196:0x0331  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0064  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x008e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getId(java.lang.String r9) throws org.schabi.newpipe.extractor.exceptions.ParsingException, java.lang.UnsupportedOperationException {
        /*
            r8 = this;
            java.lang.String r0 = "https:"
            java.lang.String r1 = "https://www.youtube.com"
            r2 = 1
            r3 = 2
            java.net.URI r4 = new java.net.URI     // Catch:{ URISyntaxException -> 0x0052 }
            r4.<init>(r9)     // Catch:{ URISyntaxException -> 0x0052 }
            java.lang.String r5 = r4.getScheme()     // Catch:{ URISyntaxException -> 0x0052 }
            if (r5 == 0) goto L_0x0052
            java.lang.String r6 = "vnd.youtube"
            boolean r6 = r5.equals(r6)     // Catch:{ URISyntaxException -> 0x0052 }
            if (r6 != 0) goto L_0x0021
            java.lang.String r6 = "vnd.youtube.launch"
            boolean r5 = r5.equals(r6)     // Catch:{ URISyntaxException -> 0x0052 }
            if (r5 == 0) goto L_0x0052
        L_0x0021:
            java.lang.String r4 = r4.getSchemeSpecificPart()     // Catch:{ URISyntaxException -> 0x0052 }
            java.lang.String r5 = "//"
            boolean r5 = r4.startsWith(r5)     // Catch:{ URISyntaxException -> 0x0052 }
            if (r5 == 0) goto L_0x004d
            java.lang.String r3 = r4.substring(r3)     // Catch:{ URISyntaxException -> 0x0052 }
            if (r3 == 0) goto L_0x0044
            java.util.regex.Pattern r5 = a     // Catch:{ URISyntaxException -> 0x0052 }
            java.util.regex.Matcher r3 = r5.matcher(r3)     // Catch:{ URISyntaxException -> 0x0052 }
            boolean r5 = r3.find()     // Catch:{ URISyntaxException -> 0x0052 }
            if (r5 == 0) goto L_0x0044
            java.lang.String r3 = r3.group(r2)     // Catch:{ URISyntaxException -> 0x0052 }
            goto L_0x0045
        L_0x0044:
            r3 = 0
        L_0x0045:
            if (r3 == 0) goto L_0x0048
            return r3
        L_0x0048:
            java.lang.String r9 = r0.concat(r4)     // Catch:{ URISyntaxException -> 0x0052 }
            goto L_0x0052
        L_0x004d:
            java.lang.String r9 = a(r4)     // Catch:{ URISyntaxException -> 0x0052 }
            return r9
        L_0x0052:
            java.net.URL r0 = org.schabi.newpipe.extractor.utils.Utils.stringToURL(r9)     // Catch:{ MalformedURLException -> 0x034d }
            java.lang.String r3 = r0.getHost()
            java.lang.String r4 = r0.getPath()
            boolean r5 = r4.isEmpty()
            if (r5 != 0) goto L_0x0068
            java.lang.String r4 = r4.substring(r2)
        L_0x0068:
            boolean r5 = org.schabi.newpipe.extractor.utils.Utils.isHTTP(r0)
            if (r5 == 0) goto L_0x0331
            boolean r5 = org.schabi.newpipe.extractor.services.youtube.YoutubeParsingHelper.isYoutubeURL(r0)
            if (r5 != 0) goto L_0x008e
            boolean r5 = org.schabi.newpipe.extractor.services.youtube.YoutubeParsingHelper.isYoutubeServiceURL(r0)
            if (r5 != 0) goto L_0x008e
            boolean r5 = org.schabi.newpipe.extractor.services.youtube.YoutubeParsingHelper.isHooktubeURL(r0)
            if (r5 != 0) goto L_0x008e
            boolean r5 = org.schabi.newpipe.extractor.services.youtube.YoutubeParsingHelper.isInvidiousURL(r0)
            if (r5 != 0) goto L_0x008e
            boolean r5 = org.schabi.newpipe.extractor.services.youtube.YoutubeParsingHelper.isY2ubeURL(r0)
            if (r5 != 0) goto L_0x008e
            goto L_0x0331
        L_0x008e:
            org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubePlaylistLinkHandlerFactory r5 = org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubePlaylistLinkHandlerFactory.getInstance()
            boolean r5 = r5.acceptUrl(r9)
            java.lang.String r6 = "Error: no suitable URL: "
            if (r5 != 0) goto L_0x0327
            java.lang.String r3 = r3.toUpperCase()
            r3.getClass()
            int r5 = r3.hashCode()
            r7 = 6
            switch(r5) {
                case -2114213841: goto L_0x027e;
                case -2087592960: goto L_0x0275;
                case -1698218251: goto L_0x026a;
                case -1693444139: goto L_0x025f;
                case -1604112842: goto L_0x0254;
                case -1416474788: goto L_0x0249;
                case -1146372515: goto L_0x023e;
                case -1141849304: goto L_0x0233;
                case -1092206675: goto L_0x0225;
                case -827318675: goto L_0x0217;
                case -397809965: goto L_0x0209;
                case -397058810: goto L_0x01fb;
                case -244508014: goto L_0x01ed;
                case -124567835: goto L_0x01df;
                case -52807283: goto L_0x01d1;
                case 73534791: goto L_0x01c3;
                case 103116914: goto L_0x01b5;
                case 103276081: goto L_0x01a7;
                case 169254170: goto L_0x0199;
                case 301798888: goto L_0x018b;
                case 360245068: goto L_0x017d;
                case 405074072: goto L_0x016f;
                case 941554175: goto L_0x0161;
                case 1061977963: goto L_0x0153;
                case 1229581114: goto L_0x0145;
                case 1607514856: goto L_0x0137;
                case 1649009762: goto L_0x0129;
                case 1720784822: goto L_0x011b;
                case 1739373659: goto L_0x010d;
                case 1818283559: goto L_0x00ff;
                case 1876693401: goto L_0x00f1;
                case 1999754024: goto L_0x00e3;
                case 2022964729: goto L_0x00d5;
                case 2024273937: goto L_0x00c7;
                case 2035582341: goto L_0x00b9;
                case 2075880438: goto L_0x00ab;
                default: goto L_0x00a9;
            }
        L_0x00a9:
            goto L_0x0289
        L_0x00ab:
            java.lang.String r2 = "YOUTUBE.COM"
            boolean r2 = r3.equals(r2)
            if (r2 != 0) goto L_0x00b5
            goto L_0x0289
        L_0x00b5:
            r2 = 35
            goto L_0x028a
        L_0x00b9:
            java.lang.String r2 = "INVIDIOUS.SITE"
            boolean r2 = r3.equals(r2)
            if (r2 != 0) goto L_0x00c3
            goto L_0x0289
        L_0x00c3:
            r2 = 34
            goto L_0x028a
        L_0x00c7:
            java.lang.String r2 = "WWW.INVIDIO.US"
            boolean r2 = r3.equals(r2)
            if (r2 != 0) goto L_0x00d1
            goto L_0x0289
        L_0x00d1:
            r2 = 33
            goto L_0x028a
        L_0x00d5:
            java.lang.String r2 = "WWW.YOUTUBE-NOCOOKIE.COM"
            boolean r2 = r3.equals(r2)
            if (r2 != 0) goto L_0x00df
            goto L_0x0289
        L_0x00df:
            r2 = 32
            goto L_0x028a
        L_0x00e3:
            java.lang.String r2 = "INVIDIOUS.SILKKY.CLOUD"
            boolean r2 = r3.equals(r2)
            if (r2 != 0) goto L_0x00ed
            goto L_0x0289
        L_0x00ed:
            r2 = 31
            goto L_0x028a
        L_0x00f1:
            java.lang.String r2 = "INVIDIOUS.BLAMEFRAN.NET"
            boolean r2 = r3.equals(r2)
            if (r2 != 0) goto L_0x00fb
            goto L_0x0289
        L_0x00fb:
            r2 = 30
            goto L_0x028a
        L_0x00ff:
            java.lang.String r2 = "INVIDIOUS.048596.XYZ"
            boolean r2 = r3.equals(r2)
            if (r2 != 0) goto L_0x0109
            goto L_0x0289
        L_0x0109:
            r2 = 29
            goto L_0x028a
        L_0x010d:
            java.lang.String r2 = "YTPRIVATE.COM"
            boolean r2 = r3.equals(r2)
            if (r2 != 0) goto L_0x0117
            goto L_0x0289
        L_0x0117:
            r2 = 28
            goto L_0x028a
        L_0x011b:
            java.lang.String r2 = "REDIRECT.INVIDIOUS.IO"
            boolean r2 = r3.equals(r2)
            if (r2 != 0) goto L_0x0125
            goto L_0x0289
        L_0x0125:
            r2 = 27
            goto L_0x028a
        L_0x0129:
            java.lang.String r2 = "INVIDIOUS.SNOPYTA.ORG"
            boolean r2 = r3.equals(r2)
            if (r2 != 0) goto L_0x0133
            goto L_0x0289
        L_0x0133:
            r2 = 26
            goto L_0x028a
        L_0x0137:
            java.lang.String r2 = "INVIDIOUS.FDN.FR"
            boolean r2 = r3.equals(r2)
            if (r2 != 0) goto L_0x0141
            goto L_0x0289
        L_0x0141:
            r2 = 25
            goto L_0x028a
        L_0x0145:
            java.lang.String r2 = "INVIDIO.US"
            boolean r2 = r3.equals(r2)
            if (r2 != 0) goto L_0x014f
            goto L_0x0289
        L_0x014f:
            r2 = 24
            goto L_0x028a
        L_0x0153:
            java.lang.String r2 = "TUBE.CONNECT.CAFE"
            boolean r2 = r3.equals(r2)
            if (r2 != 0) goto L_0x015d
            goto L_0x0289
        L_0x015d:
            r2 = 23
            goto L_0x028a
        L_0x0161:
            java.lang.String r2 = "WWW.YOUTUBE.COM"
            boolean r2 = r3.equals(r2)
            if (r2 != 0) goto L_0x016b
            goto L_0x0289
        L_0x016b:
            r2 = 22
            goto L_0x028a
        L_0x016f:
            java.lang.String r2 = "INVIDIOUS.EXONIP.DE"
            boolean r2 = r3.equals(r2)
            if (r2 != 0) goto L_0x0179
            goto L_0x0289
        L_0x0179:
            r2 = 21
            goto L_0x028a
        L_0x017d:
            java.lang.String r2 = "Y.COM.CM"
            boolean r2 = r3.equals(r2)
            if (r2 != 0) goto L_0x0187
            goto L_0x0289
        L_0x0187:
            r2 = 20
            goto L_0x028a
        L_0x018b:
            java.lang.String r2 = "INVIDIOUS.MOOMOO.ME"
            boolean r2 = r3.equals(r2)
            if (r2 != 0) goto L_0x0195
            goto L_0x0289
        L_0x0195:
            r2 = 19
            goto L_0x028a
        L_0x0199:
            java.lang.String r2 = "HOOKTUBE.COM"
            boolean r2 = r3.equals(r2)
            if (r2 != 0) goto L_0x01a3
            goto L_0x0289
        L_0x01a3:
            r2 = 18
            goto L_0x028a
        L_0x01a7:
            java.lang.String r2 = "YOUTU.BE"
            boolean r2 = r3.equals(r2)
            if (r2 != 0) goto L_0x01b1
            goto L_0x0289
        L_0x01b1:
            r2 = 17
            goto L_0x028a
        L_0x01b5:
            java.lang.String r2 = "INVIDIOUS-US.KAVIN.ROCKS"
            boolean r2 = r3.equals(r2)
            if (r2 != 0) goto L_0x01bf
            goto L_0x0289
        L_0x01bf:
            r2 = 16
            goto L_0x028a
        L_0x01c3:
            java.lang.String r2 = "INVIDIOUS.KAVIN.ROCKS"
            boolean r2 = r3.equals(r2)
            if (r2 != 0) goto L_0x01cd
            goto L_0x0289
        L_0x01cd:
            r2 = 15
            goto L_0x028a
        L_0x01d1:
            java.lang.String r2 = "MUSIC.YOUTUBE.COM"
            boolean r2 = r3.equals(r2)
            if (r2 != 0) goto L_0x01db
            goto L_0x0289
        L_0x01db:
            r2 = 14
            goto L_0x028a
        L_0x01df:
            java.lang.String r2 = "YEWTU.BE"
            boolean r2 = r3.equals(r2)
            if (r2 != 0) goto L_0x01e9
            goto L_0x0289
        L_0x01e9:
            r2 = 13
            goto L_0x028a
        L_0x01ed:
            java.lang.String r2 = "INVIDIOU.SITE"
            boolean r2 = r3.equals(r2)
            if (r2 != 0) goto L_0x01f7
            goto L_0x0289
        L_0x01f7:
            r2 = 12
            goto L_0x028a
        L_0x01fb:
            java.lang.String r2 = "INV.RIVERSIDE.ROCKS"
            boolean r2 = r3.equals(r2)
            if (r2 != 0) goto L_0x0205
            goto L_0x0289
        L_0x0205:
            r2 = 11
            goto L_0x028a
        L_0x0209:
            java.lang.String r2 = "DEV.INVIDIO.US"
            boolean r2 = r3.equals(r2)
            if (r2 != 0) goto L_0x0213
            goto L_0x0289
        L_0x0213:
            r2 = 10
            goto L_0x028a
        L_0x0217:
            java.lang.String r2 = "INVIDIOUS.NAMAZSO.EU"
            boolean r2 = r3.equals(r2)
            if (r2 != 0) goto L_0x0221
            goto L_0x0289
        L_0x0221:
            r2 = 9
            goto L_0x028a
        L_0x0225:
            java.lang.String r2 = "PIPED.KAVIN.ROCKS"
            boolean r2 = r3.equals(r2)
            if (r2 != 0) goto L_0x022f
            goto L_0x0289
        L_0x022f:
            r2 = 8
            goto L_0x028a
        L_0x0233:
            java.lang.String r2 = "VID.PUFFYAN.US"
            boolean r2 = r3.equals(r2)
            if (r2 != 0) goto L_0x023c
            goto L_0x0289
        L_0x023c:
            r2 = 7
            goto L_0x028a
        L_0x023e:
            java.lang.String r2 = "YTB.TROM.TF"
            boolean r2 = r3.equals(r2)
            if (r2 != 0) goto L_0x0247
            goto L_0x0289
        L_0x0247:
            r2 = 6
            goto L_0x028a
        L_0x0249:
            java.lang.String r2 = "VID.MINT.LGBT"
            boolean r2 = r3.equals(r2)
            if (r2 != 0) goto L_0x0252
            goto L_0x0289
        L_0x0252:
            r2 = 5
            goto L_0x028a
        L_0x0254:
            java.lang.String r2 = "YT.CYBERHOST.UK"
            boolean r2 = r3.equals(r2)
            if (r2 != 0) goto L_0x025d
            goto L_0x0289
        L_0x025d:
            r2 = 4
            goto L_0x028a
        L_0x025f:
            java.lang.String r2 = "M.YOUTUBE.COM"
            boolean r2 = r3.equals(r2)
            if (r2 != 0) goto L_0x0268
            goto L_0x0289
        L_0x0268:
            r2 = 3
            goto L_0x028a
        L_0x026a:
            java.lang.String r2 = "Y2U.BE"
            boolean r2 = r3.equals(r2)
            if (r2 != 0) goto L_0x0273
            goto L_0x0289
        L_0x0273:
            r2 = 2
            goto L_0x028a
        L_0x0275:
            java.lang.String r5 = "TUBUS.EDUVID.ORG"
            boolean r3 = r3.equals(r5)
            if (r3 != 0) goto L_0x028a
            goto L_0x0289
        L_0x027e:
            java.lang.String r2 = "INVIDIOUS.ZEE.LI"
            boolean r2 = r3.equals(r2)
            if (r2 != 0) goto L_0x0287
            goto L_0x0289
        L_0x0287:
            r2 = 0
            goto L_0x028a
        L_0x0289:
            r2 = -1
        L_0x028a:
            java.lang.String r3 = "v"
            switch(r2) {
                case 0: goto L_0x02f3;
                case 1: goto L_0x02f3;
                case 2: goto L_0x02e3;
                case 3: goto L_0x02a2;
                case 4: goto L_0x02f3;
                case 5: goto L_0x02f3;
                case 6: goto L_0x02f3;
                case 7: goto L_0x02f3;
                case 8: goto L_0x02f3;
                case 9: goto L_0x02f3;
                case 10: goto L_0x02f3;
                case 11: goto L_0x02f3;
                case 12: goto L_0x02f3;
                case 13: goto L_0x02f3;
                case 14: goto L_0x02a2;
                case 15: goto L_0x02f3;
                case 16: goto L_0x02f3;
                case 17: goto L_0x02e3;
                case 18: goto L_0x02f3;
                case 19: goto L_0x02f3;
                case 20: goto L_0x02f3;
                case 21: goto L_0x02f3;
                case 22: goto L_0x02a2;
                case 23: goto L_0x02f3;
                case 24: goto L_0x02f3;
                case 25: goto L_0x02f3;
                case 26: goto L_0x02f3;
                case 27: goto L_0x02f3;
                case 28: goto L_0x02f3;
                case 29: goto L_0x02f3;
                case 30: goto L_0x02f3;
                case 31: goto L_0x02f3;
                case 32: goto L_0x0291;
                case 33: goto L_0x02f3;
                case 34: goto L_0x02f3;
                case 35: goto L_0x02a2;
                default: goto L_0x028f;
            }
        L_0x028f:
            goto L_0x031d
        L_0x0291:
            java.lang.String r0 = "embed/"
            boolean r0 = r4.startsWith(r0)
            if (r0 == 0) goto L_0x031d
            java.lang.String r9 = r4.substring(r7)
            java.lang.String r9 = a(r9)
            return r9
        L_0x02a2:
            java.lang.String r2 = "attribution_link"
            boolean r2 = r4.equals(r2)
            if (r2 == 0) goto L_0x02d3
            java.lang.String r2 = "u"
            java.lang.String r0 = org.schabi.newpipe.extractor.utils.Utils.getQueryValue(r0, r2)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ MalformedURLException -> 0x02c9 }
            r2.<init>(r1)     // Catch:{ MalformedURLException -> 0x02c9 }
            r2.append(r0)     // Catch:{ MalformedURLException -> 0x02c9 }
            java.lang.String r0 = r2.toString()     // Catch:{ MalformedURLException -> 0x02c9 }
            java.net.URL r9 = org.schabi.newpipe.extractor.utils.Utils.stringToURL(r0)     // Catch:{ MalformedURLException -> 0x02c9 }
            java.lang.String r9 = org.schabi.newpipe.extractor.utils.Utils.getQueryValue(r9, r3)
            java.lang.String r9 = a(r9)
            return r9
        L_0x02c9:
            org.schabi.newpipe.extractor.exceptions.ParsingException r0 = new org.schabi.newpipe.extractor.exceptions.ParsingException
            java.lang.String r9 = defpackage.y2.i(r6, r9)
            r0.<init>(r9)
            throw r0
        L_0x02d3:
            java.lang.String r9 = b(r4)
            if (r9 == 0) goto L_0x02da
            return r9
        L_0x02da:
            java.lang.String r9 = org.schabi.newpipe.extractor.utils.Utils.getQueryValue(r0, r3)
            java.lang.String r9 = a(r9)
            return r9
        L_0x02e3:
            java.lang.String r9 = org.schabi.newpipe.extractor.utils.Utils.getQueryValue(r0, r3)
            if (r9 == 0) goto L_0x02ee
            java.lang.String r9 = a(r9)
            return r9
        L_0x02ee:
            java.lang.String r9 = a(r4)
            return r9
        L_0x02f3:
            java.lang.String r9 = "watch"
            boolean r9 = r4.equals(r9)
            if (r9 == 0) goto L_0x0306
            java.lang.String r9 = org.schabi.newpipe.extractor.utils.Utils.getQueryValue(r0, r3)
            if (r9 == 0) goto L_0x0306
            java.lang.String r9 = a(r9)
            return r9
        L_0x0306:
            java.lang.String r9 = b(r4)
            if (r9 == 0) goto L_0x030d
            return r9
        L_0x030d:
            java.lang.String r9 = org.schabi.newpipe.extractor.utils.Utils.getQueryValue(r0, r3)
            if (r9 == 0) goto L_0x0318
            java.lang.String r9 = a(r9)
            return r9
        L_0x0318:
            java.lang.String r9 = a(r4)
            return r9
        L_0x031d:
            org.schabi.newpipe.extractor.exceptions.ParsingException r0 = new org.schabi.newpipe.extractor.exceptions.ParsingException
            java.lang.String r9 = defpackage.y2.i(r6, r9)
            r0.<init>(r9)
            throw r0
        L_0x0327:
            org.schabi.newpipe.extractor.exceptions.ParsingException r0 = new org.schabi.newpipe.extractor.exceptions.ParsingException
            java.lang.String r9 = defpackage.y2.i(r6, r9)
            r0.<init>(r9)
            throw r0
        L_0x0331:
            java.lang.String r0 = "googleads.g.doubleclick.net"
            boolean r0 = r3.equalsIgnoreCase(r0)
            if (r0 == 0) goto L_0x0345
            org.schabi.newpipe.extractor.exceptions.FoundAdException r0 = new org.schabi.newpipe.extractor.exceptions.FoundAdException
            java.lang.String r1 = "Error: found ad: "
            java.lang.String r9 = defpackage.y2.i(r1, r9)
            r0.<init>(r9)
            throw r0
        L_0x0345:
            org.schabi.newpipe.extractor.exceptions.ParsingException r9 = new org.schabi.newpipe.extractor.exceptions.ParsingException
            java.lang.String r0 = "The URL is not a YouTube URL"
            r9.<init>(r0)
            throw r9
        L_0x034d:
            r9 = move-exception
            org.schabi.newpipe.extractor.exceptions.ParsingException r0 = new org.schabi.newpipe.extractor.exceptions.ParsingException
            java.lang.String r1 = "The given URL is not valid"
            r0.<init>(r1, r9)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeStreamLinkHandlerFactory.getId(java.lang.String):java.lang.String");
    }

    public String getUrl(String str) throws ParsingException, UnsupportedOperationException {
        return y2.i("https://www.youtube.com/watch?v=", str);
    }

    public boolean onAcceptUrl(String str) throws FoundAdException {
        try {
            getId(str);
            return true;
        } catch (FoundAdException e) {
            throw e;
        } catch (ParsingException unused) {
            return false;
        }
    }
}
