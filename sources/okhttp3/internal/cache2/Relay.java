package okhttp3.internal.cache2;

import j$.nio.channels.DesugarChannels;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import okhttp3.internal.Util;

final class Relay {
    private static final long FILE_HEADER_SIZE = 32;
    static final cq PREFIX_CLEAN = cq.m("OkHttp cache v1\n");
    static final cq PREFIX_DIRTY = cq.m("OkHttp DIRTY :(\n");
    private static final int SOURCE_FILE = 2;
    private static final int SOURCE_UPSTREAM = 1;
    final ck buffer = new ck();
    final long bufferMaxSize;
    boolean complete;
    RandomAccessFile file;
    private final cq metadata;
    int sourceCount;
    jb upstream;
    final ck upstreamBuffer = new ck();
    long upstreamPos;
    Thread upstreamReader;

    public class RelaySource implements jb {
        private FileOperator fileOperator;
        private long sourcePos;
        private final lc timeout = new lc();

        public RelaySource() {
            this.fileOperator = new FileOperator(DesugarChannels.convertMaybeLegacyFileChannelFromLibrary(Relay.this.file.getChannel()));
        }

        public void close() throws IOException {
            if (this.fileOperator != null) {
                RandomAccessFile randomAccessFile = null;
                this.fileOperator = null;
                synchronized (Relay.this) {
                    Relay relay = Relay.this;
                    int i = relay.sourceCount - 1;
                    relay.sourceCount = i;
                    if (i == 0) {
                        RandomAccessFile randomAccessFile2 = relay.file;
                        relay.file = null;
                        randomAccessFile = randomAccessFile2;
                    }
                }
                if (randomAccessFile != null) {
                    Util.closeQuietly((Closeable) randomAccessFile);
                }
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:16:0x002f, code lost:
            r0 = 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x0031, code lost:
            r12 = r7 - r0.buffer.f;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x0039, code lost:
            if (r5 >= r12) goto L_0x00ea;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x003c, code lost:
            r0 = 2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:74:?, code lost:
            r2 = java.lang.Math.min(r2, r7 - r5);
            r1.this$0.buffer.h(r1.sourcePos - r12, r22, r2);
            r1.sourcePos += r2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:76:0x0104, code lost:
            return r2;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public long read(defpackage.ck r22, long r23) throws java.io.IOException {
            /*
                r21 = this;
                r1 = r21
                r2 = r23
                okhttp3.internal.cache2.FileOperator r0 = r1.fileOperator
                if (r0 == 0) goto L_0x0108
                okhttp3.internal.cache2.Relay r4 = okhttp3.internal.cache2.Relay.this
                monitor-enter(r4)
            L_0x000b:
                long r5 = r1.sourcePos     // Catch:{ all -> 0x0105 }
                okhttp3.internal.cache2.Relay r0 = okhttp3.internal.cache2.Relay.this     // Catch:{ all -> 0x0105 }
                long r7 = r0.upstreamPos     // Catch:{ all -> 0x0105 }
                r9 = 2
                r10 = -1
                int r12 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
                if (r12 != 0) goto L_0x0031
                boolean r5 = r0.complete     // Catch:{ all -> 0x0105 }
                if (r5 == 0) goto L_0x001e
                monitor-exit(r4)     // Catch:{ all -> 0x0105 }
                return r10
            L_0x001e:
                java.lang.Thread r5 = r0.upstreamReader     // Catch:{ all -> 0x0105 }
                if (r5 == 0) goto L_0x0028
                lc r5 = r1.timeout     // Catch:{ all -> 0x0105 }
                r5.waitUntilNotified(r0)     // Catch:{ all -> 0x0105 }
                goto L_0x000b
            L_0x0028:
                java.lang.Thread r5 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x0105 }
                r0.upstreamReader = r5     // Catch:{ all -> 0x0105 }
                monitor-exit(r4)     // Catch:{ all -> 0x0105 }
                r0 = 1
                goto L_0x003d
            L_0x0031:
                ck r0 = r0.buffer     // Catch:{ all -> 0x0105 }
                long r12 = r0.f     // Catch:{ all -> 0x0105 }
                long r12 = r7 - r12
                int r0 = (r5 > r12 ? 1 : (r5 == r12 ? 0 : -1))
                if (r0 >= 0) goto L_0x00ea
                monitor-exit(r4)     // Catch:{ all -> 0x0105 }
                r0 = 2
            L_0x003d:
                r4 = 32
                if (r0 != r9) goto L_0x005a
                long r9 = r1.sourcePos
                long r7 = r7 - r9
                long r2 = java.lang.Math.min(r2, r7)
                okhttp3.internal.cache2.FileOperator r9 = r1.fileOperator
                long r6 = r1.sourcePos
                long r10 = r6 + r4
                r12 = r22
                r13 = r2
                r9.read(r10, r12, r13)
                long r4 = r1.sourcePos
                long r4 = r4 + r2
                r1.sourcePos = r4
                return r2
            L_0x005a:
                r6 = 0
                okhttp3.internal.cache2.Relay r0 = okhttp3.internal.cache2.Relay.this     // Catch:{ all -> 0x00da }
                jb r9 = r0.upstream     // Catch:{ all -> 0x00da }
                ck r12 = r0.upstreamBuffer     // Catch:{ all -> 0x00da }
                long r13 = r0.bufferMaxSize     // Catch:{ all -> 0x00da }
                long r12 = r9.read(r12, r13)     // Catch:{ all -> 0x00da }
                int r0 = (r12 > r10 ? 1 : (r12 == r10 ? 0 : -1))
                if (r0 != 0) goto L_0x007f
                okhttp3.internal.cache2.Relay r0 = okhttp3.internal.cache2.Relay.this     // Catch:{ all -> 0x00da }
                r0.commit(r7)     // Catch:{ all -> 0x00da }
                okhttp3.internal.cache2.Relay r2 = okhttp3.internal.cache2.Relay.this
                monitor-enter(r2)
                okhttp3.internal.cache2.Relay r0 = okhttp3.internal.cache2.Relay.this     // Catch:{ all -> 0x007c }
                r0.upstreamReader = r6     // Catch:{ all -> 0x007c }
                r0.notifyAll()     // Catch:{ all -> 0x007c }
                monitor-exit(r2)     // Catch:{ all -> 0x007c }
                return r10
            L_0x007c:
                r0 = move-exception
                monitor-exit(r2)     // Catch:{ all -> 0x007c }
                throw r0
            L_0x007f:
                long r2 = java.lang.Math.min(r12, r2)     // Catch:{ all -> 0x00da }
                okhttp3.internal.cache2.Relay r0 = okhttp3.internal.cache2.Relay.this     // Catch:{ all -> 0x00da }
                ck r14 = r0.upstreamBuffer     // Catch:{ all -> 0x00da }
                r15 = 0
                r17 = r22
                r18 = r2
                r14.h(r15, r17, r18)     // Catch:{ all -> 0x00da }
                long r9 = r1.sourcePos     // Catch:{ all -> 0x00da }
                long r9 = r9 + r2
                r1.sourcePos = r9     // Catch:{ all -> 0x00da }
                okhttp3.internal.cache2.FileOperator r15 = r1.fileOperator     // Catch:{ all -> 0x00da }
                long r16 = r7 + r4
                okhttp3.internal.cache2.Relay r0 = okhttp3.internal.cache2.Relay.this     // Catch:{ all -> 0x00da }
                ck r0 = r0.upstreamBuffer     // Catch:{ all -> 0x00da }
                ck r18 = r0.clone()     // Catch:{ all -> 0x00da }
                r19 = r12
                r15.write(r16, r18, r19)     // Catch:{ all -> 0x00da }
                okhttp3.internal.cache2.Relay r4 = okhttp3.internal.cache2.Relay.this     // Catch:{ all -> 0x00da }
                monitor-enter(r4)     // Catch:{ all -> 0x00da }
                okhttp3.internal.cache2.Relay r0 = okhttp3.internal.cache2.Relay.this     // Catch:{ all -> 0x00d7 }
                ck r5 = r0.buffer     // Catch:{ all -> 0x00d7 }
                ck r0 = r0.upstreamBuffer     // Catch:{ all -> 0x00d7 }
                r5.write(r0, r12)     // Catch:{ all -> 0x00d7 }
                okhttp3.internal.cache2.Relay r0 = okhttp3.internal.cache2.Relay.this     // Catch:{ all -> 0x00d7 }
                ck r5 = r0.buffer     // Catch:{ all -> 0x00d7 }
                long r7 = r5.f     // Catch:{ all -> 0x00d7 }
                long r9 = r0.bufferMaxSize     // Catch:{ all -> 0x00d7 }
                int r0 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
                if (r0 <= 0) goto L_0x00c2
                long r7 = r7 - r9
                r5.skip(r7)     // Catch:{ all -> 0x00d7 }
            L_0x00c2:
                okhttp3.internal.cache2.Relay r5 = okhttp3.internal.cache2.Relay.this     // Catch:{ all -> 0x00d7 }
                long r7 = r5.upstreamPos     // Catch:{ all -> 0x00d7 }
                long r7 = r7 + r12
                r5.upstreamPos = r7     // Catch:{ all -> 0x00d7 }
                monitor-exit(r4)     // Catch:{ all -> 0x00d7 }
                monitor-enter(r5)
                okhttp3.internal.cache2.Relay r0 = okhttp3.internal.cache2.Relay.this     // Catch:{ all -> 0x00d4 }
                r0.upstreamReader = r6     // Catch:{ all -> 0x00d4 }
                r0.notifyAll()     // Catch:{ all -> 0x00d4 }
                monitor-exit(r5)     // Catch:{ all -> 0x00d4 }
                return r2
            L_0x00d4:
                r0 = move-exception
                monitor-exit(r5)     // Catch:{ all -> 0x00d4 }
                throw r0
            L_0x00d7:
                r0 = move-exception
                monitor-exit(r4)     // Catch:{ all -> 0x00d7 }
                throw r0     // Catch:{ all -> 0x00da }
            L_0x00da:
                r0 = move-exception
                okhttp3.internal.cache2.Relay r2 = okhttp3.internal.cache2.Relay.this
                monitor-enter(r2)
                okhttp3.internal.cache2.Relay r3 = okhttp3.internal.cache2.Relay.this     // Catch:{ all -> 0x00e7 }
                r3.upstreamReader = r6     // Catch:{ all -> 0x00e7 }
                r3.notifyAll()     // Catch:{ all -> 0x00e7 }
                monitor-exit(r2)     // Catch:{ all -> 0x00e7 }
                throw r0
            L_0x00e7:
                r0 = move-exception
                monitor-exit(r2)     // Catch:{ all -> 0x00e7 }
                throw r0
            L_0x00ea:
                long r7 = r7 - r5
                long r2 = java.lang.Math.min(r2, r7)     // Catch:{ all -> 0x0105 }
                okhttp3.internal.cache2.Relay r0 = okhttp3.internal.cache2.Relay.this     // Catch:{ all -> 0x0105 }
                ck r14 = r0.buffer     // Catch:{ all -> 0x0105 }
                long r5 = r1.sourcePos     // Catch:{ all -> 0x0105 }
                long r15 = r5 - r12
                r17 = r22
                r18 = r2
                r14.h(r15, r17, r18)     // Catch:{ all -> 0x0105 }
                long r5 = r1.sourcePos     // Catch:{ all -> 0x0105 }
                long r5 = r5 + r2
                r1.sourcePos = r5     // Catch:{ all -> 0x0105 }
                monitor-exit(r4)     // Catch:{ all -> 0x0105 }
                return r2
            L_0x0105:
                r0 = move-exception
                monitor-exit(r4)     // Catch:{ all -> 0x0105 }
                throw r0
            L_0x0108:
                java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                java.lang.String r2 = "closed"
                r0.<init>(r2)
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.cache2.Relay.RelaySource.read(ck, long):long");
        }

        public lc timeout() {
            return this.timeout;
        }
    }

    private Relay(RandomAccessFile randomAccessFile, jb jbVar, long j, cq cqVar, long j2) {
        boolean z;
        this.file = randomAccessFile;
        this.upstream = jbVar;
        if (jbVar == null) {
            z = true;
        } else {
            z = false;
        }
        this.complete = z;
        this.upstreamPos = j;
        this.metadata = cqVar;
        this.bufferMaxSize = j2;
    }

    public static Relay edit(File file2, jb jbVar, cq cqVar, long j) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file2, "rw");
        Relay relay = new Relay(randomAccessFile, jbVar, 0, cqVar, j);
        randomAccessFile.setLength(0);
        relay.writeHeader(PREFIX_DIRTY, -1, -1);
        return relay;
    }

    public static Relay read(File file2) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file2, "rw");
        FileOperator fileOperator = new FileOperator(DesugarChannels.convertMaybeLegacyFileChannelFromLibrary(randomAccessFile.getChannel()));
        ck ckVar = new ck();
        fileOperator.read(0, ckVar, FILE_HEADER_SIZE);
        cq cqVar = PREFIX_CLEAN;
        if (ckVar.b((long) cqVar.t()).equals(cqVar)) {
            long readLong = ckVar.readLong();
            long readLong2 = ckVar.readLong();
            ck ckVar2 = new ck();
            fileOperator.read(readLong + FILE_HEADER_SIZE, ckVar2, readLong2);
            return new Relay(randomAccessFile, (jb) null, readLong, ckVar2.ac(), 0);
        }
        throw new IOException("unreadable cache file");
    }

    private void writeHeader(cq cqVar, long j, long j2) throws IOException {
        ck ckVar = new ck();
        ckVar.ah(cqVar);
        ckVar.am(j);
        ckVar.am(j2);
        if (ckVar.f == FILE_HEADER_SIZE) {
            new FileOperator(DesugarChannels.convertMaybeLegacyFileChannelFromLibrary(this.file.getChannel())).write(0, ckVar, FILE_HEADER_SIZE);
            return;
        }
        throw new IllegalArgumentException();
    }

    private void writeMetadata(long j) throws IOException {
        ck ckVar = new ck();
        ckVar.ah(this.metadata);
        new FileOperator(DesugarChannels.convertMaybeLegacyFileChannelFromLibrary(this.file.getChannel())).write(FILE_HEADER_SIZE + j, ckVar, (long) this.metadata.t());
    }

    public void commit(long j) throws IOException {
        writeMetadata(j);
        DesugarChannels.convertMaybeLegacyFileChannelFromLibrary(this.file.getChannel()).force(false);
        writeHeader(PREFIX_CLEAN, j, (long) this.metadata.t());
        DesugarChannels.convertMaybeLegacyFileChannelFromLibrary(this.file.getChannel()).force(false);
        synchronized (this) {
            this.complete = true;
        }
        Util.closeQuietly((Closeable) this.upstream);
        this.upstream = null;
    }

    public boolean isClosed() {
        return this.file == null;
    }

    public cq metadata() {
        return this.metadata;
    }

    public jb newSource() {
        synchronized (this) {
            if (this.file == null) {
                return null;
            }
            this.sourceCount++;
            return new RelaySource();
        }
    }
}
