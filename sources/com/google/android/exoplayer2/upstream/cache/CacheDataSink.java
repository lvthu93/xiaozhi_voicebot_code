package com.google.android.exoplayer2.upstream.cache;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.upstream.DataSink;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ReusableBufferedOutputStream;
import com.google.android.exoplayer2.util.Util;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public final class CacheDataSink implements DataSink {
    public final Cache a;
    public final long b;
    public final int c;
    @Nullable
    public DataSpec d;
    public long e;
    @Nullable
    public File f;
    @Nullable
    public OutputStream g;
    public long h;
    public long i;
    public ReusableBufferedOutputStream j;

    public static final class CacheDataSinkException extends Cache.CacheException {
        public CacheDataSinkException(IOException iOException) {
            super((Throwable) iOException);
        }
    }

    public static final class Factory implements DataSink.Factory {
        public Cache a;
        public long b = 5242880;
        public int c = 20480;

        public DataSink createDataSink() {
            return new CacheDataSink((Cache) Assertions.checkNotNull(this.a), this.b, this.c);
        }

        public Factory setBufferSize(int i) {
            this.c = i;
            return this;
        }

        public Factory setCache(Cache cache) {
            this.a = cache;
            return this;
        }

        public Factory setFragmentSize(long j) {
            this.b = j;
            return this;
        }
    }

    public CacheDataSink(Cache cache, long j2) {
        this(cache, j2, 20480);
    }

    public final void a() throws IOException {
        OutputStream outputStream = this.g;
        if (outputStream != null) {
            try {
                outputStream.flush();
                Util.closeQuietly((Closeable) this.g);
                this.g = null;
                this.f = null;
                this.a.commitFile((File) Util.castNonNull(this.f), this.h);
            } catch (Throwable th) {
                Util.closeQuietly((Closeable) this.g);
                this.g = null;
                this.f = null;
                ((File) Util.castNonNull(this.f)).delete();
                throw th;
            }
        }
    }

    public final void b(DataSpec dataSpec) throws IOException {
        long j2 = dataSpec.g;
        long j3 = -1;
        if (j2 != -1) {
            j3 = Math.min(j2 - this.i, this.e);
        }
        this.f = this.a.startFile((String) Util.castNonNull(dataSpec.h), dataSpec.f + this.i, j3);
        FileOutputStream fileOutputStream = new FileOutputStream(this.f);
        int i2 = this.c;
        if (i2 > 0) {
            ReusableBufferedOutputStream reusableBufferedOutputStream = this.j;
            if (reusableBufferedOutputStream == null) {
                this.j = new ReusableBufferedOutputStream(fileOutputStream, i2);
            } else {
                reusableBufferedOutputStream.reset(fileOutputStream);
            }
            this.g = this.j;
        } else {
            this.g = fileOutputStream;
        }
        this.h = 0;
    }

    public void close() throws CacheDataSinkException {
        if (this.d != null) {
            try {
                a();
            } catch (IOException e2) {
                throw new CacheDataSinkException(e2);
            }
        }
    }

    public void open(DataSpec dataSpec) throws CacheDataSinkException {
        long j2;
        Assertions.checkNotNull(dataSpec.h);
        if (dataSpec.g != -1 || !dataSpec.isFlagSet(2)) {
            this.d = dataSpec;
            if (dataSpec.isFlagSet(4)) {
                j2 = this.b;
            } else {
                j2 = Long.MAX_VALUE;
            }
            this.e = j2;
            this.i = 0;
            try {
                b(dataSpec);
            } catch (IOException e2) {
                throw new CacheDataSinkException(e2);
            }
        } else {
            this.d = null;
        }
    }

    public void write(byte[] bArr, int i2, int i3) throws CacheDataSinkException {
        DataSpec dataSpec = this.d;
        if (dataSpec != null) {
            int i4 = 0;
            while (i4 < i3) {
                try {
                    if (this.h == this.e) {
                        a();
                        b(dataSpec);
                    }
                    int min = (int) Math.min((long) (i3 - i4), this.e - this.h);
                    ((OutputStream) Util.castNonNull(this.g)).write(bArr, i2 + i4, min);
                    i4 += min;
                    long j2 = (long) min;
                    this.h += j2;
                    this.i += j2;
                } catch (IOException e2) {
                    throw new CacheDataSinkException(e2);
                }
            }
        }
    }

    public CacheDataSink(Cache cache, long j2, int i2) {
        Assertions.checkState(j2 > 0 || j2 == -1, "fragmentSize must be positive or C.LENGTH_UNSET.");
        int i3 = (j2 > -1 ? 1 : (j2 == -1 ? 0 : -1));
        if (i3 != 0 && j2 < 2097152) {
            Log.w("CacheDataSink", "fragmentSize is below the minimum recommended value of 2097152. This may cause poor cache performance.");
        }
        this.a = (Cache) Assertions.checkNotNull(cache);
        this.b = i3 == 0 ? Long.MAX_VALUE : j2;
        this.c = i2;
    }
}
