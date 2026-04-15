package org.schabi.newpipe.extractor.exceptions;

public class ContentNotSupportedException extends ParsingException {
    public ContentNotSupportedException(String str) {
        super(str);
    }

    public ContentNotSupportedException(String str, Throwable th) {
        super(str, th);
    }
}
