package org.schabi.newpipe.extractor.localization;

import j$.time.LocalDateTime;
import j$.time.temporal.ChronoUnit;
import j$.util.Collection$EL;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.timeago.PatternsHolder;
import org.schabi.newpipe.extractor.utils.Parser;

public class TimeAgoParser {
    public final PatternsHolder a;
    public final LocalDateTime b;

    public TimeAgoParser(PatternsHolder patternsHolder, LocalDateTime localDateTime) {
        this.a = patternsHolder;
        this.b = localDateTime;
    }

    public final DateWrapper a(int i, ChronoUnit chronoUnit) {
        LocalDateTime localDateTime;
        ChronoUnit chronoUnit2 = ChronoUnit.YEARS;
        LocalDateTime localDateTime2 = this.b;
        if (chronoUnit == chronoUnit2) {
            localDateTime = localDateTime2.minusYears((long) i).minusDays(1);
        } else {
            localDateTime = localDateTime2.g((long) i, chronoUnit);
        }
        boolean isDateBased = chronoUnit.isDateBased();
        if (isDateBased) {
            localDateTime = localDateTime.truncatedTo(ChronoUnit.DAYS);
        }
        return new DateWrapper(localDateTime, isDateBased);
    }

    public final boolean b(String str, String str2) {
        String str3;
        if (str.equals(str2)) {
            return true;
        }
        PatternsHolder patternsHolder = this.a;
        if (patternsHolder.wordSeparator().isEmpty()) {
            return str.toLowerCase().contains(str2.toLowerCase());
        }
        String quote = Pattern.quote(str2.toLowerCase());
        if (patternsHolder.wordSeparator().equals(" ")) {
            str3 = "[ \\t\\xA0\\u1680\\u180e\\u2000-\\u200a\\u202f\\u205f\\u3000\\d]";
        } else {
            str3 = Pattern.quote(patternsHolder.wordSeparator());
        }
        StringBuilder sb = new StringBuilder("(^|");
        sb.append(str3);
        sb.append(")");
        sb.append(quote);
        sb.append("($|");
        return Parser.isMatch(y2.k(sb, str3, ")"), str.toLowerCase());
    }

    public DateWrapper parse(String str) throws ParsingException {
        int i;
        PatternsHolder patternsHolder = this.a;
        for (Map.Entry next : patternsHolder.specialCases().entrySet()) {
            ChronoUnit chronoUnit = (ChronoUnit) next.getKey();
            Iterator it = ((Map) next.getValue()).entrySet().iterator();
            while (true) {
                if (it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    int intValue = ((Integer) entry.getValue()).intValue();
                    if (b(str, (String) entry.getKey())) {
                        return a(intValue, chronoUnit);
                    }
                }
            }
        }
        try {
            i = Integer.parseInt(str.replaceAll("\\D+", ""));
        } catch (NumberFormatException unused) {
            i = 1;
        }
        return a(i, (ChronoUnit) Collection$EL.stream(patternsHolder.asMap().entrySet()).filter(new kc(this, str, 0)).map(new z5(5)).findFirst().orElseThrow(new j7(str, 2)));
    }
}
