package org.schabi.newpipe.extractor;

import j$.util.List$EL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.schabi.newpipe.extractor.InfoItem;
import org.schabi.newpipe.extractor.InfoItemExtractor;
import org.schabi.newpipe.extractor.exceptions.FoundAdException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;

public abstract class InfoItemsCollector<I extends InfoItem, E extends InfoItemExtractor> implements Collector<I, E> {
    public final ArrayList a;
    public final ArrayList b;
    public final int c;
    public final Comparator<I> d;

    public InfoItemsCollector(int i) {
        this(i, (Comparator) null);
    }

    public final void a(Exception exc) {
        this.b.add(exc);
    }

    public List<Throwable> getErrors() {
        return Collections.unmodifiableList(this.b);
    }

    public List<I> getItems() {
        ArrayList arrayList = this.a;
        Comparator<I> comparator = this.d;
        if (comparator != null) {
            List$EL.sort(arrayList, comparator);
        }
        return Collections.unmodifiableList(arrayList);
    }

    public int getServiceId() {
        return this.c;
    }

    public void reset() {
        this.a.clear();
        this.b.clear();
    }

    public InfoItemsCollector(int i, Comparator<I> comparator) {
        this.a = new ArrayList();
        this.b = new ArrayList();
        this.c = i;
        this.d = comparator;
    }

    public void commit(E e) {
        try {
            this.a.add((InfoItem) extract(e));
        } catch (FoundAdException unused) {
        } catch (ParsingException e2) {
            a(e2);
        }
    }
}
