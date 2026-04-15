package com.google.android.exoplayer2.upstream.cache;

import androidx.annotation.Nullable;
import com.google.common.base.Charsets;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class DefaultContentMetadata implements ContentMetadata {
    public static final DefaultContentMetadata c = new DefaultContentMetadata(Collections.emptyMap());
    public int a;
    public final Map<String, byte[]> b;

    public DefaultContentMetadata() {
        this(Collections.emptyMap());
    }

    public static boolean a(Map<String, byte[]> map, Map<String, byte[]> map2) {
        if (map.size() != map2.size()) {
            return false;
        }
        for (Map.Entry next : map.entrySet()) {
            if (!Arrays.equals((byte[]) next.getValue(), map2.get(next.getKey()))) {
                return false;
            }
        }
        return true;
    }

    public final boolean contains(String str) {
        return this.b.containsKey(str);
    }

    public DefaultContentMetadata copyWithMutationsApplied(ContentMetadataMutations contentMetadataMutations) {
        byte[] bArr;
        Map<String, byte[]> map = this.b;
        HashMap hashMap = new HashMap(map);
        List<String> removedValues = contentMetadataMutations.getRemovedValues();
        for (int i = 0; i < removedValues.size(); i++) {
            hashMap.remove(removedValues.get(i));
        }
        for (Map.Entry next : contentMetadataMutations.getEditedValues().entrySet()) {
            String str = (String) next.getKey();
            Object value = next.getValue();
            if (value instanceof Long) {
                bArr = ByteBuffer.allocate(8).putLong(((Long) value).longValue()).array();
            } else if (value instanceof String) {
                bArr = ((String) value).getBytes(Charsets.c);
            } else if (value instanceof byte[]) {
                bArr = (byte[]) value;
            } else {
                throw new IllegalArgumentException();
            }
            hashMap.put(str, bArr);
        }
        if (a(map, hashMap)) {
            return this;
        }
        return new DefaultContentMetadata(hashMap);
    }

    public Set<Map.Entry<String, byte[]>> entrySet() {
        return this.b.entrySet();
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || DefaultContentMetadata.class != obj.getClass()) {
            return false;
        }
        return a(this.b, ((DefaultContentMetadata) obj).b);
    }

    @Nullable
    public final byte[] get(String str, @Nullable byte[] bArr) {
        byte[] bArr2 = this.b.get(str);
        return bArr2 != null ? Arrays.copyOf(bArr2, bArr2.length) : bArr;
    }

    public int hashCode() {
        if (this.a == 0) {
            int i = 0;
            for (Map.Entry next : this.b.entrySet()) {
                i += Arrays.hashCode((byte[]) next.getValue()) ^ ((String) next.getKey()).hashCode();
            }
            this.a = i;
        }
        return this.a;
    }

    public DefaultContentMetadata(Map<String, byte[]> map) {
        this.b = Collections.unmodifiableMap(map);
    }

    @Nullable
    public final String get(String str, @Nullable String str2) {
        byte[] bArr = this.b.get(str);
        return bArr != null ? new String(bArr, Charsets.c) : str2;
    }

    public final long get(String str, long j) {
        byte[] bArr = this.b.get(str);
        return bArr != null ? ByteBuffer.wrap(bArr).getLong() : j;
    }
}
