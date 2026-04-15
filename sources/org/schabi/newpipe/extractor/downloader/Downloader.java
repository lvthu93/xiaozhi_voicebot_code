package org.schabi.newpipe.extractor.downloader;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.schabi.newpipe.extractor.NewPipe;
import org.schabi.newpipe.extractor.exceptions.ReCaptchaException;
import org.schabi.newpipe.extractor.localization.Localization;

public abstract class Downloader {
    public abstract Response execute(Request request) throws IOException, ReCaptchaException;

    public Response get(String str) throws IOException, ReCaptchaException {
        return get(str, (Map<String, List<String>>) null, NewPipe.getPreferredLocalization());
    }

    public Response head(String str) throws IOException, ReCaptchaException {
        return head(str, (Map<String, List<String>>) null);
    }

    public Response post(String str, Map<String, List<String>> map, byte[] bArr) throws IOException, ReCaptchaException {
        return post(str, map, bArr, NewPipe.getPreferredLocalization());
    }

    public Response postWithContentType(String str, Map<String, List<String>> map, byte[] bArr, Localization localization, String str2) throws IOException, ReCaptchaException {
        HashMap hashMap = new HashMap();
        if (map != null) {
            hashMap.putAll(map);
        }
        hashMap.put("Content-Type", Collections.singletonList(str2));
        return post(str, hashMap, bArr, localization);
    }

    public Response postWithContentTypeJson(String str, Map<String, List<String>> map, byte[] bArr, Localization localization) throws IOException, ReCaptchaException {
        return postWithContentType(str, map, bArr, localization, "application/json");
    }

    public Response get(String str, Localization localization) throws IOException, ReCaptchaException {
        return get(str, (Map<String, List<String>>) null, localization);
    }

    public Response head(String str, Map<String, List<String>> map) throws IOException, ReCaptchaException {
        return execute(Request.newBuilder().head(str).headers(map).build());
    }

    public Response post(String str, Map<String, List<String>> map, byte[] bArr, Localization localization) throws IOException, ReCaptchaException {
        return execute(Request.newBuilder().post(str, bArr).headers(map).localization(localization).build());
    }

    public Response postWithContentTypeJson(String str, Map<String, List<String>> map, byte[] bArr) throws IOException, ReCaptchaException {
        return postWithContentTypeJson(str, map, bArr, NewPipe.getPreferredLocalization());
    }

    public Response get(String str, Map<String, List<String>> map) throws IOException, ReCaptchaException {
        return get(str, map, NewPipe.getPreferredLocalization());
    }

    public Response get(String str, Map<String, List<String>> map, Localization localization) throws IOException, ReCaptchaException {
        return execute(Request.newBuilder().get(str).headers(map).localization(localization).build());
    }

    public Response postWithContentType(String str, Map<String, List<String>> map, byte[] bArr, String str2) throws IOException, ReCaptchaException {
        return postWithContentType(str, map, bArr, NewPipe.getPreferredLocalization(), str2);
    }
}
