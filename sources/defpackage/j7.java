package defpackage;

import java.util.function.Supplier;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.localization.Localization;

/* renamed from: j7  reason: default package */
public final /* synthetic */ class j7 implements Supplier {
    public final /* synthetic */ int a;
    public final /* synthetic */ String b;

    public /* synthetic */ j7(String str, int i) {
        this.a = i;
        this.b = str;
    }

    public final Object get() {
        int i = this.a;
        String str = this.b;
        switch (i) {
            case 0:
                return new ExtractionException(y2.j("There's no service with the name = \"", str, "\""));
            case 1:
                Localization localization = Localization.g;
                return new IllegalArgumentException(y2.i("Not a localization code: ", str));
            case 2:
                return new ParsingException(y2.i("Unable to parse the date: ", str));
            case 3:
                return new ParsingException(y2.i("Cannot convert this language to a locale: ", str));
            case 4:
                return new ParsingException(y2.j("Could not parse upload date \"", str, "\""));
            default:
                return new ParsingException(y2.i("not a valid locale language code: ", str));
        }
    }
}
