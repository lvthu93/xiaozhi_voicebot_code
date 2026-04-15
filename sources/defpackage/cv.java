package defpackage;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import com.google.android.exoplayer2.database.DatabaseIOException;
import com.google.android.exoplayer2.database.DatabaseProvider;
import com.google.android.exoplayer2.database.VersionTable;
import com.google.android.exoplayer2.upstream.cache.ContentMetadata;
import com.google.android.exoplayer2.upstream.cache.ContentMetadataMutations;
import com.google.android.exoplayer2.upstream.cache.DefaultContentMetadata;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.AtomicFile;
import com.google.android.exoplayer2.util.ReusableBufferedOutputStream;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.UnmodifiableIterator;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* renamed from: cv  reason: default package */
public final class cv {
    public final HashMap<String, cu> a;
    public final SparseArray<String> b;
    public final SparseBooleanArray c;
    public final SparseBooleanArray d;
    public final c e;
    @Nullable
    public c f;

    /* renamed from: cv$a */
    public static final class a implements c {
        public static final String[] e = {"id", "key", "metadata"};
        public final DatabaseProvider a;
        public final SparseArray<cu> b = new SparseArray<>();
        public String c;
        public String d;

        public a(DatabaseProvider databaseProvider) {
            this.a = databaseProvider;
        }

        public static void b(DatabaseProvider databaseProvider, String str) throws DatabaseIOException {
            String str2;
            SQLiteDatabase writableDatabase;
            String str3;
            try {
                String valueOf = String.valueOf(str);
                if (valueOf.length() != 0) {
                    str2 = "ExoPlayerCacheIndex".concat(valueOf);
                } else {
                    str2 = new String("ExoPlayerCacheIndex");
                }
                writableDatabase = databaseProvider.getWritableDatabase();
                writableDatabase.beginTransactionNonExclusive();
                VersionTable.removeVersion(writableDatabase, 1, str);
                String valueOf2 = String.valueOf(str2);
                if (valueOf2.length() != 0) {
                    str3 = "DROP TABLE IF EXISTS ".concat(valueOf2);
                } else {
                    str3 = new String("DROP TABLE IF EXISTS ");
                }
                writableDatabase.execSQL(str3);
                writableDatabase.setTransactionSuccessful();
                writableDatabase.endTransaction();
            } catch (SQLException e2) {
                throw new DatabaseIOException(e2);
            } catch (Throwable th) {
                writableDatabase.endTransaction();
                throw th;
            }
        }

        public static void delete(DatabaseProvider databaseProvider, long j) throws DatabaseIOException {
            b(databaseProvider, Long.toHexString(j));
        }

        public final void a(SQLiteDatabase sQLiteDatabase, cu cuVar) throws IOException {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            cv.b(cuVar.getMetadata(), new DataOutputStream(byteArrayOutputStream));
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            ContentValues contentValues = new ContentValues();
            contentValues.put("id", Integer.valueOf(cuVar.a));
            contentValues.put("key", cuVar.b);
            contentValues.put("metadata", byteArray);
            sQLiteDatabase.replaceOrThrow((String) Assertions.checkNotNull(this.d), (String) null, contentValues);
        }

        public final void c(SQLiteDatabase sQLiteDatabase) throws DatabaseIOException {
            String str;
            VersionTable.setVersion(sQLiteDatabase, 1, (String) Assertions.checkNotNull(this.c), 1);
            String valueOf = String.valueOf((String) Assertions.checkNotNull(this.d));
            if (valueOf.length() != 0) {
                str = "DROP TABLE IF EXISTS ".concat(valueOf);
            } else {
                str = new String("DROP TABLE IF EXISTS ");
            }
            sQLiteDatabase.execSQL(str);
            String str2 = this.d;
            StringBuilder sb = new StringBuilder(y2.c(str2, 88));
            sb.append("CREATE TABLE ");
            sb.append(str2);
            sb.append(" (id INTEGER PRIMARY KEY NOT NULL,key TEXT NOT NULL,metadata BLOB NOT NULL)");
            sQLiteDatabase.execSQL(sb.toString());
        }

        public boolean exists() throws DatabaseIOException {
            if (VersionTable.getVersion(this.a.getReadableDatabase(), 1, (String) Assertions.checkNotNull(this.c)) != -1) {
                return true;
            }
            return false;
        }

        public void initialize(long j) {
            String str;
            String hexString = Long.toHexString(j);
            this.c = hexString;
            String valueOf = String.valueOf(hexString);
            if (valueOf.length() != 0) {
                str = "ExoPlayerCacheIndex".concat(valueOf);
            } else {
                str = new String("ExoPlayerCacheIndex");
            }
            this.d = str;
        }

        public void load(HashMap<String, cu> hashMap, SparseArray<String> sparseArray) throws IOException {
            boolean z;
            Cursor query;
            SQLiteDatabase writableDatabase;
            DatabaseProvider databaseProvider = this.a;
            if (this.b.size() == 0) {
                z = true;
            } else {
                z = false;
            }
            Assertions.checkState(z);
            try {
                if (VersionTable.getVersion(databaseProvider.getReadableDatabase(), 1, (String) Assertions.checkNotNull(this.c)) != 1) {
                    writableDatabase = databaseProvider.getWritableDatabase();
                    writableDatabase.beginTransactionNonExclusive();
                    c(writableDatabase);
                    writableDatabase.setTransactionSuccessful();
                    writableDatabase.endTransaction();
                }
                query = databaseProvider.getReadableDatabase().query((String) Assertions.checkNotNull(this.d), e, (String) null, (String[]) null, (String) null, (String) null, (String) null);
                while (query.moveToNext()) {
                    cu cuVar = new cu(query.getInt(0), query.getString(1), cv.a(new DataInputStream(new ByteArrayInputStream(query.getBlob(2)))));
                    String str = cuVar.b;
                    hashMap.put(str, cuVar);
                    sparseArray.put(cuVar.a, str);
                }
                query.close();
                return;
            } catch (SQLiteException e2) {
                hashMap.clear();
                sparseArray.clear();
                throw new DatabaseIOException(e2);
            } catch (Throwable th) {
                writableDatabase.endTransaction();
                throw th;
            }
            throw th;
        }

        public void onRemove(cu cuVar, boolean z) {
            SparseArray<cu> sparseArray = this.b;
            if (z) {
                sparseArray.delete(cuVar.a);
            } else {
                sparseArray.put(cuVar.a, (Object) null);
            }
        }

        public void onUpdate(cu cuVar) {
            this.b.put(cuVar.a, cuVar);
        }

        public void storeFully(HashMap<String, cu> hashMap) throws IOException {
            SQLiteDatabase writableDatabase;
            try {
                writableDatabase = this.a.getWritableDatabase();
                writableDatabase.beginTransactionNonExclusive();
                c(writableDatabase);
                for (cu a2 : hashMap.values()) {
                    a(writableDatabase, a2);
                }
                writableDatabase.setTransactionSuccessful();
                this.b.clear();
                writableDatabase.endTransaction();
            } catch (SQLException e2) {
                throw new DatabaseIOException(e2);
            } catch (Throwable th) {
                writableDatabase.endTransaction();
                throw th;
            }
        }

        public void storeIncremental(HashMap<String, cu> hashMap) throws IOException {
            SQLiteDatabase writableDatabase;
            SparseArray<cu> sparseArray = this.b;
            if (sparseArray.size() != 0) {
                try {
                    writableDatabase = this.a.getWritableDatabase();
                    writableDatabase.beginTransactionNonExclusive();
                    for (int i = 0; i < sparseArray.size(); i++) {
                        cu valueAt = sparseArray.valueAt(i);
                        if (valueAt == null) {
                            writableDatabase.delete((String) Assertions.checkNotNull(this.d), "id = ?", new String[]{Integer.toString(sparseArray.keyAt(i))});
                        } else {
                            a(writableDatabase, valueAt);
                        }
                    }
                    writableDatabase.setTransactionSuccessful();
                    sparseArray.clear();
                    writableDatabase.endTransaction();
                } catch (SQLException e2) {
                    throw new DatabaseIOException(e2);
                } catch (Throwable th) {
                    writableDatabase.endTransaction();
                    throw th;
                }
            }
        }

        public void delete() throws DatabaseIOException {
            b(this.a, (String) Assertions.checkNotNull(this.c));
        }
    }

    /* renamed from: cv$b */
    public static class b implements c {
        public final boolean a;
        @Nullable
        public final Cipher b;
        @Nullable
        public final SecretKeySpec c;
        @Nullable
        public final SecureRandom d;
        public final AtomicFile e;
        public boolean f;
        @Nullable
        public ReusableBufferedOutputStream g;

        /* JADX WARNING: Can't wrap try/catch for region: R(7:11|12|13|(4:15|16|17|20)|18|19|20) */
        /* JADX WARNING: Missing exception handler attribute for start block: B:18:0x002b */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public b(java.io.File r6, @androidx.annotation.Nullable byte[] r7, boolean r8) {
            /*
                r5 = this;
                r5.<init>()
                r0 = 0
                r1 = 1
                if (r7 != 0) goto L_0x000c
                if (r8 != 0) goto L_0x000a
                goto L_0x000c
            L_0x000a:
                r2 = 0
                goto L_0x000d
            L_0x000c:
                r2 = 1
            L_0x000d:
                com.google.android.exoplayer2.util.Assertions.checkState(r2)
                r2 = 0
                if (r7 == 0) goto L_0x0040
                int r3 = r7.length
                r4 = 16
                if (r3 != r4) goto L_0x0019
                r0 = 1
            L_0x0019:
                com.google.android.exoplayer2.util.Assertions.checkArgument(r0)
                int r0 = com.google.android.exoplayer2.util.Util.a     // Catch:{ NoSuchAlgorithmException -> 0x0039, NoSuchPaddingException -> 0x0037 }
                java.lang.String r1 = "AES/CBC/PKCS5PADDING"
                r3 = 18
                if (r0 != r3) goto L_0x002b
                java.lang.String r0 = "BC"
                javax.crypto.Cipher r0 = javax.crypto.Cipher.getInstance(r1, r0)     // Catch:{ all -> 0x002b }
                goto L_0x002f
            L_0x002b:
                javax.crypto.Cipher r0 = javax.crypto.Cipher.getInstance(r1)     // Catch:{ NoSuchAlgorithmException -> 0x0039, NoSuchPaddingException -> 0x0037 }
            L_0x002f:
                javax.crypto.spec.SecretKeySpec r1 = new javax.crypto.spec.SecretKeySpec     // Catch:{ NoSuchAlgorithmException -> 0x0039, NoSuchPaddingException -> 0x0037 }
                java.lang.String r3 = "AES"
                r1.<init>(r7, r3)     // Catch:{ NoSuchAlgorithmException -> 0x0039, NoSuchPaddingException -> 0x0037 }
                goto L_0x0047
            L_0x0037:
                r6 = move-exception
                goto L_0x003a
            L_0x0039:
                r6 = move-exception
            L_0x003a:
                java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                r7.<init>(r6)
                throw r7
            L_0x0040:
                r7 = r8 ^ 1
                com.google.android.exoplayer2.util.Assertions.checkArgument(r7)
                r0 = r2
                r1 = r0
            L_0x0047:
                r5.a = r8
                r5.b = r0
                r5.c = r1
                if (r8 == 0) goto L_0x0054
                java.security.SecureRandom r2 = new java.security.SecureRandom
                r2.<init>()
            L_0x0054:
                r5.d = r2
                com.google.android.exoplayer2.util.AtomicFile r7 = new com.google.android.exoplayer2.util.AtomicFile
                r7.<init>(r6)
                r5.e = r7
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: defpackage.cv.b.<init>(java.io.File, byte[], boolean):void");
        }

        public static int a(cu cuVar, int i) {
            int hashCode = cuVar.b.hashCode() + (cuVar.a * 31);
            if (i >= 2) {
                return (hashCode * 31) + cuVar.getMetadata().hashCode();
            }
            long a2 = t0.a(cuVar.getMetadata());
            return (hashCode * 31) + ((int) (a2 ^ (a2 >>> 32)));
        }

        public static cu b(int i, DataInputStream dataInputStream) throws IOException {
            DefaultContentMetadata defaultContentMetadata;
            int readInt = dataInputStream.readInt();
            String readUTF = dataInputStream.readUTF();
            if (i < 2) {
                long readLong = dataInputStream.readLong();
                ContentMetadataMutations contentMetadataMutations = new ContentMetadataMutations();
                ContentMetadataMutations.setContentLength(contentMetadataMutations, readLong);
                defaultContentMetadata = DefaultContentMetadata.c.copyWithMutationsApplied(contentMetadataMutations);
            } else {
                defaultContentMetadata = cv.a(dataInputStream);
            }
            return new cu(readInt, readUTF, defaultContentMetadata);
        }

        public void delete() {
            this.e.delete();
        }

        public boolean exists() {
            return this.e.exists();
        }

        public void initialize(long j) {
        }

        /* JADX WARNING: Removed duplicated region for block: B:52:0x00ac  */
        /* JADX WARNING: Removed duplicated region for block: B:56:0x00b3  */
        /* JADX WARNING: Removed duplicated region for block: B:59:0x00b9  */
        /* JADX WARNING: Removed duplicated region for block: B:62:? A[RETURN, SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void load(java.util.HashMap<java.lang.String, defpackage.cu> r12, android.util.SparseArray<java.lang.String> r13) {
            /*
                r11 = this;
                boolean r0 = r11.f
                r1 = 1
                r0 = r0 ^ r1
                com.google.android.exoplayer2.util.Assertions.checkState(r0)
                com.google.android.exoplayer2.util.AtomicFile r0 = r11.e
                boolean r2 = r0.exists()
                if (r2 != 0) goto L_0x0011
                goto L_0x00b7
            L_0x0011:
                r2 = 0
                r3 = 0
                java.io.BufferedInputStream r4 = new java.io.BufferedInputStream     // Catch:{ IOException -> 0x00b0, all -> 0x00a9 }
                java.io.InputStream r5 = r0.openRead()     // Catch:{ IOException -> 0x00b0, all -> 0x00a9 }
                r4.<init>(r5)     // Catch:{ IOException -> 0x00b0, all -> 0x00a9 }
                java.io.DataInputStream r5 = new java.io.DataInputStream     // Catch:{ IOException -> 0x00b0, all -> 0x00a9 }
                r5.<init>(r4)     // Catch:{ IOException -> 0x00b0, all -> 0x00a9 }
                int r3 = r5.readInt()     // Catch:{ IOException -> 0x00a6, all -> 0x00a3 }
                if (r3 < 0) goto L_0x009f
                r6 = 2
                if (r3 <= r6) goto L_0x002c
                goto L_0x009f
            L_0x002c:
                int r7 = r5.readInt()     // Catch:{ IOException -> 0x00a6, all -> 0x00a3 }
                r7 = r7 & r1
                if (r7 == 0) goto L_0x0064
                javax.crypto.Cipher r7 = r11.b
                if (r7 != 0) goto L_0x0038
                goto L_0x009f
            L_0x0038:
                r8 = 16
                byte[] r8 = new byte[r8]     // Catch:{ IOException -> 0x00a6, all -> 0x00a3 }
                r5.readFully(r8)     // Catch:{ IOException -> 0x00a6, all -> 0x00a3 }
                javax.crypto.spec.IvParameterSpec r9 = new javax.crypto.spec.IvParameterSpec     // Catch:{ IOException -> 0x00a6, all -> 0x00a3 }
                r9.<init>(r8)     // Catch:{ IOException -> 0x00a6, all -> 0x00a3 }
                javax.crypto.spec.SecretKeySpec r8 = r11.c     // Catch:{ InvalidKeyException -> 0x005d, InvalidAlgorithmParameterException -> 0x005b }
                java.lang.Object r8 = com.google.android.exoplayer2.util.Util.castNonNull(r8)     // Catch:{ InvalidKeyException -> 0x005d, InvalidAlgorithmParameterException -> 0x005b }
                java.security.Key r8 = (java.security.Key) r8     // Catch:{ InvalidKeyException -> 0x005d, InvalidAlgorithmParameterException -> 0x005b }
                r7.init(r6, r8, r9)     // Catch:{ InvalidKeyException -> 0x005d, InvalidAlgorithmParameterException -> 0x005b }
                java.io.DataInputStream r6 = new java.io.DataInputStream     // Catch:{ IOException -> 0x00a6, all -> 0x00a3 }
                javax.crypto.CipherInputStream r8 = new javax.crypto.CipherInputStream     // Catch:{ IOException -> 0x00a6, all -> 0x00a3 }
                r8.<init>(r4, r7)     // Catch:{ IOException -> 0x00a6, all -> 0x00a3 }
                r6.<init>(r8)     // Catch:{ IOException -> 0x00a6, all -> 0x00a3 }
                r5 = r6
                goto L_0x006a
            L_0x005b:
                r1 = move-exception
                goto L_0x005e
            L_0x005d:
                r1 = move-exception
            L_0x005e:
                java.lang.IllegalStateException r3 = new java.lang.IllegalStateException     // Catch:{ IOException -> 0x00a6, all -> 0x00a3 }
                r3.<init>(r1)     // Catch:{ IOException -> 0x00a6, all -> 0x00a3 }
                throw r3     // Catch:{ IOException -> 0x00a6, all -> 0x00a3 }
            L_0x0064:
                boolean r4 = r11.a     // Catch:{ IOException -> 0x00a6, all -> 0x00a3 }
                if (r4 == 0) goto L_0x006a
                r11.f = r1     // Catch:{ IOException -> 0x00a6, all -> 0x00a3 }
            L_0x006a:
                int r4 = r5.readInt()     // Catch:{ IOException -> 0x00a6, all -> 0x00a3 }
                r6 = 0
                r7 = 0
            L_0x0070:
                if (r6 >= r4) goto L_0x0088
                cu r8 = b(r3, r5)     // Catch:{ IOException -> 0x00a6, all -> 0x00a3 }
                java.lang.String r9 = r8.b
                r12.put(r9, r8)     // Catch:{ IOException -> 0x00a6, all -> 0x00a3 }
                int r10 = r8.a     // Catch:{ IOException -> 0x00a6, all -> 0x00a3 }
                r13.put(r10, r9)     // Catch:{ IOException -> 0x00a6, all -> 0x00a3 }
                int r8 = a(r8, r3)     // Catch:{ IOException -> 0x00a6, all -> 0x00a3 }
                int r7 = r7 + r8
                int r6 = r6 + 1
                goto L_0x0070
            L_0x0088:
                int r3 = r5.readInt()     // Catch:{ IOException -> 0x00a6, all -> 0x00a3 }
                int r4 = r5.read()     // Catch:{ IOException -> 0x00a6, all -> 0x00a3 }
                r6 = -1
                if (r4 != r6) goto L_0x0095
                r4 = 1
                goto L_0x0096
            L_0x0095:
                r4 = 0
            L_0x0096:
                if (r3 != r7) goto L_0x009f
                if (r4 != 0) goto L_0x009b
                goto L_0x009f
            L_0x009b:
                com.google.android.exoplayer2.util.Util.closeQuietly((java.io.Closeable) r5)
                goto L_0x00b7
            L_0x009f:
                com.google.android.exoplayer2.util.Util.closeQuietly((java.io.Closeable) r5)
                goto L_0x00b6
            L_0x00a3:
                r12 = move-exception
                r3 = r5
                goto L_0x00aa
            L_0x00a6:
                r3 = r5
                goto L_0x00b1
            L_0x00a9:
                r12 = move-exception
            L_0x00aa:
                if (r3 == 0) goto L_0x00af
                com.google.android.exoplayer2.util.Util.closeQuietly((java.io.Closeable) r3)
            L_0x00af:
                throw r12
            L_0x00b0:
            L_0x00b1:
                if (r3 == 0) goto L_0x00b6
                com.google.android.exoplayer2.util.Util.closeQuietly((java.io.Closeable) r3)
            L_0x00b6:
                r1 = 0
            L_0x00b7:
                if (r1 != 0) goto L_0x00c2
                r12.clear()
                r13.clear()
                r0.delete()
            L_0x00c2:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: defpackage.cv.b.load(java.util.HashMap, android.util.SparseArray):void");
        }

        public void onRemove(cu cuVar, boolean z) {
            this.f = true;
        }

        public void onUpdate(cu cuVar) {
            this.f = true;
        }

        public void storeFully(HashMap<String, cu> hashMap) throws IOException {
            Cipher cipher = this.b;
            AtomicFile atomicFile = this.e;
            DataOutputStream dataOutputStream = null;
            try {
                OutputStream startWrite = atomicFile.startWrite();
                ReusableBufferedOutputStream reusableBufferedOutputStream = this.g;
                if (reusableBufferedOutputStream == null) {
                    this.g = new ReusableBufferedOutputStream(startWrite);
                } else {
                    reusableBufferedOutputStream.reset(startWrite);
                }
                ReusableBufferedOutputStream reusableBufferedOutputStream2 = this.g;
                DataOutputStream dataOutputStream2 = new DataOutputStream(reusableBufferedOutputStream2);
                try {
                    dataOutputStream2.writeInt(2);
                    boolean z = this.a;
                    dataOutputStream2.writeInt(z ? 1 : 0);
                    if (z) {
                        byte[] bArr = new byte[16];
                        ((SecureRandom) Util.castNonNull(this.d)).nextBytes(bArr);
                        dataOutputStream2.write(bArr);
                        ((Cipher) Util.castNonNull(cipher)).init(1, (Key) Util.castNonNull(this.c), new IvParameterSpec(bArr));
                        dataOutputStream2.flush();
                        dataOutputStream2 = new DataOutputStream(new CipherOutputStream(reusableBufferedOutputStream2, cipher));
                    }
                    dataOutputStream2.writeInt(hashMap.size());
                    int i = 0;
                    for (cu next : hashMap.values()) {
                        dataOutputStream2.writeInt(next.a);
                        dataOutputStream2.writeUTF(next.b);
                        cv.b(next.getMetadata(), dataOutputStream2);
                        i += a(next, 2);
                    }
                    dataOutputStream2.writeInt(i);
                    atomicFile.endWrite(dataOutputStream2);
                    Util.closeQuietly((Closeable) null);
                    this.f = false;
                } catch (InvalidKeyException e2) {
                    e = e2;
                    throw new IllegalStateException(e);
                } catch (InvalidAlgorithmParameterException e3) {
                    e = e3;
                    throw new IllegalStateException(e);
                } catch (Throwable th) {
                    th = th;
                    dataOutputStream = dataOutputStream2;
                    Util.closeQuietly((Closeable) dataOutputStream);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                Util.closeQuietly((Closeable) dataOutputStream);
                throw th;
            }
        }

        public void storeIncremental(HashMap<String, cu> hashMap) throws IOException {
            if (this.f) {
                storeFully(hashMap);
            }
        }
    }

    /* renamed from: cv$c */
    public interface c {
        void delete() throws IOException;

        boolean exists() throws IOException;

        void initialize(long j);

        void load(HashMap<String, cu> hashMap, SparseArray<String> sparseArray) throws IOException;

        void onRemove(cu cuVar, boolean z);

        void onUpdate(cu cuVar);

        void storeFully(HashMap<String, cu> hashMap) throws IOException;

        void storeIncremental(HashMap<String, cu> hashMap) throws IOException;
    }

    public cv(DatabaseProvider databaseProvider) {
        this(databaseProvider, (File) null, (byte[]) null, false, false);
    }

    public static DefaultContentMetadata a(DataInputStream dataInputStream) throws IOException {
        int readInt = dataInputStream.readInt();
        HashMap hashMap = new HashMap();
        int i = 0;
        while (i < readInt) {
            String readUTF = dataInputStream.readUTF();
            int readInt2 = dataInputStream.readInt();
            if (readInt2 >= 0) {
                int min = Math.min(readInt2, 10485760);
                byte[] bArr = Util.f;
                int i2 = 0;
                while (i2 != readInt2) {
                    int i3 = i2 + min;
                    bArr = Arrays.copyOf(bArr, i3);
                    dataInputStream.readFully(bArr, i2, min);
                    min = Math.min(readInt2 - i3, 10485760);
                    i2 = i3;
                }
                hashMap.put(readUTF, bArr);
                i++;
            } else {
                throw new IOException(y2.d(31, "Invalid value size: ", readInt2));
            }
        }
        return new DefaultContentMetadata(hashMap);
    }

    public static void b(DefaultContentMetadata defaultContentMetadata, DataOutputStream dataOutputStream) throws IOException {
        Set<Map.Entry<String, byte[]>> entrySet = defaultContentMetadata.entrySet();
        dataOutputStream.writeInt(entrySet.size());
        for (Map.Entry next : entrySet) {
            dataOutputStream.writeUTF((String) next.getKey());
            byte[] bArr = (byte[]) next.getValue();
            dataOutputStream.writeInt(bArr.length);
            dataOutputStream.write(bArr);
        }
    }

    @WorkerThread
    public static void delete(DatabaseProvider databaseProvider, long j) throws DatabaseIOException {
        a.delete(databaseProvider, j);
    }

    public static boolean isIndexFile(String str) {
        return str.startsWith("cached_content_index.exi");
    }

    public void applyContentMetadataMutations(String str, ContentMetadataMutations contentMetadataMutations) {
        cu orAdd = getOrAdd(str);
        if (orAdd.applyMetadataMutations(contentMetadataMutations)) {
            this.e.onUpdate(orAdd);
        }
    }

    public int assignIdForKey(String str) {
        return getOrAdd(str).a;
    }

    @Nullable
    public cu get(String str) {
        return this.a.get(str);
    }

    public Collection<cu> getAll() {
        return Collections.unmodifiableCollection(this.a.values());
    }

    public ContentMetadata getContentMetadata(String str) {
        cu cuVar = get(str);
        if (cuVar != null) {
            return cuVar.getMetadata();
        }
        return DefaultContentMetadata.c;
    }

    @Nullable
    public String getKeyForId(int i) {
        return this.b.get(i);
    }

    public Set<String> getKeys() {
        return this.a.keySet();
    }

    public cu getOrAdd(String str) {
        int i;
        HashMap<String, cu> hashMap = this.a;
        cu cuVar = hashMap.get(str);
        if (cuVar != null) {
            return cuVar;
        }
        SparseArray<String> sparseArray = this.b;
        int size = sparseArray.size();
        int i2 = 0;
        if (size == 0) {
            i = 0;
        } else {
            i = sparseArray.keyAt(size - 1) + 1;
        }
        if (i < 0) {
            while (i2 < size && i2 == sparseArray.keyAt(i2)) {
                i2++;
            }
            i = i2;
        }
        cu cuVar2 = new cu(i, str);
        hashMap.put(str, cuVar2);
        sparseArray.put(i, str);
        this.d.put(i, true);
        this.e.onUpdate(cuVar2);
        return cuVar2;
    }

    @WorkerThread
    public void initialize(long j) throws IOException {
        c cVar;
        c cVar2 = this.e;
        cVar2.initialize(j);
        c cVar3 = this.f;
        if (cVar3 != null) {
            cVar3.initialize(j);
        }
        boolean exists = cVar2.exists();
        SparseArray<String> sparseArray = this.b;
        HashMap<String, cu> hashMap = this.a;
        if (exists || (cVar = this.f) == null || !cVar.exists()) {
            cVar2.load(hashMap, sparseArray);
        } else {
            this.f.load(hashMap, sparseArray);
            cVar2.storeFully(hashMap);
        }
        c cVar4 = this.f;
        if (cVar4 != null) {
            cVar4.delete();
            this.f = null;
        }
    }

    public void maybeRemove(String str) {
        HashMap<String, cu> hashMap = this.a;
        cu cuVar = hashMap.get(str);
        if (cuVar != null && cuVar.isEmpty() && cuVar.isFullyUnlocked()) {
            hashMap.remove(str);
            SparseBooleanArray sparseBooleanArray = this.d;
            int i = cuVar.a;
            boolean z = sparseBooleanArray.get(i);
            this.e.onRemove(cuVar, z);
            SparseArray<String> sparseArray = this.b;
            if (z) {
                sparseArray.remove(i);
                sparseBooleanArray.delete(i);
                return;
            }
            sparseArray.put(i, (Object) null);
            this.c.put(i, true);
        }
    }

    public void removeEmpty() {
        UnmodifiableIterator<String> it = ImmutableSet.copyOf(this.a.keySet()).iterator();
        while (it.hasNext()) {
            maybeRemove(it.next());
        }
    }

    @WorkerThread
    public void store() throws IOException {
        this.e.storeIncremental(this.a);
        SparseBooleanArray sparseBooleanArray = this.c;
        int size = sparseBooleanArray.size();
        for (int i = 0; i < size; i++) {
            this.b.remove(sparseBooleanArray.keyAt(i));
        }
        sparseBooleanArray.clear();
        this.d.clear();
    }

    public cv(@Nullable DatabaseProvider databaseProvider, @Nullable File file, @Nullable byte[] bArr, boolean z, boolean z2) {
        Assertions.checkState((databaseProvider == null && file == null) ? false : true);
        this.a = new HashMap<>();
        this.b = new SparseArray<>();
        this.c = new SparseBooleanArray();
        this.d = new SparseBooleanArray();
        b bVar = null;
        a aVar = databaseProvider != null ? new a(databaseProvider) : null;
        bVar = file != null ? new b(new File(file, "cached_content_index.exi"), bArr, z) : bVar;
        if (aVar == null || (bVar != null && z2)) {
            this.e = (c) Util.castNonNull(bVar);
            this.f = aVar;
            return;
        }
        this.e = aVar;
        this.f = bVar;
    }
}
