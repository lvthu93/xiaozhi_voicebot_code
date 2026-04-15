package org.schabi.newpipe.extractor.playlist;

import org.schabi.newpipe.extractor.InfoItem;
import org.schabi.newpipe.extractor.playlist.PlaylistInfo;
import org.schabi.newpipe.extractor.stream.Description;

public class PlaylistInfoItem extends InfoItem {
    public String j;
    public String k;
    public boolean l;
    public long m = 0;
    public Description n;
    public PlaylistInfo.PlaylistType o;

    public PlaylistInfoItem(int i, String str, String str2) {
        super(InfoItem.InfoType.PLAYLIST, i, str, str2);
    }

    public Description getDescription() {
        return this.n;
    }

    public PlaylistInfo.PlaylistType getPlaylistType() {
        return this.o;
    }

    public long getStreamCount() {
        return this.m;
    }

    public String getUploaderName() {
        return this.j;
    }

    public String getUploaderUrl() {
        return this.k;
    }

    public boolean isUploaderVerified() {
        return this.l;
    }

    public void setDescription(Description description) {
        this.n = description;
    }

    public void setPlaylistType(PlaylistInfo.PlaylistType playlistType) {
        this.o = playlistType;
    }

    public void setStreamCount(long j2) {
        this.m = j2;
    }

    public void setUploaderName(String str) {
        this.j = str;
    }

    public void setUploaderUrl(String str) {
        this.k = str;
    }

    public void setUploaderVerified(boolean z) {
        this.l = z;
    }
}
