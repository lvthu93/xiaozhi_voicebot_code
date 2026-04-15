package com.grack.nanojson;

public final class JsonStringWriter extends JsonWriterBase<JsonStringWriter> {
    public JsonStringWriter(String str) {
        super((Appendable) new StringBuilder(), str);
    }

    public String done() {
        super.doneInternal();
        return this.appendable.toString();
    }
}
