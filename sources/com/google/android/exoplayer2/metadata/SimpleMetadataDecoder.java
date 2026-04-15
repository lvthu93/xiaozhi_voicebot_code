package com.google.android.exoplayer2.metadata;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import java.nio.ByteBuffer;

public abstract class SimpleMetadataDecoder implements MetadataDecoder {
    @Nullable
    public abstract Metadata a(MetadataInputBuffer metadataInputBuffer, ByteBuffer byteBuffer);

    @Nullable
    public final Metadata decode(MetadataInputBuffer metadataInputBuffer) {
        boolean z;
        ByteBuffer byteBuffer = (ByteBuffer) Assertions.checkNotNull(metadataInputBuffer.g);
        if (byteBuffer.position() == 0 && byteBuffer.hasArray() && byteBuffer.arrayOffset() == 0) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkArgument(z);
        if (metadataInputBuffer.isDecodeOnly()) {
            return null;
        }
        return a(metadataInputBuffer, byteBuffer);
    }
}
