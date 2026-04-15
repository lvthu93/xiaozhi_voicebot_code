package info.dourok.voicebot.java.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

public class DeviceInfo implements Parcelable {
    public static final Parcelable.Creator<DeviceInfo> CREATOR = new a();
    public Application c;
    public Board f;
    public ChipInfo g;
    public String h;
    public Display i;
    public int j;
    public String k;
    public String l;
    public String m;
    public Ota n;
    public List<Partition> o;
    public int p;
    public String q;
    public int r;

    public static class Application implements Parcelable {
        public static final Parcelable.Creator<Application> CREATOR = new a();
        public final String c;
        public String f;
        public String g;
        public String h;
        public String i;
        public String j;

        public class a implements Parcelable.Creator<Application> {
            public final Object createFromParcel(Parcel parcel) {
                return new Application(parcel);
            }

            public final Object[] newArray(int i) {
                return new Application[i];
            }
        }

        public Application() {
        }

        public final int describeContents() {
            return 0;
        }

        public final void writeToParcel(Parcel parcel, int i2) {
            parcel.writeString(this.i);
            parcel.writeString(this.j);
            parcel.writeString(this.c);
            parcel.writeString(this.f);
            parcel.writeString(this.h);
            parcel.writeString(this.g);
        }

        public Application(Parcel parcel) {
            this.i = parcel.readString();
            this.j = parcel.readString();
            this.c = parcel.readString();
            this.f = parcel.readString();
            this.h = parcel.readString();
            this.g = parcel.readString();
        }
    }

    public static class ChipInfo implements Parcelable {
        public static final Parcelable.Creator<ChipInfo> CREATOR = new a();
        public int c;
        public int f;
        public int g;
        public int h;

        public class a implements Parcelable.Creator<ChipInfo> {
            public final Object createFromParcel(Parcel parcel) {
                return new ChipInfo(parcel);
            }

            public final Object[] newArray(int i) {
                return new ChipInfo[i];
            }
        }

        public ChipInfo() {
        }

        public final int describeContents() {
            return 0;
        }

        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.g);
            parcel.writeInt(this.c);
            parcel.writeInt(this.h);
            parcel.writeInt(this.f);
        }

        public ChipInfo(Parcel parcel) {
            this.g = parcel.readInt();
            this.c = parcel.readInt();
            this.h = parcel.readInt();
            this.f = parcel.readInt();
        }
    }

    public static class Display implements Parcelable {
        public static final Parcelable.Creator<Display> CREATOR = new a();
        public final String c;
        public int f;
        public boolean g;
        public final String h;
        public int i;

        public class a implements Parcelable.Creator<Display> {
            public final Object createFromParcel(Parcel parcel) {
                return new Display(parcel);
            }

            public final Object[] newArray(int i) {
                return new Display[i];
            }
        }

        public Display() {
        }

        public final int describeContents() {
            return 0;
        }

        public final void writeToParcel(Parcel parcel, int i2) {
            parcel.writeByte(this.g ? (byte) 1 : 0);
            parcel.writeInt(this.i);
            parcel.writeInt(this.f);
            parcel.writeString(this.h);
            parcel.writeString(this.c);
        }

        public Display(Parcel parcel) {
            this.g = parcel.readByte() != 0;
            this.i = parcel.readInt();
            this.f = parcel.readInt();
            this.h = parcel.readString();
            this.c = parcel.readString();
        }
    }

    public static class Ota implements Parcelable {
        public static final Parcelable.Creator<Ota> CREATOR = new a();
        public final String c;
        public String f;
        public final String g;
        public final String h;

        public class a implements Parcelable.Creator<Ota> {
            public final Object createFromParcel(Parcel parcel) {
                return new Ota(parcel);
            }

            public final Object[] newArray(int i) {
                return new Ota[i];
            }
        }

        public Ota() {
        }

        public final int describeContents() {
            return 0;
        }

        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.h);
            parcel.writeString(this.c);
            parcel.writeString(this.g);
            parcel.writeString(this.f);
        }

        public Ota(Parcel parcel) {
            this.h = parcel.readString();
            this.c = parcel.readString();
            this.g = parcel.readString();
            this.f = parcel.readString();
        }
    }

    public static class Partition implements Parcelable {
        public static final Parcelable.Creator<Partition> CREATOR = new a();
        public int c;
        public final boolean f;
        public String g;
        public int h;
        public int i;
        public final String j;
        public int k;
        public final String l;

        public class a implements Parcelable.Creator<Partition> {
            public final Object createFromParcel(Parcel parcel) {
                return new Partition(parcel);
            }

            public final Object[] newArray(int i) {
                return new Partition[i];
            }
        }

        public Partition() {
        }

        public final int describeContents() {
            return 0;
        }

        public final void writeToParcel(Parcel parcel, int i2) {
            parcel.writeString(this.g);
            parcel.writeString(this.l);
            parcel.writeString(this.j);
            parcel.writeInt(this.k);
            parcel.writeInt(this.i);
            parcel.writeInt(this.c);
            parcel.writeInt(this.h);
            parcel.writeByte(this.f ? (byte) 1 : 0);
        }

        public Partition(String str, int i2, int i3, int i4, int i5) {
            this.g = str;
            this.k = i2;
            this.i = i3;
            this.c = i4;
            this.h = i5;
            this.f = false;
        }

        public Partition(Parcel parcel) {
            this.g = parcel.readString();
            this.l = parcel.readString();
            this.j = parcel.readString();
            this.k = parcel.readInt();
            this.i = parcel.readInt();
            this.c = parcel.readInt();
            this.h = parcel.readInt();
            this.f = parcel.readByte() != 0;
        }
    }

    public class a implements Parcelable.Creator<DeviceInfo> {
        public final Object createFromParcel(Parcel parcel) {
            return new DeviceInfo(parcel);
        }

        public final Object[] newArray(int i) {
            return new DeviceInfo[i];
        }
    }

    public DeviceInfo() {
        this.o = new ArrayList();
    }

    public final int describeContents() {
        return 0;
    }

    public final void writeToParcel(Parcel parcel, int i2) {
        parcel.writeInt(this.r);
        parcel.writeString(this.k);
        parcel.writeInt(this.j);
        parcel.writeInt(this.p);
        parcel.writeString(this.m);
        parcel.writeString(this.l);
        parcel.writeString(this.q);
        parcel.writeString(this.h);
        parcel.writeParcelable(this.g, i2);
        parcel.writeParcelable(this.c, i2);
        parcel.writeTypedList(this.o);
        parcel.writeParcelable(this.n, i2);
        parcel.writeParcelable(this.i, i2);
        parcel.writeParcelable(this.f, i2);
    }

    public static class Board implements Parcelable {
        public static final Parcelable.Creator<Board> CREATOR = new a();
        public int c;
        public List<String> f;
        public String g;
        public String h;
        public final String i;
        public String j;
        public final String k;
        public String l;
        public String m;
        public int n;
        public String o;
        public String p;
        public String q;

        public class a implements Parcelable.Creator<Board> {
            public final Object createFromParcel(Parcel parcel) {
                return new Board(parcel);
            }

            public final Object[] newArray(int i) {
                return new Board[i];
            }
        }

        public Board() {
            this.f = new ArrayList();
        }

        public final int describeContents() {
            return 0;
        }

        public final void writeToParcel(Parcel parcel, int i2) {
            parcel.writeString(this.l);
            parcel.writeString(this.k);
            parcel.writeString(this.m);
            parcel.writeStringList(this.f);
            parcel.writeString(this.j);
            parcel.writeString(this.o);
            parcel.writeString(this.i);
        }

        public Board(Parcel parcel) {
            this.l = parcel.readString();
            this.k = parcel.readString();
            this.m = parcel.readString();
            this.f = parcel.createStringArrayList();
            this.j = parcel.readString();
            this.o = parcel.readString();
            this.i = parcel.readString();
        }
    }

    public DeviceInfo(Parcel parcel) {
        this.r = parcel.readInt();
        this.k = parcel.readString();
        this.j = parcel.readInt();
        this.p = parcel.readInt();
        this.m = parcel.readString();
        this.l = parcel.readString();
        this.q = parcel.readString();
        this.h = parcel.readString();
        this.g = (ChipInfo) parcel.readParcelable(ChipInfo.class.getClassLoader());
        this.c = (Application) parcel.readParcelable(Application.class.getClassLoader());
        this.o = parcel.createTypedArrayList(Partition.CREATOR);
        this.n = (Ota) parcel.readParcelable(Ota.class.getClassLoader());
        this.i = (Display) parcel.readParcelable(Display.class.getClassLoader());
        this.f = (Board) parcel.readParcelable(Board.class.getClassLoader());
    }
}
