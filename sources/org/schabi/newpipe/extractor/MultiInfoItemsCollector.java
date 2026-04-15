package org.schabi.newpipe.extractor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.schabi.newpipe.extractor.channel.ChannelInfoItemExtractor;
import org.schabi.newpipe.extractor.channel.ChannelInfoItemsCollector;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.playlist.PlaylistInfoItemExtractor;
import org.schabi.newpipe.extractor.playlist.PlaylistInfoItemsCollector;
import org.schabi.newpipe.extractor.stream.StreamInfoItemExtractor;
import org.schabi.newpipe.extractor.stream.StreamInfoItemsCollector;

public class MultiInfoItemsCollector extends InfoItemsCollector<InfoItem, InfoItemExtractor> {
    public final StreamInfoItemsCollector e;
    public final ChannelInfoItemsCollector f;
    public final PlaylistInfoItemsCollector g;

    public MultiInfoItemsCollector(int i) {
        super(i);
        this.e = new StreamInfoItemsCollector(i);
        this.f = new ChannelInfoItemsCollector(i);
        this.g = new PlaylistInfoItemsCollector(i);
    }

    public List<Throwable> getErrors() {
        ArrayList arrayList = new ArrayList(super.getErrors());
        arrayList.addAll(this.e.getErrors());
        arrayList.addAll(this.f.getErrors());
        arrayList.addAll(this.g.getErrors());
        return Collections.unmodifiableList(arrayList);
    }

    public void reset() {
        super.reset();
        this.e.reset();
        this.f.reset();
        this.g.reset();
    }

    public InfoItem extract(InfoItemExtractor infoItemExtractor) throws ParsingException {
        if (infoItemExtractor instanceof StreamInfoItemExtractor) {
            return this.e.extract((StreamInfoItemExtractor) infoItemExtractor);
        }
        if (infoItemExtractor instanceof ChannelInfoItemExtractor) {
            return this.f.extract((ChannelInfoItemExtractor) infoItemExtractor);
        }
        if (infoItemExtractor instanceof PlaylistInfoItemExtractor) {
            return this.g.extract((PlaylistInfoItemExtractor) infoItemExtractor);
        }
        throw new IllegalArgumentException("Invalid extractor type: " + infoItemExtractor);
    }
}
