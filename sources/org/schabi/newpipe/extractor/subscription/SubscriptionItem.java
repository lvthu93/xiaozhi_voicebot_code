package org.schabi.newpipe.extractor.subscription;

import java.io.Serializable;

public class SubscriptionItem implements Serializable {
    public final int c;
    public final String f;
    public final String g;

    public SubscriptionItem(int i, String str, String str2) {
        this.c = i;
        this.f = str;
        this.g = str2;
    }

    public String getName() {
        return this.g;
    }

    public int getServiceId() {
        return this.c;
    }

    public String getUrl() {
        return this.f;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append("@");
        sb.append(Integer.toHexString(hashCode()));
        sb.append("[name=");
        sb.append(this.g);
        sb.append(" > ");
        sb.append(this.c);
        sb.append(":");
        return y2.k(sb, this.f, "]");
    }
}
