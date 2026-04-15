package org.mozilla.javascript.ast;

public class XmlElemRef extends XmlRef {
    private AstNode indexExpr;
    private int lb = -1;
    private int rb = -1;

    public XmlElemRef() {
        this.type = 78;
    }

    public AstNode getExpression() {
        return this.indexExpr;
    }

    public int getLb() {
        return this.lb;
    }

    public int getRb() {
        return this.rb;
    }

    public void setBrackets(int i, int i2) {
        this.lb = i;
        this.rb = i2;
    }

    public void setExpression(AstNode astNode) {
        assertNotNull(astNode);
        this.indexExpr = astNode;
        astNode.setParent(this);
    }

    public void setLb(int i) {
        this.lb = i;
    }

    public void setRb(int i) {
        this.rb = i;
    }

    public String toSource(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(makeIndent(i));
        if (isAttributeAccess()) {
            sb.append("@");
        }
        Name name = this.namespace;
        if (name != null) {
            sb.append(name.toSource(0));
            sb.append("::");
        }
        sb.append("[");
        sb.append(this.indexExpr.toSource(0));
        sb.append("]");
        return sb.toString();
    }

    public void visit(NodeVisitor nodeVisitor) {
        if (nodeVisitor.visit(this)) {
            Name name = this.namespace;
            if (name != null) {
                name.visit(nodeVisitor);
            }
            this.indexExpr.visit(nodeVisitor);
        }
    }

    public XmlElemRef(int i) {
        super(i);
        this.type = 78;
    }

    public XmlElemRef(int i, int i2) {
        super(i, i2);
        this.type = 78;
    }
}
