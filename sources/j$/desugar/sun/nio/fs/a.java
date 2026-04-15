package j$.desugar.sun.nio.fs;

import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.channels.WritableByteChannel;

final class a extends FileChannel implements SeekableByteChannel {
    final FileChannel a;
    final boolean b = false;
    final boolean c = false;

    private a(FileChannel fileChannel) {
        this.a = fileChannel;
    }

    public static FileChannel k(FileChannel fileChannel) {
        return fileChannel instanceof a ? fileChannel : new a(fileChannel);
    }

    public final void force(boolean z) {
        this.a.force(z);
    }

    public final void implCloseChannel() {
        this.a.close();
        if (this.b) {
            throw null;
        }
    }

    public final FileLock lock(long j, long j2, boolean z) {
        FileLock lock = this.a.lock(j, j2, z);
        if (lock == null) {
            return null;
        }
        return new b(lock, this);
    }

    public final MappedByteBuffer map(FileChannel.MapMode mapMode, long j, long j2) {
        return this.a.map(mapMode, j, j2);
    }

    public final long position() {
        return this.a.position();
    }

    public final FileChannel position(long j) {
        return k(this.a.position(j));
    }

    public final int read(ByteBuffer byteBuffer) {
        return this.a.read(byteBuffer);
    }

    public final int read(ByteBuffer byteBuffer, long j) {
        return this.a.read(byteBuffer, j);
    }

    public final long read(ByteBuffer[] byteBufferArr, int i, int i2) {
        return this.a.read(byteBufferArr, i, i2);
    }

    public final long size() {
        return this.a.size();
    }

    public final long transferFrom(ReadableByteChannel readableByteChannel, long j, long j2) {
        return this.a.transferFrom(readableByteChannel, j, j2);
    }

    public final long transferTo(long j, long j2, WritableByteChannel writableByteChannel) {
        return this.a.transferTo(j, j2, writableByteChannel);
    }

    public final FileChannel truncate(long j) {
        return k(this.a.truncate(j));
    }

    public final FileLock tryLock(long j, long j2, boolean z) {
        FileLock tryLock = this.a.tryLock(j, j2, z);
        if (tryLock == null) {
            return null;
        }
        return new b(tryLock, this);
    }

    public final int write(ByteBuffer byteBuffer) {
        boolean z = this.c;
        FileChannel fileChannel = this.a;
        return z ? fileChannel.write(byteBuffer, size()) : fileChannel.write(byteBuffer);
    }

    public final int write(ByteBuffer byteBuffer, long j) {
        return this.a.write(byteBuffer, j);
    }

    public final long write(ByteBuffer[] byteBufferArr, int i, int i2) {
        return this.a.write(byteBufferArr, i, i2);
    }
}
