package org.schabi.newpipe.extractor;

import java.util.List;
import org.schabi.newpipe.extractor.exceptions.ParsingException;

public interface Collector<I, E> {
    void commit(E e);

    I extract(E e) throws ParsingException;

    List<Throwable> getErrors();

    List<I> getItems();

    void reset();
}
