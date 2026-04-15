package org.mozilla.javascript.ast;

import org.mozilla.javascript.Token;

public class XmlDotQuery extends InfixExpression {
    private int rp = -1;

    public XmlDotQuery() {
        this.type = Token.DOTQUERY;
    }

    public int getRp() {
        return this.rp;
    }

    public void setRp(int i) {
        this.rp = i;
    }

    public String toSource(int i) {
        return makeIndent(i) + getLeft().toSource(0) + ".(" + getRight().toSource(0) + ")";
    }

    public XmlDotQuery(int i) {
        super(i);
        this.type = Token.DOTQUERY;
    }

    public XmlDotQuery(int i, int i2) {
        super(i, i2);
        this.type = Token.DOTQUERY;
    }
}
