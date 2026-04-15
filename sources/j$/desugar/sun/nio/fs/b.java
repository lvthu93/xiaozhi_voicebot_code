package j$.desugar.sun.nio.fs;

import java.nio.channels.FileLock;

final class b extends FileLock {
    private final FileLock a;

    b(FileLock fileLock, a aVar) {
        super(aVar, fileLock.position(), fileLock.size(), fileLock.isShared());
        this.a = fileLock;
    }

    public final boolean isValid() {
        return this.a.isValid();
    }

    public final void release() {
        this.a.release();
    }
}
