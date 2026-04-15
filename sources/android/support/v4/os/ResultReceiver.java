package android.support.v4.os;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.v4.os.a;
import androidx.annotation.RestrictTo;

@SuppressLint({"BanParcelableUsage"})
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public class ResultReceiver implements Parcelable {
    public static final Parcelable.Creator<ResultReceiver> CREATOR = new a();
    public a c;

    public class a implements Parcelable.Creator<ResultReceiver> {
        public final Object createFromParcel(Parcel parcel) {
            return new ResultReceiver(parcel);
        }

        public final Object[] newArray(int i) {
            return new ResultReceiver[i];
        }
    }

    public class b extends a.C0008a {
        public b() {
        }

        public final void g(int i, Bundle bundle) {
            ResultReceiver resultReceiver = ResultReceiver.this;
            resultReceiver.getClass();
            resultReceiver.a(i, bundle);
        }
    }

    public ResultReceiver(Parcel parcel) {
        a aVar;
        IBinder readStrongBinder = parcel.readStrongBinder();
        int i = a.C0008a.a;
        if (readStrongBinder == null) {
            aVar = null;
        } else {
            IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("android.support.v4.os.IResultReceiver");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof a)) {
                aVar = new a.C0008a.C0009a(readStrongBinder);
            } else {
                aVar = (a) queryLocalInterface;
            }
        }
        this.c = aVar;
    }

    public void a(int i, Bundle bundle) {
    }

    public final void b(int i, Bundle bundle) {
        a aVar = this.c;
        if (aVar != null) {
            try {
                aVar.g(i, bundle);
            } catch (RemoteException unused) {
            }
        }
    }

    public final int describeContents() {
        return 0;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        synchronized (this) {
            if (this.c == null) {
                this.c = new b();
            }
            parcel.writeStrongBinder(this.c.asBinder());
        }
    }
}
