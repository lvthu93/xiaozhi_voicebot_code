package com.google.android.exoplayer2.audio;

import androidx.annotation.Nullable;
import org.mozilla.javascript.Token;

public final class MpegAudioUtil {
    public static final String[] a = {"audio/mpeg-L1", "audio/mpeg-L2", "audio/mpeg"};
    public static final int[] b = {44100, 48000, 32000};
    public static final int[] c = {32000, 64000, 96000, 128000, 160000, 192000, 224000, 256000, 288000, 320000, 352000, 384000, 416000, 448000};
    public static final int[] d = {32000, 48000, 56000, 64000, 80000, 96000, 112000, 128000, 144000, 160000, 176000, 192000, 224000, 256000};
    public static final int[] e = {32000, 48000, 56000, 64000, 80000, 96000, 112000, 128000, 160000, 192000, 224000, 256000, 320000, 384000};
    public static final int[] f = {32000, 40000, 48000, 56000, 64000, 80000, 96000, 112000, 128000, 160000, 192000, 224000, 256000, 320000};
    public static final int[] g = {8000, 16000, 24000, 32000, 40000, 48000, 56000, 64000, 80000, 96000, 112000, 128000, 144000, 160000};

    public static final class Header {
        public int a;
        @Nullable
        public String b;
        public int c;
        public int d;
        public int e;
        public int f;
        public int g;

        public boolean setForHeaderData(int i) {
            boolean z;
            int i2;
            int i3;
            int i4;
            int i5;
            int i6;
            int i7;
            if ((i & -2097152) == -2097152) {
                z = true;
            } else {
                z = false;
            }
            if (!z || (i2 = (i >>> 19) & 3) == 1 || (i3 = (i >>> 17) & 3) == 0 || (i4 = (i >>> 12) & 15) == 0 || i4 == 15 || (i5 = (i >>> 10) & 3) == 3) {
                return false;
            }
            this.a = i2;
            this.b = MpegAudioUtil.a[3 - i3];
            int i8 = MpegAudioUtil.b[i5];
            this.d = i8;
            int i9 = 2;
            if (i2 == 2) {
                this.d = i8 / 2;
            } else if (i2 == 0) {
                this.d = i8 / 4;
            }
            int i10 = (i >>> 9) & 1;
            int i11 = 1152;
            if (i3 != 1) {
                if (i3 != 2) {
                    if (i3 == 3) {
                        i11 = 384;
                    } else {
                        throw new IllegalArgumentException();
                    }
                }
            } else if (i2 != 3) {
                i11 = 576;
            }
            this.g = i11;
            if (i3 == 3) {
                if (i2 == 3) {
                    i7 = MpegAudioUtil.c[i4 - 1];
                } else {
                    i7 = MpegAudioUtil.d[i4 - 1];
                }
                this.f = i7;
                this.c = (((i7 * 12) / this.d) + i10) * 4;
            } else {
                int i12 = Token.DOTDOT;
                if (i2 == 3) {
                    if (i3 == 2) {
                        i6 = MpegAudioUtil.e[i4 - 1];
                    } else {
                        i6 = MpegAudioUtil.f[i4 - 1];
                    }
                    this.f = i6;
                    this.c = ((i6 * Token.DOTDOT) / this.d) + i10;
                } else {
                    int i13 = MpegAudioUtil.g[i4 - 1];
                    this.f = i13;
                    if (i3 == 1) {
                        i12 = 72;
                    }
                    this.c = ((i12 * i13) / this.d) + i10;
                }
            }
            if (((i >> 6) & 3) == 3) {
                i9 = 1;
            }
            this.e = i9;
            return true;
        }
    }

    public static int getFrameSize(int i) {
        boolean z;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        if ((i & -2097152) == -2097152) {
            z = true;
        } else {
            z = false;
        }
        if (!z || (i2 = (i >>> 19) & 3) == 1 || (i3 = (i >>> 17) & 3) == 0 || (i4 = (i >>> 12) & 15) == 0 || i4 == 15 || (i5 = (i >>> 10) & 3) == 3) {
            return -1;
        }
        int i8 = b[i5];
        if (i2 == 2) {
            i8 /= 2;
        } else if (i2 == 0) {
            i8 /= 4;
        }
        int i9 = (i >>> 9) & 1;
        if (i3 == 3) {
            if (i2 == 3) {
                i7 = c[i4 - 1];
            } else {
                i7 = d[i4 - 1];
            }
            return (((i7 * 12) / i8) + i9) * 4;
        }
        if (i2 != 3) {
            i6 = g[i4 - 1];
        } else if (i3 == 2) {
            i6 = e[i4 - 1];
        } else {
            i6 = f[i4 - 1];
        }
        int i10 = Token.DOTDOT;
        if (i2 == 3) {
            return ((i6 * Token.DOTDOT) / i8) + i9;
        }
        if (i3 == 1) {
            i10 = 72;
        }
        return ((i10 * i6) / i8) + i9;
    }

    public static int parseMpegAudioFrameSampleCount(int i) {
        int i2;
        int i3;
        if (!((i & -2097152) == -2097152) || (i2 = (i >>> 19) & 3) == 1 || (i3 = (i >>> 17) & 3) == 0) {
            return -1;
        }
        int i4 = (i >>> 12) & 15;
        int i5 = (i >>> 10) & 3;
        if (i4 == 0 || i4 == 15 || i5 == 3) {
            return -1;
        }
        if (i3 == 1) {
            return i2 == 3 ? 1152 : 576;
        }
        if (i3 == 2) {
            return 1152;
        }
        if (i3 == 3) {
            return 384;
        }
        throw new IllegalArgumentException();
    }
}
