package org.schabi.newpipe.extractor.exceptions;

public class ReCaptchaException extends ExtractionException {
    public final String c;

    public ReCaptchaException(String str, String str2) {
        super(str);
        this.c = str2;
    }

    public String getUrl() {
        return this.c;
    }
}
