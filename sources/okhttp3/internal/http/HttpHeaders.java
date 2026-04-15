package okhttp3.internal.http;

import androidx.appcompat.widget.ActivityChooserView;
import java.io.EOFException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import okhttp3.Challenge;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Util;

public final class HttpHeaders {
    private static final cq QUOTED_STRING_DELIMITERS = cq.m("\"\\");
    private static final cq TOKEN_DELIMITERS = cq.m("\t ,=");

    private HttpHeaders() {
    }

    public static long contentLength(Response response) {
        return contentLength(response.headers());
    }

    public static boolean hasBody(Response response) {
        if (response.request().method().equals("HEAD")) {
            return false;
        }
        int code = response.code();
        if (((code >= 100 && code < 200) || code == 204 || code == 304) && contentLength(response) == -1 && !"chunked".equalsIgnoreCase(response.header("Transfer-Encoding"))) {
            return false;
        }
        return true;
    }

    public static boolean hasVaryAll(Response response) {
        return hasVaryAll(response.headers());
    }

    private static void parseChallengeHeader(List<Challenge> list, ck ckVar) {
        String readToken;
        int skipAll;
        String str;
        while (true) {
            String str2 = null;
            while (true) {
                if (str2 == null) {
                    skipWhitespaceAndCommas(ckVar);
                    str2 = readToken(ckVar);
                    if (str2 == null) {
                        return;
                    }
                }
                boolean skipWhitespaceAndCommas = skipWhitespaceAndCommas(ckVar);
                readToken = readToken(ckVar);
                if (readToken != null) {
                    skipAll = skipAll(ckVar, (byte) 61);
                    boolean skipWhitespaceAndCommas2 = skipWhitespaceAndCommas(ckVar);
                    if (skipWhitespaceAndCommas || (!skipWhitespaceAndCommas2 && !ckVar.g())) {
                        LinkedHashMap linkedHashMap = new LinkedHashMap();
                        int skipAll2 = skipAll + skipAll(ckVar, (byte) 61);
                        while (true) {
                            if (readToken == null) {
                                readToken = readToken(ckVar);
                                if (skipWhitespaceAndCommas(ckVar)) {
                                    continue;
                                    break;
                                }
                                skipAll2 = skipAll(ckVar, (byte) 61);
                            }
                            if (skipAll2 == 0) {
                                continue;
                                break;
                            } else if (skipAll2 <= 1 && !skipWhitespaceAndCommas(ckVar)) {
                                if (ckVar.g() || ckVar.i(0) != 34) {
                                    str = readToken(ckVar);
                                } else {
                                    str = readQuotedString(ckVar);
                                }
                                if (str == null || ((String) linkedHashMap.put(readToken, str)) != null) {
                                    return;
                                }
                                if (skipWhitespaceAndCommas(ckVar) || ckVar.g()) {
                                    readToken = null;
                                } else {
                                    return;
                                }
                            } else {
                                return;
                            }
                        }
                        list.add(new Challenge(str2, (Map<String, String>) linkedHashMap));
                        str2 = readToken;
                    }
                } else if (ckVar.g()) {
                    list.add(new Challenge(str2, (Map<String, String>) Collections.emptyMap()));
                    return;
                } else {
                    return;
                }
            }
            StringBuilder m = y2.m(readToken);
            m.append(repeat('=', skipAll));
            list.add(new Challenge(str2, (Map<String, String>) Collections.singletonMap((Object) null, m.toString())));
        }
    }

    public static List<Challenge> parseChallenges(Headers headers, String str) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < headers.size(); i++) {
            if (str.equalsIgnoreCase(headers.name(i))) {
                ck ckVar = new ck();
                String value = headers.value(i);
                ckVar.ap(0, value.length(), value);
                parseChallengeHeader(arrayList, ckVar);
            }
        }
        return arrayList;
    }

    public static int parseSeconds(String str, int i) {
        try {
            long parseLong = Long.parseLong(str);
            if (parseLong > 2147483647L) {
                return ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            }
            if (parseLong < 0) {
                return 0;
            }
            return (int) parseLong;
        } catch (NumberFormatException unused) {
            return i;
        }
    }

    private static String readQuotedString(ck ckVar) {
        if (ckVar.readByte() == 34) {
            ck ckVar2 = new ck();
            while (true) {
                long y = ckVar.y(QUOTED_STRING_DELIMITERS);
                if (y == -1) {
                    return null;
                }
                if (ckVar.i(y) == 34) {
                    ckVar2.write(ckVar, y);
                    ckVar.readByte();
                    return ckVar2.ae();
                } else if (ckVar.f == y + 1) {
                    return null;
                } else {
                    ckVar2.write(ckVar, y);
                    ckVar.readByte();
                    ckVar2.write(ckVar, 1);
                }
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    private static String readToken(ck ckVar) {
        try {
            long y = ckVar.y(TOKEN_DELIMITERS);
            if (y == -1) {
                y = ckVar.f;
            }
            if (y != 0) {
                return ckVar.ad(y, jd.a);
            }
            return null;
        } catch (EOFException unused) {
            throw new AssertionError();
        }
    }

    public static void receiveHeaders(CookieJar cookieJar, HttpUrl httpUrl, Headers headers) {
        if (cookieJar != CookieJar.NO_COOKIES) {
            List<Cookie> parseAll = Cookie.parseAll(httpUrl, headers);
            if (!parseAll.isEmpty()) {
                cookieJar.saveFromResponse(httpUrl, parseAll);
            }
        }
    }

    private static String repeat(char c, int i) {
        char[] cArr = new char[i];
        Arrays.fill(cArr, c);
        return new String(cArr);
    }

    private static int skipAll(ck ckVar, byte b) {
        int i = 0;
        while (!ckVar.g() && ckVar.i(0) == b) {
            i++;
            ckVar.readByte();
        }
        return i;
    }

    public static int skipUntil(String str, int i, String str2) {
        while (i < str.length() && str2.indexOf(str.charAt(i)) == -1) {
            i++;
        }
        return i;
    }

    public static int skipWhitespace(String str, int i) {
        while (i < str.length() && ((r0 = str.charAt(i)) == ' ' || r0 == 9)) {
            i++;
        }
        return i;
    }

    private static boolean skipWhitespaceAndCommas(ck ckVar) {
        boolean z = false;
        while (!ckVar.g()) {
            byte i = ckVar.i(0);
            if (i != 44) {
                if (i != 32 && i != 9) {
                    break;
                }
                ckVar.readByte();
            } else {
                ckVar.readByte();
                z = true;
            }
        }
        return z;
    }

    private static long stringToLong(String str) {
        if (str == null) {
            return -1;
        }
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException unused) {
            return -1;
        }
    }

    private static Set<String> varyFields(Response response) {
        return varyFields(response.headers());
    }

    public static Headers varyHeaders(Response response) {
        return varyHeaders(response.networkResponse().request().headers(), response.headers());
    }

    public static boolean varyMatches(Response response, Headers headers, Request request) {
        for (String next : varyFields(response)) {
            if (!Util.equal(headers.values(next), request.headers(next))) {
                return false;
            }
        }
        return true;
    }

    public static long contentLength(Headers headers) {
        return stringToLong(headers.get("Content-Length"));
    }

    public static boolean hasVaryAll(Headers headers) {
        return varyFields(headers).contains("*");
    }

    public static Set<String> varyFields(Headers headers) {
        Set<String> emptySet = Collections.emptySet();
        int size = headers.size();
        for (int i = 0; i < size; i++) {
            if ("Vary".equalsIgnoreCase(headers.name(i))) {
                String value = headers.value(i);
                if (emptySet.isEmpty()) {
                    emptySet = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
                }
                for (String trim : value.split(",")) {
                    emptySet.add(trim.trim());
                }
            }
        }
        return emptySet;
    }

    public static Headers varyHeaders(Headers headers, Headers headers2) {
        Set<String> varyFields = varyFields(headers2);
        if (varyFields.isEmpty()) {
            return new Headers.Builder().build();
        }
        Headers.Builder builder = new Headers.Builder();
        int size = headers.size();
        for (int i = 0; i < size; i++) {
            String name = headers.name(i);
            if (varyFields.contains(name)) {
                builder.add(name, headers.value(i));
            }
        }
        return builder.build();
    }
}
