package org.schabi.newpipe.extractor.exceptions;

public class AccountTerminatedException extends ContentNotAvailableException {
    public final Reason c = Reason.UNKNOWN;

    public enum Reason {
        UNKNOWN,
        VIOLATION
    }

    public AccountTerminatedException(String str) {
        super(str);
    }

    public Reason getReason() {
        return this.c;
    }

    public AccountTerminatedException(String str, Reason reason) {
        super(str);
        this.c = reason;
    }

    public AccountTerminatedException(String str, Throwable th) {
        super(str, th);
    }
}
