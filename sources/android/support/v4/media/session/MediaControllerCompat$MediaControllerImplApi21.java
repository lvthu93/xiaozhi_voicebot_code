package android.support.v4.media.session;

import android.content.Context;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.b;
import android.support.v4.media.session.c;
import androidx.annotation.GuardedBy;
import androidx.annotation.RequiresApi;
import androidx.core.app.BundleCompat;
import androidx.versionedparcelable.ParcelUtils;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

@RequiresApi(21)
public final class MediaControllerCompat$MediaControllerImplApi21 {
    public final MediaController a;
    public final Object b = new Object();
    @GuardedBy("mLock")
    public final ArrayList c = new ArrayList();
    public final HashMap<c, a> d = new HashMap<>();
    public final MediaSessionCompat.Token e;

    public static class ExtraBinderRequestResultReceiver extends ResultReceiver {
        public final WeakReference<MediaControllerCompat$MediaControllerImplApi21> c;

        public ExtraBinderRequestResultReceiver(MediaControllerCompat$MediaControllerImplApi21 mediaControllerCompat$MediaControllerImplApi21) {
            super((Handler) null);
            this.c = new WeakReference<>(mediaControllerCompat$MediaControllerImplApi21);
        }

        public final void onReceiveResult(int i, Bundle bundle) {
            MediaControllerCompat$MediaControllerImplApi21 mediaControllerCompat$MediaControllerImplApi21 = this.c.get();
            if (mediaControllerCompat$MediaControllerImplApi21 != null && bundle != null) {
                synchronized (mediaControllerCompat$MediaControllerImplApi21.b) {
                    mediaControllerCompat$MediaControllerImplApi21.e.c(b.a.i(BundleCompat.getBinder(bundle, "android.support.v4.media.session.EXTRA_BINDER")));
                    mediaControllerCompat$MediaControllerImplApi21.e.d(ParcelUtils.getVersionedParcelable(bundle, "android.support.v4.media.session.SESSION_TOKEN2"));
                    mediaControllerCompat$MediaControllerImplApi21.a();
                }
            }
        }
    }

    public static class a extends c.b {
        public a(c cVar) {
            super(cVar);
        }

        public final void a() throws RemoteException {
            throw new AssertionError();
        }

        public final void b() throws RemoteException {
            throw new AssertionError();
        }

        public final void d() throws RemoteException {
            throw new AssertionError();
        }

        public final void e() throws RemoteException {
            throw new AssertionError();
        }

        public final void f() throws RemoteException {
            throw new AssertionError();
        }

        public final void h(ParcelableVolumeInfo parcelableVolumeInfo) throws RemoteException {
            throw new AssertionError();
        }
    }

    public MediaControllerCompat$MediaControllerImplApi21(Context context, MediaSessionCompat.Token token) {
        b bVar;
        this.e = token;
        MediaController mediaController = new MediaController(context, (MediaSession.Token) token.f);
        this.a = mediaController;
        synchronized (token.c) {
            bVar = token.g;
        }
        if (bVar == null) {
            mediaController.sendCommand("android.support.v4.media.session.command.GET_EXTRA_BINDER", (Bundle) null, new ExtraBinderRequestResultReceiver(this));
        }
    }

    @GuardedBy("mLock")
    public final void a() {
        b bVar;
        MediaSessionCompat.Token token = this.e;
        synchronized (token.c) {
            bVar = token.g;
        }
        if (bVar != null) {
            ArrayList arrayList = this.c;
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                c cVar = (c) it.next();
                a aVar = new a(cVar);
                this.d.put(cVar, aVar);
                cVar.a = aVar;
                try {
                    token.b().c(aVar);
                } catch (RemoteException unused) {
                }
            }
            arrayList.clear();
        }
    }
}
