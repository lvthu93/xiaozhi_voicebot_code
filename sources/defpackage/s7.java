package defpackage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Logger;

/* renamed from: s7  reason: default package */
public final class s7 {
    public static final Logger a = Logger.getLogger(s7.class.getName());

    public static za a(Socket socket) throws IOException {
        if (socket == null) {
            throw new IllegalArgumentException("socket == null");
        } else if (socket.getOutputStream() != null) {
            r7 r7Var = new r7(socket);
            OutputStream outputStream = socket.getOutputStream();
            if (outputStream != null) {
                return r7Var.sink(new o7(outputStream, r7Var));
            }
            throw new IllegalArgumentException("out == null");
        } else {
            throw new IOException("socket's output stream == null");
        }
    }

    public static jb b(Socket socket) throws IOException {
        if (socket == null) {
            throw new IllegalArgumentException("socket == null");
        } else if (socket.getInputStream() != null) {
            r7 r7Var = new r7(socket);
            InputStream inputStream = socket.getInputStream();
            if (inputStream != null) {
                return r7Var.source(new p7(inputStream, r7Var));
            }
            throw new IllegalArgumentException("in == null");
        } else {
            throw new IOException("socket's input stream == null");
        }
    }
}
