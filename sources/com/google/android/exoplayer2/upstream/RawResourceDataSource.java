package com.google.android.exoplayer2.upstream;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.net.Uri;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import j$.nio.channels.DesugarChannels;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.Map;
import org.eclipse.paho.client.mqttv3.MqttTopic;

public final class RawResourceDataSource extends BaseDataSource {
    public final Resources e;
    public final String f;
    @Nullable
    public Uri g;
    @Nullable
    public AssetFileDescriptor h;
    @Nullable
    public FileInputStream i;
    public long j;
    public boolean k;

    public static class RawResourceDataSourceException extends IOException {
        public RawResourceDataSourceException(String str) {
            super(str);
        }

        public RawResourceDataSourceException(Throwable th) {
            super(th);
        }
    }

    public RawResourceDataSource(Context context) {
        super(false);
        this.e = context.getResources();
        this.f = context.getPackageName();
    }

    public static Uri buildRawResourceUri(int i2) {
        StringBuilder sb = new StringBuilder(26);
        sb.append("rawresource:///");
        sb.append(i2);
        return Uri.parse(sb.toString());
    }

    public void close() throws RawResourceDataSourceException {
        this.g = null;
        try {
            FileInputStream fileInputStream = this.i;
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            this.i = null;
            try {
                AssetFileDescriptor assetFileDescriptor = this.h;
                if (assetFileDescriptor != null) {
                    assetFileDescriptor.close();
                }
                this.h = null;
                if (this.k) {
                    this.k = false;
                    b();
                }
            } catch (IOException e2) {
                throw new RawResourceDataSourceException((Throwable) e2);
            } catch (Throwable th) {
                this.h = null;
                if (this.k) {
                    this.k = false;
                    b();
                }
                throw th;
            }
        } catch (IOException e3) {
            throw new RawResourceDataSourceException((Throwable) e3);
        } catch (Throwable th2) {
            this.i = null;
            try {
                AssetFileDescriptor assetFileDescriptor2 = this.h;
                if (assetFileDescriptor2 != null) {
                    assetFileDescriptor2.close();
                }
                this.h = null;
                if (this.k) {
                    this.k = false;
                    b();
                }
                throw th2;
            } catch (IOException e4) {
                throw new RawResourceDataSourceException((Throwable) e4);
            } catch (Throwable th3) {
                this.h = null;
                if (this.k) {
                    this.k = false;
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
        return this.g;
    }

    public long open(DataSpec dataSpec) throws RawResourceDataSourceException {
        int i2;
        long j2;
        String str;
        String str2;
        DataSpec dataSpec2 = dataSpec;
        Uri uri = dataSpec2.a;
        this.g = uri;
        boolean equals = TextUtils.equals("rawresource", uri.getScheme());
        Resources resources = this.e;
        if (equals || (TextUtils.equals("android.resource", uri.getScheme()) && uri.getPathSegments().size() == 1 && ((String) Assertions.checkNotNull(uri.getLastPathSegment())).matches("\\d+"))) {
            try {
                i2 = Integer.parseInt((String) Assertions.checkNotNull(uri.getLastPathSegment()));
            } catch (NumberFormatException unused) {
                throw new RawResourceDataSourceException("Resource identifier must be an integer.");
            }
        } else if (TextUtils.equals("android.resource", uri.getScheme())) {
            String str3 = (String) Assertions.checkNotNull(uri.getPath());
            if (str3.startsWith(MqttTopic.TOPIC_LEVEL_SEPARATOR)) {
                str3 = str3.substring(1);
            }
            String host = uri.getHost();
            if (TextUtils.isEmpty(host)) {
                str = "";
            } else {
                str = String.valueOf(host).concat(":");
            }
            String valueOf = String.valueOf(str);
            String valueOf2 = String.valueOf(str3);
            if (valueOf2.length() != 0) {
                str2 = valueOf.concat(valueOf2);
            } else {
                str2 = new String(valueOf);
            }
            i2 = resources.getIdentifier(str2, "raw", this.f);
            if (i2 == 0) {
                throw new RawResourceDataSourceException("Resource not found.");
            }
        } else {
            throw new RawResourceDataSourceException("URI must either use scheme rawresource or android.resource");
        }
        c(dataSpec);
        try {
            AssetFileDescriptor openRawResourceFd = resources.openRawResourceFd(i2);
            this.h = openRawResourceFd;
            if (openRawResourceFd != null) {
                long length = openRawResourceFd.getLength();
                FileInputStream fileInputStream = new FileInputStream(openRawResourceFd.getFileDescriptor());
                this.i = fileInputStream;
                long j3 = dataSpec2.f;
                int i3 = (length > -1 ? 1 : (length == -1 ? 0 : -1));
                if (i3 == 0 || j3 <= length) {
                    long startOffset = openRawResourceFd.getStartOffset();
                    long j4 = length;
                    long skip = fileInputStream.skip(startOffset + j3) - startOffset;
                    if (skip == j3) {
                        if (i3 == 0) {
                            FileChannel convertMaybeLegacyFileChannelFromLibrary = DesugarChannels.convertMaybeLegacyFileChannelFromLibrary(fileInputStream.getChannel());
                            if (convertMaybeLegacyFileChannelFromLibrary.size() == 0) {
                                this.j = -1;
                            } else {
                                long size = convertMaybeLegacyFileChannelFromLibrary.size() - convertMaybeLegacyFileChannelFromLibrary.position();
                                this.j = size;
                                if (size < 0) {
                                    throw new DataSourceException(0);
                                }
                            }
                        } else {
                            long j5 = j4 - skip;
                            this.j = j5;
                            if (j5 < 0) {
                                throw new DataSourceException(0);
                            }
                        }
                        long j6 = dataSpec2.g;
                        int i4 = (j6 > -1 ? 1 : (j6 == -1 ? 0 : -1));
                        if (i4 != 0) {
                            long j7 = this.j;
                            if (j7 == -1) {
                                j2 = j6;
                            } else {
                                j2 = Math.min(j7, j6);
                            }
                            this.j = j2;
                        }
                        this.k = true;
                        d(dataSpec);
                        if (i4 != 0) {
                            return j6;
                        }
                        return this.j;
                    }
                    throw new DataSourceException(0);
                }
                try {
                    throw new DataSourceException(0);
                } catch (IOException e2) {
                    throw new RawResourceDataSourceException((Throwable) e2);
                }
            } else {
                String valueOf3 = String.valueOf(uri);
                StringBuilder sb = new StringBuilder(valueOf3.length() + 24);
                sb.append("Resource is compressed: ");
                sb.append(valueOf3);
                throw new RawResourceDataSourceException(sb.toString());
            }
        } catch (Resources.NotFoundException e3) {
            throw new RawResourceDataSourceException((Throwable) e3);
        }
    }

    public int read(byte[] bArr, int i2, int i3) throws RawResourceDataSourceException {
        if (i3 == 0) {
            return 0;
        }
        long j2 = this.j;
        if (j2 == 0) {
            return -1;
        }
        if (j2 != -1) {
            try {
                i3 = (int) Math.min(j2, (long) i3);
            } catch (IOException e2) {
                throw new RawResourceDataSourceException((Throwable) e2);
            }
        }
        int read = ((InputStream) Util.castNonNull(this.i)).read(bArr, i2, i3);
        if (read != -1) {
            long j3 = this.j;
            if (j3 != -1) {
                this.j = j3 - ((long) read);
            }
            a(read);
            return read;
        } else if (this.j == -1) {
            return -1;
        } else {
            throw new RawResourceDataSourceException((Throwable) new EOFException());
        }
    }
}
