package org.jsoup.parser;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Locale;
import org.jsoup.UncheckedIOException;
import org.jsoup.helper.Validate;

public final class CharacterReader {
    static final char EOF = '￿';
    static final int maxBufferLen = 32768;
    private static final int maxStringCacheLen = 12;
    private static final int readAheadLimit = 24576;
    private int bufLength;
    private int bufMark;
    private int bufPos;
    private int bufSplitPoint;
    private final char[] charBuf;
    private final Reader reader;
    private int readerPos;
    private final String[] stringCache;

    public CharacterReader(Reader reader2, int i) {
        this.stringCache = new String[512];
        Validate.notNull(reader2);
        Validate.isTrue(reader2.markSupported());
        this.reader = reader2;
        this.charBuf = new char[(i > 32768 ? 32768 : i)];
        bufferUp();
    }

    private void bufferUp() {
        int i = this.bufPos;
        if (i >= this.bufSplitPoint) {
            try {
                this.reader.skip((long) i);
                this.reader.mark(32768);
                int read = this.reader.read(this.charBuf);
                this.reader.reset();
                if (read != -1) {
                    this.bufLength = read;
                    this.readerPos += this.bufPos;
                    this.bufPos = 0;
                    this.bufMark = 0;
                    if (read > readAheadLimit) {
                        read = readAheadLimit;
                    }
                    this.bufSplitPoint = read;
                }
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }

    private static String cacheString(char[] cArr, String[] strArr, int i, int i2) {
        if (i2 > 12) {
            return new String(cArr, i, i2);
        }
        if (i2 < 1) {
            return "";
        }
        int i3 = 0;
        int i4 = i;
        int i5 = 0;
        while (i3 < i2) {
            i5 = (i5 * 31) + cArr[i4];
            i3++;
            i4++;
        }
        int length = i5 & (strArr.length - 1);
        String str = strArr[length];
        if (str == null) {
            String str2 = new String(cArr, i, i2);
            strArr[length] = str2;
            return str2;
        } else if (rangeEquals(cArr, i, i2, str)) {
            return str;
        } else {
            String str3 = new String(cArr, i, i2);
            strArr[length] = str3;
            return str3;
        }
    }

    private boolean isEmptyNoBufferUp() {
        return this.bufPos >= this.bufLength;
    }

    public static boolean rangeEquals(char[] cArr, int i, int i2, String str) {
        if (i2 != str.length()) {
            return false;
        }
        int i3 = 0;
        while (true) {
            int i4 = i2 - 1;
            if (i2 == 0) {
                return true;
            }
            int i5 = i + 1;
            int i6 = i3 + 1;
            if (cArr[i] != str.charAt(i3)) {
                return false;
            }
            i = i5;
            i2 = i4;
            i3 = i6;
        }
    }

    public void advance() {
        this.bufPos++;
    }

    public char consume() {
        char c;
        bufferUp();
        if (isEmptyNoBufferUp()) {
            c = EOF;
        } else {
            c = this.charBuf[this.bufPos];
        }
        this.bufPos++;
        return c;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0021  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x002b A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String consumeData() {
        /*
            r6 = this;
            r6.bufferUp()
            int r0 = r6.bufPos
            int r1 = r6.bufLength
            char[] r2 = r6.charBuf
        L_0x0009:
            int r3 = r6.bufPos
            if (r3 >= r1) goto L_0x001f
            char r4 = r2[r3]
            r5 = 38
            if (r4 == r5) goto L_0x001f
            r5 = 60
            if (r4 == r5) goto L_0x001f
            if (r4 != 0) goto L_0x001a
            goto L_0x001f
        L_0x001a:
            int r3 = r3 + 1
            r6.bufPos = r3
            goto L_0x0009
        L_0x001f:
            if (r3 <= r0) goto L_0x002b
            char[] r1 = r6.charBuf
            java.lang.String[] r2 = r6.stringCache
            int r3 = r3 - r0
            java.lang.String r0 = cacheString(r1, r2, r0, r3)
            goto L_0x002d
        L_0x002b:
            java.lang.String r0 = ""
        L_0x002d:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jsoup.parser.CharacterReader.consumeData():java.lang.String");
    }

    public String consumeDigitSequence() {
        int i;
        char c;
        bufferUp();
        int i2 = this.bufPos;
        while (true) {
            i = this.bufPos;
            if (i < this.bufLength && (c = this.charBuf[i]) >= '0' && c <= '9') {
                this.bufPos = i + 1;
            }
        }
        return cacheString(this.charBuf, this.stringCache, i2, i - i2);
    }

    public String consumeHexSequence() {
        int i;
        char c;
        bufferUp();
        int i2 = this.bufPos;
        while (true) {
            i = this.bufPos;
            if (i < this.bufLength && (((c = this.charBuf[i]) >= '0' && c <= '9') || ((c >= 'A' && c <= 'F') || (c >= 'a' && c <= 'f')))) {
                this.bufPos = i + 1;
            }
        }
        return cacheString(this.charBuf, this.stringCache, i2, i - i2);
    }

    public String consumeLetterSequence() {
        char c;
        bufferUp();
        int i = this.bufPos;
        while (true) {
            int i2 = this.bufPos;
            if (i2 < this.bufLength && (((c = this.charBuf[i2]) >= 'A' && c <= 'Z') || ((c >= 'a' && c <= 'z') || Character.isLetter(c)))) {
                this.bufPos++;
            }
        }
        return cacheString(this.charBuf, this.stringCache, i, this.bufPos - i);
    }

    public String consumeLetterThenDigitSequence() {
        char c;
        bufferUp();
        int i = this.bufPos;
        while (true) {
            int i2 = this.bufPos;
            if (i2 < this.bufLength && (((c = this.charBuf[i2]) >= 'A' && c <= 'Z') || ((c >= 'a' && c <= 'z') || Character.isLetter(c)))) {
                this.bufPos++;
            }
        }
        while (!isEmptyNoBufferUp() && (r1 = this.charBuf[r2]) >= '0' && r1 <= '9') {
            this.bufPos = (r2 = this.bufPos) + 1;
        }
        return cacheString(this.charBuf, this.stringCache, i, this.bufPos - i);
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0035  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x003f A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String consumeTagName() {
        /*
            r6 = this;
            r6.bufferUp()
            int r0 = r6.bufPos
            int r1 = r6.bufLength
            char[] r2 = r6.charBuf
        L_0x0009:
            int r3 = r6.bufPos
            if (r3 >= r1) goto L_0x0033
            char r4 = r2[r3]
            r5 = 9
            if (r4 == r5) goto L_0x0033
            r5 = 10
            if (r4 == r5) goto L_0x0033
            r5 = 13
            if (r4 == r5) goto L_0x0033
            r5 = 12
            if (r4 == r5) goto L_0x0033
            r5 = 32
            if (r4 == r5) goto L_0x0033
            r5 = 47
            if (r4 == r5) goto L_0x0033
            r5 = 62
            if (r4 == r5) goto L_0x0033
            if (r4 != 0) goto L_0x002e
            goto L_0x0033
        L_0x002e:
            int r3 = r3 + 1
            r6.bufPos = r3
            goto L_0x0009
        L_0x0033:
            if (r3 <= r0) goto L_0x003f
            char[] r1 = r6.charBuf
            java.lang.String[] r2 = r6.stringCache
            int r3 = r3 - r0
            java.lang.String r0 = cacheString(r1, r2, r0, r3)
            goto L_0x0041
        L_0x003f:
            java.lang.String r0 = ""
        L_0x0041:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jsoup.parser.CharacterReader.consumeTagName():java.lang.String");
    }

    public String consumeTo(char c) {
        int nextIndexOf = nextIndexOf(c);
        if (nextIndexOf == -1) {
            return consumeToEnd();
        }
        String cacheString = cacheString(this.charBuf, this.stringCache, this.bufPos, nextIndexOf);
        this.bufPos += nextIndexOf;
        return cacheString;
    }

    public String consumeToAny(char... cArr) {
        bufferUp();
        int i = this.bufPos;
        int i2 = this.bufLength;
        char[] cArr2 = this.charBuf;
        loop0:
        while (this.bufPos < i2) {
            for (char c : cArr) {
                if (cArr2[this.bufPos] == c) {
                    break loop0;
                }
            }
            this.bufPos++;
        }
        int i3 = this.bufPos;
        if (i3 > i) {
            return cacheString(this.charBuf, this.stringCache, i, i3 - i);
        }
        return "";
    }

    public String consumeToAnySorted(char... cArr) {
        bufferUp();
        int i = this.bufPos;
        int i2 = this.bufLength;
        char[] cArr2 = this.charBuf;
        while (true) {
            int i3 = this.bufPos;
            if (i3 >= i2 || Arrays.binarySearch(cArr, cArr2[i3]) >= 0) {
                int i4 = this.bufPos;
            } else {
                this.bufPos++;
            }
        }
        int i42 = this.bufPos;
        if (i42 > i) {
            return cacheString(this.charBuf, this.stringCache, i, i42 - i);
        }
        return "";
    }

    public String consumeToEnd() {
        bufferUp();
        char[] cArr = this.charBuf;
        String[] strArr = this.stringCache;
        int i = this.bufPos;
        String cacheString = cacheString(cArr, strArr, i, this.bufLength - i);
        this.bufPos = this.bufLength;
        return cacheString;
    }

    public boolean containsIgnoreCase(String str) {
        Locale locale = Locale.ENGLISH;
        String lowerCase = str.toLowerCase(locale);
        String upperCase = str.toUpperCase(locale);
        if (nextIndexOf((CharSequence) lowerCase) > -1 || nextIndexOf((CharSequence) upperCase) > -1) {
            return true;
        }
        return false;
    }

    public char current() {
        bufferUp();
        if (isEmptyNoBufferUp()) {
            return EOF;
        }
        return this.charBuf[this.bufPos];
    }

    public boolean isEmpty() {
        bufferUp();
        if (this.bufPos >= this.bufLength) {
            return true;
        }
        return false;
    }

    public void mark() {
        this.bufMark = this.bufPos;
    }

    public boolean matchConsume(String str) {
        bufferUp();
        if (!matches(str)) {
            return false;
        }
        this.bufPos = str.length() + this.bufPos;
        return true;
    }

    public boolean matchConsumeIgnoreCase(String str) {
        if (!matchesIgnoreCase(str)) {
            return false;
        }
        this.bufPos = str.length() + this.bufPos;
        return true;
    }

    public boolean matches(char c) {
        return !isEmpty() && this.charBuf[this.bufPos] == c;
    }

    public boolean matchesAny(char... cArr) {
        if (isEmpty()) {
            return false;
        }
        bufferUp();
        char c = this.charBuf[this.bufPos];
        for (char c2 : cArr) {
            if (c2 == c) {
                return true;
            }
        }
        return false;
    }

    public boolean matchesAnySorted(char[] cArr) {
        bufferUp();
        if (isEmpty() || Arrays.binarySearch(cArr, this.charBuf[this.bufPos]) < 0) {
            return false;
        }
        return true;
    }

    public boolean matchesDigit() {
        char c;
        if (!isEmpty() && (c = this.charBuf[this.bufPos]) >= '0' && c <= '9') {
            return true;
        }
        return false;
    }

    public boolean matchesIgnoreCase(String str) {
        bufferUp();
        int length = str.length();
        if (length > this.bufLength - this.bufPos) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (Character.toUpperCase(str.charAt(i)) != Character.toUpperCase(this.charBuf[this.bufPos + i])) {
                return false;
            }
        }
        return true;
    }

    public boolean matchesLetter() {
        if (isEmpty()) {
            return false;
        }
        char c = this.charBuf[this.bufPos];
        if ((c < 'A' || c > 'Z') && ((c < 'a' || c > 'z') && !Character.isLetter(c))) {
            return false;
        }
        return true;
    }

    public int nextIndexOf(char c) {
        bufferUp();
        for (int i = this.bufPos; i < this.bufLength; i++) {
            if (c == this.charBuf[i]) {
                return i - this.bufPos;
            }
        }
        return -1;
    }

    public int pos() {
        return this.readerPos + this.bufPos;
    }

    public void rewindToMark() {
        this.bufPos = this.bufMark;
    }

    public String toString() {
        char[] cArr = this.charBuf;
        int i = this.bufPos;
        return new String(cArr, i, this.bufLength - i);
    }

    public void unconsume() {
        this.bufPos--;
    }

    public boolean matches(String str) {
        bufferUp();
        int length = str.length();
        if (length > this.bufLength - this.bufPos) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (str.charAt(i) != this.charBuf[this.bufPos + i]) {
                return false;
            }
        }
        return true;
    }

    public boolean rangeEquals(int i, int i2, String str) {
        return rangeEquals(this.charBuf, i, i2, str);
    }

    public String consumeTo(String str) {
        int nextIndexOf = nextIndexOf((CharSequence) str);
        if (nextIndexOf == -1) {
            return consumeToEnd();
        }
        String cacheString = cacheString(this.charBuf, this.stringCache, this.bufPos, nextIndexOf);
        this.bufPos += nextIndexOf;
        return cacheString;
    }

    public int nextIndexOf(CharSequence charSequence) {
        bufferUp();
        char charAt = charSequence.charAt(0);
        int i = this.bufPos;
        while (i < this.bufLength) {
            int i2 = 1;
            if (charAt != this.charBuf[i]) {
                do {
                    i++;
                    if (i >= this.bufLength) {
                        break;
                    }
                } while (charAt == this.charBuf[i]);
            }
            int i3 = i + 1;
            int length = (charSequence.length() + i3) - 1;
            int i4 = this.bufLength;
            if (i < i4 && length <= i4) {
                int i5 = i3;
                while (i5 < length && charSequence.charAt(i2) == this.charBuf[i5]) {
                    i5++;
                    i2++;
                }
                if (i5 == length) {
                    return i - this.bufPos;
                }
            }
            i = i3;
        }
        return -1;
    }

    public CharacterReader(Reader reader2) {
        this(reader2, 32768);
    }

    public CharacterReader(String str) {
        this(new StringReader(str), str.length());
    }
}
