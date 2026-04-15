package android.support.v4.media;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.media.browse.MediaBrowser;
import android.os.BadParcelableException;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.b;
import android.support.v4.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.collection.ArrayMap;
import androidx.core.app.BundleCompat;
import androidx.media.MediaBrowserCompatUtils;
import androidx.media.MediaBrowserProtocol;
import androidx.media.MediaBrowserServiceCompat;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public final class MediaBrowserCompat {
    public static final boolean b = Log.isLoggable("MediaBrowserCompat", 3);
    public final c a;

    public static class CustomActionResultReceiver extends ResultReceiver {
        public final void a(int i, Bundle bundle) {
        }
    }

    public static class ItemReceiver extends ResultReceiver {
        public final void a(int i, Bundle bundle) {
            if (bundle != null) {
                MediaSessionCompat.a(bundle);
                try {
                    bundle.isEmpty();
                } catch (BadParcelableException unused) {
                    bundle = null;
                }
            }
            if (i != 0 || bundle == null || !bundle.containsKey(MediaBrowserServiceCompat.KEY_MEDIA_ITEM)) {
                throw null;
            }
            Parcelable parcelable = bundle.getParcelable(MediaBrowserServiceCompat.KEY_MEDIA_ITEM);
            if (parcelable == null || (parcelable instanceof MediaItem)) {
                MediaItem mediaItem = (MediaItem) parcelable;
                throw null;
            }
            throw null;
        }
    }

    public static class SearchResultReceiver extends ResultReceiver {
        public final void a(int i, Bundle bundle) {
            if (bundle != null) {
                MediaSessionCompat.a(bundle);
                try {
                    bundle.isEmpty();
                } catch (BadParcelableException unused) {
                    bundle = null;
                }
            }
            if (i != 0 || bundle == null || !bundle.containsKey(MediaBrowserServiceCompat.KEY_SEARCH_RESULTS)) {
                throw null;
            }
            Parcelable[] parcelableArray = bundle.getParcelableArray(MediaBrowserServiceCompat.KEY_SEARCH_RESULTS);
            parcelableArray.getClass();
            ArrayList arrayList = new ArrayList();
            for (Parcelable parcelable : parcelableArray) {
                arrayList.add((MediaItem) parcelable);
            }
            throw null;
        }
    }

    public static class a extends Handler {
        public final WeakReference<f> a;
        public WeakReference<Messenger> b;

        public a(f fVar) {
            this.a = new WeakReference<>(fVar);
        }

        public final void handleMessage(@NonNull Message message) {
            WeakReference<Messenger> weakReference = this.b;
            if (weakReference != null && weakReference.get() != null) {
                WeakReference<f> weakReference2 = this.a;
                if (weakReference2.get() != null) {
                    Bundle data = message.getData();
                    MediaSessionCompat.a(data);
                    f fVar = weakReference2.get();
                    Messenger messenger = this.b.get();
                    try {
                        int i = message.what;
                        if (i == 1) {
                            MediaSessionCompat.a(data.getBundle(MediaBrowserProtocol.DATA_ROOT_HINTS));
                            fVar.c(messenger, data.getString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID), (MediaSessionCompat.Token) data.getParcelable(MediaBrowserProtocol.DATA_MEDIA_SESSION_TOKEN));
                        } else if (i == 2) {
                            fVar.b(messenger);
                        } else if (i != 3) {
                            message.toString();
                        } else {
                            Bundle bundle = data.getBundle(MediaBrowserProtocol.DATA_OPTIONS);
                            MediaSessionCompat.a(bundle);
                            MediaSessionCompat.a(data.getBundle(MediaBrowserProtocol.DATA_NOTIFY_CHILDREN_CHANGED_OPTIONS));
                            String string = data.getString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID);
                            data.getParcelableArrayList(MediaBrowserProtocol.DATA_MEDIA_ITEM_LIST);
                            fVar.a(messenger, string, bundle);
                        }
                    } catch (BadParcelableException unused) {
                        if (message.what == 1) {
                            fVar.b(messenger);
                        }
                    }
                }
            }
        }
    }

    public static class b {
        final MediaBrowser.ConnectionCallback mConnectionCallbackFwk = new a();
        C0005b mConnectionCallbackInternal;

        @RequiresApi(21)
        public class a extends MediaBrowser.ConnectionCallback {
            public a() {
            }

            public final void onConnected() {
                b bVar = b.this;
                C0005b bVar2 = bVar.mConnectionCallbackInternal;
                if (bVar2 != null) {
                    c cVar = (c) bVar2;
                    MediaBrowser mediaBrowser = cVar.b;
                    try {
                        Bundle extras = mediaBrowser.getExtras();
                        if (extras != null) {
                            extras.getInt(MediaBrowserProtocol.EXTRA_SERVICE_VERSION, 0);
                            IBinder binder = BundleCompat.getBinder(extras, MediaBrowserProtocol.EXTRA_MESSENGER_BINDER);
                            if (binder != null) {
                                cVar.f = new g(binder, cVar.c);
                                a aVar = cVar.d;
                                Messenger messenger = new Messenger(aVar);
                                cVar.g = messenger;
                                aVar.getClass();
                                aVar.b = new WeakReference<>(messenger);
                                try {
                                    g gVar = cVar.f;
                                    Context context = cVar.a;
                                    Messenger messenger2 = cVar.g;
                                    gVar.getClass();
                                    Bundle bundle = new Bundle();
                                    bundle.putString(MediaBrowserProtocol.DATA_PACKAGE_NAME, context.getPackageName());
                                    bundle.putInt(MediaBrowserProtocol.DATA_CALLING_PID, Process.myPid());
                                    bundle.putBundle(MediaBrowserProtocol.DATA_ROOT_HINTS, gVar.b);
                                    gVar.a(6, bundle, messenger2);
                                } catch (RemoteException unused) {
                                }
                            }
                            android.support.v4.media.session.b i = b.a.i(BundleCompat.getBinder(extras, MediaBrowserProtocol.EXTRA_SESSION_BINDER));
                            if (i != null) {
                                cVar.h = MediaSessionCompat.Token.a(mediaBrowser.getSessionToken(), i);
                            }
                        }
                    } catch (IllegalStateException unused2) {
                    }
                }
                bVar.onConnected();
            }

            public final void onConnectionFailed() {
                b bVar = b.this;
                C0005b bVar2 = bVar.mConnectionCallbackInternal;
                if (bVar2 != null) {
                    bVar2.getClass();
                }
                bVar.onConnectionFailed();
            }

            public final void onConnectionSuspended() {
                b bVar = b.this;
                C0005b bVar2 = bVar.mConnectionCallbackInternal;
                if (bVar2 != null) {
                    c cVar = (c) bVar2;
                    cVar.f = null;
                    cVar.g = null;
                    cVar.h = null;
                    a aVar = cVar.d;
                    aVar.getClass();
                    aVar.b = new WeakReference<>((Object) null);
                }
                bVar.onConnectionSuspended();
            }
        }

        /* renamed from: android.support.v4.media.MediaBrowserCompat$b$b  reason: collision with other inner class name */
        public interface C0005b {
        }

        public void onConnected() {
        }

        public void onConnectionFailed() {
        }

        public void onConnectionSuspended() {
        }

        public void setInternalConnectionCallback(C0005b bVar) {
            this.mConnectionCallbackInternal = bVar;
        }
    }

    @RequiresApi(21)
    public static class c implements f, b.C0005b {
        public final Context a;
        public final MediaBrowser b;
        public final Bundle c;
        public final a d = new a(this);
        public final ArrayMap<String, h> e = new ArrayMap<>();
        public g f;
        public Messenger g;
        public MediaSessionCompat.Token h;

        public c(Context context, ComponentName componentName, b bVar) {
            this.a = context;
            Bundle bundle = new Bundle();
            this.c = bundle;
            bundle.putInt(MediaBrowserProtocol.EXTRA_CLIENT_VERSION, 1);
            bundle.putInt(MediaBrowserProtocol.EXTRA_CALLING_PID, Process.myPid());
            bVar.setInternalConnectionCallback(this);
            this.b = new MediaBrowser(context, componentName, bVar.mConnectionCallbackFwk, bundle);
        }

        public final void a(Messenger messenger, String str, Bundle bundle) {
            if (this.g == messenger) {
                h hVar = this.e.get(str);
                if (hVar != null) {
                    hVar.a(bundle);
                } else if (MediaBrowserCompat.b) {
                    Log.d("MediaBrowserCompat", "onLoadChildren for id that isn't subscribed id=" + str);
                }
            }
        }

        public final void b(Messenger messenger) {
        }

        public final void c(Messenger messenger, String str, MediaSessionCompat.Token token) {
        }
    }

    @RequiresApi(23)
    public static class d extends c {
        public d(Context context, ComponentName componentName, b bVar) {
            super(context, componentName, bVar);
        }
    }

    @RequiresApi(26)
    public static class e extends d {
        public e(Context context, ComponentName componentName, b bVar) {
            super(context, componentName, bVar);
        }
    }

    public interface f {
        void a(Messenger messenger, String str, Bundle bundle);

        void b(Messenger messenger);

        void c(Messenger messenger, String str, MediaSessionCompat.Token token);
    }

    public static class g {
        public final Messenger a;
        public final Bundle b;

        public g(IBinder iBinder, Bundle bundle) {
            this.a = new Messenger(iBinder);
            this.b = bundle;
        }

        public final void a(int i, Bundle bundle, Messenger messenger) throws RemoteException {
            Message obtain = Message.obtain();
            obtain.what = i;
            obtain.arg1 = 1;
            obtain.setData(bundle);
            obtain.replyTo = messenger;
            this.a.send(obtain);
        }
    }

    public static class h {
        public final ArrayList a = new ArrayList();
        public final ArrayList b = new ArrayList();

        public final void a(Bundle bundle) {
            int i = 0;
            while (true) {
                ArrayList arrayList = this.b;
                if (i >= arrayList.size()) {
                    return;
                }
                if (MediaBrowserCompatUtils.areSameOptions((Bundle) arrayList.get(i), bundle)) {
                    i iVar = (i) this.a.get(i);
                    return;
                }
                i++;
            }
        }
    }

    public static abstract class i {

        @RequiresApi(21)
        public class a extends MediaBrowser.SubscriptionCallback {
            public a() {
            }

            public final void onChildrenLoaded(@NonNull String str, List<MediaBrowser.MediaItem> list) {
                i.this.getClass();
                MediaItem.a(list);
            }

            public final void onError(@NonNull String str) {
                i.this.getClass();
            }
        }

        @RequiresApi(26)
        public class b extends a {
            public b() {
                super();
            }

            public final void onChildrenLoaded(@NonNull String str, @NonNull List<MediaBrowser.MediaItem> list, @NonNull Bundle bundle) {
                MediaSessionCompat.a(bundle);
                MediaItem.a(list);
                i.this.getClass();
            }

            public final void onError(@NonNull String str, @NonNull Bundle bundle) {
                MediaSessionCompat.a(bundle);
                i.this.getClass();
            }
        }

        public i() {
            new Binder();
            if (Build.VERSION.SDK_INT >= 26) {
                new b();
            } else {
                new a();
            }
        }
    }

    public MediaBrowserCompat(Context context, ComponentName componentName, b bVar) {
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 26) {
            this.a = new e(context, componentName, bVar);
        } else if (i2 >= 23) {
            this.a = new d(context, componentName, bVar);
        } else {
            this.a = new c(context, componentName, bVar);
        }
    }

    @SuppressLint({"BanParcelableUsage"})
    public static class MediaItem implements Parcelable {
        public static final Parcelable.Creator<MediaItem> CREATOR = new a();
        public final int c;
        public final MediaDescriptionCompat f;

        public class a implements Parcelable.Creator<MediaItem> {
            public final Object createFromParcel(Parcel parcel) {
                return new MediaItem(parcel);
            }

            public final Object[] newArray(int i) {
                return new MediaItem[i];
            }
        }

        public MediaItem(@NonNull MediaDescriptionCompat mediaDescriptionCompat, int i) {
            if (mediaDescriptionCompat == null) {
                throw new IllegalArgumentException("description cannot be null");
            } else if (!TextUtils.isEmpty(mediaDescriptionCompat.c)) {
                this.c = i;
                this.f = mediaDescriptionCompat;
            } else {
                throw new IllegalArgumentException("description must have a non-empty media id");
            }
        }

        public static void a(List list) {
            MediaItem mediaItem;
            if (list != null) {
                ArrayList arrayList = new ArrayList(list.size());
                for (Object next : list) {
                    if (next != null) {
                        MediaBrowser.MediaItem mediaItem2 = (MediaBrowser.MediaItem) next;
                        mediaItem = new MediaItem(MediaDescriptionCompat.a(mediaItem2.getDescription()), mediaItem2.getFlags());
                    } else {
                        mediaItem = null;
                    }
                    arrayList.add(mediaItem);
                }
            }
        }

        public final int describeContents() {
            return 0;
        }

        @NonNull
        public final String toString() {
            return "MediaItem{mFlags=" + this.c + ", mDescription=" + this.f + '}';
        }

        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.c);
            this.f.writeToParcel(parcel, i);
        }

        public MediaItem(Parcel parcel) {
            this.c = parcel.readInt();
            this.f = MediaDescriptionCompat.CREATOR.createFromParcel(parcel);
        }
    }
}
