package fi.iki.elonen.util;

import fi.iki.elonen.NanoHTTPD;
import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerRunner {
    private static final Logger LOG = Logger.getLogger(ServerRunner.class.getName());

    public static void executeInstance(NanoHTTPD nanoHTTPD) {
        try {
            nanoHTTPD.start(5000, false);
        } catch (IOException e) {
            PrintStream printStream = System.err;
            e.toString();
            printStream.getClass();
            System.exit(-1);
        }
        System.out.getClass();
        try {
            System.in.read();
        } catch (Throwable unused) {
        }
        nanoHTTPD.stop();
        System.out.getClass();
    }

    public static <T extends NanoHTTPD> void run(Class<T> cls) {
        try {
            executeInstance((NanoHTTPD) cls.newInstance());
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Cound nor create server", e);
        }
    }
}
