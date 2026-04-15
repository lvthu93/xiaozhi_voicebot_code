package org.mozilla.javascript.ast;

import java.util.ArrayList;
import java.util.List;
import org.mozilla.javascript.Token;

public class LabeledStatement extends AstNode {
    private List<Label> labels = new ArrayList();
    private AstNode statement;

    public LabeledStatement() {
        this.type = Token.EXPR_VOID;
    }

    public void addLabel(Label label) {
        assertNotNull(label);
        this.labels.add(label);
        label.setParent(this);
    }

    public Label getFirstLabel() {
        return this.labels.get(0);
    }

    public Label getLabelByName(String str) {
        for (Label next : this.labels) {
            if (str.equals(next.getName())) {
                return next;
            }
        }
        return null;
    }

    public List<Label> getLabels() {
        return this.labels;
    }

    public AstNode getStatement() {
        return this.statement;
    }

    public boolean hasSideEffects() {
        return true;
    }

    public void setLabels(List<Label> list) {
        assertNotNull(list);
        List<Label> list2 = this.labels;
        if (list2 != null) {
            list2.clear();
        }
        for (Label addLabel : list) {
            addLabel(addLabel);
        }
    }

    public void setStatement(AstNode astNode) {
        assertNotNull(astNode);
        this.statement = astNode;
        astNode.setParent(this);
    }

    public String toSource(int i) {
        StringBuilder sb = new StringBuilder();
        for (Label source : this.labels) {
            sb.append(source.toSource(i));
        }
        sb.append(this.statement.toSource(i + 1));
        return sb.toString();
    }

    public void visit(NodeVisitor nodeVisitor) {
        if (nodeVisitor.visit(this)) {
            for (Label visit : this.labels) {
                visit.visit(nodeVisitor);
            }
            this.statement.visit(nodeVisitor);
        }
    }

    public LabeledStatement(int i) {
        super(i);
        this.type = Token.EXPR_VOID;
    }

    public LabeledStatement(int i, int i2) {
        super(i, i2);
        this.type = Token.EXPR_VOID;
    }
}
