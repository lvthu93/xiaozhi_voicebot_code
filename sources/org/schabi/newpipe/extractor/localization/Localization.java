package org.schabi.newpipe.extractor.localization;

import j$.util.Objects;
import j$.util.Optional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.utils.LocaleCompat;

public class Localization implements Serializable {
    public static final Localization g = new Localization("en", "GB");
    public final String c;
    public final String f;

    public Localization(String str, String str2) {
        this.c = str;
        this.f = str2;
    }

    public static Localization fromLocale(Locale locale) {
        return new Localization(locale.getLanguage(), locale.getCountry());
    }

    public static Optional<Localization> fromLocalizationCode(String str) {
        return LocaleCompat.forLanguageTag(str).map(new z5(4));
    }

    public static Locale getLocaleFromThreeLetterCode(String str) throws ParsingException {
        String[] iSOLanguages = Locale.getISOLanguages();
        HashMap hashMap = new HashMap(iSOLanguages.length);
        for (String locale : iSOLanguages) {
            Locale locale2 = new Locale(locale);
            hashMap.put(locale2.getISO3Language(), locale2);
        }
        if (hashMap.containsKey(str)) {
            return (Locale) hashMap.get(str);
        }
        throw new ParsingException(y2.i("Could not get Locale from this three letter language code", str));
    }

    public static List<Localization> listFrom(String... strArr) {
        ArrayList arrayList = new ArrayList();
        for (String str : strArr) {
            arrayList.add(fromLocalizationCode(str).orElseThrow(new j7(str, 1)));
        }
        return Collections.unmodifiableList(arrayList);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Localization)) {
            return false;
        }
        Localization localization = (Localization) obj;
        if (!this.c.equals(localization.c) || !Objects.equals(this.f, localization.f)) {
            return false;
        }
        return true;
    }

    public String getCountryCode() {
        String str = this.f;
        return str == null ? "" : str;
    }

    public String getLanguageCode() {
        return this.c;
    }

    public String getLocalizationCode() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append(this.c);
        String str2 = this.f;
        if (str2 == null) {
            str = "";
        } else {
            str = "-" + str2;
        }
        sb.append(str);
        return sb.toString();
    }

    public int hashCode() {
        return Objects.hashCode(this.f) + (this.c.hashCode() * 31);
    }

    public String toString() {
        return "Localization[" + getLocalizationCode() + "]";
    }

    public Localization(String str) {
        this(str, (String) null);
    }
}
