package com.google.common.collect;

import androidx.appcompat.widget.ActivityChooserView;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.lang.Comparable;
import java.math.BigInteger;
import java.util.NoSuchElementException;

public abstract class DiscreteDomain<C extends Comparable> {
    public final boolean c;

    public static final class BigIntegerDomain extends DiscreteDomain<BigInteger> implements Serializable {
        public static final BigIntegerDomain f = new BigIntegerDomain();
        public static final BigInteger g = BigInteger.valueOf(Long.MIN_VALUE);
        public static final BigInteger h = BigInteger.valueOf(Long.MAX_VALUE);
        private static final long serialVersionUID = 0;

        public BigIntegerDomain() {
            super(true);
        }

        private Object readResolve() {
            return f;
        }

        public final Comparable a(Comparable comparable, long j) {
            CollectPreconditions.c(j);
            return ((BigInteger) comparable).add(BigInteger.valueOf(j));
        }

        public String toString() {
            return "DiscreteDomain.bigIntegers()";
        }

        public long distance(BigInteger bigInteger, BigInteger bigInteger2) {
            return bigInteger2.subtract(bigInteger).max(g).min(h).longValue();
        }

        public BigInteger next(BigInteger bigInteger) {
            return bigInteger.add(BigInteger.ONE);
        }

        public BigInteger previous(BigInteger bigInteger) {
            return bigInteger.subtract(BigInteger.ONE);
        }
    }

    public static final class IntegerDomain extends DiscreteDomain<Integer> implements Serializable {
        public static final IntegerDomain f = new IntegerDomain();
        private static final long serialVersionUID = 0;

        public IntegerDomain() {
            super(true);
        }

        private Object readResolve() {
            return f;
        }

        public final Comparable a(Comparable comparable, long j) {
            CollectPreconditions.c(j);
            return Integer.valueOf(y3.a(((Integer) comparable).longValue() + j));
        }

        public String toString() {
            return "DiscreteDomain.integers()";
        }

        public long distance(Integer num, Integer num2) {
            return ((long) num2.intValue()) - ((long) num.intValue());
        }

        public Integer maxValue() {
            return Integer.valueOf(ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
        }

        public Integer minValue() {
            return Integer.MIN_VALUE;
        }

        public Integer next(Integer num) {
            int intValue = num.intValue();
            if (intValue == Integer.MAX_VALUE) {
                return null;
            }
            return Integer.valueOf(intValue + 1);
        }

        public Integer previous(Integer num) {
            int intValue = num.intValue();
            if (intValue == Integer.MIN_VALUE) {
                return null;
            }
            return Integer.valueOf(intValue - 1);
        }
    }

    public static final class LongDomain extends DiscreteDomain<Long> implements Serializable {
        public static final LongDomain f = new LongDomain();
        private static final long serialVersionUID = 0;

        public LongDomain() {
            super(true);
        }

        private Object readResolve() {
            return f;
        }

        public final Comparable a(Comparable comparable, long j) {
            boolean z;
            Long l = (Long) comparable;
            CollectPreconditions.c(j);
            long longValue = l.longValue() + j;
            if (longValue < 0) {
                if (l.longValue() < 0) {
                    z = true;
                } else {
                    z = false;
                }
                Preconditions.checkArgument(z, "overflow");
            }
            return Long.valueOf(longValue);
        }

        public String toString() {
            return "DiscreteDomain.longs()";
        }

        public long distance(Long l, Long l2) {
            long longValue = l2.longValue() - l.longValue();
            if (l2.longValue() > l.longValue() && longValue < 0) {
                return Long.MAX_VALUE;
            }
            if (l2.longValue() >= l.longValue() || longValue <= 0) {
                return longValue;
            }
            return Long.MIN_VALUE;
        }

        public Long maxValue() {
            return Long.MAX_VALUE;
        }

        public Long minValue() {
            return Long.MIN_VALUE;
        }

        public Long next(Long l) {
            long longValue = l.longValue();
            if (longValue == Long.MAX_VALUE) {
                return null;
            }
            return Long.valueOf(longValue + 1);
        }

        public Long previous(Long l) {
            long longValue = l.longValue();
            if (longValue == Long.MIN_VALUE) {
                return null;
            }
            return Long.valueOf(longValue - 1);
        }
    }

    public DiscreteDomain() {
        this(false);
    }

    public static DiscreteDomain<BigInteger> bigIntegers() {
        return BigIntegerDomain.f;
    }

    public static DiscreteDomain<Integer> integers() {
        return IntegerDomain.f;
    }

    public static DiscreteDomain<Long> longs() {
        return LongDomain.f;
    }

    public C a(C c2, long j) {
        CollectPreconditions.c(j);
        for (long j2 = 0; j2 < j; j2++) {
            c2 = next(c2);
        }
        return c2;
    }

    public abstract long distance(C c2, C c3);

    public C maxValue() {
        throw new NoSuchElementException();
    }

    public C minValue() {
        throw new NoSuchElementException();
    }

    public abstract C next(C c2);

    public abstract C previous(C c2);

    public DiscreteDomain(boolean z) {
        this.c = z;
    }
}
