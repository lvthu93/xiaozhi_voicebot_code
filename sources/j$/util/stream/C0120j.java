package j$.util.stream;

import java.util.stream.Collector;

/* renamed from: j$.util.stream.j  reason: case insensitive filesystem */
public abstract /* synthetic */ class C0120j {
    public static /* synthetic */ C0125k a(Collector.Characteristics characteristics) {
        if (characteristics == null) {
            return null;
        }
        return characteristics == Collector.Characteristics.CONCURRENT ? C0125k.CONCURRENT : characteristics == Collector.Characteristics.UNORDERED ? C0125k.UNORDERED : C0125k.IDENTITY_FINISH;
    }

    public static /* synthetic */ Collector.Characteristics b(C0125k kVar) {
        if (kVar == null) {
            return null;
        }
        return kVar == C0125k.CONCURRENT ? Collector.Characteristics.CONCURRENT : kVar == C0125k.UNORDERED ? Collector.Characteristics.UNORDERED : Collector.Characteristics.IDENTITY_FINISH;
    }
}
