package org.jsoup.parser;

import org.jsoup.internal.Normalizer;
import org.jsoup.nodes.Attributes;

public class ParseSettings {
    public static final ParseSettings htmlDefault = new ParseSettings(false, false);
    public static final ParseSettings preserveCase = new ParseSettings(true, true);
    private final boolean preserveAttributeCase;
    private final boolean preserveTagCase;

    public ParseSettings(boolean z, boolean z2) {
        this.preserveTagCase = z;
        this.preserveAttributeCase = z2;
    }

    public String normalizeAttribute(String str) {
        String trim = str.trim();
        if (!this.preserveAttributeCase) {
            return Normalizer.lowerCase(trim);
        }
        return trim;
    }

    public Attributes normalizeAttributes(Attributes attributes) {
        if (!this.preserveAttributeCase) {
            attributes.normalize();
        }
        return attributes;
    }

    public String normalizeTag(String str) {
        String trim = str.trim();
        if (!this.preserveTagCase) {
            return Normalizer.lowerCase(trim);
        }
        return trim;
    }
}
