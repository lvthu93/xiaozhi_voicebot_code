package com.google.android.exoplayer2.source;

import java.util.Arrays;
import java.util.Random;

public interface ShuffleOrder {

    public static class DefaultShuffleOrder implements ShuffleOrder {
        public final Random a;
        public final int[] b;
        public final int[] c;

        public DefaultShuffleOrder(int i) {
            this(i, new Random());
        }

        public ShuffleOrder cloneAndClear() {
            return new DefaultShuffleOrder(0, new Random(this.a.nextLong()));
        }

        public ShuffleOrder cloneAndInsert(int i, int i2) {
            Random random;
            int[] iArr;
            int[] iArr2 = new int[i2];
            int[] iArr3 = new int[i2];
            int i3 = 0;
            while (true) {
                random = this.a;
                iArr = this.b;
                if (i3 >= i2) {
                    break;
                }
                iArr2[i3] = random.nextInt(iArr.length + 1);
                int i4 = i3 + 1;
                int nextInt = random.nextInt(i4);
                iArr3[i3] = iArr3[nextInt];
                iArr3[nextInt] = i3 + i;
                i3 = i4;
            }
            Arrays.sort(iArr2);
            int[] iArr4 = new int[(iArr.length + i2)];
            int i5 = 0;
            int i6 = 0;
            for (int i7 = 0; i7 < iArr.length + i2; i7++) {
                if (i5 >= i2 || i6 != iArr2[i5]) {
                    int i8 = i6 + 1;
                    int i9 = iArr[i6];
                    iArr4[i7] = i9;
                    if (i9 >= i) {
                        iArr4[i7] = i9 + i2;
                    }
                    i6 = i8;
                } else {
                    iArr4[i7] = iArr3[i5];
                    i5++;
                }
            }
            return new DefaultShuffleOrder(iArr4, new Random(random.nextLong()));
        }

        public ShuffleOrder cloneAndRemove(int i, int i2) {
            int i3 = i2 - i;
            int[] iArr = this.b;
            int[] iArr2 = new int[(iArr.length - i3)];
            int i4 = 0;
            for (int i5 = 0; i5 < iArr.length; i5++) {
                int i6 = iArr[i5];
                if (i6 < i || i6 >= i2) {
                    int i7 = i5 - i4;
                    if (i6 >= i) {
                        i6 -= i3;
                    }
                    iArr2[i7] = i6;
                } else {
                    i4++;
                }
            }
            return new DefaultShuffleOrder(iArr2, new Random(this.a.nextLong()));
        }

        public int getFirstIndex() {
            int[] iArr = this.b;
            if (iArr.length > 0) {
                return iArr[0];
            }
            return -1;
        }

        public int getLastIndex() {
            int[] iArr = this.b;
            if (iArr.length > 0) {
                return iArr[iArr.length - 1];
            }
            return -1;
        }

        public int getLength() {
            return this.b.length;
        }

        public int getNextIndex(int i) {
            int i2 = this.c[i] + 1;
            int[] iArr = this.b;
            if (i2 < iArr.length) {
                return iArr[i2];
            }
            return -1;
        }

        public int getPreviousIndex(int i) {
            int i2 = this.c[i] - 1;
            if (i2 >= 0) {
                return this.b[i2];
            }
            return -1;
        }

        public DefaultShuffleOrder(int i, long j) {
            this(i, new Random(j));
        }

        public DefaultShuffleOrder(int[] iArr, long j) {
            this(Arrays.copyOf(iArr, iArr.length), new Random(j));
        }

        public DefaultShuffleOrder(int[] iArr, Random random) {
            this.b = iArr;
            this.a = random;
            this.c = new int[iArr.length];
            for (int i = 0; i < iArr.length; i++) {
                this.c[iArr[i]] = i;
            }
        }

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public DefaultShuffleOrder(int r6, java.util.Random r7) {
            /*
                r5 = this;
                int[] r0 = new int[r6]
                r1 = 0
            L_0x0003:
                if (r1 >= r6) goto L_0x0013
                int r2 = r1 + 1
                int r3 = r7.nextInt(r2)
                r4 = r0[r3]
                r0[r1] = r4
                r0[r3] = r1
                r1 = r2
                goto L_0x0003
            L_0x0013:
                r5.<init>((int[]) r0, (java.util.Random) r7)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.ShuffleOrder.DefaultShuffleOrder.<init>(int, java.util.Random):void");
        }
    }

    public static final class UnshuffledShuffleOrder implements ShuffleOrder {
        public final int a;

        public UnshuffledShuffleOrder(int i) {
            this.a = i;
        }

        public ShuffleOrder cloneAndClear() {
            return new UnshuffledShuffleOrder(0);
        }

        public ShuffleOrder cloneAndInsert(int i, int i2) {
            return new UnshuffledShuffleOrder(this.a + i2);
        }

        public ShuffleOrder cloneAndRemove(int i, int i2) {
            return new UnshuffledShuffleOrder((this.a - i2) + i);
        }

        public int getFirstIndex() {
            return this.a > 0 ? 0 : -1;
        }

        public int getLastIndex() {
            int i = this.a;
            if (i > 0) {
                return i - 1;
            }
            return -1;
        }

        public int getLength() {
            return this.a;
        }

        public int getNextIndex(int i) {
            int i2 = i + 1;
            if (i2 < this.a) {
                return i2;
            }
            return -1;
        }

        public int getPreviousIndex(int i) {
            int i2 = i - 1;
            if (i2 >= 0) {
                return i2;
            }
            return -1;
        }
    }

    ShuffleOrder cloneAndClear();

    ShuffleOrder cloneAndInsert(int i, int i2);

    ShuffleOrder cloneAndRemove(int i, int i2);

    int getFirstIndex();

    int getLastIndex();

    int getLength();

    int getNextIndex(int i);

    int getPreviousIndex(int i);
}
