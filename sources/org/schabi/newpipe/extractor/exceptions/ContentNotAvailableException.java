package org.schabi.newpipe.extractor.exceptions;

public class ContentNotAvailableException extends ParsingException {
    public ContentNotAvailableException(String str) {
        super(str);
    }

    public ContentNotAvailableException(String str, Throwable th) {
        super(str, th);
    }
}
