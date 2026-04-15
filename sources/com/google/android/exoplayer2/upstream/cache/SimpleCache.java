package com.google.android.exoplayer2.upstream.cache;

import android.os.ConditionVariable;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import com.google.android.exoplayer2.database.DatabaseIOException;
import com.google.android.exoplayer2.database.DatabaseProvider;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public final class SimpleCache implements Cache {
    public static final HashSet<File> l = new HashSet<>();
    public final File a;
    public final CacheEvictor b;
    public final cv c;
    @Nullable
    public final ct d;
    public final HashMap<String, ArrayList<Cache.Listener>> e;
    public final Random f;
    public final boolean g;
    public long h;
    public long i;
    public boolean j;
    public Cache.CacheException k;

    @Deprecated
    public SimpleCache(File file, CacheEvictor cacheEvictor) {
        this(file, cacheEvictor, (byte[]) null, false);
    }

    public static void a(SimpleCache simpleCache) {
        cv cvVar = simpleCache.c;
        File file = simpleCache.a;
        if (!file.exists()) {
            try {
                c(file);
            } catch (Cache.CacheException e2) {
                simpleCache.k = e2;
                return;
            }
        }
        File[] listFiles = file.listFiles();
        if (listFiles == null) {
            String valueOf = String.valueOf(file);
            StringBuilder sb = new StringBuilder(valueOf.length() + 38);
            sb.append("Failed to list cache directory files: ");
            sb.append(valueOf);
            String sb2 = sb.toString();
            Log.e("SimpleCache", sb2);
            simpleCache.k = new Cache.CacheException(sb2);
            return;
        }
        long f2 = f(listFiles);
        simpleCache.h = f2;
        if (f2 == -1) {
            try {
                simpleCache.h = d(file);
            } catch (IOException e3) {
                String valueOf2 = String.valueOf(file);
                StringBuilder sb3 = new StringBuilder(valueOf2.length() + 28);
                sb3.append("Failed to create cache UID: ");
                sb3.append(valueOf2);
                String sb4 = sb3.toString();
                Log.e("SimpleCache", sb4, e3);
                simpleCache.k = new Cache.CacheException(sb4, e3);
                return;
            }
        }
        try {
            cvVar.initialize(simpleCache.h);
            ct ctVar = simpleCache.d;
            if (ctVar != null) {
                ctVar.initialize(simpleCache.h);
                Map<String, cs> all = ctVar.getAll();
                simpleCache.e(file, true, listFiles, all);
                ctVar.removeAll(all.keySet());
            } else {
                simpleCache.e(file, true, listFiles, (Map<String, cs>) null);
            }
            cvVar.removeEmpty();
            try {
                cvVar.store();
            } catch (IOException e4) {
                Log.e("SimpleCache", "Storing index file failed", e4);
            }
        } catch (IOException e5) {
            String valueOf3 = String.valueOf(file);
            StringBuilder sb5 = new StringBuilder(valueOf3.length() + 36);
            sb5.append("Failed to initialize cache indices: ");
            sb5.append(valueOf3);
            String sb6 = sb5.toString();
            Log.e("SimpleCache", sb6, e5);
            simpleCache.k = new Cache.CacheException(sb6, e5);
        }
    }

    public static void c(File file) throws Cache.CacheException {
        if (!file.mkdirs() && !file.isDirectory()) {
            String valueOf = String.valueOf(file);
            StringBuilder sb = new StringBuilder(valueOf.length() + 34);
            sb.append("Failed to create cache directory: ");
            sb.append(valueOf);
            String sb2 = sb.toString();
            Log.e("SimpleCache", sb2);
            throw new Cache.CacheException(sb2);
        }
    }

    public static long d(File file) throws IOException {
        long j2;
        String str;
        long nextLong = new SecureRandom().nextLong();
        if (nextLong == Long.MIN_VALUE) {
            j2 = 0;
        } else {
            j2 = Math.abs(nextLong);
        }
        String valueOf = String.valueOf(Long.toString(j2, 16));
        if (".uid".length() != 0) {
            str = valueOf.concat(".uid");
        } else {
            str = new String(valueOf);
        }
        File file2 = new File(file, str);
        if (file2.createNewFile()) {
            return j2;
        }
        String valueOf2 = String.valueOf(file2);
        StringBuilder sb = new StringBuilder(valueOf2.length() + 27);
        sb.append("Failed to create UID file: ");
        sb.append(valueOf2);
        throw new IOException(sb.toString());
    }

    @WorkerThread
    public static void delete(File file, @Nullable DatabaseProvider databaseProvider) {
        if (file.exists()) {
            File[] listFiles = file.listFiles();
            if (listFiles == null) {
                file.delete();
                return;
            }
            if (databaseProvider != null) {
                long f2 = f(listFiles);
                if (f2 != -1) {
                    try {
                        ct.delete(databaseProvider, f2);
                    } catch (DatabaseIOException unused) {
                        StringBuilder sb = new StringBuilder(52);
                        sb.append("Failed to delete file metadata: ");
                        sb.append(f2);
                        Log.w("SimpleCache", sb.toString());
                    }
                    try {
                        cv.delete(databaseProvider, f2);
                    } catch (DatabaseIOException unused2) {
                        StringBuilder sb2 = new StringBuilder(52);
                        sb2.append("Failed to delete file metadata: ");
                        sb2.append(f2);
                        Log.w("SimpleCache", sb2.toString());
                    }
                }
            }
            Util.recursiveDelete(file);
        }
    }

    public static long f(File[] fileArr) {
        int length = fileArr.length;
        int i2 = 0;
        while (i2 < length) {
            File file = fileArr[i2];
            String name = file.getName();
            if (name.endsWith(".uid")) {
                try {
                    return Long.parseLong(name.substring(0, name.indexOf(46)), 16);
                } catch (NumberFormatException unused) {
                    String valueOf = String.valueOf(file);
                    StringBuilder sb = new StringBuilder(valueOf.length() + 20);
                    sb.append("Malformed UID file: ");
                    sb.append(valueOf);
                    Log.e("SimpleCache", sb.toString());
                    file.delete();
                }
            } else {
                i2++;
            }
        }
        return -1;
    }

    public static synchronized boolean isCacheFolderLocked(File file) {
        boolean contains;
        synchronized (SimpleCache.class) {
            contains = l.contains(file.getAbsoluteFile());
        }
        return contains;
    }

    public static synchronized void j(File file) {
        synchronized (SimpleCache.class) {
            l.remove(file.getAbsoluteFile());
        }
    }

    public synchronized NavigableSet<CacheSpan> addListener(String str, Cache.Listener listener) {
        boolean z;
        if (!this.j) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkState(z);
        Assertions.checkNotNull(str);
        Assertions.checkNotNull(listener);
        ArrayList arrayList = this.e.get(str);
        if (arrayList == null) {
            arrayList = new ArrayList();
            this.e.put(str, arrayList);
        }
        arrayList.add(listener);
        return getCachedSpans(str);
    }

    public synchronized void applyContentMetadataMutations(String str, ContentMetadataMutations contentMetadataMutations) throws Cache.CacheException {
        boolean z;
        if (!this.j) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkState(z);
        checkInitialization();
        this.c.applyContentMetadataMutations(str, contentMetadataMutations);
        try {
            this.c.store();
        } catch (IOException e2) {
            throw new Cache.CacheException((Throwable) e2);
        }
    }

    public final void b(xa xaVar) {
        cv cvVar = this.c;
        String str = xaVar.c;
        cvVar.getOrAdd(str).addSpan(xaVar);
        this.i += xaVar.g;
        ArrayList arrayList = this.e.get(str);
        if (arrayList != null) {
            int size = arrayList.size();
            while (true) {
                size--;
                if (size < 0) {
                    break;
                }
                ((Cache.Listener) arrayList.get(size)).onSpanAdded(this, xaVar);
            }
        }
        this.b.onSpanAdded(this, xaVar);
    }

    public synchronized void checkInitialization() throws Cache.CacheException {
        Cache.CacheException cacheException = this.k;
        if (cacheException != null) {
            throw cacheException;
        }
    }

    public synchronized void commitFile(File file, long j2) throws Cache.CacheException {
        boolean z;
        boolean z2 = true;
        if (!this.j) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkState(z);
        if (file.exists()) {
            if (j2 == 0) {
                file.delete();
                return;
            }
            xa xaVar = (xa) Assertions.checkNotNull(xa.createCacheEntry(file, j2, this.c));
            cu cuVar = (cu) Assertions.checkNotNull(this.c.get(xaVar.c));
            Assertions.checkState(cuVar.isFullyLocked(xaVar.f, xaVar.g));
            long a2 = t0.a(cuVar.getMetadata());
            if (a2 != -1) {
                if (xaVar.f + xaVar.g > a2) {
                    z2 = false;
                }
                Assertions.checkState(z2);
            }
            if (this.d != null) {
                try {
                    this.d.set(file.getName(), xaVar.g, xaVar.j);
                } catch (IOException e2) {
                    throw new Cache.CacheException((Throwable) e2);
                } catch (IOException e3) {
                    throw new Cache.CacheException((Throwable) e3);
                }
            }
            b(xaVar);
            this.c.store();
            notifyAll();
        }
    }

    public final void e(File file, boolean z, @Nullable File[] fileArr, @Nullable Map<String, cs> map) {
        cs csVar;
        long j2;
        long j3;
        if (fileArr != null && fileArr.length != 0) {
            for (File file2 : fileArr) {
                String name = file2.getName();
                if (z && name.indexOf(46) == -1) {
                    e(file2, false, file2.listFiles(), map);
                } else if (!z || (!cv.isIndexFile(name) && !name.endsWith(".uid"))) {
                    if (map != null) {
                        csVar = map.remove(name);
                    } else {
                        csVar = null;
                    }
                    if (csVar != null) {
                        j3 = csVar.a;
                        j2 = csVar.b;
                    } else {
                        j2 = -9223372036854775807L;
                        j3 = -1;
                    }
                    xa createCacheEntry = xa.createCacheEntry(file2, j3, j2, this.c);
                    if (createCacheEntry != null) {
                        b(createCacheEntry);
                    } else {
                        file2.delete();
                    }
                }
            }
        } else if (!z) {
            file.delete();
        }
    }

    public final void g(CacheSpan cacheSpan) {
        String str;
        String str2 = cacheSpan.c;
        cv cvVar = this.c;
        cu cuVar = cvVar.get(str2);
        if (cuVar != null && cuVar.removeSpan(cacheSpan)) {
            this.i -= cacheSpan.g;
            ct ctVar = this.d;
            if (ctVar != null) {
                String name = cacheSpan.i.getName();
                try {
                    ctVar.remove(name);
                } catch (IOException unused) {
                    String valueOf = String.valueOf(name);
                    if (valueOf.length() != 0) {
                        str = "Failed to remove file index entry for: ".concat(valueOf);
                    } else {
                        str = new String("Failed to remove file index entry for: ");
                    }
                    Log.w("SimpleCache", str);
                }
            }
            cvVar.maybeRemove(cuVar.b);
            ArrayList arrayList = this.e.get(cacheSpan.c);
            if (arrayList != null) {
                int size = arrayList.size();
                while (true) {
                    size--;
                    if (size < 0) {
                        break;
                    }
                    ((Cache.Listener) arrayList.get(size)).onSpanRemoved(this, cacheSpan);
                }
            }
            this.b.onSpanRemoved(this, cacheSpan);
        }
    }

    public synchronized long getCacheSpace() {
        boolean z;
        if (!this.j) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkState(z);
        return this.i;
    }

    public synchronized long getCachedBytes(String str, long j2, long j3) {
        long j4;
        long j5;
        long j6 = Long.MAX_VALUE;
        if (j3 == -1) {
            j4 = Long.MAX_VALUE;
        } else {
            j4 = j3 + j2;
        }
        if (j4 >= 0) {
            j6 = j4;
        }
        j5 = 0;
        while (j2 < j6) {
            long cachedLength = getCachedLength(str, j2, j6 - j2);
            if (cachedLength > 0) {
                j5 += cachedLength;
            } else {
                cachedLength = -cachedLength;
            }
            j2 += cachedLength;
        }
        return j5;
    }

    public synchronized long getCachedLength(String str, long j2, long j3) {
        boolean z;
        long j4;
        if (!this.j) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkState(z);
        if (j3 == -1) {
            j3 = Long.MAX_VALUE;
        }
        cu cuVar = this.c.get(str);
        if (cuVar != null) {
            j4 = cuVar.getCachedBytesLength(j2, j3);
        } else {
            j4 = -j3;
        }
        return j4;
    }

    public synchronized NavigableSet<CacheSpan> getCachedSpans(String str) {
        boolean z;
        TreeSet treeSet;
        if (!this.j) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkState(z);
        cu cuVar = this.c.get(str);
        if (cuVar != null) {
            if (!cuVar.isEmpty()) {
                treeSet = new TreeSet(cuVar.getSpans());
            }
        }
        treeSet = new TreeSet();
        return treeSet;
    }

    public synchronized ContentMetadata getContentMetadata(String str) {
        boolean z;
        if (!this.j) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkState(z);
        return this.c.getContentMetadata(str);
    }

    public synchronized Set<String> getKeys() {
        boolean z;
        if (!this.j) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkState(z);
        return new HashSet(this.c.getKeys());
    }

    public synchronized long getUid() {
        return this.h;
    }

    public final void h() {
        ArrayList arrayList = new ArrayList();
        for (cu spans : this.c.getAll()) {
            Iterator<xa> it = spans.getSpans().iterator();
            while (it.hasNext()) {
                CacheSpan next = it.next();
                if (next.i.length() != next.g) {
                    arrayList.add(next);
                }
            }
        }
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            g((CacheSpan) arrayList.get(i2));
        }
    }

    public final xa i(String str, xa xaVar) {
        boolean z;
        if (!this.g) {
            return xaVar;
        }
        String name = ((File) Assertions.checkNotNull(xaVar.i)).getName();
        long j2 = xaVar.g;
        long currentTimeMillis = System.currentTimeMillis();
        ct ctVar = this.d;
        if (ctVar != null) {
            try {
                ctVar.set(name, j2, currentTimeMillis);
            } catch (IOException unused) {
                Log.w("SimpleCache", "Failed to update index with new touch timestamp.");
            }
            z = false;
        } else {
            z = true;
        }
        xa lastTouchTimestamp = this.c.get(str).setLastTouchTimestamp(xaVar, currentTimeMillis, z);
        ArrayList arrayList = this.e.get(xaVar.c);
        if (arrayList != null) {
            int size = arrayList.size();
            while (true) {
                size--;
                if (size < 0) {
                    break;
                }
                ((Cache.Listener) arrayList.get(size)).onSpanTouched(this, xaVar, lastTouchTimestamp);
            }
        }
        this.b.onSpanTouched(this, xaVar, lastTouchTimestamp);
        return lastTouchTimestamp;
    }

    public synchronized boolean isCached(String str, long j2, long j3) {
        boolean z;
        boolean z2;
        z = true;
        if (!this.j) {
            z2 = true;
        } else {
            z2 = false;
        }
        Assertions.checkState(z2);
        cu cuVar = this.c.get(str);
        if (cuVar == null || cuVar.getCachedBytesLength(j2, j3) < j3) {
            z = false;
        }
        return z;
    }

    public synchronized void release() {
        if (!this.j) {
            this.e.clear();
            h();
            try {
                this.c.store();
                j(this.a);
            } catch (IOException e2) {
                try {
                    Log.e("SimpleCache", "Storing index file failed", e2);
                    j(this.a);
                } catch (Throwable th) {
                    j(this.a);
                    this.j = true;
                    throw th;
                }
            }
            this.j = true;
            return;
        }
        return;
    }

    public synchronized void releaseHoleSpan(CacheSpan cacheSpan) {
        boolean z;
        if (!this.j) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkState(z);
        cu cuVar = (cu) Assertions.checkNotNull(this.c.get(cacheSpan.c));
        cuVar.unlockRange(cacheSpan.f);
        this.c.maybeRemove(cuVar.b);
        notifyAll();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0020, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void removeListener(java.lang.String r2, com.google.android.exoplayer2.upstream.cache.Cache.Listener r3) {
        /*
            r1 = this;
            monitor-enter(r1)
            boolean r0 = r1.j     // Catch:{ all -> 0x0021 }
            if (r0 == 0) goto L_0x0007
            monitor-exit(r1)
            return
        L_0x0007:
            java.util.HashMap<java.lang.String, java.util.ArrayList<com.google.android.exoplayer2.upstream.cache.Cache$Listener>> r0 = r1.e     // Catch:{ all -> 0x0021 }
            java.lang.Object r0 = r0.get(r2)     // Catch:{ all -> 0x0021 }
            java.util.ArrayList r0 = (java.util.ArrayList) r0     // Catch:{ all -> 0x0021 }
            if (r0 == 0) goto L_0x001f
            r0.remove(r3)     // Catch:{ all -> 0x0021 }
            boolean r3 = r0.isEmpty()     // Catch:{ all -> 0x0021 }
            if (r3 == 0) goto L_0x001f
            java.util.HashMap<java.lang.String, java.util.ArrayList<com.google.android.exoplayer2.upstream.cache.Cache$Listener>> r3 = r1.e     // Catch:{ all -> 0x0021 }
            r3.remove(r2)     // Catch:{ all -> 0x0021 }
        L_0x001f:
            monitor-exit(r1)
            return
        L_0x0021:
            r2 = move-exception
            monitor-exit(r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.cache.SimpleCache.removeListener(java.lang.String, com.google.android.exoplayer2.upstream.cache.Cache$Listener):void");
    }

    public synchronized void removeResource(String str) {
        boolean z;
        if (!this.j) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkState(z);
        for (CacheSpan g2 : getCachedSpans(str)) {
            g(g2);
        }
    }

    public synchronized void removeSpan(CacheSpan cacheSpan) {
        boolean z;
        if (!this.j) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkState(z);
        g(cacheSpan);
    }

    public synchronized File startFile(String str, long j2, long j3) throws Cache.CacheException {
        boolean z;
        cu cuVar;
        File file;
        if (!this.j) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkState(z);
        checkInitialization();
        cuVar = this.c.get(str);
        Assertions.checkNotNull(cuVar);
        Assertions.checkState(cuVar.isFullyLocked(j2, j3));
        if (!this.a.exists()) {
            c(this.a);
            h();
        }
        this.b.onStartFile(this, str, j2, j3);
        file = new File(this.a, Integer.toString(this.f.nextInt(10)));
        if (!file.exists()) {
            c(file);
        }
        return xa.getCacheFile(file, cuVar.a, j2, System.currentTimeMillis());
    }

    public synchronized CacheSpan startReadWrite(String str, long j2, long j3) throws InterruptedException, Cache.CacheException {
        boolean z;
        CacheSpan startReadWriteNonBlocking;
        if (!this.j) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkState(z);
        checkInitialization();
        while (true) {
            startReadWriteNonBlocking = startReadWriteNonBlocking(str, j2, j3);
            if (startReadWriteNonBlocking == null) {
                wait();
            }
        }
        return startReadWriteNonBlocking;
    }

    @Nullable
    public synchronized CacheSpan startReadWriteNonBlocking(String str, long j2, long j3) throws Cache.CacheException {
        boolean z;
        xa xaVar;
        xa span;
        if (!this.j) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkState(z);
        checkInitialization();
        cu cuVar = this.c.get(str);
        if (cuVar == null) {
            xaVar = xa.createHole(str, j2, j3);
        } else {
            while (true) {
                span = cuVar.getSpan(j2, j3);
                if (!span.h || span.i.length() == span.g) {
                    xaVar = span;
                } else {
                    h();
                }
            }
            xaVar = span;
        }
        if (xaVar.h) {
            return i(str, xaVar);
        } else if (this.c.getOrAdd(str).lockRange(j2, xaVar.g)) {
            return xaVar;
        } else {
            return null;
        }
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    @Deprecated
    public SimpleCache(File file, CacheEvictor cacheEvictor, @Nullable byte[] bArr) {
        this(file, cacheEvictor, bArr, bArr != null);
    }

    @Deprecated
    public SimpleCache(File file, CacheEvictor cacheEvictor, @Nullable byte[] bArr, boolean z) {
        this(file, cacheEvictor, (DatabaseProvider) null, bArr, z, true);
    }

    public SimpleCache(File file, CacheEvictor cacheEvictor, DatabaseProvider databaseProvider) {
        this(file, cacheEvictor, databaseProvider, (byte[]) null, false, false);
    }

    public SimpleCache(File file, CacheEvictor cacheEvictor, @Nullable DatabaseProvider databaseProvider, @Nullable byte[] bArr, boolean z, boolean z2) {
        boolean add;
        cv cvVar = new cv(databaseProvider, file, bArr, z, z2);
        ct ctVar = (databaseProvider == null || z2) ? null : new ct(databaseProvider);
        synchronized (SimpleCache.class) {
            add = l.add(file.getAbsoluteFile());
        }
        if (add) {
            this.a = file;
            this.b = cacheEvictor;
            this.c = cvVar;
            this.d = ctVar;
            this.e = new HashMap<>();
            this.f = new Random();
            this.g = cacheEvictor.requiresCacheSpanTouches();
            this.h = -1;
            ConditionVariable conditionVariable = new ConditionVariable();
            new wa(this, conditionVariable).start();
            conditionVariable.block();
            return;
        }
        String valueOf = String.valueOf(file);
        StringBuilder sb = new StringBuilder(valueOf.length() + 46);
        sb.append("Another SimpleCache instance uses the folder: ");
        sb.append(valueOf);
        throw new IllegalStateException(sb.toString());
    }
}
