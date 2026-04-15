package org.mozilla.javascript.ast;

public class GeneratorExpressionLoop extends ForInLoop {
    public GeneratorExpressionLoop() {
    }

    public boolean isForEach() {
        return false;
    }

    public void setIsForEach(boolean z) {
        throw new UnsupportedOperationException("this node type does not support for each");
    }

    public String toSource(int i) {
        String str;
        String str2;
        StringBuilder sb = new StringBuilder();
        sb.append(makeIndent(i));
        sb.append(" for ");
        if (isForEach()) {
            str = "each ";
        } else {
            str = "";
        }
        sb.append(str);
        sb.append("(");
        sb.append(this.iterator.toSource(0));
        if (isForOf()) {
            str2 = " of ";
        } else {
            str2 = " in ";
        }
        sb.append(str2);
        sb.append(this.iteratedObject.toSource(0));
        sb.append(")");
        return sb.toString();
    }

    public void visit(NodeVisitor nodeVisitor) {
        if (nodeVisitor.visit(this)) {
            this.iterator.visit(nodeVisitor);
            this.iteratedObject.visit(nodeVisitor);
        }
    }

    public GeneratorExpressionLoop(int i) {
        super(i);
    }

    public GeneratorExpressionLoop(int i, int i2) {
        super(i, i2);
    }
}
