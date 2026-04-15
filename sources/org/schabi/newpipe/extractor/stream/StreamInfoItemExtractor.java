package org.schabi.newpipe.extractor.stream;

import java.util.List;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.InfoItemExtractor;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.localization.DateWrapper;

public interface StreamInfoItemExtractor extends InfoItemExtractor {
    ContentAvailability getContentAvailability() throws ParsingException;

    long getDuration() throws ParsingException;

    String getShortDescription() throws ParsingException;

    StreamType getStreamType() throws ParsingException;

    String getTextualUploadDate() throws ParsingException;

    DateWrapper getUploadDate() throws ParsingException;

    List<Image> getUploaderAvatars() throws ParsingException;

    String getUploaderName() throws ParsingException;

    String getUploaderUrl() throws ParsingException;

    long getViewCount() throws ParsingException;

    boolean isAd() throws ParsingException;

    boolean isShortFormContent() throws ParsingException;

    boolean isUploaderVerified() throws ParsingException;
}
