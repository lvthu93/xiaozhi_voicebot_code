package org.schabi.newpipe.extractor.localization;

import j$.time.LocalDateTime;
import org.schabi.newpipe.extractor.timeago.PatternsHolder;
import org.schabi.newpipe.extractor.timeago.PatternsManager;

public final class TimeAgoPatternsManager {
    public static TimeAgoParser getTimeAgoParserFor(Localization localization, LocalDateTime localDateTime) {
        PatternsHolder patterns = PatternsManager.getPatterns(localization.getLanguageCode(), localization.getCountryCode());
        if (patterns == null) {
            return null;
        }
        return new TimeAgoParser(patterns, localDateTime);
    }

    public static TimeAgoParser getTimeAgoParserFor(Localization localization) {
        return getTimeAgoParserFor(localization, LocalDateTime.now());
    }
}
