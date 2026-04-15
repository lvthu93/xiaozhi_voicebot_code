package org.schabi.newpipe.extractor.services.youtube;

public interface PoTokenProvider {
    PoTokenResult getAndroidClientPoToken(String str);

    PoTokenResult getIosClientPoToken(String str);

    PoTokenResult getWebClientPoToken(String str);

    PoTokenResult getWebEmbedClientPoToken(String str);
}
