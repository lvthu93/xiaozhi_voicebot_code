package com.google.android.exoplayer2.upstream.crypto;

import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.eclipse.paho.client.mqttv3.MqttTopic;

public final class AesFlushingCipher {
    public final Cipher a;
    public final int b;
    public final byte[] c;
    public final byte[] d;
    public int e;

    public AesFlushingCipher(int i, byte[] bArr, long j, long j2) {
        try {
            Cipher instance = Cipher.getInstance("AES/CTR/NoPadding");
            this.a = instance;
            int blockSize = instance.getBlockSize();
            this.b = blockSize;
            this.c = new byte[blockSize];
            this.d = new byte[blockSize];
            long j3 = (long) blockSize;
            int i2 = (int) (j2 % j3);
            instance.init(i, new SecretKeySpec(bArr, Util.splitAtFirst(instance.getAlgorithm(), MqttTopic.TOPIC_LEVEL_SEPARATOR)[0]), new IvParameterSpec(a(j, j2 / j3)));
            if (i2 != 0) {
                updateInPlace(new byte[i2], 0, i2);
            }
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e2) {
            throw new RuntimeException(e2);
        }
    }

    public static byte[] a(long j, long j2) {
        return ByteBuffer.allocate(16).putLong(j).putLong(j2).array();
    }

    public void update(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        boolean z;
        int i4 = i;
        int i5 = i2;
        int i6 = i3;
        do {
            int i7 = this.e;
            byte[] bArr3 = this.d;
            int i8 = this.b;
            if (i7 > 0) {
                bArr2[i6] = (byte) (bArr[i4] ^ bArr3[i8 - i7]);
                i6++;
                i4++;
                this.e = i7 - 1;
                i5--;
            } else {
                try {
                    int update = this.a.update(bArr, i4, i5, bArr2, i6);
                    if (i5 != update) {
                        int i9 = i5 - update;
                        int i10 = 0;
                        boolean z2 = true;
                        if (i9 < i8) {
                            z = true;
                        } else {
                            z = false;
                        }
                        Assertions.checkState(z);
                        int i11 = i6 + update;
                        int i12 = i8 - i9;
                        this.e = i12;
                        try {
                            if (this.a.update(this.c, 0, i12, this.d, 0) != i8) {
                                z2 = false;
                            }
                            Assertions.checkState(z2);
                            while (i10 < i9) {
                                bArr2[i11] = bArr3[i10];
                                i10++;
                                i11++;
                            }
                            return;
                        } catch (ShortBufferException e2) {
                            throw new RuntimeException(e2);
                        }
                    } else {
                        return;
                    }
                } catch (ShortBufferException e3) {
                    throw new RuntimeException(e3);
                }
            }
        } while (i5 != 0);
    }

    public void updateInPlace(byte[] bArr, int i, int i2) {
        update(bArr, i, i2, bArr, i);
    }
}
