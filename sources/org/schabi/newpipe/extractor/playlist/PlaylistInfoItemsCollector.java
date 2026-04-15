package org.schabi.newpipe.extractor.playlist;

import org.schabi.newpipe.extractor.InfoItemsCollector;
import org.schabi.newpipe.extractor.exceptions.ParsingException;

public class PlaylistInfoItemsCollector extends InfoItemsCollector<PlaylistInfoItem, PlaylistInfoItemExtractor> {
    public PlaylistInfoItemsCollector(int i) {
        super(i);
    }

    public PlaylistInfoItem extract(PlaylistInfoItemExtractor playlistInfoItemExtractor) throws ParsingException {
        PlaylistInfoItem playlistInfoItem = new PlaylistInfoItem(getServiceId(), playlistInfoItemExtractor.getUrl(), playlistInfoItemExtractor.getName());
        try {
            playlistInfoItem.setUploaderName(playlistInfoItemExtractor.getUploaderName());
        } catch (Exception e) {
            a(e);
        }
        try {
            playlistInfoItem.setUploaderUrl(playlistInfoItemExtractor.getUploaderUrl());
        } catch (Exception e2) {
            a(e2);
        }
        try {
            playlistInfoItem.setUploaderVerified(playlistInfoItemExtractor.isUploaderVerified());
        } catch (Exception e3) {
            a(e3);
        }
        try {
            playlistInfoItem.setThumbnails(playlistInfoItemExtractor.getThumbnails());
        } catch (Exception e4) {
            a(e4);
        }
        try {
            playlistInfoItem.setStreamCount(playlistInfoItemExtractor.getStreamCount());
        } catch (Exception e5) {
            a(e5);
        }
        try {
            playlistInfoItem.setDescription(playlistInfoItemExtractor.getDescription());
        } catch (Exception e6) {
            a(e6);
        }
        try {
            playlistInfoItem.setPlaylistType(playlistInfoItemExtractor.getPlaylistType());
        } catch (Exception e7) {
            a(e7);
        }
        return playlistInfoItem;
    }
}
