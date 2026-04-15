package okhttp3;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.logging.Logger;
import okhttp3.internal.Util;

public abstract class RequestBody {
    public static RequestBody create(MediaType mediaType, String str) {
        Charset charset = Util.UTF_8;
        if (mediaType != null) {
            Charset charset2 = mediaType.charset();
            if (charset2 == null) {
                mediaType = MediaType.parse(mediaType + "; charset=utf-8");
            } else {
                charset = charset2;
            }
        }
        return create(mediaType, str.getBytes(charset));
    }

    public long contentLength() throws IOException {
        return -1;
    }

    public abstract MediaType contentType();

    public abstract void writeTo(cl clVar) throws IOException;

    public static RequestBody create(final MediaType mediaType, final cq cqVar) {
        return new RequestBody() {
            public long contentLength() throws IOException {
                return (long) cqVar.t();
            }

            public MediaType contentType() {
                return MediaType.this;
            }

            public void writeTo(cl clVar) throws IOException {
                clVar.r(cqVar);
            }
        };
    }

    public static RequestBody create(MediaType mediaType, byte[] bArr) {
        return create(mediaType, bArr, 0, bArr.length);
    }

    public static RequestBody create(final MediaType mediaType, final byte[] bArr, final int i, final int i2) {
        if (bArr != null) {
            Util.checkOffsetAndCount((long) bArr.length, (long) i, (long) i2);
            return new RequestBody() {
                public long contentLength() {
                    return (long) i2;
                }

                public MediaType contentType() {
                    return MediaType.this;
                }

                public void writeTo(cl clVar) throws IOException {
                    clVar.write(bArr, i, i2);
                }
            };
        }
        throw new NullPointerException("content == null");
    }

    public static RequestBody create(final MediaType mediaType, final File file) {
        if (file != null) {
            return new RequestBody() {
                public long contentLength() {
                    return file.length();
                }

                public MediaType contentType() {
                    return MediaType.this;
                }

                public void writeTo(cl clVar) throws IOException {
                    p7 p7Var = null;
                    try {
                        File file = file;
                        Logger logger = s7.a;
                        if (file != null) {
                            p7 p7Var2 = new p7(new FileInputStream(file), new lc());
                            try {
                                clVar.j(p7Var2);
                                Util.closeQuietly((Closeable) p7Var2);
                            } catch (Throwable th) {
                                th = th;
                                p7Var = p7Var2;
                                Util.closeQuietly((Closeable) p7Var);
                                throw th;
                            }
                        } else {
                            throw new IllegalArgumentException("file == null");
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        Util.closeQuietly((Closeable) p7Var);
                        throw th;
                    }
                }
            };
        }
        throw new NullPointerException("file == null");
    }
}
