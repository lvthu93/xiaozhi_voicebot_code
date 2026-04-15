package org.mozilla.javascript.v8dtoa;

import java.util.Arrays;

public class FastDtoaBuilder {
    static final char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    final char[] chars = new char[25];
    int end = 0;
    boolean formatted = false;
    int point;

    private void toExponentialFormat(int i, int i2) {
        char c;
        int i3 = this.end;
        if (i3 - i > 1) {
            int i4 = i + 1;
            char[] cArr = this.chars;
            System.arraycopy(cArr, i4, cArr, i4 + 1, i3 - i4);
            this.chars[i4] = '.';
            this.end++;
        }
        char[] cArr2 = this.chars;
        int i5 = this.end;
        int i6 = i5 + 1;
        this.end = i6;
        cArr2[i5] = 'e';
        int i7 = i2 - 1;
        if (i7 < 0) {
            i7 = -i7;
            c = '-';
        } else {
            c = '+';
        }
        int i8 = i6 + 1;
        this.end = i8;
        cArr2[i6] = c;
        if (i7 > 99) {
            i8 += 2;
        } else if (i7 > 9) {
            i8++;
        }
        this.end = i8 + 1;
        while (true) {
            int i9 = i8 - 1;
            this.chars[i8] = digits[i7 % 10];
            i7 /= 10;
            if (i7 != 0) {
                i8 = i9;
            } else {
                return;
            }
        }
    }

    private void toFixedFormat(int i, int i2) {
        int i3 = this.point;
        int i4 = this.end;
        if (i3 < i4) {
            if (i2 > 0) {
                char[] cArr = this.chars;
                System.arraycopy(cArr, i3, cArr, i3 + 1, i4 - i3);
                this.chars[this.point] = '.';
                this.end++;
                return;
            }
            int i5 = i + 2;
            int i6 = i5 - i2;
            char[] cArr2 = this.chars;
            System.arraycopy(cArr2, i, cArr2, i6, i4 - i);
            char[] cArr3 = this.chars;
            cArr3[i] = '0';
            cArr3[i + 1] = '.';
            if (i2 < 0) {
                Arrays.fill(cArr3, i5, i6, '0');
            }
            this.end = (2 - i2) + this.end;
        } else if (i3 > i4) {
            Arrays.fill(this.chars, i4, i3, '0');
            int i7 = this.end;
            this.end = (this.point - i7) + i7;
        }
    }

    public void append(char c) {
        char[] cArr = this.chars;
        int i = this.end;
        this.end = i + 1;
        cArr[i] = c;
    }

    public void decreaseLast() {
        char[] cArr = this.chars;
        int i = this.end - 1;
        cArr[i] = (char) (cArr[i] - 1);
    }

    public String format() {
        int i;
        if (!this.formatted) {
            if (this.chars[0] == '-') {
                i = 1;
            } else {
                i = 0;
            }
            int i2 = this.point - i;
            if (i2 < -5 || i2 > 21) {
                toExponentialFormat(i, i2);
            } else {
                toFixedFormat(i, i2);
            }
            this.formatted = true;
        }
        return new String(this.chars, 0, this.end);
    }

    public void reset() {
        this.end = 0;
        this.formatted = false;
    }

    public String toString() {
        return "[chars:" + new String(this.chars, 0, this.end) + ", point:" + this.point + "]";
    }
}
