package defpackage;

import info.dourok.voicebot.java.services.AlarmService;
import j$.util.function.Predicate$CC;
import java.util.function.Predicate;
import org.schabi.newpipe.extractor.MediaFormat;
import org.schabi.newpipe.extractor.StreamingService;

/* renamed from: af  reason: default package */
public final /* synthetic */ class af implements Predicate {
    public final /* synthetic */ int a;
    public final /* synthetic */ int b;

    public /* synthetic */ af(int i, int i2) {
        this.a = i2;
        this.b = i;
    }

    public final /* synthetic */ Predicate and(Predicate predicate) {
        switch (this.a) {
            case 0:
                return Predicate$CC.$default$and(this, predicate);
            case 1:
                return Predicate$CC.$default$and(this, predicate);
            default:
                return Predicate$CC.$default$and(this, predicate);
        }
    }

    public final /* synthetic */ Predicate negate() {
        switch (this.a) {
            case 0:
                return Predicate$CC.$default$negate(this);
            case 1:
                return Predicate$CC.$default$negate(this);
            default:
                return Predicate$CC.$default$negate(this);
        }
    }

    public final /* synthetic */ Predicate or(Predicate predicate) {
        switch (this.a) {
            case 0:
                return Predicate$CC.$default$or(this, predicate);
            case 1:
                return Predicate$CC.$default$or(this, predicate);
            default:
                return Predicate$CC.$default$or(this, predicate);
        }
    }

    public final boolean test(Object obj) {
        int i = this.a;
        int i2 = this.b;
        switch (i) {
            case 0:
                return AlarmService.lambda$deleteAlarm$0(i2, (ab) obj);
            case 1:
                if (((MediaFormat) obj).c == i2) {
                    return true;
                }
                return false;
            default:
                if (((StreamingService) obj).getServiceId() == i2) {
                    return true;
                }
                return false;
        }
    }
}
