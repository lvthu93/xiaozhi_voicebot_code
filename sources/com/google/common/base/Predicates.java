package com.google.common.base;

import com.google.common.base.Platform;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

public final class Predicates {

    public static class AndPredicate<T> implements Predicate<T>, Serializable {
        private static final long serialVersionUID = 0;
        public final List<? extends Predicate<? super T>> c;

        public AndPredicate() {
            throw null;
        }

        public AndPredicate(List list) {
            this.c = list;
        }

        public boolean apply(T t) {
            int i = 0;
            while (true) {
                List<? extends Predicate<? super T>> list = this.c;
                if (i >= list.size()) {
                    return true;
                }
                if (!((Predicate) list.get(i)).apply(t)) {
                    return false;
                }
                i++;
            }
        }

        public boolean equals(Object obj) {
            if (obj instanceof AndPredicate) {
                return this.c.equals(((AndPredicate) obj).c);
            }
            return false;
        }

        public int hashCode() {
            return this.c.hashCode() + 306654252;
        }

        public String toString() {
            return Predicates.a("and", this.c);
        }
    }

    public static class CompositionPredicate<A, B> implements Predicate<A>, Serializable {
        private static final long serialVersionUID = 0;
        public final Predicate<B> c;
        public final Function<A, ? extends B> f;

        public CompositionPredicate() {
            throw null;
        }

        public CompositionPredicate(Predicate predicate, Function function) {
            this.c = (Predicate) Preconditions.checkNotNull(predicate);
            this.f = (Function) Preconditions.checkNotNull(function);
        }

        public boolean apply(A a) {
            return this.c.apply(this.f.apply(a));
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof CompositionPredicate)) {
                return false;
            }
            CompositionPredicate compositionPredicate = (CompositionPredicate) obj;
            if (!this.f.equals(compositionPredicate.f) || !this.c.equals(compositionPredicate.c)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return this.f.hashCode() ^ this.c.hashCode();
        }

        public String toString() {
            return this.c + "(" + this.f + ")";
        }
    }

    public static class ContainsPatternFromStringPredicate extends ContainsPatternPredicate {
        private static final long serialVersionUID = 0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public ContainsPatternFromStringPredicate(String str) {
            super(Platform.a.compile(str));
            Platform.JdkPatternCompiler jdkPatternCompiler = Platform.a;
            Preconditions.checkNotNull(str);
        }

        public String toString() {
            return y2.k(new StringBuilder("Predicates.containsPattern("), this.c.pattern(), ")");
        }
    }

    public static class ContainsPatternPredicate implements Predicate<CharSequence>, Serializable {
        private static final long serialVersionUID = 0;
        public final CommonPattern c;

        public ContainsPatternPredicate(CommonPattern commonPattern) {
            this.c = (CommonPattern) Preconditions.checkNotNull(commonPattern);
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof ContainsPatternPredicate)) {
                return false;
            }
            ContainsPatternPredicate containsPatternPredicate = (ContainsPatternPredicate) obj;
            CommonPattern commonPattern = this.c;
            if (!Objects.equal(commonPattern.pattern(), containsPatternPredicate.c.pattern()) || commonPattern.flags() != containsPatternPredicate.c.flags()) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            CommonPattern commonPattern = this.c;
            return Objects.hashCode(commonPattern.pattern(), Integer.valueOf(commonPattern.flags()));
        }

        public String toString() {
            CommonPattern commonPattern = this.c;
            return y2.j("Predicates.contains(", MoreObjects.toStringHelper((Object) commonPattern).add("pattern", (Object) commonPattern.pattern()).add("pattern.flags", commonPattern.flags()).toString(), ")");
        }

        public boolean apply(CharSequence charSequence) {
            return this.c.matcher(charSequence).find();
        }
    }

    public static class InPredicate<T> implements Predicate<T>, Serializable {
        private static final long serialVersionUID = 0;
        public final Collection<?> c;

        public InPredicate() {
            throw null;
        }

        public InPredicate(Collection collection) {
            this.c = (Collection) Preconditions.checkNotNull(collection);
        }

        public boolean apply(T t) {
            try {
                return this.c.contains(t);
            } catch (ClassCastException | NullPointerException unused) {
                return false;
            }
        }

        public boolean equals(Object obj) {
            if (obj instanceof InPredicate) {
                return this.c.equals(((InPredicate) obj).c);
            }
            return false;
        }

        public int hashCode() {
            return this.c.hashCode();
        }

        public String toString() {
            return "Predicates.in(" + this.c + ")";
        }
    }

    public static class InstanceOfPredicate implements Predicate<Object>, Serializable {
        private static final long serialVersionUID = 0;
        public final Class<?> c;

        public InstanceOfPredicate() {
            throw null;
        }

        public InstanceOfPredicate(Class cls) {
            this.c = (Class) Preconditions.checkNotNull(cls);
        }

        public boolean apply(Object obj) {
            return this.c.isInstance(obj);
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof InstanceOfPredicate) || this.c != ((InstanceOfPredicate) obj).c) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return this.c.hashCode();
        }

        public String toString() {
            return "Predicates.instanceOf(" + this.c.getName() + ")";
        }
    }

    public static class IsEqualToPredicate<T> implements Predicate<T>, Serializable {
        private static final long serialVersionUID = 0;
        public final T c;

        public IsEqualToPredicate() {
            throw null;
        }

        public IsEqualToPredicate(Object obj) {
            this.c = obj;
        }

        public boolean apply(T t) {
            return this.c.equals(t);
        }

        public boolean equals(Object obj) {
            if (obj instanceof IsEqualToPredicate) {
                return this.c.equals(((IsEqualToPredicate) obj).c);
            }
            return false;
        }

        public int hashCode() {
            return this.c.hashCode();
        }

        public String toString() {
            return "Predicates.equalTo(" + this.c + ")";
        }
    }

    public static class NotPredicate<T> implements Predicate<T>, Serializable {
        private static final long serialVersionUID = 0;
        public final Predicate<T> c;

        public NotPredicate(Predicate<T> predicate) {
            this.c = (Predicate) Preconditions.checkNotNull(predicate);
        }

        public boolean apply(T t) {
            return !this.c.apply(t);
        }

        public boolean equals(Object obj) {
            if (obj instanceof NotPredicate) {
                return this.c.equals(((NotPredicate) obj).c);
            }
            return false;
        }

        public int hashCode() {
            return ~this.c.hashCode();
        }

        public String toString() {
            return "Predicates.not(" + this.c + ")";
        }
    }

    public enum ObjectPredicate implements Predicate<Object> {
        ;

        /* access modifiers changed from: public */
        ObjectPredicate() {
            throw null;
        }
    }

    public static class OrPredicate<T> implements Predicate<T>, Serializable {
        private static final long serialVersionUID = 0;
        public final List<? extends Predicate<? super T>> c;

        public OrPredicate() {
            throw null;
        }

        public OrPredicate(List list) {
            this.c = list;
        }

        public boolean apply(T t) {
            int i = 0;
            while (true) {
                List<? extends Predicate<? super T>> list = this.c;
                if (i >= list.size()) {
                    return false;
                }
                if (((Predicate) list.get(i)).apply(t)) {
                    return true;
                }
                i++;
            }
        }

        public boolean equals(Object obj) {
            if (obj instanceof OrPredicate) {
                return this.c.equals(((OrPredicate) obj).c);
            }
            return false;
        }

        public int hashCode() {
            return this.c.hashCode() + 87855567;
        }

        public String toString() {
            return Predicates.a("or", this.c);
        }
    }

    public static class SubtypeOfPredicate implements Predicate<Class<?>>, Serializable {
        private static final long serialVersionUID = 0;
        public final Class<?> c;

        public SubtypeOfPredicate() {
            throw null;
        }

        public SubtypeOfPredicate(Class cls) {
            this.c = (Class) Preconditions.checkNotNull(cls);
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof SubtypeOfPredicate) || this.c != ((SubtypeOfPredicate) obj).c) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return this.c.hashCode();
        }

        public String toString() {
            return "Predicates.subtypeOf(" + this.c.getName() + ")";
        }

        public boolean apply(Class<?> cls) {
            return this.c.isAssignableFrom(cls);
        }
    }

    public static String a(String str, List list) {
        StringBuilder sb = new StringBuilder("Predicates.");
        sb.append(str);
        sb.append('(');
        boolean z = true;
        for (Object next : list) {
            if (!z) {
                sb.append(',');
            }
            sb.append(next);
            z = false;
        }
        sb.append(')');
        return sb.toString();
    }

    public static <T> Predicate<T> alwaysFalse() {
        return ObjectPredicate.f;
    }

    public static <T> Predicate<T> alwaysTrue() {
        return ObjectPredicate.c;
    }

    public static <T> Predicate<T> and(Iterable<? extends Predicate<? super T>> iterable) {
        return new AndPredicate(b(iterable));
    }

    public static ArrayList b(Iterable iterable) {
        ArrayList arrayList = new ArrayList();
        for (Object checkNotNull : iterable) {
            arrayList.add(Preconditions.checkNotNull(checkNotNull));
        }
        return arrayList;
    }

    public static <A, B> Predicate<A> compose(Predicate<B> predicate, Function<A, ? extends B> function) {
        return new CompositionPredicate(predicate, function);
    }

    public static Predicate<CharSequence> contains(Pattern pattern) {
        return new ContainsPatternPredicate(new JdkPattern(pattern));
    }

    public static Predicate<CharSequence> containsPattern(String str) {
        return new ContainsPatternFromStringPredicate(str);
    }

    public static <T> Predicate<T> equalTo(T t) {
        return t == null ? isNull() : new IsEqualToPredicate(t);
    }

    public static <T> Predicate<T> in(Collection<? extends T> collection) {
        return new InPredicate(collection);
    }

    public static Predicate<Object> instanceOf(Class<?> cls) {
        return new InstanceOfPredicate(cls);
    }

    public static <T> Predicate<T> isNull() {
        return ObjectPredicate.g;
    }

    public static <T> Predicate<T> not(Predicate<T> predicate) {
        return new NotPredicate(predicate);
    }

    public static <T> Predicate<T> notNull() {
        return ObjectPredicate.h;
    }

    public static <T> Predicate<T> or(Iterable<? extends Predicate<? super T>> iterable) {
        return new OrPredicate(b(iterable));
    }

    public static Predicate<Class<?>> subtypeOf(Class<?> cls) {
        return new SubtypeOfPredicate(cls);
    }

    @SafeVarargs
    public static <T> Predicate<T> and(Predicate<? super T>... predicateArr) {
        return new AndPredicate(b(Arrays.asList(predicateArr)));
    }

    @SafeVarargs
    public static <T> Predicate<T> or(Predicate<? super T>... predicateArr) {
        return new OrPredicate(b(Arrays.asList(predicateArr)));
    }

    public static <T> Predicate<T> and(Predicate<? super T> predicate, Predicate<? super T> predicate2) {
        return new AndPredicate(Arrays.asList(new Predicate[]{(Predicate) Preconditions.checkNotNull(predicate), (Predicate) Preconditions.checkNotNull(predicate2)}));
    }

    public static <T> Predicate<T> or(Predicate<? super T> predicate, Predicate<? super T> predicate2) {
        return new OrPredicate(Arrays.asList(new Predicate[]{(Predicate) Preconditions.checkNotNull(predicate), (Predicate) Preconditions.checkNotNull(predicate2)}));
    }
}
