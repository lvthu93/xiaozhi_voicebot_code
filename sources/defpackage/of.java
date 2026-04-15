package defpackage;

import j$.util.function.Consumer$CC;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.schabi.newpipe.extractor.linkhandler.ReadyChannelTabListLinkHandler;
import org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeChannelExtractor;

/* renamed from: of  reason: default package */
public final /* synthetic */ class of implements Consumer {
    public final /* synthetic */ YoutubeChannelExtractor a;
    public final /* synthetic */ List b;
    public final /* synthetic */ String c;

    public /* synthetic */ of(YoutubeChannelExtractor youtubeChannelExtractor, ArrayList arrayList, String str) {
        this.a = youtubeChannelExtractor;
        this.b = arrayList;
        this.c = str;
    }

    public final void accept(Object obj) {
        String str = (String) obj;
        YoutubeChannelExtractor youtubeChannelExtractor = this.a;
        youtubeChannelExtractor.getClass();
        this.b.add(new ReadyChannelTabListLinkHandler(this.c + MqttTopic.TOPIC_LEVEL_SEPARATOR + str, youtubeChannelExtractor.i, str, new qf()));
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }
}
