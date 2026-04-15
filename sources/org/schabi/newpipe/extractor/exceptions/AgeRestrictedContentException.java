package org.schabi.newpipe.extractor.exceptions;

public class AgeRestrictedContentException extends ContentNotAvailableException {
    public AgeRestrictedContentException(String str) {
        super(str);
    }

    public AgeRestrictedContentException(String str, Throwable th) {
        super(str, th);
    }
}
