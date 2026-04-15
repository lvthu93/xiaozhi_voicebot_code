package defpackage;

import j$.util.Collection$EL;
import j$.util.function.Predicate$CC;
import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;
import org.schabi.newpipe.extractor.localization.TimeAgoParser;

/* renamed from: kc  reason: default package */
public final /* synthetic */ class kc implements Predicate {
    public final /* synthetic */ int a;
    public final /* synthetic */ TimeAgoParser b;
    public final /* synthetic */ String c;

    public /* synthetic */ kc(TimeAgoParser timeAgoParser, String str, int i) {
        this.a = i;
        this.b = timeAgoParser;
        this.c = str;
    }

    public final /* synthetic */ Predicate and(Predicate predicate) {
        switch (this.a) {
            case 0:
                return Predicate$CC.$default$and(this, predicate);
            default:
                return Predicate$CC.$default$and(this, predicate);
        }
    }

    public final /* synthetic */ Predicate negate() {
        switch (this.a) {
            case 0:
                return Predicate$CC.$default$negate(this);
            default:
                return Predicate$CC.$default$negate(this);
        }
    }

    public final /* synthetic */ Predicate or(Predicate predicate) {
        switch (this.a) {
            case 0:
                return Predicate$CC.$default$or(this, predicate);
            default:
                return Predicate$CC.$default$or(this, predicate);
        }
    }

    public final boolean test(Object obj) {
        int i = this.a;
        String str = this.c;
        TimeAgoParser timeAgoParser = this.b;
        switch (i) {
            case 0:
                timeAgoParser.getClass();
                return Collection$EL.stream((Collection) ((Map.Entry) obj).getValue()).anyMatch(new kc(timeAgoParser, str, 1));
            default:
                return timeAgoParser.b(str, (String) obj);
        }
    }
}
