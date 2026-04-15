package com.google.protobuf;

import java.io.IOException;

public class q extends IOException {
    public static final /* synthetic */ int f = 0;
    private static final long serialVersionUID = -1616151763072450476L;
    public boolean c;

    public static class a extends q {
        private static final long serialVersionUID = 3283890091615336259L;

        public a() {
            super("Protocol message tag had invalid wire type.");
        }
    }

    public q() {
        throw null;
    }

    public q(String str) {
        super(str);
    }

    public static q a() {
        return new q("Protocol message end-group tag did not match expected tag.");
    }

    public static q b() {
        return new q("Protocol message contained an invalid tag (zero).");
    }

    public static q c() {
        return new q("Protocol message had invalid UTF-8.");
    }

    public static a d() {
        return new a();
    }

    public static q e() {
        return new q("CodedInputStream encountered a malformed varint.");
    }

    public static q f() {
        return new q("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
    }

    public static q g() {
        return new q("Failed to parse the message.");
    }

    public static q h() {
        return new q("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either that the input has been truncated or that an embedded message misreported its own length.");
    }

    public q(IOException iOException) {
        super(iOException.getMessage(), iOException);
    }
}
