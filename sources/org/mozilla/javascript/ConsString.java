package org.mozilla.javascript;

import java.io.Serializable;
import java.util.ArrayDeque;

public class ConsString implements CharSequence, Serializable {
    private static final long serialVersionUID = -8432806714471372570L;
    private boolean isFlat = false;
    private CharSequence left;
    private final int length;
    private CharSequence right;

    public ConsString(CharSequence charSequence, CharSequence charSequence2) {
        this.left = charSequence;
        this.right = charSequence2;
        this.length = this.right.length() + charSequence.length();
    }

    private synchronized String flatten() {
        if (!this.isFlat) {
            int i = this.length;
            char[] cArr = new char[i];
            ArrayDeque arrayDeque = new ArrayDeque();
            arrayDeque.addFirst(this.left);
            CharSequence charSequence = this.right;
            do {
                if (charSequence instanceof ConsString) {
                    ConsString consString = (ConsString) charSequence;
                    if (consString.isFlat) {
                        charSequence = consString.left;
                    } else {
                        arrayDeque.addFirst(consString.left);
                        charSequence = consString.right;
                        continue;
                    }
                }
                String str = (String) charSequence;
                i -= str.length();
                str.getChars(0, str.length(), cArr, i);
                if (arrayDeque.isEmpty()) {
                    charSequence = null;
                    continue;
                } else {
                    charSequence = (CharSequence) arrayDeque.removeFirst();
                    continue;
                }
            } while (charSequence != null);
            this.left = new String(cArr);
            this.right = "";
            this.isFlat = true;
        }
        return (String) this.left;
    }

    private Object writeReplace() {
        return toString();
    }

    public char charAt(int i) {
        String str;
        if (this.isFlat) {
            str = (String) this.left;
        } else {
            str = flatten();
        }
        return str.charAt(i);
    }

    public int length() {
        return this.length;
    }

    public CharSequence subSequence(int i, int i2) {
        String str;
        if (this.isFlat) {
            str = (String) this.left;
        } else {
            str = flatten();
        }
        return str.substring(i, i2);
    }

    public String toString() {
        return this.isFlat ? (String) this.left : flatten();
    }
}
