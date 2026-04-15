package com.google.android.exoplayer2.offline;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.database.DatabaseProvider;
import com.google.android.exoplayer2.offline.Downloader;
import com.google.android.exoplayer2.scheduler.Requirements;
import com.google.android.exoplayer2.scheduler.RequirementsWatcher;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executor;

public final class DownloadManager {
    public static final Requirements q = new Requirements(1);
    public final Context a;
    public final WritableDownloadIndex b;
    public final Handler c;
    public final b d;
    public final i2 e;
    public final CopyOnWriteArraySet<Listener> f;
    public int g;
    public int h;
    public boolean i;
    public boolean j;
    public int k;
    public int l;
    public int m;
    public boolean n;
    public List<Download> o;
    public RequirementsWatcher p;

    public interface Listener {
        void onDownloadChanged(DownloadManager downloadManager, Download download, @Nullable Exception exc);

        void onDownloadRemoved(DownloadManager downloadManager, Download download);

        void onDownloadsPausedChanged(DownloadManager downloadManager, boolean z);

        void onIdle(DownloadManager downloadManager);

        void onInitialized(DownloadManager downloadManager);

        void onRequirementsStateChanged(DownloadManager downloadManager, Requirements requirements, int i);

        void onWaitingForRequirementsChanged(DownloadManager downloadManager, boolean z);
    }

    public static final class a {
        public final Download a;
        public final boolean b;
        public final List<Download> c;
        @Nullable
        public final Exception d;

        public a(Download download, boolean z, List<Download> list, @Nullable Exception exc) {
            this.a = download;
            this.b = z;
            this.c = list;
            this.d = exc;
        }
    }

    public static final class b extends Handler {
        public static final /* synthetic */ int m = 0;
        public boolean a;
        public final HandlerThread b;
        public final WritableDownloadIndex c;
        public final DownloaderFactory d;
        public final Handler e;
        public final ArrayList<Download> f = new ArrayList<>();
        public final HashMap<String, c> g = new HashMap<>();
        public int h;
        public boolean i;
        public int j;
        public int k;
        public int l;

        public b(HandlerThread handlerThread, WritableDownloadIndex writableDownloadIndex, DownloaderFactory downloaderFactory, Handler handler, int i2, int i3, boolean z) {
            super(handlerThread.getLooper());
            this.b = handlerThread;
            this.c = writableDownloadIndex;
            this.d = downloaderFactory;
            this.e = handler;
            this.j = i2;
            this.k = i3;
            this.i = z;
        }

        public static Download a(Download download, int i2, int i3) {
            return new Download(download.a, i2, download.c, System.currentTimeMillis(), download.e, i3, 0, download.h);
        }

        @Nullable
        public final Download b(String str, boolean z) {
            String str2;
            int c2 = c(str);
            if (c2 != -1) {
                return this.f.get(c2);
            }
            if (!z) {
                return null;
            }
            try {
                return this.c.getDownload(str);
            } catch (IOException e2) {
                String valueOf = String.valueOf(str);
                if (valueOf.length() != 0) {
                    str2 = "Failed to load download: ".concat(valueOf);
                } else {
                    str2 = new String("Failed to load download: ");
                }
                Log.e("DownloadManager", str2, e2);
                return null;
            }
        }

        public final int c(String str) {
            int i2 = 0;
            while (true) {
                ArrayList<Download> arrayList = this.f;
                if (i2 >= arrayList.size()) {
                    return -1;
                }
                if (arrayList.get(i2).a.c.equals(str)) {
                    return i2;
                }
                i2++;
            }
        }

        public final void d(Download download) {
            boolean z;
            int i2 = download.b;
            boolean z2 = true;
            if (i2 == 3 || i2 == 4) {
                z = false;
            } else {
                z = true;
            }
            Assertions.checkState(z);
            int c2 = c(download.a.c);
            ArrayList<Download> arrayList = this.f;
            if (c2 == -1) {
                arrayList.add(download);
                Collections.sort(arrayList, new db(1));
            } else {
                if (download.c == arrayList.get(c2).c) {
                    z2 = false;
                }
                arrayList.set(c2, download);
                if (z2) {
                    Collections.sort(arrayList, new db(2));
                }
            }
            try {
                this.c.putDownload(download);
            } catch (IOException e2) {
                Log.e("DownloadManager", "Failed to update index.", e2);
            }
            this.e.obtainMessage(2, new a(download, false, new ArrayList(arrayList), (Exception) null)).sendToTarget();
        }

        public final Download e(Download download, int i2, int i3) {
            boolean z;
            if (i2 == 3 || i2 == 4) {
                z = false;
            } else {
                z = true;
            }
            Assertions.checkState(z);
            Download a2 = a(download, i2, i3);
            d(a2);
            return a2;
        }

        public final void f(Download download, int i2) {
            Download download2 = download;
            int i3 = i2;
            if (i3 == 0) {
                if (download2.b == 1) {
                    e(download, 0, 0);
                }
            } else if (i3 != download2.f) {
                int i4 = download2.b;
                if (i4 == 0 || i4 == 2) {
                    i4 = 1;
                }
                d(new Download(download2.a, i4, download2.c, System.currentTimeMillis(), download2.e, i2, 0, download2.h));
            }
        }

        public final void g() {
            int i2 = 0;
            int i3 = 0;
            while (true) {
                ArrayList<Download> arrayList = this.f;
                if (i2 < arrayList.size()) {
                    Download download = arrayList.get(i2);
                    HashMap<String, c> hashMap = this.g;
                    c cVar = hashMap.get(download.a.c);
                    boolean z = true;
                    DownloaderFactory downloaderFactory = this.d;
                    int i4 = download.b;
                    if (i4 != 0) {
                        if (i4 != 1) {
                            if (i4 == 2) {
                                Assertions.checkNotNull(cVar);
                                Assertions.checkState(!cVar.h);
                                if (this.i || this.h != 0) {
                                    z = false;
                                }
                                if (!z || i3 >= this.j) {
                                    e(download, 0, 0);
                                    cVar.cancel(false);
                                }
                            } else if (i4 != 5 && i4 != 7) {
                                throw new IllegalStateException();
                            } else if (cVar == null) {
                                DownloadRequest downloadRequest = download.a;
                                c cVar2 = new c(download.a, downloaderFactory.createDownloader(downloadRequest), download.h, true, this.k, this);
                                hashMap.put(downloadRequest.c, cVar2);
                                cVar2.start();
                            } else if (!cVar.h) {
                                cVar.cancel(false);
                            }
                        } else if (cVar != null) {
                            Assertions.checkState(!cVar.h);
                            cVar.cancel(false);
                        }
                    } else if (cVar != null) {
                        Assertions.checkState(!cVar.h);
                        cVar.cancel(false);
                    } else {
                        if (this.i || this.h != 0) {
                            z = false;
                        }
                        if (!z || this.l >= this.j) {
                            cVar = null;
                        } else {
                            Download e2 = e(download, 2, 0);
                            DownloadRequest downloadRequest2 = e2.a;
                            c cVar3 = new c(e2.a, downloaderFactory.createDownloader(downloadRequest2), e2.h, false, this.k, this);
                            hashMap.put(downloadRequest2.c, cVar3);
                            int i5 = this.l;
                            this.l = i5 + 1;
                            if (i5 == 0) {
                                sendEmptyMessageDelayed(11, 5000);
                            }
                            cVar3.start();
                            cVar = cVar3;
                        }
                    }
                    if (cVar != null && !cVar.h) {
                        i3++;
                    }
                    i2++;
                } else {
                    return;
                }
            }
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v1, resolved type: int} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v3, resolved type: boolean} */
        /* JADX WARNING: type inference failed for: r10v0 */
        /* JADX WARNING: type inference failed for: r10v2 */
        /* JADX WARNING: type inference failed for: r10v4 */
        /* JADX WARNING: type inference failed for: r10v5, types: [int] */
        /* JADX WARNING: type inference failed for: r10v7, types: [int] */
        /* JADX WARNING: type inference failed for: r10v9 */
        /* JADX WARNING: type inference failed for: r10v10 */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x0056, code lost:
            r0 = r1.f;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x005c, code lost:
            if (r10 >= r0.size()) goto L_0x0079;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x005e, code lost:
            r0 = r0.get(r10);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x0066, code lost:
            if (r0.b != 2) goto L_0x0076;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:?, code lost:
            r1.c.putDownload(r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x006e, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x006f, code lost:
            com.google.android.exoplayer2.util.Log.e("DownloadManager", "Failed to update index.", r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:0x0079, code lost:
            sendEmptyMessageDelayed(11, 5000);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x007e, code lost:
            return;
         */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void handleMessage(android.os.Message r27) {
            /*
                r26 = this;
                r1 = r26
                r0 = r27
                int r2 = r0.what
                r3 = 11
                r4 = 7
                r5 = 4
                r6 = 3
                r7 = 0
                r8 = 5
                r9 = 2
                r10 = 0
                r11 = 1
                switch(r2) {
                    case 0: goto L_0x0355;
                    case 1: goto L_0x034a;
                    case 2: goto L_0x0342;
                    case 3: goto L_0x02ed;
                    case 4: goto L_0x02e4;
                    case 5: goto L_0x02de;
                    case 6: goto L_0x02a7;
                    case 7: goto L_0x0278;
                    case 8: goto L_0x01d5;
                    case 9: goto L_0x00c6;
                    case 10: goto L_0x007f;
                    case 11: goto L_0x0056;
                    case 12: goto L_0x0019;
                    default: goto L_0x0013;
                }
            L_0x0013:
                java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                r0.<init>()
                throw r0
            L_0x0019:
                java.util.HashMap<java.lang.String, com.google.android.exoplayer2.offline.DownloadManager$c> r0 = r1.g
                java.util.Collection r0 = r0.values()
                java.util.Iterator r0 = r0.iterator()
            L_0x0023:
                boolean r2 = r0.hasNext()
                if (r2 == 0) goto L_0x0033
                java.lang.Object r2 = r0.next()
                com.google.android.exoplayer2.offline.DownloadManager$c r2 = (com.google.android.exoplayer2.offline.DownloadManager.c) r2
                r2.cancel(r11)
                goto L_0x0023
            L_0x0033:
                com.google.android.exoplayer2.offline.WritableDownloadIndex r0 = r1.c     // Catch:{ IOException -> 0x0039 }
                r0.setDownloadingStatesToQueued()     // Catch:{ IOException -> 0x0039 }
                goto L_0x0041
            L_0x0039:
                r0 = move-exception
                java.lang.String r2 = "DownloadManager"
                java.lang.String r3 = "Failed to update index."
                com.google.android.exoplayer2.util.Log.e(r2, r3, r0)
            L_0x0041:
                java.util.ArrayList<com.google.android.exoplayer2.offline.Download> r0 = r1.f
                r0.clear()
                android.os.HandlerThread r0 = r1.b
                r0.quit()
                monitor-enter(r26)
                r1.a = r11     // Catch:{ all -> 0x0053 }
                r26.notifyAll()     // Catch:{ all -> 0x0053 }
                monitor-exit(r26)     // Catch:{ all -> 0x0053 }
                return
            L_0x0053:
                r0 = move-exception
                monitor-exit(r26)     // Catch:{ all -> 0x0053 }
                throw r0
            L_0x0056:
                java.util.ArrayList<com.google.android.exoplayer2.offline.Download> r0 = r1.f
                int r2 = r0.size()
                if (r10 >= r2) goto L_0x0079
                java.lang.Object r0 = r0.get(r10)
                com.google.android.exoplayer2.offline.Download r0 = (com.google.android.exoplayer2.offline.Download) r0
                int r2 = r0.b
                if (r2 != r9) goto L_0x0076
                com.google.android.exoplayer2.offline.WritableDownloadIndex r2 = r1.c     // Catch:{ IOException -> 0x006e }
                r2.putDownload(r0)     // Catch:{ IOException -> 0x006e }
                goto L_0x0076
            L_0x006e:
                r0 = move-exception
                java.lang.String r2 = "DownloadManager"
                java.lang.String r4 = "Failed to update index."
                com.google.android.exoplayer2.util.Log.e(r2, r4, r0)
            L_0x0076:
                int r10 = r10 + 1
                goto L_0x0056
            L_0x0079:
                r4 = 5000(0x1388, double:2.4703E-320)
                r1.sendEmptyMessageDelayed(r3, r4)
                return
            L_0x007f:
                java.lang.Object r2 = r0.obj
                com.google.android.exoplayer2.offline.DownloadManager$c r2 = (com.google.android.exoplayer2.offline.DownloadManager.c) r2
                int r3 = r0.arg1
                int r0 = r0.arg2
                long r18 = com.google.android.exoplayer2.util.Util.toLong(r3, r0)
                com.google.android.exoplayer2.offline.DownloadRequest r0 = r2.c
                java.lang.String r0 = r0.c
                com.google.android.exoplayer2.offline.Download r0 = r1.b(r0, r10)
                java.lang.Object r0 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r0)
                com.google.android.exoplayer2.offline.Download r0 = (com.google.android.exoplayer2.offline.Download) r0
                long r2 = r0.e
                int r4 = (r18 > r2 ? 1 : (r18 == r2 ? 0 : -1))
                if (r4 == 0) goto L_0x00c5
                r2 = -1
                int r4 = (r18 > r2 ? 1 : (r18 == r2 ? 0 : -1))
                if (r4 != 0) goto L_0x00a6
                goto L_0x00c5
            L_0x00a6:
                com.google.android.exoplayer2.offline.Download r2 = new com.google.android.exoplayer2.offline.Download
                com.google.android.exoplayer2.offline.DownloadRequest r12 = r0.a
                int r13 = r0.b
                long r14 = r0.c
                long r16 = java.lang.System.currentTimeMillis()
                int r3 = r0.f
                int r4 = r0.g
                com.google.android.exoplayer2.offline.DownloadProgress r0 = r0.h
                r11 = r2
                r20 = r3
                r21 = r4
                r22 = r0
                r11.<init>(r12, r13, r14, r16, r18, r20, r21, r22)
                r1.d(r2)
            L_0x00c5:
                return
            L_0x00c6:
                java.lang.Object r0 = r0.obj
                com.google.android.exoplayer2.offline.DownloadManager$c r0 = (com.google.android.exoplayer2.offline.DownloadManager.c) r0
                com.google.android.exoplayer2.offline.DownloadRequest r2 = r0.c
                java.lang.String r2 = r2.c
                java.util.HashMap<java.lang.String, com.google.android.exoplayer2.offline.DownloadManager$c> r12 = r1.g
                r12.remove(r2)
                boolean r12 = r0.h
                if (r12 != 0) goto L_0x00e1
                int r13 = r1.l
                int r13 = r13 - r11
                r1.l = r13
                if (r13 != 0) goto L_0x00e1
                r1.removeMessages(r3)
            L_0x00e1:
                boolean r3 = r0.k
                if (r3 == 0) goto L_0x00ea
                r26.g()
                goto L_0x03a0
            L_0x00ea:
                java.lang.Exception r3 = r0.l
                java.lang.String r13 = "DownloadManager"
                if (r3 == 0) goto L_0x0118
                com.google.android.exoplayer2.offline.DownloadRequest r0 = r0.c
                java.lang.String r0 = java.lang.String.valueOf(r0)
                int r14 = r0.length()
                int r14 = r14 + 20
                java.lang.StringBuilder r15 = new java.lang.StringBuilder
                r15.<init>(r14)
                java.lang.String r14 = "Task failed: "
                r15.append(r14)
                r15.append(r0)
                java.lang.String r0 = ", "
                r15.append(r0)
                r15.append(r12)
                java.lang.String r0 = r15.toString()
                com.google.android.exoplayer2.util.Log.e(r13, r0, r3)
            L_0x0118:
                com.google.android.exoplayer2.offline.Download r0 = r1.b(r2, r10)
                java.lang.Object r0 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r0)
                com.google.android.exoplayer2.offline.Download r0 = (com.google.android.exoplayer2.offline.Download) r0
                int r2 = r0.b
                if (r2 == r9) goto L_0x0175
                if (r2 == r8) goto L_0x0131
                if (r2 != r4) goto L_0x012b
                goto L_0x0131
            L_0x012b:
                java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                r0.<init>()
                throw r0
            L_0x0131:
                com.google.android.exoplayer2.util.Assertions.checkState(r12)
                int r2 = r0.b
                if (r2 != r4) goto L_0x0147
                int r2 = r0.f
                if (r2 != 0) goto L_0x013e
                r3 = 0
                goto L_0x013f
            L_0x013e:
                r3 = 1
            L_0x013f:
                r1.e(r0, r3, r2)
                r26.g()
                goto L_0x01d0
            L_0x0147:
                com.google.android.exoplayer2.offline.DownloadRequest r2 = r0.a
                java.lang.String r3 = r2.c
                int r3 = r1.c(r3)
                java.util.ArrayList<com.google.android.exoplayer2.offline.Download> r4 = r1.f
                r4.remove(r3)
                com.google.android.exoplayer2.offline.WritableDownloadIndex r3 = r1.c     // Catch:{ IOException -> 0x015c }
                java.lang.String r2 = r2.c     // Catch:{ IOException -> 0x015c }
                r3.removeDownload(r2)     // Catch:{ IOException -> 0x015c }
                goto L_0x0161
            L_0x015c:
                java.lang.String r2 = "Failed to remove from database"
                com.google.android.exoplayer2.util.Log.e(r13, r2)
            L_0x0161:
                com.google.android.exoplayer2.offline.DownloadManager$a r2 = new com.google.android.exoplayer2.offline.DownloadManager$a
                java.util.ArrayList r3 = new java.util.ArrayList
                r3.<init>(r4)
                r2.<init>(r0, r11, r3, r7)
                android.os.Handler r0 = r1.e
                android.os.Message r0 = r0.obtainMessage(r9, r2)
                r0.sendToTarget()
                goto L_0x01d0
            L_0x0175:
                r2 = r12 ^ 1
                com.google.android.exoplayer2.util.Assertions.checkState(r2)
                com.google.android.exoplayer2.offline.Download r2 = new com.google.android.exoplayer2.offline.Download
                com.google.android.exoplayer2.offline.DownloadRequest r15 = r0.a
                if (r3 != 0) goto L_0x0183
                r16 = 3
                goto L_0x0185
            L_0x0183:
                r16 = 4
            L_0x0185:
                long r4 = r0.c
                long r19 = java.lang.System.currentTimeMillis()
                long r6 = r0.e
                int r8 = r0.f
                if (r3 != 0) goto L_0x0194
                r24 = 0
                goto L_0x0196
            L_0x0194:
                r24 = 1
            L_0x0196:
                com.google.android.exoplayer2.offline.DownloadProgress r0 = r0.h
                r14 = r2
                r17 = r4
                r21 = r6
                r23 = r8
                r25 = r0
                r14.<init>(r15, r16, r17, r19, r21, r23, r24, r25)
                java.util.ArrayList<com.google.android.exoplayer2.offline.Download> r4 = r1.f
                com.google.android.exoplayer2.offline.DownloadRequest r0 = r2.a
                java.lang.String r0 = r0.c
                int r0 = r1.c(r0)
                r4.remove(r0)
                com.google.android.exoplayer2.offline.WritableDownloadIndex r0 = r1.c     // Catch:{ IOException -> 0x01b7 }
                r0.putDownload(r2)     // Catch:{ IOException -> 0x01b7 }
                goto L_0x01bd
            L_0x01b7:
                r0 = move-exception
                java.lang.String r5 = "Failed to update index."
                com.google.android.exoplayer2.util.Log.e(r13, r5, r0)
            L_0x01bd:
                com.google.android.exoplayer2.offline.DownloadManager$a r0 = new com.google.android.exoplayer2.offline.DownloadManager$a
                java.util.ArrayList r5 = new java.util.ArrayList
                r5.<init>(r4)
                r0.<init>(r2, r10, r5, r3)
                android.os.Handler r2 = r1.e
                android.os.Message r0 = r2.obtainMessage(r9, r0)
                r0.sendToTarget()
            L_0x01d0:
                r26.g()
                goto L_0x03a0
            L_0x01d5:
                java.lang.String r2 = "DownloadManager"
                com.google.android.exoplayer2.offline.WritableDownloadIndex r3 = r1.c
                java.util.ArrayList r4 = new java.util.ArrayList
                r4.<init>()
                int[] r0 = new int[r9]     // Catch:{ IOException -> 0x0208 }
                r0[r10] = r6     // Catch:{ IOException -> 0x0208 }
                r0[r11] = r5     // Catch:{ IOException -> 0x0208 }
                com.google.android.exoplayer2.offline.DownloadCursor r5 = r3.getDownloads(r0)     // Catch:{ IOException -> 0x0208 }
            L_0x01e8:
                boolean r0 = r5.moveToNext()     // Catch:{ all -> 0x01fa }
                if (r0 == 0) goto L_0x01f6
                com.google.android.exoplayer2.offline.Download r0 = r5.getDownload()     // Catch:{ all -> 0x01fa }
                r4.add(r0)     // Catch:{ all -> 0x01fa }
                goto L_0x01e8
            L_0x01f6:
                r5.close()     // Catch:{ IOException -> 0x0208 }
                goto L_0x020d
            L_0x01fa:
                r0 = move-exception
                r12 = r0
                if (r5 == 0) goto L_0x0207
                r5.close()     // Catch:{ all -> 0x0202 }
                goto L_0x0207
            L_0x0202:
                r0 = move-exception
                r5 = r0
                r12.addSuppressed(r5)     // Catch:{ IOException -> 0x0208 }
            L_0x0207:
                throw r12     // Catch:{ IOException -> 0x0208 }
            L_0x0208:
                java.lang.String r0 = "Failed to load downloads."
                com.google.android.exoplayer2.util.Log.e(r2, r0)
            L_0x020d:
                r0 = 0
            L_0x020e:
                java.util.ArrayList<com.google.android.exoplayer2.offline.Download> r5 = r1.f
                int r12 = r5.size()
                if (r0 >= r12) goto L_0x0226
                java.lang.Object r12 = r5.get(r0)
                com.google.android.exoplayer2.offline.Download r12 = (com.google.android.exoplayer2.offline.Download) r12
                com.google.android.exoplayer2.offline.Download r12 = a(r12, r8, r10)
                r5.set(r0, r12)
                int r0 = r0 + 1
                goto L_0x020e
            L_0x0226:
                r0 = 0
            L_0x0227:
                int r12 = r4.size()
                if (r0 >= r12) goto L_0x023d
                java.lang.Object r12 = r4.get(r0)
                com.google.android.exoplayer2.offline.Download r12 = (com.google.android.exoplayer2.offline.Download) r12
                com.google.android.exoplayer2.offline.Download r12 = a(r12, r8, r10)
                r5.add(r12)
                int r0 = r0 + 1
                goto L_0x0227
            L_0x023d:
                db r0 = new db
                r0.<init>(r6)
                java.util.Collections.sort(r5, r0)
                r3.setStatesToRemoving()     // Catch:{ IOException -> 0x0249 }
                goto L_0x0250
            L_0x0249:
                r0 = move-exception
                r3 = r0
                java.lang.String r0 = "Failed to update index."
                com.google.android.exoplayer2.util.Log.e(r2, r0, r3)
            L_0x0250:
                java.util.ArrayList r0 = new java.util.ArrayList
                r0.<init>(r5)
                r2 = 0
            L_0x0256:
                int r3 = r5.size()
                if (r2 >= r3) goto L_0x0273
                com.google.android.exoplayer2.offline.DownloadManager$a r3 = new com.google.android.exoplayer2.offline.DownloadManager$a
                java.lang.Object r4 = r5.get(r2)
                com.google.android.exoplayer2.offline.Download r4 = (com.google.android.exoplayer2.offline.Download) r4
                r3.<init>(r4, r10, r0, r7)
                android.os.Handler r4 = r1.e
                android.os.Message r3 = r4.obtainMessage(r9, r3)
                r3.sendToTarget()
                int r2 = r2 + 1
                goto L_0x0256
            L_0x0273:
                r26.g()
                goto L_0x039f
            L_0x0278:
                java.lang.Object r0 = r0.obj
                java.lang.String r0 = (java.lang.String) r0
                com.google.android.exoplayer2.offline.Download r2 = r1.b(r0, r11)
                if (r2 != 0) goto L_0x029f
                java.lang.String r0 = java.lang.String.valueOf(r0)
                int r2 = r0.length()
                java.lang.String r3 = "Failed to remove nonexistent download: "
                if (r2 == 0) goto L_0x0293
                java.lang.String r0 = r3.concat(r0)
                goto L_0x0298
            L_0x0293:
                java.lang.String r0 = new java.lang.String
                r0.<init>(r3)
            L_0x0298:
                java.lang.String r2 = "DownloadManager"
                com.google.android.exoplayer2.util.Log.e(r2, r0)
                goto L_0x039f
            L_0x029f:
                r1.e(r2, r8, r10)
                r26.g()
                goto L_0x039f
            L_0x02a7:
                java.lang.Object r2 = r0.obj
                r13 = r2
                com.google.android.exoplayer2.offline.DownloadRequest r13 = (com.google.android.exoplayer2.offline.DownloadRequest) r13
                int r0 = r0.arg1
                java.lang.String r2 = r13.c
                com.google.android.exoplayer2.offline.Download r2 = r1.b(r2, r11)
                long r3 = java.lang.System.currentTimeMillis()
                if (r2 == 0) goto L_0x02c2
                com.google.android.exoplayer2.offline.Download r0 = com.google.android.exoplayer2.offline.DownloadManager.a(r2, r13, r0, r3)
                r1.d(r0)
                goto L_0x02d9
            L_0x02c2:
                com.google.android.exoplayer2.offline.Download r2 = new com.google.android.exoplayer2.offline.Download
                if (r0 == 0) goto L_0x02c8
                r14 = 1
                goto L_0x02c9
            L_0x02c8:
                r14 = 0
            L_0x02c9:
                r19 = -1
                r22 = 0
                r12 = r2
                r15 = r3
                r17 = r3
                r21 = r0
                r12.<init>(r13, r14, r15, r17, r19, r21, r22)
                r1.d(r2)
            L_0x02d9:
                r26.g()
                goto L_0x039f
            L_0x02de:
                int r0 = r0.arg1
                r1.k = r0
                goto L_0x039f
            L_0x02e4:
                int r0 = r0.arg1
                r1.j = r0
                r26.g()
                goto L_0x039f
            L_0x02ed:
                java.lang.Object r2 = r0.obj
                java.lang.String r2 = (java.lang.String) r2
                int r0 = r0.arg1
                java.lang.String r3 = "DownloadManager"
                com.google.android.exoplayer2.offline.WritableDownloadIndex r4 = r1.c
                if (r2 != 0) goto L_0x0319
            L_0x02f9:
                java.util.ArrayList<com.google.android.exoplayer2.offline.Download> r2 = r1.f
                int r5 = r2.size()
                if (r10 >= r5) goto L_0x030d
                java.lang.Object r2 = r2.get(r10)
                com.google.android.exoplayer2.offline.Download r2 = (com.google.android.exoplayer2.offline.Download) r2
                r1.f(r2, r0)
                int r10 = r10 + 1
                goto L_0x02f9
            L_0x030d:
                r4.setStopReason(r0)     // Catch:{ IOException -> 0x0311 }
                goto L_0x033e
            L_0x0311:
                r0 = move-exception
                r2 = r0
                java.lang.String r0 = "Failed to set manual stop reason"
                com.google.android.exoplayer2.util.Log.e(r3, r0, r2)
                goto L_0x033e
            L_0x0319:
                com.google.android.exoplayer2.offline.Download r5 = r1.b(r2, r10)
                if (r5 == 0) goto L_0x0323
                r1.f(r5, r0)
                goto L_0x033e
            L_0x0323:
                r4.setStopReason(r2, r0)     // Catch:{ IOException -> 0x0327 }
                goto L_0x033e
            L_0x0327:
                r0 = move-exception
                r4 = r0
                int r0 = r2.length()
                java.lang.String r5 = "Failed to set manual stop reason: "
                if (r0 == 0) goto L_0x0336
                java.lang.String r0 = r5.concat(r2)
                goto L_0x033b
            L_0x0336:
                java.lang.String r0 = new java.lang.String
                r0.<init>(r5)
            L_0x033b:
                com.google.android.exoplayer2.util.Log.e(r3, r0, r4)
            L_0x033e:
                r26.g()
                goto L_0x039f
            L_0x0342:
                int r0 = r0.arg1
                r1.h = r0
                r26.g()
                goto L_0x039f
            L_0x034a:
                int r0 = r0.arg1
                if (r0 == 0) goto L_0x034f
                r10 = 1
            L_0x034f:
                r1.i = r10
                r26.g()
                goto L_0x039f
            L_0x0355:
                int r0 = r0.arg1
                com.google.android.exoplayer2.offline.WritableDownloadIndex r2 = r1.c
                java.util.ArrayList<com.google.android.exoplayer2.offline.Download> r3 = r1.f
                r1.h = r0
                r2.setDownloadingStatesToQueued()     // Catch:{ IOException -> 0x0380 }
                int[] r0 = new int[r8]     // Catch:{ IOException -> 0x0380 }
                r0[r10] = r10     // Catch:{ IOException -> 0x0380 }
                r0[r11] = r11     // Catch:{ IOException -> 0x0380 }
                r0[r9] = r9     // Catch:{ IOException -> 0x0380 }
                r0[r6] = r8     // Catch:{ IOException -> 0x0380 }
                r0[r5] = r4     // Catch:{ IOException -> 0x0380 }
                com.google.android.exoplayer2.offline.DownloadCursor r7 = r2.getDownloads(r0)     // Catch:{ IOException -> 0x0380 }
            L_0x0370:
                boolean r0 = r7.moveToNext()     // Catch:{ IOException -> 0x0380 }
                if (r0 == 0) goto L_0x038b
                com.google.android.exoplayer2.offline.Download r0 = r7.getDownload()     // Catch:{ IOException -> 0x0380 }
                r3.add(r0)     // Catch:{ IOException -> 0x0380 }
                goto L_0x0370
            L_0x037e:
                r0 = move-exception
                goto L_0x03b0
            L_0x0380:
                r0 = move-exception
                java.lang.String r2 = "DownloadManager"
                java.lang.String r4 = "Failed to load index."
                com.google.android.exoplayer2.util.Log.e(r2, r4, r0)     // Catch:{ all -> 0x037e }
                r3.clear()     // Catch:{ all -> 0x037e }
            L_0x038b:
                com.google.android.exoplayer2.util.Util.closeQuietly((java.io.Closeable) r7)
                java.util.ArrayList r0 = new java.util.ArrayList
                r0.<init>(r3)
                android.os.Handler r2 = r1.e
                android.os.Message r0 = r2.obtainMessage(r10, r0)
                r0.sendToTarget()
                r26.g()
            L_0x039f:
                r10 = 1
            L_0x03a0:
                android.os.Handler r0 = r1.e
                java.util.HashMap<java.lang.String, com.google.android.exoplayer2.offline.DownloadManager$c> r2 = r1.g
                int r2 = r2.size()
                android.os.Message r0 = r0.obtainMessage(r11, r10, r2)
                r0.sendToTarget()
                return
            L_0x03b0:
                com.google.android.exoplayer2.util.Util.closeQuietly((java.io.Closeable) r7)
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.offline.DownloadManager.b.handleMessage(android.os.Message):void");
        }
    }

    public static class c extends Thread implements Downloader.ProgressListener {
        public final DownloadRequest c;
        public final Downloader f;
        public final DownloadProgress g;
        public final boolean h;
        public final int i;
        @Nullable
        public volatile b j;
        public volatile boolean k;
        @Nullable
        public Exception l;
        public long m = -1;

        public c(DownloadRequest downloadRequest, Downloader downloader, DownloadProgress downloadProgress, boolean z, int i2, b bVar) {
            this.c = downloadRequest;
            this.f = downloader;
            this.g = downloadProgress;
            this.h = z;
            this.i = i2;
            this.j = bVar;
        }

        public void cancel(boolean z) {
            if (z) {
                this.j = null;
            }
            if (!this.k) {
                this.k = true;
                this.f.cancel();
                interrupt();
            }
        }

        public void onProgress(long j2, long j3, float f2) {
            this.g.a = j3;
            this.g.b = f2;
            if (j2 != this.m) {
                this.m = j2;
                b bVar = this.j;
                if (bVar != null) {
                    bVar.obtainMessage(10, (int) (j2 >> 32), (int) j2, this).sendToTarget();
                }
            }
        }

        public void run() {
            long j2;
            int i2;
            try {
                if (this.h) {
                    this.f.remove();
                } else {
                    j2 = -1;
                    i2 = 0;
                    while (!this.k) {
                        this.f.download(this);
                    }
                }
            } catch (IOException e) {
                if (!this.k) {
                    long j3 = this.g.a;
                    if (j3 != j2) {
                        j2 = j3;
                        i2 = 0;
                    }
                    i2++;
                    if (i2 <= this.i) {
                        Thread.sleep((long) Math.min((i2 - 1) * 1000, 5000));
                    } else {
                        throw e;
                    }
                }
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
            } catch (Exception e2) {
                this.l = e2;
            }
            b bVar = this.j;
            if (bVar != null) {
                bVar.obtainMessage(9, this).sendToTarget();
            }
        }
    }

    @Deprecated
    public DownloadManager(Context context, DatabaseProvider databaseProvider, Cache cache, DataSource.Factory factory) {
        this(context, databaseProvider, cache, factory, new b1(1));
    }

    public static Download a(Download download, DownloadRequest downloadRequest, int i2, long j2) {
        long j3;
        int i3;
        Download download2 = download;
        int i4 = download2.b;
        if (i4 == 5 || download.isTerminalState()) {
            j3 = j2;
        } else {
            j3 = download2.c;
        }
        if (i4 == 5 || i4 == 7) {
            i3 = 7;
        } else if (i2 != 0) {
            i3 = 1;
        } else {
            i3 = 0;
        }
        return new Download(download2.a.copyWithMergedRequest(downloadRequest), i3, j3, j2, -1, i2, 0);
    }

    public void addDownload(DownloadRequest downloadRequest) {
        addDownload(downloadRequest, 0);
    }

    public void addListener(Listener listener) {
        Assertions.checkNotNull(listener);
        this.f.add(listener);
    }

    public final void b() {
        Iterator<Listener> it = this.f.iterator();
        while (it.hasNext()) {
            it.next().onWaitingForRequirementsChanged(this, this.n);
        }
    }

    public final void c(RequirementsWatcher requirementsWatcher, int i2) {
        Requirements requirements = requirementsWatcher.getRequirements();
        if (this.m != i2) {
            this.m = i2;
            this.g++;
            this.d.obtainMessage(2, i2, 0).sendToTarget();
        }
        boolean e2 = e();
        Iterator<Listener> it = this.f.iterator();
        while (it.hasNext()) {
            it.next().onRequirementsStateChanged(this, requirements, i2);
        }
        if (e2) {
            b();
        }
    }

    public final void d(boolean z) {
        if (this.j != z) {
            this.j = z;
            this.g++;
            this.d.obtainMessage(1, z ? 1 : 0, 0).sendToTarget();
            boolean e2 = e();
            Iterator<Listener> it = this.f.iterator();
            while (it.hasNext()) {
                it.next().onDownloadsPausedChanged(this, z);
            }
            if (e2) {
                b();
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x002a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean e() {
        /*
            r4 = this;
            boolean r0 = r4.j
            r1 = 1
            r2 = 0
            if (r0 != 0) goto L_0x0024
            int r0 = r4.m
            if (r0 == 0) goto L_0x0024
            r0 = 0
        L_0x000b:
            java.util.List<com.google.android.exoplayer2.offline.Download> r3 = r4.o
            int r3 = r3.size()
            if (r0 >= r3) goto L_0x0024
            java.util.List<com.google.android.exoplayer2.offline.Download> r3 = r4.o
            java.lang.Object r3 = r3.get(r0)
            com.google.android.exoplayer2.offline.Download r3 = (com.google.android.exoplayer2.offline.Download) r3
            int r3 = r3.b
            if (r3 != 0) goto L_0x0021
            r0 = 1
            goto L_0x0025
        L_0x0021:
            int r0 = r0 + 1
            goto L_0x000b
        L_0x0024:
            r0 = 0
        L_0x0025:
            boolean r3 = r4.n
            if (r3 == r0) goto L_0x002a
            goto L_0x002b
        L_0x002a:
            r1 = 0
        L_0x002b:
            r4.n = r0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.offline.DownloadManager.e():boolean");
    }

    public Looper getApplicationLooper() {
        return this.c.getLooper();
    }

    public List<Download> getCurrentDownloads() {
        return this.o;
    }

    public DownloadIndex getDownloadIndex() {
        return this.b;
    }

    public boolean getDownloadsPaused() {
        return this.j;
    }

    public int getMaxParallelDownloads() {
        return this.k;
    }

    public int getMinRetryCount() {
        return this.l;
    }

    public int getNotMetRequirements() {
        return this.m;
    }

    public Requirements getRequirements() {
        return this.p.getRequirements();
    }

    public boolean isIdle() {
        return this.h == 0 && this.g == 0;
    }

    public boolean isInitialized() {
        return this.i;
    }

    public boolean isWaitingForRequirements() {
        return this.n;
    }

    public void pauseDownloads() {
        d(true);
    }

    public void release() {
        synchronized (this.d) {
            b bVar = this.d;
            if (!bVar.a) {
                bVar.sendEmptyMessage(12);
                boolean z = false;
                while (true) {
                    b bVar2 = this.d;
                    if (bVar2.a) {
                        break;
                    }
                    try {
                        bVar2.wait();
                    } catch (InterruptedException unused) {
                        z = true;
                    }
                }
                if (z) {
                    Thread.currentThread().interrupt();
                }
                this.c.removeCallbacksAndMessages((Object) null);
                this.o = Collections.emptyList();
                this.g = 0;
                this.h = 0;
                this.i = false;
                this.m = 0;
                this.n = false;
            }
        }
    }

    public void removeAllDownloads() {
        this.g++;
        this.d.obtainMessage(8).sendToTarget();
    }

    public void removeDownload(String str) {
        this.g++;
        this.d.obtainMessage(7, str).sendToTarget();
    }

    public void removeListener(Listener listener) {
        this.f.remove(listener);
    }

    public void resumeDownloads() {
        d(false);
    }

    public void setMaxParallelDownloads(int i2) {
        boolean z;
        if (i2 > 0) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkArgument(z);
        if (this.k != i2) {
            this.k = i2;
            this.g++;
            this.d.obtainMessage(4, i2, 0).sendToTarget();
        }
    }

    public void setMinRetryCount(int i2) {
        boolean z;
        if (i2 >= 0) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkArgument(z);
        if (this.l != i2) {
            this.l = i2;
            this.g++;
            this.d.obtainMessage(5, i2, 0).sendToTarget();
        }
    }

    public void setRequirements(Requirements requirements) {
        if (!requirements.equals(this.p.getRequirements())) {
            this.p.stop();
            RequirementsWatcher requirementsWatcher = new RequirementsWatcher(this.a, this.e, requirements);
            this.p = requirementsWatcher;
            c(this.p, requirementsWatcher.start());
        }
    }

    public void setStopReason(@Nullable String str, int i2) {
        this.g++;
        this.d.obtainMessage(3, i2, 0, str).sendToTarget();
    }

    public DownloadManager(Context context, DatabaseProvider databaseProvider, Cache cache, DataSource.Factory factory, Executor executor) {
        this(context, new DefaultDownloadIndex(databaseProvider), new DefaultDownloaderFactory(new CacheDataSource.Factory().setCache(cache).setUpstreamDataSourceFactory(factory), executor));
    }

    public void addDownload(DownloadRequest downloadRequest, int i2) {
        this.g++;
        this.d.obtainMessage(6, i2, 0, downloadRequest).sendToTarget();
    }

    public DownloadManager(Context context, WritableDownloadIndex writableDownloadIndex, DownloaderFactory downloaderFactory) {
        this.a = context.getApplicationContext();
        this.b = writableDownloadIndex;
        this.k = 3;
        this.l = 5;
        this.j = true;
        this.o = Collections.emptyList();
        this.f = new CopyOnWriteArraySet<>();
        Handler createHandlerForCurrentOrMainLooper = Util.createHandlerForCurrentOrMainLooper(new q1(1, this));
        this.c = createHandlerForCurrentOrMainLooper;
        HandlerThread handlerThread = new HandlerThread("ExoPlayer:DownloadManager");
        handlerThread.start();
        b bVar = new b(handlerThread, writableDownloadIndex, downloaderFactory, createHandlerForCurrentOrMainLooper, this.k, this.l, this.j);
        this.d = bVar;
        i2 i2Var = new i2(5, this);
        this.e = i2Var;
        RequirementsWatcher requirementsWatcher = new RequirementsWatcher(context, i2Var, q);
        this.p = requirementsWatcher;
        int start = requirementsWatcher.start();
        this.m = start;
        this.g = 1;
        bVar.obtainMessage(0, start, 0).sendToTarget();
    }
}
