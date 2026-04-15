package org.eclipse.paho.client.mqttv3.internal.websocket;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import org.mozilla.javascript.Token;

public class WebSocketFrame {
    public static final int frameLengthOverhead = 6;
    private boolean closeFlag = false;
    private boolean fin;
    private byte opcode;
    private byte[] payload;

    public WebSocketFrame(byte b, boolean z, byte[] bArr) {
        this.opcode = b;
        this.fin = z;
        if (bArr != null) {
            this.payload = (byte[]) bArr.clone();
        }
    }

    public static void appendFinAndOpCode(ByteBuffer byteBuffer, byte b, boolean z) {
        byteBuffer.put((byte) ((b & 15) | (z ? (byte) 128 : 0)));
    }

    private static void appendLength(ByteBuffer byteBuffer, int i, boolean z) {
        int i2;
        if (i >= 0) {
            if (z) {
                i2 = -128;
            } else {
                i2 = 0;
            }
            if (i > 65535) {
                byteBuffer.put((byte) (i2 | Token.VOID));
                byteBuffer.put((byte) 0);
                byteBuffer.put((byte) 0);
                byteBuffer.put((byte) 0);
                byteBuffer.put((byte) 0);
                byteBuffer.put((byte) ((i >> 24) & 255));
                byteBuffer.put((byte) ((i >> 16) & 255));
                byteBuffer.put((byte) ((i >> 8) & 255));
                byteBuffer.put((byte) (i & 255));
            } else if (i >= 126) {
                byteBuffer.put((byte) (i2 | Token.FINALLY));
                byteBuffer.put((byte) (i >> 8));
                byteBuffer.put((byte) (i & 255));
            } else {
                byteBuffer.put((byte) (i | i2));
            }
        } else {
            throw new IllegalArgumentException("Length cannot be negative");
        }
    }

    public static void appendLengthAndMask(ByteBuffer byteBuffer, int i, byte[] bArr) {
        if (bArr != null) {
            appendLength(byteBuffer, i, true);
            byteBuffer.put(bArr);
            return;
        }
        appendLength(byteBuffer, i, false);
    }

    public static byte[] generateMaskingKey() {
        SecureRandom secureRandom = new SecureRandom();
        return new byte[]{(byte) secureRandom.nextInt(255), (byte) secureRandom.nextInt(255), (byte) secureRandom.nextInt(255), (byte) secureRandom.nextInt(255)};
    }

    private void setFinAndOpCode(byte b) {
        boolean z;
        if ((b & 128) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.fin = z;
        this.opcode = (byte) (b & 15);
    }

    public byte[] encodeFrame() {
        byte[] bArr = this.payload;
        int length = bArr.length + 6;
        if (bArr.length > 65535) {
            length += 8;
        } else if (bArr.length >= 126) {
            length += 2;
        }
        ByteBuffer allocate = ByteBuffer.allocate(length);
        appendFinAndOpCode(allocate, this.opcode, this.fin);
        byte[] generateMaskingKey = generateMaskingKey();
        appendLengthAndMask(allocate, this.payload.length, generateMaskingKey);
        int i = 0;
        while (true) {
            byte[] bArr2 = this.payload;
            if (i >= bArr2.length) {
                allocate.flip();
                return allocate.array();
            }
            byte b = (byte) (bArr2[i] ^ generateMaskingKey[i % 4]);
            bArr2[i] = b;
            allocate.put(b);
            i++;
        }
    }

    public byte getOpcode() {
        return this.opcode;
    }

    public byte[] getPayload() {
        return this.payload;
    }

    public boolean isCloseFlag() {
        return this.closeFlag;
    }

    public boolean isFin() {
        return this.fin;
    }

    public WebSocketFrame(byte[] bArr) {
        byte[] bArr2;
        int i = 0;
        ByteBuffer wrap = ByteBuffer.wrap(bArr);
        setFinAndOpCode(wrap.get());
        byte b = wrap.get();
        boolean z = (b & 128) != 0;
        int i2 = (byte) (b & Byte.MAX_VALUE);
        int i3 = i2 == 127 ? 8 : i2 == 126 ? 2 : 0;
        while (true) {
            i3--;
            if (i3 <= 0) {
                break;
            }
            i2 |= (wrap.get() & 255) << (i3 * 8);
        }
        if (z) {
            bArr2 = new byte[4];
            wrap.get(bArr2, 0, 4);
        } else {
            bArr2 = null;
        }
        byte[] bArr3 = new byte[i2];
        this.payload = bArr3;
        wrap.get(bArr3, 0, i2);
        if (z) {
            while (true) {
                byte[] bArr4 = this.payload;
                if (i < bArr4.length) {
                    bArr4[i] = (byte) (bArr4[i] ^ bArr2[i % 4]);
                    i++;
                } else {
                    return;
                }
            }
        }
    }

    public WebSocketFrame(InputStream inputStream) throws IOException {
        byte[] bArr;
        int i = 0;
        setFinAndOpCode((byte) inputStream.read());
        byte b = this.opcode;
        int i2 = 8;
        if (b == 2) {
            byte read = (byte) inputStream.read();
            boolean z = (read & 128) != 0;
            int i3 = (byte) (read & Byte.MAX_VALUE);
            i2 = i3 != 127 ? i3 == 126 ? 2 : 0 : i2;
            i3 = i2 > 0 ? 0 : i3;
            while (true) {
                i2--;
                if (i2 < 0) {
                    break;
                }
                i3 |= (((byte) inputStream.read()) & 255) << (i2 * 8);
            }
            if (z) {
                bArr = new byte[4];
                inputStream.read(bArr, 0, 4);
            } else {
                bArr = null;
            }
            byte[] bArr2 = bArr;
            this.payload = new byte[i3];
            int i4 = i3;
            int i5 = 0;
            while (i5 != i3) {
                int read2 = inputStream.read(this.payload, i5, i4);
                i5 += read2;
                i4 -= read2;
            }
            if (z) {
                while (true) {
                    byte[] bArr3 = this.payload;
                    if (i < bArr3.length) {
                        bArr3[i] = (byte) (bArr3[i] ^ bArr2[i % 4]);
                        i++;
                    } else {
                        return;
                    }
                }
            }
        } else if (b == 8) {
            this.closeFlag = true;
        } else {
            throw new IOException("Invalid Frame: Opcode: " + this.opcode);
        }
    }
}
