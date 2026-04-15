package org.mozilla.javascript.ast;

public class ConditionalExpression extends AstNode {
    private int colonPosition = -1;
    private AstNode falseExpression;
    private int questionMarkPosition = -1;
    private AstNode testExpression;
    private AstNode trueExpression;

    public ConditionalExpression() {
        this.type = 103;
    }

    public int getColonPosition() {
        return this.colonPosition;
    }

    public AstNode getFalseExpression() {
        return this.falseExpression;
    }

    public int getQuestionMarkPosition() {
        return this.questionMarkPosition;
    }

    public AstNode getTestExpression() {
        return this.testExpression;
    }

    public AstNode getTrueExpression() {
        return this.trueExpression;
    }

    public boolean hasSideEffects() {
        if (this.testExpression == null || this.trueExpression == null || this.falseExpression == null) {
            AstNode.codeBug();
        }
        if (!this.trueExpression.hasSideEffects() || !this.falseExpression.hasSideEffects()) {
            return false;
        }
        return true;
    }

    public void setColonPosition(int i) {
        this.colonPosition = i;
    }

    public void setFalseExpression(AstNode astNode) {
        assertNotNull(astNode);
        this.falseExpression = astNode;
        astNode.setParent(this);
    }

    public void setQuestionMarkPosition(int i) {
        this.questionMarkPosition = i;
    }

    public void setTestExpression(AstNode astNode) {
        assertNotNull(astNode);
        this.testExpression = astNode;
        astNode.setParent(this);
    }

    public void setTrueExpression(AstNode astNode) {
        assertNotNull(astNode);
        this.trueExpression = astNode;
        astNode.setParent(this);
    }

    public String toSource(int i) {
        return makeIndent(i) + this.testExpression.toSource(i) + " ? " + this.trueExpression.toSource(0) + " : " + this.falseExpression.toSource(0);
    }

    public void visit(NodeVisitor nodeVisitor) {
        if (nodeVisitor.visit(this)) {
            this.testExpression.visit(nodeVisitor);
            this.trueExpression.visit(nodeVisitor);
            this.falseExpression.visit(nodeVisitor);
        }
    }

    public ConditionalExpression(int i) {
        super(i);
        this.type = 103;
    }

    public ConditionalExpression(int i, int i2) {
        super(i, i2);
        this.type = 103;
    }
}
