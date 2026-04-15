package org.schabi.newpipe.extractor;

import java.util.List;
import org.schabi.newpipe.extractor.exceptions.ParsingException;

public interface InfoItemExtractor {
    String getName() throws ParsingException;

    List<Image> getThumbnails() throws ParsingException;

    String getUrl() throws ParsingException;
}
