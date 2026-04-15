package com.google.android.exoplayer2.decoder;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.util.Assertions;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class DecoderReuseEvaluation {
    public final String a;
    public final Format b;
    public final Format c;
    public final int d;
    public final int e;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface DecoderDiscardReasons {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface DecoderReuseResult {
    }

    public DecoderReuseEvaluation(String str, Format format, Format format2, int i, int i2) {
        boolean z;
        if (i == 0 || i2 == 0) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkArgument(z);
        this.a = Assertions.checkNotEmpty(str);
        this.b = (Format) Assertions.checkNotNull(format);
        this.c = (Format) Assertions.checkNotNull(format2);
        this.d = i;
        this.e = i2;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || DecoderReuseEvaluation.class != obj.getClass()) {
            return false;
        }
        DecoderReuseEvaluation decoderReuseEvaluation = (DecoderReuseEvaluation) obj;
        if (this.d != decoderReuseEvaluation.d || this.e != decoderReuseEvaluation.e || !this.a.equals(decoderReuseEvaluation.a) || !this.b.equals(decoderReuseEvaluation.b) || !this.c.equals(decoderReuseEvaluation.c)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int hashCode = this.a.hashCode();
        int hashCode2 = this.b.hashCode();
        return this.c.hashCode() + ((hashCode2 + ((hashCode + ((((527 + this.d) * 31) + this.e) * 31)) * 31)) * 31);
    }
}
