package java.io;

import j$.util.Objects;

public class UncheckedIOException extends RuntimeException {
    public UncheckedIOException(IOException iOException) {
        super((Throwable) Objects.requireNonNull(iOException));
    }

    public IOException getCause() {
        return (IOException) super.getCause();
    }
}
