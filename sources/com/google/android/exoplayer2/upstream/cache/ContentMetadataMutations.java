package com.google.android.exoplayer2.upstream.cache;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContentMetadataMutations {
    public final HashMap a = new HashMap();
    public final ArrayList b = new ArrayList();

    public static ContentMetadataMutations setContentLength(ContentMetadataMutations contentMetadataMutations, long j) {
        return contentMetadataMutations.set("exo_len", j);
    }

    public static ContentMetadataMutations setRedirectedUri(ContentMetadataMutations contentMetadataMutations, @Nullable Uri uri) {
        if (uri == null) {
            return contentMetadataMutations.remove("exo_redir");
        }
        return contentMetadataMutations.set("exo_redir", uri.toString());
    }

    public final void a(Object obj, String str) {
        this.a.put((String) Assertions.checkNotNull(str), Assertions.checkNotNull(obj));
        this.b.remove(str);
    }

    public Map<String, Object> getEditedValues() {
        HashMap hashMap = new HashMap(this.a);
        for (Map.Entry entry : hashMap.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof byte[]) {
                byte[] bArr = (byte[]) value;
                entry.setValue(Arrays.copyOf(bArr, bArr.length));
            }
        }
        return Collections.unmodifiableMap(hashMap);
    }

    public List<String> getRemovedValues() {
        return Collections.unmodifiableList(new ArrayList(this.b));
    }

    public ContentMetadataMutations remove(String str) {
        this.b.add(str);
        this.a.remove(str);
        return this;
    }

    public ContentMetadataMutations set(String str, String str2) {
        a(str2, str);
        return this;
    }

    public ContentMetadataMutations set(String str, long j) {
        a(Long.valueOf(j), str);
        return this;
    }

    public ContentMetadataMutations set(String str, byte[] bArr) {
        a(Arrays.copyOf(bArr, bArr.length), str);
        return this;
    }
}
