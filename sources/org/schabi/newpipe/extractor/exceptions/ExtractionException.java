package org.schabi.newpipe.extractor.exceptions;

public class ExtractionException extends Exception {
    public ExtractionException(String str) {
        super(str);
    }

    public ExtractionException(Throwable th) {
        super(th);
    }

    public ExtractionException(String str, Throwable th) {
        super(str, th);
    }
}
