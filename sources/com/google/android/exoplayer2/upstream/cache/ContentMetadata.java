package com.google.android.exoplayer2.upstream.cache;

import androidx.annotation.Nullable;

public interface ContentMetadata {
    boolean contains(String str);

    long get(String str, long j);

    @Nullable
    String get(String str, @Nullable String str2);

    @Nullable
    byte[] get(String str, @Nullable byte[] bArr);
}
