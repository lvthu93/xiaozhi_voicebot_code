package org.jsoup.nodes;

public class BooleanAttribute extends Attribute {
    public BooleanAttribute(String str) {
        super(str, (String) null);
    }

    public boolean isBooleanAttribute() {
        return true;
    }
}
