package okhttp3;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import okhttp3.internal.Util;

public abstract class ResponseBody implements Closeable {
    private Reader reader;

    public static final class BomAwareReader extends Reader {
        private final Charset charset;
        private boolean closed;
        private Reader delegate;
        private final cm source;

        public BomAwareReader(cm cmVar, Charset charset2) {
            this.source = cmVar;
            this.charset = charset2;
        }

        public void close() throws IOException {
            this.closed = true;
            Reader reader = this.delegate;
            if (reader != null) {
                reader.close();
            } else {
                this.source.close();
            }
        }

        public int read(char[] cArr, int i, int i2) throws IOException {
            if (!this.closed) {
                Reader reader = this.delegate;
                if (reader == null) {
                    InputStreamReader inputStreamReader = new InputStreamReader(this.source.inputStream(), Util.bomAwareCharset(this.source, this.charset));
                    this.delegate = inputStreamReader;
                    reader = inputStreamReader;
                }
                return reader.read(cArr, i, i2);
            }
            throw new IOException("Stream closed");
        }
    }

    private Charset charset() {
        MediaType contentType = contentType();
        if (contentType != null) {
            return contentType.charset(Util.UTF_8);
        }
        return Util.UTF_8;
    }

    public static ResponseBody create(MediaType mediaType, String str) {
        Charset charset = Util.UTF_8;
        if (mediaType != null) {
            Charset charset2 = mediaType.charset();
            if (charset2 == null) {
                mediaType = MediaType.parse(mediaType + "; charset=utf-8");
            } else {
                charset = charset2;
            }
        }
        ck ao = new ck().ao(str, 0, str.length(), charset);
        return create(mediaType, ao.f, ao);
    }

    public final InputStream byteStream() {
        return source().inputStream();
    }

    /* JADX INFO: finally extract failed */
    public final byte[] bytes() throws IOException {
        long contentLength = contentLength();
        if (contentLength <= 2147483647L) {
            cm source = source();
            try {
                byte[] f = source.f();
                Util.closeQuietly((Closeable) source);
                if (contentLength == -1 || contentLength == ((long) f.length)) {
                    return f;
                }
                throw new IOException("Content-Length (" + contentLength + ") and stream length (" + f.length + ") disagree");
            } catch (Throwable th) {
                Util.closeQuietly((Closeable) source);
                throw th;
            }
        } else {
            throw new IOException(y2.g("Cannot buffer entire body for content length: ", contentLength));
        }
    }

    public final Reader charStream() {
        Reader reader2 = this.reader;
        if (reader2 != null) {
            return reader2;
        }
        BomAwareReader bomAwareReader = new BomAwareReader(source(), charset());
        this.reader = bomAwareReader;
        return bomAwareReader;
    }

    public void close() {
        Util.closeQuietly((Closeable) source());
    }

    public abstract long contentLength();

    public abstract MediaType contentType();

    public abstract cm source();

    public final String string() throws IOException {
        cm source = source();
        try {
            return source.q(Util.bomAwareCharset(source, charset()));
        } finally {
            Util.closeQuietly((Closeable) source);
        }
    }

    public static ResponseBody create(MediaType mediaType, byte[] bArr) {
        ck ckVar = new ck();
        ckVar.write(bArr);
        return create(mediaType, (long) bArr.length, ckVar);
    }

    public static ResponseBody create(MediaType mediaType, cq cqVar) {
        ck ckVar = new ck();
        ckVar.ah(cqVar);
        return create(mediaType, (long) cqVar.t(), ckVar);
    }

    public static ResponseBody create(final MediaType mediaType, final long j, final cm cmVar) {
        if (cmVar != null) {
            return new ResponseBody() {
                public long contentLength() {
                    return j;
                }

                public MediaType contentType() {
                    return MediaType.this;
                }

                public cm source() {
                    return cmVar;
                }
            };
        }
        throw new NullPointerException("source == null");
    }
}
