package com.google.android.exoplayer2.upstream;

import android.net.Uri;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Map;

public final class FileDataSource extends BaseDataSource {
    @Nullable
    public RandomAccessFile e;
    @Nullable
    public Uri f;
    public long g;
    public boolean h;

    public static final class Factory implements DataSource.Factory {
        @Nullable
        public TransferListener a;

        public Factory setListener(@Nullable TransferListener transferListener) {
            this.a = transferListener;
            return this;
        }

        public FileDataSource createDataSource() {
            FileDataSource fileDataSource = new FileDataSource();
            TransferListener transferListener = this.a;
            if (transferListener != null) {
                fileDataSource.addTransferListener(transferListener);
            }
            return fileDataSource;
        }
    }

    public static class FileDataSourceException extends IOException {
        public FileDataSourceException(IOException iOException) {
            super(iOException);
        }

        public FileDataSourceException(String str, IOException iOException) {
            super(str, iOException);
        }
    }

    public FileDataSource() {
        super(false);
    }

    public static RandomAccessFile e(Uri uri) throws FileDataSourceException {
        try {
            return new RandomAccessFile((String) Assertions.checkNotNull(uri.getPath()), "r");
        } catch (FileNotFoundException e2) {
            if (!TextUtils.isEmpty(uri.getQuery()) || !TextUtils.isEmpty(uri.getFragment())) {
                throw new FileDataSourceException(String.format("uri has query and/or fragment, which are not supported. Did you call Uri.parse() on a string containing '?' or '#'? Use Uri.fromFile(new File(path)) to avoid this. path=%s,query=%s,fragment=%s", new Object[]{uri.getPath(), uri.getQuery(), uri.getFragment()}), e2);
            }
            throw new FileDataSourceException(e2);
        }
    }

    public void close() throws FileDataSourceException {
        this.f = null;
        try {
            RandomAccessFile randomAccessFile = this.e;
            if (randomAccessFile != null) {
                randomAccessFile.close();
            }
            this.e = null;
            if (this.h) {
                this.h = false;
                b();
            }
        } catch (IOException e2) {
            throw new FileDataSourceException(e2);
        } catch (Throwable th) {
            this.e = null;
            if (this.h) {
                this.h = false;
                b();
            }
            throw th;
        }
    }

    public /* bridge */ /* synthetic */ Map getResponseHeaders() {
        return y0.a(this);
    }

    @Nullable
    public Uri getUri() {
        return this.f;
    }

    public long open(DataSpec dataSpec) throws FileDataSourceException {
        try {
            Uri uri = dataSpec.a;
            long j = dataSpec.f;
            this.f = uri;
            c(dataSpec);
            RandomAccessFile e2 = e(uri);
            this.e = e2;
            e2.seek(j);
            long j2 = dataSpec.g;
            if (j2 == -1) {
                j2 = this.e.length() - j;
            }
            this.g = j2;
            if (j2 >= 0) {
                this.h = true;
                d(dataSpec);
                return this.g;
            }
            throw new DataSourceException(0);
        } catch (IOException e3) {
            throw new FileDataSourceException(e3);
        }
    }

    public int read(byte[] bArr, int i, int i2) throws FileDataSourceException {
        if (i2 == 0) {
            return 0;
        }
        if (this.g == 0) {
            return -1;
        }
        try {
            int read = ((RandomAccessFile) Util.castNonNull(this.e)).read(bArr, i, (int) Math.min(this.g, (long) i2));
            if (read > 0) {
                this.g -= (long) read;
                a(read);
            }
            return read;
        } catch (IOException e2) {
            throw new FileDataSourceException(e2);
        }
    }
}
