package org.schabi.newpipe.extractor;

import j$.util.Objects;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.schabi.newpipe.extractor.services.bandcamp.BandcampService;
import org.schabi.newpipe.extractor.services.media_ccc.MediaCCCService;
import org.schabi.newpipe.extractor.services.peertube.PeertubeService;
import org.schabi.newpipe.extractor.services.soundcloud.SoundcloudService;
import org.schabi.newpipe.extractor.services.youtube.YoutubeService;

public final class ServiceList {
    public static final YoutubeService a;
    public static final SoundcloudService b;
    public static final PeertubeService c;
    public static final List<StreamingService> d;

    static {
        YoutubeService youtubeService = new YoutubeService(0);
        a = youtubeService;
        SoundcloudService soundcloudService = new SoundcloudService(1);
        b = soundcloudService;
        MediaCCCService mediaCCCService = new MediaCCCService(2);
        PeertubeService peertubeService = new PeertubeService(3);
        c = peertubeService;
        Object[] objArr = {youtubeService, soundcloudService, mediaCCCService, peertubeService, new BandcampService(4)};
        ArrayList arrayList = new ArrayList(5);
        for (int i = 0; i < 5; i++) {
            Object obj = objArr[i];
            Objects.requireNonNull(obj);
            arrayList.add(obj);
        }
        d = Collections.unmodifiableList(arrayList);
    }

    public static List<StreamingService> all() {
        return d;
    }
}
