package org.schabi.newpipe.extractor.exceptions;

public class GeographicRestrictionException extends ContentNotAvailableException {
    public GeographicRestrictionException(String str) {
        super(str);
    }

    public GeographicRestrictionException(String str, Throwable th) {
        super(str, th);
    }
}
