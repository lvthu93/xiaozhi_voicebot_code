package org.schabi.newpipe.extractor.exceptions;

public class UnsupportedContentInCountryException extends ContentNotAvailableException {
    public UnsupportedContentInCountryException(String str) {
        super(str);
    }

    public UnsupportedContentInCountryException(String str, Throwable th) {
        super(str, th);
    }
}
