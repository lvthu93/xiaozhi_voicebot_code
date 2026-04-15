package defpackage;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.upstream.cache.CacheSpan;
import com.google.android.exoplayer2.upstream.cache.ContentMetadataMutations;
import com.google.android.exoplayer2.upstream.cache.DefaultContentMetadata;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import java.io.File;
import java.util.ArrayList;
import java.util.TreeSet;

/* renamed from: cu  reason: default package */
public final class cu {
    public final int a;
    public final String b;
    public final TreeSet<xa> c;
    public final ArrayList<a> d;
    public DefaultContentMetadata e;

    /* renamed from: cu$a */
    public static final class a {
        public final long a;
        public final long b;

        public a(long j, long j2) {
            this.a = j;
            this.b = j2;
        }

        public boolean contains(long j, long j2) {
            long j3 = this.a;
            long j4 = this.b;
            return j4 == -1 ? j >= j3 : j2 != -1 && j3 <= j && j + j2 <= j3 + j4;
        }

        public boolean intersects(long j, long j2) {
            long j3 = this.a;
            if (j3 <= j) {
                long j4 = this.b;
                if (j4 == -1 || j3 + j4 > j) {
                    return true;
                }
                return false;
            } else if (j2 == -1 || j + j2 > j3) {
                return true;
            } else {
                return false;
            }
        }
    }

    public cu(int i, String str) {
        this(i, str, DefaultContentMetadata.c);
    }

    public void addSpan(xa xaVar) {
        this.c.add(xaVar);
    }

    public boolean applyMetadataMutations(ContentMetadataMutations contentMetadataMutations) {
        DefaultContentMetadata defaultContentMetadata = this.e;
        DefaultContentMetadata copyWithMutationsApplied = defaultContentMetadata.copyWithMutationsApplied(contentMetadataMutations);
        this.e = copyWithMutationsApplied;
        return !copyWithMutationsApplied.equals(defaultContentMetadata);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || cu.class != obj.getClass()) {
            return false;
        }
        cu cuVar = (cu) obj;
        if (this.a != cuVar.a || !this.b.equals(cuVar.b) || !this.c.equals(cuVar.c) || !this.e.equals(cuVar.e)) {
            return false;
        }
        return true;
    }

    public long getCachedBytesLength(long j, long j2) {
        boolean z;
        boolean z2 = true;
        if (j >= 0) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkArgument(z);
        if (j2 < 0) {
            z2 = false;
        }
        Assertions.checkArgument(z2);
        xa span = getSpan(j, j2);
        boolean isHoleSpan = span.isHoleSpan();
        long j3 = Long.MAX_VALUE;
        long j4 = span.g;
        if (isHoleSpan) {
            if (!span.isOpenEnded()) {
                j3 = j4;
            }
            return -Math.min(j3, j2);
        }
        long j5 = j + j2;
        if (j5 >= 0) {
            j3 = j5;
        }
        long j6 = span.f + j4;
        if (j6 < j3) {
            for (xa next : this.c.tailSet(span, false)) {
                long j7 = next.f;
                if (j7 <= j6) {
                    j6 = Math.max(j6, j7 + next.g);
                    if (j6 >= j3) {
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        return Math.min(j6 - j, j2);
    }

    public DefaultContentMetadata getMetadata() {
        return this.e;
    }

    public xa getSpan(long j, long j2) {
        String str = this.b;
        xa createLookup = xa.createLookup(str, j);
        TreeSet<xa> treeSet = this.c;
        xa floor = treeSet.floor(createLookup);
        if (floor != null && floor.f + floor.g > j) {
            return floor;
        }
        xa ceiling = treeSet.ceiling(createLookup);
        if (ceiling != null) {
            long j3 = ceiling.f - j;
            if (j2 == -1) {
                j2 = j3;
            } else {
                j2 = Math.min(j3, j2);
            }
        }
        return xa.createHole(str, j, j2);
    }

    public TreeSet<xa> getSpans() {
        return this.c;
    }

    public int hashCode() {
        int hashCode = this.b.hashCode();
        return this.e.hashCode() + ((hashCode + (this.a * 31)) * 31);
    }

    public boolean isEmpty() {
        return this.c.isEmpty();
    }

    public boolean isFullyLocked(long j, long j2) {
        int i = 0;
        while (true) {
            ArrayList<a> arrayList = this.d;
            if (i >= arrayList.size()) {
                return false;
            }
            if (arrayList.get(i).contains(j, j2)) {
                return true;
            }
            i++;
        }
    }

    public boolean isFullyUnlocked() {
        return this.d.isEmpty();
    }

    public boolean lockRange(long j, long j2) {
        int i = 0;
        while (true) {
            ArrayList<a> arrayList = this.d;
            if (i >= arrayList.size()) {
                arrayList.add(new a(j, j2));
                return true;
            } else if (arrayList.get(i).intersects(j, j2)) {
                return false;
            } else {
                i++;
            }
        }
    }

    public boolean removeSpan(CacheSpan cacheSpan) {
        if (!this.c.remove(cacheSpan)) {
            return false;
        }
        File file = cacheSpan.i;
        if (file == null) {
            return true;
        }
        file.delete();
        return true;
    }

    public xa setLastTouchTimestamp(xa xaVar, long j, boolean z) {
        TreeSet<xa> treeSet = this.c;
        Assertions.checkState(treeSet.remove(xaVar));
        File file = (File) Assertions.checkNotNull(xaVar.i);
        if (z) {
            File cacheFile = xa.getCacheFile((File) Assertions.checkNotNull(file.getParentFile()), this.a, xaVar.f, j);
            if (file.renameTo(cacheFile)) {
                file = cacheFile;
            } else {
                String valueOf = String.valueOf(file);
                String valueOf2 = String.valueOf(cacheFile);
                StringBuilder sb = new StringBuilder(valueOf2.length() + valueOf.length() + 21);
                sb.append("Failed to rename ");
                sb.append(valueOf);
                sb.append(" to ");
                sb.append(valueOf2);
                Log.w("CachedContent", sb.toString());
            }
        }
        xa copyWithFileAndLastTouchTimestamp = xaVar.copyWithFileAndLastTouchTimestamp(file, j);
        treeSet.add(copyWithFileAndLastTouchTimestamp);
        return copyWithFileAndLastTouchTimestamp;
    }

    public void unlockRange(long j) {
        int i = 0;
        while (true) {
            ArrayList<a> arrayList = this.d;
            if (i >= arrayList.size()) {
                throw new IllegalStateException();
            } else if (arrayList.get(i).a == j) {
                arrayList.remove(i);
                return;
            } else {
                i++;
            }
        }
    }

    public cu(int i, String str, DefaultContentMetadata defaultContentMetadata) {
        this.a = i;
        this.b = str;
        this.e = defaultContentMetadata;
        this.c = new TreeSet<>();
        this.d = new ArrayList<>();
    }
}
