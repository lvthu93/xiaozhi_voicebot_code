package org.schabi.newpipe.extractor.utils;

import j$.util.DesugarArrays;
import j$.util.stream.Collectors;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.schabi.newpipe.extractor.exceptions.ParsingException;

public final class Parser {

    public static class RegexException extends ParsingException {
        public RegexException(String str) {
            super(str);
        }
    }

    public static Map<String, String> compatParseMap(String str) {
        return (Map) DesugarArrays.stream(str.split("&")).map(new ug(2)).filter(new mg(18)).collect(Collectors.toMap(new ug(3), new ug(4), new ca(2)));
    }

    public static boolean isMatch(String str, String str2) {
        return isMatch(Pattern.compile(str), str2);
    }

    public static String matchGroup(String str, String str2, int i) throws RegexException {
        return matchGroup(Pattern.compile(str), str2, i);
    }

    public static String matchGroup1(String str, String str2) throws RegexException {
        return matchGroup(str, str2, 1);
    }

    public static String matchGroup1MultiplePatterns(Pattern[] patternArr, String str) throws RegexException {
        return matchMultiplePatterns(patternArr, str).group(1);
    }

    public static Matcher matchMultiplePatterns(Pattern[] patternArr, String str) throws RegexException {
        String str2;
        RegexException regexException = null;
        for (Pattern pattern : patternArr) {
            Matcher matcher = pattern.matcher(str);
            if (matcher.find()) {
                return matcher;
            }
            if (regexException == null) {
                StringBuilder sb = new StringBuilder("Failed to find pattern \"");
                sb.append(pattern.pattern());
                sb.append("\"");
                if (str.length() <= 1000) {
                    str2 = y2.j("inside of \"", str, "\"");
                } else {
                    str2 = "";
                }
                sb.append(str2);
                regexException = new RegexException(sb.toString());
            }
        }
        if (regexException == null) {
            regexException = new RegexException("Empty patterns array passed to matchMultiplePatterns");
        }
        throw regexException;
    }

    public static Matcher matchOrThrow(Pattern pattern, String str) throws RegexException {
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return matcher;
        }
        String str2 = "Failed to find pattern \"" + pattern.pattern() + "\"";
        if (str.length() <= 1024) {
            str2 = str2 + " inside of \"" + str + "\"";
        }
        throw new RegexException(str2);
    }

    public static boolean isMatch(Pattern pattern, String str) {
        return pattern.matcher(str).find();
    }

    public static String matchGroup(Pattern pattern, String str, int i) throws RegexException {
        return matchOrThrow(pattern, str).group(i);
    }

    public static String matchGroup1(Pattern pattern, String str) throws RegexException {
        return matchGroup(pattern, str, 1);
    }
}
