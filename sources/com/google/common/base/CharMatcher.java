package com.google.common.base;

import androidx.core.internal.view.SupportMenu;
import com.google.common.base.Platform;
import java.util.Arrays;
import java.util.BitSet;

public abstract class CharMatcher implements Predicate<Character> {

    public static final class And extends CharMatcher {
        public final CharMatcher c;
        public final CharMatcher f;

        public And(CharMatcher charMatcher, CharMatcher charMatcher2) {
            this.c = (CharMatcher) Preconditions.checkNotNull(charMatcher);
            this.f = (CharMatcher) Preconditions.checkNotNull(charMatcher2);
        }

        @Deprecated
        public /* bridge */ /* synthetic */ boolean apply(Object obj) {
            return CharMatcher.super.apply((Character) obj);
        }

        public final void d(BitSet bitSet) {
            BitSet bitSet2 = new BitSet();
            this.c.d(bitSet2);
            BitSet bitSet3 = new BitSet();
            this.f.d(bitSet3);
            bitSet2.and(bitSet3);
            bitSet.or(bitSet2);
        }

        public boolean matches(char c2) {
            return this.c.matches(c2) && this.f.matches(c2);
        }

        public String toString() {
            return "CharMatcher.and(" + this.c + ", " + this.f + ")";
        }
    }

    public static final class Any extends NamedFastMatcher {
        public static final Any f = new Any();

        public Any() {
            super("CharMatcher.any()");
        }

        public CharMatcher and(CharMatcher charMatcher) {
            return (CharMatcher) Preconditions.checkNotNull(charMatcher);
        }

        public String collapseFrom(CharSequence charSequence, char c) {
            return charSequence.length() == 0 ? "" : String.valueOf(c);
        }

        public int countIn(CharSequence charSequence) {
            return charSequence.length();
        }

        public int indexIn(CharSequence charSequence) {
            return charSequence.length() == 0 ? -1 : 0;
        }

        public int lastIndexIn(CharSequence charSequence) {
            return charSequence.length() - 1;
        }

        public boolean matches(char c) {
            return true;
        }

        public boolean matchesAllOf(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            return true;
        }

        public boolean matchesNoneOf(CharSequence charSequence) {
            return charSequence.length() == 0;
        }

        public CharMatcher negate() {
            return CharMatcher.none();
        }

        public CharMatcher or(CharMatcher charMatcher) {
            Preconditions.checkNotNull(charMatcher);
            return this;
        }

        public String removeFrom(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            return "";
        }

        public String replaceFrom(CharSequence charSequence, char c) {
            char[] cArr = new char[charSequence.length()];
            Arrays.fill(cArr, c);
            return new String(cArr);
        }

        public String trimFrom(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            return "";
        }

        public int indexIn(CharSequence charSequence, int i) {
            int length = charSequence.length();
            Preconditions.checkPositionIndex(i, length);
            if (i == length) {
                return -1;
            }
            return i;
        }

        public String replaceFrom(CharSequence charSequence, CharSequence charSequence2) {
            StringBuilder sb = new StringBuilder(charSequence2.length() * charSequence.length());
            for (int i = 0; i < charSequence.length(); i++) {
                sb.append(charSequence2);
            }
            return sb.toString();
        }
    }

    public static final class AnyOf extends CharMatcher {
        public final char[] c;

        public AnyOf(CharSequence charSequence) {
            char[] charArray = charSequence.toString().toCharArray();
            this.c = charArray;
            Arrays.sort(charArray);
        }

        @Deprecated
        public /* bridge */ /* synthetic */ boolean apply(Object obj) {
            return CharMatcher.super.apply((Character) obj);
        }

        public final void d(BitSet bitSet) {
            for (char c2 : this.c) {
                bitSet.set(c2);
            }
        }

        public boolean matches(char c2) {
            return Arrays.binarySearch(this.c, c2) >= 0;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("CharMatcher.anyOf(\"");
            for (char a : this.c) {
                sb.append(CharMatcher.a(a));
            }
            sb.append("\")");
            return sb.toString();
        }
    }

    public static final class Ascii extends NamedFastMatcher {
        public static final Ascii f = new Ascii();

        public Ascii() {
            super("CharMatcher.ascii()");
        }

        public boolean matches(char c) {
            return c <= 127;
        }
    }

    public static final class BitSetMatcher extends NamedFastMatcher {
        public final BitSet f;

        public BitSetMatcher(BitSet bitSet, String str) {
            super(str);
            this.f = bitSet.length() + 64 < bitSet.size() ? (BitSet) bitSet.clone() : bitSet;
        }

        public final void d(BitSet bitSet) {
            bitSet.or(this.f);
        }

        public boolean matches(char c) {
            return this.f.get(c);
        }
    }

    public static final class BreakingWhitespace extends CharMatcher {
        public static final CharMatcher c = new BreakingWhitespace();

        @Deprecated
        public /* bridge */ /* synthetic */ boolean apply(Object obj) {
            return CharMatcher.super.apply((Character) obj);
        }

        public boolean matches(char c2) {
            if (!(c2 == ' ' || c2 == 133 || c2 == 5760)) {
                if (c2 == 8199) {
                    return false;
                }
                if (!(c2 == 8287 || c2 == 12288 || c2 == 8232 || c2 == 8233)) {
                    switch (c2) {
                        case 9:
                        case 10:
                        case 11:
                        case 12:
                        case 13:
                            break;
                        default:
                            return c2 >= 8192 && c2 <= 8202;
                    }
                }
            }
            return true;
        }

        public String toString() {
            return "CharMatcher.breakingWhitespace()";
        }
    }

    public static final class Digit extends RangesMatcher {
        public static final Digit h = new Digit();

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public Digit() {
            /*
                r6 = this;
                java.lang.String r0 = "0٠۰߀०০੦૦୦௦౦೦൦෦๐໐༠၀႐០᠐᥆᧐᪀᪐᭐᮰᱀᱐꘠꣐꤀꧐꧰꩐꯰０"
                char[] r1 = r0.toCharArray()
                r2 = 37
                char[] r3 = new char[r2]
                r4 = 0
            L_0x000b:
                if (r4 >= r2) goto L_0x0019
                char r5 = r0.charAt(r4)
                int r5 = r5 + 9
                char r5 = (char) r5
                r3[r4] = r5
                int r4 = r4 + 1
                goto L_0x000b
            L_0x0019:
                java.lang.String r0 = "CharMatcher.digit()"
                r6.<init>(r0, r1, r3)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.base.CharMatcher.Digit.<init>():void");
        }
    }

    public static abstract class FastMatcher extends CharMatcher {
        @Deprecated
        public /* bridge */ /* synthetic */ boolean apply(Object obj) {
            return CharMatcher.super.apply((Character) obj);
        }

        public CharMatcher negate() {
            return new NegatedFastMatcher(this);
        }

        public final CharMatcher precomputed() {
            return this;
        }
    }

    public static final class ForPredicate extends CharMatcher {
        public final Predicate<? super Character> c;

        public ForPredicate(Predicate<? super Character> predicate) {
            this.c = (Predicate) Preconditions.checkNotNull(predicate);
        }

        public boolean matches(char c2) {
            return this.c.apply(Character.valueOf(c2));
        }

        public String toString() {
            return "CharMatcher.forPredicate(" + this.c + ")";
        }

        public boolean apply(Character ch) {
            return this.c.apply(Preconditions.checkNotNull(ch));
        }
    }

    public static final class InRange extends FastMatcher {
        public final char c;
        public final char f;

        public InRange(char c2, char c3) {
            boolean z;
            if (c3 >= c2) {
                z = true;
            } else {
                z = false;
            }
            Preconditions.checkArgument(z);
            this.c = c2;
            this.f = c3;
        }

        public final void d(BitSet bitSet) {
            bitSet.set(this.c, this.f + 1);
        }

        public boolean matches(char c2) {
            return this.c <= c2 && c2 <= this.f;
        }

        public String toString() {
            return "CharMatcher.inRange('" + CharMatcher.a(this.c) + "', '" + CharMatcher.a(this.f) + "')";
        }
    }

    public static final class Invisible extends RangesMatcher {
        public static final Invisible h = new Invisible();

        public Invisible() {
            super("CharMatcher.invisible()", "\u0000­؀؜۝܏࣢ ᠎   ⁦　?﻿￹".toCharArray(), "  ­؅؜۝܏࣢ ᠎‏ ⁤⁯　﻿￻".toCharArray());
        }
    }

    public static final class Is extends FastMatcher {
        public final char c;

        public Is(char c2) {
            this.c = c2;
        }

        public CharMatcher and(CharMatcher charMatcher) {
            return charMatcher.matches(this.c) ? this : CharMatcher.none();
        }

        public final void d(BitSet bitSet) {
            bitSet.set(this.c);
        }

        public boolean matches(char c2) {
            return c2 == this.c;
        }

        public CharMatcher negate() {
            return CharMatcher.isNot(this.c);
        }

        public CharMatcher or(CharMatcher charMatcher) {
            return charMatcher.matches(this.c) ? charMatcher : CharMatcher.super.or(charMatcher);
        }

        public String replaceFrom(CharSequence charSequence, char c2) {
            return charSequence.toString().replace(this.c, c2);
        }

        public String toString() {
            return "CharMatcher.is('" + CharMatcher.a(this.c) + "')";
        }
    }

    public static final class IsEither extends FastMatcher {
        public final char c;
        public final char f;

        public IsEither(char c2, char c3) {
            this.c = c2;
            this.f = c3;
        }

        public final void d(BitSet bitSet) {
            bitSet.set(this.c);
            bitSet.set(this.f);
        }

        public boolean matches(char c2) {
            return c2 == this.c || c2 == this.f;
        }

        public String toString() {
            return "CharMatcher.anyOf(\"" + CharMatcher.a(this.c) + CharMatcher.a(this.f) + "\")";
        }
    }

    public static final class IsNot extends FastMatcher {
        public final char c;

        public IsNot(char c2) {
            this.c = c2;
        }

        public CharMatcher and(CharMatcher charMatcher) {
            return charMatcher.matches(this.c) ? CharMatcher.super.and(charMatcher) : charMatcher;
        }

        public final void d(BitSet bitSet) {
            char c2 = this.c;
            bitSet.set(0, c2);
            bitSet.set(c2 + 1, 65536);
        }

        public boolean matches(char c2) {
            return c2 != this.c;
        }

        public CharMatcher negate() {
            return CharMatcher.is(this.c);
        }

        public CharMatcher or(CharMatcher charMatcher) {
            return charMatcher.matches(this.c) ? CharMatcher.any() : this;
        }

        public String toString() {
            return "CharMatcher.isNot('" + CharMatcher.a(this.c) + "')";
        }
    }

    public static final class JavaDigit extends CharMatcher {
        public static final JavaDigit c = new JavaDigit();

        @Deprecated
        public /* bridge */ /* synthetic */ boolean apply(Object obj) {
            return CharMatcher.super.apply((Character) obj);
        }

        public boolean matches(char c2) {
            return Character.isDigit(c2);
        }

        public String toString() {
            return "CharMatcher.javaDigit()";
        }
    }

    public static final class JavaIsoControl extends NamedFastMatcher {
        public static final JavaIsoControl f = new JavaIsoControl();

        public JavaIsoControl() {
            super("CharMatcher.javaIsoControl()");
        }

        public boolean matches(char c) {
            return c <= 31 || (c >= 127 && c <= 159);
        }
    }

    public static final class JavaLetter extends CharMatcher {
        public static final JavaLetter c = new JavaLetter();

        @Deprecated
        public /* bridge */ /* synthetic */ boolean apply(Object obj) {
            return CharMatcher.super.apply((Character) obj);
        }

        public boolean matches(char c2) {
            return Character.isLetter(c2);
        }

        public String toString() {
            return "CharMatcher.javaLetter()";
        }
    }

    public static final class JavaLetterOrDigit extends CharMatcher {
        public static final JavaLetterOrDigit c = new JavaLetterOrDigit();

        @Deprecated
        public /* bridge */ /* synthetic */ boolean apply(Object obj) {
            return CharMatcher.super.apply((Character) obj);
        }

        public boolean matches(char c2) {
            return Character.isLetterOrDigit(c2);
        }

        public String toString() {
            return "CharMatcher.javaLetterOrDigit()";
        }
    }

    public static final class JavaLowerCase extends CharMatcher {
        public static final JavaLowerCase c = new JavaLowerCase();

        @Deprecated
        public /* bridge */ /* synthetic */ boolean apply(Object obj) {
            return CharMatcher.super.apply((Character) obj);
        }

        public boolean matches(char c2) {
            return Character.isLowerCase(c2);
        }

        public String toString() {
            return "CharMatcher.javaLowerCase()";
        }
    }

    public static final class JavaUpperCase extends CharMatcher {
        public static final JavaUpperCase c = new JavaUpperCase();

        @Deprecated
        public /* bridge */ /* synthetic */ boolean apply(Object obj) {
            return CharMatcher.super.apply((Character) obj);
        }

        public boolean matches(char c2) {
            return Character.isUpperCase(c2);
        }

        public String toString() {
            return "CharMatcher.javaUpperCase()";
        }
    }

    public static abstract class NamedFastMatcher extends FastMatcher {
        public final String c;

        public NamedFastMatcher(String str) {
            this.c = (String) Preconditions.checkNotNull(str);
        }

        public final String toString() {
            return this.c;
        }
    }

    public static class Negated extends CharMatcher {
        public final CharMatcher c;

        public Negated(CharMatcher charMatcher) {
            this.c = (CharMatcher) Preconditions.checkNotNull(charMatcher);
        }

        @Deprecated
        public /* bridge */ /* synthetic */ boolean apply(Object obj) {
            return CharMatcher.super.apply((Character) obj);
        }

        public int countIn(CharSequence charSequence) {
            return charSequence.length() - this.c.countIn(charSequence);
        }

        public final void d(BitSet bitSet) {
            BitSet bitSet2 = new BitSet();
            this.c.d(bitSet2);
            bitSet2.flip(0, 65536);
            bitSet.or(bitSet2);
        }

        public boolean matches(char c2) {
            return !this.c.matches(c2);
        }

        public boolean matchesAllOf(CharSequence charSequence) {
            return this.c.matchesNoneOf(charSequence);
        }

        public boolean matchesNoneOf(CharSequence charSequence) {
            return this.c.matchesAllOf(charSequence);
        }

        public CharMatcher negate() {
            return this.c;
        }

        public String toString() {
            return this.c + ".negate()";
        }
    }

    public static class NegatedFastMatcher extends Negated {
        public NegatedFastMatcher(CharMatcher charMatcher) {
            super(charMatcher);
        }

        public final CharMatcher precomputed() {
            return this;
        }
    }

    public static final class None extends NamedFastMatcher {
        public static final None f = new None();

        public None() {
            super("CharMatcher.none()");
        }

        public CharMatcher and(CharMatcher charMatcher) {
            Preconditions.checkNotNull(charMatcher);
            return this;
        }

        public String collapseFrom(CharSequence charSequence, char c) {
            return charSequence.toString();
        }

        public int countIn(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            return 0;
        }

        public int indexIn(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            return -1;
        }

        public int lastIndexIn(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            return -1;
        }

        public boolean matches(char c) {
            return false;
        }

        public boolean matchesAllOf(CharSequence charSequence) {
            return charSequence.length() == 0;
        }

        public boolean matchesNoneOf(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            return true;
        }

        public CharMatcher negate() {
            return CharMatcher.any();
        }

        public CharMatcher or(CharMatcher charMatcher) {
            return (CharMatcher) Preconditions.checkNotNull(charMatcher);
        }

        public String removeFrom(CharSequence charSequence) {
            return charSequence.toString();
        }

        public String replaceFrom(CharSequence charSequence, char c) {
            return charSequence.toString();
        }

        public String trimFrom(CharSequence charSequence) {
            return charSequence.toString();
        }

        public String trimLeadingFrom(CharSequence charSequence) {
            return charSequence.toString();
        }

        public String trimTrailingFrom(CharSequence charSequence) {
            return charSequence.toString();
        }

        public int indexIn(CharSequence charSequence, int i) {
            Preconditions.checkPositionIndex(i, charSequence.length());
            return -1;
        }

        public String replaceFrom(CharSequence charSequence, CharSequence charSequence2) {
            Preconditions.checkNotNull(charSequence2);
            return charSequence.toString();
        }
    }

    public static final class Or extends CharMatcher {
        public final CharMatcher c;
        public final CharMatcher f;

        public Or(CharMatcher charMatcher, CharMatcher charMatcher2) {
            this.c = (CharMatcher) Preconditions.checkNotNull(charMatcher);
            this.f = (CharMatcher) Preconditions.checkNotNull(charMatcher2);
        }

        @Deprecated
        public /* bridge */ /* synthetic */ boolean apply(Object obj) {
            return CharMatcher.super.apply((Character) obj);
        }

        public final void d(BitSet bitSet) {
            this.c.d(bitSet);
            this.f.d(bitSet);
        }

        public boolean matches(char c2) {
            return this.c.matches(c2) || this.f.matches(c2);
        }

        public String toString() {
            return "CharMatcher.or(" + this.c + ", " + this.f + ")";
        }
    }

    public static class RangesMatcher extends CharMatcher {
        public final String c;
        public final char[] f;
        public final char[] g;

        public RangesMatcher(String str, char[] cArr, char[] cArr2) {
            boolean z;
            boolean z2;
            boolean z3;
            this.c = str;
            this.f = cArr;
            this.g = cArr2;
            if (cArr.length == cArr2.length) {
                z = true;
            } else {
                z = false;
            }
            Preconditions.checkArgument(z);
            int i = 0;
            while (i < cArr.length) {
                if (cArr[i] <= cArr2[i]) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                Preconditions.checkArgument(z2);
                int i2 = i + 1;
                if (i2 < cArr.length) {
                    if (cArr2[i] < cArr[i2]) {
                        z3 = true;
                    } else {
                        z3 = false;
                    }
                    Preconditions.checkArgument(z3);
                }
                i = i2;
            }
        }

        @Deprecated
        public /* bridge */ /* synthetic */ boolean apply(Object obj) {
            return CharMatcher.super.apply((Character) obj);
        }

        public boolean matches(char c2) {
            int binarySearch = Arrays.binarySearch(this.f, c2);
            if (binarySearch >= 0) {
                return true;
            }
            int i = (~binarySearch) - 1;
            if (i < 0 || c2 > this.g[i]) {
                return false;
            }
            return true;
        }

        public String toString() {
            return this.c;
        }
    }

    public static final class SingleWidth extends RangesMatcher {
        public static final SingleWidth h = new SingleWidth();

        public SingleWidth() {
            super("CharMatcher.singleWidth()", "\u0000־א׳؀ݐ฀Ḁ℀ﭐﹰ｡".toCharArray(), "ӹ־ת״ۿݿ๿₯℺﷿﻿ￜ".toCharArray());
        }
    }

    public static final class Whitespace extends NamedFastMatcher {
        public static final int f = Integer.numberOfLeadingZeros(31);
        public static final Whitespace g = new Whitespace();

        public Whitespace() {
            super("CharMatcher.whitespace()");
        }

        public final void d(BitSet bitSet) {
            for (int i = 0; i < 32; i++) {
                bitSet.set(" 　\r   　 \u000b　   　 \t     \f 　 　　 \n 　".charAt(i));
            }
        }

        public boolean matches(char c) {
            return " 　\r   　 \u000b　   　 \t     \f 　 　　 \n 　".charAt((48906 * c) >>> f) == c;
        }
    }

    public static String a(char c) {
        char[] cArr = {'\\', 'u', 0, 0, 0, 0};
        for (int i = 0; i < 4; i++) {
            cArr[5 - i] = "0123456789ABCDEF".charAt(c & 15);
            c = (char) (c >> 4);
        }
        return String.copyValueOf(cArr);
    }

    public static CharMatcher any() {
        return Any.f;
    }

    public static CharMatcher anyOf(CharSequence charSequence) {
        int length = charSequence.length();
        if (length == 0) {
            return none();
        }
        if (length == 1) {
            return is(charSequence.charAt(0));
        }
        if (length != 2) {
            return new AnyOf(charSequence);
        }
        return new IsEither(charSequence.charAt(0), charSequence.charAt(1));
    }

    public static CharMatcher ascii() {
        return Ascii.f;
    }

    public static CharMatcher breakingWhitespace() {
        return BreakingWhitespace.c;
    }

    public static CharMatcher c(int i, BitSet bitSet, String str) {
        boolean z;
        int i2;
        if (i == 0) {
            return none();
        }
        if (i == 1) {
            return is((char) bitSet.nextSetBit(0));
        }
        int i3 = 2;
        if (i != 2) {
            int length = bitSet.length();
            if (i > 1023 || length <= i * 4 * 16) {
                z = false;
            } else {
                z = true;
            }
            if (!z) {
                return new BitSetMatcher(bitSet, str);
            }
            int cardinality = bitSet.cardinality();
            boolean z2 = bitSet.get(0);
            if (cardinality != 1) {
                i3 = Integer.highestOneBit(cardinality - 1) << 1;
                while (((double) i3) * 0.5d < ((double) cardinality)) {
                    i3 <<= 1;
                }
            }
            char[] cArr = new char[i3];
            int i4 = i3 - 1;
            int nextSetBit = bitSet.nextSetBit(0);
            long j = 0;
            while (true) {
                long j2 = j;
                if (nextSetBit == -1) {
                    return new SmallCharMatcher(cArr, j2, z2, str);
                }
                j = (1 << nextSetBit) | j2;
                int rotateLeft = Integer.rotateLeft(-862048943 * nextSetBit, 15) * 461845907;
                while (true) {
                    i2 = rotateLeft & i4;
                    if (cArr[i2] == 0) {
                        break;
                    }
                    rotateLeft = i2 + 1;
                }
                cArr[i2] = (char) nextSetBit;
                nextSetBit = bitSet.nextSetBit(nextSetBit + 1);
            }
        } else {
            char nextSetBit2 = (char) bitSet.nextSetBit(0);
            return new IsEither(nextSetBit2, (char) bitSet.nextSetBit(nextSetBit2 + 1));
        }
    }

    @Deprecated
    public static CharMatcher digit() {
        return Digit.h;
    }

    public static CharMatcher forPredicate(Predicate<? super Character> predicate) {
        return predicate instanceof CharMatcher ? (CharMatcher) predicate : new ForPredicate(predicate);
    }

    public static CharMatcher inRange(char c, char c2) {
        return new InRange(c, c2);
    }

    @Deprecated
    public static CharMatcher invisible() {
        return Invisible.h;
    }

    public static CharMatcher is(char c) {
        return new Is(c);
    }

    public static CharMatcher isNot(char c) {
        return new IsNot(c);
    }

    @Deprecated
    public static CharMatcher javaDigit() {
        return JavaDigit.c;
    }

    public static CharMatcher javaIsoControl() {
        return JavaIsoControl.f;
    }

    @Deprecated
    public static CharMatcher javaLetter() {
        return JavaLetter.c;
    }

    @Deprecated
    public static CharMatcher javaLetterOrDigit() {
        return JavaLetterOrDigit.c;
    }

    @Deprecated
    public static CharMatcher javaLowerCase() {
        return JavaLowerCase.c;
    }

    @Deprecated
    public static CharMatcher javaUpperCase() {
        return JavaUpperCase.c;
    }

    public static CharMatcher none() {
        return None.f;
    }

    public static CharMatcher noneOf(CharSequence charSequence) {
        return anyOf(charSequence).negate();
    }

    @Deprecated
    public static CharMatcher singleWidth() {
        return SingleWidth.h;
    }

    public static CharMatcher whitespace() {
        return Whitespace.g;
    }

    public CharMatcher and(CharMatcher charMatcher) {
        return new And(this, charMatcher);
    }

    public final String b(CharSequence charSequence, int i, int i2, char c, StringBuilder sb, boolean z) {
        while (i < i2) {
            char charAt = charSequence.charAt(i);
            if (!matches(charAt)) {
                sb.append(charAt);
                z = false;
            } else if (!z) {
                sb.append(c);
                z = true;
            }
            i++;
        }
        return sb.toString();
    }

    public String collapseFrom(CharSequence charSequence, char c) {
        int length = charSequence.length();
        int i = 0;
        while (i < length) {
            char charAt = charSequence.charAt(i);
            if (matches(charAt)) {
                if (charAt != c || (i != length - 1 && matches(charSequence.charAt(i + 1)))) {
                    StringBuilder sb = new StringBuilder(length);
                    sb.append(charSequence, 0, i);
                    sb.append(c);
                    return b(charSequence, i + 1, length, c, sb, true);
                }
                i++;
            }
            i++;
        }
        return charSequence.toString();
    }

    public int countIn(CharSequence charSequence) {
        int i = 0;
        for (int i2 = 0; i2 < charSequence.length(); i2++) {
            if (matches(charSequence.charAt(i2))) {
                i++;
            }
        }
        return i;
    }

    public void d(BitSet bitSet) {
        for (int i = SupportMenu.USER_MASK; i >= 0; i--) {
            if (matches((char) i)) {
                bitSet.set(i);
            }
        }
    }

    public int indexIn(CharSequence charSequence) {
        return indexIn(charSequence, 0);
    }

    public int lastIndexIn(CharSequence charSequence) {
        for (int length = charSequence.length() - 1; length >= 0; length--) {
            if (matches(charSequence.charAt(length))) {
                return length;
            }
        }
        return -1;
    }

    public abstract boolean matches(char c);

    public boolean matchesAllOf(CharSequence charSequence) {
        for (int length = charSequence.length() - 1; length >= 0; length--) {
            if (!matches(charSequence.charAt(length))) {
                return false;
            }
        }
        return true;
    }

    public boolean matchesAnyOf(CharSequence charSequence) {
        return !matchesNoneOf(charSequence);
    }

    public boolean matchesNoneOf(CharSequence charSequence) {
        return indexIn(charSequence) == -1;
    }

    public CharMatcher negate() {
        return new Negated(this);
    }

    public CharMatcher or(CharMatcher charMatcher) {
        return new Or(this, charMatcher);
    }

    public CharMatcher precomputed() {
        String str;
        Platform.JdkPatternCompiler jdkPatternCompiler = Platform.a;
        BitSet bitSet = new BitSet();
        d(bitSet);
        int cardinality = bitSet.cardinality();
        if (cardinality * 2 <= 65536) {
            return c(cardinality, bitSet, toString());
        }
        bitSet.flip(0, 65536);
        int i = 65536 - cardinality;
        final String charMatcher = toString();
        if (charMatcher.endsWith(".negate()")) {
            str = charMatcher.substring(0, charMatcher.length() - 9);
        } else {
            str = charMatcher.concat(".negate()");
        }
        return new NegatedFastMatcher(c(i, bitSet, str)) {
            public String toString() {
                return charMatcher;
            }
        };
    }

    public String removeFrom(CharSequence charSequence) {
        String charSequence2 = charSequence.toString();
        int indexIn = indexIn(charSequence2);
        if (indexIn == -1) {
            return charSequence2;
        }
        char[] charArray = charSequence2.toCharArray();
        int i = 1;
        while (true) {
            indexIn++;
            while (indexIn != charArray.length) {
                if (matches(charArray[indexIn])) {
                    i++;
                } else {
                    charArray[indexIn - i] = charArray[indexIn];
                    indexIn++;
                }
            }
            return new String(charArray, 0, indexIn - i);
        }
    }

    public String replaceFrom(CharSequence charSequence, char c) {
        String charSequence2 = charSequence.toString();
        int indexIn = indexIn(charSequence2);
        if (indexIn == -1) {
            return charSequence2;
        }
        char[] charArray = charSequence2.toCharArray();
        charArray[indexIn] = c;
        while (true) {
            indexIn++;
            if (indexIn >= charArray.length) {
                return new String(charArray);
            }
            if (matches(charArray[indexIn])) {
                charArray[indexIn] = c;
            }
        }
    }

    public String retainFrom(CharSequence charSequence) {
        return negate().removeFrom(charSequence);
    }

    public String toString() {
        return super.toString();
    }

    public String trimAndCollapseFrom(CharSequence charSequence, char c) {
        int length = charSequence.length();
        int i = length - 1;
        int i2 = 0;
        while (i2 < length && matches(charSequence.charAt(i2))) {
            i2++;
        }
        int i3 = i;
        while (i3 > i2 && matches(charSequence.charAt(i3))) {
            i3--;
        }
        if (i2 == 0 && i3 == i) {
            return collapseFrom(charSequence, c);
        }
        int i4 = i3 + 1;
        return b(charSequence, i2, i4, c, new StringBuilder(i4 - i2), false);
    }

    public String trimFrom(CharSequence charSequence) {
        int length = charSequence.length();
        int i = 0;
        while (i < length && matches(charSequence.charAt(i))) {
            i++;
        }
        int i2 = length - 1;
        while (i2 > i && matches(charSequence.charAt(i2))) {
            i2--;
        }
        return charSequence.subSequence(i, i2 + 1).toString();
    }

    public String trimLeadingFrom(CharSequence charSequence) {
        int length = charSequence.length();
        for (int i = 0; i < length; i++) {
            if (!matches(charSequence.charAt(i))) {
                return charSequence.subSequence(i, length).toString();
            }
        }
        return "";
    }

    public String trimTrailingFrom(CharSequence charSequence) {
        for (int length = charSequence.length() - 1; length >= 0; length--) {
            if (!matches(charSequence.charAt(length))) {
                return charSequence.subSequence(0, length + 1).toString();
            }
        }
        return "";
    }

    @Deprecated
    public boolean apply(Character ch) {
        return matches(ch.charValue());
    }

    public int indexIn(CharSequence charSequence, int i) {
        int length = charSequence.length();
        Preconditions.checkPositionIndex(i, length);
        while (i < length) {
            if (matches(charSequence.charAt(i))) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public String replaceFrom(CharSequence charSequence, CharSequence charSequence2) {
        int length = charSequence2.length();
        if (length == 0) {
            return removeFrom(charSequence);
        }
        int i = 0;
        if (length == 1) {
            return replaceFrom(charSequence, charSequence2.charAt(0));
        }
        String charSequence3 = charSequence.toString();
        int indexIn = indexIn(charSequence3);
        if (indexIn == -1) {
            return charSequence3;
        }
        int length2 = charSequence3.length();
        StringBuilder sb = new StringBuilder(((length2 * 3) / 2) + 16);
        do {
            sb.append(charSequence3, i, indexIn);
            sb.append(charSequence2);
            i = indexIn + 1;
            indexIn = indexIn(charSequence3, i);
        } while (indexIn != -1);
        sb.append(charSequence3, i, length2);
        return sb.toString();
    }
}
