package com.google.android.exoplayer2.upstream;

import com.google.android.exoplayer2.util.Assertions;
import j$.io.DesugarInputStream;
import j$.io.InputStreamRetargetInterface;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class DataSourceInputStream extends InputStream implements InputStreamRetargetInterface {
    public final DataSource c;
    public final DataSpec f;
    public final byte[] g;
    public boolean h = false;
    public boolean i = false;
    public long j;

    public DataSourceInputStream(DataSource dataSource, DataSpec dataSpec) {
        this.c = dataSource;
        this.f = dataSpec;
        this.g = new byte[1];
    }

    public long bytesRead() {
        return this.j;
    }

    public void close() throws IOException {
        if (!this.i) {
            this.c.close();
            this.i = true;
        }
    }

    public void open() throws IOException {
        if (!this.h) {
            this.c.open(this.f);
            this.h = true;
        }
    }

    public int read() throws IOException {
        byte[] bArr = this.g;
        if (read(bArr) == -1) {
            return -1;
        }
        return bArr[0] & 255;
    }

    public final /* synthetic */ long transferTo(OutputStream outputStream) {
        return DesugarInputStream.transferTo(this, outputStream);
    }

    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }

    public int read(byte[] bArr, int i2, int i3) throws IOException {
        Assertions.checkState(!this.i);
        boolean z = this.h;
        DataSource dataSource = this.c;
        if (!z) {
            dataSource.open(this.f);
            this.h = true;
        }
        int read = dataSource.read(bArr, i2, i3);
        if (read == -1) {
            return -1;
        }
        this.j += (long) read;
        return read;
    }
}
