package com.google.android.exoplayer2.extractor.jpeg;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.metadata.mp4.MotionPhotoMetadata;
import java.util.List;

public final class MotionPhotoDescription {
    public final long a;
    public final List<ContainerItem> b;

    public static final class ContainerItem {
        public final String a;
        public final long b;
        public final long c;

        public ContainerItem(String str, String str2, long j, long j2) {
            this.a = str;
            this.b = j;
            this.c = j2;
        }
    }

    public MotionPhotoDescription(long j, List<ContainerItem> list) {
        this.a = j;
        this.b = list;
    }

    @Nullable
    public MotionPhotoMetadata getMotionPhotoMetadata(long j) {
        long j2;
        List<ContainerItem> list = this.b;
        if (list.size() < 2) {
            return null;
        }
        long j3 = j;
        long j4 = -1;
        long j5 = -1;
        long j6 = -1;
        long j7 = -1;
        boolean z = false;
        for (int size = list.size() - 1; size >= 0; size--) {
            ContainerItem containerItem = list.get(size);
            boolean equals = "video/mp4".equals(containerItem.a) | z;
            if (size == 0) {
                j3 -= containerItem.c;
                j2 = 0;
            } else {
                j2 = j3 - containerItem.b;
            }
            long j8 = j3;
            j3 = j2;
            long j9 = j8;
            if (!equals || j3 == j9) {
                z = equals;
            } else {
                j7 = j9 - j3;
                j6 = j3;
                z = false;
            }
            if (size == 0) {
                j4 = j3;
                j5 = j9;
            }
        }
        if (j6 == -1 || j7 == -1 || j4 == -1 || j5 == -1) {
            return null;
        }
        return new MotionPhotoMetadata(j4, j5, this.a, j6, j7);
    }
}
