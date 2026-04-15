package com.grack.nanojson;

import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.BitSet;

public final class JsonReader {
    private boolean first = true;
    private boolean inObject;
    private StringBuilder key = new StringBuilder();
    private int stateIndex = 0;
    private BitSet states = new BitSet();
    private int token;
    private JsonTokener tokener;

    public enum Type {
        OBJECT,
        ARRAY,
        STRING,
        NUMBER,
        BOOLEAN,
        NULL
    }

    public JsonReader(JsonTokener jsonTokener) throws JsonParserException {
        this.tokener = jsonTokener;
        this.token = jsonTokener.advanceToToken(false);
    }

    private JsonParserException createTokenMismatchException(int... iArr) {
        JsonTokener jsonTokener = this.tokener;
        return jsonTokener.createParseException((Exception) null, "token mismatch (expected " + Arrays.toString(iArr) + ", was " + this.token + ")", true);
    }

    public static JsonReader from(InputStream inputStream) throws JsonParserException {
        return new JsonReader(new JsonTokener(inputStream));
    }

    public static JsonReader from(String str) throws JsonParserException {
        return new JsonReader(new JsonTokener((Reader) new StringReader(str)));
    }

    public void array() throws JsonParserException {
        if (this.token == 11) {
            BitSet bitSet = this.states;
            int i = this.stateIndex;
            this.stateIndex = i + 1;
            bitSet.set(i, this.inObject);
            this.inObject = false;
            this.first = true;
            return;
        }
        throw createTokenMismatchException(11);
    }

    public boolean bool() throws JsonParserException {
        int i = this.token;
        if (i == 6) {
            return true;
        }
        if (i == 7) {
            return false;
        }
        throw createTokenMismatchException(6, 7);
    }

    public Type current() throws JsonParserException {
        switch (this.token) {
            case 5:
                return Type.NULL;
            case 6:
            case 7:
                return Type.BOOLEAN;
            case 8:
                return Type.STRING;
            case 9:
                return Type.NUMBER;
            case 10:
                return Type.OBJECT;
            case 11:
                return Type.ARRAY;
            default:
                throw createTokenMismatchException(5, 6, 7, 9, 8, 10, 11);
        }
    }

    public double doubleVal() throws JsonParserException {
        return Double.parseDouble(this.tokener.reusableBuffer.toString());
    }

    public float floatVal() throws JsonParserException {
        return Float.parseFloat(this.tokener.reusableBuffer.toString());
    }

    public int intVal() throws JsonParserException {
        String sb = this.tokener.reusableBuffer.toString();
        return this.tokener.isDouble ? (int) Double.parseDouble(sb) : Integer.parseInt(sb);
    }

    public String key() throws JsonParserException {
        if (this.inObject) {
            return this.key.toString();
        }
        throw this.tokener.createParseException((Exception) null, "Not reading an object", true);
    }

    public long longVal() throws JsonParserException {
        String sb = this.tokener.reusableBuffer.toString();
        return this.tokener.isDouble ? (long) Double.parseDouble(sb) : Long.parseLong(sb);
    }

    public boolean next() throws JsonParserException {
        int i;
        if (this.stateIndex != 0) {
            int advanceToToken = this.tokener.advanceToToken(false);
            this.token = advanceToToken;
            if (this.inObject) {
                if (advanceToToken != 3) {
                    if (!this.first) {
                        if (advanceToToken == 1) {
                            this.token = this.tokener.advanceToToken(false);
                        } else {
                            throw createTokenMismatchException(1, 3);
                        }
                    }
                    if (this.token == 8) {
                        this.key.setLength(0);
                        this.key.append(this.tokener.reusableBuffer);
                        int advanceToToken2 = this.tokener.advanceToToken(false);
                        this.token = advanceToToken2;
                        if (advanceToToken2 != 2) {
                            throw createTokenMismatchException(2);
                        }
                        this.token = this.tokener.advanceToToken(false);
                        i = this.token;
                        if (i != 5 || i == 8 || i == 9 || i == 6 || i == 7 || i == 10 || i == 11) {
                            this.first = false;
                            return true;
                        }
                        throw createTokenMismatchException(5, 8, 9, 6, 7, 10, 11);
                    }
                    throw createTokenMismatchException(8);
                }
            } else if (advanceToToken != 4) {
                if (!this.first) {
                    if (advanceToToken != 1) {
                        throw createTokenMismatchException(1, 4);
                    }
                    this.token = this.tokener.advanceToToken(false);
                }
                i = this.token;
                if (i != 5) {
                }
                this.first = false;
                return true;
            }
            BitSet bitSet = this.states;
            int i2 = this.stateIndex - 1;
            this.stateIndex = i2;
            this.inObject = bitSet.get(i2);
            this.first = false;
            return false;
        }
        throw this.tokener.createParseException((Exception) null, "Unabled to call next() at the root", true);
    }

    public void nul() throws JsonParserException {
        if (this.token != 5) {
            throw createTokenMismatchException(5);
        }
    }

    public Number number() throws JsonParserException {
        if (this.token == 5) {
            return null;
        }
        return new JsonLazyNumber(this.tokener.reusableBuffer.toString(), this.tokener.isDouble);
    }

    public void object() throws JsonParserException {
        if (this.token == 10) {
            BitSet bitSet = this.states;
            int i = this.stateIndex;
            this.stateIndex = i + 1;
            bitSet.set(i, this.inObject);
            this.inObject = true;
            this.first = true;
            return;
        }
        throw createTokenMismatchException(10);
    }

    public boolean pop() throws JsonParserException {
        do {
        } while (!next());
        this.first = false;
        BitSet bitSet = this.states;
        int i = this.stateIndex - 1;
        this.stateIndex = i;
        this.inObject = bitSet.get(i);
        return this.token != 0;
    }

    public String string() throws JsonParserException {
        int i = this.token;
        if (i == 5) {
            return null;
        }
        if (i == 8) {
            return this.tokener.reusableBuffer.toString();
        }
        throw createTokenMismatchException(5, 8);
    }

    public Object value() throws JsonParserException {
        switch (this.token) {
            case 5:
                return null;
            case 6:
                return Boolean.TRUE;
            case 7:
                return Boolean.FALSE;
            case 8:
                return string();
            case 9:
                return number();
            default:
                throw createTokenMismatchException(5, 6, 7, 9, 8);
        }
    }
}
