package org.schabi.newpipe.extractor.playlist;

import java.util.Collections;
import java.util.List;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.ListExtractor;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.schabi.newpipe.extractor.playlist.PlaylistInfo;
import org.schabi.newpipe.extractor.stream.Description;
import org.schabi.newpipe.extractor.stream.StreamInfoItem;

public abstract class PlaylistExtractor extends ListExtractor<StreamInfoItem> {
    public PlaylistExtractor(StreamingService streamingService, ListLinkHandler listLinkHandler) {
        super(streamingService, listLinkHandler);
    }

    public List<Image> getBanners() throws ParsingException {
        return Collections.emptyList();
    }

    public abstract Description getDescription() throws ParsingException;

    public PlaylistInfo.PlaylistType getPlaylistType() throws ParsingException {
        return PlaylistInfo.PlaylistType.NORMAL;
    }

    public abstract long getStreamCount() throws ParsingException;

    public List<Image> getSubChannelAvatars() throws ParsingException {
        return Collections.emptyList();
    }

    public String getSubChannelName() throws ParsingException {
        return "";
    }

    public String getSubChannelUrl() throws ParsingException {
        return "";
    }

    public List<Image> getThumbnails() throws ParsingException {
        return Collections.emptyList();
    }

    public abstract List<Image> getUploaderAvatars() throws ParsingException;

    public abstract String getUploaderName() throws ParsingException;

    public abstract String getUploaderUrl() throws ParsingException;

    public abstract boolean isUploaderVerified() throws ParsingException;
}
