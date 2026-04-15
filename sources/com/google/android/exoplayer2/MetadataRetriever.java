package com.google.android.exoplayer2;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.HandlerWrapper;
import com.google.common.base.Preconditions;
import defpackage.a;

public final class MetadataRetriever {

    public static final class a {
        public final MediaSourceFactory a;
        public final HandlerThread b;
        public final HandlerWrapper c;
        public final ua<TrackGroupArray> d = new ua<>();

        /* renamed from: com.google.android.exoplayer2.MetadataRetriever$a$a  reason: collision with other inner class name */
        public final class C0010a implements Handler.Callback {
            public final C0011a c = new C0011a();
            public MediaSource f;
            public MediaPeriod g;

            /* renamed from: com.google.android.exoplayer2.MetadataRetriever$a$a$a  reason: collision with other inner class name */
            public final class C0011a implements MediaSource.MediaSourceCaller {
                public final C0012a c = new C0012a();
                public final DefaultAllocator f = new DefaultAllocator(true, 65536);
                public boolean g;

                /* renamed from: com.google.android.exoplayer2.MetadataRetriever$a$a$a$a  reason: collision with other inner class name */
                public final class C0012a implements MediaPeriod.Callback {
                    public C0012a() {
                    }

                    public void onPrepared(MediaPeriod mediaPeriod) {
                        C0011a aVar = C0011a.this;
                        ua<TrackGroupArray> uaVar = a.this.d;
                        Object trackGroups = mediaPeriod.getTrackGroups();
                        uaVar.getClass();
                        if (trackGroups == null) {
                            trackGroups = defpackage.a.k;
                        }
                        if (defpackage.a.j.b(uaVar, (Object) null, trackGroups)) {
                            defpackage.a.c(uaVar);
                        }
                        a.this.c.obtainMessage(3).sendToTarget();
                    }

                    public void onContinueLoadingRequested(MediaPeriod mediaPeriod) {
                        a.this.c.obtainMessage(2).sendToTarget();
                    }
                }

                public C0011a() {
                }

                public void onSourceInfoRefreshed(MediaSource mediaSource, Timeline timeline) {
                    if (!this.g) {
                        this.g = true;
                        MediaPeriod createPeriod = mediaSource.createPeriod(new MediaSource.MediaPeriodId(timeline.getUidOfPeriod(0)), this.f, 0);
                        C0010a.this.g = createPeriod;
                        createPeriod.prepare(this.c, 0);
                    }
                }
            }

            public C0010a() {
            }

            public boolean handleMessage(Message message) {
                int i = message.what;
                C0011a aVar = this.c;
                a aVar2 = a.this;
                if (i == 0) {
                    MediaSource createMediaSource = aVar2.a.createMediaSource((MediaItem) message.obj);
                    this.f = createMediaSource;
                    createMediaSource.prepareSource(aVar, (TransferListener) null);
                    aVar2.c.sendEmptyMessage(1);
                    return true;
                } else if (i == 1) {
                    try {
                        MediaPeriod mediaPeriod = this.g;
                        if (mediaPeriod == null) {
                            ((MediaSource) Assertions.checkNotNull(this.f)).maybeThrowSourceInfoRefreshError();
                        } else {
                            mediaPeriod.maybeThrowPrepareError();
                        }
                        aVar2.c.sendEmptyMessageDelayed(1, 100);
                    } catch (Exception e) {
                        ua<TrackGroupArray> uaVar = aVar2.d;
                        uaVar.getClass();
                        if (defpackage.a.j.b(uaVar, (Object) null, new a.c((Throwable) Preconditions.checkNotNull(e)))) {
                            defpackage.a.c(uaVar);
                        }
                        aVar2.c.obtainMessage(3).sendToTarget();
                    }
                    return true;
                } else if (i == 2) {
                    ((MediaPeriod) Assertions.checkNotNull(this.g)).continueLoading(0);
                    return true;
                } else if (i != 3) {
                    return false;
                } else {
                    if (this.g != null) {
                        ((MediaSource) Assertions.checkNotNull(this.f)).releasePeriod(this.g);
                    }
                    ((MediaSource) Assertions.checkNotNull(this.f)).releaseSource(aVar);
                    aVar2.c.removeCallbacksAndMessages((Object) null);
                    aVar2.b.quit();
                    return true;
                }
            }
        }

        public a(MediaSourceFactory mediaSourceFactory, Clock clock) {
            this.a = mediaSourceFactory;
            HandlerThread handlerThread = new HandlerThread("ExoPlayer:MetadataRetriever");
            this.b = handlerThread;
            handlerThread.start();
            this.c = clock.createHandler(handlerThread.getLooper(), new C0010a());
        }

        public i4<TrackGroupArray> retrieveMetadata(MediaItem mediaItem) {
            this.c.obtainMessage(0, mediaItem).sendToTarget();
            return this.d;
        }
    }

    public static i4<TrackGroupArray> retrieveMetadata(Context context, MediaItem mediaItem) {
        return new a(new DefaultMediaSourceFactory(context, (ExtractorsFactory) new DefaultExtractorsFactory().setMp4ExtractorFlags(6)), Clock.a).retrieveMetadata(mediaItem);
    }

    public static i4<TrackGroupArray> retrieveMetadata(MediaSourceFactory mediaSourceFactory, MediaItem mediaItem) {
        return new a(mediaSourceFactory, Clock.a).retrieveMetadata(mediaItem);
    }
}
