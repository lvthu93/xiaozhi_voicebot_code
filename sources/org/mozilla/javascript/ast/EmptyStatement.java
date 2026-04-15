package org.mozilla.javascript.ast;

import org.mozilla.javascript.Token;

public class EmptyStatement extends AstNode {
    public EmptyStatement() {
        this.type = Token.EMPTY;
    }

    public String toSource(int i) {
        return makeIndent(i) + ";\n";
    }

    public void visit(NodeVisitor nodeVisitor) {
        nodeVisitor.visit(this);
    }

    public EmptyStatement(int i) {
        super(i);
        this.type = Token.EMPTY;
    }

    public EmptyStatement(int i, int i2) {
        super(i, i2);
        this.type = Token.EMPTY;
    }
}
