package defpackage;

import android.annotation.TargetApi;
import android.media.AudioTimestamp;
import android.media.AudioTrack;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.util.Util;

/* renamed from: bx  reason: default package */
public final class bx {
    @Nullable
    public final a a;
    public int b;
    public long c;
    public long d;
    public long e;
    public long f;

    @RequiresApi(19)
    /* renamed from: bx$a */
    public static final class a {
        public final AudioTrack a;
        public final AudioTimestamp b = new AudioTimestamp();
        public long c;
        public long d;
        public long e;

        public a(AudioTrack audioTrack) {
            this.a = audioTrack;
        }

        public long getTimestampPositionFrames() {
            return this.e;
        }

        public long getTimestampSystemTimeUs() {
            return this.b.nanoTime / 1000;
        }

        public boolean maybeUpdateTimestamp() {
            AudioTrack audioTrack = this.a;
            AudioTimestamp audioTimestamp = this.b;
            boolean timestamp = audioTrack.getTimestamp(audioTimestamp);
            if (timestamp) {
                long j = audioTimestamp.framePosition;
                if (this.d > j) {
                    this.c++;
                }
                this.d = j;
                this.e = j + (this.c << 32);
            }
            return timestamp;
        }
    }

    public bx(AudioTrack audioTrack) {
        if (Util.a >= 19) {
            this.a = new a(audioTrack);
            reset();
            return;
        }
        this.a = null;
        a(3);
    }

    public final void a(int i) {
        this.b = i;
        if (i == 0) {
            this.e = 0;
            this.f = -1;
            this.c = System.nanoTime() / 1000;
            this.d = 10000;
        } else if (i == 1) {
            this.d = 10000;
        } else if (i == 2 || i == 3) {
            this.d = 10000000;
        } else if (i == 4) {
            this.d = 500000;
        } else {
            throw new IllegalStateException();
        }
    }

    public void acceptTimestamp() {
        if (this.b == 4) {
            reset();
        }
    }

    @TargetApi(19)
    public long getTimestampPositionFrames() {
        a aVar = this.a;
        if (aVar != null) {
            return aVar.getTimestampPositionFrames();
        }
        return -1;
    }

    @TargetApi(19)
    public long getTimestampSystemTimeUs() {
        a aVar = this.a;
        if (aVar != null) {
            return aVar.getTimestampSystemTimeUs();
        }
        return -9223372036854775807L;
    }

    public boolean hasAdvancingTimestamp() {
        return this.b == 2;
    }

    public boolean hasTimestamp() {
        int i = this.b;
        return i == 1 || i == 2;
    }

    @TargetApi(19)
    public boolean maybePollTimestamp(long j) {
        a aVar = this.a;
        if (aVar == null || j - this.e < this.d) {
            return false;
        }
        this.e = j;
        boolean maybeUpdateTimestamp = aVar.maybeUpdateTimestamp();
        int i = this.b;
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        if (i != 4) {
                            throw new IllegalStateException();
                        }
                    } else if (maybeUpdateTimestamp) {
                        reset();
                    }
                } else if (!maybeUpdateTimestamp) {
                    reset();
                }
            } else if (!maybeUpdateTimestamp) {
                reset();
            } else if (aVar.getTimestampPositionFrames() > this.f) {
                a(2);
            }
        } else if (maybeUpdateTimestamp) {
            if (aVar.getTimestampSystemTimeUs() < this.c) {
                return false;
            }
            this.f = aVar.getTimestampPositionFrames();
            a(1);
        } else if (j - this.c > 500000) {
            a(3);
        }
        return maybeUpdateTimestamp;
    }

    public void rejectTimestamp() {
        a(4);
    }

    public void reset() {
        if (this.a != null) {
            a(0);
        }
    }
}
