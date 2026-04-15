package com.google.android.exoplayer2.upstream;

import android.content.Context;
import android.os.Handler;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.NetworkTypeObserver;
import com.google.android.exoplayer2.util.SlidingPercentile;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Ascii;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;

public final class DefaultBandwidthMeter implements BandwidthMeter, TransferListener {
    public static final ImmutableListMultimap<String, Integer> p = a();
    public static final ImmutableList<Long> q = ImmutableList.of(6200000L, 3900000L, 2300000L, 1300000L, 620000L);
    public static final ImmutableList<Long> r = ImmutableList.of(248000L, 160000L, 142000L, 127000L, 113000L);
    public static final ImmutableList<Long> s = ImmutableList.of(2200000L, 1300000L, 950000L, 760000L, 520000L);
    public static final ImmutableList<Long> t = ImmutableList.of(4400000L, 2300000L, 1500000L, 1100000L, 640000L);
    public static final ImmutableList<Long> u = ImmutableList.of(10000000L, 7200000L, 5000000L, 2700000L, 1600000L);
    public static final ImmutableList<Long> v = ImmutableList.of(2600000L, 2200000L, 2000000L, 1500000L, 470000L);
    @Nullable
    public static DefaultBandwidthMeter w;
    public final ImmutableMap<Integer, Long> a;
    public final BandwidthMeter.EventListener.EventDispatcher b;
    public final SlidingPercentile c;
    public final Clock d;
    public final boolean e;
    public int f;
    public long g;
    public long h;
    public int i;
    public long j;
    public long k;
    public long l;
    public long m;
    public boolean n;
    public int o;

    @Deprecated
    public DefaultBandwidthMeter() {
        this((Context) null, ImmutableMap.of(), 2000, Clock.a, false);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v0, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v0, resolved type: java.lang.Integer} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v1, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v2, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v3, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v4, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v5, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v6, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v7, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v8, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v9, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v10, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v11, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v12, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v13, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v14, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v15, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v16, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v17, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v18, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v19, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v20, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v21, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v22, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v23, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v24, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v25, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v26, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v27, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v28, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v29, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v30, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v31, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v32, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v33, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v34, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v35, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v36, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v37, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v38, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v39, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v40, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v41, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v42, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v43, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v44, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v45, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v46, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v47, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v48, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v49, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v50, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v51, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v52, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v53, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v54, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v55, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v56, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v57, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v58, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v59, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v60, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v61, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v62, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v63, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v64, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v65, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v66, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v67, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v68, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v69, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v70, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v71, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v72, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v73, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v74, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v75, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v76, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v77, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v78, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v79, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v80, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v81, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v82, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v83, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v84, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v85, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v86, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v87, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v88, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v89, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v90, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v91, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v92, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v93, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v94, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v95, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v96, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v97, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v98, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v99, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v100, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v101, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v102, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v103, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v104, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v105, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v106, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v107, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v108, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v109, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v110, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v111, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v112, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v113, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v114, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v115, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v116, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v117, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v118, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v119, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v120, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v121, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v122, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v123, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v124, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v125, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v126, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v127, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v128, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v129, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v130, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v131, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v132, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v133, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v134, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v135, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v136, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v137, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v138, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v139, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v140, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v141, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v142, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v143, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v144, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v145, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v146, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v147, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v148, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v149, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v150, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v151, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v152, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v153, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v154, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v155, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v156, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v157, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v158, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v159, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v160, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v161, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v162, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v163, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v164, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v165, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v166, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v167, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v168, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v169, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v170, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v171, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v172, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v173, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v174, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v175, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v176, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v177, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v178, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v179, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v180, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v181, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v182, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v183, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v184, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v185, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v186, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v187, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v188, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v189, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v190, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v191, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v192, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v193, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v194, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v195, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v196, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v197, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v198, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v199, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v200, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v201, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v202, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v203, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v204, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v205, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v206, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v207, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v208, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v209, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v210, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v211, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v212, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v213, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v214, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v215, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v216, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v217, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v219, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v220, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v221, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v222, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v223, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v224, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v225, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v226, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v227, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v228, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v229, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v230, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v231, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v232, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v233, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v234, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v235, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v236, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v237, resolved type: java.lang.Integer[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v1, resolved type: java.lang.Integer[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.common.collect.ImmutableListMultimap<java.lang.String, java.lang.Integer> a() {
        /*
            com.google.common.collect.ImmutableListMultimap$Builder r0 = com.google.common.collect.ImmutableListMultimap.builder()
            r1 = 6
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r3 = 1
            java.lang.Integer r4 = java.lang.Integer.valueOf(r3)
            r5 = 0
            java.lang.Integer r6 = java.lang.Integer.valueOf(r5)
            r2[r5] = r4
            r7 = 2
            java.lang.Integer r8 = java.lang.Integer.valueOf(r7)
            r2[r3] = r8
            r2[r7] = r6
            r9 = 3
            java.lang.Integer r10 = java.lang.Integer.valueOf(r9)
            r2[r9] = r6
            r11 = 4
            java.lang.Integer r12 = java.lang.Integer.valueOf(r11)
            r2[r11] = r8
            r13 = 5
            r2[r13] = r8
            java.lang.String r14 = "AD"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r4
            r2[r3] = r12
            r2[r7] = r12
            r2[r9] = r12
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "AE"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r12
            r2[r3] = r12
            r2[r7] = r10
            r2[r9] = r12
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "AF"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r12
            r2[r3] = r8
            r2[r7] = r4
            r2[r9] = r12
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "AG"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r4
            r2[r3] = r8
            r2[r7] = r8
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "AI"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r4
            r2[r3] = r4
            r2[r7] = r4
            r2[r9] = r4
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "AL"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r8
            r2[r3] = r8
            r2[r7] = r4
            r2[r9] = r10
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "AM"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r10
            r2[r3] = r12
            r2[r7] = r10
            r2[r9] = r4
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "AO"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r8
            r2[r3] = r12
            r2[r7] = r8
            r2[r9] = r4
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "AR"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r8
            r2[r3] = r8
            r2[r7] = r10
            r2[r9] = r10
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "AS"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r6
            r2[r3] = r4
            r2[r7] = r6
            r2[r9] = r6
            r2[r11] = r6
            r2[r13] = r8
            java.lang.String r14 = "AT"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r6
            r2[r3] = r8
            r2[r7] = r6
            r2[r9] = r4
            r2[r11] = r4
            r2[r13] = r8
            java.lang.String r14 = "AU"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r4
            r2[r3] = r8
            r2[r7] = r6
            r2[r9] = r12
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "AW"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r6
            r2[r3] = r8
            r2[r7] = r8
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "AX"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r10
            r2[r3] = r10
            r2[r7] = r10
            r2[r9] = r12
            r2[r11] = r12
            r2[r13] = r8
            java.lang.String r14 = "AZ"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r4
            r2[r3] = r4
            r2[r7] = r6
            r2[r9] = r4
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "BA"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r6
            r2[r3] = r8
            r2[r7] = r6
            r2[r9] = r6
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "BB"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r8
            r2[r3] = r6
            r2[r7] = r10
            r2[r9] = r10
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "BD"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r6
            r2[r3] = r6
            r2[r7] = r8
            r2[r9] = r10
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "BE"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r12
            r2[r3] = r12
            r2[r7] = r12
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "BF"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r6
            r2[r3] = r4
            r2[r7] = r6
            r2[r9] = r6
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "BG"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r4
            r2[r3] = r6
            r2[r7] = r8
            r2[r9] = r12
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "BH"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r12
            r2[r3] = r12
            r2[r7] = r12
            r2[r9] = r12
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "BI"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r12
            r2[r3] = r12
            r2[r7] = r12
            r2[r9] = r12
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "BJ"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r4
            r2[r3] = r8
            r2[r7] = r8
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "BL"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r6
            r2[r3] = r8
            r2[r7] = r6
            r2[r9] = r6
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "BM"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r10
            r2[r3] = r8
            r2[r7] = r4
            r2[r9] = r6
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "BN"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r4
            r2[r3] = r8
            r2[r7] = r12
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "BO"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r4
            r2[r3] = r8
            r2[r7] = r4
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "BQ"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r8
            r2[r3] = r12
            r2[r7] = r10
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "BR"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r8
            r2[r3] = r8
            r2[r7] = r4
            r2[r9] = r10
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "BS"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r10
            r2[r3] = r6
            r2[r7] = r10
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "BT"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r10
            r2[r3] = r12
            r2[r7] = r4
            r2[r9] = r4
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "BW"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r4
            r2[r3] = r4
            r2[r7] = r4
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "BY"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r8
            r2[r3] = r8
            r2[r7] = r8
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "BZ"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r6
            r2[r3] = r10
            r2[r7] = r4
            r2[r9] = r8
            r2[r11] = r12
            r2[r13] = r8
            java.lang.String r14 = "CA"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r12
            r2[r3] = r8
            r2[r7] = r8
            r2[r9] = r4
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "CD"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r12
            r2[r3] = r8
            r2[r7] = r10
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "CF"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r10
            r2[r3] = r12
            r2[r7] = r8
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "CG"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r6
            r2[r3] = r6
            r2[r7] = r6
            r2[r9] = r6
            r2[r11] = r4
            r2[r13] = r8
            java.lang.String r14 = "CH"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r10
            r2[r3] = r10
            r2[r7] = r10
            r2[r9] = r10
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "CI"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r8
            r2[r3] = r8
            r2[r7] = r10
            r2[r9] = r6
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "CK"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r4
            r2[r3] = r4
            r2[r7] = r8
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "CL"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r10
            r2[r3] = r12
            r2[r7] = r10
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "CM"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r8
            r2[r3] = r8
            r2[r7] = r8
            r2[r9] = r4
            r2[r11] = r10
            r2[r13] = r8
            java.lang.String r14 = "CN"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r8
            r2[r3] = r10
            r2[r7] = r12
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "CO"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r8
            r2[r3] = r10
            r2[r7] = r12
            r2[r9] = r12
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "CR"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r12
            r2[r3] = r12
            r2[r7] = r8
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "CU"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r8
            r2[r3] = r10
            r2[r7] = r4
            r2[r9] = r6
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "CV"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r4
            r2[r3] = r8
            r2[r7] = r6
            r2[r9] = r6
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "CW"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r4
            r2[r3] = r4
            r2[r7] = r6
            r2[r9] = r6
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "CY"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r6
            r2[r3] = r4
            r2[r7] = r6
            r2[r9] = r6
            r2[r11] = r4
            r2[r13] = r8
            java.lang.String r14 = "CZ"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r6
            r2[r3] = r6
            r2[r7] = r4
            r2[r9] = r4
            r2[r11] = r6
            r2[r13] = r8
            java.lang.String r14 = "DE"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r12
            r2[r3] = r6
            r2[r7] = r12
            r2[r9] = r12
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "DJ"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r6
            r2[r3] = r6
            r2[r7] = r4
            r2[r9] = r6
            r2[r11] = r6
            r2[r13] = r8
            java.lang.String r14 = "DK"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r4
            r2[r3] = r8
            r2[r7] = r8
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "DM"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r10
            r2[r3] = r12
            r2[r7] = r12
            r2[r9] = r12
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "DO"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r10
            r2[r3] = r10
            r2[r7] = r12
            r2[r9] = r12
            r2[r11] = r8
            r2[r13] = r12
            java.lang.String r14 = "DZ"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r8
            r2[r3] = r12
            r2[r7] = r10
            r2[r9] = r4
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "EC"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r6
            r2[r3] = r4
            r2[r7] = r6
            r2[r9] = r6
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "EE"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r10
            r2[r3] = r12
            r2[r7] = r10
            r2[r9] = r10
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "EG"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r8
            r2[r3] = r8
            r2[r7] = r8
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "EH"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r12
            r2[r3] = r8
            r2[r7] = r8
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "ER"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r6
            r2[r3] = r4
            r2[r7] = r4
            r2[r9] = r4
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "ES"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r12
            r2[r3] = r12
            r2[r7] = r12
            r2[r9] = r4
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "ET"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r6
            r2[r3] = r6
            r2[r7] = r6
            r2[r9] = r6
            r2[r11] = r6
            r2[r13] = r8
            java.lang.String r14 = "FI"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r10
            r2[r3] = r6
            r2[r7] = r8
            r2[r9] = r10
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "FJ"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r12
            r2[r3] = r8
            r2[r7] = r8
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "FK"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r10
            r2[r3] = r8
            r2[r7] = r12
            r2[r9] = r12
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "FM"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r4
            r2[r3] = r8
            r2[r7] = r6
            r2[r9] = r4
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "FO"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r4
            r2[r3] = r4
            r2[r7] = r8
            r2[r9] = r6
            r2[r11] = r4
            r2[r13] = r8
            java.lang.String r14 = "FR"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r10
            r2[r3] = r12
            r2[r7] = r4
            r2[r9] = r4
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "GA"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r6
            r2[r3] = r6
            r2[r7] = r4
            r2[r9] = r4
            r2[r11] = r4
            r2[r13] = r8
            java.lang.String r14 = "GB"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r4
            r2[r3] = r8
            r2[r7] = r8
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "GD"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r4
            r2[r3] = r4
            r2[r7] = r4
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "GE"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r8
            r2[r3] = r8
            r2[r7] = r8
            r2[r9] = r10
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "GF"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r4
            r2[r3] = r8
            r2[r7] = r6
            r2[r9] = r6
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "GG"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r10
            r2[r3] = r4
            r2[r7] = r10
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "GH"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r6
            r2[r3] = r8
            r2[r7] = r6
            r2[r9] = r6
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "GI"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r4
            r2[r3] = r8
            r2[r7] = r6
            r2[r9] = r6
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "GL"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r12
            r2[r3] = r10
            r2[r7] = r8
            r2[r9] = r12
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "GM"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r12
            r2[r3] = r10
            r2[r7] = r12
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "GN"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r8
            r2[r3] = r4
            r2[r7] = r8
            r2[r9] = r10
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "GP"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r12
            r2[r3] = r8
            r2[r7] = r8
            r2[r9] = r12
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "GQ"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r4
            r2[r3] = r8
            r2[r7] = r6
            r2[r9] = r6
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "GR"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r10
            r2[r3] = r8
            r2[r7] = r10
            r2[r9] = r4
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "GT"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r4
            r2[r3] = r8
            r2[r7] = r10
            r2[r9] = r12
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "GU"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r12
            r2[r3] = r12
            r2[r7] = r12
            r2[r9] = r12
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "GW"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r10
            r2[r3] = r10
            r2[r7] = r10
            r2[r9] = r12
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "GY"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r6
            r2[r3] = r4
            r2[r7] = r8
            r2[r9] = r10
            r2[r11] = r8
            r2[r13] = r6
            java.lang.String r14 = "HK"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r10
            r2[r3] = r4
            r2[r7] = r10
            r2[r9] = r10
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "HN"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r4
            r2[r3] = r4
            r2[r7] = r6
            r2[r9] = r6
            r2[r11] = r10
            r2[r13] = r8
            java.lang.String r14 = "HR"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r12
            r2[r3] = r12
            r2[r7] = r12
            r2[r9] = r12
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "HT"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r6
            r2[r3] = r6
            r2[r7] = r6
            r2[r9] = r6
            r2[r11] = r6
            r2[r13] = r8
            java.lang.String r14 = "HU"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r10
            r2[r3] = r8
            r2[r7] = r10
            r2[r9] = r10
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "ID"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r6
            r2[r3] = r6
            r2[r7] = r4
            r2[r9] = r4
            r2[r11] = r10
            r2[r13] = r8
            java.lang.String r14 = "IE"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r4
            r2[r3] = r6
            r2[r7] = r8
            r2[r9] = r10
            r2[r11] = r12
            r2[r13] = r8
            java.lang.String r14 = "IL"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r6
            r2[r3] = r8
            r2[r7] = r6
            r2[r9] = r4
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "IM"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r8
            r2[r3] = r4
            r2[r7] = r10
            r2[r9] = r10
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "IN"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r12
            r2[r3] = r8
            r2[r7] = r8
            r2[r9] = r12
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "IO"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r10
            r2[r3] = r10
            r2[r7] = r12
            r2[r9] = r12
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "IQ"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r10
            r2[r3] = r8
            r2[r7] = r10
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "IR"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r6
            r2[r3] = r8
            r2[r7] = r6
            r2[r9] = r6
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "IS"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r6
            r2[r3] = r12
            r2[r7] = r6
            r2[r9] = r4
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "IT"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r8
            r2[r3] = r8
            r2[r7] = r4
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "JE"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r10
            r2[r3] = r10
            r2[r7] = r12
            r2[r9] = r12
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "JM"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r8
            r2[r3] = r8
            r2[r7] = r4
            r2[r9] = r4
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "JO"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r6
            r2[r3] = r6
            r2[r7] = r6
            r2[r9] = r6
            r2[r11] = r8
            r2[r13] = r4
            java.lang.String r14 = "JP"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r10
            r2[r3] = r12
            r2[r7] = r8
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "KE"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r8
            r2[r3] = r6
            r2[r7] = r4
            r2[r9] = r4
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "KG"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r4
            r2[r3] = r6
            r2[r7] = r12
            r2[r9] = r10
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "KH"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r12
            r2[r3] = r8
            r2[r7] = r12
            r2[r9] = r10
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "KI"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r12
            r2[r3] = r10
            r2[r7] = r8
            r2[r9] = r10
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "KM"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r4
            r2[r3] = r8
            r2[r7] = r8
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "KN"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r12
            r2[r3] = r8
            r2[r7] = r8
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "KP"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r6
            r2[r3] = r6
            r2[r7] = r4
            r2[r9] = r10
            r2[r11] = r4
            r2[r13] = r8
            java.lang.String r14 = "KR"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r4
            r2[r3] = r10
            r2[r7] = r4
            r2[r9] = r4
            r2[r11] = r4
            r2[r13] = r8
            java.lang.String r14 = "KW"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r4
            r2[r3] = r8
            r2[r7] = r6
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "KY"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r8
            r2[r3] = r8
            r2[r7] = r8
            r2[r9] = r10
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "KZ"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r4
            r2[r3] = r8
            r2[r7] = r4
            r2[r9] = r4
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "LA"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r10
            r2[r3] = r8
            r2[r7] = r6
            r2[r9] = r6
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "LB"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r4
            r2[r3] = r8
            r2[r7] = r6
            r2[r9] = r6
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "LC"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r6
            r2[r3] = r8
            r2[r7] = r8
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "LI"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r8
            r2[r3] = r6
            r2[r7] = r8
            r2[r9] = r10
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "LK"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r10
            r2[r3] = r12
            r2[r7] = r12
            r2[r9] = r10
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "LR"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r10
            r2[r3] = r10
            r2[r7] = r8
            r2[r9] = r10
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "LS"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r6
            r2[r3] = r6
            r2[r7] = r6
            r2[r9] = r6
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "LT"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r4
            r2[r3] = r6
            r2[r7] = r4
            r2[r9] = r4
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "LU"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r6
            r2[r3] = r6
            r2[r7] = r6
            r2[r9] = r6
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "LV"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r12
            r2[r3] = r8
            r2[r7] = r12
            r2[r9] = r10
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "LY"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r10
            r2[r3] = r8
            r2[r7] = r8
            r2[r9] = r4
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "MA"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r6
            r2[r3] = r8
            r2[r7] = r6
            r2[r9] = r6
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "MC"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r4
            r2[r3] = r8
            r2[r7] = r6
            r2[r9] = r6
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "MD"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r4
            r2[r3] = r8
            r2[r7] = r6
            r2[r9] = r4
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "ME"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r8
            r2[r3] = r8
            r2[r7] = r4
            r2[r9] = r4
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "MF"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r10
            r2[r3] = r12
            r2[r7] = r8
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "MG"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r12
            r2[r3] = r8
            r2[r7] = r8
            r2[r9] = r12
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "MH"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r4
            r2[r3] = r4
            r2[r7] = r6
            r2[r9] = r6
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "MK"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r12
            r2[r3] = r12
            r2[r7] = r8
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "ML"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r8
            r2[r3] = r10
            r2[r7] = r10
            r2[r9] = r10
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "MM"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r8
            r2[r3] = r12
            r2[r7] = r8
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "MN"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r6
            r2[r3] = r8
            r2[r7] = r12
            r2[r9] = r12
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "MO"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r6
            r2[r3] = r8
            r2[r7] = r8
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "MP"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r8
            r2[r3] = r8
            r2[r7] = r8
            r2[r9] = r10
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "MQ"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r10
            r2[r3] = r6
            r2[r7] = r12
            r2[r9] = r10
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "MR"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r4
            r2[r3] = r8
            r2[r7] = r8
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "MS"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r6
            r2[r3] = r8
            r2[r7] = r6
            r2[r9] = r6
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "MT"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r8
            r2[r3] = r4
            r2[r7] = r4
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "MU"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r12
            r2[r3] = r10
            r2[r7] = r8
            r2[r9] = r12
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "MV"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r12
            r2[r3] = r8
            r2[r7] = r4
            r2[r9] = r6
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "MW"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r8
            r2[r3] = r12
            r2[r7] = r12
            r2[r9] = r12
            r2[r11] = r12
            r2[r13] = r8
            java.lang.String r14 = "MX"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r4
            r2[r3] = r6
            r2[r7] = r10
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "MY"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r10
            r2[r3] = r10
            r2[r7] = r8
            r2[r9] = r4
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "MZ"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r12
            r2[r3] = r10
            r2[r7] = r10
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "NA"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r10
            r2[r3] = r6
            r2[r7] = r12
            r2[r9] = r12
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "NC"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r12
            r2[r3] = r12
            r2[r7] = r12
            r2[r9] = r12
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "NE"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r8
            r2[r3] = r8
            r2[r7] = r8
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "NF"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r10
            r2[r3] = r10
            r2[r7] = r8
            r2[r9] = r10
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "NG"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r8
            r2[r3] = r4
            r2[r7] = r12
            r2[r9] = r12
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "NI"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r6
            r2[r3] = r8
            r2[r7] = r10
            r2[r9] = r8
            r2[r11] = r6
            r2[r13] = r8
            java.lang.String r14 = "NL"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r6
            r2[r3] = r4
            r2[r7] = r8
            r2[r9] = r6
            r2[r11] = r6
            r2[r13] = r8
            java.lang.String r14 = "NO"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r8
            r2[r3] = r6
            r2[r7] = r12
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "NP"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r10
            r2[r3] = r8
            r2[r7] = r10
            r2[r9] = r4
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "NR"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r12
            r2[r3] = r8
            r2[r7] = r8
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "NU"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r6
            r2[r3] = r8
            r2[r7] = r4
            r2[r9] = r8
            r2[r11] = r12
            r2[r13] = r8
            java.lang.String r14 = "NZ"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r8
            r2[r3] = r8
            r2[r7] = r4
            r2[r9] = r10
            r2[r11] = r10
            r2[r13] = r8
            java.lang.String r14 = "OM"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r4
            r2[r3] = r10
            r2[r7] = r10
            r2[r9] = r10
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "PA"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r8
            r2[r3] = r10
            r2[r7] = r12
            r2[r9] = r12
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "PE"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r8
            r2[r3] = r8
            r2[r7] = r8
            r2[r9] = r4
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "PF"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r12
            r2[r3] = r12
            r2[r7] = r10
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "PG"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r8
            r2[r3] = r4
            r2[r7] = r10
            r2[r9] = r10
            r2[r11] = r10
            r2[r13] = r8
            java.lang.String r14 = "PH"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r10
            r2[r3] = r8
            r2[r7] = r10
            r2[r9] = r10
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "PK"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r4
            r2[r3] = r6
            r2[r7] = r4
            r2[r9] = r8
            r2[r11] = r10
            r2[r13] = r8
            java.lang.String r14 = "PL"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r6
            r2[r3] = r8
            r2[r7] = r8
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "PM"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r8
            r2[r3] = r4
            r2[r7] = r8
            r2[r9] = r8
            r2[r11] = r12
            r2[r13] = r10
            java.lang.String r14 = "PR"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r10
            r2[r3] = r10
            r2[r7] = r8
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "PS"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r6
            r2[r3] = r4
            r2[r7] = r4
            r2[r9] = r6
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "PT"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r4
            r2[r3] = r8
            r2[r7] = r12
            r2[r9] = r4
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "PW"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r8
            r2[r3] = r6
            r2[r7] = r10
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "PY"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r8
            r2[r3] = r10
            r2[r7] = r4
            r2[r9] = r8
            r2[r11] = r10
            r2[r13] = r8
            java.lang.String r14 = "QA"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r4
            r2[r3] = r6
            r2[r7] = r8
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "RE"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r6
            r2[r3] = r4
            r2[r7] = r6
            r2[r9] = r4
            r2[r11] = r6
            r2[r13] = r8
            java.lang.String r14 = "RO"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r4
            r2[r3] = r8
            r2[r7] = r6
            r2[r9] = r6
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "RS"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r6
            r2[r3] = r4
            r2[r7] = r6
            r2[r9] = r4
            r2[r11] = r12
            r2[r13] = r8
            java.lang.String r14 = "RU"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r10
            r2[r3] = r10
            r2[r7] = r10
            r2[r9] = r4
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "RW"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r8
            r2[r3] = r8
            r2[r7] = r8
            r2[r9] = r4
            r2[r11] = r4
            r2[r13] = r8
            java.lang.String r14 = "SA"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r12
            r2[r3] = r8
            r2[r7] = r10
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "SB"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r12
            r2[r3] = r8
            r2[r7] = r4
            r2[r9] = r10
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "SC"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r12
            r2[r3] = r12
            r2[r7] = r12
            r2[r9] = r12
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "SD"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r6
            r2[r3] = r6
            r2[r7] = r6
            r2[r9] = r6
            r2[r11] = r6
            r2[r13] = r8
            java.lang.String r14 = "SE"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r4
            r2[r3] = r6
            r2[r7] = r4
            r2[r9] = r8
            r2[r11] = r10
            r2[r13] = r8
            java.lang.String r14 = "SG"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r12
            r2[r3] = r8
            r2[r7] = r8
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "SH"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r6
            r2[r3] = r6
            r2[r7] = r6
            r2[r9] = r6
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "SI"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r8
            r2[r3] = r8
            r2[r7] = r8
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "SJ"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r6
            r2[r3] = r4
            r2[r7] = r6
            r2[r9] = r6
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "SK"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r12
            r2[r3] = r10
            r2[r7] = r12
            r2[r9] = r6
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "SL"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r6
            r2[r3] = r8
            r2[r7] = r8
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "SM"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r12
            r2[r3] = r12
            r2[r7] = r12
            r2[r9] = r12
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "SN"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r10
            r2[r3] = r10
            r2[r7] = r10
            r2[r9] = r12
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "SO"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r10
            r2[r3] = r8
            r2[r7] = r8
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "SR"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r12
            r2[r3] = r12
            r2[r7] = r10
            r2[r9] = r10
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "SS"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r8
            r2[r3] = r8
            r2[r7] = r4
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "ST"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r8
            r2[r3] = r4
            r2[r7] = r12
            r2[r9] = r10
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "SV"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r8
            r2[r3] = r8
            r2[r7] = r4
            r2[r9] = r6
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "SX"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r12
            r2[r3] = r10
            r2[r7] = r10
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "SY"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r10
            r2[r3] = r10
            r2[r7] = r8
            r2[r9] = r12
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "SZ"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r8
            r2[r3] = r8
            r2[r7] = r8
            r2[r9] = r6
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "TC"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r12
            r2[r3] = r10
            r2[r7] = r12
            r2[r9] = r12
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "TD"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r10
            r2[r3] = r8
            r2[r7] = r8
            r2[r9] = r12
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "TG"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r6
            r2[r3] = r10
            r2[r7] = r8
            r2[r9] = r10
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "TH"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r12
            r2[r3] = r12
            r2[r7] = r12
            r2[r9] = r12
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "TJ"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r12
            r2[r3] = r6
            r2[r7] = r12
            r2[r9] = r12
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "TL"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r12
            r2[r3] = r8
            r2[r7] = r12
            r2[r9] = r10
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "TM"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r8
            r2[r3] = r4
            r2[r7] = r4
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "TN"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r10
            r2[r3] = r10
            r2[r7] = r12
            r2[r9] = r10
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "TO"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r4
            r2[r3] = r8
            r2[r7] = r4
            r2[r9] = r4
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "TR"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r4
            r2[r3] = r12
            r2[r7] = r6
            r2[r9] = r4
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "TT"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r10
            r2[r3] = r8
            r2[r7] = r8
            r2[r9] = r12
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "TV"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r6
            r2[r3] = r6
            r2[r7] = r6
            r2[r9] = r6
            r2[r11] = r4
            r2[r13] = r6
            java.lang.String r14 = "TW"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r10
            r2[r3] = r10
            r2[r7] = r10
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "TZ"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r6
            r2[r3] = r10
            r2[r7] = r4
            r2[r9] = r4
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "UA"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r10
            r2[r3] = r8
            r2[r7] = r10
            r2[r9] = r10
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "UG"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r4
            r2[r3] = r4
            r2[r7] = r8
            r2[r9] = r8
            r2[r11] = r12
            r2[r13] = r8
            java.lang.String r14 = "US"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r8
            r2[r3] = r8
            r2[r7] = r4
            r2[r9] = r4
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "UY"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r8
            r2[r3] = r4
            r2[r7] = r10
            r2[r9] = r12
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "UZ"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r4
            r2[r3] = r8
            r2[r7] = r8
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "VC"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r12
            r2[r3] = r12
            r2[r7] = r12
            r2[r9] = r12
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "VE"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r8
            r2[r3] = r8
            r2[r7] = r4
            r2[r9] = r4
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "VG"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r4
            r2[r3] = r8
            r2[r7] = r4
            r2[r9] = r8
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "VI"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r6
            r2[r3] = r4
            r2[r7] = r10
            r2[r9] = r12
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "VN"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r12
            r2[r3] = r6
            r2[r7] = r10
            r2[r9] = r4
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "VU"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r12
            r2[r3] = r8
            r2[r7] = r8
            r2[r9] = r12
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "WF"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r10
            r2[r3] = r4
            r2[r7] = r10
            r2[r9] = r4
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r14 = "WS"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r14, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r6
            r2[r3] = r4
            r2[r7] = r4
            r2[r9] = r6
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r6 = "XK"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r6, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r12
            r2[r3] = r12
            r2[r7] = r12
            r2[r9] = r10
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r6 = "YE"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r6, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r12
            r2[r3] = r8
            r2[r7] = r8
            r2[r9] = r10
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r6 = "YT"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r6, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r10
            r2[r3] = r10
            r2[r7] = r8
            r2[r9] = r4
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r4 = "ZA"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r4, (V[]) r2)
            java.lang.Integer[] r2 = new java.lang.Integer[r1]
            r2[r5] = r10
            r2[r3] = r8
            r2[r7] = r10
            r2[r9] = r10
            r2[r11] = r8
            r2[r13] = r8
            java.lang.String r4 = "ZM"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r4, (V[]) r2)
            java.lang.Integer[] r1 = new java.lang.Integer[r1]
            r1[r5] = r10
            r1[r3] = r8
            r1[r7] = r12
            r1[r9] = r10
            r1[r11] = r8
            r1[r13] = r8
            java.lang.String r2 = "ZW"
            com.google.common.collect.ImmutableListMultimap$Builder r0 = r0.putAll(r2, (V[]) r1)
            com.google.common.collect.ImmutableListMultimap r0 = r0.build()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.DefaultBandwidthMeter.a():com.google.common.collect.ImmutableListMultimap");
    }

    public static synchronized DefaultBandwidthMeter getSingletonInstance(Context context) {
        DefaultBandwidthMeter defaultBandwidthMeter;
        synchronized (DefaultBandwidthMeter.class) {
            if (w == null) {
                w = new Builder(context).build();
            }
            defaultBandwidthMeter = w;
        }
        return defaultBandwidthMeter;
    }

    public void addEventListener(Handler handler, BandwidthMeter.EventListener eventListener) {
        Assertions.checkNotNull(handler);
        Assertions.checkNotNull(eventListener);
        this.b.addListener(handler, eventListener);
    }

    public final long b(int i2) {
        Integer valueOf = Integer.valueOf(i2);
        ImmutableMap<Integer, Long> immutableMap = this.a;
        Long l2 = immutableMap.get(valueOf);
        if (l2 == null) {
            l2 = immutableMap.get(0);
        }
        if (l2 == null) {
            l2 = 1000000L;
        }
        return l2.longValue();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0064, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void c(int r13) {
        /*
            r12 = this;
            monitor-enter(r12)
            int r0 = r12.i     // Catch:{ all -> 0x0065 }
            if (r0 == 0) goto L_0x000b
            boolean r1 = r12.e     // Catch:{ all -> 0x0065 }
            if (r1 != 0) goto L_0x000b
            monitor-exit(r12)
            return
        L_0x000b:
            boolean r1 = r12.n     // Catch:{ all -> 0x0065 }
            if (r1 == 0) goto L_0x0011
            int r13 = r12.o     // Catch:{ all -> 0x0065 }
        L_0x0011:
            if (r0 != r13) goto L_0x0015
            monitor-exit(r12)
            return
        L_0x0015:
            r12.i = r13     // Catch:{ all -> 0x0065 }
            r0 = 1
            if (r13 == r0) goto L_0x0063
            if (r13 == 0) goto L_0x0063
            r0 = 8
            if (r13 != r0) goto L_0x0021
            goto L_0x0063
        L_0x0021:
            long r0 = r12.b(r13)     // Catch:{ all -> 0x0065 }
            r12.l = r0     // Catch:{ all -> 0x0065 }
            com.google.android.exoplayer2.util.Clock r13 = r12.d     // Catch:{ all -> 0x0065 }
            long r0 = r13.elapsedRealtime()     // Catch:{ all -> 0x0065 }
            int r13 = r12.f     // Catch:{ all -> 0x0065 }
            if (r13 <= 0) goto L_0x0038
            long r2 = r12.g     // Catch:{ all -> 0x0065 }
            long r2 = r0 - r2
            int r13 = (int) r2     // Catch:{ all -> 0x0065 }
            r3 = r13
            goto L_0x003a
        L_0x0038:
            r13 = 0
            r3 = 0
        L_0x003a:
            long r4 = r12.h     // Catch:{ all -> 0x0065 }
            long r6 = r12.l     // Catch:{ all -> 0x0065 }
            r8 = 0
            if (r3 != 0) goto L_0x004d
            int r13 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r13 != 0) goto L_0x004d
            long r10 = r12.m     // Catch:{ all -> 0x0065 }
            int r13 = (r6 > r10 ? 1 : (r6 == r10 ? 0 : -1))
            if (r13 != 0) goto L_0x004d
            goto L_0x0054
        L_0x004d:
            r12.m = r6     // Catch:{ all -> 0x0065 }
            com.google.android.exoplayer2.upstream.BandwidthMeter$EventListener$EventDispatcher r2 = r12.b     // Catch:{ all -> 0x0065 }
            r2.bandwidthSample(r3, r4, r6)     // Catch:{ all -> 0x0065 }
        L_0x0054:
            r12.g = r0     // Catch:{ all -> 0x0065 }
            r12.h = r8     // Catch:{ all -> 0x0065 }
            r12.k = r8     // Catch:{ all -> 0x0065 }
            r12.j = r8     // Catch:{ all -> 0x0065 }
            com.google.android.exoplayer2.util.SlidingPercentile r13 = r12.c     // Catch:{ all -> 0x0065 }
            r13.reset()     // Catch:{ all -> 0x0065 }
            monitor-exit(r12)
            return
        L_0x0063:
            monitor-exit(r12)
            return
        L_0x0065:
            r13 = move-exception
            monitor-exit(r12)
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.DefaultBandwidthMeter.c(int):void");
    }

    public synchronized long getBitrateEstimate() {
        return this.l;
    }

    public /* bridge */ /* synthetic */ long getTimeToFirstByteEstimateUs() {
        return cf.a(this);
    }

    public TransferListener getTransferListener() {
        return this;
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0012 A[SYNTHETIC, Splitter:B:11:0x0012] */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0010 A[DONT_GENERATE] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void onBytesTransferred(com.google.android.exoplayer2.upstream.DataSource r1, com.google.android.exoplayer2.upstream.DataSpec r2, boolean r3, int r4) {
        /*
            r0 = this;
            monitor-enter(r0)
            if (r3 == 0) goto L_0x000d
            r1 = 8
            boolean r1 = r2.isFlagSet(r1)     // Catch:{ all -> 0x001a }
            if (r1 != 0) goto L_0x000d
            r1 = 1
            goto L_0x000e
        L_0x000d:
            r1 = 0
        L_0x000e:
            if (r1 != 0) goto L_0x0012
            monitor-exit(r0)
            return
        L_0x0012:
            long r1 = r0.h     // Catch:{ all -> 0x001a }
            long r3 = (long) r4     // Catch:{ all -> 0x001a }
            long r1 = r1 + r3
            r0.h = r1     // Catch:{ all -> 0x001a }
            monitor-exit(r0)
            return
        L_0x001a:
            r1 = move-exception
            monitor-exit(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.DefaultBandwidthMeter.onBytesTransferred(com.google.android.exoplayer2.upstream.DataSource, com.google.android.exoplayer2.upstream.DataSpec, boolean, int):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0012 A[DONT_GENERATE] */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0014 A[SYNTHETIC, Splitter:B:12:0x0014] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void onTransferEnd(com.google.android.exoplayer2.upstream.DataSource r12, com.google.android.exoplayer2.upstream.DataSpec r13, boolean r14) {
        /*
            r11 = this;
            monitor-enter(r11)
            r12 = 0
            r0 = 1
            if (r14 == 0) goto L_0x000f
            r14 = 8
            boolean r13 = r13.isFlagSet(r14)     // Catch:{ all -> 0x0089 }
            if (r13 != 0) goto L_0x000f
            r13 = 1
            goto L_0x0010
        L_0x000f:
            r13 = 0
        L_0x0010:
            if (r13 != 0) goto L_0x0014
            monitor-exit(r11)
            return
        L_0x0014:
            int r13 = r11.f     // Catch:{ all -> 0x0089 }
            if (r13 <= 0) goto L_0x0019
            r12 = 1
        L_0x0019:
            com.google.android.exoplayer2.util.Assertions.checkState(r12)     // Catch:{ all -> 0x0089 }
            com.google.android.exoplayer2.util.Clock r12 = r11.d     // Catch:{ all -> 0x0089 }
            long r12 = r12.elapsedRealtime()     // Catch:{ all -> 0x0089 }
            long r1 = r11.g     // Catch:{ all -> 0x0089 }
            long r1 = r12 - r1
            int r4 = (int) r1     // Catch:{ all -> 0x0089 }
            long r1 = r11.j     // Catch:{ all -> 0x0089 }
            long r5 = (long) r4     // Catch:{ all -> 0x0089 }
            long r1 = r1 + r5
            r11.j = r1     // Catch:{ all -> 0x0089 }
            long r1 = r11.k     // Catch:{ all -> 0x0089 }
            long r5 = r11.h     // Catch:{ all -> 0x0089 }
            long r1 = r1 + r5
            r11.k = r1     // Catch:{ all -> 0x0089 }
            if (r4 <= 0) goto L_0x0082
            float r14 = (float) r5     // Catch:{ all -> 0x0089 }
            r1 = 1174011904(0x45fa0000, float:8000.0)
            float r14 = r14 * r1
            float r1 = (float) r4     // Catch:{ all -> 0x0089 }
            float r14 = r14 / r1
            com.google.android.exoplayer2.util.SlidingPercentile r1 = r11.c     // Catch:{ all -> 0x0089 }
            double r2 = (double) r5     // Catch:{ all -> 0x0089 }
            double r2 = java.lang.Math.sqrt(r2)     // Catch:{ all -> 0x0089 }
            int r2 = (int) r2     // Catch:{ all -> 0x0089 }
            r1.addSample(r2, r14)     // Catch:{ all -> 0x0089 }
            long r1 = r11.j     // Catch:{ all -> 0x0089 }
            r5 = 2000(0x7d0, double:9.88E-321)
            int r14 = (r1 > r5 ? 1 : (r1 == r5 ? 0 : -1))
            if (r14 >= 0) goto L_0x0059
            long r1 = r11.k     // Catch:{ all -> 0x0089 }
            r5 = 524288(0x80000, double:2.590327E-318)
            int r14 = (r1 > r5 ? 1 : (r1 == r5 ? 0 : -1))
            if (r14 < 0) goto L_0x0064
        L_0x0059:
            com.google.android.exoplayer2.util.SlidingPercentile r14 = r11.c     // Catch:{ all -> 0x0089 }
            r1 = 1056964608(0x3f000000, float:0.5)
            float r14 = r14.getPercentile(r1)     // Catch:{ all -> 0x0089 }
            long r1 = (long) r14     // Catch:{ all -> 0x0089 }
            r11.l = r1     // Catch:{ all -> 0x0089 }
        L_0x0064:
            long r5 = r11.h     // Catch:{ all -> 0x0089 }
            long r7 = r11.l     // Catch:{ all -> 0x0089 }
            r1 = 0
            if (r4 != 0) goto L_0x0077
            int r14 = (r5 > r1 ? 1 : (r5 == r1 ? 0 : -1))
            if (r14 != 0) goto L_0x0077
            long r9 = r11.m     // Catch:{ all -> 0x0089 }
            int r14 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r14 != 0) goto L_0x0077
            goto L_0x007e
        L_0x0077:
            r11.m = r7     // Catch:{ all -> 0x0089 }
            com.google.android.exoplayer2.upstream.BandwidthMeter$EventListener$EventDispatcher r3 = r11.b     // Catch:{ all -> 0x0089 }
            r3.bandwidthSample(r4, r5, r7)     // Catch:{ all -> 0x0089 }
        L_0x007e:
            r11.g = r12     // Catch:{ all -> 0x0089 }
            r11.h = r1     // Catch:{ all -> 0x0089 }
        L_0x0082:
            int r12 = r11.f     // Catch:{ all -> 0x0089 }
            int r12 = r12 - r0
            r11.f = r12     // Catch:{ all -> 0x0089 }
            monitor-exit(r11)
            return
        L_0x0089:
            r12 = move-exception
            monitor-exit(r11)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.DefaultBandwidthMeter.onTransferEnd(com.google.android.exoplayer2.upstream.DataSource, com.google.android.exoplayer2.upstream.DataSpec, boolean):void");
    }

    public void onTransferInitializing(DataSource dataSource, DataSpec dataSpec, boolean z) {
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0011 A[DONT_GENERATE] */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0013 A[SYNTHETIC, Splitter:B:12:0x0013] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void onTransferStart(com.google.android.exoplayer2.upstream.DataSource r1, com.google.android.exoplayer2.upstream.DataSpec r2, boolean r3) {
        /*
            r0 = this;
            monitor-enter(r0)
            r1 = 1
            if (r3 == 0) goto L_0x000e
            r3 = 8
            boolean r2 = r2.isFlagSet(r3)     // Catch:{ all -> 0x0026 }
            if (r2 != 0) goto L_0x000e
            r2 = 1
            goto L_0x000f
        L_0x000e:
            r2 = 0
        L_0x000f:
            if (r2 != 0) goto L_0x0013
            monitor-exit(r0)
            return
        L_0x0013:
            int r2 = r0.f     // Catch:{ all -> 0x0026 }
            if (r2 != 0) goto L_0x001f
            com.google.android.exoplayer2.util.Clock r2 = r0.d     // Catch:{ all -> 0x0026 }
            long r2 = r2.elapsedRealtime()     // Catch:{ all -> 0x0026 }
            r0.g = r2     // Catch:{ all -> 0x0026 }
        L_0x001f:
            int r2 = r0.f     // Catch:{ all -> 0x0026 }
            int r2 = r2 + r1
            r0.f = r2     // Catch:{ all -> 0x0026 }
            monitor-exit(r0)
            return
        L_0x0026:
            r1 = move-exception
            monitor-exit(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.DefaultBandwidthMeter.onTransferStart(com.google.android.exoplayer2.upstream.DataSource, com.google.android.exoplayer2.upstream.DataSpec, boolean):void");
    }

    public void removeEventListener(BandwidthMeter.EventListener eventListener) {
        this.b.removeListener(eventListener);
    }

    public synchronized void setNetworkTypeOverride(int i2) {
        this.o = i2;
        this.n = true;
        c(i2);
    }

    public static final class Builder {
        @Nullable
        public final Context a;
        public HashMap b;
        public int c;
        public Clock d;
        public boolean e;

        public Builder(Context context) {
            Context context2;
            if (context == null) {
                context2 = null;
            } else {
                context2 = context.getApplicationContext();
            }
            this.a = context2;
            this.b = a(Util.getCountryCode(context));
            this.c = 2000;
            this.d = Clock.a;
            this.e = true;
        }

        public static HashMap a(String str) {
            ImmutableList immutableList = DefaultBandwidthMeter.p.get((Object) str);
            if (immutableList.isEmpty()) {
                immutableList = ImmutableList.of(2, 2, 2, 2, 2, 2);
            }
            HashMap hashMap = new HashMap(8);
            hashMap.put(0, 1000000L);
            ImmutableList<Long> immutableList2 = DefaultBandwidthMeter.q;
            hashMap.put(2, immutableList2.get(((Integer) immutableList.get(0)).intValue()));
            hashMap.put(3, DefaultBandwidthMeter.r.get(((Integer) immutableList.get(1)).intValue()));
            hashMap.put(4, DefaultBandwidthMeter.s.get(((Integer) immutableList.get(2)).intValue()));
            hashMap.put(5, DefaultBandwidthMeter.t.get(((Integer) immutableList.get(3)).intValue()));
            hashMap.put(10, DefaultBandwidthMeter.u.get(((Integer) immutableList.get(4)).intValue()));
            hashMap.put(9, DefaultBandwidthMeter.v.get(((Integer) immutableList.get(5)).intValue()));
            hashMap.put(7, immutableList2.get(((Integer) immutableList.get(0)).intValue()));
            return hashMap;
        }

        public DefaultBandwidthMeter build() {
            return new DefaultBandwidthMeter(this.a, this.b, this.c, this.d, this.e);
        }

        public Builder setClock(Clock clock) {
            this.d = clock;
            return this;
        }

        public Builder setInitialBitrateEstimate(long j) {
            for (Integer intValue : this.b.keySet()) {
                setInitialBitrateEstimate(intValue.intValue(), j);
            }
            return this;
        }

        public Builder setResetOnNetworkTypeChange(boolean z) {
            this.e = z;
            return this;
        }

        public Builder setSlidingWindowMaxWeight(int i) {
            this.c = i;
            return this;
        }

        public Builder setInitialBitrateEstimate(int i, long j) {
            this.b.put(Integer.valueOf(i), Long.valueOf(j));
            return this;
        }

        public Builder setInitialBitrateEstimate(String str) {
            this.b = a(Ascii.toUpperCase(str));
            return this;
        }
    }

    public DefaultBandwidthMeter(@Nullable Context context, Map<Integer, Long> map, int i2, Clock clock, boolean z) {
        this.a = ImmutableMap.copyOf(map);
        this.b = new BandwidthMeter.EventListener.EventDispatcher();
        this.c = new SlidingPercentile(i2);
        this.d = clock;
        this.e = z;
        if (context != null) {
            NetworkTypeObserver instance = NetworkTypeObserver.getInstance(context);
            int networkType = instance.getNetworkType();
            this.i = networkType;
            this.l = b(networkType);
            instance.register(new a1(this));
            return;
        }
        this.i = 0;
        this.l = b(0);
    }
}
