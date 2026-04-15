package defpackage;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.offline.Download;
import com.google.android.exoplayer2.offline.DownloadHelper;
import com.google.android.exoplayer2.offline.DownloadManager;
import com.google.android.exoplayer2.scheduler.Requirements;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ShuffleOrder;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.ui.PlayerNotificationManager;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ListenerSet;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/* renamed from: q1  reason: default package */
public final /* synthetic */ class q1 implements Handler.Callback {
    public final /* synthetic */ int c;
    public final /* synthetic */ Object f;

    public /* synthetic */ q1(int i, Object obj) {
        this.c = i;
        this.f = obj;
    }

    public final boolean handleMessage(Message message) {
        int i = this.c;
        Object obj = this.f;
        switch (i) {
            case 0:
                DownloadHelper.e eVar = (DownloadHelper.e) obj;
                if (eVar.n) {
                    return false;
                }
                int i2 = message.what;
                DownloadHelper downloadHelper = eVar.f;
                if (i2 == 0) {
                    Assertions.checkNotNull(downloadHelper.j);
                    Assertions.checkNotNull(downloadHelper.j.m);
                    Assertions.checkNotNull(downloadHelper.j.l);
                    int length = downloadHelper.j.m.length;
                    int length2 = downloadHelper.d.length;
                    int[] iArr = new int[2];
                    iArr[1] = length2;
                    iArr[0] = length;
                    Class<List> cls = List.class;
                    downloadHelper.m = (List[][]) Array.newInstance(cls, iArr);
                    int[] iArr2 = new int[2];
                    iArr2[1] = length2;
                    iArr2[0] = length;
                    downloadHelper.n = (List[][]) Array.newInstance(cls, iArr2);
                    for (int i3 = 0; i3 < length; i3++) {
                        for (int i4 = 0; i4 < length2; i4++) {
                            downloadHelper.m[i3][i4] = new ArrayList();
                            downloadHelper.n[i3][i4] = Collections.unmodifiableList(downloadHelper.m[i3][i4]);
                        }
                    }
                    downloadHelper.k = new TrackGroupArray[length];
                    downloadHelper.l = new MappingTrackSelector.MappedTrackInfo[length];
                    for (int i5 = 0; i5 < length; i5++) {
                        downloadHelper.k[i5] = downloadHelper.j.m[i5].getTrackGroups();
                        Object obj2 = downloadHelper.b(i5).d;
                        DefaultTrackSelector defaultTrackSelector = downloadHelper.c;
                        defaultTrackSelector.onSelectionActivated(obj2);
                        downloadHelper.l[i5] = (MappingTrackSelector.MappedTrackInfo) Assertions.checkNotNull(defaultTrackSelector.getCurrentMappedTrackInfo());
                    }
                    downloadHelper.h = true;
                    ((Handler) Assertions.checkNotNull(downloadHelper.f)).post(new qb(4, downloadHelper));
                } else if (i2 != 1) {
                    return false;
                } else {
                    eVar.release();
                    ((Handler) Assertions.checkNotNull(downloadHelper.f)).post(new h2(6, downloadHelper, (IOException) Util.castNonNull(message.obj)));
                }
                return true;
            case 1:
                DownloadManager downloadManager = (DownloadManager) obj;
                Requirements requirements = DownloadManager.q;
                downloadManager.getClass();
                int i6 = message.what;
                CopyOnWriteArraySet<DownloadManager.Listener> copyOnWriteArraySet = downloadManager.f;
                if (i6 == 0) {
                    downloadManager.i = true;
                    downloadManager.o = Collections.unmodifiableList((List) message.obj);
                    boolean e = downloadManager.e();
                    Iterator<DownloadManager.Listener> it = copyOnWriteArraySet.iterator();
                    while (it.hasNext()) {
                        it.next().onInitialized(downloadManager);
                    }
                    if (e) {
                        downloadManager.b();
                    }
                } else if (i6 == 1) {
                    int i7 = message.arg1;
                    int i8 = message.arg2;
                    downloadManager.g -= i7;
                    downloadManager.h = i8;
                    if (downloadManager.isIdle()) {
                        Iterator<DownloadManager.Listener> it2 = copyOnWriteArraySet.iterator();
                        while (it2.hasNext()) {
                            it2.next().onIdle(downloadManager);
                        }
                    }
                } else if (i6 == 2) {
                    DownloadManager.a aVar = (DownloadManager.a) message.obj;
                    downloadManager.o = Collections.unmodifiableList(aVar.c);
                    boolean e2 = downloadManager.e();
                    boolean z = aVar.b;
                    Download download = aVar.a;
                    if (z) {
                        Iterator<DownloadManager.Listener> it3 = copyOnWriteArraySet.iterator();
                        while (it3.hasNext()) {
                            it3.next().onDownloadRemoved(downloadManager, download);
                        }
                    } else {
                        Iterator<DownloadManager.Listener> it4 = copyOnWriteArraySet.iterator();
                        while (it4.hasNext()) {
                            it4.next().onDownloadChanged(downloadManager, download, aVar.d);
                        }
                    }
                    if (e2) {
                        downloadManager.b();
                    }
                } else {
                    throw new IllegalStateException();
                }
                return true;
            case 2:
                ConcatenatingMediaSource concatenatingMediaSource = (ConcatenatingMediaSource) obj;
                MediaItem mediaItem = ConcatenatingMediaSource.v;
                concatenatingMediaSource.getClass();
                int i9 = message.what;
                if (i9 != 0) {
                    ArrayList arrayList = concatenatingMediaSource.m;
                    if (i9 == 1) {
                        ConcatenatingMediaSource.e eVar2 = (ConcatenatingMediaSource.e) Util.castNonNull(message.obj);
                        int i10 = eVar2.a;
                        int intValue = ((Integer) eVar2.b).intValue();
                        if (i10 == 0 && intValue == concatenatingMediaSource.u.getLength()) {
                            concatenatingMediaSource.u = concatenatingMediaSource.u.cloneAndClear();
                        } else {
                            concatenatingMediaSource.u = concatenatingMediaSource.u.cloneAndRemove(i10, intValue);
                        }
                        for (int i11 = intValue - 1; i11 >= i10; i11--) {
                            ConcatenatingMediaSource.d dVar = (ConcatenatingMediaSource.d) arrayList.remove(i11);
                            concatenatingMediaSource.o.remove(dVar.b);
                            concatenatingMediaSource.k(i11, -1, -dVar.a.getTimeline().getWindowCount());
                            dVar.f = true;
                            if (dVar.c.isEmpty()) {
                                concatenatingMediaSource.p.remove(dVar);
                                concatenatingMediaSource.h(dVar);
                            }
                        }
                        concatenatingMediaSource.q(eVar2.c);
                    } else if (i9 == 2) {
                        ConcatenatingMediaSource.e eVar3 = (ConcatenatingMediaSource.e) Util.castNonNull(message.obj);
                        ShuffleOrder shuffleOrder = concatenatingMediaSource.u;
                        int i12 = eVar3.a;
                        ShuffleOrder cloneAndRemove = shuffleOrder.cloneAndRemove(i12, i12 + 1);
                        concatenatingMediaSource.u = cloneAndRemove;
                        Integer num = (Integer) eVar3.b;
                        concatenatingMediaSource.u = cloneAndRemove.cloneAndInsert(num.intValue(), 1);
                        int intValue2 = num.intValue();
                        int i13 = eVar3.a;
                        int min = Math.min(i13, intValue2);
                        int max = Math.max(i13, intValue2);
                        int i14 = ((ConcatenatingMediaSource.d) arrayList.get(min)).e;
                        arrayList.add(intValue2, (ConcatenatingMediaSource.d) arrayList.remove(i13));
                        while (min <= max) {
                            ConcatenatingMediaSource.d dVar2 = (ConcatenatingMediaSource.d) arrayList.get(min);
                            dVar2.d = min;
                            dVar2.e = i14;
                            i14 += dVar2.a.getTimeline().getWindowCount();
                            min++;
                        }
                        concatenatingMediaSource.q(eVar3.c);
                    } else if (i9 == 3) {
                        ConcatenatingMediaSource.e eVar4 = (ConcatenatingMediaSource.e) Util.castNonNull(message.obj);
                        concatenatingMediaSource.u = (ShuffleOrder) eVar4.b;
                        concatenatingMediaSource.q(eVar4.c);
                    } else if (i9 == 4) {
                        concatenatingMediaSource.s();
                    } else if (i9 == 5) {
                        concatenatingMediaSource.n((Set) Util.castNonNull(message.obj));
                    } else {
                        throw new IllegalStateException();
                    }
                } else {
                    ConcatenatingMediaSource.e eVar5 = (ConcatenatingMediaSource.e) Util.castNonNull(message.obj);
                    ShuffleOrder shuffleOrder2 = concatenatingMediaSource.u;
                    int i15 = eVar5.a;
                    Collection collection = (Collection) eVar5.b;
                    concatenatingMediaSource.u = shuffleOrder2.cloneAndInsert(i15, collection.size());
                    concatenatingMediaSource.i(eVar5.a, collection);
                    concatenatingMediaSource.q(eVar5.c);
                }
                return true;
            case 3:
                PlayerNotificationManager playerNotificationManager = (PlayerNotificationManager) obj;
                playerNotificationManager.getClass();
                int i16 = message.what;
                if (i16 == 0) {
                    Player player = playerNotificationManager.s;
                    if (player != null) {
                        playerNotificationManager.b(player, (Bitmap) null);
                    }
                } else if (i16 != 1) {
                    return false;
                } else {
                    Player player2 = playerNotificationManager.s;
                    if (player2 != null && playerNotificationManager.v && playerNotificationManager.w == message.arg1) {
                        playerNotificationManager.b(player2, (Bitmap) message.obj);
                    }
                }
                return true;
            default:
                ListenerSet listenerSet = (ListenerSet) obj;
                listenerSet.getClass();
                int i17 = message.what;
                if (i17 == 0) {
                    Iterator<ListenerSet.a<T>> it5 = listenerSet.d.iterator();
                    while (it5.hasNext()) {
                        it5.next().iterationFinished(listenerSet.c);
                        if (listenerSet.b.hasMessages(0)) {
                        }
                    }
                } else if (i17 == 1) {
                    listenerSet.sendEvent(message.arg1, (ListenerSet.Event) message.obj);
                    listenerSet.release();
                }
                return true;
        }
    }
}
