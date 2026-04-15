package org.mozilla.javascript.ast;

import org.mozilla.javascript.Token;

public class Label extends Jump {
    private String name;

    public Label() {
        this.type = Token.LABEL;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        String str2;
        if (str == null) {
            str2 = null;
        } else {
            str2 = str.trim();
        }
        if (str2 == null || "".equals(str2)) {
            throw new IllegalArgumentException("invalid label name");
        }
        this.name = str2;
    }

    public String toSource(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(makeIndent(i));
        return y2.k(sb, this.name, ":\n");
    }

    public void visit(NodeVisitor nodeVisitor) {
        nodeVisitor.visit(this);
    }

    public Label(int i) {
        this(i, -1);
    }

    public Label(int i, int i2) {
        this.type = Token.LABEL;
        this.position = i;
        this.length = i2;
    }

    public Label(int i, int i2, String str) {
        this(i, i2);
        setName(str);
    }
}
