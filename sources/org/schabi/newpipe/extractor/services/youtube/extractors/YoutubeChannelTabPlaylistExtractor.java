package org.schabi.newpipe.extractor.services.youtube.extractors;

import java.io.IOException;
import org.schabi.newpipe.extractor.ListExtractor;
import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.channel.tabs.ChannelTabExtractor;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.ContentNotAvailableException;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;

public class YoutubeChannelTabPlaylistExtractor extends ChannelTabExtractor {
    public final YoutubePlaylistExtractor g;
    public boolean h;

    public static final class SystemPlaylistUrlCreationException extends RuntimeException {
        public SystemPlaylistUrlCreationException(ParsingException parsingException) {
            super("Could not create a YouTube playlist from a valid playlist ID", parsingException);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x005f, code lost:
        if (r0.equals("shorts") == false) goto L_0x0061;
     */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0064  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x007e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public YoutubeChannelTabPlaylistExtractor(org.schabi.newpipe.extractor.StreamingService r7, org.schabi.newpipe.extractor.linkhandler.ListLinkHandler r8) throws java.lang.IllegalArgumentException, org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeChannelTabPlaylistExtractor.SystemPlaylistUrlCreationException {
        /*
            r6 = this;
            r6.<init>(r7, r8)
            java.util.List r0 = r8.getContentFilters()
            boolean r1 = r0.isEmpty()
            if (r1 != 0) goto L_0x00b1
            java.lang.String r8 = r8.getId()
            boolean r1 = org.schabi.newpipe.extractor.utils.Utils.isNullOrEmpty((java.lang.String) r8)
            if (r1 != 0) goto L_0x00a9
            java.lang.String r1 = "UC"
            boolean r1 = r8.startsWith(r1)
            if (r1 == 0) goto L_0x00a9
            r1 = 2
            java.lang.String r8 = r8.substring(r1)
            r2 = 0
            java.lang.Object r0 = r0.get(r2)
            java.lang.String r0 = (java.lang.String) r0
            r0.getClass()
            int r3 = r0.hashCode()
            r4 = -903148681(0xffffffffca2b0b77, float:-2802397.8)
            r5 = 1
            if (r3 == r4) goto L_0x0059
            r2 = -816678056(0xffffffffcf527b58, float:-3.53129882E9)
            if (r3 == r2) goto L_0x004e
            r2 = -439267705(0xffffffffe5d14e87, float:-1.235529E23)
            if (r3 == r2) goto L_0x0043
            goto L_0x0061
        L_0x0043:
            java.lang.String r2 = "livestreams"
            boolean r0 = r0.equals(r2)
            if (r0 != 0) goto L_0x004c
            goto L_0x0061
        L_0x004c:
            r2 = 2
            goto L_0x0062
        L_0x004e:
            java.lang.String r2 = "videos"
            boolean r0 = r0.equals(r2)
            if (r0 != 0) goto L_0x0057
            goto L_0x0061
        L_0x0057:
            r2 = 1
            goto L_0x0062
        L_0x0059:
            java.lang.String r3 = "shorts"
            boolean r0 = r0.equals(r3)
            if (r0 != 0) goto L_0x0062
        L_0x0061:
            r2 = -1
        L_0x0062:
            if (r2 == 0) goto L_0x007e
            if (r2 == r5) goto L_0x0077
            if (r2 != r1) goto L_0x006f
            java.lang.String r0 = "UULV"
            java.lang.String r8 = defpackage.y2.i(r0, r8)
            goto L_0x0084
        L_0x006f:
            java.lang.IllegalArgumentException r7 = new java.lang.IllegalArgumentException
            java.lang.String r8 = "Only Videos, Shorts and Livestreams tabs can extracted as playlists"
            r7.<init>(r8)
            throw r7
        L_0x0077:
            java.lang.String r0 = "UULF"
            java.lang.String r8 = defpackage.y2.i(r0, r8)
            goto L_0x0084
        L_0x007e:
            java.lang.String r0 = "UUSH"
            java.lang.String r8 = defpackage.y2.i(r0, r8)
        L_0x0084:
            r3 = r8
            org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubePlaylistLinkHandlerFactory r8 = org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubePlaylistLinkHandlerFactory.getInstance()     // Catch:{ ParsingException -> 0x00a2 }
            java.lang.String r2 = r8.getUrl(r3)     // Catch:{ ParsingException -> 0x00a2 }
            org.schabi.newpipe.extractor.linkhandler.ListLinkHandler r8 = new org.schabi.newpipe.extractor.linkhandler.ListLinkHandler     // Catch:{ ParsingException -> 0x00a2 }
            java.util.List r4 = java.util.Collections.emptyList()     // Catch:{ ParsingException -> 0x00a2 }
            java.lang.String r5 = ""
            r0 = r8
            r1 = r2
            r0.<init>(r1, r2, r3, r4, r5)     // Catch:{ ParsingException -> 0x00a2 }
            org.schabi.newpipe.extractor.services.youtube.extractors.YoutubePlaylistExtractor r0 = new org.schabi.newpipe.extractor.services.youtube.extractors.YoutubePlaylistExtractor
            r0.<init>(r7, r8)
            r6.g = r0
            return
        L_0x00a2:
            r7 = move-exception
            org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeChannelTabPlaylistExtractor$SystemPlaylistUrlCreationException r8 = new org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeChannelTabPlaylistExtractor$SystemPlaylistUrlCreationException
            r8.<init>(r7)
            throw r8
        L_0x00a9:
            java.lang.IllegalArgumentException r7 = new java.lang.IllegalArgumentException
            java.lang.String r8 = "Invalid channel ID"
            r7.<init>(r8)
            throw r7
        L_0x00b1:
            java.lang.IllegalArgumentException r7 = new java.lang.IllegalArgumentException
            java.lang.String r8 = "A content filter is required"
            r7.<init>(r8)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeChannelTabPlaylistExtractor.<init>(org.schabi.newpipe.extractor.StreamingService, org.schabi.newpipe.extractor.linkhandler.ListLinkHandler):void");
    }

    public ListExtractor.InfoItemsPage getInitialPage() throws IOException, ExtractionException {
        if (!this.h) {
            return ListExtractor.InfoItemsPage.emptyPage();
        }
        return this.g.getInitialPage();
    }

    public ListExtractor.InfoItemsPage getPage(Page page) throws IOException, ExtractionException {
        if (!this.h) {
            return ListExtractor.InfoItemsPage.emptyPage();
        }
        return this.g.getPage(page);
    }

    public void onFetchPage(Downloader downloader) throws IOException, ExtractionException {
        try {
            this.g.onFetchPage(downloader);
            if (!this.h) {
                this.h = true;
            }
        } catch (ContentNotAvailableException unused) {
        }
    }
}
