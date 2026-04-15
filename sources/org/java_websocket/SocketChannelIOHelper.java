package org.java_websocket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;

public class SocketChannelIOHelper {
    private SocketChannelIOHelper() {
        throw new IllegalStateException("Utility class");
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x0070 A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:33:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean batch(org.java_websocket.WebSocketImpl r4, java.nio.channels.ByteChannel r5) throws java.io.IOException {
        /*
            r0 = 0
            if (r4 != 0) goto L_0x0004
            return r0
        L_0x0004:
            java.util.concurrent.BlockingQueue<java.nio.ByteBuffer> r1 = r4.outQueue
            java.lang.Object r1 = r1.peek()
            java.nio.ByteBuffer r1 = (java.nio.ByteBuffer) r1
            if (r1 != 0) goto L_0x001f
            boolean r1 = r5 instanceof org.java_websocket.WrappedByteChannel
            if (r1 == 0) goto L_0x0038
            r1 = r5
            org.java_websocket.WrappedByteChannel r1 = (org.java_websocket.WrappedByteChannel) r1
            boolean r2 = r1.isNeedWrite()
            if (r2 == 0) goto L_0x0039
            r1.writeMore()
            goto L_0x0039
        L_0x001f:
            r5.write(r1)
            int r1 = r1.remaining()
            if (r1 <= 0) goto L_0x0029
            return r0
        L_0x0029:
            java.util.concurrent.BlockingQueue<java.nio.ByteBuffer> r1 = r4.outQueue
            r1.poll()
            java.util.concurrent.BlockingQueue<java.nio.ByteBuffer> r1 = r4.outQueue
            java.lang.Object r1 = r1.peek()
            java.nio.ByteBuffer r1 = (java.nio.ByteBuffer) r1
            if (r1 != 0) goto L_0x001f
        L_0x0038:
            r1 = 0
        L_0x0039:
            java.util.concurrent.BlockingQueue<java.nio.ByteBuffer> r2 = r4.outQueue
            boolean r2 = r2.isEmpty()
            if (r2 == 0) goto L_0x0066
            boolean r2 = r4.isFlushAndClose()
            if (r2 == 0) goto L_0x0066
            org.java_websocket.drafts.Draft r2 = r4.getDraft()
            if (r2 == 0) goto L_0x0066
            org.java_websocket.drafts.Draft r2 = r4.getDraft()
            org.java_websocket.enums.Role r2 = r2.getRole()
            if (r2 == 0) goto L_0x0066
            org.java_websocket.drafts.Draft r2 = r4.getDraft()
            org.java_websocket.enums.Role r2 = r2.getRole()
            org.java_websocket.enums.Role r3 = org.java_websocket.enums.Role.SERVER
            if (r2 != r3) goto L_0x0066
            r4.closeConnection()
        L_0x0066:
            if (r1 == 0) goto L_0x0070
            org.java_websocket.WrappedByteChannel r5 = (org.java_websocket.WrappedByteChannel) r5
            boolean r4 = r5.isNeedWrite()
            if (r4 != 0) goto L_0x0071
        L_0x0070:
            r0 = 1
        L_0x0071:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.java_websocket.SocketChannelIOHelper.batch(org.java_websocket.WebSocketImpl, java.nio.channels.ByteChannel):boolean");
    }

    public static boolean read(ByteBuffer byteBuffer, WebSocketImpl webSocketImpl, ByteChannel byteChannel) throws IOException {
        byteBuffer.clear();
        int read = byteChannel.read(byteBuffer);
        byteBuffer.flip();
        if (read == -1) {
            webSocketImpl.eot();
            return false;
        } else if (read != 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean readMore(ByteBuffer byteBuffer, WebSocketImpl webSocketImpl, WrappedByteChannel wrappedByteChannel) throws IOException {
        byteBuffer.clear();
        int readMore = wrappedByteChannel.readMore(byteBuffer);
        byteBuffer.flip();
        if (readMore != -1) {
            return wrappedByteChannel.isNeedRead();
        }
        webSocketImpl.eot();
        return false;
    }
}
