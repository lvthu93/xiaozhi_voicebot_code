package defpackage;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.audio.OpusUtil;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableByteArray;
import defpackage.pb;
import java.util.Arrays;
import org.checkerframework.checker.nullness.qual.EnsuresNonNullIf;

/* renamed from: t7  reason: default package */
public final class t7 extends pb {
    public static final byte[] o = {79, 112, 117, 115, 72, 101, 97, 100};
    public boolean n;

    public static boolean verifyBitstreamType(ParsableByteArray parsableByteArray) {
        if (parsableByteArray.bytesLeft() < 8) {
            return false;
        }
        byte[] bArr = new byte[8];
        parsableByteArray.readBytes(bArr, 0, 8);
        return Arrays.equals(bArr, o);
    }

    public final long b(ParsableByteArray parsableByteArray) {
        byte b;
        int i;
        byte[] data = parsableByteArray.getData();
        byte b2 = data[0] & 255;
        byte b3 = b2 & 3;
        if (b3 != 0) {
            b = 2;
            if (!(b3 == 1 || b3 == 2)) {
                b = data[1] & 63;
            }
        } else {
            b = 1;
        }
        int i2 = b2 >> 3;
        int i3 = i2 & 3;
        if (i2 >= 16) {
            i = 2500 << i3;
        } else if (i2 >= 12) {
            i = 10000 << (i3 & 1);
        } else if (i3 == 3) {
            i = 60000;
        } else {
            i = 10000 << i3;
        }
        return (((long) this.i) * (((long) b) * ((long) i))) / 1000000;
    }

    @EnsuresNonNullIf(expression = {"#3.format"}, result = false)
    public final boolean c(ParsableByteArray parsableByteArray, long j, pb.a aVar) {
        boolean z = true;
        if (!this.n) {
            byte[] copyOf = Arrays.copyOf(parsableByteArray.getData(), parsableByteArray.limit());
            int channelCount = OpusUtil.getChannelCount(copyOf);
            aVar.a = new Format.Builder().setSampleMimeType("audio/opus").setChannelCount(channelCount).setSampleRate(48000).setInitializationData(OpusUtil.buildInitializationData(copyOf)).build();
            this.n = true;
            return true;
        }
        Assertions.checkNotNull(aVar.a);
        if (parsableByteArray.readInt() != 1332770163) {
            z = false;
        }
        parsableByteArray.setPosition(0);
        return z;
    }

    public final void d(boolean z) {
        super.d(z);
        if (z) {
            this.n = false;
        }
    }
}
