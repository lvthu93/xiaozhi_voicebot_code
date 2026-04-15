package defpackage;

import com.grack.nanojson.JsonObject;
import j$.util.function.Consumer$CC;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Pattern;
import org.jsoup.nodes.Entities;
import org.schabi.newpipe.extractor.services.youtube.YoutubeDescriptionHelper;
import org.schabi.newpipe.extractor.services.youtube.YoutubeParsingHelper;

/* renamed from: yf  reason: default package */
public final /* synthetic */ class yf implements Consumer {
    public final /* synthetic */ int a;
    public final /* synthetic */ List b;
    public final /* synthetic */ List c;

    public /* synthetic */ yf(ArrayList arrayList, ArrayList arrayList2, int i) {
        this.a = i;
        this.b = arrayList;
        this.c = arrayList2;
    }

    public final void accept(Object obj) {
        String urlFromNavigationEndpoint;
        Function function;
        int i = this.a;
        List list = this.c;
        List list2 = this.b;
        switch (i) {
            case 0:
                JsonObject jsonObject = (JsonObject) obj;
                Pattern pattern = YoutubeDescriptionHelper.a;
                JsonObject object = jsonObject.getObject("onTap").getObject("innertubeCommand");
                int i2 = jsonObject.getInt("startIndex", -1);
                int i3 = jsonObject.getInt("length", 0);
                if (i2 >= 0 && i3 >= 1 && object != null && (urlFromNavigationEndpoint = YoutubeParsingHelper.getUrlFromNavigationEndpoint(object)) != null) {
                    String str = "<a href=\"" + Entities.escape(urlFromNavigationEndpoint) + "\">";
                    String replaceFirst = jsonObject.getObject("onTapOptions").getObject("accessibilityInfo").getString("accessibilityLabel", "").replaceFirst(" Channel Link", "");
                    if (replaceFirst.isEmpty() || replaceFirst.startsWith("YouTube: ")) {
                        function = new p8(10);
                    } else {
                        function = new cc(replaceFirst, 4);
                    }
                    list2.add(new YoutubeDescriptionHelper.a(str, "</a>", i2, function));
                    list.add(new YoutubeDescriptionHelper.a(str, "</a>", i2 + i3, function));
                    return;
                }
                return;
            default:
                JsonObject jsonObject2 = (JsonObject) obj;
                Pattern pattern2 = YoutubeDescriptionHelper.a;
                int i4 = jsonObject2.getInt("startIndex", -1);
                int i5 = jsonObject2.getInt("length", 0);
                if (i4 >= 0 && i5 >= 1) {
                    int i6 = i5 + i4;
                    if (jsonObject2.has("strikethrough")) {
                        list2.add(new YoutubeDescriptionHelper.a("<s>", "</s>", i4, (Function<String, String>) null));
                        list.add(new YoutubeDescriptionHelper.a("<s>", "</s>", i6, (Function<String, String>) null));
                    }
                    if (jsonObject2.getBoolean("italic", Boolean.FALSE)) {
                        list2.add(new YoutubeDescriptionHelper.a("<i>", "</i>", i4, (Function<String, String>) null));
                        list.add(new YoutubeDescriptionHelper.a("<i>", "</i>", i6, (Function<String, String>) null));
                    }
                    if (jsonObject2.has("weightLabel") && !"FONT_WEIGHT_NORMAL".equals(jsonObject2.getString("weightLabel"))) {
                        list2.add(new YoutubeDescriptionHelper.a("<b>", "</b>", i4, (Function<String, String>) null));
                        list.add(new YoutubeDescriptionHelper.a("<b>", "</b>", i6, (Function<String, String>) null));
                        return;
                    }
                    return;
                }
                return;
        }
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        switch (this.a) {
            case 0:
                return Consumer$CC.$default$andThen(this, consumer);
            default:
                return Consumer$CC.$default$andThen(this, consumer);
        }
    }
}
