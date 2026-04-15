package defpackage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.SpannableStringBuilder;
import android.util.Base64;
import android.util.Pair;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.ttml.TtmlStyle;
import com.google.android.exoplayer2.util.Assertions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

/* renamed from: uc  reason: default package */
public final class uc {
    @Nullable
    public final String a;
    @Nullable
    public final String b;
    public final boolean c;
    public final long d;
    public final long e;
    @Nullable
    public final TtmlStyle f;
    @Nullable
    public final String[] g;
    public final String h;
    @Nullable
    public final String i;
    @Nullable
    public final uc j;
    public final HashMap<String, Integer> k;
    public final HashMap<String, Integer> l;
    public ArrayList m;

    public uc(@Nullable String str, @Nullable String str2, long j2, long j3, @Nullable TtmlStyle ttmlStyle, @Nullable String[] strArr, String str3, @Nullable String str4, @Nullable uc ucVar) {
        boolean z;
        this.a = str;
        this.b = str2;
        this.i = str4;
        this.f = ttmlStyle;
        this.g = strArr;
        if (str2 != null) {
            z = true;
        } else {
            z = false;
        }
        this.c = z;
        this.d = j2;
        this.e = j3;
        this.h = (String) Assertions.checkNotNull(str3);
        this.j = ucVar;
        this.k = new HashMap<>();
        this.l = new HashMap<>();
    }

    public static SpannableStringBuilder b(String str, TreeMap treeMap) {
        if (!treeMap.containsKey(str)) {
            Cue.Builder builder = new Cue.Builder();
            builder.setText(new SpannableStringBuilder());
            treeMap.put(str, builder);
        }
        return (SpannableStringBuilder) Assertions.checkNotNull(((Cue.Builder) treeMap.get(str)).getText());
    }

    public static uc buildNode(@Nullable String str, long j2, long j3, @Nullable TtmlStyle ttmlStyle, @Nullable String[] strArr, String str2, @Nullable String str3, @Nullable uc ucVar) {
        return new uc(str, (String) null, j2, j3, ttmlStyle, strArr, str2, str3, ucVar);
    }

    public static uc buildTextNode(String str) {
        return new uc((String) null, str.replaceAll("\r\n", "\n").replaceAll(" *\n *", "\n").replaceAll("\n", " ").replaceAll("[ \t\\x0B\f\r]+", " "), -9223372036854775807L, -9223372036854775807L, (TtmlStyle) null, (String[]) null, "", (String) null, (uc) null);
    }

    public final void a(TreeSet<Long> treeSet, boolean z) {
        boolean z2;
        String str = this.a;
        boolean equals = "p".equals(str);
        boolean equals2 = "div".equals(str);
        if (z || equals || (equals2 && this.i != null)) {
            long j2 = this.d;
            if (j2 != -9223372036854775807L) {
                treeSet.add(Long.valueOf(j2));
            }
            long j3 = this.e;
            if (j3 != -9223372036854775807L) {
                treeSet.add(Long.valueOf(j3));
            }
        }
        if (this.m != null) {
            for (int i2 = 0; i2 < this.m.size(); i2++) {
                uc ucVar = (uc) this.m.get(i2);
                if (z || equals) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                ucVar.a(treeSet, z2);
            }
        }
    }

    public void addChild(uc ucVar) {
        if (this.m == null) {
            this.m = new ArrayList();
        }
        this.m.add(ucVar);
    }

    public final void c(long j2, String str, ArrayList arrayList) {
        String str2;
        String str3 = this.h;
        if (!"".equals(str3)) {
            str = str3;
        }
        if (!isActive(j2) || !"div".equals(this.a) || (str2 = this.i) == null) {
            for (int i2 = 0; i2 < getChildCount(); i2++) {
                getChild(i2).c(j2, str, arrayList);
            }
            return;
        }
        arrayList.add(new Pair(str, str2));
    }

    public final void d(long j2, Map map, Map map2, String str, TreeMap treeMap) {
        int i2;
        if (isActive(j2)) {
            String str2 = this.h;
            if ("".equals(str2)) {
                str2 = str;
            }
            for (Map.Entry next : this.l.entrySet()) {
                String str3 = (String) next.getKey();
                HashMap<String, Integer> hashMap = this.k;
                if (hashMap.containsKey(str3)) {
                    i2 = hashMap.get(str3).intValue();
                } else {
                    i2 = 0;
                }
                int intValue = ((Integer) next.getValue()).intValue();
                if (i2 != intValue) {
                    Cue.Builder builder = (Cue.Builder) Assertions.checkNotNull((Cue.Builder) treeMap.get(str3));
                    int i3 = ((vc) Assertions.checkNotNull((vc) map2.get(str2))).j;
                    TtmlStyle resolveStyle = wc.resolveStyle(this.f, this.g, map);
                    SpannableStringBuilder spannableStringBuilder = (SpannableStringBuilder) builder.getText();
                    if (spannableStringBuilder == null) {
                        spannableStringBuilder = new SpannableStringBuilder();
                        builder.setText(spannableStringBuilder);
                    }
                    SpannableStringBuilder spannableStringBuilder2 = spannableStringBuilder;
                    if (resolveStyle != null) {
                        wc.applyStylesToSpan(spannableStringBuilder2, i2, intValue, resolveStyle, this.j, map, i3);
                        if ("p".equals(this.a)) {
                            if (resolveStyle.getShearPercentage() != Float.MAX_VALUE) {
                                builder.setShearDegrees((resolveStyle.getShearPercentage() * -90.0f) / 100.0f);
                            }
                            if (resolveStyle.getTextAlign() != null) {
                                builder.setTextAlignment(resolveStyle.getTextAlign());
                            }
                            if (resolveStyle.getMultiRowAlign() != null) {
                                builder.setMultiRowAlignment(resolveStyle.getMultiRowAlign());
                            }
                        }
                    }
                } else {
                    Map map3 = map;
                    Map map4 = map2;
                    TreeMap treeMap2 = treeMap;
                }
            }
            Map map5 = map;
            Map map6 = map2;
            TreeMap treeMap3 = treeMap;
            for (int i4 = 0; i4 < getChildCount(); i4++) {
                getChild(i4).d(j2, map, map2, str2, treeMap);
            }
        }
    }

    public final void e(long j2, boolean z, String str, TreeMap treeMap) {
        String str2;
        boolean z2;
        TreeMap treeMap2 = treeMap;
        HashMap<String, Integer> hashMap = this.k;
        hashMap.clear();
        HashMap<String, Integer> hashMap2 = this.l;
        hashMap2.clear();
        String str3 = this.a;
        if (!"metadata".equals(str3)) {
            String str4 = this.h;
            if ("".equals(str4)) {
                str2 = str;
            } else {
                str2 = str4;
            }
            if (this.c && z) {
                b(str2, treeMap2).append((CharSequence) Assertions.checkNotNull(this.b));
            } else if ("br".equals(str3) && z) {
                b(str2, treeMap2).append(10);
            } else if (isActive(j2)) {
                for (Map.Entry entry : treeMap.entrySet()) {
                    hashMap.put((String) entry.getKey(), Integer.valueOf(((CharSequence) Assertions.checkNotNull(((Cue.Builder) entry.getValue()).getText())).length()));
                }
                boolean equals = "p".equals(str3);
                for (int i2 = 0; i2 < getChildCount(); i2++) {
                    uc child = getChild(i2);
                    if (z || equals) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    child.e(j2, z2, str2, treeMap);
                }
                if (equals) {
                    SpannableStringBuilder b2 = b(str2, treeMap2);
                    int length = b2.length();
                    do {
                        length--;
                        if (length < 0 || b2.charAt(length) != ' ') {
                            if (length >= 0 && b2.charAt(length) != 10) {
                                b2.append(10);
                            }
                        }
                        length--;
                        break;
                    } while (b2.charAt(length) != ' ');
                    b2.append(10);
                }
                for (Map.Entry entry2 : treeMap.entrySet()) {
                    hashMap2.put((String) entry2.getKey(), Integer.valueOf(((CharSequence) Assertions.checkNotNull(((Cue.Builder) entry2.getValue()).getText())).length()));
                }
            }
        }
    }

    public uc getChild(int i2) {
        ArrayList arrayList = this.m;
        if (arrayList != null) {
            return (uc) arrayList.get(i2);
        }
        throw new IndexOutOfBoundsException();
    }

    public int getChildCount() {
        ArrayList arrayList = this.m;
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }

    public List<Cue> getCues(long j2, Map<String, TtmlStyle> map, Map<String, vc> map2, Map<String, String> map3) {
        ArrayList arrayList = new ArrayList();
        c(j2, this.h, arrayList);
        TreeMap treeMap = new TreeMap();
        long j3 = j2;
        e(j3, false, this.h, treeMap);
        d(j3, map, map2, this.h, treeMap);
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            String str = map3.get(pair.second);
            if (str != null) {
                byte[] decode = Base64.decode(str, 0);
                Bitmap decodeByteArray = BitmapFactory.decodeByteArray(decode, 0, decode.length);
                vc vcVar = (vc) Assertions.checkNotNull(map2.get(pair.first));
                arrayList2.add(new Cue.Builder().setBitmap(decodeByteArray).setPosition(vcVar.b).setPositionAnchor(0).setLine(vcVar.c, 0).setLineAnchor(vcVar.e).setSize(vcVar.f).setBitmapHeight(vcVar.g).setVerticalType(vcVar.j).build());
            }
        }
        for (Map.Entry entry : treeMap.entrySet()) {
            vc vcVar2 = (vc) Assertions.checkNotNull(map2.get(entry.getKey()));
            Cue.Builder builder = (Cue.Builder) entry.getValue();
            SpannableStringBuilder spannableStringBuilder = (SpannableStringBuilder) Assertions.checkNotNull(builder.getText());
            for (g1 g1Var : (g1[]) spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), g1.class)) {
                spannableStringBuilder.replace(spannableStringBuilder.getSpanStart(g1Var), spannableStringBuilder.getSpanEnd(g1Var), "");
            }
            for (int i2 = 0; i2 < spannableStringBuilder.length(); i2++) {
                if (spannableStringBuilder.charAt(i2) == ' ') {
                    int i3 = i2 + 1;
                    int i4 = i3;
                    while (i4 < spannableStringBuilder.length() && spannableStringBuilder.charAt(i4) == ' ') {
                        i4++;
                    }
                    int i5 = i4 - i3;
                    if (i5 > 0) {
                        spannableStringBuilder.delete(i2, i5 + i2);
                    }
                }
            }
            if (spannableStringBuilder.length() > 0 && spannableStringBuilder.charAt(0) == ' ') {
                spannableStringBuilder.delete(0, 1);
            }
            for (int i6 = 0; i6 < spannableStringBuilder.length() - 1; i6++) {
                if (spannableStringBuilder.charAt(i6) == 10) {
                    int i7 = i6 + 1;
                    if (spannableStringBuilder.charAt(i7) == ' ') {
                        spannableStringBuilder.delete(i7, i6 + 2);
                    }
                }
            }
            if (spannableStringBuilder.length() > 0 && spannableStringBuilder.charAt(spannableStringBuilder.length() - 1) == ' ') {
                spannableStringBuilder.delete(spannableStringBuilder.length() - 1, spannableStringBuilder.length());
            }
            for (int i8 = 0; i8 < spannableStringBuilder.length() - 1; i8++) {
                if (spannableStringBuilder.charAt(i8) == ' ') {
                    int i9 = i8 + 1;
                    if (spannableStringBuilder.charAt(i9) == 10) {
                        spannableStringBuilder.delete(i8, i9);
                    }
                }
            }
            if (spannableStringBuilder.length() > 0 && spannableStringBuilder.charAt(spannableStringBuilder.length() - 1) == 10) {
                spannableStringBuilder.delete(spannableStringBuilder.length() - 1, spannableStringBuilder.length());
            }
            builder.setLine(vcVar2.c, vcVar2.d);
            builder.setLineAnchor(vcVar2.e);
            builder.setPosition(vcVar2.b);
            builder.setSize(vcVar2.f);
            builder.setTextSize(vcVar2.i, vcVar2.h);
            builder.setVerticalType(vcVar2.j);
            arrayList2.add(builder.build());
        }
        return arrayList2;
    }

    public long[] getEventTimesUs() {
        TreeSet treeSet = new TreeSet();
        int i2 = 0;
        a(treeSet, false);
        long[] jArr = new long[treeSet.size()];
        Iterator it = treeSet.iterator();
        while (it.hasNext()) {
            jArr[i2] = ((Long) it.next()).longValue();
            i2++;
        }
        return jArr;
    }

    @Nullable
    public String[] getStyleIds() {
        return this.g;
    }

    public boolean isActive(long j2) {
        long j3 = this.e;
        long j4 = this.d;
        return (j4 == -9223372036854775807L && j3 == -9223372036854775807L) || (j4 <= j2 && j3 == -9223372036854775807L) || ((j4 == -9223372036854775807L && j2 < j3) || (j4 <= j2 && j2 < j3));
    }
}
