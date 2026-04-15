package com.grack.nanojson;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.nio.charset.Charset;

public final class JsonWriter {

    public static final class JsonWriterContext {
        private final String indent;

        private JsonWriterContext(String str) {
            this.indent = str;
        }

        public JsonAppendableWriter on(OutputStream outputStream) {
            return new JsonAppendableWriter((Appendable) new OutputStreamWriter(outputStream, Charset.forName("UTF-8")), this.indent);
        }

        public JsonAppendableWriter on(PrintStream printStream) {
            return new JsonAppendableWriter((Appendable) printStream, this.indent);
        }

        public JsonAppendableWriter on(Appendable appendable) {
            return new JsonAppendableWriter(appendable, this.indent);
        }

        public JsonStringWriter string() {
            return new JsonStringWriter(this.indent);
        }
    }

    private JsonWriter() {
    }

    public static String escape(String str) {
        String string = string(str);
        return string.substring(1, string.length() - 1);
    }

    public static JsonWriterContext indent(String str) {
        if (str != null) {
            int i = 0;
            while (i < str.length()) {
                if (str.charAt(i) == ' ' || str.charAt(i) == 9) {
                    i++;
                } else {
                    throw new IllegalArgumentException("Only tabs and spaces are allowed for indent.");
                }
            }
            return new JsonWriterContext(str);
        }
        throw new IllegalArgumentException("indent must be non-null");
    }

    public static JsonAppendableWriter on(OutputStream outputStream) {
        return new JsonAppendableWriter(outputStream, (String) null);
    }

    public static JsonAppendableWriter on(PrintStream printStream) {
        return new JsonAppendableWriter((Appendable) printStream, (String) null);
    }

    public static JsonAppendableWriter on(Appendable appendable) {
        return new JsonAppendableWriter(appendable, (String) null);
    }

    public static JsonStringWriter string() {
        return new JsonStringWriter((String) null);
    }

    public static String string(Object obj) {
        return ((JsonStringWriter) new JsonStringWriter((String) null).value(obj)).done();
    }
}
