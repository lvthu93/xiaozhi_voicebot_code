package org.jsoup.nodes;

import java.util.Collections;
import java.util.List;
import org.jsoup.helper.Validate;

abstract class LeafNode extends Node {
    private static final List<Node> EmptyNodes = Collections.emptyList();
    Object value;

    private void ensureAttributes() {
        if (!hasAttributes()) {
            Object obj = this.value;
            Attributes attributes = new Attributes();
            this.value = attributes;
            if (obj != null) {
                attributes.put(nodeName(), (String) obj);
            }
        }
    }

    public String absUrl(String str) {
        ensureAttributes();
        return super.absUrl(str);
    }

    public String attr(String str) {
        Validate.notNull(str);
        if (!hasAttributes()) {
            return str.equals(nodeName()) ? (String) this.value : "";
        }
        return super.attr(str);
    }

    public final Attributes attributes() {
        ensureAttributes();
        return (Attributes) this.value;
    }

    public String baseUri() {
        return hasParent() ? parent().baseUri() : "";
    }

    public int childNodeSize() {
        return 0;
    }

    public String coreValue() {
        return attr(nodeName());
    }

    public void doSetBaseUri(String str) {
    }

    public List<Node> ensureChildNodes() {
        return EmptyNodes;
    }

    public boolean hasAttr(String str) {
        ensureAttributes();
        return super.hasAttr(str);
    }

    public final boolean hasAttributes() {
        return this.value instanceof Attributes;
    }

    public Node removeAttr(String str) {
        ensureAttributes();
        return super.removeAttr(str);
    }

    public void coreValue(String str) {
        attr(nodeName(), str);
    }

    public Node attr(String str, String str2) {
        if (hasAttributes() || !str.equals(nodeName())) {
            ensureAttributes();
            super.attr(str, str2);
        } else {
            this.value = str2;
        }
        return this;
    }
}
