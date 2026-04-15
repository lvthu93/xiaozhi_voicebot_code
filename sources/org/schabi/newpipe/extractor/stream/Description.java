package org.schabi.newpipe.extractor.stream;

import j$.util.Objects;
import java.io.Serializable;

public class Description implements Serializable {
    public static final Description g = new Description("", 3);
    public final String c;
    public final int f;

    public Description(String str, int i) {
        this.f = i;
        this.c = (String) Objects.requireNonNullElse(str, "");
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Description description = (Description) obj;
        if (this.f != description.f || !Objects.equals(this.c, description.c)) {
            return false;
        }
        return true;
    }

    public String getContent() {
        return this.c;
    }

    public int getType() {
        return this.f;
    }

    public int hashCode() {
        return Objects.hash(this.c, Integer.valueOf(this.f));
    }
}
