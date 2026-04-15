package com.google.android.exoplayer2.offline;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.offline.DownloadManager;
import com.google.android.exoplayer2.scheduler.Requirements;
import com.google.android.exoplayer2.scheduler.Scheduler;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import java.util.HashMap;
import java.util.List;

public abstract class DownloadService extends Service {
    public static final HashMap<Class<? extends DownloadService>, a> j = new HashMap<>();
    public DownloadManager c;
    public int f;
    public boolean g;
    public boolean h;
    public boolean i;

    public static final class a implements DownloadManager.Listener {
        public final Context a;
        public final DownloadManager b;
        public final boolean c;
        @Nullable
        public final Scheduler d;
        public final Class<? extends DownloadService> e;
        @Nullable
        public DownloadService f;

        public a() {
            throw null;
        }

        public a(Context context, DownloadManager downloadManager, Class cls) {
            this.a = context;
            this.b = downloadManager;
            this.c = false;
            this.d = null;
            this.e = cls;
            downloadManager.addListener(this);
            b();
        }

        public final void a() {
            boolean z = this.c;
            Class<? extends DownloadService> cls = this.e;
            Context context = this.a;
            if (z) {
                HashMap<Class<? extends DownloadService>, a> hashMap = DownloadService.j;
                Util.startForegroundService(context, new Intent(context, cls).setAction("com.google.android.exoplayer.downloadService.action.RESTART"));
                return;
            }
            try {
                HashMap<Class<? extends DownloadService>, a> hashMap2 = DownloadService.j;
                context.startService(new Intent(context, cls).setAction("com.google.android.exoplayer.downloadService.action.INIT"));
            } catch (IllegalStateException unused) {
                Log.w("DownloadService", "Failed to restart DownloadService (process is idle).");
            }
        }

        public void attachService(DownloadService downloadService) {
            boolean z;
            if (this.f == null) {
                z = true;
            } else {
                z = false;
            }
            Assertions.checkState(z);
            this.f = downloadService;
            if (this.b.isInitialized()) {
                Util.createHandlerForCurrentOrMainLooper().postAtFrontOfQueue(new h2(7, this, downloadService));
            }
        }

        public final void b() {
            Scheduler scheduler = this.d;
            if (scheduler != null) {
                DownloadManager downloadManager = this.b;
                if (downloadManager.isWaitingForRequirements()) {
                    if (!scheduler.schedule(downloadManager.getRequirements(), this.a.getPackageName(), "com.google.android.exoplayer.downloadService.action.RESTART")) {
                        Log.e("DownloadService", "Scheduling downloads failed.");
                        return;
                    }
                    return;
                }
                scheduler.cancel();
            }
        }

        public void detachService(DownloadService downloadService) {
            boolean z;
            if (this.f == downloadService) {
                z = true;
            } else {
                z = false;
            }
            Assertions.checkState(z);
            this.f = null;
            Scheduler scheduler = this.d;
            if (scheduler != null && !this.b.isWaitingForRequirements()) {
                scheduler.cancel();
            }
        }

        public void onDownloadChanged(DownloadManager downloadManager, Download download, @Nullable Exception exc) {
            boolean z;
            DownloadService downloadService = this.f;
            if (downloadService != null) {
                HashMap<Class<? extends DownloadService>, a> hashMap = DownloadService.j;
            }
            boolean z2 = false;
            if (downloadService == null || downloadService.i) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                int i = download.b;
                HashMap<Class<? extends DownloadService>, a> hashMap2 = DownloadService.j;
                if (i == 2 || i == 5 || i == 7) {
                    z2 = true;
                }
                if (z2) {
                    Log.w("DownloadService", "DownloadService wasn't running. Restarting.");
                    a();
                }
            }
        }

        public void onDownloadRemoved(DownloadManager downloadManager, Download download) {
            DownloadService downloadService = this.f;
            if (downloadService != null) {
                HashMap<Class<? extends DownloadService>, a> hashMap = DownloadService.j;
                downloadService.getClass();
            }
        }

        public /* bridge */ /* synthetic */ void onDownloadsPausedChanged(DownloadManager downloadManager, boolean z) {
            r1.c(this, downloadManager, z);
        }

        public final void onIdle(DownloadManager downloadManager) {
            DownloadService downloadService = this.f;
            if (downloadService != null) {
                HashMap<Class<? extends DownloadService>, a> hashMap = DownloadService.j;
                downloadService.f();
            }
        }

        public void onInitialized(DownloadManager downloadManager) {
            DownloadService downloadService = this.f;
            if (downloadService != null) {
                downloadManager.getCurrentDownloads();
                HashMap<Class<? extends DownloadService>, a> hashMap = DownloadService.j;
                downloadService.getClass();
            }
        }

        public /* bridge */ /* synthetic */ void onRequirementsStateChanged(DownloadManager downloadManager, Requirements requirements, int i) {
            r1.f(this, downloadManager, requirements, i);
        }

        public void onWaitingForRequirementsChanged(DownloadManager downloadManager, boolean z) {
            boolean z2;
            if (!z && !downloadManager.getDownloadsPaused()) {
                DownloadService downloadService = this.f;
                int i = 0;
                if (downloadService == null || downloadService.i) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (z2) {
                    List<Download> currentDownloads = downloadManager.getCurrentDownloads();
                    while (true) {
                        if (i >= currentDownloads.size()) {
                            break;
                        } else if (currentDownloads.get(i).b == 0) {
                            a();
                            break;
                        } else {
                            i++;
                        }
                    }
                }
            }
            b();
        }
    }

    public final class b {
        public final int a;
        public final long b;
        public final Handler c = new Handler(Looper.getMainLooper());
        public boolean d;
        public boolean e;

        public b(int i, long j) {
            this.a = i;
            this.b = j;
        }

        public final void a() {
            DownloadService downloadService = DownloadService.this;
            ((DownloadManager) Assertions.checkNotNull(downloadService.c)).getCurrentDownloads();
            downloadService.startForeground(this.a, downloadService.b());
            this.e = true;
            if (this.d) {
                Handler handler = this.c;
                handler.removeCallbacksAndMessages((Object) null);
                handler.postDelayed(new qb(5, this), this.b);
            }
        }

        public void invalidate() {
            if (this.e) {
                a();
            }
        }

        public void showNotificationIfNotAlready() {
            if (!this.e) {
                a();
            }
        }

        public void startPeriodicUpdates() {
            this.d = true;
            a();
        }

        public void stopPeriodicUpdates() {
            this.d = false;
            this.c.removeCallbacksAndMessages((Object) null);
        }
    }

    public static Intent buildAddDownloadIntent(Context context, Class<? extends DownloadService> cls, DownloadRequest downloadRequest, boolean z) {
        return buildAddDownloadIntent(context, cls, downloadRequest, 0, z);
    }

    public static Intent buildPauseDownloadsIntent(Context context, Class<? extends DownloadService> cls, boolean z) {
        return c(context, cls, "com.google.android.exoplayer.downloadService.action.PAUSE_DOWNLOADS", z);
    }

    public static Intent buildRemoveAllDownloadsIntent(Context context, Class<? extends DownloadService> cls, boolean z) {
        return c(context, cls, "com.google.android.exoplayer.downloadService.action.REMOVE_ALL_DOWNLOADS", z);
    }

    public static Intent buildRemoveDownloadIntent(Context context, Class<? extends DownloadService> cls, String str, boolean z) {
        return c(context, cls, "com.google.android.exoplayer.downloadService.action.REMOVE_DOWNLOAD", z).putExtra("content_id", str);
    }

    public static Intent buildResumeDownloadsIntent(Context context, Class<? extends DownloadService> cls, boolean z) {
        return c(context, cls, "com.google.android.exoplayer.downloadService.action.RESUME_DOWNLOADS", z);
    }

    public static Intent buildSetRequirementsIntent(Context context, Class<? extends DownloadService> cls, Requirements requirements, boolean z) {
        return c(context, cls, "com.google.android.exoplayer.downloadService.action.SET_REQUIREMENTS", z).putExtra("requirements", requirements);
    }

    public static Intent buildSetStopReasonIntent(Context context, Class<? extends DownloadService> cls, @Nullable String str, int i2, boolean z) {
        return c(context, cls, "com.google.android.exoplayer.downloadService.action.SET_STOP_REASON", z).putExtra("content_id", str).putExtra("stop_reason", i2);
    }

    public static Intent c(Context context, Class<? extends DownloadService> cls, String str, boolean z) {
        return new Intent(context, cls).setAction(str).putExtra("foreground", z);
    }

    public static void e(Context context, Intent intent, boolean z) {
        if (z) {
            Util.startForegroundService(context, intent);
        } else {
            context.startService(intent);
        }
    }

    public static void sendAddDownload(Context context, Class<? extends DownloadService> cls, DownloadRequest downloadRequest, boolean z) {
        e(context, buildAddDownloadIntent(context, cls, downloadRequest, z), z);
    }

    public static void sendPauseDownloads(Context context, Class<? extends DownloadService> cls, boolean z) {
        e(context, buildPauseDownloadsIntent(context, cls, z), z);
    }

    public static void sendRemoveAllDownloads(Context context, Class<? extends DownloadService> cls, boolean z) {
        e(context, buildRemoveAllDownloadsIntent(context, cls, z), z);
    }

    public static void sendRemoveDownload(Context context, Class<? extends DownloadService> cls, String str, boolean z) {
        e(context, buildRemoveDownloadIntent(context, cls, str, z), z);
    }

    public static void sendResumeDownloads(Context context, Class<? extends DownloadService> cls, boolean z) {
        e(context, buildResumeDownloadsIntent(context, cls, z), z);
    }

    public static void sendSetRequirements(Context context, Class<? extends DownloadService> cls, Requirements requirements, boolean z) {
        e(context, buildSetRequirementsIntent(context, cls, requirements, z), z);
    }

    public static void sendSetStopReason(Context context, Class<? extends DownloadService> cls, @Nullable String str, int i2, boolean z) {
        e(context, buildSetStopReasonIntent(context, cls, str, i2, z), z);
    }

    public static void start(Context context, Class<? extends DownloadService> cls) {
        context.startService(new Intent(context, cls).setAction("com.google.android.exoplayer.downloadService.action.INIT"));
    }

    public static void startForeground(Context context, Class<? extends DownloadService> cls) {
        Util.startForegroundService(context, c(context, cls, "com.google.android.exoplayer.downloadService.action.INIT", true));
    }

    public abstract DownloadManager a();

    public abstract Notification b();

    @Nullable
    public abstract Scheduler d();

    public final void f() {
        if (Util.a >= 28 || !this.h) {
            this.i |= stopSelfResult(this.f);
            return;
        }
        stopSelf();
        this.i = true;
    }

    @Nullable
    public final IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException();
    }

    public void onCreate() {
        Class<?> cls = getClass();
        HashMap<Class<? extends DownloadService>, a> hashMap = j;
        a aVar = hashMap.get(cls);
        if (aVar == null) {
            DownloadManager a2 = a();
            this.c = a2;
            a2.resumeDownloads();
            aVar = new a(getApplicationContext(), this.c, cls);
            hashMap.put(cls, aVar);
        } else {
            this.c = aVar.b;
        }
        aVar.attachService(this);
    }

    public void onDestroy() {
        ((a) Assertions.checkNotNull(j.get(getClass()))).detachService(this);
    }

    public int onStartCommand(@Nullable Intent intent, int i2, int i3) {
        String str;
        String str2;
        String str3;
        boolean z;
        this.f = i3;
        this.h = false;
        if (intent != null) {
            str2 = intent.getAction();
            str = intent.getStringExtra("content_id");
            boolean z2 = this.g;
            if (intent.getBooleanExtra("foreground", false) || "com.google.android.exoplayer.downloadService.action.RESTART".equals(str2)) {
                z = true;
            } else {
                z = false;
            }
            this.g = z2 | z;
        } else {
            str2 = null;
            str = null;
        }
        if (str2 == null) {
            str2 = "com.google.android.exoplayer.downloadService.action.INIT";
        }
        DownloadManager downloadManager = (DownloadManager) Assertions.checkNotNull(this.c);
        char c2 = 65535;
        switch (str2.hashCode()) {
            case -1931239035:
                if (str2.equals("com.google.android.exoplayer.downloadService.action.ADD_DOWNLOAD")) {
                    c2 = 0;
                    break;
                }
                break;
            case -932047176:
                if (str2.equals("com.google.android.exoplayer.downloadService.action.RESUME_DOWNLOADS")) {
                    c2 = 1;
                    break;
                }
                break;
            case -871181424:
                if (str2.equals("com.google.android.exoplayer.downloadService.action.RESTART")) {
                    c2 = 2;
                    break;
                }
                break;
            case -650547439:
                if (str2.equals("com.google.android.exoplayer.downloadService.action.REMOVE_ALL_DOWNLOADS")) {
                    c2 = 3;
                    break;
                }
                break;
            case -119057172:
                if (str2.equals("com.google.android.exoplayer.downloadService.action.SET_REQUIREMENTS")) {
                    c2 = 4;
                    break;
                }
                break;
            case 191112771:
                if (str2.equals("com.google.android.exoplayer.downloadService.action.PAUSE_DOWNLOADS")) {
                    c2 = 5;
                    break;
                }
                break;
            case 671523141:
                if (str2.equals("com.google.android.exoplayer.downloadService.action.SET_STOP_REASON")) {
                    c2 = 6;
                    break;
                }
                break;
            case 1015676687:
                if (str2.equals("com.google.android.exoplayer.downloadService.action.INIT")) {
                    c2 = 7;
                    break;
                }
                break;
            case 1547520644:
                if (str2.equals("com.google.android.exoplayer.downloadService.action.REMOVE_DOWNLOAD")) {
                    c2 = 8;
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
                DownloadRequest downloadRequest = (DownloadRequest) ((Intent) Assertions.checkNotNull(intent)).getParcelableExtra("download_request");
                if (downloadRequest != null) {
                    downloadManager.addDownload(downloadRequest, intent.getIntExtra("stop_reason", 0));
                    break;
                } else {
                    Log.e("DownloadService", "Ignored ADD_DOWNLOAD: Missing download_request extra");
                    break;
                }
            case 1:
                downloadManager.resumeDownloads();
                break;
            case 2:
            case 7:
                break;
            case 3:
                downloadManager.removeAllDownloads();
                break;
            case 4:
                Requirements requirements = (Requirements) ((Intent) Assertions.checkNotNull(intent)).getParcelableExtra("requirements");
                if (requirements != null) {
                    Scheduler d = d();
                    if (d != null) {
                        Requirements supportedRequirements = d.getSupportedRequirements(requirements);
                        if (!supportedRequirements.equals(requirements)) {
                            y2.t(65, "Ignoring requirements not supported by the Scheduler: ", requirements.getRequirements() ^ supportedRequirements.getRequirements(), "DownloadService");
                            requirements = supportedRequirements;
                        }
                    }
                    downloadManager.setRequirements(requirements);
                    break;
                } else {
                    Log.e("DownloadService", "Ignored SET_REQUIREMENTS: Missing requirements extra");
                    break;
                }
            case 5:
                downloadManager.pauseDownloads();
                break;
            case 6:
                if (((Intent) Assertions.checkNotNull(intent)).hasExtra("stop_reason")) {
                    downloadManager.setStopReason(str, intent.getIntExtra("stop_reason", 0));
                    break;
                } else {
                    Log.e("DownloadService", "Ignored SET_STOP_REASON: Missing stop_reason extra");
                    break;
                }
            case 8:
                if (str != null) {
                    downloadManager.removeDownload(str);
                    break;
                } else {
                    Log.e("DownloadService", "Ignored REMOVE_DOWNLOAD: Missing content_id extra");
                    break;
                }
            default:
                if (str2.length() != 0) {
                    str3 = "Ignored unrecognized action: ".concat(str2);
                } else {
                    str3 = new String("Ignored unrecognized action: ");
                }
                Log.e("DownloadService", str3);
                break;
        }
        int i4 = Util.a;
        this.i = false;
        if (downloadManager.isIdle()) {
            f();
        }
        return 1;
    }

    public void onTaskRemoved(Intent intent) {
        this.h = true;
    }

    public static Intent buildAddDownloadIntent(Context context, Class<? extends DownloadService> cls, DownloadRequest downloadRequest, int i2, boolean z) {
        return c(context, cls, "com.google.android.exoplayer.downloadService.action.ADD_DOWNLOAD", z).putExtra("download_request", downloadRequest).putExtra("stop_reason", i2);
    }

    public static void sendAddDownload(Context context, Class<? extends DownloadService> cls, DownloadRequest downloadRequest, int i2, boolean z) {
        e(context, buildAddDownloadIntent(context, cls, downloadRequest, i2, z), z);
    }
}
