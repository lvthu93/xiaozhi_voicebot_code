package org.schabi.newpipe.extractor.playlist;

import org.schabi.newpipe.extractor.InfoItemExtractor;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.playlist.PlaylistInfo;
import org.schabi.newpipe.extractor.stream.Description;

public interface PlaylistInfoItemExtractor extends InfoItemExtractor {
    Description getDescription() throws ParsingException;

    PlaylistInfo.PlaylistType getPlaylistType() throws ParsingException;

    long getStreamCount() throws ParsingException;

    String getUploaderName() throws ParsingException;

    String getUploaderUrl() throws ParsingException;

    boolean isUploaderVerified() throws ParsingException;
}
