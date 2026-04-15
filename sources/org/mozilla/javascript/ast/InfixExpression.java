package org.mozilla.javascript.ast;

import org.mozilla.javascript.Token;

public class InfixExpression extends AstNode {
    protected AstNode left;
    protected int operatorPosition = -1;
    protected AstNode right;

    public InfixExpression() {
    }

    public AstNode getLeft() {
        return this.left;
    }

    public int getOperator() {
        return getType();
    }

    public int getOperatorPosition() {
        return this.operatorPosition;
    }

    public AstNode getRight() {
        return this.right;
    }

    public boolean hasSideEffects() {
        int type = getType();
        if (type == 90) {
            AstNode astNode = this.right;
            if (astNode == null || !astNode.hasSideEffects()) {
                return false;
            }
            return true;
        } else if (type != 105 && type != 106) {
            return super.hasSideEffects();
        } else {
            AstNode astNode2 = this.left;
            if (astNode2 != null && astNode2.hasSideEffects()) {
                return true;
            }
            AstNode astNode3 = this.right;
            if (astNode3 == null || !astNode3.hasSideEffects()) {
                return false;
            }
            return true;
        }
    }

    public void setLeft(AstNode astNode) {
        assertNotNull(astNode);
        this.left = astNode;
        setLineno(astNode.getLineno());
        astNode.setParent(this);
    }

    public void setLeftAndRight(AstNode astNode, AstNode astNode2) {
        assertNotNull(astNode);
        assertNotNull(astNode2);
        setBounds(astNode.getPosition(), astNode2.getLength() + astNode2.getPosition());
        setLeft(astNode);
        setRight(astNode2);
    }

    public void setOperator(int i) {
        if (Token.isValidToken(i)) {
            setType(i);
            return;
        }
        throw new IllegalArgumentException(y2.f("Invalid token: ", i));
    }

    public void setOperatorPosition(int i) {
        this.operatorPosition = i;
    }

    public void setRight(AstNode astNode) {
        assertNotNull(astNode);
        this.right = astNode;
        astNode.setParent(this);
    }

    public String toSource(int i) {
        return makeIndent(i) + this.left.toSource() + " " + AstNode.operatorToString(getType()) + " " + this.right.toSource();
    }

    public void visit(NodeVisitor nodeVisitor) {
        if (nodeVisitor.visit(this)) {
            this.left.visit(nodeVisitor);
            this.right.visit(nodeVisitor);
        }
    }

    public InfixExpression(int i) {
        super(i);
    }

    public InfixExpression(int i, int i2) {
        super(i, i2);
    }

    public InfixExpression(int i, int i2, AstNode astNode, AstNode astNode2) {
        super(i, i2);
        setLeft(astNode);
        setRight(astNode2);
    }

    public InfixExpression(AstNode astNode, AstNode astNode2) {
        setLeftAndRight(astNode, astNode2);
    }

    public InfixExpression(int i, AstNode astNode, AstNode astNode2, int i2) {
        setType(i);
        setOperatorPosition(i2 - astNode.getPosition());
        setLeftAndRight(astNode, astNode2);
    }
}
