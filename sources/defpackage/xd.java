package defpackage;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.VorbisUtil;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableByteArray;
import defpackage.pb;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.checkerframework.checker.nullness.qual.EnsuresNonNullIf;

/* renamed from: xd  reason: default package */
public final class xd extends pb {
    @Nullable
    public a n;
    public int o;
    public boolean p;
    @Nullable
    public VorbisUtil.VorbisIdHeader q;
    @Nullable
    public VorbisUtil.CommentHeader r;

    /* renamed from: xd$a */
    public static final class a {
        public final VorbisUtil.VorbisIdHeader a;
        public final byte[] b;
        public final VorbisUtil.Mode[] c;
        public final int d;

        public a(VorbisUtil.VorbisIdHeader vorbisIdHeader, VorbisUtil.CommentHeader commentHeader, byte[] bArr, VorbisUtil.Mode[] modeArr, int i) {
            this.a = vorbisIdHeader;
            this.b = bArr;
            this.c = modeArr;
            this.d = i;
        }
    }

    public static boolean verifyBitstreamType(ParsableByteArray parsableByteArray) {
        try {
            return VorbisUtil.verifyVorbisHeaderCapturePattern(1, parsableByteArray, true);
        } catch (ParserException unused) {
            return false;
        }
    }

    public final void a(long j) {
        boolean z;
        this.g = j;
        int i = 0;
        if (j != 0) {
            z = true;
        } else {
            z = false;
        }
        this.p = z;
        VorbisUtil.VorbisIdHeader vorbisIdHeader = this.q;
        if (vorbisIdHeader != null) {
            i = vorbisIdHeader.e;
        }
        this.o = i;
    }

    public final long b(ParsableByteArray parsableByteArray) {
        int i;
        int i2 = 0;
        if ((parsableByteArray.getData()[0] & 1) == 1) {
            return -1;
        }
        byte b = parsableByteArray.getData()[0];
        a aVar = (a) Assertions.checkStateNotNull(this.n);
        boolean z = aVar.c[(b >> 1) & (255 >>> (8 - aVar.d))].a;
        VorbisUtil.VorbisIdHeader vorbisIdHeader = aVar.a;
        if (!z) {
            i = vorbisIdHeader.e;
        } else {
            i = vorbisIdHeader.f;
        }
        if (this.p) {
            i2 = (this.o + i) / 4;
        }
        long j = (long) i2;
        if (parsableByteArray.capacity() < parsableByteArray.limit() + 4) {
            parsableByteArray.reset(Arrays.copyOf(parsableByteArray.getData(), parsableByteArray.limit() + 4));
        } else {
            parsableByteArray.setLimit(parsableByteArray.limit() + 4);
        }
        byte[] data = parsableByteArray.getData();
        data[parsableByteArray.limit() - 4] = (byte) ((int) (j & 255));
        data[parsableByteArray.limit() - 3] = (byte) ((int) ((j >>> 8) & 255));
        data[parsableByteArray.limit() - 2] = (byte) ((int) ((j >>> 16) & 255));
        data[parsableByteArray.limit() - 1] = (byte) ((int) ((j >>> 24) & 255));
        this.p = true;
        this.o = i;
        return j;
    }

    @EnsuresNonNullIf(expression = {"#3.format"}, result = false)
    public final boolean c(ParsableByteArray parsableByteArray, long j, pb.a aVar) throws IOException {
        if (this.n != null) {
            Assertions.checkNotNull(aVar.a);
            return false;
        }
        VorbisUtil.VorbisIdHeader vorbisIdHeader = this.q;
        a aVar2 = null;
        if (vorbisIdHeader == null) {
            this.q = VorbisUtil.readVorbisIdentificationHeader(parsableByteArray);
        } else {
            VorbisUtil.CommentHeader commentHeader = this.r;
            if (commentHeader == null) {
                this.r = VorbisUtil.readVorbisCommentHeader(parsableByteArray);
            } else {
                byte[] bArr = new byte[parsableByteArray.limit()];
                System.arraycopy(parsableByteArray.getData(), 0, bArr, 0, parsableByteArray.limit());
                VorbisUtil.Mode[] readVorbisModes = VorbisUtil.readVorbisModes(parsableByteArray, vorbisIdHeader.a);
                aVar2 = new a(vorbisIdHeader, commentHeader, bArr, readVorbisModes, VorbisUtil.iLog(readVorbisModes.length - 1));
            }
        }
        this.n = aVar2;
        if (aVar2 == null) {
            return true;
        }
        VorbisUtil.VorbisIdHeader vorbisIdHeader2 = aVar2.a;
        ArrayList arrayList = new ArrayList();
        arrayList.add(vorbisIdHeader2.g);
        arrayList.add(aVar2.b);
        aVar.a = new Format.Builder().setSampleMimeType("audio/vorbis").setAverageBitrate(vorbisIdHeader2.d).setPeakBitrate(vorbisIdHeader2.c).setChannelCount(vorbisIdHeader2.a).setSampleRate(vorbisIdHeader2.b).setInitializationData(arrayList).build();
        return true;
    }

    public final void d(boolean z) {
        super.d(z);
        if (z) {
            this.n = null;
            this.q = null;
            this.r = null;
        }
        this.o = 0;
        this.p = false;
    }
}
