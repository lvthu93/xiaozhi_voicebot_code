package com.google.common.base;

import androidx.appcompat.widget.ActivityChooserView;
import com.google.common.base.AbstractIterator;
import com.google.common.base.Platform;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public final class Splitter {
    public final CharMatcher a;
    public final boolean b;
    public final Strategy c;
    public final int d;

    public static final class MapSplitter {
        public final Splitter a;
        public final Splitter b;

        public MapSplitter(Splitter splitter, Splitter splitter2) {
            this.a = splitter;
            this.b = (Splitter) Preconditions.checkNotNull(splitter2);
        }

        public Map<String, String> split(CharSequence charSequence) {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (String next : this.a.split(charSequence)) {
                Splitter splitter = this.b;
                Iterator<String> it = splitter.c.iterator(splitter, next);
                Preconditions.checkArgument(it.hasNext(), "Chunk [%s] is not a valid entry", (Object) next);
                String next2 = it.next();
                Preconditions.checkArgument(!linkedHashMap.containsKey(next2), "Duplicate key [%s] found.", (Object) next2);
                Preconditions.checkArgument(it.hasNext(), "Chunk [%s] is not a valid entry", (Object) next);
                linkedHashMap.put(next2, it.next());
                Preconditions.checkArgument(!it.hasNext(), "Chunk [%s] is not a valid entry", (Object) next);
            }
            return Collections.unmodifiableMap(linkedHashMap);
        }
    }

    public static abstract class SplittingIterator extends AbstractIterator<String> {
        public final CharSequence g;
        public final CharMatcher h;
        public final boolean i;
        public int j = 0;
        public int k;

        public SplittingIterator(Splitter splitter, CharSequence charSequence) {
            this.h = splitter.a;
            this.i = splitter.b;
            this.k = splitter.d;
            this.g = charSequence;
        }

        public final Object a() {
            CharSequence charSequence;
            CharMatcher charMatcher;
            int i2;
            int i3 = this.j;
            while (true) {
                int i4 = this.j;
                if (i4 != -1) {
                    int separatorStart = separatorStart(i4);
                    charSequence = this.g;
                    if (separatorStart == -1) {
                        separatorStart = charSequence.length();
                        this.j = -1;
                    } else {
                        this.j = separatorEnd(separatorStart);
                    }
                    int i5 = this.j;
                    if (i5 == i3) {
                        int i6 = i5 + 1;
                        this.j = i6;
                        if (i6 > charSequence.length()) {
                            this.j = -1;
                        }
                    } else {
                        while (true) {
                            charMatcher = this.h;
                            if (i3 < separatorStart && charMatcher.matches(charSequence.charAt(i3))) {
                                i3++;
                            }
                        }
                        while (i2 > i3) {
                            int i7 = i2 - 1;
                            if (!charMatcher.matches(charSequence.charAt(i7))) {
                                break;
                            }
                            separatorStart = i7;
                        }
                        if (!this.i || i3 != i2) {
                            int i8 = this.k;
                        } else {
                            i3 = this.j;
                        }
                    }
                } else {
                    this.c = AbstractIterator.State.DONE;
                    return null;
                }
            }
            int i82 = this.k;
            if (i82 == 1) {
                i2 = charSequence.length();
                this.j = -1;
                while (i2 > i3) {
                    int i9 = i2 - 1;
                    if (!charMatcher.matches(charSequence.charAt(i9))) {
                        break;
                    }
                    i2 = i9;
                }
            } else {
                this.k = i82 - 1;
            }
            return charSequence.subSequence(i3, i2).toString();
        }

        public abstract int separatorEnd(int i2);

        public abstract int separatorStart(int i2);
    }

    public interface Strategy {
        Iterator<String> iterator(Splitter splitter, CharSequence charSequence);
    }

    public Splitter(Strategy strategy) {
        this(strategy, false, CharMatcher.none(), ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
    }

    public static Splitter fixedLength(final int i) {
        boolean z;
        if (i > 0) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z, "The length may not be less than 1");
        return new Splitter(new Strategy() {
            public SplittingIterator iterator(Splitter splitter, CharSequence charSequence) {
                return new SplittingIterator(splitter, charSequence) {
                    public int separatorEnd(int i) {
                        return i;
                    }

                    public int separatorStart(int i) {
                        int i2 = i + i;
                        if (i2 < this.g.length()) {
                            return i2;
                        }
                        return -1;
                    }
                };
            }
        });
    }

    public static Splitter on(char c2) {
        return on(CharMatcher.is(c2));
    }

    public static Splitter onPattern(String str) {
        Platform.JdkPatternCompiler jdkPatternCompiler = Platform.a;
        Preconditions.checkNotNull(str);
        final CommonPattern compile = Platform.a.compile(str);
        Preconditions.checkArgument(!compile.matcher("").matches(), "The pattern may not match the empty string: %s", (Object) compile);
        return new Splitter(new Strategy() {
            public SplittingIterator iterator(Splitter splitter, CharSequence charSequence) {
                final CommonMatcher matcher = CommonPattern.this.matcher(charSequence);
                return new SplittingIterator(splitter, charSequence) {
                    public int separatorEnd(int i) {
                        return matcher.end();
                    }

                    public int separatorStart(int i) {
                        CommonMatcher commonMatcher = matcher;
                        if (commonMatcher.find(i)) {
                            return commonMatcher.start();
                        }
                        return -1;
                    }
                };
            }
        });
    }

    public Splitter limit(int i) {
        boolean z;
        if (i > 0) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z, "must be greater than zero: %s", i);
        return new Splitter(this.c, this.b, this.a, i);
    }

    public Splitter omitEmptyStrings() {
        return new Splitter(this.c, true, this.a, this.d);
    }

    public Iterable<String> split(final CharSequence charSequence) {
        Preconditions.checkNotNull(charSequence);
        return new Iterable<String>() {
            public Iterator<String> iterator() {
                Splitter splitter = Splitter.this;
                return splitter.c.iterator(splitter, charSequence);
            }

            public String toString() {
                Joiner on = Joiner.on(", ");
                StringBuilder sb = new StringBuilder();
                sb.append('[');
                StringBuilder appendTo = on.appendTo(sb, (Iterable<?>) this);
                appendTo.append(']');
                return appendTo.toString();
            }
        };
    }

    public List<String> splitToList(CharSequence charSequence) {
        Preconditions.checkNotNull(charSequence);
        Iterator<String> it = this.c.iterator(this, charSequence);
        ArrayList arrayList = new ArrayList();
        while (it.hasNext()) {
            arrayList.add(it.next());
        }
        return Collections.unmodifiableList(arrayList);
    }

    public Splitter trimResults() {
        return trimResults(CharMatcher.whitespace());
    }

    public MapSplitter withKeyValueSeparator(String str) {
        return withKeyValueSeparator(on(str));
    }

    public Splitter(Strategy strategy, boolean z, CharMatcher charMatcher, int i) {
        this.c = strategy;
        this.b = z;
        this.a = charMatcher;
        this.d = i;
    }

    public static Splitter on(final CharMatcher charMatcher) {
        Preconditions.checkNotNull(charMatcher);
        return new Splitter(new Strategy() {
            public SplittingIterator iterator(Splitter splitter, CharSequence charSequence) {
                return new SplittingIterator(splitter, charSequence) {
                    public final int separatorEnd(int i) {
                        return i + 1;
                    }

                    public final int separatorStart(int i) {
                        return charMatcher.indexIn(this.g, i);
                    }
                };
            }
        });
    }

    public Splitter trimResults(CharMatcher charMatcher) {
        Preconditions.checkNotNull(charMatcher);
        return new Splitter(this.c, this.b, charMatcher, this.d);
    }

    public MapSplitter withKeyValueSeparator(char c2) {
        return withKeyValueSeparator(on(c2));
    }

    public MapSplitter withKeyValueSeparator(Splitter splitter) {
        return new MapSplitter(this, splitter);
    }

    public static Splitter on(final String str) {
        Preconditions.checkArgument(str.length() != 0, "The separator may not be the empty string.");
        if (str.length() == 1) {
            return on(str.charAt(0));
        }
        return new Splitter(new Strategy() {
            public SplittingIterator iterator(Splitter splitter, CharSequence charSequence) {
                return new SplittingIterator(splitter, charSequence) {
                    public int separatorEnd(int i) {
                        return str.length() + i;
                    }

                    public int separatorStart(int i) {
                        AnonymousClass2 r0 = AnonymousClass2.this;
                        int length = str.length();
                        CharSequence charSequence = this.g;
                        int length2 = charSequence.length() - length;
                        while (i <= length2) {
                            int i2 = 0;
                            while (i2 < length) {
                                if (charSequence.charAt(i2 + i) != str.charAt(i2)) {
                                    i++;
                                } else {
                                    i2++;
                                }
                            }
                            return i;
                        }
                        return -1;
                    }
                };
            }
        });
    }

    public static Splitter on(Pattern pattern) {
        final JdkPattern jdkPattern = new JdkPattern(pattern);
        Preconditions.checkArgument(!jdkPattern.matcher("").matches(), "The pattern may not match the empty string: %s", (Object) jdkPattern);
        return new Splitter(new Strategy() {
            public SplittingIterator iterator(Splitter splitter, CharSequence charSequence) {
                final CommonMatcher matcher = CommonPattern.this.matcher(charSequence);
                return new SplittingIterator(splitter, charSequence) {
                    public int separatorEnd(int i) {
                        return matcher.end();
                    }

                    public int separatorStart(int i) {
                        CommonMatcher commonMatcher = matcher;
                        if (commonMatcher.find(i)) {
                            return commonMatcher.start();
                        }
                        return -1;
                    }
                };
            }
        });
    }
}
