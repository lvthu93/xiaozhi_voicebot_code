package org.mozilla.javascript.ast;

import org.eclipse.paho.client.mqttv3.MqttTopic;

public class RegExpLiteral extends AstNode {
    private String flags;
    private String value;

    public RegExpLiteral() {
        this.type = 48;
    }

    public String getFlags() {
        return this.flags;
    }

    public String getValue() {
        return this.value;
    }

    public void setFlags(String str) {
        this.flags = str;
    }

    public void setValue(String str) {
        assertNotNull(str);
        this.value = str;
    }

    public String toSource(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(makeIndent(i));
        sb.append(MqttTopic.TOPIC_LEVEL_SEPARATOR);
        sb.append(this.value);
        sb.append(MqttTopic.TOPIC_LEVEL_SEPARATOR);
        String str = this.flags;
        if (str == null) {
            str = "";
        }
        sb.append(str);
        return sb.toString();
    }

    public void visit(NodeVisitor nodeVisitor) {
        nodeVisitor.visit(this);
    }

    public RegExpLiteral(int i) {
        super(i);
        this.type = 48;
    }

    public RegExpLiteral(int i, int i2) {
        super(i, i2);
        this.type = 48;
    }
}
