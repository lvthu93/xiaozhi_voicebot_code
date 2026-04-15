package com.google.android.exoplayer2.ui;

import android.content.Context;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.ui.TrackSelectionView;
import com.google.android.exoplayer2.util.Assertions;
import info.dourok.voicebot.R;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class TrackSelectionDialogBuilder {
    public final Context a;
    @StyleRes
    public int b;
    public final CharSequence c;
    public final MappingTrackSelector.MappedTrackInfo d;
    public final int e;
    public final DialogCallback f;
    public boolean g;
    public boolean h;
    public boolean i;
    @Nullable
    public TrackNameProvider j;
    public boolean k;
    public List<DefaultTrackSelector.SelectionOverride> l;
    @Nullable
    public Comparator<Format> m;

    public interface DialogCallback {
        void onTracksSelected(boolean z, List<DefaultTrackSelector.SelectionOverride> list);
    }

    public TrackSelectionDialogBuilder(Context context, CharSequence charSequence, MappingTrackSelector.MappedTrackInfo mappedTrackInfo, int i2, DialogCallback dialogCallback) {
        this.a = context;
        this.c = charSequence;
        this.d = mappedTrackInfo;
        this.e = i2;
        this.f = dialogCallback;
        this.l = Collections.emptyList();
    }

    public final qc a(View view) {
        TrackSelectionView trackSelectionView = (TrackSelectionView) view.findViewById(R.id.exo_track_selection_view);
        trackSelectionView.setAllowMultipleOverrides(this.h);
        trackSelectionView.setAllowAdaptiveSelections(this.g);
        trackSelectionView.setShowDisableOption(this.i);
        TrackNameProvider trackNameProvider = this.j;
        if (trackNameProvider != null) {
            trackSelectionView.setTrackNameProvider(trackNameProvider);
        }
        trackSelectionView.init(this.d, this.e, this.k, this.l, this.m, (TrackSelectionView.TrackSelectionListener) null);
        return new qc(this, trackSelectionView);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v14, resolved type: android.app.Dialog} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.app.Dialog build() {
        /*
            r17 = this;
            r1 = r17
            java.lang.CharSequence r0 = r1.c
            android.content.Context r2 = r1.a
            java.lang.Class<android.content.DialogInterface$OnClickListener> r3 = android.content.DialogInterface.OnClickListener.class
            r4 = 17039360(0x1040000, float:2.424457E-38)
            r5 = 17039370(0x104000a, float:2.42446E-38)
            r6 = 2131492907(0x7f0c002b, float:1.860928E38)
            r7 = 0
            java.lang.Class<androidx.appcompat.app.AlertDialog$Builder> r8 = androidx.appcompat.app.AlertDialog.Builder.class
            r9 = 2
            java.lang.Class[] r10 = new java.lang.Class[r9]     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            java.lang.Class<android.content.Context> r11 = android.content.Context.class
            r12 = 0
            r10[r12] = r11     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            java.lang.Class r11 = java.lang.Integer.TYPE     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            r13 = 1
            r10[r13] = r11     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            java.lang.reflect.Constructor r10 = r8.getConstructor(r10)     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            java.lang.Object[] r14 = new java.lang.Object[r9]     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            r14[r12] = r2     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            int r15 = r1.b     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            java.lang.Integer r15 = java.lang.Integer.valueOf(r15)     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            r14[r13] = r15     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            java.lang.Object r10 = r10.newInstance(r14)     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            java.lang.String r14 = "getContext"
            java.lang.Class[] r15 = new java.lang.Class[r12]     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            java.lang.reflect.Method r14 = r8.getMethod(r14, r15)     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            java.lang.Object[] r15 = new java.lang.Object[r12]     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            java.lang.Object r14 = r14.invoke(r10, r15)     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            android.content.Context r14 = (android.content.Context) r14     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            android.view.LayoutInflater r14 = android.view.LayoutInflater.from(r14)     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            android.view.View r14 = r14.inflate(r6, r7)     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            qc r15 = r1.a(r14)     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            java.lang.String r6 = "setTitle"
            java.lang.Class[] r7 = new java.lang.Class[r13]     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            java.lang.Class<java.lang.CharSequence> r16 = java.lang.CharSequence.class
            r7[r12] = r16     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            java.lang.reflect.Method r6 = r8.getMethod(r6, r7)     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            java.lang.Object[] r7 = new java.lang.Object[r13]     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            r7[r12] = r0     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            r6.invoke(r10, r7)     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            java.lang.String r6 = "setView"
            java.lang.Class[] r7 = new java.lang.Class[r13]     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            java.lang.Class<android.view.View> r16 = android.view.View.class
            r7[r12] = r16     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            java.lang.reflect.Method r6 = r8.getMethod(r6, r7)     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            java.lang.Object[] r7 = new java.lang.Object[r13]     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            r7[r12] = r14     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            r6.invoke(r10, r7)     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            java.lang.String r6 = "setPositiveButton"
            java.lang.Class[] r7 = new java.lang.Class[r9]     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            r7[r12] = r11     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            r7[r13] = r3     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            java.lang.reflect.Method r6 = r8.getMethod(r6, r7)     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            java.lang.Object[] r7 = new java.lang.Object[r9]     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            java.lang.Integer r14 = java.lang.Integer.valueOf(r5)     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            r7[r12] = r14     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            r7[r13] = r15     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            r6.invoke(r10, r7)     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            java.lang.String r6 = "setNegativeButton"
            java.lang.Class[] r7 = new java.lang.Class[r9]     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            r7[r12] = r11     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            r7[r13] = r3     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            java.lang.reflect.Method r3 = r8.getMethod(r6, r7)     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            java.lang.Object[] r6 = new java.lang.Object[r9]     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            java.lang.Integer r7 = java.lang.Integer.valueOf(r4)     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            r6[r12] = r7     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            r7 = 0
            r6[r13] = r7     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            r3.invoke(r10, r6)     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            java.lang.String r3 = "create"
            java.lang.Class[] r6 = new java.lang.Class[r12]     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            java.lang.reflect.Method r3 = r8.getMethod(r3, r6)     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            java.lang.Object[] r6 = new java.lang.Object[r12]     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            java.lang.Object r3 = r3.invoke(r10, r6)     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            r7 = r3
            android.app.Dialog r7 = (android.app.Dialog) r7     // Catch:{ ClassNotFoundException -> 0x00c2, Exception -> 0x00bb }
            goto L_0x00c4
        L_0x00bb:
            r0 = move-exception
            java.lang.IllegalStateException r2 = new java.lang.IllegalStateException
            r2.<init>(r0)
            throw r2
        L_0x00c2:
            r7 = 0
        L_0x00c4:
            if (r7 != 0) goto L_0x00f5
            android.app.AlertDialog$Builder r3 = new android.app.AlertDialog$Builder
            int r6 = r1.b
            r3.<init>(r2, r6)
            android.content.Context r2 = r3.getContext()
            android.view.LayoutInflater r2 = android.view.LayoutInflater.from(r2)
            r6 = 2131492907(0x7f0c002b, float:1.860928E38)
            r7 = 0
            android.view.View r2 = r2.inflate(r6, r7)
            qc r6 = r1.a(r2)
            android.app.AlertDialog$Builder r0 = r3.setTitle(r0)
            android.app.AlertDialog$Builder r0 = r0.setView(r2)
            android.app.AlertDialog$Builder r0 = r0.setPositiveButton(r5, r6)
            android.app.AlertDialog$Builder r0 = r0.setNegativeButton(r4, r7)
            android.app.AlertDialog r7 = r0.create()
        L_0x00f5:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ui.TrackSelectionDialogBuilder.build():android.app.Dialog");
    }

    public TrackSelectionDialogBuilder setAllowAdaptiveSelections(boolean z) {
        this.g = z;
        return this;
    }

    public TrackSelectionDialogBuilder setAllowMultipleOverrides(boolean z) {
        this.h = z;
        return this;
    }

    public TrackSelectionDialogBuilder setIsDisabled(boolean z) {
        this.k = z;
        return this;
    }

    public TrackSelectionDialogBuilder setOverride(@Nullable DefaultTrackSelector.SelectionOverride selectionOverride) {
        List list;
        if (selectionOverride == null) {
            list = Collections.emptyList();
        } else {
            list = Collections.singletonList(selectionOverride);
        }
        return setOverrides(list);
    }

    public TrackSelectionDialogBuilder setOverrides(List<DefaultTrackSelector.SelectionOverride> list) {
        this.l = list;
        return this;
    }

    public TrackSelectionDialogBuilder setShowDisableOption(boolean z) {
        this.i = z;
        return this;
    }

    public TrackSelectionDialogBuilder setTheme(@StyleRes int i2) {
        this.b = i2;
        return this;
    }

    public void setTrackFormatComparator(@Nullable Comparator<Format> comparator) {
        this.m = comparator;
    }

    public TrackSelectionDialogBuilder setTrackNameProvider(@Nullable TrackNameProvider trackNameProvider) {
        this.j = trackNameProvider;
        return this;
    }

    public TrackSelectionDialogBuilder(Context context, CharSequence charSequence, DefaultTrackSelector defaultTrackSelector, int i2) {
        this.a = context;
        this.c = charSequence;
        MappingTrackSelector.MappedTrackInfo mappedTrackInfo = (MappingTrackSelector.MappedTrackInfo) Assertions.checkNotNull(defaultTrackSelector.getCurrentMappedTrackInfo());
        this.d = mappedTrackInfo;
        this.e = i2;
        TrackGroupArray trackGroups = mappedTrackInfo.getTrackGroups(i2);
        DefaultTrackSelector.Parameters parameters = defaultTrackSelector.getParameters();
        this.k = parameters.getRendererDisabled(i2);
        DefaultTrackSelector.SelectionOverride selectionOverride = parameters.getSelectionOverride(i2, trackGroups);
        this.l = selectionOverride == null ? Collections.emptyList() : Collections.singletonList(selectionOverride);
        this.f = new rc(defaultTrackSelector, parameters, i2, trackGroups);
    }
}
