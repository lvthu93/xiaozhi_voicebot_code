package j$.util;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;

/* renamed from: j$.util.n  reason: case insensitive filesystem */
public abstract class C0070n {
    public static Optional a(Optional optional) {
        if (optional == null) {
            return null;
        }
        return optional.isPresent() ? Optional.of(optional.get()) : Optional.empty();
    }

    public static C0071o b(OptionalDouble optionalDouble) {
        if (optionalDouble == null) {
            return null;
        }
        return optionalDouble.isPresent() ? C0071o.d(optionalDouble.getAsDouble()) : C0071o.a();
    }

    public static C0072p c(OptionalInt optionalInt) {
        if (optionalInt == null) {
            return null;
        }
        return optionalInt.isPresent() ? C0072p.d(optionalInt.getAsInt()) : C0072p.a();
    }

    public static C0073q d(OptionalLong optionalLong) {
        if (optionalLong == null) {
            return null;
        }
        return optionalLong.isPresent() ? C0073q.d(optionalLong.getAsLong()) : C0073q.a();
    }

    public static Optional e(Optional optional) {
        if (optional == null) {
            return null;
        }
        return optional.isPresent() ? Optional.of(optional.get()) : Optional.empty();
    }

    public static OptionalDouble f(C0071o oVar) {
        if (oVar == null) {
            return null;
        }
        return oVar.c() ? OptionalDouble.of(oVar.b()) : OptionalDouble.empty();
    }

    public static OptionalInt g(C0072p pVar) {
        if (pVar == null) {
            return null;
        }
        return pVar.c() ? OptionalInt.of(pVar.b()) : OptionalInt.empty();
    }

    public static OptionalLong h(C0073q qVar) {
        if (qVar == null) {
            return null;
        }
        return qVar.c() ? OptionalLong.of(qVar.b()) : OptionalLong.empty();
    }
}
