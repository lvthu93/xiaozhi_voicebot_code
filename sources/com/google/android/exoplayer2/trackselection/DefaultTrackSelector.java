package com.google.android.exoplayer2.trackselection;

import android.content.Context;
import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.ActivityChooserView;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionParameters;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Ordering;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;

public class DefaultTrackSelector extends MappingTrackSelector {
    public static final int[] f = new int[0];
    public static final Ordering<Integer> g = Ordering.from(new db(8));
    public static final Ordering<Integer> h = Ordering.from(new db(9));
    public final ExoTrackSelection.Factory d;
    public final AtomicReference<Parameters> e;

    public static final class AudioTrackScore implements Comparable<AudioTrackScore> {
        public final boolean c;
        @Nullable
        public final String f;
        public final Parameters g;
        public final boolean h;
        public final int i;
        public final int j;
        public final int k;
        public final int l;
        public final int m;
        public final boolean n;
        public final int o;
        public final int p;
        public final int q;
        public final int r;

        public AudioTrackScore(Format format, Parameters parameters, int i2) {
            int i3;
            int i4;
            boolean z;
            int i5;
            this.g = parameters;
            this.f = DefaultTrackSelector.f(format.g);
            int i6 = 0;
            this.h = DefaultTrackSelector.d(i2, false);
            int i7 = 0;
            while (true) {
                int size = parameters.c.size();
                i3 = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
                if (i7 >= size) {
                    i7 = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
                    i4 = 0;
                    break;
                }
                i4 = DefaultTrackSelector.b(format, parameters.c.get(i7), false);
                if (i4 > 0) {
                    break;
                }
                i7++;
            }
            this.j = i7;
            this.i = i4;
            this.k = Integer.bitCount(format.i & parameters.f);
            boolean z2 = true;
            if ((format.h & 1) != 0) {
                z = true;
            } else {
                z = false;
            }
            this.n = z;
            int i8 = format.ac;
            this.o = i8;
            this.p = format.ad;
            int i9 = format.l;
            this.q = i9;
            if ((i9 != -1 && i9 > parameters.aa) || (i8 != -1 && i8 > parameters.z)) {
                z2 = false;
            }
            this.c = z2;
            String[] systemLanguageCodes = Util.getSystemLanguageCodes();
            int i10 = 0;
            while (true) {
                if (i10 >= systemLanguageCodes.length) {
                    i10 = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
                    i5 = 0;
                    break;
                }
                i5 = DefaultTrackSelector.b(format, systemLanguageCodes[i10], false);
                if (i5 > 0) {
                    break;
                }
                i10++;
            }
            this.l = i10;
            this.m = i5;
            while (true) {
                ImmutableList<String> immutableList = parameters.af;
                if (i6 < immutableList.size()) {
                    String str = format.p;
                    if (str != null && str.equals(immutableList.get(i6))) {
                        i3 = i6;
                        break;
                    }
                    i6++;
                } else {
                    break;
                }
            }
            this.r = i3;
        }

        public int compareTo(AudioTrackScore audioTrackScore) {
            Ordering<Integer> ordering;
            Ordering<Integer> ordering2;
            boolean z = this.h;
            boolean z2 = this.c;
            if (!z2 || !z) {
                ordering = DefaultTrackSelector.g.reverse();
            } else {
                ordering = DefaultTrackSelector.g;
            }
            ComparisonChain compare = ComparisonChain.start().compareFalseFirst(z, audioTrackScore.h).compare(Integer.valueOf(this.j), Integer.valueOf(audioTrackScore.j), Ordering.natural().reverse()).compare(this.i, audioTrackScore.i).compare(this.k, audioTrackScore.k).compareFalseFirst(z2, audioTrackScore.c).compare(Integer.valueOf(this.r), Integer.valueOf(audioTrackScore.r), Ordering.natural().reverse());
            int i2 = this.q;
            Integer valueOf = Integer.valueOf(i2);
            int i3 = audioTrackScore.q;
            Integer valueOf2 = Integer.valueOf(i3);
            if (this.g.ag) {
                ordering2 = DefaultTrackSelector.g.reverse();
            } else {
                ordering2 = DefaultTrackSelector.h;
            }
            ComparisonChain compare2 = compare.compare(valueOf, valueOf2, ordering2).compareFalseFirst(this.n, audioTrackScore.n).compare(Integer.valueOf(this.l), Integer.valueOf(audioTrackScore.l), Ordering.natural().reverse()).compare(this.m, audioTrackScore.m).compare(Integer.valueOf(this.o), Integer.valueOf(audioTrackScore.o), ordering).compare(Integer.valueOf(this.p), Integer.valueOf(audioTrackScore.p), ordering);
            Integer valueOf3 = Integer.valueOf(i2);
            Integer valueOf4 = Integer.valueOf(i3);
            if (!Util.areEqual(this.f, audioTrackScore.f)) {
                ordering = DefaultTrackSelector.h;
            }
            return compare2.compare(valueOf3, valueOf4, ordering).result();
        }
    }

    public static final class OtherTrackScore implements Comparable<OtherTrackScore> {
        public final boolean c;
        public final boolean f;

        public OtherTrackScore(Format format, int i) {
            this.c = (format.h & 1) == 0 ? false : true;
            this.f = DefaultTrackSelector.d(i, false);
        }

        public int compareTo(OtherTrackScore otherTrackScore) {
            return ComparisonChain.start().compareFalseFirst(this.f, otherTrackScore.f).compareFalseFirst(this.c, otherTrackScore.c).result();
        }
    }

    public static final class Parameters extends TrackSelectionParameters {
        public static final Parcelable.Creator<Parameters> CREATOR = new a();
        public static final Parameters an = new ParametersBuilder().build();
        public final int aa;
        public final boolean ab;
        public final boolean ac;
        public final boolean ad;
        public final boolean ae;
        public final ImmutableList<String> af;
        public final boolean ag;
        public final boolean ah;
        public final boolean ai;
        public final boolean aj;
        public final boolean ak;
        public final SparseArray<Map<TrackGroupArray, SelectionOverride>> al;
        public final SparseBooleanArray am;
        public final int k;
        public final int l;
        public final int m;
        public final int n;
        public final int o;
        public final int p;
        public final int q;
        public final int r;
        public final boolean s;
        public final boolean t;
        public final boolean u;
        public final int v;
        public final int w;
        public final boolean x;
        public final ImmutableList<String> y;
        public final int z;

        public class a implements Parcelable.Creator<Parameters> {
            public Parameters createFromParcel(Parcel parcel) {
                return new Parameters(parcel);
            }

            public Parameters[] newArray(int i) {
                return new Parameters[i];
            }
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public Parameters(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, boolean z2, boolean z3, boolean z4, int i9, int i10, boolean z5, ImmutableList<String> immutableList, ImmutableList<String> immutableList2, int i11, int i12, int i13, boolean z6, boolean z7, boolean z8, boolean z9, ImmutableList<String> immutableList3, ImmutableList<String> immutableList4, int i14, boolean z10, int i15, boolean z11, boolean z12, boolean z13, boolean z14, boolean z15, SparseArray<Map<TrackGroupArray, SelectionOverride>> sparseArray, SparseBooleanArray sparseBooleanArray) {
            super(immutableList2, i11, immutableList4, i14, z10, i15);
            this.k = i;
            this.l = i2;
            this.m = i3;
            this.n = i4;
            this.o = i5;
            this.p = i6;
            this.q = i7;
            this.r = i8;
            this.s = z2;
            this.t = z3;
            this.u = z4;
            this.v = i9;
            this.w = i10;
            this.x = z5;
            this.y = immutableList;
            this.z = i12;
            this.aa = i13;
            this.ab = z6;
            this.ac = z7;
            this.ad = z8;
            this.ae = z9;
            this.af = immutableList3;
            this.ag = z11;
            this.ah = z12;
            this.ai = z13;
            this.aj = z14;
            this.ak = z15;
            this.al = sparseArray;
            this.am = sparseBooleanArray;
        }

        public static Parameters getDefaults(Context context) {
            return new ParametersBuilder(context).build();
        }

        public int describeContents() {
            return 0;
        }

        /* JADX WARNING: Removed duplicated region for block: B:105:? A[RETURN, SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean equals(@androidx.annotation.Nullable java.lang.Object r11) {
            /*
                r10 = this;
                r0 = 1
                if (r10 != r11) goto L_0x0004
                return r0
            L_0x0004:
                r1 = 0
                if (r11 == 0) goto L_0x0159
                java.lang.Class<com.google.android.exoplayer2.trackselection.DefaultTrackSelector$Parameters> r2 = com.google.android.exoplayer2.trackselection.DefaultTrackSelector.Parameters.class
                java.lang.Class r3 = r11.getClass()
                if (r2 == r3) goto L_0x0011
                goto L_0x0159
            L_0x0011:
                r2 = r11
                com.google.android.exoplayer2.trackselection.DefaultTrackSelector$Parameters r2 = (com.google.android.exoplayer2.trackselection.DefaultTrackSelector.Parameters) r2
                boolean r11 = super.equals(r11)
                if (r11 == 0) goto L_0x0157
                int r11 = r10.k
                int r3 = r2.k
                if (r11 != r3) goto L_0x0157
                int r11 = r10.l
                int r3 = r2.l
                if (r11 != r3) goto L_0x0157
                int r11 = r10.m
                int r3 = r2.m
                if (r11 != r3) goto L_0x0157
                int r11 = r10.n
                int r3 = r2.n
                if (r11 != r3) goto L_0x0157
                int r11 = r10.o
                int r3 = r2.o
                if (r11 != r3) goto L_0x0157
                int r11 = r10.p
                int r3 = r2.p
                if (r11 != r3) goto L_0x0157
                int r11 = r10.q
                int r3 = r2.q
                if (r11 != r3) goto L_0x0157
                int r11 = r10.r
                int r3 = r2.r
                if (r11 != r3) goto L_0x0157
                boolean r11 = r10.s
                boolean r3 = r2.s
                if (r11 != r3) goto L_0x0157
                boolean r11 = r10.t
                boolean r3 = r2.t
                if (r11 != r3) goto L_0x0157
                boolean r11 = r10.u
                boolean r3 = r2.u
                if (r11 != r3) goto L_0x0157
                boolean r11 = r10.x
                boolean r3 = r2.x
                if (r11 != r3) goto L_0x0157
                int r11 = r10.v
                int r3 = r2.v
                if (r11 != r3) goto L_0x0157
                int r11 = r10.w
                int r3 = r2.w
                if (r11 != r3) goto L_0x0157
                com.google.common.collect.ImmutableList<java.lang.String> r11 = r10.y
                com.google.common.collect.ImmutableList<java.lang.String> r3 = r2.y
                boolean r11 = r11.equals(r3)
                if (r11 == 0) goto L_0x0157
                int r11 = r10.z
                int r3 = r2.z
                if (r11 != r3) goto L_0x0157
                int r11 = r10.aa
                int r3 = r2.aa
                if (r11 != r3) goto L_0x0157
                boolean r11 = r10.ab
                boolean r3 = r2.ab
                if (r11 != r3) goto L_0x0157
                boolean r11 = r10.ac
                boolean r3 = r2.ac
                if (r11 != r3) goto L_0x0157
                boolean r11 = r10.ad
                boolean r3 = r2.ad
                if (r11 != r3) goto L_0x0157
                boolean r11 = r10.ae
                boolean r3 = r2.ae
                if (r11 != r3) goto L_0x0157
                com.google.common.collect.ImmutableList<java.lang.String> r11 = r10.af
                com.google.common.collect.ImmutableList<java.lang.String> r3 = r2.af
                boolean r11 = r11.equals(r3)
                if (r11 == 0) goto L_0x0157
                boolean r11 = r10.ag
                boolean r3 = r2.ag
                if (r11 != r3) goto L_0x0157
                boolean r11 = r10.ah
                boolean r3 = r2.ah
                if (r11 != r3) goto L_0x0157
                boolean r11 = r10.ai
                boolean r3 = r2.ai
                if (r11 != r3) goto L_0x0157
                boolean r11 = r10.aj
                boolean r3 = r2.aj
                if (r11 != r3) goto L_0x0157
                boolean r11 = r10.ak
                boolean r3 = r2.ak
                if (r11 != r3) goto L_0x0157
                android.util.SparseBooleanArray r11 = r10.am
                int r3 = r11.size()
                android.util.SparseBooleanArray r4 = r2.am
                int r5 = r4.size()
                if (r5 == r3) goto L_0x00d4
            L_0x00d2:
                r11 = 0
                goto L_0x00e6
            L_0x00d4:
                r5 = 0
            L_0x00d5:
                if (r5 >= r3) goto L_0x00e5
                int r6 = r11.keyAt(r5)
                int r6 = r4.indexOfKey(r6)
                if (r6 >= 0) goto L_0x00e2
                goto L_0x00d2
            L_0x00e2:
                int r5 = r5 + 1
                goto L_0x00d5
            L_0x00e5:
                r11 = 1
            L_0x00e6:
                if (r11 == 0) goto L_0x0157
                android.util.SparseArray<java.util.Map<com.google.android.exoplayer2.source.TrackGroupArray, com.google.android.exoplayer2.trackselection.DefaultTrackSelector$SelectionOverride>> r11 = r10.al
                int r3 = r11.size()
                android.util.SparseArray<java.util.Map<com.google.android.exoplayer2.source.TrackGroupArray, com.google.android.exoplayer2.trackselection.DefaultTrackSelector$SelectionOverride>> r2 = r2.al
                int r4 = r2.size()
                if (r4 == r3) goto L_0x00f8
            L_0x00f6:
                r11 = 0
                goto L_0x0154
            L_0x00f8:
                r4 = 0
            L_0x00f9:
                if (r4 >= r3) goto L_0x0153
                int r5 = r11.keyAt(r4)
                int r5 = r2.indexOfKey(r5)
                if (r5 < 0) goto L_0x00f6
                java.lang.Object r6 = r11.valueAt(r4)
                java.util.Map r6 = (java.util.Map) r6
                java.lang.Object r5 = r2.valueAt(r5)
                java.util.Map r5 = (java.util.Map) r5
                int r7 = r6.size()
                int r8 = r5.size()
                if (r8 == r7) goto L_0x011c
                goto L_0x014a
            L_0x011c:
                java.util.Set r6 = r6.entrySet()
                java.util.Iterator r6 = r6.iterator()
            L_0x0124:
                boolean r7 = r6.hasNext()
                if (r7 == 0) goto L_0x014c
                java.lang.Object r7 = r6.next()
                java.util.Map$Entry r7 = (java.util.Map.Entry) r7
                java.lang.Object r8 = r7.getKey()
                com.google.android.exoplayer2.source.TrackGroupArray r8 = (com.google.android.exoplayer2.source.TrackGroupArray) r8
                boolean r9 = r5.containsKey(r8)
                if (r9 == 0) goto L_0x014a
                java.lang.Object r7 = r7.getValue()
                java.lang.Object r8 = r5.get(r8)
                boolean r7 = com.google.android.exoplayer2.util.Util.areEqual(r7, r8)
                if (r7 != 0) goto L_0x0124
            L_0x014a:
                r5 = 0
                goto L_0x014d
            L_0x014c:
                r5 = 1
            L_0x014d:
                if (r5 != 0) goto L_0x0150
                goto L_0x00f6
            L_0x0150:
                int r4 = r4 + 1
                goto L_0x00f9
            L_0x0153:
                r11 = 1
            L_0x0154:
                if (r11 == 0) goto L_0x0157
                goto L_0x0158
            L_0x0157:
                r0 = 0
            L_0x0158:
                return r0
            L_0x0159:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.trackselection.DefaultTrackSelector.Parameters.equals(java.lang.Object):boolean");
        }

        public final boolean getRendererDisabled(int i) {
            return this.am.get(i);
        }

        @Nullable
        public final SelectionOverride getSelectionOverride(int i, TrackGroupArray trackGroupArray) {
            Map map = this.al.get(i);
            if (map != null) {
                return (SelectionOverride) map.get(trackGroupArray);
            }
            return null;
        }

        public final boolean hasSelectionOverride(int i, TrackGroupArray trackGroupArray) {
            Map map = this.al.get(i);
            if (map == null || !map.containsKey(trackGroupArray)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            int hashCode = this.y.hashCode();
            return ((((((((((this.af.hashCode() + ((((((((((((((hashCode + (((((((((((((((((((((((((((((super.hashCode() * 31) + this.k) * 31) + this.l) * 31) + this.m) * 31) + this.n) * 31) + this.o) * 31) + this.p) * 31) + this.q) * 31) + this.r) * 31) + (this.s ? 1 : 0)) * 31) + (this.t ? 1 : 0)) * 31) + (this.u ? 1 : 0)) * 31) + (this.x ? 1 : 0)) * 31) + this.v) * 31) + this.w) * 31)) * 31) + this.z) * 31) + this.aa) * 31) + (this.ab ? 1 : 0)) * 31) + (this.ac ? 1 : 0)) * 31) + (this.ad ? 1 : 0)) * 31) + (this.ae ? 1 : 0)) * 31)) * 31) + (this.ag ? 1 : 0)) * 31) + (this.ah ? 1 : 0)) * 31) + (this.ai ? 1 : 0)) * 31) + (this.aj ? 1 : 0)) * 31) + (this.ak ? 1 : 0);
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.k);
            parcel.writeInt(this.l);
            parcel.writeInt(this.m);
            parcel.writeInt(this.n);
            parcel.writeInt(this.o);
            parcel.writeInt(this.p);
            parcel.writeInt(this.q);
            parcel.writeInt(this.r);
            Util.writeBoolean(parcel, this.s);
            Util.writeBoolean(parcel, this.t);
            Util.writeBoolean(parcel, this.u);
            parcel.writeInt(this.v);
            parcel.writeInt(this.w);
            Util.writeBoolean(parcel, this.x);
            parcel.writeList(this.y);
            parcel.writeInt(this.z);
            parcel.writeInt(this.aa);
            Util.writeBoolean(parcel, this.ab);
            Util.writeBoolean(parcel, this.ac);
            Util.writeBoolean(parcel, this.ad);
            Util.writeBoolean(parcel, this.ae);
            parcel.writeList(this.af);
            Util.writeBoolean(parcel, this.ag);
            Util.writeBoolean(parcel, this.ah);
            Util.writeBoolean(parcel, this.ai);
            Util.writeBoolean(parcel, this.aj);
            Util.writeBoolean(parcel, this.ak);
            SparseArray<Map<TrackGroupArray, SelectionOverride>> sparseArray = this.al;
            int size = sparseArray.size();
            parcel.writeInt(size);
            for (int i2 = 0; i2 < size; i2++) {
                int keyAt = sparseArray.keyAt(i2);
                Map valueAt = sparseArray.valueAt(i2);
                int size2 = valueAt.size();
                parcel.writeInt(keyAt);
                parcel.writeInt(size2);
                for (Map.Entry entry : valueAt.entrySet()) {
                    parcel.writeParcelable((Parcelable) entry.getKey(), 0);
                    parcel.writeParcelable((Parcelable) entry.getValue(), 0);
                }
            }
            parcel.writeSparseBooleanArray(this.am);
        }

        public ParametersBuilder buildUpon() {
            return new ParametersBuilder(this);
        }

        public Parameters(Parcel parcel) {
            super(parcel);
            this.k = parcel.readInt();
            this.l = parcel.readInt();
            this.m = parcel.readInt();
            this.n = parcel.readInt();
            this.o = parcel.readInt();
            this.p = parcel.readInt();
            this.q = parcel.readInt();
            this.r = parcel.readInt();
            this.s = Util.readBoolean(parcel);
            this.t = Util.readBoolean(parcel);
            this.u = Util.readBoolean(parcel);
            this.v = parcel.readInt();
            this.w = parcel.readInt();
            this.x = Util.readBoolean(parcel);
            ArrayList arrayList = new ArrayList();
            parcel.readList(arrayList, (ClassLoader) null);
            this.y = ImmutableList.copyOf(arrayList);
            this.z = parcel.readInt();
            this.aa = parcel.readInt();
            this.ab = Util.readBoolean(parcel);
            this.ac = Util.readBoolean(parcel);
            this.ad = Util.readBoolean(parcel);
            this.ae = Util.readBoolean(parcel);
            ArrayList arrayList2 = new ArrayList();
            parcel.readList(arrayList2, (ClassLoader) null);
            this.af = ImmutableList.copyOf(arrayList2);
            this.ag = Util.readBoolean(parcel);
            this.ah = Util.readBoolean(parcel);
            this.ai = Util.readBoolean(parcel);
            this.aj = Util.readBoolean(parcel);
            this.ak = Util.readBoolean(parcel);
            int readInt = parcel.readInt();
            SparseArray<Map<TrackGroupArray, SelectionOverride>> sparseArray = new SparseArray<>(readInt);
            for (int i = 0; i < readInt; i++) {
                int readInt2 = parcel.readInt();
                int readInt3 = parcel.readInt();
                HashMap hashMap = new HashMap(readInt3);
                for (int i2 = 0; i2 < readInt3; i2++) {
                    hashMap.put((TrackGroupArray) Assertions.checkNotNull((TrackGroupArray) parcel.readParcelable(TrackGroupArray.class.getClassLoader())), (SelectionOverride) parcel.readParcelable(SelectionOverride.class.getClassLoader()));
                }
                sparseArray.put(readInt2, hashMap);
            }
            this.al = sparseArray;
            this.am = (SparseBooleanArray) Util.castNonNull(parcel.readSparseBooleanArray());
        }
    }

    public static final class ParametersBuilder extends TrackSelectionParameters.Builder {
        public boolean aa;
        public ImmutableList<String> ab;
        public boolean ac;
        public boolean ad;
        public boolean ae;
        public boolean af;
        public boolean ag;
        public final SparseArray<Map<TrackGroupArray, SelectionOverride>> ah;
        public final SparseBooleanArray ai;
        public int g;
        public int h;
        public int i;
        public int j;
        public int k;
        public int l;
        public int m;
        public int n;
        public boolean o;
        public boolean p;
        public boolean q;
        public int r;
        public int s;
        public boolean t;
        public ImmutableList<String> u;
        public int v;
        public int w;
        public boolean x;
        public boolean y;
        public boolean z;

        @Deprecated
        public ParametersBuilder() {
            a();
            this.ah = new SparseArray<>();
            this.ai = new SparseBooleanArray();
        }

        @EnsuresNonNull({"preferredVideoMimeTypes", "preferredAudioMimeTypes"})
        public final void a() {
            this.g = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            this.h = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            this.i = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            this.j = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            this.o = true;
            this.p = false;
            this.q = true;
            this.r = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            this.s = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            this.t = true;
            this.u = ImmutableList.of();
            this.v = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            this.w = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            this.x = true;
            this.y = false;
            this.z = false;
            this.aa = false;
            this.ab = ImmutableList.of();
            this.ac = false;
            this.ad = false;
            this.ae = true;
            this.af = false;
            this.ag = true;
        }

        public final ParametersBuilder clearSelectionOverride(int i2, TrackGroupArray trackGroupArray) {
            SparseArray<Map<TrackGroupArray, SelectionOverride>> sparseArray = this.ah;
            Map map = sparseArray.get(i2);
            if (map != null && map.containsKey(trackGroupArray)) {
                map.remove(trackGroupArray);
                if (map.isEmpty()) {
                    sparseArray.remove(i2);
                }
            }
            return this;
        }

        public final ParametersBuilder clearSelectionOverrides(int i2) {
            SparseArray<Map<TrackGroupArray, SelectionOverride>> sparseArray = this.ah;
            Map map = sparseArray.get(i2);
            if (map != null && !map.isEmpty()) {
                sparseArray.remove(i2);
            }
            return this;
        }

        public ParametersBuilder clearVideoSizeConstraints() {
            return setMaxVideoSize(ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
        }

        public ParametersBuilder clearViewportSizeConstraints() {
            return setViewportSize(ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, true);
        }

        public ParametersBuilder setAllowAudioMixedChannelCountAdaptiveness(boolean z2) {
            this.aa = z2;
            return this;
        }

        public ParametersBuilder setAllowAudioMixedMimeTypeAdaptiveness(boolean z2) {
            this.y = z2;
            return this;
        }

        public ParametersBuilder setAllowAudioMixedSampleRateAdaptiveness(boolean z2) {
            this.z = z2;
            return this;
        }

        public ParametersBuilder setAllowMultipleAdaptiveSelections(boolean z2) {
            this.ag = z2;
            return this;
        }

        public ParametersBuilder setAllowVideoMixedMimeTypeAdaptiveness(boolean z2) {
            this.p = z2;
            return this;
        }

        public ParametersBuilder setAllowVideoNonSeamlessAdaptiveness(boolean z2) {
            this.q = z2;
            return this;
        }

        public ParametersBuilder setExceedAudioConstraintsIfNecessary(boolean z2) {
            this.x = z2;
            return this;
        }

        public ParametersBuilder setExceedRendererCapabilitiesIfNecessary(boolean z2) {
            this.ae = z2;
            return this;
        }

        public ParametersBuilder setExceedVideoConstraintsIfNecessary(boolean z2) {
            this.o = z2;
            return this;
        }

        public ParametersBuilder setForceHighestSupportedBitrate(boolean z2) {
            this.ad = z2;
            return this;
        }

        public ParametersBuilder setForceLowestBitrate(boolean z2) {
            this.ac = z2;
            return this;
        }

        public ParametersBuilder setMaxAudioBitrate(int i2) {
            this.w = i2;
            return this;
        }

        public ParametersBuilder setMaxAudioChannelCount(int i2) {
            this.v = i2;
            return this;
        }

        public ParametersBuilder setMaxVideoBitrate(int i2) {
            this.j = i2;
            return this;
        }

        public ParametersBuilder setMaxVideoFrameRate(int i2) {
            this.i = i2;
            return this;
        }

        public ParametersBuilder setMaxVideoSize(int i2, int i3) {
            this.g = i2;
            this.h = i3;
            return this;
        }

        public ParametersBuilder setMaxVideoSizeSd() {
            return setMaxVideoSize(1279, 719);
        }

        public ParametersBuilder setMinVideoBitrate(int i2) {
            this.n = i2;
            return this;
        }

        public ParametersBuilder setMinVideoFrameRate(int i2) {
            this.m = i2;
            return this;
        }

        public ParametersBuilder setMinVideoSize(int i2, int i3) {
            this.k = i2;
            this.l = i3;
            return this;
        }

        public ParametersBuilder setPreferredAudioMimeType(@Nullable String str) {
            if (str == null) {
                return setPreferredAudioMimeTypes(new String[0]);
            }
            return setPreferredAudioMimeTypes(str);
        }

        public ParametersBuilder setPreferredAudioMimeTypes(String... strArr) {
            this.ab = ImmutableList.copyOf((E[]) strArr);
            return this;
        }

        public ParametersBuilder setPreferredVideoMimeType(@Nullable String str) {
            if (str == null) {
                return setPreferredVideoMimeTypes(new String[0]);
            }
            return setPreferredVideoMimeTypes(str);
        }

        public ParametersBuilder setPreferredVideoMimeTypes(String... strArr) {
            this.u = ImmutableList.copyOf((E[]) strArr);
            return this;
        }

        public final ParametersBuilder setRendererDisabled(int i2, boolean z2) {
            SparseBooleanArray sparseBooleanArray = this.ai;
            if (sparseBooleanArray.get(i2) == z2) {
                return this;
            }
            if (z2) {
                sparseBooleanArray.put(i2, true);
            } else {
                sparseBooleanArray.delete(i2);
            }
            return this;
        }

        public final ParametersBuilder setSelectionOverride(int i2, TrackGroupArray trackGroupArray, @Nullable SelectionOverride selectionOverride) {
            SparseArray<Map<TrackGroupArray, SelectionOverride>> sparseArray = this.ah;
            Map map = sparseArray.get(i2);
            if (map == null) {
                map = new HashMap();
                sparseArray.put(i2, map);
            }
            if (map.containsKey(trackGroupArray) && Util.areEqual(map.get(trackGroupArray), selectionOverride)) {
                return this;
            }
            map.put(trackGroupArray, selectionOverride);
            return this;
        }

        public ParametersBuilder setTunnelingEnabled(boolean z2) {
            this.af = z2;
            return this;
        }

        public ParametersBuilder setViewportSize(int i2, int i3, boolean z2) {
            this.r = i2;
            this.s = i3;
            this.t = z2;
            return this;
        }

        public ParametersBuilder setViewportSizeToPhysicalDisplaySize(Context context, boolean z2) {
            Point currentDisplayModeSize = Util.getCurrentDisplayModeSize(context);
            return setViewportSize(currentDisplayModeSize.x, currentDisplayModeSize.y, z2);
        }

        public Parameters build() {
            return new Parameters(this.g, this.h, this.i, this.j, this.k, this.l, this.m, this.n, this.o, this.p, this.q, this.r, this.s, this.t, this.u, this.a, this.b, this.v, this.w, this.x, this.y, this.z, this.aa, this.ab, this.c, this.d, this.e, this.f, this.ac, this.ad, this.ae, this.af, this.ag, this.ah, this.ai);
        }

        public ParametersBuilder setDisabledTextTrackSelectionFlags(int i2) {
            super.setDisabledTextTrackSelectionFlags(i2);
            return this;
        }

        public ParametersBuilder setPreferredAudioLanguage(@Nullable String str) {
            super.setPreferredAudioLanguage(str);
            return this;
        }

        public ParametersBuilder setPreferredAudioLanguages(String... strArr) {
            super.setPreferredAudioLanguages(strArr);
            return this;
        }

        public ParametersBuilder setPreferredAudioRoleFlags(int i2) {
            super.setPreferredAudioRoleFlags(i2);
            return this;
        }

        public ParametersBuilder setPreferredTextLanguage(@Nullable String str) {
            super.setPreferredTextLanguage(str);
            return this;
        }

        public ParametersBuilder setPreferredTextLanguageAndRoleFlagsToCaptioningManagerSettings(Context context) {
            super.setPreferredTextLanguageAndRoleFlagsToCaptioningManagerSettings(context);
            return this;
        }

        public ParametersBuilder setPreferredTextLanguages(String... strArr) {
            super.setPreferredTextLanguages(strArr);
            return this;
        }

        public ParametersBuilder setPreferredTextRoleFlags(int i2) {
            super.setPreferredTextRoleFlags(i2);
            return this;
        }

        public ParametersBuilder setSelectUndeterminedTextLanguage(boolean z2) {
            super.setSelectUndeterminedTextLanguage(z2);
            return this;
        }

        public final ParametersBuilder clearSelectionOverrides() {
            SparseArray<Map<TrackGroupArray, SelectionOverride>> sparseArray = this.ah;
            if (sparseArray.size() == 0) {
                return this;
            }
            sparseArray.clear();
            return this;
        }

        public ParametersBuilder(Context context) {
            super(context);
            a();
            this.ah = new SparseArray<>();
            this.ai = new SparseBooleanArray();
            setViewportSizeToPhysicalDisplaySize(context, true);
        }

        public ParametersBuilder(Parameters parameters) {
            super((TrackSelectionParameters) parameters);
            this.g = parameters.k;
            this.h = parameters.l;
            this.i = parameters.m;
            this.j = parameters.n;
            this.k = parameters.o;
            this.l = parameters.p;
            this.m = parameters.q;
            this.n = parameters.r;
            this.o = parameters.s;
            this.p = parameters.t;
            this.q = parameters.u;
            this.r = parameters.v;
            this.s = parameters.w;
            this.t = parameters.x;
            this.u = parameters.y;
            this.v = parameters.z;
            this.w = parameters.aa;
            this.x = parameters.ab;
            this.y = parameters.ac;
            this.z = parameters.ad;
            this.aa = parameters.ae;
            this.ab = parameters.af;
            this.ac = parameters.ag;
            this.ad = parameters.ah;
            this.ae = parameters.ai;
            this.af = parameters.aj;
            this.ag = parameters.ak;
            SparseArray<Map<TrackGroupArray, SelectionOverride>> sparseArray = new SparseArray<>();
            int i2 = 0;
            while (true) {
                SparseArray<Map<TrackGroupArray, SelectionOverride>> sparseArray2 = parameters.al;
                if (i2 < sparseArray2.size()) {
                    sparseArray.put(sparseArray2.keyAt(i2), new HashMap(sparseArray2.valueAt(i2)));
                    i2++;
                } else {
                    this.ah = sparseArray;
                    this.ai = parameters.am.clone();
                    return;
                }
            }
        }
    }

    public static final class SelectionOverride implements Parcelable {
        public static final Parcelable.Creator<SelectionOverride> CREATOR = new a();
        public final int c;
        public final int[] f;
        public final int g;
        public final int h;

        public class a implements Parcelable.Creator<SelectionOverride> {
            public SelectionOverride createFromParcel(Parcel parcel) {
                return new SelectionOverride(parcel);
            }

            public SelectionOverride[] newArray(int i) {
                return new SelectionOverride[i];
            }
        }

        public SelectionOverride(int i, int... iArr) {
            this(i, iArr, 0);
        }

        public boolean containsTrack(int i) {
            for (int i2 : this.f) {
                if (i2 == i) {
                    return true;
                }
            }
            return false;
        }

        public int describeContents() {
            return 0;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || SelectionOverride.class != obj.getClass()) {
                return false;
            }
            SelectionOverride selectionOverride = (SelectionOverride) obj;
            if (this.c == selectionOverride.c && Arrays.equals(this.f, selectionOverride.f) && this.h == selectionOverride.h) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return ((Arrays.hashCode(this.f) + (this.c * 31)) * 31) + this.h;
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.c);
            int[] iArr = this.f;
            parcel.writeInt(iArr.length);
            parcel.writeIntArray(iArr);
            parcel.writeInt(this.h);
        }

        public SelectionOverride(int i, int[] iArr, int i2) {
            this.c = i;
            int[] copyOf = Arrays.copyOf(iArr, iArr.length);
            this.f = copyOf;
            this.g = iArr.length;
            this.h = i2;
            Arrays.sort(copyOf);
        }

        public SelectionOverride(Parcel parcel) {
            this.c = parcel.readInt();
            int readByte = parcel.readByte();
            this.g = readByte;
            int[] iArr = new int[readByte];
            this.f = iArr;
            parcel.readIntArray(iArr);
            this.h = parcel.readInt();
        }
    }

    public static final class TextTrackScore implements Comparable<TextTrackScore> {
        public final boolean c;
        public final boolean f;
        public final boolean g;
        public final boolean h;
        public final int i;
        public final int j;
        public final int k;
        public final int l;
        public final boolean m;

        public TextTrackScore(Format format, Parameters parameters, int i2, @Nullable String str) {
            boolean z;
            boolean z2;
            ImmutableList<String> immutableList;
            int i3;
            boolean z3;
            boolean z4;
            boolean z5 = false;
            this.f = DefaultTrackSelector.d(i2, false);
            int i4 = format.h & (~parameters.j);
            if ((i4 & 1) != 0) {
                z = true;
            } else {
                z = false;
            }
            this.g = z;
            if ((i4 & 2) != 0) {
                z2 = true;
            } else {
                z2 = false;
            }
            this.h = z2;
            ImmutableList<String> immutableList2 = parameters.g;
            if (immutableList2.isEmpty()) {
                immutableList = ImmutableList.of("");
            } else {
                immutableList = immutableList2;
            }
            int i5 = 0;
            while (true) {
                if (i5 >= immutableList.size()) {
                    i5 = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
                    i3 = 0;
                    break;
                }
                i3 = DefaultTrackSelector.b(format, immutableList.get(i5), parameters.i);
                if (i3 > 0) {
                    break;
                }
                i5++;
            }
            this.i = i5;
            this.j = i3;
            int i6 = parameters.h;
            int i7 = format.i;
            int bitCount = Integer.bitCount(i6 & i7);
            this.k = bitCount;
            if ((i7 & 1088) != 0) {
                z3 = true;
            } else {
                z3 = false;
            }
            this.m = z3;
            if (DefaultTrackSelector.f(str) == null) {
                z4 = true;
            } else {
                z4 = false;
            }
            int b = DefaultTrackSelector.b(format, str, z4);
            this.l = b;
            if (i3 > 0 || ((immutableList2.isEmpty() && bitCount > 0) || this.g || (this.h && b > 0))) {
                z5 = true;
            }
            this.c = z5;
        }

        public int compareTo(TextTrackScore textTrackScore) {
            ComparisonChain compare = ComparisonChain.start().compareFalseFirst(this.f, textTrackScore.f).compare(Integer.valueOf(this.i), Integer.valueOf(textTrackScore.i), Ordering.natural().reverse());
            int i2 = textTrackScore.j;
            int i3 = this.j;
            ComparisonChain compare2 = compare.compare(i3, i2);
            int i4 = textTrackScore.k;
            int i5 = this.k;
            ComparisonChain compare3 = compare2.compare(i5, i4).compareFalseFirst(this.g, textTrackScore.g).compare(Boolean.valueOf(this.h), Boolean.valueOf(textTrackScore.h), i3 == 0 ? Ordering.natural() : Ordering.natural().reverse()).compare(this.l, textTrackScore.l);
            if (i5 == 0) {
                compare3 = compare3.compareTrueFirst(this.m, textTrackScore.m);
            }
            return compare3.result();
        }
    }

    public static final class VideoTrackScore implements Comparable<VideoTrackScore> {
        public final boolean c;
        public final Parameters f;
        public final boolean g;
        public final boolean h;
        public final int i;
        public final int j;
        public final int k;

        /* JADX WARNING: Code restructure failed: missing block: B:33:0x0053, code lost:
            if (r10 < ((float) r8.q)) goto L_0x005e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:37:0x005b, code lost:
            if (r10 < r8.r) goto L_0x005e;
         */
        /* JADX WARNING: Removed duplicated region for block: B:32:0x004e  */
        /* JADX WARNING: Removed duplicated region for block: B:36:0x0059  */
        /* JADX WARNING: Removed duplicated region for block: B:42:0x0079  */
        /* JADX WARNING: Removed duplicated region for block: B:50:0x008d A[SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public VideoTrackScore(com.google.android.exoplayer2.Format r7, com.google.android.exoplayer2.trackselection.DefaultTrackSelector.Parameters r8, int r9, boolean r10) {
            /*
                r6 = this;
                r6.<init>()
                r6.f = r8
                r0 = -1082130432(0xffffffffbf800000, float:-1.0)
                r1 = 1
                r2 = 0
                r3 = -1
                if (r10 == 0) goto L_0x0033
                int r4 = r7.u
                if (r4 == r3) goto L_0x0014
                int r5 = r8.k
                if (r4 > r5) goto L_0x0033
            L_0x0014:
                int r4 = r7.v
                if (r4 == r3) goto L_0x001c
                int r5 = r8.l
                if (r4 > r5) goto L_0x0033
            L_0x001c:
                float r4 = r7.w
                int r5 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
                if (r5 == 0) goto L_0x0029
                int r5 = r8.m
                float r5 = (float) r5
                int r4 = (r4 > r5 ? 1 : (r4 == r5 ? 0 : -1))
                if (r4 > 0) goto L_0x0033
            L_0x0029:
                int r4 = r7.l
                if (r4 == r3) goto L_0x0031
                int r5 = r8.n
                if (r4 > r5) goto L_0x0033
            L_0x0031:
                r4 = 1
                goto L_0x0034
            L_0x0033:
                r4 = 0
            L_0x0034:
                r6.c = r4
                if (r10 == 0) goto L_0x005e
                int r10 = r7.u
                if (r10 == r3) goto L_0x0040
                int r4 = r8.o
                if (r10 < r4) goto L_0x005e
            L_0x0040:
                int r10 = r7.v
                if (r10 == r3) goto L_0x0048
                int r4 = r8.p
                if (r10 < r4) goto L_0x005e
            L_0x0048:
                float r10 = r7.w
                int r0 = (r10 > r0 ? 1 : (r10 == r0 ? 0 : -1))
                if (r0 == 0) goto L_0x0055
                int r0 = r8.q
                float r0 = (float) r0
                int r10 = (r10 > r0 ? 1 : (r10 == r0 ? 0 : -1))
                if (r10 < 0) goto L_0x005e
            L_0x0055:
                int r10 = r7.l
                if (r10 == r3) goto L_0x005f
                int r0 = r8.r
                if (r10 < r0) goto L_0x005e
                goto L_0x005f
            L_0x005e:
                r1 = 0
            L_0x005f:
                r6.g = r1
                boolean r9 = com.google.android.exoplayer2.trackselection.DefaultTrackSelector.d(r9, r2)
                r6.h = r9
                int r9 = r7.l
                r6.i = r9
                int r9 = r7.getPixelCount()
                r6.j = r9
            L_0x0071:
                com.google.common.collect.ImmutableList<java.lang.String> r9 = r8.y
                int r9 = r9.size()
                if (r2 >= r9) goto L_0x008d
                java.lang.String r9 = r7.p
                if (r9 == 0) goto L_0x008a
                com.google.common.collect.ImmutableList<java.lang.String> r10 = r8.y
                java.lang.Object r10 = r10.get(r2)
                boolean r9 = r9.equals(r10)
                if (r9 == 0) goto L_0x008a
                goto L_0x0090
            L_0x008a:
                int r2 = r2 + 1
                goto L_0x0071
            L_0x008d:
                r2 = 2147483647(0x7fffffff, float:NaN)
            L_0x0090:
                r6.k = r2
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.trackselection.DefaultTrackSelector.VideoTrackScore.<init>(com.google.android.exoplayer2.Format, com.google.android.exoplayer2.trackselection.DefaultTrackSelector$Parameters, int, boolean):void");
        }

        public int compareTo(VideoTrackScore videoTrackScore) {
            Ordering<Integer> ordering;
            Ordering<Integer> ordering2;
            boolean z = this.h;
            boolean z2 = this.c;
            if (!z2 || !z) {
                ordering = DefaultTrackSelector.g.reverse();
            } else {
                ordering = DefaultTrackSelector.g;
            }
            ComparisonChain compare = ComparisonChain.start().compareFalseFirst(z, videoTrackScore.h).compareFalseFirst(z2, videoTrackScore.c).compareFalseFirst(this.g, videoTrackScore.g).compare(Integer.valueOf(this.k), Integer.valueOf(videoTrackScore.k), Ordering.natural().reverse());
            int i2 = this.i;
            Integer valueOf = Integer.valueOf(i2);
            int i3 = videoTrackScore.i;
            Integer valueOf2 = Integer.valueOf(i3);
            if (this.f.ag) {
                ordering2 = DefaultTrackSelector.g.reverse();
            } else {
                ordering2 = DefaultTrackSelector.h;
            }
            return compare.compare(valueOf, valueOf2, ordering2).compare(Integer.valueOf(this.j), Integer.valueOf(videoTrackScore.j), ordering).compare(Integer.valueOf(i2), Integer.valueOf(i3), ordering).result();
        }
    }

    @Deprecated
    public DefaultTrackSelector() {
        this(Parameters.an, (ExoTrackSelection.Factory) new AdaptiveTrackSelection.Factory());
    }

    public static int b(Format format, @Nullable String str, boolean z) {
        if (!TextUtils.isEmpty(str) && str.equals(format.g)) {
            return 4;
        }
        String f2 = f(str);
        String f3 = f(format.g);
        if (f3 == null || f2 == null) {
            if (!z || f3 != null) {
                return 0;
            }
            return 1;
        } else if (f3.startsWith(f2) || f2.startsWith(f3)) {
            return 3;
        } else {
            if (Util.splitAtFirst(f3, "-")[0].equals(Util.splitAtFirst(f2, "-")[0])) {
                return 2;
            }
            return 0;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x0051  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x005b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.ArrayList c(com.google.android.exoplayer2.source.TrackGroup r16, int r17, int r18, boolean r19) {
        /*
            r0 = r16
            r1 = r17
            r2 = r18
            java.util.ArrayList r3 = new java.util.ArrayList
            int r4 = r0.c
            r3.<init>(r4)
            r4 = 0
            r5 = 0
        L_0x000f:
            int r6 = r0.c
            if (r5 >= r6) goto L_0x001d
            java.lang.Integer r6 = java.lang.Integer.valueOf(r5)
            r3.add(r6)
            int r5 = r5 + 1
            goto L_0x000f
        L_0x001d:
            r5 = 2147483647(0x7fffffff, float:NaN)
            if (r1 == r5) goto L_0x00a8
            if (r2 != r5) goto L_0x0026
            goto L_0x00a8
        L_0x0026:
            r7 = 0
            r8 = 2147483647(0x7fffffff, float:NaN)
        L_0x002a:
            r9 = 1
            if (r7 >= r6) goto L_0x0082
            com.google.android.exoplayer2.Format r10 = r0.getFormat(r7)
            int r11 = r10.u
            if (r11 <= 0) goto L_0x007f
            int r12 = r10.v
            if (r12 <= 0) goto L_0x007f
            if (r19 == 0) goto L_0x0049
            if (r11 <= r12) goto L_0x003f
            r13 = 1
            goto L_0x0040
        L_0x003f:
            r13 = 0
        L_0x0040:
            if (r1 <= r2) goto L_0x0043
            goto L_0x0044
        L_0x0043:
            r9 = 0
        L_0x0044:
            if (r13 == r9) goto L_0x0049
            r9 = r1
            r13 = r2
            goto L_0x004b
        L_0x0049:
            r13 = r1
            r9 = r2
        L_0x004b:
            int r14 = r11 * r9
            int r15 = r12 * r13
            if (r14 < r15) goto L_0x005b
            android.graphics.Point r9 = new android.graphics.Point
            int r11 = com.google.android.exoplayer2.util.Util.ceilDivide((int) r15, (int) r11)
            r9.<init>(r13, r11)
            goto L_0x0065
        L_0x005b:
            android.graphics.Point r11 = new android.graphics.Point
            int r13 = com.google.android.exoplayer2.util.Util.ceilDivide((int) r14, (int) r12)
            r11.<init>(r13, r9)
            r9 = r11
        L_0x0065:
            int r10 = r10.u
            int r11 = r10 * r12
            int r13 = r9.x
            float r13 = (float) r13
            r14 = 1065017672(0x3f7ae148, float:0.98)
            float r13 = r13 * r14
            int r13 = (int) r13
            if (r10 < r13) goto L_0x007f
            int r9 = r9.y
            float r9 = (float) r9
            float r9 = r9 * r14
            int r9 = (int) r9
            if (r12 < r9) goto L_0x007f
            if (r11 >= r8) goto L_0x007f
            r8 = r11
        L_0x007f:
            int r7 = r7 + 1
            goto L_0x002a
        L_0x0082:
            if (r8 == r5) goto L_0x00a8
            int r1 = r3.size()
            int r1 = r1 - r9
        L_0x0089:
            if (r1 < 0) goto L_0x00a8
            java.lang.Object r2 = r3.get(r1)
            java.lang.Integer r2 = (java.lang.Integer) r2
            int r2 = r2.intValue()
            com.google.android.exoplayer2.Format r2 = r0.getFormat(r2)
            int r2 = r2.getPixelCount()
            r4 = -1
            if (r2 == r4) goto L_0x00a2
            if (r2 <= r8) goto L_0x00a5
        L_0x00a2:
            r3.remove(r1)
        L_0x00a5:
            int r1 = r1 + -1
            goto L_0x0089
        L_0x00a8:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.trackselection.DefaultTrackSelector.c(com.google.android.exoplayer2.source.TrackGroup, int, int, boolean):java.util.ArrayList");
    }

    public static boolean d(int i, boolean z) {
        int d2 = ha.d(i);
        return d2 == 4 || (z && d2 == 3);
    }

    public static boolean e(Format format, @Nullable String str, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
        int i11;
        if ((format.i & 16384) != 0 || !d(i, false) || (i & i2) == 0) {
            return false;
        }
        if (str != null && !Util.areEqual(format.p, str)) {
            return false;
        }
        int i12 = format.u;
        if (i12 != -1 && (i7 > i12 || i12 > i3)) {
            return false;
        }
        int i13 = format.v;
        if (i13 != -1 && (i8 > i13 || i13 > i4)) {
            return false;
        }
        float f2 = format.w;
        if ((f2 == -1.0f || (((float) i9) <= f2 && f2 <= ((float) i5))) && (i11 = format.l) != -1 && i10 <= i11 && i11 <= i6) {
            return true;
        }
        return false;
    }

    @Nullable
    public static String f(@Nullable String str) {
        if (TextUtils.isEmpty(str) || TextUtils.equals(str, "und")) {
            return null;
        }
        return str;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:289:0x058d, code lost:
        if (r4 == 2) goto L_0x0593;
     */
    /* JADX WARNING: Removed duplicated region for block: B:184:0x0395  */
    /* JADX WARNING: Removed duplicated region for block: B:325:0x0169 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x016f A[LOOP:1: B:21:0x0053->B:61:0x016f, LOOP_END] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.util.Pair<com.google.android.exoplayer2.RendererConfiguration[], com.google.android.exoplayer2.trackselection.ExoTrackSelection[]> a(com.google.android.exoplayer2.trackselection.MappingTrackSelector.MappedTrackInfo r49, int[][][] r50, int[] r51, com.google.android.exoplayer2.source.MediaSource.MediaPeriodId r52, com.google.android.exoplayer2.Timeline r53) throws com.google.android.exoplayer2.ExoPlaybackException {
        /*
            r48 = this;
            r0 = r48
            r1 = r49
            java.util.concurrent.atomic.AtomicReference<com.google.android.exoplayer2.trackselection.DefaultTrackSelector$Parameters> r2 = r0.e
            java.lang.Object r2 = r2.get()
            com.google.android.exoplayer2.trackselection.DefaultTrackSelector$Parameters r2 = (com.google.android.exoplayer2.trackselection.DefaultTrackSelector.Parameters) r2
            int r3 = r49.getRendererCount()
            int r4 = r49.getRendererCount()
            com.google.android.exoplayer2.trackselection.ExoTrackSelection$Definition[] r5 = new com.google.android.exoplayer2.trackselection.ExoTrackSelection.Definition[r4]
            r9 = r1
            r11 = r2
            r7 = 0
            r8 = 0
            r10 = 0
        L_0x001b:
            r12 = 2
            if (r7 >= r4) goto L_0x027a
            int r14 = r9.getRendererType(r7)
            if (r12 != r14) goto L_0x0261
            if (r10 != 0) goto L_0x0246
            com.google.android.exoplayer2.source.TrackGroupArray r9 = r9.getTrackGroups(r7)
            r10 = r50[r7]
            r14 = r51[r7]
            boolean r6 = r11.ah
            boolean r15 = r11.x
            int r13 = r11.w
            int r12 = r11.v
            if (r6 != 0) goto L_0x01a1
            boolean r6 = r11.ag
            if (r6 != 0) goto L_0x01a1
            boolean r6 = r11.u
            if (r6 == 0) goto L_0x0043
            r6 = 24
            goto L_0x0045
        L_0x0043:
            r6 = 16
        L_0x0045:
            boolean r0 = r11.t
            if (r0 == 0) goto L_0x004f
            r0 = r14 & r6
            if (r0 == 0) goto L_0x004f
            r0 = 1
            goto L_0x0050
        L_0x004f:
            r0 = 0
        L_0x0050:
            r30 = r3
            r14 = 0
        L_0x0053:
            int r3 = r9.c
            if (r14 >= r3) goto L_0x018c
            com.google.android.exoplayer2.source.TrackGroup r3 = r9.get(r14)
            r31 = r10[r14]
            r32 = r4
            int r4 = r11.k
            r33 = r8
            int r8 = r11.l
            int r1 = r11.m
            r34 = r5
            int r5 = r11.n
            r35 = r7
            int r7 = r11.o
            r36 = r2
            int r2 = r11.p
            r37 = r10
            int r10 = r11.q
            int r11 = r11.r
            r38 = r9
            int r9 = r3.c
            int[] r39 = f
            r40 = r14
            r14 = 2
            if (r9 >= r14) goto L_0x0087
            r41 = r12
            goto L_0x0093
        L_0x0087:
            java.util.ArrayList r9 = c(r3, r12, r13, r15)
            r41 = r12
            int r12 = r9.size()
            if (r12 >= r14) goto L_0x009b
        L_0x0093:
            r42 = r0
            r44 = r13
            r46 = r15
            goto L_0x015e
        L_0x009b:
            if (r0 != 0) goto L_0x0119
            java.util.HashSet r12 = new java.util.HashSet
            r12.<init>()
            r42 = r0
            r44 = r13
            r0 = 0
            r14 = 0
            r43 = 0
        L_0x00aa:
            int r13 = r9.size()
            if (r0 >= r13) goto L_0x0116
            java.lang.Object r13 = r9.get(r0)
            java.lang.Integer r13 = (java.lang.Integer) r13
            int r13 = r13.intValue()
            com.google.android.exoplayer2.Format r13 = r3.getFormat(r13)
            java.lang.String r13 = r13.p
            boolean r18 = r12.add(r13)
            if (r18 == 0) goto L_0x0109
            r47 = r0
            r45 = r12
            r46 = r15
            r12 = 0
            r15 = 0
        L_0x00ce:
            int r0 = r9.size()
            if (r12 >= r0) goto L_0x0103
            java.lang.Object r0 = r9.get(r12)
            java.lang.Integer r0 = (java.lang.Integer) r0
            int r0 = r0.intValue()
            com.google.android.exoplayer2.Format r18 = r3.getFormat(r0)
            r20 = r31[r0]
            r19 = r13
            r21 = r6
            r22 = r4
            r23 = r8
            r24 = r1
            r25 = r5
            r26 = r7
            r27 = r2
            r28 = r10
            r29 = r11
            boolean r0 = e(r18, r19, r20, r21, r22, r23, r24, r25, r26, r27, r28, r29)
            if (r0 == 0) goto L_0x0100
            int r15 = r15 + 1
        L_0x0100:
            int r12 = r12 + 1
            goto L_0x00ce
        L_0x0103:
            if (r15 <= r14) goto L_0x010f
            r43 = r13
            r14 = r15
            goto L_0x010f
        L_0x0109:
            r47 = r0
            r45 = r12
            r46 = r15
        L_0x010f:
            int r0 = r47 + 1
            r12 = r45
            r15 = r46
            goto L_0x00aa
        L_0x0116:
            r46 = r15
            goto L_0x0121
        L_0x0119:
            r42 = r0
            r44 = r13
            r46 = r15
            r43 = 0
        L_0x0121:
            int r0 = r9.size()
        L_0x0125:
            r12 = -1
            int r0 = r0 + r12
            if (r0 < 0) goto L_0x0157
            java.lang.Object r12 = r9.get(r0)
            java.lang.Integer r12 = (java.lang.Integer) r12
            int r12 = r12.intValue()
            com.google.android.exoplayer2.Format r18 = r3.getFormat(r12)
            r20 = r31[r12]
            r19 = r43
            r21 = r6
            r22 = r4
            r23 = r8
            r24 = r1
            r25 = r5
            r26 = r7
            r27 = r2
            r28 = r10
            r29 = r11
            boolean r12 = e(r18, r19, r20, r21, r22, r23, r24, r25, r26, r27, r28, r29)
            if (r12 != 0) goto L_0x0125
            r9.remove(r0)
            goto L_0x0125
        L_0x0157:
            int r0 = r9.size()
            r1 = 2
            if (r0 >= r1) goto L_0x0161
        L_0x015e:
            r0 = r39
            goto L_0x0166
        L_0x0161:
            int[] r39 = defpackage.y3.c(r9)
            goto L_0x015e
        L_0x0166:
            int r1 = r0.length
            if (r1 <= 0) goto L_0x016f
            com.google.android.exoplayer2.trackselection.ExoTrackSelection$Definition r1 = new com.google.android.exoplayer2.trackselection.ExoTrackSelection$Definition
            r1.<init>(r3, r0)
            goto L_0x01a7
        L_0x016f:
            int r14 = r40 + 1
            r1 = r49
            r4 = r32
            r8 = r33
            r5 = r34
            r7 = r35
            r2 = r36
            r11 = r2
            r10 = r37
            r9 = r38
            r12 = r41
            r0 = r42
            r13 = r44
            r15 = r46
            goto L_0x0053
        L_0x018c:
            r36 = r2
        L_0x018e:
            r32 = r4
            r34 = r5
            r35 = r7
            r33 = r8
            r38 = r9
            r37 = r10
            r41 = r12
            r44 = r13
            r46 = r15
            goto L_0x01a6
        L_0x01a1:
            r36 = r2
            r30 = r3
            goto L_0x018e
        L_0x01a6:
            r1 = 0
        L_0x01a7:
            if (r1 != 0) goto L_0x0236
            r3 = r38
            r0 = 0
            r1 = 0
            r2 = 0
            r13 = -1
        L_0x01af:
            int r4 = r3.c
            if (r2 >= r4) goto L_0x021f
            com.google.android.exoplayer2.source.TrackGroup r4 = r3.get(r2)
            r7 = r41
            r6 = r44
            r5 = r46
            java.util.ArrayList r8 = c(r4, r7, r6, r5)
            r9 = r37[r2]
            r10 = 0
        L_0x01c4:
            int r11 = r4.c
            if (r10 >= r11) goto L_0x0212
            com.google.android.exoplayer2.Format r11 = r4.getFormat(r10)
            int r12 = r11.i
            r12 = r12 & 16384(0x4000, float:2.2959E-41)
            if (r12 == 0) goto L_0x01d7
            r17 = r0
            r14 = r36
            goto L_0x020b
        L_0x01d7:
            r12 = r9[r10]
            r14 = r36
            boolean r15 = r14.ai
            boolean r12 = d(r12, r15)
            if (r12 == 0) goto L_0x0209
            com.google.android.exoplayer2.trackselection.DefaultTrackSelector$VideoTrackScore r12 = new com.google.android.exoplayer2.trackselection.DefaultTrackSelector$VideoTrackScore
            r15 = r9[r10]
            r17 = r0
            java.lang.Integer r0 = java.lang.Integer.valueOf(r10)
            boolean r0 = r8.contains(r0)
            r12.<init>(r11, r14, r15, r0)
            boolean r0 = r12.c
            if (r0 != 0) goto L_0x01fd
            boolean r0 = r14.s
            if (r0 != 0) goto L_0x01fd
            goto L_0x020b
        L_0x01fd:
            if (r1 == 0) goto L_0x0205
            int r0 = r12.compareTo((com.google.android.exoplayer2.trackselection.DefaultTrackSelector.VideoTrackScore) r1)
            if (r0 <= 0) goto L_0x020b
        L_0x0205:
            r0 = r4
            r13 = r10
            r1 = r12
            goto L_0x020d
        L_0x0209:
            r17 = r0
        L_0x020b:
            r0 = r17
        L_0x020d:
            int r10 = r10 + 1
            r36 = r14
            goto L_0x01c4
        L_0x0212:
            r17 = r0
            r14 = r36
            int r2 = r2 + 1
            r46 = r5
            r44 = r6
            r41 = r7
            goto L_0x01af
        L_0x021f:
            r14 = r36
            if (r0 != 0) goto L_0x0226
            r16 = 0
            goto L_0x0233
        L_0x0226:
            com.google.android.exoplayer2.trackselection.ExoTrackSelection$Definition r1 = new com.google.android.exoplayer2.trackselection.ExoTrackSelection$Definition
            r2 = 1
            int[] r3 = new int[r2]
            r2 = 0
            r3[r2] = r13
            r1.<init>(r0, r3)
            r16 = r1
        L_0x0233:
            r1 = r16
            goto L_0x0238
        L_0x0236:
            r14 = r36
        L_0x0238:
            r34[r35] = r1
            if (r1 == 0) goto L_0x023e
            r0 = 1
            goto L_0x023f
        L_0x023e:
            r0 = 0
        L_0x023f:
            r10 = r0
            r11 = r14
            r6 = r35
            r0 = r49
            goto L_0x0252
        L_0x0246:
            r14 = r2
            r30 = r3
            r32 = r4
            r34 = r5
            r33 = r8
            r0 = r49
            r6 = r7
        L_0x0252:
            com.google.android.exoplayer2.source.TrackGroupArray r1 = r0.getTrackGroups(r6)
            int r1 = r1.c
            if (r1 <= 0) goto L_0x025c
            r15 = 1
            goto L_0x025d
        L_0x025c:
            r15 = 0
        L_0x025d:
            r8 = r33 | r15
            r9 = r0
            goto L_0x026c
        L_0x0261:
            r0 = r1
            r14 = r2
            r30 = r3
            r32 = r4
            r34 = r5
            r6 = r7
            r33 = r8
        L_0x026c:
            int r7 = r6 + 1
            r1 = r0
            r2 = r14
            r3 = r30
            r4 = r32
            r5 = r34
            r0 = r48
            goto L_0x001b
        L_0x027a:
            r0 = r1
            r14 = r2
            r30 = r3
            r34 = r5
            r33 = r8
            r1 = 0
            r2 = 0
            r3 = -1
            r5 = 0
        L_0x0286:
            if (r2 >= r4) goto L_0x03e7
            int r6 = r9.getRendererType(r2)
            r7 = 1
            if (r7 != r6) goto L_0x03d9
            boolean r6 = r11.ak
            if (r6 != 0) goto L_0x0298
            if (r33 != 0) goto L_0x0296
            goto L_0x0298
        L_0x0296:
            r6 = 0
            goto L_0x0299
        L_0x0298:
            r6 = 1
        L_0x0299:
            com.google.android.exoplayer2.source.TrackGroupArray r7 = r9.getTrackGroups(r2)
            r8 = r50[r2]
            r10 = r51[r2]
            r18 = r9
            r10 = 0
            r12 = 0
            r13 = -1
            r15 = -1
        L_0x02a7:
            int r9 = r7.c
            if (r12 >= r9) goto L_0x02fa
            com.google.android.exoplayer2.source.TrackGroup r9 = r7.get(r12)
            r19 = r8[r12]
            r20 = r13
            r21 = r15
            r13 = 0
        L_0x02b6:
            int r15 = r9.c
            if (r13 >= r15) goto L_0x02f1
            r15 = r19[r13]
            boolean r0 = r11.ai
            boolean r0 = d(r15, r0)
            if (r0 == 0) goto L_0x02e8
            com.google.android.exoplayer2.Format r0 = r9.getFormat(r13)
            com.google.android.exoplayer2.trackselection.DefaultTrackSelector$AudioTrackScore r15 = new com.google.android.exoplayer2.trackselection.DefaultTrackSelector$AudioTrackScore
            r22 = r9
            r9 = r19[r13]
            r15.<init>(r0, r11, r9)
            boolean r0 = r15.c
            if (r0 != 0) goto L_0x02da
            boolean r0 = r11.ab
            if (r0 != 0) goto L_0x02da
            goto L_0x02ea
        L_0x02da:
            if (r10 == 0) goto L_0x02e2
            int r0 = r15.compareTo((com.google.android.exoplayer2.trackselection.DefaultTrackSelector.AudioTrackScore) r10)
            if (r0 <= 0) goto L_0x02ea
        L_0x02e2:
            r20 = r12
            r21 = r13
            r10 = r15
            goto L_0x02ea
        L_0x02e8:
            r22 = r9
        L_0x02ea:
            int r13 = r13 + 1
            r0 = r49
            r9 = r22
            goto L_0x02b6
        L_0x02f1:
            int r12 = r12 + 1
            r0 = r49
            r13 = r20
            r15 = r21
            goto L_0x02a7
        L_0x02fa:
            r0 = -1
            if (r13 != r0) goto L_0x0302
            r36 = r14
            r0 = 0
            goto L_0x03aa
        L_0x0302:
            com.google.android.exoplayer2.source.TrackGroup r0 = r7.get(r13)
            boolean r7 = r11.ah
            if (r7 != 0) goto L_0x0390
            boolean r7 = r11.ag
            if (r7 != 0) goto L_0x0390
            if (r6 == 0) goto L_0x0390
            r6 = r8[r13]
            com.google.android.exoplayer2.Format r7 = r0.getFormat(r15)
            int r8 = r0.c
            int[] r9 = new int[r8]
            r12 = 0
            r13 = 0
        L_0x031c:
            if (r12 >= r8) goto L_0x0380
            if (r12 == r15) goto L_0x036c
            r19 = r8
            com.google.android.exoplayer2.Format r8 = r0.getFormat(r12)
            r36 = r14
            r14 = r6[r12]
            r20 = r6
            r6 = 0
            boolean r14 = d(r14, r6)
            if (r14 == 0) goto L_0x0368
            int r6 = r8.l
            r14 = -1
            if (r6 == r14) goto L_0x0368
            int r14 = r11.aa
            if (r6 > r14) goto L_0x0368
            boolean r6 = r11.ae
            if (r6 != 0) goto L_0x0349
            int r6 = r8.ac
            r14 = -1
            if (r6 == r14) goto L_0x0368
            int r14 = r7.ac
            if (r6 != r14) goto L_0x0368
        L_0x0349:
            boolean r6 = r11.ac
            if (r6 != 0) goto L_0x0359
            java.lang.String r6 = r8.p
            if (r6 == 0) goto L_0x0368
            java.lang.String r14 = r7.p
            boolean r6 = android.text.TextUtils.equals(r6, r14)
            if (r6 == 0) goto L_0x0368
        L_0x0359:
            boolean r6 = r11.ad
            if (r6 != 0) goto L_0x0366
            int r6 = r8.ad
            r8 = -1
            if (r6 == r8) goto L_0x0368
            int r8 = r7.ad
            if (r6 != r8) goto L_0x0368
        L_0x0366:
            r6 = 1
            goto L_0x0369
        L_0x0368:
            r6 = 0
        L_0x0369:
            if (r6 == 0) goto L_0x0377
            goto L_0x0372
        L_0x036c:
            r20 = r6
            r19 = r8
            r36 = r14
        L_0x0372:
            int r6 = r13 + 1
            r9[r13] = r12
            r13 = r6
        L_0x0377:
            int r12 = r12 + 1
            r8 = r19
            r6 = r20
            r14 = r36
            goto L_0x031c
        L_0x0380:
            r36 = r14
            int[] r6 = java.util.Arrays.copyOf(r9, r13)
            int r7 = r6.length
            r8 = 1
            if (r7 <= r8) goto L_0x0392
            com.google.android.exoplayer2.trackselection.ExoTrackSelection$Definition r7 = new com.google.android.exoplayer2.trackselection.ExoTrackSelection$Definition
            r7.<init>(r0, r6)
            goto L_0x0393
        L_0x0390:
            r36 = r14
        L_0x0392:
            r7 = 0
        L_0x0393:
            if (r7 != 0) goto L_0x03a0
            com.google.android.exoplayer2.trackselection.ExoTrackSelection$Definition r7 = new com.google.android.exoplayer2.trackselection.ExoTrackSelection$Definition
            r6 = 1
            int[] r8 = new int[r6]
            r6 = 0
            r8[r6] = r15
            r7.<init>(r0, r8)
        L_0x03a0:
            java.lang.Object r0 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r10)
            com.google.android.exoplayer2.trackselection.DefaultTrackSelector$AudioTrackScore r0 = (com.google.android.exoplayer2.trackselection.DefaultTrackSelector.AudioTrackScore) r0
            android.util.Pair r0 = android.util.Pair.create(r7, r0)
        L_0x03aa:
            if (r0 == 0) goto L_0x03dd
            if (r1 == 0) goto L_0x03b8
            java.lang.Object r6 = r0.second
            com.google.android.exoplayer2.trackselection.DefaultTrackSelector$AudioTrackScore r6 = (com.google.android.exoplayer2.trackselection.DefaultTrackSelector.AudioTrackScore) r6
            int r6 = r6.compareTo((com.google.android.exoplayer2.trackselection.DefaultTrackSelector.AudioTrackScore) r1)
            if (r6 <= 0) goto L_0x03dd
        L_0x03b8:
            r1 = -1
            if (r3 == r1) goto L_0x03be
            r1 = 0
            r34[r3] = r1
        L_0x03be:
            java.lang.Object r1 = r0.first
            com.google.android.exoplayer2.trackselection.ExoTrackSelection$Definition r1 = (com.google.android.exoplayer2.trackselection.ExoTrackSelection.Definition) r1
            r34[r2] = r1
            com.google.android.exoplayer2.source.TrackGroup r3 = r1.a
            int[] r1 = r1.b
            r5 = 0
            r1 = r1[r5]
            com.google.android.exoplayer2.Format r1 = r3.getFormat(r1)
            java.lang.String r1 = r1.g
            java.lang.Object r0 = r0.second
            com.google.android.exoplayer2.trackselection.DefaultTrackSelector$AudioTrackScore r0 = (com.google.android.exoplayer2.trackselection.DefaultTrackSelector.AudioTrackScore) r0
            r5 = r1
            r3 = r2
            r1 = r0
            goto L_0x03dd
        L_0x03d9:
            r18 = r9
            r36 = r14
        L_0x03dd:
            int r2 = r2 + 1
            r0 = r49
            r9 = r18
            r14 = r36
            goto L_0x0286
        L_0x03e7:
            r18 = r9
            r36 = r14
            r1 = 0
            r2 = 0
            r12 = -1
        L_0x03ee:
            if (r2 >= r4) goto L_0x04f8
            int r0 = r9.getRendererType(r2)
            r3 = 1
            if (r0 == r3) goto L_0x04f1
            r3 = 2
            if (r0 == r3) goto L_0x04f1
            r3 = 3
            if (r0 == r3) goto L_0x045e
            com.google.android.exoplayer2.source.TrackGroupArray r0 = r9.getTrackGroups(r2)
            r3 = r50[r2]
            r6 = 0
            r7 = 0
            r8 = 0
            r9 = 0
        L_0x0407:
            int r10 = r0.c
            if (r8 >= r10) goto L_0x044b
            com.google.android.exoplayer2.source.TrackGroup r10 = r0.get(r8)
            r13 = r3[r8]
            r14 = 0
        L_0x0412:
            int r15 = r10.c
            if (r14 >= r15) goto L_0x0444
            r15 = r13[r14]
            r51 = r0
            boolean r0 = r11.ai
            boolean r0 = d(r15, r0)
            if (r0 == 0) goto L_0x043b
            com.google.android.exoplayer2.Format r0 = r10.getFormat(r14)
            com.google.android.exoplayer2.trackselection.DefaultTrackSelector$OtherTrackScore r15 = new com.google.android.exoplayer2.trackselection.DefaultTrackSelector$OtherTrackScore
            r18 = r3
            r3 = r13[r14]
            r15.<init>(r0, r3)
            if (r7 == 0) goto L_0x0437
            int r0 = r15.compareTo((com.google.android.exoplayer2.trackselection.DefaultTrackSelector.OtherTrackScore) r7)
            if (r0 <= 0) goto L_0x043d
        L_0x0437:
            r6 = r10
            r9 = r14
            r7 = r15
            goto L_0x043d
        L_0x043b:
            r18 = r3
        L_0x043d:
            int r14 = r14 + 1
            r0 = r51
            r3 = r18
            goto L_0x0412
        L_0x0444:
            r51 = r0
            r18 = r3
            int r8 = r8 + 1
            goto L_0x0407
        L_0x044b:
            if (r6 != 0) goto L_0x044f
            r0 = 0
            goto L_0x045a
        L_0x044f:
            com.google.android.exoplayer2.trackselection.ExoTrackSelection$Definition r0 = new com.google.android.exoplayer2.trackselection.ExoTrackSelection$Definition
            r3 = 1
            int[] r7 = new int[r3]
            r3 = 0
            r7[r3] = r9
            r0.<init>(r6, r7)
        L_0x045a:
            r34[r2] = r0
            goto L_0x04f1
        L_0x045e:
            com.google.android.exoplayer2.source.TrackGroupArray r0 = r9.getTrackGroups(r2)
            r3 = r50[r2]
            r6 = 0
            r7 = 0
            r8 = 0
            r9 = -1
        L_0x0468:
            int r10 = r0.c
            if (r6 >= r10) goto L_0x04b6
            com.google.android.exoplayer2.source.TrackGroup r10 = r0.get(r6)
            r13 = r3[r6]
            r14 = r9
            r9 = r8
            r8 = r7
            r7 = 0
        L_0x0476:
            int r15 = r10.c
            if (r7 >= r15) goto L_0x04ac
            r15 = r13[r7]
            r51 = r0
            boolean r0 = r11.ai
            boolean r0 = d(r15, r0)
            if (r0 == 0) goto L_0x04a3
            com.google.android.exoplayer2.Format r0 = r10.getFormat(r7)
            com.google.android.exoplayer2.trackselection.DefaultTrackSelector$TextTrackScore r15 = new com.google.android.exoplayer2.trackselection.DefaultTrackSelector$TextTrackScore
            r18 = r3
            r3 = r13[r7]
            r15.<init>(r0, r11, r3, r5)
            boolean r0 = r15.c
            if (r0 == 0) goto L_0x04a5
            if (r9 == 0) goto L_0x049f
            int r0 = r15.compareTo((com.google.android.exoplayer2.trackselection.DefaultTrackSelector.TextTrackScore) r9)
            if (r0 <= 0) goto L_0x04a5
        L_0x049f:
            r14 = r7
            r8 = r10
            r9 = r15
            goto L_0x04a5
        L_0x04a3:
            r18 = r3
        L_0x04a5:
            int r7 = r7 + 1
            r0 = r51
            r3 = r18
            goto L_0x0476
        L_0x04ac:
            r51 = r0
            r18 = r3
            int r6 = r6 + 1
            r7 = r8
            r8 = r9
            r9 = r14
            goto L_0x0468
        L_0x04b6:
            if (r7 != 0) goto L_0x04bb
            r0 = 0
            r3 = 0
            goto L_0x04d0
        L_0x04bb:
            com.google.android.exoplayer2.trackselection.ExoTrackSelection$Definition r0 = new com.google.android.exoplayer2.trackselection.ExoTrackSelection$Definition
            r3 = 1
            int[] r6 = new int[r3]
            r3 = 0
            r6[r3] = r9
            r0.<init>(r7, r6)
            java.lang.Object r6 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r8)
            com.google.android.exoplayer2.trackselection.DefaultTrackSelector$TextTrackScore r6 = (com.google.android.exoplayer2.trackselection.DefaultTrackSelector.TextTrackScore) r6
            android.util.Pair r0 = android.util.Pair.create(r0, r6)
        L_0x04d0:
            if (r0 == 0) goto L_0x04f2
            if (r1 == 0) goto L_0x04de
            java.lang.Object r6 = r0.second
            com.google.android.exoplayer2.trackselection.DefaultTrackSelector$TextTrackScore r6 = (com.google.android.exoplayer2.trackselection.DefaultTrackSelector.TextTrackScore) r6
            int r6 = r6.compareTo((com.google.android.exoplayer2.trackselection.DefaultTrackSelector.TextTrackScore) r1)
            if (r6 <= 0) goto L_0x04f2
        L_0x04de:
            r1 = -1
            if (r12 == r1) goto L_0x04e4
            r1 = 0
            r34[r12] = r1
        L_0x04e4:
            java.lang.Object r1 = r0.first
            com.google.android.exoplayer2.trackselection.ExoTrackSelection$Definition r1 = (com.google.android.exoplayer2.trackselection.ExoTrackSelection.Definition) r1
            r34[r2] = r1
            java.lang.Object r0 = r0.second
            com.google.android.exoplayer2.trackselection.DefaultTrackSelector$TextTrackScore r0 = (com.google.android.exoplayer2.trackselection.DefaultTrackSelector.TextTrackScore) r0
            r1 = r0
            r12 = r2
            goto L_0x04f2
        L_0x04f1:
            r3 = 0
        L_0x04f2:
            int r2 = r2 + 1
            r9 = r49
            goto L_0x03ee
        L_0x04f8:
            r3 = 0
            r0 = r30
            r2 = 0
        L_0x04fc:
            if (r2 >= r0) goto L_0x0537
            r1 = r36
            boolean r4 = r1.getRendererDisabled(r2)
            if (r4 == 0) goto L_0x050c
            r4 = 0
            r34[r2] = r4
            r5 = r49
            goto L_0x0532
        L_0x050c:
            r5 = r49
            r4 = 0
            com.google.android.exoplayer2.source.TrackGroupArray r6 = r5.getTrackGroups(r2)
            boolean r7 = r1.hasSelectionOverride(r2, r6)
            if (r7 == 0) goto L_0x0532
            com.google.android.exoplayer2.trackselection.DefaultTrackSelector$SelectionOverride r7 = r1.getSelectionOverride(r2, r6)
            if (r7 != 0) goto L_0x0521
            r8 = r4
            goto L_0x0530
        L_0x0521:
            com.google.android.exoplayer2.trackselection.ExoTrackSelection$Definition r8 = new com.google.android.exoplayer2.trackselection.ExoTrackSelection$Definition
            int r9 = r7.c
            com.google.android.exoplayer2.source.TrackGroup r6 = r6.get(r9)
            int[] r9 = r7.f
            int r7 = r7.h
            r8.<init>(r6, r9, r7)
        L_0x0530:
            r34[r2] = r8
        L_0x0532:
            int r2 = r2 + 1
            r36 = r1
            goto L_0x04fc
        L_0x0537:
            r2 = r48
            r5 = r49
            r1 = r36
            r4 = 0
            com.google.android.exoplayer2.upstream.BandwidthMeter r6 = r2.b
            java.lang.Object r6 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r6)
            com.google.android.exoplayer2.upstream.BandwidthMeter r6 = (com.google.android.exoplayer2.upstream.BandwidthMeter) r6
            com.google.android.exoplayer2.trackselection.ExoTrackSelection$Factory r7 = r2.d
            r8 = r52
            r9 = r53
            r10 = r34
            com.google.android.exoplayer2.trackselection.ExoTrackSelection[] r6 = r7.createTrackSelections(r10, r6, r8, r9)
            com.google.android.exoplayer2.RendererConfiguration[] r7 = new com.google.android.exoplayer2.RendererConfiguration[r0]
            r8 = 0
        L_0x0555:
            if (r8 >= r0) goto L_0x0576
            boolean r9 = r1.getRendererDisabled(r8)
            if (r9 != 0) goto L_0x056a
            int r9 = r5.getRendererType(r8)
            r10 = 7
            if (r9 == r10) goto L_0x0568
            r9 = r6[r8]
            if (r9 == 0) goto L_0x056a
        L_0x0568:
            r9 = 1
            goto L_0x056b
        L_0x056a:
            r9 = 0
        L_0x056b:
            if (r9 == 0) goto L_0x0570
            com.google.android.exoplayer2.RendererConfiguration r9 = com.google.android.exoplayer2.RendererConfiguration.b
            goto L_0x0571
        L_0x0570:
            r9 = r4
        L_0x0571:
            r7[r8] = r9
            int r8 = r8 + 1
            goto L_0x0555
        L_0x0576:
            boolean r0 = r1.aj
            if (r0 == 0) goto L_0x05e8
            r0 = 0
            r1 = -1
            r12 = -1
        L_0x057d:
            int r4 = r49.getRendererCount()
            if (r0 >= r4) goto L_0x05d4
            int r4 = r5.getRendererType(r0)
            r8 = r6[r0]
            r9 = 1
            if (r4 == r9) goto L_0x0592
            r9 = 2
            if (r4 != r9) goto L_0x0590
            goto L_0x0593
        L_0x0590:
            r4 = -1
            goto L_0x05d1
        L_0x0592:
            r9 = 2
        L_0x0593:
            if (r8 == 0) goto L_0x0590
            r10 = r50[r0]
            com.google.android.exoplayer2.source.TrackGroupArray r11 = r5.getTrackGroups(r0)
            com.google.android.exoplayer2.source.TrackGroup r13 = r8.getTrackGroup()
            int r11 = r11.indexOf(r13)
            r13 = 0
        L_0x05a4:
            int r14 = r8.length()
            if (r13 >= r14) goto L_0x05bf
            r14 = r10[r11]
            int r15 = r8.getIndexInTrackGroup(r13)
            r14 = r14[r15]
            int r14 = defpackage.ha.e(r14)
            r15 = 32
            if (r14 == r15) goto L_0x05bc
            r8 = 0
            goto L_0x05c0
        L_0x05bc:
            int r13 = r13 + 1
            goto L_0x05a4
        L_0x05bf:
            r8 = 1
        L_0x05c0:
            if (r8 == 0) goto L_0x0590
            r8 = 1
            if (r4 != r8) goto L_0x05cb
            r4 = -1
            if (r1 == r4) goto L_0x05c9
            goto L_0x05ce
        L_0x05c9:
            r1 = r0
            goto L_0x05d1
        L_0x05cb:
            r4 = -1
            if (r12 == r4) goto L_0x05d0
        L_0x05ce:
            r0 = 0
            goto L_0x05d6
        L_0x05d0:
            r12 = r0
        L_0x05d1:
            int r0 = r0 + 1
            goto L_0x057d
        L_0x05d4:
            r4 = -1
            r0 = 1
        L_0x05d6:
            if (r1 == r4) goto L_0x05db
            if (r12 == r4) goto L_0x05db
            r3 = 1
        L_0x05db:
            r0 = r0 & r3
            if (r0 == 0) goto L_0x05e8
            com.google.android.exoplayer2.RendererConfiguration r0 = new com.google.android.exoplayer2.RendererConfiguration
            r3 = 1
            r0.<init>(r3)
            r7[r1] = r0
            r7[r12] = r0
        L_0x05e8:
            android.util.Pair r0 = android.util.Pair.create(r7, r6)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.trackselection.DefaultTrackSelector.a(com.google.android.exoplayer2.trackselection.MappingTrackSelector$MappedTrackInfo, int[][][], int[], com.google.android.exoplayer2.source.MediaSource$MediaPeriodId, com.google.android.exoplayer2.Timeline):android.util.Pair");
    }

    public ParametersBuilder buildUponParameters() {
        return getParameters().buildUpon();
    }

    public Parameters getParameters() {
        return this.e.get();
    }

    public void setParameters(Parameters parameters) {
        TrackSelector.InvalidationListener invalidationListener;
        Assertions.checkNotNull(parameters);
        if (!this.e.getAndSet(parameters).equals(parameters) && (invalidationListener = this.a) != null) {
            invalidationListener.onTrackSelectionsInvalidated();
        }
    }

    @Deprecated
    public DefaultTrackSelector(ExoTrackSelection.Factory factory) {
        this(Parameters.an, factory);
    }

    public DefaultTrackSelector(Context context) {
        this(context, (ExoTrackSelection.Factory) new AdaptiveTrackSelection.Factory());
    }

    public DefaultTrackSelector(Context context, ExoTrackSelection.Factory factory) {
        this(Parameters.getDefaults(context), factory);
    }

    public DefaultTrackSelector(Parameters parameters, ExoTrackSelection.Factory factory) {
        this.d = factory;
        this.e = new AtomicReference<>(parameters);
    }

    public void setParameters(ParametersBuilder parametersBuilder) {
        setParameters(parametersBuilder.build());
    }
}
