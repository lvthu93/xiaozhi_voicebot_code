package defpackage;

import java.util.function.Supplier;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;

/* renamed from: k7  reason: default package */
public final /* synthetic */ class k7 implements Supplier {
    public final /* synthetic */ int a;

    public /* synthetic */ k7(int i) {
        this.a = i;
    }

    public final Object get() {
        return new ExtractionException("There's no service with the id = \"" + this.a + "\"");
    }
}
