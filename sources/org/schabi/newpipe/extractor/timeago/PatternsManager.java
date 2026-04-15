package org.schabi.newpipe.extractor.timeago;

public class PatternsManager {
    public static PatternsHolder getPatterns(String str, String str2) {
        String str3;
        StringBuilder m = y2.m(str);
        if (str2 == null || str2.isEmpty()) {
            str3 = "";
        } else {
            str3 = "_".concat(str2);
        }
        m.append(str3);
        return PatternMap.getPattern(m.toString());
    }
}
