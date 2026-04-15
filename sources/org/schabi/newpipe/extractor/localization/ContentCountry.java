package org.schabi.newpipe.extractor.localization;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContentCountry implements Serializable {
    public static final ContentCountry f = new ContentCountry(Localization.g.getCountryCode());
    public final String c;

    public ContentCountry(String str) {
        this.c = str;
    }

    public static List<ContentCountry> listFrom(String... strArr) {
        ArrayList arrayList = new ArrayList();
        for (String contentCountry : strArr) {
            arrayList.add(new ContentCountry(contentCountry));
        }
        return Collections.unmodifiableList(arrayList);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ContentCountry)) {
            return false;
        }
        return this.c.equals(((ContentCountry) obj).c);
    }

    public String getCountryCode() {
        return this.c;
    }

    public int hashCode() {
        return this.c.hashCode();
    }

    public String toString() {
        return getCountryCode();
    }
}
