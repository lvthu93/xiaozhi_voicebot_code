package j$.time.format;

import j$.time.DateTimeException;

public class DateTimeParseException extends DateTimeException {
    private static final long serialVersionUID = 4304633501674722597L;

    public DateTimeParseException(String str, CharSequence charSequence) {
        super(str);
        charSequence.toString();
    }

    public DateTimeParseException(String str, CharSequence charSequence, RuntimeException runtimeException) {
        super(str, runtimeException);
        charSequence.toString();
    }
}
