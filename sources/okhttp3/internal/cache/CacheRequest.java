package okhttp3.internal.cache;

import java.io.IOException;

public interface CacheRequest {
    void abort();

    za body() throws IOException;
}
