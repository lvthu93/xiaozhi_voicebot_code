package org.mozilla.javascript.json;

import java.util.ArrayList;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;

public class JsonParser {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private Context cx;
    private int length;
    private int pos;
    private Scriptable scope;
    private String src;

    public static class ParseException extends Exception {
        private static final long serialVersionUID = 4804542791749920772L;

        public ParseException(String str) {
            super(str);
        }

        public ParseException(Exception exc) {
            super(exc);
        }
    }

    public JsonParser(Context context, Scriptable scriptable) {
        this.cx = context;
        this.scope = scriptable;
    }

    private void consume(char c) throws ParseException {
        consumeWhitespace();
        int i = this.pos;
        if (i < this.length) {
            String str = this.src;
            this.pos = i + 1;
            char charAt = str.charAt(i);
            if (charAt != c) {
                throw new ParseException("Expected " + c + " found " + charAt);
            }
            return;
        }
        throw new ParseException("Expected " + c + " but reached end of stream");
    }

    private void consumeWhitespace() {
        while (true) {
            int i = this.pos;
            if (i < this.length) {
                char charAt = this.src.charAt(i);
                if (charAt == 9 || charAt == 10 || charAt == 13 || charAt == ' ') {
                    this.pos++;
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    private static int fromHex(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        char c2 = 'A';
        if (c < 'A' || c > 'F') {
            c2 = 'a';
            if (c < 'a' || c > 'f') {
                return -1;
            }
        }
        return (c - c2) + 10;
    }

    private char nextOrNumberError(int i) throws ParseException {
        int i2 = this.pos;
        int i3 = this.length;
        if (i2 < i3) {
            String str = this.src;
            this.pos = i2 + 1;
            return str.charAt(i2);
        }
        throw numberError(i, i3);
    }

    private ParseException numberError(int i, int i2) {
        return new ParseException("Unsupported number format: " + this.src.substring(i, i2));
    }

    private Object readArray() throws ParseException {
        consumeWhitespace();
        int i = this.pos;
        if (i >= this.length || this.src.charAt(i) != ']') {
            ArrayList arrayList = new ArrayList();
            boolean z = false;
            while (true) {
                int i2 = this.pos;
                if (i2 < this.length) {
                    char charAt = this.src.charAt(i2);
                    if (charAt != ',') {
                        if (charAt != ']') {
                            if (!z) {
                                arrayList.add(readValue());
                                z = true;
                            } else {
                                throw new ParseException("Missing comma in array literal");
                            }
                        } else if (z) {
                            this.pos++;
                            return this.cx.newArray(this.scope, arrayList.toArray());
                        } else {
                            throw new ParseException("Unexpected comma in array literal");
                        }
                    } else if (z) {
                        this.pos++;
                        z = false;
                    } else {
                        throw new ParseException("Unexpected comma in array literal");
                    }
                    consumeWhitespace();
                } else {
                    throw new ParseException("Unterminated array literal");
                }
            }
        } else {
            this.pos++;
            return this.cx.newArray(this.scope, 0);
        }
    }

    private void readDigits() {
        char charAt;
        while (true) {
            int i = this.pos;
            if (i < this.length && (charAt = this.src.charAt(i)) >= '0' && charAt <= '9') {
                this.pos++;
            } else {
                return;
            }
        }
    }

    private Boolean readFalse() throws ParseException {
        int i = this.length;
        int i2 = this.pos;
        if (i - i2 >= 4 && this.src.charAt(i2) == 'a' && this.src.charAt(this.pos + 1) == 'l' && this.src.charAt(this.pos + 2) == 's' && this.src.charAt(this.pos + 3) == 'e') {
            this.pos += 4;
            return Boolean.FALSE;
        }
        throw new ParseException("Unexpected token: f");
    }

    private Object readNull() throws ParseException {
        int i = this.length;
        int i2 = this.pos;
        if (i - i2 >= 3 && this.src.charAt(i2) == 'u' && this.src.charAt(this.pos + 1) == 'l' && this.src.charAt(this.pos + 2) == 'l') {
            this.pos += 3;
            return null;
        }
        throw new ParseException("Unexpected token: n");
    }

    private Number readNumber(char c) throws ParseException {
        char charAt;
        int i = this.pos - 1;
        if (c != '-' || ((c = nextOrNumberError(i)) >= '0' && c <= '9')) {
            if (c != '0') {
                readDigits();
            }
            int i2 = this.pos;
            if (i2 < this.length && this.src.charAt(i2) == '.') {
                this.pos++;
                char nextOrNumberError = nextOrNumberError(i);
                if (nextOrNumberError < '0' || nextOrNumberError > '9') {
                    throw numberError(i, this.pos);
                }
                readDigits();
            }
            int i3 = this.pos;
            if (i3 < this.length && ((charAt = this.src.charAt(i3)) == 'e' || charAt == 'E')) {
                this.pos++;
                char nextOrNumberError2 = nextOrNumberError(i);
                if (nextOrNumberError2 == '-' || nextOrNumberError2 == '+') {
                    nextOrNumberError2 = nextOrNumberError(i);
                }
                if (nextOrNumberError2 < '0' || nextOrNumberError2 > '9') {
                    throw numberError(i, this.pos);
                }
                readDigits();
            }
            double parseDouble = Double.parseDouble(this.src.substring(i, this.pos));
            int i4 = (int) parseDouble;
            if (((double) i4) == parseDouble) {
                return Integer.valueOf(i4);
            }
            return Double.valueOf(parseDouble);
        }
        throw numberError(i, this.pos);
    }

    private Object readObject() throws ParseException {
        consumeWhitespace();
        Scriptable newObject = this.cx.newObject(this.scope);
        int i = this.pos;
        if (i >= this.length || this.src.charAt(i) != '}') {
            boolean z = false;
            while (true) {
                int i2 = this.pos;
                if (i2 < this.length) {
                    String str = this.src;
                    this.pos = i2 + 1;
                    char charAt = str.charAt(i2);
                    if (charAt != '\"') {
                        if (charAt != ',') {
                            if (charAt != '}') {
                                throw new ParseException("Unexpected token in object literal");
                            } else if (z) {
                                return newObject;
                            } else {
                                throw new ParseException("Unexpected comma in object literal");
                            }
                        } else if (z) {
                            z = false;
                        } else {
                            throw new ParseException("Unexpected comma in object literal");
                        }
                    } else if (!z) {
                        String readString = readString();
                        consume(':');
                        Object readValue = readValue();
                        long indexFromString = ScriptRuntime.indexFromString(readString);
                        if (indexFromString < 0) {
                            newObject.put(readString, newObject, readValue);
                        } else {
                            newObject.put((int) indexFromString, newObject, readValue);
                        }
                        z = true;
                    } else {
                        throw new ParseException("Missing comma in object literal");
                    }
                    consumeWhitespace();
                } else {
                    throw new ParseException("Unterminated object literal");
                }
            }
        } else {
            this.pos++;
            return newObject;
        }
    }

    private String readString() throws ParseException {
        char charAt;
        int i = this.pos;
        do {
            int i2 = this.pos;
            if (i2 < this.length) {
                String str = this.src;
                this.pos = i2 + 1;
                charAt = str.charAt(i2);
                if (charAt <= 31) {
                    throw new ParseException("String contains control character");
                } else if (charAt == '\\') {
                }
            }
            StringBuilder sb = new StringBuilder();
            while (true) {
                int i3 = this.pos;
                if (i3 < this.length) {
                    sb.append(this.src, i, i3 - 1);
                    int i4 = this.pos;
                    if (i4 < this.length) {
                        String str2 = this.src;
                        this.pos = i4 + 1;
                        char charAt2 = str2.charAt(i4);
                        if (charAt2 == '\"') {
                            sb.append('\"');
                        } else if (charAt2 == '/') {
                            sb.append('/');
                        } else if (charAt2 == '\\') {
                            sb.append('\\');
                        } else if (charAt2 == 'b') {
                            sb.append(8);
                        } else if (charAt2 == 'f') {
                            sb.append(12);
                        } else if (charAt2 == 'n') {
                            sb.append(10);
                        } else if (charAt2 == 'r') {
                            sb.append(13);
                        } else if (charAt2 == 't') {
                            sb.append(9);
                        } else if (charAt2 == 'u') {
                            int i5 = this.length;
                            int i6 = this.pos;
                            if (i5 - i6 >= 5) {
                                int fromHex = (fromHex(this.src.charAt(i6 + 0)) << 12) | (fromHex(this.src.charAt(this.pos + 1)) << 8) | (fromHex(this.src.charAt(this.pos + 2)) << 4) | fromHex(this.src.charAt(this.pos + 3));
                                if (fromHex >= 0) {
                                    this.pos += 4;
                                    sb.append((char) fromHex);
                                } else {
                                    StringBuilder sb2 = new StringBuilder("Invalid character code: ");
                                    String str3 = this.src;
                                    int i7 = this.pos;
                                    sb2.append(str3.substring(i7, i7 + 4));
                                    throw new ParseException(sb2.toString());
                                }
                            } else {
                                throw new ParseException("Invalid character code: \\u" + this.src.substring(this.pos));
                            }
                        } else {
                            throw new ParseException("Unexpected character in string: '\\" + charAt2 + "'");
                        }
                        i = this.pos;
                        while (true) {
                            int i8 = this.pos;
                            if (i8 >= this.length) {
                                break;
                            }
                            String str4 = this.src;
                            this.pos = i8 + 1;
                            char charAt3 = str4.charAt(i8);
                            if (charAt3 <= 31) {
                                throw new ParseException("String contains control character");
                            } else if (charAt3 == '\\') {
                                continue;
                                break;
                            } else if (charAt3 == '\"') {
                                sb.append(this.src, i, this.pos - 1);
                                return sb.toString();
                            }
                        }
                    } else {
                        throw new ParseException("Unterminated string");
                    }
                } else {
                    throw new ParseException("Unterminated string literal");
                }
            }
        } while (charAt != '\"');
        return this.src.substring(i, this.pos - 1);
    }

    private Boolean readTrue() throws ParseException {
        int i = this.length;
        int i2 = this.pos;
        if (i - i2 >= 3 && this.src.charAt(i2) == 'r' && this.src.charAt(this.pos + 1) == 'u' && this.src.charAt(this.pos + 2) == 'e') {
            this.pos += 3;
            return Boolean.TRUE;
        }
        throw new ParseException("Unexpected token: t");
    }

    private Object readValue() throws ParseException {
        consumeWhitespace();
        int i = this.pos;
        if (i < this.length) {
            String str = this.src;
            this.pos = i + 1;
            char charAt = str.charAt(i);
            if (charAt == '\"') {
                return readString();
            }
            if (charAt != '-') {
                if (charAt == '[') {
                    return readArray();
                }
                if (charAt == 'f') {
                    return readFalse();
                }
                if (charAt == 'n') {
                    return readNull();
                }
                if (charAt == 't') {
                    return readTrue();
                }
                if (charAt == '{') {
                    return readObject();
                }
                switch (charAt) {
                    case '0':
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                    case '7':
                    case '8':
                    case '9':
                        break;
                    default:
                        throw new ParseException("Unexpected token: " + charAt);
                }
            }
            return readNumber(charAt);
        }
        throw new ParseException("Empty JSON string");
    }

    public synchronized Object parseValue(String str) throws ParseException {
        Object readValue;
        if (str != null) {
            try {
                this.pos = 0;
                this.length = str.length();
                this.src = str;
                readValue = readValue();
                consumeWhitespace();
                if (this.pos < this.length) {
                    throw new ParseException("Expected end of stream at char " + this.pos);
                }
            } catch (Throwable th) {
                throw th;
            }
        } else {
            throw new ParseException("Input string may not be null");
        }
        return readValue;
    }
}
