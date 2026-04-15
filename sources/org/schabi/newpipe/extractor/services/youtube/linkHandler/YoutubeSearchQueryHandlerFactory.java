package org.schabi.newpipe.extractor.services.youtube.linkHandler;

import org.schabi.newpipe.extractor.linkhandler.SearchQueryHandlerFactory;
import org.schabi.newpipe.extractor.utils.Utils;

public final class YoutubeSearchQueryHandlerFactory extends SearchQueryHandlerFactory {
    public static final YoutubeSearchQueryHandlerFactory a = new YoutubeSearchQueryHandlerFactory();

    public static YoutubeSearchQueryHandlerFactory getInstance() {
        return a;
    }

    public static String getSearchParameter(String str) {
        if (Utils.isNullOrEmpty(str)) {
            return "8AEB";
        }
        str.getClass();
        char c = 65535;
        switch (str.hashCode()) {
            case -1865828127:
                if (str.equals("playlists")) {
                    c = 0;
                    break;
                }
                break;
            case -1778518201:
                if (str.equals("music_playlists")) {
                    c = 1;
                    break;
                }
                break;
            case -816678056:
                if (str.equals("videos")) {
                    c = 2;
                    break;
                }
                break;
            case -566908430:
                if (str.equals("music_artists")) {
                    c = 3;
                    break;
                }
                break;
            case 1432626128:
                if (str.equals("channels")) {
                    c = 4;
                    break;
                }
                break;
            case 1499667262:
                if (str.equals("music_albums")) {
                    c = 5;
                    break;
                }
                break;
            case 1589120868:
                if (str.equals("music_songs")) {
                    c = 6;
                    break;
                }
                break;
            case 2098153138:
                if (str.equals("music_videos")) {
                    c = 7;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return "EgIQA_ABAQ%3D%3D";
            case 1:
            case 3:
            case 5:
            case 6:
            case 7:
                return "";
            case 2:
                return "EgIQAfABAQ%3D%3D";
            case 4:
                return "EgIQAvABAQ%3D%3D";
            default:
                return "8AEB";
        }
    }

    public String[] getAvailableContentFilter() {
        return new String[]{"all", "videos", "channels", "playlists", "music_songs", "music_videos", "music_albums", "music_playlists"};
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0070, code lost:
        if (r4.equals("playlists") == false) goto L_0x001b;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getUrl(java.lang.String r3, java.util.List<java.lang.String> r4, java.lang.String r5) throws org.schabi.newpipe.extractor.exceptions.ParsingException, java.lang.UnsupportedOperationException {
        /*
            r2 = this;
            boolean r5 = r4.isEmpty()
            r0 = 0
            if (r5 != 0) goto L_0x000e
            java.lang.Object r4 = r4.get(r0)
            java.lang.String r4 = (java.lang.String) r4
            goto L_0x0010
        L_0x000e:
            java.lang.String r4 = ""
        L_0x0010:
            r4.getClass()
            int r5 = r4.hashCode()
            r1 = -1
            switch(r5) {
                case -1865828127: goto L_0x006a;
                case -1778518201: goto L_0x005f;
                case -816678056: goto L_0x0054;
                case -566908430: goto L_0x0049;
                case 1432626128: goto L_0x003e;
                case 1499667262: goto L_0x0033;
                case 1589120868: goto L_0x0028;
                case 2098153138: goto L_0x001d;
                default: goto L_0x001b;
            }
        L_0x001b:
            r0 = -1
            goto L_0x0073
        L_0x001d:
            java.lang.String r5 = "music_videos"
            boolean r4 = r4.equals(r5)
            if (r4 != 0) goto L_0x0026
            goto L_0x001b
        L_0x0026:
            r0 = 7
            goto L_0x0073
        L_0x0028:
            java.lang.String r5 = "music_songs"
            boolean r4 = r4.equals(r5)
            if (r4 != 0) goto L_0x0031
            goto L_0x001b
        L_0x0031:
            r0 = 6
            goto L_0x0073
        L_0x0033:
            java.lang.String r5 = "music_albums"
            boolean r4 = r4.equals(r5)
            if (r4 != 0) goto L_0x003c
            goto L_0x001b
        L_0x003c:
            r0 = 5
            goto L_0x0073
        L_0x003e:
            java.lang.String r5 = "channels"
            boolean r4 = r4.equals(r5)
            if (r4 != 0) goto L_0x0047
            goto L_0x001b
        L_0x0047:
            r0 = 4
            goto L_0x0073
        L_0x0049:
            java.lang.String r5 = "music_artists"
            boolean r4 = r4.equals(r5)
            if (r4 != 0) goto L_0x0052
            goto L_0x001b
        L_0x0052:
            r0 = 3
            goto L_0x0073
        L_0x0054:
            java.lang.String r5 = "videos"
            boolean r4 = r4.equals(r5)
            if (r4 != 0) goto L_0x005d
            goto L_0x001b
        L_0x005d:
            r0 = 2
            goto L_0x0073
        L_0x005f:
            java.lang.String r5 = "music_playlists"
            boolean r4 = r4.equals(r5)
            if (r4 != 0) goto L_0x0068
            goto L_0x001b
        L_0x0068:
            r0 = 1
            goto L_0x0073
        L_0x006a:
            java.lang.String r5 = "playlists"
            boolean r4 = r4.equals(r5)
            if (r4 != 0) goto L_0x0073
            goto L_0x001b
        L_0x0073:
            java.lang.String r4 = "https://www.youtube.com/results?search_query="
            switch(r0) {
                case 0: goto L_0x00cd;
                case 1: goto L_0x00ba;
                case 2: goto L_0x00a4;
                case 3: goto L_0x00ba;
                case 4: goto L_0x008e;
                case 5: goto L_0x00ba;
                case 6: goto L_0x00ba;
                case 7: goto L_0x00ba;
                default: goto L_0x0078;
            }
        L_0x0078:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>(r4)
            java.lang.String r3 = org.schabi.newpipe.extractor.utils.Utils.encodeUrlUtf8(r3)
            r5.append(r3)
            java.lang.String r3 = "&sp=8AEB"
            r5.append(r3)
            java.lang.String r3 = r5.toString()
            return r3
        L_0x008e:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>(r4)
            java.lang.String r3 = org.schabi.newpipe.extractor.utils.Utils.encodeUrlUtf8(r3)
            r5.append(r3)
            java.lang.String r3 = "&sp=EgIQAvABAQ%253D%253D"
            r5.append(r3)
            java.lang.String r3 = r5.toString()
            return r3
        L_0x00a4:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>(r4)
            java.lang.String r3 = org.schabi.newpipe.extractor.utils.Utils.encodeUrlUtf8(r3)
            r5.append(r3)
            java.lang.String r3 = "&sp=EgIQAfABAQ%253D%253D"
            r5.append(r3)
            java.lang.String r3 = r5.toString()
            return r3
        L_0x00ba:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "https://music.youtube.com/search?q="
            r4.<init>(r5)
            java.lang.String r3 = org.schabi.newpipe.extractor.utils.Utils.encodeUrlUtf8(r3)
            r4.append(r3)
            java.lang.String r3 = r4.toString()
            return r3
        L_0x00cd:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>(r4)
            java.lang.String r3 = org.schabi.newpipe.extractor.utils.Utils.encodeUrlUtf8(r3)
            r5.append(r3)
            java.lang.String r3 = "&sp=EgIQA_ABAQ%253D%253D"
            r5.append(r3)
            java.lang.String r3 = r5.toString()
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeSearchQueryHandlerFactory.getUrl(java.lang.String, java.util.List, java.lang.String):java.lang.String");
    }
}
