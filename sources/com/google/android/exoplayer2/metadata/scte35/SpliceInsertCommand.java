package com.google.android.exoplayer2.metadata.scte35;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.MediaMetadata;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class SpliceInsertCommand extends SpliceCommand {
    public static final Parcelable.Creator<SpliceInsertCommand> CREATOR = new a();
    public final long c;
    public final boolean f;
    public final boolean g;
    public final boolean h;
    public final boolean i;
    public final long j;
    public final long k;
    public final List<ComponentSplice> l;
    public final boolean m;
    public final long n;
    public final int o;
    public final int p;
    public final int q;

    public static final class ComponentSplice {
        public final int a;
        public final long b;
        public final long c;

        public ComponentSplice(int i, long j, long j2) {
            this.a = i;
            this.b = j;
            this.c = j2;
        }

        public static ComponentSplice createFromParcel(Parcel parcel) {
            return new ComponentSplice(parcel.readInt(), parcel.readLong(), parcel.readLong());
        }

        public void writeToParcel(Parcel parcel) {
            parcel.writeInt(this.a);
            parcel.writeLong(this.b);
            parcel.writeLong(this.c);
        }
    }

    public class a implements Parcelable.Creator<SpliceInsertCommand> {
        public SpliceInsertCommand createFromParcel(Parcel parcel) {
            return new SpliceInsertCommand(parcel);
        }

        public SpliceInsertCommand[] newArray(int i) {
            return new SpliceInsertCommand[i];
        }
    }

    public SpliceInsertCommand(long j2, boolean z, boolean z2, boolean z3, boolean z4, long j3, long j4, List<ComponentSplice> list, boolean z5, long j5, int i2, int i3, int i4) {
        this.c = j2;
        this.f = z;
        this.g = z2;
        this.h = z3;
        this.i = z4;
        this.j = j3;
        this.k = j4;
        this.l = Collections.unmodifiableList(list);
        this.m = z5;
        this.n = j5;
        this.o = i2;
        this.p = i3;
        this.q = i4;
    }

    @Nullable
    public /* bridge */ /* synthetic */ byte[] getWrappedMetadataBytes() {
        return q6.a(this);
    }

    @Nullable
    public /* bridge */ /* synthetic */ Format getWrappedMetadataFormat() {
        return q6.b(this);
    }

    public /* bridge */ /* synthetic */ void populateMediaMetadata(MediaMetadata.Builder builder) {
        q6.c(this, builder);
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeLong(this.c);
        parcel.writeByte(this.f ? (byte) 1 : 0);
        parcel.writeByte(this.g ? (byte) 1 : 0);
        parcel.writeByte(this.h ? (byte) 1 : 0);
        parcel.writeByte(this.i ? (byte) 1 : 0);
        parcel.writeLong(this.j);
        parcel.writeLong(this.k);
        List<ComponentSplice> list = this.l;
        int size = list.size();
        parcel.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            list.get(i3).writeToParcel(parcel);
        }
        parcel.writeByte(this.m ? (byte) 1 : 0);
        parcel.writeLong(this.n);
        parcel.writeInt(this.o);
        parcel.writeInt(this.p);
        parcel.writeInt(this.q);
    }

    public SpliceInsertCommand(Parcel parcel) {
        this.c = parcel.readLong();
        boolean z = false;
        this.f = parcel.readByte() == 1;
        this.g = parcel.readByte() == 1;
        this.h = parcel.readByte() == 1;
        this.i = parcel.readByte() == 1;
        this.j = parcel.readLong();
        this.k = parcel.readLong();
        int readInt = parcel.readInt();
        ArrayList arrayList = new ArrayList(readInt);
        for (int i2 = 0; i2 < readInt; i2++) {
            arrayList.add(ComponentSplice.createFromParcel(parcel));
        }
        this.l = Collections.unmodifiableList(arrayList);
        this.m = parcel.readByte() == 1 ? true : z;
        this.n = parcel.readLong();
        this.o = parcel.readInt();
        this.p = parcel.readInt();
        this.q = parcel.readInt();
    }
}
