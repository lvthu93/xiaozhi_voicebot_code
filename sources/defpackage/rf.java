package defpackage;

import com.grack.nanojson.JsonObject;
import j$.util.Optional;
import java.util.function.Supplier;

/* renamed from: rf  reason: default package */
public final /* synthetic */ class rf implements Supplier {
    public final /* synthetic */ int a;
    public final /* synthetic */ Object b;

    public /* synthetic */ rf(int i, Object obj) {
        this.a = i;
        this.b = obj;
    }

    public final Object get() {
        int i = this.a;
        Object obj = this.b;
        switch (i) {
            case 0:
                return Optional.ofNullable(((JsonObject) obj).getObject("microformat").getObject("microformatDataRenderer").getString("title"));
            default:
                return (Optional) obj;
        }
    }
}
