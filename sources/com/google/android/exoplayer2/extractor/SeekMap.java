package com.google.android.exoplayer2.extractor;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;

public interface SeekMap {

    public static final class SeekPoints {
        public final SeekPoint a;
        public final SeekPoint b;

        public SeekPoints(SeekPoint seekPoint) {
            this(seekPoint, seekPoint);
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || SeekPoints.class != obj.getClass()) {
                return false;
            }
            SeekPoints seekPoints = (SeekPoints) obj;
            if (!this.a.equals(seekPoints.a) || !this.b.equals(seekPoints.b)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return this.b.hashCode() + (this.a.hashCode() * 31);
        }

        public String toString() {
            String str;
            SeekPoint seekPoint = this.a;
            String valueOf = String.valueOf(seekPoint);
            SeekPoint seekPoint2 = this.b;
            if (seekPoint.equals(seekPoint2)) {
                str = "";
            } else {
                String valueOf2 = String.valueOf(seekPoint2);
                StringBuilder sb = new StringBuilder(valueOf2.length() + 2);
                sb.append(", ");
                sb.append(valueOf2);
                str = sb.toString();
            }
            StringBuilder sb2 = new StringBuilder(y2.c(str, valueOf.length() + 2));
            sb2.append("[");
            sb2.append(valueOf);
            sb2.append(str);
            sb2.append("]");
            return sb2.toString();
        }

        public SeekPoints(SeekPoint seekPoint, SeekPoint seekPoint2) {
            this.a = (SeekPoint) Assertions.checkNotNull(seekPoint);
            this.b = (SeekPoint) Assertions.checkNotNull(seekPoint2);
        }
    }

    public static class Unseekable implements SeekMap {
        public final long a;
        public final SeekPoints b;

        public Unseekable(long j) {
            this(j, 0);
        }

        public long getDurationUs() {
            return this.a;
        }

        public SeekPoints getSeekPoints(long j) {
            return this.b;
        }

        public boolean isSeekable() {
            return false;
        }

        public Unseekable(long j, long j2) {
            this.a = j;
            this.b = new SeekPoints(j2 == 0 ? SeekPoint.c : new SeekPoint(0, j2));
        }
    }

    long getDurationUs();

    SeekPoints getSeekPoints(long j);

    boolean isSeekable();
}
