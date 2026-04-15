package defpackage;

import com.grack.nanojson.JsonObject;
import j$.time.temporal.ChronoUnit;
import j$.util.function.Function$CC;
import java.util.LinkedHashMap;
import java.util.function.Function;
import java.util.regex.Pattern;
import org.schabi.newpipe.extractor.utils.Utils;

/* renamed from: ug  reason: default package */
public final /* synthetic */ class ug implements Function {
    public final /* synthetic */ int a;

    public /* synthetic */ ug(int i) {
        this.a = i;
    }

    public final /* synthetic */ Function andThen(Function function) {
        switch (this.a) {
            case 0:
                return Function$CC.$default$andThen(this, function);
            case 1:
                return Function$CC.$default$andThen(this, function);
            case 2:
                return Function$CC.$default$andThen(this, function);
            case 3:
                return Function$CC.$default$andThen(this, function);
            case 4:
                return Function$CC.$default$andThen(this, function);
            default:
                return Function$CC.$default$andThen(this, function);
        }
    }

    public final Object apply(Object obj) {
        switch (this.a) {
            case 0:
                return ((JsonObject) obj).getObject("tabRenderer");
            case 1:
                ChronoUnit chronoUnit = (ChronoUnit) obj;
                return new LinkedHashMap();
            case 2:
                return ((String) obj).split("=");
            case 3:
                return ((String[]) obj)[0];
            case 4:
                return Utils.decodeUrlUtf8(((String[]) obj)[1]);
            default:
                return Pattern.compile((String) obj);
        }
    }

    public final /* synthetic */ Function compose(Function function) {
        switch (this.a) {
            case 0:
                return Function$CC.$default$compose(this, function);
            case 1:
                return Function$CC.$default$compose(this, function);
            case 2:
                return Function$CC.$default$compose(this, function);
            case 3:
                return Function$CC.$default$compose(this, function);
            case 4:
                return Function$CC.$default$compose(this, function);
            default:
                return Function$CC.$default$compose(this, function);
        }
    }
}
