package com.google.android.exoplayer2.upstream;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Util;
import j$.nio.channels.DesugarChannels;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Map;

public final class ContentDataSource extends BaseDataSource {
    public final ContentResolver e;
    @Nullable
    public Uri f;
    @Nullable
    public AssetFileDescriptor g;
    @Nullable
    public FileInputStream h;
    public long i;
    public boolean j;

    public static class ContentDataSourceException extends IOException {
        public ContentDataSourceException(IOException iOException) {
            super(iOException);
        }
    }

    public ContentDataSource(Context context) {
        super(false);
        this.e = context.getContentResolver();
    }

    public void close() throws ContentDataSourceException {
        this.f = null;
        try {
            FileInputStream fileInputStream = this.h;
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            this.h = null;
            try {
                AssetFileDescriptor assetFileDescriptor = this.g;
                if (assetFileDescriptor != null) {
                    assetFileDescriptor.close();
                }
                this.g = null;
                if (this.j) {
                    this.j = false;
                    b();
                }
            } catch (IOException e2) {
                throw new ContentDataSourceException(e2);
            } catch (Throwable th) {
                this.g = null;
                if (this.j) {
                    this.j = false;
                    b();
                }
                throw th;
            }
        } catch (IOException e3) {
            throw new ContentDataSourceException(e3);
        } catch (Throwable th2) {
            this.h = null;
            try {
                AssetFileDescriptor assetFileDescriptor2 = this.g;
                if (assetFileDescriptor2 != null) {
                    assetFileDescriptor2.close();
                }
                this.g = null;
                if (this.j) {
                    this.j = false;
                    b();
                }
                throw th2;
            } catch (IOException e4) {
                throw new ContentDataSourceException(e4);
            } catch (Throwable th3) {
                this.g = null;
                if (this.j) {
                    this.j = false;
                    b();
                }
                throw th3;
            }
        }
    }

    public /* bridge */ /* synthetic */ Map getResponseHeaders() {
        return y0.a(this);
    }

    @Nullable
    public Uri getUri() {
        return this.f;
    }

    public long open(DataSpec dataSpec) throws ContentDataSourceException {
        long j2;
        try {
            Uri uri = dataSpec.a;
            this.f = uri;
            c(dataSpec);
            AssetFileDescriptor openAssetFileDescriptor = this.e.openAssetFileDescriptor(uri, "r");
            this.g = openAssetFileDescriptor;
            if (openAssetFileDescriptor != null) {
                long length = openAssetFileDescriptor.getLength();
                FileInputStream fileInputStream = new FileInputStream(openAssetFileDescriptor.getFileDescriptor());
                this.h = fileInputStream;
                long j3 = dataSpec.f;
                int i2 = (length > -1 ? 1 : (length == -1 ? 0 : -1));
                if (i2 == 0 || j3 <= length) {
                    long startOffset = openAssetFileDescriptor.getStartOffset();
                    long skip = fileInputStream.skip(startOffset + j3) - startOffset;
                    if (skip == j3) {
                        if (i2 == 0) {
                            FileChannel convertMaybeLegacyFileChannelFromLibrary = DesugarChannels.convertMaybeLegacyFileChannelFromLibrary(fileInputStream.getChannel());
                            long size = convertMaybeLegacyFileChannelFromLibrary.size();
                            if (size == 0) {
                                this.i = -1;
                            } else {
                                long position = size - convertMaybeLegacyFileChannelFromLibrary.position();
                                this.i = position;
                                if (position < 0) {
                                    throw new DataSourceException(0);
                                }
                            }
                        } else {
                            long j4 = length - skip;
                            this.i = j4;
                            if (j4 < 0) {
                                throw new DataSourceException(0);
                            }
                        }
                        long j5 = dataSpec.g;
                        if (j5 != -1) {
                            long j6 = this.i;
                            if (j6 == -1) {
                                j2 = j5;
                            } else {
                                j2 = Math.min(j6, j5);
                            }
                            this.i = j2;
                        }
                        this.j = true;
                        d(dataSpec);
                        if (j5 != -1) {
                            return j5;
                        }
                        return this.i;
                    }
                    throw new DataSourceException(0);
                }
                throw new DataSourceException(0);
            }
            String valueOf = String.valueOf(uri);
            StringBuilder sb = new StringBuilder(valueOf.length() + 36);
            sb.append("Could not open file descriptor for: ");
            sb.append(valueOf);
            throw new FileNotFoundException(sb.toString());
        } catch (IOException e2) {
            throw new ContentDataSourceException(e2);
        }
    }

    public int read(byte[] bArr, int i2, int i3) throws ContentDataSourceException {
        if (i3 == 0) {
            return 0;
        }
        long j2 = this.i;
        if (j2 == 0) {
            return -1;
        }
        if (j2 != -1) {
            try {
                i3 = (int) Math.min(j2, (long) i3);
            } catch (IOException e2) {
                throw new ContentDataSourceException(e2);
            }
        }
        int read = ((FileInputStream) Util.castNonNull(this.h)).read(bArr, i2, i3);
        if (read == -1) {
            return -1;
        }
        long j3 = this.i;
        if (j3 != -1) {
            this.i = j3 - ((long) read);
        }
        a(read);
        return read;
    }
}
