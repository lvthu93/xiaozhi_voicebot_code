package org.schabi.newpipe.extractor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.linkhandler.LinkHandler;

public abstract class Info implements Serializable {
    public final int c;
    public final String f;
    public final String g;
    public String h;
    public final String i;
    public final ArrayList j;

    public Info(int i2, String str, String str2, String str3, String str4) {
        this.j = new ArrayList();
        this.c = i2;
        this.f = str;
        this.g = str2;
        this.h = str3;
        this.i = str4;
    }

    public void addAllErrors(Collection<Throwable> collection) {
        this.j.addAll(collection);
    }

    public void addError(Throwable th) {
        this.j.add(th);
    }

    public List<Throwable> getErrors() {
        return this.j;
    }

    public String getId() {
        return this.f;
    }

    public String getName() {
        return this.i;
    }

    public String getOriginalUrl() {
        return this.h;
    }

    public StreamingService getService() {
        try {
            return NewPipe.getService(this.c);
        } catch (ExtractionException e) {
            throw new RuntimeException("Info object has invalid service id", e);
        }
    }

    public int getServiceId() {
        return this.c;
    }

    public String getUrl() {
        return this.g;
    }

    public void setOriginalUrl(String str) {
        this.h = str;
    }

    public String toString() {
        String str;
        String str2 = this.h;
        String str3 = this.g;
        if (str3.equals(str2)) {
            str = "";
        } else {
            str = y2.k(new StringBuilder(" (originalUrl=\""), this.h, "\")");
        }
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append("[url=\"");
        sb.append(str3);
        sb.append("\"");
        sb.append(str);
        sb.append(", name=\"");
        return y2.k(sb, this.i, "\"]");
    }

    public Info(int i2, LinkHandler linkHandler, String str) {
        this(i2, linkHandler.getId(), linkHandler.getUrl(), linkHandler.getOriginalUrl(), str);
    }
}
