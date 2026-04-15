package org.schabi.newpipe.extractor.exceptions;

public class ParsingException extends ExtractionException {
    public ParsingException(String str) {
        super(str);
    }

    public ParsingException(String str, Throwable th) {
        super(str, th);
    }
}
