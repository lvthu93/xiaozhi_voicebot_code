package org.schabi.newpipe.extractor.exceptions;

public class PrivateContentException extends ContentNotAvailableException {
    public PrivateContentException(String str) {
        super(str);
    }

    public PrivateContentException(String str, Throwable th) {
        super(str, th);
    }
}
