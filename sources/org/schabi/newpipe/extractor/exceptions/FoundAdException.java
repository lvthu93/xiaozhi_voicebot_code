package org.schabi.newpipe.extractor.exceptions;

public class FoundAdException extends ParsingException {
    public FoundAdException(String str) {
        super(str);
    }

    public FoundAdException(String str, Throwable th) {
        super(str, th);
    }
}
