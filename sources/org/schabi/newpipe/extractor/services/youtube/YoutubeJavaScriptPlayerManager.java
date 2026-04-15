package org.schabi.newpipe.extractor.services.youtube;

import j$.util.Objects;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.schabi.newpipe.extractor.NewPipe;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.localization.Localization;
import org.schabi.newpipe.extractor.utils.JavaScript;
import org.schabi.newpipe.extractor.utils.Parser;

public final class YoutubeJavaScriptPlayerManager {
    public static final HashMap a = new HashMap();
    public static String b;
    public static Integer c;
    public static String d;
    public static String e;
    public static String f;
    public static ParsingException g;
    public static ParsingException h;
    public static ParsingException i;

    public static void a(String str) throws ParsingException {
        String str2;
        String str3;
        if (b == null) {
            try {
                String a2 = dg.a(dg.b());
                new URL(a2);
                str2 = NewPipe.getDownloader().get(a2, Localization.g).responseBody();
            } catch (Exception e2) {
                throw new ParsingException("Could not get JavaScript base player's code", e2);
            } catch (Exception unused) {
                try {
                    String responseBody = NewPipe.getDownloader().get("https://www.youtube.com/embed/" + str, Localization.g).responseBody();
                    Iterator it = Jsoup.parse(responseBody).select("script").attr("name", "player/base").iterator();
                    while (true) {
                        if (it.hasNext()) {
                            str3 = ((Element) it.next()).attr("src");
                            if (str3.contains("base.js")) {
                                break;
                            }
                        } else {
                            try {
                                str3 = Parser.matchGroup1(dg.b, responseBody);
                                break;
                            } catch (Parser.RegexException e3) {
                                throw new ParsingException("Embedded watch page didn't provide JavaScript base player's URL", e3);
                            }
                        }
                    }
                    String a3 = dg.a(str3);
                    try {
                        new URL(a3);
                        try {
                            str2 = NewPipe.getDownloader().get(a3, Localization.g).responseBody();
                        } catch (Exception e4) {
                            throw new ParsingException("Could not get JavaScript base player's code", e4);
                        }
                    } catch (MalformedURLException e5) {
                        throw new ParsingException("The extracted and built JavaScript URL is invalid", e5);
                    }
                } catch (Exception e6) {
                    throw new ParsingException("Could not fetch embedded watch page", e6);
                }
            }
            b = str2;
        }
    }

    public static void clearAllCaches() {
        b = null;
        d = null;
        e = null;
        f = null;
        c = null;
        clearThrottlingParametersCache();
        g = null;
        h = null;
        i = null;
    }

    public static void clearThrottlingParametersCache() {
        a.clear();
    }

    public static String deobfuscateSignature(String str, String str2) throws ParsingException {
        ParsingException parsingException = h;
        if (parsingException == null) {
            a(str);
            if (d == null) {
                try {
                    d = kg.a(b);
                } catch (ParsingException e2) {
                    h = e2;
                    throw e2;
                } catch (Exception e3) {
                    h = new ParsingException("Could not get signature parameter deobfuscation JavaScript function", e3);
                    throw e3;
                }
            }
            try {
                return (String) Objects.requireNonNullElse(JavaScript.run(d, "deobfuscate", str2), "");
            } catch (Exception e4) {
                throw new ParsingException("Could not run signature parameter deobfuscation JavaScript function", e4);
            }
        } else {
            throw parsingException;
        }
    }

    public static Integer getSignatureTimestamp(String str) throws ParsingException {
        Integer num = c;
        if (num != null) {
            return num;
        }
        ParsingException parsingException = i;
        if (parsingException == null) {
            a(str);
            try {
                c = Integer.valueOf(kg.b(b));
            } catch (ParsingException e2) {
                i = e2;
                throw e2;
            } catch (NumberFormatException e3) {
                i = new ParsingException("Could not convert signature timestamp to a number", e3);
            } catch (Exception e4) {
                i = new ParsingException("Could not get signature timestamp", e4);
                throw e4;
            }
            return c;
        }
        throw parsingException;
    }

    public static int getThrottlingParametersCacheSize() {
        return a.size();
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x001e  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x001d A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getUrlWithThrottlingParameterDeobfuscated(java.lang.String r5, java.lang.String r6) throws org.schabi.newpipe.extractor.exceptions.ParsingException {
        /*
            java.util.regex.Pattern r0 = defpackage.rg.a
            java.lang.String r0 = "&n="
            boolean r0 = r6.contains(r0)
            if (r0 != 0) goto L_0x0013
            java.lang.String r0 = "?n="
            boolean r0 = r6.contains(r0)
            if (r0 != 0) goto L_0x0013
            goto L_0x001a
        L_0x0013:
            java.util.regex.Pattern r0 = defpackage.rg.a     // Catch:{ RegexException -> 0x001a }
            java.lang.String r0 = org.schabi.newpipe.extractor.utils.Parser.matchGroup1((java.util.regex.Pattern) r0, (java.lang.String) r6)     // Catch:{ RegexException -> 0x001a }
            goto L_0x001b
        L_0x001a:
            r0 = 0
        L_0x001b:
            if (r0 != 0) goto L_0x001e
            return r6
        L_0x001e:
            java.util.HashMap r1 = a
            java.lang.Object r2 = r1.get(r0)
            java.lang.String r2 = (java.lang.String) r2
            if (r2 == 0) goto L_0x002d
            java.lang.String r5 = r6.replace(r0, r2)
            return r5
        L_0x002d:
            a(r5)
            org.schabi.newpipe.extractor.exceptions.ParsingException r5 = g
            if (r5 != 0) goto L_0x0085
            java.lang.String r5 = f
            if (r5 != 0) goto L_0x0058
            java.lang.String r5 = b     // Catch:{ ParsingException -> 0x0054, Exception -> 0x0049 }
            java.lang.String r5 = defpackage.rg.b(r5)     // Catch:{ ParsingException -> 0x0054, Exception -> 0x0049 }
            e = r5     // Catch:{ ParsingException -> 0x0054, Exception -> 0x0049 }
            java.lang.String r2 = b     // Catch:{ ParsingException -> 0x0054, Exception -> 0x0049 }
            java.lang.String r5 = defpackage.rg.a(r2, r5)     // Catch:{ ParsingException -> 0x0054, Exception -> 0x0049 }
            f = r5     // Catch:{ ParsingException -> 0x0054, Exception -> 0x0049 }
            goto L_0x0058
        L_0x0049:
            r5 = move-exception
            org.schabi.newpipe.extractor.exceptions.ParsingException r6 = new org.schabi.newpipe.extractor.exceptions.ParsingException
            java.lang.String r0 = "Could not get throttling parameter deobfuscation JavaScript function"
            r6.<init>(r0, r5)
            g = r6
            throw r5
        L_0x0054:
            r5 = move-exception
            g = r5
            throw r5
        L_0x0058:
            java.lang.String r5 = f     // Catch:{ Exception -> 0x007c }
            java.lang.String r2 = e     // Catch:{ Exception -> 0x007c }
            r3 = 1
            java.lang.String[] r3 = new java.lang.String[r3]     // Catch:{ Exception -> 0x007c }
            r4 = 0
            r3[r4] = r0     // Catch:{ Exception -> 0x007c }
            java.lang.String r5 = org.schabi.newpipe.extractor.utils.JavaScript.run(r5, r2, r3)     // Catch:{ Exception -> 0x007c }
            boolean r2 = org.schabi.newpipe.extractor.utils.Utils.isNullOrEmpty((java.lang.String) r5)     // Catch:{ Exception -> 0x007c }
            if (r2 != 0) goto L_0x0074
            r1.put(r0, r5)     // Catch:{ Exception -> 0x007c }
            java.lang.String r5 = r6.replace(r0, r5)     // Catch:{ Exception -> 0x007c }
            return r5
        L_0x0074:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException     // Catch:{ Exception -> 0x007c }
            java.lang.String r6 = "Extracted n-parameter is empty"
            r5.<init>(r6)     // Catch:{ Exception -> 0x007c }
            throw r5     // Catch:{ Exception -> 0x007c }
        L_0x007c:
            r5 = move-exception
            org.schabi.newpipe.extractor.exceptions.ParsingException r6 = new org.schabi.newpipe.extractor.exceptions.ParsingException
            java.lang.String r0 = "Could not run throttling parameter deobfuscation JavaScript function"
            r6.<init>(r0, r5)
            throw r6
        L_0x0085:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: org.schabi.newpipe.extractor.services.youtube.YoutubeJavaScriptPlayerManager.getUrlWithThrottlingParameterDeobfuscated(java.lang.String, java.lang.String):java.lang.String");
    }
}
