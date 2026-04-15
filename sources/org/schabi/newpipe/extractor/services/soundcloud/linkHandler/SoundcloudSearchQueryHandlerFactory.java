package org.schabi.newpipe.extractor.services.soundcloud.linkHandler;

import org.schabi.newpipe.extractor.linkhandler.SearchQueryHandlerFactory;

public final class SoundcloudSearchQueryHandlerFactory extends SearchQueryHandlerFactory {
    public static final SoundcloudSearchQueryHandlerFactory a = new SoundcloudSearchQueryHandlerFactory();

    public static SoundcloudSearchQueryHandlerFactory getInstance() {
        return a;
    }

    public String[] getAvailableContentFilter() {
        return new String[]{"all", "tracks", "users", "playlists"};
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0042, code lost:
        r1 = 65535;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0043, code lost:
        if (r1 == 0) goto L_0x0050;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0045, code lost:
        if (r1 == 1) goto L_0x004d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0047, code lost:
        if (r1 == 2) goto L_0x004a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x004a, code lost:
        r0 = "https://api-v2.soundcloud.com/search/playlists";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x004d, code lost:
        r0 = "https://api-v2.soundcloud.com/search/users";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0050, code lost:
        r0 = "https://api-v2.soundcloud.com/search/tracks";
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getUrl(java.lang.String r6, java.util.List<java.lang.String> r7, java.lang.String r8) throws org.schabi.newpipe.extractor.exceptions.ParsingException, java.lang.UnsupportedOperationException {
        /*
            r5 = this;
            java.lang.String r8 = "&limit=10&offset=0"
            java.lang.String r0 = "https://api-v2.soundcloud.com/search"
            boolean r1 = r7.isEmpty()     // Catch:{ ReCaptchaException -> 0x0085, IOException -> 0x007c, ExtractionException -> 0x007a }
            if (r1 != 0) goto L_0x0052
            r1 = 0
            java.lang.Object r7 = r7.get(r1)     // Catch:{ ReCaptchaException -> 0x0085, IOException -> 0x007c, ExtractionException -> 0x007a }
            java.lang.String r7 = (java.lang.String) r7     // Catch:{ ReCaptchaException -> 0x0085, IOException -> 0x007c, ExtractionException -> 0x007a }
            int r2 = r7.hashCode()     // Catch:{ ReCaptchaException -> 0x0085, IOException -> 0x007c, ExtractionException -> 0x007a }
            r3 = 2
            r4 = 1
            switch(r2) {
                case -1865828127: goto L_0x0038;
                case -865716088: goto L_0x002f;
                case 96673: goto L_0x0025;
                case 111578632: goto L_0x001b;
                default: goto L_0x001a;
            }     // Catch:{ ReCaptchaException -> 0x0085, IOException -> 0x007c, ExtractionException -> 0x007a }
        L_0x001a:
            goto L_0x0042
        L_0x001b:
            java.lang.String r1 = "users"
            boolean r7 = r7.equals(r1)     // Catch:{ ReCaptchaException -> 0x0085, IOException -> 0x007c, ExtractionException -> 0x007a }
            if (r7 == 0) goto L_0x0042
            r1 = 1
            goto L_0x0043
        L_0x0025:
            java.lang.String r1 = "all"
            boolean r7 = r7.equals(r1)     // Catch:{ ReCaptchaException -> 0x0085, IOException -> 0x007c, ExtractionException -> 0x007a }
            if (r7 == 0) goto L_0x0042
            r1 = 3
            goto L_0x0043
        L_0x002f:
            java.lang.String r2 = "tracks"
            boolean r7 = r7.equals(r2)     // Catch:{ ReCaptchaException -> 0x0085, IOException -> 0x007c, ExtractionException -> 0x007a }
            if (r7 == 0) goto L_0x0042
            goto L_0x0043
        L_0x0038:
            java.lang.String r1 = "playlists"
            boolean r7 = r7.equals(r1)     // Catch:{ ReCaptchaException -> 0x0085, IOException -> 0x007c, ExtractionException -> 0x007a }
            if (r7 == 0) goto L_0x0042
            r1 = 2
            goto L_0x0043
        L_0x0042:
            r1 = -1
        L_0x0043:
            if (r1 == 0) goto L_0x0050
            if (r1 == r4) goto L_0x004d
            if (r1 == r3) goto L_0x004a
            goto L_0x0052
        L_0x004a:
            java.lang.String r0 = "https://api-v2.soundcloud.com/search/playlists"
            goto L_0x0052
        L_0x004d:
            java.lang.String r0 = "https://api-v2.soundcloud.com/search/users"
            goto L_0x0052
        L_0x0050:
            java.lang.String r0 = "https://api-v2.soundcloud.com/search/tracks"
        L_0x0052:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ ReCaptchaException -> 0x0085, IOException -> 0x007c, ExtractionException -> 0x007a }
            r7.<init>()     // Catch:{ ReCaptchaException -> 0x0085, IOException -> 0x007c, ExtractionException -> 0x007a }
            r7.append(r0)     // Catch:{ ReCaptchaException -> 0x0085, IOException -> 0x007c, ExtractionException -> 0x007a }
            java.lang.String r0 = "?q="
            r7.append(r0)     // Catch:{ ReCaptchaException -> 0x0085, IOException -> 0x007c, ExtractionException -> 0x007a }
            java.lang.String r6 = org.schabi.newpipe.extractor.utils.Utils.encodeUrlUtf8(r6)     // Catch:{ ReCaptchaException -> 0x0085, IOException -> 0x007c, ExtractionException -> 0x007a }
            r7.append(r6)     // Catch:{ ReCaptchaException -> 0x0085, IOException -> 0x007c, ExtractionException -> 0x007a }
            java.lang.String r6 = "&client_id="
            r7.append(r6)     // Catch:{ ReCaptchaException -> 0x0085, IOException -> 0x007c, ExtractionException -> 0x007a }
            java.lang.String r6 = org.schabi.newpipe.extractor.services.soundcloud.SoundcloudParsingHelper.clientId()     // Catch:{ ReCaptchaException -> 0x0085, IOException -> 0x007c, ExtractionException -> 0x007a }
            r7.append(r6)     // Catch:{ ReCaptchaException -> 0x0085, IOException -> 0x007c, ExtractionException -> 0x007a }
            r7.append(r8)     // Catch:{ ReCaptchaException -> 0x0085, IOException -> 0x007c, ExtractionException -> 0x007a }
            java.lang.String r6 = r7.toString()     // Catch:{ ReCaptchaException -> 0x0085, IOException -> 0x007c, ExtractionException -> 0x007a }
            return r6
        L_0x007a:
            r6 = move-exception
            goto L_0x007d
        L_0x007c:
            r6 = move-exception
        L_0x007d:
            org.schabi.newpipe.extractor.exceptions.ParsingException r7 = new org.schabi.newpipe.extractor.exceptions.ParsingException
            java.lang.String r8 = "Could not get client id"
            r7.<init>(r8, r6)
            throw r7
        L_0x0085:
            r6 = move-exception
            org.schabi.newpipe.extractor.exceptions.ParsingException r7 = new org.schabi.newpipe.extractor.exceptions.ParsingException
            java.lang.String r8 = "ReCaptcha required"
            r7.<init>(r8, r6)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: org.schabi.newpipe.extractor.services.soundcloud.linkHandler.SoundcloudSearchQueryHandlerFactory.getUrl(java.lang.String, java.util.List, java.lang.String):java.lang.String");
    }
}
