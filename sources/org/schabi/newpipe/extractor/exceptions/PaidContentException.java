package org.schabi.newpipe.extractor.exceptions;

public class PaidContentException extends ContentNotAvailableException {
    public PaidContentException(String str) {
        super(str);
    }

    public PaidContentException(String str, Throwable th) {
        super(str, th);
    }
}
