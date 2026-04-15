package org.schabi.newpipe.extractor;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.schabi.newpipe.extractor.stream.Description;

public class MetaInfo implements Serializable {
    public String c;
    public Description f;
    public List<URL> g;
    public List<String> h;

    public MetaInfo(String str, Description description, List<URL> list, List<String> list2) {
        this.c = "";
        this.g = new ArrayList();
        new ArrayList();
        this.c = str;
        this.f = description;
        this.g = list;
        this.h = list2;
    }

    public void addUrl(URL url) {
        this.g.add(url);
    }

    public void addUrlText(String str) {
        this.h.add(str);
    }

    public Description getContent() {
        return this.f;
    }

    public String getTitle() {
        return this.c;
    }

    public List<String> getUrlTexts() {
        return this.h;
    }

    public List<URL> getUrls() {
        return this.g;
    }

    public void setContent(Description description) {
        this.f = description;
    }

    public void setTitle(String str) {
        this.c = str;
    }

    public void setUrlTexts(List<String> list) {
        this.h = list;
    }

    public void setUrls(List<URL> list) {
        this.g = list;
    }

    public MetaInfo() {
        this.c = "";
        this.g = new ArrayList();
        this.h = new ArrayList();
    }
}
