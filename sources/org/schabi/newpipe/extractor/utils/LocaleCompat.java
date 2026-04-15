package org.schabi.newpipe.extractor.utils;

import j$.util.Optional;
import java.util.Locale;

public final class LocaleCompat {
    public static Optional<Locale> forLanguageTag(String str) {
        if (str.contains("-")) {
            String[] split = str.split("-", -1);
            if (split.length > 2) {
                return Optional.of(new Locale(split[0], split[1], split[2]));
            }
            if (split.length > 1) {
                return Optional.of(new Locale(split[0], split[1]));
            }
            if (split.length == 1) {
                return Optional.of(new Locale(split[0]));
            }
        } else if (!str.contains("_")) {
            return Optional.of(new Locale(str));
        } else {
            String[] split2 = str.split("_", -1);
            if (split2.length > 2) {
                return Optional.of(new Locale(split2[0], split2[1], split2[2]));
            }
            if (split2.length > 1) {
                return Optional.of(new Locale(split2[0], split2[1]));
            }
            if (split2.length == 1) {
                return Optional.of(new Locale(split2[0]));
            }
        }
        return Optional.empty();
    }
}
