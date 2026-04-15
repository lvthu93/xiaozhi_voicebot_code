package com.google.android.exoplayer2.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import androidx.annotation.AttrRes;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.util.Assertions;
import info.dourok.voicebot.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class TrackSelectionView extends LinearLayout {
    public static final /* synthetic */ int u = 0;
    public final int c;
    public final LayoutInflater f;
    public final CheckedTextView g;
    public final CheckedTextView h;
    public final a i;
    public final SparseArray<DefaultTrackSelector.SelectionOverride> j;
    public boolean k;
    public boolean l;
    public TrackNameProvider m;
    public CheckedTextView[][] n;
    public MappingTrackSelector.MappedTrackInfo o;
    public int p;
    public TrackGroupArray q;
    public boolean r;
    @Nullable
    public x5 s;
    @Nullable
    public TrackSelectionListener t;

    public interface TrackSelectionListener {
        void onTrackSelectionChanged(boolean z, List<DefaultTrackSelector.SelectionOverride> list);
    }

    public class a implements View.OnClickListener {
        public a() {
        }

        /* JADX WARNING: Removed duplicated region for block: B:46:0x00b8  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onClick(android.view.View r11) {
            /*
                r10 = this;
                com.google.android.exoplayer2.ui.TrackSelectionView r0 = com.google.android.exoplayer2.ui.TrackSelectionView.this
                android.widget.CheckedTextView r1 = r0.g
                android.util.SparseArray<com.google.android.exoplayer2.trackselection.DefaultTrackSelector$SelectionOverride> r2 = r0.j
                r3 = 1
                if (r11 != r1) goto L_0x0010
                r0.r = r3
                r2.clear()
                goto L_0x00da
            L_0x0010:
                android.widget.CheckedTextView r1 = r0.h
                r4 = 0
                if (r11 != r1) goto L_0x001c
                r0.r = r4
                r2.clear()
                goto L_0x00da
            L_0x001c:
                r0.r = r4
                java.lang.Object r1 = r11.getTag()
                java.lang.Object r1 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r1)
                com.google.android.exoplayer2.ui.TrackSelectionView$b r1 = (com.google.android.exoplayer2.ui.TrackSelectionView.b) r1
                int r5 = r1.a
                java.lang.Object r6 = r2.get(r5)
                com.google.android.exoplayer2.trackselection.DefaultTrackSelector$SelectionOverride r6 = (com.google.android.exoplayer2.trackselection.DefaultTrackSelector.SelectionOverride) r6
                com.google.android.exoplayer2.trackselection.MappingTrackSelector$MappedTrackInfo r7 = r0.o
                com.google.android.exoplayer2.util.Assertions.checkNotNull(r7)
                int r1 = r1.b
                if (r6 != 0) goto L_0x0054
                boolean r11 = r0.l
                if (r11 != 0) goto L_0x0046
                int r11 = r2.size()
                if (r11 <= 0) goto L_0x0046
                r2.clear()
            L_0x0046:
                com.google.android.exoplayer2.trackselection.DefaultTrackSelector$SelectionOverride r11 = new com.google.android.exoplayer2.trackselection.DefaultTrackSelector$SelectionOverride
                int[] r3 = new int[r3]
                r3[r4] = r1
                r11.<init>(r5, r3)
                r2.put(r5, r11)
                goto L_0x00da
            L_0x0054:
                android.widget.CheckedTextView r11 = (android.widget.CheckedTextView) r11
                boolean r11 = r11.isChecked()
                boolean r7 = r0.k
                if (r7 == 0) goto L_0x0074
                com.google.android.exoplayer2.source.TrackGroupArray r7 = r0.q
                com.google.android.exoplayer2.source.TrackGroup r7 = r7.get(r5)
                int r7 = r7.c
                if (r7 <= r3) goto L_0x0074
                com.google.android.exoplayer2.trackselection.MappingTrackSelector$MappedTrackInfo r7 = r0.o
                int r8 = r0.p
                int r7 = r7.getAdaptiveSupport((int) r8, (int) r5, (boolean) r4)
                if (r7 == 0) goto L_0x0074
                r7 = 1
                goto L_0x0075
            L_0x0074:
                r7 = 0
            L_0x0075:
                if (r7 != 0) goto L_0x0089
                boolean r8 = r0.l
                if (r8 == 0) goto L_0x0083
                com.google.android.exoplayer2.source.TrackGroupArray r8 = r0.q
                int r8 = r8.c
                if (r8 <= r3) goto L_0x0083
                r8 = 1
                goto L_0x0084
            L_0x0083:
                r8 = 0
            L_0x0084:
                if (r8 == 0) goto L_0x0087
                goto L_0x0089
            L_0x0087:
                r8 = 0
                goto L_0x008a
            L_0x0089:
                r8 = 1
            L_0x008a:
                int[] r9 = r6.f
                if (r11 == 0) goto L_0x00b6
                if (r8 == 0) goto L_0x00b6
                int r11 = r6.g
                if (r11 != r3) goto L_0x0098
                r2.remove(r5)
                goto L_0x00da
            L_0x0098:
                int r11 = r9.length
                int r11 = r11 + -1
                int[] r11 = new int[r11]
                int r3 = r9.length
                r6 = 0
            L_0x009f:
                if (r4 >= r3) goto L_0x00ad
                r7 = r9[r4]
                if (r7 == r1) goto L_0x00aa
                int r8 = r6 + 1
                r11[r6] = r7
                r6 = r8
            L_0x00aa:
                int r4 = r4 + 1
                goto L_0x009f
            L_0x00ad:
                com.google.android.exoplayer2.trackselection.DefaultTrackSelector$SelectionOverride r1 = new com.google.android.exoplayer2.trackselection.DefaultTrackSelector$SelectionOverride
                r1.<init>(r5, r11)
                r2.put(r5, r1)
                goto L_0x00da
            L_0x00b6:
                if (r11 != 0) goto L_0x00da
                if (r7 == 0) goto L_0x00ce
                int r11 = r9.length
                int r11 = r11 + r3
                int[] r11 = java.util.Arrays.copyOf(r9, r11)
                int r3 = r11.length
                int r3 = r3 + -1
                r11[r3] = r1
                com.google.android.exoplayer2.trackselection.DefaultTrackSelector$SelectionOverride r1 = new com.google.android.exoplayer2.trackselection.DefaultTrackSelector$SelectionOverride
                r1.<init>(r5, r11)
                r2.put(r5, r1)
                goto L_0x00da
            L_0x00ce:
                com.google.android.exoplayer2.trackselection.DefaultTrackSelector$SelectionOverride r11 = new com.google.android.exoplayer2.trackselection.DefaultTrackSelector$SelectionOverride
                int[] r3 = new int[r3]
                r3[r4] = r1
                r11.<init>(r5, r3)
                r2.put(r5, r11)
            L_0x00da:
                r0.a()
                com.google.android.exoplayer2.ui.TrackSelectionView$TrackSelectionListener r11 = r0.t
                if (r11 == 0) goto L_0x00ec
                boolean r1 = r0.getIsDisabled()
                java.util.List r0 = r0.getOverrides()
                r11.onTrackSelectionChanged(r1, r0)
            L_0x00ec:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ui.TrackSelectionView.a.onClick(android.view.View):void");
        }
    }

    public static final class b {
        public final int a;
        public final int b;
        public final Format c;

        public b(int i, int i2, Format format) {
            this.a = i;
            this.b = i2;
            this.c = format;
        }
    }

    public TrackSelectionView(Context context) {
        this(context, (AttributeSet) null);
    }

    public final void a() {
        boolean z;
        this.g.setChecked(this.r);
        boolean z2 = this.r;
        SparseArray<DefaultTrackSelector.SelectionOverride> sparseArray = this.j;
        if (z2 || sparseArray.size() != 0) {
            z = false;
        } else {
            z = true;
        }
        this.h.setChecked(z);
        for (int i2 = 0; i2 < this.n.length; i2++) {
            DefaultTrackSelector.SelectionOverride selectionOverride = sparseArray.get(i2);
            int i3 = 0;
            while (true) {
                CheckedTextView[] checkedTextViewArr = this.n[i2];
                if (i3 >= checkedTextViewArr.length) {
                    break;
                }
                if (selectionOverride != null) {
                    this.n[i2][i3].setChecked(selectionOverride.containsTrack(((b) Assertions.checkNotNull(checkedTextViewArr[i3].getTag())).b));
                } else {
                    checkedTextViewArr[i3].setChecked(false);
                }
                i3++;
            }
        }
    }

    public final void b() {
        boolean z;
        boolean z2;
        int i2;
        for (int childCount = getChildCount() - 1; childCount >= 3; childCount--) {
            removeViewAt(childCount);
        }
        MappingTrackSelector.MappedTrackInfo mappedTrackInfo = this.o;
        CheckedTextView checkedTextView = this.h;
        CheckedTextView checkedTextView2 = this.g;
        if (mappedTrackInfo == null) {
            checkedTextView2.setEnabled(false);
            checkedTextView.setEnabled(false);
            return;
        }
        checkedTextView2.setEnabled(true);
        checkedTextView.setEnabled(true);
        TrackGroupArray trackGroups = this.o.getTrackGroups(this.p);
        this.q = trackGroups;
        int i3 = trackGroups.c;
        this.n = new CheckedTextView[i3][];
        if (!this.l || i3 <= 1) {
            z = false;
        } else {
            z = true;
        }
        int i4 = 0;
        while (true) {
            TrackGroupArray trackGroupArray = this.q;
            if (i4 < trackGroupArray.c) {
                TrackGroup trackGroup = trackGroupArray.get(i4);
                if (!this.k || this.q.get(i4).c <= 1 || this.o.getAdaptiveSupport(this.p, i4, false) == 0) {
                    z2 = false;
                } else {
                    z2 = true;
                }
                CheckedTextView[][] checkedTextViewArr = this.n;
                int i5 = trackGroup.c;
                checkedTextViewArr[i4] = new CheckedTextView[i5];
                b[] bVarArr = new b[i5];
                for (int i6 = 0; i6 < trackGroup.c; i6++) {
                    bVarArr[i6] = new b(i4, i6, trackGroup.getFormat(i6));
                }
                x5 x5Var = this.s;
                if (x5Var != null) {
                    Arrays.sort(bVarArr, x5Var);
                }
                for (int i7 = 0; i7 < i5; i7++) {
                    LayoutInflater layoutInflater = this.f;
                    if (i7 == 0) {
                        addView(layoutInflater.inflate(R.layout.exo_list_divider, this, false));
                    }
                    if (z2 || z) {
                        i2 = 17367056;
                    } else {
                        i2 = 17367055;
                    }
                    CheckedTextView checkedTextView3 = (CheckedTextView) layoutInflater.inflate(i2, this, false);
                    checkedTextView3.setBackgroundResource(this.c);
                    checkedTextView3.setText(this.m.getTrackName(bVarArr[i7].c));
                    checkedTextView3.setTag(bVarArr[i7]);
                    if (this.o.getTrackSupport(this.p, i4, i7) == 4) {
                        checkedTextView3.setFocusable(true);
                        checkedTextView3.setOnClickListener(this.i);
                    } else {
                        checkedTextView3.setFocusable(false);
                        checkedTextView3.setEnabled(false);
                    }
                    this.n[i4][i7] = checkedTextView3;
                    addView(checkedTextView3);
                }
                i4++;
            } else {
                a();
                return;
            }
        }
    }

    public boolean getIsDisabled() {
        return this.r;
    }

    public List<DefaultTrackSelector.SelectionOverride> getOverrides() {
        SparseArray<DefaultTrackSelector.SelectionOverride> sparseArray = this.j;
        ArrayList arrayList = new ArrayList(sparseArray.size());
        for (int i2 = 0; i2 < sparseArray.size(); i2++) {
            arrayList.add(sparseArray.valueAt(i2));
        }
        return arrayList;
    }

    public void init(MappingTrackSelector.MappedTrackInfo mappedTrackInfo, int i2, boolean z, List<DefaultTrackSelector.SelectionOverride> list, @Nullable Comparator<Format> comparator, @Nullable TrackSelectionListener trackSelectionListener) {
        x5 x5Var;
        int i3;
        this.o = mappedTrackInfo;
        this.p = i2;
        this.r = z;
        if (comparator == null) {
            x5Var = null;
        } else {
            x5Var = new x5(1, comparator);
        }
        this.s = x5Var;
        this.t = trackSelectionListener;
        if (this.l) {
            i3 = list.size();
        } else {
            i3 = Math.min(list.size(), 1);
        }
        for (int i4 = 0; i4 < i3; i4++) {
            DefaultTrackSelector.SelectionOverride selectionOverride = list.get(i4);
            this.j.put(selectionOverride.c, selectionOverride);
        }
        b();
    }

    public void setAllowAdaptiveSelections(boolean z) {
        if (this.k != z) {
            this.k = z;
            b();
        }
    }

    public void setAllowMultipleOverrides(boolean z) {
        if (this.l != z) {
            this.l = z;
            if (!z) {
                SparseArray<DefaultTrackSelector.SelectionOverride> sparseArray = this.j;
                if (sparseArray.size() > 1) {
                    for (int size = sparseArray.size() - 1; size > 0; size--) {
                        sparseArray.remove(size);
                    }
                }
            }
            b();
        }
    }

    public void setShowDisableOption(boolean z) {
        this.g.setVisibility(z ? 0 : 8);
    }

    public void setTrackNameProvider(TrackNameProvider trackNameProvider) {
        this.m = (TrackNameProvider) Assertions.checkNotNull(trackNameProvider);
        b();
    }

    public TrackSelectionView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TrackSelectionView(Context context, @Nullable AttributeSet attributeSet, @AttrRes int i2) {
        super(context, attributeSet, i2);
        setOrientation(1);
        this.j = new SparseArray<>();
        setSaveFromParentEnabled(false);
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(new int[]{16843534});
        int resourceId = obtainStyledAttributes.getResourceId(0, 0);
        this.c = resourceId;
        obtainStyledAttributes.recycle();
        LayoutInflater from = LayoutInflater.from(context);
        this.f = from;
        a aVar = new a();
        this.i = aVar;
        this.m = new DefaultTrackNameProvider(getResources());
        this.q = TrackGroupArray.h;
        CheckedTextView checkedTextView = (CheckedTextView) from.inflate(17367055, this, false);
        this.g = checkedTextView;
        checkedTextView.setBackgroundResource(resourceId);
        checkedTextView.setText(R.string.exo_track_selection_none);
        checkedTextView.setEnabled(false);
        checkedTextView.setFocusable(true);
        checkedTextView.setOnClickListener(aVar);
        checkedTextView.setVisibility(8);
        addView(checkedTextView);
        addView(from.inflate(R.layout.exo_list_divider, this, false));
        CheckedTextView checkedTextView2 = (CheckedTextView) from.inflate(17367055, this, false);
        this.h = checkedTextView2;
        checkedTextView2.setBackgroundResource(resourceId);
        checkedTextView2.setText(R.string.exo_track_selection_auto);
        checkedTextView2.setEnabled(false);
        checkedTextView2.setFocusable(true);
        checkedTextView2.setOnClickListener(aVar);
        addView(checkedTextView2);
    }
}
