package org.schabi.newpipe.extractor.localization;

import j$.time.Instant;
import j$.time.LocalDateTime;
import j$.time.OffsetDateTime;
import j$.time.ZoneId;
import j$.time.ZoneOffset;
import j$.time.format.DateTimeParseException;
import java.io.Serializable;
import org.schabi.newpipe.extractor.exceptions.ParsingException;

public class DateWrapper implements Serializable {
    public final Instant c;
    public final boolean f;

    public DateWrapper(OffsetDateTime offsetDateTime) {
        this(offsetDateTime, false);
    }

    public static DateWrapper fromInstant(String str) throws ParsingException {
        if (str == null) {
            return null;
        }
        try {
            return new DateWrapper(Instant.parse(str));
        } catch (DateTimeParseException e) {
            throw new ParsingException(y2.j("Could not parse date: \"", str, "\""), e);
        }
    }

    public static DateWrapper fromOffsetDateTime(String str) throws ParsingException {
        if (str == null) {
            return null;
        }
        try {
            return new DateWrapper(OffsetDateTime.parse(str));
        } catch (DateTimeParseException e) {
            throw new ParsingException(y2.j("Could not parse date: \"", str, "\""), e);
        }
    }

    public Instant getInstant() {
        return this.c;
    }

    public LocalDateTime getLocalDateTime() {
        return getLocalDateTime(ZoneId.systemDefault());
    }

    public boolean isApproximation() {
        return this.f;
    }

    public OffsetDateTime offsetDateTime() {
        return this.c.atOffset(ZoneOffset.UTC);
    }

    public String toString() {
        return "DateWrapper{instant=" + this.c + ", isApproximation=" + this.f + '}';
    }

    public DateWrapper(OffsetDateTime offsetDateTime, boolean z) {
        this(offsetDateTime.toInstant(), z);
    }

    public LocalDateTime getLocalDateTime(ZoneId zoneId) {
        return LocalDateTime.ofInstant(this.c, zoneId);
    }

    public DateWrapper(Instant instant) {
        this(instant, false);
    }

    public DateWrapper(Instant instant, boolean z) {
        this.c = instant;
        this.f = z;
    }

    public DateWrapper(LocalDateTime localDateTime, boolean z) {
        this(localDateTime.A(ZoneId.systemDefault()).toInstant(), z);
    }
}
