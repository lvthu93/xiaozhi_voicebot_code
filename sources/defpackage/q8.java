package defpackage;

import j$.util.function.Consumer$CC;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.schabi.newpipe.extractor.stream.Stream;
import org.schabi.newpipe.extractor.stream.VideoStream;

/* renamed from: q8  reason: default package */
public final /* synthetic */ class q8 implements Consumer {
    public final /* synthetic */ int a;
    public final /* synthetic */ List b;

    public /* synthetic */ q8(ArrayList arrayList, int i) {
        this.a = i;
        this.b = arrayList;
    }

    public final void accept(Object obj) {
        int i = this.a;
        List list = this.b;
        switch (i) {
            case 0:
                list.add((VideoStream) obj);
                return;
            default:
                Stream stream = (Stream) obj;
                if (!Stream.containSimilarStream(stream, list)) {
                    list.add(stream);
                    return;
                }
                return;
        }
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        switch (this.a) {
            case 0:
                return Consumer$CC.$default$andThen(this, consumer);
            default:
                return Consumer$CC.$default$andThen(this, consumer);
        }
    }
}
