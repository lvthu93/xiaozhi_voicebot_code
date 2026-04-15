package com.google.android.exoplayer2.analytics;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.analytics.PlaybackSessionManager;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Supplier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

public final class DefaultPlaybackSessionManager implements PlaybackSessionManager {
    public static final e1 h = new e1();
    public static final Random i = new Random();
    public final Timeline.Window a;
    public final Timeline.Period b;
    public final HashMap<String, a> c;
    public final Supplier<String> d;
    public PlaybackSessionManager.Listener e;
    public Timeline f;
    @Nullable
    public String g;

    public final class a {
        public final String a;
        public int b;
        public long c;
        public final MediaSource.MediaPeriodId d;
        public boolean e;
        public boolean f;

        public a(String str, int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
            long j;
            this.a = str;
            this.b = i;
            if (mediaPeriodId == null) {
                j = -1;
            } else {
                j = mediaPeriodId.d;
            }
            this.c = j;
            if (mediaPeriodId != null && mediaPeriodId.isAd()) {
                this.d = mediaPeriodId;
            }
        }

        public boolean belongsToSession(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
            if (mediaPeriodId != null) {
                long j = mediaPeriodId.d;
                MediaSource.MediaPeriodId mediaPeriodId2 = this.d;
                if (mediaPeriodId2 == null) {
                    if (mediaPeriodId.isAd() || j != this.c) {
                        return false;
                    }
                    return true;
                } else if (j == mediaPeriodId2.d && mediaPeriodId.b == mediaPeriodId2.b && mediaPeriodId.c == mediaPeriodId2.c) {
                    return true;
                } else {
                    return false;
                }
            } else if (i == this.b) {
                return true;
            } else {
                return false;
            }
        }

        public boolean isFinishedAtEventTime(AnalyticsListener.EventTime eventTime) {
            long j = this.c;
            if (j == -1) {
                return false;
            }
            MediaSource.MediaPeriodId mediaPeriodId = eventTime.d;
            if (mediaPeriodId == null) {
                if (this.b != eventTime.c) {
                    return true;
                }
                return false;
            } else if (mediaPeriodId.d > j) {
                return true;
            } else {
                MediaSource.MediaPeriodId mediaPeriodId2 = this.d;
                if (mediaPeriodId2 == null) {
                    return false;
                }
                Object obj = mediaPeriodId.a;
                Timeline timeline = eventTime.b;
                int indexOfPeriod = timeline.getIndexOfPeriod(obj);
                int indexOfPeriod2 = timeline.getIndexOfPeriod(mediaPeriodId2.a);
                MediaSource.MediaPeriodId mediaPeriodId3 = eventTime.d;
                if (mediaPeriodId3.d < mediaPeriodId2.d || indexOfPeriod < indexOfPeriod2) {
                    return false;
                }
                if (indexOfPeriod > indexOfPeriod2) {
                    return true;
                }
                if (mediaPeriodId3.isAd()) {
                    int i = mediaPeriodId3.b;
                    int i2 = mediaPeriodId3.c;
                    int i3 = mediaPeriodId2.b;
                    if (i > i3 || (i == i3 && i2 > mediaPeriodId2.c)) {
                        return true;
                    }
                    return false;
                }
                int i4 = mediaPeriodId3.e;
                if (i4 == -1 || i4 > mediaPeriodId2.b) {
                    return true;
                }
                return false;
            }
        }

        public void maybeSetWindowSequenceNumber(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
            if (this.c == -1 && i == this.b && mediaPeriodId != null) {
                this.c = mediaPeriodId.d;
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:3:0x000d, code lost:
            if (r0 < r7.getWindowCount()) goto L_0x0037;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean tryResolvingToNewTimeline(com.google.android.exoplayer2.Timeline r6, com.google.android.exoplayer2.Timeline r7) {
            /*
                r5 = this;
                int r0 = r5.b
                int r1 = r6.getWindowCount()
                r2 = -1
                if (r0 < r1) goto L_0x0012
                int r6 = r7.getWindowCount()
                if (r0 >= r6) goto L_0x0010
                goto L_0x0037
            L_0x0010:
                r0 = -1
                goto L_0x0037
            L_0x0012:
                com.google.android.exoplayer2.analytics.DefaultPlaybackSessionManager r1 = com.google.android.exoplayer2.analytics.DefaultPlaybackSessionManager.this
                com.google.android.exoplayer2.Timeline$Window r3 = r1.a
                r6.getWindow(r0, r3)
                com.google.android.exoplayer2.Timeline$Window r0 = r1.a
                int r3 = r0.s
            L_0x001d:
                int r4 = r0.t
                if (r3 > r4) goto L_0x0010
                java.lang.Object r4 = r6.getUidOfPeriod(r3)
                int r4 = r7.getIndexOfPeriod(r4)
                if (r4 == r2) goto L_0x0034
                com.google.android.exoplayer2.Timeline$Period r6 = r1.b
                com.google.android.exoplayer2.Timeline$Period r6 = r7.getPeriod(r4, r6)
                int r0 = r6.g
                goto L_0x0037
            L_0x0034:
                int r3 = r3 + 1
                goto L_0x001d
            L_0x0037:
                r5.b = r0
                r6 = 0
                if (r0 != r2) goto L_0x003d
                return r6
            L_0x003d:
                r0 = 1
                com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r1 = r5.d
                if (r1 != 0) goto L_0x0043
                return r0
            L_0x0043:
                java.lang.Object r1 = r1.a
                int r7 = r7.getIndexOfPeriod(r1)
                if (r7 == r2) goto L_0x004c
                r6 = 1
            L_0x004c:
                return r6
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.analytics.DefaultPlaybackSessionManager.a.tryResolvingToNewTimeline(com.google.android.exoplayer2.Timeline, com.google.android.exoplayer2.Timeline):boolean");
        }
    }

    public DefaultPlaybackSessionManager() {
        this(h);
    }

    public final a a(int i2, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
        int i3;
        HashMap<String, a> hashMap = this.c;
        a aVar = null;
        long j = Long.MAX_VALUE;
        for (a next : hashMap.values()) {
            next.maybeSetWindowSequenceNumber(i2, mediaPeriodId);
            if (next.belongsToSession(i2, mediaPeriodId)) {
                long j2 = next.c;
                if (j2 == -1 || j2 < j) {
                    aVar = next;
                    j = j2;
                } else if (!(i3 != 0 || ((a) Util.castNonNull(aVar)).d == null || next.d == null)) {
                    aVar = next;
                }
            }
        }
        if (aVar != null) {
            return aVar;
        }
        String str = this.d.get();
        a aVar2 = new a(str, i2, mediaPeriodId);
        hashMap.put(str, aVar2);
        return aVar2;
    }

    @RequiresNonNull({"listener"})
    public final void b(AnalyticsListener.EventTime eventTime) {
        MediaSource.MediaPeriodId mediaPeriodId;
        if (eventTime.b.isEmpty()) {
            this.g = null;
            return;
        }
        a aVar = this.c.get(this.g);
        int i2 = eventTime.c;
        MediaSource.MediaPeriodId mediaPeriodId2 = eventTime.d;
        String str = a(i2, mediaPeriodId2).a;
        this.g = str;
        updateSessions(eventTime);
        if (mediaPeriodId2 != null && mediaPeriodId2.isAd()) {
            if (aVar == null || aVar.c != mediaPeriodId2.d || (mediaPeriodId = aVar.d) == null || mediaPeriodId.b != mediaPeriodId2.b || mediaPeriodId.c != mediaPeriodId2.c) {
                this.e.onAdPlaybackStarted(eventTime, a(i2, new MediaSource.MediaPeriodId(mediaPeriodId2.a, mediaPeriodId2.d)).a, str);
            }
        }
    }

    public synchronized boolean belongsToSession(AnalyticsListener.EventTime eventTime, String str) {
        a aVar = this.c.get(str);
        if (aVar == null) {
            return false;
        }
        aVar.maybeSetWindowSequenceNumber(eventTime.c, eventTime.d);
        return aVar.belongsToSession(eventTime.c, eventTime.d);
    }

    public synchronized void finishAllSessions(AnalyticsListener.EventTime eventTime) {
        PlaybackSessionManager.Listener listener;
        this.g = null;
        Iterator<a> it = this.c.values().iterator();
        while (it.hasNext()) {
            a next = it.next();
            it.remove();
            if (next.e && (listener = this.e) != null) {
                listener.onSessionFinished(eventTime, next.a, false);
            }
        }
    }

    @Nullable
    public synchronized String getActiveSessionId() {
        return this.g;
    }

    public synchronized String getSessionForMediaPeriodId(Timeline timeline, MediaSource.MediaPeriodId mediaPeriodId) {
        return a(timeline.getPeriodByUid(mediaPeriodId.a, this.b).g, mediaPeriodId).a;
    }

    public void setListener(PlaybackSessionManager.Listener listener) {
        this.e = listener;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00f8, code lost:
        return;
     */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00d0  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00dd  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void updateSessions(com.google.android.exoplayer2.analytics.AnalyticsListener.EventTime r25) {
        /*
            r24 = this;
            r1 = r24
            r0 = r25
            monitor-enter(r24)
            com.google.android.exoplayer2.analytics.PlaybackSessionManager$Listener r2 = r1.e     // Catch:{ all -> 0x00f9 }
            com.google.android.exoplayer2.util.Assertions.checkNotNull(r2)     // Catch:{ all -> 0x00f9 }
            com.google.android.exoplayer2.Timeline r2 = r0.b     // Catch:{ all -> 0x00f9 }
            boolean r2 = r2.isEmpty()     // Catch:{ all -> 0x00f9 }
            if (r2 == 0) goto L_0x0014
            monitor-exit(r24)
            return
        L_0x0014:
            java.util.HashMap<java.lang.String, com.google.android.exoplayer2.analytics.DefaultPlaybackSessionManager$a> r2 = r1.c     // Catch:{ all -> 0x00f9 }
            java.lang.String r3 = r1.g     // Catch:{ all -> 0x00f9 }
            java.lang.Object r2 = r2.get(r3)     // Catch:{ all -> 0x00f9 }
            com.google.android.exoplayer2.analytics.DefaultPlaybackSessionManager$a r2 = (com.google.android.exoplayer2.analytics.DefaultPlaybackSessionManager.a) r2     // Catch:{ all -> 0x00f9 }
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r3 = r0.d     // Catch:{ all -> 0x00f9 }
            r4 = 1
            if (r3 == 0) goto L_0x0041
            if (r2 == 0) goto L_0x0041
            long r5 = r2.c     // Catch:{ all -> 0x00f9 }
            r7 = -1
            r9 = 0
            int r10 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r10 != 0) goto L_0x0036
            int r2 = r2.b     // Catch:{ all -> 0x00f9 }
            int r5 = r0.c     // Catch:{ all -> 0x00f9 }
            if (r2 == r5) goto L_0x003d
        L_0x0034:
            r9 = 1
            goto L_0x003d
        L_0x0036:
            long r7 = r3.d     // Catch:{ all -> 0x00f9 }
            int r2 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r2 >= 0) goto L_0x003d
            goto L_0x0034
        L_0x003d:
            if (r9 == 0) goto L_0x0041
            monitor-exit(r24)
            return
        L_0x0041:
            int r2 = r0.c     // Catch:{ all -> 0x00f9 }
            com.google.android.exoplayer2.analytics.DefaultPlaybackSessionManager$a r2 = r1.a(r2, r3)     // Catch:{ all -> 0x00f9 }
            java.lang.String r3 = r1.g     // Catch:{ all -> 0x00f9 }
            if (r3 != 0) goto L_0x004f
            java.lang.String r3 = r2.a     // Catch:{ all -> 0x00f9 }
            r1.g = r3     // Catch:{ all -> 0x00f9 }
        L_0x004f:
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r3 = r0.d     // Catch:{ all -> 0x00f9 }
            if (r3 == 0) goto L_0x00c8
            boolean r3 = r3.isAd()     // Catch:{ all -> 0x00f9 }
            if (r3 == 0) goto L_0x00c8
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r10 = new com.google.android.exoplayer2.source.MediaSource$MediaPeriodId     // Catch:{ all -> 0x00f9 }
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r3 = r0.d     // Catch:{ all -> 0x00f9 }
            java.lang.Object r5 = r3.a     // Catch:{ all -> 0x00f9 }
            long r6 = r3.d     // Catch:{ all -> 0x00f9 }
            int r3 = r3.b     // Catch:{ all -> 0x00f9 }
            r10.<init>(r5, r6, r3)     // Catch:{ all -> 0x00f9 }
            int r3 = r0.c     // Catch:{ all -> 0x00f9 }
            com.google.android.exoplayer2.analytics.DefaultPlaybackSessionManager$a r3 = r1.a(r3, r10)     // Catch:{ all -> 0x00f9 }
            boolean r5 = r3.e     // Catch:{ all -> 0x00f9 }
            if (r5 != 0) goto L_0x00c8
            r3.e = r4     // Catch:{ all -> 0x00f9 }
            com.google.android.exoplayer2.Timeline r5 = r0.b     // Catch:{ all -> 0x00f9 }
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r6 = r0.d     // Catch:{ all -> 0x00f9 }
            java.lang.Object r6 = r6.a     // Catch:{ all -> 0x00f9 }
            com.google.android.exoplayer2.Timeline$Period r7 = r1.b     // Catch:{ all -> 0x00f9 }
            r5.getPeriodByUid(r6, r7)     // Catch:{ all -> 0x00f9 }
            com.google.android.exoplayer2.Timeline$Period r5 = r1.b     // Catch:{ all -> 0x00f9 }
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r6 = r0.d     // Catch:{ all -> 0x00f9 }
            int r6 = r6.b     // Catch:{ all -> 0x00f9 }
            long r5 = r5.getAdGroupTimeUs(r6)     // Catch:{ all -> 0x00f9 }
            long r5 = com.google.android.exoplayer2.C.usToMs(r5)     // Catch:{ all -> 0x00f9 }
            com.google.android.exoplayer2.Timeline$Period r7 = r1.b     // Catch:{ all -> 0x00f9 }
            long r7 = r7.getPositionInWindowMs()     // Catch:{ all -> 0x00f9 }
            long r5 = r5 + r7
            r7 = 0
            long r11 = java.lang.Math.max(r7, r5)     // Catch:{ all -> 0x00f9 }
            com.google.android.exoplayer2.analytics.AnalyticsListener$EventTime r15 = new com.google.android.exoplayer2.analytics.AnalyticsListener$EventTime     // Catch:{ all -> 0x00f9 }
            long r6 = r0.a     // Catch:{ all -> 0x00f9 }
            com.google.android.exoplayer2.Timeline r8 = r0.b     // Catch:{ all -> 0x00f9 }
            int r9 = r0.c     // Catch:{ all -> 0x00f9 }
            com.google.android.exoplayer2.Timeline r13 = r0.f     // Catch:{ all -> 0x00f9 }
            int r14 = r0.g     // Catch:{ all -> 0x00f9 }
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r5 = r0.h     // Catch:{ all -> 0x00f9 }
            r16 = r5
            long r4 = r0.i     // Catch:{ all -> 0x00f9 }
            r20 = r2
            r21 = r3
            long r2 = r0.j     // Catch:{ all -> 0x00f9 }
            r22 = r4
            r4 = r16
            r16 = r22
            r5 = r15
            r0 = r15
            r15 = r4
            r18 = r2
            r5.<init>(r6, r8, r9, r10, r11, r13, r14, r15, r16, r18)     // Catch:{ all -> 0x00f9 }
            com.google.android.exoplayer2.analytics.PlaybackSessionManager$Listener r2 = r1.e     // Catch:{ all -> 0x00f9 }
            r3 = r21
            java.lang.String r3 = r3.a     // Catch:{ all -> 0x00f9 }
            r2.onSessionCreated(r0, r3)     // Catch:{ all -> 0x00f9 }
            goto L_0x00ca
        L_0x00c8:
            r20 = r2
        L_0x00ca:
            r0 = r20
            boolean r2 = r0.e     // Catch:{ all -> 0x00f9 }
            if (r2 != 0) goto L_0x00dd
            r2 = 1
            r0.e = r2     // Catch:{ all -> 0x00f9 }
            com.google.android.exoplayer2.analytics.PlaybackSessionManager$Listener r2 = r1.e     // Catch:{ all -> 0x00f9 }
            java.lang.String r3 = r0.a     // Catch:{ all -> 0x00f9 }
            r4 = r25
            r2.onSessionCreated(r4, r3)     // Catch:{ all -> 0x00f9 }
            goto L_0x00df
        L_0x00dd:
            r4 = r25
        L_0x00df:
            java.lang.String r2 = r0.a     // Catch:{ all -> 0x00f9 }
            java.lang.String r3 = r1.g     // Catch:{ all -> 0x00f9 }
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x00f9 }
            if (r2 == 0) goto L_0x00f7
            boolean r2 = r0.f     // Catch:{ all -> 0x00f9 }
            if (r2 != 0) goto L_0x00f7
            r2 = 1
            r0.f = r2     // Catch:{ all -> 0x00f9 }
            com.google.android.exoplayer2.analytics.PlaybackSessionManager$Listener r2 = r1.e     // Catch:{ all -> 0x00f9 }
            java.lang.String r0 = r0.a     // Catch:{ all -> 0x00f9 }
            r2.onSessionActive(r4, r0)     // Catch:{ all -> 0x00f9 }
        L_0x00f7:
            monitor-exit(r24)
            return
        L_0x00f9:
            r0 = move-exception
            monitor-exit(r24)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.analytics.DefaultPlaybackSessionManager.updateSessions(com.google.android.exoplayer2.analytics.AnalyticsListener$EventTime):void");
    }

    public synchronized void updateSessionsWithDiscontinuity(AnalyticsListener.EventTime eventTime, int i2) {
        boolean z;
        boolean z2;
        Assertions.checkNotNull(this.e);
        if (i2 == 0) {
            z = true;
        } else {
            z = false;
        }
        Iterator<a> it = this.c.values().iterator();
        while (it.hasNext()) {
            a next = it.next();
            if (next.isFinishedAtEventTime(eventTime)) {
                it.remove();
                if (next.e) {
                    boolean equals = next.a.equals(this.g);
                    if (!z || !equals || !next.f) {
                        z2 = false;
                    } else {
                        z2 = true;
                    }
                    if (equals) {
                        this.g = null;
                    }
                    this.e.onSessionFinished(eventTime, next.a, z2);
                }
            }
        }
        b(eventTime);
    }

    public synchronized void updateSessionsWithTimelineChange(AnalyticsListener.EventTime eventTime) {
        Assertions.checkNotNull(this.e);
        Timeline timeline = this.f;
        this.f = eventTime.b;
        Iterator<a> it = this.c.values().iterator();
        while (it.hasNext()) {
            a next = it.next();
            if (!next.tryResolvingToNewTimeline(timeline, this.f)) {
                it.remove();
                if (next.e) {
                    if (next.a.equals(this.g)) {
                        this.g = null;
                    }
                    this.e.onSessionFinished(eventTime, next.a, false);
                }
            }
        }
        b(eventTime);
    }

    public DefaultPlaybackSessionManager(Supplier<String> supplier) {
        this.d = supplier;
        this.a = new Timeline.Window();
        this.b = new Timeline.Period();
        this.c = new HashMap<>();
        this.f = Timeline.c;
    }
}
