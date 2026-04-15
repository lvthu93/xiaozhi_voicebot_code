package defpackage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.InfoItem;
import org.schabi.newpipe.extractor.ServiceList;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.downloader.Request;
import org.schabi.newpipe.extractor.downloader.Response;
import org.schabi.newpipe.extractor.exceptions.ReCaptchaException;
import org.schabi.newpipe.extractor.playlist.PlaylistInfo;
import org.schabi.newpipe.extractor.playlist.PlaylistInfoItem;
import org.schabi.newpipe.extractor.search.SearchInfo;
import org.schabi.newpipe.extractor.services.youtube.YoutubeService;
import org.schabi.newpipe.extractor.stream.AudioStream;
import org.schabi.newpipe.extractor.stream.StreamInfo;
import org.schabi.newpipe.extractor.stream.StreamInfoItem;

/* renamed from: lf  reason: default package */
public final class lf {
    public static boolean a = false;

    /* renamed from: lf$b */
    public static class b extends Downloader {
        public final OkHttpClient a;

        public b() {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            TimeUnit timeUnit = TimeUnit.SECONDS;
            builder.connectTimeout(30, timeUnit);
            builder.readTimeout(30, timeUnit);
            builder.writeTimeout(30, timeUnit);
            builder.followRedirects(true);
            builder.followSslRedirects(true);
            this.a = builder.build();
        }

        public final Response execute(Request request) throws IOException, ReCaptchaException {
            String str;
            String url = request.url();
            Map<String, List<String>> headers = request.headers();
            byte[] dataToSend = request.dataToSend();
            Request.Builder builder = new Request.Builder();
            builder.url(url);
            if (headers != null) {
                for (Map.Entry next : headers.entrySet()) {
                    String str2 = (String) next.getKey();
                    List<String> list = (List) next.getValue();
                    if (list != null) {
                        for (String addHeader : list) {
                            builder.addHeader(str2, addHeader);
                        }
                    }
                }
            }
            if (headers == null || !headers.containsKey("User-Agent")) {
                builder.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
            }
            if (dataToSend != null) {
                builder.post(RequestBody.create((MediaType) null, dataToSend));
            }
            okhttp3.Response execute = this.a.newCall(builder.build()).execute();
            if (execute.code() != 429) {
                ResponseBody body = execute.body();
                if (body != null) {
                    str = body.string();
                } else {
                    str = "";
                }
                return new Response(execute.code(), execute.message(), execute.headers().toMultimap(), str, execute.request().url().toString());
            }
            execute.close();
            throw new ReCaptchaException("reCaptcha Challenge requested", url);
        }
    }

    /* renamed from: lf$c */
    public static class c {
        public final String a;
        public final String b;
        public final String c;
        public final long d;
        public final String e;

        public c(String str, String str2, String str3, long j, String str4) {
            this.a = str;
            this.b = str2;
            this.c = str3;
            this.d = j;
            this.e = str4;
        }
    }

    public static a a(String str) {
        d();
        if (!a) {
            return new a("YouTube extractor not initialized");
        }
        try {
            YoutubeService youtubeService = ServiceList.a;
            if (!str.contains("youtube.com") && !str.contains("youtu.be")) {
                str = "https://www.youtube.com/watch?v=".concat(str);
            }
            StreamInfo info2 = StreamInfo.getInfo(youtubeService, str);
            String name = info2.getName();
            String uploaderName = info2.getUploaderName();
            info2.getDuration();
            List<AudioStream> audioStreams = info2.getAudioStreams();
            if (audioStreams != null && !audioStreams.isEmpty()) {
                AudioStream audioStream = null;
                int i = 0;
                for (AudioStream next : audioStreams) {
                    String mimeType = next.getFormat().getMimeType();
                    int averageBitrate = next.getAverageBitrate();
                    if ((mimeType.contains("audio/mp4") || mimeType.contains("m4a")) && averageBitrate > i) {
                        audioStream = next;
                        i = averageBitrate;
                    }
                }
                if (audioStream == null) {
                    for (AudioStream next2 : audioStreams) {
                        int averageBitrate2 = next2.getAverageBitrate();
                        if (averageBitrate2 > i) {
                            audioStream = next2;
                            i = averageBitrate2;
                        }
                    }
                }
                if (audioStream != null) {
                    String url = audioStream.getUrl();
                    audioStream.getFormat().getName();
                    return new a(name, uploaderName, b(str), url);
                }
            }
            return new a("No audio streams available");
        } catch (Exception e) {
            e.getMessage();
            return new a("Extraction failed: " + e.getMessage());
        }
    }

    public static String b(String str) {
        if (str == null) {
            return "";
        }
        if (str.contains("v=")) {
            String substring = str.substring(str.indexOf("v=") + 2);
            if (substring.contains("&")) {
                return substring.substring(0, substring.indexOf("&"));
            }
            return substring;
        } else if (!str.contains("youtu.be/")) {
            return "";
        } else {
            String substring2 = str.substring(str.indexOf("youtu.be/") + 9);
            if (substring2.contains("?")) {
                return substring2.substring(0, substring2.indexOf("?"));
            }
            return substring2;
        }
    }

    public static ArrayList c(String str) {
        List list;
        List<StreamInfoItem> relatedItems;
        String str2;
        String str3;
        d();
        ArrayList arrayList = new ArrayList();
        if (!a) {
            return arrayList;
        }
        try {
            YoutubeService youtubeService = ServiceList.a;
            SearchInfo info2 = SearchInfo.getInfo(youtubeService, youtubeService.getSearchQHFactory().fromQuery(str, Collections.singletonList("playlists"), ""));
            PlaylistInfo playlistInfo = null;
            if (info2 != null) {
                list = info2.getRelatedItems();
            } else {
                list = null;
            }
            if (list != null && !list.isEmpty()) {
                Iterator it = list.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    InfoItem infoItem = (InfoItem) it.next();
                    if (infoItem instanceof PlaylistInfoItem) {
                        try {
                            playlistInfo = PlaylistInfo.getInfo(youtubeService, ((PlaylistInfoItem) infoItem).getUrl());
                            if (playlistInfo != null) {
                                playlistInfo.getName();
                                playlistInfo.getStreamCount();
                                break;
                            }
                        } catch (Exception e) {
                            e.getMessage();
                        }
                    }
                }
                if (!(playlistInfo == null || (relatedItems = playlistInfo.getRelatedItems()) == null || relatedItems.isEmpty())) {
                    for (StreamInfoItem streamInfoItem : relatedItems) {
                        String url = streamInfoItem.getUrl();
                        String name = streamInfoItem.getName();
                        String uploaderName = streamInfoItem.getUploaderName();
                        long duration = streamInfoItem.getDuration();
                        String b2 = b(url);
                        try {
                            List<Image> thumbnails = streamInfoItem.getThumbnails();
                            if (thumbnails == null || thumbnails.isEmpty()) {
                                str3 = "";
                            } else {
                                str3 = thumbnails.get(0).getUrl();
                            }
                            str2 = str3;
                        } catch (Exception e2) {
                            e2.getMessage();
                            str2 = "";
                        }
                        arrayList.add(new c(b2, name, uploaderName, duration, str2));
                    }
                    arrayList.size();
                }
            }
        } catch (Exception e3) {
            e3.getMessage();
        }
        return arrayList;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(6:11|12|13|14|15|16) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x0018 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void d() {
        /*
            java.lang.Class<lf> r0 = defpackage.lf.class
            monitor-enter(r0)
            java.lang.Class<lf> r1 = defpackage.lf.class
            monitor-enter(r1)     // Catch:{ all -> 0x001e }
            boolean r2 = a     // Catch:{ all -> 0x001b }
            if (r2 == 0) goto L_0x000d
            monitor-exit(r1)     // Catch:{ all -> 0x001b }
            monitor-exit(r0)
            return
        L_0x000d:
            lf$b r2 = new lf$b     // Catch:{ Exception -> 0x0018 }
            r2.<init>()     // Catch:{ Exception -> 0x0018 }
            org.schabi.newpipe.extractor.NewPipe.init(r2)     // Catch:{ Exception -> 0x0018 }
            r2 = 1
            a = r2     // Catch:{ Exception -> 0x0018 }
        L_0x0018:
            monitor-exit(r1)     // Catch:{ all -> 0x001b }
            monitor-exit(r0)
            return
        L_0x001b:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x001b }
            throw r2     // Catch:{ all -> 0x001e }
        L_0x001e:
            r1 = move-exception
            monitor-exit(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.lf.d():void");
    }

    public static ArrayList e(String str) {
        List<InfoItem> list;
        String str2;
        String str3;
        d();
        ArrayList arrayList = new ArrayList();
        if (!a) {
            return arrayList;
        }
        try {
            YoutubeService youtubeService = ServiceList.a;
            SearchInfo info2 = SearchInfo.getInfo(youtubeService, youtubeService.getSearchQHFactory().fromQuery(str, Collections.singletonList("videos"), ""));
            if (info2 != null) {
                list = info2.getRelatedItems();
            } else {
                list = null;
            }
            if (list != null && !list.isEmpty()) {
                int i = 0;
                for (InfoItem infoItem : list) {
                    if ((infoItem instanceof StreamInfoItem) && i < 10) {
                        StreamInfoItem streamInfoItem = (StreamInfoItem) infoItem;
                        String url = streamInfoItem.getUrl();
                        String name = streamInfoItem.getName();
                        String uploaderName = streamInfoItem.getUploaderName();
                        long duration = streamInfoItem.getDuration();
                        String b2 = b(url);
                        try {
                            List<Image> thumbnails = streamInfoItem.getThumbnails();
                            if (thumbnails == null || thumbnails.isEmpty()) {
                                str3 = "";
                            } else {
                                str3 = thumbnails.get(0).getUrl();
                            }
                            str2 = str3;
                        } catch (Exception e) {
                            e.getMessage();
                            str2 = "";
                        }
                        arrayList.add(new c(b2, name, uploaderName, duration, str2));
                        i++;
                    }
                }
                arrayList.size();
            }
        } catch (Exception e2) {
            e2.getMessage();
        }
        return arrayList;
    }

    /* renamed from: lf$a */
    public static class a {
        public final String a;
        public final String b;
        public final String c;
        public final boolean d;
        public final String e;
        public final String f;

        public a(String str, String str2, String str3, String str4) {
            this.d = true;
            this.c = null;
            this.e = str;
            this.b = str2;
            this.f = str3;
            this.a = str4;
        }

        public a(String str) {
            this.d = false;
            this.c = str;
            this.e = null;
            this.b = null;
            this.f = null;
            this.a = null;
        }
    }
}
