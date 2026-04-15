package com.google.android.exoplayer2.metadata.scte35;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.MediaMetadata;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class SpliceScheduleCommand extends SpliceCommand {
    public static final Parcelable.Creator<SpliceScheduleCommand> CREATOR = new a();
    public final List<Event> c;

    public static final class ComponentSplice {
        public final int a;
        public final long b;

        public ComponentSplice(int i, long j) {
            this.a = i;
            this.b = j;
        }
    }

    public class a implements Parcelable.Creator<SpliceScheduleCommand> {
        public SpliceScheduleCommand createFromParcel(Parcel parcel) {
            return new SpliceScheduleCommand(parcel);
        }

        public SpliceScheduleCommand[] newArray(int i) {
            return new SpliceScheduleCommand[i];
        }
    }

    public SpliceScheduleCommand(ArrayList arrayList) {
        this.c = Collections.unmodifiableList(arrayList);
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

    public void writeToParcel(Parcel parcel, int i) {
        List<Event> list = this.c;
        int size = list.size();
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            Event event = list.get(i2);
            parcel.writeLong(event.a);
            parcel.writeByte(event.b ? (byte) 1 : 0);
            parcel.writeByte(event.c ? (byte) 1 : 0);
            parcel.writeByte(event.d ? (byte) 1 : 0);
            List<ComponentSplice> list2 = event.f;
            int size2 = list2.size();
            parcel.writeInt(size2);
            for (int i3 = 0; i3 < size2; i3++) {
                ComponentSplice componentSplice = list2.get(i3);
                parcel.writeInt(componentSplice.a);
                parcel.writeLong(componentSplice.b);
            }
            parcel.writeLong(event.e);
            parcel.writeByte(event.g ? (byte) 1 : 0);
            parcel.writeLong(event.h);
            parcel.writeInt(event.i);
            parcel.writeInt(event.j);
            parcel.writeInt(event.k);
        }
    }

    public SpliceScheduleCommand(Parcel parcel) {
        int readInt = parcel.readInt();
        ArrayList arrayList = new ArrayList(readInt);
        for (int i = 0; i < readInt; i++) {
            arrayList.add(new Event(parcel));
        }
        this.c = Collections.unmodifiableList(arrayList);
    }

    public static final class Event {
        public final long a;
        public final boolean b;
        public final boolean c;
        public final boolean d;
        public final long e;
        public final List<ComponentSplice> f;
        public final boolean g;
        public final long h;
        public final int i;
        public final int j;
        public final int k;

        public Event(long j2, boolean z, boolean z2, boolean z3, ArrayList arrayList, long j3, boolean z4, long j4, int i2, int i3, int i4) {
            this.a = j2;
            this.b = z;
            this.c = z2;
            this.d = z3;
            this.f = Collections.unmodifiableList(arrayList);
            this.e = j3;
            this.g = z4;
            this.h = j4;
            this.i = i2;
            this.j = i3;
            this.k = i4;
        }

        public Event(Parcel parcel) {
            this.a = parcel.readLong();
            boolean z = false;
            this.b = parcel.readByte() == 1;
            this.c = parcel.readByte() == 1;
            this.d = parcel.readByte() == 1;
            int readInt = parcel.readInt();
            ArrayList arrayList = new ArrayList(readInt);
            for (int i2 = 0; i2 < readInt; i2++) {
                arrayList.add(new ComponentSplice(parcel.readInt(), parcel.readLong()));
            }
            this.f = Collections.unmodifiableList(arrayList);
            this.e = parcel.readLong();
            this.g = parcel.readByte() == 1 ? true : z;
            this.h = parcel.readLong();
            this.i = parcel.readInt();
            this.j = parcel.readInt();
            this.k = parcel.readInt();
        }
    }
}
