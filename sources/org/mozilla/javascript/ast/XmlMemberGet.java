package org.mozilla.javascript.ast;

import org.mozilla.javascript.Token;

public class XmlMemberGet extends InfixExpression {
    public XmlMemberGet() {
        this.type = Token.DOTDOT;
    }

    private String dotsToString() {
        int type = getType();
        if (type == 109) {
            return ".";
        }
        if (type == 144) {
            return "..";
        }
        throw new IllegalArgumentException("Invalid type of XmlMemberGet: " + getType());
    }

    public XmlRef getMemberRef() {
        return (XmlRef) getRight();
    }

    public AstNode getTarget() {
        return getLeft();
    }

    public void setProperty(XmlRef xmlRef) {
        setRight(xmlRef);
    }

    public void setTarget(AstNode astNode) {
        setLeft(astNode);
    }

    public String toSource(int i) {
        return makeIndent(i) + getLeft().toSource(0) + dotsToString() + getRight().toSource(0);
    }

    public XmlMemberGet(int i) {
        super(i);
        this.type = Token.DOTDOT;
    }

    public XmlMemberGet(int i, int i2) {
        super(i, i2);
        this.type = Token.DOTDOT;
    }

    public XmlMemberGet(int i, int i2, AstNode astNode, XmlRef xmlRef) {
        super(i, i2, astNode, (AstNode) xmlRef);
        this.type = Token.DOTDOT;
    }

    public XmlMemberGet(AstNode astNode, XmlRef xmlRef) {
        super(astNode, (AstNode) xmlRef);
        this.type = Token.DOTDOT;
    }

    public XmlMemberGet(AstNode astNode, XmlRef xmlRef, int i) {
        super((int) Token.DOTDOT, astNode, (AstNode) xmlRef, i);
        this.type = Token.DOTDOT;
    }
}
