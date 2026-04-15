package org.schabi.newpipe.extractor.services.youtube;

import com.grack.nanojson.JsonObject;
import j$.util.Collection$EL;
import j$.util.Comparator$CC;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
import java.util.function.Function;
import java.util.regex.Pattern;
import org.jsoup.nodes.Entities;
import org.schabi.newpipe.extractor.utils.Utils;

public final class YoutubeDescriptionHelper {
    public static final Pattern a = Pattern.compile("(?s)^ +[/•] +(.*?) +$");

    public static final class a {
        public final String a;
        public final String b;
        public final int c;
        public final Function<String, String> d;
        public int e;

        public a() {
            throw null;
        }

        public a(String str, String str2, int i, Function<String, String> function) {
            this.e = -1;
            this.a = str;
            this.b = str2;
            this.c = i;
            this.d = function;
        }

        public boolean sameOpen(a aVar) {
            return this.a.equals(aVar.a);
        }
    }

    public static String attributedDescriptionToHtml(JsonObject jsonObject) {
        String string;
        int i;
        int i2;
        if (Utils.isNullOrEmpty(jsonObject) || (string = jsonObject.getString("content")) == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Class<JsonObject> cls = JsonObject.class;
        int i3 = 0;
        y2.z(cls, 18, y2.s(cls, 18, Collection$EL.stream(jsonObject.getArray("commandRuns")))).forEach(new yf(arrayList, arrayList2, 0));
        y2.z(cls, 19, y2.s(cls, 19, Collection$EL.stream(jsonObject.getArray("styleRuns")))).forEach(new yf(arrayList, arrayList2, 1));
        Collections.sort(arrayList, Comparator$CC.comparingInt(new xf(0)));
        Collections.sort(arrayList2, Comparator$CC.comparingInt(new xf(1)));
        String replace = string.replace(160, ' ');
        Stack stack = new Stack();
        Stack stack2 = new Stack();
        StringBuilder sb = new StringBuilder();
        int i4 = 0;
        int i5 = 0;
        while (i3 < arrayList2.size()) {
            if (i5 < arrayList.size()) {
                i = Math.min(((a) arrayList2.get(i3)).c, ((a) arrayList.get(i5)).c);
            } else {
                i = ((a) arrayList2.get(i3)).c;
            }
            sb.append(Entities.escape(replace.substring(i4, i)));
            if (((a) arrayList2.get(i3)).c == i) {
                a aVar = (a) arrayList2.get(i3);
                i3++;
                while (true) {
                    if (stack.empty()) {
                        break;
                    }
                    a aVar2 = (a) stack.pop();
                    boolean sameOpen = aVar2.sameOpen(aVar);
                    String str = aVar2.b;
                    if (sameOpen) {
                        Function<String, String> function = aVar2.d;
                        if (function != null && (i2 = aVar2.e) >= 0) {
                            sb.replace(i2, sb.length(), function.apply(sb.substring(aVar2.e)));
                        }
                        sb.append(str);
                    } else {
                        sb.append(str);
                        stack2.push(aVar2);
                    }
                }
                while (!stack2.empty()) {
                    a aVar3 = (a) stack2.pop();
                    sb.append(aVar3.a);
                    stack.push(aVar3);
                }
            } else {
                a aVar4 = (a) arrayList.get(i5);
                sb.append(aVar4.a);
                aVar4.e = sb.length();
                stack.push(aVar4);
                i5++;
            }
            i4 = i;
        }
        sb.append(Entities.escape(replace.substring(i4)));
        return sb.toString().replace("\n", "<br>").replace("  ", " &nbsp;");
    }
}
