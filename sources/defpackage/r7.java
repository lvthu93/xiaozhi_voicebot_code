package defpackage;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import okio.a;

/* renamed from: r7  reason: default package */
public final class r7 extends a {
    public final /* synthetic */ Socket a;

    public r7(Socket socket) {
        this.a = socket;
    }

    public final IOException newTimeoutException(IOException iOException) {
        SocketTimeoutException socketTimeoutException = new SocketTimeoutException("timeout");
        if (iOException != null) {
            socketTimeoutException.initCause(iOException);
        }
        return socketTimeoutException;
    }

    public final void timedOut() {
        boolean z;
        Socket socket = this.a;
        try {
            socket.close();
        } catch (Exception e) {
            Logger logger = s7.a;
            Level level = Level.WARNING;
            logger.log(level, "Failed to close timed out socket " + socket, e);
        } catch (AssertionError e2) {
            if (e2.getCause() == null || e2.getMessage() == null || !e2.getMessage().contains("getsockname failed")) {
                z = false;
            } else {
                z = true;
            }
            if (z) {
                Logger logger2 = s7.a;
                Level level2 = Level.WARNING;
                logger2.log(level2, "Failed to close timed out socket " + socket, e2);
                return;
            }
            throw e2;
        }
    }
}
