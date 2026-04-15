package okhttp3.internal.http;

import okhttp3.MediaType;
import okhttp3.ResponseBody;

public final class RealResponseBody extends ResponseBody {
    private final long contentLength;
    private final String contentTypeString;
    private final cm source;

    public RealResponseBody(String str, long j, cm cmVar) {
        this.contentTypeString = str;
        this.contentLength = j;
        this.source = cmVar;
    }

    public long contentLength() {
        return this.contentLength;
    }

    public MediaType contentType() {
        String str = this.contentTypeString;
        if (str != null) {
            return MediaType.parse(str);
        }
        return null;
    }

    public cm source() {
        return this.source;
    }
}
