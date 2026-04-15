package org.schabi.newpipe.extractor.utils;

import j$.net.URLDecoder;
import j$.net.URLEncoder;
import j$.util.Collection$EL;
import j$.util.DesugarArrays;
import j$.util.stream.Collectors;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.utils.Parser;

public final class Utils {
    public static final Pattern a = Pattern.compile("(https?)?://m\\.");
    public static final Pattern b = Pattern.compile("(https?)?://www\\.");

    public static void checkUrl(String str, String str2) throws ParsingException {
        checkUrl(Pattern.compile(str), str2);
    }

    public static String decodeUrlUtf8(String str) {
        try {
            return URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String encodeUrlUtf8(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String followGoogleRedirectIfNeeded(String str) {
        try {
            URL stringToURL = stringToURL(str);
            if (!stringToURL.getHost().contains("google") || !stringToURL.getPath().equals("/url")) {
                return str;
            }
            return decodeUrlUtf8(Parser.matchGroup1("&url=([^&]+)(?:&|$)", str));
        } catch (Exception unused) {
            return str;
        }
    }

    public static String getBaseUrl(String str) throws ParsingException {
        try {
            URL stringToURL = stringToURL(str);
            return stringToURL.getProtocol() + "://" + stringToURL.getAuthority();
        } catch (MalformedURLException e) {
            String message = e.getMessage();
            if (message.startsWith("unknown protocol: ")) {
                return message.substring(18);
            }
            throw new ParsingException(y2.i("Malformed url: ", str), e);
        }
    }

    public static String getQueryValue(URL url, String str) {
        String query = url.getQuery();
        if (query == null) {
            return null;
        }
        for (String split : query.split("&")) {
            String[] split2 = split.split("=", 2);
            if (decodeUrlUtf8(split2[0]).equals(str)) {
                return decodeUrlUtf8(split2[1]);
            }
        }
        return null;
    }

    public static String getStringResultFromRegexArray(String str, String[] strArr) throws Parser.RegexException {
        return getStringResultFromRegexArray(str, strArr, 0);
    }

    public static boolean isBlank(String str) {
        boolean z;
        if (str == null) {
            return true;
        }
        int length = str.length();
        int i = 0;
        while (true) {
            if (i >= length) {
                z = true;
                break;
            }
            int codePointAt = str.codePointAt(i);
            if (!Character.isWhitespace(codePointAt)) {
                z = false;
                break;
            }
            i += Character.charCount(codePointAt);
        }
        return z;
    }

    public static boolean isHTTP(URL url) {
        boolean z;
        boolean z2;
        String protocol = url.getProtocol();
        if (!protocol.equals("http") && !protocol.equals("https")) {
            return false;
        }
        if (url.getPort() == url.getDefaultPort()) {
            z = true;
        } else {
            z = false;
        }
        if (url.getPort() == -1) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2 || z) {
            return true;
        }
        return false;
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static String join(String str, String str2, Map<? extends CharSequence, ? extends CharSequence> map) {
        return (String) Collection$EL.stream(map.entrySet()).map(new cc(str2, 7)).collect(Collectors.joining(str));
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0033, code lost:
        if (r5.equals("M") == false) goto L_0x002b;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static long mixedNumberWordToLong(java.lang.String r5) throws java.lang.NumberFormatException, org.schabi.newpipe.extractor.exceptions.ParsingException {
        /*
            r0 = 2
            java.lang.String r1 = "[\\d]+([\\.,][\\d]+)?([KMBkmb])+"
            java.lang.String r1 = org.schabi.newpipe.extractor.utils.Parser.matchGroup((java.lang.String) r1, (java.lang.String) r5, (int) r0)     // Catch:{ ParsingException -> 0x0008 }
            goto L_0x000a
        L_0x0008:
            java.lang.String r1 = ""
        L_0x000a:
            java.lang.String r2 = "([\\d]+([\\.,][\\d]+)?)"
            java.lang.String r5 = org.schabi.newpipe.extractor.utils.Parser.matchGroup1((java.lang.String) r2, (java.lang.String) r5)
            java.lang.String r2 = ","
            java.lang.String r3 = "."
            java.lang.String r5 = r5.replace(r2, r3)
            double r2 = java.lang.Double.parseDouble(r5)
            java.lang.String r5 = r1.toUpperCase()
            r5.getClass()
            int r1 = r5.hashCode()
            r4 = -1
            switch(r1) {
                case 66: goto L_0x0041;
                case 75: goto L_0x0036;
                case 77: goto L_0x002d;
                default: goto L_0x002b;
            }
        L_0x002b:
            r0 = -1
            goto L_0x004b
        L_0x002d:
            java.lang.String r1 = "M"
            boolean r5 = r5.equals(r1)
            if (r5 != 0) goto L_0x004b
            goto L_0x002b
        L_0x0036:
            java.lang.String r0 = "K"
            boolean r5 = r5.equals(r0)
            if (r5 != 0) goto L_0x003f
            goto L_0x002b
        L_0x003f:
            r0 = 1
            goto L_0x004b
        L_0x0041:
            java.lang.String r0 = "B"
            boolean r5 = r5.equals(r0)
            if (r5 != 0) goto L_0x004a
            goto L_0x002b
        L_0x004a:
            r0 = 0
        L_0x004b:
            switch(r0) {
                case 0: goto L_0x005f;
                case 1: goto L_0x0059;
                case 2: goto L_0x0050;
                default: goto L_0x004e;
            }
        L_0x004e:
            long r0 = (long) r2
            return r0
        L_0x0050:
            r0 = 4696837146684686336(0x412e848000000000, double:1000000.0)
        L_0x0055:
            double r2 = r2 * r0
            long r0 = (long) r2
            return r0
        L_0x0059:
            r0 = 4652007308841189376(0x408f400000000000, double:1000.0)
            goto L_0x0055
        L_0x005f:
            r0 = 4741671816366391296(0x41cdcd6500000000, double:1.0E9)
            goto L_0x0055
        */
        throw new UnsupportedOperationException("Method not decompiled: org.schabi.newpipe.extractor.utils.Utils.mixedNumberWordToLong(java.lang.String):long");
    }

    public static String nonEmptyAndNullJoin(CharSequence charSequence, String... strArr) {
        return (String) DesugarArrays.stream(strArr).filter(new mg(20)).collect(Collectors.joining(charSequence));
    }

    public static String removeMAndWWWFromUrl(String str) {
        if (a.matcher(str).find()) {
            return str.replace("m.", "");
        }
        if (b.matcher(str).find()) {
            return str.replace("www.", "");
        }
        return str;
    }

    public static String removeNonDigitCharacters(String str) {
        return str.replaceAll("\\D+", "");
    }

    public static String removeUTF8BOM(String str) {
        if (str.startsWith("﻿")) {
            str = str.substring(1);
        }
        if (str.endsWith("﻿")) {
            return str.substring(0, str.length() - 1);
        }
        return str;
    }

    public static String replaceHttpWithHttps(String str) {
        if (str == null) {
            return null;
        }
        if (!str.startsWith("http://")) {
            return str;
        }
        return "https://" + str.substring(7);
    }

    public static URL stringToURL(String str) throws MalformedURLException {
        try {
            return new URL(str);
        } catch (MalformedURLException e) {
            String message = e.getMessage();
            if (message.equals("no protocol: " + str)) {
                return new URL(y2.i("https://", str));
            }
            throw e;
        }
    }

    public static void checkUrl(Pattern pattern, String str) throws ParsingException {
        if (isNullOrEmpty(str)) {
            throw new IllegalArgumentException("Url can't be null or empty");
        } else if (!Parser.isMatch(pattern, str.toLowerCase())) {
            throw new ParsingException("Url doesn't match the pattern");
        }
    }

    public static String getStringResultFromRegexArray(String str, Pattern[] patternArr) throws Parser.RegexException {
        return getStringResultFromRegexArray(str, patternArr, 0);
    }

    public static boolean isNullOrEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static String getStringResultFromRegexArray(String str, String[] strArr, int i) throws Parser.RegexException {
        return getStringResultFromRegexArray(str, (Pattern[]) DesugarArrays.stream(strArr).filter(new mg(19)).map(new ug(5)).toArray(new kd()), i);
    }

    public static <K, V> boolean isNullOrEmpty(Map<K, V> map) {
        return map == null || map.isEmpty();
    }

    public static String getStringResultFromRegexArray(String str, Pattern[] patternArr, int i) throws Parser.RegexException {
        int length = patternArr.length;
        int i2 = 0;
        while (i2 < length) {
            try {
                String matchGroup = Parser.matchGroup(patternArr[i2], str, i);
                if (matchGroup != null) {
                    return matchGroup;
                }
                i2++;
            } catch (Parser.RegexException unused) {
            }
        }
        throw new Parser.RegexException(y2.f("No regex matched the input on group ", i));
    }
}
