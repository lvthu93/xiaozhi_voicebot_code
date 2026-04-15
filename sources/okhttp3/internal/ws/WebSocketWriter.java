package okhttp3.internal.ws;

import defpackage.ck;
import java.io.IOException;
import java.util.Random;
import org.mozilla.javascript.Token;

final class WebSocketWriter {
    boolean activeWriter;
    final ck buffer = new ck();
    final FrameSink frameSink = new FrameSink();
    final boolean isClient;
    private final ck.b maskCursor;
    private final byte[] maskKey;
    final Random random;
    final cl sink;
    final ck sinkBuffer;
    boolean writerClosed;

    public final class FrameSink implements za {
        boolean closed;
        long contentLength;
        int formatOpcode;
        boolean isFirstFrame;

        public FrameSink() {
        }

        public void close() throws IOException {
            if (!this.closed) {
                WebSocketWriter webSocketWriter = WebSocketWriter.this;
                webSocketWriter.writeMessageFrame(this.formatOpcode, webSocketWriter.buffer.f, this.isFirstFrame, true);
                this.closed = true;
                WebSocketWriter.this.activeWriter = false;
                return;
            }
            throw new IOException("closed");
        }

        public void flush() throws IOException {
            if (!this.closed) {
                WebSocketWriter webSocketWriter = WebSocketWriter.this;
                webSocketWriter.writeMessageFrame(this.formatOpcode, webSocketWriter.buffer.f, this.isFirstFrame, false);
                this.isFirstFrame = false;
                return;
            }
            throw new IOException("closed");
        }

        public lc timeout() {
            return WebSocketWriter.this.sink.timeout();
        }

        public void write(ck ckVar, long j) throws IOException {
            boolean z;
            long e;
            if (!this.closed) {
                WebSocketWriter.this.buffer.write(ckVar, j);
                if (this.isFirstFrame) {
                    long j2 = this.contentLength;
                    if (j2 != -1 && WebSocketWriter.this.buffer.f > j2 - 8192) {
                        z = true;
                        e = WebSocketWriter.this.buffer.e();
                        if (e > 0 && !z) {
                            WebSocketWriter.this.writeMessageFrame(this.formatOpcode, e, this.isFirstFrame, false);
                            this.isFirstFrame = false;
                            return;
                        }
                        return;
                    }
                }
                z = false;
                e = WebSocketWriter.this.buffer.e();
                if (e > 0) {
                    return;
                }
                return;
            }
            throw new IOException("closed");
        }
    }

    public WebSocketWriter(boolean z, cl clVar, Random random2) {
        byte[] bArr;
        if (clVar == null) {
            throw new NullPointerException("sink == null");
        } else if (random2 != null) {
            this.isClient = z;
            this.sink = clVar;
            this.sinkBuffer = clVar.a();
            this.random = random2;
            ck.b bVar = null;
            if (z) {
                bArr = new byte[4];
            } else {
                bArr = null;
            }
            this.maskKey = bArr;
            this.maskCursor = z ? new ck.b() : bVar;
        } else {
            throw new NullPointerException("random == null");
        }
    }

    private void writeControlFrame(int i, cq cqVar) throws IOException {
        if (!this.writerClosed) {
            int t = cqVar.t();
            if (((long) t) <= 125) {
                this.sinkBuffer.ai(i | 128);
                if (this.isClient) {
                    this.sinkBuffer.ai(t | 128);
                    this.random.nextBytes(this.maskKey);
                    this.sinkBuffer.write(this.maskKey);
                    if (t > 0) {
                        ck ckVar = this.sinkBuffer;
                        long j = ckVar.f;
                        ckVar.ah(cqVar);
                        this.sinkBuffer.ab(this.maskCursor);
                        this.maskCursor.d(j);
                        WebSocketProtocol.toggleMask(this.maskCursor, this.maskKey);
                        this.maskCursor.close();
                    }
                } else {
                    this.sinkBuffer.ai(t);
                    this.sinkBuffer.ah(cqVar);
                }
                this.sink.flush();
                return;
            }
            throw new IllegalArgumentException("Payload size must be less than or equal to 125");
        }
        throw new IOException("closed");
    }

    public za newMessageSink(int i, long j) {
        if (!this.activeWriter) {
            this.activeWriter = true;
            FrameSink frameSink2 = this.frameSink;
            frameSink2.formatOpcode = i;
            frameSink2.contentLength = j;
            frameSink2.isFirstFrame = true;
            frameSink2.closed = false;
            return frameSink2;
        }
        throw new IllegalStateException("Another message writer is active. Did you call close()?");
    }

    public void writeClose(int i, cq cqVar) throws IOException {
        cq cqVar2 = cq.i;
        if (!(i == 0 && cqVar == null)) {
            if (i != 0) {
                WebSocketProtocol.validateCloseCode(i);
            }
            ck ckVar = new ck();
            ckVar.an(i);
            if (cqVar != null) {
                ckVar.ah(cqVar);
            }
            cqVar2 = ckVar.ac();
        }
        try {
            writeControlFrame(8, cqVar2);
        } finally {
            this.writerClosed = true;
        }
    }

    public void writeMessageFrame(int i, long j, boolean z, boolean z2) throws IOException {
        if (!this.writerClosed) {
            int i2 = 0;
            if (!z) {
                i = 0;
            }
            if (z2) {
                i |= 128;
            }
            this.sinkBuffer.ai(i);
            if (this.isClient) {
                i2 = 128;
            }
            if (j <= 125) {
                this.sinkBuffer.ai(((int) j) | i2);
            } else if (j <= 65535) {
                this.sinkBuffer.ai(i2 | Token.FINALLY);
                this.sinkBuffer.an((int) j);
            } else {
                this.sinkBuffer.ai(i2 | Token.VOID);
                this.sinkBuffer.am(j);
            }
            if (this.isClient) {
                this.random.nextBytes(this.maskKey);
                this.sinkBuffer.write(this.maskKey);
                if (j > 0) {
                    ck ckVar = this.sinkBuffer;
                    long j2 = ckVar.f;
                    ckVar.write(this.buffer, j);
                    this.sinkBuffer.ab(this.maskCursor);
                    this.maskCursor.d(j2);
                    WebSocketProtocol.toggleMask(this.maskCursor, this.maskKey);
                    this.maskCursor.close();
                }
            } else {
                this.sinkBuffer.write(this.buffer, j);
            }
            this.sink.c();
            return;
        }
        throw new IOException("closed");
    }

    public void writePing(cq cqVar) throws IOException {
        writeControlFrame(9, cqVar);
    }

    public void writePong(cq cqVar) throws IOException {
        writeControlFrame(10, cqVar);
    }
}
