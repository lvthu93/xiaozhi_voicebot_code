package org.mozilla.javascript.ast;

public class ParseProblem {
    private int length;
    private String message;
    private int offset;
    private String sourceName;
    private Type type;

    public enum Type {
        Error,
        Warning
    }

    public ParseProblem(Type type2, String str, String str2, int i, int i2) {
        setType(type2);
        setMessage(str);
        setSourceName(str2);
        setFileOffset(i);
        setLength(i2);
    }

    public int getFileOffset() {
        return this.offset;
    }

    public int getLength() {
        return this.length;
    }

    public String getMessage() {
        return this.message;
    }

    public String getSourceName() {
        return this.sourceName;
    }

    public Type getType() {
        return this.type;
    }

    public void setFileOffset(int i) {
        this.offset = i;
    }

    public void setLength(int i) {
        this.length = i;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public void setSourceName(String str) {
        this.sourceName = str;
    }

    public void setType(Type type2) {
        this.type = type2;
    }

    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder(200);
        sb.append(this.sourceName);
        sb.append(":offset=");
        sb.append(this.offset);
        sb.append(",length=");
        sb.append(this.length);
        sb.append(",");
        if (this.type == Type.Error) {
            str = "error: ";
        } else {
            str = "warning: ";
        }
        sb.append(str);
        sb.append(this.message);
        return sb.toString();
    }
}
