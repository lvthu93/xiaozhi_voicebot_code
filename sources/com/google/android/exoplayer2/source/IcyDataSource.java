package com.google.android.exoplayer2.source;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public final class IcyDataSource implements DataSource {
    public final DataSource a;
    public final int b;
    public final Listener c;
    public final byte[] d;
    public int e;

    public interface Listener {
        void onIcyMetadata(ParsableByteArray parsableByteArray);
    }

    public IcyDataSource(DataSource dataSource, int i, Listener listener) {
        boolean z;
        if (i > 0) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkArgument(z);
        this.a = dataSource;
        this.b = i;
        this.c = listener;
        this.d = new byte[1];
        this.e = i;
    }

    public void addTransferListener(TransferListener transferListener) {
        Assertions.checkNotNull(transferListener);
        this.a.addTransferListener(transferListener);
    }

    public void close() {
        throw new UnsupportedOperationException();
    }

    public Map<String, List<String>> getResponseHeaders() {
        return this.a.getResponseHeaders();
    }

    @Nullable
    public Uri getUri() {
        return this.a.getUri();
    }

    public long open(DataSpec dataSpec) {
        throw new UnsupportedOperationException();
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        int i3 = this.e;
        DataSource dataSource = this.a;
        if (i3 == 0) {
            byte[] bArr2 = this.d;
            boolean z = false;
            if (dataSource.read(bArr2, 0, 1) != -1) {
                int i4 = (bArr2[0] & 255) << 4;
                if (i4 != 0) {
                    byte[] bArr3 = new byte[i4];
                    int i5 = i4;
                    int i6 = 0;
                    while (true) {
                        if (i5 <= 0) {
                            while (i4 > 0) {
                                int i7 = i4 - 1;
                                if (bArr3[i7] != 0) {
                                    break;
                                }
                                i4 = i7;
                            }
                            if (i4 > 0) {
                                this.c.onIcyMetadata(new ParsableByteArray(bArr3, i4));
                            }
                        } else {
                            int read = dataSource.read(bArr3, i6, i5);
                            if (read == -1) {
                                break;
                            }
                            i6 += read;
                            i5 -= read;
                        }
                    }
                }
                z = true;
            }
            if (!z) {
                return -1;
            }
            this.e = this.b;
        }
        int read2 = dataSource.read(bArr, i, Math.min(this.e, i2));
        if (read2 != -1) {
            this.e -= read2;
        }
        return read2;
    }
}
