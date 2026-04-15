package android.support.v4.media.session;

import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat$MediaControllerImplApi21;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v4.media.session.a;
import androidx.annotation.RequiresApi;
import androidx.media.AudioAttributesCompat;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public abstract class c implements IBinder.DeathRecipient {
    public MediaControllerCompat$MediaControllerImplApi21.a a;

    @RequiresApi(21)
    public static class a extends MediaController.Callback {
        public final WeakReference<c> a;

        public a(c cVar) {
            this.a = new WeakReference<>(cVar);
        }

        public final void onAudioInfoChanged(MediaController.PlaybackInfo playbackInfo) {
            if (this.a.get() != null) {
                playbackInfo.getPlaybackType();
                AudioAttributesCompat.wrap(playbackInfo.getAudioAttributes());
                playbackInfo.getVolumeControl();
                playbackInfo.getMaxVolume();
                playbackInfo.getCurrentVolume();
            }
        }

        public final void onExtrasChanged(Bundle bundle) {
            MediaSessionCompat.a(bundle);
            c cVar = this.a.get();
        }

        public final void onMetadataChanged(MediaMetadata mediaMetadata) {
            if (this.a.get() != null) {
                Parcelable.Creator<MediaMetadataCompat> creator = MediaMetadataCompat.CREATOR;
                if (mediaMetadata != null) {
                    Parcel obtain = Parcel.obtain();
                    mediaMetadata.writeToParcel(obtain, 0);
                    obtain.setDataPosition(0);
                    obtain.recycle();
                    MediaMetadataCompat.CREATOR.createFromParcel(obtain).getClass();
                }
            }
        }

        public final void onPlaybackStateChanged(PlaybackState playbackState) {
            ArrayList arrayList;
            PlaybackStateCompat.CustomAction customAction;
            c cVar = this.a.get();
            if (cVar != null && cVar.a == null && playbackState != null) {
                List<PlaybackState.CustomAction> customActions = playbackState.getCustomActions();
                if (customActions != null) {
                    ArrayList arrayList2 = new ArrayList(customActions.size());
                    for (PlaybackState.CustomAction next : customActions) {
                        if (next != null) {
                            PlaybackState.CustomAction customAction2 = next;
                            Bundle extras = customAction2.getExtras();
                            MediaSessionCompat.a(extras);
                            customAction = new PlaybackStateCompat.CustomAction(customAction2.getAction(), customAction2.getName(), customAction2.getIcon(), extras);
                        } else {
                            customAction = null;
                        }
                        arrayList2.add(customAction);
                    }
                    arrayList = arrayList2;
                } else {
                    arrayList = null;
                }
                Bundle extras2 = playbackState.getExtras();
                MediaSessionCompat.a(extras2);
                new PlaybackStateCompat(playbackState.getState(), playbackState.getPosition(), playbackState.getBufferedPosition(), playbackState.getPlaybackSpeed(), playbackState.getActions(), playbackState.getErrorMessage(), playbackState.getLastPositionUpdateTime(), arrayList, playbackState.getActiveQueueItemId(), extras2);
            }
        }

        public final void onQueueChanged(List<MediaSession.QueueItem> list) {
            MediaSessionCompat.QueueItem queueItem;
            if (this.a.get() != null && list != null) {
                ArrayList arrayList = new ArrayList();
                for (MediaSession.QueueItem next : list) {
                    if (next != null) {
                        MediaSession.QueueItem queueItem2 = next;
                        queueItem = new MediaSessionCompat.QueueItem(MediaDescriptionCompat.a(queueItem2.getDescription()), queueItem2.getQueueId());
                    } else {
                        queueItem = null;
                    }
                    arrayList.add(queueItem);
                }
            }
        }

        public final void onQueueTitleChanged(CharSequence charSequence) {
            c cVar = this.a.get();
        }

        public final void onSessionDestroyed() {
            c cVar = this.a.get();
        }

        public final void onSessionEvent(String str, Bundle bundle) {
            MediaSessionCompat.a(bundle);
            c cVar = this.a.get();
        }
    }

    public static class b extends a.C0006a {
        public final WeakReference<c> a;

        public b(c cVar) {
            this.a = new WeakReference<>(cVar);
        }
    }

    public c() {
        new a(this);
    }

    public final void binderDied() {
    }
}
