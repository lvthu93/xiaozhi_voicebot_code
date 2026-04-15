package defpackage;

import j$.time.LocalDate;
import j$.time.format.DateTimeFormatter;
import j$.time.format.DateTimeParseException;
import j$.util.Optional;
import java.util.Locale;
import java.util.function.Supplier;
import org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeStreamExtractor;

/* renamed from: lg  reason: default package */
public final /* synthetic */ class lg implements Supplier {
    public final /* synthetic */ YoutubeStreamExtractor a;
    public final /* synthetic */ String b;

    public /* synthetic */ lg(YoutubeStreamExtractor youtubeStreamExtractor, String str) {
        this.a = youtubeStreamExtractor;
        this.b = str;
    }

    public final Object get() {
        String str = this.b;
        this.a.getClass();
        try {
            return Optional.of(LocalDate.parse(str, DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH)));
        } catch (DateTimeParseException unused) {
            return Optional.empty();
        }
    }
}
