package org.schabi.newpipe.extractor.utils;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonParser;
import com.grack.nanojson.JsonParserException;
import j$.util.Collection$EL;
import j$.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.schabi.newpipe.extractor.exceptions.ParsingException;

public final class JsonUtils {
    public static <T> T a(JsonObject jsonObject, String str, Class<T> cls) throws ParsingException {
        Object value = getValue(jsonObject, str);
        if (cls.isInstance(value)) {
            return cls.cast(value);
        }
        throw new ParsingException(y2.i("Wrong data type at path ", str));
    }

    public static JsonArray getArray(JsonObject jsonObject, String str) throws ParsingException {
        return (JsonArray) a(jsonObject, str, JsonArray.class);
    }

    public static Boolean getBoolean(JsonObject jsonObject, String str) throws ParsingException {
        return (Boolean) a(jsonObject, str, Boolean.class);
    }

    public static JsonObject getJsonData(String str, String str2) throws JsonParserException, ArrayIndexOutOfBoundsException {
        return JsonParser.object().from(Jsoup.parse(str).getElementsByAttribute(str2).attr(str2));
    }

    public static Number getNumber(JsonObject jsonObject, String str) throws ParsingException {
        return (Number) a(jsonObject, str, Number.class);
    }

    public static JsonObject getObject(JsonObject jsonObject, String str) throws ParsingException {
        return (JsonObject) a(jsonObject, str, JsonObject.class);
    }

    public static String getString(JsonObject jsonObject, String str) throws ParsingException {
        return (String) a(jsonObject, str, String.class);
    }

    public static List<String> getStringListFromJsonArray(JsonArray jsonArray) {
        Class<String> cls = String.class;
        return (List) Collection$EL.stream(jsonArray).filter(new sg(cls, 2)).map(new tg(cls, 2)).collect(Collectors.toList());
    }

    /* JADX WARNING: Removed duplicated region for block: B:1:0x0019 A[LOOP:0: B:1:0x0019->B:4:0x0029, LOOP_START, PHI: r3 
      PHI: (r3v1 com.grack.nanojson.JsonObject) = (r3v0 com.grack.nanojson.JsonObject), (r3v6 com.grack.nanojson.JsonObject) binds: [B:0:0x0000, B:4:0x0029] A[DONT_GENERATE, DONT_INLINE]] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object getValue(com.grack.nanojson.JsonObject r3, java.lang.String r4) throws org.schabi.newpipe.extractor.exceptions.ParsingException {
        /*
            java.lang.String r0 = "\\."
            java.lang.String[] r0 = r4.split(r0)
            java.util.List r0 = java.util.Arrays.asList(r0)
            int r1 = r0.size()
            int r1 = r1 + -1
            r2 = 0
            java.util.List r1 = r0.subList(r2, r1)
            java.util.Iterator r1 = r1.iterator()
        L_0x0019:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x002b
            java.lang.Object r2 = r1.next()
            java.lang.String r2 = (java.lang.String) r2
            com.grack.nanojson.JsonObject r3 = r3.getObject(r2)
            if (r3 != 0) goto L_0x0019
        L_0x002b:
            java.lang.String r1 = "Unable to get "
            if (r3 == 0) goto L_0x004a
            int r2 = r0.size()
            int r2 = r2 + -1
            java.lang.Object r0 = r0.get(r2)
            java.lang.Object r3 = r3.get(r0)
            if (r3 == 0) goto L_0x0040
            return r3
        L_0x0040:
            org.schabi.newpipe.extractor.exceptions.ParsingException r3 = new org.schabi.newpipe.extractor.exceptions.ParsingException
            java.lang.String r4 = r1.concat(r4)
            r3.<init>(r4)
            throw r3
        L_0x004a:
            org.schabi.newpipe.extractor.exceptions.ParsingException r3 = new org.schabi.newpipe.extractor.exceptions.ParsingException
            java.lang.String r4 = r1.concat(r4)
            r3.<init>(r4)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.schabi.newpipe.extractor.utils.JsonUtils.getValue(com.grack.nanojson.JsonObject, java.lang.String):java.lang.Object");
    }

    public static List<Object> getValues(JsonArray jsonArray, String str) throws ParsingException {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < jsonArray.size(); i++) {
            arrayList.add(getValue(jsonArray.getObject(i), str));
        }
        return arrayList;
    }

    public static JsonArray toJsonArray(String str) throws ParsingException {
        try {
            return JsonParser.array().from(str);
        } catch (JsonParserException e) {
            throw new ParsingException("Could not parse JSON", e);
        }
    }

    public static JsonObject toJsonObject(String str) throws ParsingException {
        try {
            return JsonParser.object().from(str);
        } catch (JsonParserException e) {
            throw new ParsingException("Could not parse JSON", e);
        }
    }
}
